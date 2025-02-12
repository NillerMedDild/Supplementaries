package net.mehvahdjukaar.supplementaries.compat.farmersdelight;

import net.mehvahdjukaar.supplementaries.Supplementaries;
import net.mehvahdjukaar.supplementaries.compat.CompatHandler;
import net.mehvahdjukaar.supplementaries.compat.CompatObjects;
import net.mehvahdjukaar.supplementaries.setup.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class FDCompatRegistry {

    public static final String PLANTER_RICH_NAME = "planter_rich";
    @ObjectHolder(Supplementaries.MOD_ID + ":" + PLANTER_RICH_NAME)
    public static final Block PLANTER_RICH = null;

    public static final String PLANTER_RICH_SOUL_NAME = "planter_rich_soul";
    @ObjectHolder(Supplementaries.MOD_ID + ":" + PLANTER_RICH_SOUL_NAME)
    public static final Block PLANTER_RICH_SOUL = null;

    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> reg = event.getRegistry();
        reg.register(new PlanterRichBlock(BlockBehaviour.Properties.copy(ModRegistry.PLANTER.get()).randomTicks(),
                CompatObjects.RICH_SOIL)
                .setRegistryName(PLANTER_RICH_NAME));

        if (CompatHandler.nethersdelight) {
            reg.register(new PlanterRichBlock(BlockBehaviour.Properties.copy(ModRegistry.PLANTER.get()).randomTicks(),
                    CompatObjects.RICH_SOUL_SOIL)
                    .setRegistryName(PLANTER_RICH_SOUL_NAME));
        }

    }


    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> reg = event.getRegistry();
        reg.register(new BlockItem(PLANTER_RICH,
                new Item.Properties().tab(ModRegistry.getTab(CreativeModeTab.TAB_DECORATIONS, PLANTER_RICH_NAME))
        ).setRegistryName(PLANTER_RICH_NAME));

        if (CompatHandler.nethersdelight) {
            reg.register(new BlockItem(PLANTER_RICH_SOUL,
                    new Item.Properties().tab(ModRegistry.getTab(CreativeModeTab.TAB_DECORATIONS, PLANTER_RICH_SOUL_NAME))
            ).setRegistryName(PLANTER_RICH_SOUL_NAME));
        }

    }

    public static InteractionResult onCakeInteraction(BlockState state, BlockPos pos, Level world, ItemStack stack) {
        /*
        if (ModTags.KNIVES.contains(stack.getItem())) {
            int bites = state.getValue(CakeBlock.BITES);
            if (bites < 6) {
                world.setBlock(pos, state.setValue(CakeBlock.BITES, bites + 1), 3);
            } else {
            if(block==ModRegistry.DOuble_Ca)
                world.removeBlock(pos, false);
            }

            //Block.popResource();
            Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.CAKE_SLICE.get()));
            world.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }*/
        return InteractionResult.PASS;

    }


}
