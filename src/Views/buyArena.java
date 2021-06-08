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


public class buyArena extends JFrame implements Serializable {

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

    public buyArena(UserData userData, OrganisationData orgData) {
        this.userData = userData;
        this.orgData = orgData;
        array = new String[orgData.getSize()];

        initUI();

        addButtonListeners(new ButtonListener());

        // decorate the frame and make it visible
        setTitle("Buy/Sell");
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
        contentPane.add(sellListPane());
        //user inputs
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeUserFieldPanel());

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeButtonsPanel());

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


    private JScrollPane sellListPane() {
        userList = new JList(userData.getModel());
        userList.setFixedCellWidth(200);

        JScrollPane scroller = new JScrollPane(userList);

        scroller.setViewportView(userList);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setMaximumSize(new Dimension(250, 250));

        return scroller;
    }

    private JPanel makeUserFieldPanel() {
        JPanel userPanel = new JPanel();
        GroupLayout layout = new GroupLayout(userPanel);
        userPanel.setLayout(layout);

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Label of fields
        itemName = new JLabel("Item Name ");
        itemquality = new JLabel("Quantity");

        // Text Fields
        userField = new JTextField(30);
        userField.setPreferredSize(new Dimension(30, 20));
        passField = new JTextField(30);
        passField.setPreferredSize(new Dimension(30, 20));

        // Create a sequential group for the horizontal axis
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // Two parallel groups 1. contains labels and the other the fields
        hGroup.addGroup(layout.createParallelGroup().addComponent(itemName).addComponent(itemquality));
        hGroup.addGroup(layout.createParallelGroup().addComponent(userField).addComponent(passField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(itemName).addComponent(userField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(itemquality).addComponent(passField));
        layout.setVerticalGroup(vGroup);
        return userPanel;
    }

    private JPanel makeButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        createButton = new JButton("Create");
        deleteButton = new JButton("Delete");
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(createButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        return buttonPanel;
    }
    private void clearFields() {
        userField.setText("");
        passField.setText("");

    }

    private void checkListSize() {

        deleteButton.setEnabled(userData.getSize() != 0);
    }

    private void addButtonListeners(ActionListener listener) {
        createButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
    }

    private class ButtonListener implements ActionListener {
        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            if (source == createButton) {
                createPressed();
            }
            else if (source == deleteButton) {
                deletePressed();
            }
        }
        /**
         * When the create user button is pressed, add the user information to the database
         * or display error
         */
        private void createPressed() {
//               User u = new User();
//            String selectedOrg = String.valueOf(comboBox.getSelectedItem());
//
//            // If all fields are filled in continue
////            if (userField.getText() != null && !userField.getText().equals("")
////                    && !passField.equals("")) {
//
//                // Add user to database and clear fields
//                userData.add(u);
//                clearFields();
                JOptionPane.showMessageDialog(null, String.format("User successfully added"));
            //}

            // Not all fields are filled in
//            else {
//                JOptionPane.showMessageDialog(new JFrame(), "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
////            }
//            checkListSize();
        }

        private void deletePressed() {
            int index = userList.getSelectedIndex();
            String username = userField.getText();
            userData.remove(userList.getSelectedValue());
            clearFields();
            index--;
            if (index == -1) {
                if (userData.getSize() != 0) {
                    index = 0;
                }
            }
            userList.setSelectedIndex(index);
            checkListSize();
            JOptionPane.showMessageDialog(null, String.format("User '%s' successfully deleted", username));
        }
    }


    public static void main(String[] args) {

        new buyArena(new UserData(new NetworkDataSource()), new OrganisationData(new NetworkDataSource()));
    }

}
