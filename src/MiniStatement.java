import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MiniStatement extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JLabel atmImage, accountLabel;
    JTextArea statementArea;
    JButton exitButton;
    int pin;

    MiniStatement(int pinNumber) {
        this.pin = pinNumber;

        // Load and resize the image
        try {
            BufferedImage img = ImageIO.read(new File("/home/kali/Desktop/eclipse-workspace/bankManagementSystem/atm.jpg"));
            Image resizedImg = img.getScaledInstance(1500, 970, Image.SCALE_SMOOTH);
            atmImage = new JLabel(new ImageIcon(resizedImg));
            atmImage.setBounds(0, 0, 1200, 900);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieve and display the account number
        String accountNumber = "";
        try {
            Conn conn = new Conn();
            String query = "select cardNo from login where pin = '" + pin + "'";
            ResultSet rs = conn.s.executeQuery(query);
            if (rs.next()) {
                accountNumber = rs.getString("cardNo");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // Create and configure the account label
        accountLabel = new JLabel("Account Number: " + accountNumber);
        accountLabel.setForeground(Color.white);
        accountLabel.setFont(new Font("MV Boli", Font.BOLD, 17));
        accountLabel.setBounds(270, 450, 400, 20);

        // Create and configure the statement area
        statementArea = new JTextArea();
        statementArea.setFont(new Font("MV Boli", Font.PLAIN, 15));
        statementArea.setBounds(270, 475, 400, 130);
        statementArea.setEditable(false);

        // Retrieve and display recent transactions
        try {
            Conn conn = new Conn();
            String query = "select * from transactions where pin = '" + pin + "' order by date desc limit 10";
            ResultSet rs = conn.s.executeQuery(query);
            StringBuilder statement = new StringBuilder();
            while (rs.next()) {
                statement.append("Date: ").append(rs.getString("date"))
                        .append(", Type: ").append(rs.getString("type"))
                        .append(", Amount: ").append(rs.getDouble("amount"))
                        .append("\n");
            }
            statementArea.setText(statement.toString());
        } catch (Exception e) {
            System.out.println(e);
        }

        // Create and configure the exit button
        exitButton = new JButton("Exit");
        exitButton.setBounds(580, 440, 90, 30);
        exitButton.setFocusable(false);
        exitButton.addActionListener(this);

        setLayout(null);
        setSize(1200, 900);
        setVisible(true);
        this.add(accountLabel);
        this.add(statementArea);
        this.add(exitButton);
        this.add(atmImage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}