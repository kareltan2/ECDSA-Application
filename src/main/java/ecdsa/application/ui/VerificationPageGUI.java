package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.AUTHENTICATE;
import static ecdsa.application.constant.CommonConstant.CLEAR_INPUT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_HEIGHT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_WIDTH;
import static ecdsa.application.constant.CommonConstant.FILE_NAME;
import static ecdsa.application.constant.CommonConstant.LABEL_PUBLIC_KEY;
import static ecdsa.application.constant.CommonConstant.MESSAGE_CONTENT;
import static ecdsa.application.constant.CommonConstant.MESSAGE_NOTES_LABEL;
import static ecdsa.application.constant.CommonConstant.SIGNATURE;
import static ecdsa.application.constant.CommonConstant.VERIFICATION_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.VERIFICATION_PAGE;

import ecdsa.application.cryptography.VerifyDocument;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Base64;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kareltan
 */
@Slf4j
public class VerificationPageGUI extends NavigatorGUIAbstract {

  private final VerifyDocument verifyDocument = new VerifyDocument();

  public JPanel createVerificationPage(JFrame frame){
    JPanel verificationPagePanel = new JPanel(new GridBagLayout());
    verificationPagePanel.setLayout(new BoxLayout(verificationPagePanel, BoxLayout.Y_AXIS));
    addRigidAreaForVerticalSpacing(verificationPagePanel);

    JLabel verificationPageTitle = new JLabel(VERIFICATION_PAGE);
    verificationPageTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    Font fontPageTitle = new Font(DEFAULT_FONT, Font.BOLD, 20);
    verificationPageTitle.setFont(fontPageTitle);
    verificationPageTitle.setHorizontalAlignment(SwingConstants.CENTER);
    verificationPagePanel.add(verificationPageTitle);
    addRigidAreaForSpacing(verificationPagePanel, 0, 10);

    JTextField publicKeyTextField = new JTextField();
    verificationPagePanel.add(createLabelAndFileInputForSavePath(publicKeyTextField, LABEL_PUBLIC_KEY, frame, false, false));
    addRigidAreaForSpacing(verificationPagePanel, 0, 10);

    JTextField fileTextField = new JTextField();
    verificationPagePanel.add(createLabelAndFileInputForSavePath(fileTextField, FILE_NAME, frame, false, true));
    addRigidAreaForSpacing(verificationPagePanel, 0, 10);

    JTextField signatureTextField = new JTextField();
    verificationPagePanel.add(createLabelAndFileInputForSavePath(signatureTextField, SIGNATURE, frame, false, false));
    addRigidAreaForSpacing(verificationPagePanel, 0, 10);

    verificationPagePanel.add(createLabelAndScrollPane(MESSAGE_NOTES_LABEL, MESSAGE_CONTENT));
    addRigidAreaForSpacing(verificationPagePanel, 0, 10);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton verificationButton = new JButton(AUTHENTICATE);
    JButton clearButton = new JButton(CLEAR_INPUT);
    verificationButton.setPreferredSize(new Dimension(160, 40));
    clearButton.setPreferredSize(new Dimension(160, 40));
    buttonPanel.add(verificationButton);
    addRigidAreaForSpacing(buttonPanel, 10, 0);
    buttonPanel.add(clearButton);
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

    verificationPagePanel.add(buttonPanel);

    frame.setLayout(new BorderLayout());
    frame.add(verificationPagePanel, BorderLayout.CENTER); // Set the main panel to the center

    // Add rigid area for vertical spacing at the bottom
    addRigidAreaForVerticalSpacing(verificationPagePanel);

    // Set the frame properties
    frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    verificationButton.addActionListener(e -> {
      try {
        // Validate fields
        if (isEmpty(publicKeyTextField) || isEmpty(fileTextField) || isEmpty(signatureTextField)) {
          showPopUpWarningValidation(frame);
          return;
        }

        //validate the extension file
        if(!validateExtensionFile(fileTextField.getText())){
          showPopUpWarningDocumentValidationType(frame);
          return;
        }

        // Read the content of the private key file
        String publicKeyContent = readFromFile(publicKeyTextField.getText());

        // Convert the encoded private key content to a PrivateKey object
        PublicKey publicKey;
        try {
          publicKey = getPublicKeyFromString(publicKeyContent);
        } catch (Exception keyEx){
          log.debug("Decoded public key from file is failed with path: {}", publicKeyTextField.getText());
          showPopUpWarningKeyNotValid(frame);
          return;
        }

        // Read the data from the bytes of the file
        byte[] fileBytes = readBytesFromFile(fileTextField.getText());
        String data = new String(fileBytes, StandardCharsets.UTF_8);

        byte[] signatureExtracted = Base64.getDecoder().decode(readBytesFromFile(signatureTextField.getText()));

        boolean isValid = verifyDocument.verifySignature(data, signatureExtracted, publicKey);

        if(isValid){
          JOptionPane.showMessageDialog(frame, "Result Verification: " + isValid, VERIFICATION_DIALOG_TITLE, JOptionPane.INFORMATION_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(frame, "Result Verification: " + isValid, VERIFICATION_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
        }

      } catch (Exception ex) {
        log.error("Error during signing process with error: ", ex);
        showPopUpError(frame);
      }
    });

    clearButton.addActionListener(e -> clearButtonPopUpConfirmation(frame, null, publicKeyTextField, fileTextField, signatureTextField));

    return verificationPagePanel;
  }

}
