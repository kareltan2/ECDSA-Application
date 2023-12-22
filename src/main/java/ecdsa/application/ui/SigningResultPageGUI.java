package ecdsa.application.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ecdsa.application.constant.CommonConstant.*;

public class SigningResultPageGUI extends NavigatorGUIAbstract{
    private final JFrame frame;

    public SigningResultPageGUI() {
        this.frame = new JFrame("Key Generation Result Page");
    }

    public void showGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(Box.createVerticalGlue());

        JLabel signingFileTitle = new JLabel("Signing Result Page");
        signingFileTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font fontPageTitle = new Font(DEFAULT_FONT, Font.BOLD, 20);
        signingFileTitle.setFont(fontPageTitle);
        signingFileTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(signingFileTitle);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(createLabelAndTextField("File Chosen:"));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(createLabelAndTextField("Signed File Name:"));

        // Add rigid area for spacing
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add components for Message Notes
        mainPanel.add(createLabelAndScrollPane("Message Notes:", "Please be careful when inputting file names"));

        // Add rigid area for spacing
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add components for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Save to File");
        JButton backButton = new JButton("Back to Previous Page");
        saveButton.setPreferredSize(new Dimension(160, 40));
        backButton.setPreferredSize(new Dimension(160, 40));
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(backButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        mainPanel.add(buttonPanel);

        // Add the main panel and button panel to the frame
        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER); // Set the main panel to the center

        // Add rigid area for vertical spacing at the bottom
        mainPanel.add(Box.createVerticalGlue());

        // Set the frame properties
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Add action listeners for the buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle save button action
                // TODO: Implement save functionality
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to back? All the data will be remove",
                        "Confirmation", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    DashboardPageGUI dashboardPageGUI = new DashboardPageGUI();
                    dashboardPageGUI.showGUI();
                    frame.dispose();
                }
            }
        });
    }

}
