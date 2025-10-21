package com.bloodbank.service;

import com.bloodbank.model.Donneur;

public class DonneurService {
    
    public boolean estEligible(Donneur donneur) {
        if (donneur == null) {
            return false;
        }
        // Poids ≥ 50kg
        if (donneur.getPoids() < 50) return false;

        // TODO: Vérification âge (nécessitera un champ dateNaissance dans Donneur)
        // Pour l'instant on retourne true si le poids est OK.
        return true;
    }
}
