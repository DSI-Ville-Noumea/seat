package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier FPMComplete
 */
public class FPMCompleteBroker extends nc.mairie.technique.BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : FPMComplete.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerFPMComplete(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un FPMComplete.
 * @return FPMComplete
 */
public FPMComplete chercherFPMComplete(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (FPMComplete)executeSelect(aTransaction,"select * from "+getTable()+" where numfiche = "+cle+"");
}

// liste des encours
public java.util.ArrayList listerFPMCompleteEnCours(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F'");
}

//liste des valide
public java.util.ArrayList listerFPMCompleteValide(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='T'");
}


/**
 * Constructeur FPMCompleteBroker.
 */
public FPMCompleteBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.FPMCompleteMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new FPMComplete() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.FPMCompleteMetier
 */
protected FPMComplete getMyFPMComplete() {
	return (FPMComplete)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_FPMCOMPLETE";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyFPMComplete().getClass().getField("numfiche"), "STRING"));
	mappage.put("PMINV", new BasicRecord("PMINV", "VARCHAR", getMyFPMComplete().getClass().getField("pminv"), "STRING"));
	mappage.put("PMSERIE", new BasicRecord("PMSERIE", "VARCHAR", getMyFPMComplete().getClass().getField("pmserie"), "STRING"));
	mappage.put("DMES", new BasicRecord("DMES", "DATE", getMyFPMComplete().getClass().getField("dmes"), "DATE"));
	mappage.put("DENTREE", new BasicRecord("DENTREE", "DATE", getMyFPMComplete().getClass().getField("dentree"), "DATE"));
	mappage.put("DSORTIE", new BasicRecord("DSORTIE", "DATE", getMyFPMComplete().getClass().getField("dsortie"), "DATE"));
	mappage.put("VALIDE", new BasicRecord("VALIDE", "VARCHAR", getMyFPMComplete().getClass().getField("valide"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "VARCHAR", getMyFPMComplete().getClass().getField("commentaire"), "STRING"));
	return mappage;
}
}
