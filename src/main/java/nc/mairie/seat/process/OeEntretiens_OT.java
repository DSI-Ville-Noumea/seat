package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Declarations;
import nc.mairie.seat.metier.Entretien;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.OT;
import nc.mairie.seat.metier.PePerso;
import nc.mairie.seat.metier.PePersoInfos;
import nc.mairie.seat.metier.TIntervalle;
import nc.mairie.seat.metier.TypeEntretien;
import nc.mairie.technique.*;
/**
 * Process OeEntretiens_OT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
*/
public class OeEntretiens_OT extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -534433608870834132L;
	private String ACTION_SUPPRESSION = "Suppression";
	private String ACTION_MODIFICATION = "Modification";
	private String ACTION_CREATION = "Création";
	private java.lang.String[] LB_ENTRETIENS;
	private java.lang.String[] LB_TENT;
	private java.lang.String[] LB_TINT;
	public boolean isOcassionnel;
	public boolean suppresion = false;
	private String focus = null;
	private EquipementInfos equipementInfosCourant;
	private OT otCourant;
	private PePerso pePersoCourant;
	private Entretien entretienCourant;
	private TypeEntretien typeentCourant;
	private TIntervalle tintCourant;
	private Declarations declarationCourante;
	private String titreAction="";
	private ArrayList<Entretien> listEntretiens;
	private ArrayList<TypeEntretien> listTEnt;
	private ArrayList<TIntervalle> listTInt;
	private boolean isFirst = true;
	private String newCommentaire;
	private String newDuree;
	private String newIntervalle;
	private String newDprevu;
	private String newDreal;
	private String newSinistre;
	private String actionOT;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(isFirst){
			
		// récupération des variables globales
		EquipementInfos unEquipement = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
		OT unOT = (OT)VariableGlobale.recuperer(request,"OT");
		
		PePerso unPePerso = (PePerso)VariableGlobale.recuperer(request,"PEPERSO");
		titreAction = (String)VariableActivite.recuperer(this,"TITRE_ACTION");
		actionOT = (String)VariableActivite.recuperer(this,"OT_TITRE_ACTION");
		addZone(getNOM_ST_TITRE_ACTION(),titreAction);
		if(titreAction.equals(ACTION_SUPPRESSION)){
			setSuppresion(true);
		}else{
			setSuppresion(false);
		}
		// controle si null
		if (unEquipement.getNumeroinventaire()!=null){
			setEquipementInfosCourant(unEquipement);
		}
		if(unOT!=null){
			setOtCourant(unOT);
		}
		if(unPePerso.getCodepep()!=null){
			setPePersoCourant(unPePerso);
			TIntervalle unTintervalle = TIntervalle.chercherTIntervalle(getTransaction(),getPePersoCourant().getCodeti());
			if (getTransaction().isErreur()){
				return;
			}
			setTintCourant(unTintervalle);
			TypeEntretien unTypeEntreten = TypeEntretien.chercherTypeEntretien(getTransaction(),getPePersoCourant().getCodetypeent());
			if(getTransaction().isErreur()){
				return;
			}
			setTypeentCourant(unTypeEntreten);
			Entretien unEntretien = Entretien.chercherEntretien(getTransaction(),getPePersoCourant().getCodeentretien());
			if(getTransaction().isErreur()){
				return;
			}
			setEntretienCourant(unEntretien);
			newCommentaire = getPePersoCourant().getCommentairete();
			newDuree = getPePersoCourant().getDuree();
			newIntervalle = getPePersoCourant().getIntervallepep();
			newSinistre = getPePersoCourant().getSinistre();
//			 on coche la case si c'est un sinistre
			if ("oui".equals(getPePersoCourant().getSinistre().trim())){
				addZone(getNOM_CK_SINISTRE(),getCHECKED_ON());
			}else{
				addZone(getNOM_CK_SINISTRE(),getCHECKED_OFF());
			}
			
			if(getPePersoCourant().getDateprev().equals("01/01/0001")){
				newDprevu = "";
			}else{
				newDprevu = getPePersoCourant().getDateprev();
			}
			if(getPePersoCourant().getDatereal().equals("01/01/0001")){
				newDreal = "";
			}else{
				newDreal = getPePersoCourant().getDatereal();
			}
		}else{
			setPePersoCourant(new PePerso());
		}
	}
	
	// on initialise les zones si pas vide
	if (getOtCourant().getNumeroot()!=null){
		if(getEquipementInfosCourant().getNumeroinventaire()!=null){
			//on renseigne les zones
			addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
			addZone(getNOM_ST_MARQUE(),getEquipementInfosCourant().getDesignationmarque());
			addZone(getNOM_ST_MODELE(),getEquipementInfosCourant().getDesignationmodele());
			addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
			addZone(getNOM_ST_NOINVENT(),getEquipementInfosCourant().getNumeroinventaire());
			addZone(getNOM_ST_NOOT(),getOtCourant().getNumeroot());
		}else{
//			on vide les zones
			addZone(getNOM_ST_TYPE(),"");
			addZone(getNOM_ST_MARQUE(),"");
			addZone(getNOM_ST_MODELE(),"");
			addZone(getNOM_ST_NOIMMAT(),"");
			addZone(getNOM_ST_NOINVENT(),"");
			addZone(getNOM_ST_NOOT(),"");
		}
	}
	
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		PePersoInfos unPePersoInfos = PePersoInfos.chercherPePersoInfos(getTransaction(),getPePersoCourant().getCodepep());
		if(getTransaction().isErreur()){
			return ;
		}
		addZone(getNOM_ST_ENTRETIEN(),unPePersoInfos.getLibelleentretien());
		if(unPePersoInfos.getDatereal().equals("01/01/0001")){
			addZone(getNOM_ST_DREAL(),"");
		}else{
			addZone(getNOM_ST_DREAL(),unPePersoInfos.getDatereal());
		}
		if(unPePersoInfos.getDateprev().equals("01/01/0001")){
			addZone(getNOM_ST_DPREVU(),"");
		}else{
			addZone(getNOM_ST_DPREVU(),unPePersoInfos.getDateprev());
		}
		addZone(getNOM_ST_DUREE(),unPePersoInfos.getDuree());
		
	}else{
		//	initialisation des listes
		initialiseListEntretiens(request);
		initialiseListTEnt(request);
		initialiseTInt(request);
		
		//	initialisation des champs
		addZone(getNOM_EF_COMMENTAIRE(),newCommentaire);
		addZone(getNOM_EF_DPREVU(),newDprevu);
		addZone(getNOM_EF_DREAL(),newDreal);
		addZone(getNOM_EF_DUREE(),newDuree);
		addZone(getNOM_EF_INTERVALLE(),newIntervalle);
	}
	setFirst(false);
	
}

public void initialiseListEntretiens(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList<Entretien> a = Entretien.listerEntretien(getTransaction());
	String[] colonnes = {"libelleentretien"};
	//ordre croissant
	boolean[] ordres = {true};
	a = Services.trier(a,colonnes,ordres);
	
	setListEntretiens(a);
	if (a.size()>0){
		int [] tailles = {20};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < a.size() ; i++) {
			Entretien aEntretien = (Entretien)a.get(i);	
			String ligne [] = { aEntretien.getLibelleentretien()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_ENTRETIENS(aFormat.getListeFormatee());
	}else{
		setLB_ENTRETIENS(null);
	}	
	//si modif
	if(getPePersoCourant()!=null){
//		 on trouve le bon  entretien
		if(getEntretienCourant()!=null){
			//	recherche du type d'intervalle courant
			addZone(getNOM_LB_ENTRETIENS_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListEntretiens().size(); i++) {
				Entretien unEntretien = (Entretien)getListEntretiens().get(i);
				if (unEntretien.getCodeentretien().equals(getEntretienCourant().getCodeentretien())) {
					addZone(getNOM_LB_ENTRETIENS_SELECT(),String.valueOf(i));
					break;
				}
			}
		}
	}
}

public void initialiseListEntretiensOT(javax.servlet.http.HttpServletRequest request) throws Exception{
	/*int [] tailles = {20,10,5,10};
	String [] padding = {"G","C","C","C"};
	FormateListe aFormat = new FormateListe(tailles,padding,false);
	for (int i=0;i<getListEntretiensOT().size();i++){
		Pieces maPiece = (Pieces)getListEntretiensOT().get(i);
//			les élèments de la liste seront le codemarque pour pouvoir récupérer le dernier élément et les dimensions des marques.
		String ligne [] = { maPiece.getDesignationpiece(),maPiece.getPu()};
		aFormat.ajouteLigne(ligne);
		setLB_ENTRETIENS_OT(aFormat.getListeFormatee());
	}*/
}
public void initialiseListTEnt(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList<TypeEntretien> a = TypeEntretien.listerTypeEntretien(getTransaction());
	String[] colonnes = {"designationtypeent"};
	//ordre croissant
	boolean[] ordres = {true};
	a = Services.trier(a,colonnes,ordres);
		
	setListTEnt(a);
	if (a.size()>0){
		int [] tailles = {20};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < a.size() ; i++) {
			TypeEntretien aTypeEntretien = (TypeEntretien)a.get(i);	
			String ligne [] = { aTypeEntretien.getDesignationtypeent()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_TENT(aFormat.getListeFormatee());
	}else{
		setLB_TENT(null);
	}
	// si en modif
	if(getPePersoCourant()!=null){
//		 on trouve le bon typeent
		if(getTypeentCourant()!=null){
			//	recherche du type d'intervalle courant
			addZone(getNOM_LB_TENT_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListTEnt().size(); i++) {
				TypeEntretien unTypeEntretien = (TypeEntretien)getListTEnt().get(i);
				if (unTypeEntretien.getCodetypeent().equals(getTypeentCourant().getCodetypeent())) {
					addZone(getNOM_LB_TENT_SELECT(),String.valueOf(i));
					break;
				}
			}
		}
	}
}
public void initialiseTInt(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList<TIntervalle> a = TIntervalle.listerTIntervalle(getTransaction());
	String[] colonnes = {"designation"};
	//ordre croissant
	boolean[] ordres = {true};
	a = Services.trier(a,colonnes,ordres);
	
	setListTInt(a);
	if (a.size()>0){
		int [] tailles = {20};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < a.size() ; i++) {
			TIntervalle aTIntervalle = (TIntervalle)a.get(i);	
			String ligne [] = { aTIntervalle.getDesignation()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_TINT(aFormat.getListeFormatee());
	}else{
		setLB_TINT(null);
	}
	//si modif
	if(getPePersoCourant()!=null){
//		 on trouve le bon tintervalle
		if(getTintCourant()!=null){
			//	recherche du type d'intervalle courant
			addZone(getNOM_LB_TINT_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListTInt().size(); i++) {
				TIntervalle unTIntervalle = (TIntervalle)getListTInt().get(i);
				if (unTIntervalle.getCodeti().equals(getTintCourant().getCodeti())) {
					addZone(getNOM_LB_TINT_SELECT(),String.valueOf(i));
					break;
				}
			}
		}
	}
}

/**
 * Constructeur du process OeEntretiens_OT.
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 */
public OeEntretiens_OT() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// si l'ot est en création alors si pas d'entretien enregistré on supprime l'OT
	if(actionOT.equals(ACTION_CREATION)){
		// recherche des entretiens
		if(getOtCourant().getNumeroot()!=null){
			ArrayList<PePersoInfos> listEntretiens = PePersoInfos.chercherPePersoInfosOT(getTransaction(),getOtCourant().getNumeroot());
			if(getTransaction().isErreur()){
				return false;
			}
			if(listEntretiens.size()==0){
				getOtCourant().suppressionOT(getTransaction());
				if(getTransaction().isErreur()){
					return false;
				}
			}
		}
		setOtCourant(new OT());
		
	}
	// tout s'est bien passé
	commitTransaction();
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ENLEVER
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_ENLEVER() {
	return "NOM_PB_ENLEVER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OK() {
	return "NOM_PB_OK";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_ENT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OK_ENT() {
	return "NOM_PB_OK_ENT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_OK_ENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	int indice = (Services.estNumerique(getVAL_LB_ENTRETIENS_SELECT()) ? Integer.parseInt(getVAL_LB_ENTRETIENS_SELECT()): -1);
	Entretien unEntretien = (Entretien)getListEntretiens().get(indice);
	setEntretienCourant(unEntretien);
	// on récupère les infos 
	newIntervalle = getZone(getNOM_EF_INTERVALLE());
	newDprevu = getZone(getNOM_EF_DPREVU());
	newDreal = getZone(getNOM_EF_DREAL());
	newCommentaire = getZone(getNOM_EF_COMMENTAIRE());
	newDuree = getZone(getNOM_EF_DUREE());
	if(titreAction.equals(ACTION_CREATION)){
		// on recherche si cet entretien est déjà inscrit pour cet équipement
		// si l'entretien a déjà été enregistré pour cet equipement, on affiche par l'intervalle, la durée...
		PePerso unPePerso = PePerso.chercherPePersoEquipEntPrevu(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),getEntretienCourant().getCodeentretien());
		if(getTransaction().isErreur()){
			// l'entretien n'a pas été enregistré pour cet équipement
			getTransaction().traiterErreur();
		}
		// si déjà enretistré pour cet OT
		if(unPePerso.getCodeot()!=null){
			if(unPePerso.getCodeot().equals(getOtCourant().getNumeroot())){
				getTransaction().declarerErreur("Cet entretien est déjà enregistré pour cet OT.");
				return false;
			}
		
			// si prévu et pas encore fait, on affiche les infos si pas déjà saisi
			if (newIntervalle.equals("")){
				newIntervalle = unPePerso.getIntervallepep();
				getPePersoCourant().setIntervallepep(newIntervalle);
				//addZone(getNOM_EF_INTERVALLE(),newIntervalle);
			}
			if (newDprevu.equals("")){
				if(unPePerso.getDateprev().equals("01/01/0001")){
					newDprevu="";
				}else{
					newDprevu = unPePerso.getDateprev();
				}
				getPePersoCourant().setDateprev(newDprevu);
				//addZone(getNOM_EF_DPREVU(),newDprevu);
			}
			if(newDuree.equals("")){
				newDuree = unPePerso.getDuree();
				getPePersoCourant().setDuree(newDuree);
				//addZone(getNOM_EF_DUREE(),newDuree);
			}
			if(newCommentaire.equals("")){
				newCommentaire = unPePerso.getCommentairete();
				getPePersoCourant().setCommentairete(newCommentaire);
				//addZone(getNOM_EF_COMMENTAIRE(),newCommentaire);
			}	
			int indiceTEnt = Integer.parseInt(unPePerso.getCodetypeent());
			getPePersoCourant().setCodetypeent(String.valueOf(indiceTEnt));
			//addZone(getNOM_LB_TENT_SELECT(),String.valueOf(indiceTEnt));
			int indiceTInt = Integer.parseInt(unPePerso.getCodeti());
			getPePersoCourant().setCodeti(String.valueOf(indiceTInt));
			//addZone(getNOM_LB_TINT_SELECT(),String.valueOf(indiceTInt));
			int indiceEnt = Integer.parseInt(unPePerso.getCodeentretien());
			getPePersoCourant().setCodeentretien(String.valueOf(indiceEnt));
			//addZone(getNOM_LB_ENTRETIENS_SELECT(),String.valueOf(indiceEnt));
		}
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_TENT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OK_TENT() {
	return "NOM_PB_OK_TENT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_OK_TENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice = (Services.estNumerique(getVAL_LB_ENTRETIENS_SELECT()) ? Integer.parseInt(getVAL_LB_ENTRETIENS_SELECT()): -1);
	Entretien monEntretien = (Entretien)getListEntretiens().get(indice);
	if(getTransaction().isErreur()){
		return false;
	}
	setEntretienCourant(monEntretien);
	newIntervalle = getZone(getNOM_EF_INTERVALLE());
	newDuree = getZone(getNOM_EF_DUREE());
	newCommentaire = getZone(getNOM_EF_COMMENTAIRE());
	newDprevu = getZone(getNOM_EF_DPREVU());
	newDreal = getZone(getNOM_EF_DREAL());
	//	sélection du type d'entretien
	indice = (Services.estNumerique(getVAL_LB_TENT_SELECT()) ? Integer.parseInt(getVAL_LB_TENT_SELECT()): -1);
	TypeEntretien monTypeEntretien = (TypeEntretien)getListTEnt().get(indice);
	if (monTypeEntretien.getCodetypeent().trim().equals("2")){
		setOcassionnel(true);
	}else{
		setOcassionnel(false);
	}
	setTypeentCourant(monTypeEntretien);
	
//	sélection du type d'intervalle
	indice = (Services.estNumerique(getVAL_LB_TINT_SELECT()) ? Integer.parseInt(getVAL_LB_TINT_SELECT()): -1);
	TIntervalle monTIntervalle = (TIntervalle)getListTInt().get(indice);
	setTintCourant(monTIntervalle);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		// on enlève l'entretien de l'OT 
		getPePersoCourant().setCodeot("");
		getPePersoCourant().modifierPePersoInfos(getTransaction());
	}else{
		int indice = (Services.estNumerique(getVAL_LB_ENTRETIENS_SELECT()) ? Integer.parseInt(getVAL_LB_ENTRETIENS_SELECT()): -1);
		Entretien monEntretien = (Entretien)getListEntretiens().get(indice);
		if(getTransaction().isErreur()){
			return false;
		}
		setEntretienCourant(monEntretien);
		indice = (Services.estNumerique(getVAL_LB_TENT_SELECT()) ? Integer.parseInt(getVAL_LB_TENT_SELECT()): -1);
		TypeEntretien monTypeEnt = (TypeEntretien)getListTEnt().get(indice);
		if(getTransaction().isErreur()){
			return false;
		}
		setTypeentCourant(monTypeEnt);
		indice = (Services.estNumerique(getVAL_LB_TINT_SELECT()) ? Integer.parseInt(getVAL_LB_TINT_SELECT()): -1);
		TIntervalle monTintervalle = (TIntervalle)getListTInt().get(indice);
		if(getTransaction().isErreur()){
			return false;
		}
		setTintCourant(monTintervalle);
		
		//	 on ajoute l'entretien voulu dans la table f_pe_perso
		newCommentaire = getZone(getNOM_EF_COMMENTAIRE());
		newDuree = getZone(getNOM_EF_DUREE());
		newIntervalle = getZone(getNOM_EF_INTERVALLE());
		
		String sinistre = getZone(getNOM_CK_SINISTRE());
		// controle si les dates sont correctes
		if (Services.estUneDate(getZone(getNOM_EF_DPREVU()))){
			newDprevu = getZone(getNOM_EF_DPREVU());
		}else if (!getZone(getNOM_EF_DPREVU()).equals("")){
			getTransaction().declarerErreur("La date prévue n'est pas correcte.");
			setFocus(getNOM_EF_DPREVU());
			return false;
		}
		if (Services.estUneDate(getZone(getNOM_EF_DREAL()))){
			newDreal = getZone(getNOM_EF_DREAL());
		}else if (!getZone(getNOM_EF_DREAL()).equals("")){
			getTransaction().declarerErreur("La date de réalisation n'est pas correcte.");
			setFocus(getNOM_EF_DREAL());
			return false;
		}
//		 pour réservé
		newSinistre = sinistre.equals(getCHECKED_ON()) ? "oui" : "non";
		//controle
		if (newDuree.equals("")){
			getTransaction().declarerErreur("Vous devez renseigner la durée.");
			setFocus(getNOM_EF_DUREE());
			return false;
		}
		// si entretien pas occasionnel il faut renseigner l'intervalle
		if (!getTypeentCourant().getCodetypeent().trim().equals("2")){
			if(newIntervalle.equals("")){
				getTransaction().declarerErreur("Vous devez renseigner l'intervalle");
				setFocus(getNOM_EF_INTERVALLE());
				return false;
			}
		}
		Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
		if(getTransaction().isErreur()){
			return false;
		}
		if(titreAction.equals(ACTION_CREATION)){
	//		 si l'entretien est déjà prévu dans le Plan d'entretien personnalisé de l'équipement
			// on modifie le codeOt du PePerso
			// sinon on le créée
			PePerso unPePerso = PePerso.chercherPePersoEquipEntPrevu(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),getEntretienCourant().getCodeentretien());
			/*if(getTransaction().isErreur()){
				return false;
			}*/
			if (unPePerso.getCodepep()!=null){
				unPePerso.setCodeot(getOtCourant().getNumeroot());
				unPePerso.setCommentairete(newCommentaire);
				unPePerso.setDateprev(Services.dateDuJour());
				unPePerso.setDuree(newDuree);
				unPePerso.setIntervallepep(newIntervalle);
				unPePerso.setDatereal(newDreal);
				unPePerso.setSinistre(newSinistre);
				unPePerso.modifierPePersoInfos(getTransaction());
			}else{
				getTransaction().traiterErreur();
				//	on renseigne les zones
				PePerso newPePerso = new PePerso();
				newPePerso.setCodeot(getOtCourant().getNumeroot());
				newPePerso.setCommentairete(newCommentaire);
				newPePerso.setDateprev(Services.dateDuJour());
				newPePerso.setDatereal(newDreal);
				newPePerso.setDuree(newDuree);
				newPePerso.setIntervallepep(newIntervalle);
				unPePerso.setSinistre(newSinistre);
				newPePerso.creerPePerso(getTransaction(),unEquipement,getEntretienCourant(),getTypeentCourant(),getTintCourant());
				addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
			}
		}else{
			getPePersoCourant().setCodeot(getOtCourant().getNumeroot());
			getPePersoCourant().setCommentairete(newCommentaire);
			getPePersoCourant().setDateprev(newDprevu);
			getPePersoCourant().setDuree(newDuree);
			getPePersoCourant().setIntervallepep(newIntervalle);
			getPePersoCourant().setDatereal(newDreal);
			getPePersoCourant().setSinistre(newSinistre);
			getPePersoCourant().modifierPePerso(getTransaction(),unEquipement,getEntretienCourant(),getTypeentCourant(),getTintCourant());
		}
	}
	if(getTransaction().isErreur()){
		return false;
	}
	// si pas de souci on commit
	commitTransaction();
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	setStatut(STATUT_PROCESS_APPELANT,true);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOOT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOOT() {
	return "NOM_ST_NOOT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOOT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOOT() {
	return getZone(getNOM_ST_NOOT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_COMMENTAIRE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_COMMENTAIRE() {
	return "NOM_EF_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_COMMENTAIRE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_COMMENTAIRE() {
	return getZone(getNOM_EF_COMMENTAIRE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DUREE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DUREE() {
	return "NOM_EF_DUREE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DUREE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DUREE() {
	return getZone(getNOM_EF_DUREE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_INTERVALLE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_INTERVALLE() {
	return "NOM_EF_INTERVALLE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_INTERVALLE
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_INTERVALLE() {
	return getZone(getNOM_EF_INTERVALLE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_ENTRETIENS
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 */
private String [] getLB_ENTRETIENS() {
	if (LB_ENTRETIENS == null)
		LB_ENTRETIENS = initialiseLazyLB();
	return LB_ENTRETIENS;
}
/**
 * Setter de la liste:
 * LB_ENTRETIENS
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 */
private void setLB_ENTRETIENS(java.lang.String[] newLB_ENTRETIENS) {
	LB_ENTRETIENS = newLB_ENTRETIENS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_ENTRETIENS
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_ENTRETIENS() {
	return "NOM_LB_ENTRETIENS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIENS_SELECT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_ENTRETIENS_SELECT() {
	return "NOM_LB_ENTRETIENS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_ENTRETIENS
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_ENTRETIENS() {
	return getLB_ENTRETIENS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_ENTRETIENS
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_ENTRETIENS_SELECT() {
	return getZone(getNOM_LB_ENTRETIENS_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_TENT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 */
private String [] getLB_TENT() {
	if (LB_TENT == null)
		LB_TENT = initialiseLazyLB();
	return LB_TENT;
}
/**
 * Setter de la liste:
 * LB_TENT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 */
private void setLB_TENT(java.lang.String[] newLB_TENT) {
	LB_TENT = newLB_TENT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_TENT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_TENT() {
	return "NOM_LB_TENT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_TENT_SELECT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_TENT_SELECT() {
	return "NOM_LB_TENT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_TENT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_TENT() {
	return getLB_TENT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_TENT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_TENT_SELECT() {
	return getZone(getNOM_LB_TENT_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_TINT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 */
private String [] getLB_TINT() {
	if (LB_TINT == null)
		LB_TINT = initialiseLazyLB();
	return LB_TINT;
}
/**
 * Setter de la liste:
 * LB_TINT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 */
private void setLB_TINT(java.lang.String[] newLB_TINT) {
	LB_TINT = newLB_TINT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_TINT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_TINT() {
	return "NOM_LB_TINT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_TINT_SELECT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_TINT_SELECT() {
	return "NOM_LB_TINT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_TINT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_TINT() {
	return getLB_TINT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_TINT
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_TINT_SELECT() {
	return getZone(getNOM_LB_TINT_SELECT());
}
/**
 * @return Renvoie focus.
 */
public String getFocus() {
	if (focus == null) {
		focus=getDefaultFocus();
	}
	return focus;
}
/**
 * @param focus focus à définir.
 */
public void setFocus(String focus) {
	this.focus = focus;
}
/**
 * focus focus à définir.
 * @return focus
 */
public String getDefaultFocus() {
	return getNOM_LB_ENTRETIENS();
}
public boolean isOcassionnel() {
	return isOcassionnel;
}
public void setOcassionnel(boolean isOcassionnel) {
	this.isOcassionnel = isOcassionnel;
}
public EquipementInfos getEquipementInfosCourant() {
	return equipementInfosCourant;
}
public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
	this.equipementInfosCourant = equipementInfosCourant;
}
public OT getOtCourant() {
	return otCourant;
}
public void setOtCourant(OT otCourant) {
	this.otCourant = otCourant;
}
public PePerso getPePersoCourant() {
	return pePersoCourant;
}
public void setPePersoCourant(PePerso pePersoCourant) {
	this.pePersoCourant = pePersoCourant;
}
public ArrayList<Entretien> getListEntretiens() {
	return listEntretiens;
}
public void setListEntretiens(ArrayList<Entretien> listEntretiens) {
	this.listEntretiens = listEntretiens;
}
public ArrayList<TypeEntretien> getListTEnt() {
	return listTEnt;
}
public void setListTEnt(ArrayList<TypeEntretien> listTEnt) {
	this.listTEnt = listTEnt;
}
public ArrayList<TIntervalle> getListTInt() {
	return listTInt;
}
public void setListTInt(ArrayList<TIntervalle> listTInt) {
	this.listTInt = listTInt;
}
	public Entretien getEntretienCourant() {
		return entretienCourant;
	}
	public void setEntretienCourant(Entretien entretienCourant) {
		this.entretienCourant = entretienCourant;
	}
	public TIntervalle getTintCourant() {
		return tintCourant;
	}
	public void setTintCourant(TIntervalle tintCourant) {
		this.tintCourant = tintCourant;
	}
	public TypeEntretien getTypeentCourant() {
		return typeentCourant;
	}
	public void setTypeentCourant(TypeEntretien typeentCourant) {
		this.typeentCourant = typeentCourant;
	}
	public boolean isFirst() {
		return isFirst;
	}
	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DPREVU
 * Date de création : (29/07/05 15:07:18)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DPREVU() {
	return "NOM_EF_DPREVU";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DPREVU
 * Date de création : (29/07/05 15:07:18)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DPREVU() {
	return getZone(getNOM_EF_DPREVU());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DREAL
 * Date de création : (29/07/05 15:07:18)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DREAL() {
	return "NOM_EF_DREAL";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DREAL
 * Date de création : (29/07/05 15:07:18)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DREAL() {
	return getZone(getNOM_EF_DREAL());
}
/**
 * Retourne le nom de la case à cocher sélectionnée pour la JSP :
 * CK_SINISTRE
 * Date de création : (29/07/05 15:07:18)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_CK_SINISTRE() {
	return "NOM_CK_SINISTRE";
}
/**
 * Retourne la valeur de la case à cocher à afficher par la JSP pour la case à cocher  :
 * CK_SINISTRE
 * Date de création : (29/07/05 15:07:18)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_CK_SINISTRE() {
	return getZone(getNOM_CK_SINISTRE());
}
	public boolean isSuppresion() {
		return suppresion;
	}
	public void setSuppresion(boolean suppresion) {
		this.suppresion = suppresion;
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (29/07/05 09:18:57)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}

		//Si clic sur le bouton PB_OK_ENT
		if (testerParametre(request, getNOM_PB_OK_ENT())) {
			return performPB_OK_ENT(request);
		}

		//Si clic sur le bouton PB_OK_TENT
		if (testerParametre(request, getNOM_PB_OK_TENT())) {
			return performPB_OK_TENT(request);
		}

		//Si clic sur le bouton PB_VALIDER
		if (testerParametre(request, getNOM_PB_VALIDER())) {
			return performPB_VALIDER(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (01/08/05 14:42:19)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeEntretiens_OT.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DPREVU
 * Date de création : (01/08/05 14:42:19)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DPREVU() {
	return "NOM_ST_DPREVU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DPREVU
 * Date de création : (01/08/05 14:42:19)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DPREVU() {
	return getZone(getNOM_ST_DPREVU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DREAL
 * Date de création : (01/08/05 14:42:19)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DREAL() {
	return "NOM_ST_DREAL";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DREAL
 * Date de création : (01/08/05 14:42:19)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DREAL() {
	return getZone(getNOM_ST_DREAL());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DUREE
 * Date de création : (01/08/05 14:42:19)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DUREE() {
	return "NOM_ST_DUREE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DUREE
 * Date de création : (01/08/05 14:42:19)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DUREE() {
	return getZone(getNOM_ST_DUREE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ENTRETIEN
 * Date de création : (01/08/05 14:42:19)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_ENTRETIEN() {
	return "NOM_ST_ENTRETIEN";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_ENTRETIEN
 * Date de création : (01/08/05 14:42:19)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_ENTRETIEN() {
	return getZone(getNOM_ST_ENTRETIEN());
}
	public String getNewSinistre() {
		return newSinistre;
	}
	public void setNewSinistre(String newSinistre) {
		this.newSinistre = newSinistre;
	}
	public Declarations getDeclarationCourante() {
		return declarationCourante;
	}
	public void setDeclarationCourante(Declarations declarationCourante) {
		this.declarationCourante = declarationCourante;
	}
}
