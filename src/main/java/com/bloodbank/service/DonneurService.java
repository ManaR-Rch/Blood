package com.bloodbank.service;

import com.bloodbank.model.Donneur;

public class DonneurService {
    
    public boolean estEligible(Donneur donneur) {
        if (donneur == null) {
            return false;
        }
        // Poids ≥ 50kg
        if (donneur.getPoids() < 50) return false;

        // Âge 18-65 ans
        Integer age = donneur.getAge();
        if (age == null) return false;
        if (age < 18 || age > 65) return false;

        // Contre-indications médicales (si vrai => non éligible)
        if (donneur.getContreIndications()) return false;

        return true;
    }
}
