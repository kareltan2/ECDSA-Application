package ecdsa.application.ui;

import javax.swing.*;

import static ecdsa.application.constant.CommonConstant.QNA_DESCRIPTION;

public class QnAPageGUI extends NavigatorGUIAbstract{
    public JPanel createQnAPage() {
        return createAboutAndQnAPage("Frequently Asked Questions (FAQs)", QNA_DESCRIPTION);
    }
}
