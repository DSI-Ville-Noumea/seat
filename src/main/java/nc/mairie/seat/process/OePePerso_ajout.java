package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Entretien;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.PePersoInfos;
import nc.mairie.seat.metier.PePerso;
import nc.mairie.seat.metier.TIntervalle;
import nc.mairie.seat.metier.TypeEntretien;
import nc.mairie.technique.*;
/**
 * Process OePePerso_ajout
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
*/
public class OePePerso_ajout extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -663249459810213310L;
	private java.lang.String[] LB_ENTRETIEN;
	private java.lang.String[] LB_TINTER;
	private String ACTION_MODIFICATION = "Modification";
	private String ACTION_CREATION = "Création";
	private String focus = null;
	private ArrayList<TIntervalle> listeTintervalle;
	private ArrayList<Entretien> listeEntretien;
	private ArrayList<TypeEntretien> listeTEnt;
	private PePersoInfos pePersoInfosCourant;
	private TIntervalle tIntervalleCourant;
	private Entretien entretienCourant;
	private Equipement equipementCourant;
	private Modeles modeleCourant;
	private PePerso pePersoCourant;
	private EquipementInfos equipementInfosCourant;
	private TypeEntretien typeEntretienCourant;
	private String isModif="";
	private boolean isOcassionnel;
	private String intervalle = "";
	private String duree = "";
	private String commentaire = "";
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
	EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request, "EQUIPEMENTINFOS");
	setEquipementInfosCourant(unEquipementInfos);
	if (getEquipementInfosCourant()!=null){
		Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),equipementInfosCourant.getNumeroinventaire());
		if (getTransaction().isErreur()){
			return;
		}
		setEquipementCourant(unEquipement);
	}
	
	PePersoInfos unPePersoInfos = (PePersoInfos)VariableGlobale.recuperer(request, "PEPERSOINFOS");
	if (unPePersoInfos!=null){
		setPePersoInfosCourant(unPePersoInfos);
	}
	
	// zones concernant l'équipement
	if (getEquipementInfosCourant()!=null){
		if(getEquipementCourant().getNumeroinventaire()!=null){
			Modeles unModele = Modeles.chercherModeles(getTransaction(),getEquipementCourant().getCodemodele());
			if(getTransaction().isErreur()){
				return;
			}
			setModeleCourant(unModele);
		}
		addZone(getNOM_ST_NOINVENT(),equipementInfosCourant.getNumeroinventaire());
		addZone(getNOM_ST_NOIMMAT(),equipementInfosCourant.getNumeroimmatriculation());
		addZone(getNOM_ST_TYPE(),equipementInfosCourant.getDesignationtypeequip());
		addZone(getNOM_ST_NOMEQUIP(),equipementInfosCourant.getDesignationmarque()+" "+equipementInfosCourant.getDesignationmodele());
		//	zones concernant le PePerso
		if (getPePersoInfosCourant()==null){
			addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
			//addZone(getNOM_EF_DUREE(),"");
			//addZone(getNOM_EF_INTERVALLE(),"");
			addZone(getNOM_LB_ENTRETIEN_SELECT(),"0");
			addZone(getNOM_LB_TINTER_SELECT(),"0");
		}else{
			// si en modification
			if(null!=unPePersoInfos.getCodepep()){
				PePerso unPePerso = PePerso.chercherPePerso(getTransaction(),pePersoInfosCourant.getCodepep());
				if (getTransaction().isErreur()){
					return ;
				}
				setPePersoCourant(unPePerso);
				addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
				commentaire = pePersoInfosCourant.getCommentairete();
				duree = pePersoInfosCourant.getDuree();
				intervalle = pePersoInfosCourant.getIntervalle();
				addZone(getNOM_ST_ENTRETIEN(),pePersoInfosCourant.getLibelleentretien());
				setIsModif("Modif");
//				 on coche la case si c'est sinistre
				if ("oui".equals(unPePersoInfos.getSinistre().trim())){
					addZone(getNOM_CK_SINISTRE(),getCHECKED_ON());
				}else{
					addZone(getNOM_CK_SINISTRE(),getCHECKED_OFF());
				}
				TIntervalle unTIntervalle = TIntervalle.chercherTIntervalle(getTransaction(),pePersoInfosCourant.getCodeti());
				if (getTransaction().isErreur()){
					return;
				}
				setTIntervalleCourant(unTIntervalle);
				Entretien unEntretien = Entretien.chercherEntretien(getTransaction(),pePersoInfosCourant.getCodeentretien());
				if (getTransaction().isErreur()){
					return;
				}
				setEntretienCourant(unEntretien);
				TypeEntretien unTypeEntretien = TypeEntretien.chercherTypeEntretien(getTransaction(),pePersoInfosCourant.getCodetypeent());
				if(getTransaction().isErreur()){
					return;
				}
				setTypeEntretienCourant(unTypeEntretien);
				if(unTypeEntretien.getCodetypeent().equals("2")){
					setOcassionnel(true);
				}else{
					setOcassionnel(false);
				}
			}
		}
	}else{
		addZone(getNOM_ST_NOINVENT(),"");
		addZone(getNOM_ST_NOIMMAT(),"");
		addZone(getNOM_ST_TYPE(),"");
		addZone(getNOM_ST_NOMEQUIP(),"");
	}
//	 on remplit les listes déroulante
//	 Si liste des tintervalle est vide
	//if (getLB_TINTER() == LBVide) {
	ArrayList<TIntervalle> a = TIntervalle.listerTIntervalle(getTransaction());
	setListeTintervalle(a);
	//les élèments de la liste 
	int [] tailles = {10};
	String [] champs = {"designation"};
	String [] padding = {"G"};
	setLB_TINTER(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
	
	if(getTIntervalleCourant()!=null){
		//	recherche du type d'intervalle courant
		addZone(getNOM_LB_TINTER_SELECT(),String.valueOf(-1));
		for (int i = 0; i < getListeTintervalle().size(); i++) {
			TIntervalle unTIntervalle = (TIntervalle)getListeTintervalle().get(i);
			if (unTIntervalle.getCodeti().equals(getTIntervalleCourant().getCodeti())) {
				addZone(getNOM_LB_TINTER_SELECT(),String.valueOf(i));
				break;
			}
		}
	}
	//}
//	 Si liste des types d'entretien est vide
	//if (getLB_TENT() == LBVide) {
	ArrayList<TypeEntretien> aTEnt = TypeEntretien.listerTypeEntretien(getTransaction());
	setListeTEnt(aTEnt);
	//les élèments de la liste 
	int [] taillesTent = {15};
	String [] champsTent = {"designationtypeent"};
	String [] paddingTent = {"G"};
	setLB_TENT(new FormateListe(taillesTent,aTEnt,champsTent,paddingTent,false).getListeFormatee());
	
	if(getTypeEntretienCourant()!=null){
		//	recherche de l'entretien courant
		addZone(getNOM_LB_TENT_SELECT(),String.valueOf(-1));
		for (int i = 0; i < getListeTEnt().size(); i++) {
			TypeEntretien unTEntretien = (TypeEntretien)getListeTEnt().get(i);
			if (unTEntretien.getCodetypeent().equals(getTypeEntretienCourant().getCodetypeent())) {
				addZone(getNOM_LB_TENT_SELECT(),String.valueOf(i));
				break;
			}
		}
	}
	//}
//	 Si liste des entretien est vide
	//if (getLB_ENTRETIEN() == LBVide) {
	ArrayList<Entretien> aEnt = Entretien.listerEntretien(getTransaction());
	setListeEntretien(aEnt);
	//les élèments de la liste 
	int [] taillesEnt = {20};
	String [] champsEnt = {"libelleentretien"};
	String [] paddingEnt = {"G"};
	setLB_ENTRETIEN(new FormateListe(taillesEnt,aEnt,champsEnt,paddingEnt,false).getListeFormatee());
	
	if(getEntretienCourant()!=null){
		//	recherche de l'entretien courant
		addZone(getNOM_LB_ENTRETIEN_SELECT(),String.valueOf(-1));
		for (int i = 0; i < getListeEntretien().size(); i++) {
			Entretien unEntretien = (Entretien)getListeEntretien().get(i);
			if (unEntretien.getCodeentretien().equals(getEntretienCourant().getCodeentretien())) {
				addZone(getNOM_LB_ENTRETIEN_SELECT(),String.valueOf(i));
				break;
			}
		}
	}
	//initialisation des zones
	addZone(getNOM_EF_COMMENTAIRE(),commentaire);
	addZone(getNOM_EF_DUREE(),duree);
	addZone(getNOM_EF_INTERVALLE(),intervalle);
	//}


}
/**
 * Constructeur du process OePePerso_ajout.
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 */
public OePePerso_ajout() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (29/06/05 07:38:35)
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
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
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
	addZone(getNOM_ST_NOINVENT(),"");
	addZone(getNOM_ST_NOIMMAT(),"");
	addZone(getNOM_ST_TYPE(),"");
	addZone(getNOM_ST_NOMEQUIP(),"");
	setFocus(null);
	setPePersoInfosCourant(null);
	VariableGlobale.enlever(request,"PEPERSOINFOS");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (29/06/05 07:38:35)
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
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//on rècupère les zones
	String newDuree = getZone(getNOM_EF_DUREE());
	String newIntervalle = getZone(getNOM_EF_INTERVALLE());
	String newCommentaire = getZone(getNOM_EF_COMMENTAIRE()).toUpperCase();
	String sinistre = getZone(getNOM_CK_SINISTRE());
	// si sinistre coché on met oui dans sinistre
	String newSinistre = sinistre.equals(getCHECKED_ON()) ? "OUI" : "NON";
	//sélection de l'entretien
	int indice = (Services.estNumerique(getVAL_LB_ENTRETIEN_SELECT()) ? Integer.parseInt(getVAL_LB_ENTRETIEN_SELECT()): -1);
	Entretien monEntretien = (Entretien)getListeEntretien().get(indice);
	if(getTransaction().isErreur()){
		return false;
	}
	setEntretienCourant(monEntretien);
	
	//sélection du type d'intervalle
	indice = (Services.estNumerique(getVAL_LB_TINTER_SELECT()) ? Integer.parseInt(getVAL_LB_TINTER_SELECT()): -1);
	TIntervalle monTIntervalle = (TIntervalle)getListeTintervalle().get(indice);
	if (getTransaction().isErreur()){
		return false;
	}
	
	setTIntervalleCourant(monTIntervalle);
//	sélection du type d'entretien
	indice = (Services.estNumerique(getVAL_LB_TENT_SELECT()) ? Integer.parseInt(getVAL_LB_TENT_SELECT()): -1);
	TypeEntretien monTent = (TypeEntretien)getListeTEnt().get(indice);
	setTypeEntretienCourant(monTent);
	
	//on controle les champs obligatoires
	if (newDuree.length() == 0) {
		getTransaction().declarerErreur("Vous devez renseigner la durée de l'entretien.");
		setFocus(getNOM_EF_DUREE());
		return false;
	}
	// si le type d'entretien est différent d'occasionnel...
	if (!getTypeEntretienCourant().getCodetypeent().trim().equals("2")){
		if (newIntervalle.length() == 0) {
			getTransaction().declarerErreur("Vous devez renseigner l'intervalle de l'entretien.");
			setFocus(getNOM_EF_INTERVALLE());
			return false;
		}
	}else{
		newIntervalle="0";
	}
	
	if(newCommentaire.length()>200){
		getTransaction().declarerErreur("Le commentaire est trop long pour pouvoir être enregistré.");
		setFocus(getNOM_EF_COMMENTAIRE());
		return false;
	}
	
	// selon si création ou modification
	//	Si Action Modification
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {
	
		//Affectation des attributs
		getPePersoCourant().setDuree(newDuree);
		getPePersoCourant().setIntervallepep(newIntervalle);
		getPePersoCourant().setCommentairete(newCommentaire);
		getPePersoCourant().setSinistre(newSinistre);
		
		
		//Modification
		getPePersoCourant().modifierPePerso(getTransaction(),getEquipementCourant(),getEntretienCourant(),getTypeEntretienCourant(),getTIntervalleCourant());
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {		
		setPePersoCourant(new PePerso());
		
		//Affectation des attributs
		getPePersoCourant().setDuree(newDuree);
		getPePersoCourant().setIntervallepep(newIntervalle);
		getPePersoCourant().setCommentairete(newCommentaire);
		getPePersoCourant().setSinistre(newSinistre);
	
		//Création
		getPePersoCourant().creerPePerso(getTransaction(),getEquipementCourant(),getEntretienCourant(),getTypeEntretienCourant(),getTIntervalleCourant());
		
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
		addZone(getNOM_ST_NOINVENT(),"");
		addZone(getNOM_ST_NOIMMAT(),"");
		addZone(getNOM_ST_TYPE(),"");
		addZone(getNOM_ST_NOMEQUIP(),"");
		setFocus(null);
		setStatut(STATUT_PROCESS_APPELANT);
		setIsModif("");
		setPePersoInfosCourant(null);
		VariableGlobale.enlever(request,"PEPERSOINFOS");
	}
	
	return true;
}

/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TEQUIP
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TEQUIP() {
	return "NOM_ST_TEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TEQUIP
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TEQUIP() {
	return getZone(getNOM_ST_TEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TIRE_ACTION
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TIRE_ACTION
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DUREE
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DUREE() {
	return "NOM_EF_DUREE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DUREE
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DUREE() {
	return getZone(getNOM_EF_DUREE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_INTERVALLE
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_INTERVALLE() {
	return "NOM_EF_INTERVALLE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_INTERVALLE
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 * @return String
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
 * @return String
 */
public java.lang.String getNOM_LB_ENTRETIEN() {
	return "NOM_LB_ENTRETIEN";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIEN_SELECT
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 * @return String
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
 * @return String
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
 * @return String
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
 * @return String
 */
public java.lang.String getNOM_LB_TINTER() {
	return "NOM_LB_TINTER";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_TINTER_SELECT
 * Date de création : (29/06/05 07:38:35)
 * @author : Générateur de process
 * @return String
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
 * @return String
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
 * @return String
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
 * focus focus à définir.
 * @return focus
 */
public String getDefaultFocus() {
	return getNOM_LB_ENTRETIEN();
}

	public PePersoInfos getPePersoInfosCourant() {
		return pePersoInfosCourant;
	}
	public void setPePersoInfosCourant(PePersoInfos pePersoInfosCourant) {
		this.pePersoInfosCourant = pePersoInfosCourant;
	}
	public ArrayList<Entretien> getListeEntretien() {
		return listeEntretien;
	}
	public void setListeEntretien(ArrayList<Entretien> listeEntretien) {
		this.listeEntretien = listeEntretien;
	}
	
	public ArrayList<TIntervalle> getListeTintervalle() {
		return listeTintervalle;
	}
	public void setListeTintervalle(ArrayList<TIntervalle> listeTintervalle) {
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
	public PePerso getPePersoCourant() {
		return pePersoCourant;
	}
	public void setPePersoCourant(PePerso pePersoCourant) {
		this.pePersoCourant = pePersoCourant;
	}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_COMMENTAIRE
 * Date de création : (29/06/05 12:51:06)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_COMMENTAIRE() {
	return "NOM_EF_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_COMMENTAIRE
 * Date de création : (29/06/05 12:51:06)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_COMMENTAIRE() {
	return getZone(getNOM_EF_COMMENTAIRE());
}
	
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ENTRETIEN
 * Date de création : (29/06/05 15:35:12)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_ENTRETIEN() {
	return "NOM_ST_ENTRETIEN";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_ENTRETIEN
 * Date de création : (29/06/05 15:35:12)
 * @author : Générateur de process
 * @return String
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
	public EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}
	public TypeEntretien getTypeEntretienCourant() {
		return typeEntretienCourant;
	}
	public void setTypeEntretienCourant(TypeEntretien typeEntretienCourant) {
		this.typeEntretienCourant = typeEntretienCourant;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (05/07/05 13:32:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (05/07/05 13:32:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (05/07/05 13:32:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (05/07/05 13:32:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOMEQUIP
 * Date de création : (05/07/05 13:32:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOMEQUIP() {
	return "NOM_ST_NOMEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOMEQUIP
 * Date de création : (05/07/05 13:32:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOMEQUIP() {
	return getZone(getNOM_ST_NOMEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (05/07/05 13:32:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (05/07/05 13:32:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
	private java.lang.String[] LB_TENT;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_TENT
 * Date de création : (05/07/05 13:45:10)
 * @author : Générateur de process
 */
private String [] getLB_TENT() {
	if (LB_TENT == null)
		LB_TENT = initialiseLazyLB();
	return LB_TENT;
}
/**
 * Setter de la liste:
 * LB_TENT
 * Date de création : (05/07/05 13:45:10)
 * @author : Générateur de process
 */
private void setLB_TENT(java.lang.String[] newLB_TENT) {
	LB_TENT = newLB_TENT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_TENT
 * Date de création : (05/07/05 13:45:10)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_TENT() {
	return "NOM_LB_TENT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_TENT_SELECT
 * Date de création : (05/07/05 13:45:10)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_TENT_SELECT() {
	return "NOM_LB_TENT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_TENT
 * Date de création : (05/07/05 13:45:10)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_TENT() {
	return getLB_TENT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_TENT
 * Date de création : (05/07/05 13:45:10)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_TENT_SELECT() {
	return getZone(getNOM_LB_TENT_SELECT());
}
public ArrayList<TypeEntretien> getListeTEnt() {
	return listeTEnt;
}
public void setListeTEnt(ArrayList<TypeEntretien> listeTEnt) {
	this.listeTEnt = listeTEnt;
}
	public Equipement getEquipementCourant() {
		return equipementCourant;
	}
	public void setEquipementCourant(Equipement equipementCourant) {
		this.equipementCourant = equipementCourant;
	}
/**
 * Retourne le nom de la case à cocher sélectionnée pour la JSP :
 * CK_SINISTRE
 * Date de création : (05/07/05 14:07:46)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_CK_SINISTRE() {
	return "NOM_CK_SINISTRE";
}
/**
 * Retourne la valeur de la case à cocher à afficher par la JSP pour la case à cocher  :
 * CK_SINISTRE
 * Date de création : (05/07/05 14:07:46)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_CK_SINISTRE() {
	return getZone(getNOM_CK_SINISTRE());
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

		//Si clic sur le bouton PB_OK_TINT
		if (testerParametre(request, getNOM_PB_OK_TENT())) {
			return performPB_OK_TENT(request);
		}

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
 * Date de création : (12/07/05 15:02:31)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OePePerso_ajout.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_TINT
 * Date de création : (12/07/05 15:02:31)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OK_TENT() {
	return "NOM_PB_OK_TENT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (12/07/05 15:02:31)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK_TENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice = (Services.estNumerique(getVAL_LB_ENTRETIEN_SELECT()) ? Integer.parseInt(getVAL_LB_ENTRETIEN_SELECT()): -1);
	Entretien monEntretien = (Entretien)getListeEntretien().get(indice);
	if(getTransaction().isErreur()){
		return false;
	}
	setEntretienCourant(monEntretien);
	intervalle = getZone(getNOM_EF_INTERVALLE());
	duree = getZone(getNOM_EF_DUREE());
	commentaire = getZone(getNOM_EF_COMMENTAIRE());
	//	sélection du type d'entretien
	indice = (Services.estNumerique(getVAL_LB_TENT_SELECT()) ? Integer.parseInt(getVAL_LB_TENT_SELECT()): -1);
	TypeEntretien monTypeEntretien = (TypeEntretien)getListeTEnt().get(indice);
	if (monTypeEntretien.getCodetypeent().trim().equals("2")){
		setOcassionnel(true);
	}else{
		setOcassionnel(false);
	}
	setTypeEntretienCourant(monTypeEntretien);
	
//	sélection du type d'intervalle
	indice = (Services.estNumerique(getVAL_LB_TINTER_SELECT()) ? Integer.parseInt(getVAL_LB_TINTER_SELECT()): -1);
	TIntervalle monTIntervalle = (TIntervalle)getListeTintervalle().get(indice);
	setTIntervalleCourant(monTIntervalle);
	return true;
}
public boolean getIsOcassionnel() {
	return isOcassionnel;
}
public void setOcassionnel(boolean isOcassionnel) {
	this.isOcassionnel = isOcassionnel;
}
	public Modeles getModeleCourant() {
		return modeleCourant;
	}
	public void setModeleCourant(Modeles modeleCourant) {
		this.modeleCourant = modeleCourant;
	}
}
