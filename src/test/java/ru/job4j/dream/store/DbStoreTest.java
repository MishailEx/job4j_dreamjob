package ru.job4j.dream.store;

import org.junit.*;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DbStoreTest {
    static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = DbStore.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("jdbc.driver"));
            connection = DriverManager.getConnection(
                    config.getProperty("jdbc.url"),
                    config.getProperty("jdbc.username"),
                    config.getProperty("jdbc.password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement =  connection.prepareStatement("DELETE FROM post; ALTER TABLE post ALTER COLUMN id RESTART WITH 1; DELETE FROM candidate; ALTER TABLE candidate ALTER COLUMN id RESTART WITH 1; DELETE FROM users;")) {
            statement.execute();
        }
    }

    @Test
    public void whenCreatePost() {
        Store store = DbStore.instOf();
        Post post = new Post(0, "Java Job", "ddd", "ddd");
        store.save(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenCreateCandidate() {
        Store store = DbStore.instOf();
        Candidate candidate = new Candidate(0, "Oleg", "");
        store.save(candidate);
        Candidate postInDb = store.findByIdCon(candidate.getId());
        assertThat(postInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdateCandidate() {
        Store store = DbStore.instOf();
        Candidate candidate = new Candidate(0, "Oleg", "");
        store.save(candidate);
        store.save(new Candidate(candidate.getId(), "Max", ""));
        Candidate postInDb = store.findByIdCon(candidate.getId());
        assertThat(postInDb.getName(), is("Max"));
    }

    @Test
    public void whenUpdatePost() {
        Store store = DbStore.instOf();
        Post post = new Post(0, "Jun", "Jun", "Jun");
        store.save(post);
        store.save(new Post(post.getId(), "Mid", "Jun", "Jun"));
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is("Mid"));
    }

    @Test
    public void whenFindAllPosts() {
        Store store = DbStore.instOf();
        Post post = new Post(0, "Jun", "Jun", "Jun");
        Post post2 = new Post(0, "Mid", "Jun", "Jun");
        store.save(post);
        store.save(post2);
        Collection<Post> res = List.of(post, post2);
        assertThat(store.findAllPosts(), is(res));
    }

    @Test
    public void whenFindAllCandidates() {
        Store store = DbStore.instOf();
        Candidate candidate = new Candidate(0, "Jun", "");
        Candidate candidate1 = new Candidate(0, "Mid", "");
        store.save(candidate);
        store.save(candidate1);
        Collection<Candidate> res = List.of(candidate, candidate1);
        assertThat(store.findAllCandidates(), is(res));
    }

    @Test
    public void whenFindByIdCandidates() {
        Store store = DbStore.instOf();
        Candidate candidate = new Candidate(0, "Jun", "");
        Candidate candidate1 = new Candidate(0, "Mid", "");
        store.save(candidate);
        store.save(candidate1);
        Candidate res = store.findByIdCon(candidate1.getId());
        assertThat(res.getId(), is(candidate1.getId()));
    }

    @Test
    public void whenFindByIdPost() {
        Store store = DbStore.instOf();
        Post post = new Post(0, "Jun", "Jun", "Jun");
        Post post1 = new Post(0, "Mid", "Jun", "Jun");
        store.save(post);
        store.save(post1);
        Post res = store.findById(post1.getId());
        assertThat(res.getId(), is(post1.getId()));
    }

    @Test
    public void whenRegisterUserExist() {
        Store store = DbStore.instOf();
        User user = new User("oleg", "oleg@mail.ru", "oleg", "hr");
        User user2 = new User("ivan", "oleg@mail.ru", "ivan", "candidate");
        store.addUser(user);
        store.addUser(user2);
        User user3 = store.findByEmail("oleg@mail.ru");
        assertThat(user3.getName(), is(user.getName()));
    }

    @Test
    public void whenFindByEmailWhenExist() {
        Store store = DbStore.instOf();
        User user = new User("oleg", "oleg@mail.ru", "oleg", "hr");
        store.addUser(user);
        User user1 = store.findByEmail("oleg@mail.ru");
        assertThat(user, is(user1));
    }

    @Test
    public void whenFindByEmailWhenNotExist() {
        Store store = DbStore.instOf();
        User user = store.findByEmail("oleg@mail.ru");
        Assert.assertNull(user);
    }
}