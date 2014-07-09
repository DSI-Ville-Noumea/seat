package nc.mairie.seat.metier;

import java.util.ArrayList;
import java.util.Calendar;

import nc.mairie.seat.process.Outils;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Pieces_FPM
 */
public class Pieces_FPM extends BasicMetier {
	public String numfiche;
	public String numpiece;
	public String dsortie;
	public String quantite;
	public String prix;
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
 * Retourne un ArrayList d'objet métier : Pieces_FPM.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Pieces_FPM> listerPieces_FPM(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Pieces_FPM unPieces_FPM = new Pieces_FPM();
	return unPieces_FPM.getMyPieces_FPMBroker().listerPieces_FPM(aTransaction);
}

public static ArrayList<Pieces_FPM> listerPieces_FPMFPM(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception{
	Pieces_FPM unPieces_FPM = new Pieces_FPM();
	return unPieces_FPM.getMyPieces_FPMBroker().listerPieces_FPMFPM(aTransaction,numfiche);
}

/**
 * Retourne un Pieces_FPM.
 * @param aTransaction Transaction
 * @param numpiece numpiece
 * @param numfiche numfiche
 * @param date date
 * @return Pieces_FPM
 * @throws Exception Exception
 */
public static Pieces_FPM chercherPieces_FPM(nc.mairie.technique.Transaction aTransaction, String numpiece,String numfiche,String date) throws Exception{
	Pieces_FPM unPieces_FPM = new Pieces_FPM();
	return unPieces_FPM.getMyPieces_FPMBroker().chercherPieces_FPM(aTransaction, numpiece,numfiche,date);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPieces_FPM(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	//Creation du Pieces_FPM
	return getMyPieces_FPMBroker().creerPieces_FPM(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean modifierPieces_FPM(nc.mairie.technique.Transaction aTransaction) throws Exception {
//	RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	//RG : controle date
	if((getDsortie()!=null)&&(!getDsortie().equals(""))){
		if(!Services.estUneDate(getDsortie())){
			aTransaction.declarerErreur("La date de sortie n'est pas correcte.");
			return false;
		}
		//la date de sortie ne doit pas être supérieur à la date de sortie du véhicule
		FPM unFPM = FPM.chercherFPM(aTransaction,getNumfiche());
		if(aTransaction.isErreur()){
			return false;
		}
		if((unFPM.getDsortie()!=null)&&(!unFPM.getDsortie().equals("01/01/0001"))){
			int compare = Services.compareDates(getDsortie(),unFPM.getDsortie());
			if(compare==1){
				aTransaction.declarerErreur("La date de sortie des pièces ne doit pas être supérieure à la date de sortie de l'équipement.("+unFPM.getDsortie()+")");
				return false;
			}else if(compare==-9999){
				return false;
			}
		}else{
//			RG : controle de l'année (l'année saisie ne doit pas être supérieur à l'année en cours)
			String annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			String dateAnnee = Services.formateDate("31/12/"+annee);
			if((getDsortie()!=null)&&(!getDsortie().equals(""))){
				//Services.convertitDate(Services.formateDate(getDateentree()),"dd/mm/yyyy","yyyy");
				String date = Services.formateDate(getDsortie());
				int compare = Services.compareDates(date,dateAnnee);
				if(compare==1){
					aTransaction.declarerErreur("La date de sortie des pièces ne doit pas être supérieur au "+dateAnnee);
					return false;
				}else if(compare==-9999){
					return false;
				}
			}
		}
	}
	//Modification du Pieces_FPM
	return getMyPieces_FPMBroker().modifierPieces_FPM(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean supprimerPieces_FPM(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'Pieces_FPM
	return getMyPieces_FPMBroker().supprimerPieces_FPM(aTransaction);
}
/*vérifie que les champs sont bien renseignés
 * Date : 01/09/05
 * autheur : NC
 */
public boolean controleChamps(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	if((getQuantite()!=null)&&(!getQuantite().equals(""))){
		setQuantite(Outils.enleveEspace(getQuantite()));
		if(!Services.estNumerique(getQuantite())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour la quantité.");
			return false;
		}
	}
	return true;
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * on utilise la méthode créer pour ajouter un PieceOt
 * passage en paramètre de Pièce et de OT
 * @param aTransaction Transaction
 * @param unFPM unFPM
 * @param unePiece unePiece
 * @return boolean
 * @throws Exception Exception
 */
public boolean creationPiecesOT(nc.mairie.technique.Transaction aTransaction,FPM unFPM,Pieces unePiece )  throws Exception {
	// on vérifie que les objets ne sont pas null
	if (null == unFPM.getNumfiche()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","FPM"));
		return false;
	}
	if (null == unePiece.getCodepiece()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Pièces"));
		return false;
	}
	
	//RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	String dSortie = Services.formateDateInternationale(getDsortie());
	// on vérifie que cette pièce n'a pas déjà été enregistré pour cet OT avec la même date
	if(existePiecesFPM(aTransaction,unFPM.getNumfiche(),unePiece.getCodepiece(),dSortie)){
		aTransaction.declarerErreur("Cette pièce("+unePiece.getDesignationpiece()+") a déjà été enregistrée comme sortie le "+getDsortie()+" pour cet OT "+unFPM.getNumfiche());
		return false;
	}
//	RG : controle date
	if((getDsortie()!=null)&&(!getDsortie().equals(""))){
		if(!Services.estUneDate(getDsortie())){
			aTransaction.declarerErreur("La date de sortie n'est pas correcte.");
			return false;
		}
		//la date de sortie ne doit pas être supérieur à la date de sortie du véhicule
		if((unFPM.getDsortie()!=null)&&(!unFPM.getDsortie().equals("01/01/0001"))&&(!("").equals(unFPM.getDsortie()))){
			// si date de sortie > date de sortie du véhicule
			// la date de sortie peut être inférieur à la date d'enrée du véhicule
			int compare = Services.compareDates(getDsortie(),unFPM.getDsortie());
			if(compare==1){
				aTransaction.declarerErreur("La date de sortie des pièces ne doit pas être supérieure à la date de sortie du petit matériel.("+unFPM.getDsortie()+")");
				return false;
			}else if(compare==-9999){
				return false;
			}
		}else{
//			RG : controle de l'année (l'année saisie ne doit pas être supérieur à l'année en cours)
			String annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			String dateAnnee = Services.formateDate("31/12/"+annee);
			if((getDsortie()!=null)&&(!getDsortie().equals(""))){
				//Services.convertitDate(Services.formateDate(getDateentree()),"dd/mm/yyyy","yyyy");
				String date = Services.formateDate(getDsortie());
				int compare = Services.compareDates(date,dateAnnee);
				if(compare==1){
					aTransaction.declarerErreur("La date de sortie des pièces ne doit pas être supérieur au "+dateAnnee);
					return false;
				}else if(compare==-9999){
					return false;
				}
			}
		}
	}
	
	//on renseigne les clés étrangères
	setNumfiche(unFPM.getNumfiche());
	setNumpiece(unePiece.getCodepiece());
	setPrix(unePiece.getPu());
	
	//Creation du PiecesOT
	return creerPieces_FPM(aTransaction);
}
/**
 * Retourne un booléen.
 * Vérifie si existe
 * @param aTransaction Transaction
 * @param numot numot
 * @param numpiece numpiece
 * @param date date
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existePiecesFPM(nc.mairie.technique.Transaction aTransaction, String numot,String numpiece,String date) throws Exception{
	Pieces_FPM unPieces_FPM = new Pieces_FPM();
	return unPieces_FPM.getMyPieces_FPMBroker().existePiecesFPM(aTransaction, numot,numpiece,date);
}
/**
 * Constructeur Pieces_FPM.
 */
public Pieces_FPM() {
	super();
}
/**
 * Getter de l'attribut numfiche.
 * @return String
 */
public String getNumfiche() {
	return numfiche;
}
/**
 * Setter de l'attribut numfiche.
 */
/**
 * @param newNumfiche newNumfiche
 */
public void setNumfiche(String newNumfiche) { 
	numfiche = newNumfiche;
}
/**
 * Getter de l'attribut numpiece.
 * @return String
 */
public String getNumpiece() {
	return numpiece;
}
/**
 * Setter de l'attribut numpiece.
 */
/**
 * @param newNumpiece newNumpiece
 */
public void setNumpiece(String newNumpiece) { 
	numpiece = newNumpiece;
}
/**
 * Getter de l'attribut dsortie.
 * @return String
 */
public String getDsortie() {
	return dsortie;
}
/**
 * Setter de l'attribut dsortie.
 */
/**
 * @param newDsortie newDsortie
 */
public void setDsortie(String newDsortie) { 
	dsortie = newDsortie;
}
/**
 * Getter de l'attribut quantite.
 * @return String
 */
public String getQuantite() {
	return quantite;
}
/**
 * Setter de l'attribut quantite.
 */
/**
 * @param newQuantite newQuantite
 */
public void setQuantite(String newQuantite) { 
	quantite = newQuantite;
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new Pieces_FPMBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected Pieces_FPMBroker getMyPieces_FPMBroker() {
	return (Pieces_FPMBroker)getMyBasicBroker();
}
}
