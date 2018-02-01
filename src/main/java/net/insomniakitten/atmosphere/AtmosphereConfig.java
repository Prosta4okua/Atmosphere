package net.insomniakitten.atmosphere;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Atmosphere.ID, name = Atmosphere.ID)
@Mod.EventBusSubscriber(modid = Atmosphere.ID)
public final class AtmosphereConfig {

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (Atmosphere.ID.equals(event.getModID())) {
            Atmosphere.LOGGER.debug("Reloading configurations");
            ConfigManager.sync(Atmosphere.ID, Config.Type.INSTANCE);
        }
    }

}
