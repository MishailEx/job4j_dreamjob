package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.User;

public class DbStore implements Store {
    private static final DbStore INSTANCE = new DbStore();
    private static final Logger LOG = LoggerFactory.getLogger(DbStore.class.getName());


    private final BasicDataSource pool = new BasicDataSource();

    private DbStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        DbStore.class.getClassLoader()
                                .getResourceAsStream("db.properties")
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new DbStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"),
                            it.getString("name"),
                            it.getTimestamp("created").toLocalDateTime()));
                }
            }
        } catch (Exception e) {
            LOG.error("Error connect to table 'post'", e);
        }
        return posts;
    }

    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"),
                            it.getString("name"),
                            it.getInt("city_id"),
                            it.getTimestamp("created").toLocalDateTime()));
                }
            }
        } catch (Exception e) {
            LOG.error("Error connect to table 'candidate'", e);
        }
        return candidates;
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Failed to create", e);
        }
        post.setCreated(LocalDateTime.now());
        return post;
    }

    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE post SET name = ? WHERE id = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Failed to update", e);
        }
    }

    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE candidate SET name = ?, city_id = ? WHERE id = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.setInt(3, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Failed to update", e);
        }
    }

    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO candidate(name, city_id) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Failed to create", e);
        }
        candidate.setCreated(LocalDateTime.now());
        return candidate;
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(it.getInt("id"), it.getString("name"),
                            it.getTimestamp("created").toLocalDateTime().withNano(0));
                }
            }
        } catch (Exception e) {
            LOG.error("Failed to find by id", e);
        }
        return null;
    }

    public Candidate findByIdCon(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(it.getInt("id"),
                            it.getString("name"),
                            it.getInt("city_id"),
                            it.getTimestamp("created").toLocalDateTime().withNano(0));
                }
            }
        } catch (Exception e) {
            LOG.error("Failed to find by id", e);
        }
        return null;
    }

    public void delCon(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM candidate WHERE id = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            LOG.error("Failed to delete", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE email = ?")
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new User(it.getInt("id"), it.getString("name"), it.getString("email"), it.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG.error("Failed to find by email", e);
        }
        return null;
    }

    @Override
    public User addUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO users(name, email, password ) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Failed to create", e);
        }
        return user;
    }

    @Override
    public Map<Integer, String> allCity() {
        Map<Integer, String> cities = new LinkedHashMap<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM city")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cities.put(it.getInt("id"), it.getString("name"));
                }
            }
        } catch (Exception e) {
            LOG.error("Error connect to table 'city'", e);
        }
        return cities;
    }

    public List<Post> postForDay() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE created "
                     + "BETWEEN CURRENT_TIMESTAMP - interval '1 day' AND CURRENT_TIMESTAMP")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"),
                            it.getString("name"),
                            it.getTimestamp("created").toLocalDateTime().withNano(0)));
                }
            }
        } catch (Exception e) {
            LOG.error("Error connect to table 'post'", e);
        }
        return posts;
    }

    public List<Candidate> candidateForDay() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate WHERE created "
                     + "BETWEEN CURRENT_TIMESTAMP - interval '1 day' AND CURRENT_TIMESTAMP")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"),
                            it.getString("name"),
                            it.getInt("city_id"),
                            it.getTimestamp("created").toLocalDateTime().withNano(0)));
                }
            }
        } catch (Exception e) {
            LOG.error("Error connect to table 'candidate'", e);
        }
        return candidates;
    }
}
