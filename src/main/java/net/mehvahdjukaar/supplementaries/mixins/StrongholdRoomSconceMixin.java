package net.mehvahdjukaar.supplementaries.mixins;

import net.mehvahdjukaar.supplementaries.configs.RegistryConfigs;
import net.mehvahdjukaar.supplementaries.setup.ModRegistry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.StrongholdPieces;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(StrongholdPieces.RoomCrossing.class)
public abstract class StrongholdRoomSconceMixin extends StructurePiece {

    @Final
    @Shadow
    protected int type;

    private final BlockState sconce = ModRegistry.SCONCE_WALL.get().defaultBlockState();

    protected StrongholdRoomSconceMixin(StructurePieceType pType, int pGenDepth, BoundingBox pBoundingBox) {
        super(pType, pGenDepth, pBoundingBox);
    }

    @Inject(method = "postProcess", at = @At("TAIL"), cancellable = true)
    public void postProcess(WorldGenLevel reader, StructureFeatureManager manager, ChunkGenerator generator, Random random, BoundingBox bb, ChunkPos chunkPos, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (this.type == 0 && RegistryConfigs.reg.HAS_STRONGHOLD_SCONCE) {
            this.placeBlock(reader, sconce.setValue(WallTorchBlock.FACING, Direction.WEST), 4, 3, 5, bb);
            this.placeBlock(reader, sconce.setValue(WallTorchBlock.FACING, Direction.EAST), 6, 3, 5, bb);
            this.placeBlock(reader, sconce.setValue(WallTorchBlock.FACING, Direction.SOUTH), 5, 3, 4, bb);
            this.placeBlock(reader, sconce.setValue(WallTorchBlock.FACING, Direction.NORTH), 5, 3, 6, bb);
        }

    }
}