package com.bloodbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompatibiliteServiceTest {

    private CompatibiliteService compatibiliteService;

    @BeforeEach
    void setUp() {
        compatibiliteService = new CompatibiliteService();
    }

    @Test
    void testDonneurUniversel() {
        // O- donne à AB+ (Receveur Universel)
        assertTrue(compatibiliteService.estCompatible("O-", "AB+"), "O- devrait être compatible avec AB+");
        // O- donne à O-
        assertTrue(compatibiliteService.estCompatible("O-", "O-"), "O- devrait être compatible avec O-");
        // O- donne à A+
        assertTrue(compatibiliteService.estCompatible("O-", "A+"), "O- devrait être compatible avec A+");
    }

    @Test
    void testReceveurUniversel() {
        // AB+ reçoit de A-
        assertTrue(compatibiliteService.estCompatible("A-", "AB+"), "AB+ devrait recevoir de A-");
        // AB+ reçoit de B+
        assertTrue(compatibiliteService.estCompatible("B+", "AB+"), "AB+ devrait recevoir de B+");
    }

    @Test
    void testIncompatibiliteRh() {
        // Rh+ ne donne pas à Rh-
        assertFalse(compatibiliteService.estCompatible("A+", "A-"), "A+ ne devrait pas donner à A-");
        assertFalse(compatibiliteService.estCompatible("B+", "O-"), "B+ ne devrait pas donner à O-");
        // Rh- donne à Rh-
        assertTrue(compatibiliteService.estCompatible("A-", "A-"), "A- devrait donner à A-");
    }

    @Test
    void testCompatibiliteCroisee() {
        // A+ peut donner à AB+
        assertTrue(compatibiliteService.estCompatible("A+", "AB+"));
        // A+ ne peut pas donner à B+
        assertFalse(compatibiliteService.estCompatible("A+", "B+"));
        // O+ peut donner à B- (faux à cause de Rh, mais VRAI selon la logique de votre service qui gère le Rh après le type)
        // Correction de la règle dans le service:
        // Si O+, il peut donner à tous les Rh+ et O-
        assertFalse(compatibiliteService.estCompatible("O+", "B-"), "O+ ne devrait pas donner à B- (Rh)"); 
        assertTrue(compatibiliteService.estCompatible("O+", "B+"), "O+ devrait donner à B+"); 
        
        // Données invalides
        assertFalse(compatibiliteService.estCompatible(null, "A+"));
        assertFalse(compatibiliteService.estCompatible("A+", "XYZ"));
    }
}