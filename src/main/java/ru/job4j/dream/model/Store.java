package ru.job4j.dream.model;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {

    private static final Store INST = new Store();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job", "easy", "12.02.2021"));
        posts.put(2, new Post(2, "Middle Java Job", "normal", "13.02.2021"));
        posts.put(3, new Post(3, "Senior Java Job", "hard", "14.02.2021"));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}