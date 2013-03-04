package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier PePerso
 */
public class PePersoBroker extends nc.mairie.technique.BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPePerso(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPePerso(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPePerso(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPePerso(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}

public java.util.ArrayList listerPePersoEquipMoinsUnAn(nc.mairie.technique.Transaction aTransaction,String pdate) throws Exception {
	return executeSelectListe(aTransaction,"select distinct(codeequip) from "+getTable()+" where datereal<'"+pdate+"'");
}

/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPePersoDecl(nc.mairie.technique.Transaction aTransaction,String decl) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codedec="+decl);
}

/*renvoi un arrayList 
 * on liste les peperso qui ont un intervalle en km
 * autheur : OF 09-04-15
 */
public java.util.ArrayList listerPePersoRecond(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeequip = '"+inv+"' and datereal='0001-01-01' and codetypeent<>2 order by INTERVALLE DESC");
}


/*renvoi un arrayList 
 * on liste les peperso qui ont un intervalle en km
 * autheur : CN 05-07-27
 */
public java.util.ArrayList listerPePersoKm(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeequip = '"+inv+"' and datereal='0001-01-01' and codeti=1 and codetypeent<>2 order by INTERVALLE DESC");
}

/*renvoi un arrayList 
 * on liste les peperso qui ont un intervalle en km
 * autheur : CN 05-07-27
 */
//UNUSED: comment by OFONTENEAU 20090313
//public java.util.ArrayList listerPePersoAFaireKm(nc.mairie.technique.Transaction aTransaction,String mod,String inv) throws Exception {
//	return executeSelectListe(aTransaction,"select * from SEAT.F_PE_BASE where codemodele = '"+mod+"' and codeti=1 and codeentretien<>2 and codemodele not in(select codeentretien from  "+getTable()+" where codeequip='"+inv+"')");
//}

/*renvoi un arrayList 
 * on liste les peperso qui ont un intervalle en km
 * autheur : CN 05-07-27
 */
public java.util.ArrayList listerPePersoKmAFaire(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select distinct(codeentretien) from "+getTable()+" where codeequip = '"+inv+"' and codeti=1 and codetypeent<>2");
}
/*renvoi un arrayList 
 * on liste les peperso qui ont un intervalle horaire
 * autheur : CN 05-07-27
 */
public java.util.ArrayList listerPePersoHr(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeequip = '"+inv+"' and datereal='0001-01-01' and codeti=5 and codetypeent<>2 order by intervalle desc");
}

/**
 * Retourne un PePerso.
 * @return PePerso
 */
public PePerso chercherPePerso(nc.mairie.technique.Transaction aTransaction, String code) throws Exception {
	return (PePerso)executeSelect(aTransaction,"select * from "+getTable()+" where codepep = "+code+"");
}


/**
 * Retourne un PePerso.
 * @return PePerso
 */
public java.util.ArrayList chercherPePersoEquipEnt(nc.mairie.technique.Transaction aTransaction, String inv,String ent) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeequip = '"+inv+"' and codeentretien="+ent+" order by codepep");
}

/**
 * Retourne un PePerso.
 * @return PePerso
 */
public java.util.ArrayList listerPePersoEquip(nc.mairie.technique.Transaction aTransaction, String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeequip = '"+inv+"' order by codepep");
}

/**
 * Retourne un PePerso.
 * @return PePerso
 */
public java.util.ArrayList chercherPePersoOT(nc.mairie.technique.Transaction aTransaction, String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeot = "+numot);
}

/**
 * Retourne un PePerso.
 * @return PePerso
 */
public java.util.ArrayList chercherPePersoPasFaitOT(nc.mairie.technique.Transaction aTransaction, String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeot = "+numot+" and datereal='0001-01-01'");
}

/**
 * Retourne un PePerso.
 * @return PePerso
 */
public PePerso chercherPePersoEquipEntRealise(nc.mairie.technique.Transaction aTransaction, String inv,String ent) throws Exception {
	return (PePerso)executeSelect(aTransaction,"select * from "+getTable()+" where  codepep in (select max(codepep) from "+getTable()+" where codeequip = '"+inv+"' and codeentretien="+ent+" and datereal<>'0001-01-01')");
}

/**
 * Retourne un PePerso.
 * @return PePerso
 */
public PePerso chercherPePersoEquipEntPrevu(nc.mairie.technique.Transaction aTransaction, String inv,String ent) throws Exception {
	return (PePerso)executeSelect(aTransaction,"select * from "+getTable()+" where  codepep in (select max(codepep) from "+getTable()+" where codeequip = '"+inv+"' and codeentretien="+ent+" and datereal='0001-01-01')");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existePePerso(nc.mairie.technique.Transaction aTransaction, String inv,String ent,String codete,String date) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codeequip = "+inv+" and codeentretien = "+ent+" and codetypeent="+codete+" and dateprev='"+date+"'");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
* 
 */
public int nouvPePerso(nc.mairie.technique.Transaction aTransaction) throws Exception{
//	recherche du dernier code
	return executeCompter(aTransaction, "select max(codepep) from "+ getTable());
	
}

/* On recherche le dernier peperso pour un équipement et un entretien
* 
 */
public int maxPePersoEquipEnt(nc.mairie.technique.Transaction aTransaction,String inv,String ent) throws Exception{
//	recherche du dernier 
	return executeCompter(aTransaction, "select max(codepep) from "+ getTable()+" where codeequip='"+inv+"' and codeentretien="+ent);
	
}


/**
 * Retourne un booléen.
 * Vérifie si le modèleinfos existe déjà
 * @return Modeles
 */
public boolean existePePersoTEnt(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codetypeent = "+param+"");
}

/**
 * Constructeur PePersoBroker.
 */
public PePersoBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PePersoMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new PePerso() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PePersoMetier
 */
protected PePerso getMyPePerso() {
	return (PePerso)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "SEAT.F_PE_PERSO";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("CODEEQUIP", new BasicRecord("CODEEQUIP", "VARCHAR", getMyPePerso().getClass().getField("codeequip"), "STRING"));
	mappage.put("CODEENTRETIEN", new BasicRecord("CODEENTRETIEN", "INTEGER", getMyPePerso().getClass().getField("codeentretien"), "STRING"));
	mappage.put("SINISTRE", new BasicRecord("SINISTRE", "VARCHAR", getMyPePerso().getClass().getField("sinistre"), "STRING"));
	mappage.put("DUREE", new BasicRecord("DUREE", "NUMERIC", getMyPePerso().getClass().getField("duree"), "STRING"));
	mappage.put("CODEOT", new BasicRecord("CODEOT", "NUMERIC", getMyPePerso().getClass().getField("codeot"), "STRING"));
	mappage.put("CODETYPEENT", new BasicRecord("CODETYPEENT", "INTEGER", getMyPePerso().getClass().getField("codetypeent"), "STRING"));
	mappage.put("COMMENTAIRETE", new BasicRecord("COMMENTAIRETE", "VARCHAR", getMyPePerso().getClass().getField("commentairete"), "STRING"));
	mappage.put("CODEPEP", new BasicRecord("CODEPEP", "INTEGER", getMyPePerso().getClass().getField("codepep"), "STRING"));
	mappage.put("CODETI", new BasicRecord("CODETI", "INTEGER", getMyPePerso().getClass().getField("codeti"), "STRING"));
	mappage.put("INTERVALLE", new BasicRecord("INTERVALLE", "INTEGER", getMyPePerso().getClass().getField("intervallepep"), "STRING"));
	mappage.put("DATEREAL", new BasicRecord("DATEREAL", "DATE", getMyPePerso().getClass().getField("datereal"), "DATE"));
	mappage.put("DATEPREV", new BasicRecord("DATEPREV", "DATE", getMyPePerso().getClass().getField("dateprev"), "DATE"));
	return mappage;
}
}
