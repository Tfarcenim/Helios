package tfar.helios.mixin;

import net.minecraft.world.EnumSkyBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import tfar.helios.Helios;

@Mixin(EnumSkyBlock.class)
public interface EnumSkyBlockMixin {
    @Accessor(remap = Helios.REMAP) void setDefaultLightValue(int value);

}
