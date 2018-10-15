package me.tassu.cfg.impl;

import me.tassu.cfg.ConfigFactory;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

import java.nio.file.Files;
import java.nio.file.Path;

import static me.tassu.cfg.ConfigUtil.run;

@SuppressWarnings("unused")
public class YamlConfigFactory extends ConfigFactory {

    public YamlConfigFactory(Path folder) {
        this(folder, YAMLConfigurationLoader.builder().getDefaultOptions().getSerializers());
    }

    @SuppressWarnings("WeakerAccess")
    public YamlConfigFactory(Path folder, TypeSerializerCollection serializers) {
        super(folder, serializers);
    }

    @Override
    protected String getFileExtension() {
        return "yml";
    }

    @Override
    public ConfigurationLoader<? extends ConfigurationNode> getLoader(Path folder, String file) {
        // create the folder
        if (Files.notExists(folder.getParent())) {
            run(() -> Files.createDirectory(folder.getParent()));
        }

        if (Files.notExists(folder)) {
            run(() -> Files.createDirectory(folder));
        }

        // create the loader
        YAMLConfigurationLoader.Builder builder = YAMLConfigurationLoader.builder();

        return builder
                .setPath(folder.resolve(formatFileName(file)))
                .setDefaultOptions(builder.getDefaultOptions().setSerializers(serializers))
                .build();
    }

}
