package com.bloodbank.controller;

import com.bloodbank.dao.ReceveurDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class SupprimerReceveurServlet extends HttpServlet {

    private ReceveurDAO receveurDAO;

    @Override
    public void init() throws ServletException {
        receveurDAO = new ReceveurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        try {
            Long id = Long.parseLong(idParam);
            receveurDAO.delete(id);
        } catch (Exception ignored) {
        }

        response.sendRedirect("receveurs");
    }

    @Override
    public void destroy() {
        if (receveurDAO != null) {
            receveurDAO.close();
        }
    }
}


