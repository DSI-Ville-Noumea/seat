package nc.mairie.seat.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.commons.vfs.FileObject;

import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.OT;
import nc.mairie.seat.metier.Planning;
import nc.mairie.servlets.Frontale;
import nc.mairie.technique.FormateListe;
import nc.mairie.technique.Services;
import nc.mairie.technique.StarjetGenerationVFS;
import nc.mairie.technique.VariableGlobale;
/**
 * Process OeOT_Impression
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
*/
public class OeOT_Impression extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6858884383594046956L;
	public static final int STATUT_RECHERCHE = 1;
	private java.lang.String[] LB_ENTRETIENS;
	private OT otCourant;
	private EquipementInfos equipementInfosCourant;
	private ArrayList<Planning> listEntretien;
	public int tailleList = 0;
	public int tailleList_suite = 0;
	public boolean affiche;
	private String focus =  null;
	private String starjetMode = (String)Frontale.getMesParametres().get("STARJET_MODE");
	private String script;

/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	//récupération de l'OT à imprimer
	OT monOT = (OT)VariableGlobale.recuperer(request, "OT");
	if (monOT!=null){
		if(monOT.getNumeroot()!=null){
			//	on rafraichit l'OT
			monOT = OT.chercherOT(getTransaction(),monOT.getNumeroot());
			if(getTransaction().isErreur()){
				return ;
			}
			setOtCourant(monOT);
		}
	}
//	 quand appui sur entrée
	if(!("").equals(getZone(getNOM_EF_EQUIP()))){
		performPB_EQUIP(request);
		addZone(getZone(getNOM_EF_EQUIP()),"");
	}
	//EquipementInfos monEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),getOtCourant().getNuminv());
	EquipementInfos monEquipementInfos = new EquipementInfos();
	if(monOT!=null){
		if(monOT.getNuminv()!=null){
			monEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),monOT.getNuminv());
		}else{
			monEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
		}
	}else{
		monEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
	}
	if(getTransaction().isErreur()){
		return;
	}
	setEquipementInfosCourant(monEquipementInfos);
	/*EquipementInfos monEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
	if(monEquipementInfos!=null){
		setEquipementInfosCourant(monEquipementInfos);
	}*/
	
	//initialisation des zones
	if(getOtCourant()!=null){
		// infos concernant l'équipement
		if(getEquipementInfosCourant()!=null){
			addZone(getNOM_ST_MARQUE(),getEquipementInfosCourant().getDesignationmarque());
			addZone(getNOM_ST_MODELE(),getEquipementInfosCourant().getDesignationmodele());
			addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
			addZone(getNOM_ST_NOINVENT(),getEquipementInfosCourant().getNumeroinventaire());
			addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
			addZone(getNOM_ST_TCOMPTEUR(),getEquipementInfosCourant().getDesignationcompteur());
			//	infos concernant l'Ot
			addZone(getNOM_ST_NOOT(),getOtCourant().getNumeroot());
			addZone(getNOM_EF_COMMENTAIRE(),getOtCourant().getCommentaire());
			addZone(getNOM_ST_COMPTEUR(),getOtCourant().getCompteur());
			if(getOtCourant().getDateentree().equals("01/01/0001")){
				addZone(getNOM_ST_DENTREE(),"");
			}else{
				addZone(getNOM_ST_DENTREE(),getOtCourant().getDateentree());
			}
			if(getOtCourant().getDatesortie().equals("01/01/0001")){
				addZone(getNOM_ST_DSORTIE(),"");
			}else{
				addZone(getNOM_ST_DSORTIE(),getOtCourant().getDatesortie());
			}
			ArrayList<Planning> a = Planning.chercherPlanningOt(getTransaction(),getOtCourant().getNumeroot());
			if (getTransaction().isErreur()){
				return;
			}
			
			// pour chaque entretien trouvé on l'ajoute dans la liste
			initialiseListEntretiens(request,a);
//			 taille de liste
			if(getListEntretien().size()!=0){
				setTailleList(getLB_ENTRETIENS().length);
			}else{
				setTailleList(0);
			}
		}
	}else{
		if(getEquipementInfosCourant()!=null){
			addZone(getNOM_ST_MARQUE(),getEquipementInfosCourant().getDesignationmarque());
			addZone(getNOM_ST_MODELE(),getEquipementInfosCourant().getDesignationmodele());
			addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
			addZone(getNOM_ST_NOINVENT(),getEquipementInfosCourant().getNumeroinventaire());
			addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
			addZone(getNOM_ST_TCOMPTEUR(),getEquipementInfosCourant().getDesignationcompteur());
		}
		setLB_ENTRETIENS(LBVide);
	}
	
	// taille de liste_suite
	if(getLB_ENTRETIENS_SUITE()!=null){
		setTailleList_suite(getLB_ENTRETIENS_SUITE().length);
	}else{
		setTailleList_suite(0);
	}
	

}

public void initialiseListEntretiens(javax.servlet.http.HttpServletRequest request,ArrayList<Planning> a) throws Exception{
	int indice = 0;
	int i = 0;
	setListEntretien(a);
//	Si au moins un entretien à faire
	if (a.size() !=0 ) {
		int tailles [] = {50};
		FormateListe aFormat = new FormateListe(tailles);
		FormateListe aFormat_suite = new FormateListe(tailles);
		for (ListIterator<Planning> list = a.listIterator(); list.hasNext(); ) {
			Planning unPlanning = (Planning)list.next();
			String ligne [] = { unPlanning.getLibelleentretien()};
			if(a.size()>1){
				i = i+1;
				if(i==indice){
					aFormat_suite.ajouteLigne(ligne);
					indice = indice+2;
				}else{
					aFormat.ajouteLigne(ligne);
				}
				if(indice==0){
					indice=indice+2;
					//indice += 2
				}
			setLB_ENTRETIENS_SUITE(aFormat_suite.getListeFormatee());
			setLB_ENTRETIENS(aFormat.getListeFormatee());
			setAffiche(true);
			}else{
				aFormat.ajouteLigne(ligne);
				setLB_ENTRETIENS(aFormat.getListeFormatee());
				setLB_ENTRETIENS_SUITE(LBVide);
				setAffiche(false);
			}
		}			
	} else {
		setLB_ENTRETIENS(LBVide);
		setLB_ENTRETIENS_SUITE(LBVide);
	}
}

/**
 * Constructeur du process OeOT_Impression.
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public OeOT_Impression() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_IMPRIMER
 * Date de création : (25/07/05 08:30:59)
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
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public boolean performPB_IMPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	Equipement unEquipement;
	//on enregistre le commentaire s'il y en a un
	String newCommentaire = getZone(getNOM_EF_COMMENTAIRE()).toUpperCase();
	if (!newCommentaire.equals("")){
		if(newCommentaire.length()>600){
			getTransaction().declarerErreur("Le commentaire est trop long.");
			return false;
		}
		// si le commentaire a été modifié
		if(!newCommentaire.equalsIgnoreCase(getOtCourant().getCommentaire())){
			getOtCourant().setCommentaire(newCommentaire);
		
			// recherche de l'équipement a passé en paramètre
			if(getOtCourant().getNuminv()==null){
				unEquipement = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
			}else{
				unEquipement = Equipement.chercherEquipement(getTransaction(),getOtCourant().getNuminv());
			}
			if(getTransaction().isErreur()){
				return false;
			}
			getOtCourant().modifierOT(getTransaction(),unEquipement);
			if(getTransaction().isErreur()){
				return false;
			}
		}
	}
	//enregistrement 
	commitTransaction();	

//	int montantPieces = 0;
//	String chainedeb="";
	
	// impression de l'OT
	if(getOtCourant()!=null){
		String commentaireOt = "";
		String numinv = getZone(getNOM_ST_NOINVENT());
		String numimmat = getZone(getNOM_ST_NOIMMAT());
		String nomequip = getZone(getNOM_ST_MARQUE())+" "+getZone(getNOM_ST_MODELE());
		String type = getZone(getNOM_ST_TYPE());
		String numOt = getZone(getNOM_ST_NOOT());
		String dEntree = getZone(getNOM_ST_DENTREE());
		String dSortie = getZone(getNOM_ST_DSORTIE());
		String compteur = getZone(getNOM_ST_COMPTEUR());
		// pour les commentaires avec retour à la ligne par la touche "Entrée"
		String commentaire = getZone(getNOM_EF_COMMENTAIRE());
		int comlen = commentaire.length();
		int indice ;
		//commentaire = commentaire.replace('\n',' ');
		indice = 0;
		
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<comlen;i++){
			if(commentaire.charAt(i)=='\n'){
				sb.append(commentaire.substring(indice,i)+"$");
			}
		}
		commentaireOt = sb.toString();
		
//		for (int i=0;i<comlen;i++){
//			if(commentaire.charAt(i)=='\n'){
//				commentaireOt = commentaireOt+commentaire.substring(indice,i)+"$";
//				indice = i;
//			}
//		}
		commentaireOt = commentaireOt + commentaire.substring(indice,comlen);
		commentaireOt = commentaireOt.replace('\n',' ');
		commentaireOt = commentaireOt.replace('\r',' ');
		StarjetGenerationVFS g = new StarjetGenerationVFS(getTransaction(), "ficheOT_Vierge.sp", "ficheOT_Vierge");
		FileObject f = g.getFileData();
		//FileWriter fw = new FileWriter(f);
		OutputStream output = f.getContent().getOutputStream();
		OutputStreamWriter fw = new OutputStreamWriter(output,"iso-8859-1");
		PrintWriter pw = new PrintWriter(fw);
		try {	
			//	Entete
			pw.print("1");
			pw.print(Services.lpad(numinv,32," "));
			pw.print(Services.lpad(numimmat,50," "));
			pw.print(Services.lpad(nomequip,64," "));
			pw.print(Services.lpad(type,32," "));
			// OT
			pw.print(Services.lpad(numOt,8," "));
			pw.print(Services.lpad(dEntree,10," "));
			pw.print(Services.lpad(dSortie,10," "));
			pw.print(Services.lpad(compteur,10," "));
			pw.print(Services.lpad(commentaireOt,600," "));
			pw.println();
			
//			 liste des interventions
			for (int i=0;i<getListEntretien().size();i++){
				if(!getListEntretien().get(i).equals("")){
					Planning unplan = (Planning)getListEntretien().get(i);
					pw.print("2");
					pw.print(Services.lpad(unplan.getLibelleentretien(),31," "));
					pw.print(Services.lpad(unplan.getDatereal(),11," "));
					pw.print(Services.lpad(unplan.getDuree(),6," "));
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

public String getServiceForEquipement(Equipement equipement, String DateEntree){
	String sservice="";
	try{
		AffectationServiceInfos unASI = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),Services.dateDuJour());
		sservice=unASI.getCodeservice();
	}catch (Exception e){
		if (getTransaction().isErreur()){
			getTransaction().traiterErreur();
		}
	}
	
	return sservice;
	
}

/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DENTREE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DENTREE() {
	return "NOM_ST_DENTREE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DENTREE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DENTREE() {
	return getZone(getNOM_ST_DENTREE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DSORTIE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DSORTIE() {
	return "NOM_ST_DSORTIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DSORTIE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DSORTIE() {
	return getZone(getNOM_ST_DSORTIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOOT
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOOT() {
	return "NOM_ST_NOOT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOOT
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOOT() {
	return getZone(getNOM_ST_NOOT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_COMMENTAIRE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_COMMENTAIRE() {
	return "NOM_EF_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_COMMENTAIRE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_COMMENTAIRE() {
	return getZone(getNOM_EF_COMMENTAIRE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_ENTRETIENS
 * Date de création : (25/07/05 08:30:59)
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
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
private void setLB_ENTRETIENS(java.lang.String[] newLB_ENTRETIENS) {
	LB_ENTRETIENS = newLB_ENTRETIENS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_ENTRETIENS
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_ENTRETIENS() {
	return "NOM_LB_ENTRETIENS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIENS_SELECT
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_ENTRETIENS_SELECT() {
	return "NOM_LB_ENTRETIENS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_ENTRETIENS
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_ENTRETIENS() {
	return getLB_ENTRETIENS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_ENTRETIENS
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_ENTRETIENS_SELECT() {
	return getZone(getNOM_LB_ENTRETIENS_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (25/07/05 09:15:57)
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
 * Date de création : (25/07/05 09:15:57)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_RECHERCHE,true);
	return true;
}
public OT getOtCourant() {
	return otCourant;
}
public void setOtCourant(OT otCourant) {
	this.otCourant = otCourant;
}
public EquipementInfos getEquipementInfosCourant() {
	return equipementInfosCourant;
}
public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
	this.equipementInfosCourant = equipementInfosCourant;
}
	public ArrayList<Planning> getListEntretien() {
		return listEntretien;
	}
	public void setListEntretien(ArrayList<Planning> listEntretien) {
		this.listEntretien = listEntretien;
	}
	public int getTailleList() {
		return tailleList;
	}
	public void setTailleList(int tailleList) {
		this.tailleList = tailleList;
	}
	private java.lang.String[] LB_ENTRETIENS_SUITE;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_ENTRETIENS_SUITE
 * Date de création : (26/07/05 13:19:52)
 * @author : Générateur de process
 */
private String [] getLB_ENTRETIENS_SUITE() {
	if (LB_ENTRETIENS_SUITE == null)
		LB_ENTRETIENS_SUITE = initialiseLazyLB();
	return LB_ENTRETIENS_SUITE;
}
/**
 * Setter de la liste:
 * LB_ENTRETIENS_SUITE
 * Date de création : (26/07/05 13:19:52)
 * @author : Générateur de process
 */
private void setLB_ENTRETIENS_SUITE(java.lang.String[] newLB_ENTRETIENS_SUITE) {
	LB_ENTRETIENS_SUITE = newLB_ENTRETIENS_SUITE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_ENTRETIENS_SUITE
 * Date de création : (26/07/05 13:19:52)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_ENTRETIENS_SUITE() {
	return "NOM_LB_ENTRETIENS_SUITE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIENS_SUITE_SELECT
 * Date de création : (26/07/05 13:19:52)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_ENTRETIENS_SUITE_SELECT() {
	return "NOM_LB_ENTRETIENS_SUITE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_ENTRETIENS_SUITE
 * Date de création : (26/07/05 13:19:52)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_ENTRETIENS_SUITE() {
	return getLB_ENTRETIENS_SUITE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_ENTRETIENS_SUITE
 * Date de création : (26/07/05 13:19:52)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_ENTRETIENS_SUITE_SELECT() {
	return getZone(getNOM_LB_ENTRETIENS_SUITE_SELECT());
}
public int getTailleList_suite() {
	return tailleList_suite;
}
public void setTailleList_suite(int tailleList_suite) {
	this.tailleList_suite = tailleList_suite;
}
public boolean isAffiche() {
	return affiche;
}
public void setAffiche(boolean affiche) {
	this.affiche = affiche;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:56:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:56:33)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TCOMPTEUR
 * Date de création : (23/08/05 09:03:32)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TCOMPTEUR() {
	return "NOM_ST_TCOMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TCOMPTEUR
 * Date de création : (23/08/05 09:03:32)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TCOMPTEUR() {
	return getZone(getNOM_ST_TCOMPTEUR());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (24/08/05 09:34:57)
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
 * Date de création : (24/08/05 09:34:57)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//retour à l'écran précédent
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_EQUIP
 * Date de création : (02/04/07 08:46:24)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_EQUIP() {
	return "NOM_PB_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/04/07 08:46:24)
 * @author : Générateur de process
 */
public boolean performPB_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	String recherche = getZone(getNOM_EF_EQUIP()).toUpperCase();
	if(recherche.equals("")){
		getTransaction().declarerErreur("La zone de recherche doit être renseignée.");
		return false;
	}
	EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfosInvOuImmat(getTransaction(),recherche);
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("L'équipement recherché n'a pas été trouvé.");
		return false;
	}
	if(unEquipementInfos==null){
		unEquipementInfos = new EquipementInfos();
	}
	setEquipementInfosCourant(unEquipementInfos);
		
	// on renseigne la liste des BPC
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_EQUIP
 * Date de création : (02/04/07 08:46:24)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_EQUIP() {
	return "NOM_EF_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_EQUIP
 * Date de création : (02/04/07 08:46:24)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_EQUIP() {
	return getZone(getNOM_EF_EQUIP());
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
	return getNOM_EF_EQUIP();
}

/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_COMMENTAIRE
		if (testerParametre(request, getNOM_PB_COMMENTAIRE())) {
			return performPB_COMMENTAIRE(request);
		}

		//Si clic sur le bouton PB_EQUIP
		if (testerParametre(request, getNOM_PB_EQUIP())) {
			return performPB_EQUIP(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
		}

		//Si clic sur le bouton PB_IMPRIMER
		if (testerParametre(request, getNOM_PB_IMPRIMER())) {
			return performPB_IMPRIMER(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (08/08/07 15:07:49)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeOT_Impression.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_COMMENTAIRE
 * Date de création : (08/08/07 15:07:49)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_COMMENTAIRE() {
	return "NOM_PB_COMMENTAIRE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (08/08/07 15:07:49)
 * @author : Générateur de process
 */
public boolean performPB_COMMENTAIRE(javax.servlet.http.HttpServletRequest request) throws Exception {
	String commentaire = getZone(getNOM_EF_COMMENTAIRE());
	getOtCourant().setCommentaire(commentaire);
	Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("L'équipement n'a pas été trouvé.");
		return false;
	}
	getOtCourant().modifierOT(getTransaction(),unEquipement);
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
		String res = getScript();
		setScript(null);
		return res;
	}
}
