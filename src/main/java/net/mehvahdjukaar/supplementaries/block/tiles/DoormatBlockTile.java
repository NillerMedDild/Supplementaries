package net.mehvahdjukaar.supplementaries.block.tiles;

import net.mehvahdjukaar.selene.blocks.ItemDisplayTile;
import net.mehvahdjukaar.supplementaries.block.blocks.DoormatBlock;
import net.mehvahdjukaar.supplementaries.block.util.ITextHolderProvider;
import net.mehvahdjukaar.supplementaries.block.util.TextHolder;
import net.mehvahdjukaar.supplementaries.setup.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class DoormatBlockTile extends ItemDisplayTile implements ITextHolderProvider {
    public static final int MAX_LINES = 3;

    public final TextHolder textHolder;

    public DoormatBlockTile(BlockPos pos, BlockState state) {
        super(ModRegistry.DOORMAT_TILE.get(),pos, state);
        this.textHolder = new TextHolder(MAX_LINES);
    }

    @Override
    public TextHolder getTextHolder(){return this.textHolder;}

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.textHolder.read(compound);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        this.textHolder.write(compound);
        return compound;
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.supplementaries.doormat");
    }

    public Direction getDirection(){
        return this.getBlockState().getValue(DoormatBlock.FACING);
    }

    //TODO: optimize this two methods to send only what's needed
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

}