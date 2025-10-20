package com.bloodbank.controller;

import com.bloodbank.dao.DonneurDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class SupprimerDonneurServlet extends HttpServlet {

    private DonneurDAO donneurDAO;

    @Override
    public void init() throws ServletException {
        donneurDAO = new DonneurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        try {
            Long id = Long.parseLong(idParam);
            donneurDAO.delete(id);
        } catch (Exception ignored) {
        }

        response.sendRedirect("donneurs");
    }

    @Override
    public void destroy() {
        if (donneurDAO != null) {
            donneurDAO.close();
        }
    }
}


