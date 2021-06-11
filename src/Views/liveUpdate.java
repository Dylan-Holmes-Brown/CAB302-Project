package Views;


import client.NetworkDataSource;
import common.HashPassword;
import common.User;
import common.sql.OrganisationData;
import common.sql.UserData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;


/**
 * @author Vipin Vijay
 */

public class liveUpdate extends JFrame implements Serializable {

    private static final long serialVersionUID = 62L;
    private JList userList;
    private JComboBox comboBox;

    private JLabel itemName;
    private JTextField userField;
    private JLabel itemquality;
    private JTextField passField;

    private JLabel orgLabel;

    Object[] array;

    private JButton createButton;
    private JButton deleteButton;

    UserData userData;
    OrganisationData orgData;

    public liveUpdate(UserData userData, OrganisationData orgData) {
        this.userData = userData;
        this.orgData = orgData;
        array = new String[orgData.getSize()];

        initUI();

        // decorate the frame and make it visible
        setTitle("Live Update");
        setMinimumSize(new Dimension(900, 700));
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void initUI() {
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //list of users
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(buyListPane());


        contentPane.add(Box.createVerticalStrut(20));
    }

    private JScrollPane buyListPane() {
        userList = new JList(userData.getModel());
        userList.setFixedCellWidth(200);

        JScrollPane scroller = new JScrollPane(userList);
        scroller.setViewportView(userList);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));

        return scroller;
    }

    public static void main(String[] args) {

        new liveUpdate(new UserData(new NetworkDataSource()), new OrganisationData(new NetworkDataSource()));
    }

}
