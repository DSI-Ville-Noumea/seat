package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier AActifs
 */
public class AActifsBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : AActifs.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AActifs> listerAActifs(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable());
}
/**
 * Retourne un AActifs.
 * @return AActifs
 * @param aTransaction Transaction
 * @param cle cle
 * @throws Exception Exception
 */
public AActifs chercherAActifs(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (AActifs)executeSelect(aTransaction,"select * from "+getTable()+" where nomatr = "+cle);
}

/**
 * Retourne un ArrayList d'objet métier : AActifs.
 * @return java.util.ArrayList
 * @param aTransaction Transaction
 * @param servi servi
 * @throws Exception Exception
 */
public ArrayList<AActifs> listerAActifsService(nc.mairie.technique.Transaction aTransaction,String servi) throws Exception {
	if (!servi.substring(0,1).equals("A")){
		servi = servi.substring(0,3); //OK
	}
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where servi like '"+servi+"%' order by nom");
}

/**
 * Retourne un ArrayList d'objet métier : AActifs.
 * @return java.util.ArrayList
 * @param aTransaction Transaction
 * @param param param
 * @throws Exception Exception
 */
public ArrayList<AActifs> chercherListAgentServiceInfosSce(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	String servi = param.substring(0,3); //OK
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where servi like '"+servi+"%' order by nom");
}


/**
 * Constructeur AActifsBroker.
 * @param aMetier BasicMetier
 */
public AActifsBroker(AActifs aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AActifsMetier
 */
@Override
protected AActifs definirMyMetier() {
	return new AActifs() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AActifsMetier
 */
protected AActifs getMyAActifs() {
	return (AActifs)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "V_AGENTACTIFS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyAActifs().getClass().getField("nomatr"), "STRING"));
	mappage.put("NOM", new BasicRecord("NOM", "CHAR", getMyAActifs().getClass().getField("nom"), "STRING"));
	mappage.put("PRENOM", new BasicRecord("PRENOM", "CHAR", getMyAActifs().getClass().getField("prenom"), "STRING"));
	mappage.put("CODACT", new BasicRecord("CODACT", "CHAR", getMyAActifs().getClass().getField("codact"), "STRING"));
	mappage.put("SERVI", new BasicRecord("SERVI", "CHAR", getMyAActifs().getClass().getField("servi"), "STRING"));
	return mappage;
}
}
