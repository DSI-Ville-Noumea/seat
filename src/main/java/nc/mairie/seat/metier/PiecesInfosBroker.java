package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier PiecesInfos
 */
public class PiecesInfosBroker extends nc.mairie.technique.BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : PiecesInfos.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPiecesInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by designationpiece");
}
/**
 * Retourne un PiecesInfos.
 * @return PiecesInfos
 */
public PiecesInfos chercherPiecesInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PiecesInfos)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+" order by designationpiece");
}
/**
 * Retourne un PiecesInfos.
 * @return PiecesInfos
 */
public java.util.ArrayList chercherPiecesInfosOT(nc.mairie.technique.Transaction aTransaction, String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numot = "+numot);
}
/**
 * Constructeur PiecesInfosBroker.
 */
public PiecesInfosBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PiecesInfosMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new PiecesInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PiecesInfosMetier
 */
protected PiecesInfos getMyPiecesInfos() {
	return (PiecesInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_PIECESINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODEPIECE", new BasicRecord("CODEPIECE", "INTEGER", getMyPiecesInfos().getClass().getField("codepiece"), "STRING"));
	mappage.put("DESIGNATIONPIECE", new BasicRecord("DESIGNATIONPIECE", "CHAR", getMyPiecesInfos().getClass().getField("designationpiece"), "STRING"));
	mappage.put("PU", new BasicRecord("PU", "INTEGER", getMyPiecesInfos().getClass().getField("pu"), "STRING"));
	mappage.put("DATESORTIE", new BasicRecord("DATESORTIE", "DATE", getMyPiecesInfos().getClass().getField("datesortie"), "DATE"));
	mappage.put("QUANTITE", new BasicRecord("QUANTITE", "INTEGER", getMyPiecesInfos().getClass().getField("quantite"), "STRING"));
	mappage.put("NUMOT", new BasicRecord("NUMOT", "NUMERIC", getMyPiecesInfos().getClass().getField("numot"), "STRING"));
	mappage.put("PRIX", new BasicRecord("PRIX", "NUMERIC", getMyPiecesInfos().getClass().getField("prix"), "STRING"));
	return mappage;
}
}
