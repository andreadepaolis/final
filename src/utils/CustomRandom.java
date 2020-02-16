package utils;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class CustomRandom {

     private Random random;


     public CustomRandom() throws NoSuchAlgorithmException {
         this.random = SecureRandom.getInstanceStrong();
     }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
