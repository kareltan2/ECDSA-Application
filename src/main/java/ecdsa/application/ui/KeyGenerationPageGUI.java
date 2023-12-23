package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.APPLICATION_DESCRIPTION;
import static ecdsa.application.constant.CommonConstant.APPLICATION_SLOGAN;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.GENERATE;

import ecdsa.application.cryptography.GenerateKeyPair;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kareltan
 */
@Slf4j
public class KeyGenerationPageGUI {

    private final GenerateKeyPair generateKeyPair = new GenerateKeyPair();

    public JPanel createKeyGenerationPage(JFrame frame) {
        JPanel keyGenerationPanel = new JPanel(new GridBagLayout());

        // Create a panel for the title and the "Generate" button
        JPanel titlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.insets = new Insets(20, 20, 20, 20);

        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'><b>" + APPLICATION_SLOGAN + "</b></div></html>");
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 25);
        titleLabel.setFont(fontTitle);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, titleConstraints);

        GridBagConstraints descConstraint = new GridBagConstraints();
        descConstraint.gridx = 0;
        descConstraint.gridy = 1;
        descConstraint.insets = new Insets(20, 20, 20, 20);

        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center;'>" + APPLICATION_DESCRIPTION + "</div></html>");
        Font fontDescription = new Font(DEFAULT_FONT, Font.ITALIC, 16);
        descriptionLabel.setFont(fontDescription);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(descriptionLabel, descConstraint);

        GridBagConstraints keyGenConstraints = new GridBagConstraints();
        keyGenConstraints.gridx = 0;
        keyGenConstraints.gridy = 2;
        keyGenConstraints.insets = new Insets(10, 10, 10, 10);

        JButton generateButton = new JButton(GENERATE);
        Font fontButton = new Font(DEFAULT_FONT, Font.BOLD, 16);
        generateButton.setFont(fontButton);
        generateButton.setPreferredSize(new Dimension(250, 40));
        titlePanel.add(generateButton, keyGenConstraints);

        // Add action listener to the "Generate" button
        generateButton.addActionListener(e -> {
            showLoadingDialog(frame, new MainPageGUI());
            frame.dispose();
        });

        keyGenerationPanel.add(titlePanel);

        return keyGenerationPanel;
    }

    private void showLoadingDialog(JFrame frame, MainPageGUI dashboardPageGUI) {
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
        loadingDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        //Generate key pair logic
        try {
            KeyPair keyPairGenerated =  generateKeyPair.generateKeyPair();

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
                        redirectToKeyGenerationResultPage(keyPairGenerated);
                        ((Timer) e.getSource()).stop(); // Stop the timer after reaching 100%
                    }
                }
            });

            timer.start();
            loadingDialog.setVisible(true);
        } catch (Exception ex) {
            log.error("error occurred when generating key");
            throw new RuntimeException(ex);
        }
    }

    private void redirectToKeyGenerationResultPage(KeyPair keyPairGenerated) {
        // Perform any necessary actions before redirecting
        KeyGenerationResultPageGUI keyGenerationResultPage = new KeyGenerationResultPageGUI(keyPairGenerated);
        keyGenerationResultPage.showGUI();
    }

}
