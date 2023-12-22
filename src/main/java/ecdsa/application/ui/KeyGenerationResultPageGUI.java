package ecdsa.application.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ecdsa.application.constant.CommonConstant.*;

public class KeyGenerationResultPageGUI {
    private final JFrame frame;

    public KeyGenerationResultPageGUI() {
        this.frame = new JFrame("Key Generation Result Page");
    }

    public void showGUI() {
        // Create main panel with a BoxLayout along the Y-axis
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Add rigid areas for vertical spacing at the top
        mainPanel.add(Box.createVerticalGlue());

        // Add title for "Save File"
        JLabel saveFileTitle = new JLabel("SAVE GENERATING KEY FILE");
        saveFileTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font fontPageTitle = new Font(DEFAULT_FONT, Font.BOLD, 20);
        saveFileTitle.setFont(fontPageTitle);
        saveFileTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(saveFileTitle);

        // Add rigid area for spacing
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add components for Private Key
        mainPanel.add(createLabelAndTextField("Private Key File Name:"));

        // Add rigid area for spacing
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add components for Public Key
        mainPanel.add(createLabelAndTextField("Public Key File Name:"));

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
                // Handle back button action
                // TODO: Implement back functionality
            }
        });
    }



    private JPanel createLabelAndTextField(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 15);
        label.setFont(fontTitle);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(550, 40));
        textField.setMaximumSize(new Dimension(550, 40));

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(textField);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    private JPanel createLabelAndScrollPane(String labelText, String text) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setMaximumSize(new Dimension(300, 150));

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(scrollPane);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }


}
