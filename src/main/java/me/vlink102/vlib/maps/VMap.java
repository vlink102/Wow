package me.vlink102.vlib.maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VMap<K, V> extends HashMap<K, V> {
    public VMap(Map<K, V> inverted) {
        super(inverted);
    }

    public List<K> getKeys() {
        return new ArrayList<>(keySet());
    }

    public List<V> getValues() {
        return new ArrayList<>(values());
    }

    public VMap<V, K> invert() {
        Map<V, K> inverted = entrySet().stream().collect(Collectors.toMap(Entry::getValue, Entry::getKey));
        return new VMap<>(inverted);
    }

    public VMap<K, V> invertOrder() {
        Map<K, V> reordered = new HashMap<>(this);
        // todo
        return null;
    }
}