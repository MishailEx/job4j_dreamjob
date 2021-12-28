package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {

    private final static Store STORE = DbStore.instOf();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        STORE.save(new Candidate(
                Integer.valueOf(req.getParameter("id")),
                req.getParameter("name"), Integer.valueOf(req.getParameter("city"))));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String edit = req.getParameter("edit");
        String path = edit != null ? "/candidate/edit.jsp" : "/candidates.jsp";
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setAttribute("user", req.getSession().getAttribute("user"));
        if (edit == null) {
            req.setAttribute("candidates", STORE.findAllCandidates());
            req.setAttribute("allCities", STORE.allCity());
        }
        req.getRequestDispatcher(path).forward(req, resp);
    }
}
