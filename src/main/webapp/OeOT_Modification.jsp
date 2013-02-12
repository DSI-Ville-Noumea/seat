<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="theme/sigp2.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" href="theme/calendrier-mairie.css" type="text/css">
<SCRIPT type="text/javascript" src="js/GestionCalendrier.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/GestionBoutonDroit.js"></SCRIPT>
<SCRIPT language="JavaScript">

//afin de sélectionner un élément dans une liste
function executeBouton(nom)
{
document.formu.elements[nom].click();
}

// afin de mettre le focus sur une zone précise
function setfocus(nom)
{
if (document.formu.elements[nom] != null)
	document.formu.elements[nom].focus();
}

</SCRIPT>
<TITLE>OeOT_Lancement.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeOT_Modification" id="process" scope="session"></jsp:useBean>
<BODY background="images/fond.jpg" class="sigp2-BODY" BGPROPERTIES="FIXED" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
		<TD style="text-align : center;" align="center">
		<FORM name="formu" method="POST"><SPAN class="sigp2-titre"> <%=process.getVAL_ST_TITRE_ACTION() %>
		de l'OT <%= process.getVAL_ST_NOOT()%><BR>
<FIELDSET>
<%if(process.isCreation()){ %>
		<TABLE border="0" class="sigp2">
			<TR>
				<TD class="sigp2-titre">Equipement</TD>
				<TD></TD>
				<TD><INPUT type="text"
					name="<%=process.getNOM_EF_RECHERCHER() %>"
					value="<%=process.getVAL_EF_RECHERCHER() %>"
					class="sigp2-saisie"></TD>
				<TD><INPUT type="submit"
					name="<%= process.getNOM_PB_RECHERCHER() %>"
					value="Rechercher" class="sigp2-Bouton-100"></TD>
				<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17"
					name="<%=process.getNOM_PB_RECHERCHEREQUIP() %>"></TD>
			</TR>
		</TABLE>
<%} %>
</FIELDSET>
		</SPAN><BR>
		<FIELDSET>
		
		<TABLE border="3" bordercolor="#669999" cellpadding="2"
			cellspacing="2">
			<TBODY>
				<TR>
					<TD colspan="6" bgcolor="#669999" class="sigp2-titre">Infos
					concernant l'équipement</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
						<TBODY>
							<TR>
								<TD><B>N°Inventaire</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_NOINVENT()%></TD>
								<TD class="sigp2-saisie" width="10"></TD>
								<TD><B>N°immatriculation</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_NOIMMAT()%></TD>

							</TR>
							<TR>
								<TD>Nom d'équipement</TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_MARQUE()+" "+process.getVAL_ST_MODELE()%></TD>
								<TD class="sigp2-saisie"></TD>
								<TD>Type</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TYPE() %></TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		</FIELDSET>
<%if (!process.bErreurBe){ %>
		<TABLE border="0" class="sigp2">
			<tr>
				<td height="10" colspan="3"></td>
			</tr>
			<tr>
				<td colspan="3">
				<TABLE border="3" width="100%" bordercolor="#669999" cellpadding="2"
					cellspacing="2" align="center" width="100%">
					<TR>
						<TD colspan="3" bgcolor="#669999" class="sigp2-titre">Détails d'un
						OT</TD>
					</TR>
					<TR>
						<TD width="10"></TD>
						<TD><!--Détails du BPC-->
						<FIELDSET>
						<TABLE class="sigp2">
							<TR>
								<TD colspan="5" height="10">
								<TABLE border="0" class="sigp2" align="center">
									<TR>
										<TD>Numéro de l'OT</TD>
										<TD class="sigp2-saisie"><%= process.getVAL_ST_NOOT()%></TD>
									</TR>

								</TABLE>
								</TD>
							</TR>
							<TR>
								<TD>Date d'entrée</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase">
									<TABLE border="0" class="sigp2">
										<TR>
											<TD><INPUT type="text" size="10" class="sigp2-saisie"
											style="text-transform: uppercase"
											name="<%=process.getNOM_EF_DENTREE() %>"
											value="<%=process.getVAL_EF_DENTREE() %>"></TD>
											<TD><IMG src="images/calendrier.gif"
											onclick="return showCalendar('<%=process.getNOM_EF_DENTREE()%>', 'dd/mm/y');"></TD>
										</TR>
									</TABLE></TD>
								<TD width="10"></TD>
								<TD>Compteur (<%=process.getVAL_ST_TCOMPTEUR() %>)</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase">
									<INPUT type="text" size="10" class="sigp2-saisie"
									name="<%=process.getNOM_EF_COMPTEUR() %>"
									value="<%=process.getVAL_EF_COMPTEUR() %>"
									style="text-transform: uppercase"></TD>
							</TR>
							<TR>
								<TD>Date de sortie</TD>
								<TD class="sigp2-saisie">
									<TABLE border="0" class="sigp2">
										<TR>
											<TD><INPUT type="text" size="10"
											name="<%=process.getNOM_EF_DSORTIE() %>"
											value="<%=process.getVAL_EF_DSORTIE() %>"
											class="sigp2-saisie" style="text-transform: uppercase"></TD>
											<TD><IMG src="images/calendrier.gif"
											onclick="return showCalendar('<%=process.getNOM_EF_DSORTIE()%>', 'dd/mm/y');"></TD>
										</TR>
									</TABLE></TD>
								<TD width="10"></TD>
								<TD colspan="2"></TD>
							</TR>
							<TR>
								<TD>Commentaire</TD>
								<TD class="sigp2-saisie" colspan="4"><TEXTAREA rows="4"
									cols="50" name=<%=process.getNOM_EF_COMMENTAIRE() %> style="text-transform: uppercase" class="sigp2-saisie"><%=process.getVAL_EF_COMMENTAIRE() %></TEXTAREA></TD>
							</TR>
							<TR>
								<TD colspan="5" align="center"></TD>
							</TR>
<%if(!process.isCreation()){ %>
							<TR>
								<TD colspan="5" align="center">
								<FIELDSET>
								<TABLE class="sigp2">
									<tr>
										<td colspan="3" class="sigp2-titre-liste"><B>Les interventions
										à réaliser</B></td>
									</tr>
									<TR>
										<TD colspan="3">
										<TABLE width="100%" border="1">
											<TBODY>
												<TR>
													<TD class="sigp2-titre-liste">Entretien                      Réalisé    Durée Intervalle      </TD>
												</TR>
											</TBODY>
										</TABLE>
										</TD>
									</TR>
									<tr>
										<td colspan="3"><SELECT size="5"
											name="<%= process.getNOM_LB_INTERVENTIONS() %>"
											class="sigp2-liste"
											style="text-transform: uppercase; width: 100%">
											<%= process.forComboHTML(process.getVAL_LB_INTERVENTIONS(),process.getVAL_LB_INTERVENTIONS_SELECT()) %>
										</SELECT></td>
									</tr>
									<TR>
										<TD width="151" align="left"><INPUT type="submit"
											name="<%=process.getNOM_PB_AJOUTENT() %>" value="Ajouter"
											class="sigp2-Bouton-100"></TD>
										<TD width="151" align="left"><INPUT type="submit"
											name="<%=process.getNOM_PB_MODIFENT() %>"
											class="sigp2-Bouton-100" value="Modifier"></TD>
										<TD><INPUT type="submit"
											name="<%=process.getNOM_PB_SUPENT() %>" value="Supprimer"
											class="sigp2-Bouton-100"></TD>
									</TR>
								</TABLE>
								</FIELDSET>
								</TD>
							</TR>
							<TR>
								<TD colspan="5">
								<FIELDSET>
								<TABLE class="sigp2">
									<tr>
										<td colspan="3" class="sigp2-titre-liste"><B>Les pièces
										sorties du stock</B></td>
									</tr>
									<TR>
										<TD colspan="3">
										<TABLE width="100%" border="1">
											<TBODY>
												<TR>
													<TD class="sigp2-titre-liste">Pièces                         Sortie     P.U    Qté    Total  </TD>
												</TR>
											</TBODY>
										</TABLE>
										</TD>
									</TR>
									<tr>
										<td colspan="3"><SELECT size="5"
											name="<%= process.getNOM_LB_PIECES() %>" class="sigp2-liste"
											style="text-transform: uppercase; width: 100%">
											<%= process.forComboHTML(process.getVAL_LB_PIECES(),process.getVAL_LB_PIECES_SELECT()) %>
										</SELECT></td>
									</tr>
									<TR>
										<TD width="151" align="left"><INPUT type="submit"
											name="<%=process.getNOM_PB_AJOUTPIECE() %>" value="Ajouter"
											class="sigp2-Bouton-100"></TD>
										<TD width="151" align="left"><INPUT type="submit"
											name="<%=process.getNOM_PB_MODIFPIECE() %>"
											class="sigp2-Bouton-100" value="Modifier"></TD>
										<TD><INPUT type="submit"
											name="<%=process.getNOM_PB_SUPPIECE() %>" value="Supprimer"
											class="sigp2-Bouton-100"></TD>
									</TR>
								</TABLE>
								</FIELDSET>
								</TD>
							</TR>
							<TR>
								<TD colspan="5">
								<FIELDSET>
								<TABLE class="sigp2">
									<tr>
										<td colspan="3" class="sigp2-titre-liste"><B>Les bons
										d'engagement</B></td>
									</tr>
									<TR>
										<TD class="sigp2-titre-liste" colspan="3">

										<TABLE width="100%" border="1">
											<TBODY>
												<TR>
													<TD class="sigp2-titre-liste">N°engagemt Code       Fournisseur                   Montant    </TD>
												</TR>
											</TBODY>
										</TABLE>
										</TD>
									</TR>
									<TR>
										<TD class="sigp2-titre-liste" colspan="3"><SELECT size="5"
											name="<%= process.getNOM_LB_FRE() %>" class="sigp2-liste"
											style="text-transform: uppercase;width: 100%">
											<%= process.forComboHTML(process.getVAL_LB_FRE(),process.getVAL_LB_FRE_SELECT()) %>
										</SELECT></TD>
									</TR>
									<TR>
										<TD colspan="3">
										<table>
											<tr>
											<td width="151" align="left"><INPUT type="submit"
													name="<%=process.getNOM_PB_AJOUTFRE() %>" value="Ajouter"
													class="sigp2-Bouton-100"></td>
											<td width="151"></td>
											<td><INPUT type="submit"
													name="<%=process.getNOM_PB_SUPPRIMERFRE() %>"
													value="Supprimer" class="sigp2-Bouton-100"></td>
										</tr>
										</table></TD>
									</TR>
								</TABLE>
								</FIELDSET>
								</TD>
							</TR>
							<TR>
								<TD colspan="5" align="center">
								<FIELDSET>
								<TABLE class="sigp2" width="100%">
									<tr>
										<td colspan="3" class="sigp2-titre-liste"><B>Les intervenants</B></td>
									</tr>
									<TR>
										<TD class="sigp2-titre-liste" colspan="3" width="100%">
										<TABLE border="1" width="100%">
											<TBODY>
												<TR>
													<TD class="sigp2-titre-liste">Nom                 Prénom              </TD>
												</TR>
											</TBODY>
										</TABLE>
										</TD>
									</TR>
									<TR>
										<TD colspan="3"><SELECT size="5"
											name="<%= process.getNOM_LB_INTERVENANTS() %>"
											class="sigp2-liste"
											style="text-transform: uppercase;width: 100%">
											<%= process.forComboHTML(process.getVAL_LB_INTERVENANTS(),process.getVAL_LB_INTERVENANTS_SELECT()) %>
										</SELECT></TD>
									</TR>
									<tr>
										<td colspan="3" width="235">
											<table>
												<tr>
													<td width="151">
													<INPUT type="submit"
													name="<%=process.getNOM_PB_AJOUTMECA() %>" value="Ajouter"
													class="sigp2-Bouton-100"></td>
												<TD width="151"></TD>
												<td><INPUT type="submit"
													name="<%=process.getNOM_PB_SUPMECA() %>" value="Supprimer"
													class="sigp2-Bouton-100"></td>
												</tr>
											</table></TD>
									</TR>
								</TABLE>
								</FIELDSET>
								</TD>
							</TR>
						</TABLE>
<%} %></FIELDSET></TD>
						<TD width="10"></TD>
					</TR>
				</TABLE>
				</td>
			</tr>
		</table>
		<TABLE align="center">
			<tr>
				<td width="151" align="left">
					<INPUT type="submit" name="<%=process.getNOM_PB_VALIDER() %>"
			value="Valider" class="sigp2-Bouton-100">
				</td>
				<td width="151">
					 <INPUT type="submit" name="<%=process.getNOM_PB_ANNULER() %>"
			value="Annuler" class="sigp2-Bouton-100">
				</td>
				<%if(process.isDebrancheDec){ %><TD>
		<INPUT type="submit" value="Déclarations"
			name="<%=process.getNOM_PB_DECLARATIONS() %>" class="sigp2-Bouton-100"><br>
</TD><%} %>
			</tr>
		</TABLE>
<%}else{ %>
	<TABLE>
		<tr>
			<td><INPUT type="submit" name="<%=process.getNOM_PB_OK_SUP_BE() %>"
			value="Retirer" class="sigp2-Bouton-100"></td>
			
		</tr>
	</TABLE>
<%} %>
		<br>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>">
		</FORM>
		</TD>
	</TR>
</TABLE>
</BODY>
</HTML>
