package Views;

import javax.swing.*;
import java.awt.*;

public class bidList extends JFrame{

    private static JButton button;
    private static JLabel success;

    public bidList() {
        JLabel label = new JLabel();
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(null);

        frame.setTitle("Bid List");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,400));

        JLabel userNameLabel = new JLabel("Items List:");
        userNameLabel.setBounds(40,40,180,25);
        panel.add(userNameLabel);

        JButton buttonEdit = new JButton("Edit");
        buttonEdit.setBounds(40,300,70, 20);
        panel.add(buttonEdit);

        JButton buttonDelete = new JButton("Delete");
        buttonDelete.setBounds(120,300,70, 20);
        panel.add(buttonDelete);

        JButton buttonAdd = new JButton("Add New Item");
        buttonAdd.setBounds(200,300,120, 20);
        panel.add(buttonAdd);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }


    public static void main(String[] args){

        new bidList();
    }
}
