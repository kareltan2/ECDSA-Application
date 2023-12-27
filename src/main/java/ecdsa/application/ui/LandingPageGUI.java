package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.ABOUT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_HEIGHT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_WIDTH;
import static ecdsa.application.constant.CommonConstant.DOCUMENTATION;
import static ecdsa.application.constant.CommonConstant.LANDING_PAGE_GUI;
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
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>PENGEMBANGAN APLIKASI TANDA TANGAN DIGITAL<br> "
            + "DENGAN PROSES SIGNING DAN VERIFICATION MENGGUNAKAN "
            + "<br>ALGORITMA ELLIPTIC CURVE DIGITAL SIGNATURE YANG MENGGUNAKAN "
            + "<br>FUNGSI HASH BERBASIS 256 BIT</div></html>");
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 20);
        titleLabel.setFont(fontTitle);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, mainPanelConstraints);

        // Create a panel for the "Usage" section
        JPanel usagePanel = createSectionPanel("Usage");
        mainPanelConstraints.gridy++;
        mainPanel.add(usagePanel, mainPanelConstraints);

        // Create a panel for the usage text
        JPanel usageTextPanel = createTextPanelWithBorder("This application is designed for the secure development of digital signatures. "
                + "<br>It facilitates the signing and verification process using the Elliptic Curve Digital Signature Algorithm "
                + "<br>with a robust 256-bit hash function. Experience the efficiency and reliability of digital signature technology.");
        mainPanelConstraints.gridy++;
        mainPanel.add(usageTextPanel, mainPanelConstraints);

        // Create a panel for the "Documentation" section
        JPanel documentationPanel = createSectionPanel(DOCUMENTATION);
        mainPanelConstraints.gridy++;
        mainPanel.add(documentationPanel, mainPanelConstraints);

        // Create a panel for the documentation link text
        //TODO: Input the link of the documentation
        JPanel documentationTextPanel = createTextPanelWithBorder("will be input as link");
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
        startButton.setPreferredSize(new Dimension(150, 40));
        aboutButton.setPreferredSize(new Dimension(150, 40));

        startButton.addActionListener(e -> {
            // Code to execute when the "Start" button is clicked
            MainPageGUI dashboardPageGUI = new MainPageGUI();
            dashboardPageGUI.showGUI(0);
            frame.dispose();
        });

        aboutButton.addActionListener(e -> {
            MainPageGUI dashboardPageGUI = new MainPageGUI();
            dashboardPageGUI.showGUI(3);
            frame.dispose();
        });

        // Center the main panel on the frame
        frame.add(mainPanel);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame not resizable
        frame.setResizable(false);

        // Set the frame to be visible
        frame.setVisible(true);
    }
}
