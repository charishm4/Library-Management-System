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


public class finesDue extends JFrame{
	
	JFrame frame;
	JPanel panel;
	JTable table;
	static Connection conn=null;
	String databaseName = "";
	String url;
	String userName;
	String passWord;
	
	finesDue() throws SQLException
	{
		start();
	}
	
	public static void main(String[] args) throws SQLException
	{
		finesDue dfine = new finesDue();
	}
	
	void start() throws SQLException
	{
		frame=new JFrame("LBMS");
		frame.setSize(500,300);
		frame.setLocation(20, 50);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		panel=new JPanel();
		panel.setBorder(new EmptyBorder(10,10,10,10));
		setContentPane(panel);
		
		JLabel ldue=new JLabel("FinesDue",JLabel.CENTER);
		ldue.setFont(new Font("Times New Roman",Font.BOLD,18));
		panel.add(ldue);
		
		JLabel cid=new JLabel("CardID:",JLabel.CENTER);
		cid.setFont(new Font("Times New Roman",Font.BOLD,12));
		panel.add(cid);
		
		JTextField dueText = new JTextField();
		dueText.setFont(new Font("Ariel",Font.PLAIN,12));
		dueText.setForeground(Color.black);
		panel.add(dueText);
		dueText.setColumns(20);
		
		
		JButton dueButton=new JButton("FinesDue");
		dueButton.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent e) 
			{
				try {
					showDue(dueText.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//}
			}
		});
		panel.add(dueButton);
		//Close Button
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
		frame.add(panel);
		frame.setVisible(true);
	}
	
		
		void showDue(String keyword) throws SQLException
		{
			frame=new JFrame("Library Management System");
			frame.setSize(500,500);
			frame.setLocation(20, 50);
			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			panel=new JPanel();
			panel.setBorder(new EmptyBorder(10,10,10,10));
			setContentPane(panel);
			GridBagLayout gbl_controlPanel=new GridBagLayout();
			gbl_controlPanel.columnWidths=new int[]{0,0};
			gbl_controlPanel.rowHeights = new int[]{0, 0, 0};
			gbl_controlPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_controlPanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_controlPanel);
			
			JButton close= new JButton("Close");
			close.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false); 
					frame.dispose();
				}
			});
			
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
			
			GridBagConstraints gbc_btnClose = new GridBagConstraints();
			gbc_btnClose.fill=GridBagConstraints.HORIZONTAL;
			gbc_btnClose.insets = new Insets(0, 0, 5, 0);
			gbc_btnClose.gridx = 0;
			gbc_btnClose.gridy = 2;
			gbc_btnClose.anchor=GridBagConstraints.PAGE_END;
			panel.add(close, gbc_btnClose);	
			
			
			try{		
				databaseName = "lbms";
	        	url = "jdbc:mysql://localhost:3306/" + databaseName;
	        	userName = "root"; /* Use whatever user account you prefer */
	        	passWord = "root"; /* Include the password for the account of the previous line. */

	        	conn = DriverManager.getConnection(url,userName,passWord);
				Statement stmt=conn.createStatement();			
				CallableStatement myCall = conn.prepareCall("{call dueprocedure(?)} ");
	            myCall.setString(1, keyword);
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


