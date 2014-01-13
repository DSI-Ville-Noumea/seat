<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>

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
<TITLE>OeDeclaration_VisualisationEquip.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeService_Equipements" id="process" scope="session"></jsp:useBean>
<BODY background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><FIELDSET>
<%if(!process.menuService){ %>
						<TABLE border="0" class="sigp2">
							<tr>
								<td class="sigp2-titre">Service </td>
								<td></td>
				<TD><INPUT type="text" name="<%=process.getNOM_EF_SERVICE() %>"
					value="<%=process.getVAL_EF_SERVICE() %>" class="sigp2-saisie" size="10"></TD>
				<TD><INPUT type="submit" name="<%= process.getNOM_PB_SERVICE() %>"
					value="Rechercher" class="sigp2-Bouton-100"></TD>
				<td><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" name="<%=process.getNOM_PB_SCE_RECHERCHE() %>"
					align="middle"></td>
							</tr>
						</TABLE>
<%} %>
<TABLE border="3" bordercolor="#669999" cellpadding="2"
			cellspacing="2">
			<TBODY>
				<TR>
					<TD colspan="6" bgcolor="#669999" class="sigp2-titre">Infos
					concernant le service</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
						<TBODY>
							<TR>
								<TD><B>Code</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_CODE_SCE()%></TD>
								<TD class="sigp2-saisie" width="10"></TD>
								<TD><B>Libellé</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_LIBELLE_SCE()%></TD>

							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		</FIELDSET>
				<TABLE border="0" class="sigp2">
					<tr>
						<td colspan="3" align="center"></td>
					</tr>
					<tr>
						<td height="10" colspan="3">
				<TABLE border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">Inv   Immat      Nom                       Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Début     &nbsp; </TD>
						</TR>
					</TBODY>
				</TABLE>
				</td>
					</tr>
					<tr>
						<td colspan="3">
				<SELECT size="10" name="<%= process.getNOM_LB_EQUIP() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%" onclick='executeBouton("<%= process.getNOM_PB_RESPONSABLE()%>")'>

					<%= process.forComboHTML(process.getVAL_LB_EQUIP(),process.getVAL_LB_EQUIP_SELECT()) %>

				</SELECT></td>
		</tr>
		 <tr>
			<td colspan="3" align="center" height="10"></td>
		</tr>
			<TR>
				<TD align="left">Responsable : </td>
	<td colspan="2" align="left" class="sigp2-saisie"
					style="text-transform: uppercase; font-weight: bold"><%=process.getVAL_ST_RESPONSABLE() %></TD>
			</TR>
<%if((process.getListEquip()!=null)&&(process.getListEquip().size()>0)){ %>
			<TR>
				<TD align="center" colspan="3"><INPUT type="submit"
					value="Affectation" name="<%=process.getNOM_PB_AFFECTATION() %>"
					style="sigp2-Bouton-100"></TD>
			</TR>
<%} %>
			<tr>
				<td colspan="3"><INPUT type="submit" value="Responsable"
					name="<%=process.getNOM_PB_RESPONSABLE() %>"
					style="visibility : hidden;"></td>
			</tr> 
<tr>
	<td align="right"><INPUT type="submit" value="Visualiser"
					name="<%=process.getNOM_PB_VISUALISER() %>"
					style="visibility : hidden;"></td>
	<td width="10"></td>
	<td><INPUT type="submit" value="Tableau de bord"
					name="<%=process.getNOM_PB_TDB() %>"
					style="visibility : hidden;"></td>
</tr>
		</table><INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
