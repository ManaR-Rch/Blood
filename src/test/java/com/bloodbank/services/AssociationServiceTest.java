package com.bloodbank.service;

import com.bloodbank.dao.DonneurDAO;
import com.bloodbank.dao.ReceveurDAO;
import com.bloodbank.model.Donneur;
import com.bloodbank.model.Receveur;
import com.bloodbank.model.Receveur.Priorite;
import com.bloodbank.model.Receveur.Etat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssociationServiceTest {

    // On crée des mocks pour les dépendances
    @Mock
    private DonneurDAO donneurDAO;
    @Mock
    private ReceveurDAO receveurDAO;
    @Mock
    private CompatibiliteService compatibiliteService;

    // Le service que l'on veut tester, et on y injecte les mocks
    @InjectMocks
    private AssociationService associationService;
    
    // Objets de test
    private Donneur donneurDisponible;
    private Receveur receveurEnAttenteCritique;
    private Receveur receveurSatisfait;


    @BeforeEach
    void setUp() {
        // Initialise les mocks et injecte-les dans associationService
        MockitoAnnotations.openMocks(this);

        // Configuration d'un donneur valide
        donneurDisponible = new Donneur();
        donneurDisponible.setId(1L);
        donneurDisponible.setGroupeSanguin("A+");
        donneurDisponible.setStatutDisponibilite(Donneur.StatutDisponibilite.DISPONIBLE);
        
        // Configuration d'un receveur en attente (CRITIQUE)
        receveurEnAttenteCritique = new Receveur();
        receveurEnAttenteCritique.setId(10L);
        receveurEnAttenteCritique.setGroupeSanguin("AB+");
        receveurEnAttenteCritique.setPriorite(Priorite.CRITIQUE); // 4 poches requises
        receveurEnAttenteCritique.setEtat(Etat.EN_ATTENTE);
        receveurEnAttenteCritique.setDonneursAssocies(new ArrayList<>()); // Doit être initialisé pour la taille

        // Configuration d'un receveur déjà satisfait (pour les tests de rejet)
        receveurSatisfait = new Receveur();
        receveurSatisfait.setId(11L);
        receveurSatisfait.setEtat(Etat.SATISFAIT);
    }
    
    @Test
    void testAssociationReussie() {
        // 1. Configurer les mocks pour le succès
        when(donneurDAO.findById(1L)).thenReturn(donneurDisponible);
        when(receveurDAO.findById(10L)).thenReturn(receveurEnAttenteCritique);
        when(compatibiliteService.estCompatible("A+", "AB+")).thenReturn(true);

        // 2. Appeler la méthode à tester
        boolean resultat = associationService.associerDonneurReceveur(1L, 10L);

        // 3. Vérifier le résultat et les interactions
        assertTrue(resultat, "L'association devrait réussir.");
        
        // Vérifier que les méthodes DAO d'update ont été appelées
        verify(donneurDAO, times(1)).update(any(Donneur.class));
        verify(receveurDAO, times(1)).update(any(Receveur.class));

        // Vérifier les changements d'état
        assertEquals(Donneur.StatutDisponibilite.NON_DISPONIBLE, donneurDisponible.getStatutDisponibilite());
        assertEquals(receveurEnAttenteCritique, donneurDisponible.getReceveurAssocie());
        
        // Pour une seule poche (critique = 4), le receveur doit rester EN_ATTENTE
        assertEquals(Etat.EN_ATTENTE, receveurEnAttenteCritique.getEtat());
        assertEquals(1, receveurEnAttenteCritique.getDonneursAssocies().size());
    }

    @Test
    void testAssociationEchoue_DonneurNonDisponible() {
        donneurDisponible.setStatutDisponibilite(Donneur.StatutDisponibilite.NON_DISPONIBLE);
        when(donneurDAO.findById(1L)).thenReturn(donneurDisponible);
        when(receveurDAO.findById(10L)).thenReturn(receveurEnAttenteCritique);

        boolean resultat = associationService.associerDonneurReceveur(1L, 10L);

        assertFalse(resultat, "L'association devrait échouer si le donneur n'est pas DISPONIBLE.");
        // Vérifier qu'aucune persistance n'a eu lieu
        verify(donneurDAO, never()).update(any(Donneur.class));
    }
    
    @Test
    void testAssociationEchoue_ReceveurNonEnAttente() {
        when(donneurDAO.findById(1L)).thenReturn(donneurDisponible);
        when(receveurDAO.findById(11L)).thenReturn(receveurSatisfait);

        boolean resultat = associationService.associerDonneurReceveur(1L, 11L);

        assertFalse(resultat, "L'association devrait échouer si le receveur n'est pas EN_ATTENTE.");
        verify(donneurDAO, never()).update(any(Donneur.class));
    }
    
    @Test
    void testAssociationEchoue_Incompatibilite() {
        when(donneurDAO.findById(1L)).thenReturn(donneurDisponible);
        when(receveurDAO.findById(10L)).thenReturn(receveurEnAttenteCritique);
        when(compatibiliteService.estCompatible("A+", "AB+")).thenReturn(false); // Simuler l'incompatibilité

        boolean resultat = associationService.associerDonneurReceveur(1L, 10L);

        assertFalse(resultat, "L'association devrait échouer en cas d'incompatibilité.");
        verify(donneurDAO, never()).update(any(Donneur.class));
    }

    @Test
    void testVerifierSiReceveurSatisfait_Critique() {
        // Receveur CRITIQUE (besoin de 4)
        Receveur receveur = new Receveur();
        receveur.setPriorite(Priorite.CRITIQUE);
        receveur.setEtat(Etat.EN_ATTENTE);
        receveur.setDonneursAssocies(new ArrayList<>(Collections.nCopies(3, new Donneur()))); // 3 poches reçues

        // L'association va ajouter la 4ème poche
        when(donneurDAO.findById(1L)).thenReturn(donneurDisponible);
        when(receveurDAO.findById(10L)).thenReturn(receveur);
        when(compatibiliteService.estCompatible(anyString(), anyString())).thenReturn(true);
        
        associationService.associerDonneurReceveur(1L, 10L);
        
        // L'état doit être passé à SATISFAIT
        assertEquals(Etat.SATISFAIT, receveur.getEtat(), "Le receveur CRITIQUE devrait être SATISFAIT après 4 dons.");
    }

    @Test
    void testVerifierSiReceveurSatisfait_Urgent() {
        // Receveur URGENT (besoin de 3)
        Receveur receveur = new Receveur();
        receveur.setPriorite(Priorite.URGENT);
        receveur.setEtat(Etat.EN_ATTENTE);
        receveur.setDonneursAssocies(new ArrayList<>(Collections.nCopies(2, new Donneur()))); // 2 poches reçues

        // L'association va ajouter la 3ème poche
        when(donneurDAO.findById(1L)).thenReturn(donneurDisponible);
        when(receveurDAO.findById(10L)).thenReturn(receveur);
        when(compatibiliteService.estCompatible(anyString(), anyString())).thenReturn(true);
        
        associationService.associerDonneurReceveur(1L, 10L);
        
        // L'état doit être passé à SATISFAIT
        assertEquals(Etat.SATISFAIT, receveur.getEtat(), "Le receveur URGENT devrait être SATISFAIT après 3 dons.");
    }
}