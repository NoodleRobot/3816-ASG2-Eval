package edu;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import javax.swing.*;
import javax.swing.event.*;

public class Eval extends JFrame implements ActionListener, ItemListener //dont need the itemlistener unless adding radio buttons
{

    //begin element declaration
    public JLabel teamLabel;
    private JComboBox teamComboBox;
    //public JComboBox imagesComboBox;
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
    private JTextField calcAvgField;

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
    public static void main(String args[]) {
        // check command-line arguments
        //if ( args.length == 2 )
        //{
        // get command-line arguments
        String databaseDriver = "org.apache.derby.jdbc.ClientDriver"; //connection string
        //String databaseDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
        String databaseURL = "jdbc:derby://localhost:1527/Evaluation2;create=true";

        // create new Eval
        Eval eval = new Eval(databaseDriver, databaseURL);
        eval.createUserInterface();
        eval.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //}
        //else // invalid command-line arguments
        //{
        //   System.out.println( "Usage: java EVAL needs databaseDriver databaseURL" );
        //}
    }

    //CONSTRUCTOR: WE SET UP OUR DATABASE HERE THEN MAKE A CALL
    //TO A FUNCTION CALLED CREATEUSERINTERFACE TO BUILD OUR GUI
    public Eval(String databaseDriver, String databaseURL) {
        // establish connection to database
        try {
            // load Sun driver
            //Class.forName( databaseDriver );
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            // connect to database
            myConnection = DriverManager.getConnection(databaseURL);  //building highway

            // create Statement for executing SQL
            myStatement = myConnection.createStatement();  //
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        //catch ( ClassNotFoundException exception )
        // {
        //   exception.printStackTrace();
        //}
        // set up accountNumberJComboBox

        // createUserInterface(); // set up GUI
    }

    private void createUserInterface() {
        // get content pane for attaching GUI components
        Container contentPane = getContentPane();

        contentPane.setLayout(null);

        //begin team panel: holds team label + team dropdown combobox
        teamPanel = new JPanel();
        teamPanel.setBounds(40, 20, 276, 50);
        teamPanel.setBorder(BorderFactory.createEtchedBorder());
        teamPanel.setLayout(null);
        contentPane.add(teamPanel);

        teamLabel = new JLabel();
        teamLabel.setBounds(25, 15, 100, 20);
        teamLabel.setText("Teams:");
        teamPanel.add(teamLabel);

        teamComboBox = new JComboBox();
        teamComboBox.setBounds(150, 15, 96, 25);
        teamComboBox.addItem(""); //empty string so nothing shows up by default
        teamComboBox.setSelectedIndex(0);
        teamPanel.add(teamComboBox);
        //end team panel

        //begin button panel: holds submit and clear button    
        buttonPanel = new JPanel();
        buttonPanel.setBounds(200, 705, 276, 60); //(left-right, up-down, width, height)
        buttonPanel.setBorder(BorderFactory.createEtchedBorder());
        buttonPanel.setLayout(null);
        contentPane.add(buttonPanel);

        submitButton = new JButton("Submit");
        submitButton.setBounds(50, 10, 78, 40);
        buttonPanel.add(submitButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(150, 10, 78, 40);
        buttonPanel.add(clearButton);
        contentPane.add(buttonPanel);
        //end button panel

        //begin Q1 panel: holds label and slider
        Q1Panel = new JPanel();
        Q1Panel.setBounds(40, 75, 600, 100);
        Q1Panel.setBorder(BorderFactory.createEtchedBorder());
        Q1Panel.setLayout(null);
        contentPane.add(Q1Panel);

        Q1Label = new JLabel();
        Q1Label.setBounds(25, 15, 100, 20);
        Q1Label.setText("Q1 Technical:");
        Q1Panel.add(Q1Label);

//        //begin Q2 panel: holds label and slider
        Q2Panel = new JPanel();
        Q2Panel.setBounds(40, 180, 600, 100);
        Q2Panel.setBorder(BorderFactory.createEtchedBorder());
        Q2Panel.setLayout(null);
        contentPane.add(Q2Panel);

        Q2Label = new JLabel();
        Q2Label.setBounds(25, 15, 100, 20);
        Q2Label.setText("Q2 Usefulness:");
        Q2Panel.add(Q2Label);

        //begin Q3 panel: holds label and slider
        Q3Panel = new JPanel();
        Q3Panel.setBounds(40, 285, 600, 100);
        Q3Panel.setBorder(BorderFactory.createEtchedBorder());
        Q3Panel.setLayout(null);
        contentPane.add(Q3Panel);

        Q3Label = new JLabel();
        Q3Label.setBounds(25, 15, 100, 20);
        Q3Label.setText("Q3 Clarity:");
        Q3Panel.add(Q3Label);

        //begin Q4 panel: holds label and slider
        Q4Panel = new JPanel();
        Q4Panel.setBounds(40, 390, 600, 100);
        Q4Panel.setBorder(BorderFactory.createEtchedBorder());
        Q4Panel.setLayout(null);
        contentPane.add(Q4Panel);

        Q4Label = new JLabel();
        Q4Label.setBounds(25, 15, 100, 20);
        Q4Label.setText("Q4 Overall:");
        Q4Panel.add(Q4Label);

        //begin comment panel: holds comment label and comment field
        commentPanel = new JPanel();
        commentPanel.setBounds(40, 495, 600, 100);
        commentPanel.setBorder(BorderFactory.createEtchedBorder());
        commentPanel.setLayout(null);
        contentPane.add(commentPanel);
        
        commentLabel = new JLabel();
        commentLabel.setBounds(25, 15, 100, 20);
        commentLabel.setText("Comments:");
        commentPanel.add(commentLabel);
        
        commentArea = new JTextArea("Add team member names here.");
        commentArea.setBounds(120, 10, 450 ,80);
        commentPanel.add(commentArea);
    
        //begin calcAvg panel: holds label, button and field
        calcAvgPanel = new JPanel();
        calcAvgPanel.setBounds(40, 600, 600, 100);
        calcAvgPanel.setBorder(BorderFactory.createEtchedBorder());
        calcAvgPanel.setLayout(null);
        contentPane.add(calcAvgPanel);
        
        calcAvgButton = new JButton("Calc Avg");
        calcAvgButton.setBounds(75, 35, 100, 40);
        calcAvgPanel.add(calcAvgButton);
        
        calcAvgLabel = new JLabel();
        calcAvgLabel.setBounds(25, 10, 200, 20);
        calcAvgLabel.setText("Computed average from above:");
        calcAvgPanel.add(calcAvgLabel);
        
        calcAvgField = new JTextField();
        calcAvgField.setBounds(220, 10, 100, 30);
        calcAvgPanel.add(calcAvgField);
        
        submitButton.addActionListener(this);
        //teamComboBox.addItemListener(this);
        loadTeams();

        setTitle("EVAL");   // set title bar string
        setSize(800, 850); // set window size
        setVisible(true);  // display window
    }

    private void loadTeams() {
        // get all account numbers from database
        try {

            myResultSet = myStatement.executeQuery("SELECT DISTINCT TEAMNAME FROM APP.TEAMS");

            while (myResultSet.next()) {
                teamComboBox.addItem(myResultSet.getString("TEAMNAME"));
            }

            myResultSet.close(); // close myResultSet

        } // end try
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
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
    private void updateTeams() {
        // update balance in database
        try {

            //
            String sql = "UPDATE APP.TEAMS SET Q1TECH = " + q1 + "," + "Q2CLARITY = " + q2 + " WHERE "
                    + "TEAMNAME = " + "'" + teamname + "'";
            //String sql2 =  "UPDATE APP.TEAMEVAL" + " SET q2 = " + q2 + " WHERE " +
            //             "TEAMNAME = '" + myteamname + "'" + "and course = '" + courseName + "'";
            myStatement.executeUpdate(sql);
            //myStatement.executeUpdate(sql2);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
//        if ( event.getStateChange() == ItemEvent.SELECTED )
//        {
//                int x = teamComboBox.getSelectedIndex();
//        }
    }

}
