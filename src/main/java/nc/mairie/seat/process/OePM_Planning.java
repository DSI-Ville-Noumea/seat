package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import nc.mairie.seat.metier.Entretien;
import nc.mairie.seat.metier.FPM;
import nc.mairie.seat.metier.PM_PePerso;
import nc.mairie.seat.metier.PM_Planning;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.technique.FormateListe;
import nc.mairie.technique.Services;
import nc.mairie.technique.Transaction;
import nc.mairie.technique.UserAppli;
import nc.mairie.technique.VariableActivite;
import nc.mairie.technique.VariableGlobale;
/**
 * Process OePlanning
 * Date de création : (18/07/05 09:25:07)
 * @author : Générateur de process
*/
public class OePM_Planning extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_VALIDATION = 2;
	public static final int STATUT_PEPERSO = 1;
	private PM_Planning pMPlanningCourant;
	private String tri = "pminv";
	private String param ="";
	private ArrayList listePlanning;
	private ArrayList listeAFaire;
	private ArrayList listInventaire;
	private ArrayList listPourFiche;
	private java.lang.String[] LB_PLANNING;
	private java.lang.String[] LB_PLANNING_Couleurs;
	private java.lang.String[] LB_PLANNING_FCouleurs;
	private java.lang.String[] LB_ENTRETIENS_A_FAIRE;
	public 	boolean isVide = true;
	public 	boolean isAfaire = false;
	private String msg = "";
	private String msg2 = "";
	public String firstNumFiche = "";
	public String lastNumFiche = "";
	private String dateFinPlanning = "";
	private boolean first = true;
	private boolean aDoubleCliquer = false;
	private boolean trouve = false;
	private PMatInfos pMatInfosCourant;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (18/07/05 09:25:07)
 * @author : Générateur de process
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
	if(unPMatInfos!=null){
		setPMatInfosCourant(unPMatInfos);
	}
	// si la date de fin du planning n'a pas été renseignée : on affiche le planning sur une semaine calendaire
	dateFinPlanning = getZone(getNOM_EF_DATEFINPLANNING());
	if(dateFinPlanning.equals("")){
		dateFinPlanning = Services.ajouteJours(Services.dateDuJour(),7);
	}
	addZone(getNOM_EF_DATEFINPLANNING(),dateFinPlanning);
	
	// vérif des entretiens annuels si besoin
	//LB 09/09/11 
	if (isFirst()) {
		verifEntretienAnnuel(request);
		setFirst(false);
	}
	
	
	ArrayList a = new ArrayList();
	// Si param = encours on liste les petits matériels actifs
	if ("encours".equals(param)){
		ArrayList listEnCours = PM_Planning.listerPlanningEnCoursAvecFPMValideDiffetentT(getTransaction(),firstNumFiche);
		a=listEnCours;
		//optimisation luc 9/9/11
/*		a = new ArrayList();
		if(listEnCours.size()>0){
			for(int i=0;i<listEnCours.size();i++){
				//on enlève tous les plannings dont FPM est validé
				PM_Planning unPmPlanning = (PM_Planning)listEnCours.get(i);
				FPM unPMatFiche = FPM.chercherFPM(getTransaction(),unPmPlanning.getNumfiche());
				if(getTransaction().isErreur()){
					return ;
				}
				if(!unPMatFiche.getValide().trim().equals("T")){
					a.add(unPmPlanning);
				}
			}
		
		}
		*/
//		 si param = "enretard" : on liste les entretiens en retard
	} else if ("enretard".equals(param)){
		ArrayList listEnRetard = PM_Planning.listerPlanningEnRetard(getTransaction(),Services.dateDuJour());
		if(getTransaction().isErreur()){
			return;
		}
		a = listEnRetard;
	} else {
		a = PM_Planning.listerPlanningAFaire(getTransaction(),dateFinPlanning);
	}
		
	
	//si la liste des entretiens à faire n'est pas vide : on doit épurée la liste du planning
	if (!param.equals("encours")){
		if ((getListeAFaire().size()>0) &&(a.size()>0)){
			for (int i = 0; i<getListeAFaire().size();i++){
				trouve = false;
				PM_Planning monPlanningAFaire = (PM_Planning)getListeAFaire().get(i);
				String mPAFaire = monPlanningAFaire.getCodeentretien();
				String mPAFaireInv = monPlanningAFaire.getPminv();
				for (int j=0;j<a.size();j++){
					if (!trouve){
						PM_Planning monPlanning = (PM_Planning)a.get(j);
						String mP = monPlanning.getCodeentretien();
						String mPInv = monPlanning.getPminv();
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
	if((getListeAFaire().size()==0)&&(getListePlanning().size()==0)&&(getListPourFiche().size()==0)){
		setVide(true);
	}else{
		setVide(false);
	}
	String isRetour = (String)VariableActivite.recuperer(this,"RETOUR");
	if(isRetour!=null&&isRetour.equals("TRUE")){
		setLB_ENTRETIENS_A_FAIRE(LBVide);
	}
}


//on vérifie que tous les petits matériels ont été visité au moins une fois
public void verifEntretienAnnuel(javax.servlet.http.HttpServletRequest request) throws Exception{
	//Modif de LUC optimisation 9/9/2011
	ArrayList listPM = PMatInfos.listerPMatInfosSansEntretienPlanifie(getTransaction());

	//Si on en trouve, on lui crée des entretiens planifiés
	if(listPM.size()>0){
		Transaction transTemp = new Transaction((UserAppli)VariableGlobale.recuperer(request,VariableGlobale.GLOBAL_USER_APPLI));
		
		for (int i=0;i<listPM.size();i++){
			PMatInfos unPMatInfos = (PMatInfos)listPM.get(i);

			// plannification de l'entretien annuel.
			PM_PePerso unPmPep = new PM_PePerso();
			PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),unPMatInfos.getPminv());
			if(getTransaction().isErreur()){
				return;
			}
			// l'entretien 0 est l'entretien de visite annuelle
			Entretien unEntretien = Entretien.chercherEntretien(getTransaction(),"0");
			if(getTransaction().isErreur()){
				return;
			}
			unPmPep.setDprev(Services.dateDuJour());
			unPmPep.creerPM_PePerso(getTransaction(), unPMateriel,unEntretien);
			if(getTransaction().isErreur()){
				return;
			}
		}
		//on comite les creations
		transTemp.commitTransaction();
		transTemp.getConnection().close();
		transTemp = null;
	}
}

/*on initialisse la liste des entretiens qui vont constituer les FPM
 * 
 * 
 */

public void initialiseListeAFaire(javax.servlet.http.HttpServletRequest request) throws Exception{
	String date = "";
	if(getListeAFaire().size()>0){
		//les élèments de la liste 
		int [] tailles = {10,30,10};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G","G","C"};
		
		FormateListe aFormat = new FormateListe(tailles,padding,true);
		for (int i = 0; i < getListeAFaire().size() ; i++) {
			PM_Planning aPlanning = (PM_Planning)getListeAFaire().get(i);	
			
			// on affiche la date que si elle est différente de 01/01/0001
			date = aPlanning.getDprev();
			if (aPlanning.getDprev().equals("01/01/0001")){
				date = "";
			}
			
			String ligne [] = { aPlanning.getPmserie(),aPlanning.getLibelleentretien(),date};
			aFormat.ajouteLigne(ligne);
		}
		setAfaire(true);
		setLB_ENTRETIENS_A_FAIRE(aFormat.getListeFormatee());
	}else{
		setLB_ENTRETIENS_A_FAIRE(LBVide);
	}
	if(getTransaction().isErreur()){
		return ;
	}
}
/**
 * Constructeur du process OePlanning.
 * Date de création : (18/07/05 09:25:07)
 * @author : Générateur de process
 */
public OePM_Planning() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (18/07/05 09:25:07)
 * @author : Générateur de process
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
 * @author : Générateur de process
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_PLANNING_SELECT()) ? Integer.parseInt(getVAL_LB_PLANNING_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	PM_Planning monPlanning = (PM_Planning)getListePlanning().get(indice);
	PMatInfos monPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),monPlanning.getPminv());
	if (getTransaction().isErreur()){
		return false;
	}
	if(null==monPMatInfos){
		monPMatInfos = new PMatInfos();
	}
//	On met la variable globale
	VariableGlobale.ajouter(request, "PMATINFOS", monPMatInfos);
	// on débranche sur le plan d'entretien personnalisé du petit matériel sélectionné
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
	if (colonne.equals("dprev"))
		ordres=new boolean[] {false};
	String encours = "";
	String date = "";
	String datedujour = Services.dateDuJour();
	boolean estAFaire = false;
	
	a= Services.trier(a,colonnes,ordres);
	setListePlanning(a);
	if(a.size()>0){
		//les élèments de la liste 
		int [] tailles = {20,20,10,10,13};
		String [] padding = {"G","G","C","G","G"};
		setLB_PLANNING_Couleurs(new String[a.size()]);
		setLB_PLANNING_FCouleurs(new String[a.size()]);
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		
		for (int i = 0; i < a.size() ; i++) {
			PM_Planning aPlanning = (PM_Planning)a.get(i);
			if(getTransaction().isErreur()){
				return;
			}
			
			 //controle si en cours de réalisation ou pas
			if((aPlanning.getNumfiche()!=null)&&(!aPlanning.getNumfiche().equals(""))){
				//je cherche si FPM correspondant est validé ou pas
				FPM unPMatFiche = FPM.chercherFPM(getTransaction(),aPlanning.getNumfiche());
				if(getTransaction().isErreur()){
					return;
				}
				// si FPM n'est pas validé
				if(unPMatFiche.getValide().trim().equals("F")){
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
				if (!aPlanning.getDprev().equals("01/01/0001")){
					int enRetard = Services.compareDates(aPlanning.getDprev(),datedujour);
					if (enRetard==-9999){
						return;
					}else if (enRetard==-1){
						if (aPlanning.getNumfiche()==null){
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
			if(getPMatInfosCourant()!=null){
				if(getPMatInfosCourant().getPminv()!=null){
//					 si l'entretien appartient au petit matériel en cours on le surligne
					if (aPlanning.pminv.equals(getPMatInfosCourant().getPminv())){
						getLB_PLANNING_Couleurs()[i] = "white";
						getLB_PLANNING_FCouleurs()[i] = "teal";
					}
				}
			}
			//on affiche la date que si elle est différente de 01/01/0001
			date = aPlanning.getDprev();
			if (aPlanning.getDprev().equals("01/01/0001")){
				date = "";
			}
			
			String ligne [] = { aPlanning.getPmserie(),aPlanning.getLibelleentretien(),date,aPlanning.getNumfiche(),encours};
			aFormat.ajouteLigne(ligne);
			setVide(false);
			setLB_PLANNING(aFormat.getListeFormatee());
		}
	}else{
		setLB_PLANNING(LBVide);
		setVide(true);
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
	
	if (tri.equals("pmserie")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_SERIE());
	}else if (tri.equals("libelleentretien")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_ENTRETIENS());
	}else if (tri.equals("dprev")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_DATEPREVU());
	}
}

/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_TRI
 * Date de création : (18/07/05 09:25:07)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RG_TRI() {
	return "NOM_RG_TRI";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_TRI
 * Date de création : (18/07/05 09:25:07)
 * @author : Générateur de process
 */
public java.lang.String getVAL_RG_TRI() {
	return getZone(getNOM_RG_TRI());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_ENCOURS
 * Date de création : (18/07/05 09:25:07)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_AFFICHAGE_ENCOURS() {
	return "NOM_RB_AFFICHAGE_ENCOURS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_TOUS
 * Date de création : (18/07/05 09:25:07)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_AFFICHAGE_TOUS() {
	return "NOM_RB_AFFICHAGE_TOUS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_DATEPREVU
 * Date de création : (18/07/05 09:25:07)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_DATEPREVU() {
	return "NOM_RB_TRI_DATEPREVU";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_ENTRETIENS
 * Date de création : (18/07/05 09:25:07)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_ENTRETIENS() {
	return "NOM_RB_TRI_ENTRETIENS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_IMMAT
 * Date de création : (18/07/05 09:25:07)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_SERIE() {
	return "NOM_RB_TRI_SERIE";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_TRI
 * Date de création : (18/07/05 09:27:41)
 * @author : Générateur de process
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
 * @author : Générateur de process
 */
public boolean performPB_OK_TRI(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 selon la sélection la liste sera triée
	if (getNOM_RB_TRI_SERIE().equals(getZone(getNOM_RG_TRI()))){
		tri = "pmserie";
	}
	if (getNOM_RB_TRI_DATEPREVU().equals(getZone(getNOM_RG_TRI()))){
		tri = "dprev";
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
 * @author : Générateur de process
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
 * @author : Générateur de process
 */
private void setLB_PLANNING(java.lang.String[] newLB_PLANNING) {
	LB_PLANNING = newLB_PLANNING;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_PLANNING
 * Date de création : (18/07/05 09:30:00)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PLANNING() {
	return "NOM_LB_PLANNING";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PLANNING_SELECT
 * Date de création : (18/07/05 09:30:00)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PLANNING_SELECT() {
	return "NOM_LB_PLANNING_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_PLANNING
 * Date de création : (18/07/05 09:30:00)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_PLANNING() {
	return getLB_PLANNING();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_PLANNING
 * Date de création : (18/07/05 09:30:00)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_PLANNING_SELECT() {
	return getZone(getNOM_LB_PLANNING_SELECT());
}
public PM_Planning getPMPlanningCourant() {
	return pMPlanningCourant;
}
public void setPMPlanningCourant(PM_Planning pmPlanningCourant) {
	this.pMPlanningCourant = pmPlanningCourant;
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
 * @author : Générateur de process
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
 * @author : Générateur de process
 */
private void setLB_ENTRETIENS_A_FAIRE(java.lang.String[] newLB_ENTRETIENS_A_FAIRE) {
	LB_ENTRETIENS_A_FAIRE = newLB_ENTRETIENS_A_FAIRE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_ENTRETIENS_A_FAIRE
 * Date de création : (18/07/05 16:08:58)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_ENTRETIENS_A_FAIRE() {
	return "NOM_LB_ENTRETIENS_A_FAIRE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIENS_A_FAIRE_SELECT
 * Date de création : (18/07/05 16:08:58)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_ENTRETIENS_A_FAIRE_SELECT() {
	return "NOM_LB_ENTRETIENS_A_FAIRE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_ENTRETIENS_A_FAIRE
 * Date de création : (18/07/05 16:08:58)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_ENTRETIENS_A_FAIRE() {
	return getLB_ENTRETIENS_A_FAIRE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_ENTRETIENS_A_FAIRE
 * Date de création : (18/07/05 16:08:58)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_ENTRETIENS_A_FAIRE_SELECT() {
	return getZone(getNOM_LB_ENTRETIENS_A_FAIRE_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (18/07/05 16:09:45)
 * @author : Générateur de process
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
 * @author : Générateur de process
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice = (Services.estNumerique(getVAL_LB_PLANNING_SELECT()) ? Integer.parseInt(getVAL_LB_PLANNING_SELECT()): -1);
	if(indice == -1){
//		 si un petit matériel en cours
		if(getPMatInfosCourant()!=null){
			if(!("").equals(getPMatInfosCourant().getPminv())){
				for (int i=0;i<getListePlanning().size();i++){
					PM_Planning unPM_Planning= (PM_Planning)getListePlanning().get(i);
					if (unPM_Planning.getPminv().equals(getPMatInfosCourant().getPminv())){
						ajouteEntretiens(request,i);
						i=0;
					}
				}
			}
		}else {
			getTransaction().declarerErreur("Vous devez sélectionner un élément à ajouter dans la liste des entretiens.");
			return false;
		}
	}else{
			// on ajoute l'entretien à faire dans la liste 2 et on l'enlève du planning
//			int indice = (Services.estNumerique(getVAL_LB_PLANNING_SELECT()) ? Integer.parseInt(getVAL_LB_PLANNING_SELECT()): -1); 
//			if (indice == -1) {
//				getTransaction().declarerErreur("Vous devez sélectionner un élement à ajouter dans la liste des entretiens ");
//				return false;
//			}
	ajouteEntretiens(request,indice);
	}
	return true;
}

// méthode : ajout l'entretien dont l'indice est passé en paramètre dans la liste des entretiens à faire
public void ajouteEntretiens(javax.servlet.http.HttpServletRequest request,int indice) throws Exception {
	PM_Planning monPlanning = (PM_Planning)getListePlanning().get(indice);
	//on teste si l'entretien est en cours de réalisation ou pas : si numfiche renseigné et valide=F
	if((monPlanning.getNumfiche()!=null)&&(!monPlanning.getNumfiche().equals(""))){
		//je cherche si FPM correspondant est validé ou pas
		FPM unFPM = FPM.chercherFPM(getTransaction(),monPlanning.getNumfiche());
		if(getTransaction().isErreur()){
			return ;
		}
		// si FPM n'est pas validé
		if(unFPM.getValide().trim().equals("F")){
			msg2 =" Cet entretien est déjà en cours de réalisation.";
		}else{
			if((monPlanning.getDreal().equals(""))||("01/01/0001".equals(monPlanning.getDreal()))){
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
		return ;
	}
	setADoubleCliquer(true);
	return;
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ENLEVER
 * Date de création : (18/07/05 16:09:45)
 * @author : Générateur de process
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
 * @author : Générateur de process
 */
public boolean performPB_ENLEVER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on ajoute l'entretien à faire dans le planning et on l'enlève de la liste 2
	int indice = (Services.estNumerique(getVAL_LB_ENTRETIENS_A_FAIRE_SELECT()) ? Integer.parseInt(getVAL_LB_ENTRETIENS_A_FAIRE_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement à enlever de la liste des entretiens ");
		return false;
	}
	PM_Planning monPlanning = (PM_Planning)getListeAFaire().get(indice);
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
 * Date de création : (19/07/05 10:12:15)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//on créer les FPM pour les petits matériels dont les entretiens ont été sélectionné
	/*if(getListeAFaire().size()>0){
		for (int i=0;i<getListeAFaire().size();i++){
			
		}
	}*/
	Hashtable hRegroupEnt = new Hashtable();
	for (int i = 0; i < getListeAFaire().size(); i++){
		PM_Planning unPlanning = (PM_Planning)getListeAFaire().get(i);
		// on regroupe les entretiens par petits matériels dans une hashtable
		//on liste d'abord les entretiens d'un petit matériel
		ArrayList listeEnt = (ArrayList)hRegroupEnt.get(unPlanning.getPminv());
		//ArrayList listPePerso = unPlanning;
		if (listeEnt == null) 
			listeEnt = new ArrayList();
		listeEnt.add(unPlanning);
		// on rajoute dans la hashtable à l'indice numéroinventaire
		hRegroupEnt.put(unPlanning.getPminv(),listeEnt);
	}
	ArrayList listeAValider = new ArrayList();

	Enumeration enumer = hRegroupEnt.keys();
	//pour chaque petit matériel on crée un FPM avec le max trouvé dans la table
	while (enumer.hasMoreElements()) {
		String noinvent = String.valueOf(enumer.nextElement());
		FPM unPMatFiche = new FPM();
		PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),noinvent);
		if(getTransaction().isErreur()){
			return false;
		}
		unPMatFiche.creerFPM(getTransaction(),unPMateriel);
		if (getTransaction().isErreur()){
			return false;
		}
		//on récupère la liste des entretiens à faire pour le petit matériel
		ArrayList array = (ArrayList)hRegroupEnt.get(noinvent);
		for (int i = 0; i < array.size(); i++){
			PM_Planning unPlanning = (PM_Planning)array.get(i);
			// on modifie le code FPM des peperso
			PM_PePerso unPePerso = PM_PePerso.chercherPM_PePerso(getTransaction(),unPlanning.getCodepmpep());
			if(getTransaction().isErreur()){
				return false;
			}
			//modification du numfiche
			unPePerso.setNumfiche(unPMatFiche.getNumfiche());
			unPePerso.modifierPmPePersoInfos(getTransaction());
			if(getTransaction().isErreur()){
				return false;
			}
			
		}	
		// pour afficher uniquement les FPM qui ont été générés par le planning
		if (firstNumFiche.equals("")){
			firstNumFiche = unPMatFiche.getNumfiche();
		}
		lastNumFiche = unPMatFiche.getNumfiche();
	}
//	Tout s'est bien passé
	commitTransaction();	
	VariableGlobale.ajouter(request, "NUMFICHE", firstNumFiche);
	VariableGlobale.ajouter(request, "NUMFICHEFIN", lastNumFiche);
	setStatut(STATUT_VALIDATION,true);
	return true;
}

/*on initialisse la liste des entretiens qui vont constituer les FPM
 * 
 * 
 */

public boolean modifiePePerso(javax.servlet.http.HttpServletRequest request,PM_Planning unPlanning) throws Exception{
//	 on recherche le PePerso correspondant pour la modification
	PM_PePerso unPePerso = PM_PePerso.chercherPM_PePerso(getTransaction(),unPlanning.getCodepmpep());
	if (getTransaction().isErreur()){
		return false;
	}
	unPePerso.setNumfiche("0");
	// pour les paramètres
	PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),unPePerso.getPminv());
	if (getTransaction().isErreur()){
		return false;
	}
	Entretien unEntretien = Entretien.chercherEntretien(getTransaction(),unPePerso.getCodeentretien());
	if (getTransaction().isErreur()){
		return false;
	}
	
	//modification du PePerso
	unPePerso.modifierPM_PePerso(getTransaction(),unPMateriel,unEntretien);
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
public ArrayList getListPourFiche() {
	//initialise
	if (listPourFiche == null){
		listPourFiche = new ArrayList();
	}
	return listPourFiche;
}
public void setListPourFiche(ArrayList listPourFiche) {
	this.listPourFiche = listPourFiche;
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
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DATEFINPLANNING() {
	return "NOM_EF_DATEFINPLANNING";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATEFINPLANNING
 * Date de création : (18/08/05 16:07:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DATEFINPLANNING() {
	return getZone(getNOM_EF_DATEFINPLANNING());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_PLANNING
 * Date de création : (18/08/05 16:10:30)
 * @author : Générateur de process
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
 * @author : Générateur de process
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
 * @author : Générateur de process
 */
public java.lang.String getNOM_RG_AFFICHAGE() {
	return "NOM_RG_AFFICHAGE";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_AFFICHAGE
 * Date de création : (28/11/05 14:41:44)
 * @author : Générateur de process
 */
public java.lang.String getVAL_RG_AFFICHAGE() {
	return getZone(getNOM_RG_AFFICHAGE());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_EN_RETARD
 * Date de création : (28/11/05 14:41:45)
 * @author : Générateur de process
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
 * @author : Générateur de process
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
 * @author : Générateur de process
 */
public boolean performPB_DETAILS(javax.servlet.http.HttpServletRequest request) throws Exception {
//	if (!aDoubleCliquer) {
	int indice = (Services.estNumerique(getVAL_LB_PLANNING_SELECT()) ? Integer.parseInt(getVAL_LB_PLANNING_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement.");
		return false;
	}		
	PM_Planning monPlanning = (PM_Planning)getListePlanning().get(indice);
	PM_PePerso unPePerso = PM_PePerso.chercherPM_PePerso(getTransaction(),monPlanning.getCodepmpep());
	if(getTransaction().isErreur()){
		return false;
	}
	if(unPePerso!=null){
		if((unPePerso.getCommentaire()!=null)&&(!unPePerso.getCommentaire().equals(""))){
			addZone(getNOM_ST_COMMENTAIRE(),unPePerso.getCommentaire());
		}else{
			addZone(getNOM_ST_COMMENTAIRE(),"AUCUN COMMENTAIRE");
		}
	}
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMMENTAIRE
 * Date de création : (10/01/06 06:56:07)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COMMENTAIRE() {
	return "NOM_ST_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMMENTAIRE
 * Date de création : (10/01/06 06:56:07)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COMMENTAIRE() {
	return getZone(getNOM_ST_COMMENTAIRE());
}

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
 * @author : Générateur de process
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
 * @author : Générateur de process
 */
public String getJSP() {
	return "OePM_Planning.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_EFFACE_COMMENTAIRE
 * Date de création : (07/02/06 11:14:06)
 * @author : Générateur de process
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
 * @author : Générateur de process
 */
public boolean performPB_EFFACE_COMMENTAIRE(javax.servlet.http.HttpServletRequest request) throws Exception {
	addZone(getNOM_ST_COMMENTAIRE(),"");
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
}
