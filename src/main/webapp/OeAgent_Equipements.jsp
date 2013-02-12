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
<TITLE>OeDeclaration_VisualisationEquip.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeAgent_Equipements" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><FIELDSET>
<%if((null==process.origine)||("".equals(process.origine))){ %>
						<TABLE border="0" class="sigp2">
			<TR>
				<TD class="sigp2-titre">Agent</TD>
				<TD></TD>
				<TD><INPUT size="1" type="text" class="sigp2-saisie" maxlength="1" name="ZoneTampon" style="display : 'none';">
				<INPUT type="text" name="<%=process.getNOM_EF_AGENT() %>"
					value="<%=process.getVAL_EF_AGENT() %>" class="sigp2-saisie"
					size="10"></TD>
				<TD></TD>
				<TD><INPUT type="submit" name="<%= process.getNOM_PB_AGENT() %>"
					value="Rechercher" class="sigp2-Bouton-100"></TD>
			</TR>
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
								<TD><B>Agent</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_AGENT()%></TD>
								
							</TR>
							<TR>
								<TD><B>Service</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_CODE_SCE()%>&nbsp;<%= process.getVAL_ST_LIBELLE_SCE()%></TD>
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
							<TD class="sigp2-titre-liste">Inv   Immat      Nom                       Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Début     &nbsp;</TD>
						</TR>
					</TBODY>
				</TABLE>
				</td>
					</tr>
					<tr>
						<td colspan="3">
				<SELECT size="10" name="<%= process.getNOM_LB_EQUIP() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%" ondblclick='executeBouton("<%= process.getNOM_PB_VISUALISER()%>")'>

					<%= process.forComboHTML(process.getVAL_LB_EQUIP(),process.getVAL_LB_EQUIP_SELECT()) %>

				</SELECT></td>
		</tr>
		 <tr>
			<td colspan="3" align="center" height="10"></td>
		</tr>
			<tr>
				<td colspan="3" align="center">
<table>
	<tr>
		<td><%if (process.isVide) {%><INPUT type="submit" value="Visualiser"
					name="<%=process.getNOM_PB_VISUALISER() %>" class="sigp2-Bouton-100"><%} %></td>
						<TD width="10"></TD>
						<td><%if (process.isDebranche) {%><INPUT type="submit"
					value="Retour" name="<%=process.getNOM_PB_RETOUR() %>" class="sigp2-Bouton-100"><%} %></td>
	</tr>
</table>

</td>
			</tr>
		</table><INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
