package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Store {
    Collection<Post> findAllPosts();
    Collection<Candidate> findAllCandidates();
    void save(Post post);
    void save(Candidate candidate);
    Post findById(int id);
    Candidate findByIdCon(int id);
    void delCon(int id);
    User findByEmail(String email);
    User addUser(User user);
    Map<Integer, String> allCity();
    List<Post> postForDay();
    List<Candidate> candidateForDay();
}