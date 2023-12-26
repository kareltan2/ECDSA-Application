package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.BC;
import static ecdsa.application.constant.CommonConstant.BROWSE;
import static ecdsa.application.constant.CommonConstant.CONFIRMATION_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.DOC;
import static ecdsa.application.constant.CommonConstant.DOCUMENTS;
import static ecdsa.application.constant.CommonConstant.DOCX;
import static ecdsa.application.constant.CommonConstant.EC;
import static ecdsa.application.constant.CommonConstant.ERROR_DIALOG_MESSAGE;
import static ecdsa.application.constant.CommonConstant.ERROR_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.MESSAGE_DIALOG_CONFIRMATION_BACK;
import static ecdsa.application.constant.CommonConstant.MESSAGE_DIALOG_CONFIRMATION_CLEAR;
import static ecdsa.application.constant.CommonConstant.MESSAGE_DIALOG_CONFIRMATION_SUCCESS_GENERATED;
import static ecdsa.application.constant.CommonConstant.PDF;
import static ecdsa.application.constant.CommonConstant.SIGNING;
import static ecdsa.application.constant.CommonConstant.SUCCESS_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.VERIFICATION;
import static ecdsa.application.constant.CommonConstant.WARNING_EMPTY_FIELD_DIALOG_MESSAGE;
import static ecdsa.application.constant.CommonConstant.WARNING_EMPTY_FIELD_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.WARNING_EXTENSION_FILE_DIALOG_MESSAGE;
import static ecdsa.application.constant.CommonConstant.WARNING_EXTENSION_FILE_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.WARNING_KEY_NOT_VALID_DIALOG_MESSAGE;
import static ecdsa.application.constant.CommonConstant.WARNING_KEY_NOT_VALID_DIALOG_TITLE;

import ecdsa.application.cryptography.ECDSACryptographyAbstract;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author kareltan
 */
public abstract class NavigatorGUIAbstract extends ECDSACryptographyAbstract {

    protected JPanel createSectionPanel(String sectionTitle) {
        JPanel sectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel sectionLabel = new JLabel(sectionTitle);
        Font fontSection = new Font(DEFAULT_FONT, Font.BOLD, 18);
        sectionLabel.setFont(fontSection);
        sectionPanel.add(sectionLabel);
        return sectionPanel;
    }

    protected JPanel createTextPanelWithBorder(String text) {
        JPanel textPanel = new JPanel();

        int horizontalPadding = 20;
        int verticalPadding = 20;

        JLabel textLabel = new JLabel("<html><div style='text-align: left;'>" + text + "</div></html>");
        textLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(verticalPadding, horizontalPadding, verticalPadding, horizontalPadding)
        ));

        textPanel.add(textLabel);
        return textPanel;
    }

    protected JPanel createLabelAndTextField(JTextField jTextField, String labelText, String text, boolean isEditable) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 15);
        label.setFont(fontTitle);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        if(!isEditable){
            jTextField.setText(text);
            jTextField.setEditable(false);
        }

        jTextField.setPreferredSize(new Dimension(550, 40));
        jTextField.setMaximumSize(new Dimension(550, 40));

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(jTextField);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    protected JPanel createLabelAndFileInputForSavePath(
        JTextField jTextField, String labelText, JFrame frame, boolean isFolderInput,
        boolean isFilteredInput) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 15);
        label.setFont(fontTitle);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        jTextField.setEditable(false);  // Make the text field read-only
        jTextField.setPreferredSize(new Dimension(400, 40));
        jTextField.setMaximumSize(new Dimension(450, 40));

        JButton browseButton = new JButton(BROWSE);
        browseButton.addActionListener(e -> {
            // Open a file chooser dialog
            JFileChooser fileChooser = new JFileChooser();

            if(isFilteredInput){
                // Add a file filter to allow only doc, docx, and pdf files
                FileNameExtensionFilter filter = new FileNameExtensionFilter(DOCUMENTS, DOC, DOCX, PDF);
                fileChooser.setFileFilter(filter);
            } else if(isFolderInput){
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            } else {
                // Set the file chooser to allow only directories (folders)
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            }

            int result = fileChooser.showOpenDialog(frame);

            // If a folder is chosen, set the folder path in the text field
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                jTextField.setText(selectedFolder.getAbsolutePath());
            }
        });

        addRigidAreaForHorizontalSpacing(panel);
        panel.add(label);
        addRigidAreaForSpacing(panel, 10, 0);
        panel.add(jTextField);
        addRigidAreaForSpacing(panel, 10, 0);
        panel.add(browseButton);
        addRigidAreaForHorizontalSpacing(panel);

        return panel;
    }

    protected JPanel createLabelAndScrollPane(String labelText, String text) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setMaximumSize(new Dimension(300, 150));

        addRigidAreaForHorizontalSpacing(panel);
        panel.add(label);
        addRigidAreaForSpacing(panel, 10, 0);
        panel.add(scrollPane);
        addRigidAreaForHorizontalSpacing(panel);

        return panel;
    }

    protected void clearButtonPopUpConfirmation(
        JFrame frame, JTextField privateKeyTextField,
        JTextField publicKeyTextField, JTextField fileTextField, JTextField signatureTextField
    ) {
        int result = JOptionPane.showConfirmDialog(frame,
                MESSAGE_DIALOG_CONFIRMATION_CLEAR,
                CONFIRMATION_DIALOG_TITLE, JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            clearTextFieldIfNotNull(privateKeyTextField);
            clearTextFieldIfNotNull(publicKeyTextField);
            clearTextFieldIfNotNull(fileTextField);
            clearTextFieldIfNotNull(signatureTextField);
        }
    }

    protected void clearTextFieldIfNotNull(JTextField textField) {
        if (textField != null) {
            textField.setText("");
        }
    }

    protected void saveKeyToFile(java.security.Key key, String fileName) throws IOException {
        // Convert key to Base64-encoded string
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

        // Write the key to a file
       this.saveToFile(encodedKey, fileName);
    }

    protected void saveKeyToFile(byte[] signature, String fileName) throws IOException {
        // Convert key to Base64-encoded string
        String encodedSignature = Base64.getEncoder().encodeToString(signature);

        // Write the key to a file
        this.saveToFile(encodedSignature, fileName);
    }

    protected void saveToFile(String content, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(content.getBytes());
        }
    }

    protected void addRigidAreaForVerticalSpacing(JPanel panel){
        panel.add(Box.createVerticalGlue());
    }

    protected void addRigidAreaForHorizontalSpacing(JPanel panel){
        panel.add(Box.createHorizontalGlue());
    }

    protected void addRigidAreaForSpacing(JPanel panel, int width, int height){
        panel.add(Box.createRigidArea(new Dimension(width, height)));
    }

    protected void backActionWithPopUp(JFrame frame) {
        int result = JOptionPane.showConfirmDialog(frame,
            MESSAGE_DIALOG_CONFIRMATION_BACK,
            CONFIRMATION_DIALOG_TITLE, JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            MainPageGUI dashboardPageGUI = new MainPageGUI();
            dashboardPageGUI.showGUI(0);
            frame.dispose();
        }
    }

    protected void successActionWithPopUp(JFrame frame, String nextAction) {
        int result = JOptionPane .showConfirmDialog(frame,
            MESSAGE_DIALOG_CONFIRMATION_SUCCESS_GENERATED,
            SUCCESS_DIALOG_TITLE, JOptionPane.YES_NO_OPTION
        );

        MainPageGUI dashboardPageGUI = new MainPageGUI();
        //if user choose yes
        if(result == JOptionPane.YES_OPTION) {
            //if nextAction is signing
            if(nextAction.equalsIgnoreCase(SIGNING)){
                dashboardPageGUI.showGUI(1);
            } else if(nextAction.equalsIgnoreCase(VERIFICATION)){
                dashboardPageGUI.showGUI(2);
            }
            //if nextAction is verification
        }

        //if user choose no
        else {
            dashboardPageGUI.showGUI(0);
        }

        frame.dispose();
    }

    protected void showPopUpWarningValidation(JFrame frame){
        JOptionPane.showMessageDialog(frame, WARNING_EMPTY_FIELD_DIALOG_MESSAGE, WARNING_EMPTY_FIELD_DIALOG_TITLE, JOptionPane.WARNING_MESSAGE);
    }

    protected void showPopUpWarningDocumentValidationType(JFrame frame){
        JOptionPane.showMessageDialog(frame, WARNING_EXTENSION_FILE_DIALOG_MESSAGE, WARNING_EXTENSION_FILE_DIALOG_TITLE, JOptionPane.WARNING_MESSAGE);
    }

    protected void showPopUpWarningKeyNotValid(JFrame frame){
        JOptionPane.showMessageDialog(frame, WARNING_KEY_NOT_VALID_DIALOG_MESSAGE, WARNING_KEY_NOT_VALID_DIALOG_TITLE, JOptionPane.WARNING_MESSAGE);
    }

    protected boolean isEmpty(JTextField textField) {
        return textField.getText().trim().isEmpty();
    }

    // Helper method to read the content of a file
    protected String readFromFile(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    protected byte[] readBytesFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    // Helper method to convert a Base64-encoded private key string to a PrivateKey object
    protected PrivateKey getPrivateKeyFromString(String privateKeyString)
        throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        addProvider();
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(EC, BC);
        return keyFactory.generatePrivate(keySpec);
    }

    protected PublicKey getPublicKeyFromString(String publicKeyString)
        throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        addProvider();
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(EC, BC);
        return keyFactory.generatePublic(keySpec);
    }

    protected void showPopUpError(JFrame frame) {
        JOptionPane.showMessageDialog(frame, ERROR_DIALOG_MESSAGE, ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
    }

    protected byte[] combineDataWithSignature(byte[] originalData, byte[] signature) {
        // Concatenate the original data and the signature
        byte[] combinedData = new byte[originalData.length + signature.length];
        System.arraycopy(originalData, 0, combinedData, 0, originalData.length);
        System.arraycopy(signature, 0, combinedData, originalData.length, signature.length);
        return combinedData;
    }

    protected boolean validateExtensionFile(String filePath){
        return filePath.contains(DOC)
            || filePath.contains(DOCX)
            || filePath.contains(PDF);
    }

    protected boolean isWordExtensionFile(String filePath){
        return filePath.contains(DOC)
            || filePath.contains(DOCX);
    }

    protected boolean isPDFExtensionFile(String filePath){
        return filePath.contains(PDF);
    }

}
