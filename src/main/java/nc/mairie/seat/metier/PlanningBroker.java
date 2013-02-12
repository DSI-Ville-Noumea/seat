package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
/**
 * Broker de l'Objet métier Planning
 */
public class PlanningBroker extends nc.mairie.technique.BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPlanning(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}

/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @return java.util.ArrayList
 *//*
public java.util.ArrayList listerPlanningProp(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeot like 'T%'");
}
/
/**
 * Retourne un Planning.
 * @return Planning
 */
public Planning chercherPlanning(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Planning)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}

/**
 * Retourne un Planning.
 * @return Planning
 */
public java.util.ArrayList chercherPlanningAFaire(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select distinct(numeroinventaire) from "+getTable()+" where codeot=0");
}

/**
 * Retourne un Planning.
 * @return Planning
 */
public java.util.ArrayList chercherPlanningOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeot="+numot);
}

/**
 * Retourne un Planning.
 * @return Planning
 */
public java.util.ArrayList chercherPlanningEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select distinct(codeot) from "+getTable()+" where numeroinventaire='"+inv+"'");
}

/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPlanningAFaire(nc.mairie.technique.Transaction aTransaction,String dateFinPrev) throws Exception {
	if((dateFinPrev==null)||(dateFinPrev.equals("01/01/0001"))){
		dateFinPrev = Services.ajouteJours(Services.dateDuJour(),7);
	}
	dateFinPrev = Services.formateDateInternationale(dateFinPrev);
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where datereal = '0001-01-01' and dateprev<='"+dateFinPrev+"' and dateprev<>'0001-01-01' order by numeroimmatriculation");
}

/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPlanningEnCours(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	int codeot = 0;
	if(numot.equals("")){
		codeot = 0;
	}else{
		codeot = Integer.parseInt(numot)-1;
	}
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where  datereal = '0001-01-01' and codeot>"+codeot+" and codeot is not null order by numeroimmatriculation");
}

/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPlanningEnCoursAvecOTValideDifferentT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	int codeot = 0;
	if(numot.equals("")){
		codeot = 0;
	}else{
		codeot = Integer.parseInt(numot)-1;
	}
	return executeSelectListe(aTransaction,"select "+getTable()+".* from "+getTable()+" inner join seat.F_OT on seat.F_OT.numeroot = codeot  where  datereal = '0001-01-01' and codeot>"+codeot+" and codeot is not null  and valide <> 'T' order by numeroimmatriculation");
}

/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPlanningEnRetard(nc.mairie.technique.Transaction aTransaction,String date) throws Exception {
	if(Services.estUneDate(date)){
		date = Services.formateDateInternationale(date);
	}
	//return executeSelectListe(aTransaction,"select * from "+getTable()+" where  datereal = '0001-01-01' and dateprev <'"+date+"'and codeot is null order by numeroimmatriculation");
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where  datereal = '0001-01-01' and dateprev <'"+date+"' and dateprev <> '0001-01-01' and codeot is null order by numeroimmatriculation");
}

/**
 * Constructeur PlanningBroker.
 */
public PlanningBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PlanningMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new Planning() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PlanningMetier
 */
protected Planning getMyPlanning() {
	return (Planning)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_PLANNING";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyPlanning().getClass().getField("numeroinventaire"), "STRING"));
	mappage.put("NUMEROIMMATRICULATION", new BasicRecord("NUMEROIMMATRICULATION", "VARCHAR", getMyPlanning().getClass().getField("numeroimmatriculation"), "STRING"));
	mappage.put("CODETYPEENT", new BasicRecord("CODETYPEENT", "INTEGER", getMyPlanning().getClass().getField("codetypeent"), "STRING"));
	mappage.put("DESIGNATIONTYPEENT", new BasicRecord("DESIGNATIONTYPEENT", "VARCHAR", getMyPlanning().getClass().getField("designationtypeent"), "STRING"));
	mappage.put("LIBELLEENTRETIEN", new BasicRecord("LIBELLEENTRETIEN", "VARCHAR", getMyPlanning().getClass().getField("libelleentretien"), "STRING"));
	mappage.put("CODEENTRETIEN", new BasicRecord("CODEENTRETIEN", "INTEGER", getMyPlanning().getClass().getField("codeentretien"), "STRING"));
	mappage.put("SINISTRE", new BasicRecord("SINISTRE", "VARCHAR", getMyPlanning().getClass().getField("sinistre"), "STRING"));
	mappage.put("DUREE", new BasicRecord("DUREE", "NUMERIC", getMyPlanning().getClass().getField("duree"), "STRING"));
	mappage.put("CODEOT", new BasicRecord("CODEOT", "NUMERIC", getMyPlanning().getClass().getField("codeot"), "STRING"));
	mappage.put("CODEPEP", new BasicRecord("CODEPEP", "INTEGER", getMyPlanning().getClass().getField("codepep"), "STRING"));
	mappage.put("DATEREAL", new BasicRecord("DATEREAL", "DATE", getMyPlanning().getClass().getField("datereal"), "DATE"));
	mappage.put("DATEPREV", new BasicRecord("DATEPREV", "DATE", getMyPlanning().getClass().getField("dateprev"), "DATE"));
	return mappage;
}
}
