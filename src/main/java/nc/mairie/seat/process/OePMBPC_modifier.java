package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.mairie.seat.metier.BPC;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.Marques;
import nc.mairie.seat.metier.Carburant;
import nc.mairie.seat.metier.ModePrise;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.seat.metier.PePerso;
import nc.mairie.seat.metier.Pompes;
import nc.mairie.seat.metier.Service;
import nc.mairie.seat.metier.TYPEEQUIP;
import nc.mairie.technique.*;
/**
 * Process OeBPC_modifier
 * Date de création : (02/06/05 14:41:58)
* 
*/
public class OePMBPC_modifier extends nc.mairie.technique.BasicProcess {
	private java.lang.String[] LB_IMMAT;
	private java.lang.String[] LB_INVENTAIRE;
	private java.lang.String[] LB_MODEPRISE;
	private java.lang.String[] LB_POMPE;
	private ArrayList listeBPC;
	private ArrayList listeCarbu;
	private ArrayList listePompe;
	private ArrayList listeEquipement;
	private ArrayList listeEquipementInfos;
	private ArrayList listeModePrise;
	private Modeles modeleCourant;
	private BPC bpcCourant;
	private BPC bpcAvant;
	private PMateriel pMaterielCourant;
	private PMatInfos pMatInfosCourant;
	private int codeCarburant;
	private int codeModePrise;
	private Hashtable hashInventaire;
	private Hashtable hashImmat;
	private String focus = null;
	private boolean first = true;
	private String newNumeroBpc;
	private String newValeurCompteur;
	private String newDate;
	private String newQte;
	private String indiceMode="1";
	private String indicePompe="1";
	private String newHeure;
	private Pompes pompeCourante ;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (02/06/05 14:41:58)
* 
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(isFirst()){
		PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request, "PMATINFOS");
		setpMatInfosCourant(unPMatInfos);
		// on récupère le BPC à modifier
		BPC unBPC = (BPC)VariableGlobale.recuperer(request, "BPC");
		setBpcCourant(unBPC);
		// on renseigne les infos 
		newValeurCompteur = getBpcCourant().getValeurcompteur();
		newHeure = getBpcCourant().getHeure();
		newDate = getBpcCourant().getDate();
		newNumeroBpc = getBpcCourant().getNumerobpc();
		//indiceMode = String.valueOf(Integer.parseInt(getBpcCourant().getModedeprise())-1);
		indicePompe = String.valueOf(Integer.parseInt(getBpcCourant().getNumeropompe())-1);
		newQte = getBpcCourant().getQuantite();
		
		BPC monBPC = (BPC)VariableActivite.recuperer(this, "BPCAVANT");
		setBpcAvant(monBPC);
		// On récupère l'objet Equipement pour avoir les infos pour les modèles et marques
		if(null!=getPMatInfosCourant()){
			if(null!=getPMatInfosCourant().getPminv()){
				PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
				if(getTransaction().isErreur()){
					return;
				}
				setPMaterielCourant(unPMateriel);
			
			//	on récupère le modèle courant
				Modeles unModele = Modeles.chercherModeles(getTransaction(),getPMaterielCourant().getCodemodele());
				if(getTransaction().isErreur()){
					return;
				}
				setModeleCourant(unModele);
			}
			PM_Affectation_Sce_Infos unPMASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),getBpcCourant().getDate());
			if(unPMASI.getPminv()==null){
					addZone(getNOM_ST_SERVICE(),"pas affecté");
					getTransaction().traiterErreur();
				}else{
					Service unService = Service.chercherService(getTransaction(),unPMASI.getSiserv());
					if(getTransaction().isErreur()){
						return;
					}
					addZone(getNOM_ST_SERVICE(),unPMASI.getSiserv()+" "+unService.getLiserv());
				}
		}

	}
		
		
//		 on alimente la liste des pompes
		//String[] pompes = {"1","2"};
		//setLB_POMPE(pompes);
//	 recherche de la pompe concerné en fonction du carbu de l'équipement
	if (getPMatInfosCourant()!=null){
		/*Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
		if(getTransaction().isErreur()){
			return ;
		}
		setEquipementCourant(unEquipement);
		Modeles unModele = Modeles.chercherModeles(getTransaction(),getEquipementCourant().getCodemodele());
		if(getTransaction().isErreur()){
			return;
		}
		setModeleCourant(unModele);
		Carburant unCarbu = Carburant.chercherCarburant(getTransaction(),getModeleCourant().getCodecarburant());
		if(getTransaction().isErreur()){
			return;
		}*/
		Pompes unePompe = Pompes.chercherPompes(getTransaction(),getBpcCourant().getNumeropompe());
		if(getTransaction().isErreur()){
			return;
		}
		setPompeCourante(unePompe);
	}
	initialiseListePompes(request);	
	
		// on initialise les listes déroulantes
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
			
//		 Si liste des inventaires et immatriculation est vide
		if (getLB_INVENTAIRE() == LBVide) {
			java.util.ArrayList a = Equipement.listerEquipement(getTransaction());
			setListeEquipement(a);
			//les élèments de la liste 
			int [] tailles = {13};
			String [] champs = {"numeroinventaire"};
			String [] champs2 = {"numeroimmatriculation"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"D"};
			String [] padding2 = {"D"};
			
			FormateListe f = new FormateListe(tailles,a,champs,padding,false);
			String [] l = f.getListeFormatee();
			setLB_INVENTAIRE(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
			setLB_IMMAT(new FormateListe(tailles,a,champs2,padding2,false).getListeFormatee());
			
			//recherche de l'équipement courant
			int position = -1;
			addZone(getNOM_LB_INVENTAIRE_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeEquipement().size(); i++) {
				PMateriel eq = (PMateriel)getListeEquipement().get(i);
				if (eq.getPminv().equals(getPMatInfosCourant().getPminv())) {
					addZone(getNOM_LB_INVENTAIRE_SELECT(),String.valueOf(i));
					addZone(getNOM_LB_IMMAT_SELECT(),String.valueOf(i));
					break;
				}
			}
		}
//		sélectionner le bon inventaire
		for (int i = 0; i<getLB_INVENTAIRE().length;i++){
			if (getPMaterielCourant().getPminv().equals(LB_INVENTAIRE[i].trim())){
				addZone(getNOM_LB_INVENTAIRE_SELECT(),String.valueOf(i));
				addZone(getNOM_LB_IMMAT_SELECT(),String.valueOf(i));
			}
		}	
		
		// on initialise les infos concernant l'équipement
		addZone(getNOM_ST_NOIMMAT(),getPMatInfosCourant().getPmserie());
		addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
		Modeles monModele = Modeles.chercherModeles(getTransaction(),getPMatInfosCourant().getCodemodele());
		if(getTransaction().isErreur()){
			return ;
		}
		Marques maMarque = Marques.chercherMarques(getTransaction(),monModele.getCodemarque());
		if(getTransaction().isErreur()){
			return;
		}
		TYPEEQUIP monTe = TYPEEQUIP.chercherTYPEEQUIP(getTransaction(),monModele.getCodete());
		if(getTransaction().isErreur()){
			return ;
		}
		addZone(getNOM_ST_TYPE(),monTe.getDesignationte());
		addZone(getNOM_ST_NOMEQUIP(),maMarque.getDesignationmarque()+" "+monModele.getDesignationmodele());
		
		// infos concernant le BPC
		addZone(getNOM_EF_BPC(),newNumeroBpc);
		Carburant monCarbu = Carburant.chercherCarburant(getTransaction(),monModele.getCodecarburant());
		if(getTransaction().isErreur()){
			return;
		}
		addZone(getNOM_ST_CARBU(),monCarbu.getDesignationcarbu());
		addZone(getNOM_EF_COMPTEUR(),newValeurCompteur);
		addZone(getNOM_EF_DATE(),newDate);
		if (newHeure.length()<3){
			addZone(getNOM_EF_HEURE(),newHeure+"H");
		}else{
			addZone(getNOM_EF_HEURE(),newHeure);
		}
		addZone(getNOM_EF_QTE(),newQte);
		//addZone(getNOM_LB_POMPE_SELECT(),indicePompe);
		//addZone(getNOM_LB_MODEPRISE_SELECT(),indiceMode);

//		 on met le focus sur le libellé du modèle
		setFocus(getNOM_EF_BPC());
	setFirst(false);
}
/*
 * initialisation de la liste des pompes
 */
public boolean initialiseListePompes(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList a = Pompes.listerPompes(getTransaction());
	if (getTransaction().isErreur()){
		return false;
	}
	if (a.size()>0){
		//les élèments de la liste
		int [] tailles = {25};
		String [] champs = {"libelle_pompe"};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G"};
		boolean[] colonnes = {true};
		a = Services.trier(a,champs,colonnes);
		setListePompe(a);
		
		FormateListe f = new FormateListe(tailles,a,champs,padding,false);
		String [] l = f.getListeFormatee();
		setLB_POMPE(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
	}else{
		setLB_POMPE(null);
	}
//	 on sélectionne la pompe en cours
	if(getPompeCourante()!=null){
		if(getPompeCourante().getNum_pompe()!=null){
			int position = -1;
			addZone(getNOM_LB_POMPE_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListePompe().size(); i++) {
				Pompes unePompe = (Pompes)getListePompe().get(i);
				if (unePompe.getLibelle_pompe().equals(getPompeCourante().getLibelle_pompe())) {
					addZone(getNOM_LB_POMPE_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}
	return true;
}


public ArrayList epureListeEntretiensAFaire(javax.servlet.http.HttpServletRequest request,ArrayList listEntretiens) throws Exception {
	ArrayList listEntretiensEpure = new ArrayList();
	int aVidSimple = -1;
	int aVidComplete = -1;
	
	// on regarde si dans la liste des entretiens à faire il y a les 2 types de vidange (simple code 1 ou complète code 3)
	// si oui on ne fait que la vidange complète
	for (int indice=0;indice<listEntretiens.size();indice++){
		PePerso unPePerso = (PePerso)listEntretiens.get(indice);
		if(Integer.parseInt(unPePerso.getCodeentretien())==1){
			aVidSimple = indice;
		}
		if (Integer.parseInt(unPePerso.getCodeentretien())==3){
			aVidComplete = indice;
		}
	}
	
	// si les 2 types de vidange on enlève la vidange simple
	if((aVidSimple>-1)&&(aVidComplete>-1)){
		listEntretiens.remove(aVidSimple);
	}
	listEntretiensEpure = listEntretiens;
	return listEntretiensEpure;
}

/**
 * Constructeur du process OeBPC_modifier.
 * Date de création : (02/06/05 14:41:58)
* 
 */
public OePMBPC_modifier() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/06/05 14:41:58)
* 
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_IMMAT
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_PB_IMMAT() {
	return "NOM_PB_IMMAT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/06/05 14:41:58)
* 
 */
public boolean performPB_IMMAT(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_IMMAT_SELECT()) ? Integer.parseInt(getVAL_LB_IMMAT_SELECT()): -1);  
	
	addZone(getNOM_LB_INVENTAIRE_SELECT(),String.valueOf(indice));
	PMateriel monPMateriel = (PMateriel)getListeEquipement().get(indice);
	setPMaterielCourant(monPMateriel);
	
	addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
	addZone(getNOM_ST_NOMEQUIP(),getPMatInfosCourant().getDesignationmarque()+" "+getPMatInfosCourant().getDesignationmodele());

	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_INVENTAIRE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_PB_INVENTAIRE() {
	return "NOM_PB_INVENTAIRE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/06/05 14:41:58)
* 
 */
public boolean performPB_INVENTAIRE(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_INVENTAIRE_SELECT()) ? Integer.parseInt(getVAL_LB_INVENTAIRE_SELECT()): -1); 
	
	addZone(getNOM_LB_IMMAT_SELECT(),String.valueOf(indice));
	PMateriel monPMateriel = (PMateriel)getListeEquipement().get(indice);
	setPMaterielCourant(monPMateriel);
	
	PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),getPMaterielCourant().getPminv());
	setpMatInfosCourant(unPMatInfos);
	
	addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
	addZone(getNOM_ST_NOMEQUIP(),getPMatInfosCourant().getDesignationmarque()+" "+getPMatInfosCourant().getDesignationmodele());
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_MODEPRISE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_PB_OK_MODEPRISE() {
	return "NOM_PB_OK_MODEPRISE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/06/05 14:41:58)
* 
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
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/06/05 14:41:58)
* 
 */
public boolean performPB_OK_POMPE(javax.servlet.http.HttpServletRequest request) throws Exception {
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/06/05 14:41:58)
* 
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup des zones saisies
	newNumeroBpc = getZone(getNOM_EF_BPC()); 
	newQte = getZone(getNOM_EF_QTE());
	newDate = getZone(getNOM_EF_DATE());
	newHeure = getZone(getNOM_EF_HEURE());
	newValeurCompteur = getZone(getNOM_EF_COMPTEUR());
	if (Services.estUneDate(newDate)){
		//newDate = Services.formateDate(getZone(getNOM_EF_DATE()));
		//	On met la variable activité pour conserver la dernière date saisie
		VariableActivite.ajouter(this, "DATE", newDate);
	}else if (!getZone(getNOM_EF_DATE()).equals("")){
		getTransaction().declarerErreur("La date n'est pas correcte.");
		setFocus(getNOM_EF_DATE());
		return false;
	}
	//int pompe = Integer.parseInt(getZone(getNOM_LB_POMPE_SELECT()))+1;
	int pompe = (Services.estNumerique(getVAL_LB_POMPE_SELECT()) ? Integer.parseInt(getVAL_LB_POMPE_SELECT()): -1);
	//String newPompe = String.valueOf(pompe);
	Pompes unePompe = (Pompes)getListePompe().get(pompe);
	String newPompe = unePompe.getNum_pompe();
	
	//int modeprise  = Integer.parseInt(getZone(getNOM_LB_MODEPRISE_SELECT()))+1;
	//String newmodeprise = String.valueOf(modeprise);
	int indice = (Services.estNumerique(getVAL_LB_INVENTAIRE_SELECT()) ? Integer.parseInt(getVAL_LB_INVENTAIRE_SELECT()): -1); 
	
	PMateriel monPMateriel = (PMateriel)getListeEquipement().get(indice);
	setPMaterielCourant(monPMateriel);
	
	//	 Pour les champs obligatoires
	//	Si lib numero Bpc non saisit
	if (newNumeroBpc.length() == 0) {
		getTransaction().declarerErreur("Le n° du BPC est obligatoire");
		setFocus(getNOM_EF_BPC());
		return false;
	}
	
	//	Si lib date non saisit
	if (newDate.length() == 0) {
		getTransaction().declarerErreur("La date de prise de carburant est obligatoire");
		setFocus(getNOM_EF_DATE());
		return false;
	}
	//	Si lib heure non saisit
	if (newHeure.length() == 0) {
		getTransaction().declarerErreur("L'heure est obligatoire");
		setFocus(getNOM_EF_HEURE());
		return false;
	}
	//	Si lib quantité non saisit
	if (newQte.length() == 0) {
		getTransaction().declarerErreur("La quantité est obligatoire");
		setFocus(getNOM_EF_QTE());
		return false;
	}
//	Si lib compteur non saisit
	if (newValeurCompteur.length() == 0) {
		getTransaction().declarerErreur("La valeur du compteur est obligatoire");
		setFocus(getNOM_EF_COMPTEUR());
		return false;
	}
	//setBpcCourant(new BPC());
	
//		Affectation des attributs
	getBpcCourant().setNumerobpc(newNumeroBpc);
	getBpcCourant().setNumeroinventaire(getPMatInfosCourant().getPminv());
	getBpcCourant().setDate(newDate);
	getBpcCourant().setHeure(newHeure);
	getBpcCourant().setValeurcompteur(newValeurCompteur);
	getBpcCourant().setQuantite(newQte);
	getBpcCourant().setNumeropompe(newPompe);
	//getBpcCourant().setModedeprise(newMode);
	getBpcCourant().setModedeprise(indiceMode);

//	 on regarde si des entretiens ne doivent pas être plannifié
	//ReconductionEntretien(request);
	//verifEntretiens(request);
	
	//modification
//	PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
//	if(getTransaction().isErreur()){
//		return false;
//	}
	getBpcCourant().modifierBPC(getTransaction(),getPMaterielCourant(),getBpcAvant(),getModeleCourant());
	if(getTransaction().isErreur()){
		return false;
	}
	// tout s'est bien passé
	commitTransaction();
	
//	 on retourne à la liste des équipements
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}

/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CARBU
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_ST_CARBU() {
	return "NOM_ST_CARBU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CARBU
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getVAL_ST_CARBU() {
	return getZone(getNOM_ST_CARBU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOMEQUIP
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_ST_NOMEQUIP() {
	return "NOM_ST_NOMEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOMEQUIP
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getVAL_ST_NOMEQUIP() {
	return getZone(getNOM_ST_NOMEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_BPC
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_EF_BPC() {
	return "NOM_EF_BPC";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_BPC
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getVAL_EF_BPC() {
	return getZone(getNOM_EF_BPC());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_COMPTEUR
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_EF_COMPTEUR() {
	return "NOM_EF_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_COMPTEUR
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getVAL_EF_COMPTEUR() {
	return getZone(getNOM_EF_COMPTEUR());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_EF_DATE() {
	return "NOM_EF_DATE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getVAL_EF_DATE() {
	return getZone(getNOM_EF_DATE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_HEURE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_EF_HEURE() {
	return "NOM_EF_HEURE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_HEURE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getVAL_EF_HEURE() {
	return getZone(getNOM_EF_HEURE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_QTE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_EF_QTE() {
	return "NOM_EF_QTE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_QTE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getVAL_EF_QTE() {
	return getZone(getNOM_EF_QTE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_IMMAT
 * Date de création : (02/06/05 14:41:58)
* 
 */
private String [] getLB_IMMAT() {
	if (LB_IMMAT == null)
		LB_IMMAT = initialiseLazyLB();
	return LB_IMMAT;
}
/**
 * Setter de la liste:
 * LB_IMMAT
 * Date de création : (02/06/05 14:41:58)
* 
 */
private void setLB_IMMAT(java.lang.String[] newLB_IMMAT) {
	LB_IMMAT = newLB_IMMAT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_IMMAT
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_LB_IMMAT() {
	return "NOM_LB_IMMAT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_IMMAT_SELECT
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_LB_IMMAT_SELECT() {
	return "NOM_LB_IMMAT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_IMMAT
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String [] getVAL_LB_IMMAT() {
	return getLB_IMMAT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_IMMAT
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getVAL_LB_IMMAT_SELECT() {
	return getZone(getNOM_LB_IMMAT_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_INVENTAIRE
 * Date de création : (02/06/05 14:41:58)
* 
 */
private String [] getLB_INVENTAIRE() {
	if (LB_INVENTAIRE == null)
		LB_INVENTAIRE = initialiseLazyLB();
	return LB_INVENTAIRE;
}
/**
 * Setter de la liste:
 * LB_INVENTAIRE
 * Date de création : (02/06/05 14:41:58)
* 
 */
private void setLB_INVENTAIRE(java.lang.String[] newLB_INVENTAIRE) {
	LB_INVENTAIRE = newLB_INVENTAIRE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_INVENTAIRE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_LB_INVENTAIRE() {
	return "NOM_LB_INVENTAIRE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_INVENTAIRE_SELECT
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_LB_INVENTAIRE_SELECT() {
	return "NOM_LB_INVENTAIRE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_INVENTAIRE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String [] getVAL_LB_INVENTAIRE() {
	return getLB_INVENTAIRE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_INVENTAIRE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getVAL_LB_INVENTAIRE_SELECT() {
	return getZone(getNOM_LB_INVENTAIRE_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_MODEPRISE
 * Date de création : (02/06/05 14:41:58)
* 
 */
private String [] getLB_MODEPRISE() {
	if (LB_MODEPRISE == null)
		LB_MODEPRISE = initialiseLazyLB();
	return LB_MODEPRISE;
}
/**
 * Setter de la liste:
 * LB_MODEPRISE
 * Date de création : (02/06/05 14:41:58)
* 
 */
private void setLB_MODEPRISE(java.lang.String[] newLB_MODEPRISE) {
	LB_MODEPRISE = newLB_MODEPRISE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MODEPRISE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_LB_MODEPRISE() {
	return "NOM_LB_MODEPRISE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MODEPRISE_SELECT
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_LB_MODEPRISE_SELECT() {
	return "NOM_LB_MODEPRISE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MODEPRISE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String [] getVAL_LB_MODEPRISE() {
	return getLB_MODEPRISE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MODEPRISE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getVAL_LB_MODEPRISE_SELECT() {
	return getZone(getNOM_LB_MODEPRISE_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_POMPE
 * Date de création : (02/06/05 14:41:58)
* 
 */
private String [] getLB_POMPE() {
	if (LB_POMPE == null)
		LB_POMPE = initialiseLazyLB();
	return LB_POMPE;
}
/**
 * Setter de la liste:
 * LB_POMPE
 * Date de création : (02/06/05 14:41:58)
* 
 */
private void setLB_POMPE(java.lang.String[] newLB_POMPE) {
	LB_POMPE = newLB_POMPE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_POMPE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_LB_POMPE() {
	return "NOM_LB_POMPE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_POMPE_SELECT
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getNOM_LB_POMPE_SELECT() {
	return "NOM_LB_POMPE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_POMPE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String [] getVAL_LB_POMPE() {
	return getLB_POMPE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_POMPE
 * Date de création : (02/06/05 14:41:58)
* 
 */
public java.lang.String getVAL_LB_POMPE_SELECT() {
	return getZone(getNOM_LB_POMPE_SELECT());
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
	 * @return Renvoie codeCarburant.
	 */
	private int getCodeCarburant() {
		return codeCarburant;
	}
	/**
	 * @param codeCarburant codeCarburant à définir.
	 */
	private void setCodeCarburant(int codeCarburant) {
		this.codeCarburant = codeCarburant;
	}
	/**
	 * @return Renvoie codeModePrise.
	 */
	private int getCodeModePrise() {
		return codeModePrise;
	}
	/**
	 * @param codeModePrise codeModePrise à définir.
	 */
	private void setCodeModePrise(int codeModePrise) {
		this.codeModePrise = codeModePrise;
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
	 * @return Renvoie equipementInfosCourant.
	 */
	private PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	/**
	 * @param equipementInfosCourant equipementInfosCourant à définir.
	 */
	private void setpMatInfosCourant(
			PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
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
	 * @return Renvoie listeCarbu.
	 */
	private ArrayList getListeCarbu() {
		return listeCarbu;
	}
	/**
	 * @param listeCarbu listeCarbu à définir.
	 */
	private void setListeCarbu(ArrayList listeCarbu) {
		this.listeCarbu = listeCarbu;
	}
	/**
	 * @return Renvoie listeEquipement.
	 */
	private ArrayList getListeEquipement() {
		return listeEquipement;
	}
	/**
	 * @param listeEquipement listeEquipement à définir.
	 */
	private void setListeEquipement(ArrayList listeEquipement) {
		this.listeEquipement = listeEquipement;
	}
	/**
	 * @return Renvoie listeEquipementInfos.
	 */
	private ArrayList getListeEquipementInfos() {
		return listeEquipementInfos;
	}
	/**
	 * @param listeEquipementInfos listeEquipementInfos à définir.
	 */
	private void setListeEquipementInfos(ArrayList listeEquipementInfos) {
		this.listeEquipementInfos = listeEquipementInfos;
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
	 * @return focus focus à définir.
	 */
	public String getDefaultFocus() {
		return getNOM_EF_BPC();
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
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (14/06/05 12:32:28)
* 
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (14/06/05 12:32:28)
* 
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (14/06/05 12:32:28)
* 
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (14/06/05 12:32:28)
* 
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (02/06/05 14:41:58)
* 
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_IMMAT
		if (testerParametre(request, getNOM_PB_IMMAT())) {
			return performPB_IMMAT(request);
		}

		//Si clic sur le bouton PB_INVENTAIRE
		if (testerParametre(request, getNOM_PB_INVENTAIRE())) {
			return performPB_INVENTAIRE(request);
		}

		//Si clic sur le bouton PB_OK_MODEPRISE
		if (testerParametre(request, getNOM_PB_OK_MODEPRISE())) {
			return performPB_OK_MODEPRISE(request);
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
 * Date de création : (23/08/05 08:22:46)
* 
 */
@Override
public String getJSP() {
	return "OePMBPC_modifier.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:22:46)
* 
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:22:46)
* 
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
	public Pompes getPompeCourante() {
		return pompeCourante;
	}
	public void setPompeCourante(Pompes pompeCourante) {
		this.pompeCourante = pompeCourante;
	}
}
