package utils;

public class Toast {

    private String title;
    private String message;
    private int type;


    public Toast(String title,String message, int type){

        this.title = title;
        this.message = message;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
