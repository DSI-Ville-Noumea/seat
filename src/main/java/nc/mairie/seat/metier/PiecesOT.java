package nc.mairie.seat.metier;

import java.util.ArrayList;
import java.util.Calendar;

import nc.mairie.seat.process.Outils;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier PiecesOT
 */
public class PiecesOT extends BasicMetier {
	public String numot;
	public String numpiece;
	public String datesortie;
	public String quantite;
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
 * Retourne un ArrayList d'objet métier : PiecesOT.
 * @return java.util.ArrayList
 */
public static ArrayList<PiecesOT> listerPiecesOT(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PiecesOT unPiecesOT = new PiecesOT();
	return unPiecesOT.getMyPiecesOTBroker().listerPiecesOT(aTransaction);
}
/**
 * Retourne un PiecesOT.
 * @return PiecesOT
 */
public static PiecesOT chercherPiecesOT(nc.mairie.technique.Transaction aTransaction, String numpiece,String numot,String date) throws Exception{
	PiecesOT unPiecesOT = new PiecesOT();
	return unPiecesOT.getMyPiecesOTBroker().chercherPiecesOT(aTransaction, numpiece,numot,date);
}

/**
 * Retourne un arrayList.
 * @return PiecesOT
 */
public static ArrayList<PiecesOT> chercherPiecesOTOT(nc.mairie.technique.Transaction aTransaction, String numot) throws Exception{
	PiecesOT unPiecesOT = new PiecesOT();
	return unPiecesOT.getMyPiecesOTBroker().chercherPiecesOTOT(aTransaction, numot);
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 */
public boolean existePiecesOT(nc.mairie.technique.Transaction aTransaction, String numot,String numpiece,String date) throws Exception{
	PiecesOT unPiecesOT = new PiecesOT();
	return unPiecesOT.getMyPiecesOTBroker().existePiecesOT(aTransaction, numot,numpiece,date);
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 */
public boolean existePiecesOTPieces(nc.mairie.technique.Transaction aTransaction, String numpiece) throws Exception{
	PiecesOT unPiecesOT = new PiecesOT();
	return unPiecesOT.getMyPiecesOTBroker().existePiecesOTPieces(aTransaction, numpiece);
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerPiecesOT(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	//Creation du PiecesOT
	return getMyPiecesOTBroker().creerPiecesOT(aTransaction);
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
 */
public boolean creationPiecesOT(nc.mairie.technique.Transaction aTransaction,OT unOT,Pieces unePiece )  throws Exception {
	// on vérifie que les objets ne sont pas null
	if (null == unOT.getNumeroot()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","OT"));
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
	String dSortie = Services.formateDateInternationale(getDatesortie());
	// on vérifie que cette pièce n'a pas déjà été enregistré pour cet OT avec la même date
	if(existePiecesOT(aTransaction,unOT.getNumeroot(),unePiece.getCodepiece(),dSortie)){
		aTransaction.declarerErreur("Cette pièce("+unePiece.getDesignationpiece()+") a déjà été enregistrée comme sortie le "+getDatesortie()+" pour cet OT "+unOT.getNumeroot());
		return false;
	}
//	RG : controle date
	if((getDatesortie()!=null)&&(!getDatesortie().equals(""))){
		if(!Services.estUneDate(getDatesortie())){
			aTransaction.declarerErreur("La date de sortie n'est pas correcte.");
			return false;
		}
		//la date de sortie ne doit pas être supérieur à la date de sortie du véhicule
		if((unOT.getDatesortie()!=null)&&(!unOT.getDatesortie().equals("01/01/0001"))&&(!("").equals(unOT.getDatesortie()))){
			// si date de sortie > date de sortie du véhicule
			// la date de sortie peut être inférieur à la date d'enrée du véhicule
			int compare = Services.compareDates(getDatesortie(),unOT.getDatesortie());
			if(compare==1){
				aTransaction.declarerErreur("La date de sortie des pièces ne doit pas être supérieure à la date de sortie de l'équipement.("+unOT.getDatesortie()+")");
				return false;
			}else if(compare==-9999){
				return false;
			}
		}else{
//			RG : controle de l'année (l'année saisie ne doit pas être supérieur à l'année en cours)
			String annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			String dateAnnee = Services.formateDate("31/12/"+annee);
			if((getDatesortie()!=null)&&(!getDatesortie().equals(""))){
				//Services.convertitDate(Services.formateDate(getDateentree()),"dd/mm/yyyy","yyyy");
				String date = Services.formateDate(getDatesortie());
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
	setNumot(unOT.getNumeroot());
	setNumpiece(unePiece.getCodepiece());
	setPrix(unePiece.getPu());
	
	//Creation du PiecesOT
	return creerPiecesOT(aTransaction);
}


/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierPiecesOT(nc.mairie.technique.Transaction aTransaction) throws Exception {
//	RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	//RG : controle date
	if((getDatesortie()!=null)&&(!getDatesortie().equals(""))){
		if(!Services.estUneDate(getDatesortie())){
			aTransaction.declarerErreur("La date de sortie n'est pas correcte.");
			return false;
		}
		//la date de sortie ne doit pas être supérieur à la date de sortie du véhicule
		OT unOT = OT.chercherOT(aTransaction,getNumot());
		if(aTransaction.isErreur()){
			return false;
		}
		if((unOT.getDatesortie()!=null)&&(!unOT.getDatesortie().equals("01/01/0001"))){
			int compare = Services.compareDates(getDatesortie(),unOT.getDatesortie());
			if(compare==1){
				aTransaction.declarerErreur("La date de sortie des pièces ne doit pas être supérieure à la date de sortie de l'équipement.("+unOT.getDatesortie()+")");
				return false;
			}else if(compare==-9999){
				return false;
			}
		}else{
//			RG : controle de l'année (l'année saisie ne doit pas être supérieur à l'année en cours)
			String annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			String dateAnnee = Services.formateDate("31/12/"+annee);
			if((getDatesortie()!=null)&&(!getDatesortie().equals(""))){
				//Services.convertitDate(Services.formateDate(getDateentree()),"dd/mm/yyyy","yyyy");
				String date = Services.formateDate(getDatesortie());
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
	//Modification du PiecesOT
	return getMyPiecesOTBroker().modifierPiecesOT(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerPiecesOT(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PiecesOT
	return getMyPiecesOTBroker().supprimerPiecesOT(aTransaction);
}
	public String prix;
/**
 * Constructeur PiecesOT.
 */
public PiecesOT() {
	super();
}
/**
 * Getter de l'attribut numot.
 */
public String getNumot() {
	return numot;
}
/**
 * Setter de l'attribut numot.
 */
public void setNumot(String newNumot) { 
	numot = newNumot;
}
/**
 * Getter de l'attribut numpiece.
 */
public String getNumpiece() {
	return numpiece;
}
/**
 * Setter de l'attribut numpiece.
 */
public void setNumpiece(String newNumpiece) { 
	numpiece = newNumpiece;
}
/**
 * Getter de l'attribut datesortie.
 */
public String getDatesortie() {
	return datesortie;
}
/**
 * Setter de l'attribut datesortie.
 */
public void setDatesortie(String newDatesortie) { 
	datesortie = newDatesortie;
}
/**
 * Getter de l'attribut quantite.
 */
public String getQuantite() {
	return quantite;
}
/**
 * Setter de l'attribut quantite.
 */
public void setQuantite(String newQuantite) { 
	quantite = newQuantite;
}
/**
 * Getter de l'attribut prix.
 */
public String getPrix() {
	return prix;
}
/**
 * Setter de l'attribut prix.
 */
public void setPrix(String newPrix) { 
	prix = newPrix;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PiecesOTBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected PiecesOTBroker getMyPiecesOTBroker() {
	return (PiecesOTBroker)getMyBasicBroker();
}
}
