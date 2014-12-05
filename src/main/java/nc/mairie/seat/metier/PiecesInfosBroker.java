package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PiecesInfos
 */
public class PiecesInfosBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : PiecesInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<PiecesInfos> listerPiecesInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by designationpiece");
}
/**
 * Retourne un PiecesInfos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return PiecesInfos
 * @throws Exception Exception
 */
public PiecesInfos chercherPiecesInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PiecesInfos)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+" order by designationpiece");
}
/**
 * Retourne un PiecesInfos.
 * @param aTransaction Transaction
 * @param numot numot
 * @return PiecesInfos
 * @throws Exception Exception
 */
public ArrayList<PiecesInfos> chercherPiecesInfosOT(nc.mairie.technique.Transaction aTransaction, String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numot = "+numot);
}
/**
 * Constructeur PiecesInfosBroker.
 * @param aMetier BasicMetier
 */
public PiecesInfosBroker(PiecesInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PiecesInfosMetier
 */
@Override
protected PiecesInfos definirMyMetier() {
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
	return "V_PIECESINFOS";
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
