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
        String poidsStr = request.getParameter("poids");
        String sexe = request.getParameter("sexe");
        
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
        if (poidsStr == null || poidsStr.trim().isEmpty()) {
            erreur += "Le poids est obligatoire.<br>";
        }
        if (sexe == null || sexe.trim().isEmpty()) {
            erreur += "Le sexe est obligatoire.<br>";
        }
        
        // Si il y a des erreurs, renvoyer vers le formulaire
        if (!erreur.isEmpty()) {
            request.setAttribute("erreur", erreur);
            request.getRequestDispatcher("/WEB-INF/views/ajouter-donneur.jsp").forward(request, response);
            return;
        }
        
        // Validation du poids (doit être un nombre)
        double poids;
        try {
            poids = Double.parseDouble(poidsStr);
            if (poids <= 0) {
                erreur += "Le poids doit être supérieur à 0.<br>";
            }
        } catch (NumberFormatException e) {
            erreur += "Le poids doit être un nombre valide.<br>";
        }
        
        // Si erreur de validation du poids, renvoyer vers le formulaire
        if (!erreur.isEmpty()) {
            request.setAttribute("erreur", erreur);
            request.getRequestDispatcher("/WEB-INF/views/ajouter-donneur.jsp").forward(request, response);
            return;
        }
        
        // Créer et sauvegarder le donneur
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
