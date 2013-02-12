package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Declarations
 */
public class DeclarationsBroker extends nc.mairie.technique.BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerDeclarations(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierDeclarations(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerDeclarations(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Declarations.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerDeclarations(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by  \"DATE\" desc, codedec desc");
}
/**
 * Retourne un ArrayList d'objet métier : Declarations.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerDeclarationsOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeot="+numot);
}

/**
 * Retourne un ArrayList d'objet métier : Declarations.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerDeclarationsEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numinv = '"+ inv+"'");
}


/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCodeDecl(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier code
	return executeCompter(aTransaction, "select max(codedec) from "+ getTable());
	
}

/**
 * Retourne un Declarations.
 * @return Declarations
 */
public Declarations chercherDeclarations(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Declarations)executeSelect(aTransaction,"select * from "+getTable()+" where CODEDEC = "+cle+"");
}

/**
 * Retourne un Declarations.
 * @return Declarations
 */
public Declarations chercherDeclarationsOT(nc.mairie.technique.Transaction aTransaction, String numot) throws Exception {
	return (Declarations)executeSelect(aTransaction,"select * from "+getTable()+" where codeot = "+numot+"");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existeDeclarations(nc.mairie.technique.Transaction aTransaction, String numot) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codeOt = "+numot);
}

/**
 * Constructeur DeclarationsBroker.
 */
public DeclarationsBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.DeclarationsMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
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
	return "SEAT.F_DECLARATIONS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
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
