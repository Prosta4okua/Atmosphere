package net.insomniakitten.atmosphere.block;

import com.google.common.collect.ImmutableMap;
import net.insomniakitten.atmosphere.item.IItemSupplier;
import net.insomniakitten.atmosphere.item.ItemSolarPanel;
import net.insomniakitten.atmosphere.tile.TileSolarPanel;
import net.insomniakitten.atmosphere.util.dev.IJsonDefaults;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Locale;

public final class BlockSolarPanel extends BlockBase implements IItemSupplier, IJsonDefaults {

    public static final PropertyEnum<Half> HALF = PropertyEnum.create("half", Half.class);
    public static final PropertyEnum<Section> SECTION = PropertyEnum.create("section", Section.class);

    public BlockSolarPanel() {
        super(Material.IRON, MapColor.SNOW);
        setUnlocalizedName("solar_panel");
        setSoundType(SoundType.METAL);
        setHardness(5.0F);
        setResistance(30.0F);
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        Half half = Half.VALUES[meta & 1];
        Section section = Section.VALUES[meta >> 1];
        return getDefaultState()
                .withProperty(HALF, half)
                .withProperty(SECTION, section);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF, SECTION);
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state) {
        return isMaster(state) ? new TileSolarPanel() : null;
    }

    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(HALF) == Half.LOWER ? 255 : 0;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(SECTION, Section.NORTH);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int half = state.getValue(HALF).ordinal() & 1;
        int section = state.getValue(SECTION).ordinal() << 1;
        return half | section;
    }

    private boolean isMaster(IBlockState state) {
        boolean half = state.getValue(HALF) == Half.LOWER;
        boolean section = state.getValue(SECTION) == Section.NORTH;
        return half && section;
    }

    @Nullable
    private TileSolarPanel getMaster(IBlockState state, IBlockAccess world, BlockPos pos) {
        MutableBlockPos master = new MutableBlockPos(pos);
        state.getValue(HALF).setMasterY(master);
        state.getValue(SECTION).setMasterXZ(master);
        TileEntity tile = world.getTileEntity(pos);
        return tile instanceof TileSolarPanel ? (TileSolarPanel) tile : null;
    }

    @Override
    public Item getItem() {
        return new ItemSolarPanel(this).setRegistryName(getRegistryName());
    }

    @Override
    public Object getJsonDefaults() {
        return ImmutableMap.of("model", "atmoshphere:solar_panel");
    }

    public enum Half implements IStringSerializable {
        LOWER, UPPER;

        public static final Half[] VALUES = values();

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }

        private void setMasterY(MutableBlockPos pos) {
            pos.setY(pos.getY() - ordinal());
        }
    }

    public enum Section implements IStringSerializable {
        SOUTH(1, 0),
        SOUTH_WEST(1, 0),
        NORTH_WEST(0, 0),
        NORTH(0, 0),
        NORTH_EAST(0, 2),
        SOUTH_EAST(1, 2);

        public static final Section[] VALUES = values();

        private final int x, z;

        Section(int x, int z) {
            this.x = x;
            this.z = z;
        }

        private void setMasterXZ(MutableBlockPos pos) {
            pos.setPos(pos.getX() - x, pos.getY(), pos.getZ() - z);
        }

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

}
