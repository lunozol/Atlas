package io.lunozol.atlas.system.cache.caches;

import io.lunozol.atlas.system.cache.Cache;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;

@AllArgsConstructor
@Getter
@Setter
public class ImageCache extends Cache {
    private ResourceLocation resourceLocation;
    private BufferedImage bufferedImage;
}
