package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.ABOUT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_HEIGHT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_WIDTH;
import static ecdsa.application.constant.CommonConstant.DOCUMENTATION;
import static ecdsa.application.constant.CommonConstant.LANDING_PAGE_DESCRIPTION;
import static ecdsa.application.constant.CommonConstant.LANDING_PAGE_GUI;
import static ecdsa.application.constant.CommonConstant.LANDING_PAGE_TITLE_FIX;
import static ecdsa.application.constant.CommonConstant.START;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kareltan
 */
@Slf4j
public class LandingPageGUI extends CommonAbstract {

    private final JFrame frame;

    public LandingPageGUI() {
        this.frame = new JFrame(LANDING_PAGE_GUI);
    }

    public void showGUI() {
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create the main panel with a GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());

        // Add a compound border to the main panel for the outside border
        Border outsideBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border insideBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        mainPanel.setBorder(new CompoundBorder(outsideBorder, insideBorder));

        // Create a GridBagConstraints for the main panel
        GridBagConstraints mainPanelConstraints = new GridBagConstraints();
        mainPanelConstraints.gridx = 0;
        mainPanelConstraints.gridy = 0;
        mainPanelConstraints.insets = new Insets(10, 10, 10, 10);

        // Create the titleLabel and add it to the main panel
        JLabel titleLabel = new JLabel(LANDING_PAGE_TITLE_FIX);
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 40);
        titleLabel.setFont(fontTitle);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, mainPanelConstraints);

        // Create a panel for the "Usage" section
        JPanel usagePanel = createSectionPanel("Usage");
        mainPanelConstraints.gridy++;
        mainPanel.add(usagePanel, mainPanelConstraints);

        // Create a panel for the usage text
        JPanel usageTextPanel = createTextPanelWithBorder(LANDING_PAGE_DESCRIPTION);
        mainPanelConstraints.gridy++;
        mainPanel.add(usageTextPanel, mainPanelConstraints);

        // Create a panel for the "Documentation" section
        JPanel documentationPanel = createSectionPanel(DOCUMENTATION);
        mainPanelConstraints.gridy++;
        mainPanel.add(documentationPanel, mainPanelConstraints);

        // Create a panel for the documentation link text
        JPanel documentationTextPanel = createTextPanelWithBorderClickable("https://youtu.be/7EVgIFUBa60");
        mainPanelConstraints.gridy++;
        mainPanel.add(documentationTextPanel, mainPanelConstraints);

        // Create a panel for the buttons "Start" and "About"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainPanelConstraints.gridy++;
        mainPanel.add(buttonPanel, mainPanelConstraints);

        // Create the "Start" button
        JButton startButton = new JButton(START);
        Font fontButton = new Font(DEFAULT_FONT, Font.BOLD, 16);
        startButton.setFont(fontButton);
        buttonPanel.add(startButton);

        // Create the "About" button
        JButton aboutButton = new JButton(ABOUT);
        aboutButton.setFont(fontButton);
        buttonPanel.add(aboutButton);

        // Adjust the width and height of the button
        startButton.setPreferredSize(setNewDimensionForButton());
        aboutButton.setPreferredSize(setNewDimensionForButton());

        startButton.addActionListener(e -> moveToMainPage(0));

        aboutButton.addActionListener(e -> moveToMainPage(3));

        // Center the main panel on the frame
        frame.add(mainPanel);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame not resizable
        frame.setResizable(false);

        // Set the frame to be visible
        frame.setVisible(true);
        addListenerWhenExit(frame);
    }

    private Dimension setNewDimensionForButton() {
        return new Dimension(150, 40);
    }

    private void moveToMainPage(int index) {
        MainPageGUI dashboardPageGUI = new MainPageGUI();
        dashboardPageGUI.showGUI(index);
        frame.dispose();
    }
}
