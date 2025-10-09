package com.bloodbank.controller;

import com.bloodbank.dao.ReceveurDAO;
import com.bloodbank.model.Receveur;
import com.bloodbank.service.ReceveurService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ReceveurServlet extends HttpServlet {
    
    private ReceveurDAO receveurDAO;
    private ReceveurService receveurService;
    
    @Override
    public void init() throws ServletException {
        receveurDAO = new ReceveurDAO();
        receveurService = new ReceveurService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Receveur> receveurs = receveurDAO.findAll();
        request.setAttribute("receveurs", receveurs);
        
        request.getRequestDispatcher("/WEB-INF/views/receveurs.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String cin = request.getParameter("cin");
        String groupeSanguin = request.getParameter("groupeSanguin");
        String prioriteStr = request.getParameter("priorite");
        
        Receveur.Priorite priorite = Receveur.Priorite.valueOf(prioriteStr);
        
        Receveur receveur = new Receveur(nom, prenom, telephone, cin, groupeSanguin, 
                                        priorite, Receveur.Etat.EN_ATTENTE);
        
        receveurDAO.save(receveur);
        
        response.sendRedirect("receveurs");
    }
    
    @Override
    public void destroy() {
        if (receveurDAO != null) {
            receveurDAO.close();
        }
    }
}
