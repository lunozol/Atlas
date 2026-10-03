# Complete Radbus Documentation & Hooking Guide
**Target Platform:** Minecraft 1.8.9 (Minecraft Coder Pack / GradleMCPBase)  
**Client:** Atlas (`io.lunozol.atlas`)  
**Library:** [radbus](https://github.com/nevalackin/radbus) (`io.github.nevalackin:radbus:1.0.0`)

---

## 1. What is Radbus?

**Radbus** is a high-performance, lightweight, dependency-free pub-sub event bus designed specifically for Java applications and Minecraft clients by `nevalackin`.

### Key Technical Characteristics:
- **Zero Runtime Reflection Overhead:** While it uses reflection once at registration time to discover handlers, it compiles handler calls into high-performance JVM call sites using `java.lang.invoke.LambdaMetafactory`. Dispatch performance is comparable to direct method invocation.
- **Exact Class Dispatch:** Events are matched strictly by `event.getClass()`. Listeners must subscribe to the exact concrete event class being published (e.g., `Render2DEvent`), rather than an abstract base class or interface.
- **LIFO Execution Order:** Listeners registered last are invoked first (reverse iteration).
- **Type-Safe Event Bounds:** When initializing `PubSub<Event>`, every `@Listen` method parameter must be assignable to `Event`.

---

## 2. Radbus API Overview

Radbus consists of four core components in package `io.github.nevalackin.radbus`:

### 1. `PubSub<Event>`
The primary event bus interface.
- `static <Event> PubSub<Event> newInstance(Consumer<String> errorLogger)`: Factory method to create an instance with an error handler (e.g., `System.err::println` or log4j).
- `void subscribe(Object subscriber)`: Scans declared methods of `subscriber` for `@Listen` and binds them.
- `void unsubscribe(Object subscriber)`: Unsubscribes an object and rebuilds the handler cache.
- `<T extends Event> void subscribe(Class<T> event, Listener<T> listener)`: Programmatically registers a lambda/functional listener.
- `void publish(Event event)`: Publishes an event to all subscribed listeners.
- `void clear()`: Clears all subscribers and caches.

### 2. `@Listen`
An annotation for marking event listener methods.

> **Strict Method Rules for `@Listen`:**
> 1. Must be **`public`**
> 2. Must be **non-static**
> 3. Must be **non-abstract**
> 4. Must be **non-native**
> 5. Return type must be **`void`**
> 6. Must accept **exactly 1 parameter** (the concrete event type)
> 7. The parameter must inherit from the bus's `<Event>` type.
> 
> *Note on Inheritance:* Radbus uses `subscriber.getClass().getDeclaredMethods()`. Listener methods must be declared directly on the class being subscribed, not inherited from an abstract superclass without overriding!

### 3. `Listener<Event>`
Functional interface for lambda-based listeners:
```java
@FunctionalInterface
public interface Listener<Event> {
    void invoke(Event event);
}
```

---

## 3. Implementing the Event System in Atlas

### Step 3.1: Create the Base `Event` Class
Create `src/main/java/io/lunozol/atlas/system/event/Event.java`:

```java
package io.lunozol.atlas.system.event;

public abstract class Event {
    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }
}
```

---

### Step 3.2: Initialize the EventBus in `Atlas.java`
Update `src/main/java/io/lunozol/atlas/Atlas.java` to instantiate and expose the `PubSub` instance:

```java
package io.lunozol.atlas;

import io.github.nevalackin.radbus.PubSub;
import io.lunozol.atlas.system.event.Event;
import io.lunozol.atlas.system.module.ModuleManager;
import org.lwjgl.opengl.Display;

import java.awt.*;

public class Atlas {
    public static Atlas instance;
    public ModuleManager moduleManager = new ModuleManager();

    // Radbus instance bounded to your Event base class
    private final PubSub<Event> eventBus = PubSub.newInstance(System.err::println);

    public static final String name = "Atlas", version = "October 4th 2026";
    public static Color color = Color.green;

    public void init() {
        Display.setTitle(name + " " + version);
        moduleManager.init();
        System.out.println("Initialised Atlas " + moduleManager.getModules().size() + " modules loaded");
    }

    public PubSub<Event> getEventBus() {
        return eventBus;
    }

    public static Atlas getInstance() {
        if (instance == null) instance = new Atlas();
        return instance;
    }
}
```

---

### Step 3.3: Hook the EventBus into `Module.java`
Automatically register/unregister modules with Radbus when enabled or disabled:

```java
package io.lunozol.atlas.system.module;

import io.lunozol.atlas.Atlas;

public abstract class Module {
    private final String name;
    private final String description;
    private final ModuleCategory category;
    private boolean isEnabled;

    public Module(String name, String description, ModuleCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean state) {
        if (this.isEnabled != state) {
            this.isEnabled = state;
            if (state) {
                onEnable();
                Atlas.getInstance().getEventBus().subscribe(this);
            } else {
                Atlas.getInstance().getEventBus().unsubscribe(this);
                onDisable();
            }
        }
    }

    public void toggle() {
        setEnabled(!isEnabled);
    }

    public void onEnable() {}
    public void onDisable() {}

    public String getName() { return name; }
    public String getDescription() { return description; }
    public ModuleCategory getCategory() { return category; }
}
```

---

## 4. Creating Event Classes

Below are common events required for a Minecraft 1.8.9 client. Place these in `src/main/java/io/lunozol/atlas/system/event/events`.

### 1. `GameLoopEvent.java`
Triggered every frame in Minecraft's main game loop:
```java
package io.lunozol.atlas.system.event.events;

import io.lunozol.atlas.system.event.Event;

public class GameLoopEvent extends Event {
}
```

### 2. `Render2DEvent.java`
Triggered when rendering the 2D HUD (text, arrays, watermarks):
```java
package io.lunozol.atlas.system.event.events;

import io.lunozol.atlas.system.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent extends Event {
    private final ScaledResolution scaledResolution;
    private final float partialTicks;

    public Render2DEvent(ScaledResolution scaledResolution, float partialTicks) {
        this.scaledResolution = scaledResolution;
        this.partialTicks = partialTicks;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
```

### 3. `Render3DEvent.java`
Triggered when rendering in-world 3D objects (ESP, Tracers, Chams):
```java
package io.lunozol.atlas.system.event.events;

import io.lunozol.atlas.system.event.Event;

public class Render3DEvent extends Event {
    private final float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
```

### 4. `MotionEvent.java`
Triggered before and after sending player position and rotations to the server:
```java
package io.lunozol.atlas.system.event.events;

import io.lunozol.atlas.system.event.Event;

public class MotionEvent extends Event {
    public enum Era { PRE, POST }

    private final Era era;
    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;

    public MotionEvent(Era era, double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.era = era;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public Era getEra() { return era; }
    public boolean isPre() { return era == Era.PRE; }
    public boolean isPost() { return era == Era.POST; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getZ() { return z; }
    public void setZ(double z) { this.z = z; }

    public float getYaw() { return yaw; }
    public void setYaw(float yaw) { this.yaw = yaw; }

    public float getPitch() { return pitch; }
    public void setPitch(float pitch) { this.pitch = pitch; }

    public boolean isOnGround() { return onGround; }
    public void setOnGround(boolean onGround) { this.onGround = onGround; }
}
```

### 5. `KeyEvent.java`
Triggered on keyboard input:
```java
package io.lunozol.atlas.system.event.events;

import io.lunozol.atlas.system.event.Event;

public class KeyEvent extends Event {
    private final int key;

    public KeyEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
```

### 6. `PacketEvent.java` (Send & Receive)
```java
package io.lunozol.atlas.system.event.events;

import io.lunozol.atlas.system.event.Event;
import net.minecraft.network.Packet;

public class PacketEvent extends Event {
    private Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public static class Send extends PacketEvent {
        public Send(Packet<?> packet) { super(packet); }
    }

    public static class Receive extends PacketEvent {
        public Receive(Packet<?> packet) { super(packet); }
    }
}
```

---

## 5. Hooking Events into Minecraft 1.8.9 (MCP)

Here is where to place your hook calls in MCP's decompiled source:

### Hook 1: GameLoop (`Minecraft.java`)
**File:** `net.minecraft.client.Minecraft.java`  
**Method:** `runGameLoop()`  
**Hook Placement:**
```java
private void runGameLoop() throws IOException
{
    // Hook Radbus GameLoopEvent
    Atlas.getInstance().getEventBus().publish(new GameLoopEvent());
    
    // ... rest of runGameLoop ...
```

---

### Hook 2: Key Press (`Minecraft.java`)
**File:** `net.minecraft.client.Minecraft.java`  
**Method:** `runTick()`  
**Hook Placement:** Find `Keyboard.next()` inside `runTick()`:
```java
if (Keyboard.getEventKeyState())
{
    int key = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
    
    // Hook Radbus KeyEvent
    Atlas.getInstance().getEventBus().publish(new KeyEvent(key));
    
    // ... rest of key handling ...
```

---

### Hook 3: 2D HUD Rendering (`GuiIngame.java`)
**File:** `net.minecraft.client.gui.GuiIngame.java`  
**Method:** `renderGameOverlay(float partialTicks)`  
**Hook Placement:** At the bottom of `renderGameOverlay`:
```java
    // Hook Radbus Render2DEvent
    ScaledResolution sr = new ScaledResolution(this.mc);
    Atlas.getInstance().getEventBus().publish(new Render2DEvent(sr, partialTicks));
}
```

---

### Hook 4: 3D World Rendering (`EntityRenderer.java`)
**File:** `net.minecraft.client.renderer.EntityRenderer.java`  
**Method:** `renderWorldPass(int pass, float partialTicks, long finishTimeNano)`  
**Hook Placement:** Near the end of `renderWorldPass` after hand/world rendering:
```java
    // Hook Radbus Render3DEvent
    Atlas.getInstance().getEventBus().publish(new Render3DEvent(partialTicks));
}
```

---

### Hook 5: Player Motion Update (`EntityPlayerSP.java`)
**File:** `net.minecraft.client.entity.EntityPlayerSP.java`  
**Method:** `onUpdateWalkingPlayer()`  
**Hook Placement:**
```java
public void onUpdateWalkingPlayer()
{
    // 1. Publish Pre Event
    MotionEvent preEvent = new MotionEvent(
        MotionEvent.Era.PRE,
        this.posX, this.getEntityBoundingBox().minY, this.posZ,
        this.rotationYaw, this.rotationPitch,
        this.onGround
    );
    Atlas.getInstance().getEventBus().publish(preEvent);

    if (preEvent.isCancelled()) return;

    // Use values modified by modules (e.g. KillAura rotations / Scaffold pos)
    double origX = this.posX;
    double origY = this.getEntityBoundingBox().minY;
    double origZ = this.posZ;
    float origYaw = this.rotationYaw;
    float origPitch = this.rotationPitch;
    boolean origGround = this.onGround;

    this.posX = preEvent.getX();
    this.getEntityBoundingBox().minY = preEvent.getY();
    this.posZ = preEvent.getZ();
    this.rotationYaw = preEvent.getYaw();
    this.rotationPitch = preEvent.getPitch();
    this.onGround = preEvent.isOnGround();

    // Minecraft's packet sending code...
    // ...

    // Restore original positions to prevent client desync
    this.posX = origX;
    this.getEntityBoundingBox().minY = origY;
    this.posZ = origZ;
    this.rotationYaw = origYaw;
    this.rotationPitch = origPitch;
    this.onGround = origGround;

    // 2. Publish Post Event
    Atlas.getInstance().getEventBus().publish(new MotionEvent(
        MotionEvent.Era.POST,
        this.posX, this.getEntityBoundingBox().minY, this.posZ,
        this.rotationYaw, this.rotationPitch,
        this.onGround
    ));
}
```

---

### Hook 6: Packets (`NetworkManager.java`)
**File:** `net.minecraft.network.NetworkManager.java`

**Incoming Packets (`channelRead0`):**
```java
protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet p_channelRead0_2_) throws Exception
{
    if (this.channel.isOpen())
    {
        PacketEvent.Receive event = new PacketEvent.Receive(p_channelRead0_2_);
        Atlas.getInstance().getEventBus().publish(event);
        if (event.isCancelled()) return;

        try
        {
            p_channelRead0_2_.processPacket(this.packetListener);
        }
        // ...
```

**Outgoing Packets (`sendPacket(Packet inPacket)`):**
```java
public void sendPacket(Packet inPacket)
{
    PacketEvent.Send event = new PacketEvent.Send(inPacket);
    Atlas.getInstance().getEventBus().publish(event);
    if (event.isCancelled()) return;

    // ... vanilla sendPacket handling ...
```

---

## 6. Subscribing to Events in Modules

### Annotation-Based Listening (`@Listen`)
Inside any module (e.g. `WatermarkModule`):

```java
package io.lunozol.atlas.system.module.modules.visual;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.event.events.Render2DEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import net.minecraft.client.Minecraft;

public class WatermarkModule extends Module {

    public WatermarkModule() {
        super("Watermark", "Displays client info on the HUD", ModuleCategory.VISUAL);
        setEnabled(true);
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        String text = Atlas.name + " (" + Atlas.version + ")";
        mc.fontRendererObj.drawStringWithShadow(text, 4, 4, Atlas.color.getRGB());
    }
}
```

### Programmatic / Lambda Listening
You can also subscribe without an object instance:

```java
Atlas.getInstance().getEventBus().subscribe(KeyEvent.class, event -> {
    if (event.getKey() == org.lwjgl.input.Keyboard.KEY_RSHIFT) {
        // Open ClickGUI
    }
});
```

---

## 7. Best Practices & Troubleshooting

| Issue / Symptom | Cause | Solution |
|---|---|---|
| Listener method is never called | Method is not `public`, is `static`, has wrong return type, or wrong parameter count | Ensure method signature is: `public void methodName(EventType event)` |
| Listener in base class isn't triggered | Radbus uses `getDeclaredMethods()`, ignoring un-overridden superclass methods | Declare the `@Listen` method directly inside the concrete Module class |
| Subclass / polymorphic event not firing | Radbus checks exact class via `event.getClass()` | Listen to the exact class published (e.g., `PacketEvent.Send`, not `PacketEvent`) |
| Crash / `ClassCastException` on subscription | Method parameter doesn't extend the `PubSub<Event>` bound | Ensure all events extend `io.lunozol.atlas.system.event.Event` |
| Concurrency / `ConcurrentModificationException` | Packets on Netty threads publishing while subscribing | Handle network events or synchronize if mutating subscribers concurrently |
