package com.appsolute.cel.adapter;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appsolute.cel.DAO.CEL_Actions_DAO;
import com.appsolute.cel.DAO.CEL_Biens_DAO;
import com.appsolute.cel.DAO.CEL_Elements_DAO;
import com.appsolute.cel.DAO.CEL_Elements_Obs_DAO;
import com.appsolute.cel.DAO.CEL_Piece_DAO;
import com.appsolute.cel.DAO.ItemType_DAO;
import com.appsolute.cel.DAO.ItemsPack_DAO;
import com.appsolute.cel.DAO.OPERA_Photos_DAO;
import com.appsolute.cel.DAO.PackElement_DAO;
import com.appsolute.cel.DAO.RoomItem_DAO;
import com.appsolute.cel.DAO.Room_Damage_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.activity.EtatDesLieux;
import com.appsolute.cel.activity.MissionsList;
import com.appsolute.cel.activity.PieceActivity;
import com.appsolute.cel.models.CEL_Actions;
import com.appsolute.cel.models.CEL_Elements;
import com.appsolute.cel.models.CEL_Elements_Obs;
import com.appsolute.cel.models.CEL_Piece;
import com.appsolute.cel.models.ItemType;
import com.appsolute.cel.models.ItemsPack;
import com.appsolute.cel.models.Observation;
import com.appsolute.cel.models.PackElement;
import com.appsolute.cel.models.RoomDamage;
import com.appsolute.cel.models.RoomItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static com.appsolute.cel.BaseActivity.mContext;
import static com.appsolute.cel.activity.PieceActivity.cel_ElementsArrayAdapter;

public class CustomAlertDialog {

    private static String actionval = "";
    private static RoomItem roomItem;
    private static ItemsPack itemsPack;
    private static String selectedEtat;
    private static AlertDialog alertDialog;
    private static Boolean isCheck = true;


    /**
     * Show an Alert Dialog to allow user to delete or add a new
     * row on listView, for no XML Adapter
     *
     * @param position
     */
    static void AlertDialogElements(final int position, final Context context,
                                    final List<CEL_Elements> listCEL_Elements) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_piece_element);
        dialog.setTitle(context.getString(R.string.app_name));

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });

        final Button dialogButtonDeleteElement = (Button) dialog.findViewById(R.id.dialogButtonDelete);
        final Button dialogButtonAddElement = (Button) dialog.findViewById(R.id.dialogButtonAdd);
        final Button dialogButtonAddPack = (Button) dialog.findViewById(R.id.dialogButtonAddPack);

        dialogButtonDeleteElement.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                CEL_Elements_DAO cel_Elements_DAO = new CEL_Elements_DAO(context);

                if (listCEL_Elements.get(position).getMandatory() == 1) {

                    if (!cel_Elements_DAO.checkExistingDuplicationElement(
                            listCEL_Elements.get(position).getElementElements(),
                            listCEL_Elements.get(position).getIdPiece())) {
                        Toast.makeText(context, context.getResources().getString(R.string.impossible_to_delete), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }
                }
                cel_Elements_DAO.deleteValue(listCEL_Elements.get(position).getIdElements());
                cel_Elements_DAO.close();

                OPERA_Photos_DAO opera_photos_dao = new OPERA_Photos_DAO(context);
                opera_photos_dao.deleteValue(listCEL_Elements.get(position).getIdOperaPhoto());

                listCEL_Elements.remove(position);
                updateOrderElement(mContext);
                cel_ElementsArrayAdapter.notifyDataSetChanged();
                if (cel_ElementsArrayAdapter.isEmpty())
                    PieceActivity.button_ajouter.setVisibility(View.VISIBLE);
                else
                    PieceActivity.button_ajouter.setVisibility(View.INVISIBLE);

                PieceActivity.pieces.setHasUserStartedDeletion(true);
                CEL_Biens_DAO cel_biens_dao = new CEL_Biens_DAO(mContext);
                CEL_Piece_DAO pieces_DAO = new CEL_Piece_DAO(mContext, MissionsList.missionSelected.getIdMission(),
                        cel_biens_dao.select(MissionsList.missionSelected.getIdBien()));
                pieces_DAO.updateValue(PieceActivity.pieces);
                dialog.dismiss();
            }
        });

        dialogButtonAddPack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AlertDialogAddPackElement(position, context);
            }
        });

        dialogButtonAddElement.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AlertDialogAddElement(position, context);
            }
        });

        dialog.show();
    }


    static void AlertDialogAction(final TextView actionElement_TextView, final Context context,
                                  final int idElement) {

        final List<String> unite_list = new ArrayList<>();
        unite_list.add(context.getString(R.string.etat_unite));
        unite_list.add(context.getString(R.string.etat_m2));
        unite_list.add(context.getString(R.string.etat_m3));
        unite_list.add(context.getString(R.string.etat_m1));

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_piece_action);
        dialog.setCancelable(false);
        dialog.setTitle(context.getString(R.string.app_name));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        final RadioGroup etat_choose = (RadioGroup) dialog.findViewById(R.id.etat_choose);
        final EditText quantite = (EditText) dialog.findViewById(R.id.quantite);
        final EditText note = (EditText) dialog.findViewById(R.id.note);
        final Spinner unite = (Spinner) dialog.findViewById(R.id.unite);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, unite_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unite.setAdapter(dataAdapter);

        //We fill alert dialog if action is already selected
        CEL_Actions_DAO actions_DAO = new CEL_Actions_DAO(context);
        CEL_Actions actions = actions_DAO.selectActions(idElement);
        actions_DAO.close();
        if (actions != null) {
            int idActionSelected = 0;
            switch (actions.getActionActions()) {
                case "Réparer / Réviser /Entretenir":
                    idActionSelected = R.id.reparer;
                    break;
                case "Changer / Remplacer":
                    idActionSelected = R.id.changer;
                    break;
                case "Repeindre":
                    idActionSelected = R.id.repeindre;
                    break;
                case "Refixer":
                    idActionSelected = R.id.refixer;
                    break;
                case "Débarrasser / Vider / Supprimer":
                    idActionSelected = R.id.debarrasser;
                    break;
                default:
                    idActionSelected = R.id.ancune;
                    break;
            }
            etat_choose.check(idActionSelected);
            quantite.setText(String.valueOf(actions.getQuantiteActions()));
            int positionUniteSelected = 0;
            switch (actions.getUniteActions()) {
                case "m²":
                    positionUniteSelected = 1;
                    break;
                case "m3":
                    positionUniteSelected = 2;
                    break;
                case "ml":
                    positionUniteSelected = 3;
                    break;
                default:
                    positionUniteSelected = 0;
                    break;
            }
            unite.setSelection(positionUniteSelected);
            note.setText(actions.getNoteActions());
        }
        isCheck = true;
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = etat_choose.getCheckedRadioButtonId();
                actionval = "";
                switch (selectedId) {
                    case R.id.reparer:
                        actionval = context.getString(R.string.etat_reparer);
                        break;
                    case R.id.changer:
                        actionval = context.getString(R.string.etat_changer);
                        break;
                    case R.id.repeindre:
                        actionval = context.getString(R.string.etat_repeindre);
                        break;
                    case R.id.refixer:
                        actionval = context.getString(R.string.etat_refixer);
                        break;
                    case R.id.debarrasser:
                        actionval = context.getString(R.string.etat_debarrasser);
                        break;
                    case R.id.ancune:
                        actionElement_TextView.setText("");
                        CEL_Actions_DAO actions_DAO = new CEL_Actions_DAO(context);
                        actions_DAO.deleteValueFromIdElement(idElement);
                        actions_DAO.close();
                        cel_ElementsArrayAdapter.notifyDataSetChanged();
                        isCheck = false;
                        dialog.dismiss();
                        break;
                }
                if (isCheck) {
                    if (actionval.length() > 0 && quantite.getText().toString().length() > 0) {

                        CEL_Actions actions = new CEL_Actions();
                        actions.setActionActions(actionval);
                        actions.setQuantiteActions(Float.parseFloat(quantite.getText().toString()));
                        actions.setUniteActions(unite.getSelectedItem().toString());
                        actions.setNoteActions(note.getText().toString());
                        actions.setIdElement(idElement);
                        CEL_Actions_DAO actions_DAO = new CEL_Actions_DAO(context);
                        actions_DAO.deleteValueFromIdElement(idElement);
                        actions_DAO.addValue(actions);
                        actions_DAO.close();
                        cel_ElementsArrayAdapter.notifyDataSetChanged();
                        dialog.dismiss();

                    } else
                        Toast.makeText(context, context.getResources().getString(R.string.impossible_save_action), Toast.LENGTH_LONG).show();
                }
            }
        });
        dialogCancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    /**
     * This method allow user to edit quantity of a TextView, currently used of element quantity
     * and TrouDeCheville
     *
     * @param quantityTextView
     * @param context
     * @param positionGetView
     */
    static void EditQuantityElement(final TextView quantityTextView,
                                    final Context context,
                                    final int positionGetView,
                                    final boolean isTrouDeCheville) {

        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_edit_element_quantity);
        dialog.setTitle(context.getString(R.string.etat_quantite_tit));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        final EditText editTextElementQuantity = (EditText) dialog.findViewById(R.id.editTextElementQuantity);
        editTextElementQuantity.setText(String.valueOf(
                PieceActivity
                        .cel_ElementsArrayAdapter
                        .getCEL_Elements(positionGetView)
                        .getQuantiteElements()));

        Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
        Button buttonValidate = (Button) dialog.findViewById(R.id.buttonValidate);


        final InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        buttonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });
        buttonValidate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityTextView.setText(editTextElementQuantity.getText().toString());
                CEL_Elements_DAO cel_elements_dao = new CEL_Elements_DAO(context);
                if (isTrouDeCheville) {

                    PieceActivity
                            .cel_ElementsArrayAdapter
                            .getCEL_Elements(positionGetView)
                            .setNombreTrousChevilleElements(Integer.valueOf(editTextElementQuantity.getText().toString()));

                    cel_elements_dao.updateValue(PieceActivity
                            .cel_ElementsArrayAdapter
                            .getCEL_Elements(positionGetView));
                } else {
                    cel_ElementsArrayAdapter
                            .getCEL_Elements(positionGetView)
                            .setQuantiteElements(Integer.valueOf(editTextElementQuantity.getText().toString()));

                    cel_elements_dao.updateValue(PieceActivity
                            .cel_ElementsArrayAdapter
                            .getCEL_Elements(positionGetView));
                }
                dialog.dismiss();
                inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });

        dialog.show();
    }


    /**
     * Show an Alert Dialog to add dynamically observation
     * for one Element
     *
     * @param ent_obervations_val editText where observations will be displayed
     * @param observationsList    Observation list corresponding to RoomDamage
     * @param context
     * @param positionGetView     position of item on listView
     */
    static void AlertDialogAddObservations(final TextView ent_obervations_val, final List<Observation> observationsList,
                                           final Context context, final int positionGetView, final int idItemType) {

        final int idElement = cel_ElementsArrayAdapter.getCEL_Elements(positionGetView).getIdElements();

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_observation);
        dialog.setTitle(context.getString(R.string.obs_text));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        TextView cancelbt = (TextView) dialog.findViewById(R.id.cancelbt);
        TextView okbt = (TextView) dialog.findViewById(R.id.okbt);
        Button obs_ajouter_btn = (Button) dialog.findViewById(R.id.obs_ajouter_btn);
        final EditText obs_ajouter_val = (EditText) dialog.findViewById(R.id.obs_ajouter_val);
        final GridView gridview = (GridView) dialog.findViewById(R.id.grid_view2);

        final CEL_Elements_Obs_DAO cel_Elements_Obs_DAO = new CEL_Elements_Obs_DAO(context);
        final List<CEL_Elements_Obs> observationListSaveState = cel_Elements_Obs_DAO.selectAllCel_Elements_Obs(idElement);

        for (Observation observation : observationsList) {
            for (String observationName : cel_Elements_Obs_DAO.getObservationsList(idElement)) {
                if (observation.getName().equals(observationName)) {
                    observation.setSelected(true);
                }
            }
        }


        callAlertLayout(observationsList, gridview, context, positionGetView);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = 800; //WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);


        cancelbt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                cel_Elements_Obs_DAO.deleteValueData(idElement);
                for (CEL_Elements_Obs cel_elements_obs : observationListSaveState) {
                    cel_Elements_Obs_DAO.addValue(cel_elements_obs);
                }

                dialog.dismiss();
            }
        });

        okbt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                ent_obervations_val.setText("");
                ent_obervations_val.setText(cel_Elements_Obs_DAO.getObservationsString(idItemType));
                cel_ElementsArrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        obs_ajouter_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String newObservation = obs_ajouter_val.getText().toString();

                if(!newObservation.isEmpty()){
                    observationsList.add(new Observation(newObservation));

                    callAlertLayout(observationsList, gridview, context, positionGetView);
                    obs_ajouter_val.getText().clear();

                    RoomDamage newRoomDamage = new RoomDamage();
                    newRoomDamage.setDescriptionRoom(newObservation);
                    newRoomDamage.setMandatoryRoom("no");
                    newRoomDamage.setIdItemType(idItemType);
                    Room_Damage_DAO roomDamage_DAO = new Room_Damage_DAO(context);
                    roomDamage_DAO.addValue(newRoomDamage);
                }




            }
        });
    }

    private static void callAlertLayout(final List<Observation> observationsList, final GridView gridView,
                                        Context context, final int positionGetview) {
        gridView.setPadding(10, 15, 10, 15);
        gridView.setAdapter(new ObservationsAdapter(observationsList, context, cel_ElementsArrayAdapter.getCEL_Elements(positionGetview).getIdElements()));
    }


    /**
     * Show an Alert Dialog to add dynamically new Element on the list.
     * Element correspond to a specific Piece, from XML file
     */
    public static void AlertDialogAddElement(final int position, final Context context) {

        RoomItem_DAO roomItem_DAO = new RoomItem_DAO(context);
        final List<RoomItem> roomItemList = roomItem_DAO.selectAllRoomItems(PieceActivity.ROOM_ID, true, PieceActivity.ITEM_GROUP_DESCRIPTION);
        roomItem_DAO.close();
        List<String> listItems = new ArrayList<String>();
        for (RoomItem roomItem : roomItemList) {
            listItems.add(roomItem.getDescriptionString());
        }

        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
        final Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(context.getString(R.string.element_choose_tit));
        builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (roomItem == null)
                    Toast.makeText(context, context.getResources().getString(R.string.select_element), Toast.LENGTH_SHORT).show();
                else {

                    if (cel_ElementsArrayAdapter.isEmpty() && PieceActivity.ITEM_GROUP_DESCRIPTION != 2)
                        EtatDesLieux.pieces.setHasUserStartedReport(true);

                    LinkedHashMap<RoomItem, LinkedHashMap<ItemType, List<RoomDamage>>> newElement = new LinkedHashMap<>();
                    ItemType_DAO itemType_DAO = new ItemType_DAO(context);
                    List<ItemType> itemTypeList = itemType_DAO.selectItemType(roomItem.getIdRoomItem());
                    itemType_DAO.close();
                    LinkedHashMap<ItemType, List<RoomDamage>> itemTypeLinkedHashMap = new LinkedHashMap<>();
                    for (int j = 0; j < itemTypeList.size(); j++) {
                        Room_Damage_DAO roomDamage_DAO = new Room_Damage_DAO(context);
                        List<RoomDamage> roomDamageList = roomDamage_DAO.selectData(itemTypeList.get(j).getIdItemType());
                        roomDamage_DAO.close();
                        itemTypeLinkedHashMap.put(itemTypeList.get(j), roomDamageList);
                    }
                    newElement.put(roomItem, itemTypeLinkedHashMap);
                    CEL_Elements cel_Elements = new CEL_Elements();
                    cel_Elements.setIdRoomItem(roomItem.getIdRoomItem());
                    cel_Elements.setIdPiece(EtatDesLieux.pieces.getIdPiece());
                    cel_Elements.setElementElements(roomItem.getDescriptionString());
                    cel_Elements.setItemGroupDescription(PieceActivity.ITEM_GROUP_DESCRIPTION);
                    if (roomItem.getCountableRoomItem().equals("yes")) {
                        cel_Elements.setIsCountable(1);
                        cel_Elements.setQuantiteElements(1);
                    }

                    if (roomItem.getDisplayRoomItem().equals("always")) {
                        cel_Elements.setMandatory(1);
                    } else {
                        cel_Elements.setMandatory(0);
                    }


                    CEL_Elements_DAO cel_Elements_DAO = new CEL_Elements_DAO(context);
                    cel_Elements.setIdElements(cel_Elements_DAO.addValue(cel_Elements));
                    cel_Elements_DAO.close();
                    if (cel_ElementsArrayAdapter.listCEL_Elements.size() == 0) {
                        cel_ElementsArrayAdapter.listCEL_Elements.add(cel_Elements);
                        PieceActivity.button_ajouter.setVisibility(View.INVISIBLE);
                    } else {
                        cel_ElementsArrayAdapter.listCEL_Elements.add(position + 1, cel_Elements);
                    }
                    updateOrderElement(context);
                    cel_ElementsArrayAdapter.notifyDataSetChanged();


                    dialog.dismiss();
                    Toast.makeText(context, context.getResources().getString(R.string.success_add_element), Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                roomItem = roomItemList.get(which);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     * Show an Alert Dialog to add dynamically new ItemsPack on the list.
     *
     * @param position
     * @param context
     */
    public static void AlertDialogAddPackElement(final int position, final Context context) {

        ItemsPack_DAO itemsPack_DAO = new ItemsPack_DAO(context);
        final List<ItemsPack> itemsPackList = itemsPack_DAO.getListItemsPack(PieceActivity.ROOM_ID);
        itemsPack_DAO.close();
        List<String> listItemsPack = new ArrayList<String>();
        for (ItemsPack itemsPack : itemsPackList) {
            listItemsPack.add(itemsPack.getDescription());
        }

        final CharSequence[] items = listItemsPack.toArray(new CharSequence[listItemsPack.size()]);
        final Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(context.getString(R.string.element_choose_tit));
        builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (itemsPack == null)
                    Toast.makeText(context, context.getResources().getString(R.string.select_pack_element), Toast.LENGTH_SHORT).show();
                else {
                    PackElement_DAO packElement_DAO = new PackElement_DAO(context);
                    List<PackElement> packElementArrayList = packElement_DAO.getAllPackElementFromItemPack(itemsPack.getIdItemsPack());
                    int i = 0;
                    for (PackElement packElement : packElementArrayList) {
                        RoomItem_DAO roomItem_DAO = new RoomItem_DAO(context);
                        roomItem = roomItem_DAO.getRoomItemFromParameters(PieceActivity.ROOM_ID, packElement.getItem());
                        if (roomItem != null && roomItem.getIdRoom() != 0) {
                            LinkedHashMap<RoomItem, LinkedHashMap<ItemType, List<RoomDamage>>> newElement = new LinkedHashMap<>();
                            ItemType_DAO itemType_DAO = new ItemType_DAO(context);
                            List<ItemType> itemTypeList = itemType_DAO.selectItemType(roomItem.getIdRoomItem());
                            itemType_DAO.close();
                            LinkedHashMap<ItemType, List<RoomDamage>> itemTypeLinkedHashMap = new LinkedHashMap<>();
                            for (int j = 0; j < itemTypeList.size(); j++) {
                                Room_Damage_DAO roomDamage_DAO = new Room_Damage_DAO(context);
                                List<RoomDamage> roomDamageList = roomDamage_DAO.selectData(itemTypeList.get(j).getIdItemType());
                                roomDamage_DAO.close();
                                itemTypeLinkedHashMap.put(itemTypeList.get(j), roomDamageList);
                            }
                            newElement.put(roomItem, itemTypeLinkedHashMap);
                            CEL_Elements cel_Elements = new CEL_Elements();
                            cel_Elements.setIdRoomItem(roomItem.getIdRoomItem());
                            cel_Elements.setIdPiece(EtatDesLieux.pieces.getIdPiece());
                            cel_Elements.setElementElements(roomItem.getDescriptionString());
                            cel_Elements.setTypeElements(packElement.getType());
                            cel_Elements.setIdItemType(packElement.getIdItemType());
                            cel_Elements.setItemGroupDescription(PieceActivity.ITEM_GROUP_DESCRIPTION);
                            if (roomItem.getCountableRoomItem().equals("yes")) {
                                cel_Elements.setIsCountable(1);
                                cel_Elements.setQuantiteElements(1);
                            }
                            if (roomItem.getDisplayRoomItem().equals("always")) {
                                cel_Elements.setMandatory(1);
                            } else {
                                cel_Elements.setMandatory(0);
                            }
                            CEL_Elements_DAO cel_Elements_DAO = new CEL_Elements_DAO(context);
                            cel_Elements.setIdElements(cel_Elements_DAO.addValue(cel_Elements));
                            cel_Elements_DAO.close();
                            cel_ElementsArrayAdapter.listCEL_Elements.add(position + 1 + i, cel_Elements);
                            updateOrderElement(context);
                            cel_ElementsArrayAdapter.notifyDataSetChanged();
                        }
                        i++;
                    }
                    dialog.dismiss();
                    Toast.makeText(context, context.getResources().getString(R.string.success_add_element), Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemsPack = itemsPackList.get(which);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private static void updateOrderElement(Context context) {

        int order = 0;
        for (CEL_Elements elements : cel_ElementsArrayAdapter.listCEL_Elements) {
            elements.setOrdre(order);
            order++;
            CEL_Elements_DAO cel_Elements_DAO = new CEL_Elements_DAO(context);
            cel_Elements_DAO.updateValue(elements);
            cel_Elements_DAO.close();
        }
    }

    /**
     * Show an Alert Dialog to select dynamically the type of an element.
     *
     * @param context
     * @param nameItem
     * @param typeList
     */
    public static void AlertDialogSelectTypeElement(final Context context, String nameItem,
                                                    final List<ItemType> typeList,
                                                    final CEL_Elements cel_Elements) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        GridView gridView = new GridView(context);

        gridView.setAdapter(new TypeElementArrayAdapter(context, R.layout.dialog_type_element, typeList));
        gridView.setNumColumns(3);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView_dialog_type_element = (TextView) view.findViewById(R.id.textView_dialog_type_element);
                cel_Elements.setTypeElements(textView_dialog_type_element.getText().toString());
                cel_Elements.setIdItemType(typeList.get(position).getIdItemType());
                CEL_Elements_DAO cel_Elements_DAO = new CEL_Elements_DAO(context);
                cel_Elements_DAO.updateValue(cel_Elements);
                cel_Elements_DAO.close();
                cel_ElementsArrayAdapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });
        builder.setView(gridView);
        builder.setTitle(nameItem);
        alertDialog = builder.create();
        alertDialog.show();
    }


    public static void callChooseEtat(final TextView ent_etat_val,
                                      final Context context, final CEL_Elements cel_Elements, final Boolean isXML) {

        final String[] etats = {context.getString(R.string.etat_neuf).replace("\n", " "),
                context.getString(R.string.etat_bon).replace("\n", " "),
                context.getString(R.string.etat_usage).replace("\n", " "),
                context.getString(R.string.etat_mauvais).replace("\n", " "),
                context.getString(R.string.etat_hors).replace("\n", " "),
                context.getString(R.string.etat_non).replace("\n", " ")
        };
        final List<String> etatList = Arrays.asList(etats);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        GridView gridView = new GridView(context);

        gridView.setAdapter(new EtatElementArrayAdapter(context, R.layout.dialog_etat_element, etatList));
        gridView.setNumColumns(4);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedEtat = etatList.get(position);
                String etatString = "";
                if (context.getString(R.string.etat_non).replace("\n", " ").equals(selectedEtat)) {
                    ent_etat_val.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_no_verified));
                    etatString = context.getString(R.string.etat_nv).replace("\n", " ");
                } else if (context.getString(R.string.etat_neuf).replace("\n", " ").equals(selectedEtat)) {
                    ent_etat_val.setBackgroundColor(ContextCompat.getColor(context, R.color.green_neuf));
                    etatString = context.getString(R.string.etat_nf).replace("\n", " ");
                } else if (context.getString(R.string.etat_hors).replace("\n", " ").equals(selectedEtat)) {
                    ent_etat_val.setBackgroundColor(ContextCompat.getColor(context, R.color.red_hs));
                    etatString = context.getString(R.string.etat_hs).replace("\n", " ");
                } else if (context.getString(R.string.etat_bon).replace("\n", " ").equals(selectedEtat)) {
                    ent_etat_val.setBackgroundColor(ContextCompat.getColor(context, R.color.green_bon_etat));
                    etatString = context.getString(R.string.etat_be).replace("\n", " ");
                } else if (context.getString(R.string.etat_mauvais).replace("\n", " ").equals(selectedEtat)) {
                    ent_etat_val.setBackgroundColor(ContextCompat.getColor(context, R.color.orange_mauvais_etat));
                    etatString = context.getString(R.string.etat_me).replace("\n", " ");
                } else if (context.getString(R.string.etat_usage).replace("\n", " ").equals(selectedEtat)) {
                    ent_etat_val.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_etat_usage));
                    etatString = context.getString(R.string.etat_eu).replace("\n", " ");
                }
                ent_etat_val.setText(etatString);
                if (!isXML) {
                    cel_Elements.setEtatElements(etatString);
                    CEL_Elements_DAO cel_Elements_DAO = new CEL_Elements_DAO(context);
                    cel_Elements_DAO.updateValue(cel_Elements);
                    cel_ElementsArrayAdapter.notifyDataSetChanged();
                }
                alertDialog.dismiss();
            }
        });
        builder.setView(gridView);
        builder.setTitle(context.getString(R.string.etat_title));
        alertDialog = builder.create();
        alertDialog.show();

    }

    public static void alertDialogDuplicatePiece(final Context context) {

        //Initialize custom alert dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_dialog_save_licence);
        dialog.setTitle(R.string.dupliquer);
        final EditText code_licence_EditText = (EditText) dialog.findViewById(R.id.code_licence_EditText);

        code_licence_EditText.setText(EtatDesLieux.pieces.getNomTypePiece());
        code_licence_EditText.setInputType(InputType.TYPE_CLASS_TEXT);
        Selection.setSelection(code_licence_EditText.getText(), code_licence_EditText.getText().length());
        code_licence_EditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().contains(EtatDesLieux.pieces.getNomTypePiece())) {
                    code_licence_EditText.setText(EtatDesLieux.pieces.getNomTypePiece());
                    Selection.setSelection(code_licence_EditText.getText(), code_licence_EditText.getText().length());
                }
            }
        });
        final TextView code_licence_TextView = (TextView) dialog.findViewById(R.id.code_licence_TextView);
        code_licence_TextView.setText(R.string.duplicate_piece);

        //On click on cancel button we dismiss alert dialog
        TextView cancel_TextView = (TextView) dialog.findViewById(R.id.cancel_TextView);
        cancel_TextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //On click on validate button we get name of duplicated piece
        TextView validate_TextView = (TextView) dialog.findViewById(R.id.validate_TextView);
        validate_TextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code_licence_EditText.getText().toString().matches(""))
                    Toast.makeText(context, R.string.error_empty_licence, Toast.LENGTH_SHORT).show();
                else {
                    CEL_Biens_DAO cel_biens_dao = new CEL_Biens_DAO(mContext);
                    CEL_Piece_DAO cel_Piece_DAO = new CEL_Piece_DAO(mContext, MissionsList.missionSelected.getIdMission(),
                            cel_biens_dao.select(MissionsList.missionSelected.getIdBien()));
                    CEL_Piece duplicatedPiece = new CEL_Piece();
                    CEL_Piece currentPiece = cel_Piece_DAO.select(EtatDesLieux.pieces.getIdPiece(), MissionsList.missionSelected.getIdMission());
                    duplicatedPiece.setHauteurPiece(currentPiece.getHauteurPiece());
                    duplicatedPiece.setIdMission(currentPiece.getIdMission());
                    duplicatedPiece.setLargeurPiece(currentPiece.getLargeurPiece());
                    duplicatedPiece.setLongueurPiece(currentPiece.getLongueurPiece());
                    duplicatedPiece.setNotesPiece(currentPiece.getNotesPiece());
                    duplicatedPiece.setInclusDansPiece(currentPiece.getInclusDansPiece());

                    duplicatedPiece.setNomTypePiece(EtatDesLieux.pieces.getNomTypePiece());
                    if (code_licence_EditText.getText().toString().matches(EtatDesLieux.pieces.getNomTypePiece())) {
                        int nbCopy = 0;
                        for (CEL_Piece celPiece : EtatDesLieux.piecesList) {
                            if (celPiece.getNomTypePiece().matches(EtatDesLieux.pieces.getNomTypePiece())) {
                                nbCopy++;
                            }
                        }
                        duplicatedPiece.setPiecePiece(code_licence_EditText.getText().toString() + "0" + nbCopy);
                    } else {
                        duplicatedPiece.setPiecePiece(code_licence_EditText.getText().toString());
                    }
                    duplicatedPiece.setIsFinish(0);
                    duplicatedPiece.setIdPiece(cel_Piece_DAO.addValue(duplicatedPiece));
                    cel_Piece_DAO.close();

                    CEL_Elements_DAO elements_DAO = new CEL_Elements_DAO(context);
                    if (cel_ElementsArrayAdapter != null
                            && cel_ElementsArrayAdapter.listCEL_Elements != null) {
                        for (CEL_Elements cel_Elements : elements_DAO.selectAllElementsFromPiece(EtatDesLieux.pieces.getIdPiece(), 4)) {

                            cel_Elements.setIdElements(0);
                            cel_Elements.setItemGroupDescription(cel_Elements.getItemGroupDescription());
                            cel_Elements.setIdPiece(duplicatedPiece.getIdPiece());
                            cel_Elements.setNombreTrousChevilleElements(0);
                            cel_Elements.setIsNettoyer(0);

                            int idElement = elements_DAO.addValue(cel_Elements);
                            elements_DAO.close();

                            CEL_Elements_Obs_DAO elements_Obs_DAO = new CEL_Elements_Obs_DAO(context);
                            if (elements_Obs_DAO.selectAllCel_Elements_Obs(idElement) != null) {
                                for (CEL_Elements_Obs cel_Elements_Obs : elements_Obs_DAO.selectAllCel_Elements_Obs(idElement)) {
                                    cel_Elements_Obs.setIdElementsObs(0);
                                    cel_Elements_Obs.setIdElements(idElement);
                                    elements_Obs_DAO.addValue(cel_Elements_Obs);
                                    elements_Obs_DAO.close();
                                }
                            }

                            CEL_Actions_DAO cel_Actions_DAO = new CEL_Actions_DAO(context);
                            if (cel_Actions_DAO.selectActions(idElement) != null) {
                                CEL_Actions actions = new CEL_Actions(cel_Actions_DAO.selectActions(idElement));
                                actions.setIdElement(idElement);
                                actions.setIdActions(0);
                                cel_Actions_DAO.addValue(actions);
                                cel_Actions_DAO.close();
                            }
                        }
                    }

                    Toast.makeText(context, R.string.duplicate_success, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}