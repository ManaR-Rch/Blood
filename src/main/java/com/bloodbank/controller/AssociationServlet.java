package com.bloodbank.controller;

import com.bloodbank.dao.DonneurDAO;
import com.bloodbank.dao.ReceveurDAO;
import com.bloodbank.model.Donneur;
import com.bloodbank.model.Receveur;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AssociationServlet extends HttpServlet {

    private DonneurDAO donneurDAO;
    private ReceveurDAO receveurDAO;

    @Override
    public void init() throws ServletException {
        donneurDAO = new DonneurDAO();
        receveurDAO = new ReceveurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Donneur> donneursDisponibles = new ArrayList<>();
        List<Receveur> receveursEnAttente = new ArrayList<>();

        for (Donneur d : donneurDAO.findAll()) {
            if (d.getStatutDisponibilite() == Donneur.StatutDisponibilite.DISPONIBLE) {
                donneursDisponibles.add(d);
            }
        }

        for (Receveur r : receveurDAO.findAll()) {
            if (r.getEtat() == Receveur.Etat.EN_ATTENTE) {
                receveursEnAttente.add(r);
            }
        }

        request.setAttribute("donneursDisponibles", donneursDisponibles);
        request.setAttribute("receveursEnAttente", receveursEnAttente);

        request.getRequestDispatcher("/WEB-INF/views/association.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String donneurIdParam = request.getParameter("donneurId");
        String receveurIdParam = request.getParameter("receveurId");

        String erreur = "";
        Long donneurId = null;
        Long receveurId = null;

        try {
            donneurId = Long.parseLong(donneurIdParam);
        } catch (Exception e) {
            erreur += "Identifiant donneur invalide.<br>";
        }

        try {
            receveurId = Long.parseLong(receveurIdParam);
        } catch (Exception e) {
            erreur += "Identifiant receveur invalide.<br>";
        }

        if (erreur.isEmpty()) {
            Donneur d = donneurDAO.findById(donneurId);
            Receveur r = receveurDAO.findById(receveurId);
            if (d == null || r == null) {
                erreur += "Donneur ou receveur introuvable.<br>";
            }
        }

        if (!erreur.isEmpty()) {
            request.setAttribute("erreur", erreur);
            doGet(request, response);
            return;
        }

        response.sendRedirect("home");
    }

    @Override
    public void destroy() {
        if (donneurDAO != null) donneurDAO.close();
        if (receveurDAO != null) receveurDAO.close();
    }
}


