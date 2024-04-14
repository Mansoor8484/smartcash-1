package rebelalliance.smartcash.database.DatabaseConnections.src; // Add the package declaration
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
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
    //Generates the new user information and inserts it into the database
    public void DBregisterUser(String user, String pass){ //review the relationship with AccountID
        //SQL Query to insert a new user into the database
        String sql = "INSERT INTO UserAuthentication (UserID, Email, UserName, Password) VALUES (?,?,?,?)";
        //SQLQuery to insert UserID to userDetails table
        String sql2 = "INSERT INTO UserDetails (UserID, Email) VALUES (?,?)";
        //Connection URL to the Azure SQL Database
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter2024@!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

        //Generates a new UserID
        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //UserAuthentication table input
            int NewUserID = generateUserID();
            preparedStatement.setInt(1, NewUserID); // Fix: Change the argument type to String
            preparedStatement.setString(2, user);
            preparedStatement.setString(3, user);

            //would the hash function go here? since this is where the password would be? 
            preparedStatement.setString(4, pass);

            //userDetails table input
            //Most likely have to add the username/email as well
            PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
            preparedStatement2.setInt(1, NewUserID);
            preparedStatement2.setString(2, user);
              
            //Execute the SQL query
            preparedStatement.executeUpdate();
            System.out.println("User Registered");
            preparedStatement2.executeUpdate();
            System.out.println("User Details Recorded");
            //Creates the account details in the Account table
            createAccount(NewUserID);
            System.out.println("Account Created");//Foreign key constraint 
        } catch(SQLException e){
            e.printStackTrace();
            //handles any kind of errors that may occur
        }
    }
    //Creates the account details for the user
    public void createAccount(int UserID){
        //SQL Query to insert a new account details into the database
        String sql = "INSERT INTO Account(AccountID, UserID, RoutingNumber, AccountNumber) VALUES (?,?,?,?)";
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter2024@!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //Generates AccountID
            int NewAccountID = generateAccountID();
            int newRoutingNumber = createRoutingNumber();
            int newAccountNumber = createAccountNumber();
            preparedStatement.setInt(1, NewAccountID); // Fix: Change the argument type to String
            preparedStatement.setInt(2, UserID); // Fix: Change the argument type to String
            preparedStatement.setInt(3, newRoutingNumber); // Fix: Change the argument type to String 
            preparedStatement.setInt(4, newAccountNumber); // Fix: Change the argument type to String

            //Execute the SQL query
            preparedStatement.executeUpdate();
            System.out.println("Account Details Recorded");
        } catch(SQLException e){
            e.printStackTrace();
            //handles any kind of errors that may occur
        }

    }
    public void inputTransaction(double Amount, String Description){
        //SQL Query to insert a new transaction into the database
        String sql = "INSERT INTO Transaction (TransactionID, Amount, Description) VALUES (?,?,?)";
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter2024@!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //Setting the parameters for the prepared statement
            //Generates TransactionID
            int NewTransactionID = generateTransactionID();
            
            preparedStatement.setInt(1, NewTransactionID); // Fix: Change the argument type to String
            preparedStatement.setDouble(2, Amount); // Fix: Change the argument type to String
            preparedStatement.setString(3, Description); // Fix: Change the argument type to String

            //Execute the SQL query
            preparedStatement.executeUpdate();
            System.out.println("Transaction Recorded");
        } catch(SQLException e){
            e.printStackTrace();
            //handles any kind of errors that may occur
        }        
    }

    public void createBudget(int UserID, double BudgetAmount){
        //SQL Query to insert a new budget into the database
        String sql = "INSERT INTO Budget (BudgetID, AccountID, Balance) VALUES (?,?,?)";
        String url = String.format("jdbc:sqlserver://smartcash.database.windows.net:1433;database=SmartCash;user=SmartCash@smartcash;password=Winter");
        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //Setting the parameters for the prepared statement
            //Generates BudgetID
            int newBudgetID = generateBudgetID();
            preparedStatement.setInt(1, newBudgetID); // Fix: Change the argument type to String
            preparedStatement.setInt(2, UserID); // Fix: Change the argument type to String
            preparedStatement.setDouble(3, BudgetAmount); // Fix: Change the argument type to String

            //Execute the SQL query
            preparedStatement.executeUpdate();
            System.out.println("Budget Recorded");
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
            int minUserID = 10000;
            int maxUserID = 99999;
            Random random = new Random();
            int UserID = random.nextInt(maxUserID - minUserID) + minUserID;
            try (Connection conn = DriverManager.getConnection(url)){
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                //Execute the SQL query
                preparedStatement.executeQuery();
            } catch(SQLException e){
                e.printStackTrace();
                //handles any kind of errors that may occur
            }
            //Java to actually generate the new userID
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
