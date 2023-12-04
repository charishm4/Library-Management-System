import java.awt.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.sql.CallableStatement;
import java.sql.Connection;

public class payFines extends JFrame
{
	JFrame frame;
	JPanel panel;
	JTable table;
	static Connection conn;
	String databaseName = "";
	String url;
	String userName;
	String passWord;
	
	payFines()
	{
		start();
	}
	
	public static void main(String[] args)
	{
		payFines pfines=new payFines();
		//mainPage.show();
	}
	
	void start()
	{
		
		frame=new JFrame("Library Management System");
		frame.setSize(600,350);
		frame.setLocation(20, 50);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		panel=new JPanel();
		panel.setBorder(new EmptyBorder(10,10,10,10));
		setContentPane(panel);
		
		//Header
		JLabel lblHeader=new JLabel("Fines",JLabel.CENTER);
		lblHeader.setFont(new Font("Times New Roman",Font.BOLD,20));
		panel.add(lblHeader);
		
		JLayeredPane layeredPane=new JLayeredPane();
		GridBagConstraints gbc_layeredPane = new GridBagConstraints();
		gbc_layeredPane.insets = new Insets(0, 0, 5, 0);
		gbc_layeredPane.fill = GridBagConstraints.BOTH;
		gbc_layeredPane.gridx = 0;
		gbc_layeredPane.gridy = 0;
		panel.add(layeredPane, gbc_layeredPane);
		
		table=new JTable();
		JScrollPane scrollPane=new JScrollPane(table);
		scrollPane.setBounds(6,6,1100,600);
		layeredPane.add(scrollPane);
		
		scrollPane.setViewportView(table);

		JLabel lid=new JLabel("Loanid :",JLabel.LEFT);
		lid.setFont(new Font("Ariel",Font.BOLD,14));
		panel.add(lid);
		
		
		JTextField lidBox = new JTextField();
		lidBox.setFont(new Font("Ariel",Font.PLAIN,14));
		lidBox.setForeground(Color.black);
		panel.add(lidBox);
		lidBox.setColumns(15);
		
		JButton pay = new JButton("Pay Fine");
		pay.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
		    	String LoanID=lidBox.getText();
				try{		
					databaseName = "lbms";
		        	url = "jdbc:mysql://localhost:3306/" + databaseName;
		        	userName = "root"; /* Use whatever user account you prefer */
		        	passWord = "root"; /* Include the password for the account of the previous line. */

		        	conn = DriverManager.getConnection(url,userName,passWord);
					Statement stmt=conn.createStatement();			
					CallableStatement myCall = conn.prepareCall("{call payfineprocedure(?)} ");
		            myCall.setString(1, LoanID);
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
		panel.add(pay);
		
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