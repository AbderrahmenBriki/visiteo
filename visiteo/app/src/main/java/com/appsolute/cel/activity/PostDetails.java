package com.appsolute.cel.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsolute.cel.BaseActivity;
import com.appsolute.cel.DAO.CEL_Actions_DAO;
import com.appsolute.cel.DAO.CEL_Biens_DAO;
import com.appsolute.cel.DAO.CEL_Chauffage_DAO;
import com.appsolute.cel.DAO.CEL_Clefs_DAO;
import com.appsolute.cel.DAO.CEL_Compteurs_DAO;
import com.appsolute.cel.DAO.CEL_ECS_DAO;
import com.appsolute.cel.DAO.CEL_Elements_DAO;
import com.appsolute.cel.DAO.CEL_Elements_Obs_DAO;
import com.appsolute.cel.DAO.CEL_Mission_DAO;
import com.appsolute.cel.DAO.CEL_Mission_Personnes_DAO;
import com.appsolute.cel.DAO.CEL_Personnes_DAO;
import com.appsolute.cel.DAO.CEL_Piece_DAO;
import com.appsolute.cel.DAO.OPERA_Photos_DAO;
import com.appsolute.cel.DAO.Signature_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.SignatureView;
import com.appsolute.cel.models.CEL_Actions;
import com.appsolute.cel.models.CEL_Biens;
import com.appsolute.cel.models.CEL_Chauffage;
import com.appsolute.cel.models.CEL_Clefs;
import com.appsolute.cel.models.CEL_Compteurs;
import com.appsolute.cel.models.CEL_ECS;
import com.appsolute.cel.models.CEL_Elements;
import com.appsolute.cel.models.CEL_Elements_Obs;
import com.appsolute.cel.models.CEL_Mission;
import com.appsolute.cel.models.CEL_Personnes;
import com.appsolute.cel.models.CEL_Piece;
import com.appsolute.cel.models.OPERA_Photos;
import com.appsolute.cel.models.SignatureItem;
import com.appsolute.cel.utils.InternetConnection;
import com.appsolute.cel.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.appsolute.cel.activity.Login.sendPhotosToFTP;
import static com.appsolute.cel.activity.Login.statusCode;

public class PostDetails extends BaseActivity {
    LinearLayout entrantLayout, sortantLayout, operaLayout;
    SignatureView entrantSign, sortantSign, operaSign;
    RelativeLayout entrant, sortant;
    TextView entrantClear, sortantClear, operaClear, mGetSign;
    TextView title_entrant, title_sortant;
    public static String tempDir;
    public String current = null;
    private Bitmap mBitmap;
    View entrantView, sortantView, operaView;
    File mypath;
    private String uniqueId;
    ImageView entrantview, sortantview, operaview;
    JSONObject data = new JSONObject();
    String date_for_request = "";
    CEL_Mission missions;

    TextView nomval, adresseval, rdvval, cpval, dateval, digicodeval, heureval, etageval, expertval, typeval, proprietaireval,
            nomPrenom_entrant_TextView, nomPrenom_sortant_TextView, date_sortant_TextView, adresse_sortant_TextView,
            cpVille_sortant_TextView, mail_entrant, mail_sortant, date_entrant_TextView, adresse_entrant_TextView, cpVille_entrant_TextView,
            gazval, jourval, chaudeval, nuitval, froideval, fournisseurval, chaudeval1, froideval1, modeval, chaudiereval,
            sanitaireval, certificatval, valider_entrant, valider_sortant, valider_opera, detecteur_present_val, detecteur_nb_val,
            tab_representant_entrant, tab_representant_sortant, immeuble_TextView, lot_TextView, mandat_TextView, ecsType_TextView,
            ecsMarque_TextView, comment_entrant, comment_sortant, comment_opera;
    LinearLayout dynamiclistitems, dynamicitemscles;
    LayoutInflater inflater, inflatercles;
    Signature_DAO signature_DAO;
    ArrayList<SignatureItem> signatureitem = new ArrayList<>();
    boolean entrantflag = false, sortantflag = false, operaflag = false;
    boolean entrantValider = false, sortantValider = false, operaValider = false;
    CheckBox check_approuve_entrant, check_approuve_sortant,
            check_approuve_opera, checkBox_refusEntrant, checkBox_refusSortant;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postdetails);

        baseMethod();
        Utils.exportDatabaseFileTask(mContext);

        showProgress(mContext, getString(R.string.loading));
        signature_DAO = new Signature_DAO(mContext);

        date_for_request = getIntent().getStringExtra("date_for_request");
        File directory = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + mContext.getPackageName() + "/cache");
        if (!directory.exists())
            directory.mkdirs();

        prepareDirectory();
        uniqueId = Utils.getTodaysDate() + "_" + Utils.getCurrentTime() + "_" + Math.random();
        current = uniqueId + ".png";
        mypath = new File(directory, current);

        dynamiclistitems = (LinearLayout) findViewById(R.id.dynamiclistitems);
        dynamicitemscles = (LinearLayout) findViewById(R.id.dynamicitemscles);
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflatercles = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        title_entrant = (TextView) findViewById(R.id.title_entrant);
        title_sortant = (TextView) findViewById(R.id.title_sortant);
        valider_entrant = (TextView) findViewById(R.id.valider_entrant);
        valider_sortant = (TextView) findViewById(R.id.valider_sortant);
        valider_opera = (TextView) findViewById(R.id.valider_opera);

        mGetSign = (TextView) findViewById(R.id.valider);
        nomval = (TextView) findViewById(R.id.nomval);
        adresseval = (TextView) findViewById(R.id.adresseval);
        rdvval = (TextView) findViewById(R.id.rdvval);
        cpval = (TextView) findViewById(R.id.cpval);
        dateval = (TextView) findViewById(R.id.dateval);
        digicodeval = (TextView) findViewById(R.id.digicodeval);
        heureval = (TextView) findViewById(R.id.heureval);
        etageval = (TextView) findViewById(R.id.etageval);
        expertval = (TextView) findViewById(R.id.expertval);
        typeval = (TextView) findViewById(R.id.typeval);
        proprietaireval = (TextView) findViewById(R.id.proprietaireval);
        mail_entrant = (TextView) findViewById(R.id.tab_mail_entrant);
        mail_sortant = (TextView) findViewById(R.id.tab_mail_sortant);
        immeuble_TextView = (TextView) findViewById(R.id.immeuble_TextView);
        lot_TextView = (TextView) findViewById(R.id.lot_TextView);
        mandat_TextView = (TextView) findViewById(R.id.mandat_TextView);
        ecsType_TextView = (TextView) findViewById(R.id.ecsType_TextView);
        ecsMarque_TextView = (TextView) findViewById(R.id.ecsMarque_TextView);

        nomPrenom_entrant_TextView = (TextView) findViewById(R.id.nomPrenom_entrant_TextView);
        nomPrenom_sortant_TextView = (TextView) findViewById(R.id.nomPrenom_sortant_TextView);
        date_entrant_TextView = (TextView) findViewById(R.id.date_entrant_TextView);
        date_sortant_TextView = (TextView) findViewById(R.id.date_sortant_TextView);
        adresse_entrant_TextView = (TextView) findViewById(R.id.adresse_entrant_TextView);
        adresse_sortant_TextView = (TextView) findViewById(R.id.adresse_sortant_TextView);
        cpVille_entrant_TextView = (TextView) findViewById(R.id.cpVille_entrant_TextView);
        cpVille_sortant_TextView = (TextView) findViewById(R.id.cpVille_sortant_TextView);
        tab_representant_entrant = (TextView) findViewById(R.id.tab_representant_entrant);
        tab_representant_sortant = (TextView) findViewById(R.id.tab_representant_sortant);
        gazval = (TextView) findViewById(R.id.gazval);
        jourval = (TextView) findViewById(R.id.jourval);
        chaudeval = (TextView) findViewById(R.id.chaudeval);
        nuitval = (TextView) findViewById(R.id.nuitval);
        froideval = (TextView) findViewById(R.id.froideval);
        fournisseurval = (TextView) findViewById(R.id.fournisseurval);
        chaudeval1 = (TextView) findViewById(R.id.chaudeval1);
        froideval1 = (TextView) findViewById(R.id.froideval1);
        modeval = (TextView) findViewById(R.id.modeval);
        chaudiereval = (TextView) findViewById(R.id.chaudiereval);
        sanitaireval = (TextView) findViewById(R.id.sanitaireval);
        certificatval = (TextView) findViewById(R.id.certificatval);
        detecteur_present_val = (TextView) findViewById(R.id.post_detecteur_present_val);
        detecteur_nb_val = (TextView) findViewById(R.id.post_detecteur_nb_val);

        entrantClear = (TextView) findViewById(R.id.clear_entrant);
        sortantClear = (TextView) findViewById(R.id.clear_sortant);
        operaClear = (TextView) findViewById(R.id.clear_opera);

        comment_entrant = (TextView) findViewById(R.id.comment_entrant);
        comment_sortant = (TextView) findViewById(R.id.comment_sortant);
        comment_opera = (TextView) findViewById(R.id.comment_opera);

        entrantview = (ImageView) findViewById(R.id.entrantview);
        sortantview = (ImageView) findViewById(R.id.sortantview);
        operaview = (ImageView) findViewById(R.id.operaview);

        entrantLayout = (LinearLayout) findViewById(R.id.entrantLayout);
        sortantLayout = (LinearLayout) findViewById(R.id.sortantLayout);
        operaLayout = (LinearLayout) findViewById(R.id.operaLayout);

        check_approuve_entrant = (CheckBox) findViewById(R.id.check_approuve_entrant);
        check_approuve_sortant = (CheckBox) findViewById(R.id.check_approuve_sortant);
        check_approuve_opera = (CheckBox) findViewById(R.id.check_approuve_opera);
        checkBox_refusEntrant = (CheckBox) findViewById(R.id.checkBox_refusEntrant);
        checkBox_refusSortant = (CheckBox) findViewById(R.id.checkBox_refusSortant);

        entrant = (RelativeLayout) findViewById(R.id.entrant);
        sortant = (RelativeLayout) findViewById(R.id.sortant);

        entrantSign = new SignatureView(this, null);
        sortantSign = new SignatureView(this, null);
        operaSign = new SignatureView(this, null);

        entrantLayout.addView(entrantSign, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        sortantLayout.addView(sortantSign, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        operaLayout.addView(operaSign, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        entrantView = entrantSign;
        sortantView = sortantSign;
        operaView = operaSign;

        switch (MissionsList.missionSelected.getType_edl()) {
            case 0:
                valider_entrant.setVisibility(View.VISIBLE);
                entrantClear.setVisibility(View.VISIBLE);
                title_entrant.setVisibility(View.VISIBLE);
                checkBox_refusEntrant.setVisibility(View.VISIBLE);
                check_approuve_entrant.setVisibility(View.VISIBLE);
                entrant.setVisibility(View.VISIBLE);
                comment_entrant.setVisibility(View.VISIBLE);

                valider_sortant.setVisibility(View.GONE);
                sortantClear.setVisibility(View.GONE);
                title_sortant.setVisibility(View.GONE);
                checkBox_refusSortant.setVisibility(View.GONE);
                check_approuve_sortant.setVisibility(View.GONE);
                sortant.setVisibility(View.GONE);
                comment_sortant.setVisibility(View.GONE);
                break;

            case 1:
                valider_entrant.setVisibility(View.GONE);
                entrantClear.setVisibility(View.GONE);
                title_entrant.setVisibility(View.GONE);
                checkBox_refusEntrant.setVisibility(View.GONE);
                check_approuve_entrant.setVisibility(View.GONE);
                entrant.setVisibility(View.GONE);
                comment_entrant.setVisibility(View.GONE);

                valider_sortant.setVisibility(View.VISIBLE);
                sortantClear.setVisibility(View.VISIBLE);
                title_sortant.setVisibility(View.VISIBLE);
                checkBox_refusSortant.setVisibility(View.VISIBLE);
                check_approuve_sortant.setVisibility(View.VISIBLE);
                sortant.setVisibility(View.VISIBLE);
                comment_sortant.setVisibility(View.VISIBLE);
                break;

            default:
                valider_entrant.setVisibility(View.VISIBLE);
                entrantClear.setVisibility(View.VISIBLE);
                title_entrant.setVisibility(View.VISIBLE);
                checkBox_refusEntrant.setVisibility(View.VISIBLE);
                check_approuve_entrant.setVisibility(View.VISIBLE);
                entrant.setVisibility(View.VISIBLE);
                comment_entrant.setVisibility(View.VISIBLE);

                valider_sortant.setVisibility(View.VISIBLE);
                sortantClear.setVisibility(View.VISIBLE);
                title_sortant.setVisibility(View.VISIBLE);
                checkBox_refusSortant.setVisibility(View.VISIBLE);
                check_approuve_sortant.setVisibility(View.VISIBLE);
                sortant.setVisibility(View.VISIBLE);
                comment_sortant.setVisibility(View.VISIBLE);
                break;
        }

        entrantClear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (entrantflag && !entrantValider && !checkBox_refusEntrant.isChecked()) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setMessage(getString(R.string.sing_remove));
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            entrantSign.clearSignature();
                            entrantLayout.setVisibility(View.VISIBLE);
                            entrantview.setVisibility(View.GONE);
                            entrantflag = false;
                            entrantValider = false;
                        }
                    });
                    alertDialogBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        sortantClear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (sortantflag && !sortantValider && !checkBox_refusSortant.isChecked()) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setMessage(getString(R.string.sing_remove));
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sortantSign.clearSignature();
                            sortantLayout.setVisibility(View.VISIBLE);
                            sortantview.setVisibility(View.GONE);
                            sortantflag = false;
                            sortantValider = false;
                        }
                    });
                    alertDialogBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        operaClear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (operaflag && !operaValider) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setMessage(getString(R.string.sing_remove));
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Log.v("log_tag", "Panel Cleared");
                            operaSign.clearSignature();
                            operaLayout.setVisibility(View.VISIBLE);
                            operaview.setVisibility(View.GONE);
                            operaflag = false;
                            operaValider = false;
                        }
                    });
                    alertDialogBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        entrantSign.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                entrantflag = true;
                return false;
            }
        });

        sortantSign.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                sortantflag = true;
                return false;
            }
        });

        operaSign.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                operaflag = true;
                return false;
            }
        });
        valider_entrant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entrantflag) {
                    Bitmap img = setSignatureImg("entrant");
                    entrantview.setImageBitmap(img);
                    entrantview.setVisibility(View.VISIBLE);
                    entrantLayout.setVisibility(View.INVISIBLE);
                    entrantValider = true;
                    Toast.makeText(mContext, getString(R.string.sing_valider), Toast.LENGTH_SHORT).show();
                }
            }
        });
        valider_sortant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortantflag) {
                    Bitmap img = setSignatureImg("sortant");
                    sortantview.setImageBitmap(img);
                    sortantview.setVisibility(View.VISIBLE);
                    sortantLayout.setVisibility(View.INVISIBLE);
                    sortantValider = true;
                    Toast.makeText(mContext, getString(R.string.sing_valider), Toast.LENGTH_SHORT).show();
                }
            }
        });
        valider_opera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operaflag) {
                    Bitmap img = setSignatureImg("opera");
                    operaview.setImageBitmap(img);
                    operaview.setVisibility(View.VISIBLE);
                    operaLayout.setVisibility(View.INVISIBLE);
                    operaValider = true;
                    Toast.makeText(mContext, getString(R.string.sing_valider), Toast.LENGTH_SHORT).show();
                }
            }
        });

        comment_entrant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogCommentRefus(0);
            }
        });

        comment_sortant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogCommentRefus(1);
            }
        });

        comment_opera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogCommentRefus(2);
            }
        });


        mGetSign.setClickable(false);
        mGetSign.setEnabled(false);
        getAllMissionDetails();
        closeProgress();
        setEntrantSortantCheckboxListener();
    }

    public void callValider(View v) {

        Boolean signatureMissing = false;

        switch (MissionsList.missionSelected.getType_edl()) {
            case 0:
                if (!((entrantValider || checkBox_refusEntrant.isChecked()) || check_approuve_entrant.isChecked()) || !operaValider || !check_approuve_opera.isChecked())
                    signatureMissing = true;
                break;
            case 1:
                if (!((sortantValider || checkBox_refusSortant.isChecked()) || check_approuve_sortant.isChecked()) || !operaValider || !check_approuve_opera.isChecked())
                    signatureMissing = true;
                break;
            default:
                if (!((entrantValider || checkBox_refusEntrant.isChecked()) || !check_approuve_entrant.isChecked()) || !operaValider || !check_approuve_opera.isChecked()
                        || !((sortantValider || checkBox_refusSortant.isChecked()) || check_approuve_sortant.isChecked())
                        )
                    signatureMissing = true;
                break;
        }

        if (!signatureMissing) {

            if(!InternetConnection.isConnected(mContext)){
                alertDialogPostMissionShow(R.string.nointernet, mContext);
            }else{
                Toast.makeText(mContext, getString(R.string.updating_mission), Toast.LENGTH_LONG).show();

                ProgressBar progressBar_loading = (ProgressBar) findViewById(R.id.progressBar_loading);
                progressBar_loading.setVisibility(View.VISIBLE);
                TextView textView_pictures = (TextView) findViewById(R.id.see_all_photos);
                TextView textView_envoyer = (TextView) findViewById(R.id.valider);
                TextView textView_close = (TextView) findViewById(R.id.cancel);
                textView_pictures.setClickable(false);
                textView_envoyer.setClickable(false);
                textView_close.setClickable(false);


                getAllMissionDetails();
                postMission(data, this);
            }

        } else
            Toast.makeText(mContext, getString(R.string.error_envoyer), Toast.LENGTH_LONG).show();
    }

    public Bitmap setSignatureImg(String typeSignature) {
        Bitmap imgData = null;
        try {
            switch (typeSignature) {
                case "entrant":
                    entrantSign.setBackgroundColor(Color.WHITE);
                    entrantSign.setDrawingCacheEnabled(true);
                    mBitmap = Bitmap.createBitmap(entrantSign.getDrawingCache());
                    break;
                case "sortant":
                    sortantSign.setBackgroundColor(Color.WHITE);
                    sortantSign.setDrawingCacheEnabled(true);
                    mBitmap = Bitmap.createBitmap(sortantSign.getDrawingCache());
                    break;
                case "opera":
                    operaSign.setBackgroundColor(Color.WHITE);
                    operaSign.setDrawingCacheEnabled(true);
                    mBitmap = Bitmap.createBitmap(operaSign.getDrawingCache());
                    break;
            }

            if (mBitmap != null) {
                imgData = mBitmap;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                String photo_name = MissionsList.missionSelected.getNumeroRdvMission()
                        + " - app -Signature " + typeSignature;

                OPERA_Photos opera_photos_signature = new OPERA_Photos(MissionsList.missionSelected.getIdMission(), byteArray, photo_name);
                OPERA_Photos_DAO photo_DAO = new OPERA_Photos_DAO(mContext);

                SignatureItem signature = new SignatureItem();
                signature.setIdOperaPhoto(photo_DAO.addValue(opera_photos_signature));
                signature.setType(typeSignature);
                signature.setIdMission(MissionsList.missionSelected.getIdMission());
                signature_DAO.addValue(signature);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgData;
    }

    public void callCancel(View v) {
        this.finish();
    }

    private boolean prepareDirectory() {
        try {
            return makedirs();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.singature_error), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean makedirs() {
        if (tempDir != null) {
            File tempdir = new File(tempDir);
            if (!tempdir.exists())
                tempdir.mkdirs();

            if (tempdir.isDirectory()) {
                File[] files = tempdir.listFiles();
                for (File file : files) {
                    if (!file.delete()) {
                        Log.d("Error", "Failed to delete " + file);
                    }
                }
            }
            return (tempdir.isDirectory());
        }
        return false;
    }

    private void getAllMissionDetails() {
        try {
            missions = null;
            CEL_Chauffage chauffage = null;
            CEL_ECS ecs = null;
            CEL_Biens biens = null;
            ArrayList<CEL_Piece> pieces = new ArrayList<CEL_Piece>();
            ArrayList<CEL_Elements> elements = new ArrayList<CEL_Elements>();
            ArrayList<CEL_Personnes> personnes = new ArrayList<CEL_Personnes>();
            ArrayList<CEL_Compteurs> compteurs = new ArrayList<CEL_Compteurs>();
            ArrayList<CEL_Clefs> clefsList = new ArrayList<CEL_Clefs>();
            CEL_Elements_Obs obs_elements = null;
            CEL_Actions actions;
            CEL_Clefs_DAO clefs_DAO = new CEL_Clefs_DAO(mContext);
            CEL_Elements_DAO elements_DAO = new CEL_Elements_DAO(mContext);
            CEL_Elements_Obs_DAO elements_Obs_DAO = new CEL_Elements_Obs_DAO(mContext);
            CEL_Actions_DAO actions_DAO = new CEL_Actions_DAO(mContext);
            CEL_Personnes_DAO personnes_DAO = new CEL_Personnes_DAO(mContext);
            CEL_Mission_Personnes_DAO locataires_DAO = new CEL_Mission_Personnes_DAO(mContext);
            CEL_Compteurs_DAO compteurs_DAO = new CEL_Compteurs_DAO(mContext);
            CEL_Chauffage_DAO chauffage_DAO = new CEL_Chauffage_DAO(mContext);
            CEL_Biens_DAO biens_DAO = new CEL_Biens_DAO(mContext);
            CEL_ECS_DAO ecs_DAO = new CEL_ECS_DAO(mContext);
            OPERA_Photos_DAO photos_DAO = new OPERA_Photos_DAO(mContext);

            CEL_Personnes locataireEntrant = locataires_DAO.selectTypePersonne(MissionsList.missionSelected.getIdMission(), true);
            CEL_Personnes locataireSortant = locataires_DAO.selectTypePersonne(MissionsList.missionSelected.getIdMission(), false);

            CEL_Mission_DAO mission_DAO = new CEL_Mission_DAO(mContext);

            data = new JSONObject();
            missions = mission_DAO.select(MissionsList.missionSelected.getIdMission());
            JSONObject missionData = new JSONObject();
            missionData.put("IdMission", missions.getIdMission());
            missionData.put("NumeroRDV", missions.getNumeroRdvMission());
            missionData.put("Date", replacePost(missions.getDateMission()));
            missionData.put("Heure", replacePost(missions.getHeureMission()));
            missionData.put("CodeClient", replacePost(missions.getCodeClientMission()));
            missionData.put("NomClient", replacePost(missions.getNomClientMission()));
            missionData.put("CodeArticle", replacePost(missions.getCodeArticleMission()));
            missionData.put("Constatavecdescriptif", missions.getConstatAvecDescriptifMission());
            missionData.put("Conformeauxtravaux", missions.getConformeAuxTravauxMission());
            missionData.put("DetecteurFumee", missions.getDetecteurFumeeMission());
            missionData.put("IdBien", missions.getIdBien());
            missionData.put("idChauffage", missions.getIdChauffage());
            missionData.put("idECS", missions.getIdECS());
            missionData.put("Obs", replacePost(missions.getObservationMission()));
            missionData.put("Tel", replacePost(missions.getTel_Entrant()));
            missionData.put("Tel2", replacePost(missions.getTel_Sortant()));
            missionData.put("commentaireEntrant", replacePost(missions.getRefusCommentEntrant()));
            missionData.put("commentaireSortant", replacePost(missions.getRefusCommentSortant()));
            missionData.put("commentaireExpert", replacePost(missions.getCommentExpert()));
            missionData.put("InformationCompteurs", replacePost(missions.getInformationsCompteursMission()));

            if (missions.getCommentaireClefMission() != null && missions.getCommentaireClefMission().length() > 0)
                missionData.put("CommentaireClef", replacePost(missions.getCommentaireClefMission()));
            else
                missionData.put("CommentaireClef", "");
            missionData.put("Mandataire", replacePost(missions.getMandataireMission()));

            if (locataireSortant != null)
                missionData.put("DateEntree", replacePost(locataireSortant.getDateEntree()));
            else
                missionData.put("DateEntree", "");


            missionData.put("FournisseurElectricite", replacePost(missions.getFournisseurElectriviteMission()));
            missionData.put("Relocation", replacePost(missions.getRelocationMission()));
            missionData.put("mailReceptionEdl", replacePost(missions.getMailReceptionEdl()));
            missionData.put("Proprietaire", replacePost(missions.getProprietaire()));
            missionData.put("Photofacade", replacePost(photos_DAO.select(missions.getIdMissionPhotoFacade()).getPhotoName() + ".png"));
            missionData.put("Photoclef", replacePost(photos_DAO.select(missions.getIdMissionPhotoClef()).getPhotoName() + ".png"));

            biens = biens_DAO.select(MissionsList.missionSelected.getIdBien());
            missionData.put("Interieur", replacePost(biens.getInterieur()));

            Signature_DAO signature_dao = new Signature_DAO(mContext);
            HashMap<String, String> signatureNameMap = signature_dao.getSignaturePhotoName(missions.getIdMission());

            if (signatureNameMap.get("entrant") != null)
                missionData.put("PhotoSignatureEntrant", replacePost(signatureNameMap.get("entrant") + ".png"));

            if (signatureNameMap.get("sortant") != null)
                missionData.put("PhotoSignatureSortant", replacePost(signatureNameMap.get("sortant") + ".png"));

            if (signatureNameMap.get("opera") != null)
                missionData.put("PhotoSignatureExpertOuProprietaire", replacePost(signatureNameMap.get("opera") + ".png"));


            data.put("Mission", missionData);

            if (missions.getNomClientMission() != null && !missions.getNomClientMission().equalsIgnoreCase("null"))
                nomval.setText(missions.getNomClientMission());

            rdvval.setText(String.valueOf(missions.getNumeroRdvMission()));

            if (missions.getDateMission() != null && !missions.getDateMission().equalsIgnoreCase("null"))
                dateval.setText(missions.getDateMission());

            if (missions.getHeureMission() != null && !missions.getHeureMission().equalsIgnoreCase("null"))
                heureval.setText(missions.getHeureMission());

            if (locataireSortant != null && locataireSortant.getDateEntree() != null)
                date_entrant_TextView.setText(locataireSortant.getDateEntree());


            CEL_Personnes personnes_entrant = locataires_DAO.selectTypePersonne(missions.getIdMission(), true);
            if (personnes_entrant != null) {
                nomPrenom_entrant_TextView.setText(personnes_entrant.getPrenomPersonnes() + " " + personnes_entrant.getNomPersonnes());

                adresse_entrant_TextView.setText(personnes_entrant.getAdressePersonnes());

                cpVille_entrant_TextView.setText(personnes_entrant.getCodePostalPersonnes() + " " + personnes_entrant.getVillePersonnes());

                mail_entrant.setText(personnes_entrant.getEmailPersonnes());

                tab_representant_entrant.setText(personnes_entrant.getRepresentantPersonnes());

                title_entrant.setText(getString(R.string.signature) + " " + personnes_entrant.getPrenomPersonnes() + " "
                        + personnes_entrant.getNomPersonnes() + " - app -" + personnes_entrant.getRepresentantPersonnes());
            }

            CEL_Personnes personnes_sortant = locataires_DAO.selectTypePersonne(missions.getIdMission(), false);
            if (personnes_sortant != null) {
                nomPrenom_sortant_TextView.setText(personnes_sortant.getPrenomPersonnes() + " " + personnes_sortant.getNomPersonnes());

                adresse_sortant_TextView.setText(personnes_sortant.getAdressePersonnes());

                cpVille_sortant_TextView.setText(personnes_sortant.getCodePostalPersonnes() + " " + personnes_sortant.getVillePersonnes());

                mail_sortant.setText(personnes_sortant.getEmailPersonnes());

                tab_representant_sortant.setText(personnes_sortant.getRepresentantPersonnes());

                title_sortant.setText(getString(R.string.signature) + " " + personnes_sortant.getPrenomPersonnes() + " "
                        + personnes_sortant.getNomPersonnes() + " - app -" + personnes_sortant.getRepresentantPersonnes());
            }

            if (missions.getFournisseurElectriviteMission() != null && !missions.getFournisseurElectriviteMission().equalsIgnoreCase("null"))
                fournisseurval.setText(missions.getFournisseurElectriviteMission());

            if (missions.getIsEntrantRefusChecked() == 1)
                checkBox_refusEntrant.setChecked(true);

            if (missions.getIsEntrantApproveChecked() == 1)
                check_approuve_entrant.setChecked(true);

            if (missions.getIsSortantRefusChecked() == 1)
                checkBox_refusSortant.setChecked(true);

            if (missions.getIsSortantApproveChecked() == 1)
                check_approuve_sortant.setChecked(true);

            if (missions.getIsOperaApproveChecked() == 1)
                check_approuve_opera.setChecked(true);

            CEL_Piece_DAO pieces_DAO = new CEL_Piece_DAO(mContext, MissionsList.missionSelected.getIdMission(),
                    biens_DAO.select(MissionsList.missionSelected.getIdBien()));

            pieces = pieces_DAO.selectAllPieceWithIdMission(MissionsList.missionSelected.getIdMission());
            JSONArray piecesData = new JSONArray();
            if (pieces != null && pieces.size() != 0) {
                for (int k = 0; k < pieces.size(); k++) {
                    JSONObject piecesObjData = new JSONObject();
                    JSONObject pieceData = new JSONObject();
                    pieceData.put("idPiece", pieces.get(k).getIdPiece());
                    pieceData.put("idMission", pieces.get(k).getIdMission());
                    pieceData.put("piece", replacePost(pieces.get(k).getPiecePiece()));
                    pieceData.put("logueur", pieces.get(k).getLongueurPiece());
                    pieceData.put("largeur", pieces.get(k).getLargeurPiece());
                    pieceData.put("hauteur", pieces.get(k).getHauteurPiece());
                    pieceData.put("Inclus_dans_piece", replacePost(pieces.get(k).getInclusDansPiece()));
                    pieceData.put("notes", replacePost(pieces.get(k).getNotesPiece()));
                    pieceData.put("photo", replacePost(photos_DAO.select(pieces.get(k).getIdOperaPhoto()).getPhotoName() + ".png"));
                    piecesObjData.put("Piece", pieceData);

                    LinearLayout post_piece_h = new LinearLayout(mContext);
                    post_piece_h = (LinearLayout) inflater.inflate(R.layout.post_piece_header, null);
                    LinearLayout dynamiclistitems1 = (LinearLayout) post_piece_h.findViewById(R.id.dynamiclistitems1);
                    TextView piece_title = (TextView) post_piece_h.findViewById(R.id.piece_title);
                    TextView piece_note_val = (TextView) post_piece_h.findViewById(R.id.piece_note_val);
                    piece_title.setText(pieces.get(k).getPiecePiece());
                    piece_note_val.setText(pieces.get(k).getNotesPiece());
                    LayoutInflater inflater1 = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    elements = elements_DAO.selectAllElementsFromPiece(pieces.get(k).getIdPiece(), 4);
                    JSONArray elementsData = new JSONArray();
                    if (elements != null && elements.size() != 0) {
                        for (int m = 0; m < elements.size(); m++) {
                            JSONObject elementsObjData = new JSONObject();

                            LinearLayout post_piece_item = new LinearLayout(mContext);
                            post_piece_item = (LinearLayout) inflater1.inflate(R.layout.post_piece_item, null);
                            TextView piece_val = (TextView) post_piece_item.findViewById(R.id.piece_val);
                            TextView piece_type_val = (TextView) post_piece_item.findViewById(R.id.piece_type_val);
                            TextView piece_qte_val = (TextView) post_piece_item.findViewById(R.id.piece_qte_val);
                            TextView piece_tr_val = (TextView) post_piece_item.findViewById(R.id.piece_tr_val);
                            ImageView piece_nv_val = (ImageView) post_piece_item.findViewById(R.id.piece_nv_val);
                            ImageView piece_nf_val = (ImageView) post_piece_item.findViewById(R.id.piece_nf_val);
                            ImageView piece_be_val = (ImageView) post_piece_item.findViewById(R.id.piece_be_val);
                            ImageView piece_eu_val = (ImageView) post_piece_item.findViewById(R.id.piece_eu_val);
                            ImageView piece_me_val = (ImageView) post_piece_item.findViewById(R.id.piece_me_val);
                            ImageView piece_hs_val = (ImageView) post_piece_item.findViewById(R.id.piece_hs_val);
                            TextView piece_observations_val = (TextView) post_piece_item.findViewById(R.id.piece_observations_val);
                            CheckBox piece_nett_val = (CheckBox) post_piece_item.findViewById(R.id.piece_nett_val);
                            CheckBox piece_photo_val = (CheckBox) post_piece_item.findViewById(R.id.piece_photo_val);
                            TextView piece_action_val = (TextView) post_piece_item.findViewById(R.id.piece_reserve_val);

                            if (elements.get(m).getEtatElements() != null) {
                                if (elements.get(m).getEtatElements().equalsIgnoreCase("BE"))
                                    piece_be_val.setImageResource(R.drawable.green_circle);
                                if (elements.get(m).getEtatElements().equalsIgnoreCase("HS"))
                                    piece_hs_val.setImageResource(R.drawable.red_circle);
                                if (elements.get(m).getEtatElements().equalsIgnoreCase("ME"))
                                    piece_me_val.setImageResource(R.drawable.yellow_circle);
                                if (elements.get(m).getEtatElements().equalsIgnoreCase("EU"))
                                    piece_eu_val.setImageResource(R.drawable.blue_circle);
                                if (elements.get(m).getEtatElements().equalsIgnoreCase("NF"))
                                    piece_nf_val.setImageResource(R.drawable.light_green_circle);
                                if (elements.get(m).getEtatElements().equalsIgnoreCase("NV"))
                                    piece_nv_val.setImageResource(R.drawable.grey_circle);
                            }
                            piece_tr_val.setText(String.valueOf(elements.get(m).getNombreTrousChevilleElements()));

                            JSONObject elementData = new JSONObject();
                            elementData.put("idElements", elements.get(m).getIdElements());
                            elementData.put("idPiece", elements.get(m).getIdPiece());
                            elementData.put("Element", replacePost(elements.get(m).getElementElements()));
                            elementData.put("Type", replacePost(elements.get(m).getTypeElements()));
                            elementData.put("Qte", elements.get(m).getQuantiteElements());
                            elementData.put("Etat", replacePost(elements.get(m).getEtatElements()));
                            elementData.put("NombreTrousCheville", elements.get(m).getNombreTrousChevilleElements());
                            elementData.put("TypeEtat", elements.get(m).getItemGroupDescription());
                            elementData.put("Mandatory", elements.get(m).getMandatory());
                            if (elements.get(m).getIsNettoyer() == 0)
                                elementData.put("Nettoyage", false);
                            else
                                elementData.put("Nettoyage", true);
                            elementData.put("Photo", replacePost(photos_DAO.select(elements.get(m).getIdOperaPhoto()).getPhotoName() + ".png"));
                            elementsObjData.put("Element", elementData);

                            JSONObject obs_elementsData = new JSONObject();
                            elements_Obs_DAO = new CEL_Elements_Obs_DAO(mContext);
                            String observationsString = elements_Obs_DAO.getObservationsString(elements.get(m).getIdElements());
                            piece_observations_val.setText(observationsString);

                            obs_elementsData.put("idElement", elements.get(m).getIdElements());
                            obs_elementsData.put("Obs", observationsString);
                            elementsObjData.put("Observations", obs_elementsData);

                            elements_Obs_DAO.close();
                            actions = actions_DAO.selectActions(elements.get(m).getIdElements());
                            JSONObject actionsData = new JSONObject();
                            if (actions != null) {
                                actionsData.put("idAction", actions.getIdActions());
                                actionsData.put("idElements", actions.getIdElement());
                                actionsData.put("qte", actions.getQuantiteActions());
                                if (actions.getActionActions() == null || actions.getActionActions().equalsIgnoreCase("null"))
                                    actionsData.put("action", "");
                                else {
                                    actionsData.put("action", replacePost(actions.getActionActions()));
                                    piece_action_val.setText(actions.getActionActions());
                                }

                                if (actions.getUniteActions() == null || actions.getUniteActions().equalsIgnoreCase("null")) {
                                    actionsData.put("Note", "");
                                } else {
                                    actionsData.put("Note", replacePost(actions.getNoteActions()));
                                }
                                actionsData.put("unite", replacePost(actions.getUniteActions()));

                                elementsObjData.put("Actions", actionsData);
                            }

                            elementsData.put(elementsObjData);

                            if (elements.get(m).getElementElements() != null && !elements.get(m).getElementElements().equalsIgnoreCase("null"))
                                piece_val.setText(elements.get(m).getElementElements());

                            if (elements.get(m).getTypeElements() != null && !elements.get(m).getTypeElements().equalsIgnoreCase("null"))
                                piece_type_val.setText(elements.get(m).getTypeElements());

                            if (elements.get(m).getIsNettoyer() != 0)
                                piece_nett_val.setChecked(true);

                            if (elements.get(m).getIdOperaPhoto() != 0) {
                                piece_photo_val.setChecked(true);
                            }

                            piece_qte_val.setText(String.valueOf(elements.get(m).getQuantiteElements()));
                            dynamiclistitems1.addView(post_piece_item);
                        }

                        dynamiclistitems.addView(post_piece_h);
                    }
                    piecesObjData.put("Elements", elementsData);
                    piecesData.put(piecesObjData);
                }
                data.put("Pieces", piecesData);
            }

            personnes = personnes_DAO.selectPersonnes(MissionsList.missionSelected.getIdMission());

            JSONArray personnesData = new JSONArray();
            if (personnes != null && personnes.size() > 0) {
                for (int l = 0; l < personnes.size(); l++) {
                    JSONObject personneData = new JSONObject();
                    personneData.put("idPersonne", personnes.get(l).getIdPersonnes());
                    personneData.put("Nom", replacePost(personnes.get(l).getNomPersonnes()));
                    personneData.put("Prenom", replacePost(personnes.get(l).getPrenomPersonnes()));
                    personneData.put("Adresse", replacePost(personnes.get(l).getAdressePersonnes()));
                    personneData.put("Suite", replacePost(personnes.get(l).getSuitePersonnes()));
                    personneData.put("Cp", replacePost(personnes.get(l).getCodePostalPersonnes()));
                    personneData.put("Ville", replacePost(personnes.get(l).getVillePersonnes()));
                    personneData.put("Representant", replacePost(personnes.get(l).getRepresentantPersonnes()));
                    personneData.put("Email", replacePost(personnes.get(l).getEmailPersonnes()));
                    personneData.put("Tel", replacePost(personnes.get(l).getTelephonePersonnes()));
                    personneData.put("Type", personnes.get(l).getTypePersonnes());

                    personnesData.put(personneData);
                }
            }
            data.put("Personnes", personnesData);

            compteurs = compteurs_DAO.selectAllCEL_Compteurs(MissionsList.missionSelected.getIdMission());
            JSONArray compteursData = new JSONArray();
            if (compteurs != null && compteurs.size() > 0) {

                for (int l = 0; l < compteurs.size(); l++) {

                    if (compteurs.get(l).getTypeCompteurs() == 1) {
                        jourval.setText(String.valueOf(compteurs.get(l).getIndex_1Compteurs()));
                        nuitval.setText(String.valueOf(compteurs.get(l).getIndex_2Compteurs()));
                        if (compteurs.get(l).getEtat() != null && !compteurs.get(l).getEtat().equalsIgnoreCase("null"))
                            fournisseurval.setText(compteurs.get(l).getEtat());
                    } else if (compteurs.get(l).getTypeCompteurs() == 0) {
                        chaudeval.setText(String.valueOf(compteurs.get(l).getIndex_1Compteurs()));
                        froideval.setText(String.valueOf(compteurs.get(l).getIndex_2Compteurs()));
                    } else if (compteurs.get(l).getTypeCompteurs() == 4) {
                        chaudeval1.setText(String.valueOf(compteurs.get(l).getIndex_1Compteurs()));
                        froideval1.setText(String.valueOf(compteurs.get(l).getIndex_2Compteurs()));
                    } else if (compteurs.get(l).getTypeCompteurs() == 2) {
                        gazval.setText(String.valueOf(compteurs.get(l).getIndex_1Compteurs()));
                    } else if (compteurs.get(l).getTypeCompteurs() == 3) {
                        if (compteurs.get(l).getIndex_1Compteurs() != 1) {
                            detecteur_present_val.setText(R.string.no);
                        } else {
                            detecteur_present_val.setText(R.string.yes);
                            detecteur_nb_val.setText(String.valueOf(compteurs.get(l).getIndex_2Compteurs()));
                        }
                    }


                    //We don't need Detecteur in this json
                    if (compteurs.get(l).getTypeCompteurs() == 3)
                        continue;

                    JSONObject compteurData = new JSONObject();
                    compteurData.put("idCompteur", compteurs.get(l).getIdCompteurs());
                    compteurData.put("Index_1", compteurs.get(l).getIndex_1Compteurs());
                    compteurData.put("Index_2", compteurs.get(l).getIndex_2Compteurs());
                    compteurData.put("idEtat", compteurs.get(l).getIdEtat());
                    compteurData.put("idMission", compteurs.get(l).getIdMission());
                    compteurData.put("Etat", replacePost(compteurs.get(l).getEtat()));
                    compteurData.put("Photo", replacePost(photos_DAO.select(compteurs.get(l).getIdPhoto()).getPhotoName() + ".png"));
                    compteurData.put("Type", compteurs.get(l).getTypeCompteurs());
                    compteursData.put(compteurData);
                }
            }
            data.put("Compteurs", compteursData);

            clefsList = clefs_DAO.selectAllFromBiens(MissionsList.missionSelected.getIdMission());
            JSONArray clefsData = new JSONArray();
            if (clefsList != null && clefsList.size() > 0) {
                String[] mTypeArray = mContext.getResources().getStringArray(R.array.array_clefs);

                LinearLayout post_cles_h = new LinearLayout(mContext);
                post_cles_h = (LinearLayout) inflatercles.inflate(R.layout.post_clefs_header, null);
                LinearLayout dynamiclistitems1 = (LinearLayout) post_cles_h.findViewById(R.id.dynamiclistitems1);
                TextView cles_verifiees_total = (TextView) post_cles_h.findViewById(R.id.cles_verifiees_total);
                TextView cles_nonverifiees_total = (TextView) post_cles_h.findViewById(R.id.cles_nonverifiees_total);
                TextView cles_total_tot = (TextView) post_cles_h.findViewById(R.id.cles_total_tot);
                LayoutInflater inflater1 = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                int verifi = 0, nonverifi = 0, total = 0;
                for (int l = 0; l < clefsList.size(); l++) {
                    LinearLayout post_cles_item = new LinearLayout(mContext);
                    post_cles_item = (LinearLayout) inflater1.inflate(R.layout.post_clefs_item, null);
                    TextView cles_txt_val = (TextView) post_cles_item.findViewById(R.id.cles_txt_val);
                    TextView cles_verifiees_val = (TextView) post_cles_item.findViewById(R.id.cles_verifiees_val);
                    TextView cles_nonverifiees_val = (TextView) post_cles_item.findViewById(R.id.cles_nonverifiees_val);
                    TextView cles_total_val = (TextView) post_cles_item.findViewById(R.id.cles_total_val);
                    TextView cles_hs_val = (TextView) post_cles_item.findViewById(R.id.cles_hs_val);
                    JSONObject clefData = new JSONObject();

                    String val = "";
                    try {
                        val = mTypeArray[clefsList.get(l).getTypeClefs()];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    clefData.put("idClefs", clefsList.get(l).getIdClefs());
                    clefData.put("idMission", clefsList.get(l).getIdMission());
                    clefData.put("Type", replacePost(val));
                    clefData.put("NombreClefVerifiee", clefsList.get(l).getNombreClefVerifiee());
                    clefData.put("NombreClefNonVerifiee", clefsList.get(l).getNombreClefNonVerifiee());
                    clefData.put("Hs", clefsList.get(l).isHsClefs());
                    if (clefsList.get(l).getComment() == null)
                        clefData.put("Commentaire", "");
                    else
                        clefData.put("Commentaire", clefsList.get(l).getComment());
                    clefsData.put(clefData);

                    cles_txt_val.setText(val);
                    cles_verifiees_val.setText(String.valueOf(clefsList.get(l).getNombreClefVerifiee()));
                    cles_nonverifiees_val.setText(String.valueOf(clefsList.get(l).getNombreClefNonVerifiee()));
                    cles_total_val.setText(String.valueOf(clefsList.get(l).getTotal()));
                    if (clefsList.get(l).isHsClefs())
                        cles_hs_val.setText("Oui");
                    else
                        cles_hs_val.setText("Non");
                    try {
                        verifi = verifi + clefsList.get(l).getNombreClefVerifiee();
                        nonverifi = nonverifi + clefsList.get(l).getNombreClefNonVerifiee();
                        total = total + clefsList.get(l).getTotal();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dynamiclistitems1.addView(post_cles_item);
                }
                cles_verifiees_total.setText(String.valueOf(verifi));
                cles_nonverifiees_total.setText(String.valueOf(nonverifi));
                cles_total_tot.setText(String.valueOf(total));
                dynamicitemscles.addView(post_cles_h);
            }
            data.put("Clefs", clefsData);

            chauffage = chauffage_DAO.select(MissionsList.missionSelected.getIdChauffage());
            JSONObject chauffageData = new JSONObject();
            if (chauffage != null) {
                chauffageData.put("idChauffage", chauffage.getIdChauffage());
                chauffageData.put("Type", chauffage.getTypeChauffage());
                chauffageData.put("Chaudiere", chauffage.getChaudiereChauffage());
                chauffageData.put("Marque", chauffage.getMarqueChauffage());
                chauffageData.put("Entretien", replacePost(chauffage.getEntretienChauffage()));
                chauffageData.put("HorsService", chauffage.getHorsService());

                String[] mChaudiere = getResources().getStringArray(R.array.spinnerItemsChaudiereChauffage);
                certificatval.setText(mChaudiere[chauffage.getChaudiereChauffage()]);
                String[] mMarque = getResources().getStringArray(R.array.spinnerItemsMarqueChauffage);
                chaudiereval.setText(mMarque[chauffage.getMarqueChauffage()]);
                String[] mChauffage = getResources().getStringArray(R.array.spinnerItemsTypeChauffage);
                modeval.setText(mChauffage[chauffage.getTypeChauffage()]);
                if (chauffage.getEntretienChauffage() != null && !chauffage.getEntretienChauffage().equalsIgnoreCase("null"))
                    sanitaireval.setText(chauffage.getEntretienChauffage());
            } else {
                chauffageData.put("idChauffage", "");
                chauffageData.put("Type", "");
                chauffageData.put("Chaudiere", "");
                chauffageData.put("Marque", "");
                chauffageData.put("Entretien", "");
                chauffageData.put("HorsService", "");
            }
            data.put("Chauffage", chauffageData);

            ecs = ecs_DAO.select(MissionsList.missionSelected.getIdECS());
            JSONObject ecsData = new JSONObject();
            if (ecs != null) {
                ecsData.put("idECS", ecs.getIdECS());
                ecsData.put("type", ecs.getTypeEcs());
                ecsData.put("Marque", ecs.getMarqueEcs());

                String[] type = getResources().getStringArray(R.array.spinnerItemsEauChaudeType);
                String[] marque = getResources().getStringArray(R.array.spinnerItemsEauChaudeMarque);

                ecsType_TextView.setText(type[ecs.getTypeEcs()]);
                ecsMarque_TextView.setText(marque[ecs.getMarqueEcs()]);

            } else {
                ecsData.put("idECS", "");
                ecsData.put("type", "");
                ecsData.put("Marque", "");
            }
            data.put("ECS", ecsData);

            biens = biens_DAO.select(MissionsList.missionSelected.getIdBien());
            JSONObject biensData = new JSONObject();
            if (biens != null) {
                biensData.put("Adresse", replacePost(biens.getAdresseBiens()));
                if (biens.getCodePostalBiens() == null || biens.getCodePostalBiens().equalsIgnoreCase("null"))
                    biensData.put("Suite", "");
                else
                    biensData.put("Suite", replacePost(biens.getSuiteBiens()));
                biensData.put("Cp", replacePost(biens.getCodePostalBiens()));
                biensData.put("Ville", replacePost(biens.getVilleBiens()));
                biensData.put("Digicode", replacePost(biens.getDigicodeBiens()));
                biensData.put("Etage", replacePost(biens.getEtageBiens()));
                biensData.put("Surface", biens.getSurfaceBiens());
                biensData.put("Pieces", biens.getPiecesBiens());
                biensData.put("Immeuble", replacePost(biens.getImmeubleBiens()));
                biensData.put("Lot", replacePost(biens.getLotBiens()));
                if (biens.getMandatBiens() == null || biens.getMandatBiens().equalsIgnoreCase("null"))
                    biensData.put("Mandat", "");
                else {
                    biensData.put("Mandat", replacePost(biens.getMandatBiens()));
                }
                biensData.put("Type", biens.getTypeBiens());

                if (biens.getCodePostalBiens() != null && !biens.getCodePostalBiens().equalsIgnoreCase("null"))
                    cpval.setText(biens.getCodePostalBiens());

                if (biens.getVilleBiens() != null && !biens.getVilleBiens().equalsIgnoreCase("null"))
                    cpval.setText(cpval.getText().toString() + " " + biens.getVilleBiens());

                if (biens.getDigicodeBiens() != null && !biens.getDigicodeBiens().equalsIgnoreCase("null"))
                    digicodeval.setText(biens.getDigicodeBiens());

                if (biens.getEtageBiens() != null && !biens.getEtageBiens().equalsIgnoreCase("null"))
                    etageval.setText(biens.getEtageBiens());

                if (biens.getAdresseBiens() != null && !biens.getAdresseBiens().equalsIgnoreCase("null"))
                    adresseval.setText(biens.getAdresseBiens());

                if (biens.getSuiteBiens() != null && !biens.getSuiteBiens().equalsIgnoreCase("null"))
                    adresseval.setText(adresseval.getText() + " " + biens.getSuiteBiens());


                if (biens.getImmeubleBiens() != null && !biens.getImmeubleBiens().equalsIgnoreCase("null"))
                    immeuble_TextView.setText(biens.getImmeubleBiens());


                if (biens.getLotBiens() != null && !biens.getLotBiens().equalsIgnoreCase("null"))
                    lot_TextView.setText(biens.getLotBiens());


                if (biens.getMandatBiens() != null && !biens.getMandatBiens().equalsIgnoreCase("null"))
                    mandat_TextView.setText(biens.getMandatBiens());

                String[] type = getResources().getStringArray(R.array.spinnerItemsTypeBien);
                typeval.setText(type[biens.getTypeBiens()]);
                typeval.setText(typeval.getText().toString() + " * " + Float.toString(biens.getSurfaceBiens()) + " m²");


                if (MissionsList.missionSelected.getProprietaire() != null && !MissionsList.missionSelected.getProprietaire().equalsIgnoreCase("null"))
                    proprietaireval.setText(MissionsList.missionSelected.getProprietaire());

                Login.settings = getSharedPreferences(Login.PREFS_NAME, 0);
                /*if (Login.settings.getBoolean("isIndependant", false)) {
                    expertval.setText(Login.settings.getString("independant_login", ""));
                } else {
                    expertval.setText(Login.settings.getString("nomExpert", "") + " " + Login.settings.getString("prenomExpert", ""));
                }*/
                expertval.setText("Indépendant");

                data.put("Bien", biensData);
            }

            Bundle bundle = new Bundle();
            bundle.putString("date", date_for_request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            signatureitem = signature_DAO.select(MissionsList.missionSelected.getIdMission());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (signatureitem != null && signatureitem.size() > 0) {
                for (int s = 0; s < signatureitem.size(); s++) {

                    OPERA_Photos_DAO opera_photo_dao = new OPERA_Photos_DAO(mContext);
                    OPERA_Photos opera_photos = opera_photo_dao.select(signatureitem.get(s).getIdOperaPhoto());

                    if (signatureitem.get(s).getType().equals("entrant")) {
                        entrantview.setImageBitmap(BitmapFactory.decodeByteArray(opera_photos.getPhoto(), 0,
                                opera_photos.getPhoto().length));
                        entrantview.setVisibility(View.VISIBLE);
                        entrantLayout.setVisibility(View.INVISIBLE);
                        entrantValider = true;
                    } else if (signatureitem.get(s).getType().equals("sortant")) {
                        sortantview.setImageBitmap(BitmapFactory.decodeByteArray(opera_photos.getPhoto(), 0,
                                opera_photos.getPhoto().length));
                        sortantview.setVisibility(View.VISIBLE);
                        sortantLayout.setVisibility(View.INVISIBLE);
                        sortantValider = true;
                    } else if (signatureitem.get(s).getType().equals("opera")) {
                        operaview.setImageBitmap(BitmapFactory.decodeByteArray(opera_photos.getPhoto(), 0,
                                opera_photos.getPhoto().length));
                        operaview.setVisibility(View.VISIBLE);
                        operaLayout.setVisibility(View.INVISIBLE);
                        operaValider = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mGetSign.setClickable(true);
        mGetSign.setEnabled(true);

    }


    public String replacePost(String data) {
        try {
            //return URLEncoder.encode(data.trim().replaceAll("\n", " ").replace("\r", " "), "utf-8");
            return data.trim().replaceAll("\\n?\\r", " ").replaceAll("\n", " ").replaceAll("\r", " ")
                    .replaceAll("<br />", " ").replaceAll("<br>", " ");
        } catch (Exception e) {
            return "";
        }
    }

    public void callPhotosView(View v) {
        startActivity(new Intent(PostDetails.this, PhotosActivity.class));
    }

    private void setEntrantSortantCheckboxListener() {

        if (mission_DAO == null)
            mission_DAO = new CEL_Mission_DAO(this);

        checkBox_refusEntrant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_refusEntrant.isChecked()) {
                    check_approuve_entrant.setChecked(false);
                    missions.setIsEntrantRefusChecked(1);
                    missions.setIsEntrantApproveChecked(0);

                    AlertDialogCommentRefus(0);
                } else
                    missions.setIsEntrantRefusChecked(0);

                mission_DAO.updateValue(missions);
            }
        });


        check_approuve_entrant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check_approuve_entrant.isChecked()) {
                    checkBox_refusEntrant.setChecked(false);
                    missions.setIsEntrantApproveChecked(1);
                    missions.setIsEntrantRefusChecked(0);
                } else {
                    missions.setIsEntrantApproveChecked(0);

                    MissionsList.missionSelected.setRefusCommentEntrant("");

                    CEL_Mission_DAO mission_dao = new CEL_Mission_DAO(mContext);
                    mission_dao.updateValue(MissionsList.missionSelected);
                }

                mission_DAO.updateValue(missions);
            }
        });

        checkBox_refusSortant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_refusSortant.isChecked()) {
                    check_approuve_sortant.setChecked(false);
                    missions.setIsSortantRefusChecked(1);
                    missions.setIsSortantApproveChecked(0);

                    AlertDialogCommentRefus(1);
                } else
                    missions.setIsSortantRefusChecked(0);

                MissionsList.missionSelected.setRefusCommentSortant("");

                CEL_Mission_DAO mission_dao = new CEL_Mission_DAO(mContext);
                mission_dao.updateValue(MissionsList.missionSelected);

                mission_DAO.updateValue(missions);
            }
        });


        check_approuve_sortant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check_approuve_sortant.isChecked()) {
                    checkBox_refusSortant.setChecked(false);
                    missions.setIsSortantApproveChecked(1);
                    missions.setIsSortantRefusChecked(0);
                } else {
                    missions.setIsSortantRefusChecked(0);
                }

                mission_DAO.updateValue(missions);
            }
        });

        check_approuve_opera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (missions.getIsOperaApproveChecked() == 0) {
                    missions.setIsOperaApproveChecked(1);
                } else {
                    missions.setIsOperaApproveChecked(0);
                }
                mission_DAO.updateValue(missions);
            }
        });
    }

    /**
     * @param type 0 is Entrant, 1 is Sortant and 2 is Expert
     */
    public void AlertDialogCommentRefus(final int type) {

        //Initialize custom alert dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.alert_dialog_save_licence);
        dialog.setTitle(R.string.alert_dialog_refus_commentaire);
        dialog.setCanceledOnTouchOutside(false);

        TextView code_licence_TextView = (TextView) dialog.findViewById(R.id.code_licence_TextView);
        code_licence_TextView.setText(getResources().getString(R.string.alert_dialog_comment));

        final EditText code_licence_EditText = (EditText) dialog.findViewById(R.id.code_licence_EditText);
        code_licence_EditText.setInputType(InputType.TYPE_CLASS_TEXT);

        if (type == 0)
            code_licence_EditText.setText(MissionsList.missionSelected.getRefusCommentEntrant());
        else if (type == 1)
            code_licence_EditText.setText(MissionsList.missionSelected.getRefusCommentSortant());
        else if (type == 2)
            code_licence_EditText.setText(MissionsList.missionSelected.getCommentExpert());

        TextView cancel_TextView = (TextView) dialog.findViewById(R.id.cancel_TextView);
        cancel_TextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0)
                    checkBox_refusEntrant.setChecked(false);
                else if (type == 1)
                    checkBox_refusSortant.setChecked(false);
                dialog.dismiss();
            }
        });
        //On click on validate button we register licence code
        TextView validate_TextView = (TextView) dialog.findViewById(R.id.validate_TextView);
        validate_TextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code_licence_EditText.getText().toString().matches(""))
                    Toast.makeText(mContext, R.string.error_empty_licence, Toast.LENGTH_SHORT).show();
                else {
                    if (type == 0)
                        MissionsList.missionSelected.setRefusCommentEntrant(code_licence_EditText.getText().toString());
                    else if (type == 1)
                        MissionsList.missionSelected.setRefusCommentSortant(code_licence_EditText.getText().toString());
                    else if (type == 2)
                        MissionsList.missionSelected.setCommentExpert(code_licence_EditText.getText().toString());

                    CEL_Mission_DAO mission_dao = new CEL_Mission_DAO(mContext);
                    mission_dao.updateValue(MissionsList.missionSelected);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void postMission(final JSONObject json, final Context context) {

        if (json != null && json.length() != 0) {


            Thread thread = new Thread() {
                public void run() {

                    PrintWriter out;
                    try {
                        out = new PrintWriter("mnt/sdcard/update_mission.json");
                        out.println(json.toString());
                        out.close();
                        sendPhotosToFTP();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    Looper.prepare();

                    try {
                        URL url = new URL(Splash.BASE_URL + "update");
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setConnectTimeout(10000);
                        urlConnection.setDoOutput(true);
                        urlConnection.setDoInput(true);
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        urlConnection.setInstanceFollowRedirects(false);
                        urlConnection.connect();

                        OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                        writer.write(json.toString());
                        writer.flush();

                        statusCode = urlConnection.getResponseCode();

                        if (statusCode == HttpURLConnection.HTTP_OK) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"), 8);
                            StringBuilder stringBuilder = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                stringBuilder.append(line + "\n");
                            }
                            String in = stringBuilder.toString();
                            JSONObject globalJsonObject = new JSONObject(in);
                            if (globalJsonObject.getBoolean("update")) {
                                alertDialogPostMissionShow(R.string.update_success, context);
                                CEL_Mission_DAO cel_mission_dao = new CEL_Mission_DAO(mContext);
                                MissionsList.missionSelected.setExported(true);
                                cel_mission_dao.updateValue(MissionsList.missionSelected);
                            } else
                                alertDialogPostMissionShow(R.string.connection_impossible, context);

                        } else if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream(), "UTF-8"), 8);
                            StringBuilder stringBuilder = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                stringBuilder.append(line + "\n");
                            }
                            String in = stringBuilder.toString();
                            JSONObject globalJsonObject = new JSONObject(in);
                            alertDialogPostMissionShowString(globalJsonObject.getString("message"), context);
                        } else {
                            alertDialogPostMissionShow(R.string.connection_impossible, context);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        alertDialogPostMissionShow(R.string.connection_impossible, context);
                    }
                    Looper.loop();
                }
            };
            thread.start();
        }
    }

    public void alertDialogPostMissionShow(int resId, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setMessage(context.getString(resId));
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        if (!isFinishing())
            dialog.show();
    }

    public void alertDialogPostMissionShowString(String message, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setMessage(message);
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        if (!isFinishing())
            dialog.show();
    }
}
