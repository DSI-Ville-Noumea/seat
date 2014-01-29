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
<script language="JavaScript1.2" src="js/masks.js"></script>
<jsp:useBean class="nc.mairie.seat.process.OeBPC_ajout" id="process" scope="session"></jsp:useBean>

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
					
<TITLE>OeBPC_ajout.jsp</TITLE>
</HEAD>
<BODY background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre">
<!-- 			<INPUT type="submit" value="OK immat" -->
<%-- 			name="<%=process.getNOM_PB_OK_IMMAT() %>" --%>
<!-- 			><INPUT type="submit" value="OK invent" -->
<%-- 			name="<%=process.getNOM_PB_OK_INVENT() %>" --%>
<!-- 			style="visibility : hidden;"> -->
			</SPAN>
		<TABLE border="0" class="sigp2">
			<TR>
				<TD class="sigp2-titre">Equipement</TD>
				<TD class="sigp2-titre"></TD>
				<TD class="sigp2-titre"><INPUT type="text"
					name="<%=process.getNOM_EF_RECHERCHE() %>"
					value="<%=process.getVAL_EF_RECHERCHE() %>" class="sigp2-saisie"
					size="10"></TD>
				<TD class="sigp2-titre"><INPUT type="submit"
					name="<%= process.getNOM_PB_RECHERCHE() %>" value="Rechercher"
					class="sigp2-Bouton-100"></TD>
				<TD class="sigp2-titre"><INPUT type="image" border="0"
					src="images/jumelle.gif" alt="Sélection d'un équipement" width="21" height="17"
					name="<%=process.getNOM_PB_RECH_EQUIP() %>"></TD>
				<TD class="sigp2-titre" width="35" align="right">PM</TD>

				<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" alt="Sélection d'un petit matériel" name="<%=process.getNOM_PB_SEL_PM() %>"></TD>
			</TR>
		</TABLE>
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
								<TD><B>N° d'inventaire</B></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%=process.getVAL_ST_NOINVENT() %></TD>
								<TD width="10"></TD>
								<TD><B>N°d'immat(ou série)</B></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%=process.getVAL_ST_NOIMMAT() %></TD>

							</TR>
							<TR>
								<TD>Nom d'équipement</TD>
								<TD style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_NOMEQUIP()%></TD>
								<TD width="10"></TD>
								<TD>Type</TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TYPE() %></TD>
							</TR>
							<TR>
								<TD height="15"></TD>
								<TD style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"></TD>
								<TD width="10"></TD>
								<TD></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"></TD>
							</TR>
							<TR>
								<TD>Service</TD>
								<TD style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"
									colspan="5"><%=process.getVAL_ST_SERVICE() %></TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE><!-- <FIELDSET> -->
				<TABLE border="0" class="sigp2">
					<tr>
						<td colspan="3" align="center"></td>
					</tr>
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
							<table border="3" bordercolor="#669999" cellpadding="2" cellspacing="2" align="center">
								<tr>
									<td colspan="3" bgcolor="#669999" class="sigp2-titre">Détails d'un BPC</td>
								</tr>
								<TR>
									<TD width="10"></TD>
									<TD><!--Détails du BPC--><FIELDSET>
										<TABLE class="sigp2">
											<tr>
								<td colspan="6" height="10"></td>
							</tr>
							<TR>
								<TD height="10" colspan="6" align="center">
								<TABLE border="0" class="sigp2"
									style="text-transform: uppercase">
									<TR>
										<TD>Numéro du BPC</TD>
										<TD><INPUT type="text" name="<%=process.getNOM_EF_BPC() %>"
											value="<%=process.getVAL_EF_BPC() %>"
											class="sigp2-saisiemajuscule"></TD>
									</TR>

								</TABLE>
								</TD>
							</TR>
							<TR>
												<TD></TD>
												<TD valign="middle">
								
								</TD>
												<TD></TD>
												<TD valign="middle">Dernière valeur du compteur : </TD>
								<TD valign="middle" width="29"><INPUT type="submit" value="&gt;&gt;"
									style="sigp2-Bouton-100"
									name="<%=process.getNOM_PB_AFFICHE_COMPTEUR() %>"></TD>
								<TD class="sigp2-saisie" valign="middle" width="118"><b><%=process.getVAL_ST_COMPTEURAVANT() %></b></TD>
							</TR>
							<TR>
								<TD>Date</TD>
								<TD>
								<TABLE border="0" class="sigp2">
									<TR>
										<TD><INPUT type="text" size="8"
											name="<%=process.getNOM_EF_DATE() %>"
											value="<%=process.getVAL_EF_DATE() %>" class="sigp2-saisie"></TD>
										<TD><IMG src="images/calendrier.gif"
											onclick="return showCalendar('<%=process.getNOM_EF_DATE()%>', 'dd/mm/y');"></TD>
									</TR>
								</TABLE>
								</TD>
								<TD></TD>
								<TD>Compteur (<%=process.getVAL_ST_COMPTEUR() %>)</TD>
								<TD colspan="2">
									<%if(process.isMateriel || process.getBpcAvant() == null){ %>
										<%=process.getVAL_ST_VALEURCOMPTEUR() %>
									<%}else{ %>
									<INPUT type="text" size="10"
									name="<%=process.getNOM_EF_COMPTEUR() %>"
									value="<%=process.getVAL_EF_COMPTEUR() %>" class="sigp2-saisie">
									RAZ : <INPUT type="checkbox" <%= process.forCheckBoxHTML(process.getNOM_CK_CHG_COMPTEUR() , process.getVAL_CK_CHG_COMPTEUR()) %> >
									<%} %>
								</TD></TR>
							<TR>
								<TD></TD>
								<TD valign="middle"></TD>
								<TD></TD>
								<TD colspan="3"><%if (process.isAfficheMesage()){ %><FONT
									face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular"><!--DEBUT CODE TEXTE CLIGNOTANT-->
								<SCRIPT language="JavaScript1.2">
function initArray() {
this.length = initArray.arguments.length;
for (var i = 0; i < this.length; i++) {
this[i] = initArray.arguments[i];
}
}
var ctext = "Veuillez vérifier le numéro de pompe !";
var speed = 500;
var x = 0;
var color = new initArray(
"#555555", 
"teal"
);
if(navigator.appName == "Netscape") {
document.write('<layer id="c">' +ctext+'</layer><br>');
} if (navigator.appVersion.indexOf("MSIE") != -1){
document.write('<div id="c">'+ctext+'</div>');
}
function chcolor(){ 
if(navigator.appName == "Netscape") {
document.c.document.write('<font color="'+color[x]);
document.c.document.write('">'+ctext+'</font>');
document.c.document.close();
}
else if (navigator.appVersion.indexOf("MSIE") != -1){
document.all.c.style.color = color[x];
}
(x < color.length-1) ? x++ : x = 0;
}
setInterval("chcolor()",1000);
// -->
</SCRIPT> <!--FIN CODE TEXTE CLIGNOTANT--></FONT> <%} %></TD>
							</TR>
							<TR>
								<TD>Heure de prise</TD>
								<TD><TABLE border="0" class="sigp2">
									<TR>
										<TD><INPUT type="text" size="5"
											name="<%=process.getNOM_EF_HEURE() %>"
											value="<%=process.getVAL_EF_HEURE() %>" class="sigp2-saisie"></TD>
									</TR>
								</TABLE>
								</TD>
								<TD></TD>
								<TD>N° pompe</TD>
								<TD colspan="2"><SELECT size="1"
									name="<%=process.getNOM_LB_POMPE() %>" class="sigp2-liste">
									<%= process.forComboHTML(process.getVAL_LB_POMPE(),process.getVAL_LB_POMPE_SELECT()) %>
								</SELECT></TD></TR>
							<TR>
												<TD >Quantité</TD>
												<TD class="sigp2-saisie"><TABLE border="0" class="sigp2">
									<TR>
										<TD><INPUT type="text" size="5"
											name="<%=process.getNOM_EF_QUANTITE() %>"
											value="<%=process.getVAL_EF_QUANTITE() %>"
											class="sigp2-saisie"> L</TD>
									</TR>
								</TABLE>
								</TD>
												<TD width="10"></TD>
												<TD>Carburant</TD>
								<TD colspan="2"><%=process.getVAL_ST_CARBU() %></TD></TR>
											<TR>
	
							</TR>
										</TABLE>
								</FIELDSET><br>
									</TD>
									<TD width="10"></TD>
								</TR>
							</TABLE><TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
					<TR align="center">
<%if(!process.isManqueParam()){ %>
						<TD width="140"><BR>
						<INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
<%} %>
						<!--<TD width="137"> <INPUT type="submit" name="" value="Annuler" class="sigp2-Bouton-100"></TD> -->
					</TR>
				</TABLE>
			</td>
		</tr>
	</table>
				<br>
	<!-- </FIELDSET> -->
	

		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
