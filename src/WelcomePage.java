import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class WelcomePage implements ActionListener {

	JFrame frame;
	JLabel atmImage;
	JLabel transactionLabel;
	int pin;
	JButton withdrawButton;
	JButton depositButton;
	JButton balanceButton;
	JButton miniStatementButton;
	JButton pinChangeButton;
	JButton exitButton;

	WelcomePage(int pinNumber) {
		this.pin = pinNumber;
		frame = new JFrame();

		// Load and resize the image
		try {
			BufferedImage img = ImageIO.read(new File("/home/kali/Desktop/eclipse-workspace/bankManagementSystem/atm.jpg"));
			Image resizedImg = img.getScaledInstance(1500, 970, Image.SCALE_SMOOTH);
			atmImage = new JLabel(new ImageIcon(resizedImg));
			atmImage.setBounds(0, 0, 1200, 900);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create and configure the transaction label
		transactionLabel = new JLabel("Please select your transaction", SwingConstants.CENTER);
		transactionLabel.setFont(new Font("MV Boli", Font.BOLD, 24));
		transactionLabel.setForeground(Color.WHITE); // Set the text color to white
		transactionLabel.setOpaque(false); // Make the background transparent
		transactionLabel.setBounds(175, 433, 600, 40);

		// Create and configure the ATM buttons
		withdrawButton = new JButton("Withdraw");
		withdrawButton.setFont(new Font("MV Boli", Font.BOLD, 18));
		withdrawButton.setBounds(565, 500, 127, 30);
		withdrawButton.addActionListener(this);
		withdrawButton.setFocusable(false);

		depositButton = new JButton("Deposit");
		depositButton.setFont(new Font("MV Boli", Font.BOLD, 18));
		depositButton.setBounds(250, 500, 120, 30);
		depositButton.addActionListener(this);
		depositButton.setFocusable(false);

		balanceButton = new JButton("Balance");
		balanceButton.setFont(new Font("MV Boli", Font.BOLD, 18));
		balanceButton.setBounds(250, 535, 120, 30);
		balanceButton.addActionListener(this);
		balanceButton.setFocusable(false);

		miniStatementButton = new JButton("Mini Statement");
		miniStatementButton.setFont(new Font("MV Boli", Font.BOLD, 18));
		miniStatementButton.setBounds(519, 535, 175, 30);
		miniStatementButton.addActionListener(this);
		miniStatementButton.setFocusable(false);

		pinChangeButton = new JButton("Pin Change");
		pinChangeButton.setFont(new Font("MV Boli", Font.BOLD, 18));
		pinChangeButton.setBounds(250, 570, 150, 30);
		pinChangeButton.addActionListener(this);
		pinChangeButton.setFocusable(false);

		exitButton = new JButton("Exit");
		exitButton.setFont(new Font("MV Boli", Font.BOLD, 18));
		exitButton.setBounds(565, 570, 120, 30);
		exitButton.addActionListener(this);
		exitButton.setFocusable(false);

		// Create a layered pane
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(1200, 900));
		layeredPane.add(atmImage, JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(transactionLabel, JLayeredPane.PALETTE_LAYER);
		layeredPane.add(withdrawButton, JLayeredPane.PALETTE_LAYER);
		layeredPane.add(depositButton, JLayeredPane.PALETTE_LAYER);
		layeredPane.add(balanceButton, JLayeredPane.PALETTE_LAYER);
		layeredPane.add(miniStatementButton, JLayeredPane.PALETTE_LAYER);
		layeredPane.add(pinChangeButton, JLayeredPane.PALETTE_LAYER);
		layeredPane.add(exitButton, JLayeredPane.PALETTE_LAYER);

		// Add layered pane to the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(1200, 900);
		frame.add(layeredPane, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == withdrawButton) {
			new Withdrawl(pin);
		} else if (e.getSource() == depositButton) {
			new Deposit(pin);
		} else if (e.getSource() == balanceButton) {
			new Balance(pin);
		} else if (e.getSource() == miniStatementButton) {
			new MiniStatement(pin);
		} else if (e.getSource() == pinChangeButton) {
			new PinChange(pin);
		} else if (e.getSource() == exitButton) {
			frame.dispose();
		}
	}
}