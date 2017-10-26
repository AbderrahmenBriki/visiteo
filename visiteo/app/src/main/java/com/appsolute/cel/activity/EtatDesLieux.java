package com.appsolute.cel.activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Selection;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsolute.cel.DAO.CEL_Biens_DAO;
import com.appsolute.cel.DAO.OPERA_Photos_DAO;
import com.appsolute.cel.BaseActivity;
import com.appsolute.cel.DAO.CEL_Elements_DAO;
import com.appsolute.cel.DAO.CEL_Piece_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.dragdrop.DeleteZone;
import com.appsolute.cel.dragdrop.DragController;
import com.appsolute.cel.dragdrop.DynGridView;
import com.appsolute.cel.dragdrop.DynGridView.DynGridViewListener;
import com.appsolute.cel.dragdrop.DynGridViewAdapter;
import com.appsolute.cel.dragdrop.DynGridViewItemData;
import com.appsolute.cel.fragment.ClesFrag;
import com.appsolute.cel.fragment.CompteursFrag;
import com.appsolute.cel.fragment.MissionFragment;
import com.appsolute.cel.models.CEL_Piece;

import java.util.ArrayList;

public class EtatDesLieux extends BaseActivity implements View.OnClickListener, DynGridView.DynGridViewListener,
ClesFrag.OnValiderTextChange{
	TextView tab_mission, tab_compteurs, tab_piecse, tab_cles;	
	FrameLayout frame;
	Fragment fragment = null;
	ScrollView fragmentview;
	RelativeLayout pieceslayout, dynamicgridview;

	//int table to keep picto to print on gridView
	private int listPicto [] = {R.drawable.picto_cave1, R.drawable.picto_chambre1, 
			R.drawable.picto_cuisine1, R.drawable.picto_degagement1, R.drawable.picto_entree1, 
			R.drawable.picto_garage1, R.drawable.picto_salle_de_bain1, 
			R.drawable.picto_sejour1, R.drawable.picto_wc1, R.drawable.picto_grenier_blue,
			R.drawable.picto_additif_blue, R.drawable.picto_facade_exterieur_blue, R.drawable.picto_sas_blue,
			R.drawable.picto_local_blue, R.drawable.picto_entrepot_blue, R.drawable.picto_circulation_blue,
			R.drawable.picto_local_info_blue, R.drawable.picto_sanitaire_blue, R.drawable.picto_ascenseur_blue,
			R.drawable.picto_bureau_mixte_blue};

	private String[] listNamePicto;

	TextView pieces_choice;
	Button adjouter;
	EditText nomPiece_EditText;
	String piecesval = "", piecesnom ="", nomval="";
	Dialog dialog;
	int tabclas = 1;

	final static int		idTopLayout = Menu.FIRST + 100,
			idBack 		= Menu.FIRST + 101,
			idBotLayout	= Menu.FIRST + 102,
			idToggleScroll=Menu.FIRST+ 103,
			idToggleFavs = Menu.FIRST+ 104;

	DynGridViewAdapter	 	m_gridviewAdapter		= null; 
	DeleteZone 				mDeleteZone				= null;
	ArrayList<DynGridViewItemData> itemList			= null;
	DynGridView 			dynamicGridView			= null;
	boolean	mToggleScroll = false, mToggleFavs = false;

	public static int typeBien;
	public static CEL_Piece pieces;
	private CEL_Piece_DAO pieces_DAO;
	public static ArrayList<CEL_Piece> piecesList;
	private String namePieceSelected;

	public static Uri mImageUri;
    public static final int REQUEST_CAMERA = 0;
    public static String pictureFromCamera = "/photo.png";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.etatdeslieux);
		baseMethod();

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setTitle(getString(R.string.tit_etatdes) + String.valueOf(MissionsList.missionSelected.getNumeroRdvMission()));
		actionBar.setHomeButtonEnabled(true);     
		actionBar.setDisplayUseLogoEnabled(true);

		pieceslayout = (RelativeLayout) findViewById(R.id.pieceslayout);
		fragmentview = (ScrollView) findViewById(R.id.fragmentview);
		frame = (FrameLayout) findViewById(R.id.frame_container);
		tab_mission = (TextView)findViewById(R.id.tab_mission);
		tab_compteurs = (TextView)findViewById(R.id.tab_compteurs);
		tab_piecse = (TextView)findViewById(R.id.tab_piecse);
		tab_cles = (TextView)findViewById(R.id.tab_cles);
		dynamicgridview = (RelativeLayout) findViewById(R.id.dynamicgridview);

		pieces_choice = (TextView) findViewById(R.id.pieces_choice);	
		adjouter = (Button) findViewById(R.id.adjouter);	
		nomPiece_EditText = (EditText) findViewById(R.id.nom);
		Selection.setSelection(nomPiece_EditText.getText(), nomPiece_EditText.getText().length());

		listNamePicto = getResources().getStringArray(R.array.name_piece);

		frame.removeAllViews();
		fragment = new MissionFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

		adjouter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callAjouter();
			}
		});
		pieces_choice.setOnClickListener(new OnClickListener() {
			@Override	
			public void onClick(View v) {
				callChoosePiece();
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		if(pieces_DAO == null) {
            CEL_Biens_DAO cel_biens_dao = new CEL_Biens_DAO(mContext);
            pieces_DAO = new CEL_Piece_DAO(mContext, MissionsList.missionSelected.getIdMission(),
                    cel_biens_dao.select(MissionsList.missionSelected.getIdBien()));
        }
		pieces_DAO.close();
	}

	@Override
	public void onResume() {
		super.onResume();
		if(tabclas==3) {
			initializePiece();
			setGridview();
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

	private void initializePiece() {
		piecesList = new ArrayList<>();
        CEL_Biens_DAO cel_biens_dao = new CEL_Biens_DAO(mContext);
        pieces_DAO = new CEL_Piece_DAO(mContext, MissionsList.missionSelected.getIdMission(),
                cel_biens_dao.select(MissionsList.missionSelected.getIdBien()));
		piecesList = pieces_DAO.selectAllPieceWithIdMission(MissionsList.missionSelected.getIdMission());
		pieces_DAO.close();

		if(piecesList.size() == 0)
			Toast.makeText(this, getString(R.string.computer_error), Toast.LENGTH_LONG).show();
	}

	public void callValider(View v) {
		if(tabclas==4) {
			if (fragment != null) {
				((ClesFrag) fragment).saveClefsPhoto(fragment);
				ClesFrag.saveCles();
			}
			this.finish();
		} else if(tabclas==2) {
			if (fragment != null) {
				if(((CompteursFrag) fragment).saveCompteurData())
					this.finish();
			}
		} else 
			this.finish();
	}

	public void callMission(View v) {
		saveDatas();
		tabclas = 1;
		tab_mission.setBackgroundResource(R.drawable.menuhover);
		tab_compteurs.setBackgroundResource(R.drawable.menu_left);
		tab_piecse.setBackgroundResource(R.drawable.menu_left);
		tab_cles.setBackgroundResource(R.drawable.menu_right);

		pieceslayout.setVisibility(View.INVISIBLE);   
		fragmentview.setVisibility(View.VISIBLE);		
		frame.removeAllViews();
		fragment = new MissionFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
	}

	public void callCompteurs(View v) {
		saveDatas();
		tabclas = 2;
		tab_mission.setBackgroundResource(R.drawable.menu_left);
		tab_compteurs.setBackgroundResource(R.drawable.menuhover);
		tab_piecse.setBackgroundResource(R.drawable.menu_left);
		tab_cles.setBackgroundResource(R.drawable.menu_right);

		pieceslayout.setVisibility(View.INVISIBLE);   
		fragmentview.setVisibility(View.VISIBLE);
		frame.removeAllViews();
		fragment = new CompteursFrag();	
		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();				
		} else {
			Log.e("Fragment Load", "Error in creating fragment");
		}			
	}

	public void callPiece(View v) {
		saveDatas();
		tabclas = 3;
		tab_mission.setBackgroundResource(R.drawable.menu_left);
		tab_compteurs.setBackgroundResource(R.drawable.menu_left);
		tab_piecse.setBackgroundResource(R.drawable.menuhover);
		tab_cles.setBackgroundResource(R.drawable.menu_right);

		fragmentview.setVisibility(View.INVISIBLE);
		pieceslayout.setVisibility(View.VISIBLE);     

		initializePiece();

		setGridview();
	}

	public void callCles(View v) {
		saveDatas();
		tabclas = 4;
		tab_mission.setBackgroundResource(R.drawable.menu_left);
		tab_compteurs.setBackgroundResource(R.drawable.menu_left);
		tab_piecse.setBackgroundResource(R.drawable.menu_left);
		tab_cles.setBackgroundResource(R.drawable.menuhover_right);

		pieceslayout.setVisibility(View.INVISIBLE);
		fragmentview.setVisibility(View.VISIBLE);
		frame.removeAllViews();
		fragment = new ClesFrag();	
		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();				
		} else {
			Log.e("Fragment Load", "Error in creating fragment");
		}	
	}


	private void saveDatas() {
		switch (tabclas) {
		case 1:

			break;
		case 2:
			CompteursFrag.saveCompteurData();
			break;
		case 3:

			break;
		case 4:
			ClesFrag.saveClefsPhoto(fragment);
			ClesFrag.saveCles();
			break;
		}
	}

	public String[] setDefaultPiece() {

		String[] pieceStrings;

		switch (typeBien) {
		case 0:
			pieceStrings = getResources().getStringArray(R.array.spinnerItemsT1); 
			break;
		case 1:
			pieceStrings = getResources().getStringArray(R.array.spinnerItemsT2); 
			break;
		case 2:
			pieceStrings = getResources().getStringArray(R.array.spinnerItemsT3); 
			break;
		case 3:
			pieceStrings = getResources().getStringArray(R.array.spinnerItemsT4); 
			break;
		case 4:
			pieceStrings = getResources().getStringArray(R.array.spinnerItemsT5); 
			break;
		case 5:
			pieceStrings = getResources().getStringArray(R.array.spinnerItemsT6); 
			break;
		case 6:
			pieceStrings = getResources().getStringArray(R.array.spinnerItemsVilla); 
			break;
		case 7:
			pieceStrings = getResources().getStringArray(R.array.spinnerItemsBureaux); 
			break;
		case 8:
			pieceStrings = getResources().getStringArray(R.array.spinnerItemsEntrepot); 
			break;
		default:
			pieceStrings = getResources().getStringArray(R.array.spinnerItemsBoutique);
			break;
		}
		return pieceStrings;
	}

	public void setGridview() {
		if(dynamicgridview!=null)
			dynamicgridview.removeAllViews();
		RelativeLayout ibMenuBot = new RelativeLayout(this);
		ibMenuBot.setId(idBotLayout);
		//ibMenu.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.line));
		RelativeLayout.LayoutParams botParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		botParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		dynamicgridview.addView(ibMenuBot,botParams);		

		mDeleteZone = new DeleteZone(mContext);
		//ivD.setImageResource(R.drawable.ic_launcher);
		LevelListDrawable a  = new LevelListDrawable();
		a.addLevel(0, 1, ContextCompat.getDrawable(this, R.drawable.close_img)); // normal image
		a.addLevel(1, 2, ContextCompat.getDrawable(this, R.drawable.close_img1)); // delete image, drag over
		mDeleteZone.setImageDrawable(a);

		RelativeLayout.LayoutParams lpbDel = new RelativeLayout.LayoutParams(getDPI(50),getDPI(50));//LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpbDel.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		lpbDel.addRule(RelativeLayout.CENTER_VERTICAL);
		ibMenuBot.addView(mDeleteZone, lpbDel);

		//1. create gridview view 
		dynamicGridView = new DynGridView(mContext);
		RelativeLayout.LayoutParams midParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);	
		midParams.addRule(RelativeLayout.ABOVE,ibMenuBot.getId());	
		dynamicgridview.addView(dynamicGridView,midParams);

		setGridviewOnclick();
	}

	public int getPiecesIcon(String pieces) {
		if(pieces.equalsIgnoreCase(getString(R.string.op_entree)))
			return R.drawable.picto_entree1;
		else if(pieces.matches(".*\\bCuisine\\b.*"))
			return R.drawable.picto_cuisine1;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_sejour)))
			return R.drawable.picto_sejour1;
		else if(pieces.matches(".*\\bWC\\b.*"))
			return R.drawable.picto_wc1;
		else if(pieces.matches(".*\\bSalle de bain\\b.*"))
			return R.drawable.picto_salle_de_bain1;
		else if(pieces.matches(".*\\bChambre\\b.*"))
			return R.drawable.picto_chambre1;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_cave)))
			return R.drawable.picto_cave1;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_degage)))
			return R.drawable.picto_degagement1;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_garage)))
			return R.drawable.picto_garage1;
		else if(pieces.matches(".*\\bGrenier\\b.*"))
			return R.drawable.picto_grenier_blue;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_additif)))
			return R.drawable.picto_additif_blue;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_facade)))
			return R.drawable.picto_facade_exterieur_blue;
		else if(pieces.matches(".*\\bSAS\\b.*"))
			return R.drawable.picto_sas_blue;
		else if(pieces.matches(".*\\bLocal\\b.*"))
			return R.drawable.picto_local_blue;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_entrepot)))
			return R.drawable.picto_entrepot_blue;
		else if(pieces.matches(".*\\bCirculation\\b.*"))
			return R.drawable.picto_circulation_blue;
		else if(pieces.matches(".*\\bLocal informatique\\b.*"))
			return R.drawable.picto_local_info_blue;
		else if(pieces.matches(".*\\bSanitaire\\b.*"))
			return R.drawable.picto_sanitaire_blue;
		else if(pieces.matches(".*\\bAscenseur\\b.*"))
			return R.drawable.picto_ascenseur_blue;
		else if(pieces.matches(".*\\bBureau\\b.*"))
			return R.drawable.picto_bureau_mixte_blue;

		return R.drawable.cave_img;
	}

	public int getPicesIconGreen(String pieces) {
		if(pieces.equalsIgnoreCase(getString(R.string.op_entree)))
			return R.drawable.picto_entree2;
		else if(pieces.matches(".*\\bCuisine\\b.*"))
			return R.drawable.picto_cuisine2;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_sejour)))
			return R.drawable.picto_sejour2;
		else if(pieces.matches(".*\\bWC\\b.*"))
			return R.drawable.picto_wc2;
		else if(pieces.matches(".*\\bSalle de bain\\b.*"))
			return R.drawable.picto_salle_de_bain2;
		else if(pieces.matches(".*\\bChambre\\b.*"))
			return R.drawable.picto_chambre2;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_degage)))
			return R.drawable.picto_cave2;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_degage)))
			return R.drawable.picto_degagement2;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_garage)))
			return R.drawable.picto_garage2;
		else if(pieces.matches(".*\\bGrenier\\b.*"))
			return R.drawable.picto_grenier_green;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_additif)))
			return R.drawable.picto_additif_green;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_facade)))
			return R.drawable.picto_facade_exterieur_green;
		else if(pieces.matches(".*\\bSAS\\b.*"))
			return R.drawable.picto_sas_green;
		else if(pieces.matches(".*\\bLocal\\b.*"))
			return R.drawable.picto_local_green;
		else if(pieces.equalsIgnoreCase(getString(R.string.op_entrepot)))
			return R.drawable.picto_entrepot_green;
		else if(pieces.matches(".*\\bCirculation\\b.*"))
			return R.drawable.picto_circulation_green;
		else if(pieces.matches(".*\\bLocal informatique\\b.*"))
			return R.drawable.picto_local_info_green;
		else if(pieces.matches(".*\\bSanitaire\\b.*"))
			return R.drawable.picto_sanitaire_green;
		else if(pieces.matches(".*\\bAscenseur\\b.*"))
			return R.drawable.picto_ascenseur_green;
		else if(pieces.matches(".*\\bBureau\\b.*"))
			return R.drawable.picto_bureau_mixte_green;

		return R.drawable.entree;
	}
	
	public String getPieceTypeNanme(String pieces) {
		if(pieces.equalsIgnoreCase(getString(R.string.op_entree)))
			return getString(R.string.op_entree);
		else if(pieces.matches(".*\\bCuisine\\b.*"))
			return getString(R.string.op_cuisine);
		else if(pieces.equalsIgnoreCase(getString(R.string.op_sejour)))
			return getString(R.string.op_sejour);
		else if(pieces.matches(".*\\bWC\\b.*"))
			return getString(R.string.op_wc);
		else if(pieces.matches(".*\\bSalle de bain\\b.*"))
			return getString(R.string.op_salle);
		else if(pieces.matches(".*\\bChambre\\b.*"))
			return getString(R.string.op_chambre);
		else if(pieces.equalsIgnoreCase(getString(R.string.op_degage)))
			return getString(R.string.op_degage);
		else if(pieces.equalsIgnoreCase(getString(R.string.op_garage)))
			return getString(R.string.op_garage);
		else if(pieces.matches(".*\\bGrenier\\b.*"))
			return getString(R.string.op_grenier);
		else if(pieces.equalsIgnoreCase(getString(R.string.op_additif)))
			return getString(R.string.op_additif);
		else if(pieces.equalsIgnoreCase(getString(R.string.op_facade)))
			return getString(R.string.op_facade);
		else if(pieces.matches(".*\\bSAS\\b.*"))
			return getString(R.string.op_sas);
		else if(pieces.matches(".*\\bLocal\\b.*"))
			return getString(R.string.op_local);
		else if(pieces.equalsIgnoreCase(getString(R.string.op_entrepot)))
			return getString(R.string.op_entrepot);
		else if(pieces.matches(".*\\bCirculation\\b.*"))
			return getString(R.string.op_circulation);
		else if(pieces.matches(".*\\bLocal informatique\\b.*"))
			return getString(R.string.op_local_info);
		else if(pieces.matches(".*\\bSanitaire\\b.*"))
			return getString(R.string.op_sanitaire);
		else if(pieces.matches(".*\\bAscenseur\\b.*"))
			return getString(R.string.op_ascenseur);
		else if(pieces.matches(".*\\bBureau\\b.*"))
			return getString(R.string.op_bureau);

		return getString(R.string.op_entree);
	}

	public void setGridviewOnclick() {
		//2. setup Gridview data
		itemList = new ArrayList<>();
		int icon = R.drawable.cave_img;
		int i = 0;
		if (piecesList.size()>0) {
			for (CEL_Piece pieces : piecesList) {
				boolean pieceFlag = false;				
				if(pieces.getIsFinish() == 1) {
					icon = getPicesIconGreen(pieces.getNomTypePiece());
					pieceFlag = true;
				} else {
					icon = getPiecesIcon(pieces.getNomTypePiece());
					pieceFlag = false;
				}
				DynGridViewItemData item = new DynGridViewItemData(
						pieces.getPiecePiece(), getDPI(140), getDPI(120), 10, false, mToggleFavs, icon, i, pieceFlag);
				itemList.add(item);
				i++;
			}
		} else {
			int j = 1;
			for (String piece : setDefaultPiece()) {
				boolean pieceFlag = false;
                for(CEL_Piece cel_piece : piecesList) {
                    if(cel_piece.getNomPiece().equals(piece)) {
                        j++;
                        piece = piece + " " + j;
                    }
                }
				pieces = new CEL_Piece(MissionsList.missionSelected.getIdMission(), piece, getPieceTypeNanme(piece), 0);
				if(pieces.getIsFinish() == 1) {
					icon = getPicesIconGreen(pieces.getNomTypePiece());
					pieceFlag = true;
				} else {
					icon = getPiecesIcon(pieces.getNomTypePiece());
					pieceFlag = false;
				}
				DynGridViewItemData item = new DynGridViewItemData(piece, getDPI(140), getDPI(120), 10, false, mToggleFavs, icon, i, pieceFlag);
				pieces.setIdPiece(pieces_DAO.addValue(pieces));
				piecesList.add(pieces);
				itemList.add(item);
				i++;
			}
		}

		m_gridviewAdapter = new DynGridViewAdapter(mContext, itemList);

		dynamicGridView.setAdapter(m_gridviewAdapter);   
		dynamicGridView.setNumColumns(4);
		dynamicGridView.setDynGridViewListener((DynGridViewListener) mContext);  
		dynamicGridView.setDeleteView(mDeleteZone);
		DragController dragController = new DragController(mContext);        
		dynamicGridView.setDragController(dragController);
		dynamicGridView.setSwipeEnabled(mToggleScroll);
	}
	
	public int getDPI(int px_value) {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		float logicalDensity = metrics.density;
		int dpi = (int) Math.ceil(px_value * logicalDensity);
		return dpi;
	}

	public void callAjouter() {
		try {		
			nomval = nomPiece_EditText.getText().toString();
			if(piecesval == null || piecesval.trim().equals("")) {
				callAlerter(getString(R.string.empty_string_piece));				
			} else {	
				if(nomval==null || nomval.length()<=0) 
					nomval = piecesnom;
				final Dialog dialog = new Dialog(mContext);
				dialog.setContentView(R.layout.dialog_dupliquer);
				dialog.setTitle(getString(R.string.dupliquer));
				dialog.setCancelable(false);

				final EditText foisval = (EditText)dialog.findViewById(R.id.foisval);
				TextView cancelButton_TextView = (TextView)dialog.findViewById(R.id.cancel_Button);
				TextView okButton_TextView = (TextView)dialog.findViewById(R.id.okDupliquer_Button);

				cancelButton_TextView.setOnClickListener(new OnClickListener() {			
					@Override
					public void onClick(View view) {
						dialog.dismiss();
					}
				});
				okButton_TextView.setOnClickListener(new OnClickListener() {			
					@Override
					public void onClick(View view) {						
						String fois = foisval.getText().toString();
						if(!fois.trim().isEmpty()) {
							dialog.dismiss();

							for(int i=1;i<=Integer.parseInt(fois);i++) {
								//Add pieces on local database 
								
								String pieceName = "";
								if(nomPiece_EditText.getText().toString().equals(""))
									pieceName = pieces_choice.getText().toString();
								else
									pieceName = nomPiece_EditText.getText().toString();
								
								if(Integer.parseInt(fois) >1)
									pieceName = pieceName + " 00" + i;
								pieces = new CEL_Piece(MissionsList.missionSelected.getIdMission(), 
										pieceName, pieces_choice.getText().toString(), 0);
								pieces.setIdPiece(pieces_DAO.addValue(pieces));
								piecesList.add(pieces);
							}
							piecesval = "";
							piecesnom = "";
							pieces_choice.setText("");
							nomPiece_EditText.setText("");
							setGridviewOnclick();
						}
					}
				});
				dialog.setCancelable(false);
				dialog.show();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void callChoosePiece() {
		dialog = new Dialog(mContext);
		dialog.setContentView(R.layout.dialog_pieces);
		dialog.setTitle(getString(R.string.pie_pieces));

		GridView gridview2 = (GridView) dialog.findViewById(R.id.grid_view2);
		gridview2.setPadding(20, 20, 20, 5);
		gridview2.setAdapter(new GridAdapter());

		dialog.setCancelable(true);
		dialog.show();
	}

	public class GridAdapter extends BaseAdapter {

		public int getCount() {
			return listNamePicto.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView(final int position, View convertView, ViewGroup parent) {
			TextView textview = null;
			if (convertView == null) {     	  
				LayoutInflater inflater3 = mActivity.getLayoutInflater();
				convertView = inflater3.inflate(R.layout.dialog_pieces_item, parent, false);
				textview = (TextView)convertView.findViewById(R.id.piecesitem);
				textview.setText(listNamePicto[position]);
				textview.setCompoundDrawablesWithIntrinsicBounds(0, listPicto[position], 0, 0);
			} else
				textview = (TextView) convertView;

			textview.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					pieces_choice.setText(listNamePicto[position]); 
					namePieceSelected = listNamePicto[position];
					nomPiece_EditText.setText("");
					piecesnom = listNamePicto[position];
					piecesval = listNamePicto[position];
					if(dialog!=null)
						dialog.dismiss();
				}
			});
			return textview;
		}
	}

	public void onItemFavClick(View v, int position, int id) {	}

	public void onDragStart() { }

	public void onDragStop() { }

	public void onItemsChanged(int positionOne, int positionTwo) { }

	public void onItemDeleted(int position, int id) {
        CEL_Biens_DAO cel_biens_dao = new CEL_Biens_DAO(mContext);
        pieces_DAO = new CEL_Piece_DAO(mContext, MissionsList.missionSelected.getIdMission(),
                cel_biens_dao.select(MissionsList.missionSelected.getIdBien()));
		pieces_DAO.deleteValue(piecesList.get(position).getIdPiece());
		pieces_DAO.close();


		OPERA_Photos_DAO opera_photos_dao = new OPERA_Photos_DAO(mContext);
		opera_photos_dao.deleteValue(piecesList.get(position).getIdOperaPhoto());

		CEL_Elements_DAO elements_DAO = new CEL_Elements_DAO(mContext);
		elements_DAO.deleteAllElementsForPiece(piecesList.get(position).getIdPiece());
		elements_DAO.close();
		piecesList.remove(position);
		m_gridviewAdapter.notifyDataSetChanged();
	}

	public void onSwipeLeft() {	}

	public void onSwipeRight() {	}

	public void onSwipeUp() {	}

	public void onSwipeDown() {	}

	@Override
	public void onClick(View v) { 
	}

	@Override
	public void onItemClick(View v, int position, int id) {		
	}

	@Override
	public void validerTextChange() {
		if (fragment != null) {
			((ClesFrag) fragment).saveClefsPhoto(fragment);
			ClesFrag.saveCles();
			ClesFrag.initializeClefs();
			//callAlerter(mContext.getString(R.string.cls_updated));
		}
	}
}
