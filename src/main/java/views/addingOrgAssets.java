package views;

import common.Organisation;
import common.User;
import common.sql.AssetTypeData;
import common.sql.OrganisationData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Initialises the admin interface for adding assets to an Organisation to the database.
 * Listeners are included as sub-classes of this class
 *
 * @author Dylan Holmes-Brown
 */
public class addingOrgAssets extends JFrame implements Serializable {
    // Global variables
    private static final long serialVersionUID = 333L;
    private OrganisationData orgData;
    private AssetTypeData assetTypeData;
    private User user;
    private Organisation org;
    private List<String> assetList;
    private List<String> orgList;
    private List<Organisation> organisationList;

    // JSwing variables
    private JTextField assetQField;
    private JLabel assetQuantity;
    private JList list;
    private JComboBox comboBox;
    private JLabel assetLabel;

    private JButton applyButton;
    private JButton backButton;

    /**
     * Initialises the admin interface for adding assets to an Organisations from the database.
     * Listeners are included as sub-classes of this class
     *
     * @author Dylan Holmes-Brown
     */
    public addingOrgAssets(User user, Organisation org, OrganisationData orgData, AssetTypeData assetTypeData) {
        // Initialise data
        this.user = user;
        this.org = org;
        this.orgData = orgData;
        this.assetTypeData = assetTypeData;
        assetList = new ArrayList<>();
        organisationList = new ArrayList<>();
        orgList = new ArrayList<>();

        // Initialise the UI and listeners
        initUI();
        addButtonListeners(new ButtonListener());
        addClosingListener(new ClosingListener());

        // Decorate the frame and make it visible
        setTitle("Asset Trading System - Add Assets to Organisation");
        setMinimumSize(new Dimension(650, 300));
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
        contentPane.add(makeAssetListPane());

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeAssetFieldPane());

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeButtonPane());
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
    private JScrollPane makeAssetListPane() {
        // Initialise the organisation model, add the organisation object to a list
        // and organisation assets to a separate list
        ListModel model = orgData.getModel();
        for (int i = 0; i < orgData.getSize(); i++) {
            Organisation orgGet = orgData.get(model.getElementAt(i));
            if (orgGet.getName().equals(org.getName())) {
                orgList.add(orgGet.getAsset());
            }
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
     * Makes a JPanel containing the asset and quantity fields to be
     * recorded.
     *
     * @return a panel containing the organisation fields
     */
    private JPanel makeAssetFieldPane() {
        // Initialise the JPanel
        JPanel orgPanel = new JPanel();
        GroupLayout layout = new GroupLayout(orgPanel);
        orgPanel.setLayout(layout);

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Initialise the ListModel and array of all elements of the asset table
        ListModel model = assetTypeData.getModel();
        for (int i = 0; i < assetTypeData.getSize(); i++) {
            if (!orgList.contains(model.getElementAt(i).toString())) {
                assetList.add(model.getElementAt(i).toString());
            }
        }

        // Initialise the Drop down box
        comboBox = new JComboBox(assetList.toArray());
        comboBox.setBackground(Color.white);
        comboBox.setPrototypeDisplayValue("Text Size");
        assetLabel = new JLabel("Asset");

        // Initialise Labels and Fields
        assetQuantity = new JLabel("Quantity");
        assetQField = new JTextField(30);
        assetQField.setPreferredSize(new Dimension(30, 20));

        // Create a sequential group for the horizontal axis
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // Two parallel groups 1. contains labels and the other the fields
        hGroup.addGroup(layout.createParallelGroup().addComponent(assetQuantity).addComponent(assetLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(assetQField).addComponent(comboBox));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetQuantity).addComponent(assetQField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetLabel).addComponent(comboBox));
        layout.setVerticalGroup(vGroup);

        // Set Dimensions
        orgPanel.setMinimumSize(new Dimension(250, 120));
        orgPanel.setPreferredSize(new Dimension(275, 120));
        orgPanel.setMaximumSize(new Dimension(275, 120));
        return orgPanel;
    }

    /**
     * Adds the addAsset button to the Frame
     *
     * @return a panel containing the create organisation button
     */
    private JPanel makeButtonPane() {
        // Initialise the JPanel and create button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        applyButton = new JButton("Add Asset");

        // Add Create button to panel
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(applyButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        return buttonPanel;
    }

    /**
     * Adds a listener to the buttons
     *
     * @param listener the listener for the buttons to use
     */
    private void addButtonListeners(ActionListener listener) {
        applyButton.addActionListener(listener);
        backButton.addActionListener(listener);
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
            if (source == applyButton) {
                applyPressed();
            } else if (source == backButton) {
                orgData.persist();
                assetTypeData.persist();
                dispose();
                new adminOptions(user);
            }
        }

        private void applyPressed() {
            // Get drop box value
            String selectedAsset = comboBox.getSelectedItem().toString();
            // Check that quantity has input
            if (assetQField != null && !assetQField.getText().equals("")
                    && selectedAsset != null) {
                // Check the org doesn't already have the asset
                if (!orgList.contains(selectedAsset)) {
                    // Get inputs and add quantity to an asset for an organisation
                    String input = assetQField.getText();
                    int inputInt = Integer.parseInt(input);
                    Organisation o = new Organisation(org.getName(), org.getCredits(), selectedAsset, inputInt);
                    orgData.add(o);
                    JOptionPane.showMessageDialog(null, String.format("Asset '%s' successfully added to Organisation '%s'", o.getAsset(), o.getName()));
                }
                // Organisation already owns the asset
                else {
                    JOptionPane.showMessageDialog(null, String.format("Asset '%s' already owned by Organisation '%s'", selectedAsset, org.getName()), "Asset Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            // All fields not complete
            else {
                JOptionPane.showMessageDialog(null, "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
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
