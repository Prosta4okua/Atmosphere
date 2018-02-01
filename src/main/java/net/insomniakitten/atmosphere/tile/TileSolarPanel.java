package net.insomniakitten.atmosphere.tile;

import net.insomniakitten.atmosphere.Atmosphere;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class TileSolarPanel extends TileEntity {

    public static final ResourceLocation ID = new ResourceLocation(Atmosphere.ID, "solar_panel_master");
    public static final AxisAlignedBB RENDER_AABB = new AxisAlignedBB(0.0D, -1.0D, -1.0D, 0.0D, 0.0D, 2.0D);

    private float angle;

    public TileSolarPanel() {}

    public float getAngle() {
        return angle;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        angle = compound.getFloat("angle");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setFloat("angle", angle);
        return compound;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

}
