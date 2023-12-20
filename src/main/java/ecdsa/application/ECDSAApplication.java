package ecdsa.application;

import ecdsa.application.UI.LandingPageGUI;

/**
 * @author kareltan
 */
public class ECDSAApplication {
  private static final LandingPageGUI landingPageGUI = new LandingPageGUI();

  public static void main(String[] args) {
    landingPageGUI.showGUI();
  }

}
