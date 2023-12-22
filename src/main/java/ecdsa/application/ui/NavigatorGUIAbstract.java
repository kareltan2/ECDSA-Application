package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author kareltan
 */
public abstract class NavigatorGUIAbstract {
    protected JTextField textField;
    protected JTextField textFieldFile;

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

    protected JPanel createLabelAndTextField(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 15);
        label.setFont(fontTitle);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(550, 40));
        textField.setMaximumSize(new Dimension(550, 40));

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(textField);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    protected JPanel createLabelAndFileInput(String labelText, JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 15);
        label.setFont(fontTitle);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        textFieldFile = new JTextField();
        textFieldFile.setEditable(false);  // Make the text field read-only
        textFieldFile.setPreferredSize(new Dimension(400, 40));
        textFieldFile.setMaximumSize(new Dimension(450, 40));

        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a file chooser dialog
                JFileChooser fileChooser = new JFileChooser();

                // Add a file filter to allow only doc, docx, and pdf files
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Documents", "doc", "docx", "pdf");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(frame);

                // If a file is chosen, set the file path in the text field
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textFieldFile.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(textFieldFile);
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

    protected JTextField getTextField() {
        return textField;
    }

    protected JTextField getTextFieldFile() {
        return textFieldFile;
    }

    protected void clearButtonPopUpConfirmation(JFrame frame) {
        int result = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to clear all the data?",
                "Confirmation", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            JTextField textFieldInstance = getTextField();
            JTextField textFieldIFileInstance = getTextFieldFile();
            textFieldInstance.setText("");
            textFieldIFileInstance.setText("");
        }
    }

    protected JPanel createLabelAndTextField(String labelText, String text) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        Font fontTitle = new Font(DEFAULT_FONT, Font.BOLD, 15);
        label.setFont(fontTitle);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        textField = new JTextField(text);
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(550, 40));
        textField.setMaximumSize(new Dimension(550, 40));

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(textField);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

}
