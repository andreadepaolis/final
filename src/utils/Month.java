package utils;

public class Month {

   private int day;
   private int index;
   private String name;
   private int year;


    Month(int index, String name, int day, int year){

        this.day = day;
        this.index = index;
        this.name = name;
        this.year = year;

    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public int getIndex() {
        return index;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
