package com.bloodbank.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class HomeServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        doGet(request, response);
    }
}
