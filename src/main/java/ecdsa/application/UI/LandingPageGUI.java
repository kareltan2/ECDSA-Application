package ecdsa.application.UI;

import static ecdsa.application.constant.CommonConstant.APPLICATION_TITLE;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author kareltan
 */
public class LandingPageGUI extends NavigatorGUIAbstract {

  public void showGUI(){
    JFrame frame = new JFrame(APPLICATION_TITLE);
    frame.setSize(1200, 900);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    // Create a JPanel with left-aligned FlowLayout
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    // Create a JLabel for the title
    JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>PENGEMBANGAN APLIKASI TANDA TANGAN DIGITAL<br> DENGAN PROSES SIGNING DAN VERIFICATION MENGGUNAKAN <br>ALGORITMA ELLIPTIC CURVE DIGITAL SIGNATURE YANG MENGGUNAKAN <br>FUNGSI HASH BERBASIS 256 BIT</div></html>");
    Font fontTitle = new Font("Arial", Font.BOLD, 32);
    titleLabel.setFont(fontTitle);
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titleLabel.setBorder(new EmptyBorder(30, 30, 30, 30));
    panel.add(titleLabel);

    // Create a JButton
    JButton startButton = new JButton("Start");
    Font fontButton = new Font("Arial", Font.BOLD, 20);
    startButton.setFont(fontButton);
    startButton.setPreferredSize(new Dimension(100, 50));
    panel.add(startButton);

    // Add action listener to the button
    startButton.addActionListener(e -> {
      System.out.println("Button Clicked!");
      System.exit(0);
    });

    GridBagConstraints panelConstraints = new GridBagConstraints();
    panelConstraints.gridx = 0;
    panelConstraints.gridy = 0;
    panelConstraints.insets = new Insets(10, 10, 10, 10);

    // Add the panel to the frame
    frame.add(panel);

    // Center the frame on the screen
    frame.setLocationRelativeTo(null);

    // Make the frame not resizable
    frame.setResizable(false);

    // Set the frame to be visible
    frame.setVisible(true);
  }
}
