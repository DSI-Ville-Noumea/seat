package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.Services;
import nc.mairie.seat.metier.PeBase;
import nc.mairie.seat.process.Outils;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Equipement
 */
public class Equipement extends BasicMetier {
	public String numeroinventaire;
	public String numeroimmatriculation;
	public String datemiseencirculation;
	public String datereceptionmateriel;
	public String datehorscircuit;
	public String dateventeoureforme;
	public String prixachat;
	public String reserve;
	public String codemodele;
	public String version;
	public String dureegarantie;
/**
 * Getter de l'attribut version.
 */
public String getVersion() {
	return version;
}
/**
 * Setter de l'attribut version.
 */
public void setVersion(String newVersion) { 
	version = newVersion;
}
/**
* Renvoie une chaîne correspondant à la valeur de cet objet.
* @return une représentation sous forme de chaîne du destinataire
*/
@Override
public String toString() {
	// Insérez ici le code pour finaliser le destinataire
	// Cette implémentation transmet le message au super. Vous pouvez remplacer ou compléter le message.
	return super.toString();
}
/**
 * Retourne un ArrayList d'objet métier : Equipement.
 * @return java.util.ArrayList
 */
public static ArrayList<Equipement> listerEquipement(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Equipement unEquipement = new Equipement();
	return unEquipement.getMyEquipementBroker().listerEquipement(aTransaction);
}
/**
 * Retourne un Equipement.
 * @return Equipement
 */
public static Equipement chercherEquipement(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Equipement unEquipement = new Equipement();
	return unEquipement.getMyEquipementBroker().chercherEquipement(aTransaction, code);
}
/**
 * Retourne un Equipement.
 * @return Equipement
 */
public static Equipement chercherEquipementInv(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Equipement unEquipement = new Equipement();
	return unEquipement.getMyEquipementBroker().chercherEquipementInv(aTransaction, code);
}
/*vérifie que les champs sont bien renseignés
 * Date : 01/09/05
 * autheur : NC
 */
public boolean controleChamps(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	// le numéro d'inventaire est un varchar donc on accepte les lettres
	/*if((getNumeroinventaire()!=null)&&(!getNumeroinventaire().equals(""))){
		if(!Services.estNumerique(getNumeroinventaire())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour le numéro d'inventaire.");
			return false;
		}
	}*/
	if((getPrixachat()!=null)&&(!getPrixachat().equals(""))){
		setPrixachat(Outils.enleveEspace(getPrixachat()));
		if(!Services.estNumerique(getPrixachat())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour le prix d'achat.");
			return false;
		}
	}
	if((getDureegarantie()!=null)&&(!getDureegarantie().equals(""))){
		setDureegarantie(Outils.enleveEspace(getDureegarantie()));
		if(!Services.estNumerique(getDureegarantie())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour la durée de garantie.");
			return false;
		}
	}
	return true;
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerEquipement(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement)  throws Exception {
	//on controle si null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	//RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	//on vérifie qu'un autre équipement n'a pas déjà cette immatriculation
	//Equipement unEquipement = chercherEquipementImmat(aTransaction,getNumeroimmatriculation());
	int compte = chercherEquipementImmat(aTransaction,getNumeroimmatriculation(),getNumeroinventaire());
	if (aTransaction.isErreur()){
		return false;
	}
	if (compte>0){
		aTransaction.declarerErreur("Un équipement avec ce numéro d'immatriculation est déjà enregistré.");
		return false;
	}else{
		// création de l'équipement
		boolean creatOK = getMyEquipementBroker().creerEquipement(aTransaction);
		if (!creatOK)
			return false;
		//Creation du plan d'entretien personnalisé
		ArrayList<PeBase> aEntretien = PeBase.listerPeBaseModeleActif(aTransaction,getCodemodele());
		
		//Si au moins un entretien
		if (aEntretien.size() !=0 ) {					
			for (java.util.ListIterator<PeBase> list = aEntretien.listIterator(); list.hasNext(); ) {
				PeBase aPeBase = (PeBase)list.next();
				Entretien unEntretien = Entretien.chercherEntretien(aTransaction,aPeBase.getCodeentretien());
				if(aTransaction.isErreur()){
					return false;
				}
				TIntervalle unTi = TIntervalle.chercherTIntervalle(aTransaction,aPeBase.getCodeti());
				if (aTransaction.isErreur()){
					return false;
				}
				TypeEntretien unTypeEntretien = new TypeEntretien();
				// création du plan d'entretien personnalisé
				PePerso unPePerso = new PePerso();
				unPePerso.setIntervallepep(aPeBase.getIntervalle());
				unPePerso.setDuree(aPeBase.getDuree());
				if (!unPePerso.creerPePerso(aTransaction,unEquipement,unEntretien,unTypeEntretien,unTi)){
					return false;
				}
			}
		}
		return true;
	}
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierEquipement(nc.mairie.technique.Transaction aTransaction,String ancienNuminv) throws Exception {
	//RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	
	// on vérifie que l'équipement n'est pas déjà utilisé
	if(!ancienNuminv.equals(getNumeroinventaire())){
		// BPC
		ArrayList<BPC> listBPC = BPC.listerBPCEquipement(aTransaction,ancienNuminv);
		if(aTransaction.isErreur()){
			aTransaction.traiterErreur();
		}
		if(listBPC!=null){
			if(listBPC.size()>0){
				aTransaction.declarerErreur("La modification du numéro d'inventaire n'est pas possible car des BPC ont été fait pour cet équipement.");
				return false;
			}
		}
		//OT
		ArrayList<OT> listOT = OT.listerOTEquip(aTransaction,ancienNuminv);
		if(aTransaction.isErreur()){
			aTransaction.traiterErreur();
		}
		if(listOT!=null){
			if(listOT.size()>0){
				aTransaction.declarerErreur("La modification du numéro d'inventaire n'est pas possible car des OT ont été fait pour cet équipement.");
				return false;
			}
		}
		//Déclarations
		ArrayList<Declarations> listDecl = Declarations.listerDeclarationsEquip(aTransaction,ancienNuminv);
		if(aTransaction.isErreur()){
			aTransaction.traiterErreur();
		}
		if(listDecl!=null){
			if(listDecl.size()>0){
				aTransaction.declarerErreur("La modification du numéro d'inventaire n'est pas possible car des déclarations ont été faites pour cet équipement.");
				return false;
			}
		}
		//Affectation service
		ArrayList<Affecter_Service> listAffSce = Affecter_Service.chercherListAffecter_ServiceEquip(aTransaction,ancienNuminv);
		if(aTransaction.isErreur()){
			aTransaction.traiterErreur();
		}
		if(listAffSce!=null){
			if(listAffSce.size()>0){
				aTransaction.declarerErreur("La modification du numéro d'inventaire n'est pas possible car cet équipement a été affecté à au moins un service.");
				return false;
			}
		}
		//Affectation agent
		ArrayList<Affecter_Agent> listAffAgent = Affecter_Agent.chercherListAffecter_AgentEquip(aTransaction,ancienNuminv);
		if(aTransaction.isErreur()){
			aTransaction.traiterErreur();
		}
		if(listAffAgent!=null){
			if(listAffAgent.size()>0){
				aTransaction.declarerErreur("La modification du numéro d'inventaire n'est pas possible car cet équipement a été à un agent.");
				return false;
			}
		}
		// Pe Perso
		ArrayList<PePerso> listPePerso = PePerso.listerPePersoEquip(aTransaction,ancienNuminv);
		if(aTransaction.isErreur()){
			aTransaction.traiterErreur();
		}
		if(listPePerso!=null){
			if(listPePerso.size()>0){
				aTransaction.declarerErreur("La modification du numéro d'inventaire n'est pas possible car cet équipement a des entretiens rattachés.");
				return false;
			}
		}
	}
	
//	on vérifie qu'un autre équipement n'a pas déjà cette immatriculation
	//Equipement unEquipement = chercherEquipementImmat(aTransaction,getNumeroimmatriculation());
	int compte = chercherEquipementImmat(aTransaction,getNumeroimmatriculation(),ancienNuminv);
	//compte = chercherEquipementImmat(aTransaction,getNumeroimmatriculation(),getNumeroinventaire());
	if (aTransaction.isErreur()){
		return false;
	}
	if (compte>0){
		aTransaction.declarerErreur("Un équipement avec ce numéro d'immatriculation est déjà enregistré.");
		return false;
	}else{
//		 on contrôle les dates hors circuit et date de vente
		//if (!"01/01/0001".equals(getDatehorscircuit())){
		if ((getDatehorscircuit()!=null)&&(!"".equals(getDatehorscircuit()))){
			if(!Services.estUneDate(getDatehorscircuit())){
				aTransaction.declarerErreur("La date hors service n'est pas correcte.");
				return false;
			}else{
				setDatehorscircuit(Services.formateDate(getDatehorscircuit()));
			}
			int compareHc = Services.compareDates(getDatehorscircuit(),getDatemiseencirculation());
			if (compareHc<-1){
				return false;
			}else if (compareHc==-1){
				aTransaction.declarerErreur("La date hors circuit doit être supérieur ou égale à la date de mise en circulation.("+getDatemiseencirculation()+")");
				return false;
			}
		}
		//if (!"01/01/0001".equals(getDateventeoureforme())){
		if ((getDateventeoureforme()!=null)&&(!"".equals(getDateventeoureforme()))){
			if(!Services.estUneDate(getDateventeoureforme())){
				aTransaction.declarerErreur("La date de vente ou réforme n'est pas correcte.");
				return false;
			}else{
				setDateventeoureforme(Services.formateDate(getDateventeoureforme()));
			}
			int compareHc = Services.compareDates(getDateventeoureforme(),getDatemiseencirculation());
			if (compareHc<-1){
				return false;
			}else if (compareHc==-1){
				aTransaction.declarerErreur("La date de vente ou réforme doit être supérieur ou égale à la date de mise en circulation.("+getDatemiseencirculation()+")");
				return false;
			}
		}
		//on modifie les date prévues pour les entretiens
		String tri = "";
		ArrayList<PePersoInfos> listEntretien = PePersoInfos.listerPePersoInfosEquip(aTransaction,getNumeroinventaire(),tri);
		if (listEntretien.size()>0){
			for (int i = 0;i<listEntretien.size();i++){
				//on regarde si les dates de prévus sont renseignées ou pas
				PePersoInfos unPePersoInfos = (PePersoInfos)listEntretien.get(i);
				if (!unPePersoInfos.getDateprev().equals("01/01/0001")){
					PePerso unPePerso = PePerso.chercherPePerso(aTransaction,unPePersoInfos.getCodepep());
					if (aTransaction.isErreur()){
						return false;
					}
					unPePerso.modifierPePersoInfos(aTransaction);
				}
			}
		}
		
//		Modification du Equipement
		return getMyEquipementBroker().modifierEquipement(aTransaction);
	}
	
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerEquipement(nc.mairie.technique.Transaction aTransaction) throws Exception{
	// si utilisé pas de suppression
	BPC unBPC = new BPC();
	if (!unBPC.existeBPCEquip(aTransaction,getNumeroinventaire())){
		//Suppression de l'Equipement
		return getMyEquipementBroker().supprimerEquipement(aTransaction);
	}else{
		aTransaction.declarerErreur("Cet équipement est utilisé pour un BPC.La suppression n'est pas possible.");
		return false;
	}
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */

public static ArrayList<Equipement> listerEquipementTri(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception{
	Equipement unEquipement = new Equipement();
	return unEquipement.getMyEquipementBroker().listerEquipementTri(aTransaction,tri);
}

/**
 * Retourne un ArrayList d'objet métier : Equipement.
 * @return java.util.ArrayList
 * Selon le paramètre on liste les équipements
 */
public static ArrayList<Equipement> listerEquipementParam(nc.mairie.technique.Transaction aTransaction,String param,String tri) throws Exception{
	Equipement unEquipement = new Equipement();
	return unEquipement.getMyEquipementBroker().listerEquipementParam(aTransaction,param,tri);
}

/**
 * Getter de l'attribut datereceptionmateriel.
 */
public String getDatereceptionmateriel() {
	return datereceptionmateriel;
}
/**
 * Setter de l'attribut datereceptionmateriel.
 */
public void setDatereceptionmateriel(String newDatereceptionmateriel) { 
	datereceptionmateriel = newDatereceptionmateriel;
}
/**
 * Constructeur Equipement.
 */
public Equipement() {
	super();
}
/**
 * Getter de l'attribut numeroinventaire.
 */
public String getNumeroinventaire() {
	return numeroinventaire;
}
/**
 * Setter de l'attribut numeroinventaire.
 */
public void setNumeroinventaire(String newNumeroinventaire) { 
	numeroinventaire = newNumeroinventaire;
}
/**
 * Getter de l'attribut numeroimmatriculation.
 */
public String getNumeroimmatriculation() {
	return numeroimmatriculation;
}
/**
 * Setter de l'attribut numeroimmatriculation.
 */
public void setNumeroimmatriculation(String newNumeroimmatriculation) { 
	numeroimmatriculation = newNumeroimmatriculation;
}
/**
 * Getter de l'attribut datemiseencirculation.
 */
public String getDatemiseencirculation() {
	return datemiseencirculation;
}
/**
 * Setter de l'attribut datemiseencirculation.
 */
public void setDatemiseencirculation(String newDatemiseencirculation) { 
	datemiseencirculation = newDatemiseencirculation;
}
/**
 * Getter de l'attribut dateventeoureforme.
 */
public String getDateventeoureforme() {
	return dateventeoureforme;
}
/**
 * Setter de l'attribut dateventeoureforme.
 */
public void setDateventeoureforme(String newDateventeoureforme) { 
	dateventeoureforme = newDateventeoureforme;
}
/**
 * Getter de l'attribut datehorscircuit.
 */
public String getDatehorscircuit() {
	return datehorscircuit;
}
/**
 * Setter de l'attribut datehorscircuit.
 */
public void setDatehorscircuit(String newDatehorscircuit) { 
	datehorscircuit = newDatehorscircuit;
}
/**
 * Getter de l'attribut prixachat.
 */
public String getPrixachat() {
	return prixachat;
}
/**
 * Setter de l'attribut prixachat.
 */
public void setPrixachat(String newPrixachat) { 
	prixachat = newPrixachat;
}
/**
 * Getter de l'attribut reserve.
 */
public String getReserve() {
	return reserve;
}
/**
 * Setter de l'attribut reserve.
 */
public void setReserve(String newReserve) { 
	reserve = newReserve;
}
/**
 * Getter de l'attribut codemodele.
 */
public String getCodemodele() {
	return codemodele;
}
/**
 * Setter de l'attribut codemodele.
 */
public void setCodemodele(String newCodemodele) { 
	codemodele = newCodemodele;
}
/**
 * Getter de l'attribut dureegarantie.
 */
public String getDureegarantie() {
	return dureegarantie;
}
/**
 * Setter de l'attribut dureegarantie.
 */
public void setDureegarantie(String newDureegarantie) { 
	dureegarantie = newDureegarantie;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new EquipementBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected EquipementBroker getMyEquipementBroker() {
	return (EquipementBroker)getMyBasicBroker();
}

/**
 * Retourne un Equipement.
 * @return Equipement
 */
public static int chercherEquipementImmat(nc.mairie.technique.Transaction aTransaction, String immat,String inv) throws Exception{
	Equipement unEquipement = new Equipement();
	return unEquipement.getMyEquipementBroker().chercherEquipementImmat(aTransaction, immat,inv);
}

/**
 * Retourne un booléen.
 * Vérifie que le bpc existe
 * @return BPC
 */
public boolean existeEquipement(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Equipement unEquipement = new Equipement();
	return unEquipement.getMyEquipementBroker().existeEquipement(aTransaction, param);
}

/**
 * Retourne un booléen.
 * Vérifie que le modèle existe avec des paramètres de clé étrangères
 * @return BPC
 */
public boolean existeEquipementModele(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Equipement unEquipement = new Equipement();
	return unEquipement.getMyEquipementBroker().existeEquipementModele(aTransaction, param);
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */

public static ArrayList<Equipement> listerEquipementModele(nc.mairie.technique.Transaction aTransaction,String mod) throws Exception{
	Equipement unEquipement = new Equipement();
	return unEquipement.getMyEquipementBroker().listerEquipementModele(aTransaction,mod);
}

}
