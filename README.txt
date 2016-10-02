 
#Sous linux pour monter les dossiers r�seau (en mode root)
mount -t cifs -o username=SITE-MAIRIE/**REMOVED**,pass=**REMOVED**,uid=505,gid=507,dir_mode=0775,file_mode=0775 //starjet/edition$ /home/luc/mnt/STARJET

#Sous linux pour monter les dossiers r�seau (en mode root)
umount  /home/luc/mnt/STARJET

#Sous linux config du context.xml :
<Parameter name="STARJET_SERVER" description="Serveur starjet (nom du serveur)" override="false" value="/home/luc/mnt/STARJET/" />
