package com.appsolute.cel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appsolute.cel.BaseActivity;
import com.appsolute.cel.DAO.CEL_Biens_DAO;
import com.appsolute.cel.DAO.CEL_Mission_DAO;
import com.appsolute.cel.DAO.CEL_Mission_Personnes_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.fragment.DatePickerFragment;
import com.appsolute.cel.models.CEL_Biens;
import com.appsolute.cel.models.CEL_Mission;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.appsolute.cel.R.id.opt2;

public class ModifyMissionActivity extends BaseActivity
        implements DatePickerFragment.DatePickerListener {

    public static final String EXTRA_DATE = "";

    private EditText heureMission_EditText,
            personneEntrant_EditText, personneSortant_EditText, client_EditText,
            immeuble_EditText, lot_EditText, adresse_EditText, suite_EditText,
            codePostal_EditText, ville_EditText, pieces_EditText, surface_EditText,
            observation_EditText, mandat_EditText, info_Edit,
            proprietaire_EditText, etage_EditText, digicode_EditText, mailReceptionEdl_EditText;

    private TextView dateMission_EditText, numeroRDV_TextView;

    private Spinner spinnerItemsTypeBien, spinnerItemsTypeMission,
            spinnerInterieur, spinnerItemsAvisReloc;
    RadioGroup options;

    private CEL_Mission_DAO mission_DAO;
    private CEL_Biens_DAO biens_DAO;
    private CEL_Biens bien;
    boolean addMission = true;
    private int year, month, day;
    private String date_for_request;
    private Calendar calendar;
    private String currentdate = "";
    private String currentHour;
    public static boolean isFromMissionList = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mission_detail_modify);
        baseMethod();


        if(Login.settings.getBoolean("isIndependant", false)) {
            RadioButton opt2 = (RadioButton) findViewById(R.id.opt2);
            opt2.setVisibility(View.GONE);
        }

        addMission = getIntent().getBooleanExtra("addMission", false);
        numeroRDV_TextView = (TextView) findViewById(R.id.numeroRDV_TextView);
        dateMission_EditText = (TextView) findViewById(R.id.dateMission_EditText);
        heureMission_EditText = (EditText) findViewById(R.id.heureMission_EditText);
        personneEntrant_EditText = (EditText) findViewById(R.id.personneEntrant_EditText);
        personneSortant_EditText = (EditText) findViewById(R.id.personneSortant_EditText);
        proprietaire_EditText = (EditText) findViewById(R.id.proprietaire_EditText);
        spinnerItemsTypeMission = (Spinner) findViewById(R.id.spinnerItemsTypeMission);
        client_EditText = (EditText) findViewById(R.id.client_EditText);
        immeuble_EditText = (EditText) findViewById(R.id.immeuble_EditText);
        lot_EditText = (EditText) findViewById(R.id.lot_EditText);
        adresse_EditText = (EditText) findViewById(R.id.adresse_EditText);
        suite_EditText = (EditText) findViewById(R.id.suite_EditText);
        codePostal_EditText = (EditText) findViewById(R.id.codePostal_EditText);
        ville_EditText = (EditText) findViewById(R.id.ville_EditText);
        etage_EditText = (EditText) findViewById(R.id.etage_EditText);
        pieces_EditText = (EditText) findViewById(R.id.pieces_EditText);
        surface_EditText = (EditText) findViewById(R.id.surface_EditText);
        observation_EditText = (EditText) findViewById(R.id.observation_EditText);
        mandat_EditText = (EditText) findViewById(R.id.mandat_EditText);
        digicode_EditText = (EditText) findViewById(R.id.digicode_EditText);
        mailReceptionEdl_EditText = (EditText) findViewById(R.id.mailReceptionEdl_EditText);
        spinnerItemsTypeBien = (Spinner) findViewById(R.id.spinnerItemsTypeBien);
        info_Edit = (EditText) findViewById(R.id.info_Edit);
        options = (RadioGroup) findViewById(R.id.options);
        spinnerInterieur = (Spinner) findViewById(R.id.spinnerInterieur);
        spinnerItemsAvisReloc = (Spinner) findViewById(R.id.spinnerItemsAvisReloc);

        proprietaire_EditText.setNextFocusDownId(R.id.client_EditText);
        digicode_EditText.setNextFocusDownId(R.id.adresse_EditText);
        codePostal_EditText.setNextFocusDownId(R.id.ville_EditText);
        etage_EditText.setNextFocusDownId(R.id.pieces_EditText);
        surface_EditText.setNextFocusDownId(R.id.info_Edit);
        heureMission_EditText.requestFocus();

        if(!Login.settings.getBoolean("isIndependant", false)) {
            dateMission_EditText.setEnabled(false);
            heureMission_EditText.setEnabled(false);
            proprietaire_EditText.setEnabled(false);
            client_EditText.setEnabled(false);
            immeuble_EditText.setEnabled(false);
            lot_EditText.setEnabled(false);
            mandat_EditText.setEnabled(false);

            RadioButton opt1 = (RadioButton) findViewById(R.id.opt1);
            opt1.setEnabled(false);

            RadioButton opt2 = (RadioButton) findViewById(R.id.opt2);
            opt2.setEnabled(false);

            RadioButton opt3 = (RadioButton) findViewById(R.id.opt3);
            opt3.setEnabled(false);

            RadioButton opt4 = (RadioButton) findViewById(R.id.opt4);
            opt4.setEnabled(false);
        }


        // Use the current date as the default date in the picker
        calendar = Calendar.getInstance(Locale.FRANCE);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dateMission_EditText.setText(new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime()));
        currentdate = dateMission_EditText.getText().toString();

        dateMission_EditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callDatePicker(v);
            }
        });

        if (!getIntent().getBooleanExtra("addMission", false))
            initializeBien();
        else {
            numeroRDV_TextView.setText(String.valueOf(((int) (new Date().getTime() / 1000))));
            currentHour = new SimpleDateFormat("HH:mm").format(calendar.getTime());
            currentHour = currentHour.replaceAll(":", "h");
            heureMission_EditText.setText(currentHour);
        }
    }

    public void callDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void returnDate(String dateString, Date date) {

        dateMission_EditText.setText(dateString);
        date_for_request = dateString;

    }

    public void initializeBien() {
        if (MissionsList.missionSelected != null) {

            biens_DAO = new CEL_Biens_DAO(this);
            bien = biens_DAO.select(MissionsList.missionSelected.getIdBien());

            numeroRDV_TextView.setText(Integer.toString(MissionsList.missionSelected.getNumeroRdvMission()));
            dateMission_EditText.setText(MissionsList.missionSelected.getDateMission());
            heureMission_EditText.setText(MissionsList.missionSelected.getHeureMission());
            client_EditText.setText(MissionsList.missionSelected.getNomClientMission());
            proprietaire_EditText.setText(MissionsList.missionSelected.getProprietaire());
            mailReceptionEdl_EditText.setText(MissionsList.missionSelected.getMailReceptionEdl());

            if (bien.getImmeubleBiens() != null && !bien.getImmeubleBiens().equalsIgnoreCase("null"))
                immeuble_EditText.setText(bien.getImmeubleBiens());

            if (bien.getLotBiens() != null && !bien.getLotBiens().equalsIgnoreCase("null"))
                lot_EditText.setText(bien.getLotBiens());

            if (bien.getAdresseBiens() != null && !bien.getAdresseBiens().equalsIgnoreCase("null"))
                adresse_EditText.setText(bien.getAdresseBiens());

            if (bien.getSuiteBiens() != null && !bien.getSuiteBiens().equalsIgnoreCase("null"))
                suite_EditText.setText(bien.getSuiteBiens());

            if (bien.getCodePostalBiens() != null && !bien.getCodePostalBiens().equalsIgnoreCase("null"))
                codePostal_EditText.setText(bien.getCodePostalBiens());

            if (bien.getVilleBiens() != null && !bien.getVilleBiens().equalsIgnoreCase("null"))
                ville_EditText.setText(bien.getVilleBiens());

            if (bien.getEtageBiens() != null && !bien.getEtageBiens().equalsIgnoreCase("null"))
                etage_EditText.setText(bien.getEtageBiens());

            spinnerItemsTypeBien.setSelection(bien.getTypeBiens());
            pieces_EditText.setText(Integer.toString(bien.getPiecesBiens()));
            surface_EditText.setText(Float.toString(bien.getSurfaceBiens()));
            if(bien.getInterieur().equals("Meublé"))
                spinnerInterieur.setSelection(1);
            else
                spinnerInterieur.setSelection(0);

            if (bien.getMandatBiens() != null && !bien.getMandatBiens().equalsIgnoreCase("null"))
                mandat_EditText.setText(bien.getMandatBiens());

            if (bien.getDigicodeBiens() != null && !bien.getDigicodeBiens().equalsIgnoreCase("null"))
                digicode_EditText.setText(bien.getDigicodeBiens());

            if (MissionsList.missionSelected.getObservationMission() != null &&
                    !MissionsList.missionSelected.getObservationMission().equalsIgnoreCase("null"))
                observation_EditText.setText(MissionsList.missionSelected.getObservationMission());

            if (MissionsList.missionSelected.getInformationsCompteursMission() != null &&
                    !MissionsList.missionSelected.getInformationsCompteursMission().equalsIgnoreCase("null"))
                info_Edit.setText(MissionsList.missionSelected.getInformationsCompteursMission());

            if (MissionsList.missionSelected.getEntrant() != null &&
                    !MissionsList.missionSelected.getEntrant().equalsIgnoreCase("null"))
                personneEntrant_EditText.setText(MissionsList.missionSelected.getEntrant());

            if (MissionsList.missionSelected.getSortant() != null &&
                    !MissionsList.missionSelected.getSortant().equalsIgnoreCase("null"))
                personneSortant_EditText.setText(MissionsList.missionSelected.getSortant());
            spinnerItemsTypeMission.setSelection(MissionsList.missionSelected.getType_edl());


            if(MissionsList.missionSelected.getRelocationMission() != null) {
                switch (MissionsList.missionSelected.getRelocationMission()) {
                    case "Bon":
                        spinnerItemsAvisReloc.setSelection(1);
                        break;
                    case "Correct":
                        spinnerItemsAvisReloc.setSelection(2);
                        break;
                    case "Difficile":
                        spinnerItemsAvisReloc.setSelection(3);
                        break;
                    default:
                        spinnerItemsAvisReloc.setSelection(0);
                        break;
                }
            }
            else
                spinnerItemsAvisReloc.setSelection(0);

            switch (MissionsList.missionSelected.getClef()) {
                case 1:
                    options.check(opt2);
                    break;
                case 2:
                    options.check(R.id.opt3);
                    break;
                case 3:
                    options.check(R.id.opt4);
                    break;
                default:
                    options.check(R.id.opt1);
                    break;
            }
        }
    }


    private void updateMission() {

        /*if(codePostal_EditText.getText().toString().length() != 5) {
            Toast.makeText(mContext, mContext.getString(R.string.error_cp), Toast.LENGTH_SHORT).show();
            return;
        }*/

        if (MissionsList.missionSelected != null) {

            MissionsList.missionSelected.setDateMission(dateMission_EditText.getText().toString());
            MissionsList.missionSelected.setHeureMission(heureMission_EditText.getText().toString());
            MissionsList.missionSelected.setNomClientMission(client_EditText.getText().toString());
            MissionsList.missionSelected.setObservationMission(observation_EditText.getText().toString());
            MissionsList.missionSelected.setInformationsCompteursMission(info_Edit.getText().toString());
            MissionsList.missionSelected.setEntrant(personneEntrant_EditText.getText().toString());
            MissionsList.missionSelected.setSortant(personneSortant_EditText.getText().toString());
            MissionsList.missionSelected.setProprietaire(proprietaire_EditText.getText().toString());
            MissionsList.missionSelected.setMailReceptionEdl(mailReceptionEdl_EditText.getText().toString());

            CEL_Mission_Personnes_DAO cel_mission_personnes_dao = new CEL_Mission_Personnes_DAO(mContext);

            int typeEDL = 0;
            switch (spinnerItemsTypeMission.getSelectedItemPosition()) {
                case 0:
                    typeEDL = 0;
                    MissionsList.missionSelected.setCodeArticleMission("4544");
                    cel_mission_personnes_dao.deleteTypePersonne(MissionsList.missionSelected.getIdMission(), false);
                    break;
                case 1:
                    typeEDL = 1;
                    MissionsList.missionSelected.setCodeArticleMission("8038");
                    cel_mission_personnes_dao.deleteTypePersonne(MissionsList.missionSelected.getIdMission(), true);
                    break;
                case 2:
                    typeEDL = 2;
                    MissionsList.missionSelected.setCodeArticleMission("8744");
                    break;
                case 3:
                    typeEDL = 3;
                    MissionsList.missionSelected.setCodeArticleMission("5274");
                    break;
            }

            switch (spinnerItemsAvisReloc.getSelectedItemPosition()) {
                case 0: MissionsList.missionSelected.setRelocationMission(null);
                    break;
                case 1 : MissionsList.missionSelected.setRelocationMission(getResources().getStringArray(R.array.array_avis_reloc)[1]);
                    break;
                case 2 : MissionsList.missionSelected.setRelocationMission(getResources().getStringArray(R.array.array_avis_reloc)[2]);
                    break;
                case 3 : MissionsList.missionSelected.setRelocationMission(getResources().getStringArray(R.array.array_avis_reloc)[3]);
                    break;
            }


            MissionsList.missionSelected.setType_edl(typeEDL);

            int radioButtonID = options.getCheckedRadioButtonId();
            View radioButton = options.findViewById(radioButtonID);
            int idx = options.indexOfChild(radioButton);
            MissionsList.missionSelected.setClef(idx);

            mission_DAO = new CEL_Mission_DAO(mContext);
            mission_DAO.updateValue(MissionsList.missionSelected);

            bien.setImmeubleBiens(immeuble_EditText.getText().toString());
            bien.setLotBiens(lot_EditText.getText().toString());
            bien.setAdresseBiens(adresse_EditText.getText().toString());
            bien.setSuiteBiens(suite_EditText.getText().toString());
            bien.setCodePostalBiens(codePostal_EditText.getText().toString());
            bien.setVilleBiens(ville_EditText.getText().toString());
            bien.setTypeBiens(spinnerItemsTypeBien.getSelectedItemPosition());
            bien.setPiecesBiens(Integer.parseInt(pieces_EditText.getText().toString()));
            bien.setSurfaceBiens(Float.parseFloat(surface_EditText.getText().toString()));
            bien.setMandatBiens(mandat_EditText.getText().toString());
            bien.setDigicodeBiens(digicode_EditText.getText().toString());
            bien.setEtageBiens(etage_EditText.getText().toString());
            if(spinnerInterieur.getSelectedItemPosition() == 0)
                bien.setInterieur("Vide");
            else
                bien.setInterieur("Meublé");

            biens_DAO.updateValue(bien);
            biens_DAO.close();
        }
    }


    public Boolean createMission() {

       /* if (numeroRDV_TextView.getText().toString().equals("")) {
            Toast.makeText(mContext, R.string.select_number_mission, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(codePostal_EditText.getText().toString().length() != 5) {
            Toast.makeText(mContext, mContext.getString(R.string.error_cp), Toast.LENGTH_SHORT).show();
            return false;
        }*/

        bien = new CEL_Biens();

        bien.setImmeubleBiens(immeuble_EditText.getText().toString());
        bien.setLotBiens(lot_EditText.getText().toString());
        bien.setAdresseBiens(adresse_EditText.getText().toString());
        bien.setSuiteBiens(suite_EditText.getText().toString());
        bien.setCodePostalBiens(codePostal_EditText.getText().toString());
        bien.setVilleBiens(ville_EditText.getText().toString());
        bien.setTypeBiens(spinnerItemsTypeBien.getSelectedItemPosition());
        bien.setEtageBiens(etage_EditText.getText().toString());
        try {
            bien.setPiecesBiens(Integer.parseInt(pieces_EditText.getText().toString()));
            bien.setSurfaceBiens(Float.parseFloat(surface_EditText.getText().toString()));
        } catch (NumberFormatException e) {
            Toast.makeText(mContext, R.string.error_number_piece_surface, Toast.LENGTH_SHORT).show();
            return false;
        }
        bien.setMandatBiens(mandat_EditText.getText().toString());
        bien.setDigicodeBiens(digicode_EditText.getText().toString());
        if(spinnerInterieur.getSelectedItemPosition() == 0)
            bien.setInterieur("Vide");
        else
            bien.setInterieur("Meublé");

        biens_DAO = new CEL_Biens_DAO(this);
        int idBienCreated = biens_DAO.addValue(bien);

        try {
            String[] parts = heureMission_EditText.getText().toString().split("h");
            String hours = parts[0];
            String minutes = parts[1];
            try {
                if ((Integer.valueOf(hours) > 24 || Integer.valueOf(hours) < 0)
                        || (Integer.valueOf(minutes) > 60 || Integer.valueOf(minutes) < 0))
                    Toast.makeText(mContext, getString(R.string.error_date_format) + currentHour, Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(mContext, getString(R.string.error_date_format) + currentHour, Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Toast.makeText(mContext, getString(R.string.error_date_format) + currentHour, Toast.LENGTH_SHORT).show();
            return false;
        }


        CEL_Mission mission = new CEL_Mission();

        mission.setNumeroRdvMission((int) Long.parseLong(numeroRDV_TextView.getText().toString()));
        mission.setIsIndependant(1);
        mission.setDateMission(dateMission_EditText.getText().toString());
        mission.setHeureMission(heureMission_EditText.getText().toString());
        mission.setNomClientMission(client_EditText.getText().toString());
        mission.setObservationMission(observation_EditText.getText().toString());
        mission.setEntrant(personneEntrant_EditText.getText().toString());
        mission.setSortant(personneSortant_EditText.getText().toString());
        mission.setProprietaire(proprietaire_EditText.getText().toString());
        mission.setMailReceptionEdl(mailReceptionEdl_EditText.getText().toString());
        mission.setIdBien(idBienCreated);

        int typeEDL = 0;
        switch (spinnerItemsTypeMission.getSelectedItemPosition()) {
            case 0:
                typeEDL = 0;
                mission.setCodeArticleMission("4544");
                break;
            case 1:
                typeEDL = 1;
                mission.setCodeArticleMission("8038");
                break;
            case 2:
                typeEDL = 2;
                mission.setCodeArticleMission("8744");
                break;
            case 3:
                typeEDL = 3;
                mission.setCodeArticleMission("5274");
                break;
        }
        mission.setType_edl(typeEDL);



        switch (spinnerItemsAvisReloc.getSelectedItemPosition()) {
            case 0: mission.setRelocationMission(null);
                break;
            case 1 : mission.setRelocationMission(getResources().getStringArray(R.array.array_avis_reloc)[1]);
                break;
            case 2 : mission.setRelocationMission(getResources().getStringArray(R.array.array_avis_reloc)[2]);
                break;
            case 3 : mission.setRelocationMission(getResources().getStringArray(R.array.array_avis_reloc)[3]);
                break;
        }

       /* if (typeEDL == 0 && personneEntrant_EditText.getText().toString().equals("")) {
            Toast.makeText(mContext, R.string.error_missing_entrant, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (typeEDL == 1 && personneSortant_EditText.getText().toString().equals("")) {
            Toast.makeText(mContext, R.string.error_missing_sortant, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (typeEDL == 2 && (personneEntrant_EditText.getText().toString().equals("")
                || personneSortant_EditText.getText().toString().equals(""))) {
            Toast.makeText(mContext, R.string.error_missing_entrant_sortant, Toast.LENGTH_SHORT).show();
            return false;
        }*/

        int radioButtonID = options.getCheckedRadioButtonId();
        View radioButton = options.findViewById(radioButtonID);
        int idx = options.indexOfChild(radioButton);
        mission.setClef(idx);

        mission_DAO = new CEL_Mission_DAO(mContext);
        mission_DAO.addValue(mission);
        mission_DAO.close();
        return true;

    }

    public void endView() {
        finish();
        if (!addMission) {
            Intent intentEtatDesLieux = new Intent(mActivity, EtatDesLieux.class);
            startActivity(intentEtatDesLieux);
        } else {
            Intent intentMissionList = new Intent(mActivity, MissionsList.class);
            intentMissionList.putExtra(EXTRA_DATE, date_for_request);
            startActivity(intentMissionList);
        }
    }

    public void callAnnuler(View v) {
        endView();
    }

    public void callValider(View v) {
        if (!addMission)
            updateMission();
        else {
            if (!createMission())
                return;
        }
        endView();
    }

}
