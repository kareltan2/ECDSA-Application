package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.APPLICATION_DESCRIPTION;
import static ecdsa.application.constant.CommonConstant.APPLICATION_SLOGAN;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.GENERATE;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class KeyGenerationPageGUI {

    public JPanel createKeyGenerationPage() {
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
            //TODO: Going to the next page
        });

        keyGenerationPanel.add(titlePanel);

        return keyGenerationPanel;
    }

}
