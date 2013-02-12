package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Equipement
 */
public class EquipementBroker extends nc.mairie.technique.BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerEquipement(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierEquipement(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerEquipement(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Equipement.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerEquipement(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un Equipement.
 * @return Equipement
 */
public Equipement chercherEquipement(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Equipement)executeSelect(aTransaction,"select * from "+getTable()+" where NUMEROINVENTAIRE = '"+cle+"' or numeroimmatriculation='"+cle+"'");
}

/**
 * Retourne un Equipement.
 * @return Equipement
 */
public Equipement chercherEquipementInv(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Equipement)executeSelect(aTransaction,"select * from "+getTable()+" where NUMEROINVENTAIRE = '"+cle+"'");
}

/**
 * Retourne un Equipement.
 * @return Equipement
 */
public int chercherEquipementImmat(nc.mairie.technique.Transaction aTransaction, String cle,String inv) throws Exception {
	return executeCompter(aTransaction,"select count(numeroimmatriculation) from "+getTable()+" where numeroimmatriculation = '"+cle+"' and numeroinventaire<>'"+inv+"'");
}

/**
 * Retourne un ArrayList d'objet métier : Equipement.
 * @return java.util.ArrayList
 * On fait un Tri selon le paramètre
 */
public java.util.ArrayList listerEquipementTri(nc.mairie.technique.Transaction aTransaction, String tri) throws Exception {
	//return executeSelectListe(aTransaction,"select * from "+getTable()+", SEAT.F_MODELES, SEAT.F_MARQUES, SEAT.F_TYPEEQUIP where F_MARQUES.CODEMARQUE=F_MODELES.CODEMARQUE and F_MODELES.CODETE=F_TYPEEQUIP.CODETYPEEQUIP and F_MODELES.CODEMODELE=F_EQUIPEMENT.CODEMODELE order by "+tri+"");
	return executeSelectListe(aTransaction,"select * from "+getTable()+", SEAT.F_MODELES, SEAT.F_MARQUES, SEAT.F_TYPEEQUIP where SEAT.F_MARQUES.CODEMARQUE=SEAT.F_MODELES.CODEMARQUE and SEAT.F_MODELES.CODETE=SEAT.F_TYPEEQUIP.CODETYPEEQUIP and SEAT.F_MODELES.CODEMODELE=SEAT.F_EQUIPEMENT.CODEMODELE order by "+tri+"");
}

/**
 * Retourne un ArrayList d'objet métier : Equipement.
 * @return java.util.ArrayList
 * On fait un Tri selon le paramètre
 */
public java.util.ArrayList listerEquipementParam(nc.mairie.technique.Transaction aTransaction, String param,String tri) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+", SEAT/f_modeles, SEAT/f_marques, SEAT/f_typeequip where f_marques.codemarque=f_modeles.codemarque and f_modeles.codete=f_typeequip.codetypeequip and f_modeles.codemodel=f_equipement.codemodele and f_equipement.datehorscircuit "+param+" '0001-01-01' order by "+tri+"");
}

/**
 * Retourne un ArrayList d'objet métier : Equipement.
 * @return java.util.ArrayList
 * On cherche les équipements qui font parti d'un modèle
 */
public java.util.ArrayList listerEquipementModele(nc.mairie.technique.Transaction aTransaction, String mod) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codemodele="+mod);
}

/**
 * Constructeur EquipementBroker.
 */
public EquipementBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.EquipementMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
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
	return "SEAT.F_EQUIPEMENT";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
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
 * @return BPC
 */
public boolean existeEquipement(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+param+"'");
}

/**
 * Retourne un booléen.
 * Vérifie si le modèleinfos existe déjà
 * @return Equipement
 */
public boolean existeEquipementModele(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codemodele = "+param+"");
}

}
