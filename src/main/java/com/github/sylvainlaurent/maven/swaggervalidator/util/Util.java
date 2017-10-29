package com.github.sylvainlaurent.maven.swaggervalidator.util;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Util {

    public static <T> Set<T> findDuplicates(Collection<T> list) {
        Set<T> duplicates = new LinkedHashSet<>();
        Set<T> uniques = new HashSet<>();

        for (T elem : emptyIfNull(list)) {
            if (!uniques.add(elem)) {
                duplicates.add(elem);
            }
        }

        return duplicates;
    }
}
