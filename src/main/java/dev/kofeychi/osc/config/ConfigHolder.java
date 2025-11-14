package dev.kofeychi.osc.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ConfigHolder<T> {
    protected Class<? extends T> clazz;
    protected Path file;
    protected T instance;
    protected ConfigSerializer<T> serializer;

    public ConfigHolder(Class<? extends T> clazz,Path file, ConfigSerializerBuilder<T> serializer) {
        this.clazz = clazz;
        this.file = file;
        this.serializer = serializer.build(clazz);
    }
    public ConfigHolder(Class<? extends T> clazz,Path file, ConfigSerializer<T> serializer) {
        this.clazz = clazz;
        this.file = file;
        this.serializer = serializer;
    }

    public T instance() {
        return instance;
    }
    public T defaults() {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void applyChanges() {
        save();
        load();
    }
    public void save(){
        try {
            if(!Files.exists(file)) {
                file.toFile().getParentFile().mkdirs();
                Files.writeString(file, serializer.serialize(defaults()), StandardOpenOption.CREATE,StandardOpenOption.WRITE);
            } else {
                Files.writeString(file, serializer.serialize(instance), StandardOpenOption.CREATE,StandardOpenOption.WRITE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(){
        try {
            if(!Files.exists(file)) {
                file.toFile().getParentFile().mkdirs();
                Files.writeString(file, serializer.serialize(defaults()), StandardOpenOption.CREATE,StandardOpenOption.WRITE);
                instance = defaults();
                return;
            } else {
                instance = serializer.deserialize(Files.readString(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public interface ConfigSerializerBuilder<T> {
        ConfigSerializer<T> build(Class<? extends T> clazz);
    }
}
