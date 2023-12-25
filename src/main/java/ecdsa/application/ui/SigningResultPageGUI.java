package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.BACK_TO_PREVIOUS_PAGE;
import static ecdsa.application.constant.CommonConstant.CONFIRMATION_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_HEIGHT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_WIDTH;
import static ecdsa.application.constant.CommonConstant.FILE_CHOSEN_LABEL;
import static ecdsa.application.constant.CommonConstant.FOLDER_LABEL;
import static ecdsa.application.constant.CommonConstant.MESSAGE_CONTENT;
import static ecdsa.application.constant.CommonConstant.MESSAGE_DIALOG_CONFIRMATION_BACK;
import static ecdsa.application.constant.CommonConstant.MESSAGE_NOTES_LABEL;
import static ecdsa.application.constant.CommonConstant.SAVE_TO_FILE;
import static ecdsa.application.constant.CommonConstant.SIGNED_FILE;
import static ecdsa.application.constant.CommonConstant.SIGNING_RESULT_PAGE;
import static ecdsa.application.constant.CommonConstant.VERIFICATION;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
public class SigningResultPageGUI extends NavigatorGUIAbstract{

    private final JFrame frame;

    private final String filePath;

    private final byte[] signature;

    public SigningResultPageGUI(String filePath, byte[] signature) {
        this.frame = new JFrame(SIGNING_RESULT_PAGE);
        this.filePath = filePath;
        this.signature = signature;
    }

    public void showGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        addRigidAreaForVerticalSpacing(mainPanel);

        JLabel signingFileTitle = new JLabel(SIGNING_RESULT_PAGE);
        signingFileTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font fontPageTitle = new Font(DEFAULT_FONT, Font.BOLD, 20);
        signingFileTitle.setFont(fontPageTitle);
        signingFileTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(signingFileTitle);

        addRigidAreaForSpacing(mainPanel, 0, 10);

        JTextField chosenFileTextField = new JTextField();
        mainPanel.add(createLabelAndTextField(chosenFileTextField, FILE_CHOSEN_LABEL, filePath, false));

        addRigidAreaForSpacing(mainPanel, 0, 10);

        JTextField signedFileTextField = new JTextField();
        mainPanel.add(createLabelAndTextField(signedFileTextField, SIGNED_FILE, null, true));

        // Add rigid area for spacing
        addRigidAreaForSpacing(mainPanel, 0, 10);

        JTextField folderNameTextField = new JTextField();
        mainPanel.add(createLabelAndFileInputForSavePath(folderNameTextField, FOLDER_LABEL, frame, true, false));
        addRigidAreaForSpacing(mainPanel, 0, 10);

        // Add components for Message Notes
        mainPanel.add(createLabelAndScrollPane(MESSAGE_NOTES_LABEL, MESSAGE_CONTENT));

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
        addRigidAreaForVerticalSpacing(mainPanel);

        // Set the frame properties
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Add action listeners for the buttons
        saveButton.addActionListener(e -> {
            try {
                // Validate if the signedFileTextField is empty
                if (isEmpty(signedFileTextField) || isEmpty(chosenFileTextField)) {
                    showPopUpWarningValidation(frame);
                    return;
                }

                //check whether the input is pdf or word
                if(isPDFExtensionFile(chosenFileTextField.getText())){
                    //pdf editor
                } else if(isWordExtensionFile(chosenFileTextField.getText())){
                    //word editor
                } else {
                    showPopUpWarningDocumentValidationType(frame);
                    return;
                }

                // Show success message or perform other actions
                successActionWithPopUp(frame, VERIFICATION);
            } catch (Exception ex) {
                log.error("Error while saving signed PDF document with error: ", ex);
                showPopUpError(frame);
            }
        });

        backButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(frame,
                    MESSAGE_DIALOG_CONFIRMATION_BACK,
                    CONFIRMATION_DIALOG_TITLE, JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                MainPageGUI dashboardPageGUI = new MainPageGUI();
                dashboardPageGUI.showGUI(1);
                frame.dispose();
            }
        });
    }

}
