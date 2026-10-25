package io.lunozol.atlas.utils.game;

import io.lunozol.atlas.Constants;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class InventoryUtil implements Constants {

    public static float getSwordDamage(ItemStack itemStack) {
        ItemSword sword = (ItemSword) itemStack.getItem();
        float points = 0;
        points += sword.getDamageVsEntity();

        return points;
    }
}
