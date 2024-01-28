import javax.swing.*;
import java.awt.*;


public class BankGUI {

    public static void main(String[] args) {
        new BankGUI();
    }

    BankGUI() {

        JFrame frame = new JFrame("SmartCash");
        frame.setSize(1650, 1080);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.decode("#CFEAC1"));
        frame.setVisible(true);



        // buttons

        JButton overAccount = new JButton("Account Overview");
        overAccount.setBounds(10, 10, 150, 45);
        overAccount.setFont(new Font("Arial", Font.PLAIN, 14));
        //overAccount.setForeground(Color.black);
        //overAccount.setBackground(Color.green);
        frame.add(overAccount);
        overAccount.setVisible(true);


        JButton transaction = new JButton("Transactions");
        transaction.setBounds(160, 10, 150, 45);
        transaction.setFont(new Font("Arial", Font.PLAIN, 14));
        //transaction.setForeground(Color.black);
        //transaction.setBackground(Color.green);
        frame.add(transaction);
        transaction.setVisible(true);


        JButton budgeting = new JButton("Budgeting");
        budgeting.setBounds(310, 10, 150, 45);
        budgeting.setFont(new Font("Arial", Font.PLAIN, 14));
        //budgeting.setForeground(Color.black);
        //budgeting.setBackground(Color.green);
        frame.add(budgeting);
        budgeting.setVisible(true);


        JButton security = new JButton("Security");
        security.setBounds(460, 10, 150, 45);
        security.setFont(new Font("Arial", Font.PLAIN, 14));
        //security.setForeground(Color.black);
        //security.setBackground(Color.green);
        frame.add(security);
        security.setVisible(true);


        JButton transfer = new JButton("Transfer Funds");
        transfer.setBounds(610, 10, 150, 45);
        transfer.setFont(new Font("Arial", Font.PLAIN, 14));
        //transfer.setForeground(Color.black);
        //transfer.setBackground(Color.green);
        frame.add(transfer);
        transfer.setVisible(true);


        // message to user

        JLabel welcomeMessage = new JLabel("Welcome to SmartCash, USER_NAME");
        welcomeMessage.setBounds(10, 60, 300, 60);
        welcomeMessage.setFont(new Font("Arial", Font.BOLD, 14));
        welcomeMessage.setForeground(Color.black);
        frame.add(welcomeMessage);
        welcomeMessage.setVisible(true);

    }

}