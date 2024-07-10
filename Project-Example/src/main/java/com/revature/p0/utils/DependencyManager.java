package com.revature.p0.utils;

import java.util.HashMap;
import java.util.Map;

public class DependencyManager {
    private static Map<String, Object> dependencyMap = new HashMap<>();

    public static void addDependency(String name, Object obj) {
        dependencyMap.put(name, obj);
    }

    public static Object getDependency(String name) {
        return dependencyMap.get(name);
    }

}
