package ecdsa.application.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ecdsa.application.constant.CommonConstant.DEFAULT_WIDTH;
import static ecdsa.application.constant.CommonConstant.DEFAULT_HEIGHT;
public class DashboardPageGUI {
    private final JFrame frame;

    public DashboardPageGUI() {
        this.frame = new JFrame("Key Generation Page");
    }

    public void showGUI() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);

        // Create panels for each menu
        JPanel keyGenerationPanel = createKeyGenerationPanel();
        JPanel signingPanel = createSigningPanel();
        JPanel verificationPanel = createVerificationPanel();
        JPanel aboutPanel = createAboutPanel();
        JPanel qnaPanel = createQnaPanel();

        // Add the panels to the tabbed pane with corresponding titles
        tabbedPane.addTab("Key Generation", keyGenerationPanel);
        tabbedPane.addTab("Signing", signingPanel);
        tabbedPane.addTab("Verification", verificationPanel);
        tabbedPane.addTab("About", aboutPanel);
        tabbedPane.addTab("QnA", qnaPanel);
        tabbedPane.setPreferredSize(new Dimension(800, 400));

        frame.add(tabbedPane);

        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createKeyGenerationPanel() {
        KeyGenerationPageGUI keyGenerationPageGUI = new KeyGenerationPageGUI();
        return keyGenerationPageGUI.createKeyGenerationPage();
    }

    private JPanel createSigningPanel() {
        return new JPanel();
    }

    private JPanel createVerificationPanel() {
        return new JPanel();
    }

    private JPanel createAboutPanel() {
        return new JPanel();
    }

    private JPanel createQnaPanel() {
        return new JPanel();
    }
}
