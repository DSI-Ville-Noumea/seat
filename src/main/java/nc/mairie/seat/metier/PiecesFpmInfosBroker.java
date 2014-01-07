package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier PiecesFpmInfos
 */
public class PiecesFpmInfosBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur PiecesFpmInfosBroker.
 */
public PiecesFpmInfosBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PiecesFpmInfosMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
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
	return "SEAT.V_PIECESFPMINFOS";
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
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPiecesFpmInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PiecesFpmInfos.
 * @return PiecesFpmInfos
 */
public PiecesFpmInfos chercherPiecesFpmInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PiecesFpmInfos)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}
/**
 * Retourne un PiecesInfos.
 * @return PiecesInfos
 */
public java.util.ArrayList chercherPiecesFpmInfosFpm(nc.mairie.technique.Transaction aTransaction, String numfiche) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche = "+numfiche);
}
/**
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @return java.util.ArrayList
 */
public int cumuleMontantFpmInfos(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	return executeCompter(aTransaction,"select sum(quantite * prix) from "+getTable()+" where numfiche="+numot+" group by numfiche");
}
}
