package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PiecesFpmInfos
 */
public class PiecesFpmInfosBroker extends BasicBroker {
/**
 * Constructeur PiecesFpmInfosBroker.
 * @param aMetier BasicMetier
 */
public PiecesFpmInfosBroker(PiecesFpmInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PiecesFpmInfosMetier
 */
@Override
protected PiecesFpmInfos definirMyMetier() {
	return new PiecesFpmInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PiecesFpmInfosMetier
 */
protected PiecesFpmInfos getMyPiecesFpmInfos() {
	return (PiecesFpmInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "V_PIECESFPMINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODEPIECE", new BasicRecord("CODEPIECE", "INTEGER", getMyPiecesFpmInfos().getClass().getField("codepiece"), "STRING"));
	mappage.put("DESIGNATIONPIECE", new BasicRecord("DESIGNATIONPIECE", "CHAR", getMyPiecesFpmInfos().getClass().getField("designationpiece"), "STRING"));
	mappage.put("PU", new BasicRecord("PU", "INTEGER", getMyPiecesFpmInfos().getClass().getField("pu"), "STRING"));
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyPiecesFpmInfos().getClass().getField("numfiche"), "STRING"));
	mappage.put("NUMPIECE", new BasicRecord("NUMPIECE", "INTEGER", getMyPiecesFpmInfos().getClass().getField("numpiece"), "STRING"));
	mappage.put("DSORTIE", new BasicRecord("DSORTIE", "DATE", getMyPiecesFpmInfos().getClass().getField("dsortie"), "DATE"));
	mappage.put("QUANTITE", new BasicRecord("QUANTITE", "INTEGER", getMyPiecesFpmInfos().getClass().getField("quantite"), "STRING"));
	mappage.put("PRIX", new BasicRecord("PRIX", "NUMERIC", getMyPiecesFpmInfos().getClass().getField("prix"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : PiecesFpmInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<PiecesFpmInfos> listerPiecesFpmInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PiecesFpmInfos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return PiecesFpmInfos
 * @throws Exception Exception
 */
public PiecesFpmInfos chercherPiecesFpmInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PiecesFpmInfos)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}
/**
 * Retourne un PiecesInfos.
 * @param aTransaction Transaction
 * @param numfiche numfiche
 * @return PiecesInfos
 * @throws Exception Exception
 */
public ArrayList<PiecesFpmInfos> chercherPiecesFpmInfosFpm(nc.mairie.technique.Transaction aTransaction, String numfiche) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche = "+numfiche);
}
/**
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @param aTransaction Transaction
 * @param numot numot
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public int cumuleMontantFpmInfos(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	return executeCompter(aTransaction,"select sum(quantite * prix) from "+getTable()+" where numfiche="+numot+" group by numfiche");
}
}
