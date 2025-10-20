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
        
        String requestURI = request.getRequestURI();
        
        // Si c'est une requête pour ajouter un receveur
        if (requestURI.endsWith("/ajouter-receveur")) {
            request.getRequestDispatcher("/WEB-INF/views/ajouter-receveur.jsp").forward(request, response);
            return;
        }
        
        // Sinon, afficher la liste des receveurs
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
        
        String erreur = "";
        
        // Validation simple des champs vides
        if (nom == null || nom.trim().isEmpty()) {
            erreur += "Le nom est obligatoire.<br>";
        }
        if (prenom == null || prenom.trim().isEmpty()) {
            erreur += "Le prénom est obligatoire.<br>";
        }
        if (telephone == null || telephone.trim().isEmpty()) {
            erreur += "Le téléphone est obligatoire.<br>";
        }
        if (cin == null || cin.trim().isEmpty()) {
            erreur += "Le CIN est obligatoire.<br>";
        }
        if (groupeSanguin == null || groupeSanguin.trim().isEmpty()) {
            erreur += "Le groupe sanguin est obligatoire.<br>";
        }
        if (prioriteStr == null || prioriteStr.trim().isEmpty()) {
            erreur += "La priorité est obligatoire.<br>";
        }
        
        // Si il y a des erreurs, renvoyer vers le formulaire
        if (!erreur.isEmpty()) {
            request.setAttribute("erreur", erreur);
            request.getRequestDispatcher("/WEB-INF/views/ajouter-receveur.jsp").forward(request, response);
            return;
        }
        
        // Validation de la priorité
        Receveur.Priorite priorite;
        try {
            priorite = Receveur.Priorite.valueOf(prioriteStr);
        } catch (IllegalArgumentException e) {
            erreur += "La priorité sélectionnée n'est pas valide.<br>";
            request.setAttribute("erreur", erreur);
            request.getRequestDispatcher("/WEB-INF/views/ajouter-receveur.jsp").forward(request, response);
            return;
        }
        
        // Créer et sauvegarder le receveur
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
