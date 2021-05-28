package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class addingUserList extends JFrame{

    public addingUserList() {

        // create JFrame and JTable
        JFrame frame = new JFrame();
        JTable tableArea = new JTable();

        Object[] columns = {"First Name","Last Name", "Organisation","Password"};
        DefaultTableModel tableSetup = new DefaultTableModel();
        tableSetup.setColumnIdentifiers(columns);

        // set the model to the table
        tableArea.setModel(tableSetup);

        JLabel userNameLabel = new JLabel("Name");
        userNameLabel.setBounds(240,250,80,25);
        frame.add(userNameLabel);

        JTextField textFirstName = new JTextField();
        textFirstName.setBounds(350, 250, 120, 25);
        frame.add(textFirstName);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(240,280,80,25);
        frame.add(userLabel);

        JTextField textUserName = new JTextField();
        textUserName.setBounds(350, 280, 120, 25);
        frame.add(textUserName);

        JLabel orgLabel = new JLabel("Organisation");
        orgLabel.setBounds(240,310,80,25);
        frame.add(orgLabel);

        JTextField textOrganisation = new JTextField();
        textOrganisation.setBounds(350, 310, 120, 25);
        frame.add(textOrganisation);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(240,340,80,25);
        frame.add(passLabel);

        JTextField textPassword = new JTextField();
        textPassword.setBounds(350, 340, 120, 25);
        frame.add(textPassword);

        // create JButtons
        JButton buttonAdd = new JButton("Add");
        buttonAdd.setBounds(500, 250, 100, 25);
        frame.add(buttonAdd);

        JButton buttonDelete = new JButton("Delete");
        buttonDelete.setBounds(500, 310, 100, 25);
        frame.add(buttonDelete);

        JButton buttonUpdate = new JButton("Update");
        buttonUpdate.setBounds(500, 280, 100, 25);
        frame.add(buttonUpdate);

        // create JScrollPane
        JScrollPane panel = new JScrollPane(tableArea);
        panel.setBounds(50, 20, 880, 200);

        frame.setLayout(null);
        frame.add(panel);

        Object[] rows = new Object[4];

        buttonAdd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                rows[0] = textFirstName.getText();
                rows[1] = textUserName.getText();
                rows[2] = textOrganisation.getText();
                rows[3] = textPassword.getText();

                tableSetup.addRow(rows);
            }
        });

        buttonDelete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                // i = the index of the selected row
                int i = tableArea.getSelectedRow();
                if(i >= 0){
                    // remove a row from jtable
                    tableSetup.removeRow(i);
                }
                else{
                    System.out.println("Error when deleting");
                }
            }
        });

        tableArea.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e){

                int i = tableArea.getSelectedRow();

                textFirstName.setText(tableSetup.getValueAt(i, 0).toString());
                textUserName.setText(tableSetup.getValueAt(i, 1).toString());
                textOrganisation.setText(tableSetup.getValueAt(i, 2).toString());
                textPassword.setText(tableSetup.getValueAt(i, 3).toString());
            }
        });

        buttonUpdate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                int i = tableArea.getSelectedRow();

                if(i >= 0)
                {
                    tableSetup.setValueAt(textFirstName.getText(), i, 0);
                    tableSetup.setValueAt(textUserName.getText(), i, 1);
                    tableSetup.setValueAt(textOrganisation.getText(), i, 2);
                    tableSetup.setValueAt(textPassword.getText(), i, 3);
                }
                else{
                    System.out.println("Error when Updating");
                }
            }
        });

        frame.setSize(1000,450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    public static void main(String[] args) {

        new addingUserList();
    }

}



