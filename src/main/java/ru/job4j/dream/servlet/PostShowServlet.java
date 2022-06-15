package ru.job4j.dream.servlet;

import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostShowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = DbStore.instOf();
        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("post", store.findById(id));
        req.getRequestDispatcher("/post/show.jsp").forward(req, resp);
    }
}
