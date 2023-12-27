package ecdsa.application.ui;

import static ecdsa.application.constant.CommonConstant.QNA_DESCRIPTION;
import static ecdsa.application.constant.CommonConstant.QNA_TITLE_LABEL;

import javax.swing.JPanel;

public class QnAPageGUI extends CommonAbstract {

    public JPanel createQnAPage() {
        return createAboutAndQnAPage(QNA_TITLE_LABEL, QNA_DESCRIPTION);
    }

}
