<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V3.5.3 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE>Menu Personnel</TITLE>
<!-- SCRIPT language="javascript" src="js/GestionBoutonDroit.js"></SCRIPT> -->

<SCRIPT type="text/javascript" src="js/GestionBoutonDroit.js"></SCRIPT>
<%
nc.mairie.technique.UserAppli aUser= (nc.mairie.technique.UserAppli)nc.mairie.technique.VariableGlobale.recuperer(request,nc.mairie.technique.VariableGlobale.GLOBAL_USER_APPLI);
//java.util.ArrayList droits = aUser.getListeDroits();
// on déclare tous les liens
java.util.ArrayList droits = new java.util.ArrayList();
droits.add("GestionMarques");
droits.add("GestionModePrise");
droits.add("GestionCarburant");
droits.add("GestionCompteur");
droits.add("GestionPneu");
droits.add("GestionTypeEquip");
droits.add("GestionTypeInt");
droits.add("GestionModeles");
droits.add("VisualisationModele");
droits.add("GestionTypeEntretien");
droits.add("GestionEntretien");
droits.add("GestionEquipement");
droits.add("VisualisationEquipement");
droits.add("GestionBPC");
droits.add("SaisieBPC");
droits.add("GestionPeBase");
droits.add("GestionPePerso");
droits.add("VisualisationBPC");
droits.add("AffectationService");
droits.add("AffectationAgent");
droits.add("AffectationServiceEquipement");
droits.add("AffectationAgentEquipements");
droits.add("AffectationServicePm");
droits.add("AffectationAgentPm");
droits.add("VisuAffScePm");
droits.add("VisuAffAgentPm");
droits.add("Module_Planning");
droits.add("VisualisationPlanning");
droits.add("GestionOT");
droits.add("VisualisationOT");
droits.add("ImprimerOT");
droits.add("ValiderOT");
droits.add("GestionPieces");
droits.add("GestionSpecialite");
droits.add("GestionATM");
droits.add("GestionExterieurs");
droits.add("VisualisationBPCs");
droits.add("TableauDeBord");
droits.add("GestionDeclaration");
droits.add("VisualisationDeclarationEquip");
droits.add("VisualisationBPCComplete");
droits.add("VisualisationPlanning");
droits.add("VisualisationTableauDeBord");
droits.add("VisuPmTDB");
droits.add("GestionPompes");
droits.add("GestionFrePM");
droits.add("GestionPMateriel");
droits.add("VisualisationPMateriel");
droits.add("PMPePerso");
droits.add("VisuPlanningPm");
droits.add("GestionFPM");
droits.add("VisualisationFPM");
droits.add("ValiderFPM");
droits.add("ImprimerFPM");


String res = 	"<script language=\"javascript\">\n"+
		"var listeDroits = new Array(\n";
		res+="   \"construction\",\n";

for (int i = 0; i < droits.size(); i++){
	res+= "   \""+(String)droits.get(i)+"\"";
	if (i+1 < droits.size())
		res+=",\n";
	else	res+="\n";
}

res+=")</script>";
%>
<%=res%>

<script language="javascript" src="js/GestionMenu.js"> </script>
<LINK rel="stylesheet" href="theme/menu.css" type="text/css">

</HEAD>
<BODY bgcolor="#ffffff" background="images/fond_menu.jpg" text="#000000" onload="preload();" style="cursor : auto;"><BASEFONT FACE="Arial" SIZE=2>
<nobr>
<FORM name="leForm" method="POST" target="Main" action="ServletSeat"><INPUT type="hidden" name="ACTIVITE" value="">


<script>
<!-- 
var menu = new Menu();

//***************************************************************
//*               Le module paramètres
//***************************************************************
var Module_parametres = new Dossier("Module_parametres", "Paramètres");

	//***************************************************************
	//*               La gestion des marques
	//***************************************************************
	//var Gestion_marques = new Dossier("Gestion_marques", "Marques");
		//Gestion_marques.ajouterFils(new Lien("GestionMarques", "Gestion", "Gestion d'une marque", true));
	Module_parametres.ajouterFils(new Lien("GestionMarques", "Marques", "Gestion des marques", true));
	//***************************************************************
	//*               La gestion des type d'équipements
	//***************************************************************
	Module_parametres.ajouterFils(new Lien("GestionTypeEquip", "Types d'équipement", "Gestion des types d'équipement", true));
	//***************************************************************
	//*               La gestion des pompes
	//***************************************************************
	Module_parametres.ajouterFils(new Lien("GestionPompes", "Pompes", "Gestion des pompes", true));
	//***************************************************************
	//*               La gestion des carburants
	//***************************************************************
	Module_parametres.ajouterFils(new Lien("GestionCarburant", "Carburant", "Gestion des carburants", true));
	//***************************************************************
	//*               La gestion des modes de prise de carburant
	//***************************************************************
	//Module_parametres.ajouterFils(new Lien("GestionModePrise", "Mode de prise", "Gestion des modes de prise de carburant", true));
	
	//***************************************************************
	//*               La gestion des compteurs
	//***************************************************************
	Module_parametres.ajouterFils(new Lien("GestionCompteur", "Compteur", "Gestion des compteurs", true));
	//***************************************************************
	//*               La gestion des pneus
	//***************************************************************
	Module_parametres.ajouterFils(new Lien("GestionPneu", "Pneu", "Gestion des pneus", true));
	//***************************************************************
	//*               La gestion des types d'intervalle
	//***************************************************************
	Module_parametres.ajouterFils(new Lien("GestionTypeInt", "Types d'intervalle", "Gestion des types d'intervalle", true));
	//***************************************************************
	//*               La gestion des types d'entretiens
	//***************************************************************
	Module_parametres.ajouterFils(new Lien("GestionTypeEntretien", "Types d'entretien", "Gestion des types d'entretiens", true));
	//***************************************************************
	//*               La gestion des entretiens
	//***************************************************************
	Module_parametres.ajouterFils(new Lien("GestionEntretien", "Entretiens", "Gestion des entretiens", true));
	//***************************************************************
	//*               La gestion des spécialités
	//***************************************************************
	Module_parametres.ajouterFils(new Lien("GestionSpecialite", "Spécialité", "Gestion des spécialités", true));
	//***************************************************************
	//*               La gestion des pièces
	//***************************************************************
	Module_parametres.ajouterFils(new Lien("GestionPieces", "Pièces", "Gestion des pièces", true));
	//***************************************************************
	//*               La gestion des fournisseurs de petit matériel
	//***************************************************************
	Module_parametres.ajouterFils(new Lien("GestionFrePM", "Fournisseurs PM", "Gestion des fournisseurs de petit matériel", true));

	//***************************************************************
	//*               La gestion des modèles
	//***************************************************************
	var Module_modeles = new Dossier("Module_modeles", "Modèles");
		Module_modeles.ajouterFils(new Lien("GestionModeles", "Saisie", "Gestion des modèles", true));
		Module_modeles.ajouterFils(new Lien("VisualisationModele", "Visualisation", "Visualisation des modèles", true));
		//Module_modeles.ajouterFils(new Lien("GestionPeBase", "Plan d'entretien de base", "Gestion des plans d'entretiens de bases", true));
	Module_parametres.ajouterFils(Module_modeles);
	//Module_parametres.ajouterFils(new Lien("GestionModeles", "Modèles", "Gestion des modèles", true));
//***************************************************************
//*               Le module Intervenant
//***************************************************************
	var Module_Intervenant = new Dossier("Module_Intervenant", "Intervenants");
		Module_Intervenant.ajouterFils(new Lien("GestionATM", "ATM", "Gestion des intervenants de l'ATM", true));
		Module_Intervenant.ajouterFils(new Lien("GestionExterieur", "Extérieurs", "Gestion des intervenants extérieurs", true));

	Module_parametres.ajouterFils(Module_Intervenant);
//***************************************************************
//*               Le module Equipement
//***************************************************************
var Module_Equipement = new Dossier("Module_Equipement", "Equipement");
		Module_Equipement.ajouterFils(new Lien("GestionEquipement", "Saisie", "Gestion des équipements", true));
		Module_Equipement.ajouterFils(new Lien("VisualisationEquipement", "Visualisation", "Visualisation des équipements", true));
		//Module_Equipement.ajouterFils(new Lien("GestionPePerso", "Plan d'entretien personnalisé", "Gestion des plans d'entretiens personnalisés", true));
	//***************************************************************
	//*               La gestion des affectations
	//***************************************************************
	var Module_Affectation = new Dossier("Module_affectation", "Affectations");
		Module_Affectation.ajouterFils(new Lien("AffectationService", "Un équipement à un service", "Gestion des affectations d'un équipement à un service", true));
		Module_Affectation.ajouterFils(new Lien("AffectationAgent", "Un équipement à un agent", "Les affectations ponctuelles d'un équipement à un agent", true));
		Module_Equipement.ajouterFils(Module_Affectation);
	//***************************************************************
	//*               La visualisation des affectations
	//***************************************************************
	var Module_VisuAffectation = new Dossier("Module_VisuAffectation", "Visu des affectations");
		Module_VisuAffectation.ajouterFils(new Lien("AffectationServiceEquipement", "Equipements d'un service", "Visualisation des équipements d'un service", true));
		Module_VisuAffectation.ajouterFils(new Lien("AffectationAgentEquipements", "Equipements d'un agent", "Visualisation des équipements d'un agent", true));
		Module_Equipement.ajouterFils(Module_VisuAffectation);
	//Module_BPC.ajouterFils(new Lien("RechercheEquipement", "Recherche", "Recherche d'un équipement", true));
	//Module_Equipement.ajouterFils(GestionEquipement);
//***************************************************************
//*               Le module Petit Matériel
//***************************************************************
var Module_PMateriel = new Dossier("Module_PMateriel", "Petit matériel");
		Module_PMateriel.ajouterFils(new Lien("GestionPMateriel", "Saisie", "Gestion des petits matériels", true));
		Module_PMateriel.ajouterFils(new Lien("VisualisationPMateriel", "Visualisation", "Visualisation des petits matériels", true));
		Module_PMateriel.ajouterFils(new Lien("PMPePerso", "Plan d'entretien perso", "Gestion des plans d'entretiens personnalisés", true));
	//***************************************************************
	//*               La gestion des affectations
	//***************************************************************
	var Module_AffectationPm = new Dossier("Module_affectationPm", "Affectations");
		Module_AffectationPm.ajouterFils(new Lien("AffectationServicePm", "Un petit matériel à un service", "Gestion des affectations d'un petit matériel à un service", true));
		Module_AffectationPm.ajouterFils(new Lien("AffectationAgentPm", "Un petit matériel à un agent", "Les affectations ponctuelles d'un petit matériel à un agent", true));
		Module_PMateriel.ajouterFils(Module_AffectationPm);
	//***************************************************************
	//*               La visualisation des affectations
	//***************************************************************
	var Module_VisuAffectationPm = new Dossier("Module_VisuAffectationPm", "Visu des affectations");
		Module_VisuAffectationPm.ajouterFils(new Lien("VisuAffScePm", "Petit matériel d'un service", "Visualisation des petits matériels d'un service", true));
		Module_VisuAffectationPm.ajouterFils(new Lien("VisuAffAgentPm", "Petit matériel d'un agent", "Visualisation des petits matériels d'un agent", true));
		Module_PMateriel.ajouterFils(Module_VisuAffectationPm);		
//***************************************************************
//*               Le module BPC
//***************************************************************
//var Module_BPC = new Dossier(new Lien("Gestion_BPC", "Création", "Création d'un BPC", true));
//***************************************************************
//*               Le module BPC
//***************************************************************
var Module_BPC = new Dossier("Module_BPC", "BPC");
		Module_BPC.ajouterFils(new Lien("SaisieBPC", "Saisie", "Saisie des BPC", true));
		Module_BPC.ajouterFils(new Lien("GestionBPC", "Gestion", "Gestion des BPC", true));
		Module_BPC.ajouterFils(new Lien("VisualisationBPC", "Visualisation", "Visualisation des BPC", true));
		Module_BPC.ajouterFils(new Lien("VisualisationBPCs", "Visualisation complète", "Visualisation des BPC d'un équipement", true));
		Module_BPC.ajouterFils(new Lien("VisualisationBPCComplete", "Visualisation paramétrée", "Visualisation des BPC selon des critères", true));
//***************************************************************
//*               Le module Plan d'entretien
//***************************************************************
var Module_Pe = new Dossier("Module_Pe", "Plan d'entretien");
		Module_Pe.ajouterFils(new Lien("GestionPeBase", "original", "Gestion des plans d'entretiens originaux", true));
		Module_Pe.ajouterFils(new Lien("GestionPePerso", "personnalisé", "Gestion des plans d'entretiens personnalisés", true));
//***************************************************************
//*               Le module Déclaration
//***************************************************************
var Module_Declaration = new Dossier("Module_Declaration", "Déclarations");
		Module_Declaration.ajouterFils(new Lien("GestionDeclaration", "Saisie", "Gestion des déclarations", true));
		Module_Declaration.ajouterFils(new Lien("VisualisationDeclarationEquip", "Visualisation par équipement", "Visualisation des déclarations par équipement", true));
//***************************************************************
//*               Le module Planning
//***************************************************************
//var Module_Planning = new Dossier(new Lien("Module_Planning", "Visualisation", "Visualisation du planning", true));
//***************************************************************
//*               Le module OT
//***************************************************************
var Module_OT = new Dossier("Module_OT", "OT");
		Module_OT.ajouterFils(new Lien("GestionOT", "Saisie", "Gestion des OT", true));
		Module_OT.ajouterFils(new Lien("ImprimerOT", "Impression", "Impression d'un OT à réaliser", true));
		Module_OT.ajouterFils(new Lien("ValiderOT", "Validation", "Validation des OT", true));
		Module_OT.ajouterFils(new Lien("VisualisationOT", "Visualisation", "Visualisation des OT", true));
//***************************************************************
//*               Le module FPM
//***************************************************************
var Module_FPM = new Dossier("Module_FPM", "Fiche d'entretien du PM");
		Module_FPM.ajouterFils(new Lien("GestionFPM", "Saisie", "Gestion des fiches d'entretiens", true));
		Module_FPM.ajouterFils(new Lien("ImprimerFPM", "Impression", "Impression d'une fiche d'entretiens", true));
		Module_FPM.ajouterFils(new Lien("ValiderFPM", "Validation", "Validation des fiches d'entretiens", true));
		Module_FPM.ajouterFils(new Lien("VisualisationFPM", "Visualisation", "Visualisation d'une fiche d'entretien", true));

//***************************************************************
//*               Le module Tableau de bord
//***************************************************************
//*var Module_TableauDeBord = new Dossier("TableauDeBord", "Tableau de bord");*/
//***************************************************************
//*               Le module Planning
//***************************************************************
var Module_Planning = new Dossier("Module_Planning", "Planning");
		Module_Planning.ajouterFils(new Lien("VisualisationPlanning", "Equipement", "Planning des équipements", true));
		Module_Planning.ajouterFils(new Lien("VisuPlanningPm", "Petit matériel", "Planning du petit matériel", true));
//***************************************************************
//*               Le module Planning
//***************************************************************
var Module_TableauDeBord = new Dossier("Module_TableauDeBord", "Tableau de bord");
		Module_TableauDeBord.ajouterFils(new Lien("VisualisationTableauDeBord", "Equipement", "Tableau de bord d'un équipement", true));
		Module_TableauDeBord.ajouterFils(new Lien("VisuPmTDB", "Petit matériel", "Tableau de bord du petit matériel", true));


var menu = new Menu();
menu.ajouterFils(Module_parametres);
menu.ajouterFils(Module_Equipement);
menu.ajouterFils(Module_PMateriel);
menu.ajouterFils(Module_Pe);
menu.ajouterFils(Module_Declaration);
menu.ajouterFils(Module_OT);
menu.ajouterFils(Module_FPM);
menu.ajouterFils(Module_Planning);
menu.ajouterFils(Module_BPC);
menu.ajouterFils(Module_TableauDeBord);



//	Menu pour les services
var menuService = new Menu();

var Module_SEquipement = new Dossier("Module_SEquipement", "Equipement");

	Module_SEquipement.ajouterFils(new Lien("AffectationServiceEquipement", "Les équipements", "Visualisation des équipements", true));
	Module_SEquipement.ajouterFils(new Lien("AffectationAgent", "Affectation à un agent", "Gestion des affectations des équipements à un agent", true));
	Module_SEquipement.ajouterFils(new Lien("AffectationAgentEquipements", "Equipements d'un agent", "Les équipements d'un agent responsable", true));

var Module_SPMateriel = new Dossier("Module_SPMateriel", "Petit matériel");

	Module_SPMateriel.ajouterFils(new Lien("VisuAffScePm", "Les petits matériels", "Visualisation des petits matériels", true));
	Module_SPMateriel.ajouterFils(new Lien("AffectationAgentPm", "Affectation à un agent", "Gestion des affectations des petits matériels à un agent", true));
	Module_SPMateriel.ajouterFils(new Lien("VisuAffAgentPm", "Petit matériel d'un agent", "Les petits matériels d'un agent responsable", true));

var menuService = new Menu();
menuService.ajouterFils(Module_SEquipement);
menuService.ajouterFils(Module_SPMateriel);

//menu.ajouterFils(Module_Intervenant);
//menu.ajouterFils(Module_TableauDeBord);
//menu.ajouterFils(Module_Planning);
//menu.ajouterFils(new Lien("GestionMarques", "Marques", "Gestion d'une marque", true));
//menu.ajouterFils(new Lien("GestionBPC", "BPC", "Gestion des BPC", true));
<%
	java.util.Hashtable h = nc.mairie.technique.MairieLDAP.chercherUserLDAPAttributs(aUser.getUserName());
	String dpt = (String)h.get("department");
%>

<% if (aUser.getUserName().equals("boulu72") || aUser.getUserName().equals("peymi67")) {%>
menu.ajouterFils(new Lien("ZZZTESTEUR", "Testeur de process", "Testeur de process", true));
<%}%>
<% 
if(aUser.getUserName().equals("boulu72")||aUser.getUserName().equals("fonol77")){
	dpt = "ATM";
}
//if ((aUser.getUserName().equals("nicco81"))||(!process.dpt.equals("ATM"))) {
if ((dpt!=null)&&(!dpt.equals(""))){

	//LB 12/01/12 changement suite à modif departmentnumber dans l'AD
	if (dpt.toUpperCase().startsWith("ATM")) {
		dpt="ATM";
	}

	if(!dpt.equals("ATM")) {
	%>
		document.write(menuService.afficher());
	<% nc.mairie.technique.VariableGlobale.ajouter(request,"MENU","menuService");
		nc.mairie.technique.VariableGlobale.ajouter(request,"ACCRONYME",dpt);
	}else{
%>
		document.write(menu.afficher());
<%
		nc.mairie.technique.VariableGlobale.ajouter(request,"MENU","menu");
	}
}else{
%>
	alert('Vous ne disposez pas des droits nécessaire pour utiliser cette application.');
<%
}
%>

	//document.write(menu.afficher());
-->
</script><BR>
<BR>
<BR>
<BR>
<BR>
</FORM>
</BODY>
</HTML>
