package net.insomniakitten.atmosphere.block;

import net.insomniakitten.atmosphere.item.IItemSupplier;
import net.insomniakitten.atmosphere.item.ItemHelpIHaveNothingForModOff;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public final class BlockHelpIHaveNothingForModOff extends BlockBase implements IItemSupplier {

    public BlockHelpIHaveNothingForModOff() {
        super(Material.ROCK, MapColor.RED);
        setUnlocalizedName("help_i_have_nothing_for_mod_off");
        setHardness(5.0F);
        setResistance(30.0F);
    }

    @Override
    public Item getItem() {
        return new ItemHelpIHaveNothingForModOff(this)
                .setRegistryName(getRegistryName());
    }

}
