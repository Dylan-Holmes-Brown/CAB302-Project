package Views;

import client.NetworkDataSource;
import common.User;
import common.sql.AssetTypeData;
import common.sql.CurrentData;
import common.sql.OrganisationData;
import common.sql.UserData;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

/**
 * Displays all member options, all listeners are included
 * as sub-classes of this class
 *
 * @author Vipin Vijay
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */
public class userOptions extends JFrame implements Serializable {
    private static final long serialVersionUID = 68L;
    private User user;

    // JSwing Variables
    private JButton createTrade;
    private JButton viewTrades;
    private JButton assetHistory;
    private JButton updatePassword;
    private JButton logout;

    /**
     * Constructor sets up UI frame and adds listeners
     *
     * @param user the user object passed through the menus
     */
    public userOptions(User user) {
        this.user = user;
        // Initialise the UI and listen for a Button press or window close
        initUI();
        addButtonListeners(new ButtonListener());
        addClosingListener(new ClosingListener());

        // Set up the frame
        setTitle("Asset Trading System - Member Options");
        setMinimumSize(new Dimension(400, 400));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initialises the UI placing the panels in a container with Y Axis
     * alignment spacing each panel.
     */
    private void initUI() {
        // Create a container for the panels and set the layout
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // Add panels to container with padding
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeOptionsPanel());
        contentPane.add(Box.createVerticalStrut(20));
    }

    /**
     * Create a JPanel with all button options
     *
     * @return the created JPanel
     */
    private JPanel makeOptionsPanel() {
        // Initialise Border
        Border empty = BorderFactory.createEmptyBorder();
        Border border = BorderFactory.createTitledBorder(empty, "Select an Option:");

        // Initialise the JPanel
        JPanel layout = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel();
        GridLayout buttonLayout = new GridLayout(5, 1, 0, 20);
        buttonPanel.setLayout(buttonLayout);

        // Initialise Buttons
        createTrade = new JButton("Create Trade");
        viewTrades = new JButton("View Trades");
        assetHistory = new JButton("View Asset History");
        updatePassword = new JButton("Update Password");
        logout = new JButton("Logout");

        // Add buttons to the panel
        buttonPanel.add(createTrade);
        buttonPanel.add(viewTrades);
        buttonPanel.add(assetHistory);
        buttonPanel.add(updatePassword);
        buttonPanel.add(logout);

        // Add button panel and border to layout panel
        layout.add(buttonPanel);
        layout.setBorder(border);
        return layout;
    }

    /**
     * Adds a listener to the buttons
     *
     * @param listener the listener for the buttons to use
     */
    private void addButtonListeners(ActionListener listener) {
        createTrade.addActionListener(listener);
        viewTrades.addActionListener(listener);
        assetHistory.addActionListener(listener);
        updatePassword.addActionListener(listener);
        logout.addActionListener(listener);
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
     * Handles events for the buttons on the UI.
     *
     * @author Dylan Holmes-Brown
     */
    private class ButtonListener implements ActionListener {
        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            // Get the button pressed and go to corresponding method
            JButton source = (JButton) e.getSource();
            if (source == createTrade) {
                dispose();
                //new addingAssetList(user, new AssetTypeData(new NetworkDataSource()));
            } else if (source == viewTrades) {
                dispose();
                //new addingOrganisationList(user, new OrganisationData(new NetworkDataSource()), new AssetTypeData(new NetworkDataSource()));
            } else if (source == assetHistory) {
                dispose();
                //new addingOrgAssets(user, new OrganisationData(new NetworkDataSource()), new AssetTypeData(new NetworkDataSource()));
            } else if (source == updatePassword) {
                dispose();
                new updatePassword(user, new UserData(new NetworkDataSource()));
            } else if (source == logout) {
                dispose();
                new loginGui(new UserData(new NetworkDataSource()));
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
            // Stop the application
            System.exit(0);
        }
    }
}
