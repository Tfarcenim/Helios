package tfar.helios.mixin;

import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tfar.helios.Helios;

@Mixin(Chunk.class)
public class ChunkMixin {

    @ModifyVariable(method = "getLightSubtracted",
            at = @At(value = "INVOKE",target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLight(III)I",remap = Helios.REMAP)
            ,print = true,remap = Helios.REMAP,ordinal = 4)
    private int modifyLight(int original) {
        return Math.max(0,original - 8);//cap sky light at 7
    }
}
