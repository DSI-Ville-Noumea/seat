package nc.mairie.seat.process;

import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.BPC;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.ModePrise;
import nc.mairie.seat.metier.ModeleInfos;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.seat.metier.Pompes;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
/**
 * Process OeBPC_Visualisation
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
*/
public class OeBPC_Visualisation extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7153387823143055100L;
	private EquipementInfos equipementInfosCourant;
	private Equipement equipementCourant;
	private PMateriel pMaterielCourant;
	private PMatInfos pMatInfosCourant;
	private BPC bpcCourant;
	public static final int STATUT_RECHERCHE= 1 ;
	public boolean isDebranche = false;
	private boolean isMateriel = false;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	//if(etatStatut()==STATUT_RECHERCHE){
		//EquipementInfos unEquipementInfos = (EquipementInfos)VariableActivite.recuperer(this, "EQUIPEMENTINFOS");
		//setEquipementInfosCourant(unEquipementInfos);
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
	//}
		
		BPC unBPC = (BPC)VariableGlobale.recuperer(request, "BPC");
		if (unBPC!=null){
			if (unBPC.existeBPC(getTransaction(),unBPC.getNumerobpc())){
				if(getTransaction().isErreur()){
					//équipement
					addZone(getNOM_ST_NOIMMAT(),"");
					addZone(getNOM_ST_NOINVENT(),"");
					addZone(getNOM_ST_NOMEQUIP(),"");
					addZone(getNOM_ST_TYPE(),"");
					addZone(getNOM_ST_CARBU(),"");
					addZone(getNOM_ST_TCOMPTEUR(),"");
					//BPC
					addZone(getNOM_ST_COMPTEUR(),"");
					addZone(getNOM_ST_DATE(),"");
					addZone(getNOM_ST_HEURE(),"");
					addZone(getNOM_ST_MODEPRISE(),"");
					addZone(getNOM_ST_NOBPC(),"");
					addZone(getNOM_ST_POMPE(),"");
					addZone(getNOM_ST_QTE(),"");
					return;
				}
				setBpcCourant(unBPC);
			}else{
				VariableGlobale.enlever(request,"BPC");
			}
//			recherche de l'équipement
			EquipementInfos unEI = EquipementInfos.chercherEquipementInfos(getTransaction(),getBpcCourant().getNumeroinventaire());
			if(getTransaction().isErreur()){
				getTransaction().traiterErreur() ;
				PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),getBpcCourant().getNumeroinventaire());
				if(getTransaction().isErreur()){
					return ;
				}
				setPMatInfosCourant(unPMatInfos);
				if(unPMatInfos!=null){
					isMateriel = true;
				}
				if(unBPC!=null){
					//recherche du service concerné à la date du BPC en visu
						if(unBPC.getNumeroinventaire()!=null){
							PM_Affectation_Sce_Infos unPMASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),unBPC.getNumeroinventaire(),unBPC.date);
							if(getTransaction().isErreur()){
								//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
								getTransaction().traiterErreur();
								addZone(getNOM_ST_SERVICE(),"pas affecté");
							}else{
								Service unService = Service.chercherService(getTransaction(),unPMASI.getSiserv());
								if(getTransaction().isErreur()){
									return ;
								}
								addZone(getNOM_ST_SERVICE(),unService.getLiserv());
							}
						}
					}
			}else{
				setEquipementInfosCourant(unEI);
				if(unBPC!=null){
					//recherche du service concerné à la date du BPC en visu
						if(unBPC.getNumeroinventaire()!=null){
							AffectationServiceInfos unAffectationServiceInfos = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),unBPC.getNumeroinventaire(),unBPC.date);
							if(getTransaction().isErreur()){
								//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
								getTransaction().traiterErreur();
								addZone(getNOM_ST_SERVICE(),"pas affecté");
							}else{
								addZone(getNOM_ST_SERVICE(),unAffectationServiceInfos.getLiserv());
							}
						}
					}
			}
			
		}
		if (getTransaction().isErreur()){
			return;
		}
	//quand appui sur entree
	if(!getZone(getNOM_EF_RECHNUMBPC()).equals("")){
		performPB_RECHERCHER(request);
	}
	
	//initialisation des zones
	if(!isMateriel){
		if (null!=getEquipementInfosCourant()){
		//infos concernant l'equipement
			//if(!isFirst){
				addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
				addZone(getNOM_ST_NOINVENT(),getEquipementInfosCourant().getNumeroinventaire());
				addZone(getNOM_ST_NOMEQUIP(),getEquipementInfosCourant().getDesignationmarque()+" "+getEquipementInfosCourant().getDesignationmodele());
				addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
				addZone(getNOM_ST_CARBU(),getEquipementInfosCourant().getDesignationcarbu());
				addZone(getNOM_ST_TCOMPTEUR(),getEquipementInfosCourant().getDesignationcompteur());
				
			//}
		}
	}else{
		if(null!=getPMatInfosCourant()){
			addZone(getNOM_ST_NOIMMAT(),getPMatInfosCourant().getPmserie());
			addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
			addZone(getNOM_ST_NOMEQUIP(),getPMatInfosCourant().getDesignationmarque()+" "+getPMatInfosCourant().getDesignationmodele());
			addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
			ModeleInfos unMI = ModeleInfos.chercherModeleInfos(getTransaction(),getPMatInfosCourant().getCodemodele());
			if(getTransaction().isErreur()){
				return ;
			}
			addZone(getNOM_ST_CARBU(),unMI.getDesignationcarbu());
			//addZone(getNOM_ST_TCOMPTEUR(),getPMatInfosCourant().getDesignationcompteur());
		}
	}
	if (getTransaction().isErreur()){
		return;
	}
	if (null!=getBpcCourant()){
		if(getBpcCourant().getNumerobpc()!=null){
			//infos concernant le bpc
			addZone(getNOM_ST_COMPTEUR(),bpcCourant.getValeurcompteur());
			addZone(getNOM_ST_DATE(),bpcCourant.getDate());
			if ((bpcCourant.getHeure()).length()<3){
				addZone(getNOM_ST_HEURE(),bpcCourant.getHeure()+"H");
			}else{
				addZone(getNOM_ST_HEURE(),bpcCourant.getHeure());
			}
			addZone(getNOM_ST_NOBPC(),bpcCourant.getNumerobpc());
			ModePrise.chercherModePrise(getTransaction(),bpcCourant.getModedeprise());
			if (getTransaction().isErreur()){
				return;
			}
			//addZone(getNOM_ST_MODEPRISE(),monMP.getDesignationmodeprise());
			// recherche de la pompe
			Pompes unePompe = Pompes.chercherPompes(getTransaction(),getBpcCourant().getNumeropompe());
			if(getTransaction().isErreur()){
				return ;
			}
			addZone(getNOM_ST_POMPE(),unePompe.getLibelle_pompe());
			addZone(getNOM_ST_QTE(),bpcCourant.getQuantite()+" L");
			
			if(!isMateriel){
				if(getEquipementCourant()!=null){
					if(getEquipementCourant().getNumeroinventaire()!=null){
						AffectationServiceInfos unAffectationServiceInfos = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementCourant().getNumeroinventaire(),getBpcCourant().getDate());
						if(getTransaction().isErreur()){
							//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
							getTransaction().traiterErreur();
							addZone(getNOM_ST_SERVICE(),"pas affecté");
						}else{
							addZone(getNOM_ST_SERVICE(),unAffectationServiceInfos.getLiserv());
						}
					}
					
				}
			}else{
				if(getPMatInfosCourant()!=null){
					if(null!=getPMatInfosCourant().getPminv()){
						PM_Affectation_Sce_Infos unPMASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),getBpcCourant().getDate());
						if(getTransaction().isErreur()){
							//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
							getTransaction().traiterErreur();
							addZone(getNOM_ST_SERVICE(),"pas affecté");
						}else{
							Service unService = Service.chercherService(getTransaction(),unPMASI.getSiserv());
							if(getTransaction().isErreur()){
								return ;
							}
							addZone(getNOM_ST_SERVICE(),unService.getLiserv());
						}
					}
				}
			}
		}
	}
}
/**
 * Constructeur du process OeBPC_Visualisation.
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
public OeBPC_Visualisation() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
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
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
//	équipement
	addZone(getNOM_ST_NOIMMAT(),"");
	addZone(getNOM_ST_NOINVENT(),"");
	addZone(getNOM_ST_NOMEQUIP(),"");
	addZone(getNOM_ST_TYPE(),"");
	addZone(getNOM_ST_CARBU(),"");
	addZone(getNOM_ST_TCOMPTEUR(),"");
	//BPC
	addZone(getNOM_ST_COMPTEUR(),"");
	addZone(getNOM_ST_DATE(),"");
	addZone(getNOM_ST_HEURE(),"");
	addZone(getNOM_ST_MODEPRISE(),"");
	addZone(getNOM_ST_NOBPC(),"");
	addZone(getNOM_ST_POMPE(),"");
	addZone(getNOM_ST_QTE(),"");
	setStatut(STATUT_RECHERCHE,true);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CARBU
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_CARBU() {
	return "NOM_ST_CARBU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CARBU
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_CARBU() {
	return getZone(getNOM_ST_CARBU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATE
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_DATE() {
	return "NOM_ST_DATE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATE
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_DATE() {
	return getZone(getNOM_ST_DATE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_HEURE
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_HEURE() {
	return "NOM_ST_HEURE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_HEURE
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_HEURE() {
	return getZone(getNOM_ST_HEURE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODEPRISE
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_MODEPRISE() {
	return "NOM_ST_MODEPRISE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODEPRISE
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_MODEPRISE() {
	return getZone(getNOM_ST_MODEPRISE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOBPC
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_NOBPC() {
	return "NOM_ST_NOBPC";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOBPC
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_NOBPC() {
	return getZone(getNOM_ST_NOBPC());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOMEQUIP
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_NOMEQUIP() {
	return "NOM_ST_NOMEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOMEQUIP
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_NOMEQUIP() {
	return getZone(getNOM_ST_NOMEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_POMPE
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_POMPE() {
	return "NOM_ST_POMPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_POMPE
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_POMPE() {
	return getZone(getNOM_ST_POMPE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_QTE
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_QTE() {
	return "NOM_ST_QTE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_QTE
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_QTE() {
	return getZone(getNOM_ST_QTE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
	/**
	 * @return Renvoie bpcCourant.
	 */
	public BPC getBpcCourant() {
		return bpcCourant;
	}
	/**
	 * @param bpcCourant bpcCourant à définir.
	 */
	public void setBpcCourant(BPC bpcCourant) {
		this.bpcCourant = bpcCourant;
	}
	/**
	 * @return Renvoie equipementCourant.
	 */
	public Equipement getEquipementCourant() {
		return equipementCourant;
	}
	/**
	 * @param equipementCourant equipementCourant à définir.
	 */
	public void setEquipementCourant(Equipement equipementCourant) {
		this.equipementCourant = equipementCourant;
	}
	/**
	 * @return Renvoie equipementInfosCourant.
	 */
	public EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	/**
	 * @param equipementInfosCourant equipementInfosCourant à définir.
	 */
	public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}
	/**
	 * @return Renvoie equipementCourant.
	 */
	public PMateriel getPMaterielCourant() {
		return pMaterielCourant;
	}
	/**
	 * @param pMaterielCourant pMaterielCourant à définir.
	 */
	public void setPMaterielCourant(PMateriel pMaterielCourant) {
		this.pMaterielCourant = pMaterielCourant;
	}
	/**
	 * @return Renvoie equipementInfosCourant.
	 */
	public PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	/**
	 * @param pMatInfosCourant pMatInfosCourant à définir.
	 */
	public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (11/07/05 14:40:40)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (11/07/05 14:40:40)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (18/08/05 09:47:06)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (18/08/05 09:47:06)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TCOMPTEUR
 * Date de création : (23/08/05 08:23:27)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_TCOMPTEUR() {
	return "NOM_ST_TCOMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TCOMPTEUR
 * Date de création : (23/08/05 08:23:27)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_TCOMPTEUR() {
	return getZone(getNOM_ST_TCOMPTEUR());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (09/06/05 10:50:41)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_RECHERCHER
		if (testerParametre(request, getNOM_PB_RECHERCHER())) {
			return performPB_RECHERCHER(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
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
 * Date de création : (29/08/05 09:25:09)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeBPC_Visualisation.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHER
 * Date de création : (29/08/05 09:25:09)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHER() {
	return "NOM_PB_RECHERCHER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (29/08/05 09:25:09)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHER(javax.servlet.http.HttpServletRequest request) throws Exception {
	boolean trouveEquipement = false;
	//	équipement
	addZone(getNOM_ST_NOIMMAT(),"");
	addZone(getNOM_ST_NOINVENT(),"");
	addZone(getNOM_ST_NOMEQUIP(),"");
	addZone(getNOM_ST_TYPE(),"");
	addZone(getNOM_ST_CARBU(),"");
	addZone(getNOM_ST_TCOMPTEUR(),"");
	addZone(getNOM_ST_SERVICE(),"");
	//BPC
	addZone(getNOM_ST_COMPTEUR(),"");
	addZone(getNOM_ST_DATE(),"");
	addZone(getNOM_ST_HEURE(),"");
	addZone(getNOM_ST_MODEPRISE(),"");
	addZone(getNOM_ST_NOBPC(),"");
	addZone(getNOM_ST_POMPE(),"");
	addZone(getNOM_ST_QTE(),"");
	String nobpc = getZone(getNOM_EF_RECHNUMBPC());
	if(nobpc.equals("")){
		getTransaction().declarerErreur("Vous devez saisir un numéro de BPC.");
		return false;
	}
	BPC unBPC = BPC.chercherBPC(getTransaction(),nobpc);
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("Aucun BPC trouvé avec ce numéro.");
		return false;
	}
	setBpcCourant(unBPC);
	/*setEquipementInfosCourant(EquipementInfos.chercherEquipementInfos(getTransaction(),getBpcCourant().getNumerobpc()));
	if(getTransaction().isErreur()){
		return false;
	}*/
	// recherche de l'équipement
	
	setEquipementInfosCourant(EquipementInfos.chercherEquipementInfos(getTransaction(),getBpcCourant().getNumeroinventaire()));
	if(getTransaction().isErreur()){
		getTransaction().traiterErreur();
	}
	if(getEquipementInfosCourant()!=null){
		if(getEquipementInfosCourant().getNumeroinventaire()!=null){
			trouveEquipement = true;
			isMateriel = false;
			setEquipementCourant(Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire()));
			if(getTransaction().isErreur()){
				return false;
			}
		}else{
			trouveEquipement = false;
		}
	}else{
		trouveEquipement = false;
	}
	if(!trouveEquipement){
		setPMatInfosCourant(PMatInfos.chercherPMatInfos(getTransaction(),getBpcCourant().getNumeroinventaire()));
		if(getTransaction().isErreur()){
			getTransaction().declarerErreur("Aucun équipement ne correspond au numéro d'inventaire.");
			return false;
		}
		if(getPMatInfosCourant()!=null){
			if(null!=getPMatInfosCourant().getPminv()){
				isMateriel = true;
				setPMaterielCourant(PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv()));
				if(getTransaction().isErreur()){
					return false;
				}
			}
		}
		
	}
	
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHNUMBPC
 * Date de création : (29/08/05 09:25:09)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_RECHNUMBPC() {
	return "NOM_EF_RECHNUMBPC";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHNUMBPC
 * Date de création : (29/08/05 09:25:09)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_RECHNUMBPC() {
	return getZone(getNOM_EF_RECHNUMBPC());
}

	public boolean isDebranche() {
		return isDebranche;
	}
	public void setDebranche(boolean isDebranche) {
		this.isDebranche = isDebranche;
	}
}
