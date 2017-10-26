package com.appsolute.cel.dragdrop;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;

import com.appsolute.cel.DAO.CEL_Biens_DAO;
import com.appsolute.cel.DAO.Room_DAO;
import com.appsolute.cel.activity.EtatDesLieux;
import com.appsolute.cel.DAO.CEL_Piece_DAO;
import com.appsolute.cel.activity.MissionsList;
import com.appsolute.cel.activity.PieceActivity;

import static com.appsolute.cel.BaseActivity.mContext;

public class DynGridViewAdapter extends BaseAdapter {

	private Context context;
	private List<DynGridViewItemData> itemList, itemListOrig;
	private Room_DAO room_DAO;

	public DynGridViewAdapter(Context context, List<DynGridViewItemData> itemList ) { 
		this.context = context;
		this.itemList = itemList;
	}

	public int getCount() {                        
		return itemList.size();
	}

	public Object getItem(int position) {     
		return itemList.get(position);
	}

	/**
	 * Removes the specified element from the list
	 */
	public void remove(DynGridViewItemData item) {
		itemList.remove(item);
		notifyDataSetChanged();
	}

	/**
	 * Removes the element at the specified position in the list
	 */
	public void remove(int position) {
		if(itemList.size()>position)
			itemList.remove(position);
		else
			itemList.remove(position-1);
		notifyDataSetChanged();
	}
	public long getItemId(int position) {  
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) { 
		DynGridViewItemData itemData = itemList.get(position);
		View view = new DynGridViewItemView(this.context, itemData );
		// set listeners to trigger the drag events
		view.setOnClickListener((OnClickListener) parent);
		if (((DynGridViewItemView) view).getFavoriteView() != null) {
			((DynGridViewItemView) view).getFavoriteView().setId(DynGridViewItemView.FAVICONID);
			((DynGridViewItemView) view).getFavoriteView().setOnClickListener((OnClickListener) parent);
		}
		view.setOnLongClickListener((OnLongClickListener) parent);
		view.setOnTouchListener ((OnTouchListener) parent);
		final int positionPiece = position;
		final boolean pieceFlag = itemData.getPieceFlag();
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {			
				EtatDesLieux.pieces = EtatDesLieux.piecesList.get(positionPiece);
				room_DAO = new Room_DAO(context);
				String namePiece = "";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bWC\\b.*"))
					namePiece = "WC";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bSalle de bain\\b.*"))
					namePiece = "Salle de bain";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bChambre\\b.*"))
					namePiece = "Chambre";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bEntrée\\b.*"))
					namePiece = "Entrée";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bCuisine\\b.*"))
					namePiece = "Cuisine";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bSéjour\\b.*"))
					namePiece = "Séjour";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bDégagement\\b.*"))
					namePiece = "Dégagement";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bGarage\\b.*"))
					namePiece = "Garage";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bCave\\b.*"))
					namePiece = "Cave";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bGrenier\\b.*"))
					namePiece = "Grenier";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bAdditif extérieur\\b.*"))
					namePiece = "Additif extérieur";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bFaçade extérieure\\b.*"))
					namePiece = "Façade extérieure";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bSAS\\b.*"))
					namePiece = "SAS";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bLocal\\b.*"))
					namePiece = "Local";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bEntrepôt\\b.*"))
					namePiece = "Entrepôt";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bCirculation\\b.*"))
					namePiece = "Circulation";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bLocal informatique\\b.*"))
					namePiece = "Local informatique";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bSanitaire\\b.*"))
					namePiece = "Sanitaire";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bAscenseur\\b.*"))
					namePiece = "Ascenseur";
				if(EtatDesLieux.pieces.getPiecePiece().matches(".*\\bBureau\\b.*"))
					namePiece = "Bureau";

				int room_id = room_DAO.selectId(namePiece);
				if(EtatDesLieux.pieces.getNomTypePiece() == null 
						|| EtatDesLieux.pieces.getNomTypePiece().equals("")) {
					EtatDesLieux.pieces.setNomTypePiece(namePiece);

					CEL_Biens_DAO cel_biens_dao = new CEL_Biens_DAO(mContext);
					CEL_Piece_DAO cel_Piece_DAO = new CEL_Piece_DAO(mContext, MissionsList.missionSelected.getIdMission(),
							cel_biens_dao.select(MissionsList.missionSelected.getIdBien()));

					cel_Piece_DAO.updateValue(EtatDesLieux.pieces);
				}

				Intent i = new Intent(context, PieceActivity.class);
				i.putExtra("room_id", room_id);
				i.putExtra("pieceFlag", pieceFlag);
				context.startActivity(i);		
			}
		});
		return view;
	}


	/**
	 * Replaces the element at the specified position in this list with the
	 * specified element.
	 */
	public void set(int position, DynGridViewItemData item) {
		itemList.set(position, item);
		notifyDataSetChanged();
	}

	public void swapItems(int positionOne, int positionTwo) {
		DynGridViewItemData temp = (DynGridViewItemData) getItem(positionOne);
		set(positionOne, (DynGridViewItemData) getItem(positionTwo));
		set(positionTwo, temp);
	} 

	public Filter getFilter() {
		return new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				final FilterResults oReturn = new FilterResults();
				//final Document[] mDocus;
				final ArrayList<DynGridViewItemData> results = new ArrayList<DynGridViewItemData>();
				if (itemListOrig == null) itemListOrig = itemList;
				if (constraint != null) {
					if (itemListOrig != null && itemListOrig.size() > 0) {
						for (final DynGridViewItemData g : itemListOrig) {
							// filter: check document name
							if (g.getLabel().toLowerCase(Locale.getDefault()).contains(constraint.toString().toLowerCase(Locale.getDefault())))
								results.add(g);
						}
					}
					oReturn.values = results;
				}
				return oReturn;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				itemList = (ArrayList<DynGridViewItemData>) results.values;
				notifyDataSetChanged();
			}
		};
	} 
}
