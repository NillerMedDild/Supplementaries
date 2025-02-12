package net.mehvahdjukaar.supplementaries.items.enchantment;

import net.mehvahdjukaar.supplementaries.configs.RegistryConfigs;
import net.mehvahdjukaar.supplementaries.items.SlingshotItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class StasisEnchantment extends Enchantment {

    private final boolean enabled;

    public StasisEnchantment(Rarity rarity, EnchantmentCategory type, EquipmentSlot... slotTypes) {
        super(rarity, type, slotTypes);
        enabled = RegistryConfigs.reg.SLINGSHOT_ENABLED.get();
    }

    @Override
    public int getMinCost(int level) {
        return 10 + level * 5;
    }

    @Override
    public int getMaxCost(int level) {
        return 40;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isTradeable() {
        return enabled;
    }

    @Override
    public boolean isDiscoverable() {
        return enabled;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return enabled;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && enchantment != Enchantments.MULTISHOT;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof SlingshotItem;
    }
}
