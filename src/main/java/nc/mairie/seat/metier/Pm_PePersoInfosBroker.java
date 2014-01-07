package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Pm_PePersoInfos
 */
public class Pm_PePersoInfosBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur Pm_PePersoInfosBroker.
 */
public Pm_PePersoInfosBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Pm_PePersoInfosMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new Pm_PePersoInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Pm_PePersoInfosMetier
 */
protected Pm_PePersoInfos getMyPm_PePersoInfos() {
	return (Pm_PePersoInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_PM_PEPERSOINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("PMINV", new BasicRecord("PMINV", "VARCHAR", getMyPm_PePersoInfos().getClass().getField("pminv"), "STRING"));
	mappage.put("PMSERIE", new BasicRecord("PMSERIE", "VARCHAR", getMyPm_PePersoInfos().getClass().getField("pmserie"), "STRING"));
	mappage.put("DMES", new BasicRecord("DMES", "DATE", getMyPm_PePersoInfos().getClass().getField("dmes"), "DATE"));
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyPm_PePersoInfos().getClass().getField("numfiche"), "STRING"));
	mappage.put("DENTREE", new BasicRecord("DENTREE", "DATE", getMyPm_PePersoInfos().getClass().getField("dentree"), "DATE"));
	mappage.put("DSORTIE", new BasicRecord("DSORTIE", "DATE", getMyPm_PePersoInfos().getClass().getField("dsortie"), "DATE"));
	mappage.put("VALIDE", new BasicRecord("VALIDE", "VARCHAR", getMyPm_PePersoInfos().getClass().getField("valide"), "STRING"));
	mappage.put("CODEPMPEP", new BasicRecord("CODEPMPEP", "INTEGER", getMyPm_PePersoInfos().getClass().getField("codepmpep"), "STRING"));
	mappage.put("LIBELLEENTRETIEN", new BasicRecord("LIBELLEENTRETIEN", "VARCHAR", getMyPm_PePersoInfos().getClass().getField("libelleentretien"), "STRING"));
	mappage.put("DPREV", new BasicRecord("DPREV", "DATE", getMyPm_PePersoInfos().getClass().getField("dprev"), "DATE"));
	mappage.put("DREAL", new BasicRecord("DREAL", "DATE", getMyPm_PePersoInfos().getClass().getField("dreal"), "DATE"));
	mappage.put("DUREE", new BasicRecord("DUREE", "VARCHAR", getMyPm_PePersoInfos().getClass().getField("duree"), "STRING"));
	mappage.put("SINISTRE", new BasicRecord("SINISTRE", "VARCHAR", getMyPm_PePersoInfos().getClass().getField("sinistre"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "VARCHAR", getMyPm_PePersoInfos().getClass().getField("commentaire"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : Pm_PePersoInfos.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPm_PePersoInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un Pm_PePersoInfos.
 * @return Pm_PePersoInfos
 */
public Pm_PePersoInfos chercherPm_PePersoInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Pm_PePersoInfos)executeSelect(aTransaction,"select * from "+getTable()+" where codepmpep = "+cle+"");
}

// liste des peperso d'une fiche
public java.util.ArrayList chercherPmPePersoInfosFPM(nc.mairie.technique.Transaction aTransaction, String numfiche) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche = "+numfiche);
}

/**
 *liste des peperso fait
 */
public java.util.ArrayList listerPmPePersoInfosFait(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where dreal <> '0001-01-01' and pminv='"+inv+"' order by "+tri+"");
}

}
