package utils;

public class ToastException extends Exception {

        private String message;
        private String title;
        public ToastException(String title,String message){
            super();
            this.title = title;
            this.message = message;
         }

    @Override
    public String getMessage() {
        return message;
    }

    public String getTitle() {
            return title;
    }
}

