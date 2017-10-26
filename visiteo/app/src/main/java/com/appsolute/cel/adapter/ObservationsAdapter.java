package com.appsolute.cel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.appsolute.cel.DAO.CEL_Elements_Obs_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.models.CEL_Elements_Obs;
import com.appsolute.cel.models.Observation;

import java.util.List;

class ObservationsAdapter extends BaseAdapter {

    private List<Observation> observationsList;
    private Context context;
    private CEL_Elements_Obs_DAO cel_Elements_Obs_DAO;
    private int idElement;

    ObservationsAdapter(List<Observation> observationsList, Context context, int idElement) {
        super();
        this.observationsList = observationsList;
        this.context = context;
        this.idElement = idElement;
        cel_Elements_Obs_DAO = new CEL_Elements_Obs_DAO(context);
    }

    @Override
    public int getCount() {
        return observationsList.size();
    }

    @Override
    public Observation getItem(int position) {
        return observationsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dialog_observation_item, null);

            holder = new ViewHolder();
            holder.observationCheckBox = (CheckBox) convertView.findViewById(R.id.observation_item);
            holder.observationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    int getPosition = (Integer) buttonView.getTag();

                    getItem(getPosition).setSelected(buttonView.isChecked());

                    if(isChecked) {
                        if (!cel_Elements_Obs_DAO.isOnDatabase(idElement, getItem(getPosition).getName())) {
                            CEL_Elements_Obs cel_Elements_Obs = new CEL_Elements_Obs();
                            cel_Elements_Obs.setIdElements(idElement);
                            cel_Elements_Obs.setObsElementsObs(getItem(getPosition).getName());
                            cel_Elements_Obs_DAO.addValue(cel_Elements_Obs);
                        }
                    }
                    else {
                        CEL_Elements_Obs_DAO cel_Elements_Obs_DAO = new CEL_Elements_Obs_DAO(context);
                        cel_Elements_Obs_DAO.deleteValueFromString(observationsList.get(getPosition).getName(), idElement);
                        cel_Elements_Obs_DAO.close();
                    }
                }
            });

            convertView.setTag(holder);
            convertView.setTag(R.id.observation_item, holder.observationCheckBox);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.observationCheckBox.setTag(position);
        holder.observationCheckBox.setChecked(observationsList.get(position).isSelected());
        holder.observationCheckBox.setText(observationsList.get(position).getName());

        return convertView;
    }

    private static class ViewHolder {
        CheckBox observationCheckBox;
    }
}