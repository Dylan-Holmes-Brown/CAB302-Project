package views;

import common.AssetType;
import common.User;
import common.sql.AssetTypeData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

/**
 * Initialises the admin interface for adding Assets to the database.
 * Listeners are included as sub-classes of this class
 *
 * @author Vipin Vijay
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */
public class addingAssetList extends JFrame implements Serializable{
    private static final long serialVersionUID = 62L;
    private AssetTypeData assetTypeData;
    private User user;

    // JSwing Variables
    private JList assetList;
    private JLabel assetLabel;
    private JTextField assetField;
    private JButton createButton;
    private JButton backButton;

    /**
     * Constructor sets up UI, adds button listeners and displays
     *
     * @param assetTypeData the user data from the database
     * @param user The signed in user
     */
    public addingAssetList(User user, AssetTypeData assetTypeData) {
        // Initialise data
        this.assetTypeData = assetTypeData;
        this.user = user;

        // Initialise the UI and listeners
        initUI();
        addButtonListeners(new ButtonListener());
        addAssetListListener(new AssetListListener());
        addClosingListener(new ClosingListener());

        // decorate the frame and make it visible
        setTitle("Asset Trading System - Create Asset");
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

        contentPane.add(Box.createVerticalStrut(15));
        contentPane.add(makeNameListPane());

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeUserFieldPanel());

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
     * Makes a JScrollPane that holds a JList for the list of names in the
     * asset types table.
     *
     * @return the scrolling name list panel
     */
    private JScrollPane makeNameListPane() {
        // Initialise the JList and JScrollerPane
        assetList = new JList(assetTypeData.getModel());
        assetList.setFixedCellWidth(200);
        JScrollPane scroller = new JScrollPane(assetList);

        // Set the scroller to display the assetList, initialise the scrollbars
        scroller.setViewportView(assetList);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Set Dimensions
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));
        return scroller;
    }

    /**
     * Makes a JPanel containing the Organization name, credits, asset and quantity fields to be
     * recorded.
     *
     * @return a panel containing the user fields
     */
    private JPanel makeUserFieldPanel() {
        // Initialise the JPanel
        JPanel userPanel = new JPanel();
        GroupLayout layout = new GroupLayout(userPanel);
        userPanel.setLayout(layout);

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Initialise labels and fields
        assetLabel = new JLabel("Asset Name");
        assetField = new JTextField(30);
        assetField.setPreferredSize(new Dimension(30, 20));

        // Create a sequential group for the horizontal axis
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // Two parallel groups 1. contains labels and the other the fields
        hGroup.addGroup(layout.createParallelGroup().addComponent(assetLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(assetField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetLabel).addComponent(assetField));
        layout.setVerticalGroup(vGroup);

        // Set Dimensions
        userPanel.setMinimumSize(new Dimension(250, 31));
        userPanel.setPreferredSize(new Dimension(275, 31));
        userPanel.setMaximumSize(new Dimension(275, 31));
        return userPanel;
    }

    /**
     * Create a JPanel with teh create button
     *
     * @return a panel containing the create asset button
     */
    private JPanel makeButtonsPanel() {
        // Initialise the JPanel and Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        createButton = new JButton("Create");

        // Add Button to panel
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(createButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        return buttonPanel;
    }

    /**
     * Sets the text in the asset field to an empty string.
     */
    private void clearFields() {
        assetField.setText("");
    }

    /**
     * Display the Asset details in the field
     *
     * @param asset the asset to display
     */
    private void display(AssetType asset) {
        // Check that asset is not null and set field to asset name
        if (asset != null) {
            assetField.setText(asset.getAsset());
        }
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
     * Adds a listener to the asset list
     *
     * @param listener the listener for the list to use
     */
    private void addAssetListListener(ListSelectionListener listener) {
        assetList.addListSelectionListener(listener);
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
     * @author Dylan
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
            }
            else if (source == backButton) {
                // Persist the data, close the frame and return to the admin options
                assetTypeData.persist();
                dispose();
                new adminOptions(user);
            }
        }

        /**
         * When the create user button is pressed, add the asset information to the database
         * or display error
         */
        private void createPressed() {
            // If all fields are filled in continue
            if (assetField.getText() != null && !assetField.getText().equals("")) {
                // Add asset to database and clear fields
                AssetType asset = new AssetType(assetField.getText());
                assetTypeData.add(asset);
                clearFields();

                // Display message if the asset is added
                if (assetTypeData.get(assetField.getText()) != null) {
                    JOptionPane.showMessageDialog(null, String.format("Asset '%s' successfully added", asset.getAsset()));
                }
            }
            // Not all fields are filled in, display error
            else {
                JOptionPane.showMessageDialog(null, "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Implements a ListSelectionListener for making the UI respond when a
     * different asset is selected from the list.
     *
     * @author Dylan Holmes-Brown
     */
    private class AssetListListener implements ListSelectionListener {
        /**
         * @see ListSelectionListener#valueChanged(ListSelectionEvent)
         */
        public void valueChanged(ListSelectionEvent e) {
            // Check to see that the selected item is not null,
            // and does not have an empty string
            if (assetList.getSelectedValue() != null
                    && !assetList.getSelectedValue().equals("")) {
                // Display the asset
                display(assetTypeData.get(assetList.getSelectedValue()));
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
            assetTypeData.persist();
            System.exit(0);
        }
    }
}
