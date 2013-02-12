package nc.mairie.seat.process;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.ENGJU;
import nc.mairie.seat.metier.FPM;
import nc.mairie.seat.metier.FPMComplete;
import nc.mairie.seat.metier.PM_ATM;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PM_BE;
import nc.mairie.seat.metier.PM_PePerso;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PiecesFpmInfos;
import nc.mairie.seat.metier.Pm_PePersoInfos;
import nc.mairie.seat.metier.Service;
import nc.mairie.seat.servlet.ServletSeat;
import nc.mairie.servlets.Frontale;
import nc.mairie.technique.*;
/**
 * Process OeFPM_Visualisation
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
*/
public class OeFPM_Visualisation extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_RECHERCHER = 1;
	private java.lang.String[] LB_INTERVENANTS;
	private java.lang.String[] LB_INTERVENTIONS;
	private java.lang.String[] LB_PIECES;
	private FPMComplete fpmCompleteCourant;
	private PMatInfos pMatInfosCourant;
	private FPM fpmCourant;
	private ArrayList listePmBe;
	private ArrayList listeInterventions;
	private ArrayList listeIntervenants;
	private ArrayList listPieces;
	public boolean isDebranche = false;
	public String messErreur;
	private String focus = null;
	private String starjetMode = (String)Frontale.getMesParametres().get("STARJET_MODE");
	private String script;
	public int	montantTotalPieces;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	String debranche = (String)VariableActivite.recuperer(this,"DEBRANCHE");
	PM_Affectation_Sce_Infos unASI = new PM_Affectation_Sce_Infos();
	if(debranche==null){
		setDebranche(false);
	}else{
		if(debranche.equals("TRUE")){
			setDebranche(true);
		}else{
			setDebranche(false);
		}
	}
	FPM unFPM = (FPM)VariableGlobale.recuperer(request,"FPM");
	PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
	setPMatInfosCourant(unPMatInfos);
	String dentree = "";
	String dsortie = "";
	if(unFPM!=null){
		setFpmCourant(unFPM);
//		 pour trouver le service qui a le véhicule à la date de la fiche d'entretiens
		if((getPMatInfosCourant()!=null)&&(getPMatInfosCourant().getPminv()!=null)&&(getFpmCourant().getDentree()!=null)){
			if(!getFpmCourant().getDentree().equals("")){
				unASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),getFpmCourant().getDentree());
			}else{
				unASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),Services.dateDuJour());
			}
			if(getTransaction().isErreur()){
				//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
				getTransaction().traiterErreur();
				addZone(getNOM_ST_SERVICE(),"pas affecté");
			}else{
				Service unService = Service.chercherService(getTransaction(),unASI.getSiserv());
				if(getTransaction().isErreur()){
					getTransaction().declarerErreur("Le service n'a pas été trouvé.");
					return ;
				}
				addZone(getNOM_ST_SERVICE(),unService.getLiserv());
			}
		}else{
			addZone(getNOM_ST_SERVICE(),"");
		}
		// initialisation des zones
		initialiseListIntervenants(request);
		initialiseListInterventions(request);
		initialiseListFre(request);
		initialiseListPieces(request);
		addZone(getNOM_ST_NUMFICHE(),getFpmCourant().getNumfiche());
		if((getFpmCourant().getDentree()==null)||(getFpmCourant().getDentree().equals("01/01/0001"))){
			dentree = "";
		}else{
			dentree = getFpmCourant().getDentree();
		}
		if((getFpmCourant().getDsortie()==null)||(getFpmCourant().getDsortie().equals("01/01/0001"))){
			dsortie = "";
		}else{
			dsortie = getFpmCourant().getDsortie();
		}
		addZone(getNOM_ST_DSORTIE(),dsortie);
		addZone(getNOM_ST_DENTREE(),dentree);
		addZone(getNOM_ST_COMMENTAIRE(),getFpmCourant().getCommentaire());

		
		//recherche du coût total de FPM
		int total = ENGJU.CoutTotal_PM(getTransaction(),getFpmCourant());
		if(total==-1){
			total = 0;
		}
		if(getTransaction().isErreur()){
			messErreur = "Une erreur s'est produite lors du calcul du montant du bon d'engagement.";
			getTransaction().traiterErreur() ;
		}
		addZone(getNOM_ST_COUT_TOTAL(),String.valueOf(total));
	}
	
	if(unPMatInfos!=null){
		setPMatInfosCourant(unPMatInfos);
		if(unPMatInfos.getPminv()!=null){
			//initialisation des zones
			addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
			addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
			addZone(getNOM_ST_NOIMMAT(),getPMatInfosCourant().getPmserie());
			addZone(getNOM_ST_MODELE(),getPMatInfosCourant().getDesignationmodele());
			addZone(getNOM_ST_MARQUE(),getPMatInfosCourant().getDesignationmarque());
		}
	}
	
	// lors du problème du calcul du montant du bon d'engagement
	if (!("").equals(messErreur)&&(messErreur!=null)){
		getTransaction().declarerErreur(messErreur);
		messErreur = "";
		return;
	}
}

public boolean initialiseListInterventions(javax.servlet.http.HttpServletRequest request) throws Exception{
	// on initialise la liste des interventions réalisées pendant FPM
	if(getFpmCourant().getNumfiche()!=null){
		//ArrayList listInterventions = Pm_PePersoInfos.chercherPmPePersoInfosFPM(getTransaction(),getFpmCourant().getNumfiche());
		ArrayList listInterventions = PM_PePerso.chercherPmPePersoFiche(getTransaction(),getFpmCourant().getNumfiche());
		if(getTransaction().isErreur()){
			return false;
		}
		setListeInterventions(listInterventions);
	}
	
	if(getListeInterventions().size()>0){
		int [] tailles = {30,10,10};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G","C","G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < getListeInterventions().size() ; i++) {
			//Pm_PePersoInfos monPePersoInfos = (Pm_PePersoInfos)getListeInterventions().get(i);
			PM_PePerso monPePerso = (PM_PePerso)getListeInterventions().get(i);
			Pm_PePersoInfos monPePersoInfos = Pm_PePersoInfos.chercherPm_PePersoInfos(getTransaction(),monPePerso.getCodepmpep());
			if(getTransaction().isErreur()){
				return false;
			}
			String datereal = "";
			if(monPePersoInfos.getDreal().equals("01/01/0001")){
				datereal = "";
			}else{
				datereal = monPePersoInfos.getDreal();
			}
			String ligne [] = { monPePersoInfos.getLibelleentretien(),datereal,monPePersoInfos.getDuree()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_INTERVENTIONS(aFormat.getListeFormatee());
	}else{
		setLB_INTERVENTIONS(LBVide);
	}	
	return true;
}

public boolean initialiseListPieces(javax.servlet.http.HttpServletRequest request) throws Exception{
//	 on initialise la liste des pièces sorties du stock pour l'OT
	if(getFpmCourant()!=null){
		ArrayList listPiecesInfos = PiecesFpmInfos.chercherFpmPiecesInfosFpm(getTransaction(),getFpmCourant().getNumfiche());
		if(getTransaction().isErreur()){
			return false;
		}
		setListePieces(listPiecesInfos);
	}
	montantTotalPieces = 0;
	if(getListePieces()!=null){
		if(getListePieces().size()>0){
			int [] tailles = {30,10,6,6,6};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G","C","D","D","D"};
			FormateListe aFormat = new FormateListe(tailles,padding,false);
			for (int i = 0; i < getListePieces().size() ; i++) {
				PiecesFpmInfos maPiecesInfos = (PiecesFpmInfos)getListePieces().get(i);	
				// calcul du total par type de pièces
				int total = Integer.parseInt(maPiecesInfos.getQuantite())*Integer.parseInt(maPiecesInfos.getPrix());
				String ligne [] = { maPiecesInfos.getDesignationpiece(),maPiecesInfos.getDsortie(),maPiecesInfos.getPrix(),maPiecesInfos.getQuantite(),String.valueOf(total)};
				aFormat.ajouteLigne(ligne);
				montantTotalPieces = montantTotalPieces + total;
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

public void initialiseListFre(javax.servlet.http.HttpServletRequest request) throws Exception{
	//on initialise la liste des fournisseurs intervenus sur FPM
	if(getFpmCourant().getNumfiche()!=null){
		ArrayList listPmBe = PM_BE.listerPM_BE_FPM(getTransaction(),getFpmCourant().getNumfiche());
		if(getTransaction().isErreur()){
			return;
		}
		setListePmBe(listPmBe);
	}
	
	if(getListePmBe().size()>0){
		int tailles [] = {10,10,30,10};
		String[] padding = {"G","G","G","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i=0;i<getListePmBe().size();i++){
			PM_BE unPmBe = (PM_BE)getListePmBe().get(i);
			ENGJU unEnju = ENGJU.chercherpremierENGJU(getTransaction(),unPmBe.getExerci(),unPmBe.getNoengj());
			if(getTransaction().isErreur()){
				messErreur = "Une erreur s'est produite lors du calcul du montant du bon d'engagement.";
				//getTransaction().declarerErreur("Une erreur s'est produite lors du calcul du montant du bon d'engagement.");
				getTransaction().traiterErreur() ;
				return ;
			}

			//on recherche les lignes concernant l'engagement juridique et on calcul le montant
			int montantTotal = ENGJU.montantBE(getTransaction(),unEnju);
			if(montantTotal==-1){
				messErreur = "Une erreur s'est produite lors du calcul du montant du bon d'engagement.";
				//getTransaction().declarerErreur("Une erreur s'est produite lors du calcul du montant du bon d'engagement.");
				getTransaction().traiterErreur();
				return ;
			}
			//on renseigne la lb
			String ligne [] = { unEnju.getNoengj(),unEnju.getIdetbs(),unEnju.getEnscom(),String.valueOf(montantTotal)};
			aFormat.ajouteLigne(ligne);
		}
		setLB_FRE(aFormat.getListeFormatee());
	} else {
		setLB_FRE(null);
	}
}
/*
public int montantBE(javax.servlet.http.HttpServletRequest request,ENGJU unEnju) throws Exception{
	String codeDep;
	// controle si unEnju null
	if (unEnju.getCodcoll()==null){
		getTransaction().declarerErreur("Le Bon d'engagement passé en paramètre est null.");
		return -1;
	}
	int total = 0;
	// recherche des lignes du bon d'engagement
	ArrayList listLeju = LEJU.listerLEJUBE(getTransaction(),unEnju.getExerci(),unEnju.getNoengju());
	if(getTransaction().isErreur()){
		messErreur = "Une erreur s'est produite lors du calcul du montant du bon d'engagement.";
		//getTransaction().declarerErreur("Une erreur est survenue lors du calcul du montant du bon d'engagement.");
		getTransaction().traiterErreur();
		return -1;
	}
	if(listLeju.size()>0){
		for(int i=0;i<listLeju.size();i++){
			LEJU unLeju = (LEJU)listLeju.get(i);
			codeDep = unLeju.getCddep().substring(1,5);
			if(codeDep.trim().equals(getPMatInfosCourant().getPminv().trim())||unLeju.getCddep().trim().equals(getPMatInfosCourant().getPminv().trim())){
				int montant = Integer.parseInt(unLeju.getMtlengju());
				total = total + montant;
			}
		}
	}
	return total;
}
*/
public void initialiseListIntervenants(javax.servlet.http.HttpServletRequest request) throws Exception{
	//on initialise la liste des intervenants de l'ATM
	if(getFpmCourant().getNumfiche()!=null){
		ArrayList listAgents = PM_ATM.listerPM_ATM_FPM(getTransaction(),getFpmCourant().getNumfiche());
		if(getTransaction().isErreur()){
			return;
		}
		setListeIntervenants(listAgents);
	}
	
	if(getListeIntervenants().size()>0){
		int [] tailles = {30};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i =0;i<getListeIntervenants().size();i++){
			PM_ATM unPM_ATM = (PM_ATM)getListeIntervenants().get(i);
			Agents unAgent = Agents.chercherAgents(getTransaction(),unPM_ATM.getMatricule());
			if(getTransaction().isErreur()){
				return ;
			}
			String ligne [] = { unAgent.getNom().trim()+" "+unAgent.getPrenom().trim()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_INTERVENANTS(aFormat.getListeFormatee());
	}else{
		setLB_INTERVENANTS(LBVide);
	}	
}

/**
 * Constructeur du process OeFPM_Visualisation.
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public OeFPM_Visualisation() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHER
 * Date de création : (28/07/05 14:46:02)
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
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	setStatut(STATUT_RECHERCHER,true);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DENTREE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DENTREE() {
	return "NOM_ST_DENTREE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DENTREE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DENTREE() {
	return getZone(getNOM_ST_DENTREE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DSORTIE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DSORTIE() {
	return "NOM_ST_DSORTIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DSORTIE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DSORTIE() {
	return getZone(getNOM_ST_DSORTIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NUMFICHE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NUMFICHE() {
	return "NOM_ST_NUMFICHE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NUMFICHE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NUMFICHE() {
	return getZone(getNOM_ST_NUMFICHE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_INTERVENANTS
 * Date de création : (28/07/05 14:46:02)
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
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
private void setLB_INTERVENANTS(java.lang.String[] newLB_INTERVENANTS) {
	LB_INTERVENANTS = newLB_INTERVENANTS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_INTERVENANTS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INTERVENANTS() {
	return "NOM_LB_INTERVENANTS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_INTERVENANTS_SELECT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INTERVENANTS_SELECT() {
	return "NOM_LB_INTERVENANTS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_INTERVENANTS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_INTERVENANTS() {
	return getLB_INTERVENANTS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_INTERVENANTS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_INTERVENANTS_SELECT() {
	return getZone(getNOM_LB_INTERVENANTS_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_INTERVENTIONS
 * Date de création : (28/07/05 14:46:02)
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
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
private void setLB_INTERVENTIONS(java.lang.String[] newLB_INTERVENTIONS) {
	LB_INTERVENTIONS = newLB_INTERVENTIONS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_INTERVENTIONS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INTERVENTIONS() {
	return "NOM_LB_INTERVENTIONS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_INTERVENTIONS_SELECT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INTERVENTIONS_SELECT() {
	return "NOM_LB_INTERVENTIONS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_INTERVENTIONS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_INTERVENTIONS() {
	return getLB_INTERVENTIONS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_INTERVENTIONS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_INTERVENTIONS_SELECT() {
	return getZone(getNOM_LB_INTERVENTIONS_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_PIECES
 * Date de création : (28/07/05 14:46:02)
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
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
private void setLB_PIECES(java.lang.String[] newLB_PIECES) {
	LB_PIECES = newLB_PIECES;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_PIECES
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PIECES() {
	return "NOM_LB_PIECES";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PIECES_SELECT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PIECES_SELECT() {
	return "NOM_LB_PIECES_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_PIECES
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_PIECES() {
	return getLB_PIECES();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_PIECES
 * Date de création : (28/07/05 14:46:02)
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
public FPMComplete getFpmCompleteCourant() {
	return fpmCompleteCourant;
}
public void setFpmCompleteCourant(FPMComplete fpmCompleteCourant) {
	this.fpmCompleteCourant = fpmCompleteCourant;
}
public ArrayList getListeIntervenants() {
	if(listeIntervenants==null){
		listeIntervenants=new ArrayList();
	}
	return listeIntervenants;
}
	public void setListeIntervenants(ArrayList listeIntervenants) {
		this.listeIntervenants = listeIntervenants;
	}
	public ArrayList getListeInterventions() {
		if(listeInterventions==null){
			listeInterventions=new ArrayList();
		}
		return listeInterventions;
	}
	public void setListeInterventions(ArrayList listeInterventions) {
		this.listeInterventions = listeInterventions;
	}
	public ArrayList getListePieces() {
		if(listPieces==null){
			listPieces=new ArrayList();
		}
		return listPieces;
	}
	public void setListePieces(ArrayList listPieces) {
		this.listPieces = listPieces;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMMENTAIRE
 * Date de création : (05/08/05 08:01:38)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COMMENTAIRE() {
	return "NOM_ST_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMMENTAIRE
 * Date de création : (05/08/05 08:01:38)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COMMENTAIRE() {
	return getZone(getNOM_ST_COMMENTAIRE());
}
	private java.lang.String[] LB_FRE;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_FRE
 * Date de création : (09/08/05 13:33:45)
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
 * Date de création : (09/08/05 13:33:45)
 * @author : Générateur de process
 */
private void setLB_FRE(java.lang.String[] newLB_FRE) {
	LB_FRE = newLB_FRE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_FRE
 * Date de création : (09/08/05 13:33:45)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_FRE() {
	return "NOM_LB_FRE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_FRE_SELECT
 * Date de création : (09/08/05 13:33:45)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_FRE_SELECT() {
	return "NOM_LB_FRE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_FRE
 * Date de création : (09/08/05 13:33:45)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_FRE() {
	return getLB_FRE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_FRE
 * Date de création : (09/08/05 13:33:45)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_FRE_SELECT() {
	return getZone(getNOM_LB_FRE_SELECT());
}
	public ArrayList getListePmBe() {
		if(listePmBe==null){
			listePmBe=new ArrayList();
		}
		return listePmBe;
	}
	public void setListePmBe(ArrayList listeBe) {
		this.listePmBe = listeBe;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (18/08/05 10:02:05)
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
 * Date de création : (18/08/05 10:02:05)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:58:15)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:58:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TCOMPTEUR
 * Date de création : (23/08/05 09:12:51)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TCOMPTEUR() {
	return "NOM_ST_TCOMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TCOMPTEUR
 * Date de création : (23/08/05 09:12:51)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TCOMPTEUR() {
	return getZone(getNOM_ST_TCOMPTEUR());
}
	public boolean isDebranche() {
		return isDebranche;
	}
	public void setDebranche(boolean isDebranche) {
		this.isDebranche = isDebranche;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COUT_TOTAL
 * Date de création : (31/01/06 13:31:13)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COUT_TOTAL() {
	return "NOM_ST_COUT_TOTAL";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COUT_TOTAL
 * Date de création : (31/01/06 13:31:13)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COUT_TOTAL() {
	return getZone(getNOM_ST_COUT_TOTAL());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_FPM
 * Date de création : (03/04/07 09:55:38)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_FPM() {
	return "NOM_PB_FPM";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (03/04/07 09:55:38)
 * @author : Générateur de process
 */
public boolean performPB_FPM(javax.servlet.http.HttpServletRequest request) throws Exception {
	String recherche;
	if (Services.estNumerique(getZone(getNOM_EF_NUMFICHE()))){
		recherche = getZone(getNOM_EF_NUMFICHE());
	}else{
		getTransaction().declarerErreur("Le numéro da la fiche d'entretien est numérique.");
		return false;
	}
	
	FPM unFPM = FPM.chercherFPM(getTransaction(),recherche);
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("La fiche d'entretien recherchée n'a pas été trouvé.");
		return false;
	}
	setFpmCourant(unFPM);
	setPMatInfosCourant(new PMatInfos());
	if (unFPM!=null){
		PMatInfos unPMatInfos= PMatInfos.chercherPMatInfos(getTransaction(),unFPM.getPminv());
		if(getTransaction().isErreur()){
			return false;
		}
		if(null==unPMatInfos){
			unPMatInfos = new PMatInfos();
		}
		setPMatInfosCourant(unPMatInfos);
	}else{
		setFpmCourant(new FPM());
	}
	// on renseigne la liste des BPC
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_OT
 * Date de création : (03/04/07 09:55:38)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_NUMFICHE() {
	return "NOM_EF_NUMFICHE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_OT
 * Date de création : (03/04/07 09:55:38)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_NUMFICHE() {
	return getZone(getNOM_EF_NUMFICHE());
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
	return getNOM_EF_NUMFICHE();
}

/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_IMPRIMER
		if (testerParametre(request, getNOM_PB_IMPRIMER())) {
			return performPB_IMPRIMER(request);
		}

		//Si clic sur le bouton PB_FPM
		if (testerParametre(request, getNOM_PB_FPM())) {
			return performPB_FPM(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_RECHERCHER
		if (testerParametre(request, getNOM_PB_RECHERCHER())) {
			return performPB_RECHERCHER(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (08/08/07 14:29:01)
 * @author : Générateur de process
 */
public String getJSP() {
	return "OeFPM_Visualisation.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_IMPRIMER
 * Date de création : (08/08/07 14:29:01)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_IMPRIMER() {
	return "NOM_PB_IMPRIMER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (08/08/07 14:29:01)
 * @author : Générateur de process
 */
public boolean performPB_IMPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int montantPieces = 0;
	String infosBe = "";
	int inter1;
	int inter2;
	String stService = "";
	
	// impression de l'OT
	if(getFpmCourant()!=null){
		String commentaireOt = "";
		String inter = "";
		String numinv = getZone(getNOM_ST_NOINVENT());
		String numimmat = getZone(getNOM_ST_NOIMMAT());
		String nomequip = getZone(getNOM_ST_MARQUE())+" "+getZone(getNOM_ST_MODELE());
		String type = getZone(getNOM_ST_TYPE());
		String service = getZone(getNOM_ST_SERVICE());
		String numFiche = getZone(getNOM_ST_NUMFICHE());
		String montantTotal = getZone(getNOM_ST_COUT_TOTAL());
		String dEntree = getZone(getNOM_ST_DENTREE());
		String dSortie = getZone(getNOM_ST_DSORTIE());
		String compteur = getZone(getNOM_ST_COMPTEUR());
		// pour les commentaires avec retour à la ligne par la touche "Entrée"
		String commentaire = getZone(getNOM_ST_COMMENTAIRE());
		int comlen = commentaire.length();
		int indice ;
		//commentaire = commentaire.replace('\n',' ');
		indice = 0;
		for (int i=0;i<comlen;i++){
			if(commentaire.charAt(i)=='\n'){
				commentaireOt = commentaireOt+commentaire.substring(indice,i)+"$";
				indice = i;
			}
		}
		commentaireOt = commentaireOt + commentaire.substring(indice,comlen);
		commentaireOt = commentaireOt.replace('\n',' ');
		commentaireOt = commentaireOt.replace('\r',' ');
		StarjetGeneration g = new StarjetGeneration(getTransaction(), "MAIRIE", starjetMode, "SEAT", "ficheFpm.sp", "ficheFpm");
		File f = g.getFileData();
		FileWriter fw = new FileWriter(f);
		PrintWriter pw = new PrintWriter(fw);
		try {	
			//	Entete
			pw.print("1");
			pw.print(Services.lpad(numinv,32," "));
			pw.print(Services.lpad(numimmat,50," "));
			pw.print(Services.lpad(nomequip,64," "));
			pw.print(Services.lpad(type,32," "));
			stService = service.trim();
			if(stService.length()>64){
				stService = stService.substring(0,64);
				pw.print(stService);
			}else{
				pw.print(Services.lpad(service,64," "));
			}
			// OT
			pw.print(Services.lpad(numFiche,8," "));
			pw.print(Services.lpad(dEntree,10," "));
			pw.print(Services.lpad(dSortie,10," "));
			pw.print(Services.lpad(compteur,10," "));
			pw.print(Services.lpad(montantTotal,10," "));
			pw.print(Services.lpad(commentaireOt,600," "));
			pw.println();
			
//			 liste des interventions
			for (int i=0;i<getListeInterventions().size();i++){
				if(!getListeInterventions().get(i).equals("")){
					PM_PePerso monPeP = (PM_PePerso)getListeInterventions().get(i);
					Pm_PePersoInfos unPeP = Pm_PePersoInfos.chercherPm_PePersoInfos(getTransaction(),monPeP.getCodepmpep());
					if(getTransaction().isErreur()){
						return false;
					}
					pw.print("2");
					pw.print(Services.lpad(unPeP.getLibelleentretien(),31," "));
					pw.print(Services.lpad(unPeP.getDreal(),11," "));
					pw.print(Services.lpad(unPeP.getDuree(),6," "));
					pw.println();
				}
			}
			// liste des pièces
			for (int i=0;i<getListePieces().size();i++){
				if(!getListePieces().get(i).equals("")){
					PiecesFpmInfos unPI = (PiecesFpmInfos)getListePieces().get(i);
					pw.print("3");
					pw.print(Services.lpad(unPI.getDesignationpiece(),3," "));
					pw.print(Services.lpad(unPI.getDsortie(),10," "));
					pw.print(Services.lpad(unPI.getPrix(),7," "));
					pw.print(Services.lpad(unPI.getQuantite(),7," "));
					montantPieces = Integer.parseInt(unPI.getPrix())*Integer.parseInt(unPI.getQuantite());
					pw.print(Services.lpad(String.valueOf(montantPieces),7," "));
					pw.print(getListePieces().get(i));
					pw.println();
				}
			}
			
			// liste des bons d'engagement
			for(int i=0;i<getListePmBe().size();i++){
				if(!getListePmBe().get(i).equals("")){
					PM_BE unBe = (PM_BE)getListePmBe().get(i);
					infosBe = ListeBe(request,unBe);
					
					pw.print("4");
					pw.print(Services.lpad(unBe.getNoengj(),11," "));
					//System.out.println("infosBE="+infosBe);
					if(!infosBe.equals("")){
						inter1 = infosBe.indexOf(";");
						inter2 = inter1 + 1;
						pw.print(Services.lpad(infosBe.substring(0,inter1),11," "));
						inter1 = infosBe.indexOf(";",inter2);
						//inter1 = inter2+1;
						pw.print(Services.lpad(infosBe.substring(inter2,inter1),31," "));
						//inter2 = infosBe.indexOf(";",inter1);
						inter1=inter1+1;
						inter2 = infosBe.length();
						pw.print(Services.lpad(infosBe.substring(inter1,inter2),11," "));
					}else{
						pw.print(Services.lpad(" ",42," "));
						pw.print(Services.lpad("0",11," "));
					}
					pw.print(getListePmBe().get(i));
					pw.println();
				}
			}
			
			// liste des intervenants
			for(int i=0;i<getListeIntervenants().size();i++){
				if(!getListeIntervenants().get(i).equals("")){
					PM_ATM unPM_ATM = (PM_ATM)getListeIntervenants().get(i);
					Agents unAgent = Agents.chercherAgents(getTransaction(),unPM_ATM.getMatricule());
					if(getTransaction().isErreur()){
						return false;
					}
					pw.print("5");
					pw.print(Services.lpad(unAgent.getNom().trim()+" "+unAgent.getPrenom(),31," "));
					pw.println();
				}
			}
					
			pw.close();
			fw.close();
		} catch (Exception e) {
			pw.close();
			fw.close();
			throw e;
		}
			setScript(g.getScriptOuverture());		
	}
	return true;
}
public String ListeBe(javax.servlet.http.HttpServletRequest request,PM_BE unBe) throws Exception {
	String be = "";
	ENGJU unEnju = ENGJU.chercherpremierENGJU(getTransaction(),unBe.getExerci(),unBe.getNoengj());
	if(getTransaction().isErreur()){
		messErreur = "Une erreur s'est produite lors du calcul du montant du bon d'engagement.";
		//getTransaction().declarerErreur("Une erreur s'est produite lors du calcul du montant du bon d'engagement.");
		getTransaction().traiterErreur() ;
		return "";
	}

	//on recherche les lignes concernant l'engagement juridique et on calcul le montant
	int montantTotal = ENGJU.montantBE(getTransaction(),unEnju);
	if(montantTotal==-1){
		messErreur = "Une erreur s'est produite lors du calcul du montant du bon d'engagement.";
		//getTransaction().declarerErreur("Une erreur s'est produite lors du calcul du montant du bon d'engagement.");
		getTransaction().traiterErreur();
		return "";
	}
	be = unEnju.getIdetbs()+";"+unEnju.getEnscom()+";"+montantTotal;
	return be;
}
public String getScript() {
	if (script == null) script="";
	return script;
}
public void setScript(String script) {
	this.script = script;
}
public String afficheScript() {	
	String res = new String(getScript());
	setScript(null);
	return res;
}
}
