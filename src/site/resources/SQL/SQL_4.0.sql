update f_aff_sce set codeservice =codeservice  || 'AAAAAAAAAAAA' 
    where (substr(codeservice ,1,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(codeservice ,2,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(codeservice ,3,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(codeservice ,4,1) not in ('0','1','2','3','4','5','6','7','8','9',' '));
        
update f_affecter set codeservice=codeservice || 'AAAAAAAAAAAA' 
    where (substr(codeservice,1,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(codeservice,2,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(codeservice,3,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(codeservice,4,1) not in ('0','1','2','3','4','5','6','7','8','9',' '));
        
update f_pmaff_ag set codesce=codesce || 'AAAAAAAAAAAA' 
    where (substr(codesce,1,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(codesce,2,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(codesce,3,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(codesce,4,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')); 
        
update f_pmaff_sc set siserv=siserv || 'AAAAAAAAAAAA' 
    where (substr(siserv,1,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(siserv,2,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(siserv,3,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(siserv,4,1) not in ('0','1','2','3','4','5','6','7','8','9',' '));
        
update f_declarations set codeservice=codeservice || 'AAAAAAAAAAAA' 
    where (substr(codeservice,1,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(codeservice,2,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(codeservice,3,1) not in ('0','1','2','3','4','5','6','7','8','9',' ')) 
        and (substr(codeservice,4,1) not in ('0','1','2','3','4','5','6','7','8','9',' '));