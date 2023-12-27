package ecdsa.application.ui;

import javax.swing.*;

import static ecdsa.application.constant.CommonConstant.ABOUT_DESCRIPTION;

public class AboutPageGUI extends NavigatorGUIAbstract {

    public JPanel createAboutPage() {
        return createAboutAndQnAPage("About Application", ABOUT_DESCRIPTION);
    }

}
