package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier AActifs
 */
public class AActifsBroker extends nc.mairie.technique.BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : AActifs.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerAActifs(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with ur");
}
/**
 * Retourne un AActifs.
 * @return AActifs
 */
public AActifs chercherAActifs(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (AActifs)executeSelect(aTransaction,"select * from "+getTable()+" where nomatr = "+cle+"  with ur");
}

/**
 * Retourne un ArrayList d'objet métier : AActifs.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerAActifsService(nc.mairie.technique.Transaction aTransaction,String servi) throws Exception {
	if (!servi.substring(0,1).equals("A")){
		servi = servi.substring(0,3);
	}
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where servi like '"+servi+"%' order by nom  with ur");
}

/**
 * Retourne un ArrayList d'objet métier : AActifs.
 * @return java.util.ArrayList
 */
public java.util.ArrayList chercherListAgentServiceInfosSce(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	String servi = param.substring(0,3); 
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where servi like '"+servi+"%' order by nom  with ur");
}


/**
 * Constructeur AActifsBroker.
 */
public AActifsBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AActifsMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
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
	return "SEAT.V_AGENTACTIFS";
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
