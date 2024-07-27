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

public class PinChange extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JButton changePinButton, backButton;
    JTextField currentPinField, newPinField, confirmPinField;
    JLabel currentPinLabel, newPinLabel, confirmPinLabel, atmImage;
    int pin;

    PinChange(int pinNumber) {
        this.pin = pinNumber;

        // Load and resize the image
        try {
            BufferedImage img = ImageIO.read(new File(System.getProperty("user.home") + "/Desktop/atm.jpg"));
            Image resizedImg = img.getScaledInstance(1500, 970, Image.SCALE_SMOOTH);
            atmImage = new JLabel(new ImageIcon(resizedImg));
            atmImage.setBounds(0, 0, 1200, 900);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create and configure the labels and text fields
        currentPinLabel = new JLabel("Enter Current PIN: ");
        currentPinLabel.setForeground(Color.white);
        currentPinLabel.setFont(new Font("MV Boli", Font.BOLD, 17));
        currentPinLabel.setBounds(270, 450, 400, 20);

        currentPinField = new JTextField();
        currentPinField.setFont(new Font("MV Boli", Font.BOLD, 22));
        currentPinField.setBounds(450, 450, 200, 20);

        newPinLabel = new JLabel("Enter New PIN: ");
        newPinLabel.setForeground(Color.white);
        newPinLabel.setFont(new Font("MV Boli", Font.BOLD, 17));
        newPinLabel.setBounds(270, 480, 400, 20);

        newPinField = new JTextField();
        newPinField.setFont(new Font("MV Boli", Font.BOLD, 22));
        newPinField.setBounds(450, 480, 200, 20);

        confirmPinLabel = new JLabel("Confirm New PIN: ");
        confirmPinLabel.setForeground(Color.white);
        confirmPinLabel.setFont(new Font("MV Boli", Font.BOLD, 17));
        confirmPinLabel.setBounds(270, 510, 400, 20);

        confirmPinField = new JTextField();
        confirmPinField.setFont(new Font("MV Boli", Font.BOLD, 22));
        confirmPinField.setBounds(450, 510, 200, 20);

        changePinButton = new JButton("Change PIN");
        changePinButton.setBounds(350, 560, 120, 30);
        changePinButton.setFocusable(false);
        changePinButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.setBounds(500, 560, 120, 30);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        setLayout(null);
        setSize(1200, 900);
        setVisible(true);
        this.add(currentPinLabel);
        this.add(currentPinField);
        this.add(newPinLabel);
        this.add(newPinField);
        this.add(confirmPinLabel);
        this.add(confirmPinField);
        this.add(changePinButton);
        this.add(backButton);
        this.add(atmImage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            this.dispose();
            new WelcomePage(pin);
        } else if (e.getSource() == changePinButton) {
            String currentPin = currentPinField.getText();
            String newPin = newPinField.getText();
            String confirmPin = confirmPinField.getText();

            if (currentPin.equals("") || newPin.equals("") || confirmPin.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all fields.");
            } else if (!newPin.equals(confirmPin)) {
                JOptionPane.showMessageDialog(null, "New PIN and Confirm New PIN do not match.");
            } else {
                try {
                    Conn conn = new Conn();
                    String query = "select * from login where pin = '" + currentPin + "'";
                    ResultSet rs = conn.s.executeQuery(query);
                    if (rs.next()) {
                        String updateQuery = "update login set pin = '" + newPin + "' where pin = '" + currentPin + "'";
                        conn.s.executeUpdate(updateQuery);
                        JOptionPane.showMessageDialog(null, "PIN changed successfully.");
                        this.dispose();
                        new WelcomePage(pin);
                    } else {
                        JOptionPane.showMessageDialog(null, "Current PIN is incorrect.");
                    }
                } catch (Exception ae) {
                    System.out.println(ae);
                }
            }
        }
    }
}