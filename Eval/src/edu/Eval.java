package edu;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import javax.swing.*;
import javax.swing.event.*;
//import org.apache.derby.jdbc.*;
//import sun.jdbc.odbc.JdbcOdbcDriver;


public class Eval extends JFrame implements ActionListener, ItemListener //dont need the itemlistener unless adding radio buttons
{
    //DECLARE THE ELEMENTS OR OBJECTS THAT YOU WILL PUT IN YOUR FRAME
    //NOTICE HOW A PANEL IS CREATED FOR EACH ONE THIS WILL MAKE IT EASIER BUILD
   
    public JLabel teamLabel;
    private JComboBox teamComboBox;
    public JComboBox imagesComboBox;
    private JPanel teamPanel;
    private JPanel buttonPanel;
    private JButton submitButton;
    private JButton clearButton;
    
    private JPanel Q1Panel;
    private JLabel Q1Label;
    private JSlider Q1Slider;
    
    private JPanel Q2Panel;
    private JLabel Q2Label;
    private JSlider Q2Slider;
    
    private JPanel Q3Panel;
    private JLabel Q3Label;
    private JSlider Q3Slider;
    
    private JPanel Q4Panel;
    private JLabel Q4Label;
    private JSlider Q4Slider;
    
    private JPanel commentPanel;
    private JLabel commentLabel;
    private JTextArea commentArea;
    
    private JPanel calcAvgPanel;
    private JButton calcAvgButton;
    private JLabel calcAvgLabel;
    //private JFormattedTextField;
    
    
    
   
   //these are fields that will be used to hold the values pulled from the interface
    String teamname;
    int q1;
    int q2;
    int q3;
    int q4;
    String comments;
    double teamavg;
    
    
    
    // instance variables used to manipulate database //this is how to connect to database
   private Connection myConnection;
   private Statement myStatement; //works like a delivery truck
   private ResultSet myResultSet; 
          
   
  
   //MAIN METHOD: NOTICE WE TAKE IN THE ARGUMENTS THAT ARE
   //PASSED IN AND INSTANTIATE OUR CLASS WITH THEM
    public static void main(String args[])
    {
        // check command-line arguments
      //if ( args.length == 2 )
      //{
         // get command-line arguments
        String databaseDriver = "org.apache.derby.jdbc.ClientDriver"; //connection string
        //String databaseDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
        String databaseURL = "jdbc:derby://localhost:1527/Evaluation2;create=true";

         // create new Eval
         Eval eval = new Eval( databaseDriver, databaseURL );
         eval.createUserInterface();
         eval.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
      //}
      //else // invalid command-line arguments
      //{
      //   System.out.println( "Usage: java EVAL needs databaseDriver databaseURL" );
      //}
    }
   
    //CONSTRUCTOR: WE SET UP OUR DATABASE HERE THEN MAKE A CALL
    //TO A FUNCTION CALLED CREATEUSERINTERFACE TO BUILD OUR GUI
    public Eval(String databaseDriver, String databaseURL)
    {
        // establish connection to database
      try
      {
         // load Sun driver
         //Class.forName( databaseDriver );
         DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
         // connect to database
         myConnection = DriverManager.getConnection( databaseURL );  //building highway

         // create Statement for executing SQL
         myStatement = myConnection.createStatement();  //
      }
      catch ( SQLException exception )
      {
         exception.printStackTrace();
      }
      //catch ( ClassNotFoundException exception )
     // {
      //   exception.printStackTrace();
      //}
        // set up accountNumberJComboBox
     
       
     // createUserInterface(); // set up GUI
     
     

     
    }
   

    private void createUserInterface()
    {
      // get content pane for attaching GUI components
      Container contentPane = getContentPane();
       
      contentPane.setLayout( null ); 
      
      // INSTRUCTOR COMBO BOX SET UP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      // set up Instructor Panel
      
      // enable explicit positioning of GUI components
      
      teamPanel = new JPanel();
      teamPanel.setBounds(40, 20, 276, 48 );
      teamPanel.setBorder( BorderFactory.createEtchedBorder() );
      teamPanel.setLayout( null );
      contentPane.add( teamPanel );

      // set up Instructor Label
      teamLabel = new JLabel();
      teamLabel.setBounds( 25, 15, 100, 20 );
      teamLabel.setText( "Teams:" );
      teamPanel.add( teamLabel );

      // set up accountNumberJComboBox
      teamComboBox = new JComboBox();
      teamComboBox.setBounds( 150, 15, 96, 25 );
      teamComboBox.addItem( "" ); //empty string so nothing shows up by default
      teamComboBox.setSelectedIndex( 0 );
      teamPanel.add( teamComboBox ); 
          
      buttonPanel = new JPanel();
      buttonPanel.setBounds(40, 100, 276, 60);
      buttonPanel.setBorder( BorderFactory.createEtchedBorder() );
      buttonPanel.setLayout(null);
      
      submitButton = new JButton("Submit");
      submitButton.setBounds(50, 15, 78, 40);
      buttonPanel.add(submitButton);
      contentPane.add(buttonPanel);
      
      //begin my code
      Q1Panel = new JPanel();
      Q1Panel.setBounds(60, 40, 350, 100 );
      Q1Panel.setBorder( BorderFactory.createEtchedBorder() );
      Q1Panel.setLayout( null );
      contentPane.add(Q1Panel);
      
      Q1Label = new JLabel();
      Q1Label.setBounds( 25, 15, 100, 20 );
      Q1Label.setText( "Q1" );
      Q1Panel.add( Q1Label );
      
      Q2Panel = new JPanel();
      Q2Panel.setBounds(160, 160, 600, 100 );
      Q2Panel.setBorder( BorderFactory.createEtchedBorder() );
      Q2Panel.setLayout( null );
      contentPane.add(Q2Panel);
      
      Q2Label = new JLabel();
      Q2Label.setBounds( 25, 15, 100, 20 );
      Q2Label.setText( "Q1" );
      Q2Panel.add( Q2Label );
      
      Q3Panel = new JPanel();
      Q3Panel.setBounds(60, 40, 350, 100 );
      Q3Panel.setBorder( BorderFactory.createEtchedBorder() );
      Q3Panel.setLayout( null );
      contentPane.add(Q3Panel);
      
      Q4Panel = new JPanel();
      Q4Panel.setBounds(55, 15, 350, 100 );
      Q4Panel.setBorder( BorderFactory.createEtchedBorder() );
      Q4Panel.setLayout( null );
      contentPane.add(Q4Panel);
        
      clearButton = new JButton("Clear");
      clearButton.setBounds(175, 15, 78, 40);
      buttonPanel.add(clearButton);
      contentPane.add(buttonPanel);
      
      
      submitButton.addActionListener(this);
      //teamComboBox.addItemListener(this);
      
      loadTeams();
      
      
      setTitle( "EVAL" );   // set title bar string
      setSize( 900, 900 ); // set window size
      setVisible( true );  // display window
    }

   
    private void loadTeams()
    {
         // get all account numbers from database
      try
      {
        
          myResultSet = myStatement.executeQuery( "SELECT DISTINCT TEAMNAME FROM APP.TEAMS");
       
         while ( myResultSet.next() )
         {
               teamComboBox.addItem(myResultSet.getString( "TEAMNAME" ) );
         }

         myResultSet.close(); // close myResultSet
        

      } // end try

      catch ( SQLException exception )
      {
         exception.printStackTrace();
      }
    }

    @Override
   public void actionPerformed(ActionEvent event)
    {
        //if(event.getSource(submitButton))
        //teamname = teamComboBox.getSelectedItem().toString()
        //comments = txtcommentbox
        
        
        //JOptionPane.showMessageDialog( null, "You pressed: " + teamComboBox.getSelectedItem().toString() );
        //Object obj = teamComboBox.g
        //teamname = teamComboBox.getSelectedItem().toString();
        //int x = teamComboBox.getSelectedIndex();
       // q1 = 5;
        //q2 = 3;
        updateTeams();
        //System.out.println(teamComboBox.getSelectedIndex() + "     " + (String)teamComboBox.getSelectedItem());

    }
   
   // @Override
   /* public void itemStateChanged(ItemEvent event)
    {
       
        if ( event.getSource() == rb1 && event.getStateChange() == ItemEvent.SELECTED)
        {
            q1 = Integer.parseInt(rb1.getText());
        }
        else if (event.getSource() == rb2 && event.getStateChange() == ItemEvent.SELECTED)
        {
            q1 = Integer.parseInt(rb2.getText());
        }
        else if (event.getSource() == rb3 && event.getStateChange() == ItemEvent.SELECTED)
        {
           q1 = Integer.parseInt(rb3.getText());
        }
        else if( event.getSource() == rb1 && event.getStateChange() == ItemEvent.DESELECTED)
        {
            JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");
        }
    }*/

    private void updateTeams()
   {
      // update balance in database
      try
      {
       
          //
          String sql = "UPDATE APP.TEAMS SET Q1TECH = " + q1 + "," + "Q2CLARITY = " + q2 + " WHERE " +
                       "TEAMNAME = " + "'" + teamname + "'";
          //String sql2 =  "UPDATE APP.TEAMEVAL" + " SET q2 = " + q2 + " WHERE " +
          //             "TEAMNAME = '" + myteamname + "'" + "and course = '" + courseName + "'";
          myStatement.executeUpdate(sql);
          //myStatement.executeUpdate(sql2);
         
      }
      catch ( SQLException exception )
      {
         exception.printStackTrace();
      }
   }

    @Override
   public void itemStateChanged( ItemEvent event )
   {
//        if ( event.getStateChange() == ItemEvent.SELECTED )
//        {
//                int x = teamComboBox.getSelectedIndex();
//        }
   }

    
}