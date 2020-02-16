package bean;

public class UserLoginBean{

     private int matricola;
     private String password;

    public UserLoginBean(){}

    public int getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        try {
            int i = Integer.parseInt(matricola);

            this.matricola = i;
        } catch (Exception e ){
            e.printStackTrace();
            this.matricola = 0;
        }

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
