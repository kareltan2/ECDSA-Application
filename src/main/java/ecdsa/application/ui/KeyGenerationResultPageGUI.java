package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.BACK_TO_PREVIOUS_PAGE;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_HEIGHT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_WIDTH;
import static ecdsa.application.constant.CommonConstant.FOLDER;
import static ecdsa.application.constant.CommonConstant.FULLSTOPS;
import static ecdsa.application.constant.CommonConstant.KEY_GENERATION_RESULT_PAGE;
import static ecdsa.application.constant.CommonConstant.LABEL_PRIVATE_KEY;
import static ecdsa.application.constant.CommonConstant.LABEL_PUBLIC_KEY;
import static ecdsa.application.constant.CommonConstant.PRIVATE_KEY;
import static ecdsa.application.constant.CommonConstant.PRIVATE_KEY_EXTENSION;
import static ecdsa.application.constant.CommonConstant.PUBLIC_KEY;
import static ecdsa.application.constant.CommonConstant.PUBLIC_KEY_EXTENSION;
import static ecdsa.application.constant.CommonConstant.SAVE_TO_FILE;
import static ecdsa.application.constant.CommonConstant.SIGNING;
import static ecdsa.application.constant.CommonConstant.SLASH;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.security.KeyPair;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kareltan
 */
@Slf4j
public class KeyGenerationResultPageGUI extends CommonAbstract {

    private final JFrame frame;

    private final KeyPair keyPair;

    public KeyGenerationResultPageGUI(KeyPair keyPair) {
        this.frame = new JFrame(KEY_GENERATION_RESULT_PAGE);
        this.keyPair = keyPair;
    }

    public void showGUI() {
        // Create main panel with a BoxLayout along the Y-axis
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Add rigid areas for vertical spacing at the top
        addRigidAreaForVerticalSpacing(mainPanel);

        // Add title for "Save File"
        JLabel saveFileTitle = new JLabel(KEY_GENERATION_RESULT_PAGE);
        saveFileTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font fontPageTitle = new Font(DEFAULT_FONT, Font.BOLD, 20);
        saveFileTitle.setFont(fontPageTitle);
        saveFileTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(saveFileTitle);

        // Add rigid area for spacing
        addRigidAreaForSpacing(mainPanel, 0, 10);

        // Add components for Private Key
        JTextField privateKeyTextField = new JTextField();
        mainPanel.add(createLabelAndTextField(privateKeyTextField, LABEL_PRIVATE_KEY, null, true));

        // Add rigid area for spacing
        addRigidAreaForSpacing(mainPanel, 0, 10);

        // Add components for Public Key
        JTextField publicKeyTextField = new JTextField();
        mainPanel.add(createLabelAndTextField(publicKeyTextField, LABEL_PUBLIC_KEY, null, true));

        // Add rigid area for spacing
        addRigidAreaForSpacing(mainPanel, 0, 10);

        // Add file path panel
        JTextField folderNameTextField = new JTextField();
        mainPanel.add(createLabelAndFileInputForSavePath(folderNameTextField, FOLDER, frame, FOLDER));

        // Add rigid area for spacing
        addRigidAreaForSpacing(mainPanel, 0, 10);

        // Add components for Message Notes
        mainPanel.add(createMessageNotesBeforeAction());

        // Add rigid area for spacing
        addRigidAreaForSpacing(mainPanel, 0, 10);

        // Add components for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton(SAVE_TO_FILE);
        JButton backButton = new JButton(BACK_TO_PREVIOUS_PAGE);
        saveButton.setPreferredSize(new Dimension(160, 40));
        backButton.setPreferredSize(new Dimension(160, 40));
        buttonPanel.add(saveButton);
        addRigidAreaForSpacing(buttonPanel, 10, 0);
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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        addListenerWhenExit(frame);

        // Add action listeners for the buttons
        saveButton.addActionListener(e -> saveLogic(privateKeyTextField, publicKeyTextField, folderNameTextField));

        backButton.addActionListener(e -> backActionWithPopUp(frame));
    }

    private void saveLogic(JTextField privateKeyTextField, JTextField publicKeyTextField,
        JTextField folderNameTextField) {
        try {
            // Validate fields
            if (isEmpty(privateKeyTextField) || isEmpty(publicKeyTextField) || isEmpty(folderNameTextField)) {
                showPopUpWarningValidation(frame);
                return;
            }

            //saving private key
            saveKeyToFile(keyPair.getPrivate(), generateKeyPath(folderNameTextField, privateKeyTextField, PRIVATE_KEY));

            //saving public key
            saveKeyToFile(keyPair.getPublic(), generateKeyPath(folderNameTextField, publicKeyTextField, PUBLIC_KEY));

            successActionWithPopUp(frame, SIGNING);
        } catch (Exception ex) {
            log.error("Error while saving key pair with file name: {}, {}",
                privateKeyTextField.getText(), publicKeyTextField.getText());
            showPopUpError(frame);
        }
    }

    private String generateKeyPath(JTextField folderNameTextField, JTextField keyTextField, String keyType) {
        if(keyType.equalsIgnoreCase(PRIVATE_KEY)){
            return folderNameTextField.getText() + SLASH + keyTextField.getText() + FULLSTOPS + PRIVATE_KEY_EXTENSION;
        }
        return folderNameTextField.getText() + SLASH + keyTextField.getText() + FULLSTOPS + PUBLIC_KEY_EXTENSION;
    }

}
