package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier AgentAtmRenseignements
 */
public class AgentAtmRenseignementsBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : AgentAtmRenseignements.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AgentAtmRenseignements> listerAgentAtmRenseignements(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by nom  with ur");
}
/**
 * Retourne un AgentAtmRenseignements.
 * @param aTransaction Transaction
 * @param cle cle
 * @return AgentAtmRenseignements
 * @throws Exception Exception
 */
public AgentAtmRenseignements chercherAgentAtmRenseignements(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (AgentAtmRenseignements)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+" with ur");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param nomatr nomatr
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeMecanicien(nc.mairie.technique.Transaction aTransaction, String nomatr) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where nomatr = "+nomatr+" with ur");
}

/**
 * Constructeur AgentAtmRenseignementsBroker.
 * @param aMetier BasicMetier
 */
public AgentAtmRenseignementsBroker(AgentAtmRenseignements aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentAtmRenseignementsMetier
 */
@Override
protected AgentAtmRenseignements definirMyMetier() {
	return new AgentAtmRenseignements() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentAtmRenseignementsMetier
 */
protected AgentAtmRenseignements getMyAgentAtmRenseignements() {
	return (AgentAtmRenseignements)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "V_AGENTSATMRENSEIGNEMENTS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyAgentAtmRenseignements().getClass().getField("nomatr"), "STRING"));
	mappage.put("NOM", new BasicRecord("NOM", "CHAR", getMyAgentAtmRenseignements().getClass().getField("nom"), "STRING"));
	mappage.put("PRENOM", new BasicRecord("PRENOM", "CHAR", getMyAgentAtmRenseignements().getClass().getField("prenom"), "STRING"));
	mappage.put("CODESPE", new BasicRecord("CODESPE", "INTEGER", getMyAgentAtmRenseignements().getClass().getField("codespe"), "STRING"));
	mappage.put("LIBELLESPE", new BasicRecord("LIBELLESPE", "VARCHAR", getMyAgentAtmRenseignements().getClass().getField("libellespe"), "STRING"));
	mappage.put("ESTMECANICIEN", new BasicRecord("ESTMECANICIEN", "VARCHAR", getMyAgentAtmRenseignements().getClass().getField("estmecanicien"), "STRING"));
	return mappage;
}
}
