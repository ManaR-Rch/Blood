package com.bloodbank.controller;

import com.bloodbank.dao.DonneurDAO;
import com.bloodbank.model.Donneur;
import com.bloodbank.service.DonneurService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class DonneurServlet extends HttpServlet {
    
    private DonneurDAO donneurDAO;
    private DonneurService donneurService;
    
    @Override
    public void init() throws ServletException {
        donneurDAO = new DonneurDAO();
        donneurService = new DonneurService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Donneur> donneurs = donneurDAO.findAll();
        request.setAttribute("donneurs", donneurs);
        
        request.getRequestDispatcher("/WEB-INF/views/donneurs.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String cin = request.getParameter("cin");
        String groupeSanguin = request.getParameter("groupeSanguin");
        double poids = Double.parseDouble(request.getParameter("poids"));
        String sexe = request.getParameter("sexe");
        
        Donneur donneur = new Donneur(nom, prenom, telephone, cin, groupeSanguin, 
                                     poids, sexe, Donneur.StatutDisponibilite.DISPONIBLE);
        
        donneurDAO.save(donneur);
        
        response.sendRedirect("donneurs");
    }
    
    @Override
    public void destroy() {
        if (donneurDAO != null) {
            donneurDAO.close();
        }
    }
}
