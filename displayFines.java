import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.*;

import net.proteanit.sql.DbUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;


public class displayFines extends JFrame{
	
	JFrame frame;
	JPanel panel;
	JTable table;
	static Connection conn=null;
	String databaseName = "";
	String url;
	String userName;
	String passWord;
	displayFines() throws SQLException
	{
		start();
	}
	
	public static void main(String[] args) throws SQLException
	{
		displayFines dfines = new displayFines();
	}
	
		void start() throws SQLException
		{
			frame=new JFrame("LBMS");
			frame.setSize(500,500);
			frame.setLocation(20, 50);
			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			panel=new JPanel();
			panel.setBorder(new EmptyBorder(10,10,10,10));
			setContentPane(panel);
			GridBagLayout gpanel=new GridBagLayout();
			gpanel.columnWidths=new int[]{0,0};
			gpanel.rowHeights = new int[]{0, 0, 0};
			gpanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gpanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gpanel);
			
			JButton close=new JButton("Back");
			close.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					frame.setVisible(false);
					new LibMain();
				}
			});
			panel.add(close);
			
			JLayeredPane lPane=new JLayeredPane();
			GridBagConstraints glayer = new GridBagConstraints();
			glayer.insets = new Insets(0, 0, 5, 0);
			glayer.fill = GridBagConstraints.BOTH;
			glayer.gridx = 0;
			glayer.gridy = 0;
			panel.add(lPane, glayer);
			
			table=new JTable();
			JScrollPane sPane=new JScrollPane(table);
			sPane.setBounds(6,6,1100,600);
			lPane.add(sPane);
			sPane.setViewportView(table);
		
			
			try{		
				databaseName = "lbms";
	        	url = "jdbc:mysql://localhost:3306/" + databaseName;
	        	userName = "root"; /* Use whatever user account you prefer */
	        	passWord = "root"; /* Include the password for the account of the previous line. */

	        	conn = DriverManager.getConnection(url,userName,passWord);
				Statement stmt=conn.createStatement();			
				CallableStatement myCall = conn.prepareCall("{call finedisplayprocedure()} ");
	            myCall.executeQuery();
	            ResultSet rs = myCall.getResultSet();
	            
				table.setModel(DbUtils.resultSetToTableModel(rs));
				table.setEnabled(false);
				
				table.getColumnModel().getColumn(0).setPreferredWidth(150);
				table.getColumnModel().getColumn(1).setPreferredWidth(500);
				table.getColumnModel().getColumn(2).setPreferredWidth(350);
				table.getColumnModel().getColumn(3).setPreferredWidth(100);
				
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				}
				catch(SQLException e)
				{
					System.out.println(e.getMessage());
				}
			
			frame.add(panel);
			frame.setVisible(true);
			
		}

	}


