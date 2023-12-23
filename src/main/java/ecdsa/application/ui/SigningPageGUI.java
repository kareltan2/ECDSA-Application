package ecdsa.application.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ecdsa.application.constant.CommonConstant.*;

public class SigningPageGUI extends NavigatorGUIAbstract {
    public JPanel createSigningPage(JFrame frame) {
        JPanel signingPagePanel = new JPanel(new GridBagLayout());
        signingPagePanel.setLayout(new BoxLayout(signingPagePanel, BoxLayout.Y_AXIS));
        signingPagePanel.add(Box.createVerticalGlue());

        JLabel signingPageTitle = new JLabel("Signing Page");
        signingPageTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font fontPageTitle = new Font(DEFAULT_FONT, Font.BOLD, 20);
        signingPageTitle.setFont(fontPageTitle);
        signingPageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        signingPagePanel.add(signingPageTitle);

        signingPagePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        signingPagePanel.add(createLabelAndTextField("Private Key:"));
        signingPagePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        signingPagePanel.add(createLabelAndFileInput("File:", frame));
        signingPagePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        signingPagePanel.add(createLabelAndScrollPane("Message Notes:", "Please be careful when inputting file names"));
        signingPagePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton signingButton = new JButton("Sign");
        JButton clearButton = new JButton("Clear All");
        signingButton.setPreferredSize(new Dimension(160, 40));
        clearButton.setPreferredSize(new Dimension(160, 40));
        buttonPanel.add(signingButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(clearButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        signingPagePanel.add(buttonPanel);

        frame.setLayout(new BorderLayout());
        frame.add(signingPagePanel, BorderLayout.CENTER); // Set the main panel to the center

        // Add rigid area for vertical spacing at the bottom
        signingPagePanel.add(Box.createVerticalGlue());

        // Set the frame properties
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Add action listeners for the buttons
        signingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle save button action
                // TODO: Implement signing functionality
                showLoadingDialog(frame, new DashboardPageGUI());
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearButtonPopUpConfirmation(frame);
            }
        });

        return signingPagePanel;
    }

    private void showLoadingDialog(JFrame frame, DashboardPageGUI dashboardPageGUI) {
        JDialog loadingDialog = new JDialog(frame, "Loading", true);
        loadingDialog.setLayout(new BorderLayout());

        // Add a label for "Please wait..."
        JLabel pleaseWaitLabel = new JLabel("Please Wait...", SwingConstants.CENTER);
        loadingDialog.add(pleaseWaitLabel, BorderLayout.NORTH);

        // Add a progress bar to simulate loading
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        loadingDialog.add(progressBar, BorderLayout.CENTER);

        loadingDialog.setSize(300, 120);
        loadingDialog.setLocationRelativeTo(frame);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        // Set up a timer to update the progress bar and close the loading dialog after 5 seconds
        Timer timer = new Timer(30, new ActionListener() {
            private int progress = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (progress <= 100) {
                    progressBar.setValue(progress);
                    progress++;
                } else {
                    loadingDialog.dispose();
                    dashboardPageGUI.closingFrame();
                    redirectToSignResultPage();
                    ((Timer) e.getSource()).stop(); // Stop the timer after reaching 100%
                }
            }
        });

        timer.start();
        loadingDialog.setVisible(true);
    }

    private void redirectToSignResultPage() {
        SigningResultPageGUI signingResultPageGUI = new SigningResultPageGUI();
        signingResultPageGUI.setTextFieldFile(textFieldFile.getText());
        signingResultPageGUI.showGUI();
    }

}
