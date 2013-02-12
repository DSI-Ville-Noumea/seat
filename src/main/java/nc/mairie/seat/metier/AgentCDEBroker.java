package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
/**
 * Broker de l'Objet métier AgentCDE
 */
public class AgentCDEBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur AgentCDEBroker.
 */
public AgentCDEBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentCDEMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new AgentCDE() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentCDEMetier
 */
protected AgentCDE getMyAgentCDE() {
	return (AgentCDE)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "MAIRCDE.SPPERS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("IDINDI", new BasicRecord("IDINDI", "DECIMAL", getMyAgentCDE().getClass().getField("idindi"), "STRING"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyAgentCDE().getClass().getField("nomatr"), "STRING"));
	mappage.put("NOM", new BasicRecord("NOM", "CHAR", getMyAgentCDE().getClass().getField("nom"), "STRING"));
	mappage.put("PRENOM", new BasicRecord("PRENOM", "CHAR", getMyAgentCDE().getClass().getField("prenom"), "STRING"));
	mappage.put("NOMJFI", new BasicRecord("NOMJFI", "CHAR", getMyAgentCDE().getClass().getField("nomjfi"), "STRING"));
	mappage.put("DATNAI", new BasicRecord("DATNAI", "NUMERIC", getMyAgentCDE().getClass().getField("datnai"), "STRING"));
	mappage.put("LIEUNA", new BasicRecord("LIEUNA", "CHAR", getMyAgentCDE().getClass().getField("lieuna"), "STRING"));
	mappage.put("CDDESI", new BasicRecord("CDDESI", "CHAR", getMyAgentCDE().getClass().getField("cddesi"), "STRING"));
	mappage.put("SEXE", new BasicRecord("SEXE", "CHAR", getMyAgentCDE().getClass().getField("sexe"), "STRING"));
	mappage.put("NATION", new BasicRecord("NATION", "CHAR", getMyAgentCDE().getClass().getField("nation"), "STRING"));
	mappage.put("CDFAMI", new BasicRecord("CDFAMI", "CHAR", getMyAgentCDE().getClass().getField("cdfami"), "STRING"));
	mappage.put("NINSEE", new BasicRecord("NINSEE", "DECIMAL", getMyAgentCDE().getClass().getField("ninsee"), "STRING"));
	mappage.put("DATTIT", new BasicRecord("DATTIT", "NUMERIC", getMyAgentCDE().getClass().getField("dattit"), "STRING"));
	mappage.put("DATDEC", new BasicRecord("DATDEC", "NUMERIC", getMyAgentCDE().getClass().getField("datdec"), "STRING"));
	mappage.put("CDREGL", new BasicRecord("CDREGL", "NUMERIC", getMyAgentCDE().getClass().getField("cdregl"), "STRING"));
	mappage.put("IDADRS", new BasicRecord("IDADRS", "NUMERIC", getMyAgentCDE().getClass().getField("idadrs"), "STRING"));
	mappage.put("IDCPTE", new BasicRecord("IDCPTE", "NUMERIC", getMyAgentCDE().getClass().getField("idcpte"), "STRING"));
	mappage.put("TELDOM", new BasicRecord("TELDOM", "NUMERIC", getMyAgentCDE().getClass().getField("teldom"), "STRING"));
	mappage.put("NOPORT", new BasicRecord("NOPORT", "DECIMAL", getMyAgentCDE().getClass().getField("noport"), "STRING"));
	mappage.put("BISTER", new BasicRecord("BISTER", "CHAR", getMyAgentCDE().getClass().getField("bister"), "STRING"));
	mappage.put("LIDOPU", new BasicRecord("LIDOPU", "CHAR", getMyAgentCDE().getClass().getField("lidopu"), "STRING"));
	mappage.put("LIRUE", new BasicRecord("LIRUE", "CHAR", getMyAgentCDE().getClass().getField("lirue"), "STRING"));
	mappage.put("BP", new BasicRecord("BP", "CHAR", getMyAgentCDE().getClass().getField("bp"), "STRING"));
	mappage.put("LICARE", new BasicRecord("LICARE", "CHAR", getMyAgentCDE().getClass().getField("licare"), "STRING"));
	mappage.put("CDVILL", new BasicRecord("CDVILL", "DECIMAL", getMyAgentCDE().getClass().getField("cdvill"), "STRING"));
	mappage.put("LIVILL", new BasicRecord("LIVILL", "CHAR", getMyAgentCDE().getClass().getField("livill"), "STRING"));
	mappage.put("CDBANQ", new BasicRecord("CDBANQ", "DECIMAL", getMyAgentCDE().getClass().getField("cdbanq"), "STRING"));
	mappage.put("CDGUIC", new BasicRecord("CDGUIC", "DECIMAL", getMyAgentCDE().getClass().getField("cdguic"), "STRING"));
	mappage.put("NOCPTE", new BasicRecord("NOCPTE", "CHAR", getMyAgentCDE().getClass().getField("nocpte"), "STRING"));
	mappage.put("CLERIB", new BasicRecord("CLERIB", "NUMERIC", getMyAgentCDE().getClass().getField("clerib"), "STRING"));
	mappage.put("CDELEC", new BasicRecord("CDELEC", "CHAR", getMyAgentCDE().getClass().getField("cdelec"), "STRING"));
	mappage.put("DATEMB", new BasicRecord("DATEMB", "NUMERIC", getMyAgentCDE().getClass().getField("datemb"), "STRING"));
	mappage.put("CDETUD", new BasicRecord("CDETUD", "NUMERIC", getMyAgentCDE().getClass().getField("cdetud"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : AgentCDE.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerAgentCDE(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" inner join MAIRCDE.SPPOST on nomatr = pomatr and codact <> 'I' order by nom with ur");
}
/**
 * Retourne un AgentCDE.
 * @return AgentCDE
 */
public AgentCDE chercherAgentCDE(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (AgentCDE)executeSelect(aTransaction,"select * from "+getTable()+" where nomatr = "+cle+" with ur");
}
/**
 * Retourne un AgentCDE.
 * @return AgentCDE
 */
public java.util.ArrayList chercherAgentCDENom(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	String dateDuJour = Services.formateDateInternationale(Services.dateDuJour());
	dateDuJour = Services.convertitDate(dateDuJour,"yyyy-mm-dd","yyyymmdd");
	return executeSelectListe(aTransaction,"select * from "+getTable()+",MAIRIE.SPMTSR where "+getTable()+".nomatr=MAIRIE.SPMTSR.nomatr and upper(nom) like '"+param+"%' and mairie.spmtsr.datdeb<="+dateDuJour+" and mairie.spmtsr.servi='2000' and (mairie.spmtsr.datfin=0 or mairie.spmtsr.datfin>="+dateDuJour+") with ur");
}
}
