package yuuko;

import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.Border;

import cbrhandler.CBRHandler;
import cbzhandler.CBZHandler;

public class Yuuko extends JFrame {
   private static final long serialVersionUID = 6395785056741701642L;
   static int xMouse;
   static int yMouse;

   @SuppressWarnings("deprecation")
   public static void main(String[] args) {
      JFrame frame = new JFrame("YUUKO");
      frame.setSize(538, 175);
      frame.setResizable(false);
      frame.setUndecorated(true);
      frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - frame.getSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - frame.getSize().height / 2);

      try {
         frame.setIconImage(ImageIO.read(new File("images\\icon.png")));
      } catch (IOException var13) {
      }

      JPanel panel = new JPanel();
      panel.setBackground(new Color(127, 127, 127));
      panel.setSize(538, 175);
      panel.setLayout((LayoutManager)null);
      panel.setBorder((Border)null);

      JPanel panel2 = new JPanel();
      panel2.setBackground(new Color(172, 171, 172));
      panel2.setSize(538, 30);
      panel2.setBounds(0, 0, 552, 30);
      panel2.setBorder((Border)null);
      panel2.setLayout((LayoutManager)null);
      
      JLabel yuuko = new JLabel();
      yuuko.setText("YUUKO v1.00");
      yuuko.setSize(100, 12);
      yuuko.setBorder((Border)null);
      yuuko.setForeground(Color.GRAY);
      yuuko.setLocation(15, 9);
      
      JButton close_button = new JButton("X");
      close_button.setBounds(508, 0, 30, 30);
      close_button.setBorder((Border)null);
      close_button.setFocusable(false);
      close_button.setBackground(new Color(195, 195, 195));
      
      JButton select_button = new JButton("Select From");
      select_button.setBounds(403, 35, 123, 25);
      select_button.setBorderPainted(false);
      select_button.setFocusable(false);
      select_button.setBackground(new Color(195, 195, 195));
      
      JButton extract_button = new JButton("Extract To");
      extract_button.setBounds(403, 65, 123, 25);
      extract_button.setBorderPainted(false);
      extract_button.setFocusable(false);
      extract_button.setBackground(new Color(195, 195, 195));
      
      JButton convert_button = new JButton("Convert");
      convert_button.setBounds(403, 95, 123, 25);
      convert_button.setBorderPainted(false);
      convert_button.setFocusable(false);
      convert_button.setBackground(new Color(195, 195, 195));
      
      final JTextField select_field = new JTextField();
      select_field.setBounds(12, 35, 380, 40);
      select_field.setBackground(new Color(195, 195, 195));
      select_field.setFocusable(false);
      select_field.setBorder((Border)null);
      select_field.setForeground(Color.RED);
      
      final JTextField destination_field = new JTextField();
      destination_field.setBounds(12, 80, 380, 40);
      destination_field.setBackground(new Color(195, 195, 195));
      destination_field.setFocusable(false);
      destination_field.setBorder((Border)null);
      destination_field.setForeground(Color.RED);
      
      final JTextField terminal_field = new JTextField();
      terminal_field.setBounds(12, 125, 514, 40);
      terminal_field.setBackground(new Color(71, 71, 71));
      terminal_field.setFocusable(false);
      terminal_field.setBorder((Border)null);
      terminal_field.setForeground(Color.GREEN);
      
      final JProgressBar bar = new JProgressBar();
      bar.setBackground(new Color(71, 71, 71));
      bar.setForeground(Color.GREEN);
      bar.setBorder((Border)null);
      bar.setBounds(0, 170, 538, 5);
      select_button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            int ret_val = chooser.showOpenDialog((Component)null);
            if (ret_val == 0) {
               File file = chooser.getSelectedFile();
               String str = "" + file;
               select_field.setText(str);
            }

            terminal_field.setText((String)null);
            bar.setValue(0);
         }
      });
      
      extract_button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(1);
            int ret_val = chooser.showOpenDialog((Component)null);
            if (ret_val == 0) {
               File file = chooser.getSelectedFile();
               String str = "" + file;
               destination_field.setText(str);
            }

            terminal_field.setText((String)null);
            bar.setValue(0);
         }
      });
      
      convert_button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            String select = select_field.getText();
            String dest = destination_field.getText();
            String name = select.substring(select.lastIndexOf("\\") + 1);
            name = name.substring(0, name.lastIndexOf("."));
            dest = dest + "\\" + name + ".pdf";
            boolean b = false;
            if (select.toLowerCase().endsWith(".cbr")) {
               CBRHandler cbr = new CBRHandler();
               b = cbr.convertToPdf(select, dest);
            } else if (select.toLowerCase().endsWith(".cbz")) {
               CBZHandler cbz = new CBZHandler();
               b = cbz.convertToPdf(select, dest);
            } else {
               b = false;
            }

            if (b) {
               bar.setValue(100);
               terminal_field.setText("SUCCESS");
            } else {
               bar.setForeground(Color.RED);
               bar.setValue(100);
               terminal_field.setForeground(Color.RED);
               terminal_field.setText("FAIL");
            }

         }
      });
      
      close_button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });
      
      panel2.add(close_button);
      
      panel.add(yuuko);
      panel.add(panel2);
      panel.add(select_button);
      panel.add(extract_button);
      panel.add(convert_button);
      panel.add(select_field);
      panel.add(destination_field);
      panel.add(terminal_field);
      panel.add(bar);
      
      frame.add(panel);
      frame.setDefaultCloseOperation(3);
      frame.show();
   }
}