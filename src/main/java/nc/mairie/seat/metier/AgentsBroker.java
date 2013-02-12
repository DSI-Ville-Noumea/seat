package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
/**
 * Broker de l'Objet métier Agents
 */
public class AgentsBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur AgentsBroker.
 */
public AgentsBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentsMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new Agents() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentsMetier
 */
protected Agents getMyAgents() {
	return (Agents)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "MAIRIE.SPPERS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("IDINDI", new BasicRecord("IDINDI", "DECIMAL", getMyAgents().getClass().getField("idindi"), "STRING"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyAgents().getClass().getField("nomatr"), "STRING"));
	mappage.put("NOM", new BasicRecord("NOM", "CHAR", getMyAgents().getClass().getField("nom"), "STRING"));
	mappage.put("PRENOM", new BasicRecord("PRENOM", "CHAR", getMyAgents().getClass().getField("prenom"), "STRING"));
	mappage.put("NOMJFI", new BasicRecord("NOMJFI", "CHAR", getMyAgents().getClass().getField("nomjfi"), "STRING"));
	mappage.put("DATNAI", new BasicRecord("DATNAI", "NUMERIC", getMyAgents().getClass().getField("datnai"), "STRING"));
	mappage.put("LIEUNA", new BasicRecord("LIEUNA", "CHAR", getMyAgents().getClass().getField("lieuna"), "STRING"));
	mappage.put("CDDESI", new BasicRecord("CDDESI", "CHAR", getMyAgents().getClass().getField("cddesi"), "STRING"));
	mappage.put("SEXE", new BasicRecord("SEXE", "CHAR", getMyAgents().getClass().getField("sexe"), "STRING"));
	mappage.put("NATION", new BasicRecord("NATION", "CHAR", getMyAgents().getClass().getField("nation"), "STRING"));
	mappage.put("CDFAMI", new BasicRecord("CDFAMI", "CHAR", getMyAgents().getClass().getField("cdfami"), "STRING"));
	mappage.put("NINSEE", new BasicRecord("NINSEE", "DECIMAL", getMyAgents().getClass().getField("ninsee"), "STRING"));
	mappage.put("DATTIT", new BasicRecord("DATTIT", "NUMERIC", getMyAgents().getClass().getField("dattit"), "STRING"));
	mappage.put("DATDEC", new BasicRecord("DATDEC", "NUMERIC", getMyAgents().getClass().getField("datdec"), "STRING"));
	mappage.put("CDREGL", new BasicRecord("CDREGL", "NUMERIC", getMyAgents().getClass().getField("cdregl"), "STRING"));
	mappage.put("IDADRS", new BasicRecord("IDADRS", "NUMERIC", getMyAgents().getClass().getField("idadrs"), "STRING"));
	mappage.put("IDCPTE", new BasicRecord("IDCPTE", "NUMERIC", getMyAgents().getClass().getField("idcpte"), "STRING"));
	mappage.put("TELDOM", new BasicRecord("TELDOM", "NUMERIC", getMyAgents().getClass().getField("teldom"), "STRING"));
	mappage.put("NOPORT", new BasicRecord("NOPORT", "DECIMAL", getMyAgents().getClass().getField("noport"), "STRING"));
	mappage.put("BISTER", new BasicRecord("BISTER", "CHAR", getMyAgents().getClass().getField("bister"), "STRING"));
	mappage.put("LIDOPU", new BasicRecord("LIDOPU", "CHAR", getMyAgents().getClass().getField("lidopu"), "STRING"));
	mappage.put("LIRUE", new BasicRecord("LIRUE", "CHAR", getMyAgents().getClass().getField("lirue"), "STRING"));
	mappage.put("BP", new BasicRecord("BP", "CHAR", getMyAgents().getClass().getField("bp"), "STRING"));
	mappage.put("LICARE", new BasicRecord("LICARE", "CHAR", getMyAgents().getClass().getField("licare"), "STRING"));
	mappage.put("CDVILL", new BasicRecord("CDVILL", "DECIMAL", getMyAgents().getClass().getField("cdvill"), "STRING"));
	mappage.put("LIVILL", new BasicRecord("LIVILL", "CHAR", getMyAgents().getClass().getField("livill"), "STRING"));
	mappage.put("CDBANQ", new BasicRecord("CDBANQ", "DECIMAL", getMyAgents().getClass().getField("cdbanq"), "STRING"));
	mappage.put("CDGUIC", new BasicRecord("CDGUIC", "DECIMAL", getMyAgents().getClass().getField("cdguic"), "STRING"));
	mappage.put("NOCPTE", new BasicRecord("NOCPTE", "CHAR", getMyAgents().getClass().getField("nocpte"), "STRING"));
	mappage.put("CLERIB", new BasicRecord("CLERIB", "NUMERIC", getMyAgents().getClass().getField("clerib"), "STRING"));
	mappage.put("CDELEC", new BasicRecord("CDELEC", "CHAR", getMyAgents().getClass().getField("cdelec"), "STRING"));
	mappage.put("DATEMB", new BasicRecord("DATEMB", "NUMERIC", getMyAgents().getClass().getField("datemb"), "STRING"));
	mappage.put("CDETUD", new BasicRecord("CDETUD", "NUMERIC", getMyAgents().getClass().getField("cdetud"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : Agents.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerAgents(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with ur");
}
/**
 * Retourne un Agents.
 * @return Agents
 */
public Agents chercherAgents(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Agents)executeSelect(aTransaction,"select * from "+getTable()+" where nomatr = "+cle+" with ur");
}
/**
 * Retourne un Agents.
 * @return Agents
 */
public java.util.ArrayList chercherAgentsNom(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	String dateDuJour = Services.formateDateInternationale(Services.dateDuJour());
	dateDuJour = Services.convertitDate(dateDuJour,"yyyy-mm-dd","yyyymmdd");
	return executeSelectListe(aTransaction,"select * from "+getTable()+",mairie.spmtsr where mairie.spmtsr.nomatr="+getTable()+".nomatr and upper(nom) like '"+param+"%' and mairie.spmtsr.datdeb<="+dateDuJour+" and (mairie.spmtsr.datfin=0 or mairie.spmtsr.datfin>="+dateDuJour+") order by nom with ur");
}

}
