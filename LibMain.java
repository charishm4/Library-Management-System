import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.*;
//import javax.swing.border.EmptyBorder;
import javax.swing.border.*;
public class LibMain extends JFrame{
	
	JFrame frame;
	JLabel header;
	JPanel panel;
	String databaseName = "";
	String url;
	String userName;
	String passWord;
	static Connection conn;
	LibMain()
	{
		start();
	}
	
	void start()
	{
		frame = new JFrame("LBMS");
		frame.setSize(500,500);
		frame.setLocation(25, 60);
		frame.setLayout(new GridLayout(2,1));
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		header = new JLabel("Library Management Application",JLabel.CENTER);
		header.setFont(new Font("Arial",Font.BOLD,28));
		header.setForeground(Color.gray);
		header.setBackground(Color.white);
		
		//Search
		JButton search =  new JButton("Search");
		search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
					frame.setVisible(false);
					try {
						new Search();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		
		//CheckIn
		JButton checkIn = new JButton("Check In");
		checkIn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				frame.setVisible(false);
				new CheckIn();
			}
		});
		
		//CheckOut
		JButton checkOut = new JButton("Check Out");
		checkOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				frame.setVisible(false);
				new CheckOut();
			}
		});
		
		//Add Borrower
		JButton borrower = new JButton("New Borrower");
		borrower.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				frame.setVisible(false);
				new Borrower();
			}
		});
		
		//Pay Fines
		JButton pfine = new JButton("Pay Fine");
		pfine.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				frame.setVisible(false);
				new payFines();
			}
		});
		
		// Display Dues
		JButton fineDue = new JButton("Fine Due");
		fineDue.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				frame.setVisible(false);
				try {
					new finesDue();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// Refresh Fines
		JButton rfine = new JButton("Refresh Fine");
		rfine.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{			    	
					try{		
						databaseName = "lbms";
			        	url = "jdbc:mysql://localhost:3306/" + databaseName;
			        	userName = "root"; /* Use whatever user account you prefer */
			        	passWord = "root"; /* Include the password for the account of the previous line. */

			        	conn = DriverManager.getConnection(url,userName,passWord);
						Statement stmt=conn.createStatement();	
						CallableStatement myCall = conn.prepareCall("{call updateprocedure()} ");
			            myCall.executeQuery();
			            ResultSet rs = myCall.getResultSet();
			            rs.next();
			            int status = rs.getInt("Rstatus");
			            String message = rs.getString("ResultMessage");
			            JOptionPane.showMessageDialog(null, message);
					}				
					catch(SQLException ex) {
						System.out.println("Error in connection: " + ex.getMessage());
					}
				}	
		});
		
		
		// Display Fines
		JButton displayfine = new JButton("DisplayFines");
		displayfine.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				frame.setVisible(false);
				try {
					new displayFines();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// Creating Panel to add buttons
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(10,10,10,10));
		setContentPane(panel);

		panel.add(search);
		panel.add(checkIn);
		panel.add(checkOut);
		panel.add(borrower);
		panel.add(pfine);
		panel.add(fineDue);
		panel.add(rfine);
		panel.add(displayfine);
		
		frame.add(header);
		frame.add(panel);
		frame.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					LibMain mainPage=new LibMain();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	

}
