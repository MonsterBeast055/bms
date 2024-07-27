import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Scanner;

public class AccDetails extends JFrame implements ActionListener{

	JButton back;
	int pin;
	AccDetails(int pinnumber){

		this.pin = pinnumber;

		back = new JButton("Back");
		back.setBounds(250,500,150,20);
		back.setFocusable(false);
		back.addActionListener(this);

		JLabel cardNumber1 = null;
		JLabel amount1 = null;
		try {
			String amount = null;
			String cardNumber = null;
			Conn conn = new Conn();
			String query1 = "select amount from bank where pin = '"+pin+"'";
			String query2 = "select cardNo from login where pin = '"+pin+"'";

			ResultSet rs = conn.s.executeQuery(query1);
			if (rs.next()) {
				amount = rs.getString("amount");
			}
			rs.close();

			ResultSet rs1 = conn.s.executeQuery(query2);
			if (rs1.next()) {
				cardNumber = rs1.getString("cardNo");
			}
			rs1.close();

			System.out.println(amount);
			System.out.println(cardNumber);

			cardNumber1 = new JLabel(cardNumber);
			amount1 = new JLabel(amount);

			cardNumber1.setBounds(50,50,50,50);
			amount1.setBounds(100,300,50,50);

		} catch(Exception e) {
			System.out.println(e);
		}

		setSize(600,600);
		setVisible(true);
		setLayout(null);
		this.add(back);
		this.getContentPane().add(cardNumber1);
		this.getContentPane().add(amount1);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == back ) {
			new WelcomePage(pin);
			this.dispose();
		}
	}
}