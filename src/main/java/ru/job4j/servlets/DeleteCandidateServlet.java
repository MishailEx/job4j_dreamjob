package ru.job4j.servlets;

import ru.job4j.dream.model.MemStore;
import ru.job4j.dream.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteCandidateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File file = new File("c:\\images\\" + req.getParameter("name") + ".jpg");
        file.delete();
        DbStore.instOf().delCon(Integer.parseInt(req.getParameter("name")));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");

    }
}
