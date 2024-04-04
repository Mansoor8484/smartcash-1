package rebelalliance.smartcash.database.DatabaseConnections.src; // Add the package declaration
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Date;
import rebelalliance.smartcash.database.User;
import rebelalliance.smartcash.ledger.container.Account;

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
 


    public void DBregisterUser(String user, String pass){ //review the relationship with AccountID
        //SQL Query to insert a new user into the database
        String sql = "INSERT INTO UserAuthentication (UserID, Email, UserName, Password) VALUES (?,?,?,?)";
        //SQLQuery to insert UserID to userDetails table
        //Connection URL to the Azure SQL Database
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter2024@!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

        //Generates a new UserID
        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            int UserID = generateUserID();
            //UserAuthentication table input
            preparedStatement.setInt(1, UserID); // Fix: Change the argument type to String
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
    public void inputIncome(int TransactionID, int AccountID, double Amount, Date TransactionDate, String Description){
        //SQL Query to insert a new transaction into the database
        String sql = "INSERT INTO Transaction (TransactionID, AccountID, Amount, Date, Description) VALUES (?,?,?,?,?)";
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter2024@!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //Setting the parameters for the prepared statement
            //Generates TransactionID
            preparedStatement.setInt(1, TransactionID); // Fix: Change the argument type to String
            preparedStatement.setInt(2, AccountID); // Fix: Change the argument type to String
            preparedStatement.setDouble(3, Amount); // Fix: Change the argument type to String
            preparedStatement.setDate(4, (java.sql.Date) TransactionDate); // Fix: Change the argument type to String
            preparedStatement.setString(5, Description); // Fix: Change the argument type to String

            //Execute the SQL query
            preparedStatement.executeUpdate();
            System.out.println("Transaction Recorded");
        } catch(SQLException e){
            e.printStackTrace();
            //handles any kind of errors that may occur
        }        
    }

    public void createAccount(int AccountID, int UserID, int AccountNumber, int RoutingNumber, String AccountType, double Balance){
        //SQL Query to insert a new account details into the database
        String sql = "INSERT INTO Account (AccountID, UserID, Balance, RoutingNumber, AccountNumber) VALUES (?,?,?,?)";
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter2024@!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //Setting the parameters for the prepared statement
            preparedStatement.setInt(1, AccountID); // Fix: Change the argument type to String
            preparedStatement.setInt(2, UserID); // Fix: Change the argument type to String
            preparedStatement.setDouble(3, Balance); // Fix: Change the argument type to String
            preparedStatement.setDouble(4, RoutingNumber); // Fix: Change the argument type to String
            preparedStatement.setInt(5, AccountNumber); // Fix: Change the argument type to String  

            //Execute the SQL query
            preparedStatement.executeUpdate();
            System.out.println("Account Details Recorded");
        } catch(SQLException e){
            e.printStackTrace();
            //handles any kind of errors that may occur
        }

    }

        //Function to generate a different userID for each user
        public int generateUserID(){
            //SQL Query to get the last userID from the database
            String sql = "SELECT TOP 1 UserID FROM UserAuthentication ORDER BY UserID DESC";
            String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter2024@!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
    
            try (Connection conn = DriverManager.getConnection(url)){
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                //Execute the SQL query
                preparedStatement.executeQuery();
                System.out.println("UserID Generated");
            } catch(SQLException e){
                e.printStackTrace();
                //handles any kind of errors that may occur
            }
            //Java to actually generate the new userID
            int UserID;
            int minUserID = 10000;
            int maxUserID = 99999;
            Random random = new Random();
            UserID = random.nextInt(maxUserID - minUserID) + minUserID;
    
            //Return the generated userID
            System.out.println("Generated UserID: " + UserID);
            return UserID;
        
        }

    public int generateAccountID(){
        //SQL Query to get the last accountID from the database
        String sql = "SELECT TOP 1 AccountID FROM Account ORDER BY AccountID DESC";
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter2024@!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //Execute the SQL query
            preparedStatement.executeQuery();
            System.out.println("AccountID Generated");
        } catch(SQLException e){
            e.printStackTrace();
            //handles any kind of errors that may occur
        }
        //Java to actually generate the new accountID
        int AccountID;
        int minAccountID = 20000;
        int maxAccountID = 99999;
        Random random = new Random();
        AccountID = random.nextInt(maxAccountID - minAccountID) + minAccountID;

        //Return the generated accountID
        System.out.println("Generated AccountID: " + AccountID);
        return AccountID;
    }
    public int generateBudgetID(){
        //SQL Query to get the last budgetID from the database
        String sql = "SELECT TOP 1 BudgetID FROM Budget ORDER BY BudgetID DESC";
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter2024@!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //Execute the SQL query
            preparedStatement.executeQuery();
            System.out.println("BudgetID Generated");
        } catch(SQLException e){
            e.printStackTrace();
            //handles any kind of errors that may occur
        }
        //Java to actually generate the new budgetID
        int BudgetID;
        int minBudgetID = 30000;
        int maxBudgetID = 99999;
        Random random = new Random();
        BudgetID = random.nextInt(maxBudgetID - minBudgetID) + minBudgetID;

        //Return the generated budgetID
        System.out.println("Generated BudgetID: " + BudgetID);
        return BudgetID;
    }
    
    public int generatePaymentID(){
        //SQL Query to get the last paymentID from the database
        String sql = "SELECT TOP 1 PaymentMethodID FROM PaymentMethod ORDER BY PaymentMethodID DESC";
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter2024@!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //Execute the SQL query
            preparedStatement.executeQuery();
            System.out.println("PaymentID Generated");
        } catch(SQLException e){
            e.printStackTrace();
            //handles any kind of errors that may occur
        }
        //Java to actually generate the new paymentID
        int PaymentID;
        int minPaymentID = 40000;
        int maxPaymentID = 99999;
        Random random = new Random();
        PaymentID = random.nextInt(maxPaymentID - minPaymentID) + minPaymentID;

        //Return the generated paymentID
        System.out.println("Generated PaymentID: " + PaymentID);
        return PaymentID;
    }

    public int generateTransactionID(){
        //SQL Query to get the last transactionID from the database
        String sql = "SELECT TOP 1 TransactionID FROM Transactions ORDER BY TransactionID DESC";
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter2024@!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //Execute the SQL query
            preparedStatement.executeQuery();
            System.out.println("TransactionID Generated");
        } catch(SQLException e){
            e.printStackTrace();
            //handles any kind of errors that may occur
        }
        //Java to actually generate the new transactionID
        int TransactionID;
        int minTransactionID = 50000;
        int maxTransactionID = 99999;
        Random random = new Random();
        TransactionID = random.nextInt(maxTransactionID - minTransactionID) + minTransactionID;

        //Return the generated transactionID
        System.out.println("Generated TransactionID: " + TransactionID);
        return TransactionID;
    }

    public int createRoutingNumber(){
        int RoutingNumber;
        int minRoutingNumber = 100000000;
        int maxRoutingNumber = 999999999;
        Random random = new Random();
        RoutingNumber = random.nextInt(maxRoutingNumber - minRoutingNumber) + minRoutingNumber;
        return RoutingNumber;
    }

    public int createAccountNumber(){
        int AccountNumber;
        int minAccountNumber = 100000000;
        int maxAccountNumber = 999999999;
        Random random = new Random();       
        AccountNumber = random.nextInt(maxAccountNumber - minAccountNumber) + minAccountNumber;
        return AccountNumber;
    }
}
