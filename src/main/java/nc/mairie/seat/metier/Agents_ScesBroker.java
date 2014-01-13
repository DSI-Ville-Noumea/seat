package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Agents_Sces
 */
public class Agents_ScesBroker extends BasicBroker {
/**
 * Constructeur Agents_ScesBroker.
 */
public Agents_ScesBroker(Agents_Sces aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Agents_ScesMetier
 */
@Override
protected Agents_Sces definirMyMetier() {
	return new Agents_Sces() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Agents_ScesMetier
 */
protected Agents_Sces getMyAgents_Sces() {
	return (Agents_Sces)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "MAIRIE.SPMTSR";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyAgents_Sces().getClass().getField("nomatr"), "STRING"));
	mappage.put("SERVI", new BasicRecord("SERVI", "CHAR", getMyAgents_Sces().getClass().getField("servi"), "STRING"));
	mappage.put("REFARR", new BasicRecord("REFARR", "NUMERIC", getMyAgents_Sces().getClass().getField("refarr"), "STRING"));
	mappage.put("DATDEB", new BasicRecord("DATDEB", "NUMERIC", getMyAgents_Sces().getClass().getField("datdeb"), "STRING"));
	mappage.put("DATFIN", new BasicRecord("DATFIN", "NUMERIC", getMyAgents_Sces().getClass().getField("datfin"), "STRING"));
	mappage.put("CDECOL", new BasicRecord("CDECOL", "NUMERIC", getMyAgents_Sces().getClass().getField("cdecol"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : Agents_Sces.
 * @return java.util.ArrayList
 */
public ArrayList<Agents_Sces> listerAgents_Sces(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with ur");
}
/**
 * Retourne un Agents_Sces.
 * @return Agents_Sces
 */
public Agents_Sces chercherAgents_Sces(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Agents_Sces)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+" with ur");
}
}
