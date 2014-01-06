<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>

<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="theme/sigp2.css" rel="stylesheet" type="text/css">
<link href="theme/calendrier-mairie.css" rel="stylesheet"
	type="text/css">
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
<TITLE>OePM_Affectation_Agent.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OePM_Affectation_Agent"
	id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg"
	class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
	<%@ include file="BanniereErreur.jsp"%>
	<TR>
		<TD style="text-align : center;">
		<FORM name="formu" method="POST">
		<FIELDSET>
		<%
		if (!process.isAction) {
		%>
		<TABLE border="0" class="sigp2">
			<TR>
				<TD class="sigp2-titre">Petit Matériel</TD>
				<TD class="sigp2-titre"></TD>
				<TD class="sigp2-titre"><INPUT size="1" type="text"
					class="sigp2-saisie" maxlength="1" name="ZoneTampon"
					style="display : none;"> <INPUT type="text"
					name="<%=process.getNOM_EF_EQUIP() %>"
					value="<%=process.getVAL_EF_EQUIP() %>" class="sigp2-saisie"
					size="10"></TD>
				<TD class="sigp2-titre"><INPUT type="submit"
					name="<%= process.getNOM_PB_EQUIP() %>" value="Rechercher"
					class="sigp2-Bouton-100"></TD>
				<TD><input type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" name="<%=process.getNOM_PB_RECHERCHE() %>"></td>
			</TR>
		</TABLE>
		<%
		}
		%>
		</FIELDSET>

		<FIELDSET><LEGEND class="sigp2Fieldset" align="left"></LEGEND>
		<TABLE border="3" bordercolor="#669999" cellpadding="2"
			cellspacing="2">
			<TBODY>
				<TR>
					<TD colspan="6" bgcolor="#669999" class="sigp2-titre">Infos
					concernant le petit matériel</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
						<TBODY>
							<TR>
								<TD><B>N° inventaire </B></TD>
								<TD width="10"></TD>
								<TD width="10" class="sigp2-saisiemajuscule"
									style="text-transform: uppercase"><%=process.getVAL_ST_NOINVENT()%></TD>
								<TD width="10" class="sigp2-saisiemajuscule"></TD>
								<TD><B>N°de série</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule"
									style="text-transform: uppercase"><%=process.getVAL_ST_NOSERIE()%></TD>

							</TR>
							<TR>
								<TD>Nom d'équipement</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_NOMEQUIP()%></TD>
								<TD class="sigp2-saisie"></TD>
								<TD>Type</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TYPE()%></TD>
							</TR>
							<TR>
								<TD colspan="7" height="15"></TD>
							</TR>
							<TR>
								<TD>Service</TD>
								<TD></TD>
								<TD class="sigp2-saisie" colspan="5"
									style="text-transform: uppercase"><%=process.getVAL_ST_SERVICE()%></TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<%
		if ("".equals(process.getVAL_ST_TITRE_ACTION())) {
		%><INPUT
			type="submit" name="<%= process.getNOM_PB_TRI() %>"
			style="visibility : hidden;" value="OK" class="sigp2-Bouton-100"><BR>
		<TABLE border="1" class="sigp2">
			<tr>
				<td align="center">
				<TABLE class="sigp2" border="0">
					<tr>
						<td>
						<TABLE border="1">
							<TBODY>
								<TR>
									<TD class="sigp2-titre-liste">
									<TABLE>
										<TR>
											<TD height="29" width="28">
											<TABLE border="0" class="sigp2">
												<TR>
													<TD class="sigp2-titre-liste">Sce</TD>
												</TR>
											</TABLE>
											</TD>
											<TD height="29" width="279">
											<TABLE border="0" class="sigp2">
												<TR>
													<TD><INPUT type="radio"
														<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_EMPLOYE(),process.getVAL_RG_TRI()) %>
														onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'
														style="visibility : hidden;"></TD>
													<TD class="sigp2-titre-liste" width="251">Employé</TD>
												</TR>
											</TABLE>
											</TD>
											<TD height="29">
											<TABLE border="0" class="sigp2" class="sigp2-titre-liste">
												<TR>
													<TD><INPUT type="radio"
														<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_DEBUT(),process.getVAL_RG_TRI()) %>
														onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'></TD>
													<TD class="sigp2-titre-liste" width="86">Début</TD>
												</TR>
											</TABLE>
											</TD>
											<TD height="29" width="135">
											<TABLE border="0" class="sigp2">
												<TR>
													<TD><INPUT type="radio"
														<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_FIN(),process.getVAL_RG_TRI()) %>
														onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'></TD>
													<TD class="sigp2-titre-liste">Fin</TD>
												</TR>
											</TABLE>
											</TD>
										</TR>
									</TABLE>
									</TD>
								</TR>
							</TBODY>
						</TABLE>

						<!-- 	<TR align="center">
					<TD align="left"><TABLE width="100%" border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">Sce  Employé                                  Début            Fin               </TD>
						</TR>
					</TBODY>
				</TABLE>

</TD>
				</TR>-->
				</td></tr>
					<TR>
						<TD align="left"><SELECT size="12"
							name="<%= process.getNOM_LB_AFFECTATION() %>" class="sigp2-liste"
							style="width: 100%">
							<%=process.forComboHTML(process.getVAL_LB_AFFECTATION(),
								process.getVAL_LB_AFFECTATION_SELECT())%>
						</SELECT></TD>
					</TR>
				</TABLE>
				<INPUT type="submit" value="OK" name="<%=process.getNOM_PB_OK() %>"
					style="visibility : hidden;">
				<%
				if (null != process.getServiceCourant()) {
				%>
				<TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2"
					align="center">
					<TR>
						<%
						if (!process.isManqueParam()) {
						%>
						<%
						if (process.isAffecter) {
						%>
						<TD width="151" align="center"><INPUT type="submit"
							name="<%=process.getNOM_PB_AJOUTER() %>" value="Affecter"
							class="sigp2-Bouton-100"></TD>
						<%
						}
						%>
						<%
						}
						%>
						<%
						if (process.getIsVide() > 0) {
						%>
						<TD width="151"><INPUT type="submit"
							name="<%= process.getNOM_PB_MODIFIER() %>" value="Modifier"
							class="sigp2-Bouton-100"></TD>
						<TD width="49"><INPUT type="submit"
							name="<%= process.getNOM_PB_SUPPRIMER() %>" value="Supprimer"
							class="sigp2-Bouton-100"></TD>
						<%
						}
						%>
					</TR>
				</TABLE>
				<%
				}
				%>
				<%
				if (process.isDebranche) {
				%><INPUT type="submit"
					name="<%=process.getNOM_PB_RETOUR() %>" value="Retour"
					class="sigp2-Bouton-100">
				<%
				}
				%> <br>
				<%
				} else {
				%> <!-- </FIELDSET><BR>
	<FIELDSET>-->
				<TABLE border="0" class="sigp2" width="90%">
					<tr>
						<td colspan="3" align="center" class="sigp2-titre"><%=process.getVAL_ST_TITRE_ACTION()%></td>
					</tr>
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
						<TABLE border="3" bordercolor="#669999" cellpadding="2"
							cellspacing="2" align="center">
							<TR>
								<TD colspan="3" bgcolor="#669999" class="sigp2-titre">Affectation
								d'un petit matériel</TD>
							</TR>
							<TR>
								<TD width="10"></TD>
								<TD><!--Détails du BPC-->
								<FIELDSET>
								<%
										if (null == process.getVAL_ST_DATEDEB()
										|| "".equals(process.getVAL_ST_DATEDEB())) {
								%>
								<TABLE class="sigp2">
									<TR>
										<TD colspan="7" height="10"></TD>
									</TR>
									<TR>
										<TD>Agent</TD>
										<TD colspan="6"><SELECT size="1"
											name="<%=process.getNOM_LB_AGENT() %>" class="sigp2-liste">
											<%=process.forComboHTML(process.getVAL_LB_AGENT(),
									process.getVAL_LB_AGENT_SELECT())%>
										</SELECT></TD>
									</TR>
									<TR>
										<TD>Date de début</TD>
										<TD><INPUT type="text" size="10"
											name="<%=process.getNOM_EF_DATE() %>"
											value="<%=process.getVAL_EF_DATE() %>" class="sigp2-saisie"></TD>
										<TD class="sigp2-saisie" width="25"><IMG
											src="images/calendrier.gif"
											onclick="return showCalendar('<%=process.getNOM_EF_DATE()%>', 'dd/mm/y');"></TD>
										<TD>Heure de début</TD>
										<TD class="sigp2-saisie"><INPUT type="text" size="2"
											name="<%=process.getNOM_EF_HDEB() %>"
											value="<%=process.getVAL_EF_HDEB() %>" class="sigp2-saisie"></TD>
										<TD class="sigp2-saisie">H</TD>
										<TD class="sigp2-saisie"><INPUT type="text" size="2"
											name="<%=process.getNOM_EF_HDEBMN() %>"
											value="<%=process.getVAL_EF_HDEBMN() %>" class="sigp2-saisie"></TD>
									</TR>
									<TR>
										<TD>Date de fin</TD>
										<TD><INPUT type="text" size="10"
											name="<%=process.getNOM_EF_DATEFIN() %>"
											value="<%=process.getVAL_EF_DATEFIN() %>"
											class="sigp2-saisie"></TD>
										<TD class="sigp2-saisie" width="25"><IMG
											src="images/calendrier.gif"
											onclick="return showCalendar('<%=process.getNOM_EF_DATEFIN()%>', 'dd/mm/y');"></TD>
										<TD>Heure de fin</TD>
										<TD class="sigp2-saisie"><INPUT type="text" size="2"
											name="<%=process.getNOM_EF_HFIN() %>"
											value="<%=process.getVAL_EF_HFIN() %>" class="sigp2-saisie"></TD>
										<TD class="sigp2-saisie">H</TD>
										<TD class="sigp2-saisie"><INPUT type="text" size="2"
											name="<%=process.getNOM_EF_HFINMN() %>"
											value="<%=process.getVAL_EF_HFINMN() %>" class="sigp2-saisie"></TD>
									</TR>
								</TABLE>
								<%
								} else {
								%>
								<table border="0" class="sigp2">
									<tr>
										<td>Employé</td>
										<td></td>
										<td align="center">Début</td>
										<td></td>
										<td>Heure de début</td>
										<td></td>
										<td align="center">Fin</td>
										<td></td>
										<td>Heure de fin</td>
									</tr>
									<tr class="sigp2-saisie">
										<td><%=process.getVAL_ST_AGENT()%></td>
										<td></td>
										<td><%=process.getVAL_ST_DATEDEB()%></td>
										<td></td>
										<td align="center"><%=process.getVAL_ST_HEUREDEB()%></td>
										<td></td>
										<td><%=process.getVAL_ST_DATEFIN()%></td>
										<td></td>
										<td align="center"><%=process.getVAL_ST_HEUREFIN()%></td>
									</tr>
								</table>
								<%
								}
								%>
								</FIELDSET>
								</TD>
								<TD width="10"></TD>
							</TR>
						</TABLE>
						<br>
						<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2"
							align="center">
							<TR align="center">
								<TD width="151" align="left"><INPUT type="submit"
									name="<%= process.getNOM_PB_VALIDER() %>" value="Valider"
									class="sigp2-Bouton-100"></TD>
								<TD width="137" align="left"><INPUT type="submit"
									name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler"
									class="sigp2-Bouton-100"></TD>
							</TR>
						</TABLE>
						</td>
					</tr>
				</table>
				<%}	%> 
				<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>">
				</TD>
			</TR>
		</TABLE>
		</FIELDSET>
		</FORM>
	</TD>
	</TR>
	</TABLE>
		
</BODY>
</HTML>
