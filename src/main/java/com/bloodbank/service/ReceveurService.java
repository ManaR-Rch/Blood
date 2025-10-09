package com.bloodbank.service;

import com.bloodbank.model.Receveur;

public class ReceveurService {
    
    public void verifierPriorite(Receveur receveur) {
        if (receveur == null) {
            return;
        }
        
        // Logique simple de vérification de priorité
        if (receveur.getPriorite() == Receveur.Priorite.CRITIQUE) {
            // Traitement pour priorité critique
            System.out.println("Receveur critique - traitement urgent");
        } else if (receveur.getPriorite() == Receveur.Priorite.URGENT) {
            // Traitement pour priorité urgente
            System.out.println("Receveur urgent - traitement prioritaire");
        } else {
            // Traitement normal
            System.out.println("Receveur normal - traitement standard");
        }
    }
}
