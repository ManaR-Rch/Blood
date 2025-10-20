package com.bloodbank.controller;

import com.bloodbank.dao.DonneurDAO;
import com.bloodbank.model.Donneur;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ModifierDonneurServlet extends HttpServlet {

    private DonneurDAO donneurDAO;

    @Override
    public void init() throws ServletException {
        donneurDAO = new DonneurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                Donneur donneur = donneurDAO.findById(id);
                if (donneur != null) {
                    request.setAttribute("donneur", donneur);
                } else {
                    request.setAttribute("erreur", "Donneur introuvable.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("erreur", "Identifiant invalide.");
            }
        } else {
            request.setAttribute("erreur", "Identifiant manquant.");
        }

        request.getRequestDispatcher("/WEB-INF/views/modifier-donneur.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String cin = request.getParameter("cin");
        String groupeSanguin = request.getParameter("groupeSanguin");
        String poidsStr = request.getParameter("poids");
        String sexe = request.getParameter("sexe");

        String erreur = "";

        Long id = null;
        try {
            id = Long.parseLong(idParam);
        } catch (Exception e) {
            erreur += "Identifiant invalide.<br>";
        }

        if (nom == null || nom.trim().isEmpty()) erreur += "Le nom est obligatoire.<br>";
        if (prenom == null || prenom.trim().isEmpty()) erreur += "Le prénom est obligatoire.<br>";
        if (telephone == null || telephone.trim().isEmpty()) erreur += "Le téléphone est obligatoire.<br>";
        if (cin == null || cin.trim().isEmpty()) erreur += "Le CIN est obligatoire.<br>";
        if (groupeSanguin == null || groupeSanguin.trim().isEmpty()) erreur += "Le groupe sanguin est obligatoire.<br>";
        if (poidsStr == null || poidsStr.trim().isEmpty()) erreur += "Le poids est obligatoire.<br>";
        if (sexe == null || sexe.trim().isEmpty()) erreur += "Le sexe est obligatoire.<br>";

        double poids = 0;
        if (erreur.isEmpty()) {
            try {
                poids = Double.parseDouble(poidsStr);
                if (poids <= 0) {
                    erreur += "Le poids doit être supérieur à 0.<br>";
                }
            } catch (NumberFormatException e) {
                erreur += "Le poids doit être un nombre valide.<br>";
            }
        }

        if (!erreur.isEmpty()) {
            if (id != null) {
                Donneur donneur = donneurDAO.findById(id);
                request.setAttribute("donneur", donneur);
            }
            request.setAttribute("erreur", erreur);
            request.getRequestDispatcher("/WEB-INF/views/modifier-donneur.jsp").forward(request, response);
            return;
        }

        Donneur donneur = donneurDAO.findById(id);
        if (donneur == null) {
            request.setAttribute("erreur", "Donneur introuvable.");
            request.getRequestDispatcher("/WEB-INF/views/modifier-donneur.jsp").forward(request, response);
            return;
        }

        donneur.setNom(nom);
        donneur.setPrenom(prenom);
        donneur.setTelephone(telephone);
        donneur.setCin(cin);
        donneur.setGroupeSanguin(groupeSanguin);
        donneur.setPoids(poids);
        donneur.setSexe(sexe);

        donneurDAO.update(donneur);

        response.sendRedirect("donneurs");
    }

    @Override
    public void destroy() {
        if (donneurDAO != null) {
            donneurDAO.close();
        }
    }
}


