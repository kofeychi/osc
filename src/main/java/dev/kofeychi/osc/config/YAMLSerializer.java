package dev.kofeychi.osc.config;

import tools.jackson.core.TokenStreamFactory;
import tools.jackson.dataformat.yaml.YAMLFactory;

public class YAMLSerializer<T> extends JacksonSerializer<T> {
    public YAMLSerializer(Class<? extends T> clazz) {
        super(clazz);
    }

    @Override
    public TokenStreamFactory getFactory() {
        return new YAMLFactory();
    }
}
