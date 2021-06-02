package Views;

import common.User;

import javax.swing.*;

import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class adding extends JFrame implements ActionListener {
    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passLabel;
    private static JPasswordField passText;
    private static JButton button;
    private static JLabel success;

    private static Connection con;

    public adding() {
        JLabel label = new JLabel();
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(null);

        frame.setTitle("Action time - Creating User");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,400));

        JLabel userNameLabel = new JLabel("Name");
        userNameLabel.setBounds(40,50,80,25);
        panel.add(userNameLabel);

        JTextField userNameField = new JTextField(20);
        userNameField.setBounds(140,50,165,25);
        panel.add(userNameField);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(40,90,80,25);
        panel.add(userLabel);

        JTextField userField = new JTextField(20);
        userField.setBounds(140,90,165,25);
        panel.add(userField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(40,130,80,25);
        panel.add(passLabel);

        JTextField passField = new JTextField(20);
        passField.setBounds(140,130,165,25);
        panel.add(passField);

        JButton button = new JButton("Create User");
        button.setBounds(140,210,150, 20);
        button.addActionListener(this);
        panel.add(button);

        JRadioButton userButton = new JRadioButton("User");
        userButton.setBounds(210,170,80,25);
        JRadioButton adminButton = new JRadioButton("Admin");
        adminButton.setBounds(100,170,80,25);
        ButtonGroup group = new ButtonGroup();
        group.add(userButton);
        group.add(adminButton);

        panel.add(userButton);
        panel.add(adminButton);




        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }





//    private void newUser() throws SQLException {
//        if (firstname.getText() != null ){
//            User p = new User(firstname.getText(), lastname.getText(), username
//                    .getText(), team.getText());
//            System.out.println("new user created " + p.firstName);
//            firstname.setText("");
//            lastname.setText("");
//            username.setText("");
//            team.setText("");
//
//            DBConnect.addUser(connection,p);
//
//        }
//
//    }

    // create one Frame
    public static void main(String[] args) {
        new adding();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //verify user inputs
        //get response from server
        //add new user to database
        //later on hash the password as well
    }
}


