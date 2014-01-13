package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.StringTokenizer;

import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.BE;
import nc.mairie.seat.metier.ENGJU;
import nc.mairie.seat.metier.FPMComplete;
import nc.mairie.seat.metier.FPM;
import nc.mairie.seat.metier.PM_ATM;
import nc.mairie.seat.metier.PM_BE;
import nc.mairie.seat.metier.PM_PePerso;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.seat.metier.Pieces;
import nc.mairie.seat.metier.PiecesFpmInfos;
import nc.mairie.seat.metier.Pm_PePersoInfos;
import nc.mairie.technique.*;
/**
 * Process OeFPM_Modification
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
*/
public class OeFPM_Modification extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3189721232717479317L;
	public static final int STATUT_DECLARATIONS = 7;
	public static final int STATUT_CREATIONFPM = 6;
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
	private PMatInfos pMatInfosCourant;
	private FPM fpmCourant;
	private FPMComplete fpmCompleteCourant;
	private ArrayList<PM_PePerso> listInterventions;
	private ArrayList<PM_ATM> listIntervenants;
	private ArrayList<PM_BE> listBe;
	private ArrayList<PiecesFpmInfos> listPieces;
	private String newDSortie;
	private String newDEntree;
	private String newCommentaire;
	private String newNoFiche;
	private boolean first = true;
	public boolean creation;
	public String focus;
	private String messageErreur="";
	private	String listeBEInexistant = "";
	public String elementBE = "";
	public boolean	bErreurBe;
	public boolean isDebrancheDec = false;
	public boolean isModif = false;
	
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
		PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
		if(unPMatInfos!=null){
			if (unPMatInfos.getPminv()!=null){
				setPMatInfosCourant(unPMatInfos);
			}
		}
//	}*/
		if(getTransaction().isErreur()){
			if (!getTransaction().getMessageErreur().equals("")){
				messageErreur = messageErreur + getTransaction().getMessageErreur();
				getTransaction().traiterErreur();
			}
		}
	if(etatStatut()!=0){
		FPM unFPM = (FPM)VariableGlobale.recuperer(request,"FPM");
		setFpmCourant(unFPM);
		if(getFpmCourant()!=null){
			if(getFpmCourant().getNumfiche()!=null){
				FPM.chercherFPM(getTransaction(),getFpmCourant().getNumfiche());
				if (getTransaction().isErreur()){
					getTransaction().traiterErreur();
					addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
					isModif = false;
				}else{
					addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
					isModif = true;
				}
			}else{
				addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
				isModif = false;
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
			setFpmCourant(new FPM());
			setCreation(true);
			isModif = true;
		}else{
			FPM unFPM = (FPM)VariableGlobale.recuperer(request,"FPM");
			if(unFPM!=null){
				setFpmCourant(unFPM);
			}
			isModif = false;
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
	}
	
	// controle pour initialisation des champs
	if (getFpmCourant()!=null){
		if(getFpmCourant().getNumfiche()!=null){
			FPM.chercherFPM(getTransaction(),getFpmCourant().getNumfiche());
			if (getTransaction().isErreur()){
				getTransaction().traiterErreur();
			}else{
			newNoFiche = getFpmCourant().getNumfiche();
			newCommentaire = getFpmCourant().getCommentaire();
			
			if((getFpmCourant().getDentree()==null)||(getFpmCourant().getDentree().equals("01/01/0001"))){
				newDEntree = "";
			}else{
				newDEntree = getFpmCourant().getDentree();
			}
			if((getFpmCourant().getDsortie()==null)||(getFpmCourant().getDsortie().equals("01/01/0001"))){
				newDSortie = "";
			}else{
				newDSortie = getFpmCourant().getDsortie();
			}
			//initialisation des listes
			initialiseListIntervenants(request);
			initialiseListInterventions(request);
			initialiseListBe(request);
			initialiseListPieces(request);
			//	initialisation des zones
			addZone(getNOM_ST_NOOT(),newNoFiche);
			addZone(getNOM_EF_DENTREE(),newDEntree);
			addZone(getNOM_EF_DSORTIE(),newDSortie);
			addZone(getNOM_EF_COMMENTAIRE(),newCommentaire);
			}
		}else{
//			 initialisation des zones de FPM
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
			if((newCommentaire!=null)&&(!newCommentaire.equals(""))){
				addZone(getNOM_EF_COMMENTAIRE(),newCommentaire);
			}else{
				addZone(getNOM_EF_COMMENTAIRE(),"");
			}
		}
	//sinon on met les champs vides
	}else{
		setCreation(true);
		setFpmCourant(new FPM());
		//	 initialisation des zones de FPM
		addZone(getNOM_ST_NOOT(),"");
		addZone(getNOM_EF_COMPTEUR(),"");
		addZone(getNOM_EF_DENTREE(),"");
		addZone(getNOM_EF_DSORTIE(),"");
	}
	if(getPMatInfosCourant()!=null){
		//initialisation des zones
		addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
		addZone(getNOM_ST_MARQUE(),getPMatInfosCourant().getDesignationmarque());
		addZone(getNOM_ST_MODELE(),getPMatInfosCourant().getDesignationmodele());
		addZone(getNOM_ST_NOSERIE(),getPMatInfosCourant().getPmserie());
		addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
	}else{
		addZone(getNOM_ST_TYPE(),"");
		addZone(getNOM_ST_MARQUE(),"");
		addZone(getNOM_ST_MODELE(),"");
		addZone(getNOM_ST_NOSERIE(),"");
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
	if((messageErreur!=null) && !messageErreur.equals("")){
		getTransaction().declarerErreur(messageErreur);
		messageErreur = "";
	}
//	 on traite le cas où un (ou plusieurs) bons d'engagement n'a pas été trouvé.
	if (!("").equals(listeBEInexistant)&&(listeBEInexistant!=null)){
		StringTokenizer stListBE = new StringTokenizer(listeBEInexistant,";");
		while(stListBE.hasMoreElements()){
			elementBE = stListBE.nextToken();
			bErreurBe = true;
			getTransaction().declarerErreur("Le bon d'engagement "+elementBE+" n'a pas été trouvé. Cliquer sur le bouton 'Retirer' pour le retirer de la Fiche du petit matériel ?");
		}
	}
	setFirst(false);
}
public boolean initialiseListPieces(javax.servlet.http.HttpServletRequest request) throws Exception{
//	 on initialise la liste des pièces sorties du stock pour l'OT
	if(getFpmCourant()!=null){
		ArrayList<PiecesFpmInfos> listPiecesInfos = PiecesFpmInfos.chercherFpmPiecesInfosFpm(getTransaction(),getFpmCourant().getNumfiche());
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
				PiecesFpmInfos maPiecesInfos = (PiecesFpmInfos)getListPieces().get(i);	
				// calcul du total par type de pièces
				int total = Integer.parseInt(maPiecesInfos.getQuantite())*Integer.parseInt(maPiecesInfos.getPrix());
				String ligne [] = { maPiecesInfos.getDesignationpiece(),maPiecesInfos.getDsortie(),maPiecesInfos.getPrix(),maPiecesInfos.getQuantite(),String.valueOf(total)};
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
/*Si on débranche sur une fenêtre (actions sur les entretiens, intervenants ou pièces)
 *il faut quand même garder les éventuelles modifications que l'utilisateur a fait
 * sur les dates et le comtpeur
 */
public void recupereInfosFPM(javax.servlet.http.HttpServletRequest request) throws Exception{
	newDEntree = getZone(getNOM_EF_DENTREE());
	newDSortie = getZone(getNOM_EF_DSORTIE());
	newCommentaire = getZone(getNOM_EF_COMMENTAIRE()).toUpperCase();
}

public boolean initialiseListInterventions(javax.servlet.http.HttpServletRequest request) throws Exception{
	// on initialise la liste des interventions réalisées pendant FPM
	if(getFpmCourant()!=null){
		//ArrayList listInterventions= Pm_PePersoInfos.chercherPmPePersoInfosFPM(getTransaction(),getFpmCourant().getNumfiche());
		ArrayList<PM_PePerso> listInterventions= PM_PePerso.chercherPmPePersoFiche(getTransaction(),getFpmCourant().getNumfiche());
		if(getTransaction().isErreur()){
			return false;
		}
		setListInterventions(listInterventions);
	}
	
	//if(getListInterventions()!=null){
		if(getListInterventions().size()>0){
			int [] tailles = {30,10,10};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G","C","G"};
			FormateListe aFormat = new FormateListe(tailles,padding,false);
			for (int i = 0; i < listInterventions.size() ; i++) {
				PM_PePerso monPePersoInfos = (PM_PePerso)listInterventions.get(i);
				Pm_PePersoInfos unPeP = Pm_PePersoInfos.chercherPm_PePersoInfos(getTransaction(),monPePersoInfos.getCodepmpep());
				if(getTransaction().isErreur()){
					return false;
				}
				String datereal = "";
				if(monPePersoInfos.getDreal().equals("01/01/0001")){
					datereal = "";
				}else{
					datereal = monPePersoInfos.getDreal();
				}
				String ligne [] = { unPeP.getLibelleentretien(),datereal,monPePersoInfos.getDuree()};
				aFormat.ajouteLigne(ligne);
			}
			setLB_INTERVENTIONS(aFormat.getListeFormatee());
		}else{
			setLB_INTERVENTIONS(LBVide);
		}
return true;		
}

public boolean initialiseListBe(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList<PM_BE> listBe = PM_BE.listerPM_BE_FPM(getTransaction(),getFpmCourant().getNumfiche());
	if(getTransaction().isErreur()){
		return false;
	}
	setListBe(listBe);
	if(getListBe().size()>0){
		int tailles [] = {10,10,30,10};
		String[] padding = {"G","G","G","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i=0;i<getListBe().size();i++){
			PM_BE unBe = (PM_BE)getListBe().get(i);
			ENGJU unEnju = ENGJU.chercherpremierENGJU(getTransaction(), unBe.getExerci(), unBe.getNoengj());
			if(getTransaction().isErreur()){
				// on ne l'a pas trouvé : on met le numéro de l'engagement dans un variable pour traiter ce problème après
				listeBEInexistant = unBe.getNoengj()+";";
				getTransaction().traiterErreur() ;
			}else{
				//on recherche les lignes concernant l'engagement juridique et on calcul le montant
				int montantTotal = ENGJU.montantBE(getTransaction(), unEnju);
				if(montantTotal==-1){
					getTransaction().declarerErreur("Une erreur s'est produite lors du calcul du montant du bon d'engagement.");
					return false;
				}
				//on renseigne la lb
				String ligne [] = { unEnju.getNoengj(),unEnju.getIdetbs(),unEnju.getEnscom(),String.valueOf(montantTotal)};
				aFormat.ajouteLigne(ligne);
			}
		}
		setLB_FRE(aFormat.getListeFormatee());
	} else {
		setLB_FRE(null);
	}
	return true;
}

/*
public int montantBE(javax.servlet.http.HttpServletRequest request,ENGJU unEnju) throws Exception{
	// controle si unEnju null
	if (unEnju.getNoengj()==null){
		getTransaction().declarerErreur("Le Bon d'engagement passé en paramètre est null.");
		return -1;
	}
	int total = 0;
	// recherche des lignes du bon d'engagement
	ArrayList listLeju = ENGJU.listerENGJU(getTransaction(),unEnju.getExerci(),unEnju.getNoengj());
	if(getTransaction().isErreur()){
		return -1;
	}
	if(listLeju.size()>0){
		for(int i=0;i<listLeju.size();i++){
			ENGJU unLeju = (ENGJU)listLeju.get(i);
			int montant = Integer.parseInt(unLeju.getMtlenju());
			total = total + montant;
		}
	}
	
	return total;
}
*/
public boolean initialiseListIntervenants(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList<PM_ATM> listMeca = PM_ATM.listerPM_ATM_FPM(getTransaction(),getFpmCourant().getNumfiche());
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
			PM_ATM unPM_ATM = (PM_ATM)listMeca.get(i);
			Agents unAgent = Agents.chercherAgents(getTransaction(),unPM_ATM.getMatricule());
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

/**
 * Constructeur du process OeFPM_Modification.
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public OeFPM_Modification() {
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
	recupereInfosFPM(request);
	
	//addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	VariableActivite.ajouter(this,"FPM_TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_CREATION);
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	PM_PePerso unPePerso = new PM_PePerso();
	VariableGlobale.ajouter(request,"PMPEPERSO",unPePerso);
	if(isCreation()){
		setStatut(STATUT_CREATIONFPM,true);
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
	recupereInfosFPM(request);
	PM_ATM unPM_ATM = new PM_ATM();
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	VariableActivite.ajouter(this,"FPM_ATM",unPM_ATM);
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	VariableActivite.ajouter(this,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	setStatut(STATUT_MECANICIEN,true);
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
	recupereInfosFPM(request);
	
	// on récupère le peperso sélectionné
	int numligne = (Services.estNumerique(getZone(getNOM_LB_INTERVENTIONS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_INTERVENTIONS_SELECT())) : -1);
	if (numligne == -1 || getListInterventions().size() == 0 || numligne > getListInterventions().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Entretiens"));
		return false;
	}
	PM_PePerso unPePerso = (PM_PePerso)getListInterventions().get(numligne);
//	PM_PePerso unPePerso = PM_PePerso.chercherPM_PePerso(getTransaction(),unPePersoInfos.getCodepmpep());
//	if(getTransaction().isErreur()){
//		return false;
//	}
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
	VariableActivite.ajouter(this,"FPM_TITRE_ACTION",ACTION_MODIFICATION);
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_MODIFICATION);
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	VariableGlobale.ajouter(request,"PMPEPERSO",unPePerso);
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
	recupereInfosFPM(request);
	
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
	VariableGlobale.ajouter(request,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	setStatut(STATUT_MECANICIEN,true);
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
	recupereInfosFPM(request);
	
	// on récupère l'entretien sélectionné
	int numligne = (Services.estNumerique(getZone(getNOM_LB_INTERVENTIONS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_INTERVENTIONS_SELECT())) : -1);
	if (numligne == -1 || getListInterventions().size() == 0 || numligne > getListInterventions().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Entretiens"));
		return false;
	}
	PM_PePerso unPePerso = (PM_PePerso)getListInterventions().get(numligne);
//	PM_PePerso unPePerso = PM_PePerso.chercherPM_PePerso(getTransaction(),unPePersoInfos.getCodepmpep());
//	if(getTransaction().isErreur()){
//		return false;
//	}
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	VariableActivite.ajouter(this,"FPM_TITRE_ACTION",ACTION_SUPPRESSION);
	VariableGlobale.ajouter(request,"TITRE_ACTION",ACTION_SUPPRESSION);//getVAL_ST_TITRE_ACTION());
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_SUPPRESSION);//getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	VariableGlobale.ajouter(request,"PMPEPERSO",unPePerso);
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
	recupereInfosFPM(request);
//	 on récupère le FPM_atm sélectionné
	int numligne = (Services.estNumerique(getZone(getNOM_LB_INTERVENANTS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_INTERVENANTS_SELECT())) : -1);
	if (numligne == -1 || getListIntervenants().size() == 0 || numligne > getListIntervenants().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Agents"));
		return false;
	}
	PM_ATM unFPM_ATM = (PM_ATM)getListIntervenants().get(numligne);
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	VariableActivite.ajouter(this,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableActivite.ajouter(this,"FPM_ATM",unFPM_ATM);
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	setStatut(STATUT_MECANICIEN,true);
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
	recupereInfosFPM(request);
	
	// on cherche si c'est un petit matériel
	PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("Le petit matériel n'a pas été trouvé.");
		return false;
	}
	
	//si equipementinfos est null
	if(getPMatInfosCourant()!=null){
		if(getPMatInfosCourant().getPminv()==null){
			getTransaction().declarerErreur("Aucun petit matériel n'a été selectionné.");
			return false;
		}
	}else{
		getTransaction().declarerErreur("Aucun petit matériel n'a été selectionné.");
		return false;
	}
		
	// contrôle de la longueur de l'anomalie
	commentaire = getZone(getNOM_EF_COMMENTAIRE());
	if (commentaire.length()>600){
		getTransaction().declarerErreur("La longueur du commentaire ne doit pas dépasser 600 caractères.");
		setFocus(getNOM_EF_COMMENTAIRE());
		return false;
	}
	
	if(getTransaction().isErreur()){
		return false;
	}
	if(getFpmCourant()==null){
		getTransaction().declarerErreur("La fiche d'entretien ne peut être générée, aucun petit matériel n'a été sélectionné");
		return false;
	}
	getFpmCourant().setDentree(newDEntree);
	getFpmCourant().setDsortie(newDSortie);
	getFpmCourant().setCommentaire(newCommentaire);
	
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		getFpmCourant().creerFPM(getTransaction(),unPMateriel);
	}else if(getVAL_ST_TITRE_ACTION().equals(ACTION_MODIFICATION)){
		getFpmCourant().modifierPMateriel_Fiche(getTransaction(),unPMateriel);
	}
	if(getTransaction().isErreur()){
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
			setFpmCourant(new FPM());
		}
		return false;
	}
	commitTransaction();
	//pour conserver la même fiche durant la navigation
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
//	 si création d'une FPM : on force les entretiens
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
		VariableActivite.ajouter(this,"FPM_TITRE_ACTION",ACTION_CREATION);
		VariableActivite.ajouter(this,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
		VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
		PM_PePerso unPePerso = new PM_PePerso();
		VariableGlobale.ajouter(request,"PMPEPERSO",unPePerso);
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
public java.lang.String getNOM_ST_NOSERIE() {
	return "NOM_ST_NOSERIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOSERIE() {
	return getZone(getNOM_ST_NOSERIE());
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
	public PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
	}
	public FPM getFpmCourant() {
		return fpmCourant;
	}
	public void setFpmCourant(FPM fpmCourant) {
		this.fpmCourant = fpmCourant;
	}
	public ArrayList<PM_ATM> getListIntervenants() {
		if(listIntervenants==null){
			listIntervenants = new ArrayList<PM_ATM>();
		}
		return listIntervenants;
	}
	public void setListIntervenants(ArrayList<PM_ATM> listIntervenants) {
		this.listIntervenants = listIntervenants;
	}
	public ArrayList<PM_PePerso> getListInterventions() {
		if(listInterventions==null){
			listInterventions = new ArrayList<PM_PePerso>();
		}
		return listInterventions;
	}
	public void setListInterventions(ArrayList<PM_PePerso> listInterventions) {
		this.listInterventions = listInterventions;
	}
	public ArrayList<PiecesFpmInfos> getListPieces() {
		if(listPieces==null){
			listPieces = new ArrayList<PiecesFpmInfos>();
		}
		return listPieces;
	}
	public void setListPieces(ArrayList<PiecesFpmInfos> listPieces) {
		this.listPieces = listPieces;
	}
	public FPMComplete getFpmCompleteCourant() {
		return fpmCompleteCourant;
	}
	public void setFpmCompleteCourant(FPMComplete fpmCompleteCourant) {
		this.fpmCompleteCourant = fpmCompleteCourant;
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
	recupereInfosFPM(request);
	VariableActivite.ajouter(this,"MODE","FPM_MAJ");
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
	recupereInfosFPM(request);
	BE unBE = new BE();
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	VariableActivite.ajouter(this,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
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
	recupereInfosFPM(request);
//	 on récupère le peperso sélectionné
	int numligne = (Services.estNumerique(getZone(getNOM_LB_FRE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_FRE_SELECT())) : -1);
	if (numligne == -1 || getListBe().size() == 0 || numligne > getListBe().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Bons d'engagement"));
		return false;
	}
	PM_BE unPM_BE = (PM_BE)getListBe().get(numligne);
	//BE unBE = (BE)getListBe().get(numligne);
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	VariableActivite.ajouter(this,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	VariableActivite.ajouter(this,"PM_BE",unPM_BE);
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
	public ArrayList<PM_BE> getListBe() {
		return listBe;
	}
	public void setListBe(ArrayList<PM_BE> listBe) {
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
	recupereInfosFPM(request);
//	si un numéro saisie on recherche le petit matériel équivalent
	String numero = getZone(getNOM_EF_RECHERCHER());
	if(numero.equals("")){
		getTransaction().declarerErreur("La zone de recherche doit être renseignée.");
		return false;
	}
	PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),numero);
	if(getTransaction().isErreur()){
		return false;
	}
	if(unPMatInfos!=null){
		setPMatInfosCourant(unPMatInfos);
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
	// on retire l'engagement de la FPM
	if (!("").equals(elementBE)){
		PM_BE unBE = PM_BE.chercherPM_BE(getTransaction(),getFpmCourant().getNumfiche(),elementBE);
		if (getTransaction().isErreur()){
			return false;
		}
		unBE.supprimerPM_BE(getTransaction());
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
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (28/07/05 14:48:44)
 * @author : Générateur de process
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_AJOUTPIECES
		if (testerParametre(request, getNOM_PB_AJOUTPIECES())) {
			return performPB_AJOUTPIECES(request);
		}

		//Si clic sur le bouton PB_MODIFPIECES
		if (testerParametre(request, getNOM_PB_MODIFPIECES())) {
			return performPB_MODIFPIECES(request);
		}

		//Si clic sur le bouton PB_SUPPIECES
		if (testerParametre(request, getNOM_PB_SUPPIECES())) {
			return performPB_SUPPIECES(request);
		}

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

		//Si clic sur le bouton PB_SUPENT
		if (testerParametre(request, getNOM_PB_SUPENT())) {
			return performPB_SUPENT(request);
		}

		//Si clic sur le bouton PB_SUPMECA
		if (testerParametre(request, getNOM_PB_SUPMECA())) {
			return performPB_SUPMECA(request);
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
 * Date de création : (06/08/07 14:40:21)
 * @author : Générateur de process
 */
public String getJSP() {
	return "OeFPM_Modification.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTPIECES
 * Date de création : (06/08/07 14:40:21)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_AJOUTPIECES() {
	return "NOM_PB_AJOUTPIECES";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (06/08/07 14:40:21)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTPIECES(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosFPM(request);
	
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	VariableGlobale.ajouter(request,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	Pieces unePiece = new Pieces();
	VariableGlobale.ajouter(request,"PIECES",unePiece);
	setStatut(STATUT_PIECES,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFPIECES
 * Date de création : (06/08/07 14:40:21)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MODIFPIECES() {
	return "NOM_PB_MODIFPIECES";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (06/08/07 14:40:21)
 * @author : Générateur de process
 */
public boolean performPB_MODIFPIECES(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosFPM(request);
//	 on récupère le peperso sélectionné
	int numligne = (Services.estNumerique(getZone(getNOM_LB_PIECES_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_PIECES_SELECT())) : -1);
	if (numligne == -1 || getListPieces().size() == 0 || numligne > getListPieces().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Pièces"));
		return false;
	}
	PiecesFpmInfos unPiecesInfos = (PiecesFpmInfos)getListPieces().get(numligne);
	
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
	VariableGlobale.ajouter(request,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	VariableGlobale.ajouter(request,"PIECEFPMINFOS",unPiecesInfos);
	setStatut(STATUT_PIECES,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPIECES
 * Date de création : (06/08/07 14:40:21)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_SUPPIECES() {
	return "NOM_PB_SUPPIECES";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (06/08/07 14:40:21)
 * @author : Générateur de process
 */
public boolean performPB_SUPPIECES(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfosFPM(request);
	
//	 on récupère le peperso sélectionné
	int numligne = (Services.estNumerique(getZone(getNOM_LB_PIECES_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_PIECES_SELECT())) : -1);
	if (numligne == -1 || getListPieces().size() == 0 || numligne > getListPieces().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Pièces"));
		return false;
	}
	PiecesFpmInfos unPiecesInfos = (PiecesFpmInfos)getListPieces().get(numligne);
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	VariableGlobale.ajouter(request,"TITRE_ACTION",getVAL_ST_TITRE_ACTION());
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	VariableGlobale.ajouter(request,"PIECEFPMINFOS",unPiecesInfos);
	setStatut(STATUT_PIECES,true);
	return true;
}

}
