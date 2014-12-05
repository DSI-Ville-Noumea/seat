package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier AgentsATMInfos
 */
public class AgentsATMInfosBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : AgentsATMInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AgentsATMInfos> listerAgentsATMInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by nom with ur");
}
/**
 * Retourne un AgentsATMInfos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return AgentsATMInfos
 * @throws Exception Exception
 */
public AgentsATMInfos chercherAgentsATMInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (AgentsATMInfos)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+" with ur");
}
/**
 * Constructeur AgentsATMInfosBroker.
 * @param aMetier BasicMetier
 */
public AgentsATMInfosBroker(AgentsATMInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentsATMInfosMetier
 */
@Override
protected AgentsATMInfos definirMyMetier() {
	return new AgentsATMInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentsATMInfosMetier
 */
protected AgentsATMInfos getMyAgentsATMInfos() {
	return (AgentsATMInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "V_AGENTINFOSATM";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODESPECIALITE", new BasicRecord("CODESPECIALITE", "INTEGER", getMyAgentsATMInfos().getClass().getField("codespecialite"), "STRING"));
	mappage.put("LIBELLESPE", new BasicRecord("LIBELLESPE", "VARCHAR", getMyAgentsATMInfos().getClass().getField("libellespe"), "STRING"));
	mappage.put("MATRICULE", new BasicRecord("MATRICULE", "NUMERIC", getMyAgentsATMInfos().getClass().getField("matricule"), "STRING"));
	mappage.put("ESTMECANICIEN", new BasicRecord("ESTMECANICIEN", "VARCHAR", getMyAgentsATMInfos().getClass().getField("estmecanicien"), "STRING"));
	mappage.put("NOM", new BasicRecord("NOM", "CHAR", getMyAgentsATMInfos().getClass().getField("nom"), "STRING"));
	mappage.put("PRENOM", new BasicRecord("PRENOM", "CHAR", getMyAgentsATMInfos().getClass().getField("prenom"), "STRING"));
	mappage.put("SERVI", new BasicRecord("SERVI", "CHAR", getMyAgentsATMInfos().getClass().getField("servi"), "STRING"));
	mappage.put("NUMOT", new BasicRecord("NUMOT", "NUMERIC", getMyAgentsATMInfos().getClass().getField("numot"), "STRING"));
	return mappage;
}
}
