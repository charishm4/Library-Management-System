import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CheckIn extends JFrame{
	
	JFrame frame;
	JPanel panel;
	static Connection conn;
	String databaseName = "";
	String url;
	String userName;
	String passWord;
	
	CheckIn()
	{
		start();
	}
	
	public static void main(String[] args)
	{
		CheckIn cin=new CheckIn();
	}
	
	void start()
	{
		frame=new JFrame("LBMS");
		frame.setSize(500,300);
		frame.setLocation(20, 50);
		frame.setMinimumSize(frame.getSize());
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		panel=new JPanel();
		panel.setBorder(new EmptyBorder(15,15,15,15));
		setContentPane(panel);

		JLabel lblIsbn=new JLabel("ISBN* :",JLabel.LEFT);
		lblIsbn.setFont(new Font("Ariel",Font.BOLD,14));
		panel.add(lblIsbn);
		
		JTextField isbn = new JTextField();
		isbn.setFont(new Font("Ariel",Font.PLAIN,14));
		isbn.setForeground(Color.black);
		panel.add(isbn);
		isbn.setColumns(15);
		
		JLabel lblcard=new JLabel("Card No.* :",JLabel.LEFT);
		lblcard.setFont(new Font("Ariel",Font.BOLD,14));
		panel.add(lblcard);
		
		JTextField cardID = new JTextField();
		cardID.setFont(new Font("Ariel",Font.PLAIN,14));
		cardID.setForeground(Color.black);
		panel.add(cardID);
		cardID.setColumns(15);
		
		JButton checkIn=new JButton("Check In");
		checkIn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
		    	String ISBN=isbn.getText();
				String cardId=cardID.getText();
				try{		
					databaseName = "lbms";
		        	url = "jdbc:mysql://localhost:3306/" + databaseName;
		        	userName = "root"; /* Use whatever user account you prefer */
		        	passWord = "root"; /* Include the password for the account of the previous line. */

		        	conn = DriverManager.getConnection(url,userName,passWord);
					Statement stmt=conn.createStatement();			
					CallableStatement myCall = conn.prepareCall("{call checkinprocedure(?,?)} ");
		            myCall.setString(1, ISBN);
		            myCall.setString(2, cardId);
		            myCall.executeQuery();
		            ResultSet rs = myCall.getResultSet();
		            rs.next();
		            int status = rs.getInt("rstatus");
		            String message = rs.getString("ResultMessage");
		            JOptionPane.showMessageDialog(null, message);
				}				
				catch(SQLException ex) {
					System.out.println("Error in connection: " + ex.getMessage());
				}
			}
		});
		panel.add(checkIn);
		
		JButton back=new JButton("Back");
		back.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				frame.setVisible(false);
				new LibMain();
			}
		});
		panel.add(back);		

		frame.add(panel);
		frame.setVisible(true);
	}

}
