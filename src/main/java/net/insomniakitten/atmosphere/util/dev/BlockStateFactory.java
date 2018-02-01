package net.insomniakitten.atmosphere.util.dev;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.insomniakitten.atmosphere.Atmosphere;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@SideOnly(Side.CLIENT)
public final class BlockStateFactory {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final String assets;
    private final String path;

    private BlockStateFactory(Minecraft mc, String modid) {
        String path = Paths.get(mc.mcDataDir.getAbsolutePath()).getParent().getParent().toString();
        this.assets = path + "/src/main/resources/assets/";
        this.path = modid + "/blockstates/";
    }

    public static BlockStateFactory create(String modid) {
        Minecraft mc = FMLClientHandler.instance().getClient();
        return new BlockStateFactory(mc, modid);
    }

    public void generateFor(Block block) {
        generateFor(block, null);
    }

    public void generateFor(Block block, @Nullable Object defaults) {
        if (!Atmosphere.DEOBF) return;

        Map<String, Object> json = new LinkedHashMap<>();
        Map<String, Object> variants = new TreeMap<>();

        String name = block.getRegistryName().getResourcePath();
        File file = new File(assets + path + name + ".json");

        file.getParentFile().mkdirs();
        if (file.exists()) return;

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        json.put("forge_marker", 1);

        if (defaults != null) {
            json.put("defaults", defaults);
        }

        for (IProperty property : block.getDefaultState().getPropertyKeys()) {
            String key = property.getName();
            Map<String, Object> values = new TreeMap<>();
            for (Object value : property.getAllowedValues()) {
                String v = property.getName((Comparable) value);
                values.put(v, new Object());
            }
            variants.put(key, values);
        }

        json.put("variants", variants);

        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
