package dev.kofeychi.osc;

import dev.kofeychi.osc.config.ConfigHolder;
import dev.kofeychi.osc.config.YAMLSerializer;
import net.fabricmc.loader.api.FabricLoader;

public class OscConfig {
    public static final ConfigHolder<OscConfig> HOLDER = new ConfigHolder<>(
            OscConfig.class,
            FabricLoader.getInstance().getConfigDir().resolve("osc.yaml"),
            YAMLSerializer::new
    );

    public Nuke nuke = new Nuke();
    public Stab stab = new Stab();

    public static class Nuke {
        public int RINGS = 12;
        public int RING_STEP = 4;
        public int TNT_PER_RING = 6;
        public int TNT_PER_RING_ADDED = 6;
    }

    public static class Stab {
        public int DEPTH = 8;
        public int SPACING = 4;
    }
}
