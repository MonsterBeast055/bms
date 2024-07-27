import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Deposit extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JLabel atmImage, enterAmountLabel;
	JTextField amountField;
	JButton depositButton, backButton;
	int pin;

	Deposit(int pinNumber) {
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

		// Create and configure the enter amount label
		enterAmountLabel = new JLabel("Enter Amount: ");
		enterAmountLabel.setForeground(Color.white);
		enterAmountLabel.setFont(new Font("MV Boli", Font.BOLD, 17));
		enterAmountLabel.setBounds(270, 450, 400, 20);

		// Create and configure the amount field
		amountField = new JTextField();
		amountField.setFont(new Font("MV Boli", Font.BOLD, 22));
		amountField.setBounds(270, 475, 350, 30);

		// Create and configure the deposit button
		depositButton = new JButton("Deposit");
		depositButton.setBounds(500, 510, 120, 30);
		depositButton.setFocusable(false);
		depositButton.addActionListener(this);

		// Create and configure the back button
		backButton = new JButton("Back");
		backButton.setBounds(500, 545, 120, 30);
		backButton.setFocusable(false);
		backButton.addActionListener(this);

		setLayout(null);
		setSize(1200, 900);
		setVisible(true);
		this.add(enterAmountLabel);
		this.add(amountField);
		this.add(depositButton);
		this.add(backButton);
		this.add(atmImage);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			this.dispose();
			new WelcomePage(pin);
		} else if (e.getSource() == depositButton) {
			String amount = amountField.getText();
			if (amount.equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter the amount to deposit.");
			} else {
				try {
					Conn conn = new Conn();
					String query = "insert into transactions(pin, date, type, amount) values(?, ?, ?, ?)";
					PreparedStatement pstmt = conn.c.prepareStatement(query);
					pstmt.setInt(1, pin);
					pstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
					pstmt.setString(3, "Deposit");
					pstmt.setDouble(4, Double.parseDouble(amount));
					pstmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Amount deposited successfully.");
					this.dispose();
					new WelcomePage(pin);
				} catch (Exception ae) {
					System.out.println(ae);
				}
			}
		}
	}
}