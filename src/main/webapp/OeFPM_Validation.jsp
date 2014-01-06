<%@page contentType="text/html;charset=UTF-8"%>
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
<TITLE>OeOT_Validation.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeFPM_Validation" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;" align="center">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre"></SPAN>
<%if (!process.estEnregistré){ %>		
<FIELDSET><LEGEND class="sigp2Fieldset" align="left">Fiches d'entretiens non validé</LEGEND><TABLE border="0" class="sigp2">
				<TR align="center">
					<TD align="left" height="35">
				<TABLE class="sigp2" border="0" cellspacing="1">
					<TR>
						<TD class="sigp2-titre-liste">
						<TABLE class="sigp2" border="1">
							<TR>
								<TD class="sigp2-titre-liste">N°Fiche    Inv.  Série.               Entrée     Sortie      </TD>
								<!-- <TD height="29" width="87">Service d'affectation</TD>-->
							</TR>
						</TABLE></TD>

					</TR>
				</TABLE>
				</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="8" name="<%= process.getNOM_LB_FPMENCOURS() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%"
					ondblclick='executeBouton("<%= process.getNOM_PB_AJOUTER()%>")'>
					<%= process.forComboHTML(process.getVAL_LB_FPMENCOURS(),process.getVAL_LB_FPMENCOURS_SELECT()) %>
				</SELECT>
				</TR>
<%if (!process.isListFpmEncoursVide()){ %>
				<tr>
					<td align="center">
<table class="sigp2" border="0" cellspacing="0">
	<tr>
		<td width="151"><INPUT type="submit" value="Ajouter"
					name="<%=process.getNOM_PB_AJOUTER() %>"
					class="sigp2-Bouton-100"></td>
		<td width="151"><INPUT type="submit" value="Modifier"
					name="<%=process.getNOM_PB_MODIFIER() %>"
					class="sigp2-Bouton-100"></td>
		<td><INPUT type="submit" value="Visualiser"
					name="<%=process.getNOM_PB_VISUALISER() %>"
					class="sigp2-Bouton-100"></td>
	</tr>
</table>
						
					</td>
				</tr>
			</TABLE>
				
			</FIELDSET><br>
<FIELDSET><LEGEND class="sigp2Fieldset" align="left">Fiches d'entretiens à valider</LEGEND><TABLE border="0" class="sigp2">
				<TR align="center">
					<TD align="left" height="35">
				<TABLE class="sigp2" border="1">
					<TR>
						<TD class="sigp2-titre-liste">N°Fiche     Inv.  Série.               Entrée     Sortie      </TD>
						<!-- <TD height="29" width="87">Service d'affectation</TD>-->
					</TR>
				</TABLE>
				</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="4" name="<%= process.getNOM_LB_FPMAVALIDER() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%"
					ondblclick='executeBouton("<%= process.getNOM_PB_ENLEVER()%>")'>
					<%= process.forComboHTML(process.getVAL_LB_FPMAVALIDER(),process.getVAL_LB_FPMAVALIDER_SELECT()) %>
				</SELECT>
				</TR>
<%if(process.isAValider()){ %>
			<TR>
				<TD align="center"><INPUT type="submit" value="Enlever"
					name="<%=process.getNOM_PB_ENLEVER() %>"
					class="sigp2-Bouton-100"> </TD>
			</TR>
		</TABLE>
			</FIELDSET>
			<FIELDSET>
				<table border="0" class="sigp2">
					<tr>
						<td><INPUT
					type="submit" name="<%= process.getNOM_PB_VALIDER() %>"
					value="Valider" class="sigp2-Bouton-100"></td>
					</tr>
<%} %>
<%} %>
				</table>
			</FIELDSET>
<%}else{ %>
			<FIELDSET><LEGEND class="sigp2Fieldset">La validation des OT suivants a bien été effectuée</LEGEND>
		<TABLE border="0" class="sigp2">
			<TR align="center">
				<TD align="left" height="35">
				<TABLE class="sigp2" border="1">
					<TR>
						<TD height="29" class="sigp2-titre-liste">N°OT      Inv.  Immat.     Entrée     Sortie     Compteur   </TD>
						<!-- <TD height="29" width="87">Service d'affectation</TD>-->
					</TR>
				</TABLE>
				</TD>
			</TR>
			<TR>
				<TD align="left"><SELECT size="4"
					name="<%= process.getNOM_LB_FPMAVALIDER() %>" class="sigp2-liste"
					style="text-transform: uppercase; width: 100%">
					<%= process.forComboHTML(process.getVAL_LB_FPMAVALIDER(),process.getVAL_LB_FPMAVALIDER_SELECT()) %>
				</SELECT></TD>
			</TR>
			<tr>
				<td align="center"><INPUT type="submit" value="OK"
					name="<%=process.getNOM_PB_OK_VALIDATION() %>" class="sigp2-Bouton-100"></td>
			</tr>
		</TABLE>
			</FIELDSET>
<%} %>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
