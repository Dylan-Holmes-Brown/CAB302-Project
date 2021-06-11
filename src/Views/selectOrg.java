package Views;

import client.NetworkDataSource;
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
 * Initialises the admin interface for selecting an Organisations from the database.
 * Listeners are included as sub-classes of this class
 *
 * @author Dylan Holmes-Brown
 */
public class selectOrg extends JFrame implements Serializable {
    // Global Variables
    private static final long serialVersionUID = 555L;
    private OrganisationData orgData;
    private User user;
    private List<String> orgList;

    // JSwing Variables
    private JComboBox orgBox;
    private JLabel orgLabel;
    private JButton applyButton;
    private JButton backButton;

    /**
     * Constructor sets up UI, adds button listeners and displays
     *
     * @param user the signed in user
     * @param orgData the organisation data from the database
     */
    public selectOrg(User user, OrganisationData orgData) {
        // Initialise Data
        this.user = user;
        this.orgData = orgData;
        orgList = new ArrayList<>();

        // Initialise the UI and listeners
        initUI();
        addButtonListeners(new ButtonListener());
        addClosingListener(new ClosingListener());

        // Decorate the frame and make it visible
        setTitle("Asset Trading System - Select Organisation");
        setMinimumSize(new Dimension(300, 300));
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
        contentPane.add(makeGetOrgPane());

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
     * Makes a JPanel containing the drop down box holding all organisations
     *
     * @return a panel containing the organisation drop box
     */
    private JPanel makeGetOrgPane() {
        // Get the org data model and add all elements to the org list
        ListModel model = orgData.getModel();
        for (int i = 0; i < orgData.getSize(); i++) {
            Organisation org = orgData.get(model.getElementAt(i));
            if (!orgList.contains(org.getName())) {
                orgList.add(org.getName());
            }
        }

        // Initialise the Drop down box and panel
        orgBox = new JComboBox(orgList.toArray());
        orgBox.setBackground(Color.white);
        JPanel panel = new JPanel();
        orgLabel = new JLabel("Organisation");
        panel.add(orgLabel);
        panel.add(orgBox);
        return panel;
    }

    /**
     * Adds the apply button to the Frame
     *
     * @return a panel containing the create organisation button
     */
    private JPanel makeButtonPane() {
        // Initialise the JPanel and create button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        applyButton = new JButton("Apply");

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
                Integer selectedOrg = orgBox.getSelectedIndex() + 1;
                Organisation org = orgData.get(selectedOrg);
                orgData.persist();
                dispose();
                new addingOrgAssets(user, org, new OrganisationData(new NetworkDataSource()), new AssetTypeData(new NetworkDataSource()));
            } else if (source == backButton) {
                orgData.persist();
                dispose();
                new adminOptions(user);
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
            System.exit(0);
        }
    }
}
