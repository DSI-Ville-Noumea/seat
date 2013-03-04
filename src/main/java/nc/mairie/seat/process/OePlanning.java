package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import nc.mairie.seat.metier.Entretien;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.OT;
import nc.mairie.seat.metier.PePerso;
import nc.mairie.seat.metier.Planning;
import nc.mairie.seat.metier.TIntervalle;
import nc.mairie.seat.metier.TYPEEQUIP;
import nc.mairie.seat.metier.TypeEntretien;
import nc.mairie.technique.*;
/**
 * Process OePlanning
 * Date de création : (18/07/05 09:25:07)
* 
*/
public class OePlanning extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_VALIDATION = 2;
	public static final int STATUT_PEPERSO = 1;
	private Planning planningCourant;
	private String tri = "numeroimmatriculation";
	private String param ="";
	private ArrayList listePlanning;
	private ArrayList listeAFaire;
	private ArrayList listInventaire;
	private ArrayList listPourOt;
	private java.lang.String[] LB_PLANNING;
	private java.lang.String[] LB_PLANNING_Couleurs;
	private java.lang.String[] LB_PLANNING_FCouleurs;
	private java.lang.String[] LB_ENTRETIENS_A_FAIRE;
	private Hashtable<String, TypeEntretien> hashTypeEntretien;
	public 	boolean isVide = true;
	public 	boolean isAfaire = false;
	private String msg = "";
	private String msg2 = "";
	public String firstNoOt = "";
	public String lastNoOt = "";
	private String dateFinPlanning = "";
	private boolean first = true;
	private boolean aDoubleCliquer = false;
	private boolean trouve = false;
	private EquipementInfos equipementInfosCourant;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (18/07/05 09:25:07)
* 
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	//LB optimisation 07/09
	if (getHashTypeEntretien().size() == 0) {
		initialiseListeTypeEntretien();
	}
	
	
	if (isFirst()){
		// à rétablir une fois tous les entretiens et équipements saisis
		//verifVisiteAnnuelle(getTransaction());
	}
	EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
	if(unEquipementInfos!=null){
		setEquipementInfosCourant(unEquipementInfos);
	}
	// si la date de fin du planning n'a pas été renseignée : on affiche le planning sur une semaine calendaire
	dateFinPlanning = getZone(getNOM_EF_DATEFINPLANNING());
	if(dateFinPlanning.equals("")){
		dateFinPlanning = Services.ajouteJours(Services.dateDuJour(),7);
	}
	addZone(getNOM_EF_DATEFINPLANNING(),dateFinPlanning);

	
	ArrayList a = new ArrayList();
	// Si param = encours on liste les équipements actifs
	if ("encours".equals(param)){
		ArrayList listEnCours = Planning.listerPlanningEnCoursAvecOTValideDifferentT(getTransaction(),firstNoOt);
		a=listEnCours;
		//optimisation luc 9/9/11
/*		if(getTransaction().isErreur()){
			return;
		}
		a = new ArrayList();
		if(listEnCours.size()>0){
			for(int i=0;i<listEnCours.size();i++){
				//on enlève tous les plannings dont l'OT est validé
				Planning unPlanning = (Planning)listEnCours.get(i);
				OT unOT = OT.chercherOT(getTransaction(),unPlanning.getCodeot());
				if(getTransaction().isErreur()){
					return ;
				}
				if(!unOT.getValide().trim().equals("T")){
					a.add(unPlanning);
				}
			}
		}
	*/
	} else 	// si param = "enretard" : on liste les entretiens en retard
		if ("enretard".equals(param)){
			ArrayList listEnRetard = Planning.listerPlanningEnRetard(getTransaction(),Services.dateDuJour());
			if(getTransaction().isErreur()){
				return;
			}
			a = listEnRetard;
		} else {// on récupère tous les entretiens à faire	
			a = Planning.listerPlanningAFaire(getTransaction(),dateFinPlanning);
		}
	 
	
	//si la liste des entretiens à faire n'est pas vide : on doit épurée la liste du planning
	if (!param.equals("encours")){
		if ((getListeAFaire().size()>0) &&(a.size()>0)){
			for (int i = 0; i<getListeAFaire().size();i++){
				trouve = false;
				Planning monPlanningAFaire = (Planning)getListeAFaire().get(i);
				String mPAFaire = monPlanningAFaire.getCodeentretien();
				String mPAFaireInv = monPlanningAFaire.getNumeroinventaire();
				for (int j=0;j<a.size();j++){
					if (!trouve){
						Planning monPlanning = (Planning)a.get(j);
						String mP = monPlanning.getCodeentretien();
						String mPInv = monPlanning.getNumeroinventaire();
						//if ((monPlanning.codeentretien == monPlanningAFaire.codeentretien)&&(monPlanning.numeroinventaire==monPlanningAFaire.numeroinventaire)){
						if ((mPAFaire.equals(mP))&&(mPAFaireInv.equals(mPInv))){
							a.remove(j);
							trouve = true;
						}
					}
				}
			}
		}else{
			setAfaire(false);
		}
	}
		
	trier(a, tri);
	cocher(param,tri);
	// affiche les messages d'erreurs
	if (!msg.equals("")||(!msg2.equals(""))){
		getTransaction().declarerErreur(msg2+" "+msg);
	}
	msg2= "";
	setFirst(false);
	if(getTransaction().isErreur()){
		return ;
	}
	String isRetour = (String)VariableActivite.recuperer(this,"RETOUR");
	if(isRetour!=null&&isRetour.equals("TRUE")){
		setLB_ENTRETIENS_A_FAIRE(LBVide);
	}
}

/**
 * Init de la liste des équipements 
 * 
 */
public void initialiseListeTypeEntretien() throws Exception{
	ArrayList arr = TypeEntretien.listerTypeEntretien(getTransaction());
	
	for (Iterator iter = arr.iterator(); iter.hasNext();) {
		TypeEntretien typeEntretien = (TypeEntretien) iter.next();
		getHashTypeEntretien().put(typeEntretien.getCodetypeent(), typeEntretien);
	}
}

public boolean initialiseListeAFaire(javax.servlet.http.HttpServletRequest request) throws Exception{
	String date = "";
	if(getListeAFaire().size()>0){
		//les élèments de la liste 
		int [] tailles = {10,30,10,20};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G","G","C","G"};
		
		FormateListe aFormat = new FormateListe(tailles,padding,true);
		for (int i = 0; i < getListeAFaire().size() ; i++) {
			Planning aPlanning = (Planning)getListeAFaire().get(i);	
			
			// on affiche la date que si elle est différente de 01/01/0001
			date = aPlanning.getDateprev();
			if (aPlanning.getDateprev().equals("01/01/0001")){
				date = "";
			}
			/*LB
			// on affiche le type d'entretien
			TypeEntretien unTE = TypeEntretien.chercherTypeEntretien(getTransaction(),aPlanning.getCodetypeent());
			if (getTransaction().isErreur()){
				return false;
			}
			*/
			TypeEntretien unTE = getHashTypeEntretien().get(aPlanning.getCodetypeent());
			if (unTE == null) {
				getTransaction().declarerErreur("Erreur : le type entretien "+aPlanning.getCodetypeent()+" est introuvable");
				return false;
			}
			
			String ligne [] = { aPlanning.getNumeroimmatriculation(),aPlanning.getLibelleentretien(),date,unTE.getDesignationtypeent()};
			aFormat.ajouteLigne(ligne);
		}
		setAfaire(true);
		setLB_ENTRETIENS_A_FAIRE(aFormat.getListeFormatee());
	}else{
		setLB_ENTRETIENS_A_FAIRE(LBVide);
	}
	if(getTransaction().isErreur()){
		return false;
	}
	return true;
}
/**
 * Constructeur du process OePlanning.
 * Date de création : (18/07/05 09:25:07)
* 
 */
public OePlanning() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (18/07/05 09:25:07)
* 
 */
public java.lang.String getNOM_PB_OK() {
	return "NOM_PB_OK";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (18/07/05 09:25:07)
* 
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_PLANNING_SELECT()) ? Integer.parseInt(getVAL_LB_PLANNING_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	Planning monPlanning = (Planning)getListePlanning().get(indice);
	EquipementInfos monEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),monPlanning.getNumeroinventaire());
	if (getTransaction().isErreur()){
		getTransaction().declarerErreur(getTransaction().traiterErreur());
		return false;
	}
	setEquipementInfosCourant(monEquipementInfos);
	if (monEquipementInfos==null){
		monEquipementInfos = new EquipementInfos();
	}
//	On met la variable globale
	VariableGlobale.ajouter(request, "EQUIPEMENTINFOS", monEquipementInfos);
	// on débranche sur le plan d'entretien personnalisé de l'équipement sélectionné
	setStatut(STATUT_PEPERSO,true);
	if(getTransaction().isErreur()){
		return false;
	}
	return true;
	
}

public void trier(ArrayList a,String colonne) throws Exception{
	String[] colonnes = {colonne};
	//ordre croissant
	boolean[] ordres = {true};
	if (colonne.equals("dateprev"))
		ordres=new boolean[] {false};
	String encours = "";
	String date = "";
	String datedujour = Services.dateDuJour();
	
	a= Services.trier(a,colonnes,ordres);
	setListePlanning(a);
	if(a.size()>0){
		//les élèments de la liste 
		int [] tailles = {10,30,10,10,13};
		String [] padding = {"G","G","C","G","G"};
		setLB_PLANNING_Couleurs(new String[a.size()]);
		setLB_PLANNING_FCouleurs(new String[a.size()]);
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		
		for (int i = 0; i < a.size() ; i++) {
			Planning aPlanning = (Planning)a.get(i);
			if(getTransaction().isErreur()){
				return;
			}
			
			 //controle si en cours de réalisation ou pas
			if((aPlanning.getCodeot()!=null)&&(!aPlanning.getCodeot().equals(""))){
				//je cherche si l'Ot correspondant est validé ou pas
				OT unOT = OT.chercherOT(getTransaction(),aPlanning.getCodeot());
				if(getTransaction().isErreur()){
					return;
				}
				// si l'OT n'est pas validé
				if(unOT.getValide().trim().equals("F")){
					encours = "EN COURS";
					// on met soit le nom de la couleur soit le code
					getLB_PLANNING_Couleurs()[i] = "blue";
				}else{
					encours="NON EFFECTUE";
				}
				if(getTransaction().isErreur()){
					return;
				}
			}else{
				if(getTransaction().isErreur()){
					return;
				}
				encours = "";
				//	pour les entretiens ayant pris du retard on les signale en rouge
				if (!aPlanning.getDateprev().equals("01/01/0001")){
					int enRetard = Services.compareDates(aPlanning.getDateprev(),datedujour);
					if (enRetard==-9999){
						return;
					}else if (enRetard==-1){
						if (aPlanning.getCodeot()==null){
							getLB_PLANNING_Couleurs()[i] = "red";
							encours = "EN RETARD";
						}
					}
				}
				if(getTransaction().isErreur()){
					return;
				}
			}
			if(getTransaction().isErreur()){
				return;
			}
			if(getEquipementInfosCourant()!=null){
				if(getEquipementInfosCourant().getNumeroinventaire()!=null){
//					 si l'entretien appartient à l'équipement en cours on le surligne
					if (aPlanning.numeroinventaire.equals(getEquipementInfosCourant().getNumeroinventaire())){
						getLB_PLANNING_Couleurs()[i] = "white";
						getLB_PLANNING_FCouleurs()[i] = "teal";
					}
				}
			}
			//on affiche la date que si elle est différente de 01/01/0001
			date = aPlanning.getDateprev();
			if (aPlanning.getDateprev().equals("01/01/0001")){
				date = "";
			}
			/*LB
			// on affiche le type d'entretien
			TypeEntretien unTE = TypeEntretien.chercherTypeEntretien(getTransaction(),aPlanning.getCodetypeent());
			if (getTransaction().isErreur()){
				return;
			}
			*/
			TypeEntretien unTE = getHashTypeEntretien().get(aPlanning.getCodetypeent());
			if (unTE == null) {
				getTransaction().declarerErreur("Erreur : le type entretien "+aPlanning.getCodetypeent()+" est introuvable");
				return ;
			}
			
			
			
			
			//String ligne [] = { aPlanning.getNumeroimmatriculation(),aPlanning.getLibelleentretien(),date,unTE.getDesignationtypeent(),aPlanning.getCodeot()};//,encours};
			String ligne [] = { aPlanning.getNumeroimmatriculation(),aPlanning.getLibelleentretien(),date,aPlanning.getCodeot(),encours};
			aFormat.ajouteLigne(ligne);
			setVide(false);
			setLB_PLANNING(aFormat.getListeFormatee());
		}
	}else{
		setLB_PLANNING(LBVide);
	}
	if(getTransaction().isErreur()){
		return ;
	}
	//int te = getLB_PLANNING().length;
	return ;
}

public void cocher(String param, String tri){
	// Selon le param on coche le bon affichage
	addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_TOUS());
	if ("encours".equals(param)){
		addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_ENCOURS());
	}
	if("enretard".equals(param)){
		addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_EN_RETARD());
	}
	
	//Selon le tri coche la bonne colonne
	addZone(getNOM_RG_TRI(),getNOM_RB_TRI_ENTRETIENS());
	
	if (tri.equals("numeroimmatriculation")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_IMMAT());
	}else if (tri.equals("libelleentretien")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_ENTRETIENS());
	}else if (tri.equals("dateprev")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_DATEPREVU());
	}
}

/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_TRI
 * Date de création : (18/07/05 09:25:07)
* 
 */
public java.lang.String getNOM_RG_TRI() {
	return "NOM_RG_TRI";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_TRI
 * Date de création : (18/07/05 09:25:07)
* 
 */
public java.lang.String getVAL_RG_TRI() {
	return getZone(getNOM_RG_TRI());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_ENCOURS
 * Date de création : (18/07/05 09:25:07)
* 
 */
public java.lang.String getNOM_RB_AFFICHAGE_ENCOURS() {
	return "NOM_RB_AFFICHAGE_ENCOURS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_TOUS
 * Date de création : (18/07/05 09:25:07)
* 
 */
public java.lang.String getNOM_RB_AFFICHAGE_TOUS() {
	return "NOM_RB_AFFICHAGE_TOUS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_DATEPREVU
 * Date de création : (18/07/05 09:25:07)
* 
 */
public java.lang.String getNOM_RB_TRI_DATEPREVU() {
	return "NOM_RB_TRI_DATEPREVU";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_ENTRETIENS
 * Date de création : (18/07/05 09:25:07)
* 
 */
public java.lang.String getNOM_RB_TRI_ENTRETIENS() {
	return "NOM_RB_TRI_ENTRETIENS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_IMMAT
 * Date de création : (18/07/05 09:25:07)
* 
 */
public java.lang.String getNOM_RB_TRI_IMMAT() {
	return "NOM_RB_TRI_IMMAT";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_TRI
 * Date de création : (18/07/05 09:27:41)
* 
 */
public java.lang.String getNOM_PB_OK_TRI() {
	return "NOM_PB_OK_TRI";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (18/07/05 09:27:41)
* 
 */
public boolean performPB_OK_TRI(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 selon la sélection la liste sera triée
	if (getNOM_RB_TRI_IMMAT().equals(getZone(getNOM_RG_TRI()))){
		tri = "numeroimmatriculation";
	}
	if (getNOM_RB_TRI_DATEPREVU().equals(getZone(getNOM_RG_TRI()))){
		tri = "dateprev";
	}
	if (getNOM_RB_TRI_ENTRETIENS().equals(getZone(getNOM_RG_TRI()))){
		tri = "libelleentretien";
	}
	
	// selon les entretiens voulus (tous / encours)
	if (getNOM_RB_AFFICHAGE_ENCOURS().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "encours";
	}
	if (getNOM_RB_AFFICHAGE_TOUS().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "Tous";
	}
	if (getNOM_RB_AFFICHAGE_EN_RETARD().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "enretard";
	}

	return true;
}
	
/**
 * Getter de la liste avec un lazy initialize :
 * LB_PLANNING
 * Date de création : (18/07/05 09:30:00)
* 
 */
private String [] getLB_PLANNING() {
	if (LB_PLANNING == null)
		LB_PLANNING = initialiseLazyLB();
	return LB_PLANNING;
}
/**
 * Setter de la liste:
 * LB_PLANNING
 * Date de création : (18/07/05 09:30:00)
* 
 */
private void setLB_PLANNING(java.lang.String[] newLB_PLANNING) {
	LB_PLANNING = newLB_PLANNING;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_PLANNING
 * Date de création : (18/07/05 09:30:00)
* 
 */
public java.lang.String getNOM_LB_PLANNING() {
	return "NOM_LB_PLANNING";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PLANNING_SELECT
 * Date de création : (18/07/05 09:30:00)
* 
 */
public java.lang.String getNOM_LB_PLANNING_SELECT() {
	return "NOM_LB_PLANNING_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_PLANNING
 * Date de création : (18/07/05 09:30:00)
* 
 */
public java.lang.String [] getVAL_LB_PLANNING() {
	return getLB_PLANNING();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_PLANNING
 * Date de création : (18/07/05 09:30:00)
* 
 */
public java.lang.String getVAL_LB_PLANNING_SELECT() {
	return getZone(getNOM_LB_PLANNING_SELECT());
}
public Planning getPlanningCourant() {
	return planningCourant;
}
public void setPlanningCourant(Planning planningCourant) {
	this.planningCourant = planningCourant;
}
public ArrayList getListePlanning() {
	
	return listePlanning;
}
public void setListePlanning(ArrayList listePlanning) {
	this.listePlanning = listePlanning;
}
public String getParam() {
	return param;
}
public void setParam(String param) {
	this.param = param;
}
public String getTri() {
	return tri;
}
public void setTri(String tri) {
	this.tri = tri;
}
	public java.lang.String[] getLB_PLANNING_Couleurs() {
		return LB_PLANNING_Couleurs;
	}
	public void setLB_PLANNING_Couleurs(java.lang.String[] couleurs) {
		LB_PLANNING_Couleurs = couleurs;
	}
	public java.lang.String[] getLB_PLANNING_FCouleurs() {
		return LB_PLANNING_FCouleurs;
	}
	public void setLB_PLANNING_FCouleurs(java.lang.String[] fcouleurs) {
		LB_PLANNING_FCouleurs = fcouleurs;
	}	
/**
 * Getter de la liste avec un lazy initialize :
 * LB_ENTRETIENS_A_FAIRE
 * Date de création : (18/07/05 16:08:58)
* 
 */
private String [] getLB_ENTRETIENS_A_FAIRE() {
	if (LB_ENTRETIENS_A_FAIRE == null)
		LB_ENTRETIENS_A_FAIRE = initialiseLazyLB();
	return LB_ENTRETIENS_A_FAIRE;
}
/**
 * Setter de la liste:
 * LB_ENTRETIENS_A_FAIRE
 * Date de création : (18/07/05 16:08:58)
* 
 */
private void setLB_ENTRETIENS_A_FAIRE(java.lang.String[] newLB_ENTRETIENS_A_FAIRE) {
	LB_ENTRETIENS_A_FAIRE = newLB_ENTRETIENS_A_FAIRE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_ENTRETIENS_A_FAIRE
 * Date de création : (18/07/05 16:08:58)
* 
 */
public java.lang.String getNOM_LB_ENTRETIENS_A_FAIRE() {
	return "NOM_LB_ENTRETIENS_A_FAIRE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIENS_A_FAIRE_SELECT
 * Date de création : (18/07/05 16:08:58)
* 
 */
public java.lang.String getNOM_LB_ENTRETIENS_A_FAIRE_SELECT() {
	return "NOM_LB_ENTRETIENS_A_FAIRE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_ENTRETIENS_A_FAIRE
 * Date de création : (18/07/05 16:08:58)
* 
 */
public java.lang.String [] getVAL_LB_ENTRETIENS_A_FAIRE() {
	return getLB_ENTRETIENS_A_FAIRE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_ENTRETIENS_A_FAIRE
 * Date de création : (18/07/05 16:08:58)
* 
 */
public java.lang.String getVAL_LB_ENTRETIENS_A_FAIRE_SELECT() {
	return getZone(getNOM_LB_ENTRETIENS_A_FAIRE_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (18/07/05 16:09:45)
* 
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (18/07/05 16:09:45)
* 
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice = (Services.estNumerique(getVAL_LB_PLANNING_SELECT()) ? Integer.parseInt(getVAL_LB_PLANNING_SELECT()): -1);
	// si un équipement en cours
	if((getEquipementInfosCourant()!=null)&&(indice==-1)){
		if(!("").equals(getEquipementInfosCourant().getNumeroinventaire())){
			for (int i=0;i<getListePlanning().size();i++){
				Planning unPlanning = (Planning)getListePlanning().get(i);
				if (unPlanning.getNumeroinventaire().equals(getEquipementInfosCourant().getNumeroinventaire())){
					if(!ajouteEntretiens(request,i)){
						return false;
					}
					i=0;
				}
			}
		}
		return true;
	}else{
		// on ajoute l'entretien à faire dans la liste 2 et on l'enlève du planning
		//int indice = (Services.estNumerique(getVAL_LB_PLANNING_SELECT()) ? Integer.parseInt(getVAL_LB_PLANNING_SELECT()): -1); 
		if (indice == -1) {
			getTransaction().declarerErreur("Vous devez sélectionner un élement à ajouter dans la liste des entretiens ");
			return false;
		}
		//Planning unPlanning = (Planning)getListePlanning().get(indice);
		return ajouteEntretiens(request,indice);
		
	}
	
}

// méthode : ajout l'entretien dont l'indice est passé en paramètre dans la liste des entretiens à faire
public boolean ajouteEntretiens(javax.servlet.http.HttpServletRequest request,int indice) throws Exception {
	Planning monPlanning = (Planning)getListePlanning().get(indice);
	//on teste si l'entretien est en cours de réalisation ou pas : si codeot renseigné et valide=F
	if((monPlanning.getCodeot()!=null)&&(!monPlanning.getCodeot().equals(""))){
		//je cherche si l'Ot correspondant est validé ou pas
		OT unOT = OT.chercherOT(getTransaction(),monPlanning.getCodeot());
		if(getTransaction().isErreur()){
			return false;
		}
		// si l'OT n'est pas validé
		if(unOT.getValide().trim().equals("F")){
			msg2 =" Cet entretien est déjà en cours de réalisation.";
		}else{
			if((monPlanning.getDatereal().equals(""))||("01/01/0001".equals(monPlanning.getDatereal()))){
	//			 on ajoute à la liste à faire
				getListeAFaire().add(monPlanning);
				//LUC
				getListePlanning().remove(monPlanning);
				initialiseListeAFaire(request);
			}
		}
	}else{
		// on ajoute à la liste à faire
		getListeAFaire().add(monPlanning);
		
		//LUC
		getListePlanning().remove(monPlanning);
		initialiseListeAFaire(request);
	}
	if(getTransaction().isErreur()){
		return false;
	}
	setADoubleCliquer(true);
	return true;
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ENLEVER
 * Date de création : (18/07/05 16:09:45)
* 
 */
public java.lang.String getNOM_PB_ENLEVER() {
	return "NOM_PB_ENLEVER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (18/07/05 16:09:45)
* 
 */
public boolean performPB_ENLEVER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on ajoute l'entretien à faire dans le planning et on l'enlève de la liste 2
	int indice = (Services.estNumerique(getVAL_LB_ENTRETIENS_A_FAIRE_SELECT()) ? Integer.parseInt(getVAL_LB_ENTRETIENS_A_FAIRE_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement à enlever de la liste des entretiens ");
		return false;
	}
	Planning monPlanning = (Planning)getListeAFaire().get(indice);
	// on ajoute dans le planning
	getListePlanning().add(monPlanning);
	// on ajoute à la liste à faire
	getListeAFaire().remove(indice);
	// on rafraichit la liste
	initialiseListeAFaire(request);
	return true;
}
	public ArrayList getListeAFaire() {
		//on initialise
		if (listeAFaire==null){
			listeAFaire = new ArrayList();
		}
		return listeAFaire;
	}
	public void setListeAFaire(ArrayList listeAFaire) {
		this.listeAFaire = listeAFaire;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (19/07/05 10:12:15)
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
 * Date de création : (19/07/05 10:12:15)
* 
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//on créer les OT pour les équipements dont les entretiens ont été sélectionné
	/*if(getListeAFaire().size()>0){
		for (int i=0;i<getListeAFaire().size();i++){
			
		}
	}*/
	Hashtable hRegroupEnt = new Hashtable();
	for (int i = 0; i < getListeAFaire().size(); i++){
		Planning unPlanning = (Planning)getListeAFaire().get(i);
		// on regroupe les entretiens par équipements dans une hashtable
		//on liste d'abord les entretiens d'un équipement
		ArrayList listeEnt = (ArrayList)hRegroupEnt.get(unPlanning.getNumeroinventaire());
		//ArrayList listPePerso = unPlanning;
		if (listeEnt == null) 
			listeEnt = new ArrayList();
		listeEnt.add(unPlanning);
		// on rajoute dans la hashtable à l'indice numéroinventaire
		hRegroupEnt.put(unPlanning.getNumeroinventaire(),listeEnt);
	}

	//Hashtable hOt = new Hashtable();
	Enumeration enumer = hRegroupEnt.keys();
	//pour chaque équipement on crée un OT avec le max trouvé dans la table
	while (enumer.hasMoreElements()) {
		String noinvent = String.valueOf(enumer.nextElement());
		OT unOT = new OT();
		Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),noinvent);
		if(getTransaction().isErreur()){
			return false;
		}
		unOT.creerOT(getTransaction(),unEquipement);
		if (getTransaction().isErreur()){
			return false;
		}
		//on récupère la liste des entretiens à faire pour l'équipement
		ArrayList array = (ArrayList)hRegroupEnt.get(noinvent);
		for (int i = 0; i < array.size(); i++){
			Planning unPlanning = (Planning)array.get(i);
			// on modifie le code OT des peperso
			PePerso unPePerso = PePerso.chercherPePerso(getTransaction(),unPlanning.getCodepep());
			if(getTransaction().isErreur()){
				return false;
			}
			//modification du numéroOt
			unPePerso.setCodeot(unOT.getNumeroot());
			unPePerso.modifierPePersoInfos(getTransaction());
			if(getTransaction().isErreur()){
				return false;
			}
			//on modifie le code ot des entretiens à faire
			//listeAValider.add(unPePerso);
			
		}	
		// pour afficher uniquement les OT qui ont été générés par le planning
		if (firstNoOt.equals("")){
			firstNoOt = unOT.getNumeroot();
		}
		lastNoOt = unOT.getNumeroot();
	}
//	Tout s'est bien passé
	commitTransaction();	
	VariableGlobale.ajouter(request, "NUMOT", firstNoOt);
	VariableGlobale.ajouter(request, "NUMOTFIN", lastNoOt);
	setStatut(STATUT_VALIDATION,true);
	return true;
}

public boolean modifiePePerso(javax.servlet.http.HttpServletRequest request,Planning unPlanning) throws Exception{
//	 on recherche le PePerso correspondant pour la modification
	PePerso unPePerso = PePerso.chercherPePerso(getTransaction(),unPlanning.getCodepep());
	if (getTransaction().isErreur()){
		return false;
	}
	unPePerso.setCodeot("0");
	// pour les paramètres
	Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),unPePerso.getCodeequip());
	if (getTransaction().isErreur()){
		return false;
	}
	Entretien unEntretien = Entretien.chercherEntretien(getTransaction(),unPePerso.getCodeentretien());
	if (getTransaction().isErreur()){
		return false;
	}
	TypeEntretien unTe = TypeEntretien.chercherTypeEntretien(getTransaction(),unPePerso.getCodetypeent());
	if (getTransaction().isErreur()){
		return false;
	}
	TIntervalle unTi = TIntervalle.chercherTIntervalle(getTransaction(),unPePerso.getCodeti());
	if (getTransaction().isErreur()){
		return false;
	}
	//modification du PePerso
	unPePerso.modifierPePerso(getTransaction(),unEquipement,unEntretien,unTe,unTi);
	if (getTransaction().isErreur()){
		return false;
	}
	return true;
}

public ArrayList getListInventaire() {
	//initialisation
	if (listInventaire == null){
		listInventaire = new ArrayList();
	}
	return listInventaire;
}
public void setListInventaire(ArrayList listInventaire) {
	this.listInventaire = listInventaire;
}
public ArrayList getListPourOt() {
	//initialise
	if (listPourOt == null){
		listPourOt = new ArrayList();
	}
	return listPourOt;
}
public void setListPourOt(ArrayList listPourOt) {
	this.listPourOt = listPourOt;
}
	public boolean isAfaire() {
		return isAfaire;
	}
	public void setAfaire(boolean isAfaire) {
		this.isAfaire = isAfaire;
	}
	public boolean isVide() {
		return isVide;
	}
	public void setVide(boolean isVide) {
		this.isVide = isVide;
	}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATEFINPLANNING
 * Date de création : (18/08/05 16:07:05)
* 
 */
public java.lang.String getNOM_EF_DATEFINPLANNING() {
	return "NOM_EF_DATEFINPLANNING";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATEFINPLANNING
 * Date de création : (18/08/05 16:07:05)
* 
 */
public java.lang.String getVAL_EF_DATEFINPLANNING() {
	return getZone(getNOM_EF_DATEFINPLANNING());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_PLANNING
 * Date de création : (18/08/05 16:10:30)
* 
 */
public java.lang.String getNOM_PB_OK_PLANNING() {
	return "NOM_PB_OK_PLANNING";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (18/08/05 16:10:30)
* 
 */
public boolean performPB_OK_PLANNING(javax.servlet.http.HttpServletRequest request) throws Exception {
	if(!Services.estUneDate(getZone(getNOM_EF_DATEFINPLANNING()))){
		getTransaction().declarerErreur("La date saisie n'est pas correcte.");
		return false;
	}
	dateFinPlanning = getZone(getNOM_EF_DATEFINPLANNING());
	return true;
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_AFFICHAGE
 * Date de création : (28/11/05 14:41:44)
* 
 */
public java.lang.String getNOM_RG_AFFICHAGE() {
	return "NOM_RG_AFFICHAGE";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_AFFICHAGE
 * Date de création : (28/11/05 14:41:44)
* 
 */
public java.lang.String getVAL_RG_AFFICHAGE() {
	return getZone(getNOM_RG_AFFICHAGE());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_EN_RETARD
 * Date de création : (28/11/05 14:41:45)
* 
 */
public java.lang.String getNOM_RB_AFFICHAGE_EN_RETARD() {
	return "NOM_RB_AFFICHAGE_EN_RETARD";
}
	public boolean isTrouve() {
		return trouve;
	}
	public void setTrouve(boolean trouve) {
		this.trouve = trouve;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_DETAILS
 * Date de création : (10/01/06 06:56:07)
* 
 */
public java.lang.String getNOM_PB_DETAILS() {
	return "NOM_PB_DETAILS";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (10/01/06 06:56:07)
* 
 */
public boolean performPB_DETAILS(javax.servlet.http.HttpServletRequest request) throws Exception {
//	if (!aDoubleCliquer) {
		int indice = (Services.estNumerique(getVAL_LB_PLANNING_SELECT()) ? Integer.parseInt(getVAL_LB_PLANNING_SELECT()): -1);
		if(indice<0){
			if(getEquipementInfosCourant()!=null){
				if(getEquipementInfosCourant().getNumeroinventaire()!=null){
					// parcours de la liste des entretiens pour trouver le premier de l'équipement en cours
					for(int i=0;i<getListePlanning().size();i++){
						Planning unPlanning = (Planning)getListePlanning().get(i);
						if(unPlanning.getNumeroinventaire().equals(getEquipementInfosCourant().getNumeroinventaire())){
							indice = i;
							break;
						}
					}
				}
			}
		}
		if (indice == -1) {
			getTransaction().declarerErreur("Vous devez sélectionner un élement.");
			return false;
		}
		Planning monPlanning = (Planning)getListePlanning().get(indice);
		PePerso unPePerso = PePerso.chercherPePerso(getTransaction(),monPlanning.getCodepep());
		if(getTransaction().isErreur()){
			return false;
		}
		if(unPePerso!=null){
			addZone(getNOM_LB_PLANNING_SELECT(),String.valueOf(indice));
			if((unPePerso.getCommentairete()!=null)&&(!unPePerso.getCommentairete().equals(""))){
				addZone(getNOM_ST_COMMENTAIRE(),unPePerso.getCommentairete());
			}else{
				addZone(getNOM_ST_COMMENTAIRE(),"AUCUN COMMENTAIRE");
			}
		}
	//}
	//setADoubleCliquer(false);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMMENTAIRE
 * Date de création : (10/01/06 06:56:07)
* 
 */
public java.lang.String getNOM_ST_COMMENTAIRE() {
	return "NOM_ST_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMMENTAIRE
 * Date de création : (10/01/06 06:56:07)
* 
 */
public java.lang.String getVAL_ST_COMMENTAIRE() {
	return getZone(getNOM_ST_COMMENTAIRE());
}

/*
private boolean verifVisiteAnnuelle(nc.mairie.technique.Transaction aTransaction)  throws Exception {
	// on vérifie que tous les équipements ont été vus par l'ATM au moins une fois sur l'année
	// la visite annuelle a le code 0 dans la base
	Entretien unEntretien = Entretien.chercherEntretien(getTransaction(),"0");
	if(getTransaction().isErreur()){
		return false;
	}
	boolean programmee = false;
	String dateJour = Services.dateDuJour();
	int nResultat;
	dateJour = Services.enleveAnnee(dateJour,1);
	String dateJour1 = dateJour;
	dateJour = Services.formateDateInternationale(dateJour);
	ArrayList listEquip = Equipement.listerEquipement(getTransaction());
	if(getTransaction().isErreur()){
		return false;
	}
	ArrayList listCodeEquip = PePerso.listerPePersoEquipMoinsUnAn(getTransaction(),dateJour);
	if(getTransaction().isErreur()){
		return false;
	}
	// pour chaque équipement on regarde s'il a des entretiens s'il n'en a pas on vérifie s'il faut faire la visite annuelle
	for (int indice=0;indice<listEquip.size();indice++){
		Equipement unEquipement = (Equipement)listEquip.get(indice);
		// si c'est un équipement
		Modeles unModele = Modeles.chercherModeles(getTransaction(),unEquipement.getCodemodele());
		if(getTransaction().isErreur()){
			return false;
		}
		TYPEEQUIP unTe = TYPEEQUIP.chercherTYPEEQUIP(getTransaction(),unModele.getCodete());
		if(getTransaction().isErreur()){
			return false;
		}
		if(unTe.getTypete().equals("E")){
			ArrayList listEntretien = PePerso.listerPePersoEquip(getTransaction(),unEquipement.getNumeroinventaire());
			if(getTransaction().isErreur()){
				return false;
			}
			//	si pas trouvé d'entretien c'est que le plan d'entretien n'a pas été renseigné
			if(listEntretien.size()<=0){
				// si plus d'un an que l'équipement a été mis en circulation : on crée un entretien visite annuelle
				int compare = Services.compareDates(dateJour,unEquipement.getDatemiseencirculation());
				if(compare==1){
					programmee = true;
				}
			}
	//		 création de la visite annuelle si l'un des cas s'est présenté
			if(programmee){	
				PePerso unNouvPePerso = new PePerso();
				unNouvPePerso.setCodeequip(unEquipement.getNumeroinventaire());
				
				//le type d'intervalle est sans
				TIntervalle unTInt = TIntervalle.chercherTIntervalle(getTransaction(),"4");
				if(getTransaction().isErreur()){
					return false;
				}
				// le type d'entretien est occasionnel(2)
				TypeEntretien unTEnt = TypeEntretien.chercherTypeEntretien(getTransaction(),"2");
				if(getTransaction().isErreur()){
					return false;
				}
				
				unNouvPePerso.setDateprev(Services.dateDuJour());
				unNouvPePerso.setDuree("2");
				unNouvPePerso.setIntervallepep("0");
				unNouvPePerso.setSinistre("NON");
				unNouvPePerso.creerPePerso(getTransaction(),unEquipement,unEntretien,unTEnt,unTInt);
				if(getTransaction().isErreur()){
					return false;
				}
			}
		}
	}
	programmee = false;
	// les autres équipements se trouvent dans la liste des codes d'équipements des peperso
	if(listCodeEquip.size()>0){
		for(int i=0;i<listCodeEquip.size();i++){
			PePerso unPePerso = (PePerso)listCodeEquip.get(i);
			
			// on vérifie que l'équipement à été mis en service avant DateduJour - 1 an
			Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),unPePerso.getCodeequip());
			if(getTransaction().isErreur()){
				return false;
			}
			//String dateEnCirculation = Services.formateDateInternationale(unEquipement.getDatemiseencirculation());
			
			nResultat = Services.compareDates(dateJour1,unEquipement.getDatemiseencirculation());
			if(nResultat==1){
				ArrayList listVisite = PePerso.chercherPePersoEquipEnt(getTransaction(),unPePerso.getCodeequip(),unEntretien.getCodeentretien());
				if(getTransaction().isErreur()){
					return false;
				}else{
					if(listVisite.size()>0){
						for(int j=0;j<listVisite.size();j++){
							PePerso unPePersoVisite = (PePerso)listVisite.get(j);
							if(!"01/01/0001".equals(unPePersoVisite.getDatereal())){
								programmee = false;
							}else{
								programmee = true;
							}
						}
					}
				}
			}
			//	création de la visite annuelle si l'un des cas s'est présenté
			if(programmee){	
				PePerso unNouvPePerso = new PePerso();
				unNouvPePerso.setCodeequip(unEquipement.getNumeroinventaire());
				
				//le type d'intervalle est sans
				TIntervalle unTInt = TIntervalle.chercherTIntervalle(getTransaction(),"4");
				if(getTransaction().isErreur()){
					return false;
				}
				// le type d'entretien est occasionnel(2)
				TypeEntretien unTEnt = TypeEntretien.chercherTypeEntretien(getTransaction(),"2");
				if(getTransaction().isErreur()){
					return false;
				}
				
				unNouvPePerso.setDateprev(Services.dateDuJour());
				unNouvPePerso.setDuree("2");
				unNouvPePerso.setIntervallepep("0");
				unNouvPePerso.setSinistre("NON");
				unNouvPePerso.creerPePerso(getTransaction(),unEquipement,unEntretien,unTEnt,unTInt);
				if(getTransaction().isErreur()){
					return false;
				}
			}
		}
	}
	if(getTransaction().isErreur()){
		return false;
	}
	return true;
}*/
public boolean isFirst() {
	return first;
}
public void setFirst(boolean first) {
	this.first = first;
}
	public boolean isADoubleCliquer() {
		return aDoubleCliquer;
	}
	public void setADoubleCliquer(boolean doubleCliquer) {
		aDoubleCliquer = doubleCliquer;
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (18/07/05 09:25:07)
* 
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_EFFACE_COMMENTAIRE
		if (testerParametre(request, getNOM_PB_EFFACE_COMMENTAIRE())) {
			return performPB_EFFACE_COMMENTAIRE(request);
		}

		//Si clic sur le bouton PB_DETAILS
		if (testerParametre(request, getNOM_PB_DETAILS())) {
			return performPB_DETAILS(request);
		}

		//Si clic sur le bouton PB_OK_PLANNING
		if (testerParametre(request, getNOM_PB_OK_PLANNING())) {
			return performPB_OK_PLANNING(request);
		}

		//Si clic sur le bouton PB_VALIDER
		if (testerParametre(request, getNOM_PB_VALIDER())) {
			return performPB_VALIDER(request);
		}

		//Si clic sur le bouton PB_AJOUTER
		if (testerParametre(request, getNOM_PB_AJOUTER())) {
			return performPB_AJOUTER(request);
		}

		//Si clic sur le bouton PB_ENLEVER
		if (testerParametre(request, getNOM_PB_ENLEVER())) {
			return performPB_ENLEVER(request);
		}

		//Si clic sur le bouton PB_OK_TRI
		if (testerParametre(request, getNOM_PB_OK_TRI())) {
			return performPB_OK_TRI(request);
		}

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (07/02/06 11:14:05)
* 
 */
public String getJSP() {
	return "OePlanning.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_EFFACE_COMMENTAIRE
 * Date de création : (07/02/06 11:14:06)
* 
 */
public java.lang.String getNOM_PB_EFFACE_COMMENTAIRE() {
	return "NOM_PB_EFFACE_COMMENTAIRE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (07/02/06 11:14:06)
* 
 */
public boolean performPB_EFFACE_COMMENTAIRE(javax.servlet.http.HttpServletRequest request) throws Exception {
	addZone(getNOM_ST_COMMENTAIRE(),"");
	return true;
}
/**
 * @return Renvoie equipementInfosCourant.
 */
private EquipementInfos getEquipementInfosCourant() {
	return equipementInfosCourant;
}
/**
 * @param equipementInfosCourant equipementInfosCourant à définir.
 */
private void setEquipementInfosCourant(
		EquipementInfos equipementInfosCourant) {
	this.equipementInfosCourant = equipementInfosCourant;
}

public Hashtable<String, TypeEntretien> getHashTypeEntretien() {
	if (hashTypeEntretien == null) 
		hashTypeEntretien = new Hashtable<String, TypeEntretien>();
	return hashTypeEntretien;
}

public void setHashTypeEntretien(
		Hashtable<String, TypeEntretien> hashTypeEntretien) {
	this.hashTypeEntretien = hashTypeEntretien;
}
}
