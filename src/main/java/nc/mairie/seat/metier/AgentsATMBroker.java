package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier AgentsATM
 */
public class AgentsATMBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerAgentsATM(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierAgentsATM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerAgentsATM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : AgentsATM.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AgentsATM> listerAgentsATM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+", MAIRIE_SPPERS where "+getTable()+".matricule=MAIRIE_SPPERS.nomatr order by nom");
}
/**
 * Retourne un AgentsATM.
 * @param aTransaction Transaction
 * @param cle cle
 * @return AgentsATM
 * @throws Exception Exception
 */
public AgentsATM chercherAgentsATM(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (AgentsATM)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}
/**
 * Retourne un AgentsATM.
 * @param aTransaction Transaction
 * @param nomatr nomatr
 * @return AgentsATM
 * @throws Exception Exception
 */
public AgentsATM chercherAgentsATMMatr(nc.mairie.technique.Transaction aTransaction, String nomatr) throws Exception {
	return (AgentsATM)executeSelect(aTransaction,"select * from "+getTable()+" where matricule = "+nomatr+"");
}
/**
 * Constructeur AgentsATMBroker.
 * @param aMetier BasicMetier
 */
public AgentsATMBroker(AgentsATM aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentsATMMetier
 */
@Override
protected AgentsATM definirMyMetier() {
	return new AgentsATM() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentsATMMetier
 */
protected AgentsATM getMyAgentsATM() {
	return (AgentsATM)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_AGENTSATM";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("MATRICULE", new BasicRecord("MATRICULE", "NUMERIC", getMyAgentsATM().getClass().getField("matricule"), "STRING"));
	mappage.put("ESTMECANICIEN", new BasicRecord("ESTMECANICIEN", "VARCHAR", getMyAgentsATM().getClass().getField("estmecanicien"), "STRING"));
	mappage.put("CODESPE", new BasicRecord("CODESPE", "INTEGER", getMyAgentsATM().getClass().getField("codespe"), "STRING"));
	return mappage;
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeAgentsATM(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where matricule = "+param);
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param spe spe
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeAgentsATMSpe(nc.mairie.technique.Transaction aTransaction, String spe) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codespe="+spe);
}

}
