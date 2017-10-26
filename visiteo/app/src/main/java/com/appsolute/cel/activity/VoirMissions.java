package com.appsolute.cel.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appsolute.cel.BaseActivity;
import com.appsolute.cel.R;
import com.appsolute.cel.DAO.CEL_Biens_DAO;
import com.appsolute.cel.DAO.CEL_Mission_DAO;
import com.appsolute.cel.models.CEL_Biens;

public class VoirMissions extends BaseActivity {

    private TextView numeroRDV_TextView, dateMission_TextView, heureMission_TextView,
            personneEntrant_TextView, personneSortant_TextView, client_TextView,
            immeuble_TextView, lot_TextView, adresse_TextView, codePostal_TextView,
            ville_TextView, type_TextView, pieces_TextView, surface_TextView, Mandat_val,
            proprietaire_TextView, etage_TextView, digicode_val, suite_TextView, interieur_TextView,
            mailReceptionEdl_TextView, relocation_TextView;

    private EditText observation_EditText, info_Edit;
    RadioGroup options;

    private ImageView ville_map;

    private CEL_Mission_DAO mission_DAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mission_detail);
        baseMethod();


        if (Login.settings.getBoolean("isIndependant", false)) {
            RadioButton opt2 = (RadioButton) findViewById(R.id.opt2);
            opt2.setVisibility(View.GONE);
        }

        numeroRDV_TextView = (TextView) findViewById(R.id.numeroRDV_TextView);
        dateMission_TextView = (TextView) findViewById(R.id.dateMission_TextView);
        heureMission_TextView = (TextView) findViewById(R.id.heureMission_TextView);
        personneEntrant_TextView = (TextView) findViewById(R.id.personneEntrant_TextView);
        personneSortant_TextView = (TextView) findViewById(R.id.personneSortant_TextView);
        proprietaire_TextView = (TextView) findViewById(R.id.proprietaire_TextView);
        client_TextView = (TextView) findViewById(R.id.client_TextView);
        immeuble_TextView = (TextView) findViewById(R.id.immeuble_TextView);
        lot_TextView = (TextView) findViewById(R.id.lot_TextView);
        adresse_TextView = (TextView) findViewById(R.id.adresse_TextView);
        suite_TextView = (TextView) findViewById(R.id.suite_TextView);
        codePostal_TextView = (TextView) findViewById(R.id.codePostal_TextView);
        ville_TextView = (TextView) findViewById(R.id.ville_TextView);
        etage_TextView = (TextView) findViewById(R.id.etage_TextView);
        type_TextView = (TextView) findViewById(R.id.type_TextView);
        pieces_TextView = (TextView) findViewById(R.id.pieces_TextView);
        surface_TextView = (TextView) findViewById(R.id.surface_TextView);
        Mandat_val = (TextView) findViewById(R.id.Mandat_val);
        digicode_val = (TextView) findViewById(R.id.digicode_val);
        options = (RadioGroup) findViewById(R.id.options);
        observation_EditText = (EditText) findViewById(R.id.observation_EditText);
        info_Edit = (EditText) findViewById(R.id.info_Edit);
        ville_map = (ImageView) findViewById(R.id.ville_map);
        interieur_TextView = (TextView) findViewById(R.id.interieur_TextView);
        mailReceptionEdl_TextView = (TextView) findViewById(R.id.mailReceptionEdl_TextView);
        relocation_TextView = (TextView) findViewById(R.id.relocation_TextView);

        initializeBien();
        //initializePersonnes();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setTitle(getString(R.string.tit_ordermission));
        actionBar.setHomeButtonEnabled(true);
    }

    public void initializeBien() {
        if (MissionsList.missionSelected != null) {

            CEL_Biens_DAO biens_DAO = new CEL_Biens_DAO(this);
            CEL_Biens bien = biens_DAO.select(MissionsList.missionSelected.getIdBien());

            numeroRDV_TextView.setText(Integer.toString(MissionsList.missionSelected.getNumeroRdvMission()));
            if (MissionsList.missionSelected.getProprietaire() != null &&
                    !MissionsList.missionSelected.getProprietaire().equalsIgnoreCase("null"))
                proprietaire_TextView.setText(MissionsList.missionSelected.getProprietaire());
            if (MissionsList.missionSelected.getDateMission() != null &&
                    !MissionsList.missionSelected.getDateMission().equalsIgnoreCase("null"))
                dateMission_TextView.setText(MissionsList.missionSelected.getDateMission());
            if (MissionsList.missionSelected.getHeureMission() != null &&
                    !MissionsList.missionSelected.getHeureMission().equalsIgnoreCase("null"))
                heureMission_TextView.setText(MissionsList.missionSelected.getHeureMission());
            if (MissionsList.missionSelected.getNomClientMission() != null &&
                    !MissionsList.missionSelected.getNomClientMission().equalsIgnoreCase("null"))
                client_TextView.setText(MissionsList.missionSelected.getNomClientMission());
            if (MissionsList.missionSelected.getEntrant() != null &&
                    !MissionsList.missionSelected.getEntrant().equalsIgnoreCase("null"))
                personneEntrant_TextView.setText(MissionsList.missionSelected.getEntrant());
            if (MissionsList.missionSelected.getSortant() != null &&
                    !MissionsList.missionSelected.getSortant().equalsIgnoreCase("null"))
                personneSortant_TextView.setText(MissionsList.missionSelected.getSortant());

            if (MissionsList.missionSelected.getObservationMission() != null &&
                    !MissionsList.missionSelected.getObservationMission().equalsIgnoreCase("null"))
                observation_EditText.setText(MissionsList.missionSelected.getObservationMission());

            if (MissionsList.missionSelected.getInformationsCompteursMission() != null &&
                    !MissionsList.missionSelected.getInformationsCompteursMission().equalsIgnoreCase("null"))
                info_Edit.setText(MissionsList.missionSelected.getInformationsCompteursMission());

            if (MissionsList.missionSelected.getMailReceptionEdl() != null &&
                    !MissionsList.missionSelected.getMailReceptionEdl().equalsIgnoreCase("null"))
                mailReceptionEdl_TextView.setText(MissionsList.missionSelected.getMailReceptionEdl());

            if (MissionsList.missionSelected.getRelocationMission() != null &&
                    !MissionsList.missionSelected.getRelocationMission().equalsIgnoreCase("null"))
                relocation_TextView.setText(MissionsList.missionSelected.getRelocationMission());

            if (bien.getImmeubleBiens() != null && !bien.getImmeubleBiens().equalsIgnoreCase("null"))
                immeuble_TextView.setText(bien.getImmeubleBiens());

            if (bien.getLotBiens() != null && !bien.getLotBiens().equalsIgnoreCase("null"))
                lot_TextView.setText(bien.getLotBiens());

            if (bien.getAdresseBiens() != null && !bien.getAdresseBiens().equalsIgnoreCase("null"))
                adresse_TextView.setText(bien.getAdresseBiens());

            if (bien.getSuiteBiens() != null && !bien.getSuiteBiens().equalsIgnoreCase("null"))
                suite_TextView.setText(bien.getSuiteBiens());

            if (bien.getCodePostalBiens() != null && !bien.getCodePostalBiens().equalsIgnoreCase("null"))
                codePostal_TextView.setText(bien.getCodePostalBiens());

            if (bien.getVilleBiens() != null && !bien.getVilleBiens().equalsIgnoreCase("null"))
                ville_TextView.setText(bien.getVilleBiens());

            String[] type = getResources().getStringArray(R.array.spinnerItemsTypeBien);
            type_TextView.setText(type[bien.getTypeBiens()]);
            pieces_TextView.setText(Integer.toString(bien.getPiecesBiens()) + " " + getString(R.string.om_pieces_tv));
            if (bien.getEtageBiens() != null && !bien.getEtageBiens().equalsIgnoreCase("null"))
                etage_TextView.setText(bien.getEtageBiens());
            surface_TextView.setText(Float.toString(bien.getSurfaceBiens()) + " m2");
            if (bien.getMandatBiens() != null && !bien.getMandatBiens().equalsIgnoreCase("null"))
                Mandat_val.setText(bien.getMandatBiens());
            if (bien.getDigicodeBiens() != null && !bien.getDigicodeBiens().equalsIgnoreCase("null"))
                digicode_val.setText(bien.getDigicodeBiens());
            ville_map.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(VoirMissions.this, MapActivity.class);
                    startActivity(in);
                }
            });

            switch (MissionsList.missionSelected.getClef()) {
                case 1:
                    options.check(R.id.opt2);
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

            interieur_TextView.setText(bien.getInterieur());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mission_DAO = new CEL_Mission_DAO(this);
        mission_DAO.close();
    }


    public void callAnnuler(View v) {
        onBackPressed();
    }

    public void callValider(View v) {
        onBackPressed();
    }

}