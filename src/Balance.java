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

public class Balance extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JLabel atmImage, balanceLabel;
    JButton backButton;
    int pin;

    Balance(int pinNumber) {
        this.pin = pinNumber;
        double balance = 0;

        // Load and resize the image
        try {
            BufferedImage img = ImageIO.read(new File(System.getProperty("user.home") + "/Desktop/atm.jpg"));
            Image resizedImg = img.getScaledInstance(1500, 970, Image.SCALE_SMOOTH);
            atmImage = new JLabel(new ImageIcon(resizedImg));
            atmImage.setBounds(0, 0, 1200, 900);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieve and display the current balance
        try {
            Conn conn = new Conn();
            String query = "select amount from bank where pin = '" + pin + "'";
            ResultSet rs = conn.s.executeQuery(query);
            if (rs.next()) {
                balance = rs.getDouble("amount");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // Create and configure the balance label
        balanceLabel = new JLabel("Your current balance is: Rs " + balance);
        balanceLabel.setForeground(Color.white);
        balanceLabel.setFont(new Font("MV Boli", Font.BOLD, 17));
        balanceLabel.setBounds(270, 450, 400, 20);

        // Create and configure the back button
        backButton = new JButton("Back");
        backButton.setBounds(500, 700, 120, 30);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        setLayout(null);
        setSize(1200, 900);
        setVisible(true);
        this.add(balanceLabel);
        this.add(backButton);
        this.add(atmImage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            this.dispose();
            new WelcomePage(pin);
        }
    }
}