package nc.mairie.seat.process;

import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.Declarations;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
/**
 * Process OeDeclaration_Visualisation
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
*/
public class OeDeclaration_Visualisation extends nc.mairie.technique.BasicProcess {
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
 // on récupère la déclaration
	String declarant = "agent non trouvé";
	Declarations uneDec = (Declarations)VariableGlobale.recuperer(request, "DECLARATION");
	if (uneDec!=null){
		// on reseigne les zones
		addZone(getNOM_ST_ANOMALIES(),uneDec.getAnomalies());
		addZone(getNOM_ST_NUMOT(),uneDec.getCodeot());
		// recherche du service
		Service unService = Service.chercherService(getTransaction(),uneDec.getCodeservice());
		if(getTransaction().isErreur()){
			return ;
		}
		addZone(getNOM_ST_NUMSCE(),uneDec.getCodeservice().trim()+" "+unService.getLiserv().trim());
		addZone(getNOM_ST_DATE(),uneDec.getDate());
		// selon le code service
		if (uneDec.getCodeservice().equals("4000")){
			AgentCDE unAgent = AgentCDE.chercherAgentCDE(getTransaction(),uneDec.getMatricule());
			if(getTransaction().isErreur()){
				getTransaction().traiterErreur();
				declarant = "agent non trouvé";
			}else{
				declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
			}
			//addZone(getNOM_ST_DECLARANT(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
		}else if(uneDec.getCodeservice().equals("5000")){
			AgentCCAS unAgent = AgentCCAS.chercherAgentCCAS(getTransaction(),uneDec.getMatricule());
			if(getTransaction().isErreur()){
				getTransaction().traiterErreur();
				declarant = "agent non trouvé";
			}else{
				declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
			}
			//addZone(getNOM_ST_DECLARANT(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
		}else{
			Agents unAgent = Agents.chercherAgents(getTransaction(),uneDec.getMatricule());
			if(getTransaction().isErreur()){
				getTransaction().traiterErreur();
				declarant = "agent non trouvé";
			}else{
				declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
			}
			//addZone(getNOM_ST_DECLARANT(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
		}
		addZone(getNOM_ST_DECLARANT(),declarant);
		EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),uneDec.getNuminv());
		if(getTransaction().isErreur()){
			getTransaction().traiterErreur();
			PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),uneDec.getNuminv());
			if(getTransaction().isErreur()){
				return ;
			}
			addZone(getNOM_ST_NOM_EQUIP(),unPMatInfos.getDesignationmarque()+" "+unPMatInfos.getDesignationmodele());
			addZone(getNOM_ST_NUMIMMAT(),unPMatInfos.getPmserie());
			addZone(getNOM_ST_NUMINV(),unPMatInfos.getPminv());
			addZone(getNOM_ST_TYPE(),unPMatInfos.getDesignationtypeequip());
			// service en cours
			PM_Affectation_Sce_Infos unPMASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),uneDec.getNuminv(),Services.dateDuJour());
			if(getTransaction().isErreur()){
				// l'équipement peut ne pas être affecté
				getTransaction().traiterErreur();
			}
			addZone(getNOM_ST_CODE_SCE(),unPMASI.getSiserv());
			Service monService = Service.chercherService(getTransaction(),unPMASI.getSiserv());
			if(getTransaction().isErreur()){
				return ;
			}
			addZone(getNOM_ST_LIBELLE_SCE(),monService.getLiserv());
		}else{
			addZone(getNOM_ST_NOM_EQUIP(),unEquipementInfos.getDesignationmarque()+" "+unEquipementInfos.getDesignationmodele());
			addZone(getNOM_ST_NUMIMMAT(),unEquipementInfos.getNumeroimmatriculation());
			addZone(getNOM_ST_NUMINV(),unEquipementInfos.getNumeroinventaire());
			addZone(getNOM_ST_TYPE(),unEquipementInfos.getDesignationtypeequip());
			// service en cours
			AffectationServiceInfos unASI = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),uneDec.getNuminv(),Services.dateDuJour());
			if(getTransaction().isErreur()){
				// l'équipement peut ne pas être affecté
				getTransaction().traiterErreur();
			}
			addZone(getNOM_ST_CODE_SCE(),unASI.getCodeservice());
			addZone(getNOM_ST_LIBELLE_SCE(),unASI.getLiserv());
		}
	}

}
/**
 * Constructeur du process OeDeclaration_Visualisation.
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public OeDeclaration_Visualisation() {
	super();
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ANOMALIES
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_ANOMALIES() {
	return "NOM_ST_ANOMALIES";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_ANOMALIES
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_ANOMALIES() {
	return getZone(getNOM_ST_ANOMALIES());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CODE_SCE
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_CODE_SCE() {
	return "NOM_ST_CODE_SCE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CODE_SCE
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_CODE_SCE() {
	return getZone(getNOM_ST_CODE_SCE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATE
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DATE() {
	return "NOM_ST_DATE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATE
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DATE() {
	return getZone(getNOM_ST_DATE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DECLARANT
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DECLARANT() {
	return "NOM_ST_DECLARANT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DECLARANT
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DECLARANT() {
	return getZone(getNOM_ST_DECLARANT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_LIBELLE_SCE
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_LIBELLE_SCE() {
	return "NOM_ST_LIBELLE_SCE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_LIBELLE_SCE
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_LIBELLE_SCE() {
	return getZone(getNOM_ST_LIBELLE_SCE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOM_EQUIP
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOM_EQUIP() {
	return "NOM_ST_NOM_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOM_EQUIP
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOM_EQUIP() {
	return getZone(getNOM_ST_NOM_EQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NUMIMMAT
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NUMIMMAT() {
	return "NOM_ST_NUMIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NUMIMMAT
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NUMIMMAT() {
	return getZone(getNOM_ST_NUMIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NUMINV
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NUMINV() {
	return "NOM_ST_NUMINV";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NUMINV
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NUMINV() {
	return getZone(getNOM_ST_NUMINV());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NUMOT
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NUMOT() {
	return "NOM_ST_NUMOT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NUMOT
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NUMOT() {
	return getZone(getNOM_ST_NUMOT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NUMSCE
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NUMSCE() {
	return "NOM_ST_NUMSCE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NUMSCE
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NUMSCE() {
	return getZone(getNOM_ST_NUMSCE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (03/04/07 14:35:33)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_RETOUR
		if (testerParametre(request, getNOM_PB_RETOUR())) {
			return performPB_RETOUR(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (03/04/07 14:58:05)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeDeclaration_Visualisation.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RETOUR
 * Date de création : (03/04/07 14:58:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RETOUR() {
	return "NOM_PB_RETOUR";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (03/04/07 14:58:05)
 * @author : Générateur de process
 */
public boolean performPB_RETOUR(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"DEBRANCHE","FALSE");
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
}
