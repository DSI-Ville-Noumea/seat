package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier FPMComplete
 */
public class FPMCompleteBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : FPMComplete.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<FPMComplete> listerFPMComplete(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un FPMComplete.
 * @param aTransaction Transaction
 * @param cle cle
 * @return FPMComplete
 * @throws Exception Exception
 */
public FPMComplete chercherFPMComplete(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (FPMComplete)executeSelect(aTransaction,"select * from "+getTable()+" where numfiche = "+cle+"");
}

// liste des encours
public ArrayList<FPMComplete> listerFPMCompleteEnCours(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F'");
}

//liste des valide
public ArrayList<FPMComplete> listerFPMCompleteValide(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='T'");
}


/**
 * Constructeur FPMCompleteBroker.
 * @param aMetier BasicMetier
 */
public FPMCompleteBroker(FPMComplete aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.FPMCompleteMetier
 */
@Override
protected FPMComplete definirMyMetier() {
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
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
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
