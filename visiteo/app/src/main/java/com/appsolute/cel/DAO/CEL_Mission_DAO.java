package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.CEL_Mission;
import com.appsolute.cel.utils.CommonMethods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CEL_Mission_DAO extends CEL_Database_DAO {

    private ArrayList<CEL_Mission> missionList;

    public CEL_Mission_DAO(Context pContext) {
        super(pContext);
    }

    public static final String MISSION_TABLE = "CEL_Mission";
    public static final String KEY = "idMission";
    public static final String NUMERO_RDV_MISSION = "numeroRdvMission";
    public static final String DATE_MISSION = "dateMission";
    public static final String HEURE_MISSION = "heureMission";
    public static final String CODE_CLIENT = "codeClientMission";
    public static final String NOM_CLIENT = "nomClientMission";
    public static final String CODE_ARTICLE_MISSION = "codeArticleMission";
    public static final String COMMENTAIRE_CLEF_MISSION = "commentaireClefMission";
    public static final String MANDATAIRE_MISSION = "mandataireMission";
    public static final String DATE_ENTREE_MISSION = "dateEntreeMission";
    public static final String CONSTAT_AVEC_DESCRIPTIF = "constatAvecDescriptifMission";
    public static final String CONFORME_AUX_TRAVAUX = "conformeAuxTravauxMission";
    public static final String FOURNISSEUR_ELECTRICITE = "fournisseurElectriviteMission";
    public static final String DETECTEUR_FUMEE = "detecteurFumeeMission";
    public static final String RELOCATION = "relocationMission";
    public static final String OBSERVATION_MISSION = "observationMission";
    public static final String ETAT_MISSION = "etatMission";
    public static final String ID_BIEN = "idBien";
    public static final String ID_CHAUFFAGE = "idChauffage";
    public static final String ID_ECS = "idECS";
    public static final String ID_PERSONNE_ENTRANT = "idPersonneEntrant";
    public static final String ID_PERSONNE_SORTANT = "idPersonneSortant";
    public static final String ID_PERSONNE_PROPRIETAIRE = "idPersonneProprietaire";
    public static final String IS_FINISHED = "isFinished";
    public static final String EDL_PRECEDENT = "edl_precedent";
    public static final String ENTRANT = "entrant";
    public static final String SORTANT = "sortant";
    public static final String PROPRIETAIRE = "proprietaire";
    public static final String CLEF = "clef";
    public static final String TYPE_EDL = "type_edl";
    public static final String IS_INDEPENDANT = "isIndependant";
    public static final String ID_PHOTO_FACADE = "idPhotoFacade";
    public static final String ID_PHOTO_CLEF = "idPhotoClef";
    public static final String ENTRANT_REFUS_CHECKED = "isEntrantRefusChecked";
    public static final String ENTRANT_APPROVE_CHECKED = "isEntrantReadAndApproveChecked";
    public static final String SORTANT_REFUS_CHECKED = "isSortantRefusChecked";
    public static final String SORTANT_APPROVE_CHECKED = "isSortantReadAndApproveChecked";
    public static final String PROPRIETAIRE_APPROVE_CHECKED = "isProprietaireReadAndApproveChecked";
    public static final String TEL_ENTRANT = "tel_entrant";
    public static final String TEL_SORTANT = "tel_sortant";
    public static final String IS_EXPORTED = "isExported";
    public static final String REFUS_COMMENT_ENTRANT = "refusCommentEntrant";
    public static final String REFUS_COMMENT_SORTANT = "refusCommentSortant";
    public static final String MAIL_ENTRANT = "mail_entrant";
    public static final String MAIL_SORTANT = "mail_sortant";
    public static final String MAIL_RECEPTION_EDL = "mailReceptionEdl";
    public static final String INFORMATION_COMPTEURS = "informationCompteurs";
    public static final String USER_LOGIN = "user_login";
    public static final String COMMENT_EXPERT = "comment_expert";

    public static final String TABLE_CREATE = "CREATE TABLE " + MISSION_TABLE + " ("
            + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL, "
            + NUMERO_RDV_MISSION + " INTEGER, "
            + DATE_MISSION + " TEXT, "
            + HEURE_MISSION + " TEXT, "
            + CODE_CLIENT + " TEXT, "
            + NOM_CLIENT + " TEXT, "
            + CODE_ARTICLE_MISSION + " TEXT, "
            + COMMENTAIRE_CLEF_MISSION + " TEXT, "
            + MANDATAIRE_MISSION + " TEXT, "
            + DATE_ENTREE_MISSION + " TEXT, "
            + CONSTAT_AVEC_DESCRIPTIF + " INTEGER, "
            + CONFORME_AUX_TRAVAUX + " INTEGER, "
            + FOURNISSEUR_ELECTRICITE + " TEXT, "
            + DETECTEUR_FUMEE + " BOOLEAN,"
            + RELOCATION + " TEXT,"
            + OBSERVATION_MISSION + " TEXT,"
            + ETAT_MISSION + " INTEGER,"
            + ID_BIEN + " INTEGER, "
            + ID_CHAUFFAGE + " INTEGER, "
            + ID_ECS + " INTEGER, "
            + IS_FINISHED + " INTEGER, "
            + EDL_PRECEDENT + " INTEGER, "
            + ENTRANT + " TEXT, "
            + SORTANT + " TEXT, "
            + PROPRIETAIRE + " TEXT, "
            + CLEF + " INTEGER, "
            + ID_PERSONNE_ENTRANT + " INTEGER, "
            + ID_PERSONNE_SORTANT + " INTEGER, "
            + ID_PERSONNE_PROPRIETAIRE + " INTEGER, "
            + TYPE_EDL + " INTEGER, "
            + IS_INDEPENDANT + " INTEGER, "
            + ID_PHOTO_FACADE + " INTEGER, "
            + ID_PHOTO_CLEF + " INTEGER, "
            + ENTRANT_REFUS_CHECKED + " INTEGER, "
            + ENTRANT_APPROVE_CHECKED + " INTEGER, "
            + SORTANT_REFUS_CHECKED + " INTEGER, "
            + SORTANT_APPROVE_CHECKED + " INTEGER, "
            + PROPRIETAIRE_APPROVE_CHECKED + " INTEGER, "
            + TEL_ENTRANT + " TEXT, "
            + TEL_SORTANT + " TEXT, "
            + IS_EXPORTED + " INTEGER, "
            + REFUS_COMMENT_ENTRANT + " TEXT, "
            + REFUS_COMMENT_SORTANT + " TEXT, "
            + MAIL_ENTRANT + " TEXT, "
            + MAIL_SORTANT + " TEXT, "
            + MAIL_RECEPTION_EDL + " TEXT, "
            + INFORMATION_COMPTEURS + " TEXT, "
            + USER_LOGIN + " TEXT, "
            + COMMENT_EXPERT + " TEXT, "
            + " FOREIGN KEY (" + ID_BIEN + ") REFERENCES " + CEL_Biens_DAO.BIENS_TABLE + " (" + CEL_Biens_DAO.KEY + "), "
            + " FOREIGN KEY (" + ID_CHAUFFAGE + ") REFERENCES " + CEL_Chauffage_DAO.CHAUFFAGE_TABLE + " (" + CEL_Chauffage_DAO.KEY + "), "
            + " FOREIGN KEY (" + ID_ECS + ") REFERENCES " + CEL_Piece_DAO.PIECES_TABLE + " (" + CEL_Piece_DAO.KEY + "), "
            + " FOREIGN KEY (" + ID_PERSONNE_ENTRANT + ") REFERENCES " + CEL_Personnes_DAO.PERSONNES_TABLES + " (" + CEL_Personnes_DAO.KEY + "), "
            + " FOREIGN KEY (" + ID_PERSONNE_SORTANT + ") REFERENCES " + CEL_Personnes_DAO.PERSONNES_TABLES + " (" + CEL_Personnes_DAO.KEY + "), "
            + " FOREIGN KEY (" + ID_PERSONNE_PROPRIETAIRE + ") REFERENCES " + CEL_Personnes_DAO.PERSONNES_TABLES + " (" + CEL_Personnes_DAO.KEY + "), "
            + " FOREIGN KEY (" + ID_PHOTO_FACADE + ") REFERENCES " + OPERA_Photos_DAO.PHOTOS_TABLE + " (" + OPERA_Photos_DAO.KEY + "), "
            + " FOREIGN KEY (" + ID_PHOTO_CLEF + ") REFERENCES " + OPERA_Photos_DAO.PHOTOS_TABLE + " (" + OPERA_Photos_DAO.KEY + "));";


    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + MISSION_TABLE + ";";


    /**
     * Insert new value on CEL_Mission
     *
     * @param mission
     */
    public int addValue(CEL_Mission mission) {
        open();
        ContentValues value = new ContentValues();
        if (mission.getIdMission() != 0)
            value.put(KEY, mission.getIdMission());
        value.put(NUMERO_RDV_MISSION, mission.getNumeroRdvMission());
        value.put(DATE_MISSION, mission.getDateMission());
        value.put(HEURE_MISSION, mission.getHeureMission());
        value.put(CODE_CLIENT, mission.getCodeClientMission());
        value.put(NOM_CLIENT, mission.getNomClientMission());
        value.put(CODE_ARTICLE_MISSION, mission.getCodeArticleMission());
        value.put(COMMENTAIRE_CLEF_MISSION, mission.getCommentaireClefMission());
        value.put(MANDATAIRE_MISSION, mission.getMandataireMission());
        value.put(DATE_ENTREE_MISSION, mission.getDateEntreeMission());
        value.put(CONSTAT_AVEC_DESCRIPTIF, mission.getConstatAvecDescriptifMission());
        value.put(CONFORME_AUX_TRAVAUX, mission.getConformeAuxTravauxMission());
        value.put(FOURNISSEUR_ELECTRICITE, mission.getFournisseurElectriviteMission());
        value.put(DETECTEUR_FUMEE, mission.getDetecteurFumeeMission());
        value.put(RELOCATION, mission.getRelocationMission());
        value.put(OBSERVATION_MISSION, mission.getObservationMission());
        value.put(ETAT_MISSION, mission.getEtatMission());
        value.put(ID_BIEN, mission.getIdBien());
        value.put(ID_CHAUFFAGE, mission.getIdChauffage());
        value.put(ID_ECS, mission.getIdECS());
        value.put(IS_FINISHED, false);
        value.put(EDL_PRECEDENT, mission.getEDL_precedent());
        value.put(ENTRANT, mission.getEntrant());
        value.put(SORTANT, mission.getSortant());
        value.put(PROPRIETAIRE, mission.getProprietaire());
        value.put(CLEF, mission.getClef());
        value.put(ID_PERSONNE_ENTRANT, mission.getIdPersonneEntrant());
        value.put(ID_PERSONNE_SORTANT, mission.getIdPersonneSortant());
        value.put(ID_PERSONNE_PROPRIETAIRE, mission.getIdPersonneProprietaire());
        value.put(TYPE_EDL, mission.getType_edl());
        value.put(IS_INDEPENDANT, mission.getIsIndependant());
        value.put(ID_PHOTO_FACADE, mission.getIdMissionPhotoFacade());
        value.put(ID_PHOTO_CLEF, mission.getIdMissionPhotoClef());
        value.put(ENTRANT_REFUS_CHECKED, mission.getIsEntrantRefusChecked());
        value.put(ENTRANT_APPROVE_CHECKED, mission.getIsEntrantApproveChecked());
        value.put(SORTANT_REFUS_CHECKED, mission.getIsSortantRefusChecked());
        value.put(SORTANT_APPROVE_CHECKED, mission.getIsSortantApproveChecked());
        value.put(PROPRIETAIRE_APPROVE_CHECKED, mission.getIsOperaApproveChecked());
        value.put(TEL_ENTRANT, mission.getTel_Entrant());
        value.put(TEL_SORTANT, mission.getTel_Sortant());
        value.put(IS_EXPORTED, mission.isExported());
        value.put(REFUS_COMMENT_ENTRANT, mission.getRefusCommentEntrant());
        value.put(REFUS_COMMENT_SORTANT, mission.getRefusCommentSortant());
        value.put(MAIL_ENTRANT, mission.getMail_Entrant());
        value.put(MAIL_SORTANT, mission.getMail_Sortant());
        value.put(MAIL_RECEPTION_EDL, mission.getMailReceptionEdl());
        value.put(INFORMATION_COMPTEURS, mission.getInformationsCompteursMission());
        value.put(USER_LOGIN, mission.getUserLogin());
        value.put(COMMENT_EXPERT, mission.getCommentExpert());
        return (int) operaDataBase.insert(MISSION_TABLE, null, value);
    }

    /**
     * Delete a Mission value from an Id
     *
     * @param idMission
     */
    public void deleteValue(int idMission) {
        open();
        operaDataBase.delete(MISSION_TABLE, KEY + " = ?", new String[]{String.valueOf(idMission)});
        close();
    }


    /**
     * Update/Modify a Mission
     *
     * @param mission
     */
    public void updateValue(CEL_Mission mission) {
        open();
        ContentValues value = new ContentValues();
        value.put(NUMERO_RDV_MISSION, mission.getNumeroRdvMission());
        value.put(DATE_MISSION, mission.getDateMission());
        value.put(HEURE_MISSION, mission.getHeureMission());
        value.put(CODE_CLIENT, mission.getCodeClientMission());
        value.put(NOM_CLIENT, mission.getNomClientMission());
        value.put(CODE_ARTICLE_MISSION, mission.getCodeArticleMission());
        value.put(COMMENTAIRE_CLEF_MISSION, mission.getCommentaireClefMission());
        value.put(MANDATAIRE_MISSION, mission.getMandataireMission());
        value.put(DATE_ENTREE_MISSION, mission.getDateEntreeMission());
        value.put(CONSTAT_AVEC_DESCRIPTIF, mission.getConstatAvecDescriptifMission());
        value.put(CONFORME_AUX_TRAVAUX, mission.getConformeAuxTravauxMission());
        value.put(FOURNISSEUR_ELECTRICITE, mission.getFournisseurElectriviteMission());
        value.put(DETECTEUR_FUMEE, mission.getDetecteurFumeeMission());
        value.put(RELOCATION, mission.getRelocationMission());
        value.put(OBSERVATION_MISSION, mission.getObservationMission());
        value.put(ETAT_MISSION, mission.getEtatMission());
        value.put(ID_BIEN, mission.getIdBien());
        value.put(ID_CHAUFFAGE, mission.getIdChauffage());
        value.put(ID_ECS, mission.getIdECS());
        value.put(IS_FINISHED, false);
        value.put(EDL_PRECEDENT, mission.getEDL_precedent());
        value.put(ENTRANT, mission.getEntrant());
        value.put(SORTANT, mission.getSortant());
        value.put(PROPRIETAIRE, mission.getProprietaire());
        value.put(CLEF, mission.getClef());
        value.put(ID_PERSONNE_ENTRANT, mission.getIdPersonneEntrant());
        value.put(ID_PERSONNE_SORTANT, mission.getIdPersonneSortant());
        value.put(ID_PERSONNE_PROPRIETAIRE, mission.getIdPersonneProprietaire());
        value.put(TYPE_EDL, mission.getType_edl());
        value.put(IS_INDEPENDANT, mission.getIsIndependant());
        value.put(ID_PHOTO_FACADE, mission.getIdMissionPhotoFacade());
        value.put(ID_PHOTO_CLEF, mission.getIdMissionPhotoClef());
        value.put(ENTRANT_REFUS_CHECKED, mission.getIsEntrantRefusChecked());
        value.put(ENTRANT_APPROVE_CHECKED, mission.getIsEntrantApproveChecked());
        value.put(SORTANT_REFUS_CHECKED, mission.getIsSortantRefusChecked());
        value.put(SORTANT_APPROVE_CHECKED, mission.getIsSortantApproveChecked());
        value.put(PROPRIETAIRE_APPROVE_CHECKED, mission.getIsOperaApproveChecked());
        value.put(TEL_ENTRANT, mission.getTel_Entrant());
        value.put(TEL_SORTANT, mission.getTel_Sortant());
        value.put(IS_EXPORTED, mission.isExported());
        value.put(REFUS_COMMENT_ENTRANT, mission.getRefusCommentEntrant());
        value.put(REFUS_COMMENT_SORTANT, mission.getRefusCommentSortant());
        value.put(MAIL_ENTRANT, mission.getMail_Entrant());
        value.put(MAIL_SORTANT, mission.getMail_Sortant());
        value.put(MAIL_RECEPTION_EDL, mission.getMailReceptionEdl());
        value.put(INFORMATION_COMPTEURS, mission.getInformationsCompteursMission());
        value.put(USER_LOGIN, mission.getUserLogin());
        value.put(COMMENT_EXPERT, mission.getCommentExpert());
        operaDataBase.update(MISSION_TABLE, value, KEY + " = ?",
                new String[]{String.valueOf(mission.getIdMission())});
        close();
    }

    /**
     * Select a specific Mission
     *
     * @param idMission
     */
    public CEL_Mission select(int idMission) {
        open();
        CEL_Mission mission = null;
        Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + MISSION_TABLE + " WHERE " + KEY + " = ?", new String[]
                {String.valueOf(idMission)});

        //If we got results get the first one
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
                    //Build mission object
                    mission = new CEL_Mission();
                    mission.setIdMission(Integer.parseInt(cursor.getString(0)));
                    mission.setNumeroRdvMission(Integer.parseInt(cursor.getString(1)));
                    mission.setDateMission(cursor.getString(2));
                    mission.setHeureMission(cursor.getString(3));
                    mission.setCodeClientMission(cursor.getString(4));
                    mission.setNomClientMission(cursor.getString(5));
                    mission.setCodeArticleMission(cursor.getString(6));
                    mission.setCommentaireClefMission(cursor.getString(7));
                    mission.setMandataireMission(cursor.getString(8));
                    mission.setDateEntreeMission(cursor.getString(9));
                    mission.setConstatAvecDescriptifMission(Integer.parseInt(cursor.getString(10)));
                    mission.setConformeAuxTravauxMission(Integer.parseInt(cursor.getString(11)));
                    mission.setFournisseurElectriviteMission(cursor.getString(12));
                    mission.setDetecteurFumeeMission(Integer.parseInt(cursor.getString(13)));
                    mission.setRelocationMission(cursor.getString(14));
                    mission.setObservationMission(cursor.getString(15));
                    mission.setEtatMission(Integer.parseInt(cursor.getString(16)));
                    mission.setIdBien(Integer.parseInt(cursor.getString(17)));
                    mission.setIdChauffage(Integer.parseInt(cursor.getString(18)));
                    mission.setIdECS(Integer.parseInt(cursor.getString(19)));
                    mission.setEDL_isFinished(Integer.parseInt(cursor.getString(20)));
                    mission.setEDL_precedent(Integer.parseInt(cursor.getString(21)));
                    mission.setEntrant(cursor.getString(22));
                    mission.setSortant(cursor.getString(23));
                    mission.setProprietaire(cursor.getString(24));
                    mission.setClef(Integer.parseInt(cursor.getString(25)));
                    mission.setIdPersonneEntrant(Integer.parseInt(cursor.getString(26)));
                    mission.setIdPersonneSortant(Integer.parseInt(cursor.getString(27)));
                    mission.setIdPersonneProprietaire(Integer.parseInt(cursor.getString(28)));
                    mission.setType_edl(Integer.parseInt(cursor.getString(29)));
                    mission.setIsIndependant(Integer.parseInt(cursor.getString(30)));
                    mission.setIdMissionPhotoFacade(Integer.parseInt(cursor.getString(31)));
                    mission.setIdMissionPhotoClef(Integer.parseInt(cursor.getString(32)));
                    mission.setIsEntrantRefusChecked(Integer.parseInt(cursor.getString(33)));
                    mission.setIsEntrantApproveChecked(Integer.parseInt(cursor.getString(34)));
                    mission.setIsSortantRefusChecked(Integer.parseInt(cursor.getString(35)));
                    mission.setIsSortantApproveChecked(Integer.parseInt(cursor.getString(36)));
                    mission.setIsOperaApproveChecked(Integer.parseInt(cursor.getString(37)));
                    mission.setTel_Entrant(cursor.getString(38));
                    mission.setTel_Sortant(cursor.getString(39));
                    mission.setExported(CommonMethods.getBoolean(cursor.getInt(40)));
                    mission.setRefusCommentEntrant(cursor.getString(41));
                    mission.setRefusCommentSortant(cursor.getString(42));
                    mission.setMail_Entrant(cursor.getString(43));
                    mission.setMail_Sortant(cursor.getString(44));
                    mission.setMailReceptionEdl(cursor.getString(45));
                    mission.setInformationsCompteursMission(cursor.getString(46));
                    mission.setUserLogin(cursor.getString(47));
                    mission.setCommentExpert(cursor.getString(48));
                }
            }
        }
        if (cursor != null)
            cursor.close();
        close();
        return mission;
    }

    /**
     * Check if mission already exist on database
     * <p>
     * return true if already exist
     *
     * @param numeroRdvMission
     * @return
     */
    public boolean checkMission(int numeroRdvMission) {
        open();
        Cursor cursor = operaDataBase.rawQuery("SELECT COUNT(*) FROM " + MISSION_TABLE + " WHERE " + NUMERO_RDV_MISSION + " = ?", new String[]
                {String.valueOf(numeroRdvMission)});
        //If we got results get the first one
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getInt(0) > 0) {
                cursor.close();
                return true;
            }
        }
        close();
        return false;
    }

    /**
     * Check if mission already exist on database
     * <p>
     * return true if already exist
     *
     * @param numeroRdvMission
     * @return
     */
    public int getIdMissionFromNumeroRDV(int numeroRdvMission) {
        open();
        Cursor cursor = operaDataBase.rawQuery("SELECT " + KEY + " FROM " + MISSION_TABLE + " WHERE " + NUMERO_RDV_MISSION + " = ?", new String[]
                {String.valueOf(numeroRdvMission)});
        //If we got results get the first one
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0)
                return cursor.getInt(0);
        }
        close();
        return 0;
    }


    /**
     * Get all missions
     *
     * @param dateMission   date wanted
     * @param isIndependant if user is an independant or not
     * @param userLogin     login of the connected user
     * @return List of missions
     */
    public ArrayList<CEL_Mission> selectAllDateCEL_Mission(String dateMission, int isIndependant,
                                                           String userLogin) {
        open();
        missionList = new ArrayList<>();

        Cursor cursor;
        if (isIndependant == 0) {

            cursor = operaDataBase.rawQuery("SELECT * FROM " + MISSION_TABLE
                            + " WHERE " + DATE_MISSION + " = '" + dateMission
                            + "' AND " + IS_INDEPENDANT + " =?"
                            + " AND " + USER_LOGIN + " =?"
                            + " ORDER BY " + HEURE_MISSION + " ASC",
                    new String[]{String.valueOf(isIndependant), userLogin});
        }
        else {

            cursor = operaDataBase.rawQuery("SELECT * FROM " + MISSION_TABLE
                            + " WHERE " + DATE_MISSION + " = '" + dateMission
                            + "' AND " + IS_INDEPENDANT + " =?"
                            + " ORDER BY " + HEURE_MISSION + " ASC",
                    new String[]{String.valueOf(isIndependant)});

        }

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    CEL_Mission mission = new CEL_Mission();
                    mission.setIdMission(Integer.parseInt(cursor.getString(0)));
                    mission.setNumeroRdvMission(Integer.parseInt(cursor.getString(1)));
                    mission.setDateMission(cursor.getString(2));
                    mission.setHeureMission(cursor.getString(3));
                    mission.setCodeClientMission(cursor.getString(4));
                    mission.setNomClientMission(cursor.getString(5));
                    mission.setCodeArticleMission(cursor.getString(6));
                    mission.setCommentaireClefMission(cursor.getString(7));
                    mission.setMandataireMission(cursor.getString(8));
                    mission.setDateEntreeMission(cursor.getString(9));
                    mission.setConstatAvecDescriptifMission(Integer.parseInt(cursor.getString(10)));
                    mission.setConformeAuxTravauxMission(Integer.parseInt(cursor.getString(11)));
                    mission.setFournisseurElectriviteMission(cursor.getString(12));
                    mission.setDetecteurFumeeMission(Integer.parseInt(cursor.getString(13)));
                    mission.setRelocationMission(cursor.getString(14));
                    mission.setObservationMission(cursor.getString(15));
                    mission.setEtatMission(Integer.parseInt(cursor.getString(16)));
                    mission.setIdBien(Integer.parseInt(cursor.getString(17)));
                    mission.setIdChauffage(Integer.parseInt(cursor.getString(18)));
                    mission.setIdECS(Integer.parseInt(cursor.getString(19)));
                    mission.setEDL_isFinished(Integer.parseInt(cursor.getString(20)));
                    mission.setEDL_precedent(Integer.parseInt(cursor.getString(21)));
                    mission.setEntrant(cursor.getString(22));
                    mission.setSortant(cursor.getString(23));
                    mission.setProprietaire(cursor.getString(24));
                    mission.setClef(Integer.parseInt(cursor.getString(25)));
                    mission.setIdPersonneEntrant(Integer.parseInt(cursor.getString(26)));
                    mission.setIdPersonneSortant(Integer.parseInt(cursor.getString(27)));
                    mission.setIdPersonneProprietaire(Integer.parseInt(cursor.getString(28)));
                    mission.setType_edl(Integer.parseInt(cursor.getString(29)));
                    mission.setIsIndependant(Integer.parseInt(cursor.getString(30)));
                    mission.setIdMissionPhotoFacade(Integer.parseInt(cursor.getString(31)));
                    mission.setIdMissionPhotoClef(Integer.parseInt(cursor.getString(32)));
                    mission.setIsEntrantRefusChecked(Integer.parseInt(cursor.getString(33)));
                    mission.setIsEntrantApproveChecked(Integer.parseInt(cursor.getString(34)));
                    mission.setIsSortantRefusChecked(Integer.parseInt(cursor.getString(35)));
                    mission.setIsSortantApproveChecked(Integer.parseInt(cursor.getString(36)));
                    mission.setIsOperaApproveChecked(Integer.parseInt(cursor.getString(37)));
                    mission.setTel_Entrant(cursor.getString(38));
                    mission.setTel_Sortant(cursor.getString(39));
                    mission.setExported(CommonMethods.getBoolean(cursor.getInt(40)));
                    mission.setRefusCommentEntrant(cursor.getString(41));
                    mission.setRefusCommentSortant(cursor.getString(42));
                    mission.setMail_Entrant(cursor.getString(43));
                    mission.setMail_Sortant(cursor.getString(44));
                    mission.setMailReceptionEdl(cursor.getString(45));
                    mission.setInformationsCompteursMission(cursor.getString(46));
                    mission.setUserLogin(cursor.getString(47));
                    mission.setCommentExpert(cursor.getString(48));
                    missionList.add(mission);
                    cursor.moveToNext();
                }
            }
        }
        if (cursor != null)
            cursor.close();
        close();
        return missionList;
    }

    /**
     * Return all CEL_Mission contains with etat != 0
     *
     * @return ArrayList<CEL_Mission>
     */
    public ArrayList<Integer> selectAllCEL_MissionNotStarted() {
        open();
        ArrayList<Integer> listIdMission = new ArrayList<>();
        Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + MISSION_TABLE
                + " WHERE " + ETAT_MISSION + " = 0"
                + " AND " + DATE_MISSION + " ='"
                + new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(Calendar.getInstance(Locale.FRANCE).getTime())
                + "' ORDER BY " + HEURE_MISSION + " ASC", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    listIdMission.add(Integer.parseInt(cursor.getString(1)));
                    cursor.moveToNext();
                }
            }
        }
        if (cursor != null)
            cursor.close();
        close();
        if (listIdMission.size() == 0)
            return null;
        else
            return listIdMission;
    }

    /**
     * Delete mission not started, if they exist in database but not in the list
     *
     * @param idMissionList idMission who has been added or updated after being parsed
     *                      in Json response during connection
     * @param dateMission   dateMission selected
     */
    public void deleteMissionFromIdList(List<Integer> idMissionList, String dateMission) {
        open();
        Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + MISSION_TABLE
                + " WHERE " + DATE_MISSION + " = '" + dateMission
                + "' AND " + ETAT_MISSION + " = 0", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    int idMission = cursor.getInt(0);
                    //Mission who have been previously imported without being started, and are not in the new import, must be deleted
                    if (!idMissionList.contains(idMission)) {
                        operaDataBase.delete(MISSION_TABLE, KEY + " = ?", new String[]{String.valueOf(idMission)});
                    }
                    cursor.moveToNext();
                }
            }
        }

        if (cursor != null)
            cursor.close();
        close();
    }


    public boolean isLogin(String login) {
        open();
        Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + MISSION_TABLE
                + " WHERE " + USER_LOGIN + " = ?", new String[]{login});

        if (cursor != null)
            if (cursor.moveToFirst()) {
                cursor.close();
                return true;
            } else
                return false;
        else
            return false;

    }


    /**
     * Delete all missions not started, if they exist in database but not in the list
     */
    public void deleteMissions() {
        open();
        Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + MISSION_TABLE, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    int idMission = cursor.getInt(0);
                    operaDataBase.delete(MISSION_TABLE, KEY + " = ?", new String[]{String.valueOf(idMission)});
                    cursor.moveToNext();
                }
            }
        }

        if (cursor != null)
            cursor.close();
        close();
    }

    public boolean isMissionInDatabaseAndStarted(int rdvMission) {
        open();
        Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + MISSION_TABLE
                + " WHERE " + NUMERO_RDV_MISSION + " = " + rdvMission
                + " AND " + ETAT_MISSION + " != 0", null);

        if (cursor != null)
            if (cursor.moveToFirst()) {
                cursor.close();
                return true;
            } else
                return false;
        else
            return false;

    }
}