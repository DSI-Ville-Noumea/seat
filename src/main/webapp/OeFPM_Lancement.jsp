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
<TITLE>OeOT_Lancement.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeFPM_Lancement" id="process" scope="session"></jsp:useBean>
<BODY background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre">Les  fiches d'entretiens: impression<INPUT
			type="submit" value="OK" name="<%=process.getNOM_PB_OK_FPM() %>"
			style="visibility : hidden;"><BR></SPAN>
		<FIELDSET><LEGEND class="sigp2Fieldset" align="left"></LEGEND><TABLE border="0" class="sigp2">
				<TR align="center">
					<TD align="left" height="35">
				<TABLE class="sigp2" border="0" cellspacing="1">
					<TR>
						<TD class="sigp2-titre-liste">
						<TABLE class="sigp2" border="1">
							<TR>
								<TD height="29" class="sigp2-titre-liste">N°Fiche       Immat.     Entretien                                 </TD>
								<!-- <TD height="29" width="87">Service d'affectation</TD>-->
							</TR>
						</TABLE></TD>

					</TR>
				</TABLE>
				</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="8" name="<%= process.getNOM_LB_ENTRETIENS() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%"
					ondblclick='executeBouton("<%= process.getNOM_PB_VALIDER()%>")'>
					<%= process.forComboHTML(process.getVAL_LB_Fiche(),process.getVAL_LB_Fiche_SELECT()) %>
				</SELECT>
				</TR>
			</TABLE>
				
			</FIELDSET>
			<TABLE class="sigp2" border="0">
				<TR>
				<TD colspan="8" align="center"></TD>
			</TR>
				<TR>
					<TD><INPUT type="submit"
					name="<%= process.getNOM_PB_DETAILS_FPM() %>"
					value="Détails de la fiche" class="sigp2-Bouton-100"></TD>
				<TD></TD>
				<TD><INPUT type="submit"
					name="<%= process.getNOM_PB_MODIFIER_FPM() %>"
					value="Modifier la fiche" class="sigp2-Bouton-100"></TD>
				<TD></TD>
				<TD><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>"
					value="Annuler" class="sigp2-Bouton-100"></TD>
				<TD></TD>
				<TD><INPUT type="submit" name="<%= process.getNOM_PB_RETOUR() %>"
					value="Retour" class="sigp2-Bouton-100"></TD>
				<TD></TD>
			</TR>
			<TR>
				<TD colspan="4" align="center"><INPUT type="submit"
					name="<%= process.getNOM_PB_VALIDER() %>" value="Impression"
					class="sigp2-Bouton-100"></TD>
				<TD colspan="4" align="center"><INPUT type="submit"
					name="<%= process.getNOM_PB_OK_FPM() %>"
					value="Plan d'Entretien personnalisé" class="sigp2-Bouton-200"></TD>
			</TR>
		</TABLE><INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
