package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.seat.process.Outils;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Pieces
 */
public class Pieces extends BasicMetier {
	public String codepiece;
	public String designationpiece;
	public String pu;
/**
 * Constructeur Pieces.
 */
public Pieces() {
	super();
}
/**
 * Getter de l'attribut codepiece.
 * @return String
 */
public String getCodepiece() {
	return codepiece;
}
/**
 * Setter de l'attribut codepiece.
 * @param newCodepiece newCodepiece
 */
public void setCodepiece(String newCodepiece) { 
	codepiece = newCodepiece;
}
/**
 * Getter de l'attribut designationpiece.
 * @return String
 */
public String getDesignationpiece() {
	return designationpiece;
}
/**
 * Setter de l'attribut designationpiece.
 * @param newDesignationpiece newDesignationpiece
 */
public void setDesignationpiece(String newDesignationpiece) { 
	designationpiece = newDesignationpiece;
}
/**
 * Getter de l'attribut pu.
 * @return String
 */
public String getPu() {
	return pu;
}
/**
 * Setter de l'attribut pu.
 * @param newPu newPu
 */
public void setPu(String newPu) { 
	pu = newPu;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PiecesBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PiecesBroker getMyPiecesBroker() {
	return (PiecesBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : Pieces.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Pieces> listerPieces(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Pieces unPieces = new Pieces();
	return unPieces.getMyPiecesBroker().listerPieces(aTransaction);
}
/**
 * Retourne un Pieces.
 * @param aTransaction Transaction
 * @param code code
 * @return Pieces
 * @throws Exception Exception
 */
public static Pieces chercherPieces(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Pieces unPieces = new Pieces();
	return unPieces.getMyPiecesBroker().chercherPieces(aTransaction, code);
}

/**
 * Retourne un ArrayList d'objet métier : Pieces.
 * @param aTransaction Transaction
 * @param lib lib
 * @return java.util.ArrayList
 * on cherche les pièces qui comment par lib...
 * @throws Exception Exception
 */
public static ArrayList<Pieces> chercherPiecesLib(nc.mairie.technique.Transaction aTransaction,String lib) throws Exception{
	Pieces unPieces = new Pieces();
	return unPieces.getMyPiecesBroker().chercherPiecesLib(aTransaction,lib);
}

/*vérifie que les champs sont bien renseignés
 * Date : 01/09/05
 * autheur : NC
 */
public boolean controleChamps(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	if((getPu()!=null)&&(!getPu().equals(""))){
		setPu(Outils.enleveEspace(getPu()));
		if(!Services.estNumerique(getPu())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour le prix unitaire.");
			return false;
		}
	}
	return true;
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPieces(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	//RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
//	on ajoute le code de la piece
	int nouvcodepiece = nouvPieces(aTransaction);
	setCodepiece(String.valueOf(nouvcodepiece));
	//Creation du Pieces
	return getMyPiecesBroker().creerPieces(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean modifierPieces(nc.mairie.technique.Transaction aTransaction) throws Exception {
//	RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	//Modification du Pieces
	return getMyPiecesBroker().modifierPieces(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean supprimerPieces(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//RG : contrôle si la pièce est utilisée par un OT
	PiecesOT unePiecesOt = new PiecesOT();
	if(unePiecesOt.existePiecesOTPieces(aTransaction,getCodepiece())){
		aTransaction.declarerErreur("Cette pièce est utilisée pour les OT. La suppression n'est donc pas possible.");
		return false;
	}		
	
	//Suppression de l'Pieces
	return getMyPiecesBroker().supprimerPieces(aTransaction);
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvPieces(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier codepneu
	int nouvcodePieces = getMyPiecesBroker().nouvPieces(aTransaction);
	
	//si pas trouvé
	if (nouvcodePieces == -1) {
		//fonctionnellement normal: table vide
		nouvcodePieces = 1;
	} else {
		nouvcodePieces++;
	}
	
	return nouvcodePieces;
	
}

}
