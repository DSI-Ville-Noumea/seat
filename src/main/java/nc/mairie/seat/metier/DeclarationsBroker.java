package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Declarations
 */
public class DeclarationsBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception exception
 */
public boolean creerDeclarations(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierDeclarations(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerDeclarations(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Declarations.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception exception
 */
public ArrayList<Declarations> listerDeclarations(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by  DATE desc, codedec desc");
}
/**
 * Retourne un ArrayList d'objet métier : Declarations.
 * @param aTransaction Transaction
 * @param numot numot
 * @return java.util.ArrayList
 * @throws Exception exception
 */
public ArrayList<Declarations> listerDeclarationsOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeot="+numot);
}

/**
 * Retourne un ArrayList d'objet métier : Declarations.
 * @param aTransaction Transaction
 * @param inv inv
 * @return java.util.ArrayList
 * @throws Exception exception
 */
public ArrayList<Declarations> listerDeclarationsEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numinv = '"+ inv+"'");
}


/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * author : Coralie NICOLAS
 */
public int nouvCodeDecl(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier code
	return executeCompter(aTransaction, "select max(codedec) from "+ getTable());
	
}

/**
 * Retourne un Declarations.
 * @param aTransaction Transaction
 * @param cle cle
 * @return Declarations
 * @throws Exception exception
 */
public Declarations chercherDeclarations(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Declarations)executeSelect(aTransaction,"select * from "+getTable()+" where CODEDEC = "+cle+"");
}

/**
 * Retourne un Declarations.
 * @param aTransaction Transaction
 * @param numot numot
 * @return Declarations
 * @throws Exception exception
 */
public Declarations chercherDeclarationsOT(nc.mairie.technique.Transaction aTransaction, String numot) throws Exception {
	return (Declarations)executeSelect(aTransaction,"select * from "+getTable()+" where codeot = "+numot+"");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param numot numot
 * @return true ou false
 * @throws Exception exception
 */
public boolean existeDeclarations(nc.mairie.technique.Transaction aTransaction, String numot) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codeOt = "+numot);
}

/**
 * Constructeur DeclarationsBroker.
 * @param aMetier BasicMetier
 */
public DeclarationsBroker(Declarations aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.DeclarationsMetier
 */
@Override
protected Declarations definirMyMetier() {
	return new Declarations() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.DeclarationsMetier
 */
protected Declarations getMyDeclarations() {
	return (Declarations)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_DECLARATIONS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODEDEC", new BasicRecord("CODEDEC", "NUMERIC", getMyDeclarations().getClass().getField("codedec"), "STRING"));
	mappage.put("DATE", new BasicRecord("DATE", "DATE", getMyDeclarations().getClass().getField("date"), "DATE"));
	mappage.put("ANOMALIES", new BasicRecord("ANOMALIES", "LONG VARCHAR", getMyDeclarations().getClass().getField("anomalies"), "STRING"));
	mappage.put("MATRICULE", new BasicRecord("MATRICULE", "NUMERIC", getMyDeclarations().getClass().getField("matricule"), "STRING"));
	mappage.put("CODEOT", new BasicRecord("CODEOT", "NUMERIC", getMyDeclarations().getClass().getField("codeot"), "STRING"));
	mappage.put("NUMINV", new BasicRecord("NUMINV", "VARCHAR", getMyDeclarations().getClass().getField("numinv"), "STRING"));
	mappage.put("CODESERVICE", new BasicRecord("CODESERVICE", "VARCHAR", getMyDeclarations().getClass().getField("codeservice"), "STRING"));
	return mappage;
}
}
