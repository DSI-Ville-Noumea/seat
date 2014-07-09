package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.seat.process.Outils;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier PMateriel
 */
public class PMateriel extends BasicMetier {
	public String pminv;
	public String pmserie;
	public String codemodele;
	public String codemarque;
	public String codetypeequip;
	public String codefre;
	public String dgarantie;
	public String reserve;
	public String dmes;
	public String dmhs;
	public String prix;
	public String dachat;
/**
 * Getter de l'attribut codemarque.
 * @return String
 */
public String getCodemarque() {
	return codemarque;
}
/**
 * Setter de l'attribut codemarque.
 */
/**
 * @param newCodemarque newCodemarque
 */
public void setCodemarque(String newCodemarque) { 
	codemarque = newCodemarque;
}
/**
 * Getter de l'attribut codetypeequip.
 * @return String
 */
public String getCodetypeequip() {
	return codetypeequip;
}
/**
 * Setter de l'attribut codetypeequip.
 */
/**
 * @param newCodetypeequip newCodetypeequip
 */
public void setCodetypeequip(String newCodetypeequip) { 
	codetypeequip = newCodetypeequip;
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
 * Retourne un ArrayList d'objet métier : PMateriel.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PMateriel> listerPMateriel(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PMateriel unPMateriel = new PMateriel();
	return unPMateriel.getMyPMaterielBroker().listerPMateriel(aTransaction);
}
/**
 * Retourne un PMateriel.
 * @param aTransaction Transaction
 * @param code code
 * @return PMateriel
 * @throws Exception Exception
 */
public static PMateriel chercherPMateriel(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PMateriel unPMateriel = new PMateriel();
	return unPMateriel.getMyPMaterielBroker().chercherPMateriel(aTransaction, code);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean supprimerPMateriel(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PMateriel
	return getMyPMaterielBroker().supprimerPMateriel(aTransaction);
}
public boolean existePMaterielFre(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	PMateriel unPMateriel = new PMateriel();
	return unPMateriel.getMyPMaterielBroker().existePMaterielFre(aTransaction, param);
}
/**
 * Constructeur PMateriel.
 */
public PMateriel() {
	super();
}
/**
 * Getter de l'attribut pminv.
 * @return String
 */
public String getPminv() {
	return pminv;
}
/**
 * Setter de l'attribut pminv.
 */
/**
 * @param newPminv newPminv
 */
public void setPminv(String newPminv) { 
	pminv = newPminv;
}
/**
 * Getter de l'attribut pmserie.
 * @return String
 */
public String getPmserie() {
	return pmserie;
}
/**
 * Setter de l'attribut pmserie.
 */
/**
 * @param newPmserie newPmserie
 */
public void setPmserie(String newPmserie) { 
	pmserie = newPmserie;
}
/**
 * Getter de l'attribut codemodele.
 * @return String
 */
public String getCodemodele() {
	return codemodele;
}
/**
 * Setter de l'attribut codemodele.
 */
/**
 * @param newCodemodele newCodemodele
 */
public void setCodemodele(String newCodemodele) { 
	codemodele = newCodemodele;
}
/**
 * Getter de l'attribut codefre.
 * @return String
 */
public String getCodefre() {
	return codefre;
}
/**
 * Setter de l'attribut codefre.
 */
/**
 * @param newCodefre newCodefre
 */
public void setCodefre(String newCodefre) { 
	codefre = newCodefre;
}
/**
 * Getter de l'attribut dgarantie.
 * @return String
 */
public String getDgarantie() {
	return dgarantie;
}
/**
 * Setter de l'attribut dgarantie.
 */
/**
 * @param newDgarantie newDgarantie
 */
public void setDgarantie(String newDgarantie) { 
	dgarantie = newDgarantie;
}
/**
 * Getter de l'attribut reserve.
 * @return String
 */
public String getReserve() {
	return reserve;
}
/**
 * Setter de l'attribut reserve.
 */
/**
 * @param newReserve newReserve
 */
public void setReserve(String newReserve) { 
	reserve = newReserve;
}
/**
 * Getter de l'attribut dmes.
 * @return String
 */
public String getDmes() {
	return dmes;
}
/**
 * Setter de l'attribut dmes.
 */
/**
 * @param newDmes newDmes
 */
public void setDmes(String newDmes) { 
	dmes = newDmes;
}
/**
 * Getter de l'attribut dmhs.
 * @return String
 */
public String getDmhs() {
	return dmhs;
}
/**
 * Setter de l'attribut dmhs.
 */
/**
 * @param newDmhs newDmhs
 */
public void setDmhs(String newDmhs) { 
	dmhs = newDmhs;
}
/**
 * Getter de l'attribut prix.
 * @return String
 */
public String getPrix() {
	return prix;
}
/**
 * Setter de l'attribut prix.
 */
/**
 * @param newPrix newPrix
 */
public void setPrix(String newPrix) { 
	prix = newPrix;
}
/**
 * Getter de l'attribut dachat.
 * @return String
 */
public String getDachat() {
	return dachat;
}
/**
 * Setter de l'attribut dachat.
 */
/**
 * @param newDachat newDachat
 */
public void setDachat(String newDachat) { 
	dachat = newDachat;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PMaterielBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PMaterielBroker getMyPMaterielBroker() {
	return (PMaterielBroker)getMyBasicBroker();
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @param unPMateriel unPMateriel
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPMateriel(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel)  throws Exception {
//	on controle si null
	if (null == unPMateriel.getPminv()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return false;
	}
	controleChamps(aTransaction);
	
	if(aTransaction.isErreur()){
		return false;
	}
	//on vérifie qu'un autre équipement n'a pas déjà cette immatriculation
	//Equipement unEquipement = chercherEquipementImmat(aTransaction,getNumeroimmatriculation());
	int compte = chercherPMSerie(aTransaction,getPmserie(),getPminv());
	if (aTransaction.isErreur()){
		return false;
	}
	if (compte>0){
		aTransaction.declarerErreur("Un petit matériel avec ce numéro de série est déjà enregistré.");
		return false;
	}else{
		if((!getDmes().equals(""))&&(getDmes()!=null)&&(!getDachat().equals(""))&&(getDachat()!=null)){
			int controle = Services.compareDates(getDmes(),getDachat());
			if(controle==-1){
				aTransaction.declarerErreur("La date de mise en circulation doit être supérieure à la date d'achat.");
				return false;
			}else if(controle==-9999){
				aTransaction.declarerErreur("Erreur dans le controle des dates.");
				return false;
			}
		}
		// création du petit matériel
		boolean creatOK = getMyPMaterielBroker().creerPMateriel(aTransaction);
		if (!creatOK)
			return false;
//		//Creation du plan d'entretien personnalisé
//		ArrayList aEntretien = PeBase.listerPeBaseModeleActif(aTransaction,getCodemodele());
//		
//		//Si au moins un entretien
//		if (aEntretien.size() !=0 ) {					
//			for (java.util.ListIterator list = aEntretien.listIterator(); list.hasNext(); ) {
//				PeBase aPeBase = (PeBase)list.next();
//				Entretien unEntretien = Entretien.chercherEntretien(aTransaction,aPeBase.getCodeentretien());
//				if(aTransaction.isErreur()){
//					return false;
//				}
//				TIntervalle unTi = TIntervalle.chercherTIntervalle(aTransaction,aPeBase.getCodeti());
//				if (aTransaction.isErreur()){
//					return false;
//				}
//				TypeEntretien unTypeEntretien = new TypeEntretien();
//				// création du plan d'entretien personnalisé
//				PePerso unPePerso = new PePerso();
//				unPePerso.setIntervallepep(aPeBase.getIntervalle());
//				unPePerso.setDuree(aPeBase.getDuree());
//				if (!unPePerso.creerPePerso(aTransaction,unEquipement,unEntretien,unTypeEntretien,unTi)){
//					return false;
//				}
//			}
		}
		
return true;
}
/**
 * Retourne un booléen.
 * Vérifie si existe
 * @param aTransaction Transaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existePMateriel(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	PMateriel unPMateriel = new PMateriel();
	return unPMateriel.getMyPMaterielBroker().existePMateriel(aTransaction, param);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @param ancienNuminv ancienNuminv
 * @return boolean
 * @throws Exception Exception
 */
public boolean modifierPMateriel(nc.mairie.technique.Transaction aTransaction, String ancienNuminv) throws Exception {
	controleChamps(aTransaction);
	
//	 on vérifie que l'équipement n'est pas déjà utilisé
	if(!ancienNuminv.equals(getPminv())){
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
		ArrayList<FPM> listFPM = FPM.listerFpmPmat(aTransaction,ancienNuminv);
		if(aTransaction.isErreur()){
			aTransaction.traiterErreur();
		}
		if(listFPM!=null){
			if(listFPM.size()>0){
				aTransaction.declarerErreur("La modification du numéro d'inventaire n'est pas possible car des fiches d'entretiens ont été faites pour cet équipement.");
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
		ArrayList<PM_Affecter_Sce> listAffSce = PM_Affecter_Sce.chercherListPmAffecter_ScePm(aTransaction,ancienNuminv);
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
		ArrayList<PM_Affecter_Agent> listAffAgent = PM_Affecter_Agent.chercherListPmAffecter_AgentPM(aTransaction,ancienNuminv);
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
		ArrayList<PM_PePerso> listPePerso = PM_PePerso.listerPM_PePersoPMateriel(aTransaction,ancienNuminv);
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
	int compte = chercherPMSerie(aTransaction,getPmserie(),ancienNuminv);
	//compte = chercherEquipementImmat(aTransaction,getNumeroimmatriculation(),getNumeroinventaire());
	if (aTransaction.isErreur()){
		return false;
	}
	if (compte>0){
		aTransaction.declarerErreur("Un équipement avec ce numéro de série est déjà enregistré.");
		return false;
	}else{
		if((!getDmes().equals(""))&&(getDmes()!=null)&&(!getDachat().equals(""))&&(getDachat()!=null)){
			int controle = Services.compareDates(getDmes(),getDachat());
			if(controle==-1){
				aTransaction.declarerErreur("La date de mise en circulation doit être supérieure à la date d'achat.");
				return false;
			}else if(controle==-9999){
				aTransaction.declarerErreur("Erreur dans le controle des dates.");
				return false;
			}
		}
//		 on contrôle les dates hors circuit et date de vente
		//if (!"01/01/0001".equals(getDatehorscircuit())){
		if ((getDmhs()!=null)&&(!"".equals(getDmhs()))){
			if(!Services.estUneDate(getDmhs())){
				aTransaction.declarerErreur("La date hors service n'est pas correcte.");
				return false;
			}else{
				setDmhs(Services.formateDate(getDmhs()));
			}
			int compareHc = Services.compareDates(getDmhs(),getDmes());
			if (compareHc<-1){
				return false;
			}else if (compareHc==-1){
				aTransaction.declarerErreur("La date hors circuit doit être supérieur ou égale à la date de mise en circulation.("+getDmes()+")");
				return false;
			}
		}
		
		//on modifie les date prévues pour les entretiens
		String tri = "";
		ArrayList<PePersoInfos> listEntretien = PePersoInfos.listerPePersoInfosEquip(aTransaction,getPminv(),tri);
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
	}
	
	//Modification du PMateriel
	return getMyPMaterielBroker().modifierPMateriel(aTransaction);
}
public boolean controleChamps(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	
	if((getPrix()!=null)&&(!getPrix().equals(""))){
		setPrix(Outils.enleveEspace(getPrix()));
		if(!Services.estNumerique(getPrix())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour le prix d'achat.");
			return false;
		}
	}
	if((getDgarantie()!=null)&&(!getDgarantie().equals(""))){
		setDgarantie(Outils.enleveEspace(getDgarantie()));
		if(!Services.estNumerique(getDgarantie())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour la durée de garantie.");
			return false;
		}
	}
	return true;
}

public static int chercherPMSerie(nc.mairie.technique.Transaction aTransaction, String serie,String inv) throws Exception{
	PMateriel unPMateriel = new PMateriel();
	return unPMateriel.getMyPMaterielBroker().chercherPmSerie(aTransaction, serie,inv);
}

}
