package nc.mairie.seat.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.FileObject;

import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.BPC;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
import nc.mairie.seat.servlet.ServletSeat;
import nc.mairie.servlets.Frontale;

/**
 * Process OeBPC_VisualisationEquip
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
*/
public class OePMBPC_VisualisationEquip extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_DETAILS = 2;
	public static final int STATUT_RECHERCHER = 1;
	private java.lang.String[] LB_BPC;
	private ArrayList listeBPC;
	private PMatInfos pMatInfosCourant;
	private String totalQte ;
	private String nbBPC;
	private String kmParcouru;
	private String moyenne;
	private boolean first=true;
	public boolean listeVide=true;
	public int quantiteTotal = 0;
	public int kmParcourusTotal = 0;
	private String starjetMode = (String)Frontale.getMesParametres().get("STARJET_MODE");
	private String starjetServer = (String)Frontale.getMesParametres().get("STARJET_SERVER");
	private String script;
	public boolean isVide = true;
	public String codeService ;
	public String agentResponsable;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	//if(etatStatut()==STATUT_RECHERCHER){
		PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
		if(unPMatInfos!=null){
			setPMatInfosCourant(unPMatInfos);
		}
	//}
	//quand appui sur entree
	if(!getZone(getNOM_EF_RECHERCHE_EQUIP()).equals("")){
		performPB_RECHERCHE_EQUIP(request);
	}
	if(getPMatInfosCourant()!=null){
		if(getPMatInfosCourant().getPminv()!=null){
			//	initialisation des zones
			addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
			addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
			addZone(getNOM_ST_NOIMMAT(),getPMatInfosCourant().getPmserie());
			addZone(getNOM_ST_MODELE(),getPMatInfosCourant().getDesignationmodele());
			addZone(getNOM_ST_MARQUE(),getPMatInfosCourant().getDesignationmarque());
			//initialisation des listes
			initialiseListeBPC(request);
			initialiseListeTotal(request);
		}
//		recherche du service concerné à la date du BPC en visu
		String date = Services.dateDuJour();
		PM_Affectation_Sce_Infos unAffectationServiceInfos = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),date);
		if(getTransaction().isErreur()){
			//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
			getTransaction().traiterErreur();
			addZone(getNOM_ST_SERVICE(),"pas affecté");
		}else{
			Service unService = Service.chercherService(getTransaction(),unAffectationServiceInfos.getSiserv());
			if(getTransaction().isErreur()){
				return ;
			}else{
				addZone(getNOM_ST_SERVICE(),unService.getLiserv());
			}
			codeService = unAffectationServiceInfos.getSiserv();
			agentResponsable = unAffectationServiceInfos.getNomatr();
		}
	}
	setFirst(false);
}

/*
 * initialisation de la liste des BPC et calcul des infos
 *  date : 05/08/05
 * @autheur : Coralie NICOLAS
 */
public void initialiseListeBPC(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList liste = BPC.listerBPCEquipement(getTransaction(),getPMatInfosCourant().getPminv());
	if(getTransaction().isErreur()){
		return;
	}
	if(liste.size()>0){
		setListeBPC(liste);
		trier(liste);
		setListeVide(false);
		setVide(false);
	}else{
		setListeVide(true);
		setVide(true);
	}
}

public void initialiseListeTotal(javax.servlet.http.HttpServletRequest request) throws Exception{
	NumberFormat moyenneTotalFormat = new DecimalFormat("0.00");
	BPC unBPC = (BPC)getListeBPC().get(0);
	quantiteTotal = quantiteTotal - Integer.parseInt(unBPC.getQuantite());
	double MoyenneTotal = (double)quantiteTotal/(double)kmParcourusTotal*100;
	if(getListeBPC()!=null){
		if(getListeBPC().size()>0){
			int tailles [] = {10,10,10,6,10,10};
			String[] padding = {"D","C","D","D","D","D"};
			FormateListe aFormat = new FormateListe(tailles,padding,false);
			String ligne [] = { nbBPC,"","",totalQte,kmParcouru,String.valueOf(moyenneTotalFormat.format(MoyenneTotal))};
			aFormat.ajouteLigne(ligne);
			setLB_TOTAL(aFormat.getListeFormatee());
		} else {
			setLB_TOTAL(null);
		}
	}
	kmParcourusTotal = 0;
	kmParcouru = "";
	quantiteTotal = 0;
	MoyenneTotal = 0;
}

public void trier(ArrayList a) throws Exception{
	String[] colonnes = {"date","valeurcompteur"};
	//ordre croissant
	boolean[] ordres = {false,false};
	//int quantiteTotal = 0;
	//int kmParcouruTotal =0;
	
//	Si au moins un bpc
	if (a.size() !=0 ) {
		ArrayList aTrier = Services.trier(a,colonnes,ordres);
		setListeBPC(aTrier);
		int tailles [] = {10,10,10,6,10,10};
		String[] padding = {"D","C","D","D","D","D"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		int indice = a.size();
		/*if (a.size()>12){
			indice = a.size()-11;
		}*/
		int recup = 1;
				
		for (int i = 0; i < indice ; i++) {
			BPC aBPC = (BPC)aTrier.get(i);
			BPC bpcAvant = null;
			try {
				bpcAvant = (BPC) aTrier.get(i+1);	
			} catch (Exception e) {
				bpcAvant = null;
			}
			
			//setBpcCourant(aBPC);
			// Calcul des km parcourus et moyenne au 100
			int kmParcourus =0 ;
			double moyennecalcul = 0;
			String moyenneL = "";
			// calcul des totaux
			quantiteTotal = quantiteTotal + Integer.parseInt(aBPC.getQuantite()); 
			//kmParcourusTotal = kmParcourusTotal + Integer.parseInt(aBPC.get)
			
			if (null != bpcAvant){
				kmParcourus =  Integer.parseInt(aBPC.getValeurcompteur())-Integer.parseInt(bpcAvant.getValeurcompteur());
				int qteAvant = Integer.parseInt(bpcAvant.getQuantite());
				int qte = Integer.parseInt(aBPC.getQuantite());
				//moyennecalcul = (double)qteAvant/(double)kmParcourus*100;
				//moyennecalcul = (double)qte/(double)kmParcourus*100;
				moyennecalcul = (double)qte/(double)kmParcourus;
				if (("KILOMETRIQUE").equals(getPMatInfosCourant().getDesignationcompteur())){
					moyennecalcul = moyennecalcul*100;
				}
				NumberFormat moyenneFormat = new DecimalFormat("0.00");
				moyenneL = moyenneFormat.format(moyennecalcul);
			}
			String ligne [] = { aBPC.getNumerobpc(),aBPC.getDate(),aBPC.getValeurcompteur(),aBPC.getQuantite(),String.valueOf(kmParcourus),moyenneL};
			aFormat.ajouteLigne(ligne);
			kmParcourusTotal = kmParcourusTotal + kmParcourus;//Integer.parseInt(aBPC.getValeurcompteur());
		}
		// calcul pour les totaux
		NumberFormat moyenneTotalFormat = new DecimalFormat("0.00");
		double moyenneTotal = (double)quantiteTotal/(double)kmParcourusTotal*100;
		nbBPC = String.valueOf(getListeBPC().size());
		kmParcouru = String.valueOf(kmParcourusTotal);
		totalQte = String.valueOf(quantiteTotal);
		moyenne = moyenneTotalFormat.format(moyenneTotal);
		
		setLB_BPC(aFormat.getListeFormatee());
	} else {
		setLB_BPC(null);
	}
	
	return ;
}
/**
 * Constructeur du process OeBPC_VisualisationEquip.
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public OePMBPC_VisualisationEquip() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHER
 * Date de création : (05/08/05 08:43:16)
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
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHER(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"MODE","BPC_VISU_EQUIP");
	setStatut(STATUT_RECHERCHER,true);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_BPC
 * Date de création : (05/08/05 08:43:16)
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
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
private void setLB_BPC(java.lang.String[] newLB_BPC) {
	LB_BPC = newLB_BPC;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_BPC
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_BPC() {
	return "NOM_LB_BPC";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_BPC_SELECT
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_BPC_SELECT() {
	return "NOM_LB_BPC_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_BPC
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_BPC() {
	return getLB_BPC();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_BPC
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_BPC_SELECT() {
	return getZone(getNOM_LB_BPC_SELECT());
}
	public PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
	}
	public ArrayList getListeBPC() {
		return listeBPC;
	}
	public void setListeBPC(ArrayList listeBPC) {
		this.listeBPC = listeBPC;
	}
	private java.lang.String[] LB_TOTAL;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_TOTAL
 * Date de création : (05/08/05 09:12:48)
 * @author : Générateur de process
 */
private String [] getLB_TOTAL() {
	if (LB_TOTAL == null)
		LB_TOTAL = initialiseLazyLB();
	return LB_TOTAL;
}
/**
 * Setter de la liste:
 * LB_TOTAL
 * Date de création : (05/08/05 09:12:48)
 * @author : Générateur de process
 */
private void setLB_TOTAL(java.lang.String[] newLB_TOTAL) {
	LB_TOTAL = newLB_TOTAL;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_TOTAL
 * Date de création : (05/08/05 09:12:48)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_TOTAL() {
	return "NOM_LB_TOTAL";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_TOTAL_SELECT
 * Date de création : (05/08/05 09:12:48)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_TOTAL_SELECT() {
	return "NOM_LB_TOTAL_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_TOTAL
 * Date de création : (05/08/05 09:12:48)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_TOTAL() {
	return getLB_TOTAL();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_TOTAL
 * Date de création : (05/08/05 09:12:48)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_TOTAL_SELECT() {
	return getZone(getNOM_LB_TOTAL_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE_EQUIP
 * Date de création : (24/08/05 15:17:55)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECHERCHE_EQUIP() {
	return "NOM_PB_RECHERCHE_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (24/08/05 15:17:55)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHE_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on vide les zones
	addZone(getNOM_ST_TYPE(),"");
	addZone(getNOM_ST_NOINVENT(),"");
	addZone(getNOM_ST_NOIMMAT(),"");
	addZone(getNOM_ST_MODELE(),"");
	addZone(getNOM_ST_MARQUE(),"");
	setLB_BPC(LBVide);
	setLB_TOTAL(LBVide);
	setListeBPC(new ArrayList());
	setPMatInfosCourant(new PMatInfos());
		
	//	recherche de l'équipement voulu
	String recherche = getZone(getNOM_EF_RECHERCHE_EQUIP()).toUpperCase();
	PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),recherche);
	if(unPMatInfos==null){
		if(unPMatInfos.getPminv()==null){
			getTransaction().declarerErreur("Le petit matériel recherché n'a pas été trouvé.");
			return false;
		}
	}
	if(getTransaction().isErreur()){
		return false;
	}
	if(unPMatInfos==null){
		unPMatInfos = new PMatInfos();
	}
	setPMatInfosCourant(unPMatInfos);
	/*Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
	if(getTransaction().isErreur()){
		return false;
	}
	setEquipementCourant(unEquipement);*/
	//pour garder l'équipement 
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE_EQUIP
 * Date de création : (24/08/05 15:17:55)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_RECHERCHE_EQUIP() {
	return "NOM_EF_RECHERCHE_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE_EQUIP
 * Date de création : (24/08/05 15:17:55)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_RECHERCHE_EQUIP() {
	return getZone(getNOM_EF_RECHERCHE_EQUIP());
}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_DETAILS
 * Date de création : (29/08/05 09:04:24)
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
 * Date de création : (29/08/05 09:04:24)
 * @author : Générateur de process
 */
public boolean performPB_DETAILS(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice = (Services.estNumerique(getVAL_LB_BPC_SELECT()) ? Integer.parseInt(getVAL_LB_BPC_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	if(null==getPMatInfosCourant()){
		setPMatInfosCourant(new PMatInfos());
	}
	BPC unBPC = (BPC)getListeBPC().get(indice);
	VariableGlobale.ajouter(request,"BPC",unBPC);
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	setStatut(STATUT_DETAILS,true);
	return true;
}
	public boolean isListeVide() {
		return listeVide;
	}
	public void setListeVide(boolean listeVide) {
		this.listeVide = listeVide;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (24/10/05 11:05:39)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (24/10/05 11:05:39)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_IMPRIMER
		if (testerParametre(request, getNOM_PB_IMPRIMER())) {
			return performPB_IMPRIMER(request);
		}

		//Si clic sur le bouton PB_DETAILS
		if (testerParametre(request, getNOM_PB_DETAILS())) {
			return performPB_DETAILS(request);
		}

		//Si clic sur le bouton PB_RECHERCHE_EQUIP
		if (testerParametre(request, getNOM_PB_RECHERCHE_EQUIP())) {
			return performPB_RECHERCHE_EQUIP(request);
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
 * Date de création : (07/06/07 08:43:13)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeBPC_VisualisationEquip.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_IMPRIMER
 * Date de création : (07/06/07 08:43:13)
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
 * Date de création : (07/06/07 08:43:13)
 * @author : Générateur de process
 */
/*construction du fichier d'impression : 
 *1er caractère : 1 pour le BPC 2 pour le total de l'équipement
 *  numéro d'invenaire sur 10
 * numéro d'immatriculation sur 10
 * nom d'équipement sur 64
 * type sur 32
 * service sur 60
 * début de période sur 10
 * fin de période sur 10
 * numéro de BPC sur 10
 * date sur 10
 * compteur sur 10
 * quantité sur 6
 * km parcouru sur 10
 * moyenne sur 10
 * pour le total
 * 
 */
public boolean performPB_IMPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	String numinv = getZone(getNOM_ST_NOINVENT());
	String numimmat = getZone(getNOM_ST_NOIMMAT());
	String nomequip = getZone(getNOM_ST_MARQUE())+" "+getZone(getNOM_ST_MODELE());
	String type = getZone(getNOM_ST_TYPE());
	String service = getZone(getNOM_ST_SERVICE());
	String ddebut = getPMatInfosCourant().getDmes();
	String dfin = Services.dateDuJour();
	String nomAgent;
	String stService = "";
	
	// recherche de l'agent responsable
	if(!agentResponsable.equals("0")){
		if(codeService.equals("EAAA")){
			AgentCDE unAgentCde = AgentCDE.chercherAgentCDE(getTransaction(),agentResponsable);
			if(getTransaction().isErreur()){
				return false;
			}
			nomAgent = unAgentCde.nom.trim()+" "+unAgentCde.prenom.trim();
		}else if(codeService.equals("5000")){
			AgentCCAS unAgentCcas = AgentCCAS.chercherAgentCCAS(getTransaction(),agentResponsable);
			if(getTransaction().isErreur()){
				return false;
			}
			nomAgent = unAgentCcas.nom.trim()+" "+unAgentCcas.prenom.trim();
		}else {
			Agents unAgent = Agents.chercherAgents(getTransaction(),agentResponsable);
			if(getTransaction().isErreur()){
				return false;
			}
			nomAgent = unAgent.nom.trim()+" "+unAgent.prenom.trim();
		}
	}else{
		nomAgent = "sans";
	}
	
	if (getLB_BPC().length>0){
		StarjetGeneration g = new StarjetGeneration(getTransaction(), "MAIRIE", starjetMode, "SEAT", "listeBPCEquip.sp", "listeBPCEquip");
		g.setStarjetServer(starjetServer);
		FileObject f = g.getFileObjectData();
		OutputStream output = f.getContent().getOutputStream();
		OutputStreamWriter ouw = new OutputStreamWriter(output, "UTF8");
		BufferedWriter out = new BufferedWriter(ouw);
		try {	
			for (int i=1;i<getLB_BPC().length;i++){
				
				//Entete
				out.write("1");
				out.write(StringUtils.leftPad(numinv,10," "));
				out.write(StringUtils.leftPad(numimmat,10," "));
				out.write(StringUtils.leftPad(nomequip,64," "));
				out.write(StringUtils.leftPad(type,32," "));
				stService = codeService+" "+service.trim();
				if(stService.length()>64){
					stService = stService.substring(0,64);
					out.write(stService);
				}else{
					out.write(StringUtils.leftPad(codeService+" "+service,64," "));
				}
				out.write(StringUtils.leftPad(Services.formateDate(ddebut),10," "));
				out.write(StringUtils.leftPad(Services.formateDate(dfin),10," "));
				out.write(StringUtils.leftPad(nomAgent,50," "));
				//BPC
				out.write(getLB_BPC()[i]);
				out.write("\n");
				
				
			}
//			Total
			out.write("2");
			out.write(StringUtils.leftPad(numinv,10," "));
			out.write(getLB_TOTAL()[0]);
			out.write("\n");
			
			out.flush();
			out.close();
			ouw.close();
			output.close();
			f.close();
		} catch (Exception e) {
			out.flush();
			out.close();
			ouw.close();
			output.close();
			f.close();
			throw e;
		}
		
		setScript(g.getScriptOuverture());
		
	}
	return true;
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
public boolean isVide() {
	return isVide;
}
public void setVide(boolean isVide) {
	this.isVide = isVide;
}
}
