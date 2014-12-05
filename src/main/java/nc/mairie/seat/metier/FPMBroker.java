package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;
/**
 * Broker de l'Objet métier FPM
 */
public class FPMBroker extends BasicBroker {
/**
 * Constructeur FPMBroker.
 * @param aMetier BasicMetier
 */
public FPMBroker(FPM aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.FPMMetier
 */
@Override
protected FPM definirMyMetier() {
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
	return "F_FPM";
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
public ArrayList<FPM> listerFPM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable());
}

// recherche
public FPM chercherFPM(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (FPM)executeSelect(aTransaction,"select * from "+getTable()+" where numfiche = "+cle+"");
}

// liste des à valider
public ArrayList<FPM> listerFPMAValider(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F'");
}

/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerFPM(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}

/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierFPM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}

/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
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

public ArrayList<FPM> listerFpmPmat(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where pminv='"+inv+"'");
}

}
