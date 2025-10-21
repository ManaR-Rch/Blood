package com.bloodbank.service;

import com.bloodbank.dao.DonneurDAO;
import com.bloodbank.dao.ReceveurDAO;
import com.bloodbank.model.Donneur;
import com.bloodbank.model.Receveur;

import java.util.Objects;

public class AssociationService {
	private final DonneurDAO donneurDAO;
	private final ReceveurDAO receveurDAO;
	private final CompatibiliteService compatibiliteService;

	public AssociationService() {
		donneurDAO = new DonneurDAO();
		receveurDAO = new ReceveurDAO();
		compatibiliteService = new CompatibiliteService();
	}

	/**
	 * Effectue l'association donneur -> receveur en vérifiant la compatibilité et les statuts.
	 * Retourne true si l'association a été persistée avec succès.
	 */
	public boolean associerDonneurReceveur(Long idDonneur, Long idReceveur) {
		if (idDonneur == null || idReceveur == null) return false;

		Donneur donneur = donneurDAO.findById(idDonneur);
		Receveur receveur = receveurDAO.findById(idReceveur);

		if (donneur == null || receveur == null) return false;

		// Vérifier disponibilités/statuts
		if (donneur.getStatutDisponibilite() != Donneur.StatutDisponibilite.DISPONIBLE) {
			return false; // donneur non disponible
		}

		if (receveur.getEtat() != Receveur.Etat.EN_ATTENTE) {
			return false; // receveur pas en attente
		}

		// Vérifier compatibilité des groupes sanguins
		String gDon = donneur.getGroupeSanguin();
		String gRec = receveur.getGroupeSanguin();
		if (!compatibiliteService.estCompatible(gDon, gRec)) {
			return false; // incompatibilité
		}

		// Effectuer l'association en mémoire
		donneur.setReceveurAssocie(receveur);
		// Marquer donneur comme non disponible après donation
		donneur.setStatutDisponibilite(Donneur.StatutDisponibilite.NON_DISPONIBLE);

		// Ajouter à la collection côté receveur si absent
		if (receveur.getDonneursAssocies() == null) {
			receveur.setDonneursAssocies(new java.util.ArrayList<>());
		}
		if (!receveur.getDonneursAssocies().contains(donneur)) {
			receveur.getDonneursAssocies().add(donneur);
		}

	// Ne pas marquer directement le receveur comme SATISFAIT ici.
	// L'état doit être vérifié en fonction du nombre requis de donneurs.
	verifierEtatReceveur(receveur);

		// Persister changements via DAOs. If one fails, we return false.
		try {
			donneurDAO.update(donneur);
			receveurDAO.update(receveur);
			return true;
		} catch (Exception ex) {
			// Tentative de rollback léger : essayer de rétablir l'état en mémoire
			try {
				donneur.setReceveurAssocie(null);
				donneur.setStatutDisponibilite(Donneur.StatutDisponibilite.DISPONIBLE);
				donneurDAO.update(donneur);
			} catch (Exception ignored) {
			}

			/**
			 * Vérifie le nombre de donneurs associés et met à jour l'état du receveur si besoin.
			 */
			private void verifierEtatReceveur(Receveur receveur) {
				if (receveur == null) return;
				int besoin = getBesoinPoches(receveur.getPriorite());
				int donneursAssocies = (receveur.getDonneursAssocies() != null) ? receveur.getDonneursAssocies().size() : 0;

				if (donneursAssocies >= besoin) {
					receveur.setEtat(Receveur.Etat.SATISFAIT);
				}
			}

			private int getBesoinPoches(Receveur.Priorite priorite) {
				if (priorite == null) return 1;
				switch (priorite) {
					case NORMAL:
						return 1;
					case URGENT:
						return 3;
					case CRITIQUE:
						return 4;
					default:
						return 1;
				}
			}
			return false;
		}
	}

	public void close() {
		try {
			if (donneurDAO != null) {
				donneurDAO.close();
			}
			if (receveurDAO != null) {
				receveurDAO.close();
			}
		} catch (Exception ignored) {
		}
	}
}
