/*
 * The MIT License.
 *
 * Copyright (c) tassu <hello@tassu.me>
 * Copyright (c) contributors
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

package me.tassu.cfg;

import com.google.common.base.Preconditions;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.SimpleCommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;

/**
 * Class handling configuration logic.
 * @param <T> Implementing class
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractConfig<T extends AbstractConfig> {

    protected ObjectMapper<T>.BoundInstance configMapper;
    protected ConfigurationLoader<? extends ConfigurationNode> loader;

    private void checkPreconditions() {
        Preconditions.checkNotNull(loader, "Please create a ConfigurationLoader before calling #load() or #save(). " +
                "A great way to create a ConfigurationLoader is using a ConfigFactory.");
        Preconditions.checkNotNull(configMapper, "Please call \"this.configMapper = ObjectMapper.forObject(this);\" " +
                "before calling #load() or #save().");
    }

    public void load() throws IOException, ObjectMappingException {
        this.checkPreconditions();
        this.configMapper.populate(this.loader.load());
    }

    public void save() throws ObjectMappingException, IOException {
        this.checkPreconditions();

        SimpleCommentedConfigurationNode out = SimpleCommentedConfigurationNode.root();
        this.configMapper.serialize(out);
        this.loader.save(out);
    }

}
