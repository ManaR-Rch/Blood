package com.bloodbank.service;

import com.bloodbank.model.Donneur;

public class DonneurService {
    
    public boolean estEligible(Donneur donneur) {
        if (donneur == null) {
            return false;
        }
        
        return donneur.getPoids() >= 50;
    }
}
