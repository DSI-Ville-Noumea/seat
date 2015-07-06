package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Modeles
 */
public class ModelesBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerModeles(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierModeles(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerModeles(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Modeles.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Modeles> listerModeles(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by designationmodele");
}
/**
 * Retourne un ArrayList d'objet métier : Modeles.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * liste de modèle de petits matériels
 * @throws Exception Exception
 */
public ArrayList<Modeles> listerModelesMT(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+",f_typeequip where f_typeequip.codetypeequip="+getTable()+".codete and f_typeequip.typete='MT' order by designationmodele");
}
/**
 * Retourne un Modeles.
 * @param aTransaction Transaction
 * @param cle cle
 * @return Modeles
 * @throws Exception Exception
 */
public Modeles chercherModeles(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Modeles)executeSelect(aTransaction,"select * from "+getTable()+" where CODEMODELE = "+cle+"");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * author : Coralie NICOLAS
 */
public int nouvModeles(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier codemodele
	return executeCompter(aTransaction, "select max(codemodele) from "+ getTable());
	
}

/**
 * Retourne un ArrayList d'objet métier : Modeles.
 * @param aTransaction Transaction
 * @param cle cle
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Modeles> listerModelesMarque(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codemarque = "+cle+" order by designationmodele");
}

public ArrayList<Modeles> listerModelesMarqueMT(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codemarque = "+cle+" and codete in(select * from f_typeequip where typete='MT') order by designationmodele");
}

public ArrayList<Modeles> listerModelesLib(nc.mairie.technique.Transaction aTransaction, String modele) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where upper(designationmodele) like '"+modele.toUpperCase()+"%'");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param code code
 * @param param param
 * @param version version
 * @param codecarbu codecarbu
 * @return true ou false
 * @throws Exception Exception
 */
public int existeModeles(nc.mairie.technique.Transaction aTransaction, String code, String param, String version,String codecarbu) throws Exception {
	return executeCompter(aTransaction,"select count(designationmodele) from "+getTable()+" where upper(designationmodele) = '"+param.toUpperCase()+"' and upper(version) = '"+version.toUpperCase()+"' and codemodele<>"+code+" and codecarburant="+codecarbu);
}

/**
 * Constructeur ModelesBroker.
 * @param aMetier BasicMetier
 */
public ModelesBroker(Modeles aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.ModelesMetier
 */
protected Modeles definirMyMetier() {
	return new Modeles() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.ModelesMetier
 */
protected Modeles getMyModeles() {
	return (Modeles)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "F_MODELES";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODEMODELE", new BasicRecord("CODEMODELE", "INTEGER", getMyModeles().getClass().getField("codemodele"), "STRING"));
	mappage.put("NBPNEUAVANT", new BasicRecord("NBPNEUAVANT", "INTEGER", getMyModeles().getClass().getField("nbpneuavant"), "STRING"));
	mappage.put("NBPNEUARRIERE", new BasicRecord("NBPNEUARRIERE", "INTEGER", getMyModeles().getClass().getField("nbpneuarriere"), "STRING"));
	mappage.put("NBESSIEUX", new BasicRecord("NBESSIEUX", "INTEGER", getMyModeles().getClass().getField("nbessieux"), "STRING"));
	mappage.put("CAPACITERESERVOIR", new BasicRecord("CAPACITERESERVOIR", "INTEGER", getMyModeles().getClass().getField("capacitereservoir"), "STRING"));
	mappage.put("CODEMARQUE", new BasicRecord("CODEMARQUE", "INTEGER", getMyModeles().getClass().getField("codemarque"), "STRING"));
	mappage.put("DESIGNATIONMODELE", new BasicRecord("DESIGNATIONMODELE", "VARCHAR", getMyModeles().getClass().getField("designationmodele"), "STRING"));
	mappage.put("CODEPNEU", new BasicRecord("CODEPNEU", "INTEGER", getMyModeles().getClass().getField("codepneu"), "STRING"));
	mappage.put("PUISSANCE", new BasicRecord("PUISSANCE", "INTEGER", getMyModeles().getClass().getField("puissance"), "STRING"));
	mappage.put("CODETE", new BasicRecord("CODETE", "INTEGER", getMyModeles().getClass().getField("codete"), "STRING"));
	mappage.put("VERSION", new BasicRecord("VERSION", "VARCHAR", getMyModeles().getClass().getField("version"), "STRING"));
	mappage.put("CODECARBURANT", new BasicRecord("CODECARBURANT", "INTEGER", getMyModeles().getClass().getField("codecarburant"), "STRING"));
	mappage.put("CODECOMPTEUR", new BasicRecord("CODECOMPTEUR", "INTEGER", getMyModeles().getClass().getField("codecompteur"), "STRING"));
	return mappage;
}

/**
 * Retourne un booléen.
 * Vérifie si le modèleinfos existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return Modeles
 * @throws Exception Exception
 */
public boolean existeModeleTEquip(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codete = "+param+"");
}
public boolean existeModeleMarques(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codemarque = "+param+"");
}
public boolean existeModeleCarbu(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codecarburant = "+param+"");
}
public boolean existeModeleCompteur(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codecompteur = "+param+"");
}
public boolean existeModelePneu(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codepneu = "+param+"");
}

}
