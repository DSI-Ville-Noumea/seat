package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PiecesOT
 */
public class PiecesOTBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPiecesOT(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existePiecesOT(nc.mairie.technique.Transaction aTransaction, String numot,String numpiece,String date) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numot = "+numot+" and numpiece = "+numpiece+" and datesortie='"+date+"'");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existePiecesOTPieces(nc.mairie.technique.Transaction aTransaction, String numpiece) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numpiece = "+numpiece);
}

/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPiecesOT(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPiecesOT(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PiecesOT.
 * @return java.util.ArrayList
 */
public ArrayList<PiecesOT> listerPiecesOT(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PiecesOT.
 * @return PiecesOT
 */
public PiecesOT chercherPiecesOT(nc.mairie.technique.Transaction aTransaction, String numpiece,String numot,String date) throws Exception {
	String dSortie = Services.formateDateInternationale(date);
	return (PiecesOT)executeSelect(aTransaction,"select * from "+getTable()+" where numpiece = "+numpiece+" and numot="+numot+" and datesortie='"+dSortie+"'");
}

/**
 * Retourne un arrayList.
 * @return PiecesOT
 */
public ArrayList<PiecesOT> chercherPiecesOTOT(nc.mairie.technique.Transaction aTransaction, String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numot="+numot);
}

/**
 * Constructeur PiecesOTBroker.
 */
public PiecesOTBroker(PiecesOT aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PiecesOTMetier
 */
@Override
protected PiecesOT definirMyMetier() {
	return new PiecesOT() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PiecesOTMetier
 */
protected PiecesOT getMyPiecesOT() {
	return (PiecesOT)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_PIECES_OT";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMOT", new BasicRecord("NUMOT", "NUMERIC", getMyPiecesOT().getClass().getField("numot"), "STRING"));
	mappage.put("NUMPIECE", new BasicRecord("NUMPIECE", "INTEGER", getMyPiecesOT().getClass().getField("numpiece"), "STRING"));
	mappage.put("DATESORTIE", new BasicRecord("DATESORTIE", "DATE", getMyPiecesOT().getClass().getField("datesortie"), "DATE"));
	mappage.put("QUANTITE", new BasicRecord("QUANTITE", "INTEGER", getMyPiecesOT().getClass().getField("quantite"), "STRING"));
	mappage.put("PRIX", new BasicRecord("PRIX", "NUMERIC", getMyPiecesOT().getClass().getField("prix"), "STRING"));
	return mappage;
}
}
