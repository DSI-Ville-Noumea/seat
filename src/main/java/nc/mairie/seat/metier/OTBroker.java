package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier OT
 */
public class OTBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerOT(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierOT(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerOT(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OT> listerOT(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" ot, f_equipement equipement where equipement.NUMEROINVENTAIRE = ot.NUMINV");
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @param inv inv
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OT> listerOTEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numinv='"+inv+"'");
}


/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OT> listerOTValide(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" ot, f_equipement equipement where equipement.NUMEROINVENTAIRE = ot.NUMINV and valide='T'");
}
/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OT> listerOTAValider(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F'");
}
/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OT> listerOTEncours(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" ot, f_equipement equipement where equipement.NUMEROINVENTAIRE = ot.NUMINV and valide='F' ");//and numeroot in(select codeot from f_pe_perso)");                                      
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OT> listerOTDeclarationsEncours(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F' and numeroot in(select codeot from f_declarations)");                                      
}
/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OT> listerOTDeclarationsValide(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='T' and numeroot in(select codeot from f_declarations)");                                      
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 *//*
public ArrayList<OT> listerOTEquip(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select distinct(numeroot),numeroinventaire,numeroimmatriculation,dateentree,datesortie,compteur,valide,commentaire from "+getTable()+", f_equipement, f_pe_perso where f_equipement.numeroinventaire = f_pe_perso.codeequip and f_pe_perso.codeot = f_ot.numeroot");
}*/

/**
 * Retourne un OT.
 * @param aTransaction Transaction
 * @param cle cle
 * @return OT
 * @throws Exception Exception
 */
public OT chercherOT(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (OT)executeSelect(aTransaction,"select * from "+getTable()+" where numeroot = "+cle+"");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCodeOt(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier numeroot
	return executeCompter(aTransaction, "select max(numeroot) from "+ getTable());
	
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeCarburant(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeroot = '"+param.toUpperCase()+"'");
}

/**
 * Constructeur OTBroker.
 * @param aMetier BasicMetier
 */
public OTBroker(OT aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.OTMetier
 */
@Override
protected OT definirMyMetier() {
	return new OT() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.OTMetier
 */
protected OT getMyOT() {
	return (OT)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_OT";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMEROOT", new BasicRecord("NUMEROOT", "NUMERIC", getMyOT().getClass().getField("numeroot"), "STRING"));
	mappage.put("DATEENTREE", new BasicRecord("DATEENTREE", "DATE", getMyOT().getClass().getField("dateentree"), "DATE"));
	mappage.put("DATESORTIE", new BasicRecord("DATESORTIE", "DATE", getMyOT().getClass().getField("datesortie"), "DATE"));
	mappage.put("COMPTEUR", new BasicRecord("COMPTEUR", "INTEGER", getMyOT().getClass().getField("compteur"), "STRING"));
	mappage.put("VALIDE", new BasicRecord("VALIDE", "CHAR", getMyOT().getClass().getField("valide"), "STRING"));
	mappage.put("NUMEROBC", new BasicRecord("NUMEROBC", "INTEGER", getMyOT().getClass().getField("numerobc"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "LONG VARCHAR", getMyOT().getClass().getField("commentaire"), "STRING"));
	mappage.put("NUMINV", new BasicRecord("NUMINV", "VARCHAR", getMyOT().getClass().getField("numinv"), "STRING"));
	return mappage;
}
}
