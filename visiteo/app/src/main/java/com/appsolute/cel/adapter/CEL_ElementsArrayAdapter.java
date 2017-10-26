package com.appsolute.cel.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsolute.cel.BaseActivity;
import com.appsolute.cel.DAO.CEL_Actions_DAO;
import com.appsolute.cel.DAO.CEL_Elements_DAO;
import com.appsolute.cel.DAO.CEL_Elements_Obs_DAO;
import com.appsolute.cel.DAO.ItemType_DAO;
import com.appsolute.cel.DAO.OPERA_Photos_DAO;
import com.appsolute.cel.DAO.Room_Damage_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.activity.MissionsList;
import com.appsolute.cel.activity.PieceActivity;
import com.appsolute.cel.models.CEL_Elements;
import com.appsolute.cel.models.ItemType;
import com.appsolute.cel.models.OPERA_Photos;
import com.appsolute.cel.models.Observation;
import com.appsolute.cel.models.RoomDamage;

import java.util.ArrayList;
import java.util.List;

public class CEL_ElementsArrayAdapter extends ArrayAdapter<CEL_Elements> {


    private Context context;
    public List<CEL_Elements> listCEL_Elements;

    public CEL_ElementsArrayAdapter(Context context, int resource,
                                    List<CEL_Elements> listCEL_Elements, int idRoom) {
        super(context, resource, listCEL_Elements);

        this.context = context;
        this.listCEL_Elements = listCEL_Elements;
    }

    CEL_Elements getCEL_Elements(int i) {
        return listCEL_Elements.get(i);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater viewInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = viewInflater.inflate(R.layout.etat_piece_item, parent, false);
            final MutableWatcher.holder holder = new MutableWatcher.holder();
            holder.descriptionElement_TextView = (TextView) convertView.findViewById(R.id.descriptionElement_TextView);
            holder.etatElement_TextView = (TextView) convertView.findViewById(R.id.etatElement_TextView);
            holder.actionElement_TextView = (TextView) convertView.findViewById(R.id.actionElement_TextView);
            holder.typeElement_TextView = (TextView) convertView.findViewById(R.id.typeElement_TextView);
            holder.quantiteElement_TextView = (TextView) convertView.findViewById(R.id.quantiteElement_TextView);
            holder.ent_obervations_val = (TextView) convertView.findViewById(R.id.ent_obervations_val);
            holder.trouElement_TextView = (TextView) convertView.findViewById(R.id.trouElement_TextView);
            holder.nettoyagelement_CheckBox = (CheckBox) convertView.findViewById(R.id.nettoyagelement_CheckBox);
            holder.imageLayout = (LinearLayout) convertView.findViewById(R.id.imageLayout);
            holder.listImg = (ImageView) convertView.findViewById(R.id.photoElement_ImageView);
            holder.mutableWatcher = new MutableWatcher(holder, context, false, listCEL_Elements);
            holder.trouElement_TextView.addTextChangedListener(holder.mutableWatcher);
            convertView.setTag(holder);
        }

        final MutableWatcher.holder holder = (MutableWatcher.holder) convertView.getTag();

        final CEL_Elements cel_Elements = listCEL_Elements.get(position);

        if (cel_Elements != null) {

            CEL_Elements_Obs_DAO elements_Obs_DAO = new CEL_Elements_Obs_DAO(context);
            String observationsString = elements_Obs_DAO.getObservationsString(cel_Elements.getIdElements());
            elements_Obs_DAO.close();

            CEL_Actions_DAO actions_DAO = new CEL_Actions_DAO(context);
            String actionsString = actions_DAO.stringCel_Actions(cel_Elements.getIdElements());
            actions_DAO.close();

            holder.descriptionElement_TextView.setText(cel_Elements.getElementElements());
            holder.typeElement_TextView.setText(cel_Elements.getTypeElements());
            holder.etatElement_TextView.setText(cel_Elements.getEtatElements());
            holder.ent_obervations_val.setText(observationsString);
            holder.trouElement_TextView.setText(String.valueOf(cel_Elements.getNombreTrousChevilleElements()));

            if (cel_Elements.getIdOperaPhoto() != 0) {
                OPERA_Photos_DAO photo_clef_DAO = new OPERA_Photos_DAO(BaseActivity.mContext);
                OPERA_Photos opera_photos_element = photo_clef_DAO.select(cel_Elements.getIdOperaPhoto());
                if(opera_photos_element.getPhoto() != null) {
                    holder.listImg.setImageBitmap(BitmapFactory.decodeByteArray(opera_photos_element.getPhoto(), 0, opera_photos_element.getPhoto().length));
                }
            } else
                holder.listImg.setImageResource(R.drawable.photo_img);


            holder.actionElement_TextView.setText(actionsString);

            switch (holder.etatElement_TextView.getText().toString()) {
                case "NF":
                    holder.etatElement_TextView.setBackgroundColor(ContextCompat.getColor(context, R.color.green_neuf));
                    break;
                case "BE":
                    holder.etatElement_TextView.setBackgroundColor(ContextCompat.getColor(context, R.color.green_bon_etat));
                    break;
                case "EU":
                    holder.etatElement_TextView.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_etat_usage));
                    break;
                case "ME":
                    holder.etatElement_TextView.setBackgroundColor(ContextCompat.getColor(context, R.color.orange_mauvais_etat));
                    break;
                case "HS":
                    holder.etatElement_TextView.setBackgroundColor(ContextCompat.getColor(context, R.color.red_hs));
                    break;
                case "NV":
                    holder.etatElement_TextView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_no_verified));
                    break;
                default:
                    holder.etatElement_TextView.setBackgroundResource(android.R.color.transparent);
                    break;
            }

            holder.mutableWatcher.setPosition(position);
            holder.mutableWatcher.setActive(true);

            if (cel_Elements.getIsCountable() == 0) {
                holder.quantiteElement_TextView.setEnabled(false);
                holder.quantiteElement_TextView.setText("");
                holder.quantiteElement_TextView.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_gray));
            } else {
                holder.quantiteElement_TextView.setEnabled(true);
                holder.quantiteElement_TextView.setText(String.valueOf(cel_Elements.getQuantiteElements()));
                holder.quantiteElement_TextView.setBackgroundResource(android.R.color.transparent);
            }

            //Add event
            holder.descriptionElement_TextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomAlertDialog.AlertDialogElements(position, context, listCEL_Elements);
                }
            });

            holder.typeElement_TextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemType_DAO itemType_DAO = new ItemType_DAO(context);
                    List<ItemType> itemTypesList = itemType_DAO.selectItemType(cel_Elements.getIdRoomItem());
                    itemType_DAO.close();
                    CustomAlertDialog.AlertDialogSelectTypeElement(context,
                            holder.descriptionElement_TextView.getText().toString(),
                            itemTypesList,
                            cel_Elements);
                }
            });

            holder.quantiteElement_TextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomAlertDialog.EditQuantityElement(holder.quantiteElement_TextView, context, position, false);
                }
            });

            holder.etatElement_TextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomAlertDialog.callChooseEtat(holder.etatElement_TextView, context, cel_Elements, false);
                }
            });

            holder.ent_obervations_val.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!holder.typeElement_TextView.getText().toString().isEmpty()) {

                        Room_Damage_DAO room_Damage_DAO = new Room_Damage_DAO(context);
                        List<RoomDamage> roomDamageList = room_Damage_DAO.selectData(cel_Elements.getIdItemType());
                        room_Damage_DAO.close();
                        List<Observation> observationsList = new ArrayList<>();
                        for (RoomDamage roomDamage : roomDamageList) {
                            observationsList.add(new Observation(roomDamage.getDescriptionRoom()));
                        }
                        CustomAlertDialog.AlertDialogAddObservations(holder.ent_obervations_val,
                                observationsList, context, position, cel_Elements.getIdItemType());
                    }
                    else
                        Toast.makeText(context, context.getResources().getString(R.string.select_type_element), Toast.LENGTH_SHORT).show();
                }
            });

            holder.imageLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) { Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, PieceActivity.pictureFromCamera);
                    PieceActivity.mImageUri = BaseActivity.mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, PieceActivity.mImageUri);


                    if (cameraIntent.resolveActivity(context.getPackageManager()) != null) {
                        ((FragmentActivity) context).startActivityForResult(cameraIntent, PieceActivity.REQUEST_CAMERA_FOR_ELEMENT);
                        PieceActivity.isPictureSaved = false;
                        PieceActivity.position_picture = position;
                        PieceActivity.listImg = holder.listImg;
                        PieceActivity.CEL_ELEMENT = cel_Elements;
                    }
                }
            });


            holder.trouElement_TextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomAlertDialog.EditQuantityElement(holder.quantiteElement_TextView, context, position, true);
                }
            });

            holder.nettoyagelement_CheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    if (isChecked)
                        cel_Elements.setIsNettoyer(1);
                    else
                        cel_Elements.setIsNettoyer(0);

                    CEL_Elements_DAO cel_elements_dao = new CEL_Elements_DAO(context);
                    cel_elements_dao.updateValue(cel_Elements);


                }
            });
            if (cel_Elements.getIsNettoyer() == 1)
                holder.nettoyagelement_CheckBox.setChecked(true);
            else
                holder.nettoyagelement_CheckBox.setChecked(false);

            holder.actionElement_TextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomAlertDialog.AlertDialogAction(holder.actionElement_TextView, context,
                            cel_Elements.getIdElements());
                }
            });
        }
        return convertView;
    }
}