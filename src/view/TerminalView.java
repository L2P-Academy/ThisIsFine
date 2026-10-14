package view;

// author: M4ntis, ted88888888

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TerminalView extends JFrame {
    private JLabel labelNorth, labelSouth;
    private JPanel panelMiddle, panelBottomAttempts;
    private JButton buttonPower;

    public TerminalView() {
        setTitle("-- Insert Stuff -- ");
        setSize(1200, 1900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        labelNorth = new JLabel("THIS IS FINE --- ADMIN-TERMINAL", JLabel.TOP);
        labelSouth = new JLabel("Attempts Remaining", JLabel.BOTTOM);
        panelMiddle = new JPanel(new BorderLayout());
        panelBottomAttempts = new JPanel();
        buttonPower = new JButton("POWER");
        beautifyButton(buttonPower);
        panelBottomAttempts.add(buttonPower);
        panelMiddle.add(panelBottomAttempts, BorderLayout.SOUTH);

        add(labelNorth, BorderLayout.NORTH);
        add(panelMiddle, BorderLayout.CENTER);
        add(labelSouth, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

}