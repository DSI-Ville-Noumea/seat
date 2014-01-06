<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>

<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE>Debut.jsp</TITLE>
<SCRIPT language="JavaScript"> 
function simpleDialog(msg) { 
features = 
   'toolbar=no,location=no,directories=no,status=no,menubar=no,' +
   'scrollbars=no,resizable=no,width=200,height=100' 
dlg = window.open ("","Dialog",features) 
dlg.document.write ("<BODY bgColor='black' text='white'>") 
dlg.document.write ("<H2><CENTER>",msg,"</CENTER></H2>") 
dlg.document.write ("<FORM><CENTER>") 
dlg.document.write ("<INPUT type='button' value='OK' onClick = 'self.close()'>") 
dlg.document.write ("</CENTER></FORM>") 
} 

function principal(fenetre) {

features = 
   'toolbar=no,location=no,directories=no,status=yes,menubar=no,' +
   'scrollbars=no,resizable=no,width=790,height=545,screenX=0,screenY=0,left=0,top=0' 
dlg = window.open (fenetre,"Dialog",features) 

var frm = window.frames;
var res=''
for (i=0; i < frm.length; i++) 
	res += i;

res=window.frames.length
liste.innerText=res

//alert (dlg.frames("Main").title)

} 

</SCRIPT></HEAD>


<BODY>

<P>Cliquez sur le bouton "Connexion" pour ouvrir l'application.</P>
<FORM action="ServletSeat" name="formu" method="post">
<input type ="hidden" name="ACTIVITE" value="ACTIVITE1">
<!-- <INPUT type="submit" name="toto" value="Connexion"> -->
<INPUT type="button" value="Connexion" onclick='principal("Seat.jsp")'><BR>
</FORM>



</BODY>
</HTML>
