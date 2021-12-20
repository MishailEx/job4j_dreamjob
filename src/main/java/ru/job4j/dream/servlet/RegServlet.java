package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (DbStore.instOf().findByEmail(req.getParameter("email")) == null) {
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            if (email.matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}")) {

                DbStore.instOf().addUser(new User(name, email, password));
                req.getRequestDispatcher("auth.do").forward(req, resp);
            } else {
                req.setAttribute("error", "Не верный формат email");
                req.getRequestDispatcher("reg.jsp").forward(req, resp);
            }

        } else  {
            req.setAttribute("error", "такой пользователь уже существует");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}
