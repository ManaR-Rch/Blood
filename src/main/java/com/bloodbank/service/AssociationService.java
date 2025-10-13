package com.bloodbank.service;

import com.bloodbank.dao.DonneurDAO;
import com.bloodbank.dao.ReceveurDAO;
import com.bloodbank.model.Donneur;
import com.bloodbank.model.Receveur;

public class AssociationService {
	private final DonneurDAO donneurDAO;
	private final ReceveurDAO receveurDAO;

	public AssociationService() {
		donneurDAO = new DonneurDAO();
		receveurDAO = new ReceveurDAO();
	}

	public boolean associer(Long idDonneur, Long idReceveur) {
		if (idDonneur == null || idReceveur == null) {
			return false;
		}

		Donneur donneur = donneurDAO.findById(idDonneur);
		Receveur receveur = receveurDAO.findById(idReceveur);

		return (donneur != null && receveur != null);
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
