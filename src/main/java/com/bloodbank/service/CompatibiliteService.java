package com.bloodbank.service;

import java.util.*;

/**
 * Service simple pour vérifier la compatibilité entre groupes sanguins.
 * Règles implémentées (simplifiées) :
 * - O- est donneur universel (compatible avec tous)
 * - AB+ est receveur universel (peut recevoir de tous)
 * - Rh+ ne peut pas donner à Rh- (ex: A+ -> A- non compatible)
 * - Type A donne à A et AB, B donne à B et AB, O donne à tous, AB donne à AB
 */
public class CompatibiliteService {

    private static final Set<String> GROUPES_VALIDES = new HashSet<>(Arrays.asList(
            "O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"));

    /**
     * Vérifie si un donneur avec groupe 'groupeDonneur' est compatible avec un receveur 'groupeReceveur'.
     */
    public boolean estCompatible(String groupeDonneur, String groupeReceveur) {
        if (groupeDonneur == null || groupeReceveur == null) return false;
        groupeDonneur = groupeDonneur.trim().toUpperCase();
        groupeReceveur = groupeReceveur.trim().toUpperCase();

        if (!GROUPES_VALIDES.contains(groupeDonneur) || !GROUPES_VALIDES.contains(groupeReceveur)) {
            return false; // groupe inconnu => non compatible
        }

n        // Receveur universel
        if ("AB+".equals(groupeReceveur)) return true;

n        // Donneur universel
        if ("O-".equals(groupeDonneur)) return true;

        // Si donneur est Rh+ et receveur Rh- => incompatible
        boolean donneurRhPos = groupeDonneur.endsWith("+");
        boolean receveurRhNeg = groupeReceveur.endsWith("-");
        if (donneurRhPos && receveurRhNeg) return false;

        // Types de base (sans Rh): O, A, B, AB
        String baseDonneur = groupeDonneur.substring(0, groupeDonneur.length() - 1);
        String baseReceveur = groupeReceveur.substring(0, groupeReceveur.length() - 1);

        // O donne à tous (déjà couvert par O-), ici O+ donne aussi selon Rh rules
        if ("O".equals(baseDonneur)) return true;
        // A donne à A et AB
        if ("A".equals(baseDonneur) && ("A".equals(baseReceveur) || "AB".equals(baseReceveur))) return true;
        // B donne à B et AB
        if ("B".equals(baseDonneur) && ("B".equals(baseReceveur) || "AB".equals(baseReceveur))) return true;
        // AB donne seulement à AB
        if ("AB".equals(baseDonneur) && "AB".equals(baseReceveur)) return true;

        return false;
    }

    /**
     * Retourne la liste des groupes receveurs compatibles pour un groupe donneur donné.
     */
    public List<String> receveursCompatiblesPour(String groupeDonneur) {
        List<String> compatibles = new ArrayList<>();
        for (String receveur : GROUPES_VALIDES) {
            if (estCompatible(groupeDonneur, receveur)) compatibles.add(receveur);
        }
        return compatibles;
    }

    /**
     * Retourne la liste des groupes donneurs compatibles pour un groupe receveur donné.
     */
    public List<String> donneursCompatiblesPour(String groupeReceveur) {
        List<String> compatibles = new ArrayList<>();
        for (String donneur : GROUPES_VALIDES) {
            if (estCompatible(donneur, groupeReceveur)) compatibles.add(donneur);
        }
        return compatibles;
    }
}
