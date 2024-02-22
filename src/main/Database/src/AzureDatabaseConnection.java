import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class AzureDatabaseConnection {
    public static void main(String[] args){
        //the Azure SQL database connection parameters:
        String serverName = "smartcash.database.windows.net";
        String databaseName = "";
        String username = "SmartCash";
        String password = "Winter2024@!";

        // JDBC connectoin URL
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

        try{
            //Establishing a conenction with the database
            Connection connection = DriverManager.getConnection(url);

            //Connection successful
            System.out.println("Program has connected with Azure SQL Database. ");

            // Database operationse are placed here


            //close connection after operations are finished. 
            connection.close();

        } catch (SQLException e){
            //connection failed
            e.printStackTrace();
        }
    }
}
