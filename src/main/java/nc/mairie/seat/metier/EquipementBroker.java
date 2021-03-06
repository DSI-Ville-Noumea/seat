package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;

import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Equipement
 */
public class EquipementBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerEquipement(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierEquipement(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerEquipement(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Equipement.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Equipement> listerEquipement(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un Equipement.
 * @param aTransaction Transaction
 * @param cle cle
 * @return Equipement
 * @throws Exception Exception
 */
public Equipement chercherEquipement(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Equipement)executeSelect(aTransaction,"select * from "+getTable()+" where NUMEROINVENTAIRE = '"+cle+"' or numeroimmatriculation='"+cle+"'");
}

/**
 * Retourne un Equipement.
 * @param aTransaction Transaction
 * @param cle cle
 * @return Equipement
 * @throws Exception Exception
 */
public Equipement chercherEquipementInv(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Equipement)executeSelect(aTransaction,"select * from "+getTable()+" where NUMEROINVENTAIRE = '"+cle+"'");
}

/**
 * Retourne un Equipement.
 * @param aTransaction Transaction
 * @param cle cle
 * @param inv inv
 * @return Equipement
 * @throws Exception Exception
 */
public int chercherEquipementImmat(nc.mairie.technique.Transaction aTransaction, String cle,String inv) throws Exception {
	return executeCompter(aTransaction,"select count(numeroimmatriculation) from "+getTable()+" where numeroimmatriculation = '"+cle+"' and numeroinventaire<>'"+inv+"'");
}

/**
 * Retourne un ArrayList d'objet métier : Equipement.
 * @param aTransaction Transaction
 * @param tri tri
 * @return java.util.ArrayList
 * On fait un Tri selon le paramètre
 * @throws Exception Exception
 */
public ArrayList<Equipement> listerEquipementTri(nc.mairie.technique.Transaction aTransaction, String tri) throws Exception {
	//return executeSelectListe(aTransaction,"select * from "+getTable()+", F_MODELES, F_MARQUES, F_TYPEEQUIP where F_MARQUES.CODEMARQUE=F_MODELES.CODEMARQUE and F_MODELES.CODETE=F_TYPEEQUIP.CODETYPEEQUIP and F_MODELES.CODEMODELE=F_EQUIPEMENT.CODEMODELE order by "+tri+"");
	return executeSelectListe(aTransaction,"select * from "+getTable()+", F_MODELES, F_MARQUES, F_TYPEEQUIP where F_MARQUES.CODEMARQUE=F_MODELES.CODEMARQUE and F_MODELES.CODETE=F_TYPEEQUIP.CODETYPEEQUIP and F_MODELES.CODEMODELE=F_EQUIPEMENT.CODEMODELE order by "+tri+"");
}

/**
 * Retourne un ArrayList d'objet métier : Equipement.
 * @param aTransaction Transaction
 * @param param param
 * @param tri tri
 * @return java.util.ArrayList
 * On fait un Tri selon le paramètre
 * @throws Exception Exception
 */
public ArrayList<Equipement> listerEquipementParam(nc.mairie.technique.Transaction aTransaction, String param,String tri) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+", SEAT/f_modeles, SEAT/f_marques, SEAT/f_typeequip where f_marques.codemarque=f_modeles.codemarque and f_modeles.codete=f_typeequip.codetypeequip and f_modeles.codemodel=f_equipement.codemodele and f_equipement.datehorscircuit "+param+" '0001-01-01' order by "+tri+"");
}

/**
 * Retourne un ArrayList d'objet métier : Equipement.
 * @param aTransaction Transaction
 * @param mod mod
 * @return java.util.ArrayList
 * On cherche les équipements qui font parti d'un modèle
 * @throws Exception Exception
 */
public ArrayList<Equipement> listerEquipementModele(nc.mairie.technique.Transaction aTransaction, String mod) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codemodele="+mod);
}

/**
 * Constructeur EquipementBroker.
 * @param aMetier BasicMetier
 */
public EquipementBroker(Equipement aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.EquipementMetier
 */
@Override
protected Equipement definirMyMetier() {
	return new Equipement() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.EquipementMetier
 */
protected Equipement getMyEquipement() {
	return (Equipement)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_EQUIPEMENT";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyEquipement().getClass().getField("numeroinventaire"), "STRING"));
	mappage.put("NUMEROIMMATRICULATION", new BasicRecord("NUMEROIMMATRICULATION", "VARCHAR", getMyEquipement().getClass().getField("numeroimmatriculation"), "STRING"));
	mappage.put("DATEMISEENCIRCULATION", new BasicRecord("DATEMISEENCIRCULATION", "DATE", getMyEquipement().getClass().getField("datemiseencirculation"), "DATE"));
	mappage.put("DATEVENTEOUREFORME", new BasicRecord("DATEVENTEOUREFORME", "DATE", getMyEquipement().getClass().getField("dateventeoureforme"), "DATE"));
	mappage.put("DATEHORSCIRCUIT", new BasicRecord("DATEHORSCIRCUIT", "DATE", getMyEquipement().getClass().getField("datehorscircuit"), "DATE"));
	mappage.put("PRIXACHAT", new BasicRecord("PRIXACHAT", "INTEGER", getMyEquipement().getClass().getField("prixachat"), "STRING"));
	mappage.put("RESERVE", new BasicRecord("RESERVE", "CHAR", getMyEquipement().getClass().getField("reserve"), "STRING"));
	mappage.put("CODEMODELE", new BasicRecord("CODEMODELE", "INTEGER", getMyEquipement().getClass().getField("codemodele"), "STRING"));
	mappage.put("DUREEGARANTIE", new BasicRecord("DUREEGARANTIE", "INTEGER", getMyEquipement().getClass().getField("dureegarantie"), "STRING"));
	return mappage;
}

/**
 * Retourne un booléen.
 * Vérifie si l'équipement existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return BPC
 * @throws Exception Exception
 */
public boolean existeEquipement(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+param+"'");
}

/**
 * Retourne un booléen.
 * Vérifie si le modèleinfos existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return Equipement
 * @throws Exception Exception
 */
public boolean existeEquipementModele(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codemodele = "+param+"");
}

}
