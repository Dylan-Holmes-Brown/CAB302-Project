package Views;

import client.NetworkDataSource;
import common.AssetTypes;
import common.sql.AssetTypeData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

public class addingAssetList extends JFrame implements Serializable{

    private static final long serialVersionUID = 62L;
    private JList assetList;

    private JLabel assetLabel;
    private JTextField assetField;


    private JButton createButton;
    private JButton deleteButton;

    AssetTypeData assetTypeData;


    /**
     * Constructor sets up UI, adds button listeners and displays
     *
     * @param assetTypeData the user data from the database
     */
    public addingAssetList(AssetTypeData assetTypeData) {
        this.assetTypeData = assetTypeData;

        initUI();
        checkListSize();

        // Add listeners to components
        addButtonListeners(new ButtonListener());
        addNameListListener(new NameListListener());
        addClosingListener(new ClosingListener());

        // decorate the frame and make it visible
        setTitle("Create Asset");
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

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeButtonsPanel());

        contentPane.add(Box.createVerticalStrut(20));
    }

    private JScrollPane makeNameListPane() {
        assetList = new JList(assetTypeData.getModel());
        //ListModel model = data.getModel();
        assetList.setFixedCellWidth(200);

        JScrollPane scroller = new JScrollPane(assetList);
        scroller.setViewportView(assetList);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
        JPanel userPanel = new JPanel();
        GroupLayout layout = new GroupLayout(userPanel);
        userPanel.setLayout(layout);

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Label of fields
        assetLabel = new JLabel("Asset Name");

        // Text Fields
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
        return userPanel;
    }



    /**
     * Adds the buttons to the panel
     *
     * @return a panel containing the create user button
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
        assetField.setText("");
    }

    /**
     * Checks the size of the user table determining the state of the delete button
     */
    private void checkListSize() {

        deleteButton.setEnabled(assetTypeData.getSize() != 0);
    }

    /**
     * Display the Asset details in the fields
     * @param ass
     */
    private void display(AssetTypes ass) {
        if (ass != null) {
            assetField.setText(ass.getAsset());
        }
    }

    private void addButtonListeners(ActionListener listener) {
        createButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
    }

    private void addNameListListener(ListSelectionListener listener) {
        assetList.addListSelectionListener(listener);
    }

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

            // If all fields are filled in continue
            if (assetField.getText() != null && !assetField.getText().equals("")) {

                AssetTypes ass = new AssetTypes(assetField.getText());

                // Add user to database and clear fields
                assetTypeData.add(ass);
                clearFields();

                JOptionPane.showMessageDialog(null, String.format("Asset '%s' successfully added", ass.getAsset()));
            }

            // Not all fields are filled in
            else {
                JOptionPane.showMessageDialog(new JFrame(), "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
            }
            checkListSize();
        }

        private void deletePressed() {
            int index = assetList.getSelectedIndex();
            String assetFieldText = assetField.getText();
            assetTypeData.remove(assetList.getSelectedValue());
            clearFields();
            index--;
            if (index == -1) {
                if (assetTypeData.getSize() != 0) {
                    index = 0;
                }
            }
            assetList.setSelectedIndex(index);
            checkListSize();
            JOptionPane.showMessageDialog(null, String.format("Asset '%s' successfully deleted", assetFieldText));
        }
    }

    private class NameListListener implements ListSelectionListener {
        /**
         * @see ListSelectionListener#valueChanged(ListSelectionEvent)
         */
        public void valueChanged(ListSelectionEvent e) {
            if (assetList.getSelectedValue() != null
                    && !assetList.getSelectedValue().equals("")) {
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
            assetTypeData.persist();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new addingAssetList(new AssetTypeData());
    }
}
