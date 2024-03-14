import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
//most likely have to import library for prepared statement
import java.sql.ResultSet;

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

    
    //Possibly supposed to be moved to other file to address the database connection need when creating username and password.
    public void registerUser(String username, String password){
        //SQL Query to insert a new user into the database:
        String insertUserSQL = "INSERT INTO users (username, password, userID, accountID) VALUES (?,?,?,?)";
        
        // Azure SQL database connection parameters
        String serverName = "smartcash.database.windows.net";
        String databaseName = "SmartCash";
        String url = String.format("jdbc:sqlserver://%s:1433;databaseName=%s;user=%s;password=%s;", serverName, databaseName, username, password);


        try (Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = conn.prepareStatement(insertUserSQL)){

            //retrieve the latest userID and accountID from the database
            int latestUserID = getLatestUserID(conn);
            int latestAccountID = getLatestAccountID(conn);

            // Increment the latest values by 1 to get the new values
            int newUserID = latestUserID + 1;
            int newAccountID = latestAccountID + 1;

            // Insert new user with incremented userID and accountID
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.setInt(3, newUserID);
            insertStatement.setInt(4, newAccountID);
            insertStatement.executeUpdate();

            System.out.println("User registered successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error registering user.");
          }
        }

        // Method to retrieve the latest userID from the database
        private int getLatestUserID(Connection conn) throws SQLException {
        String sql = "SELECT MAX(userID) FROM users";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                // No users exist in the database yet
                return 0;
            }
        }
        }
            // Method to retrieve the latest accountID from the database
        private int getLatestAccountID(Connection conn) throws SQLException {
        String sql = "SELECT MAX(accountID) FROM users";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                // No users exist in the database yet
                return 0;
            }
        }
    }




    //Possible function to connect the balance details into the database
    public void inputBalance(float balance){

    }

    //Used to possibly connect each user with a userID, accountID, etc. 
    public void inputInformation(){

    }


    public void inputTransactoin(int userID ){

    }
}

