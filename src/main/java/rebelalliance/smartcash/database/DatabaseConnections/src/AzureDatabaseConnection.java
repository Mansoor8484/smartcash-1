package rebelalliance.smartcash.database.DatabaseConnections.src; // Add the package declaration
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AzureDatabaseConnection{
     public static void main(String[] args){
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter2024@!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

        try{
        //Establishing a connection with the database
            Connection connection = DriverManager.getConnection(url);
                
            if(connection != null ){
                System.out.println("Connected to the database");
            }
            else{
                System.out.println("Failed Connection");
            }

            } catch (SQLException e){
            //connection failed
            e.printStackTrace();
        }
    }
 


    public void DBregisterUser(String user, String pass){
        //SQL Query to insert a new user into the database
        String sql = "INSERT INTO UserAuthentication (UserID, Email, UserName, Password) VALUES (?,?,?,?)";
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter2024@!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");


        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //Setting the parameters for the prepared statement
            preparedStatement.setInt(1, 10001); // Fix: Change the argument type to String
            preparedStatement.setString(2, user);
            preparedStatement.setString(3, user);
            preparedStatement.setString(4, pass);

            //Execute the SQL query
            preparedStatement.executeUpdate();
            System.out.println("User Registered");
        } catch(SQLException e){
            e.printStackTrace();
            //handles any kind of errors that may occur
        }
    

    }
}
