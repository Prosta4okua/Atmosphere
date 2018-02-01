package net.insomniakitten.atmosphere.item;

import net.insomniakitten.atmosphere.client.model.IModelled;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;

public final class ItemHelpIHaveNothingForModOff extends ItemBlock implements IModelled {

    public ItemHelpIHaveNothingForModOff(Block block) {
        super(block);
    }

    @Override
    public ModelResourceLocation getModel() {
        return new ModelResourceLocation(getRegistryName(), "normal");
    }

}
