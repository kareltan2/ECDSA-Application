package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.ABOUT_DESCRIPTION;
import static ecdsa.application.constant.CommonConstant.ABOUT_TITLE_LABEL;

import javax.swing.JPanel;

public class AboutPageGUI extends CommonAbstract {

    public JPanel createAboutPage() {
        return createAboutAndQnAPage(ABOUT_TITLE_LABEL, ABOUT_DESCRIPTION);
    }

}
