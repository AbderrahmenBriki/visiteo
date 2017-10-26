package com.appsolute.cel.activity;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.BitmapCompat;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appsolute.cel.BaseActivity;
import com.appsolute.cel.DAO.CEL_Actions_DAO;
import com.appsolute.cel.DAO.CEL_Biens_DAO;
import com.appsolute.cel.DAO.CEL_Elements_DAO;
import com.appsolute.cel.DAO.CEL_Elements_Obs_DAO;
import com.appsolute.cel.DAO.CEL_Piece_DAO;
import com.appsolute.cel.DAO.OPERA_Photos_DAO;
import com.appsolute.cel.DAO.RoomItem_DAO;
import com.appsolute.cel.DAO.Room_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.adapter.CEL_ElementsArrayAdapter;
import com.appsolute.cel.adapter.CustomAlertDialog;
import com.appsolute.cel.models.CEL_Elements;
import com.appsolute.cel.models.CEL_Piece;
import com.appsolute.cel.models.OPERA_Photos;
import com.appsolute.cel.models.RoomItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PieceActivity extends BaseActivity {

    //Used to communicate state changes in callEtatPieceThread
    public static final int MESSAGE_DOWNLOAD_COMPLETE = 1001;
    public static final int MESSAGE_UPDATE_PROGRESS_BAR = 1002;
    private static ProgressBar progressBarEtatPiece;
    private Thread thread;
    public static int VALIDATED_ERROR = 0;
    public static int POSITION_ERROR;
    public static int IS_ETAT_PIECE_ERROR = 0;
    public static boolean isPictureSaved = true;
    public static int position_picture;
    private static float heightSize;

    //Variables to store data CRUD by user
    public static CEL_ElementsArrayAdapter cel_ElementsArrayAdapter;
    public static SparseArray<RoomItem> ROOM_ITEM_SPARSE_ARRAY;
    public static SparseArray<String> ETAT_SPARSE_ARRAY;
    public static SparseIntArray colorValuesSparseIntArray;

    static LinearLayout dynamiclistitems;
    public static ImageView listImg;
    ImageView imageview;
    static Button tab_general, tab_elements_eau, tab_etat, tab_inventaire;
    TextView text_title;
    String fermeture[], fermetureimg[];
    List<CEL_Piece> pieceslist = new ArrayList<>();
    static List<RoomItem> roomItemList = new ArrayList<>();
    List<String> list = new ArrayList<>();

    EditText longueur, largeur, hauteur, notes;
    Spinner piecelist;
    CheckBox checbox_other_piece;
    private TextView validerButton, fermerButton;
    private Button dupliquerButton;
    public static Button button_ajouter;
    boolean isFragmentGeneral = true, isValider = true;

    public static int ITEM_GROUP_DESCRIPTION = 1;
    // 0 = general, 1 = etat_piece, 2 = element_eau
    public static int CURRENT_POSITION = 0;
    public static Boolean isForFinish = false;

    private CEL_Piece_DAO pieces_DAO;
    private CEL_Elements_DAO elements_DAO;
    private CEL_Elements_Obs_DAO elements_Obs_DAO;
    private CEL_Actions_DAO cel_Actions_DAO;
    private RoomItem_DAO roomItem_DAO;

    public static CEL_Piece pieces;
    public static CEL_Elements CEL_ELEMENT;

    private ArrayList<CEL_Elements> elementsList;
    static RelativeLayout layout;
    public static int ROOM_ID;
    boolean pieceFlag = false;

    //From EtatPiece
    private static ListView listViewElements;

    //Validity Elements
    private boolean hasBeenOnEtat = false;
    private boolean hasBeenOnEau = false;
    private boolean hasElementsEau = false;
    private boolean hasElementsInventaire = false;


    //Picture
    public static Uri mImageUri;
    public static final int REQUEST_CAMERA_FOR_PIECE = 0;
    public static final int REQUEST_CAMERA_FOR_ELEMENT = 1;
    public static String pictureFromCamera = "/photo.png";
    private static Bitmap imageBitmapActivityResultPiece;
    private static Bitmap imageBitmapActivityResultElement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entree);
        baseMethod();

        CEL_Biens_DAO cel_biens_dao = new CEL_Biens_DAO(mContext);
        pieces_DAO = new CEL_Piece_DAO(mContext, MissionsList.missionSelected.getIdMission(),
                cel_biens_dao.select(MissionsList.missionSelected.getIdBien()));
        pieces = pieces_DAO.select(EtatDesLieux.pieces.getIdPiece(), MissionsList.missionSelected.getIdMission());
        pieces_DAO.close();

        Room_DAO room_DAO = new Room_DAO(mContext);
        ROOM_ID = room_DAO.selectId(pieces.getNomTypePiece());
        pieceFlag = getIntent().getBooleanExtra("pieceFlag", false);

        fermeture = getResources().getStringArray(R.array.array_fermeture);
        fermetureimg = getResources().getStringArray(R.array.array_fermetureimg);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setTitle(pieces.getPiecePiece());
        actionBar.setHomeButtonEnabled(true);
        dynamiclistitems = (LinearLayout) findViewById(R.id.dynamiclistitems);
        validerButton = (TextView) findViewById(R.id.validerButton);
        fermerButton = (TextView) findViewById(R.id.fermerButton);
        dupliquerButton = (Button) findViewById(R.id.dupliquerButton);
        tab_general = (Button) findViewById(R.id.tab_general);
        tab_etat = (Button) findViewById(R.id.tab_etat);
        tab_elements_eau = (Button) findViewById(R.id.tab_elements_eau);
        tab_inventaire = (Button) findViewById(R.id.tab_inventaire);

        tab_general.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callSaveElements(v);
                CURRENT_POSITION = 0;
                callGeneralLayout();
                initializePiece();

            }
        });

        tab_etat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callSaveElements(v);
                ITEM_GROUP_DESCRIPTION = 1;
                CURRENT_POSITION = 1;
                callEtatPiece(v);
                hasBeenOnEtat = true;

            }
        });
        tab_elements_eau.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callSaveElements(v);
                ITEM_GROUP_DESCRIPTION = 2;
                CURRENT_POSITION = 2;
                callEtatPiece(v);
                hasBeenOnEau = true;

            }
        });
        tab_inventaire.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callSaveElements(v);
                ITEM_GROUP_DESCRIPTION = 3;
                CURRENT_POSITION = 3;
                callEtatPiece(v);
                hasElementsInventaire = true;

            }
        });
        dupliquerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlertDialog.alertDialogDuplicatePiece(mContext);
            }
        });

        if (pieces.getNomTypePiece().matches(".*\\b" + getResources().getString(R.string.op_cuisine) + "\\b.*") ||
                pieces.getNomTypePiece().matches(".*\\b" + getResources().getString(R.string.op_wc) + "\\b.*")
                || pieces.getNomTypePiece().matches(".*\\b" + getResources().getString(R.string.op_salle) + "\\b.*")
                || pieces.getNomTypePiece().matches(".*\\b" + getResources().getString(R.string.op_sanitaire) + "\\b.*")) {
            tab_elements_eau.setVisibility(View.VISIBLE);
            hasElementsEau = true;
        }
        else {
            tab_elements_eau.setVisibility(View.GONE);
            hasElementsEau = false;
        }

        validerButton.setVisibility(View.INVISIBLE);
        fermerButton.setVisibility(View.INVISIBLE);

        pieces_DAO = new CEL_Piece_DAO(mContext, MissionsList.missionSelected.getIdMission(),
                cel_biens_dao.select(MissionsList.missionSelected.getIdBien()));
        pieceslist = pieces_DAO.selectAllPieceWithIdMission(MissionsList.missionSelected.getIdMission());
        pieces_DAO.close();
        list = new ArrayList<>();
        for (int i = 0; i < pieceslist.size(); i++) {
            list.add(pieceslist.get(i).getPiecePiece());
        }

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = new RelativeLayout(mActivity);
        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_piece_general, (ViewGroup) layout.getParent(), false);
        genralFileds(layout);
        initializePiece();
        dynamiclistitems.addView(layout);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (pieces_DAO == null) {
            CEL_Biens_DAO cel_biens_dao = new CEL_Biens_DAO(mContext);
            pieces_DAO = new CEL_Piece_DAO(mContext, MissionsList.missionSelected.getIdMission(),
                    cel_biens_dao.select(MissionsList.missionSelected.getIdBien()));
        }
        pieces_DAO.close();
    }

    private void initializePiece() {

        if (pieces.getNotesPiece() != null)
            notes.setText(pieces.getNotesPiece());

        if (pieces.getPiecePiece() != null)
            text_title.setText(pieces.getPiecePiece());

        if (pieces.getLongueurPiece() != 0.0f)
            longueur.setText(String.valueOf(pieces.getLongueurPiece()));

        if (pieces.getLargeurPiece() != 0.0f)
            largeur.setText(String.valueOf(pieces.getLargeurPiece()));

        if (pieces.getHauteurPiece() != 0.0f)
            hauteur.setText(String.valueOf(pieces.getHauteurPiece()));

        if (pieces.getNotesPiece() != null && pieces.getNotesPiece().length() > 0)
            notes.setText(pieces.getNotesPiece());

        if (pieces.getInclusDansPiece() != null && pieces.getInclusDansPiece().length() > 0) {
            piecelist.setSelection(0);
            for (int i = 0; i < pieceslist.size(); i++) {
                if (pieceslist.get(i).getPiecePiece().equalsIgnoreCase(pieces.getInclusDansPiece())) {
                    piecelist.setSelection(i);
                    break;
                }
            }
            checbox_other_piece.setChecked(true);
        } else {
            checbox_other_piece.setChecked(false);
            piecelist.setEnabled(false);
            piecelist.setBackgroundColor(ContextCompat.getColor(this, R.color.bg_gray));
        }

        BitmapDrawable bd = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.photo);
        imageview.getLayoutParams().height = bd.getBitmap().getHeight();
        imageview.getLayoutParams().width = bd.getBitmap().getWidth();
        if (pieces.getIdOperaPhoto() != 0) {
            OPERA_Photos_DAO photo_clef_DAO = new OPERA_Photos_DAO(mContext);
            OPERA_Photos opera_photos_piece = photo_clef_DAO.select(pieces.getIdOperaPhoto());
            imageview.setImageBitmap(BitmapFactory.decodeByteArray(opera_photos_piece.getPhoto(), 0, opera_photos_piece.getPhoto().length));
        }
    }


    public void fragmentGeneral(RelativeLayout layout) {
        longueur = (EditText) layout.findViewById(R.id.longueur);
        largeur = (EditText) layout.findViewById(R.id.largeur);
        hauteur = (EditText) layout.findViewById(R.id.hauteur);
        if (heightSize != 0)
            hauteur.setText(String.valueOf(heightSize));
        notes = (EditText) layout.findViewById(R.id.notes);
        piecelist = (Spinner) layout.findViewById(R.id.piecelist);
        checbox_other_piece = (CheckBox) layout.findViewById(R.id.checbox_other_piece);
        checbox_other_piece.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    piecelist.setEnabled(true);
                    piecelist.setBackgroundResource(R.drawable.spinner_ab_default);
                } else {
                    piecelist.setEnabled(false);
                    piecelist.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bg_gray));
                }
            }
        });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, list);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        piecelist.setAdapter(spinnerArrayAdapter);
        checbox_other_piece.setChecked(false);
    }

    public void callFermer(View v) {
        isValider = false;
        isForFinish = true;
        saveElementsPiece(v);
    }

    public void callValider(View v) {
        isValider = true;
        isForFinish = true;
        saveElementsPiece(v);
    }

    public void saveElementsPiece(View v) {

        elements_DAO = new CEL_Elements_DAO(mContext);

        String error_view;
        if (isValider) {

            if(hasElementsEau && (!hasBeenOnEtat || !hasBeenOnEau || !hasElementsInventaire)) {
                VALIDATED_ERROR = 1009;
            }
            else {

                cel_Actions_DAO = new CEL_Actions_DAO(mContext);
                elements_Obs_DAO = new CEL_Elements_Obs_DAO(mContext);
                elements_DAO = new CEL_Elements_DAO(mContext);
                elementsList = elements_DAO.selectAllElementsFromPiece(pieces.getIdPiece(), 4);
                elements_DAO.close();
                if (elementsList != null) {
                    for (int i = 0; i < elementsList.size(); i++) {
                        if (isValider) {
                            if (elementsList.get(i).getTypeElements() == null) {

                                VALIDATED_ERROR = 1002;
                                POSITION_ERROR = i;
                                IS_ETAT_PIECE_ERROR = elementsList.get(i).getItemGroupDescription();
                                break;
                            }
                            if (elementsList.get(i).getEtatElements() != null) {
                                if ((elementsList.get(i).getEtatElements().equals("ME")
                                        || elementsList.get(i).getEtatElements().equals("HS"))) {

                                    if (elements_Obs_DAO.selectAllCel_Elements_Obs(elementsList.get(i).getIdElements()).size() == 0) {

                                        VALIDATED_ERROR = 1001;
                                        POSITION_ERROR = i;
                                        IS_ETAT_PIECE_ERROR = elementsList.get(i).getItemGroupDescription();
                                        break;
                                    }
                                    /*if (cel_Actions_DAO.selectActions(elementsList.get(i).getIdElements()) == null) {

                                        VALIDATED_ERROR = 1000;
                                        POSITION_ERROR = i;
                                        IS_ETAT_PIECE_ERROR = elementsList.get(i).getItemGroupDescription();
                                        break;
                                    }
                                    if (elementsList.get(i).getIdOperaPhoto() == 0) {

                                        VALIDATED_ERROR = 1010;
                                        POSITION_ERROR = i;
                                        IS_ETAT_PIECE_ERROR = elementsList.get(i).getItemGroupDescription();
                                        break;
                                    }*/
                                } else if (elementsList.get(i).getEtatElements().equals("")) {

                                    VALIDATED_ERROR = 1003;
                                    POSITION_ERROR = i;
                                    IS_ETAT_PIECE_ERROR = elementsList.get(i).getItemGroupDescription();
                                    break;
                                }
                            } else {
                                VALIDATED_ERROR = 1003;
                                POSITION_ERROR = i;
                                IS_ETAT_PIECE_ERROR = elementsList.get(i).getItemGroupDescription();
                                break;
                            }
                            VALIDATED_ERROR = 0;
                        } else
                            VALIDATED_ERROR = 0;
                    }
                }
                cel_Actions_DAO.close();
                elements_Obs_DAO.close();
            }
            /*if (pieces.getInclusDansPiece() == null) {
                if (pieces.getLongueurPiece() == 0)
                    VALIDATED_ERROR = 1004;
                if (pieces.getLargeurPiece() == 0)
                    VALIDATED_ERROR = 1005;
                if (pieces.getHauteurPiece() == 0)
                    VALIDATED_ERROR = 1006;
            }
            if (pieces.getIdOperaPhoto() == 0)
                VALIDATED_ERROR = 1007;*/


            if (IS_ETAT_PIECE_ERROR == 1)
                error_view = getString(R.string.error_from_etat);
            else if (IS_ETAT_PIECE_ERROR == 2)
                error_view = getString(R.string.error_from_eau);
            else
                error_view = getString(R.string.error_from_inventaire);

        } else {
            VALIDATED_ERROR = 0;
            error_view = "";
        }

        switch (VALIDATED_ERROR) {
            case 1000:
                Toast.makeText(mContext, getString(R.string.etat_error) + " "
                        + elementsList.get(POSITION_ERROR).getElementElements() + " " + error_view, Toast.LENGTH_LONG).show();
                break;

            case 1001:
                Toast.makeText(mContext, getString(R.string.observation_missing) + " "
                        + elementsList.get(POSITION_ERROR).getElementElements() + " " + error_view, Toast.LENGTH_LONG).show();
                break;
            case 1002:
                Toast.makeText(mContext, getString(R.string.type_missing) + " "
                        + elementsList.get(POSITION_ERROR).getElementElements() + " " + error_view, Toast.LENGTH_LONG).show();
                break;

            case 1003:
                Toast.makeText(mContext, getString(R.string.etat_missing) + " "
                        + elementsList.get(POSITION_ERROR).getElementElements() + " " + error_view, Toast.LENGTH_LONG).show();
                break;
            case 1004:
                Toast.makeText(mContext, getString(R.string.longueur_missing), Toast.LENGTH_LONG).show();
                break;
            case 1005:
                Toast.makeText(mContext, getString(R.string.largeur_missing), Toast.LENGTH_LONG).show();
                break;
            case 1006:
                Toast.makeText(mContext, getString(R.string.hauteur_missing), Toast.LENGTH_LONG).show();
                break;
            case 1007:
                Toast.makeText(mContext, getString(R.string.picture_piece_missing), Toast.LENGTH_LONG).show();
                break;
            case 1008:
                Toast.makeText(mContext, getString(R.string.picture_element_missing) + " "
                        + elementsList.get(POSITION_ERROR).getElementElements(), Toast.LENGTH_LONG).show();
                break;
            case 1009:
                Toast.makeText(mContext, getString(R.string.sanitaire_piece_missing), Toast.LENGTH_LONG).show();
                break;

            case 1010:
                Toast.makeText(mContext, getString(R.string.photo_missing) + " "
                        + elementsList.get(POSITION_ERROR).getElementElements() + " " + error_view, Toast.LENGTH_LONG).show();
                break;
            default:
                if (isValider) {
                    pieces.setIsFinish(1);
                    CEL_Biens_DAO cel_biens_dao = new CEL_Biens_DAO(mContext);
                    pieces_DAO = new CEL_Piece_DAO(mContext, MissionsList.missionSelected.getIdMission(),
                            cel_biens_dao.select(MissionsList.missionSelected.getIdBien()));
                    pieces_DAO.updateValue(pieces);
                }
                if (isForFinish)
                    finish();
                break;
        }
    }


    private void updateGeneral() {

        if (text_title.getText().toString().length() == 0)
            pieces.setPiecePiece("");
        else
            pieces.setPiecePiece(text_title.getText().toString());

        if (notes.getText().toString().length() == 0)
            pieces.setNotesPiece("");
        else
            pieces.setNotesPiece(notes.getText().toString());

        if (longueur.getText().toString().length() == 0)
            pieces.setLongueurPiece(0);
        else
            pieces.setLongueurPiece(Float.parseFloat(longueur.getText().toString()));

        if (largeur.getText().toString().length() == 0)
            pieces.setLargeurPiece(0);
        else
            pieces.setLargeurPiece(Float.parseFloat(largeur.getText().toString()));

        if (hauteur.getText().toString().length() == 0)
            pieces.setHauteurPiece(0);
        else {
            pieces.setHauteurPiece(Float.parseFloat(hauteur.getText().toString()));
            heightSize = Float.parseFloat(hauteur.getText().toString());
        }

        if (checbox_other_piece.isChecked())
            pieces.setInclusDansPiece(piecelist.getSelectedItem().toString());
        else
            pieces.setInclusDansPiece(null);

        if (imageBitmapActivityResultPiece != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmapActivityResultPiece.compress(Bitmap.CompressFormat.PNG, 100, stream);

            String photo_name = MissionsList.missionSelected.getNumeroRdvMission()
                    + " - app -" + pieces.getNomPiece();

            OPERA_Photos opera_photos_piece = new OPERA_Photos(MissionsList.missionSelected.getIdMission(), stream.toByteArray(), photo_name);

            OPERA_Photos_DAO opera_photos_dao = new OPERA_Photos_DAO(mContext);
            if (pieces.getIdOperaPhoto() >= 0) {
                opera_photos_dao.deleteValue(pieces.getIdOperaPhoto());
            }

            opera_photos_piece.setIdPhotos(opera_photos_dao.addValue(opera_photos_piece));

            pieces.setIdOperaPhoto(opera_photos_piece.getIdPhotos());
        }
        CEL_Biens_DAO cel_biens_dao = new CEL_Biens_DAO(mContext);
        pieces_DAO = new CEL_Piece_DAO(mContext, MissionsList.missionSelected.getIdMission(),
                cel_biens_dao.select(MissionsList.missionSelected.getIdBien()));
        pieces_DAO.updateValue(pieces);
        pieces_DAO.close();
    }

    public void  callEtatPiece(View view) {
        updateGeneral();
        validerButton.setVisibility(View.VISIBLE);
        if (pieces.getIsFinish() == 1)
            fermerButton.setVisibility(View.INVISIBLE);
        else
            fermerButton.setVisibility(View.VISIBLE);
        //We switch View to frament_etat_piece
        dynamiclistitems.removeAllViews();
        isFragmentGeneral = false;
        tab_general.setBackgroundResource(R.drawable.menu_left);
        if (ITEM_GROUP_DESCRIPTION == 1) {
            tab_etat.setBackgroundResource(R.drawable.menuhover);
            tab_elements_eau.setBackgroundResource(R.drawable.menu_left);
            tab_inventaire.setBackgroundResource(R.drawable.menu_left);
        } else if (ITEM_GROUP_DESCRIPTION == 2) {
            tab_etat.setBackgroundResource(R.drawable.menu_left);
            tab_elements_eau.setBackgroundResource(R.drawable.menuhover);
            tab_inventaire.setBackgroundResource(R.drawable.menu_left);
        } else if (ITEM_GROUP_DESCRIPTION == 3) {
            tab_etat.setBackgroundResource(R.drawable.menu_left);
            tab_elements_eau.setBackgroundResource(R.drawable.menu_left);
            tab_inventaire.setBackgroundResource(R.drawable.menuhover);
        }
        final LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = new RelativeLayout(mActivity);
        layout = (RelativeLayout) layoutInflater.inflate(R.layout.fragment_etat_piece, (ViewGroup) layout.getParent(), false);
        progressBarEtatPiece = (ProgressBar) findViewById(R.id.progressBarEtatPiece);

        //Initializing ListView
        LinearLayout headerLinearLayout = (LinearLayout) layoutInflater.inflate(R.layout.etat_piece_header, (ViewGroup) layout.getParent(), false);

        button_ajouter = (Button) layout.findViewById(R.id.button_ajouter);
        button_ajouter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlertDialog.AlertDialogAddElement(0, mContext);
            }
        });

        listViewElements = (ListView) layout.findViewById(R.id.listview_elements);
        listViewElements.addHeaderView(headerLinearLayout);

        progressBarEtatPiece.setVisibility(View.VISIBLE);
        tab_general.setClickable(false);
        thread = new Thread() {
            public void run() {

                //Get existing CEL_Elements
                elements_DAO = new CEL_Elements_DAO(mContext);
                elementsList = elements_DAO.selectAllElementsFromPiece(pieces.getIdPiece(), ITEM_GROUP_DESCRIPTION);


                //Set Adapter for ListView
                if (checkConditionForElements()) {

                    //There is no CEL_Elements for this CEL_Piece, we set default elements from Room/RoomItem/RoomDamage
                    roomItemList = new ArrayList<>();

                    //We get all data from this Room
                    roomItem_DAO = new RoomItem_DAO(mContext);
                    roomItemList = roomItem_DAO.selectAllRoomItems(ROOM_ID, false, ITEM_GROUP_DESCRIPTION);
                    roomItem_DAO.close();
                    ROOM_ITEM_SPARSE_ARRAY = new SparseArray<>();
                    CEL_Elements_DAO elements_DAO = new CEL_Elements_DAO(mContext);
                    CEL_Elements cel_Elements = new CEL_Elements();
                    int ordre = 0;
                    for (RoomItem roomItem : roomItemList) {
                        if (roomItem.getDisplayRoomItem().equals("initial") ||
                                roomItem.getDisplayRoomItem().equals("always")) {

                            cel_Elements.setElementElements(roomItem.getDescriptionString());
                            cel_Elements.setIdRoomItem(roomItem.getIdRoomItem());
                            if (roomItem.getCountableRoomItem().equals("yes"))
                                cel_Elements.setIsCountable(1);
                            else
                                cel_Elements.setIsCountable(0);
                            cel_Elements.setIdPiece(pieces.getIdPiece());
                            cel_Elements.setItemGroupDescription(roomItem.getItemGroupDescription());
                            if (roomItem.getDisplayRoomItem().equals("always")) {
                                cel_Elements.setMandatory(1);
                            }
                            else {
                                cel_Elements.setMandatory(0);
                            }
                            cel_Elements.setQuantiteElements(1);
                            cel_Elements.setOrdre(ordre);
                            ordre++;
                            elements_DAO.addValue(cel_Elements);
                        }
                    }
                }

                if (elementsList == null || elementsList.isEmpty()) {
                    elementsList = new ArrayList<>();
                    elementsList = elements_DAO.selectAllElementsFromPiece(pieces.getIdPiece(), ITEM_GROUP_DESCRIPTION);
                }
               /* if(MissionsList.missionSelected.getType_edl()!=0)
                {
                    findViewById(R.id.nettoyagelement_CheckBox).setVisibility(View.VISIBLE);
                    findViewById(R.id.ent_tr_text).setVisibility(View.VISIBLE);
                }*/
                roomItemList = new ArrayList<>();
                colorValuesSparseIntArray = new SparseIntArray();
                ETAT_SPARSE_ARRAY = new SparseArray<>();
                cel_ElementsArrayAdapter = new CEL_ElementsArrayAdapter(mContext, R.layout.etat_piece_item,
                        elementsList, ROOM_ID);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listViewElements.setAdapter(cel_ElementsArrayAdapter);
                    }
                });

                fragmentHandler.sendMessage(Message.obtain(fragmentHandler, MESSAGE_DOWNLOAD_COMPLETE));
            }
        };

        thread.start();
    }


    public boolean checkConditionForElements() {

        //check if the list is ot null or empty
        if (elementsList == null || elementsList.isEmpty()) {
            //check if user hasn't already modified the CEL_Piece
            if (!pieces.isHasUserStartedDeletion()) {
                if (!pieces.isHasUserStartedReport() && pieces.getInclusDansPiece() != null && ITEM_GROUP_DESCRIPTION != 2) {
                    return false;
                } else {
                    return true;
                }

            }
        }


        return false;
    }

    /**
     * This is the Handler for this activity. It will receive messages from the
     * thread and make the necessary updates to the UI.
     */
    public static Handler fragmentHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                /**
                 * Handling MESSAGE_UPDATE_PROGRESS_BAR:
                 * 1. Get the current progress, as indicated in the arg1 field
                 *    of the Message.
                 * 2. Update the progress bar.
                 */
                case MESSAGE_UPDATE_PROGRESS_BAR:
                    if (progressBarEtatPiece != null) {
                        int currentProgress = msg.arg1;
                        progressBarEtatPiece.setProgress(currentProgress);
                    }
                    break;

                /**
                 * Handling MESSAGE_DOWNLOAD_COMPLETE:
                 * Reload ListView when download is complete
                 */
                case MESSAGE_DOWNLOAD_COMPLETE:

                    if (cel_ElementsArrayAdapter.isEmpty())
                        button_ajouter.setVisibility(View.VISIBLE);
                    else
                        button_ajouter.setVisibility(View.GONE);

                    progressBarEtatPiece.setVisibility(View.INVISIBLE);
                    dynamiclistitems.removeAllViews();
                    dynamiclistitems.addView(layout);
                    tab_general.setClickable(true);
                    break;
            }
        }
    };


    public void genralFileds(RelativeLayout layout) {
        text_title = (TextView) layout.findViewById(R.id.text_title);
        TextView text_notes = (TextView) layout.findViewById(R.id.text_notes);
        TextView text_largeur = (TextView) layout.findViewById(R.id.text_largeur);
        TextView text_longueur = (TextView) layout.findViewById(R.id.text_longueur);
        TextView text_hauteur = (TextView) layout.findViewById(R.id.text_hauteur);
        CheckBox text_chk = (CheckBox) layout.findViewById(R.id.checbox_other_piece);
        imageview = (ImageView) layout.findViewById(R.id.imageview);

        text_title.setTypeface(bold);
        text_notes.setTypeface(normal);
        text_largeur.setTypeface(normal);
        text_longueur.setTypeface(normal);
        text_hauteur.setTypeface(normal);
        text_chk.setTypeface(normal);

        fragmentGeneral(layout);
    }

    public void callSaveElements(View v) {
        if (CURRENT_POSITION != 0) {
            isForFinish = false;
            isValider = false;
            saveElementsPiece(v);
        }
    }

    public void callGeneralLayout() {
        validerButton.setVisibility(View.INVISIBLE);
        fermerButton.setVisibility(View.INVISIBLE);
        if (progressBarEtatPiece != null)
            progressBarEtatPiece.setVisibility(View.GONE);
        isFragmentGeneral = true;
        tab_general.setBackgroundResource(R.drawable.menuhover);
        tab_etat.setBackgroundResource(R.drawable.menu_right);

        dynamiclistitems.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = new RelativeLayout(mActivity);
        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_piece_general, (ViewGroup) layout.getParent(), false);
        dynamiclistitems.setPadding(10, 50, 10, 10);
        genralFileds(layout);

        dynamiclistitems.addView(layout);
    }

    public void calCameraAction(View v) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, pictureFromCamera);
        mImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(cameraIntent, REQUEST_CAMERA_FOR_PIECE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CAMERA_FOR_PIECE:
                if (resultCode == RESULT_OK) {
                    try {
                        saveToInternalStorage(MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri), true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case REQUEST_CAMERA_FOR_ELEMENT:
                if (resultCode == RESULT_OK) {
                    if (!isPictureSaved) {
                        listImg.setTag("true");

                        try {
                            saveToInternalStorage(MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri), false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        isPictureSaved = true;
                    }
                }
                break;
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("cameraImageUri")) {
            mImageUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mImageUri != null) {
            outState.putString("cameraImageUri", mImageUri.toString());
        }
    }

    private void saveToInternalStorage(Bitmap bitmapImage, boolean isForPiece) {

        imageBitmapActivityResultPiece = bitmapImage;

        File imageToUpload = new File(getFilesDir(), "imageName.png");

        FileOutputStream outputStream;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        if (BitmapCompat.getAllocationByteCount(imageBitmapActivityResultPiece) > 2000000) {

            do {
                imageBitmapActivityResultPiece = Bitmap.createScaledBitmap(imageBitmapActivityResultPiece, (int) (imageBitmapActivityResultPiece.getWidth() * 0.9), (int) (imageBitmapActivityResultPiece.getHeight() * 0.9), true);
            }
            while (BitmapCompat.getAllocationByteCount(imageBitmapActivityResultPiece) > 2000000);
        }

        try {
            outputStream = openFileOutput(imageToUpload.getName(), Context.MODE_PRIVATE);
            outputStream.write(stream.toByteArray());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isForPiece) {
            imageview.setImageBitmap(imageBitmapActivityResultPiece);
            imageBitmapActivityResultElement = null;
        }
        else {
            imageBitmapActivityResultElement = imageBitmapActivityResultPiece;
            imageBitmapActivityResultPiece = null;

            listImg.setImageBitmap(imageBitmapActivityResultElement);
            CEL_ELEMENT = elementsList.get(position_picture);

            if (CEL_ELEMENT != null && listImg.getTag().equals("true")) {

                imageBitmapActivityResultElement.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                String photo_name = MissionsList.missionSelected.getNumeroRdvMission()
                        + " - app - " + elementsList.get(position_picture).getIdElements() + " - "
                        + elementsList.get(position_picture).getElementElements();

                OPERA_Photos_DAO opera_photos_dao = new OPERA_Photos_DAO(mContext);
                if (CEL_ELEMENT.getIdOperaPhoto() >= 0) {
                    opera_photos_dao.deleteValue(CEL_ELEMENT.getIdOperaPhoto());
                }

                OPERA_Photos opera_photos_element = new OPERA_Photos(MissionsList.missionSelected.getIdMission(), byteArray, photo_name);
                CEL_ELEMENT.setIdOperaPhoto(opera_photos_dao.addValue(opera_photos_element));

                elements_DAO.updateValue(CEL_ELEMENT);
                elements_DAO.close();

                elementsList.get(position_picture).setIdOperaPhoto(CEL_ELEMENT.getIdOperaPhoto());
            }

            cel_ElementsArrayAdapter.notifyDataSetChanged();

        }

    }
}
