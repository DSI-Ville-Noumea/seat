package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier AgentCDESppost
 */
public class AgentCDESppostBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur AgentCDESppostBroker.
 */
public AgentCDESppostBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentCDESppostMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new AgentCDESppost() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentCDESppostMetier
 */
protected AgentCDESppost getMyAgentCDESppost() {
	return (AgentCDESppost)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "MAIRCDE.SPPOST";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("POANNE", new BasicRecord("POANNE", "NUMERIC", getMyAgentCDESppost().getClass().getField("poanne"), "STRING"));
	mappage.put("PONUOR", new BasicRecord("PONUOR", "NUMERIC", getMyAgentCDESppost().getClass().getField("ponuor"), "STRING"));
	mappage.put("CDLIEU", new BasicRecord("CDLIEU", "DECIMAL", getMyAgentCDESppost().getClass().getField("cdlieu"), "STRING"));
	mappage.put("POSERV", new BasicRecord("POSERV", "CHAR", getMyAgentCDESppost().getClass().getField("poserv"), "STRING"));
	mappage.put("CTITRE", new BasicRecord("CTITRE", "NUMERIC", getMyAgentCDESppost().getClass().getField("ctitre"), "STRING"));
	mappage.put("POETUD", new BasicRecord("POETUD", "NUMERIC", getMyAgentCDESppost().getClass().getField("poetud"), "STRING"));
	mappage.put("CRESPO", new BasicRecord("CRESPO", "NUMERIC", getMyAgentCDESppost().getClass().getField("crespo"), "STRING"));
	mappage.put("POGRAD", new BasicRecord("POGRAD", "CHAR", getMyAgentCDESppost().getClass().getField("pograd"), "STRING"));
	mappage.put("POMIS1", new BasicRecord("POMIS1", "VARCHAR", getMyAgentCDESppost().getClass().getField("pomis1"), "STRING"));
	mappage.put("POMIS2", new BasicRecord("POMIS2", "VARCHAR", getMyAgentCDESppost().getClass().getField("pomis2"), "STRING"));
	mappage.put("POMIS3", new BasicRecord("POMIS3", "VARCHAR", getMyAgentCDESppost().getClass().getField("pomis3"), "STRING"));
	mappage.put("POMIS4", new BasicRecord("POMIS4", "VARCHAR", getMyAgentCDESppost().getClass().getField("pomis4"), "STRING"));
	mappage.put("POMATR", new BasicRecord("POMATR", "NUMERIC", getMyAgentCDESppost().getClass().getField("pomatr"), "STRING"));
	mappage.put("POCOND", new BasicRecord("POCOND", "CHAR", getMyAgentCDESppost().getClass().getField("pocond"), "STRING"));
	mappage.put("PODVAL", new BasicRecord("PODVAL", "NUMERIC", getMyAgentCDESppost().getClass().getField("podval"), "STRING"));
	mappage.put("PODSUP", new BasicRecord("PODSUP", "NUMERIC", getMyAgentCDESppost().getClass().getField("podsup"), "STRING"));
	mappage.put("POSERP", new BasicRecord("POSERP", "CHAR", getMyAgentCDESppost().getClass().getField("poserp"), "STRING"));
	mappage.put("CODFON", new BasicRecord("CODFON", "CHAR", getMyAgentCDESppost().getClass().getField("codfon"), "STRING"));
	mappage.put("CODACT", new BasicRecord("CODACT", "CHAR", getMyAgentCDESppost().getClass().getField("codact"), "STRING"));
	mappage.put("NOACTI", new BasicRecord("NOACTI", "CHAR", getMyAgentCDESppost().getClass().getField("noacti"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : AgentCDESppost.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerAgentCDESppost(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with ur");
}
/**
 * Retourne un AgentCDESppost.
 * @return AgentCDESppost
 */
public AgentCDESppost chercherAgentCDESppost(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (AgentCDESppost)executeSelect(aTransaction,"select * from "+getTable()+" where pomatr = "+cle+" with ur");
}
}
