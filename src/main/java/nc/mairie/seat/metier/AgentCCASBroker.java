package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
/**
 * Broker de l'Objet métier AgentCCAS
 */
public class AgentCCASBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur AgentCCASBroker.
 */
public AgentCCASBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentCCASMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new AgentCCAS() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentCCASMetier
 */
protected AgentCCAS getMyAgentCCAS() {
	return (AgentCCAS)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "MAIRCCAS.SPPERS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("IDINDI", new BasicRecord("IDINDI", "DECIMAL", getMyAgentCCAS().getClass().getField("idindi"), "STRING"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyAgentCCAS().getClass().getField("nomatr"), "STRING"));
	mappage.put("NOM", new BasicRecord("NOM", "CHAR", getMyAgentCCAS().getClass().getField("nom"), "STRING"));
	mappage.put("PRENOM", new BasicRecord("PRENOM", "CHAR", getMyAgentCCAS().getClass().getField("prenom"), "STRING"));
	mappage.put("NOMJFI", new BasicRecord("NOMJFI", "CHAR", getMyAgentCCAS().getClass().getField("nomjfi"), "STRING"));
	mappage.put("DATNAI", new BasicRecord("DATNAI", "NUMERIC", getMyAgentCCAS().getClass().getField("datnai"), "STRING"));
	mappage.put("LIEUNA", new BasicRecord("LIEUNA", "CHAR", getMyAgentCCAS().getClass().getField("lieuna"), "STRING"));
	mappage.put("CDDESI", new BasicRecord("CDDESI", "CHAR", getMyAgentCCAS().getClass().getField("cddesi"), "STRING"));
	mappage.put("SEXE", new BasicRecord("SEXE", "CHAR", getMyAgentCCAS().getClass().getField("sexe"), "STRING"));
	mappage.put("NATION", new BasicRecord("NATION", "CHAR", getMyAgentCCAS().getClass().getField("nation"), "STRING"));
	mappage.put("CDFAMI", new BasicRecord("CDFAMI", "CHAR", getMyAgentCCAS().getClass().getField("cdfami"), "STRING"));
	mappage.put("NINSEE", new BasicRecord("NINSEE", "DECIMAL", getMyAgentCCAS().getClass().getField("ninsee"), "STRING"));
	mappage.put("DATTIT", new BasicRecord("DATTIT", "NUMERIC", getMyAgentCCAS().getClass().getField("dattit"), "STRING"));
	mappage.put("DATDEC", new BasicRecord("DATDEC", "NUMERIC", getMyAgentCCAS().getClass().getField("datdec"), "STRING"));
	mappage.put("CDREGL", new BasicRecord("CDREGL", "NUMERIC", getMyAgentCCAS().getClass().getField("cdregl"), "STRING"));
	mappage.put("IDADRS", new BasicRecord("IDADRS", "NUMERIC", getMyAgentCCAS().getClass().getField("idadrs"), "STRING"));
	mappage.put("IDCPTE", new BasicRecord("IDCPTE", "NUMERIC", getMyAgentCCAS().getClass().getField("idcpte"), "STRING"));
	mappage.put("TELDOM", new BasicRecord("TELDOM", "NUMERIC", getMyAgentCCAS().getClass().getField("teldom"), "STRING"));
	mappage.put("NOPORT", new BasicRecord("NOPORT", "DECIMAL", getMyAgentCCAS().getClass().getField("noport"), "STRING"));
	mappage.put("BISTER", new BasicRecord("BISTER", "CHAR", getMyAgentCCAS().getClass().getField("bister"), "STRING"));
	mappage.put("LIDOPU", new BasicRecord("LIDOPU", "CHAR", getMyAgentCCAS().getClass().getField("lidopu"), "STRING"));
	mappage.put("LIRUE", new BasicRecord("LIRUE", "CHAR", getMyAgentCCAS().getClass().getField("lirue"), "STRING"));
	mappage.put("BP", new BasicRecord("BP", "CHAR", getMyAgentCCAS().getClass().getField("bp"), "STRING"));
	mappage.put("LICARE", new BasicRecord("LICARE", "CHAR", getMyAgentCCAS().getClass().getField("licare"), "STRING"));
	mappage.put("CDVILL", new BasicRecord("CDVILL", "DECIMAL", getMyAgentCCAS().getClass().getField("cdvill"), "STRING"));
	mappage.put("LIVILL", new BasicRecord("LIVILL", "CHAR", getMyAgentCCAS().getClass().getField("livill"), "STRING"));
	mappage.put("CDBANQ", new BasicRecord("CDBANQ", "DECIMAL", getMyAgentCCAS().getClass().getField("cdbanq"), "STRING"));
	mappage.put("CDGUIC", new BasicRecord("CDGUIC", "DECIMAL", getMyAgentCCAS().getClass().getField("cdguic"), "STRING"));
	mappage.put("NOCPTE", new BasicRecord("NOCPTE", "CHAR", getMyAgentCCAS().getClass().getField("nocpte"), "STRING"));
	mappage.put("CLERIB", new BasicRecord("CLERIB", "NUMERIC", getMyAgentCCAS().getClass().getField("clerib"), "STRING"));
	mappage.put("CDELEC", new BasicRecord("CDELEC", "CHAR", getMyAgentCCAS().getClass().getField("cdelec"), "STRING"));
	mappage.put("DATEMB", new BasicRecord("DATEMB", "NUMERIC", getMyAgentCCAS().getClass().getField("datemb"), "STRING"));
	mappage.put("CDETUD", new BasicRecord("CDETUD", "NUMERIC", getMyAgentCCAS().getClass().getField("cdetud"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : AgentCCAS.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerAgentCCAS(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" inner join MAIRCCAS.SPPOST on nomatr = pomatr and codact <> 'I'  order by nom with ur");
}
/**
 * Retourne un AgentCCAS.
 * @return AgentCCAS
 */
public AgentCCAS chercherAgentCCAS(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (AgentCCAS)executeSelect(aTransaction,"select * from "+getTable()+" where nomatr = "+cle+"  with ur");
}
/**
 * Retourne un AgentCCAS.
 * @return AgentCCAS
 */
public java.util.ArrayList chercherAgentCCASNom(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	String dateDuJour = Services.formateDateInternationale(Services.dateDuJour());
	dateDuJour = Services.convertitDate(dateDuJour,"yyyy-mm-dd","yyyymmdd");
	return executeSelectListe(aTransaction,"select * from "+getTable()+",mairie.spmtsr where upper(nom) like '"+param+"%'and mairie.spmtsr.nomatr="+getTable()+".nomatr and mairie.spmtsr.datdeb<="+dateDuJour+" and (mairie.spmtsr.datfin=0 or mairie.spmtsr.datfin>="+dateDuJour+") and servi='5000' with ur");
}
}
