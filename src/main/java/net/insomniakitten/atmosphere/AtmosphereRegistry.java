package net.insomniakitten.atmosphere;

import net.insomniakitten.atmosphere.block.BlockHelpIHaveNothingForModOff;
import net.insomniakitten.atmosphere.client.model.IModelled;
import net.insomniakitten.atmosphere.item.IItemSupplier;
import net.insomniakitten.atmosphere.util.RegistryHolder;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Atmosphere.ID)
@GameRegistry.ObjectHolder(Atmosphere.ID)
public final class AtmosphereRegistry {

    private static final RegistryHolder<Block> BLOCKS = RegistryHolder.create();
    private static final RegistryHolder<Item> ITEMS = RegistryHolder.create();

    private AtmosphereRegistry() {}

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        Atmosphere.LOGGER.debug("Registering blocks and tile entities");
        BLOCKS.begin(event).register(new BlockHelpIHaveNothingForModOff()
                .setRegistryName("help_i_have_nothing_for_mod_off"));
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        Atmosphere.LOGGER.debug("Registering items");
        RegistryHolder<Item>.Registry items = ITEMS.begin(event);
        for (Block block : BLOCKS.entries()) {
            if (block instanceof IItemSupplier) {
                 items.register(((IItemSupplier) block).getItem());
            }
        }
    }

    @SubscribeEvent
    public static void onEntityRegistry(RegistryEvent.Register<EntityEntry> event) {
        Atmosphere.LOGGER.debug("Registering entities");
    }

    @SubscribeEvent
    public static void onBiomeRegistry(RegistryEvent.Register<Biome> event) {
        Atmosphere.LOGGER.debug("Registering dimension and biomes");
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        Atmosphere.LOGGER.debug("Registering models");
/*
       BlockStateFactory factory = BlockStateFactory.create(Atmosphere.ID);
        for (Block block : BLOCKS.entries()) {
            Object defaults = null;
            if (block instanceof IJsonDefaults) {
                defaults = ((IJsonDefaults) block).getJsonDefaults();
            }
            factory.generateFor(block, defaults);
        }
*/
        for (Item item : ITEMS.entries()) {
            if (item instanceof IModelled) {
                ModelResourceLocation mrl = ((IModelled) item).getModel();
                ModelLoader.setCustomModelResourceLocation(item, 0, mrl);
            }
        }
    }

}
