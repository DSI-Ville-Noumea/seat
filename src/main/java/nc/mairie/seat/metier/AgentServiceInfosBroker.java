package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier AgentServiceInfos
 */
public class AgentServiceInfosBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : AgentServiceInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AgentServiceInfos> listerAgentServiceInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable());
}
/**
 * Retourne un AgentServiceInfos.
 * @param aTransaction Transaction
 * @param nomatr nomatr
 * @return AgentServiceInfos
 * @throws Exception Exception
 */
public AgentServiceInfos chercherAgentServiceInfos(nc.mairie.technique.Transaction aTransaction, String nomatr) throws Exception {
	return (AgentServiceInfos)executeSelect(aTransaction,"select * from "+getTable()+" where nomatr = "+nomatr);
}
/**
 * Retourne un ArrayList d'objet métier : AgentServiceInfos.
 * @param aTransaction Transaction
 * @param param param
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AgentServiceInfos> chercherListAgentServiceInfosSce(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	String servi = param.substring(0,3); //OK
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where servi like '"+servi+"%' and datfin=0 order by nom");
}
/**
 * Retourne un ArrayList d'objet métier : AgentServiceInfos.
 * @param aTransaction Transaction
 * @param serv serv
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AgentServiceInfos> listerAgentService(nc.mairie.technique.Transaction aTransaction,String serv) throws Exception {
	serv = serv.substring(0,3); //OK
	return executeSelectListe(aTransaction,"select * from mairie_sppers sppers, mairie_spmtsr spmtsr, mairie_sppost sppost where sppers.nomatr=spmtsr.nomatr and sppers.nomatr=sppost.pomatr and spmtsr.servi like '"+serv+"%' and codact='A' and podsup=0 and datfin=0 order by nom");
}
/**
 * Constructeur AgentServiceInfosBroker.
 * @param aMetier BasicMetier
 */
public AgentServiceInfosBroker(AgentServiceInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentServiceInfosMetier
 */
@Override
protected AgentServiceInfos definirMyMetier() {
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
	return "V_AGENTSSERVICEINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
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
