package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.BACK_TO_PREVIOUS_PAGE;
import static ecdsa.application.constant.CommonConstant.BLACK_BIT;
import static ecdsa.application.constant.CommonConstant.CONFIRMATION_DIALOG_TITLE;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_HEIGHT;
import static ecdsa.application.constant.CommonConstant.DEFAULT_WIDTH;
import static ecdsa.application.constant.CommonConstant.FILE_CHOSEN_LABEL;
import static ecdsa.application.constant.CommonConstant.FOLDER;
import static ecdsa.application.constant.CommonConstant.FULLSTOPS;
import static ecdsa.application.constant.CommonConstant.MESSAGE_DIALOG_CONFIRMATION_BACK;
import static ecdsa.application.constant.CommonConstant.PDF;
import static ecdsa.application.constant.CommonConstant.SAVE_TO_FILE;
import static ecdsa.application.constant.CommonConstant.SIGNED_AUTO_GENERATED_TEXT;
import static ecdsa.application.constant.CommonConstant.SIGNED_FILE;
import static ecdsa.application.constant.CommonConstant.SIGNING_RESULT_PAGE;
import static ecdsa.application.constant.CommonConstant.SLASH;
import static ecdsa.application.constant.CommonConstant.VERIFICATION;
import static ecdsa.application.constant.CommonConstant.WHITE_BIT;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * @author kareltan
 */
@Slf4j
public class SigningResultPageGUI extends CommonAbstract {

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
        mainPanel.add(createLabelAndFileInputForSavePath(folderNameTextField, FOLDER, frame, FOLDER));
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
        addRigidAreaForVerticalSpacing(mainPanel);

        // Set the frame properties
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        // Add action listeners for the buttons
        saveButton.addActionListener(e -> saveLogic(signedFileTextField, chosenFileTextField, folderNameTextField));

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

    private void saveLogic(JTextField signedFileTextField, JTextField chosenFileTextField,
        JTextField folderNameTextField) {
        try {
            // Validate if the signedFileTextField is empty
            if (isEmpty(signedFileTextField) || isEmpty(chosenFileTextField)) {
                showPopUpWarningValidation(frame);
                return;
            }

            //save signature to file
            if(validateExtensionFile(chosenFileTextField.getText())){
                saveConcatenatedSignatureToFile(generateFileSignedPath(folderNameTextField, signedFileTextField));
            } else {
                showPopUpWarningDocumentValidationType(frame);
                return;
            }

            // Show success message or perform other actions
            successActionWithPopUp(frame, VERIFICATION);
        } catch (Exception ex) {
            log.error("Error while saving signature with error: ", ex);
            showPopUpError(frame);
        }
    }

    private String generateFileSignedPath(JTextField folderNameTextField, JTextField signedFileTextField) {
        return folderNameTextField.getText() + SLASH + signedFileTextField.getText() + FULLSTOPS + PDF;
    }

    private void saveConcatenatedSignatureToFile(String targetedSavedFile) throws IOException {
        PDDocument document = PDDocument.load(new File(filePath));
        PDPage page = document.getPage(document.getNumberOfPages() - 1);

        PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true, true);

        // Generate the barcode image
        BufferedImage barcodeImage = generateBarcodeImage(signature);

        // Create PDImageXObject from BufferedImage
        if(Objects.nonNull(barcodeImage)){
            // Calculate the position to place the barcode in the left top of the page
            float x = 25;
            float y = page.getMediaBox().getHeight() - 100;

            PDImageXObject pdImage = LosslessFactory.createFromImage(document, barcodeImage);

            // Draw the barcode on the PDF
            contentStream.drawImage(pdImage, x, y, 75, 75);

            // Add text below the barcode
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.newLineAtOffset(x + 10, y - 5);
            String formattedDate = getFormattedDate();
            contentStream.showText(SIGNED_AUTO_GENERATED_TEXT + formattedDate);
            contentStream.endText();

            contentStream.close();
            document.save(new File(targetedSavedFile));
            document.close();
        } else {
            log.error("error when saving file concat-ed with signature, barcode image has a null value!");
            showPopUpError(this.frame);
        }
    }

    private BufferedImage generateBarcodeImage(byte[] data) {
        try {
            // Set up QR code writer
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            // Encode the data as a QR code
            String base64EncodedData = Base64.getEncoder().encodeToString(data);
            BitMatrix bitMatrix = qrCodeWriter.encode(base64EncodedData, BarcodeFormat.QR_CODE, 200, 200);

            // Create a BufferedImage from the BitMatrix
            BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 200; x++) {
                for (int y = 0; y < 200; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK_BIT : WHITE_BIT);
                }
            }

            return image;
        } catch (WriterException e) {
            log.error("error when generating barcode image with error: ", e);
            return null;
        }
    }

}
