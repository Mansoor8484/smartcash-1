import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//HelloApplication.java use 

public class AzureDatabaseConnection {
    public static void main(String[] args){
        //the Azure SQL database connection parameters:
        String serverName = "smartcash.database.windows.net";
        String databaseName = "SmartCash";
        String username = "SmartCash";
        String password = "Winter2024@!";

        // JDBC connectoin URL
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password={Winter2024@!};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

        try{
            //Establishing a conenction with the database
            Connection connection = DriverManager.getConnection(url);

            //Connection successful
            System.out.println("Program has connected with Azure SQL Database. ");

            // Database operations are placed here


            //close connection after operations are finished. 
            connection.close();

        } catch (SQLException e){
            //connection failed
            e.printStackTrace();
        }
    }

    public void registerUser(String username, String password){
        //SQL Query to insert a new user into the database
        String sql = "INSERT INTO users (username, password) VALUES (?,?)";

        try (Conneciton conn = DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            //Setting the parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            //Execute the SQL query
            preparedStatement.executeUpdate();
        }catch(SSQLException e){
            e.printStackTrace();
            //handles any kind of errros that may occur
        }
    }
}
