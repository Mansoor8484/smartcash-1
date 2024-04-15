package rebelalliance.smartcash.database;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashSalt {
    public String hashPassword(String password){
        try{    
            int outputLength = 40;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            String hash = sb.toString();
            if(outputLength >= 0 && outputLength <= hash.length()){
                return hash.substring(0, outputLength);
            }
            return hash;
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }
    
}
