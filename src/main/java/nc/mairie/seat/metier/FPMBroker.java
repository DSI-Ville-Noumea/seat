package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier FPM
 */
public class FPMBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur FPMBroker.
 */
public FPMBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.FPMMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new FPM() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.FPMMetier
 */
protected FPM getMyFPM() {
	return (FPM)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_FPM";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyFPM().getClass().getField("numfiche"), "STRING"));
	mappage.put("PMINV", new BasicRecord("PMINV", "VARCHAR", getMyFPM().getClass().getField("pminv"), "STRING"));
	mappage.put("DENTREE", new BasicRecord("DENTREE", "DATE", getMyFPM().getClass().getField("dentree"), "DATE"));
	mappage.put("DSORTIE", new BasicRecord("DSORTIE", "DATE", getMyFPM().getClass().getField("dsortie"), "DATE"));
	mappage.put("VALIDE", new BasicRecord("VALIDE", "VARCHAR", getMyFPM().getClass().getField("valide"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "VARCHAR", getMyFPM().getClass().getField("commentaire"), "STRING"));
	return mappage;
}

// liste
public java.util.ArrayList listerFPM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable());
}

// recherche
public FPM chercherFPM(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (FPM)executeSelect(aTransaction,"select * from "+getTable()+" where numfiche = "+cle+"");
}

// liste des à valider
public java.util.ArrayList listerFPMAValider(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F'");
}

/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerFPM(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}

/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierFPM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}

/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerFPM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCodeFpm(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier numeroot
	return executeCompter(aTransaction, "select max(numfiche) from "+ getTable());
	
}

public java.util.ArrayList listerFpmPmat(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where pminv='"+inv+"'");
}

}
