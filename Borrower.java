import java.awt.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.CallableStatement;
import java.sql.Connection;

public class Borrower extends JFrame
{
	JFrame frame;
	JPanel panel;
	static Connection conn;
	String databaseName = "";
	String url;
	String userName;
	String passWord;
	
	Borrower()
	{
		start();
	}
	
	public static void main(String[] args)
	{
		Borrower newBorrower=new Borrower();
		//mainPage.show();
	}
	
	void start()
	{
		frame=new JFrame("LBMS");
		frame.setSize(500,500);
		frame.setLocation(25, 60);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		panel=new JPanel();
		panel.setBorder(new EmptyBorder(15,15,15,15));
		setContentPane(panel);
		
		//Header
		JLabel lblHeader=new JLabel("New Borrower",JLabel.CENTER);
		lblHeader.setBorder(new EmptyBorder(15,15,15,15));
		lblHeader.setFont(new Font("Arial",Font.BOLD,20));
		panel.add(lblHeader);

	
		JLabel name=new JLabel("Name* :",SwingConstants.CENTER);
		name.setBorder(new EmptyBorder(15,15,15,15));
		name.setFont(new Font("Arial",Font.BOLD,14));
		panel.add(name);
		
		
		JTextField nameBox = new JTextField();
		nameBox.setFont(new Font("Ariel",Font.PLAIN,14));
		nameBox.setForeground(Color.black);
		panel.add(nameBox);
		nameBox.setColumns(15);
		
		JLabel lblSsn=new JLabel("SSN* :",JLabel.LEFT);
		lblSsn.setBorder(new EmptyBorder(15,15,15,15));
		lblSsn.setFont(new Font("Times New Roman",Font.BOLD,14));
		panel.add(lblSsn);
		
		JTextField ssnBox = new JTextField();
		ssnBox.setFont(new Font("Ariel",Font.PLAIN,14));
		ssnBox.setForeground(Color.black);
		panel.add(ssnBox);
		ssnBox.setColumns(15);

		JLabel address=new JLabel("Address* :",JLabel.LEFT);
		address.setBorder(new EmptyBorder(15,15,15,15));
		address.setFont(new Font("Times New Roman",Font.BOLD,14));
		panel.add(address);
		
		JTextField addressBox = new JTextField();
		addressBox.setFont(new Font("Ariel",Font.PLAIN,14));
		addressBox.setForeground(Color.black);
		panel.add(addressBox);
		addressBox.setColumns(15);

		JLabel phone=new JLabel("Phone* :",JLabel.LEFT);
		phone.setBorder(new EmptyBorder(15,15,15,15));
		phone.setFont(new Font("Times New Roman",Font.BOLD,14));
		panel.add(phone);
		
		JTextField phoneBox = new JTextField();
		phoneBox.setFont(new Font("Ariel",Font.PLAIN,14));
		phoneBox.setForeground(Color.black);
		panel.add(phoneBox);
		phoneBox.setColumns(15);
		
		JButton borrower=new JButton("Add Borrower");
		borrower.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try{
					String bname=nameBox.getText();
					String bssn=ssnBox.getText();
					String baddress=addressBox.getText();
					String bphone=phoneBox.getText();
					databaseName = "lbms";
		        	url = "jdbc:mysql://localhost:3306/" + databaseName;
		        	userName = "root"; /* Use whatever user account you prefer */
		        	passWord = "root"; /* Include the password for the account of the previous line. */

		        	conn = DriverManager.getConnection(url,userName,passWord);
					Statement stmt=conn.createStatement();			
					CallableStatement myCall = conn.prepareCall("{call borrowerprocedure(?,?,?,?)} ");
		            myCall.setString(1, bssn);
		            myCall.setString(2, bname);
		            myCall.setString(3, baddress);
		            myCall.setString(4, bphone);
		            myCall.executeQuery();
		            ResultSet rs = myCall.getResultSet();
		            rs.next();
		            int status = rs.getInt("Rstatus");
		            String message = rs.getString("ResultMessage");
		            JOptionPane.showMessageDialog(null, message);
					
				}
					catch (SQLException e1) {
						// TODO Auto-generated catch block
						System.out.println("Error in connection: " + e1.getMessage());
					}
					
			}		
		});

		panel.add(borrower);
		

		//Close Button
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