package ecdsa.application;

import ecdsa.application.ui.LandingPageGUI;

import javax.swing.*;

import static ecdsa.application.constant.CommonConstant.APPLICATION_TITLE;

/**
 * @author kareltan
 */
public class ECDSAApplication {

  private static final LandingPageGUI landingPageGUI = new LandingPageGUI(new JFrame(APPLICATION_TITLE));

  public static void main(String[] args) {
    landingPageGUI.showGUI();
  }

}
