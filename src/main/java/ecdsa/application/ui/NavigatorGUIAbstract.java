package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.DEFAULT_FONT;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author kareltan
 */
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

}
