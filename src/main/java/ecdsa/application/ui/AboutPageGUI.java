package ecdsa.application.ui;

import javax.swing.*;
import java.awt.*;

import static ecdsa.application.constant.CommonConstant.ABOUT_DESCRIPTION;
import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;

public class AboutPageGUI extends NavigatorGUIAbstract {

    public JPanel createAboutPage() {
        JPanel aboutPagePanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("About Application");
        Font titleFont = new Font(DEFAULT_FONT, Font.BOLD, 24);
        titleLabel.setFont(titleFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        aboutPagePanel.add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        JTextPane descriptionTextPane = new JTextPane();
        descriptionTextPane.setEditable(false);
        descriptionTextPane.setContentType("text/html");

        descriptionTextPane.setText(ABOUT_DESCRIPTION);

        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(descriptionTextPane);

        scrollPane.setPreferredSize(new Dimension(600, 400));

        contentPanel.add(scrollPane, gbc);
        aboutPagePanel.add(contentPanel, BorderLayout.CENTER);

        return aboutPagePanel;
    }

}
