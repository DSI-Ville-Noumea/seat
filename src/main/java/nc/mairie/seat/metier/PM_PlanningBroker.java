package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
/**
 * Broker de l'Objet métier PM_Planning
 */
public class PM_PlanningBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur PM_PlanningBroker.
 */
public PM_PlanningBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_PlanningMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new PM_Planning() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_PlanningMetier
 */
protected PM_Planning getMyPM_Planning() {
	return (PM_Planning)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_PMPLANNING";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("PMINV", new BasicRecord("PMINV", "VARCHAR", getMyPM_Planning().getClass().getField("pminv"), "STRING"));
	mappage.put("PMSERIE", new BasicRecord("PMSERIE", "VARCHAR", getMyPM_Planning().getClass().getField("pmserie"), "STRING"));
	mappage.put("CODEPMPEP", new BasicRecord("CODEPMPEP", "INTEGER", getMyPM_Planning().getClass().getField("codepmpep"), "STRING"));
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyPM_Planning().getClass().getField("numfiche"), "STRING"));
	mappage.put("DPREV", new BasicRecord("DPREV", "DATE", getMyPM_Planning().getClass().getField("dprev"), "DATE"));
	mappage.put("DREAL", new BasicRecord("DREAL", "DATE", getMyPM_Planning().getClass().getField("dreal"), "DATE"));
	mappage.put("DUREE", new BasicRecord("DUREE", "VARCHAR", getMyPM_Planning().getClass().getField("duree"), "STRING"));
	mappage.put("SINISTRE", new BasicRecord("SINISTRE", "VARCHAR", getMyPM_Planning().getClass().getField("sinistre"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "VARCHAR", getMyPM_Planning().getClass().getField("commentaire"), "STRING"));
	mappage.put("CODEENTRETIEN", new BasicRecord("CODEENTRETIEN", "INTEGER", getMyPM_Planning().getClass().getField("codeentretien"), "STRING"));
	mappage.put("LIBELLEENTRETIEN", new BasicRecord("LIBELLEENTRETIEN", "VARCHAR", getMyPM_Planning().getClass().getField("libelleentretien"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : PM_Planning.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPM_Planning(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PM_Planning.
 * @return PM_Planning
 */
public PM_Planning chercherPM_Planning(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PM_Planning)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}

// lister tous les entretiens  d'un petit matériel
public java.util.ArrayList listerPM_EntretiensPm(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where pminv='"+inv+"' order by "+tri+"");
}

// lister les entretiens d'un petit matériel à faire
public java.util.ArrayList listerPM_EntretiensPmAFaire(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where dreal = '0001-01-01' and pminv='"+inv+"' order by "+tri+"");
}

public java.util.ArrayList listerPM_EntretiensPmFait(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where dreal <> '0001-01-01' and pminv='"+inv+"' order by "+tri+"");
}

// liste des encours
public java.util.ArrayList listerPlanningEnCours(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception {
	int codefiche = 0;
	if(numfiche.equals("")){
		codefiche = 0;
	}else{
		codefiche = Integer.parseInt(numfiche)-1;
	}
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where  dreal = '0001-01-01' and numfiche>"+codefiche+" and numfiche is not null order by pmserie");
}

//liste des encours avec PFM VALIDE à T
public java.util.ArrayList listerPlanningEnCoursAvecFPMValideDiffetentT(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception {
	int codefiche = 0;
	if(numfiche.equals("")){
		codefiche = 0;
	}else{
		codefiche = Integer.parseInt(numfiche)-1;
	}
	return executeSelectListe(aTransaction,"select distinct "+getTable()+".* from "+getTable()+" inner join SEAT.F_FPM on "+getTable()+".numfiche = SEAT.F_FPM.numfiche where  dreal = '0001-01-01' and "+getTable()+".numfiche>"+codefiche+" and "+getTable()+".numfiche is not null and valide <> 'T' order by pmserie");
}

// liste des afaire
public java.util.ArrayList listerPlanningAFaire(nc.mairie.technique.Transaction aTransaction,String dateFinPrev) throws Exception {
	if((dateFinPrev==null)||(dateFinPrev.equals("01/01/0001"))){
		dateFinPrev = Services.ajouteJours(Services.dateDuJour(),7);
	}
	dateFinPrev = Services.formateDateInternationale(dateFinPrev);
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where dreal = '0001-01-01' and dprev<='"+dateFinPrev+"' and dprev<>'0001-01-01' order by pmserie");
}

public java.util.ArrayList listerPlanningEnRetard(nc.mairie.technique.Transaction aTransaction,String date) throws Exception {
	if(Services.estUneDate(date)){
		date = Services.formateDateInternationale(date);
	}
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where  dreal = '0001-01-01' and dprev <> '0001-01-01' and dprev <'"+date+"'and numfiche is null order by pmserie");
}

public java.util.ArrayList chercherPM_Planning_FPM(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche = "+cle+"");
}

}
