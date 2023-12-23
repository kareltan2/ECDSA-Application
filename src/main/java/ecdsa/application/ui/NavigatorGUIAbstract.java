package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.FILE;
import static ecdsa.application.constant.CommonConstant.PRIVATE_KEY;
import static ecdsa.application.constant.CommonConstant.PUBLIC_KEY;
import static ecdsa.application.constant.CommonConstant.SIGNED_FILE;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
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
import lombok.extern.slf4j.Slf4j;

/**
 * @author kareltan
 */
@Slf4j
public abstract class NavigatorGUIAbstract {

    protected JTextField privateKeyTextField;

    protected JTextField publicKeyTextField;

    protected JTextField fileTextField;

    protected JTextField signedFileTextField;

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

        // Adjust the padding around the text within the border
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

    protected JPanel createLabelAndTextFieldByCategory(String typeLabel, String labelText){
        if(typeLabel.equalsIgnoreCase(PRIVATE_KEY)){
            privateKeyTextField = new JTextField();
            return createLabelAndTextField(privateKeyTextField, labelText);
        } else if(typeLabel.equalsIgnoreCase(PUBLIC_KEY)){
            publicKeyTextField = new JTextField();
            return createLabelAndTextField(publicKeyTextField, labelText);
        } else if(typeLabel.equalsIgnoreCase(FILE)){
            fileTextField = new JTextField();
            return createLabelAndTextField(fileTextField, labelText);
        } else if(typeLabel.equalsIgnoreCase(SIGNED_FILE)){
            signedFileTextField = new JTextField();
            return createLabelAndTextField(signedFileTextField, labelText);
        }
        return new JPanel();
    }

    protected JPanel createLabelAndTextField(JTextField jTextField, String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 15);
        label.setFont(fontTitle);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        jTextField.setPreferredSize(new Dimension(550, 40));
        jTextField.setMaximumSize(new Dimension(550, 40));

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(jTextField);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    protected JPanel createLabelAndFileInputForSavePath(String labelText, JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 15);
        label.setFont(fontTitle);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        fileTextField = new JTextField();
        fileTextField.setEditable(false);  // Make the text field read-only
        fileTextField.setPreferredSize(new Dimension(400, 40));
        fileTextField.setMaximumSize(new Dimension(450, 40));

        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(e -> {
            // Open a file chooser dialog
            JFileChooser fileChooser = new JFileChooser();

            // Set the file chooser to allow only directories (folders)
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int result = fileChooser.showOpenDialog(frame);

            // If a folder is chosen, set the folder path in the text field
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                fileTextField.setText(selectedFolder.getAbsolutePath());
            }
        });

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(fileTextField);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(browseButton);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    protected JPanel createLabelAndFileInputForECDSA(String labelText, JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 15);
        label.setFont(fontTitle);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        fileTextField = new JTextField();
        fileTextField.setEditable(false);  // Make the text field read-only
        fileTextField.setPreferredSize(new Dimension(400, 40));
        fileTextField.setMaximumSize(new Dimension(450, 40));

        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(e -> {
            // Open a file chooser dialog
            JFileChooser fileChooser = new JFileChooser();

            // Add a file filter to allow only doc, docx, and pdf files
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Documents", "doc", "docx", "pdf");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(frame);

            // If a file is chosen, set the file path in the text field
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileTextField.setText(selectedFile.getAbsolutePath());
            }
        });

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(fileTextField);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(browseButton);
        panel.add(Box.createHorizontalGlue());

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

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(scrollPane);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    protected JTextField getPrivateKeyTextField() {
        return privateKeyTextField;
    }

    protected JTextField getPublicKeyTextField() {
        return publicKeyTextField;
    }

    protected JTextField getFileTextField() {
        return fileTextField;
    }

    protected void clearButtonPopUpConfirmation(JFrame frame) {
        int result = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to clear all the data?",
                "Confirmation", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            JTextField privateKeyJTextField = getPrivateKeyTextField();
            JTextField fileTextJField = getFileTextField();
            privateKeyJTextField.setText("");
            fileTextJField.setText("");
        }
    }

    protected JPanel createLabelAndTextField(String labelText, String text) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 15);
        label.setFont(fontTitle);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        fileTextField = new JTextField(text);
        fileTextField.setEditable(false);
        fileTextField.setPreferredSize(new Dimension(550, 40));
        fileTextField.setMaximumSize(new Dimension(550, 40));

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(fileTextField);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    protected void saveKeyToFile(java.security.Key key, String fileName) throws Exception {
        // Convert key to Base64-encoded string
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

        // Write the key to a file
       this.saveToFile(encodedKey, fileName);
    }

    protected void saveToFile(String content, String fileName) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(content.getBytes());
            log.info("File saved to: " + fileName);
        }
    }

}
