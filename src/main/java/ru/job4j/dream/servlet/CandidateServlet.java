package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        Store store = DbStore.instOf();
        store.save(new Candidate(
                Integer.valueOf(req.getParameter("id")),
                req.getParameter("name"),
                req.getParameter("description"),
                Integer.valueOf(req.getParameter("city")),
                user.getEmail()));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = DbStore.instOf();
        String edit = req.getParameter("edit");
        String path = edit != null ? "/candidate/edit.jsp" : "/candidates.jsp";
        req.setAttribute("user", req.getSession().getAttribute("user"));
        if (edit == null) {
            req.setAttribute("candidates", store.findAllCandidates());
            req.setAttribute("allCities", store.allCity());
        }
        req.getRequestDispatcher(path).forward(req, resp);
    }
}
