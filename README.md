# Config `1.0.01`

A java configuration utility

## Getting started
### Add the dependency

```groovy
repositories {
    mavenCentral()
    maven {
        name = 'sponge-repo'
        url = 'https://repo.spongepowered.org/maven'
    }
    maven {
        name = 'tassu-repo'
        url = 'https://maven.tassu.me/'
    }
}

dependencies {
    // choose one:
    compile 'me.tassu.cfg:hocon:(version)'
    compile 'me.tassu.cfg:yaml:(version)'
}
```

No example for Maven because Maven is bad.

### Create the ConfigFactory
```java
import me.tassu.cfg.ConfigFactory;
import me.tassu.cfg.impl.HoconConfigFactory;

void loadConfig() {
    final Path path = /* ... */;
    final ConfigFactory factory = new HoconConfigFactory(path);    
}
```

### Create a config class
```java
package me.tassu.publicplugin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import me.tassu.cfg.AbstractConfig;
import me.tassu.cfg.ConfigFactory;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.Setting;

import java.util.List;
import java.util.Map;

@SuppressWarnings("FieldCanBeLocal")
public class ExampleConfig extends AbstractConfig<ExampleConfig> {

    public ExampleConfig(ConfigFactory factory) {
        // get configuration loader for "example.conf"
        loader = factory.getLoader("example");

        try {
            // logic handled by AbstractConfig
            this.configMapper = ObjectMapper.forObject(this);
        } catch (ObjectMappingException e) {
            throw new RuntimeException(e);
        }
    }

    @Setting
    private int exampleInt = 0;

    @Setting
    private String exampleString = "Hello!";

    @Setting(comment = "Example comment")
    private Map<String, String> exampleMap = ImmutableMap.<String, String>builder()
            .put("first key", "first value")
            .put("second key", "second value")
            .build();

    @Setting
    private List<Float> exampleList = Lists.newArrayList(1.5f);

    public int getExampleInt() {
        return exampleInt;
    }

    public String getExampleString() {
        return exampleString;
    }

    public Map<String, String> getExampleMap() {
        return exampleMap;
    }

    public List<Float> getExampleList() {
        return exampleList;
    }
}
```

### Load and use the configuration

```java
import me.tassu.cfg.ConfigFactory;
import me.tassu.cfg.impl.HoconConfigFactory;

void loadConfig() {
    final Path path = /* ... */;
    final ConfigFactory factory = new HoconConfigFactory(path);
    final ExampleConfig config = new ExampleConfig(factory);

    try {
        config.load();
        config.save();
    } catch (IOException | ObjectMappingException e) {
        throw new RuntimeException(e);
    }
    
    System.out.println("Example int: " + config.getExampleInt());
    System.out.println("Example string: " + config.getExampleString());
    System.out.println("Example map: " + config.getExampleMap());
    System.out.println("Example list: " + config.getExampleList());
}
```

### Result
```hocon
exampleInt=0
exampleList=[
    1.5
]
# Example comment
exampleMap {
    "first key"="first value"
    "second key"="second value"
}
exampleString="Hello!"
```

# License
- MIT