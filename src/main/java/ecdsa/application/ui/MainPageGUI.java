package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.ABOUT;
import static ecdsa.application.constant.CommonConstant.ABOUT_PAGE;
import static ecdsa.application.constant.CommonConstant.DEFAULT_HEIGHT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_TITLE_TAB_PAGE;
import static ecdsa.application.constant.CommonConstant.DEFAULT_WIDTH;
import static ecdsa.application.constant.CommonConstant.KEY_GENERATION;
import static ecdsa.application.constant.CommonConstant.KEY_GENERATION_PAGE;
import static ecdsa.application.constant.CommonConstant.QNA;
import static ecdsa.application.constant.CommonConstant.QNA_PAGE;
import static ecdsa.application.constant.CommonConstant.SIGNING;
import static ecdsa.application.constant.CommonConstant.SIGNING_PAGE;
import static ecdsa.application.constant.CommonConstant.VERIFICATION;
import static ecdsa.application.constant.CommonConstant.VERIFICATION_PAGE;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * @author kareltan
 */
public class MainPageGUI {

    private final JFrame frame;

    private final JTabbedPane tabbedPane;

    public MainPageGUI() {
        this.frame = new JFrame();
        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setTabPlacement(SwingConstants.BOTTOM);

        // Add a ChangeListener to the JTabbedPane
        this.tabbedPane.addChangeListener(e -> {
            // Update the frame title based on the selected tab
            int selectedIndex = tabbedPane.getSelectedIndex();
            updateFrameTitle(selectedIndex);
        });
    }

    public void showGUI(int index) {
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
        tabbedPane.setSelectedIndex(index);
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
        VerificationPageGUI verificationPageGUI = new VerificationPageGUI();
        return verificationPageGUI.createVerificationPage(frame);
    }

    private JPanel createAboutPanel() {
        AboutPageGUI aboutPageGUI = new AboutPageGUI();
        return aboutPageGUI.createAboutPage();
    }

    private JPanel createQnaPanel() {
        QnAPageGUI qnAPageGUI = new QnAPageGUI();
        return qnAPageGUI.createQnAPage();
    }

    private void updateFrameTitle(int selectedIndex) {
        // Set the frame title based on the selected tab
        switch (selectedIndex) {
            case 0:
                frame.setTitle(KEY_GENERATION_PAGE);
                break;
            case 1:
                frame.setTitle(SIGNING_PAGE);
                break;
            case 2:
                frame.setTitle(VERIFICATION_PAGE);
                break;
            case 3:
                frame.setTitle(ABOUT_PAGE);
                break;
            case 4:
                frame.setTitle(QNA_PAGE);
                break;
            default:
                frame.setTitle(DEFAULT_TITLE_TAB_PAGE);
        }
    }

    public void closingFrame() {
        frame.dispose();
    }

}
