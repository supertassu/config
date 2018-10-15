/*
 * MIT License
 *
 * Copyright (c) 2018 Tassu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.tassu.cfg.impl;

import me.tassu.cfg.ConfigFactory;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;

import java.nio.file.Files;
import java.nio.file.Path;

import static me.tassu.cfg.ConfigUtil.run;

@SuppressWarnings("unused")
public class HoconConfigFactory extends ConfigFactory {

    public HoconConfigFactory(Path folder) {
        this(folder, HoconConfigurationLoader.builder().getDefaultOptions().getSerializers());
    }

    @SuppressWarnings("WeakerAccess")
    public HoconConfigFactory(Path folder, TypeSerializerCollection serializers) {
        super(folder, serializers);
    }

    @Override
    protected String getFileExtension() {
        return "conf";
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
        HoconConfigurationLoader.Builder builder = HoconConfigurationLoader.builder();

        return builder
                .setPath(folder.resolve(formatFileName(file)))
                .setDefaultOptions(builder.getDefaultOptions().setSerializers(serializers))
                .build();
    }
}
