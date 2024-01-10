package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.BC;
import static ecdsa.application.constant.CommonConstant.BROWSE;
import static ecdsa.application.constant.CommonConstant.CONFIRMATION_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.CONTENT_TYPE_QNA;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.DOCUMENTS;
import static ecdsa.application.constant.CommonConstant.EC;
import static ecdsa.application.constant.CommonConstant.ERROR_DIALOG_MESSAGE;
import static ecdsa.application.constant.CommonConstant.ERROR_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.EXIT_WARNING_MESSAGE;
import static ecdsa.application.constant.CommonConstant.EXIT_WARNING_TITLE;
import static ecdsa.application.constant.CommonConstant.FOLDER;
import static ecdsa.application.constant.CommonConstant.FORMAT_DATE_PRINT;
import static ecdsa.application.constant.CommonConstant.MESSAGE_CONTENT;
import static ecdsa.application.constant.CommonConstant.MESSAGE_DIALOG_CONFIRMATION_BACK;
import static ecdsa.application.constant.CommonConstant.MESSAGE_DIALOG_CONFIRMATION_CLEAR;
import static ecdsa.application.constant.CommonConstant.MESSAGE_DIALOG_CONFIRMATION_SUCCESS_GENERATED;
import static ecdsa.application.constant.CommonConstant.PDF;
import static ecdsa.application.constant.CommonConstant.PRIVATE_KEY;
import static ecdsa.application.constant.CommonConstant.PRIVATE_KEY_EXTENSION;
import static ecdsa.application.constant.CommonConstant.PUBLIC_KEY;
import static ecdsa.application.constant.CommonConstant.PUBLIC_KEY_EXTENSION;
import static ecdsa.application.constant.CommonConstant.SIGNATURE;
import static ecdsa.application.constant.CommonConstant.SIGNATURE_EXTENSION;
import static ecdsa.application.constant.CommonConstant.SIGNING;
import static ecdsa.application.constant.CommonConstant.SUCCESS_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.VERIFICATION;
import static ecdsa.application.constant.CommonConstant.WARNING_EMPTY_FIELD_DIALOG_MESSAGE;
import static ecdsa.application.constant.CommonConstant.WARNING_EMPTY_FIELD_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.WARNING_EXTENSION_FILE_DIALOG_MESSAGE;
import static ecdsa.application.constant.CommonConstant.WARNING_EXTENSION_FILE_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.WARNING_KEY_NOT_VALID_DIALOG_MESSAGE;
import static ecdsa.application.constant.CommonConstant.WARNING_KEY_NOT_VALID_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.WARNING_SIGNATURE_NOT_VALID_DIALOG_MESSAGE;
import static ecdsa.application.constant.CommonConstant.WARNING_SIGNATURE_NOT_VALID_DIALOG_TITLE;

import ecdsa.application.cryptography.ECDSACryptographyAbstract;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
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
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author kareltan
 */
public abstract class CommonAbstract extends ECDSACryptographyAbstract {

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

    protected JPanel createTextPanelWithBorderClickable(String link) {
        JPanel textPanel = new JPanel();
        int horizontalPadding = 20;
        int verticalPadding = 20;

        JLabel textLabel = new JLabel("<html><div style='text-align: left;'><a href='" + link + "'>" + link + "</a></div></html>");
        textLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEtchedBorder(),
            BorderFactory.createEmptyBorder(verticalPadding, horizontalPadding, verticalPadding, horizontalPadding)
        ));

        textLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        textLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(link));
                } catch (IOException | URISyntaxException ex) {
                    createTextPanelWithBorder(link);
                }
            }
        });

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

        addRigidAreaForHorizontalSpacing(panel);
        panel.add(label);
        addRigidAreaForSpacing(panel, 10, 0);
        panel.add(jTextField);
        addRigidAreaForHorizontalSpacing(panel);

        return panel;
    }

    protected JPanel createLabelAndFileInputForSavePath(
        JTextField jTextField, String labelText, JFrame frame, String filterType) {
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

            if(filterType.equalsIgnoreCase(DOCUMENTS)){
                // Add a file filter to allow only doc, docx, and pdf files
                FileNameExtensionFilter filter = new FileNameExtensionFilter(DOCUMENTS, PDF);
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
            } else if(filterType.equalsIgnoreCase(FOLDER)){
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            } else if(filterType.equalsIgnoreCase(PRIVATE_KEY)){
                FileNameExtensionFilter filter = new FileNameExtensionFilter(PRIVATE_KEY, PRIVATE_KEY_EXTENSION);
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
            } else if(filterType.equalsIgnoreCase(PUBLIC_KEY)){
                FileNameExtensionFilter filter = new FileNameExtensionFilter(PUBLIC_KEY, PUBLIC_KEY_EXTENSION);
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
            } else {
                FileNameExtensionFilter filter = new FileNameExtensionFilter(SIGNATURE, SIGNATURE_EXTENSION);
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
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

    protected JPanel createMessageNotesBeforeAction() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(MESSAGE_CONTENT);
        label.setForeground(Color.RED);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        addRigidAreaForHorizontalSpacing(panel);
        panel.add(label);
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
        int result = JOptionPane.showConfirmDialog(frame,
            String.format(MESSAGE_DIALOG_CONFIRMATION_SUCCESS_GENERATED, nextAction.toLowerCase()),
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

    protected void showPopUpWarningSignatureNotValid(JFrame frame){
        JOptionPane.showMessageDialog(frame, WARNING_SIGNATURE_NOT_VALID_DIALOG_MESSAGE, WARNING_SIGNATURE_NOT_VALID_DIALOG_TITLE, JOptionPane.WARNING_MESSAGE);
    }

    protected boolean isEmpty(JTextField textField) {
        return textField.getText().trim().isEmpty();
    }

    protected String readFromFile(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    protected byte[] readBytesFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

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

    protected boolean validateExtensionFile(String filePath){
        return filePath.contains(PDF);
    }

    protected String getFormattedDate() {
        Date date = new Date();
        return new SimpleDateFormat(FORMAT_DATE_PRINT).format(date);
    }

    protected JPanel createAboutAndQnAPage(String titleLabelText, String contentText) {
        JPanel aboutAndQnAPagePanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel(titleLabelText);
        Font titleFont = new Font(DEFAULT_FONT, Font.BOLD, 24);
        titleLabel.setFont(titleFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        aboutAndQnAPagePanel.add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();

        JTextPane descriptionTextPane = new JTextPane();
        descriptionTextPane.setEditable(false);
        descriptionTextPane.setContentType(CONTENT_TYPE_QNA);

        descriptionTextPane.setText(contentText);

        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(descriptionTextPane);

        scrollPane.setPreferredSize(new Dimension(600, 400));

        contentPanel.add(scrollPane);
        aboutAndQnAPagePanel.add(contentPanel, BorderLayout.CENTER);

        return aboutAndQnAPagePanel;
    }

    public void addListenerWhenExit(JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(frame,
                    EXIT_WARNING_MESSAGE, EXIT_WARNING_TITLE,
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                } else {
                    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

}
