package net.mehvahdjukaar.supplementaries.setup;

import net.mehvahdjukaar.supplementaries.block.blocks.*;
import net.mehvahdjukaar.supplementaries.configs.RegistryConfigs;
import net.mehvahdjukaar.supplementaries.datagen.types.IWoodType;
import net.mehvahdjukaar.supplementaries.datagen.types.WoodTypes;
import net.mehvahdjukaar.supplementaries.items.BurnableBlockItem;
import net.mehvahdjukaar.supplementaries.items.FlagItem;
import net.mehvahdjukaar.supplementaries.items.SignPostItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fmllegacy.RegistryObject;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("ConstantConditions")
public class Variants {

    public static boolean doesntHaveWoodInstalled(IWoodType wood) {
        return !ModList.get().isLoaded(wood.getNamespace());
    }

    //hanging signs
    public static Map<IWoodType, RegistryObject<Block>> makeHangingSingsBlocks() {
        Map<IWoodType, RegistryObject<Block>> map = new HashMap<>();

        for (IWoodType wood : WoodTypes.TYPES.values()) {
            if(RegistryConfigs.reg.CONDITIONAL_SIGN_REGISTRATIONS.get() && !wood.isModActive()) continue;
            String name = getHangingSignName(wood);
            map.put(wood, ModRegistry.BLOCKS.register(name, () -> new HangingSignBlock(
                    BlockBehaviour.Properties.of(wood.getMaterial(), wood.getColor())
                            .strength(2f, 3f)
                            .sound(SoundType.WOOD)
                            .noOcclusion()
                            .noCollission()
            )));
        }
        return map;
    }

    public static Map<IWoodType, RegistryObject<Item>> makeHangingSignsItems() {
        Map<IWoodType, RegistryObject<Item>> map = new HashMap<>();

        for (IWoodType wood : WoodTypes.TYPES.values()) {
            if(RegistryConfigs.reg.CONDITIONAL_SIGN_REGISTRATIONS.get() && !wood.isModActive()) continue;
            String name = getHangingSignName(wood);
            map.put(wood, ModRegistry.ITEMS.register(name, () -> new BurnableBlockItem(ModRegistry.HANGING_SIGNS.get(wood).get(),
                    new Item.Properties().tab(doesntHaveWoodInstalled(wood) ? null :
                            ModRegistry.getTab(CreativeModeTab.TAB_DECORATIONS, ModRegistry.HANGING_SIGN_NAME)), 200
            )));
        }
        return map;
    }

    public static String getHangingSignName(IWoodType type) {
        return ModRegistry.HANGING_SIGN_NAME + "_" + type.getRegName();
    }

    //sign posts
    public static Map<IWoodType, RegistryObject<Item>> makeSignPostItems() {
        Map<IWoodType, RegistryObject<Item>> map = new HashMap<>();

        for (IWoodType wood : WoodTypes.TYPES.values()) {
            if(RegistryConfigs.reg.CONDITIONAL_SIGN_REGISTRATIONS.get() && !wood.isModActive()) continue;
            String name = getSignPostName(wood);
            map.put(wood, ModRegistry.ITEMS.register(name, () -> new SignPostItem(
                    new Item.Properties().tab(doesntHaveWoodInstalled(wood) ? null :
                            ModRegistry.getTab(CreativeModeTab.TAB_DECORATIONS, ModRegistry.SIGN_POST_NAME)), wood
            )));
        }
        return map;
    }

    public static String getSignPostName(IWoodType type) {
        return ModRegistry.SIGN_POST_NAME + "_" + type.getRegName();
    }

    //flags
    public static Map<DyeColor, RegistryObject<Block>> makeFlagBlocks(String baseName) {
        Map<DyeColor, RegistryObject<Block>> map = new HashMap<>();

        for (DyeColor color : DyeColor.values()) {
            String name = baseName + "_" + color.getName();
            map.put(color, ModRegistry.BLOCKS.register(name, () -> new FlagBlock(color,
                    BlockBehaviour.Properties.of(Material.WOOD, color.getMaterialColor())
                            .strength(1.0F)
                            .noOcclusion()
                            .sound(SoundType.WOOD))
            ));
        }
        return map;
    }


    public static Map<DyeColor, RegistryObject<Item>> makeFlagItems(String baseName) {
        Map<DyeColor, RegistryObject<Item>> map = new HashMap<>();

        for (DyeColor color : DyeColor.values()) {
            String name = baseName + "_" + color.getName();
            map.put(color, ModRegistry.ITEMS.register(name, () -> new FlagItem(ModRegistry.FLAGS.get(color).get(),
                    new Item.Properties()
                            .stacksTo(16)
                            .tab(ModRegistry.getTab(CreativeModeTab.TAB_DECORATIONS, ModRegistry.FLAG_NAME))
            )));
        }
        return map;
    }

    //ceiling banners
    public static Map<DyeColor, RegistryObject<Block>> makeCeilingBanners(String baseName) {
        Map<DyeColor, RegistryObject<Block>> map = new HashMap<>();

        for (DyeColor color : DyeColor.values()) {
            String name = baseName + "_" + color.getName();
            map.put(color, ModRegistry.BLOCKS.register(name, () -> new CeilingBannerBlock(color,
                            BlockBehaviour.Properties.of(Material.WOOD, color.getMaterialColor())
                                    .strength(1.0F)
                                    .noCollission()
                                    .sound(SoundType.WOOD)
                                    .lootFrom(() -> BannerBlock.byColor(color))
                    )
            ));
        }
        return map;
    }


    //ceiling banners
    public static Map<DyeColor, RegistryObject<Item>> makeCeilingBannersItems() {
        Map<DyeColor, RegistryObject<Item>> map = new HashMap<>();

        for (DyeColor color : DyeColor.values()) {
            map.put(color, ModRegistry.regBlockItem(ModRegistry.CEILING_BANNERS.get(color), null));
        }
        return map;
    }

    //presents
    public static Map<DyeColor, RegistryObject<Block>> makePresents(String baseName) {
        Map<DyeColor, RegistryObject<Block>> map = new HashMap<>();

        for (DyeColor color : DyeColor.values()) {
            String name = baseName + "_" + color.getName();
            map.put(color, ModRegistry.BLOCKS.register(name, () -> new PresentBlock(color,
                    BlockBehaviour.Properties.of(Material.WOOL, color.getMaterialColor())
                            .strength(1.0F)
                            .sound(SoundType.WOOL))
            ));
        }
        map.put(null, ModRegistry.BLOCKS.register(baseName, () -> new PresentBlock(null,
                BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.WOOD)
                        .strength(1.0F)
                        .sound(SoundType.WOOL))
        ));
        return map;
    }


    //presents
    public static Map<DyeColor, RegistryObject<Item>> makePresentsItems() {
        Map<DyeColor, RegistryObject<Item>> map = new HashMap<>();

        /*
        for(DyeColor color : DyeColor.values()){
            //ModRegistry.getTab(ItemGroup.TAB_DECORATIONS, ModRegistry.PRESENT_NAME)
            map.put(color, ModRegistry.regBlockItem(ModRegistry.PRESENTS.get(color), ModRegistry.getTab(ItemGroup.TAB_DECORATIONS, ModRegistry.PRESENT_NAME)));
        }
        */
        map.put(null, ModRegistry.regBlockItem(ModRegistry.PRESENTS.get(null), null));

        return map;
    }

    public enum VariantType {
        BLOCK(Block::new),
        SLAB(SlabBlock::new),
        VERTICAL_SLAB(VerticalSlabBlock::new),
        WALL(WallBlock::new),
        STAIRS(StairBlock::new);
        private final BiFunction<Supplier<BlockState>,BlockBehaviour.Properties,Block> constructor;

        VariantType(BiFunction<Supplier<BlockState>,BlockBehaviour.Properties, Block> constructor) {
            this.constructor = constructor;
        }
        VariantType(Function<BlockBehaviour.Properties,Block> constructor){
            this.constructor = (b,p)->constructor.apply(p);
        }
        private Block create(Block parent){
            return this.constructor.apply(parent::defaultBlockState,  BlockBehaviour.Properties.copy(parent));
        }
    }

    public static EnumMap<VariantType, RegistryObject<Block>> registerFullBlockSet(String baseName, Block parentBlock) {
        EnumMap<VariantType, RegistryObject<Block>> map = new EnumMap<>(VariantType.class);
        for (VariantType type : VariantType.values()) {
            String name = baseName;
            if (!type.equals(VariantType.BLOCK)) name += "_" + type.name().toLowerCase();
            RegistryObject<Block> block = ModRegistry.BLOCKS.register(name, ()->type.create(parentBlock));
            CreativeModeTab tab = switch (type){
                case VERTICAL_SLAB -> ModRegistry.getTab("quark",CreativeModeTab.TAB_BUILDING_BLOCKS, baseName);
                case WALL -> ModRegistry.getTab(CreativeModeTab.TAB_DECORATIONS, baseName);
                default -> ModRegistry.getTab(CreativeModeTab.TAB_BUILDING_BLOCKS, baseName);
            };
            ModRegistry.regBlockItem(block, tab);
            map.put(type, block);
        }
        return map;
    }


}