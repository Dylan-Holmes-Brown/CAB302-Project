package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Vipin Vijay
 */

public class buyTableArena extends JFrame{

    public buyTableArena() {

        // create JFrame and JTable
        JFrame frame = new JFrame();
        JTable tableArea = new JTable();


        JLabel titleLabel = new JLabel("Buy/Sell");
        titleLabel.setBounds(20,0,80,25);
        frame.add(titleLabel);

        Object[] columns = {"Buy/Sell","Organisation", "Asset","Quantity","Price"};
        DefaultTableModel tableSetup = new DefaultTableModel();
        tableSetup.setColumnIdentifiers(columns);

        // set the model to the table
        tableArea.setModel(tableSetup);

        JLabel buySellLabel = new JLabel("Buy/Sell");
        buySellLabel.setBounds(240,250,80,25);
        frame.add(buySellLabel);

        JTextField textBuySell = new JTextField();
        textBuySell.setBounds(350, 250, 120, 25);
        frame.add(textBuySell);

        JLabel organisationLabel = new JLabel("Organisation");
        organisationLabel.setBounds(240,280,80,25);
        frame.add(organisationLabel);

        JTextField textOrganisation = new JTextField();
        textOrganisation.setBounds(350, 280, 120, 25);
        frame.add(textOrganisation);

        JLabel assestLabel = new JLabel("Asset");
        assestLabel.setBounds(240,310,80,25);
        frame.add(assestLabel);

        JTextField textAssest = new JTextField();
        textAssest.setBounds(350, 310, 120, 25);
        frame.add(textAssest);

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setBounds(240,340,80,25);
        frame.add(quantityLabel);

        JTextField textQuantity = new JTextField();
        textQuantity.setBounds(350, 340, 120, 25);
        frame.add(textQuantity);


        JLabel priceLabel = new JLabel("Price");
        priceLabel.setBounds(240,370,80,25);
        frame.add(priceLabel);

        JTextField textPrice = new JTextField();
        textPrice.setBounds(350, 370, 120, 25);
        frame.add(textPrice);

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

        Object[] rows = new Object[5];

        buttonAdd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                rows[0] = textBuySell.getText();
                rows[1] = textOrganisation.getText();
                rows[2] = textAssest.getText();
                rows[3] = textQuantity.getText();
                rows[4] = textPrice.getText();

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

                textBuySell.setText(tableSetup.getValueAt(i, 0).toString());
                textOrganisation.setText(tableSetup.getValueAt(i, 1).toString());
                textAssest.setText(tableSetup.getValueAt(i, 2).toString());
                textQuantity.setText(tableSetup.getValueAt(i, 3).toString());
                textPrice.setText(tableSetup.getValueAt(i, 4).toString());
            }
        });

        buttonUpdate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                int i = tableArea.getSelectedRow();

                if(i >= 0)
                {
                    tableSetup.setValueAt(textBuySell.getText(), i, 0);
                    tableSetup.setValueAt(textOrganisation.getText(), i, 1);
                    tableSetup.setValueAt(textAssest.getText(), i, 2);
                    tableSetup.setValueAt(textQuantity.getText(), i, 3);
                    tableSetup.setValueAt(textPrice.getText(), i, 4);
                }
                else{
                    System.out.println("Error when Updating");
                }
            }
        });

        frame.setTitle("Buy/Sell");
        frame.setSize(1000,450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    public static void main(String[] args) {

        new buyTableArena();
    }

}