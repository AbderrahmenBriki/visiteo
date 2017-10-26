package com.appsolute.cel.models;


public class CEL_Mission {
	
	private int idMission;
	private int numeroRdvMission;
	private String dateMission;
	private String heureMission;
	private String codeClientMission;
	private String nomClientMission;
	private String codeArticleMission;
	private String commentaireClefMission;
	private String mandataireMission;
	private String dateEntreeMission;
	private int constatAvecDescriptifMission;
	private int conformeAuxTravauxMission;
	private String fournisseurElectriciteMission;
	private int detecteurFumeeMission;
	private String relocationMission;
	private String observationMission;
	private String informationsCompteursMission;
	private int etatMission;
	private int idBien;
	private int idChauffage;
	private int idECS;
	private int idPersonneSortant;
	private int idPersonneEntrant;
	private int idPersonneProprietaire;
	private int edl_precedent;
	private int isFinished;
	private String entrant;
	private String sortant;
	private String proprietaire;
	private int clef;
	private int type_edl;
	private int isIndependant;
	private int idMissionPhotoFacade;
	private int idMissionPhotoClef;
	private int isEntrantRefusChecked;
	private int isEntrantApproveChecked;
	private int isSortantRefusChecked;
	private int isSortantApproveChecked;
	private int isOperaApproveChecked;
	private String tel_Entrant;
	private String tel_Sortant;
	private boolean isExported;
	private String refusCommentEntrant;
	private String refusCommentSortant;
	private String mail_Entrant;
	private String mail_Sortant;
	private String mailReceptionEdl;
	private String userLogin;
	private String commentExpert;

	/**
	 * Build an empty CEL_Mission
	 */
	public CEL_Mission() {
		this.codeClientMission = "411PA";
	}
	
	
	//All Getter and Setter

	public int getIdMission() {
		return idMission;
	}
	public void setIdMission(int idMission) {
		this.idMission = idMission;
	}
	public int getNumeroRdvMission() {
		return numeroRdvMission;
	}
	public void setNumeroRdvMission(int numeroRdvMission) {
		this.numeroRdvMission = numeroRdvMission;
	}
	public String getDateMission() {
		return dateMission;
	}
	public void setDateMission(String dateMission) {
		this.dateMission = dateMission;
	}	
	public String getHeureMission() {
		return heureMission;
	}
	public void setHeureMission(String heureMission) {
		this.heureMission = heureMission;
	}
	public String getCodeClientMission() {
		return codeClientMission;
	}
	public void setCodeClientMission(String codeClientMission) {
		this.codeClientMission = codeClientMission;
	}
	public String getNomClientMission() {
		return nomClientMission;
	}
	public void setNomClientMission(String nomClientMission) {
		this.nomClientMission = nomClientMission;
	}
	public String getCodeArticleMission() {
		return codeArticleMission;
	}
	public void setCodeArticleMission(String codeArticleMission) {
		this.codeArticleMission = codeArticleMission;
	}
	public String getCommentaireClefMission() {
		return commentaireClefMission;
	}
	public void setCommentaireClefMission(String commentaireClefMission) {
		this.commentaireClefMission = commentaireClefMission;
	}
	public String getMandataireMission() {
		return mandataireMission;
	}
	public void setMandataireMission(String mandataireMission) {
		this.mandataireMission = mandataireMission;
	}
	public String getDateEntreeMission() {
		return dateEntreeMission;
	}
	public void setDateEntreeMission(String dateEntreeMission) {
		this.dateEntreeMission = dateEntreeMission;
	}
	public int getConstatAvecDescriptifMission() {
		return constatAvecDescriptifMission;
	}
	public void setConstatAvecDescriptifMission(int constatAvecDescriptifMission) {
		this.constatAvecDescriptifMission = constatAvecDescriptifMission;
	}
	public int getConformeAuxTravauxMission() {
		return conformeAuxTravauxMission;
	}
	public void setConformeAuxTravauxMission(int conformeAuxTravauxMission) {
		this.conformeAuxTravauxMission = conformeAuxTravauxMission;
	}
	public String getFournisseurElectriviteMission() {
		return fournisseurElectriciteMission;
	}
	public void setFournisseurElectriviteMission(
			String fournisseurElectriviteMission) {
		this.fournisseurElectriciteMission = fournisseurElectriviteMission;
	}
	public int getDetecteurFumeeMission() {
		return detecteurFumeeMission;
	}
	public void setDetecteurFumeeMission(int detecteurFumeeMission) {
		this.detecteurFumeeMission = detecteurFumeeMission;
	}
	public String getRelocationMission() {
		return relocationMission;
	}
	public void setRelocationMission(String relocationMission) {
		this.relocationMission = relocationMission;
	}
	public String getObservationMission() {
		return observationMission;
	}
	public void setObservationMission(String observationMission) {
		this.observationMission = observationMission;
	}
	public String getInformationsCompteursMission() {
		return informationsCompteursMission;
	}
	public void setInformationsCompteursMission(String informationsCompteursMission) {
		this.informationsCompteursMission = informationsCompteursMission;
	}
	public int getEtatMission() {
		return etatMission;
	}
	public void setEtatMission(int etatMission) {
		this.etatMission = etatMission;
	}

	public int getIdBien() {
		return idBien;
	}
	public void setIdBien(int idBien) {
		this.idBien = idBien;
	}
	public int getIdChauffage() {
		return idChauffage;
	}
	public void setIdChauffage(int idChauffage) {
		this.idChauffage = idChauffage;
	}
	public int getIdECS() {
		return idECS;
	}
	public void setIdECS(int idECS) {
		this.idECS = idECS;
	}
	public int getEDL_precedent() {
		return edl_precedent;
	}
	public void setEDL_precedent(int edl_precedent) {
		this.edl_precedent = edl_precedent;
	}
	public int getEDL_isFinished() {	
		return isFinished;
	}
	public void setEDL_isFinished(int isFinished) {
		this.isFinished = isFinished;
	}
	public String getEntrant() {
		return entrant;
	}
	public void setEntrant(String entrant) {
		this.entrant = entrant;
	}
	public String getSortant() {
		return sortant;
	}
	public void setSortant(String sortant) {
		this.sortant = sortant;
	}
	public int getClef() {
		return clef;
	}
	public void setClef(int clef) {
		this.clef = clef;
	}
	public int getIdPersonneSortant() {
		return idPersonneSortant;
	}
	public void setIdPersonneSortant(int idPersonneSortant) {
		this.idPersonneSortant = idPersonneSortant;
	}
	public int getIdPersonneEntrant() {
		return idPersonneEntrant;
	}
	public void setIdPersonneEntrant(int idPersonneEntrant) {
		this.idPersonneEntrant = idPersonneEntrant;
	}
	public int getIdPersonneProprietaire() {
		return idPersonneProprietaire;
	}
	public void setIdPersonneProprietaire(int idPersonneProprietaire) {
		this.idPersonneProprietaire = idPersonneProprietaire;
	}
	public int getType_edl() {
		return type_edl;
	}
	public void setType_edl(int type_edl) {
		this.type_edl = type_edl;
	}
	public String getProprietaire() {
		return proprietaire;
	}
	public void setProprietaire(String proprietaire) {
		this.proprietaire = proprietaire;
	}
	public int getIsIndependant() {
		return isIndependant;
	}
	public void setIsIndependant(int isIndependant) {
		this.isIndependant = isIndependant;
	}

	public int getIdMissionPhotoFacade() {
		return idMissionPhotoFacade;
	}

	public void setIdMissionPhotoFacade(int idMissionPhotoFacade) {
		this.idMissionPhotoFacade = idMissionPhotoFacade;
	}

	public int getIdMissionPhotoClef() {
		return idMissionPhotoClef;
	}

	public void setIdMissionPhotoClef(int idMissionPhotoClef) {
		this.idMissionPhotoClef = idMissionPhotoClef;
	}

	public int getIsEntrantApproveChecked() {
		return isEntrantApproveChecked;
	}

	public void setIsEntrantApproveChecked(int isEntrantReadAndApproveChecked) {
		this.isEntrantApproveChecked = isEntrantReadAndApproveChecked;
	}

	public int getIsSortantApproveChecked() {
		return isSortantApproveChecked;
	}

	public void setIsSortantApproveChecked(int isSortantReadAndApproveChecked) {
		this.isSortantApproveChecked = isSortantReadAndApproveChecked;
	}

	public int getIsOperaApproveChecked() {
		return isOperaApproveChecked;
	}

	public void setIsOperaApproveChecked(int isProprietaireReadAndApproveChecked) {
		this.isOperaApproveChecked = isProprietaireReadAndApproveChecked;
	}
	public int getIsEntrantRefusChecked() {
		return isEntrantRefusChecked;
	}
	public void setIsEntrantRefusChecked(int isEntrantRefusChecked) {
		this.isEntrantRefusChecked = isEntrantRefusChecked;
	}
	public int getIsSortantRefusChecked() {
		return isSortantRefusChecked;
	}
	public void setIsSortantRefusChecked(int isSortantRefusChecked) {
		this.isSortantRefusChecked = isSortantRefusChecked;
	}

	public String getTel_Entrant() {
		return tel_Entrant;
	}

	public void setTel_Entrant(String tel_Entrant) {
		this.tel_Entrant = tel_Entrant;
	}

	public String getTel_Sortant() {
		return tel_Sortant;
	}

	public void setTel_Sortant(String tel_Sortant) {
		this.tel_Sortant = tel_Sortant;
	}

	public boolean isExported() {
		return isExported;
	}

	public void setExported(boolean exported) {
		isExported = exported;
	}

	public String getRefusCommentEntrant() {
		return refusCommentEntrant;
	}

	public void setRefusCommentEntrant(String refusCommentEntrant) {
		this.refusCommentEntrant = refusCommentEntrant;
	}

	public String getRefusCommentSortant() {
		return refusCommentSortant;
	}

	public void setRefusCommentSortant(String refusCommentSortant) {
		this.refusCommentSortant = refusCommentSortant;
	}

	public String getMail_Entrant() {
		return mail_Entrant;
	}

	public void setMail_Entrant(String mail_Entrant) {
		this.mail_Entrant = mail_Entrant;
	}

	public String getMail_Sortant() {
		return mail_Sortant;
	}

	public void setMail_Sortant(String mail_Sortant) {
		this.mail_Sortant = mail_Sortant;
	}

	public String getMailReceptionEdl() {
		return mailReceptionEdl;
	}

	public void setMailReceptionEdl(String mailReceptionEdl) {
		this.mailReceptionEdl = mailReceptionEdl;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getCommentExpert() {
		return commentExpert;
	}

	public void setCommentExpert(String commentExpert) {
		this.commentExpert = commentExpert;
	}
}
