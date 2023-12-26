package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.CLEAR_INPUT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_HEIGHT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_WIDTH;
import static ecdsa.application.constant.CommonConstant.FILE_NAME;
import static ecdsa.application.constant.CommonConstant.LABEL_PRIVATE_KEY;
import static ecdsa.application.constant.CommonConstant.LOADING;
import static ecdsa.application.constant.CommonConstant.MESSAGE_CONTENT;
import static ecdsa.application.constant.CommonConstant.MESSAGE_NOTES_LABEL;
import static ecdsa.application.constant.CommonConstant.PLEASE_WAIT;
import static ecdsa.application.constant.CommonConstant.SIGNING;
import static ecdsa.application.constant.CommonConstant.SIGNING_PAGE;

import ecdsa.application.cryptography.SignDocument;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kareltan
 */
@Slf4j
public class SigningPageGUI extends NavigatorGUIAbstract {

    private final SignDocument signDocument = new SignDocument();

    public JPanel createSigningPage(JFrame frame) {
        JPanel signingPagePanel = new JPanel(new GridBagLayout());
        signingPagePanel.setLayout(new BoxLayout(signingPagePanel, BoxLayout.Y_AXIS));
        addRigidAreaForVerticalSpacing(signingPagePanel);

        JLabel signingPageTitle = new JLabel(SIGNING_PAGE);
        signingPageTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font fontPageTitle = new Font(DEFAULT_FONT, Font.BOLD, 20);
        signingPageTitle.setFont(fontPageTitle);
        signingPageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        signingPagePanel.add(signingPageTitle);
        addRigidAreaForSpacing(signingPagePanel, 0, 10);

        JTextField privateKeyTextField = new JTextField();
        signingPagePanel.add(createLabelAndFileInputForSavePath(privateKeyTextField, LABEL_PRIVATE_KEY, frame, false, false));
        addRigidAreaForSpacing(signingPagePanel, 0, 10);

        JTextField fileTextField = new JTextField();
        signingPagePanel.add(createLabelAndFileInputForSavePath(fileTextField, FILE_NAME, frame, false, true));
        addRigidAreaForSpacing(signingPagePanel, 0, 10);

        signingPagePanel.add(createLabelAndScrollPane(MESSAGE_NOTES_LABEL, MESSAGE_CONTENT));
        addRigidAreaForSpacing(signingPagePanel, 0, 10);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton signingButton = new JButton(SIGNING);
        JButton clearButton = new JButton(CLEAR_INPUT);
        signingButton.setPreferredSize(new Dimension(160, 40));
        clearButton.setPreferredSize(new Dimension(160, 40));
        buttonPanel.add(signingButton);
        addRigidAreaForSpacing(buttonPanel, 10, 0);
        buttonPanel.add(clearButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        signingPagePanel.add(buttonPanel);

        frame.setLayout(new BorderLayout());
        frame.add(signingPagePanel, BorderLayout.CENTER); // Set the main panel to the center

        // Add rigid area for vertical spacing at the bottom
        addRigidAreaForVerticalSpacing(signingPagePanel);

        // Set the frame properties
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        signingButton.addActionListener(e -> {
            try {
                // Validate fields
                if (isEmpty(privateKeyTextField) || isEmpty(fileTextField)) {
                    showPopUpWarningValidation(frame);
                    return;
                }

                //validate the extension file
                if(!validateExtensionFile(fileTextField.getText())){
                    showPopUpWarningDocumentValidationType(frame);
                    return;
                }

                // Read the content of the private key file
                String privateKeyContent = readFromFile(privateKeyTextField.getText());

                // Convert the encoded private key content to a PrivateKey object
                PrivateKey privateKey;
                try {
                    privateKey = getPrivateKeyFromString(privateKeyContent);
                } catch (Exception keyEx){
                    log.debug("Decoded private key from file is failed with path: {}", privateKeyTextField.getText());
                    showPopUpWarningKeyNotValid(frame);
                    return;
                }

                // Read the data from the bytes of the file
                byte[] fileBytes = readBytesFromFile(fileTextField.getText());
                String data = new String(fileBytes, StandardCharsets.UTF_8);

                byte[] signature = signDocument.signData(data, privateKey);

                // Show loading dialog or perform other actions
                showLoadingDialog(frame, new MainPageGUI(), fileTextField.getText(), signature);
            } catch (Exception ex) {
                log.error("Error during signing process with error: ", ex);
                showPopUpError(frame);
            }
        });

        clearButton.addActionListener(e -> clearButtonPopUpConfirmation(frame, privateKeyTextField, null, fileTextField, null));

        return signingPagePanel;
    }

    private void showLoadingDialog(JFrame frame, MainPageGUI dashboardPageGUI, String filePath,
        byte[] signature) {
        JDialog loadingDialog = new JDialog(frame, LOADING, true);
        loadingDialog.setLayout(new BorderLayout());

        // Add a label for "Please wait..."
        JLabel pleaseWaitLabel = new JLabel(PLEASE_WAIT, SwingConstants.CENTER);
        loadingDialog.add(pleaseWaitLabel, BorderLayout.NORTH);

        // Add a progress bar to simulate loading
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        loadingDialog.add(progressBar, BorderLayout.CENTER);

        loadingDialog.setSize(300, 120);
        loadingDialog.setLocationRelativeTo(frame);
        loadingDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

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
                    redirectToSignResultPage(filePath, signature);
                    ((Timer) e.getSource()).stop(); // Stop the timer after reaching 100%
                }
            }
        });

        timer.start();
        loadingDialog.setVisible(true);
    }

    private void redirectToSignResultPage(String filePath, byte[] signature) {
        SigningResultPageGUI signingResultPageGUI = new SigningResultPageGUI(filePath, signature);
        signingResultPageGUI.showGUI();
    }

}
