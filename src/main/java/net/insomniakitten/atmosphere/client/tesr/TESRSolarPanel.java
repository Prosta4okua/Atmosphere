package net.insomniakitten.atmosphere.client.tesr;

import net.insomniakitten.atmosphere.Atmosphere;
import net.insomniakitten.atmosphere.tile.TileSolarPanel;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(modid = Atmosphere.ID, value = Side.CLIENT)
public final class TESRSolarPanel extends TileEntitySpecialRenderer<TileSolarPanel> {

    private static final ModelResourceLocation MRL = new ModelResourceLocation(new ResourceLocation(Atmosphere.ID, "solar_panel"), "panel");

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        Atmosphere.LOGGER.debug("Registering solar panel renderer");
        ClientRegistry.bindTileEntitySpecialRenderer(TileSolarPanel.class, new TESRSolarPanel());
    }

    @SubscribeEvent
    public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
        Atmosphere.LOGGER.debug("Stitching solar panel renderer textures");
        for (ResourceLocation texture : getModelFor(MRL).getTextures()) {
            event.getMap().registerSprite(texture);
        }

    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        Atmosphere.LOGGER.debug("Baking solar panel renderer model");
        IModel model = getModelFor(MRL);
        event.getModelRegistry().putObject(MRL, model.bake(
                model.getDefaultState(),
                DefaultVertexFormats.BLOCK,
                ModelLoader.defaultTextureGetter()
        ));
    }

    private static IModel getModelFor(ModelResourceLocation MRL) {
        String error = "Failed to get model for <" + MRL.toString() + ">!";
        return ModelLoaderRegistry.getModelOrLogError(MRL, error);
    }

    @Override
    public void render(TileSolarPanel tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        GlStateManager.pushMatrix();

        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        RenderHelper.disableStandardItemLighting();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableBlend();

        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else GlStateManager.shadeModel(GL11.GL_FLAT);

        GlStateManager.translate(x + 0.5, y + 1.5F, z + 1.0F);
        GlStateManager.rotate(360.0F * getWorld().getCelestialAngle(partialTicks), 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-0.5F, -1.5F, -1.0F);

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        IBakedModel model = retrieveModel(new ResourceLocation(Atmosphere.ID, "solar_panel"), "panel");
        getRenderer().renderModel(getWorld(), model, tile.getBlockType().getDefaultState(), BlockPos.ORIGIN, buffer, true);

        buffer.setTranslation(0, 0, 0);
        tessellator.draw();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    protected BlockModelRenderer getRenderer() {
        return FMLClientHandler.instance().getClient()
                .getBlockRendererDispatcher()
                .getBlockModelRenderer();
    }

    protected IBakedModel retrieveModel(ResourceLocation model, String variant) {
        return FMLClientHandler.instance().getClient()
                .getBlockRendererDispatcher()
                .getBlockModelShapes()
                .getModelManager()
                .getModel(new ModelResourceLocation(model, variant));
    }

}
