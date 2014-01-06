package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.StringTokenizer;


import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.BE;
import nc.mairie.seat.metier.ENGJU;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.OT;
import nc.mairie.seat.metier.OTInfos;
import nc.mairie.seat.metier.OT_ATM;
import nc.mairie.seat.metier.PePerso;
import nc.mairie.seat.metier.PePersoInfos;
import nc.mairie.seat.metier.Pieces;
import nc.mairie.seat.metier.PiecesInfos;
import nc.mairie.seat.metier.TIntervalle;
import nc.mairie.technique.*;
/**
 * Process OeOT_Modification
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
*/
public class OeOT_Modification extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_DECLARATIONS = 7;
	public static final int STATUT_CREATIONOT = 6;
	public static final int STATUT_FRE = 5;
	public static final int STATUT_RECHERCHEREQUIP = 4;
	public static final int STATUT_PIECES = 3;
	public static final int STATUT_MECANICIEN = 2;
	public static final int STATUT_ENTRETIENS = 1;
	private String ACTION_SUPPRESSION = "Suppression";
	private String ACTION_MODIFICATION = "Modification";
	private String ACTION_CREATION = "Création";
	private java.lang.String[] LB_INTERVENANTS;
	private java.lang.String[] LB_INTERVENTIONS;
	private java.lang.String[] LB_PIECES;
	private EquipementInfos equipementInfosCourant;
	private OT otCourant;
	private OTInfos otInfosCourant;
	private ArrayList listInterventions;
	private ArrayList listIntervenants;
	private ArrayList listPieces;
	private ArrayList listBe;
	private String newDSortie;
	private String newDEntree;
	private String newCompteur;
	private String newCommentaire;
	private String newNoOt;
	private boolean first = true;
	public boolean creation;
	public String focus = null;
	private String messageErreur="";
	private	String listeBEInexistant = "";
	public String elementBE = "";
	public boolean	bErreurBe;
	public boolean isDebrancheDec = false;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	String debranche = (String)VariableActivite.recuperer(this,"DEBRANCHEDEC");
	if(debranche==null){
		setDebrancheDec(false);
	}else{
		if(debranche.equals("TRUE")){
			setDebrancheDec(true);
		}else{
			setDebrancheDec(false);
		}
	}
	//if(first){
		EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
		if(unEquipementInfos!=null){
			if (unEquipementInfos.getNumeroinventaire()!=null){
				setEquipementInfosCourant(unEquipementInfos);
			}
		}
//	}*/
		if(getTransaction().isErreur()){
			if (!getTransaction().getMessageErreur().equals("")){
				messageErreur = messageErreur + getTransaction().getMessageErreur();
				getTransaction().traiterErreur();
			}
		}
	if(etatStatut()!=0){//(etatStatut()==STATUT_CREATIONOT)||(etatStatut()==st){
		OT unOT = (OT)VariableGlobale.recuperer(request,"OT");
		setOtCourant(unOT);
		if(getOtCourant()!=null){
			if(getOtCourant().getNumeroot()!=null){
				OT aOT = OT.chercherOT(getTransaction(),getOtCourant().getNumeroot());
				if (getTransaction().isErreur()){
					getTransaction().traiterErreur();
					addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
				}else{
					addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
				}
			}else{
				addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
				//on vide les zones
				
				//getTransaction().declarerErreur("L'OT doit comporter des entretiens à faire.")
			}
		}else{
			addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
			setFocus(getNOM_EF_RECHERCHER());
		}
		
	}
	//si première fois 
	if(first){
		String titreAction = (String)VariableActivite.recuperer(this,"TITRE_ACTION");
		addZone(getNOM_ST_TITRE_ACTION(),titreAction);
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
			setOtCourant(new OT());
			setCreation(true);
		}else{
			//OTInfos unOTInfos = (OTInfos)VariableGlobale.recuperer(request,"OTINFOS");
			OT unOT = (OT)VariableGlobale.recuperer(request,"OT");
			if(unOT!=null){
				setOtCourant(unOT);
			}
			setCreation(false);
			addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
		}
	}else{
//		 si erreur lors de la création
		String titre = (String)VariableActivite.recuperer(this,"TITRE_ACTION");
		if(titre!=null){
			if(titre.equals(ACTION_CREATION)){
				setCreation(true);
			}
		}
		/*if(getTransaction().isErreur()){
			if (!getTransaction().getMessageErreur().equals("")){
				messageErreur = messageErreur + getTransaction().getMessageErreur();
			}
		}*/
	}
	
	
	//addZone(getNOM_ST_TITRE_ACTION(),)
	// controle pour initialisation des champs
	if (getOtCourant()!=null){
		if(getOtCourant().getNumeroot()!=null){
			OT unOT = OT.chercherOT(getTransaction(),getOtCourant().getNumeroot());
			if (getTransaction().isErreur()){
				getTransaction().traiterErreur();
			}else{
			newNoOt = getOtCourant().getNumeroot();
			newCompteur = getOtCourant().getCompteur();	
			newCommentaire = getOtCourant().getCommentaire();
			
			if((getOtCourant().getDateentree()==null)||(getOtCourant().getDateentree().equals("01/01/0001"))){
				newDEntree = "";
			}else{
				newDEntree = getOtCourant().getDateentree();
			}
			if((getOtCourant().getDatesortie()==null)||(getOtCourant().getDatesortie().equals("01/01/0001"))){
				newDSortie = "";
			}else{
				newDSortie = getOtCourant().getDatesortie();
			}
			//initialisation des listes
			initialiseListIntervenants(request);
			initialiseListInterventions(request);
			initialiseListPieces(request);
			initialiseListBe(request);
			//	initialisation des zones
			addZone(getNOM_ST_NOOT(),newNoOt);
			addZone(getNOM_EF_COMPTEUR(),newCompteur);
			addZone(getNOM_EF_DENTREE(),newDEntree);
			addZone(getNOM_EF_DSORTIE(),newDSortie);
			addZone(getNOM_EF_COMMENTAIRE(),newCommentaire);
			}
		}else{
//			 initialisation des zones de l'OT
			addZone(getNOM_ST_NOOT(),"");
			if((newDEntree!=null)&&(!newDEntree.equals(""))){
				addZone(getNOM_EF_DENTREE(),newDEntree);
			}else{
				addZone(getNOM_EF_DENTREE(),"");
			}
			if((newDSortie!=null)&&(!newDSortie.equals(""))){
				addZone(getNOM_EF_DSORTIE(),newDSortie);
			}else{
				addZone(getNOM_EF_DSORTIE(),"");
			}
			if((newCompteur!=null)&&(!newCompteur.equals(""))){
				addZone(getNOM_EF_COMPTEUR(),newCompteur);
			}else{
				addZone(getNOM_EF_COMPTEUR(),"");
			}
			if((newCommentaire!=null)&&(!newCommentaire.equals(""))){
				addZone(getNOM_EF_COMMENTAIRE(),newCommentaire);
			}else{
				addZone(getNOM_EF_COMMENTAIRE(),"");
			}
		}
	//sinon on met les champs vides
	}else{
		setCreation(true);
		setOtCourant(new OT());
		//	 initialisation des zones de l'OT
		addZone(getNOM_ST_NOOT(),"");
		addZone(getNOM_EF_COMPTEUR(),"");
		addZone(getNOM_EF_DENTREE(),"");
		addZone(getNOM_EF_DSORTIE(),"");
	}
	//if(((etatStatut()!=0)||(isFirst()))&&(getEquipementInfosCourant()!=null)){
	if(getEquipementInfosCourant()!=null){
		//	 pour trouver le service qui a le véhicule
		/*AffectationServiceInfos unAffectationServiceInfos = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),getOtCourant().getDateentree());
		if(unAffectationServiceInfos.getNumeroinventaire()==null){
			//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
			getTransaction().traiterErreur();
			addZone(getNOM_ST_SERVICE(),"pas affecter");
		}else{
			addZone(getNOM_ST_SERVICE(),unAffectationServiceInfos.getLiserv());
		}*/
		//initialisation des zones
		addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
		addZone(getNOM_ST_MARQUE(),getEquipementInfosCourant().getDesignationmarque());
		addZone(getNOM_ST_MODELE(),getEquipementInfosCourant().getDesignationmodele());
		addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
		addZone(getNOM_ST_NOINVENT(),getEquipementInfosCourant().getNumeroinventaire());
		addZone(getNOM_ST_TCOMPTEUR(),getEquipementInfosCourant().getDesignationcompteur());
	}else{
		addZone(getNOM_ST_TYPE(),"");
		addZone(getNOM_ST_MARQUE(),"");
		addZone(getNOM_ST_MODELE(),"");
		addZone(getNOM_ST_NOIMMAT(),"");
		addZone(getNOM_ST_NOINVENT(),"");
		addZone(getNOM_ST_SERVICE(),"");
	}
	if((etatStatut()!=0)&&(!getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION))){
		setCreation(false);
		addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
	}else{
		setCreation(true);
	}
	if(!getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)){
		setCreation(false);
	}
	// si un message d'erreur
	if(!messageErreur.equals("")&&(messageErreur!=null)){
		getTransaction().declarerErreur(messageErreur);
		messageErreur = "";
	}
//	 on traite le cas où un (ou plusieurs) bons d'engagement n'a pas été trouvé.
	if (!("").equals(listeBEInexistant)&&(listeBEInexistant!=null)){
		StringTokenizer stListBE = new StringTokenizer(listeBEInexistant,";");
		while(stListBE.hasMoreElements()){
			elementBE = stListBE.nextToken();
			bErreurBe = true;
			getTransaction().declarerErreur("Le bon d'engagement "+elementBE+" n'a pas été trouvé. Cliquer sur le bouton 'Retirer' pour le retirer de l'OT?");
		}
	}
	setFirst(false);
}
/*Si on débranche sur une fenêtre (actions sur les entretiens, intervenants ou pièces)
 *il faut quand même garder les éventuelles modifications que l'utilisateur a fait
 * sur les dates et le comtpeur
 */
public void recupereInfosOT(javax.servlet.http.HttpServletRequest request) throws Exception{
	newDEntree = getZone(getNOM_EF_DENTREE());
	newDSortie = getZone(getNOM_EF_DSORTIE());
	newCommentaire = getZone(getNOM_EF_COMMENTAIRE()).toUpperCase();
	newCompteur = getZone(getNOM_EF_COMPTEUR());
}

public boolean initialiseListInterventions(javax.servlet.http.HttpServletRequest request) throws Exception{
	String intervalle;
	// on initialise la liste des interventions réalisées pendant l'OT
	if(getOtCourant()!=null){
		ArrayList listInterventions= PePersoInfos.chercherPePersoInfosOT(getTransaction(),getOtCourant().getNumeroot());
		if(getTransaction().isErreur()){
			return false;
		}
		setListInterventions(listInterventions);
	}
	
	//if(getListInterventions()!=null){
		if(getListInterventions().size()>0){
			int [] tailles = {30,10,5,15};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G","C","D","G"};
			FormateListe aFormat = new FormateListe(tailles,padding,false);
			for (int i = 0; i < listInterventions.size() ; i++) {
				PePersoInfos monPePersoInfos = (PePersoInfos)listInterventions.get(i);
				String datereal = "";
				if(monPePersoInfos.getDatereal().equals("01/01/0001")){
					datereal = "";
				}else{
					datereal = monPePersoInfos.getDatereal();
				}
				TIntervalle unTint = TIntervalle.chercherTIntervalle(getTransaction(),monPePersoInfos.getCodeti());
				if(getTransaction().isErreur()){
					return false;
				}
				
				//MODIF OFONTENEAU 20090403
				if (null!=monPePersoInfos.getIntervalle())
					intervalle=monPePersoInfos.getIntervalle()+" "+ unTint.getDesignation();
				else
					intervalle = unTint.getDesignation();
				//intervalle = monPePersoInfos.getIntervalle()+ " "+unTint.getDesignation();
				String ligne [] = { monPePersoInfos.getLibelleentretien(),datereal,monPePersoInfos.getDuree(),intervalle};
				aFormat.ajouteLigne(ligne);
			}
			setLB_INTERVENTIONS(aFormat.getListeFormatee());
		}else{
			setLB_INTERVENTIONS(LBVide);
		}
		return true;
}

public boolean initialiseListBe(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList listBe = BE.listerBEOTNumInv(getTransaction(),getOtCourant().getNumeroot(),getOtCourant().getNuminv());
	if(getTransaction().isErreur()){
		return false;
	}
	setListBe(listBe);
	
//	Alim de la liste des EngJU
	ArrayList arrENGJU = ENGJU.listerENGJUGroupByCdepNoengjIdetbs(getTransaction(), getOtCourant().getNumeroot(), getOtCourant().getNuminv());
		
	if(arrENGJU.size()>0){
		int tailles [] = {10,10,30,10};
		String[] padding = {"G","G","G","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i=0;i<arrENGJU.size();i++){
			ENGJU unEnju = (ENGJU )arrENGJU.get(i);
			String ligne [] = { unEnju.getNoengj(),unEnju.getIdetbs(),unEnju.getEnscom(),unEnju.getMtlenju()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_FRE(aFormat.getListeFormatee());
	} else {
		setLB_FRE(null);
	}
	
	/*LB correction et optimisation 8/11/11
	if(getListBe().size()>0){
		int tailles [] = {10,10,30,10};
		String[] padding = {"G","G","G","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i=0;i<getListBe().size();i++){
			BE unBe = (BE)getListBe().get(i);
			ENGJU unEnju = ENGJU.chercherpremierENGJU(getTransaction(),unBe.getExerci(),unBe.getNoengj());
			if(getTransaction().isErreur()){
				// on ne l'a pas trouvé : on met le numéro de l'engagement dans un variable pour traiter ce problème après
				listeBEInexistant = unBe.getNoengj()+";";
				getTransaction().traiterErreur() ;
			}else{
				//on recherche les lignes concernant l'engagement juridique et on calcul le montant
				int montantTotal = ENGJU.montantBE(getTransaction(),unEnju);
				if(montantTotal==-1){
					getTransaction().declarerErreur("Une erreur s'est produite lors du calcul du montant du bon d'engagement.");
					return false;
				}
				//on renseigne la lb
				String ligne [] = { unEnju.getNlengju(),unEnju.getIdetbs(),unEnju.getEnscom(),String.valueOf(montantTotal)};
				aFormat.ajouteLigne(ligne);
			}
		}
		setLB_FRE(aFormat.getListeFormatee());
	} else {
		setLB_FRE(null);
	}
	*/
	return true;
}
/*
public int montantBE(javax.servlet.http.HttpServletRequest request,ENJU unEnju) throws Exception{
	// controle si unEnju null
	if (unEnju.getCodcoll()==null){
		getTransaction().declarerErreur("Le Bon d'engagement passé en paramètre est null.");
		return -1;
	}
	int total = 0;
	// recherche des lignes du bon d'engagement
	ArrayList listLeju = LEJU.listerLEJUBE(getTransaction(),unEnju.getExerci(),unEnju.getNoengju());
	if(getTransaction().isErreur()){
		return -1;
	}
	if(listLeju.size()>0){
		for(int i=0;i<listLeju.size();i++){
			LEJU unLeju = (LEJU)listLeju.get(i);
			int montant = Integer.parseInt(unLeju.getMtlengju());
			total = total + montant;
		}
	}
	
	return total;
}
*/
public boolean initialiseListIntervenants(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList listMeca = OT_ATM.listerOT_ATMOT(getTransaction(),getOtCourant().getNumeroot());
	if(getTransaction().isErreur()){
		return false;
	}
	setListIntervenants(listMeca);
	if(listMeca.size()>0){
		int [] tailles = {20,20};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G","G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i=0;i<listMeca.size();i++){
			OT_ATM unOTATM = (OT_ATM)listMeca.get(i);
			Agents unAgent = Agents.chercherAgents(getTransaction(),unOTATM.getMatricule());
			if(getTransaction().isErreur()){
				return false;
			}
			String ligne [] = { unAgent.getNom(),unAgent.getPrenom()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_INTERVENANTS(aFormat.getListeFormatee());
	}else{
		setLB_INTERVENANTS(LBVide);
	}	
	return true;
}

public boolean initialiseListPieces(javax.servlet.http.HttpServletRequest request) throws Exception{
//	 on initialise la liste des pièces sorties du stock pour l'OT
	if(getOtCourant()!=null){
		ArrayList listPiecesInfos = PiecesInfos.chercherPiecesInfosOT(getTransaction(),getOtCourant().getNumeroot());
		if(getTransaction().isErreur()){
			return false;
		}
		setListPieces(listPiecesInfos);
	}
	if(getListPieces()!=null){
		if(getListPieces().size()>0){
			int [] tailles = {30,10,6,6,6};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G","C","D","D","D"};
			FormateListe aFormat = new FormateListe(tailles,padding,false);
			for (int i = 0; i < getListPieces().size() ; i++) {
				PiecesInfos maPiecesInfos = (PiecesInfos)getListPieces().get(i);	
				// calcul du total par type de pièces
				int total = Integer.parseInt(maPiecesInfos.getQuantite())*Integer.parseInt(maPiecesInfos.getPrix());
				String ligne [] = { maPiecesInfos.getDesignationpiece(),maPiecesInfos.getDatesortie(),maPiecesInfos.getPrix(),maPiecesInfos.getQuantite(),String.valueOf(total)};
				aFormat.ajouteLigne(ligne);
			}
			setLB_PIECES(aFormat.getListeFormatee());
		}else{
			setLB_PIECES(LBVide);
		}	
	}else{
		setLB_PIECES(LBVide);
	}
	return true;
}

/**
 * Constructeur du process OeOT_Modification.
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public OeOT_Modification() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTENT
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_AJOUTENT() {
	return "NOM_PB_AJOUTENT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosOT(request);
	
	//addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	VariableActivite.ajouter(this,"OT_TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_CREATION);
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	PePerso unPePerso = new PePerso();
	VariableGlobale.ajouter(request,"PEPERSO",unPePerso);
	if(isCreation()){
		setStatut(STATUT_CREATIONOT,true);
	}else{
		setStatut(STATUT_ENTRETIENS,true);
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTMECA
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_AJOUTMECA() {
	return "NOM_PB_AJOUTMECA";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTMECA(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosOT(request);
	OT_ATM unOT_ATM = new OT_ATM();
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	VariableActivite.ajouter(this,"OT_ATM",unOT_ATM);
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	VariableActivite.ajouter(this,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	setStatut(STATUT_MECANICIEN,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTPIECE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_AJOUTPIECE() {
	return "NOM_PB_AJOUTPIECE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTPIECE(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosOT(request);
	
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	VariableGlobale.ajouter(request,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	Pieces unePiece = new Pieces();
	VariableGlobale.ajouter(request,"PIECES",unePiece);
	setStatut(STATUT_PIECES,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFENT
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MODIFENT() {
	return "NOM_PB_MODIFENT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public boolean performPB_MODIFENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosOT(request);
	
	// on récupère le peperso sélectionné
	int numligne = (Services.estNumerique(getZone(getNOM_LB_INTERVENTIONS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_INTERVENTIONS_SELECT())) : -1);
	if (numligne == -1 || getListInterventions().size() == 0 || numligne > getListInterventions().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Entretiens"));
		return false;
	}
	PePersoInfos unPePersoInfos = (PePersoInfos)getListInterventions().get(numligne);
	PePerso unPePerso = PePerso.chercherPePerso(getTransaction(),unPePersoInfos.getCodepep());
	if(getTransaction().isErreur()){
		return false;
	}
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
	VariableActivite.ajouter(this,"OT_TITRE_ACTION",ACTION_MODIFICATION);
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_MODIFICATION);
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	VariableGlobale.ajouter(request,"PEPERSO",unPePerso);
	setStatut(STATUT_ENTRETIENS,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFMECA
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MODIFMECA() {
	return "NOM_PB_MODIFMECA";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public boolean performPB_MODIFMECA(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosOT(request);
	
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
	VariableGlobale.ajouter(request,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	setStatut(STATUT_MECANICIEN,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFPIECE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MODIFPIECE() {
	return "NOM_PB_MODIFPIECE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public boolean performPB_MODIFPIECE(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosOT(request);
//	 on récupère le peperso sélectionné
	int numligne = (Services.estNumerique(getZone(getNOM_LB_PIECES_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_PIECES_SELECT())) : -1);
	if (numligne == -1 || getListPieces().size() == 0 || numligne > getListPieces().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Pièces"));
		return false;
	}
	PiecesInfos unPiecesInfos = (PiecesInfos)getListPieces().get(numligne);
	
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
	VariableGlobale.ajouter(request,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	VariableGlobale.ajouter(request,"PIECEINFOS",unPiecesInfos);
	setStatut(STATUT_PIECES,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPENT
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_SUPENT() {
	return "NOM_PB_SUPENT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public boolean performPB_SUPENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosOT(request);
	
	// on récupère l'entretien sélectionné
	int numligne = (Services.estNumerique(getZone(getNOM_LB_INTERVENTIONS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_INTERVENTIONS_SELECT())) : -1);
	if (numligne == -1 || getListInterventions().size() == 0 || numligne > getListInterventions().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Entretiens"));
		return false;
	}
	PePersoInfos unPePersoInfos = (PePersoInfos)getListInterventions().get(numligne);
	PePerso unPePerso = PePerso.chercherPePerso(getTransaction(),unPePersoInfos.getCodepep());
	if(getTransaction().isErreur()){
		return false;
	}
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	VariableActivite.ajouter(this,"OT_TITRE_ACTION",ACTION_SUPPRESSION);
	VariableGlobale.ajouter(request,"TITRE_ACTION",ACTION_SUPPRESSION);//getVAL_ST_TITRE_ACTION());
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_SUPPRESSION);//getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	VariableGlobale.ajouter(request,"PEPERSO",unPePerso);
	setStatut(STATUT_ENTRETIENS,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPMECA
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_SUPMECA() {
	return "NOM_PB_SUPMECA";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public boolean performPB_SUPMECA(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosOT(request);
//	 on récupère le ot_atm sélectionné
	int numligne = (Services.estNumerique(getZone(getNOM_LB_INTERVENANTS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_INTERVENANTS_SELECT())) : -1);
	if (numligne == -1 || getListIntervenants().size() == 0 || numligne > getListIntervenants().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Agents"));
		return false;
	}
	OT_ATM unOT_ATM = (OT_ATM)getListIntervenants().get(numligne);
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	VariableActivite.ajouter(this,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableActivite.ajouter(this,"OT_ATM",unOT_ATM);
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	setStatut(STATUT_MECANICIEN,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPIECE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_SUPPIECE() {
	return "NOM_PB_SUPPIECE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public boolean performPB_SUPPIECE(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosOT(request);
	
//	 on récupère le peperso sélectionné
	int numligne = (Services.estNumerique(getZone(getNOM_LB_PIECES_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_PIECES_SELECT())) : -1);
	if (numligne == -1 || getListPieces().size() == 0 || numligne > getListPieces().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Pièces"));
		return false;
	}
	PiecesInfos unPiecesInfos = (PiecesInfos)getListPieces().get(numligne);
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	VariableGlobale.ajouter(request,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	VariableGlobale.ajouter(request,"PIECEINFOS",unPiecesInfos);
	setStatut(STATUT_PIECES,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	String commentaire = "";
//	récupération des infos renseignées
	recupereInfosOT(request);
	
	// on cherche si c'est un petit matériel
	Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("L'équipement n'a pas été trouvé.");
		return false;
	}
	/*Modeles unModele = Modeles.chercherModeles(getTransaction(),unEquipement.getCodemodele());
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("Le modèle n'a pas été trouvé.");
		return false;
	}
	TYPEEQUIP unTe = TYPEEQUIP.chercherTYPEEQUIP(getTransaction(),unModele.getCodete());
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("Le type d'équipement n'a pas été trouvé.");
		return false;
	}
	// Rg : le compteur doit être obligatoire
	/*if(!("MT").equals(unTe.getTypete())){
		if (getZone(getNOM_EF_COMPTEUR()).equals("")){
			getTransaction().declarerErreur("Le compteur est obligatoire.");
			return false;
		}
	}*/
	
	//si equipementinfos est null
	if(getEquipementInfosCourant()!=null){
		if(getEquipementInfosCourant().getNumeroinventaire()==null){
			getTransaction().declarerErreur("Aucun équipement n'a été selectionné.");
			return false;
		}
	}else{
		getTransaction().declarerErreur("Aucun équipement n'a été selectionné.");
		return false;
	}
	
	
	
	// contrôle de la longueur de la zone anomalie
	commentaire = getZone(getNOM_EF_COMMENTAIRE());
	if (commentaire.length()>600){
		getTransaction().declarerErreur("La longueur du commentaire ne doit pas dépasser 600 caractères.");
		setFocus(getNOM_EF_COMMENTAIRE());
		return false;
	}
	
	if(getTransaction().isErreur()){
		return false;
	}
	if(getOtCourant()==null){
		getTransaction().declarerErreur("L'OT ne peut être généré, aucun équipement n'a été sélectionné");
		return false;
	}
	getOtCourant().setCompteur(newCompteur);
	getOtCourant().setDateentree(newDEntree);
	getOtCourant().setDatesortie(newDSortie);
	getOtCourant().setCommentaire(newCommentaire);
	/*Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
	if(getTransaction().isErreur()){
		return false;
	}*/
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		getOtCourant().creerOT(getTransaction(),unEquipement);
	}else if(getVAL_ST_TITRE_ACTION().equals(ACTION_MODIFICATION)){
		getOtCourant().modifierOT(getTransaction(),unEquipement);
	}
	if(getTransaction().isErreur()){
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
			setOtCourant(new OT());
		}
		return false;
	}
	commitTransaction();
	//pour conserver le même OT durant la navigation
	VariableGlobale.ajouter(request,"OT",getOtCourant());
//	 si création d'un OT : on force les entretiens
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
		VariableActivite.ajouter(this,"OT_TITRE_ACTION",ACTION_CREATION);
		VariableActivite.ajouter(this,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
		VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
		//VariableGlobale.ajouter(request,"OT",getOtCourant());
		PePerso unPePerso = new PePerso();
		VariableGlobale.ajouter(request,"PEPERSO",unPePerso);
		//addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
		if (!performPB_AJOUTENT(request)){
			return false;
		}
	}else{
		setStatut(STATUT_PROCESS_APPELANT);
	}
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOOT
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOOT() {
	return "NOM_ST_NOOT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOOT
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOOT() {
	return getZone(getNOM_ST_NOOT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_COMPTEUR
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_COMPTEUR() {
	return "NOM_EF_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_COMPTEUR
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_COMPTEUR() {
	return getZone(getNOM_EF_COMPTEUR());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DENTREE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DENTREE() {
	return "NOM_EF_DENTREE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DENTREE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DENTREE() {
	return getZone(getNOM_EF_DENTREE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DSORTIE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DSORTIE() {
	return "NOM_EF_DSORTIE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DSORTIE
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DSORTIE() {
	return getZone(getNOM_EF_DSORTIE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_INTERVENANTS
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
private String [] getLB_INTERVENANTS() {
	if (LB_INTERVENANTS == null)
		LB_INTERVENANTS = initialiseLazyLB();
	return LB_INTERVENANTS;
}
/**
 * Setter de la liste:
 * LB_INTERVENANTS
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
private void setLB_INTERVENANTS(java.lang.String[] newLB_INTERVENANTS) {
	LB_INTERVENANTS = newLB_INTERVENANTS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_INTERVENANTS
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INTERVENANTS() {
	return "NOM_LB_INTERVENANTS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_INTERVENANTS_SELECT
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INTERVENANTS_SELECT() {
	return "NOM_LB_INTERVENANTS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_INTERVENANTS
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_INTERVENANTS() {
	return getLB_INTERVENANTS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_INTERVENANTS
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_INTERVENANTS_SELECT() {
	return getZone(getNOM_LB_INTERVENANTS_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_INTERVENTIONS
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
private String [] getLB_INTERVENTIONS() {
	if (LB_INTERVENTIONS == null)
		LB_INTERVENTIONS = initialiseLazyLB();
	return LB_INTERVENTIONS;
}
/**
 * Setter de la liste:
 * LB_INTERVENTIONS
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
private void setLB_INTERVENTIONS(java.lang.String[] newLB_INTERVENTIONS) {
	LB_INTERVENTIONS = newLB_INTERVENTIONS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_INTERVENTIONS
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INTERVENTIONS() {
	return "NOM_LB_INTERVENTIONS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_INTERVENTIONS_SELECT
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INTERVENTIONS_SELECT() {
	return "NOM_LB_INTERVENTIONS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_INTERVENTIONS
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_INTERVENTIONS() {
	return getLB_INTERVENTIONS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_INTERVENTIONS
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_INTERVENTIONS_SELECT() {
	return getZone(getNOM_LB_INTERVENTIONS_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_PIECES
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
private String [] getLB_PIECES() {
	if (LB_PIECES == null)
		LB_PIECES = initialiseLazyLB();
	return LB_PIECES;
}
/**
 * Setter de la liste:
 * LB_PIECES
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
private void setLB_PIECES(java.lang.String[] newLB_PIECES) {
	LB_PIECES = newLB_PIECES;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_PIECES
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PIECES() {
	return "NOM_LB_PIECES";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PIECES_SELECT
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PIECES_SELECT() {
	return "NOM_LB_PIECES_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_PIECES
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_PIECES() {
	return getLB_PIECES();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_PIECES
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_PIECES_SELECT() {
	return getZone(getNOM_LB_PIECES_SELECT());
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
	public ArrayList getListIntervenants() {
		if(listIntervenants==null){
			listIntervenants = new ArrayList();
		}
		return listIntervenants;
	}
	public void setListIntervenants(ArrayList listIntervenants) {
		this.listIntervenants = listIntervenants;
	}
	public ArrayList getListInterventions() {
		if(listInterventions==null){
			listInterventions = new ArrayList();
		}
		return listInterventions;
	}
	public void setListInterventions(ArrayList listInterventions) {
		this.listInterventions = listInterventions;
	}
	public ArrayList getListPieces() {
		if(listPieces==null){
			listPieces = new ArrayList();
		}
		return listPieces;
	}
	public void setListPieces(ArrayList listPieces) {
		this.listPieces = listPieces;
	}
	public OTInfos getOtInfosCourant() {
		return otInfosCourant;
	}
	public void setOtInfosCourant(OTInfos otInfosCourant) {
		this.otInfosCourant = otInfosCourant;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHEREQUIP
 * Date de création : (05/08/05 10:16:24)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECHERCHEREQUIP() {
	return "NOM_PB_RECHERCHEREQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (05/08/05 10:16:24)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHEREQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosOT(request);
	VariableActivite.ajouter(this,"MODE","OT_MAJ");
	setStatut(STATUT_RECHERCHEREQUIP,true);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (05/08/05 10:16:24)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (05/08/05 10:16:24)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
	public boolean isCreation() {
		return creation;
	}
	public void setCreation(boolean creation) {
		this.creation = creation;
	}
	
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTFRE
 * Date de création : (09/08/05 08:08:36)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_AJOUTFRE() {
	return "NOM_PB_AJOUTFRE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (09/08/05 08:08:36)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTFRE(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosOT(request);
	BE unBE = new BE();
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	VariableActivite.ajouter(this,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	VariableActivite.ajouter(this,"BE",unBE);
	setStatut(STATUT_FRE,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMERFRE
 * Date de création : (09/08/05 08:08:36)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_SUPPRIMERFRE() {
	return "NOM_PB_SUPPRIMERFRE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (09/08/05 08:08:36)
 * @author : Générateur de process
 */
public boolean performPB_SUPPRIMERFRE(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosOT(request);
//	 on récupère le peperso sélectionné
	int numligne = (Services.estNumerique(getZone(getNOM_LB_FRE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_FRE_SELECT())) : -1);
	if (numligne == -1 || getListBe().size() == 0 || numligne > getListBe().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Bons d'engagement"));
		return false;
	}
	BE unBE = (BE)getListBe().get(numligne);
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	VariableActivite.ajouter(this,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	VariableActivite.ajouter(this,"BE",unBE);
	setStatut(STATUT_FRE,true);
	return true;
}
	private java.lang.String[] LB_FRE;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_FRE
 * Date de création : (09/08/05 08:09:25)
 * @author : Générateur de process
 */
private String [] getLB_FRE() {
	if (LB_FRE == null)
		LB_FRE = initialiseLazyLB();
	return LB_FRE;
}
/**
 * Setter de la liste:
 * LB_FRE
 * Date de création : (09/08/05 08:09:25)
 * @author : Générateur de process
 */
private void setLB_FRE(java.lang.String[] newLB_FRE) {
	LB_FRE = newLB_FRE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_FRE
 * Date de création : (09/08/05 08:09:25)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_FRE() {
	return "NOM_LB_FRE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_FRE_SELECT
 * Date de création : (09/08/05 08:09:25)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_FRE_SELECT() {
	return "NOM_LB_FRE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_FRE
 * Date de création : (09/08/05 08:09:25)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_FRE() {
	return getLB_FRE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_FRE
 * Date de création : (09/08/05 08:09:25)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_FRE_SELECT() {
	return getZone(getNOM_LB_FRE_SELECT());
}
	public ArrayList getListBe() {
		return listBe;
	}
	public void setListBe(ArrayList listBe) {
		this.listBe = listBe;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:56:14)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:56:14)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TCOMPTEUR
 * Date de création : (23/08/05 09:11:22)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TCOMPTEUR() {
	return "NOM_ST_TCOMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TCOMPTEUR
 * Date de création : (23/08/05 09:11:22)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TCOMPTEUR() {
	return getZone(getNOM_ST_TCOMPTEUR());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_COMMENTAIRE
 * Date de création : (24/08/05 08:26:27)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_COMMENTAIRE() {
	return "NOM_EF_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_COMMENTAIRE
 * Date de création : (24/08/05 08:26:27)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_COMMENTAIRE() {
	return getZone(getNOM_EF_COMMENTAIRE());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHER
 * Date de création : (09/09/05 08:41:25)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECHERCHER() {
	return "NOM_PB_RECHERCHER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (09/09/05 08:41:25)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHER(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosOT(request);
//	si un numéro saisie on recherche l'équipement équivalent
	String numero = getZone(getNOM_EF_RECHERCHER());
	EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfosInvOuImmat(getTransaction(),numero);
	if(getTransaction().isErreur()){
		return false;
	}
	if(unEquipementInfos!=null){
		setEquipementInfosCourant(unEquipementInfos);
	}
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHER
 * Date de création : (09/09/05 08:41:25)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_RECHERCHER() {
	return "NOM_EF_RECHERCHER";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHER
 * Date de création : (09/09/05 08:41:25)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_RECHERCHER() {
	return getZone(getNOM_EF_RECHERCHER());
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
 * @param focus focus à définir.
 */
public String getDefaultFocus() {
	return getNOM_EF_RECHERCHER();
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULE_SUPP_BE
 * Date de création : (17/10/06 08:29:53)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_ANNULE_SUPP_BE() {
	return "NOM_PB_ANNULE_SUPP_BE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (17/10/06 08:29:53)
 * @author : Générateur de process
 */
public boolean performPB_ANNULE_SUPP_BE(javax.servlet.http.HttpServletRequest request) throws Exception {
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_SUP_BE
 * Date de création : (17/10/06 08:29:53)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_SUP_BE() {
	return "NOM_PB_OK_SUP_BE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (17/10/06 08:29:53)
 * @author : Générateur de process
 */
public boolean performPB_OK_SUP_BE(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on retire l'engagement de l'OT
	if (!("").equals(elementBE)){
		BE unBE = BE.chercheBE(getTransaction(),getOtCourant().getNumeroot(),elementBE);
		if (getTransaction().isErreur()){
			return false;
		}
		unBE.supprimerBE(getTransaction());
		if (getTransaction().isErreur()){
			return false;
		}else{
		commitTransaction();
		}
	}
	bErreurBe = false;
	elementBE = "";
	listeBEInexistant ="";
	//initialiseZones(request);
	return true;
}
public boolean isDebrancheDec() {
	return isDebrancheDec;
}
public void setDebrancheDec(boolean isDebrancheDec) {
	this.isDebrancheDec = isDebrancheDec;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_DECLARATIONS
		if (testerParametre(request, getNOM_PB_DECLARATIONS())) {
			return performPB_DECLARATIONS(request);
		}

		//Si clic sur le bouton PB_ANNULE_SUPP_BE
		if (testerParametre(request, getNOM_PB_ANNULE_SUPP_BE())) {
			return performPB_ANNULE_SUPP_BE(request);
		}

		//Si clic sur le bouton PB_OK_SUP_BE
		if (testerParametre(request, getNOM_PB_OK_SUP_BE())) {
			return performPB_OK_SUP_BE(request);
		}


		//Si clic sur le bouton PB_AJOUTFRE
		if (testerParametre(request, getNOM_PB_AJOUTFRE())) {
			return performPB_AJOUTFRE(request);
		}

		//Si clic sur le bouton PB_SUPPRIMERFRE
		if (testerParametre(request, getNOM_PB_SUPPRIMERFRE())) {
			return performPB_SUPPRIMERFRE(request);
		}

//		Si clic sur le bouton PB_RECHERCHER
		if (testerParametre(request, getNOM_PB_RECHERCHER())) {
			return performPB_RECHERCHER(request);
		}
		
		//Si clic sur le bouton PB_RECHERCHEREQUIP
		if (testerParametre(request, getNOM_PB_RECHERCHEREQUIP())) {
			return performPB_RECHERCHEREQUIP(request);
		}

		//Si clic sur le bouton PB_AJOUTENT
		if (testerParametre(request, getNOM_PB_AJOUTENT())) {
			return performPB_AJOUTENT(request);
		}

		//Si clic sur le bouton PB_AJOUTMECA
		if (testerParametre(request, getNOM_PB_AJOUTMECA())) {
			return performPB_AJOUTMECA(request);
		}

		//Si clic sur le bouton PB_AJOUTPIECE
		if (testerParametre(request, getNOM_PB_AJOUTPIECE())) {
			return performPB_AJOUTPIECE(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_MODIFENT
		if (testerParametre(request, getNOM_PB_MODIFENT())) {
			return performPB_MODIFENT(request);
		}

		//Si clic sur le bouton PB_MODIFMECA
		if (testerParametre(request, getNOM_PB_MODIFMECA())) {
			return performPB_MODIFMECA(request);
		}

		//Si clic sur le bouton PB_MODIFPIECE
		if (testerParametre(request, getNOM_PB_MODIFPIECE())) {
			return performPB_MODIFPIECE(request);
		}

		//Si clic sur le bouton PB_SUPENT
		if (testerParametre(request, getNOM_PB_SUPENT())) {
			return performPB_SUPENT(request);
		}

		//Si clic sur le bouton PB_SUPMECA
		if (testerParametre(request, getNOM_PB_SUPMECA())) {
			return performPB_SUPMECA(request);
		}

		//Si clic sur le bouton PB_SUPPIECE
		if (testerParametre(request, getNOM_PB_SUPPIECE())) {
			return performPB_SUPPIECE(request);
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
 * Date de création : (03/04/07 10:39:38)
 * @author : Générateur de process
 */
public String getJSP() {
	return "OeOT_Modification.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_DECLARATIONS
 * Date de création : (03/04/07 10:39:39)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_DECLARATIONS() {
	return "NOM_PB_DECLARATIONS";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (03/04/07 10:39:39)
 * @author : Générateur de process
 */
public boolean performPB_DECLARATIONS(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_DECLARATIONS,false);
	return true;
}
}
