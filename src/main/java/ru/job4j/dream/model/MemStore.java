package ru.job4j.dream.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore {

    private static final MemStore INST = new MemStore();
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();


    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job", LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job", LocalDateTime.now()));
        candidates.put(1, new Candidate(1, "Junior"));
        candidates.put(2, new Candidate(2, "Middle"));
        candidates.put(3, new Candidate(3, "Senior"));
    }

    public void saveCon(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public Candidate condFindById(int id) {
        return candidates.get(id);
    }

    public void saveCon(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(POST_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    public void delCon(int id) {
        if (candidates.containsKey(id)) {
            candidates.remove(id);
        }
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}