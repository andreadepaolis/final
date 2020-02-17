package utils;

public class MonthFactory {

     public Month createMonth(int i,int year){

         switch (i){
             case 1:
                 return new Month(1,"Gennaio",30,year);
             case 2:
                 return new Month(2,"Febbraio",28,year);
             case 3:
                 return new Month(3,"Marzo",31,year);
             case 4:
                 return new Month(4,"Aprile",30,year);
             case 5:
                 return new Month(5,"Maggio",31,year);
             case 6:
                 return new Month(6,"Giugno",30,year);
             case 7:
                 return new Month(7,"Luglio",31,year);
             case 8:
                 return new Month(8,"Agosto",31,year);
             case 9:
                 return new Month(9,"Settembre",30,year);
             case 10:
                 return new Month(10,"Ottobre",31,year);
             case 11:
                 return new Month(11,"Novembre",30,year);
             case 12:
                 return new Month(12,"Dicembre",31,year);
             default:
                 return null;
         }
     }
}
