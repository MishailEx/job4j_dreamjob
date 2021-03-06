package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setCharacterEncoding("UTF-8");
        Store store = DbStore.instOf();
        store.save(
                new Post(Integer.valueOf(req.getParameter("id")),
                        req.getParameter("name"),
                        req.getParameter("description"),
                        user.getEmail()));
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = DbStore.instOf();
        String edit = req.getParameter("edit");
        String path = edit != null ? "/post/edit.jsp" : "/posts.jsp";
        req.setAttribute("user", req.getSession().getAttribute("user"));
        if (edit == null) {
            req.setAttribute("posts", store.findAllPosts());
        }
        req.getRequestDispatcher(path).forward(req, resp);
    }
}