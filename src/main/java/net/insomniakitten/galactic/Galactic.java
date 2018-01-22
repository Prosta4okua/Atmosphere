package net.insomniakitten.galactic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Galactic.ID, name = Galactic.NAME, version = Galactic.VERSION)
public final class Galactic {

    public static final String ID = "galactic";
    public static final String NAME = "Galactic";
    public static final String VERSION = "%VERSION%";

    public static final CreativeTabs TAB = new CreativeTabs(ID) {
        @Override
        @SideOnly(Side.CLIENT)
        public String getTranslatedTabLabel() {
            return "tab." + ID + ".label";
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return ItemStack.EMPTY;
        }
    };

}
