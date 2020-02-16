package bean;

import java.io.Serializable;

public class UserLoginBean implements Serializable {

     private int matricola;
     private String password;

    public UserLoginBean(){
        //Bean
    }

    public int getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        try {
            int i = Integer.parseInt(matricola);

            this.matricola = i;
        } catch (Exception e ){
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
