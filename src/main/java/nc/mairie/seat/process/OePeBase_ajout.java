package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Entretien;
import nc.mairie.seat.metier.ModeleInfos;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.PeBase;
import nc.mairie.seat.metier.PeBaseInfos;
import nc.mairie.seat.metier.TIntervalle;
import nc.mairie.technique.*;
/**
 * Process OePeBase_ajout
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
*/
public class OePeBase_ajout extends nc.mairie.technique.BasicProcess {
	private java.lang.String[] LB_ENTRETIEN;
	private java.lang.String[] LB_TINTER;
	private String ACTION_MODIFICATION = "Modification";
	private String ACTION_CREATION = "Création";
	private String focus = null;
	private ArrayList listeTintervalle;
	private ArrayList listeEntretien;
	private ModeleInfos modeleInfosCourant;
	private PeBaseInfos peBaseInfosCourant;
	private TIntervalle tIntervalleCourant;
	private Entretien entretienCourant;
	private PeBase peBaseCourant;
	private Modeles modeleCourant;
	private String isModif="";
	private boolean manqueParam = false;
	private boolean first = true;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(first){
		ModeleInfos unModeleInfos = (ModeleInfos)VariableGlobale.recuperer(request, "MODELEINFOS");
		setModeleInfosCourant(unModeleInfos);
		
		PeBaseInfos unPeBaseInfos = (PeBaseInfos)VariableGlobale.recuperer(request, "PEBASEINFOS");
		if (unPeBaseInfos!=null){
			setPeBaseInfosCourant(unPeBaseInfos);
		}
	}
	
	// zones concernant le modèle
	if (getModeleInfosCourant()!=null){
		addZone(getNOM_ST_MARQUE(),modeleInfosCourant.getDesignationmarque());
		addZone(getNOM_ST_MODELE(),modeleInfosCourant.getDesignationmodele());
		addZone(getNOM_ST_TEQUIP(),modeleInfosCourant.getDesignationtypeequip());
		//	zones concernant le PeBase
		if (getPeBaseInfosCourant()==null){
			addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
			addZone(getNOM_EF_DUREE(),"");
			addZone(getNOM_EF_INTERVALLE(),"");
			addZone(getNOM_LB_ENTRETIEN_SELECT(),"0");
			addZone(getNOM_LB_TINTER_SELECT(),"0");
			addZone(getNOM_CK_DESACTIV(),getCHECKED_OFF());
		}else{
			PeBase unPeBase = PeBase.chercherPeBase(getTransaction(),peBaseInfosCourant.getCodemodele(),peBaseInfosCourant.getCodeentretien());
			if (getTransaction().isErreur()){
				return ;
			}
			setPeBaseCourant(unPeBase);
			addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
			addZone(getNOM_EF_COMMENTAIRE(),peBaseInfosCourant.getCommentaire());
			addZone(getNOM_EF_DUREE(),peBaseInfosCourant.getDuree());
			addZone(getNOM_EF_INTERVALLE(),peBaseInfosCourant.getIntervalle());
			addZone(getNOM_ST_ENTRETIEN(),peBaseInfosCourant.getLibelleentretien());
			setIsModif("Modif");
			//	 on coche la case si l'équipement est reservé
			if ("OUI".equals(getPeBaseInfosCourant().getDesactive().trim())){
				addZone(getNOM_CK_DESACTIV(),getCHECKED_ON());
				addZone(getNOM_EF_DATEDESACTIV(),getPeBaseInfosCourant().getDatedesactivation());
			}else{
				addZone(getNOM_CK_DESACTIV(),getCHECKED_OFF());
			}
			if("01/01/0001".equals(getPeBaseInfosCourant().getDatedesactivation())){
				addZone(getNOM_EF_DATEDESACTIV(),"");
			}else{
				addZone(getNOM_EF_DATEDESACTIV(),getPeBaseInfosCourant().getDatedesactivation());
			}
			//	 on met la date du jour
			String date = Services.dateDuJour();
			addZone(getNOM_EF_DATEDESACTIV(),date);
			TIntervalle unTIntervalle = TIntervalle.chercherTIntervalle(getTransaction(),peBaseInfosCourant.getCodeti());
			setTIntervalleCourant(unTIntervalle);
			Entretien unEntretien = Entretien.chercherEntretien(getTransaction(),peBaseInfosCourant.getCodeentretien());
			setEntretienCourant(unEntretien);
		}
	}else{
		addZone(getNOM_ST_MARQUE(),"");
		addZone(getNOM_ST_MODELE(),"");
		addZone(getNOM_ST_TEQUIP(),"");
	}
//	 on remplit les listes déroulante
//	 Si liste des tintervalle est vide
	if (getLB_TINTER() == LBVide) {
		java.util.ArrayList a = TIntervalle.listerTIntervalle(getTransaction());
		setListeTintervalle(a);
		//les élèments de la liste 
		int [] tailles = {10};
		String [] champs = {"designation"};
		String [] padding = {"G"};
		setLB_TINTER(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
		
		if(getTIntervalleCourant()!=null){
			//	recherche du type d'intervalle courant
			int position = -1;
			addZone(getNOM_LB_TINTER_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeTintervalle().size(); i++) {
				TIntervalle unTIntervalle = (TIntervalle)getListeTintervalle().get(i);
				if (unTIntervalle.getCodeti().equals(getTIntervalleCourant().getCodeti())) {
					addZone(getNOM_LB_TINTER_SELECT(),String.valueOf(i));
					break;
				}
			}
		}
	}
	// Si liste des entretiens est vide
		if (getLB_ENTRETIEN() == LBVide) {
			java.util.ArrayList a = Entretien.listerEntretien(getTransaction());
			setListeEntretien(a);
			//les élèments de la liste 
			int [] tailles = {20};
			String [] champs = {"libelleentretien"};
			String [] padding = {"G"};
			setLB_ENTRETIEN(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
			
		}
		if(getListeTintervalle().size()==0){
			getTransaction().declarerErreur("Aucun type d'intervalle n'est enregistré.L'alimentation du plan d'entretien ne peut pas se faire.");
			setManqueParam(true);
		}
		if(getListeEntretien().size()==0){
			getTransaction().declarerErreur("Aucun entretien n'est enregistré.L'alimentation du plan d'entretien ne peut pas se faire.");
			setManqueParam(true);
		}
setFirst(false);
}
/**
 * Constructeur du process OePeBase_ajout.
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public OePeBase_ajout() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (29/06/05 07:38:35)
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
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	setIsModif("");
	//on vides les zones
	setLB_ENTRETIEN(LBVide);
	setLB_TINTER(LBVide);
	addZone(getNOM_EF_DUREE(),"");
	addZone(getNOM_EF_INTERVALLE(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_MARQUE(),"");
	addZone(getNOM_ST_MODELE(),"");
	addZone(getNOM_ST_TEQUIP(),"");
	setFocus(null);
	setPeBaseInfosCourant(null);
	VariableGlobale.enlever(request,"PEBASEINFOS");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (29/06/05 07:38:35)
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
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//on rècupère les zones
	String newDuree = getZone(getNOM_EF_DUREE());
	String newIntervalle = getZone(getNOM_EF_INTERVALLE());
	String newCommentaire = getZone(getNOM_EF_COMMENTAIRE()).toUpperCase();
	int indice = 0;
	String newDate = getZone(getNOM_EF_DATEDESACTIV());
	String desactiv = getZone(getNOM_CK_DESACTIV());
	String newDesactiv = desactiv.equals(getCHECKED_ON()) ? "OUI" : "NON";
	if(newDesactiv.equals("NON")){
		newDate = "";
	}
//	Affectation 
	/*if (newDesactiv.equals("OUI")){
		getPeBaseCourant().setDatedesactivation(newDate);
		getPeBaseCourant().setDesactive(newDesactiv);
	}*/
	
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		indice = (Services.estNumerique(getVAL_LB_ENTRETIEN_SELECT()) ? Integer.parseInt(getVAL_LB_ENTRETIEN_SELECT()): -1);
		Entretien monEntretien = (Entretien)getListeEntretien().get(indice);
		if(getTransaction().isErreur()){
			return false;
		}
		setEntretienCourant(monEntretien);
	}else if(getVAL_ST_TITRE_ACTION().equals(ACTION_MODIFICATION)){
		Entretien monEntretien = Entretien.chercherEntretien(getTransaction(),peBaseInfosCourant.getCodeentretien());
		if(getTransaction().isErreur()){
			return false;
		}
		setEntretienCourant(monEntretien);
	}
	
	indice = (Services.estNumerique(getVAL_LB_TINTER_SELECT()) ? Integer.parseInt(getVAL_LB_TINTER_SELECT()): -1);
	TIntervalle monTIntervalle = (TIntervalle)getListeTintervalle().get(indice);
	setTIntervalleCourant(monTIntervalle);
	if (getTransaction().isErreur()){
		return false;
	}

	Modeles unModele = Modeles.chercherModeles(getTransaction(),getModeleInfosCourant().getCodemodele());
	setModeleCourant(unModele);
	
	//on controle les champs obligatoires
	if (newDuree.length() == 0) {
		getTransaction().declarerErreur("Vous devez renseigner la durée de l'entretien.");
		setFocus(getNOM_EF_DUREE());
		return false;
	}
	if (!tIntervalleCourant.getDesignation().trim().equals("sans")){
		if (newIntervalle.length() == 0) {
			getTransaction().declarerErreur("Vous devez renseigner l'intervalle de l'entretien.");
			setFocus(getNOM_EF_INTERVALLE());
			return false;
		}
	}else{
		newIntervalle="0";
	}
	
	if(newCommentaire.length()>200){
		getTransaction().declarerErreur("Le commentaire est trop long pour pouvoir être enregistré dans la base.");
		setFocus(getNOM_EF_COMMENTAIRE());
		return false;
	}
	
	// selon si création ou modification
	//	Si Action Modification
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {
	
		//Affectation des attributs
		getPeBaseCourant().setDuree(newDuree);
		getPeBaseCourant().setIntervalle(newIntervalle);
		getPeBaseCourant().setCommentaire(newCommentaire);
		getPeBaseCourant().setDesactive(newDesactiv);
		getPeBaseCourant().setDatedesactivation(newDate);
//		Affectation 
		if (newDesactiv.equals("OUI")){
			getPeBaseCourant().setDatedesactivation(newDate);
			getPeBaseCourant().setDesactive(newDesactiv);
		}
		
		//Modification
		getPeBaseCourant().modifierPeBase(getTransaction(),getModeleCourant(),getEntretienCourant(),getTIntervalleCourant());
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {		
		setPeBaseCourant(new PeBase());
		
		//Affectation des attributs
		getPeBaseCourant().setDuree(newDuree);
		getPeBaseCourant().setIntervalle(newIntervalle);
		getPeBaseCourant().setCommentaire(newCommentaire);
		getPeBaseCourant().setDesactive(newDesactiv);
		getPeBaseCourant().setDatedesactivation(newDate);
	
		//Création
		getPeBaseCourant().creerPeBase(getTransaction(),getModeleCourant(),getEntretienCourant(),getTIntervalleCourant());
		
	}
	if (getTransaction().isErreur()){
		return false;
	}else{
//		Tout s'est bien passé
		commitTransaction();
		//initialiseListeCompteur(request);
		//on vide les zones
		setLB_ENTRETIEN(LBVide);
		setLB_TINTER(LBVide);
		addZone(getNOM_EF_DUREE(),"");
		addZone(getNOM_EF_INTERVALLE(),"");
		addZone(getNOM_ST_TITRE_ACTION(),"");
		addZone(getNOM_ST_MARQUE(),"");
		addZone(getNOM_ST_MODELE(),"");
		addZone(getNOM_ST_TEQUIP(),"");
		setFocus(null);
		setStatut(STATUT_PROCESS_APPELANT);
		setIsModif("");
		setPeBaseInfosCourant(null);
		VariableGlobale.enlever(request,"PEBASEINFOS");
	}
	
	return true;
	
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TEQUIP
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TEQUIP() {
	return "NOM_ST_TEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TEQUIP
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TEQUIP() {
	return getZone(getNOM_ST_TEQUIP());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DUREE
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DUREE() {
	return "NOM_EF_DUREE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DUREE
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DUREE() {
	return getZone(getNOM_EF_DUREE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_INTERVALLE
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_INTERVALLE() {
	return "NOM_EF_INTERVALLE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_INTERVALLE
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_INTERVALLE() {
	return getZone(getNOM_EF_INTERVALLE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_ENTRETIEN
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
private String [] getLB_ENTRETIEN() {
	if (LB_ENTRETIEN == null)
		LB_ENTRETIEN = initialiseLazyLB();
	return LB_ENTRETIEN;
}
/**
 * Setter de la liste:
 * LB_ENTRETIEN
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
private void setLB_ENTRETIEN(java.lang.String[] newLB_ENTRETIEN) {
	LB_ENTRETIEN = newLB_ENTRETIEN;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_ENTRETIEN
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_ENTRETIEN() {
	return "NOM_LB_ENTRETIEN";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIEN_SELECT
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_ENTRETIEN_SELECT() {
	return "NOM_LB_ENTRETIEN_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_ENTRETIEN
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_ENTRETIEN() {
	return getLB_ENTRETIEN();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_ENTRETIEN
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_ENTRETIEN_SELECT() {
	return getZone(getNOM_LB_ENTRETIEN_SELECT());
}


/**
 * Getter de la liste avec un lazy initialize :
 * LB_TINTER
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
private String [] getLB_TINTER() {
	if (LB_TINTER == null)
		LB_TINTER = initialiseLazyLB();
	return LB_TINTER;
}
/**
 * Setter de la liste:
 * LB_TINTER
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
private void setLB_TINTER(java.lang.String[] newLB_TINTER) {
	LB_TINTER = newLB_TINTER;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_TINTER
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_TINTER() {
	return "NOM_LB_TINTER";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_TINTER_SELECT
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_TINTER_SELECT() {
	return "NOM_LB_TINTER_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_TINTER
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_TINTER() {
	return getLB_TINTER();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_TINTER
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_TINTER_SELECT() {
	return getZone(getNOM_LB_TINTER_SELECT());
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
	return getNOM_LB_ENTRETIEN();
}

	public ModeleInfos getModeleInfosCourant() {
		return modeleInfosCourant;
	}
	public void setModeleInfosCourant(ModeleInfos modeleInfosCourant) {
		this.modeleInfosCourant = modeleInfosCourant;
	}
	public PeBaseInfos getPeBaseInfosCourant() {
		return peBaseInfosCourant;
	}
	public void setPeBaseInfosCourant(PeBaseInfos peBaseInfosCourant) {
		this.peBaseInfosCourant = peBaseInfosCourant;
	}
	public ArrayList getListeEntretien() {
		return listeEntretien;
	}
	public void setListeEntretien(ArrayList listeEntretien) {
		this.listeEntretien = listeEntretien;
	}
	
	public ArrayList getListeTintervalle() {
		return listeTintervalle;
	}
	public void setListeTintervalle(ArrayList listeTintervalle) {
		this.listeTintervalle = listeTintervalle;
	}
	public Entretien getEntretienCourant() {
		return entretienCourant;
	}
	public void setEntretienCourant(Entretien entretienCourant) {
		this.entretienCourant = entretienCourant;
	}
	
	public TIntervalle getTIntervalleCourant() {
		return tIntervalleCourant;
	}
	public void setTIntervalleCourant(TIntervalle intervalleCourant) {
		tIntervalleCourant = intervalleCourant;
	}
	public PeBase getPeBaseCourant() {
		return peBaseCourant;
	}
	public void setPeBaseCourant(PeBase peBaseCourant) {
		this.peBaseCourant = peBaseCourant;
	}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_COMMENTAIRE
 * Date de création : (29/06/05 12:51:06)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_COMMENTAIRE() {
	return "NOM_EF_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_COMMENTAIRE
 * Date de création : (29/06/05 12:51:06)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_COMMENTAIRE() {
	return getZone(getNOM_EF_COMMENTAIRE());
}
	public Modeles getModeleCourant() {
		return modeleCourant;
	}
	public void setModeleCourant(Modeles modeleCourant) {
		this.modeleCourant = modeleCourant;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ENTRETIEN
 * Date de création : (29/06/05 15:35:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_ENTRETIEN() {
	return "NOM_ST_ENTRETIEN";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_ENTRETIEN
 * Date de création : (29/06/05 15:35:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_ENTRETIEN() {
	return getZone(getNOM_ST_ENTRETIEN());
}
	public String getIsModif() {
		return isModif;
	}
	public void setIsModif(String isModif) {
		this.isModif = isModif;
	}
	public boolean isManqueParam() {
		return manqueParam;
	}
	public void setManqueParam(boolean manqueParam) {
		this.manqueParam = manqueParam;
	}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATEDESACTIV
 * Date de création : (08/07/05 10:40:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DATEDESACTIV() {
	return "NOM_EF_DATEDESACTIV";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATEDESACTIV
 * Date de création : (08/07/05 10:40:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DATEDESACTIV() {
	return getZone(getNOM_EF_DATEDESACTIV());
}
/**
 * Retourne le nom de la case à cocher sélectionnée pour la JSP :
 * CK_DESACTIV
 * Date de création : (08/07/05 10:40:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_CK_DESACTIV() {
	return "NOM_CK_DESACTIV";
}
/**
 * Retourne la valeur de la case à cocher à afficher par la JSP pour la case à cocher  :
 * CK_DESACTIV
 * Date de création : (08/07/05 10:40:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_CK_DESACTIV() {
	return getZone(getNOM_CK_DESACTIV());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
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
 * Date de création : (23/08/05 10:01:58)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OePeBase_ajout.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (23/08/05 10:01:58)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (23/08/05 10:01:58)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
}
