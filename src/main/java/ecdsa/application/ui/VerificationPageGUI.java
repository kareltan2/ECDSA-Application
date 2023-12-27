package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.AUTHENTICATE;
import static ecdsa.application.constant.CommonConstant.CLEAR_INPUT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_HEIGHT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_WIDTH;
import static ecdsa.application.constant.CommonConstant.DOCUMENTS;
import static ecdsa.application.constant.CommonConstant.MESSAGE_CONTENT;
import static ecdsa.application.constant.CommonConstant.MESSAGE_NOTES_LABEL;
import static ecdsa.application.constant.CommonConstant.ORIGINAL_FILE;
import static ecdsa.application.constant.CommonConstant.PUBLIC_KEY;
import static ecdsa.application.constant.CommonConstant.SIGNED_FILE_NAME_LABEL;
import static ecdsa.application.constant.CommonConstant.VERIFICATION_DIALOG_MESSAGE_NOT_VALID;
import static ecdsa.application.constant.CommonConstant.VERIFICATION_DIALOG_MESSAGE_VALID;
import static ecdsa.application.constant.CommonConstant.VERIFICATION_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.VERIFICATION_PAGE;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import ecdsa.application.cryptography.VerifyDocument;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;
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
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * @author kareltan
 */
@Slf4j
public class VerificationPageGUI extends CommonAbstract {

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
    verificationPagePanel.add(createLabelAndFileInputForSavePath(publicKeyTextField, PUBLIC_KEY, frame, PUBLIC_KEY));
    addRigidAreaForSpacing(verificationPagePanel, 0, 10);

    JTextField signedFileTextField = new JTextField();
    verificationPagePanel.add(createLabelAndFileInputForSavePath(signedFileTextField, SIGNED_FILE_NAME_LABEL, frame, DOCUMENTS));
    addRigidAreaForSpacing(verificationPagePanel, 0, 10);

    JTextField originalFileTextField = new JTextField();
    verificationPagePanel.add(createLabelAndFileInputForSavePath(originalFileTextField, ORIGINAL_FILE, frame, DOCUMENTS));
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
        if (isEmpty(publicKeyTextField) || isEmpty(signedFileTextField) || isEmpty(originalFileTextField)) {
          showPopUpWarningValidation(frame);
          return;
        }

        //validate the extension file
        if(!validateExtensionFile(signedFileTextField.getText())){
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
        byte[] fileBytes = readBytesFromFile(originalFileTextField.getText());
        String data = new String(fileBytes, StandardCharsets.UTF_8);

        byte[] signatureExtracted = extractSignatureFromQRCodeSignature(signedFileTextField.getText());
        if(signatureExtracted.length != 0){
          boolean isValid = verifyDocument.verifySignature(data, signatureExtracted, publicKey);

          if(isValid){
            JOptionPane.showMessageDialog(frame, VERIFICATION_DIALOG_MESSAGE_VALID, VERIFICATION_DIALOG_TITLE, JOptionPane.INFORMATION_MESSAGE);
          } else {
            JOptionPane.showMessageDialog(frame, VERIFICATION_DIALOG_MESSAGE_NOT_VALID, VERIFICATION_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
          }
        } else {
          showPopUpWarningSignatureNotValid(frame);
        }

      } catch (IOException | NoSuchAlgorithmException | SignatureException | NoSuchProviderException | InvalidKeyException ex) {
        log.error("Error during verification process with error: ", ex);
        showPopUpError(frame);
      }
    });

    clearButton.addActionListener(e ->
        clearButtonPopUpConfirmation(frame, null, publicKeyTextField,
            originalFileTextField, signedFileTextField)
    );

    return verificationPagePanel;
  }

  private byte[] extractSignatureFromQRCodeSignature(String pdfFilePath) {
    try {
      PDDocument document = PDDocument.load(new File(pdfFilePath));
      PDPage page = document.getPage(document.getNumberOfPages() - 1);
      PDResources resources = page.getResources();
      for (COSName cosName : resources.getXObjectNames()) {
        PDXObject pdxObject = resources.getXObject(cosName);
        if (pdxObject instanceof PDImageXObject) {
          PDImageXObject imageXObject = (PDImageXObject) pdxObject;
          BufferedImage image = imageXObject.getImage();

          QRCodeReader qrCodeReader = new QRCodeReader();
          BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
          Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
          hints.put(DecodeHintType.PURE_BARCODE, Boolean.FALSE);

          // Use ZXing library to decode the QR code
          Result result = qrCodeReader.decode(binaryBitmap, hints);

          document.close();

          // Extract the QR code bytes directly from the Result
          return Base64.getDecoder().decode(result.getText());
        }
      }
      document.close();
    } catch (IOException | ChecksumException | FormatException | NotFoundException ex) {
      log.error("There is error occurred when extract signature, error: ", ex);
    }
    return new byte[0];
  }

}
