package io.lunozol.atlas.system.features.module.modules.player;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.game.TickEvent;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import io.lunozol.atlas.system.features.module.property.properties.SliderProperty;
import io.lunozol.atlas.utils.game.ChatUtil;
import io.lunozol.atlas.utils.game.InventoryUtil;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ChestStealerModule extends Module {
    public static final SliderProperty speed = new SliderProperty("Speed", "Speed at which the chest stealer steals per slot", 50, 0, 200, 1);
    private long lastTaken;

    public ChestStealerModule() {
        super("ChestStealer", "Steals from chests!!!", ModuleCategory.PLAYER);
        registerSettings(speed);
    }

    @Listen
    public void onTick(TickEvent event) {
        if (mc.currentScreen instanceof GuiChest) {
            GuiChest gui = ((GuiChest) mc.currentScreen);

            for (Slot slot : gui.inventorySlots.inventorySlots) {
                if (slot.getStack() != null) {
                    if (slot.getStack().getItem() instanceof ItemSword) {
                        float origPoints = InventoryUtil.getSwordDamage(slot.getStack());

                        for (int i = 0; i < 9; i++) {
                            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);

                            if (stack != null) {
                                if (stack.getItem() instanceof ItemSword) {
                                    float compPoints = InventoryUtil.getSwordDamage(stack);

                                    if (compPoints < origPoints) {
                                        ChatUtil.send("Better sword at slot: " + slot.slotNumber);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
