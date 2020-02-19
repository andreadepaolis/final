package utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.GregorianCalendar;

public class InputController {
    
        private static InputController inputController;
    
        public InputController() {
            //controller
        }

        public static InputController getIstance(){
            if(inputController==null){
                inputController= new InputController();
            }
            return inputController;
        }


            public Date convertDate(String data) {

                data = data.replace('-','/');


                try{

                    return new SimpleDateFormat("yyyy/MM/dd").parse(data);

                }catch(ParseException ps){
                    return null;
                }

    }
    public int stringToInt(String value){

        try {
            return Integer.parseInt(value);
        } catch(Exception e){
            return 0;
        }
    }

    public Date generateDate(int dayIndex, int index, int year) {

        return new GregorianCalendar(year, index - 1, dayIndex).getTime();
    }


    public boolean checkInRange(int num, int min, int max){

        return num >= min && num <= max;
    }


    public Date converDate(String date) {
        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");

        Date d;
        try {
            d = formatter1.parse(date);
            return d;
        } catch (ParseException e) {
            return null;
        }
    }

    public boolean checkDate(Date d) throws ToastException {
        String date1="01/08/2020";
        String date2 = "01/09/2019";
        if(d == null)
            throw new ToastException("Invalid Date","Date is null");


        try {
            Date end = new SimpleDateFormat("dd/MM/yyyy").parse(date1);
            Date start = new SimpleDateFormat("dd/MM/yyyy").parse(date2);
            return d.after(start) && d.before(end);
        }catch (ParseException p){
            throw new ToastException("Invalid Date",p.getMessage());
        }
    }

}
