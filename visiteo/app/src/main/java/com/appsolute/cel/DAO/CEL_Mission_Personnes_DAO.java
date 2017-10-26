package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.CEL_Personnes;

public class CEL_Mission_Personnes_DAO extends CEL_Database_DAO {

    public CEL_Mission_Personnes_DAO(Context pContext) {
        super(pContext);
    }

    public static final String MISSION_PERSONNES_TABLE = "CEL_Mission_Personnes";
    public static final String MISSION_KEY = "idMission";
    public static final String PERSONNE_KEY = "idPersonne";
    public static final String TYPE = "type";

    public static final String TABLE_CREATE = "CREATE TABLE " + MISSION_PERSONNES_TABLE + " ("
            + MISSION_KEY + " INTEGER, "
            + PERSONNE_KEY + " INTEGER, "
            + TYPE + " INTEGER, "
            + "PRIMARY KEY (" + MISSION_KEY + ", " + PERSONNE_KEY + "), "
            + " FOREIGN KEY (" + MISSION_KEY + ") REFERENCES " + CEL_Mission_DAO.MISSION_TABLE + " (" + CEL_Mission_DAO.KEY + "),"
            + " FOREIGN KEY (" + PERSONNE_KEY + ") REFERENCES " + CEL_Personnes_DAO.PERSONNES_TABLES + " (" + CEL_Personnes_DAO.KEY + "));";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + MISSION_PERSONNES_TABLE + ";";


    /**
     * Insert new value on CEL_Mission_Personnes
     *
     * @param idMission
     * @param idPersonnes
     * @param type
     */
    public void addValue(int idMission, int idPersonnes, int type) {
        open();
        ContentValues value = new ContentValues();
        value.put(MISSION_KEY, idMission);
        value.put(PERSONNE_KEY, idPersonnes);
        value.put(TYPE, type);
        operaDataBase.insert(MISSION_PERSONNES_TABLE, null, value);
        close();
    }

    /**
     * Select a Locataire of a specific CEL_Mission if exist
     *
     * @param idMission
     * @return CEL_Personnes
     */
    public CEL_Personnes selectTypePersonne(int idMission, Boolean isEntrant) {
        open();
        int typeLocataire;
        if (isEntrant)
            typeLocataire = 1;
        else
            typeLocataire = 2;

        Cursor cursor = operaDataBase.rawQuery("SELECT P." + CEL_Personnes_DAO.KEY
                        + ", P." + CEL_Personnes_DAO.NOM
                        + ", P." + CEL_Personnes_DAO.PRENOM
                        + ", P." + CEL_Personnes_DAO.ADRESSE
                        + ", P." + CEL_Personnes_DAO.SUITE
                        + ", P." + CEL_Personnes_DAO.CODE_POSTAL
                        + ", P." + CEL_Personnes_DAO.VILLE
                        + ", P." + CEL_Personnes_DAO.REPRESENTANT
                        + ", P." + CEL_Personnes_DAO.EMAIL
                        + ", P." + CEL_Personnes_DAO.TELEPHONE
                        + ", P." + CEL_Personnes_DAO.DATE_ENTREE
                        + " FROM " + CEL_Personnes_DAO.PERSONNES_TABLES + " AS P JOIN "
                        + MISSION_PERSONNES_TABLE + " AS A ON"
                        + " P." + CEL_Personnes_DAO.KEY + " = A." + PERSONNE_KEY
                        + " WHERE A." + MISSION_KEY
                        + " = ? AND " + TYPE + "= ?",
                new String[]{String.valueOf(idMission), String.valueOf(typeLocataire)});

        //If we got result
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                //if (!cursor.isNull(cursor.getColumnIndex(PERSONNE_KEY))) {
                //Build CEL_Personnes object
                CEL_Personnes personnes = new CEL_Personnes();
                personnes.setIdPersonnes(Integer.parseInt(cursor.getString(0)));
                personnes.setNomPersonnes(cursor.getString(1));
                personnes.setPrenomPersonnes(cursor.getString(2));
                personnes.setAdressePersonnes(cursor.getString(3));
                personnes.setSuitePersonnes(cursor.getString(4));
                personnes.setCodePostalPersonnes(cursor.getString(5));
                personnes.setVillePersonnes(cursor.getString(6));
                personnes.setRepresentantPersonnes(cursor.getString(7));
                personnes.setEmailPersonnes(cursor.getString(8));
                personnes.setTelephonePersonnes(cursor.getString(9));
                personnes.setDateEntree(cursor.getString(10));
                personnes.setTypePersonnes(typeLocataire);

                cursor.close();
                close();
                return personnes;
                //}
            }
        }
        if (cursor != null)
            cursor.close();
        close();
        return null;
    }

    /**
     * Delete Personne
     *
     * @param idMission id of the mission
     * @param isEntrant true equal to entrant and false equal to sortant
     */
    public void deleteTypePersonne(int idMission, Boolean isEntrant) {

        int typeLocataire;
        if (isEntrant)
            typeLocataire = 1;
        else
            typeLocataire = 2;

        open();
        operaDataBase.delete(MISSION_PERSONNES_TABLE, TYPE + " = ? AND " + MISSION_KEY + " = ?"  , new String[]{String.valueOf(typeLocataire), String.valueOf(idMission)});
        if(selectTypePersonne(idMission, isEntrant) != null)
            operaDataBase.delete(CEL_Personnes_DAO.PERSONNES_TABLES, CEL_Personnes_DAO.KEY + " = ?", new String[]{String.valueOf(selectTypePersonne(idMission, isEntrant).getIdPersonnes())});
        close();
    }
}
