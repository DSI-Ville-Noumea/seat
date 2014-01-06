package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Pieces
 */
public class PiecesBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur PiecesBroker.
 */
public PiecesBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PiecesMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new Pieces() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PiecesMetier
 */
protected Pieces getMyPieces() {
	return (Pieces)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_PIECES";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("CODEPIECE", new BasicRecord("CODEPIECE", "INTEGER", getMyPieces().getClass().getField("codepiece"), "STRING"));
	mappage.put("DESIGNATIONPIECE", new BasicRecord("DESIGNATIONPIECE", "CHAR", getMyPieces().getClass().getField("designationpiece"), "STRING"));
	mappage.put("PU", new BasicRecord("PU", "INTEGER", getMyPieces().getClass().getField("pu"), "STRING"));
	return mappage;
}


/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvPieces(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier codepiece
	return executeCompter(aTransaction, "select max(codepiece) from "+ getTable());
	
}

/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPieces(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPieces(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPieces(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Pieces.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPieces(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by designationpiece");
}
/**
 * Retourne un Pieces.
 * @return Pieces
 */
public Pieces chercherPieces(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Pieces)executeSelect(aTransaction,"select * from "+getTable()+" where CODEPIECE = "+cle+" order by designationpiece");
}

/**
 * Retourne un Pieces.
 * @return Pieces
 */
public ArrayList chercherPiecesLib(nc.mairie.technique.Transaction aTransaction, String lib) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where upper(designationpiece) like '"+lib.toUpperCase()+"%' order by designationpiece");
}

}
