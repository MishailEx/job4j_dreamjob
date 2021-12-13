package ru.job4j.dream.model;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Store {

    private static final Store INST = new Store();
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();


    private Store() {
        posts.put(1, new Post(1, "Junior Java Job", "easy", "12.02.2021"));
        posts.put(2, new Post(2, "Middle Java Job", "normal", "13.02.2021"));
        posts.put(3, new Post(3, "Senior Java Job", "hard", "14.02.2021"));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public void save(Post post) {
        post.setId(POST_ID.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public void save(Candidate candidate) {
        candidate.setId(POST_ID.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}