package nc.mairie.seat.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.ListIterator;

import nc.mairie.seat.metier.FPM;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PM_Planning;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.servlets.Frontale;
import nc.mairie.technique.*;
/**
 * Process OeFPM_Impression
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
*/
public class OeFPM_Impression extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6427440715820116062L;
	public static final int STATUT_RECHERCHE = 1;
	private java.lang.String[] LB_ENTRETIENS;
	private FPM fpmCourant;
	private PMatInfos pMatInfosCourant;
	private ArrayList<PM_Planning> listEntretien;
	public int tailleList = 0;
	public int tailleList_suite = 0;
	public boolean affiche;
	public boolean afficheRetour = false;
	private String focus = null;
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
	//récupération de FPM à imprimer
	FPM monFPM = (FPM)VariableGlobale.recuperer(request, "FPM");
//	if (monFPM!=null){
//		if(monFPM.getNumfiche()!=null){
//			//	on rafraichit la fPM
//			monFPM = FPM.chercherFPM(getTransaction(),monFPM.getNumfiche());
//			if(getTransaction().isErreur()){
//				return ;
//			}
//			setFpmCourant(monFPM);
//		}
//	}
	setFpmCourant(monFPM);
//	 quand appui sur entrée
	if(!("").equals(getZone(getNOM_EF_EQUIP()))){
		performPB_EQUIP(request);
		addZone(getZone(getNOM_EF_EQUIP()),"");
	}
	
	PMatInfos monPMatInfos = new PMatInfos();
	if(monFPM!=null){
		if(monFPM.getPminv()!=null){
			monPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),monFPM.getPminv());
		}else{
			monPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
		}
	}else{
		monPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
	}
	if(getTransaction().isErreur()){
		return;
	}
	setPMatInfosCourant(monPMatInfos);
	/*EquipementInfos monEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
	if(monEquipementInfos!=null){
		setEquipementInfosCourant(monEquipementInfos);
	}*/
	
	//initialisation des zones
	if(getFpmCourant()!=null){
		if(getFpmCourant().getNumfiche()!=null){

			addZone(getNOM_ST_DENTREE(),getFpmCourant().getDentree());
			addZone(getNOM_ST_DSORTIE(),getFpmCourant().getDsortie());
			addZone(getNOM_EF_COMMENTAIRE(),getFpmCourant().getCommentaire());
		}
		// infos concernant le petit matériel
		if(getPMatInfosCourant()!=null){
			if(getPMatInfosCourant().getPminv()!=null){
				addZone(getNOM_ST_MARQUE(),getPMatInfosCourant().getDesignationmarque());
				addZone(getNOM_ST_MODELE(),getPMatInfosCourant().getDesignationmodele());
				addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
				addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
				addZone(getNOM_ST_NOSERIE(),getPMatInfosCourant().getPmserie());
				//	infos concernant FPM
				addZone(getNOM_ST_NUMFICHE(),getFpmCourant().getNumfiche());
				addZone(getNOM_EF_COMMENTAIRE(),getFpmCourant().getCommentaire());
				if(getFpmCourant().getDentree().equals("01/01/0001")){
					addZone(getNOM_ST_DENTREE(),"");
				}else{
					addZone(getNOM_ST_DENTREE(),getFpmCourant().getDentree());
				}
				if(getFpmCourant().getDsortie().equals("01/01/0001")){
					addZone(getNOM_ST_DSORTIE(),"");
				}else{
					addZone(getNOM_ST_DSORTIE(),getFpmCourant().getDsortie());
				}
				ArrayList<PM_Planning> a = PM_Planning.chercherPM_Planning_FPM(getTransaction(),getFpmCourant().getNumfiche());
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
		}
	}else{
		if(getPMatInfosCourant()!=null){
			if(getPMatInfosCourant().getPminv()!=null){
				addZone(getNOM_ST_MARQUE(),getPMatInfosCourant().getDesignationmarque());
				addZone(getNOM_ST_MODELE(),getPMatInfosCourant().getDesignationmodele());
				addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
				addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
				addZone(getNOM_ST_NOSERIE(),getPMatInfosCourant().getPmserie());
			}
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

public void initialiseListEntretiens(javax.servlet.http.HttpServletRequest request,ArrayList<PM_Planning> a) throws Exception{
	int indice = 0;
	int i = 0;
	setListEntretien(a);
//	Si au moins un entretien à faire
	if (a.size() !=0 ) {
		int tailles [] = {50};
		FormateListe aFormat = new FormateListe(tailles);
		FormateListe aFormat_suite = new FormateListe(tailles);
		for (ListIterator<PM_Planning> list = a.listIterator(); list.hasNext(); ) {
			PM_Planning unPlanning = (PM_Planning)list.next();
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
 * Constructeur du process OeFPM_Impression.
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public OeFPM_Impression() {
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
@SuppressWarnings("deprecation")
public boolean performPB_IMPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	PMateriel unPMateriel;
	//on enregistre le commentaire s'il y en a un
	String newCommentaire = getZone(getNOM_EF_COMMENTAIRE()).toUpperCase();
	if (!newCommentaire.equals("")){
		if(newCommentaire.length()>600){
			getTransaction().declarerErreur("Le commentaire est trop long.");
			return false;
		}
		
		// si le commentaire a été modifié
		if(!newCommentaire.equalsIgnoreCase(getFpmCourant().getCommentaire())){
			getFpmCourant().setCommentaire(newCommentaire);
			// recherche de le petit matériel a passé en paramètre
			if(getFpmCourant().getPminv()==null){
				unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
			}else{
				unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getFpmCourant().getPminv());
			}
			if(getTransaction().isErreur()){
				return false;
			}
			getFpmCourant().modifierPMateriel_Fiche(getTransaction(),unPMateriel);
			if(getTransaction().isErreur()){
				return false;
			}
		}
	}
	//enregistrement 
	commitTransaction();
		
	
	// impression de l'OT
	if(getFpmCourant()!=null){
		String commentaireOt = "";
		String numinv = getZone(getNOM_ST_NOINVENT());
		String numserie = getZone(getNOM_ST_NOSERIE());
		String nomequip = getZone(getNOM_ST_MARQUE())+" "+getZone(getNOM_ST_MODELE());
		String type = getZone(getNOM_ST_TYPE());
		String numFpm = getZone(getNOM_ST_NUMFICHE());
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
//				
//		for (int i=0;i<comlen;i++){
//			if(commentaire.charAt(i)=='\n'){
//				commentaireOt = commentaireOt+commentaire.substring(indice,i)+"$";
//				indice = i;
//			}
//		}
		commentaireOt = commentaireOt + commentaire.substring(indice,comlen);
		commentaireOt = commentaireOt.replace('\n',' ');
		commentaireOt = commentaireOt.replace('\r',' ');
		StarjetGeneration g = new StarjetGeneration(getTransaction(), "MAIRIE", starjetMode, "SEAT", "ficheFPM_Vierge.sp", "ficheFPM_Vierge");
		File f = g.getFileData();
		//FileWriter fw = new FileWriter(f);
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(f),"iso-8859-1");
		PrintWriter pw = new PrintWriter(fw);
		try {	
			//	Entete
			pw.print("1");
			pw.print(Services.lpad(numinv,32," "));
			pw.print(Services.lpad(numserie,50," "));
			pw.print(Services.lpad(nomequip,64," "));
			pw.print(Services.lpad(type,32," "));
			// OT
			pw.print(Services.lpad(numFpm,8," "));
			pw.print(Services.lpad(dEntree,10," "));
			pw.print(Services.lpad(dSortie,10," "));
			pw.print(Services.lpad(compteur,10," "));
			pw.print(Services.lpad(commentaireOt,600," "));
			pw.println();
			
//			 liste des interventions
			for (int i=0;i<getListEntretien().size();i++){
				if(!getListEntretien().get(i).equals("")){
					PM_Planning unPMp = (PM_Planning)getListEntretien().get(i);
					pw.print("2");
					pw.print(Services.lpad(unPMp.getLibelleentretien(),31," "));
					pw.print(Services.lpad(unPMp.getDreal(),11," "));
					pw.print(Services.lpad(unPMp.getDuree(),6," "));
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

public String getServiceForPmateriel(PMateriel petitmat, String DateEntree){
	String sservice="";
	try{
		PM_Affectation_Sce_Infos unPMASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),DateEntree);
		sservice=unPMASI.getSiserv();
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
public java.lang.String getNOM_ST_NOSERIE() {
	return "NOM_ST_NOSERIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOSERIE() {
	return getZone(getNOM_ST_NOSERIE());
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
 * ST_NUMFICHE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NUMFICHE() {
	return "NOM_ST_NUMFICHE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NUMFICHE
 * Date de création : (25/07/05 08:30:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NUMFICHE() {
	return getZone(getNOM_ST_NUMFICHE());
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

	if((!getZone(getNOM_EF_EQUIP()).equals(""))||(!getZone(getNOM_EF_NUMFICHE()).equals(""))){
		performPB_EQUIP(request);
	}else{
		setStatut(STATUT_RECHERCHE,true);
	}
	return true;
}
public FPM getFpmCourant() {
	return fpmCourant;
}
public void setFpmCourant(FPM fpmCourant) {
	this.fpmCourant = fpmCourant;
}
public PMatInfos getPMatInfosCourant() {
	return pMatInfosCourant;
}
public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
	this.pMatInfosCourant = pMatInfosCourant;
}
	public ArrayList<PM_Planning> getListEntretien() {
		return listEntretien;
	}
	public void setListEntretien(ArrayList<PM_Planning> listEntretien) {
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
	String numfiche = getZone(getNOM_EF_NUMFICHE().toUpperCase());
	String recherche = getZone(getNOM_EF_EQUIP()).toUpperCase();
	if((numfiche!=null)&&(!numfiche.equals(""))){
		numfiche = Outils.enleveEspace(numfiche);
		if(!Services.estNumerique(numfiche)){
			getTransaction().declarerErreur("Le numéro de fiche ne doit comporter que des chiffres.");
			return false;
		}
		FPM unFPM = FPM.chercherFPM(getTransaction(),numfiche);
		if(getTransaction().isErreur()){
			return false;
		}
		setFpmCourant(unFPM);
	}else{
		if((recherche!=null)&&(!recherche.equals(""))){
			PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),recherche);
			if(getTransaction().isErreur()){
				getTransaction().declarerErreur("Le petit matériel recherché n'a pas été trouvé.");
				return false;
			}
			if(null==unPMatInfos){
				unPMatInfos = new PMatInfos();
			}
			setPMatInfosCourant(unPMatInfos);
			// on recherche la liste des FPM
			ArrayList<?> listFpm = FPM.listerFpmPmat(getTransaction(),getPMatInfosCourant().getPminv());
			if(getTransaction().isErreur()){
				getTransaction().traiterErreur();
			}
			if(listFpm!=null){
				if(listFpm.size()>0){
					if(listFpm.size()==1){
						FPM unFpm = (FPM)listFpm.get(0);
						setFpmCourant(unFpm);
					}else{
						VariableActivite.ajouter(this,"PMATINFOS",getPMatInfosCourant());
						setStatut(STATUT_RECHERCHE,true);
					}
				}else{
					getTransaction().declarerErreur("Aucune fiche d'entretiens enregistrée pour ce petit matériel.");
					return false;
				}
			}
		}
	}
	if(null==getPMatInfosCourant()){
		setPMatInfosCourant(new PMatInfos());
	}
	// on renseigne
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
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
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_NUMFICHE
 * Date de création : (05/07/07 10:59:23)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_NUMFICHE() {
	return "NOM_EF_NUMFICHE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_NUMFICHE
 * Date de création : (05/07/07 10:59:23)
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
 * Date de création : (08/08/07 15:08:10)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeFPM_Impression.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_COMMENTAIRE
 * Date de création : (08/08/07 15:08:11)
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
 * Date de création : (08/08/07 15:08:11)
 * @author : Générateur de process
 */
public boolean performPB_COMMENTAIRE(javax.servlet.http.HttpServletRequest request) throws Exception {
	String commentaire = getZone(getNOM_EF_COMMENTAIRE());
	getFpmCourant().setCommentaire(commentaire);
	PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("L'équipement n'a pas été trouvé.");
		return false;
	}
	getFpmCourant().modifierPMateriel_Fiche(getTransaction(),unPMateriel);
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
