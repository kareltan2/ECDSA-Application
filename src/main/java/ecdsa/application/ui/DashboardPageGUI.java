package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.ABOUT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_HEIGHT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_WIDTH;
import static ecdsa.application.constant.CommonConstant.KEY_GENERATION;
import static ecdsa.application.constant.CommonConstant.KEY_GENERATION_PAGE;
import static ecdsa.application.constant.CommonConstant.QNA;
import static ecdsa.application.constant.CommonConstant.SIGNING;
import static ecdsa.application.constant.CommonConstant.VERIFICATION;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class DashboardPageGUI {

    private final JFrame frame;

    public DashboardPageGUI() {
        this.frame = new JFrame(KEY_GENERATION_PAGE);
    }

    public void showGUI() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(SwingConstants.BOTTOM);

        // Create panels for each menu
        JPanel keyGenerationPanel = createKeyGenerationPanel();
        JPanel signingPanel = createSigningPanel();
        JPanel verificationPanel = createVerificationPanel();
        JPanel aboutPanel = createAboutPanel();
        JPanel qnaPanel = createQnaPanel();

        // Add the panels to the tabbed pane with corresponding titles
        tabbedPane.addTab(KEY_GENERATION, keyGenerationPanel);
        tabbedPane.addTab(SIGNING, signingPanel);
        tabbedPane.addTab(VERIFICATION, verificationPanel);
        tabbedPane.addTab(ABOUT, aboutPanel);
        tabbedPane.addTab(QNA, qnaPanel);
        tabbedPane.setPreferredSize(new Dimension(800, 400));

        frame.add(tabbedPane);

        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createKeyGenerationPanel() {
        KeyGenerationPageGUI keyGenerationPageGUI = new KeyGenerationPageGUI();
        return keyGenerationPageGUI.createKeyGenerationPage(frame);
    }

    private JPanel createSigningPanel() {
        SigningPageGUI signingPageGUI = new SigningPageGUI();
        return signingPageGUI.createSigningPage(frame);
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

    public void closingFrame() {
        frame.dispose();
    }

}
