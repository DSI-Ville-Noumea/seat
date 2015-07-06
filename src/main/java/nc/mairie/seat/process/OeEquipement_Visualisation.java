package nc.mairie.seat.process;

import nc.mairie.seat.metier.AActifs;
import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.Pneu;
import nc.mairie.technique.*;
/**
 * Process OeEquipement_Visualisation
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
*/
public class OeEquipement_Visualisation extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3568321179335974723L;
	private EquipementInfos equipementInfosCourant;
	private Equipement equipementCourant;
	public static final int STATUT_RECHERCHE= 1 ;
	private boolean first;
	public boolean isDebranche = false;
	private String focus = null;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	//performPB_RECHERCHE_EQUIP(request);
	//if(first){
	String debranche = (String)VariableActivite.recuperer(this,"DEBRANCHE");
	if(debranche==null){
		setDebranche(false);
	}else{
		if(debranche.equals("TRUE")){
			setDebranche(true);
		}else{
			setDebranche(false);
		}
	}
	String responsable = "agent non trouvé";
		EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request, "EQUIPEMENTINFOS");
		
		if (unEquipementInfos!=null){
			if (unEquipementInfos.existeEquipementInfosEquip(getTransaction(),unEquipementInfos.getNumeroinventaire())){
				setEquipementInfosCourant(unEquipementInfos);
			}else{
				VariableGlobale.enlever(request,"EQUIPEMENTINFOS");
			}
		}
		//quand appuie sur entree
		if(!getZone(getNOM_EF_RECHERCHE_EQUIP()).equals("")){
			performPB_RECHERCHE_EQUIP(request);
		}
	//}
	

	if (null!=(getEquipementInfosCourant())){
		if(null!=getEquipementInfosCourant().getNumeroinventaire()){
			Equipement monEquip = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
			if (getTransaction().isErreur()){
				return;
			}
			
			setEquipementCourant(monEquip);
			addZone(getNOM_ST_CARBU(),getEquipementInfosCourant().getDesignationcarbu());
			addZone(getNOM_ST_COMPTEUR(),getEquipementInfosCourant().getDesignationcompteur());
			addZone(getNOM_ST_DATEHORSCIRCUIT(),getEquipementInfosCourant().getDatehorscircuit().trim());
			addZone(getNOM_ST_DATEMISEENCIRCULATION(),getEquipementInfosCourant().getDatemiseencirculation().trim());
			addZone(getNOM_ST_DATEVENTEREFORME(),getEquipementCourant().getDateventeoureforme().trim());
			addZone(getNOM_ST_GARANTIE(),getEquipementCourant().getDureegarantie());
			addZone(getNOM_ST_MARQUE(),getEquipementInfosCourant().getDesignationmarque());
			addZone(getNOM_ST_MODELE(),getEquipementInfosCourant().getDesignationmodele());
			addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
			addZone(getNOM_ST_NOINVENT(),getEquipementInfosCourant().getNumeroinventaire());
			
			Modeles monModele = Modeles.chercherModeles(getTransaction(),getEquipementCourant().getCodemodele());
			if (getTransaction().isErreur()){
				return;
			}
			
			// recherche du service auquel l'équipement est affecté et l'agent responsable
			AffectationServiceInfos unASI = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),Services.dateDuJour());
			if(getTransaction().isErreur()){
				// l'équipement peut ne pas être affecté
				getTransaction().traiterErreur();
			}

			if (unASI!=null){
				addZone(getNOM_ST_SERVICE(),unASI.getCodeservice()+" - "+unASI.getLiserv());
				if ((unASI.getNomatr()!=null)&&(!unASI.getNomatr().equals("0"))){
					// recherche de l'agent responsable si l'équipement a un responsable
					// selon le service
					if (unASI.getCodeservice().equals("4000")){
						AgentCDE unAgent =AgentCDE.chercherAgentCDE(getTransaction(),unASI.getNomatr());
						if(getTransaction().isErreur()){
							getTransaction().traiterErreur();
							responsable = "agent non trouvé";
						}else{
							responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
						}
						//addZone(getNOM_ST_AGENT_RESPONSABLE(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
					}else if (unASI.getCodeservice().equals("5000")){
						AgentCCAS unAgent = AgentCCAS.chercherAgentCCAS(getTransaction(),unASI.getNomatr());
						if (getTransaction().isErreur()){
							getTransaction().traiterErreur();
							responsable = "agent non trouvé";
						}else{
							responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
						}
						//addZone(getNOM_ST_AGENT_RESPONSABLE(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
					}else{
						// recherche classique
						AActifs unAgent = AActifs.chercherAActifs(getTransaction(),unASI.getNomatr());
						if(getTransaction().isErreur()){
							getTransaction().traiterErreur();
							responsable = "agent non trouvé";
						}else{
							responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
						}
						//addZone(getNOM_ST_AGENT_RESPONSABLE(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
					}
				}else{
					//addZone(getNOM_ST_AGENT_RESPONSABLE(),"sans");
					responsable = "sans";
				}
				addZone(getNOM_ST_AGENT_RESPONSABLE(),responsable);
			}
			
			
			Pneu monPneu = Pneu.chercherPneu(getTransaction(),monModele.getCodepneu());
			if (getTransaction().isErreur()){
				return;
			}
			addZone(getNOM_ST_PNEU(),monPneu.getDimension());
			addZone(getNOM_ST_PRIX(),getEquipementCourant().getPrixachat());
			addZone(getNOM_ST_PUISSANCE(),monModele.getPuissance());
			addZone(getNOM_ST_RESERVOIR(),monModele.getCapacitereservoir());
			addZone(getNOM_ST_TEQUIP(),getEquipementInfosCourant().getDesignationtypeequip());		
			addZone(getNOM_ST_NOMEQUIP(),getVAL_ST_MARQUE()+" "+getVAL_ST_MODELE()+" "+getVAL_ST_TEQUIP());
			addZone(getNOM_ST_VERSION(),getEquipementInfosCourant().getVersion());
			addZone(getNOM_ST_PE(),getVAL_ST_MARQUE()+" "+getVAL_ST_MODELE());
			if ("F".equals(getEquipementCourant().getReserve().trim())){
				addZone(getNOM_ST_RESERVE(),"non");
			}else{
				addZone(getNOM_ST_RESERVE(),"oui");
			}
	//		Si les dates sont = 01/01/0001 alors on met vides les zones
			if ("01/01/0001".equals(getEquipementCourant().getDatehorscircuit())){
				addZone(getNOM_ST_DATEHORSCIRCUIT(), "");
			}else{
				addZone(getNOM_ST_DATEHORSCIRCUIT(), getEquipementCourant().getDatehorscircuit());
			}
			if ("01/01/0001".equals(getEquipementCourant().getDatemiseencirculation())){
				addZone(getNOM_ST_DATEMISEENCIRCULATION(), "");
			}else{
				addZone(getNOM_ST_DATEMISEENCIRCULATION(), getEquipementCourant().getDatemiseencirculation());
			}
			if ("01/01/0001".equals(getEquipementCourant().getDatereceptionmateriel())){
				addZone(getNOM_ST_DATERECEPTION(), "");
			}else{
				addZone(getNOM_ST_DATERECEPTION(), getEquipementCourant().getDatereceptionmateriel());
			}
			if ("01/01/0001".equals(getEquipementCourant().getDateventeoureforme())){
				addZone(getNOM_ST_DATEVENTEREFORME(),"");
			}else{
				addZone(getNOM_ST_DATEVENTEREFORME(), getEquipementCourant().getDateventeoureforme());
			}
		}
	}
setFirst(false);
}
/**
 * Constructeur du process OeEquipement_Visualisation.
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 */
public OeEquipement_Visualisation() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHE() {
	return "NOM_PB_RECHERCHE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	//#12438 
	addZone(getNOM_EF_RECHERCHE_EQUIP(), "");
	
	VariableActivite.ajouter(this,"MODE","VISUALISER");
	setStatut(STATUT_RECHERCHE,true);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CARBU
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_CARBU() {
	return "NOM_ST_CARBU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CARBU
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_CARBU() {
	return getZone(getNOM_ST_CARBU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATEHORSCIRCUIT
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DATEHORSCIRCUIT() {
	return "NOM_ST_DATEHORSCIRCUIT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATEHORSCIRCUIT
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DATEHORSCIRCUIT() {
	return getZone(getNOM_ST_DATEHORSCIRCUIT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATEMISEENCIRCULATION
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DATEMISEENCIRCULATION() {
	return "NOM_ST_DATEMISEENCIRCULATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATEMISEENCIRCULATION
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DATEMISEENCIRCULATION() {
	return getZone(getNOM_ST_DATEMISEENCIRCULATION());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATERECEPTION
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DATERECEPTION() {
	return "NOM_ST_DATERECEPTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATERECEPTION
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DATERECEPTION() {
	return getZone(getNOM_ST_DATERECEPTION());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATEVENTEREFORME
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DATEVENTEREFORME() {
	return "NOM_ST_DATEVENTEREFORME";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATEVENTEREFORME
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DATEVENTEREFORME() {
	return getZone(getNOM_ST_DATEVENTEREFORME());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_GARANTIE
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_GARANTIE() {
	return "NOM_ST_GARANTIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_GARANTIE
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_GARANTIE() {
	return getZone(getNOM_ST_GARANTIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PE
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PE() {
	return "NOM_ST_PE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PE
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PE() {
	return getZone(getNOM_ST_PE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PNEU
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PNEU() {
	return "NOM_ST_PNEU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PNEU
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PNEU() {
	return getZone(getNOM_ST_PNEU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PRIX
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PRIX() {
	return "NOM_ST_PRIX";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PRIX
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PRIX() {
	return getZone(getNOM_ST_PRIX());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PUISSANCE
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PUISSANCE() {
	return "NOM_ST_PUISSANCE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PUISSANCE
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PUISSANCE() {
	return getZone(getNOM_ST_PUISSANCE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_RESERVOIR
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_RESERVOIR() {
	return "NOM_ST_RESERVOIR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_RESERVOIR
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_RESERVOIR() {
	return getZone(getNOM_ST_RESERVOIR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TEQUIP
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TEQUIP() {
	return "NOM_ST_TEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TEQUIP
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TEQUIP() {
	return getZone(getNOM_ST_TEQUIP());
}

/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOMEQUIP
 * Date de création : (08/06/05 08:44:17)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOMEQUIP() {
	return "NOM_ST_NOMEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOMEQUIP
 * Date de création : (08/06/05 08:44:17)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOMEQUIP() {
	return getZone(getNOM_ST_NOMEQUIP());
}
	/**
	 * @return Renvoie equipementInfosCourant.
	 */
	private EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	/**
	 * @param equipementInfosCourant equipementInfosCourant à définir.
	 */
	private void setEquipementInfosCourant(
			EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}
	/**
	 * @return Renvoie equipementCourant.
	 */
	private Equipement getEquipementCourant() {
		return equipementCourant;
	}
	/**
	 * @param equipementCourant equipementCourant à définir.
	 */
	private void setEquipementCourant(Equipement equipementCourant) {
		this.equipementCourant = equipementCourant;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_RESERVE
 * Date de création : (08/06/05 13:12:54)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_RESERVE() {
	return "NOM_ST_RESERVE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_RESERVE
 * Date de création : (08/06/05 13:12:54)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_RESERVE() {
	return getZone(getNOM_ST_RESERVE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_VERSION
 * Date de création : (08/06/05 13:16:41)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_VERSION() {
	return "NOM_ST_VERSION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_VERSION
 * Date de création : (08/06/05 13:16:41)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_VERSION() {
	return getZone(getNOM_ST_VERSION());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE_EQUIP
 * Date de création : (24/08/05 15:13:49)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHE_EQUIP() {
	return "NOM_PB_RECHERCHE_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (24/08/05 15:13:49)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	//on vide les zones
	addZone(getNOM_ST_CARBU(),"");
	addZone(getNOM_ST_COMPTEUR(),"");
	addZone(getNOM_ST_DATEHORSCIRCUIT(),"");
	addZone(getNOM_ST_DATEMISEENCIRCULATION(),"");
	addZone(getNOM_ST_DATEVENTEREFORME(),"");
	addZone(getNOM_ST_GARANTIE(),"");
	addZone(getNOM_ST_MARQUE(),"");
	addZone(getNOM_ST_MODELE(),"");
	addZone(getNOM_ST_NOIMMAT(),"");
	addZone(getNOM_ST_NOINVENT(),"");

	addZone(getNOM_ST_PNEU(),"");
	addZone(getNOM_ST_PRIX(),"");
	addZone(getNOM_ST_PUISSANCE(),"");
	addZone(getNOM_ST_RESERVOIR(),"");
	addZone(getNOM_ST_TEQUIP(),"");		
	addZone(getNOM_ST_NOMEQUIP(),"");
	addZone(getNOM_ST_VERSION(),"");
	addZone(getNOM_ST_PE(),"");
	addZone(getNOM_ST_RESERVE(),"");
	addZone(getNOM_ST_DATEHORSCIRCUIT(), "");
	addZone(getNOM_ST_DATEMISEENCIRCULATION(), "");
	addZone(getNOM_ST_DATERECEPTION(), "");
	addZone(getNOM_ST_DATEVENTEREFORME(),"");
	addZone(getNOM_ST_AGENT_RESPONSABLE(),"");
	addZone(getNOM_ST_SERVICE(),"");
	
	//	recherche de l'équipement voulu
	String recherche = getZone(getNOM_EF_RECHERCHE_EQUIP()).toUpperCase();
	EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfosInvOuImmat(getTransaction(),recherche);
	if(getTransaction().isErreur()){
		return false;
	}
	if(unEquipementInfos!=null){
		if(unEquipementInfos.getNumeroinventaire()==null){
			getTransaction().declarerErreur("L'équipement recherché n'a pas été trouvé.");
			return false;
		}
	}

	setEquipementInfosCourant(unEquipementInfos);
	Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
	if(getTransaction().isErreur()){
		return false;
	}
	setEquipementCourant(unEquipement);
	
	//pour garder le même equipement
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE_EQUIP
 * Date de création : (24/08/05 15:13:49)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_RECHERCHE_EQUIP() {
	return "NOM_EF_RECHERCHE_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE_EQUIP
 * Date de création : (24/08/05 15:13:49)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_RECHERCHE_EQUIP() {
	return getZone(getNOM_EF_RECHERCHE_EQUIP());
}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_AGENT_RESPONSABLE
 * Date de création : (02/04/07 14:27:29)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_AGENT_RESPONSABLE() {
	return "NOM_ST_AGENT_RESPONSABLE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_AGENT_RESPONSABLE
 * Date de création : (02/04/07 14:27:29)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_AGENT_RESPONSABLE() {
	return getZone(getNOM_ST_AGENT_RESPONSABLE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (02/04/07 14:27:29)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (02/04/07 14:27:29)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (08/06/05 08:26:34)
 * author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_RETOUR
		if (testerParametre(request, getNOM_PB_RETOUR())) {
			return performPB_RETOUR(request);
		}

		//Si clic sur le bouton PB_RECHERCHE_EQUIP
		if (testerParametre(request, getNOM_PB_RECHERCHE_EQUIP())) {
			return performPB_RECHERCHE_EQUIP(request);
		}
		
		
		
		//Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (06/04/07 10:20:51)
 * author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeEquipement_Visualisation.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RETOUR
 * Date de création : (06/04/07 10:20:51)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RETOUR() {
	return "NOM_PB_RETOUR";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (06/04/07 10:20:51)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RETOUR(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
public boolean isDebranche() {
	return isDebranche;
}
public void setDebranche(boolean isDebranche) {
	this.isDebranche = isDebranche;
}
/**
 * @return Renvoie focus.
 */
public String getFocus() {
	if (focus == null) {
		focus=getDefaultFocus();
	}
	return focus;
}
/**
 * @param focus focus à définir.
 */
public void setFocus(String focus) {
	this.focus = focus;
}
/**
 * focus focus à définir.
 * @return focus
 */
public String getDefaultFocus() {
	return getNOM_EF_RECHERCHE_EQUIP();
}
}
