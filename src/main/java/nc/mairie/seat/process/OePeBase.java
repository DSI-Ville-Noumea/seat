package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Entretien;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.ModeleInfos;
import nc.mairie.seat.metier.PeBase;
import nc.mairie.seat.metier.PeBaseInfos;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.TIntervalle;
import nc.mairie.technique.*;
/**
 * Process OePeBase
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
*/
public class OePeBase extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6909826406145237878L;
	public static final int STATUT_RECHERCHERMODELE = 3;
	public static final int STATUT_MODIFIER = 2;
	public static final int STATUT_AJOUTER = 1;
	private String ACTION_SUPPRESSION = "Suppression d'un entretien pour un modèle.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private ArrayList<PeBaseInfos> listePeBase = null;
	private java.lang.String[] LB_PE;
	private java.lang.String[] LB_PE_Couleurs;
	private ModeleInfos modeleInfosCourant;
	private PeBaseInfos peBaseInfosCourant;
	private Modeles modeleCourant;
	private Entretien entretienCourant;
	private TIntervalle tintervalleCourant;
	private PeBase peBaseCourant;
	public int isVide=0;
	private String focus = null;
	private boolean first = true;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	if((first)||(etatStatut()==STATUT_RECHERCHERMODELE)){
		ModeleInfos unModeleInfos = (ModeleInfos)VariableGlobale.recuperer(request, "MODELEINFOS");
		if (unModeleInfos!=null){
			if (unModeleInfos.existeModeleInfos(getTransaction(),unModeleInfos.getCodemodele())){
				setModeleInfosCourant(unModeleInfos);
				Modeles unModele = Modeles.chercherModeles(getTransaction(),getModeleInfosCourant().getCodemodele());
				if (getTransaction().isErreur()){
					return;
				}
				setModeleCourant(unModele);
			}else{
				VariableGlobale.enlever(request,"MODELEINFOS");
			}
		}else{
			EquipementInfos unEquipInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
			if(unEquipInfos!=null){
				if(unEquipInfos.getNumeroinventaire()!=null){
					Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),unEquipInfos.getNumeroinventaire());
					if(getTransaction().isErreur()){
						return;
					}
					Modeles unModele = Modeles.chercherModeles(getTransaction(),unEquipement.getCodemodele());
					if(getTransaction().isErreur()){
						getTransaction().declarerErreur("Le modèle de l'équipement courant n'a pas été trouvé.");
						return ;
					}
					setModeleCourant(unModele);
					ModeleInfos unMI = ModeleInfos.chercherModeleInfos(getTransaction(),unModele.getCodemodele());
					if(getTransaction().isErreur()){
						getTransaction().declarerErreur("Les informations sur le modèle de l'équipement courant n'ont pas été trouvées.");
						return;
					}
					setModeleInfosCourant(unMI);
				}
			}
		}
	}
	
	if(getModeleInfosCourant()!=null){
		initialiseListePe(request);
		// on renseigne les zones
		addZone(getNOM_ST_MARQUE(),getModeleInfosCourant().getDesignationmarque());
		addZone(getNOM_ST_MODELE(),getModeleInfosCourant().getDesignationmodele());
		addZone(getNOM_ST_TEQUIP(),getModeleInfosCourant().getDesignationtypeequip());
		addZone(getNOM_ST_VERSION(),getModeleInfosCourant().getVersion());
	}else{
		setLB_PE(LBVide);
//		 on vide les zones
		addZone(getNOM_ST_MARQUE(),"");
		addZone(getNOM_ST_MODELE(),"");
		addZone(getNOM_ST_TEQUIP(),"");
	}
	first = false;
	if(!getZone(getNOM_EF_RECHERCHE()).equals("")){
		performPB_RECHERCHE(request);
	}
}

/**
 * Initialisation de la liste des entretiens
 * @author : Coralie NICOLAS
 */
private void initialiseListePe(javax.servlet.http.HttpServletRequest request) throws Exception{

	//	Recherche des entretiens
	ArrayList<PeBaseInfos> a = PeBaseInfos.listerPeBaseInfos(getTransaction(),modeleInfosCourant.getCodemodele());
	if (null == a){
		System.out.println("Aucun élément enregistré dans la base.");
	}
	setListePeBase(a);
	String desactiv = "";
	String date = "";
	
	if (a!=null && a.size()>0){
		//les élèments de la liste seront le codemarque pour pouvoir récupérer le dernier élément et les dimensions des marques.
		int [] tailles = {30,6,10,7,10,};
		String [] padding = {"G","D","G","C","C"};
		
		setLB_PE_Couleurs(new String[a.size()]);
		FormateListe aFormat = new FormateListe(tailles,padding,true);
		for (int i = 0; i < a.size() ; i++) {
			PeBaseInfos aPeBaseInfos = (PeBaseInfos)a.get(i);	
			
			if(aPeBaseInfos.getDesactive().equals("OUI")){
				desactiv = "X";
				date = aPeBaseInfos.getDatedesactivation();
				LB_PE_Couleurs[i] = "red";
			}else{
				desactiv = "";
				date="";
			}
			//date = aPeBaseInfos.getDatedesactivation();
			TIntervalle unTi = TIntervalle.chercherTIntervalle(getTransaction(),aPeBaseInfos.getCodeti());
			if (getTransaction().isErreur()){
				return;
			}
			//MODIF OFONTENEAU 20090403
			String intervalle="";
			if (null!=aPeBaseInfos.getIntervalle())
				intervalle=aPeBaseInfos.getIntervalle()+" "+ unTi.getDesignation();
			else
				intervalle = unTi.getDesignation();
			String ligne [] = { aPeBaseInfos.getLibelleentretien(),aPeBaseInfos.getDuree(),intervalle,desactiv,date};
			aFormat.ajouteLigne(ligne);
		}
		setLB_PE(aFormat.getListeFormatee());
	}else{
		setLB_PE(null);
	}
	setIsVide(a == null ? 0 : a.size());		
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	On met la variable activité
	if(null==getModeleInfosCourant()){
		setModeleInfosCourant(new ModeleInfos());
	}
	if(null==getPeBaseInfosCourant()){
		setPeBaseInfosCourant(new PeBaseInfos());
	}
	VariableGlobale.ajouter(request, "MODELEINFOS", getModeleInfosCourant());
	setPeBaseInfosCourant(null);
	VariableGlobale.ajouter(request, "PEBASEINFOS", getPeBaseInfosCourant());
	setStatut(STATUT_AJOUTER,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 on vide les zones
	addZone(getNOM_ST_TINT(),"");
	addZone(getNOM_ST_TDUREE(),"");
	addZone(getNOM_ST_INTERVALLE(),"");
	addZone(getNOM_ST_ENTRETIEN(),"");
	addZone(getNOM_ST_DUREE(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int numligne = (Services.estNumerique(getZone(getNOM_LB_PE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_PE_SELECT())) : -1);
	if (numligne == -1 || getListePeBase().size() == 0 || numligne > getListePeBase().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Plan d'entretien de base"));
		return false;
	}
	
	PeBaseInfos monPeBaseInfos = (PeBaseInfos)getListePeBase().get(numligne);
	setPeBaseInfosCourant(monPeBaseInfos);
//	On met la variable activité
	if(null==getModeleInfosCourant()){
		setModeleInfosCourant(new ModeleInfos());
	}
	VariableGlobale.ajouter(request, "MODELEINFOS", getModeleInfosCourant());
	VariableGlobale.ajouter(request, "PEBASEINFOS", getPeBaseInfosCourant());
	setStatut(STATUT_MODIFIER,true);
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHEMODELE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHEMODELE() {
	return "NOM_PB_RECHERCHEMODELE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHEMODELE(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"RECHMODELE","");
	setStatut(STATUT_RECHERCHERMODELE,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int numligne = (Services.estNumerique(getZone(getNOM_LB_PE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_PE_SELECT())) : -1);
	if (numligne == -1 || getListePeBase().size() == 0 || numligne > getListePeBase().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Plan d'entretien de base"));
		return false;
	}
	
	PeBaseInfos monPeBaseInfos = (PeBaseInfos)getListePeBase().get(numligne);
	setPeBaseInfosCourant(monPeBaseInfos);
	// on recherche le peBase
	PeBase unPeBase = PeBase.chercherPeBase(getTransaction(),peBaseInfosCourant.getCodemodele(),peBaseInfosCourant.getCodeentretien());
	if (getTransaction().isErreur()){
		return false;
	}
	setPeBaseCourant(unPeBase);
	Entretien unEntretien = Entretien.chercherEntretien(getTransaction(),peBaseCourant.getCodeentretien());
	if(getTransaction().isErreur()){
		return false;
	}
	setEntretienCourant(unEntretien);
	TIntervalle unTIntervalle = TIntervalle.chercherTIntervalle(getTransaction(),peBaseCourant.getCodeti());
	if (getTransaction().isErreur()){
		return false;
	}
	setTintervalleCourant(unTIntervalle);
	
	//on nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	// on initialise les zones
	addZone(getNOM_ST_TINT(),peBaseInfosCourant.getDesignation());
	addZone(getNOM_ST_COMMENTAIRE(),peBaseInfosCourant.getCommentaire());
	addZone(getNOM_ST_INTERVALLE(),peBaseInfosCourant.getIntervalle());
	addZone(getNOM_ST_ENTRETIEN(),peBaseInfosCourant.getLibelleentretien());
	addZone(getNOM_ST_DUREE(),peBaseInfosCourant.getDuree());
	setFocus(getNOM_CK_DESACTIV());
	// on met la date du jour
	String date = Services.dateDuJour();
	addZone(getNOM_EF_DATEDESACTIV(),date);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//	Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}
	//Récupération des zones renseignées
	String newDate = getZone(getNOM_EF_DATEDESACTIV());
	String desactiv = getZone(getNOM_CK_DESACTIV());
	String newDesactiv = desactiv.equals(getCHECKED_ON()) ? "OUI" : "NON";
	
	//Affectation 
	if (newDesactiv.equals("OUI")){
		getPeBaseCourant().setDatedesactivation(newDate);
		getPeBaseCourant().setDesactive(newDesactiv);
	}
	
	//	Suppression : on désactive l'entretien
	peBaseCourant.modifierPeBase(getTransaction(),getModeleCourant(),getEntretienCourant(),getTintervalleCourant());
	
	//getPeBaseCourant().supprimerPeBase(getTransaction());
	if (getTransaction().isErreur()){
		return false;
	}
//	Tout s'est bien passé
	commitTransaction();
	initialiseListePe(request);
	// on vide les zones
	addZone(getNOM_ST_TINT(),"");
	addZone(getNOM_ST_TDUREE(),"");
	addZone(getNOM_ST_INTERVALLE(),"");
	addZone(getNOM_ST_ENTRETIEN(),"");
	addZone(getNOM_ST_DUREE(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DUREE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DUREE() {
	return "NOM_ST_DUREE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DUREE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DUREE() {
	return getZone(getNOM_ST_DUREE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ENTRETIEN
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_ENTRETIEN() {
	return "NOM_ST_ENTRETIEN";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_ENTRETIEN
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_ENTRETIEN() {
	return getZone(getNOM_ST_ENTRETIEN());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_INTERVALLE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_INTERVALLE() {
	return "NOM_ST_INTERVALLE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_INTERVALLE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_INTERVALLE() {
	return getZone(getNOM_ST_INTERVALLE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TDUREE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TDUREE() {
	return "NOM_ST_TDUREE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TDUREE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TDUREE() {
	return getZone(getNOM_ST_TDUREE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TEQUIP
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TEQUIP() {
	return "NOM_ST_TEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TEQUIP
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TEQUIP() {
	return getZone(getNOM_ST_TEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TINT
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TINT() {
	return "NOM_ST_TINT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TINT
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TINT() {
	return getZone(getNOM_ST_TINT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_PE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
private String [] getLB_PE() {
	if (LB_PE == null)
		LB_PE = initialiseLazyLB();
	return LB_PE;
}
/**
 * Setter de la liste:
 * LB_PE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
private void setLB_PE(java.lang.String[] newLB_PE) {
	LB_PE = newLB_PE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_PE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_PE() {
	return "NOM_LB_PE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PE_SELECT
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_PE_SELECT() {
	return "NOM_LB_PE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_PE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_PE() {
	return getLB_PE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_PE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_PE_SELECT() {
	return getZone(getNOM_LB_PE_SELECT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (28/06/05 15:47:42)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (28/06/05 15:47:42)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
	public int getIsVide() {
		return isVide;
	}
	public void setIsVide(int isVide) {
		this.isVide = isVide;
	}
	public ModeleInfos getModeleInfosCourant() {
		return modeleInfosCourant;
	}
	public void setModeleInfosCourant(ModeleInfos modeleInfosCourant) {
		this.modeleInfosCourant = modeleInfosCourant;
	}
	public ArrayList<PeBaseInfos> getListePeBase() {
		return listePeBase;
	}
	public void setListePeBase(ArrayList<PeBaseInfos> listePeBase) {
		this.listePeBase = listePeBase;
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
	 * focus focus à définir.
	 * @return focus
	 */
	public String getDefaultFocus() {
		return getNOM_EF_RECHERCHE();
	}
	public PeBaseInfos getPeBaseInfosCourant() {
		return peBaseInfosCourant;
	}
	public void setPeBaseInfosCourant(PeBaseInfos peBaseInfosCourant) {
		this.peBaseInfosCourant = peBaseInfosCourant;
	}
	public PeBase getPeBaseCourant() {
		return peBaseCourant;
	}
	public void setPeBaseCourant(PeBase peBaseCourant) {
		this.peBaseCourant = peBaseCourant;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMMENTAIRE
 * Date de création : (01/07/05 13:14:20)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_COMMENTAIRE() {
	return "NOM_ST_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMMENTAIRE
 * Date de création : (01/07/05 13:14:20)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_COMMENTAIRE() {
	return getZone(getNOM_ST_COMMENTAIRE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATEDESACTIV
 * Date de création : (08/07/05 10:07:12)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DATEDESACTIV() {
	return "NOM_EF_DATEDESACTIV";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATEDESACTIV
 * Date de création : (08/07/05 10:07:12)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DATEDESACTIV() {
	return getZone(getNOM_EF_DATEDESACTIV());
}
/**
 * Retourne le nom de la case à cocher sélectionnée pour la JSP :
 * CK_DESACTIV
 * Date de création : (08/07/05 10:07:12)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_CK_DESACTIV() {
	return "NOM_CK_DESACTIV";
}
/**
 * Retourne la valeur de la case à cocher à afficher par la JSP pour la case à cocher  :
 * CK_DESACTIV
 * Date de création : (08/07/05 10:07:12)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_CK_DESACTIV() {
	return getZone(getNOM_CK_DESACTIV());
}
	public Modeles getModeleCourant() {
		return modeleCourant;
	}
	public void setModeleCourant(Modeles modeleCourant) {
		this.modeleCourant = modeleCourant;
	}
	public Entretien getEntretienCourant() {
		return entretienCourant;
	}
	public void setEntretienCourant(Entretien entretienCourant) {
		this.entretienCourant = entretienCourant;
	}
	public TIntervalle getTintervalleCourant() {
		return tintervalleCourant;
	}
	public void setTintervalleCourant(TIntervalle tintervalleCourant) {
		this.tintervalleCourant = tintervalleCourant;
	}
	public java.lang.String[] getLB_PE_Couleurs() {
		return LB_PE_Couleurs == null ? null : LB_PE_Couleurs.clone();
	}
	private void setLB_PE_Couleurs(java.lang.String[] couleurs) {
		LB_PE_Couleurs = couleurs;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_VERSION
 * Date de création : (02/04/07 09:19:07)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_VERSION() {
	return "NOM_ST_VERSION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_VERSION
 * Date de création : (02/04/07 09:19:07)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_VERSION() {
	return getZone(getNOM_ST_VERSION());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (28/06/05 15:54:20)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {
		//Si clic sur le bouton PB_AJOUTER
		if (testerParametre(request, getNOM_PB_AJOUTER())) {
			return performPB_AJOUTER(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_MODIFIER
		if (testerParametre(request, getNOM_PB_MODIFIER())) {
			return performPB_MODIFIER(request);
		}

		//Si clic sur le bouton PB_RECHERCHEMODELE
		if (testerParametre(request, getNOM_PB_RECHERCHEMODELE())) {
			return performPB_RECHERCHEMODELE(request);
		}
		
//		Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
		}

		//Si clic sur le bouton PB_SUPPRIMER
		if (testerParametre(request, getNOM_PB_SUPPRIMER())) {
			return performPB_SUPPRIMER(request);
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
 * Date de création : (21/06/07 14:54:31)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OePeBase.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (21/06/07 14:54:31)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHE() {
	return "NOM_PB_RECHERCHE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (21/06/07 14:54:31)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	addZone(getNOM_EF_DATEDESACTIV(),"");
	setLB_PE(null);
	setListePeBase(null);
	addZone(getNOM_ST_VERSION(),""); 
	addZone(getNOM_ST_COMMENTAIRE(),"");
	addZone(getNOM_ST_DUREE(),"");
	addZone(getNOM_ST_ENTRETIEN(),"");
	addZone(getNOM_ST_INTERVALLE(),"");
	addZone(getNOM_ST_MARQUE(),"");
	addZone(getNOM_ST_MODELE(),"");
	addZone(getNOM_ST_TDUREE(),"");
	addZone(getNOM_ST_TEQUIP(),"");
	addZone(getNOM_ST_TINT(),"");
	if(!getZone(getNOM_EF_RECHERCHE()).equals("")){
		ArrayList<?> listM = Modeles.listerModelesLib(getTransaction(),getZone(getNOM_EF_RECHERCHE()));
		if(getTransaction().isErreur()){
			return false;
		}
		if(listM.size()>1){
			VariableActivite.ajouter(this,"RECHMODELE",getZone(getNOM_EF_RECHERCHE()));
			addZone(getNOM_EF_RECHERCHE(),"");
			setStatut(STATUT_RECHERCHERMODELE,true);
		}else{
			if(listM.size()==1){
				Modeles unM = (Modeles)listM.get(0);
				ModeleInfos unMI = ModeleInfos.chercherModeleInfos(getTransaction(),unM.codemodele);
				if(getTransaction().isErreur()){
					return false;
				}
				setModeleInfosCourant(unMI);
			}else{
				getTransaction().declarerErreur("Aucun entretien enregistré pour ce modèle.");
				return false;
			}
			addZone(getNOM_EF_RECHERCHE(),"");
		}
	}else{
		getTransaction().declarerErreur("La zone de recherche doit être renseignée.");
		return false;
	}
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE
 * Date de création : (21/06/07 14:54:31)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_RECHERCHE() {
	return "NOM_EF_RECHERCHE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE
 * Date de création : (21/06/07 14:54:31)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_RECHERCHE() {
	return getZone(getNOM_EF_RECHERCHE());
}
}
