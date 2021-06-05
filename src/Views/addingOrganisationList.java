package Views;

import client.NetworkDataSource;
import common.Organisation;
import common.sql.AssetTypeData;
import common.sql.OrganisationData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

public class addingOrganisationList extends JFrame implements Serializable{

    private static final long serialVersionUID = 62L;
    private JList orgList;
    private JComboBox comboBox;

    private JLabel orgLabel;
    private JTextField orgField;
    private JLabel orgCredits;
    private JTextField orgCreditsField;
    private JLabel assetQuantity;
    private JTextField assetQField;

    private JLabel assetLabel;

    Object[] array;

    private JButton createButton;
    private JButton deleteButton;


    OrganisationData orgData;
    AssetTypeData assetTypeData;

    /**
     * Constructor sets up UI, adds button listeners and displays
     *
     * @param orgData the user data from the database
     */
    public addingOrganisationList(OrganisationData orgData, AssetTypeData assetTypeData) {
        this.orgData = orgData;
        this.assetTypeData = assetTypeData;
        array = new String[assetTypeData.getSize()];

        initUI();
        checkListSize();

        // Add listeners to components
        addButtonListeners(new Views.addingOrganisationList.ButtonListener());
        addNameListListener(new Views.addingOrganisationList.NameListListener());
        addClosingListener(new Views.addingOrganisationList.ClosingListener());


        // decorate the frame and make it visible
        setTitle("Create Organisation");
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    /**
     * Initialises the UI placing the panels in a box layout with vertical
     * alignment spacing each panel.
     */
    private void initUI() {
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //List of Organisations
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeNameListPane());

        //Input area
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeUserFieldPanel());

        //dropdown for assets
        contentPane.add(Box.createVerticalStrut(5));
        contentPane.add(makeDropDownPanel());

        //buttons
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeButtonsPanel());

        contentPane.add(Box.createVerticalStrut(20));
    }

    private JScrollPane makeNameListPane() {
        orgList = new JList(orgData.getModel());
        //ListModel model = data.getModel();
        orgList.setFixedCellWidth(200);

        JScrollPane scroller = new JScrollPane(orgList);
        scroller.setViewportView(orgList);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));

        return scroller;
    }

    private JPanel makeDropDownPanel() {
        ListModel model = assetTypeData.getModel();
        for (int i = 0; i < assetTypeData.getSize(); i++) {
            array[i] = model.getElementAt(i).toString();

        }
        comboBox = new JComboBox(array);
        comboBox.setBackground(Color.white);
        JPanel panel = new JPanel();
        assetLabel = new JLabel("Asset");
        panel.add(assetLabel);
        panel.add(comboBox);

        return panel;
    }


    /**
     * Makes a JPanel containing the Organization name, credits, asset and quantity fields to be
     * recorded.
     *
     * @return a panel containing the user fields
     */
    private JPanel makeUserFieldPanel() {
        JPanel orgPanel = new JPanel();
        GroupLayout layout = new GroupLayout(orgPanel);
        orgPanel.setLayout(layout);

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Label of fields
        orgLabel = new JLabel("Organisation Name");
        orgCredits = new JLabel("Organisation Credits");
        assetQuantity = new JLabel("Asset Quantity");

        // Text Fields
        orgField = new JTextField(30);
        orgField.setPreferredSize(new Dimension(30, 20));
        orgCreditsField = new JTextField(30);
        orgCreditsField.setPreferredSize(new Dimension(30, 20));
        assetQField = new JTextField(30);
        assetQField.setPreferredSize(new Dimension(30, 20));

        // Create a sequential group for the horizontal axis
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // Two parallel groups 1. contains labels and the other the fields
        hGroup.addGroup(layout.createParallelGroup().addComponent(orgLabel).addComponent(orgCredits).addComponent(assetQuantity));
        hGroup.addGroup(layout.createParallelGroup().addComponent(orgField).addComponent(orgCreditsField).addComponent(assetQField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(orgLabel).addComponent(orgField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(orgCredits).addComponent(orgCreditsField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetQuantity).addComponent(assetQField));
        layout.setVerticalGroup(vGroup);
        return orgPanel;
    }



    /**
     * Adds the buttons to the panel
     *
     * @return a panel containing the create organisation button
     */
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
        orgField.setText("");
        orgCreditsField.setText("");
        assetQField.setText("");
    }

    /**
     * Checks the size of the organisation table determining the state of the delete button
     */
    private void checkListSize() {
        deleteButton.setEnabled(orgData.getSize() != 0);
    }

    /**
     * Display the organisations details in the fields
     * int i = Integer.parseInt(s.trim());
     * @param org
     */
    private void display(Organisation org) {
        if (org != null) {
            orgField.setText(org.getName());
            orgCreditsField.setText(String.valueOf(org.getCredits()));
            assetQField.setText(String.valueOf(org.getQuantity()));
        }
        comboBox.setSelectedItem(org.getAsset());
    }

    private void addButtonListeners(ActionListener listener) {
        createButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
    }

    private void addNameListListener(ListSelectionListener listener) {
        orgList.addListSelectionListener(listener);
    }

    private void addClosingListener(WindowListener listener) {
        addWindowListener(listener);
    }

    public static void main(String[] args) {
        new addingOrganisationList(new OrganisationData(new NetworkDataSource()), new AssetTypeData());
    }



    /**
     * Handles events for the buttons on the UI
     *
     * @author Dylan
     */
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

            String selectedValue = comboBox.getSelectedItem().toString();
            //Integer orgCredits =

            // If all fields are filled in continue
            if (orgField.getText() != null && !orgField.getText().equals("")
                    && !orgCreditsField.equals("") && !assetQField.equals("")) {

                Organisation o = new Organisation(orgField.getText(), Integer.valueOf(orgCreditsField.getText()), selectedValue, Integer.valueOf(assetQField.getText()));

                // Add user to database and clear fields
                orgData.add(o);
                clearFields();
                JOptionPane.showMessageDialog(null, String.format("Organisation '%s' successfully added", o.getName()));
            }

            // Not all fields are filled in
            else {
                JOptionPane.showMessageDialog(new JFrame(), "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
            }
            checkListSize();
        }

        private void deletePressed() {
            int index = orgList.getSelectedIndex();
            String orgFieldText = orgField.getText();
            orgData.remove(orgList.getSelectedValue());
            clearFields();
            index--;
            if (index == -1) {
                if (orgData.getSize() != 0) {
                    index = 0;
                }
            }
            orgList.setSelectedIndex(index);
            checkListSize();
            JOptionPane.showMessageDialog(null, String.format("Organisation '%s' successfully deleted", orgFieldText));
        }
    }

    private class NameListListener implements ListSelectionListener {
        /**
         * @see ListSelectionListener#valueChanged(ListSelectionEvent)
         */
        public void valueChanged(ListSelectionEvent e) {
            if (orgList.getSelectedValue() != null
                    && !orgList.getSelectedValue().equals("")) {
                display(orgData.get(orgList.getSelectedValue()));
            }
        }
    }

    /**
     * Implements the windowClosing method from WindowAdapter to persist the contents of the
     * user table
     */
    private class ClosingListener extends WindowAdapter {
        /**
         * @see WindowAdapter#windowClosing(WindowEvent)
         */
        public void windowClosing(WindowEvent e) {
            orgData.persist();
            System.exit(0);
        }
    }
}
