package io.lunozol.atlas.system.cache.caches;

import io.lunozol.atlas.system.cache.Cache;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

// for TargetInfoWidget when I care enough to make one
@AllArgsConstructor
@Getter
@Setter
public class EntityCache extends Cache {
    private Entity entity;
    private String displayName;
    private String name;
    private double distFromPlayer;
    private double health;
    private double maxHealth;
    private int hurtTime;
    private int maxHurtTime;
    private ResourceLocation locationSkin;
}
