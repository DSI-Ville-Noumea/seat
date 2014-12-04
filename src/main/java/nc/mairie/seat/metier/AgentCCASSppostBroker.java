package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier AgentCCASSppost
 */
public class AgentCCASSppostBroker extends BasicBroker {
/**
 * Constructeur AgentCCASSppostBroker.
 * @param aMetier BasicMetier
 */
public AgentCCASSppostBroker(AgentCCASSppost aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentCCASSppostMetier
 */
protected AgentCCASSppost definirMyMetier() {
	return new AgentCCASSppost() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentCCASSppostMetier
 */
protected AgentCCASSppost getMyAgentCCASSppost() {
	return (AgentCCASSppost)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "MAIRCCAS_SPPOST";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("POANNE", new BasicRecord("POANNE", "NUMERIC", getMyAgentCCASSppost().getClass().getField("poanne"), "STRING"));
	mappage.put("PONUOR", new BasicRecord("PONUOR", "NUMERIC", getMyAgentCCASSppost().getClass().getField("ponuor"), "STRING"));
	mappage.put("CDLIEU", new BasicRecord("CDLIEU", "DECIMAL", getMyAgentCCASSppost().getClass().getField("cdlieu"), "STRING"));
	mappage.put("POSERV", new BasicRecord("POSERV", "CHAR", getMyAgentCCASSppost().getClass().getField("poserv"), "STRING"));
	mappage.put("CTITRE", new BasicRecord("CTITRE", "NUMERIC", getMyAgentCCASSppost().getClass().getField("ctitre"), "STRING"));
	mappage.put("POETUD", new BasicRecord("POETUD", "NUMERIC", getMyAgentCCASSppost().getClass().getField("poetud"), "STRING"));
	mappage.put("CRESPO", new BasicRecord("CRESPO", "NUMERIC", getMyAgentCCASSppost().getClass().getField("crespo"), "STRING"));
	mappage.put("POGRAD", new BasicRecord("POGRAD", "CHAR", getMyAgentCCASSppost().getClass().getField("pograd"), "STRING"));
	mappage.put("POMIS1", new BasicRecord("POMIS1", "VARCHAR", getMyAgentCCASSppost().getClass().getField("pomis1"), "STRING"));
	mappage.put("POMIS2", new BasicRecord("POMIS2", "VARCHAR", getMyAgentCCASSppost().getClass().getField("pomis2"), "STRING"));
	mappage.put("POMIS3", new BasicRecord("POMIS3", "VARCHAR", getMyAgentCCASSppost().getClass().getField("pomis3"), "STRING"));
	mappage.put("POMIS4", new BasicRecord("POMIS4", "VARCHAR", getMyAgentCCASSppost().getClass().getField("pomis4"), "STRING"));
	mappage.put("POMATR", new BasicRecord("POMATR", "NUMERIC", getMyAgentCCASSppost().getClass().getField("pomatr"), "STRING"));
	mappage.put("POCOND", new BasicRecord("POCOND", "CHAR", getMyAgentCCASSppost().getClass().getField("pocond"), "STRING"));
	mappage.put("PODVAL", new BasicRecord("PODVAL", "NUMERIC", getMyAgentCCASSppost().getClass().getField("podval"), "STRING"));
	mappage.put("PODSUP", new BasicRecord("PODSUP", "NUMERIC", getMyAgentCCASSppost().getClass().getField("podsup"), "STRING"));
	mappage.put("POSERP", new BasicRecord("POSERP", "CHAR", getMyAgentCCASSppost().getClass().getField("poserp"), "STRING"));
	mappage.put("CODFON", new BasicRecord("CODFON", "CHAR", getMyAgentCCASSppost().getClass().getField("codfon"), "STRING"));
	mappage.put("CODACT", new BasicRecord("CODACT", "CHAR", getMyAgentCCASSppost().getClass().getField("codact"), "STRING"));
	mappage.put("NOACTI", new BasicRecord("NOACTI", "CHAR", getMyAgentCCASSppost().getClass().getField("noacti"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : AgentCCASSppost.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AgentCCASSppost> listerAgentCCASSppost(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with ur");
}
/**
 * Retourne un AgentCCASSppost.
 * @param aTransaction Transaction
 * @param cle cle
 * @return AgentCCASSppost
 * @throws Exception Exception
 */
public AgentCCASSppost chercherAgentCCASSppost(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (AgentCCASSppost)executeSelect(aTransaction,"select * from "+getTable()+" where pomatr = "+cle+" with ur");
}
}
