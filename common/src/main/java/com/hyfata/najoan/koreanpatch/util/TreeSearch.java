package com.hyfata.najoan.koreanpatch.util;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public final class TreeSearch {
    private TreeSearch() {
    }

    public static <T> boolean containsBfs(
            T root,
            Predicate<? super T> matcher,
            Function<? super T, ? extends Iterable<? extends T>> children) {
        if (root == null || matcher == null || children == null) {
            return false;
        }
        Set<T> visited = Collections.newSetFromMap(new IdentityHashMap<>());
        Deque<T> queue = new ArrayDeque<>();
        queue.add(root);
        visited.add(root);
        while (!queue.isEmpty()) {
            T current = queue.removeFirst();
            if (matcher.test(current)) {
                return true;
            }
            Iterable<? extends T> kids = children.apply(current);
            if (kids != null) {
                for (T kid : kids) {
                    if (kid != null && visited.add(kid)) {
                        queue.add(kid);
                    }
                }
            }
        }
        return false;
    }
}
