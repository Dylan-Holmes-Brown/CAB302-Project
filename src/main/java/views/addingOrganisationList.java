package views;

import common.Organisation;
import common.User;
import common.sql.AssetTypeData;
import common.sql.OrganisationData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Initialises the admin interface for adding Organisations to the database.
 * Listeners are included as sub-classes of this class
 *
 * @author Vipin Vijay
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */
public class addingOrganisationList extends JFrame implements Serializable{
    private static final long serialVersionUID = 62L;

    private OrganisationData orgData;
    private AssetTypeData assetTypeData;
    private User user;
    private Object[] array;
    private List<String> orgList;

    // JSwing Variables
    private JList list;
    private JComboBox comboBox;

    private JLabel orgLabel;
    private JTextField orgField;
    private JLabel orgCredits;
    private JTextField orgCreditsField;
    private JLabel assetQuantity;
    private JTextField assetQField;
    private JLabel assetLabel;

    private JButton createButton;
    private JButton backButton;

    /**
     * Constructor sets up UI, adds button listeners and displays
     *
     * @param user the signed in user
     * @param orgData the organisation data from the database
     * @param assetTypeData the asset type data from the database
     */
    public addingOrganisationList(User user, OrganisationData orgData, AssetTypeData assetTypeData) {
        // Initialise data
        this.orgData = orgData;
        this.assetTypeData = assetTypeData;
        this.user = user;
        array = new String[assetTypeData.getSize()];
        orgList = new ArrayList<>();

        // Initialise the UI and listeners
        initUI();
        checkAssetSize();
        addButtonListeners(new ButtonListener());
        addOrganisationListListener(new OrganisationListListener());
        addClosingListener(new ClosingListener());

        // credits field listener to to make sure key pressed is number or backspace
        orgCreditsField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Check the key pressed and set the field to editable
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyCode() == 8) {
                    orgCreditsField.setEditable(true);
                }
                // Set the field to uneditable if the key pressed is outside the range
                else {
                    orgCreditsField.setEditable(false);
                }
            }
        });
        // asset quantity field listener to to make sure key pressed is number or backspace
        assetQField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Check the key pressed and set the field to editable
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyCode() == 8) {
                    assetQField.setEditable(true);
                }
                // Set the field to uneditable if the key pressed is outside the range
                else {
                    assetQField.setEditable(false);
                }
            }
        });
        // Decorate the frame and make it visible
        setTitle("Asset Trading System - Create Organisation");
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
        // Create a container for the panels and set the layout
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // Add panels to container with padding
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeReturnPane());

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeOrgListPane());

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeOrgFieldPanel());

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeButtonsPanel());
        contentPane.add(Box.createVerticalStrut(20));
    }

    /**
     * Create a JPanel with return button in the top right corner
     *
     * @return the created JPanel
     */
    private JPanel makeReturnPane() {
        // Initialise the JPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Initialise the button and add to the panel
        backButton = new JButton("Return to Menu");
        buttonPanel.add(backButton);
        return buttonPanel;
    }

    /**
     * Makes a JScrollPane that holds a JList for the list of organisations in the
     * organisation types table.
     *
     * @return the scrolling organisation list panel
     */
    private JScrollPane makeOrgListPane() {
        ListModel model = orgData.getModel();
        for (int i = 0; i < orgData.getSize(); i++) {
            Organisation org = orgData.get(model.getElementAt(i));
            orgList.add(org.getName());
        }
        // Initialise the JList and JScrollerPane
        list = new JList(orgList.toArray());
        list.setFixedCellWidth(200);
        JScrollPane scroller = new JScrollPane(list);

        // Set the scroller to display the orgList, initialise the scroll bars
        scroller.setViewportView(list);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Set Dimensions
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));
        return scroller;
    }

    /**
     * Makes a JPanel containing the Organisation name, credits, asset and quantity fields to be
     * recorded.
     *
     * @return a panel containing the organisation fields
     */
    private JPanel makeOrgFieldPanel() {
        // Initialise the JPanel
        JPanel orgPanel = new JPanel();
        GroupLayout layout = new GroupLayout(orgPanel);
        orgPanel.setLayout(layout);

        // Initialise the ListModel and array of all elements of the asset table
        ListModel model = assetTypeData.getModel();
        for (int i = 0; i < assetTypeData.getSize(); i++) {
            array[i] = model.getElementAt(i).toString();
        }
        // Initialise the Drop down box
        comboBox = new JComboBox(array);
        comboBox.setBackground(Color.white);
        comboBox.setPrototypeDisplayValue("Text Size");
        assetLabel = new JLabel("Asset");

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Initialise Labels and Fields
        orgLabel = new JLabel("Organisation Name");
        orgCredits = new JLabel("Organisation Credits");
        assetQuantity = new JLabel("Asset Quantity");
        orgField = new JTextField(30);
        orgField.setPreferredSize(new Dimension(30, 20));
        orgCreditsField = new JTextField(30);
        orgCreditsField.setPreferredSize(new Dimension(30, 20));
        assetQField = new JTextField(30);
        assetQField.setPreferredSize(new Dimension(30, 20));

        // Create a sequential group for the horizontal axis
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // Two parallel groups 1. contains labels and the other the fields
        hGroup.addGroup(layout.createParallelGroup().addComponent(orgLabel).addComponent(orgCredits).addComponent(assetLabel).addComponent(assetQuantity));
        hGroup.addGroup(layout.createParallelGroup().addComponent(orgField).addComponent(orgCreditsField).addComponent(comboBox).addComponent(assetQField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(orgLabel).addComponent(orgField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(orgCredits).addComponent(orgCreditsField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetLabel).addComponent(comboBox));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetQuantity).addComponent(assetQField));
        layout.setVerticalGroup(vGroup);

        // Set Dimensions
        orgPanel.setMinimumSize(new Dimension(250, 120));
        orgPanel.setPreferredSize(new Dimension(275, 120));
        orgPanel.setMaximumSize(new Dimension(275, 120));

        return orgPanel;
    }

    /**
     * Adds the create button to the Frame
     *
     * @return a panel containing the create organisation button
     */
    private JPanel makeButtonsPanel() {
        // Initialise the JPanel and create button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        createButton = new JButton("Create");

        // Add Create button to panel
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(createButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        return buttonPanel;
    }

    /**
     * Sets the text of all fields to be an empty string
     */
    private void clearFields() {
        orgField.setText("");
        orgCreditsField.setText("");
        assetQField.setText("");
    }

    /**
     * Display the Organisation details in the field
     *
     * @param org the organisation to display
     */
    private void display(Organisation org) {
        // Check that org is not null and set fields to org details
        if (org != null) {
            orgField.setText(org.getName());
            orgCreditsField.setText(String.valueOf(org.getCredits()));
            assetQField.setText(String.valueOf(org.getQuantity()));
            comboBox.setSelectedItem(org.getAsset());
        }
    }

    /**
     * Checks the size of the organisation table determining the state of the combobox and member radiobutton
     */
    private void checkAssetSize() {
        comboBox.setEnabled(assetTypeData.getSize() != 0);
    }

    /**
     * Adds a listener to the buttons
     *
     * @param listener the listener for the buttons to use
     */
    private void addButtonListeners(ActionListener listener) {
        createButton.addActionListener(listener);
        backButton.addActionListener(listener);
    }

    /**
     * Adds a listener to the organisation list
     *
     * @param listener the listener for the list to use
     */
    private void addOrganisationListListener(ListSelectionListener listener) {
        list.addListSelectionListener(listener);
    }

    /**
     * Adds a listener to the JFrame
     *
     * @param listener the listener for the JFrame to use
     */
    private void addClosingListener(WindowListener listener) {
        addWindowListener(listener);
    }

    /**
     * Handles events for the buttons on the UI
     *
     * @author Dylan Holmes-Brown
     */
    private class ButtonListener implements ActionListener {
        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            // Get the button source and check which method to goto according to the source
            JButton source = (JButton) e.getSource();
            if (source == createButton) {
                createPressed();
                // Persist data dispose the frame and return to admin options menu
                orgData.persist();
                assetTypeData.persist();
                dispose();
                new adminOptions(user);
            }
            else if (source == backButton) {
                // Persist data dispose the frame and return to admin options menu
                orgData.persist();
                assetTypeData.persist();
                dispose();
                new adminOptions(user);
            }
        }

        /**
         * When the create user button is pressed, add the organisation information
         * to the database or display error
         */
        private void createPressed() {
            // Get drop box value
            String selectedValue = comboBox.getSelectedItem().toString();

            // If all fields are filled in continue
            if (orgField.getText() != null && !orgField.getText().equals("")
                    && !orgCreditsField.getText().equals("") && selectedValue != null && !assetQField.getText().equals("")) {
                // Add organisation to database and clear fields
                int credits = Integer.valueOf(orgCreditsField.getText());
                int quantity = Integer.valueOf(assetQField.getText());
                Organisation o = new Organisation(orgField.getText(), credits, selectedValue, quantity);
                orgData.add(o);
                clearFields();
                JOptionPane.showMessageDialog(null, String.format("Organisation '%s' successfully added", o.getName()));
            }
            // Not all fields are filled in
            else {
                JOptionPane.showMessageDialog(null, "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Implements a ListSelectionListener for making the UI respond when a
     * different organisation is selected from the list.
     *
     * @author Dylan Holmes-Brown
     */
    private class OrganisationListListener implements ListSelectionListener {
        /**
         * @see ListSelectionListener#valueChanged(ListSelectionEvent)
         */
        public void valueChanged(ListSelectionEvent e) {
            // Check to see that the selected item is not null,
            // and does not have an empty string
            if (list.getSelectedValue() != null
                    && !list.getSelectedValue().equals("")) {
                // Display the organisation
                display(orgData.get(list.getSelectedIndex() + 1));
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
            // Persist the data and stop the application
            orgData.persist();
            assetTypeData.persist();
            System.exit(0);
        }
    }
}
