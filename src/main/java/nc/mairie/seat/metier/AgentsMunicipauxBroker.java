package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier AgentsMunicipaux
 */
public class AgentsMunicipauxBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur AgentsMunicipauxBroker.
 */
public AgentsMunicipauxBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentsMunicipauxMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new AgentsMunicipaux() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentsMunicipauxMetier
 */
protected AgentsMunicipaux getMyAgentsMunicipaux() {
	return (AgentsMunicipaux)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_AGENTS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyAgentsMunicipaux().getClass().getField("nomatr"), "STRING"));
	mappage.put("NOM", new BasicRecord("NOM", "CHAR", getMyAgentsMunicipaux().getClass().getField("nom"), "STRING"));
	mappage.put("PRENOM", new BasicRecord("PRENOM", "CHAR", getMyAgentsMunicipaux().getClass().getField("prenom"), "STRING"));
	mappage.put("CODACT", new BasicRecord("CODACT", "CHAR", getMyAgentsMunicipaux().getClass().getField("codact"), "STRING"));
	mappage.put("SERVI", new BasicRecord("SERVI", "CHAR", getMyAgentsMunicipaux().getClass().getField("servi"), "STRING"));
	mappage.put("DATFIN", new BasicRecord("DATFIN", "NUMERIC", getMyAgentsMunicipaux().getClass().getField("datfin"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : AgentsMunicipaux.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerAgentsMunicipaux(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with ur");
}
/**
 * Retourne un AgentsMunicipaux.
 * @return AgentsMunicipaux
 */
public AgentsMunicipaux chercherAgentsMunicipaux(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (AgentsMunicipaux)executeSelect(aTransaction,"select * from "+getTable()+" where nomatr = "+cle+" with ur");
}

public AgentsMunicipaux chercherAgentsMunicipauxService(nc.mairie.technique.Transaction aTransaction, String cle,String servi) throws Exception {
	return (AgentsMunicipaux)executeSelect(aTransaction,"select * from "+getTable()+" where nomatr = "+cle+" with ur");
	//SUPPRESSION PAR LB 05/09/11
	/*
	//System.out.println("chercherAgentsMunicipauxService avec clé nomatr="+cle+" et servi="+servi);
	servi = servi.substring(0,3);
	AgentsMunicipaux am=new AgentsMunicipaux();
	//am = (AgentsMunicipaux)executeSelect(aTransaction,"select * from "+getTable()+" where nomatr = "+cle+" and servi like '"+servi+"%'");
	am = (AgentsMunicipaux)executeSelect(aTransaction,"select * from "+getTable()+" where nomatr = "+cle);
	System.out.println("am="+am);
	if (null==am || null==am.getNomatr())
		am = chercherAgentsMunicipaux(aTransaction, cle);
	 
	System.out.println("am="+am);
	if (null!=am)
		System.out.println("AM nomatr="+am.nomatr);
	return am;
	*/
}

public java.util.ArrayList listerAgentsMunicipauxNom(nc.mairie.technique.Transaction aTransaction,String nom) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where upper(nom) like '"+nom+"%' order by nom, prenom with ur");
}

public java.util.ArrayList listerAgentsMunicipauxNomServi(nc.mairie.technique.Transaction aTransaction,String nom,String servi) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where upper(nom) like '"+nom+"%' and servi like '"+servi+"%' order by nom, prenom with ur");
}

public java.util.ArrayList listerAgentsMunicipauxServi(nc.mairie.technique.Transaction aTransaction,String servi) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where servi like '"+servi+"%' order by nom, prenom with ur");
}


}
