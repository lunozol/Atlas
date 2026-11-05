package io.lunozol.atlas.system.cache;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Cache<K, V> {
    private Map<K, V> map = new HashMap<>();
}
