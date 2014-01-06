package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.mairie.seat.metier.BPC;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.ModePrise;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.seat.metier.Pompes;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
/**
 * Process OeBPC_ajout
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
*/
public class OePMBPC_ajout extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_RECH_EQUIP = 1;
	private java.lang.String[] LB_BPC;
	private java.lang.String[] LB_SERIE;
	private java.lang.String[] LB_INVENTAIRE;
	private java.lang.String[] LB_MODEPRISE;
	private java.lang.String[] LB_POMPE;
	private ArrayList listeBPC;
	private ArrayList listePompe;
	private ArrayList listePMateriel;
	private ArrayList listePMaterielInfos;
	private ArrayList listeModePrise;
	private BPC bpcCourant;
	private BPC bpcAvant;
	private PMateriel pMaterielCourant;
	private PMatInfos pMatInfosCourant;
	private Modeles modeleCourant;
	private int newNumeroBpc;
	private int codeModePrise;
	private Hashtable hashInventaire;
	private Hashtable hashImmat;
	private String focus = null;
	private boolean manqueParam = false;
	private boolean first = true;
	String newNumBpc = ""; 
	String newQte = "";
	String newDate ="";
	String newHeure = "";
	String newValeurCompteur = "";
	String newPompe = "";
	String newmodeprise ="1";
	private Pompes pompeCourante;
	private ArrayList listePompes = null;
	public boolean afficheMesage = false;
	
	//private String date;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	setAfficheMesage(false);
	if(first){
		PMatInfos unPMatInfos = (PMatInfos)VariableActivite.recuperer(this,"PMATINFOS");
		//EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request, "EQUIPEMENTINFOS");
		if(unPMatInfos!=null){
			if(unPMatInfos.getPminv()!=null){
				setPMatInfosCourant(unPMatInfos);
			}
		}
	}
	if(etatStatut()==1){
		// on récupère les objets
		
			PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request, "PMATINFOS");
			setPMatInfosCourant(unPMatInfos);
			//BPC unBPC = (BPC)VariableActivite.recuperer(this,"BPCAVANT");
			//setBpcAvant(unBPC);	
			// On récupère l'objet Equipement pour avoir les infos pour les modèles et marques
			if (null!=getPMatInfosCourant()){
				if(null!=getPMatInfosCourant().getPminv()){
					PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),unPMatInfos.getPminv());
					if(getTransaction().isErreur()){
						return;
					}
					setPMaterielCourant(unPMateriel);
				}
//				on récupère le modèle courant
				Modeles unModele = Modeles.chercherModeles(getTransaction(),getPMaterielCourant().getCodemodele());
				if(getTransaction().isErreur()){
					return ;
				}
				setModeleCourant(unModele);

			}
	}
//	 si erreur 
	if(getTransaction().isErreur()){
		return ;
	}
	// on recherche le service où l'équipement a été affecté 
	if(getPMatInfosCourant()!=null){
		if(getPMatInfosCourant().getPminv()!=null){
			PM_Affectation_Sce_Infos unPMASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),Services.dateDuJour());
			if(unPMASI.getPminv()==null){
					//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
					getTransaction().traiterErreur();
					addZone(getNOM_ST_SERVICE(),"pas affecté");
			}else{
				Service unService = Service.chercherService(getTransaction(),unPMASI.getSiserv());
				if(getTransaction().isErreur()){
					return;
				}
				addZone(getNOM_ST_SERVICE(),unPMASI.getSiserv()+" "+unService.getLiserv());
			}
		}else{
			addZone(getNOM_ST_SERVICE(),"");
		}
	}
	// on affiche le type de compteur pour rappel et le compteur précédent pour infos
//	if(getPMatInfosCourant()!=null){
//		if(getPMatInfosCourant().getPminv()!=null){
//			if(!getZone(getNOM_EF_DATE()).equals("")){
//				performPB_AFFICHE_COMPTEUR(request);
//			}
//			addZone(getNOM_ST_COMPTEUR(),getPMatInfosCourant().getDesignationcompteur());
//		}
//	}
	
	if(!newNumBpc.equals("")){
		addZone(getNOM_EF_BPC(),newNumBpc);
	}

		
	// on propose un numéro de BPC : si première ouverture :  max +1
	// sinon dernier numéro saisi +1
	if(first){
		BPC monBpc = new BPC();
		newNumeroBpc = monBpc.nouvBPC(getTransaction());
		if (newNumeroBpc>-1){
			addZone(getNOM_EF_BPC(),String.valueOf(newNumeroBpc));
		}else{
			addZone(getNOM_EF_BPC(),"1");
		}	
	}else{
		BPC unBPC = new BPC();
		while (unBPC.existeBPC(getTransaction(),newNumBpc)){
			newNumBpc = String.valueOf(Integer.parseInt(newNumBpc)+1);			
		}
		addZone(getNOM_EF_BPC(),newNumBpc);
	}
	
	
	//	on alimente la liste des pompes
	//String[] pompes = {"1","2"};
	//setLB_POMPE(pompes);
	
	// on initialise les listes déroulantes
	// la liste des pompes
//	 recherche de la pompe concerné en fonction du carbu de l'équipement
//	if (getPMatInfosCourant()!=null){
//		if(getPMatInfosCourant().getPminv()!=null){
//			PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
//			if(getTransaction().isErreur()){
//				return ;
//			}
//			setPMaterielCourant(unPMateriel);
//			Modeles unModele = Modeles.chercherModeles(getTransaction(),getPMaterielCourant().getCodemodele());
//			if(getTransaction().isErreur()){
//				return;
//			}
//			setModeleCourant(unModele);
//			Carburant unCarbu = Carburant.chercherCarburant(getTransaction(),getModeleCourant().getCodecarburant());
//			if(getTransaction().isErreur()){
//				return;
//			}
//			Pompes unePompe = Pompes.chercherPompes(getTransaction(),unCarbu.getNum_pompe_atm());
//			if(getTransaction().isErreur()){
//				return;
//			}
//			setPompeCourante(unePompe);
//		setAfficheMesage(true);
//		}
//	}
	initialiseListePompes(request);
//		 Si liste des modes de prise de carburant est vide
	if (getLB_MODEPRISE() == LBVide) {
		java.util.ArrayList a = ModePrise.listerModePrise(getTransaction());
		setListeModePrise(a);
		//les élèments de la liste 
		int [] tailles = {10};
		String [] champs = {"designationmodeprise"};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G"};
		
		FormateListe f = new FormateListe(tailles,a,champs,padding,false);
		String [] l = f.getListeFormatee();
		setLB_MODEPRISE(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
	}
	if(getLB_MODEPRISE()==null){
		if(LB_MODEPRISE.length>0){
			getTransaction().declarerErreur("Aucun mode de prise n'a été enregistré.L'enregistrement d'un BPC est impossible.");
			setManqueParam(true);
		}
	}
	
		
	// on initialise les infos concernant l'équipement
	if(getPMatInfosCourant()!=null){
		if(getPMatInfosCourant().getPminv()!=null){
			addZone(getNOM_ST_NOIMMAT(),getPMatInfosCourant().getPmserie());
			addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
			addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
			addZone(getNOM_ST_NOMEQUIP(),getPMatInfosCourant().getDesignationmarque()+" "+getPMatInfosCourant().getDesignationmodele());
			
		}else{
			addZone(getNOM_ST_NOIMMAT(),"");
			addZone(getNOM_ST_NOINVENT(),"");
			addZone(getNOM_ST_TYPE(),"");
			addZone(getNOM_ST_NOMEQUIP()," ");
			addZone(getNOM_ST_CARBU(),"");
		}
	}	
	if(first){
	//		on met la date de la veille par défaut
		String datedujour = Services.enleveJours(Services.dateDuJour(),1);
		// on met la dernière date saisie
		String date = (String)VariableActivite.recuperer(this,"DATE");
		if((date==null)||(date.equals(""))){
			addZone(getNOM_EF_DATE(),datedujour);
		}else{
			addZone(getNOM_EF_DATE(),date);
		}
		setFirst(false);
		if(getPMatInfosCourant()!=null){
			if(getZone(getNOM_EF_BPC()).equals("")){
				setFocus(getNOM_EF_BPC());
			}else{
				if(!getZone(getNOM_EF_DATE()).equals("")){
					setFocus(getNOM_EF_COMPTEUR());
					//performPB_AFFICHE_COMPTEUR(request);
				}else{
					setFocus(getNOM_EF_DATE());
				}
			}
		}else{
			setFocus(getNOM_EF_RECHERCHE());
		}
	}
	setFirst(false);
	
}

/*
 * initialisation de la liste des pompes
 */
public void initialiseListePompes(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList a = Pompes.listerPompes(getTransaction());
	if (getTransaction().isErreur()){
		return;
	}
	if (a.size()>0){
		//les élèments de la liste
		int [] tailles = {15};
		String [] champs = {"libelle_pompe"};
		
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G"};
		boolean[] colonnes = {true};
		a = Services.trier(a,champs,colonnes);
		setListePompes(a);
		FormateListe f = new FormateListe(tailles,a,champs,padding,false);
		String [] l = f.getListeFormatee();
		setLB_POMPE(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
	}else{
		setLB_POMPE(null);
	}
//	 on sélectionne la pompe en cours
//	if(getPompeCourante()!=null){
//		if(getPompeCourante().getNum_pompe()!=null){
//			int position = -1;
//			addZone(getNOM_LB_POMPE_SELECT(),String.valueOf(-1));
//			for (int i = 0; i < getListePompes().size(); i++) {
//				Pompes unePompe = (Pompes)getListePompes().get(i);
//				if (unePompe.getLibelle_pompe().equals(getPompeCourante().getLibelle_pompe())) {
//					addZone(getNOM_LB_POMPE_SELECT(),String.valueOf(i));
//					break;
//				}
//			}	
//		}
//	}
}

private void recupereInfos(javax.servlet.http.HttpServletRequest request){
	newNumBpc = getZone(getNOM_EF_BPC()); 
	newQte = getZone(getNOM_EF_QUANTITE());
	newDate ="";
	newHeure = getZone(getNOM_EF_HEURE());
	//newValeurCompteur = getZone(getNOM_EF_COMPTEUR());
	
	int pompe = Integer.parseInt(getZone(getNOM_LB_POMPE_SELECT()))+1;
	newPompe = String.valueOf(pompe);
	
	//int modeprise  = Integer.parseInt(getZone(getNOM_LB_MODEPRISE_SELECT()))+1;
	//newmodeprise = String.valueOf(modeprise);
}


/**
 * Constructeur du process OeBPC_ajout.
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public OePMBPC_ajout() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (30/05/05 10:15:12)
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
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	setStatut(STATUT_PROCESS_APPELANT);
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_EQUIP
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_EQUIP() {
	return "NOM_PB_OK_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public boolean performPB_OK_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (30/05/05 10:15:12)
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
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//Récup des zones saisies
	newNumBpc = getZone(getNOM_EF_BPC()); 
	newQte = getZone(getNOM_EF_QUANTITE());
	newDate ="";
	newHeure = getZone(getNOM_EF_HEURE());
	newValeurCompteur = getZone(getNOM_EF_COMPTEUR());
	if (Services.estUneDate(getZone(getNOM_EF_DATE()))){
		newDate = Services.formateDate(getZone(getNOM_EF_DATE()));
		//	On met la variable activité pour garder la dernière date enregistrée
		VariableActivite.ajouter(this, "DATE", newDate);
	}else if (!getZone(getNOM_EF_DATE()).equals("")){
		getTransaction().declarerErreur("La date n'est pas correcte.");
		setFocus(getZone(getNOM_EF_DATE()));
		return false;
	}
//	Integer.parseInt(getZone(getNOM_LB_POMPE_SELECT()));
	int pompe = (Services.estNumerique(getVAL_LB_POMPE_SELECT()) ? Integer.parseInt(getVAL_LB_POMPE_SELECT()): -1);
	Pompes unePompe = (Pompes)getListePompes().get(pompe);//String.valueOf(pompe);
	String newPompe = unePompe.getNum_pompe();
	//int modeprise  = Integer.parseInt(getZone(getNOM_LB_MODEPRISE_SELECT()))+1;
	//String newmodeprise = String.valueOf(modeprise);
	
	//	 Pour les champs obligatoires
//	Si lib numero Bpc non saisit
	if (newNumBpc.length() == 0) {
		getTransaction().declarerErreur("Le n° du BPC est obligatoire");
		setFocus(getZone(getNOM_EF_BPC()));
		return false;
	}
	
	//	Si lib date non saisit
	if (newDate.length() == 0) {
		getTransaction().declarerErreur("La date de prise de carburant est obligatoire");
		setFocus(getZone(getNOM_EF_DATE()));
		return false;
	}
	//	Si lib heure non saisit
	if (newHeure.length() == 0) {
		getTransaction().declarerErreur("L'heure est obligatoire");
		setFocus(getZone(getNOM_EF_HEURE()));
		return false;
	}
	//	Si lib quantité non saisit
	if (newQte.length() == 0) {
		getTransaction().declarerErreur("La quantité est obligatoire");
		setFocus(getZone(getNOM_EF_QUANTITE()));
		return false;
	}
//	Si lib compteur non saisit
	if (newValeurCompteur.length() == 0) {
		getTransaction().declarerErreur("La valeur du compteur est obligatoire");
		setFocus(getZone(getNOM_EF_COMPTEUR()));
		return false;
	}
	setBpcCourant(new BPC());
	
//		Affectation des attributs
	getBpcCourant().setNumerobpc(newNumBpc);
	getBpcCourant().setNumeroinventaire(getPMaterielCourant().getPminv());
	getBpcCourant().setDate(newDate);
	getBpcCourant().setHeure(newHeure);
	getBpcCourant().setValeurcompteur(newValeurCompteur);
	getBpcCourant().setQuantite(newQte);
	getBpcCourant().setNumeropompe(newPompe);
	getBpcCourant().setModedeprise(newmodeprise);
	
	// RG : on regarde si des entretiens ne doivent pas être plannifié
	//verifEntretiens(request);
	//Création
	if (!getBpcCourant().existeBPC(getTransaction(),bpcCourant.getNumerobpc())){
		if(getTransaction().isErreur()){
			getTransaction().traiterErreur();
		}
			getBpcCourant().creerBPC(getTransaction(),getPMaterielCourant(),getModeleCourant());
			if(getTransaction().isErreur()){
				return false;
			}
		//Tout s'est bien passé
		commitTransaction();
		//on vide les zones
		addZone(getNOM_EF_COMPTEUR(),"");
		//addZone(getNOM_EF_DATE(),"");
		addZone(getNOM_EF_HEURE(),"");
		addZone(getNOM_EF_QUANTITE(),"");
		addZone(getNOM_EF_RECHERCHE(),"");
		addZone(getNOM_LB_MODEPRISE_SELECT(),"0");
		addZone(getNOM_LB_POMPE_SELECT(),"0");
		addZone(getNOM_ST_TYPE(),"");
		addZone(getNOM_ST_SERVICE(),"");
		addZone(getNOM_ST_NOMEQUIP(),"");
		addZone(getNOM_ST_NOINVENT(),"");
		addZone(getNOM_ST_NOIMMAT(),"");
		addZone(getNOM_ST_COMPTEURAVANT(),"");
		//setFirst(true);
		setFocus(getNOM_EF_RECHERCHE());
		setPMatInfosCourant(null);
		//	on retourne à la liste des équipements
		//setStatut(STATUT_PROCESS_APPELANT);
	}else{
		getTransaction().declarerErreur("Le BPC est déjà enregistré.");
	}
	setPMatInfosCourant(new PMatInfos());
	setPMaterielCourant(new PMateriel());
	addZone(getNOM_ST_COMPTEUR(),"");
	//newNumBpc = "";
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOMEQUIP
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOMEQUIP() {
	return "NOM_ST_NOMEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOMEQUIP
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOMEQUIP() {
	return getZone(getNOM_ST_NOMEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_BPC
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_BPC() {
	return "NOM_EF_BPC";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_BPC
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_BPC() {
	return getZone(getNOM_EF_BPC());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_COMPTEUR
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_COMPTEUR() {
	return "NOM_EF_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_COMPTEUR
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_COMPTEUR() {
	return getZone(getNOM_EF_COMPTEUR());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DATE() {
	return "NOM_EF_DATE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DATE() {
	return getZone(getNOM_EF_DATE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_HEURE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_HEURE() {
	return "NOM_EF_HEURE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_HEURE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_HEURE() {
	return getZone(getNOM_EF_HEURE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_QUANTITE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_QUANTITE() {
	return "NOM_EF_QUANTITE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_QUANTITE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_QUANTITE() {
	return getZone(getNOM_EF_QUANTITE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_BPC
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
private String [] getLB_BPC() {
	if (LB_BPC == null)
		LB_BPC = initialiseLazyLB();
	return LB_BPC;
}
/**
 * Setter de la liste:
 * LB_BPC
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
private void setLB_BPC(java.lang.String[] newLB_BPC) {
	LB_BPC = newLB_BPC;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_BPC
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_BPC() {
	return "NOM_LB_BPC";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_BPC_SELECT
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_BPC_SELECT() {
	return "NOM_LB_BPC_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_BPC
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_BPC() {
	return getLB_BPC();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_BPC
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_BPC_SELECT() {
	return getZone(getNOM_LB_BPC_SELECT());
}



/**
 * Getter de la liste avec un lazy initialize :
 * LB_IMMAT
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
private String [] getLB_SERIE() {
	if (LB_SERIE == null)
		LB_SERIE = initialiseLazyLB();
	return LB_SERIE;
}
/**
 * Setter de la liste:
 * LB_IMMAT
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
private void setLB_SERIE(java.lang.String[] newLB_SERIE) {
	LB_SERIE = newLB_SERIE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_IMMAT
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_IMMAT() {
	return "NOM_LB_IMMAT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_IMMAT_SELECT
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_IMMAT_SELECT() {
	return "NOM_LB_IMMAT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_IMMAT
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_IMMAT() {
	return getLB_SERIE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_IMMAT
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_IMMAT_SELECT() {
	return getZone(getNOM_LB_IMMAT_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_INVENTAIRE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
private String [] getLB_INVENTAIRE() {
	if (LB_INVENTAIRE == null)
		LB_INVENTAIRE = initialiseLazyLB();
	return LB_INVENTAIRE;
}
/**
 * Setter de la liste:
 * LB_INVENTAIRE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
private void setLB_INVENTAIRE(java.lang.String[] newLB_INVENTAIRE) {
	LB_INVENTAIRE = newLB_INVENTAIRE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_INVENTAIRE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INVENTAIRE() {
	return "NOM_LB_INVENTAIRE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_INVENTAIRE_SELECT
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INVENTAIRE_SELECT() {
	return "NOM_LB_INVENTAIRE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_INVENTAIRE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_INVENTAIRE() {
	return getLB_INVENTAIRE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_INVENTAIRE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_INVENTAIRE_SELECT() {
	return getZone(getNOM_LB_INVENTAIRE_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_MODEPRISE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
private String [] getLB_MODEPRISE() {
	if (LB_MODEPRISE == null)
		LB_MODEPRISE = initialiseLazyLB();
	return LB_MODEPRISE;
}
/**
 * Setter de la liste:
 * LB_MODEPRISE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
private void setLB_MODEPRISE(java.lang.String[] newLB_MODEPRISE) {
	LB_MODEPRISE = newLB_MODEPRISE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MODEPRISE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_MODEPRISE() {
	return "NOM_LB_MODEPRISE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MODEPRISE_SELECT
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_MODEPRISE_SELECT() {
	return "NOM_LB_MODEPRISE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MODEPRISE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_MODEPRISE() {
	return getLB_MODEPRISE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MODEPRISE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_MODEPRISE_SELECT() {
	return getZone(getNOM_LB_MODEPRISE_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_POMPE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
private String [] getLB_POMPE() {
	if (LB_POMPE == null)
		LB_POMPE = initialiseLazyLB();
	return LB_POMPE;
}
/**
 * Setter de la liste:
 * LB_POMPE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
private void setLB_POMPE(java.lang.String[] newLB_POMPE) {
	LB_POMPE = newLB_POMPE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_POMPE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_POMPE() {
	return "NOM_LB_POMPE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_POMPE_SELECT
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_POMPE_SELECT() {
	return "NOM_LB_POMPE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_POMPE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_POMPE() {
	return getLB_POMPE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_POMPE
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_POMPE_SELECT() {
	return getZone(getNOM_LB_POMPE_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_IMMAT
 * Date de création : (30/05/05 15:19:09)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_IMMAT() {
	return "NOM_PB_OK_IMMAT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (30/05/05 15:19:09)
 * @author : Générateur de process
 */
public boolean performPB_OK_IMMAT(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_IMMAT_SELECT()) ? Integer.parseInt(getVAL_LB_IMMAT_SELECT()): -1);  
	
	addZone(getNOM_LB_INVENTAIRE_SELECT(),String.valueOf(indice));
	PMateriel monPMateriel = (PMateriel)getListePMateriel().get(indice);
	setPMaterielCourant(monPMateriel);
	
	addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
	addZone(getNOM_ST_NOMEQUIP(),getPMatInfosCourant().getDesignationmarque()+" "+getPMatInfosCourant().getDesignationmodele());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_INVENT
 * Date de création : (30/05/05 15:19:09)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_INVENT() {
	return "NOM_PB_OK_INVENT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (30/05/05 15:19:09)
 * @author : Générateur de process
 */
public boolean performPB_OK_INVENT(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_INVENTAIRE_SELECT()) ? Integer.parseInt(getVAL_LB_INVENTAIRE_SELECT()): -1); 
	
	addZone(getNOM_LB_IMMAT_SELECT(),String.valueOf(indice));
	PMateriel monPMateriel = (PMateriel)getListePMateriel().get(indice);
	setPMaterielCourant(monPMateriel);
	
	PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),getPMaterielCourant().getPminv());
	setPMatInfosCourant(unPMatInfos);
	
	addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
	addZone(getNOM_ST_NOMEQUIP(),getPMatInfosCourant().getDesignationmarque()+" "+getPMatInfosCourant().getDesignationmodele());
	
	return true;
}
	/**
	 * @return Renvoie bpcCourant.
	 */
	private BPC getBpcCourant() {
		return bpcCourant;
	}
	/**
	 * @param bpcCourant bpcCourant à définir.
	 */
	private void setBpcCourant(BPC bpcCourant) {
		this.bpcCourant = bpcCourant;
	}
	/**
	 * @return Renvoie equipementCourant.
	 */
	private PMateriel getPMaterielCourant() {
		return pMaterielCourant;
	}
	/**
	 * @param equipementCourant equipementCourant à définir.
	 */
	private void setPMaterielCourant(PMateriel pMaterielCourant) {
		this.pMaterielCourant = pMaterielCourant;
	}
	/**
	 * @return Renvoie listeModePrise.
	 */
	private ArrayList getListeModePrise() {
		return listeModePrise;
	}
	/**
	 * @param listeModePrise listeModePrise à définir.
	 */
	private void setListeModePrise(ArrayList listeModePrise) {
		this.listeModePrise = listeModePrise;
	}
	/**
	 * @return Renvoie listePompe.
	 */
	private ArrayList getListePompe() {
		return listePompe;
	}
	/**
	 * @param listePompe listePompe à définir.
	 */
	private void setListePompe(ArrayList listePompe) {
		this.listePompe = listePompe;
	}
	/**
	 * @return Renvoie listeBPC.
	 */
	private ArrayList getListeBPC() {
		return listeBPC;
	}
	/**
	 * @param listeBPC listeBPC à définir.
	 */
	private void setListeBPC(ArrayList listeBPC) {
		this.listeBPC = listeBPC;
	}
	/**
	 * @return Renvoie listeEquipement.
	 */
	private ArrayList getListePMateriel() {
		return listePMateriel;
	}
	/**
	 * @param listeEquipement listeEquipement à définir.
	 */
	private void setListePMateriel(ArrayList listePMateriel) {
		this.listePMateriel = listePMateriel;
	}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_MODEPRISE
 * Date de création : (01/06/05 08:08:30)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_MODEPRISE() {
	return "NOM_PB_OK_MODEPRISE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (01/06/05 08:08:30)
 * @author : Générateur de process
 */
public boolean performPB_OK_MODEPRISE(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_MODEPRISE_SELECT()) ? Integer.parseInt(getVAL_LB_MODEPRISE_SELECT()): -1); 
		
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	BPC monBpc = (BPC)getListeModePrise().get(indice);
	setBpcCourant(monBpc);

	codeModePrise= indice +1;
	
	return true;
}
	/**
	 * @return Renvoie equipementInfosCourant.
	 */
	private PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	/**
	 * @param equipementInfosCourant equipementInfosCourant à définir.
	 */
	private void setPMatInfosCourant(
			PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
	}
	/**
	 * @return Renvoie newNumeroBpc.
	 */
	private int getNewNumeroBpc() {
		return newNumeroBpc;
	}
	/**
	 * @param newNumeroBpc newNumeroBpc à définir.
	 */
	private void setNewNumeroBpc(int newNumeroBpc) {
		this.newNumeroBpc = newNumeroBpc;
	}
	/**
	 * @return Renvoie listeEquipementInfos.
	 */
	private ArrayList getListePMaterielInfos() {
		return listePMaterielInfos;
	}
	/**
	 * @param listeEquipementInfos listeEquipementInfos à définir.
	 */
	private void setListePMaterielInfos(ArrayList listePMaterielInfos) {
		this.listePMaterielInfos = listePMaterielInfos;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CARBU
 * Date de création : (03/06/05 12:44:24)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_CARBU() {
	return "NOM_ST_CARBU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CARBU
 * Date de création : (03/06/05 12:44:24)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_CARBU() {
	return getZone(getNOM_ST_CARBU());
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
	return getNOM_EF_RECHERCHE();
}
	/**
	 * @return Renvoie modeleCourant.
	 */
	public Modeles getModeleCourant() {
		return modeleCourant;
	}
	/**
	 * @param modeleCourant modeleCourant à définir.
	 */
	public void setModeleCourant(Modeles modeleCourant) {
		this.modeleCourant = modeleCourant;
	}
	/**
	 * @return Renvoie bpcAvant.
	 */
	public BPC getBpcAvant() {
		return bpcAvant;
	}
	/**
	 * @param bpcAvant bpcAvant à définir.
	 */
	public void setBpcAvant(BPC bpcAvant) {
		this.bpcAvant = bpcAvant;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (14/06/05 12:41:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (14/06/05 12:41:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (14/06/05 12:41:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (14/06/05 12:41:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
	public boolean isManqueParam() {
		return manqueParam;
	}
	public void setManqueParam(boolean manqueParam) {
		this.manqueParam = manqueParam;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (22/08/05 15:41:27)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECHERCHE() {
	return "NOM_PB_RECHERCHE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (22/08/05 15:41:27)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	recupereInfos(request);
	//recherche de l'équipement voulu
	String recherche = getZone(getNOM_EF_RECHERCHE()).toUpperCase();
	PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),recherche);
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("L'équipement recherché n'a pas été trouvé.");
		return false;
	}
	
	setPMatInfosCourant(unPMatInfos);
	PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
	if(getTransaction().isErreur()){
		return false;
	}
	setPMaterielCourant(unPMateriel);
	// on cherche le dernier BPC 
	ArrayList listBPC = BPC.chercherBPCEquip(getTransaction(),getPMaterielCourant().getPminv());
	//si premier BPC, aucun BPC ne sera trouvé
	if(getTransaction().isErreur()){
		getTransaction().traiterErreur();
	}else{
		if(listBPC.size()>0){
			int dernier = listBPC.size()-1;
			BPC dernierBPC = (BPC)listBPC.get(dernier);
			setBpcAvant(dernierBPC);
		}
	}
	
	setFocus(getNOM_EF_BPC());
	
	return true;

	
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE
 * Date de création : (22/08/05 15:41:27)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_RECHERCHE() {
	return "NOM_EF_RECHERCHE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE
 * Date de création : (22/08/05 15:41:27)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_RECHERCHE() {
	return getZone(getNOM_EF_RECHERCHE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:22:24)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:22:24)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEURAVANT
 * Date de création : (24/08/05 10:25:30)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COMPTEURAVANT() {
	return "NOM_ST_COMPTEURAVANT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEURAVANT
 * Date de création : (24/08/05 10:25:30)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COMPTEURAVANT() {
	return getZone(getNOM_ST_COMPTEURAVANT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECH_EQUIP
 * Date de création : (05/09/05 14:48:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECH_EQUIP() {
	return "NOM_PB_RECH_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (05/09/05 14:48:11)
 * @author : Générateur de process
 */
public boolean performPB_RECH_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_RECH_EQUIP,true);
	return true;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (30/05/05 10:15:12)
 * @author : Générateur de process
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_AFFICHE_COMPTEUR
//		if (testerParametre(request, getNOM_PB_AFFICHE_COMPTEUR())) {
//			return performPB_AFFICHE_COMPTEUR(request);
//		}

		//Si clic sur le bouton PB_RECH_EQUIP
		if (testerParametre(request, getNOM_PB_RECH_EQUIP())) {
			return performPB_RECH_EQUIP(request);
		}

		//Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
		}
		//Si clic sur le bouton PB_OK_MODEPRISE
		if (testerParametre(request, getNOM_PB_OK_MODEPRISE())) {
			return performPB_OK_MODEPRISE(request);
		}
		
	//Si clic sur le bouton PB_OK_IMMAT
		if (testerParametre(request, getNOM_PB_OK_IMMAT())) {
			return performPB_OK_IMMAT(request);
		}

		//Si clic sur le bouton PB_OK_INVENT
		if (testerParametre(request, getNOM_PB_OK_INVENT())) {
			return performPB_OK_INVENT(request);
		}
		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_OK_EQUIP
		if (testerParametre(request, getNOM_PB_OK_EQUIP())) {
			return performPB_OK_EQUIP(request);
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
 * Date de création : (25/10/05 08:44:00)
 * @author : Générateur de process
 */
public String getJSP() {
	return "OePMBPC_ajout.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AFFICHE_COMPTEUR
 * Date de création : (25/10/05 08:44:00)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_AFFICHE_COMPTEUR() {
	return "NOM_PB_AFFICHE_COMPTEUR";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/10/05 08:44:00)
 * @author : Générateur de process
 */
//public boolean performPB_AFFICHE_COMPTEUR(javax.servlet.http.HttpServletRequest request) throws Exception {
//	newNumBpc = getZone(getNOM_EF_BPC());
//	String nomFocus = getFocus();
//	if(!getZone(getNOM_EF_DATE()).equals("")){
//		if(!Services.estUneDate(getZone(getNOM_EF_DATE()))){
//			getTransaction().declarerErreur("La date n'est pas correcte.");
//			setFocus(getNOM_EF_DATE());
//			return false;
//		}
//		if(getEquipementInfosCourant()!=null){
//			if(getEquipementInfosCourant().getNumeroinventaire()==null){
//				getTransaction().declarerErreur("vous devez renseigner l'équipement.");
//				setFocus(getNOM_EF_RECHERCHE());
//				return false;
//			}
//		}else{
//			getTransaction().declarerErreur("vous devez renseigner l'équipement.");
//			setFocus(getNOM_EF_RECHERCHE());
//			return false;
//		}
//		
//		//selon la date on cherche le BPC précédent
//		setBpcAvant(BPC.chercherBPCPrecEquipDate(getTransaction(),getVAL_EF_DATE(),getEquipementInfosCourant().getNumeroinventaire()));
//		if(getTransaction().isErreur()){
//			//aucun BPC n'a été enregistré avant
//			getTransaction().traiterErreur();
//		}
//		addZone(getNOM_ST_COMPTEURAVANT(),getBpcAvant().getValeurcompteur());
//		setFocus(getNOM_EF_COMPTEUR());
//		//setFocus(nomFocus);
//	}
//	return true;
//}
public ArrayList getListePompes() {
	return listePompes;
}
public void setListePompes(ArrayList listePompes) {
	this.listePompes = listePompes;
}
public Pompes getPompeCourante() {
	return pompeCourante;
}
public void setPompeCourante(Pompes pompeCourante) {
	this.pompeCourante = pompeCourante;
}
	public boolean isAfficheMesage() {
		return afficheMesage;
	}
	public void setAfficheMesage(boolean afficheMesage) {
		this.afficheMesage = afficheMesage;
	}
}
