package net.mehvahdjukaar.supplementaries.mixins;

import net.mehvahdjukaar.supplementaries.common.ModTags;
import net.mehvahdjukaar.supplementaries.world.songs.SongsManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({NoteBlock.class})
public abstract class NoteBlockMixin {


    @Inject(method = "playNote", at = @At("HEAD"))
    private void playNote(Level pLevel, BlockPos pPos, CallbackInfo ci) {
        if (pLevel.getBlockState(pPos.above()).isAir()) {
            SongsManager.recordNote(pLevel, pPos);
        }

    }
}
