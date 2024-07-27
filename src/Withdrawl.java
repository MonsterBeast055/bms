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

public class Withdrawl extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JButton clear, withdraw, back;
	JTextField amountField;
	JLabel text, atmImage;
	int pin;

	Withdrawl(int pinNumber) {
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

		// Create and configure the withdrawal label
		text = new JLabel("Enter the amount You want to withdraw: ");
		text.setForeground(Color.white);
		text.setFont(new Font("MV Boli", Font.BOLD, 17));
		text.setBounds(270, 450, 400, 20);

		amountField = new JTextField();
		amountField.setFont(new Font("MV Boli", Font.BOLD, 22));
		amountField.setBounds(270, 475, 350, 30);

		withdraw = new JButton("Withdraw");
		withdraw.setBounds(500, 530, 120, 30);
		withdraw.setFocusable(false);
		withdraw.addActionListener(this);

		back = new JButton("Back");
		back.setBounds(500, 565, 120, 30);
		back.setFocusable(false);
		back.addActionListener(this);

		setLayout(null);
		setSize(1200, 900);
		setVisible(true);
		this.add(text);
		this.add(amountField);
		this.add(withdraw);
		this.add(back);
		this.add(atmImage);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			this.dispose();
			new WelcomePage(pin);
		} else if (e.getSource() == withdraw) {
			String number = amountField.getText();
			if (number.equals("")) {
				JOptionPane.showMessageDialog(null, "Please Enter the amount you want to withdraw:)");
			} else {
				try {
					double balance = 0;
					Conn conn = new Conn();
					String query1 = "select amount from bank where pin = ?";
					PreparedStatement pstmt1 = conn.c.prepareStatement(query1);
					pstmt1.setInt(1, pin);
					ResultSet rs = pstmt1.executeQuery();
					if (rs.next()) {
						balance = rs.getDouble("amount");
					}
					double withdrawAmount = Double.parseDouble(number);
					if (withdrawAmount > balance) {
						JOptionPane.showMessageDialog(null, "Insufficient balance");
					} else {
						double newBalance = balance - withdrawAmount;

						String updateQuery = "update bank set amount = ? where pin = ?";
						PreparedStatement pstmt2 = conn.c.prepareStatement(updateQuery);
						pstmt2.setDouble(1, newBalance);
						pstmt2.setInt(2, pin);
						pstmt2.executeUpdate();

						String insertQuery = "insert into transactions (pin, date, type, amount) values(?, ?, ?, ?)";
						PreparedStatement pstmt3 = conn.c.prepareStatement(insertQuery);
						pstmt3.setInt(1, pin);
						pstmt3.setTimestamp(2, new Timestamp(new Date().getTime()));
						pstmt3.setString(3, "Withdraw");
						pstmt3.setDouble(4, withdrawAmount);
						pstmt3.executeUpdate();

						JOptionPane.showMessageDialog(null, "Rs " + number + " Withdrawn Successfully");
						this.dispose();
						new WelcomePage(pin);
					}
				} catch (Exception ae) {
					System.out.println(ae);
				}
			}
		}
	}
}