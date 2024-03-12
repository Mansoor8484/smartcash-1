import java.sql.Connection;
import java.sql.DriverManager;
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
        String url = String.format("jdbc:sqlserver://%s:1433;databaseName=%s;user=%s;password=%s;", serverName, databaseName, username, password);
        try{
            //Establishing a conenction with the database
            Connection connection = DriverManager.getConnection(url);

            //Connection successful
            System.out.println("Program has connected with Azure SQL Database. ");

            // Database operations are placed here


            //close connection after operations are finished. 
            connection.close();

        } 
        catch(SQLException e){
            System.err.println("Connection to Azure SQL Database failed.");
            e.printStackTrace();
        }
    }

    /*
    //Possibly supposed to be moved to other file to address the database connection need when creating username and password.
    public void registerUser(String username, String password){
        //SQL Query to insert a new user into the database
        String sql = "INSERT INTO users (username, password) VALUES (?,?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            //Setting the parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            //Execute the SQL query
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            //handles any kind of errros that may occur
        }
    }

    //Not sure how we are going to address passwords in this project. 
    public void encryptPassword(){

    }

    //Possible function to connect the balance details into the database
    public void inputBalance(){

    }

    //Used to possibly connect each user with a userID, accountID, etc. 
    public void inputInformation(){

    }


    public void inputTransactoin(){

    }*/
}
