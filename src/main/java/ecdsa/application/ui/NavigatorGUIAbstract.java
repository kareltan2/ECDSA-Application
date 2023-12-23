package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.CONFIRMATION_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.FILE;
import static ecdsa.application.constant.CommonConstant.MESSAGE_DIALOG_CONFIRMATION_CLEAR;
import static ecdsa.application.constant.CommonConstant.PRIVATE_KEY;
import static ecdsa.application.constant.CommonConstant.PUBLIC_KEY;
import static ecdsa.application.constant.CommonConstant.SIGNED_FILE;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
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
        JTextField jTextField, String labelText, JFrame frame, boolean isFilteredInput) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 15);
        label.setFont(fontTitle);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        jTextField.setEditable(false);  // Make the text field read-only
        jTextField.setPreferredSize(new Dimension(400, 40));
        jTextField.setMaximumSize(new Dimension(450, 40));

        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(e -> {
            // Open a file chooser dialog
            JFileChooser fileChooser = new JFileChooser();

            if(isFilteredInput){
                // Add a file filter to allow only doc, docx, and pdf files
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Documents", "doc", "docx", "pdf");
                fileChooser.setFileFilter(filter);
            } else {
                // Set the file chooser to allow only directories (folders)
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
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
        JTextField publicKeyTextField, JTextField fileTextField
    ) {
        int result = JOptionPane.showConfirmDialog(frame,
                MESSAGE_DIALOG_CONFIRMATION_CLEAR,
                CONFIRMATION_DIALOG_TITLE, JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            clearTextFieldIfNotNull(privateKeyTextField);
            clearTextFieldIfNotNull(publicKeyTextField);
            clearTextFieldIfNotNull(fileTextField);
        }
    }

    protected void clearTextFieldIfNotNull(JTextField textField) {
        if (textField != null) {
            textField.setText("");
        }
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

    protected void addRigidAreaForVerticalSpacing(JPanel panel){
        panel.add(Box.createVerticalGlue());
    }

    protected void addRigidAreaForHorizontalSpacing(JPanel panel){
        panel.add(Box.createHorizontalGlue());
    }

    protected void addRigidAreaForSpacing(JPanel panel, int width, int height){
        panel.add(Box.createRigidArea(new Dimension(width, height)));
    }

}
