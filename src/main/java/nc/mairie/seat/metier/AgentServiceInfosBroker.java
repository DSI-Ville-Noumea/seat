package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier AgentServiceInfos
 */
public class AgentServiceInfosBroker extends nc.mairie.technique.BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : AgentServiceInfos.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerAgentServiceInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with ur");
}
/**
 * Retourne un AgentServiceInfos.
 * @return AgentServiceInfos
 */
public AgentServiceInfos chercherAgentServiceInfos(nc.mairie.technique.Transaction aTransaction, String nomatr) throws Exception {
	return (AgentServiceInfos)executeSelect(aTransaction,"select * from "+getTable()+" where nomatr = "+nomatr+" with ur");
}
/**
 * Retourne un ArrayList d'objet métier : AgentServiceInfos.
 * @return java.util.ArrayList
 */
public java.util.ArrayList chercherListAgentServiceInfosSce(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	String servi = param.substring(0,3); 
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where servi like '"+servi+"%' and datfin=0 order by nom with ur");
		//	"SELECT MAIRIE.SPPERS.NOMATR "+
			//"FROM (MAIRIE.SPPOST INNER JOIN MAIRIE_SPPERS ON MAIRIE.SPPOST.POMATR = MAIRIE.SPPERS.NOMATR) INNER JOIN MAIRIE.SISERV ON MAIRIE.SPPOST.POSERV = MAIRIE.SISERV.SERVI"+
			//"WHERE (((MAIRIE.SPPOST.PODSUP)=0) AND ((MAIRIE.SPPOST.CODACT)='A') AND ((MAIRIE.SPPOST.POMATR)>1000 And (MAIRIE.SPPOST.POMATR)<9000)))");
}
/**
 * Retourne un ArrayList d'objet métier : AgentServiceInfos.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerAgentService(nc.mairie.technique.Transaction aTransaction,String serv) throws Exception {
	serv = serv.substring(0,3); 
	return executeSelectListe(aTransaction,"select * from mairie.sppers sppers, mairie.spmtsr spmtsr, mairie.sppost sppost where sppers.nomatr=spmtsr.nomatr and sppers.nomatr=sppost.pomatr and spmtsr.servi like '"+serv+"%' and codact='A' and podsup=0 and datfin=0 order by nom with ur");
}
/**
 * Constructeur AgentServiceInfosBroker.
 */
public AgentServiceInfosBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentServiceInfosMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new AgentServiceInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentServiceInfosMetier
 */
protected AgentServiceInfos getMyAgentServiceInfos() {
	return (AgentServiceInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_AGENTSSERVICEINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("LISERV", new BasicRecord("LISERV", "CHAR", getMyAgentServiceInfos().getClass().getField("liserv"), "STRING"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyAgentServiceInfos().getClass().getField("nomatr"), "STRING"));
	mappage.put("NOM", new BasicRecord("NOM", "CHAR", getMyAgentServiceInfos().getClass().getField("nom"), "STRING"));
	mappage.put("PRENOM", new BasicRecord("PRENOM", "CHAR", getMyAgentServiceInfos().getClass().getField("prenom"), "STRING"));
	mappage.put("DATDEB", new BasicRecord("DATDEB", "NUMERIC", getMyAgentServiceInfos().getClass().getField("datdeb"), "STRING"));
	mappage.put("DATFIN", new BasicRecord("DATFIN", "NUMERIC", getMyAgentServiceInfos().getClass().getField("datfin"), "STRING"));
	mappage.put("SERVI", new BasicRecord("SERVI", "CHAR", getMyAgentServiceInfos().getClass().getField("servi"), "STRING"));
	return mappage;
}
}
