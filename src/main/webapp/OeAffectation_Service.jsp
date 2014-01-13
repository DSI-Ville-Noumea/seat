<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>

<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="theme/sigp2.css" rel="stylesheet" type="text/css">
<link href="theme/calendrier-mairie.css" rel="stylesheet" type="text/css">
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
<TITLE>OAffectation_Service.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeAffectation_Service" id="process" scope="session"></jsp:useBean>
<BODY  background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST">
		<FIELDSET>
<%if(!process.isAction){ %>
			<TABLE border="0" class="sigp2">
			<TR>
				<TD class="sigp2-titre">Equipement </TD>
				<TD class="sigp2-titre"></TD>
				<TD class="sigp2-titre"><INPUT type="text"
					name="<%=process.getNOM_EF_EQUIP() %>"
					value="<%=process.getVAL_EF_EQUIP() %>" class="sigp2-saisie"
					size="10"></TD>
				<TD class="sigp2-titre"><INPUT type="submit"
					name="<%= process.getNOM_PB_EQUIP() %>" value="Rechercher"
					class="sigp2-Bouton-100"></TD>
				<TD><input type="image" border="0" src="images/jumelle.gif" width="21" height="17" name="<%=process.getNOM_PB_RECHERCHE() %>"></td>
			</TR>
		</TABLE>
<%} %>
		</FIELDSET>

		<FIELDSET><LEGEND class="sigp2Fieldset" align="left"></LEGEND><TABLE border="3" bordercolor="#669999" cellpadding="2"
			cellspacing="2">
			<TBODY>
				<TR>
					<TD colspan="6" bgcolor="#669999" class="sigp2-titre">Infos concernant l'équipement</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
						<TBODY>
							<TR>
								<TD><B>N° inventaire </B></TD>
								<TD width="10"></TD>
								<TD width="10" class="sigp2-saisiemajuscule"
									style="text-transform: uppercase"><%=process.getVAL_ST_NOINVENT() %></TD>
								<TD></TD>
								<TD width="10"></TD>
								<TD><B>N°immatriculation</B> </TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule"
									style="text-transform: uppercase"><%=process.getVAL_ST_NOIMMAT() %></TD>

							</TR>
							<TR>
								<TD>Nom d'équipement</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_NOMEQUIP()%></TD>
								<TD class="sigp2-saisie"></TD>
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
		<%if ( "".equals(process.getVAL_ST_TITRE_ACTION()) ) { %><INPUT
			type="submit" name="<%= process.getNOM_PB_RESPONSABLE() %>"
			value="Responsable" style="visibility : hidden;"
			class="sigp2-Bouton-100"><BR>
		<TABLE border="1" class="sigp2">
				<TR align="center">
					<TD align="left"><TABLE width="100%" border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">Service                                                      Début      Fin        </TD>
						</TR>
					</TBODY>
				</TABLE>

</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="10" onclick='executeBouton("<%=process.getNOM_PB_RESPONSABLE() %>")' name="<%= process.getNOM_LB_AFFECTATION() %>" class="sigp2-liste" style="width: 100%">
							<%= process.forComboHTML(process.getVAL_LB_AFFECTATION(),process.getVAL_LB_AFFECTATION_SELECT()) %>
						</SELECT>
					</TD>
				</TR>
			</TABLE>
		<TABLE class="sigp2" width="100%">
			<TR>
				<TD width="200">Responsable :</TD>
				<TD align="left" class="sigp2-saisie"
					style="text-transform: uppercase; font-weight: bold"><%=process.getVAL_ST_AGENT() %></TD>
			</TR>
		</TABLE>
		<%if (!"".equals(process.getVAL_ST_NOINVENT())) {%>
			<TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<TD width="151"><INPUT type="submit" name="<%=process.getNOM_PB_AJOUTER() %>" value="Affecter" class="sigp2-Bouton-100"><INPUT
					type="submit" value="OK" name="<%=process.getNOM_PB_OK() %>"
					style="visibility : hidden;"></TD>
<%if (process.getIsVide()>0){ %>
					<TD width="151"><INPUT type="submit" name="<%= process.getNOM_PB_MODIFIER() %>" value="Modifier" class="sigp2-Bouton-100"></TD>
					<TD width="49"><INPUT type="submit" name="<%= process.getNOM_PB_SUPPRIMER() %>" value="Supprimer" class="sigp2-Bouton-100"></TD>
<%} %>
				</TR>
			</TABLE>
			<%} %>
			<br>
		<%}else{%>
	</FIELDSET><BR>
<FIELDSET>
				<TABLE border="0" class="sigp2" width="90%">
					<tr>
						<td colspan="3" align="center" class="sigp2-titre"><%= process.getVAL_ST_TITRE_ACTION() %></td>
					</tr>
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
							<TABLE border="3" bordercolor="#669999" cellpadding="2"
								cellspacing="2" align="center">
								<TR>
									<TD colspan="3" bgcolor="#669999" class="sigp2-titre">Affectation d'un équipement</TD>
								</TR>
								<TR>
									<TD width="10"></TD>
									<TD><!--Détails du BPC-->
									<INPUT type="submit" value="OK"
							name="<%=process.getNOM_PB_AFFICH_AGENT() %>"
							style="visibility : hidden;">
						<INPUT type="submit" value="OK"
							name="<%=process.getNOM_PB_OK_SCE() %>"
							style="visibility : hidden;">
						<INPUT type="submit" value="OK"
							name="<%=process.getNOM_PB_OK_AGENT() %>"
							style="visibility : hidden;">
						<FIELDSET>
<%if ("".equals(process.getVAL_ST_DATE())){ %>
							<table border="0" class="sigp2">
								<tr>
									<td>Service</td>
									<td><INPUT type="image" border="0" src="images/loupe.gif"
									name="<%=process.getNOM_PB_SELECTIONNER() %>"></td>
									<td class="sigp2-saisie"><%= process.getVAL_ST_SERVICE() %></td>
									<td></td>
								</tr>
								<tr>
									<td>Début d'affectation</td>
									<td><IMG src="images/calendrier.gif"
									onclick="return showCalendar('<%=process.getNOM_EF_DATE()%>', 'dd/mm/y');"></td>
									<td><INPUT type="text" name="<%=process.getNOM_EF_DATE() %>"
									value="<%=process.getVAL_EF_DATE() %>" class="sigp2-saisie"
									maxlength="10" size="10"></td>
									<td></td>
								</tr>
								<%if (!"".equals(process.getVAL_ST_SERVICE())){ %>
							<TR>
								<TD>Agent responsable</TD>
								<TD></TD>
								<TD>
								<SELECT size="1" name="<%=process.getNOM_LB_AGENT() %>"
									class="sigp2-liste">
									<%= process.forComboHTML(process.getVAL_LB_AGENT(),process.getVAL_LB_AGENT_SELECT()) %>
								</SELECT></TD>
								<TD></TD>
								<TD>Sans responsable</TD>
								<TD><INPUT type="checkbox"<%= process.forCheckBoxHTML(process.getNOM_CK_AGENT() , process.getVAL_CK_AGENT()) %>></TD>
								<TD></TD>
							</TR>
							<%} %>
						</table>
<%}else{ %>
								<table border="0" class="sigp2">
								<tr>
									<td>Service</td>
									<td></td>
									<td class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_SERVICE() %></td>
									<td></td>
								<TD>Agent responsable</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_AGENT() %></TD>
							</tr>
								<tr>
									<td>Début d'affectation</td>
									<td></td>
									<td class="sigp2-saisie"><%= process.getVAL_ST_DATE() %></td>
									<td></td>
								<TD>Fin d'affectation</TD>
								<TD></TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_DATEFIN() %></TD>
							</tr>
						</table>
<%} %>
									</FIELDSET></TD>
									<TD width="10"></TD>
								</TR>
							</TABLE>

				<br>
							<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
								<TR align="center">
									<TD width="140"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
									<TD width="137"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
								</TR>
							</TABLE>
						</td>
					</tr>
				</table>
				<%} %><br>
	</FIELDSET>

		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
