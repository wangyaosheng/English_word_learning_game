package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import util.GUIUtil;

public class Function3Frame extends JFrame implements ActionListener {
  private JButton btExit;
  private JButton btExitFrame;

  public Function3Frame() {
    super("错词查看");
    this.setLayout(new BorderLayout());

    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);
    this.add(scrollPane, BorderLayout.CENTER);

    // 添加退出按钮
    JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btExit = new JButton("退出当前页面");
    btExit.addActionListener(this);
    panelBottom.add(btExit);
    btExitFrame = new JButton("退出全部页面");
    btExitFrame.addActionListener(this);
    panelBottom.add(btExitFrame);
    this.add(panelBottom, BorderLayout.SOUTH);

    loadWords(textArea);

    this.setSize(400, 300);
    GUIUtil.toCenter(this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setVisible(true);
  }

  private void loadWords(JTextArea textArea) {
    String filePath = "/Users/wangyaosheng/Desktop/JAVA实验/code/dict_game/System_library/未掌握单词.txt";
    System.out.println("Loading words from: " + filePath); // 调试信息
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        textArea.append(line + "\n");
      }
      System.out.println("Loaded words successfully."); // 调试信息
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btExit) {
      this.dispose();
      new OperationFrame().setVisible(true);
    } else if (e.getSource() == btExitFrame) {
      JOptionPane.showMessageDialog(this, "谢谢光临");
      System.exit(0);
    }
  }

  public static void main(String[] args) {
    new Function3Frame().setVisible(true);
  }
}