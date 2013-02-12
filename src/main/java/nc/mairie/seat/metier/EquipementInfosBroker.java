package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
/**
 * Broker de l'Objet métier EquipementInfos
 */
public class EquipementInfosBroker extends nc.mairie.technique.BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : EquipementInfos.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerEquipementInfos(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception {
	if(tri.equals("")){
		tri="numeroinventaire";
	}
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by "+tri);
}

/**
 * Retourne un ArrayList d'objet métier : EquipementInfosActifs.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerEquipementInfosActifs(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception {
	if(tri.equals("")){
		tri="numeroinventaire";
	}
//	on cherche la date du jour
	String datedujour = Services.dateDuJour();
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where dateventeoureforme = '0001-01-01' or dateventeoureforme >'"+Services.formateDateInternationale(datedujour)+"' order by "+tri  );
}
/**
 * Retourne un ArrayList d'objet métier : EquipementInfos.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerEquipementInfosInactifs(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception {
	if(tri.equals("")){
		tri="numeroinventaire";
	}
//	on cherche la date du jour
	String datedujour = Services.dateDuJour();
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where dateventeoureforme <> '0001-01-01' and dateventeoureforme <='"+Services.formateDateInternationale(datedujour)+"' order by "+tri );
	//return executeSelectListe(aTransaction,"select * from SEAT.testluc where ladate <> '0001-01-01' and ladate <'"+datedujour+"'"  );
}
/**
 * Retourne un EquipementInfos.
 * @return EquipementInfos
 */
public java.util.ArrayList chercherListEquipementInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	//return executeSelectListe(aTransaction,"select * from "+getTable()+" where dateventeoureforme='0001-01-01' and char(NUMEROINVENTAIRE) like '"+cle+"%' OR char(NUMEROIMMATRICULATION)  like '"+cle+"%'");
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where char(numeroinventaire) like '"+cle+"%' OR char(numeroimmatriculation)  like '"+cle+"%' order by numeroimmatriculation");
}
/**
 * Retourne un EquipementInfos.
 * @return EquipementInfos
 */
public EquipementInfos chercherEquipementInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (EquipementInfos)executeSelect(aTransaction,"select * from "+getTable()+" where NUMEROINVENTAIRE = '"+cle+"'");
}
/**
 * Retourne un EquipementInfos.
 * @return EquipementInfos
 */
public java.util.ArrayList chercherListEquipementInfosTous(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where char(NUMEROINVENTAIRE) like '"+cle+"%' OR char(NUMEROIMMATRICULATION)  like '"+cle+"%'");
}

/**
 * Retourne un EquipementInfos.
 * @return EquipementInfos
 */
public EquipementInfos chercherEquipementInfosInvOuImmat(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (EquipementInfos)executeSelect(aTransaction,"select * from "+getTable()+" where char(NUMEROINVENTAIRE) = '"+cle+"' OR char(NUMEROIMMATRICULATION)  = '"+cle+"'");
}

/**
 * Retourne un booléen.
 * Vérifie si l'équipement existe déjà
 * @return BPC
 */
public boolean existeEquipementInfosEquip(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+param+"'");
}

/**
 * Constructeur EquipementInfosBroker.
 */
public EquipementInfosBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.EquipementInfosMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new EquipementInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.EquipementInfosMetier
 */
protected EquipementInfos getMyEquipementInfos() {
	return (EquipementInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_EQUIPEMENTINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyEquipementInfos().getClass().getField("numeroinventaire"), "STRING"));
	mappage.put("NUMEROIMMATRICULATION", new BasicRecord("NUMEROIMMATRICULATION", "VARCHAR", getMyEquipementInfos().getClass().getField("numeroimmatriculation"), "STRING"));
	mappage.put("DATEMISEENCIRCULATION", new BasicRecord("DATEMISEENCIRCULATION", "DATE", getMyEquipementInfos().getClass().getField("datemiseencirculation"), "DATE"));
	mappage.put("DATEHORSCIRCUIT", new BasicRecord("DATEHORSCIRCUIT", "DATE", getMyEquipementInfos().getClass().getField("datehorscircuit"), "DATE"));
	mappage.put("DESIGNATIONMODELE", new BasicRecord("DESIGNATIONMODELE", "VARCHAR", getMyEquipementInfos().getClass().getField("designationmodele"), "STRING"));
	mappage.put("DESIGNATIONMARQUE", new BasicRecord("DESIGNATIONMARQUE", "VARCHAR", getMyEquipementInfos().getClass().getField("designationmarque"), "STRING"));
	mappage.put("DESIGNATIONTYPEEQUIP", new BasicRecord("DESIGNATIONTYPEEQUIP", "VARCHAR", getMyEquipementInfos().getClass().getField("designationtypeequip"), "STRING"));
	mappage.put("VERSION", new BasicRecord("VERSION", "VARCHAR", getMyEquipementInfos().getClass().getField("version"), "STRING"));
	mappage.put("DESIGNATIONCARBU", new BasicRecord("DESIGNATIONCARBU", "VARCHAR", getMyEquipementInfos().getClass().getField("designationcarbu"), "STRING"));
	mappage.put("DESIGNATIONCOMPTEUR", new BasicRecord("DESIGNATIONCOMPTEUR", "VARCHAR", getMyEquipementInfos().getClass().getField("designationcompteur"), "STRING"));
	mappage.put("DIMENSION", new BasicRecord("DIMENSION", "VARCHAR", getMyEquipementInfos().getClass().getField("dimension"), "STRING"));
	mappage.put("DATEVENTEOUREFORME", new BasicRecord("DATEVENTEOUREFORME", "DATE", getMyEquipementInfos().getClass().getField("dateventeoureforme"), "DATE"));
	mappage.put("TYPETE", new BasicRecord("TYPETE", "VARCHAR", getMyEquipementInfos().getClass().getField("typete"), "STRING"));
	return mappage;
}
}
