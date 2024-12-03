package frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import util.Conf;
import util.GUIUtil;

public class OperationFrame extends JFrame implements ActionListener {
  /*********************** 定义各控件 *****************************/
  private String welcomeMsg = "选择如下操作:";
  private JLabel lbWelcome = new JLabel(welcomeMsg);
  private JButton btQuery = new JButton("显示详细信息");
  private JButton btModify = new JButton("修改个人资料");
  private JButton btExit = new JButton("退出");
  private JButton btFunction1 = new JButton("中文补全英文");
  private JButton btFunction2 = new JButton("英文补全中文");
  private JButton btFunction3 = new JButton("错词查看");

  private List<String[]> vocabularyList = new ArrayList<>();

  public OperationFrame() {
    /*********************** 界面初始化 *************************/
    super("当前登录：" + Conf.account);
    this.setLayout(new GridLayout(7, 1));
    this.add(lbWelcome);
    this.add(btQuery);
    this.add(btModify);
    this.add(btFunction1);
    this.add(btFunction2);
    this.add(btFunction3);
    this.add(btExit);
    this.setSize(300, 350);
    GUIUtil.toCenter(this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setVisible(true);
    /*********************** 增加监听 *************************/
    btQuery.addActionListener(this);
    btModify.addActionListener(this);
    btExit.addActionListener(this);
    btFunction1.addActionListener(this);
    btFunction2.addActionListener(this);
    btFunction3.addActionListener(this);

    loadVocabulary();
  }

  private void loadVocabulary() {
    String filePath = "/Users/wangyaosheng/Desktop/JAVA实验/code/dict_game/System_library/考研词汇表.txt";
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(" ", 3); // 分割成三部分：序号、单词、中文描述
        if (parts.length >= 3) {
          vocabularyList.add(new String[]{parts[2], parts[1]}); // 确保中文在前，英文在后
        }
      }
      JOptionPane.showMessageDialog(this, "词汇表加载成功！");
    } catch (IOException e) {
      JOptionPane.showMessageDialog(this, "词汇表加载失败：" + e.getMessage());
    }
  }

  private String[] getRandomVocabulary() {
    Random random = new Random();
    return vocabularyList.get(random.nextInt(vocabularyList.size()));
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btQuery) {
      String message = "您的详细资料为:\n";
      message += "账号:" + Conf.account + "\n";
      message += "姓名:" + Conf.name + "\n";
      JOptionPane.showMessageDialog(this, message);
    } else if (e.getSource() == btModify) {
      new ModifyDialog(this);
    } else if (e.getSource() == btFunction1) {
      String[] vocabulary = getRandomVocabulary();
      this.dispose();
      new Function1Frame(vocabularyList, vocabulary[0], vocabulary[1], 10).setVisible(true); // 传递词汇表、中文描述和英文单词，并初始化分数为10
    } else if (e.getSource() == btFunction2) {
      String[] vocabulary = getRandomVocabulary();
      this.dispose();
      new Function2Frame(vocabularyList, vocabulary[1], vocabulary[0], 10).setVisible(true); // 传递词汇表、英文单词和中文描述，并初始化分数为10
    } else if (e.getSource() == btFunction3) {
      this.dispose();
      new Function3Frame();
    } else {
      JOptionPane.showMessageDialog(this, "谢谢光临");
      System.exit(0);
    }
  }

  public static void main(String[] args) {
    new OperationFrame().setVisible(true);
  }
}