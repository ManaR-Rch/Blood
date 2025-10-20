package com.bloodbank.controller;

import com.bloodbank.dao.ReceveurDAO;
import com.bloodbank.model.Receveur;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ModifierReceveurServlet extends HttpServlet {

    private ReceveurDAO receveurDAO;

    @Override
    public void init() throws ServletException {
        receveurDAO = new ReceveurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                Receveur receveur = receveurDAO.findById(id);
                if (receveur != null) {
                    request.setAttribute("receveur", receveur);
                } else {
                    request.setAttribute("erreur", "Receveur introuvable.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("erreur", "Identifiant invalide.");
            }
        } else {
            request.setAttribute("erreur", "Identifiant manquant.");
        }

        request.getRequestDispatcher("/WEB-INF/views/modifier-receveur.jsp").forward(request, response);
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
        String prioriteStr = request.getParameter("priorite");

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
        if (prioriteStr == null || prioriteStr.trim().isEmpty()) erreur += "La priorité est obligatoire.<br>";

        Receveur.Priorite priorite = null;
        if (erreur.isEmpty()) {
            try {
                priorite = Receveur.Priorite.valueOf(prioriteStr);
            } catch (IllegalArgumentException e) {
                erreur += "La priorité sélectionnée n'est pas valide.<br>";
            }
        }

        if (!erreur.isEmpty()) {
            if (id != null) {
                Receveur receveur = receveurDAO.findById(id);
                request.setAttribute("receveur", receveur);
            }
            request.setAttribute("erreur", erreur);
            request.getRequestDispatcher("/WEB-INF/views/modifier-receveur.jsp").forward(request, response);
            return;
        }

        Receveur receveur = receveurDAO.findById(id);
        if (receveur == null) {
            request.setAttribute("erreur", "Receveur introuvable.");
            request.getRequestDispatcher("/WEB-INF/views/modifier-receveur.jsp").forward(request, response);
            return;
        }

        receveur.setNom(nom);
        receveur.setPrenom(prenom);
        receveur.setTelephone(telephone);
        receveur.setCin(cin);
        receveur.setGroupeSanguin(groupeSanguin);
        receveur.setPriorite(priorite);

        receveurDAO.update(receveur);

        response.sendRedirect("receveurs");
    }

    @Override
    public void destroy() {
        if (receveurDAO != null) {
            receveurDAO.close();
        }
    }
}


