package net.insomniakitten.atmosphere.block;

import net.insomniakitten.atmosphere.Atmosphere;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

abstract class BlockBase extends Block {

    private String name;

    protected BlockBase(Material material, MapColor mapColor) {
        super(material, mapColor);
        setCreativeTab(Atmosphere.TAB);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public String getUnlocalizedName() {
        if (name == null) {
            String s = super.getUnlocalizedName();
            String r = "block." + Atmosphere.ID + ".";
            name = s.replace("tile.", r);
        }
        return name;
    }

}
