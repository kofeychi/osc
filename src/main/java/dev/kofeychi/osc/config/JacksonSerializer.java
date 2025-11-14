package dev.kofeychi.osc.config;

import tools.jackson.core.TokenStreamFactory;
import tools.jackson.databind.ObjectMapper;

public abstract class JacksonSerializer<T> extends ConfigSerializer<T> {
    public JacksonSerializer(Class<? extends T> clazz) {
        super(clazz);
    }

    @Override
    public String serialize(T instance) {
        ObjectMapper objectMapper = new ObjectMapper(getFactory());
        return objectMapper.writeValueAsString(instance);
    }

    @Override
    public T deserialize(String serialized) {
        ObjectMapper objectMapper = new ObjectMapper(getFactory());
        return objectMapper.readValue(serialized, clazz);
    }

    public abstract TokenStreamFactory getFactory();
}
