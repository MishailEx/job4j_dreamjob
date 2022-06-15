package ru.job4j.dream.servlet;

import org.junit.Test;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

public class PostServletTest {
    private final static Store STORE = DbStore.instOf();

    @Test
    public void whenCreatePost() throws IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user"))
                .thenReturn(new User("ff", "email@mail.ru", "111", "1"));
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("name of new post");
        when(req.getParameter("description")).thenReturn("d");
        new PostServlet().doPost(req, resp);
        Post post = STORE.findById(1);

        assertThat(post, notNullValue());
    }

    @Test
    public void whenFindAllPost() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);
        User user = new User(2, "jon", "admin@mail.ru", "admin", "HR");
        session.setAttribute("user", user);
        when(req.getParameter("edit")).thenReturn(null);
        when(req.getSession()).thenReturn(session);
        String path = "/posts.jsp";
        when(req.getRequestDispatcher(path)).thenReturn(dispatcher);
        new PostServlet().doGet(req, resp);
        verify(req, times(1)).getRequestDispatcher(path);
        verify(req, times(1)).getSession();
        verify(req, times(1)).setAttribute("posts", STORE.findAllPosts());
        verify(dispatcher).forward(req, resp);
    }

    @Test
    public void whenCreateCandidate() throws IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user"))
                .thenReturn(new User("ff", "email@mail.ru", "111", "1"));
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("name of new candidate");
        when(req.getParameter("city")).thenReturn("2");
        new CandidateServlet().doPost(req, resp);
        Candidate candidate = STORE.findByIdCon(1);
        assertThat(candidate, notNullValue());
    }

    @Test
    public void whenFindAllCandidate() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);
        User user = new User(2, "jon", "admin@mail.ru", "admin", "HR");
        session.setAttribute("user", user);
        when(req.getParameter("edit")).thenReturn(null);
        when(req.getSession()).thenReturn(session);
        String path = "/candidates.jsp";
        when(req.getRequestDispatcher(path)).thenReturn(dispatcher);
        new CandidateServlet().doGet(req, resp);
        verify(req, times(1)).getRequestDispatcher(path);
        verify(req, times(1)).getSession();
        verify(req, times(1)).setAttribute("candidates", STORE.findAllCandidates());
        verify(dispatcher).forward(req, resp);
    }
}