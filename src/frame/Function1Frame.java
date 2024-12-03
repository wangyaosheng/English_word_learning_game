package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import util.FileUtil;
import util.GUIUtil;

public class Function1Frame extends JFrame implements ActionListener {
  private JLabel lbChinese;
  private JTextField tfEnglish;
  private JLabel lbTimer;
  private JLabel lbScore;
  private JButton btExit;
  private JButton btExitFrame;
  private int score;
  private String englishWord;
  private Timer timer;
  private int timeLeft;
  private List<String[]> vocabularyList;

  public Function1Frame(List<String[]> vocabularyList, String chinese, String english, int score) {
    super("中文补全英文");
    this.vocabularyList = vocabularyList;
    this.englishWord = english;
    this.score = score;
    this.setLayout(new BorderLayout());

    JPanel panelCenter = new JPanel(new GridLayout(4, 1));
    lbChinese = new JLabel("中文释义: " + chinese);
    tfEnglish = new JTextField(english.length() - 2); // 设置输入框长度为单词长度减去首尾字母
    lbTimer = new JLabel("剩余时间: 10秒");

    // 创建一个包含首尾字母和输入框的面板
    JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelInput.add(new JLabel(String.valueOf(english.charAt(0))));
    panelInput.add(tfEnglish);
    panelInput.add(new JLabel(String.valueOf(english.charAt(english.length() - 1))));

    panelCenter.add(lbChinese);
    panelCenter.add(panelInput);
    panelCenter.add(lbTimer);

    lbScore = new JLabel("分数: " + score, SwingConstants.RIGHT);
    this.add(lbScore, BorderLayout.NORTH);
    this.add(panelCenter, BorderLayout.CENTER);

    // 添加退出按钮
    JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
    btExit = new JButton("退出当前页面");
    btExit.addActionListener(this);
    panelBottom.add(btExit);
    btExitFrame = new JButton("退出全部页面");
    btExitFrame.addActionListener(this);
    panelBottom.add(btExitFrame);
    this.add(panelBottom, BorderLayout.SOUTH);

    this.setSize(400, 300);
    GUIUtil.toCenter(this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setVisible(true);

    tfEnglish.addActionListener(this);
    startTimer();
  }

  private void startTimer() {
    timeLeft = 10;
    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (timeLeft > 0) {
          timeLeft--;
          lbTimer.setText("剩余时间: " + timeLeft + "秒");
        } else {
          timer.cancel();
          checkAnswer("");
        }
      }
    }, 1000, 1000);
  }

  private void checkAnswer(String answer) {
    String fullAnswer = englishWord.charAt(0) + answer + englishWord.charAt(englishWord.length() - 1);
    if (fullAnswer.equalsIgnoreCase(englishWord)) {
      score++;
      JOptionPane.showMessageDialog(this, "恭喜回答正确！");
      FileUtil.saveWord("已掌握单词.txt", englishWord, "正确");
    } else if (answer.isEmpty()) {
      score--;
      JOptionPane.showMessageDialog(this, "您没有回答，答案是: " + englishWord);
      FileUtil.saveWord("未掌握单词.txt", englishWord, "未答");
    } else {
      score -= 2;
      JOptionPane.showMessageDialog(this, "回答错误，答案是: " + englishWord);
      FileUtil.saveWord("未掌握单词.txt", englishWord, "答错");
    }

    // 更新分数显示
    lbScore.setText("分数: " + score);
    lbScore.repaint(); // 确保立即更新界面

    if (score <= 0) {
      JOptionPane.showMessageDialog(this, "游戏结束，您的分数为0。");
      this.dispose();
      new OperationFrame().setVisible(true);
    } else {
      String[] vocabulary = getRandomVocabulary();
      new Function1Frame(vocabularyList, vocabulary[0], vocabulary[1], score).setVisible(true);
      this.dispose();
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == tfEnglish) {
      timer.cancel();
      checkAnswer(tfEnglish.getText());
    } else if (e.getSource() == btExit) {
      this.dispose();
      new OperationFrame().setVisible(true);
    } else if (e.getSource() == btExitFrame) {
      JOptionPane.showMessageDialog(this, "谢谢光临");
      System.exit(0);
    }
  }

  private String[] getRandomVocabulary() {
    Random random = new Random();
    return vocabularyList.get(random.nextInt(vocabularyList.size()));
  }

  public static void main(String[] args) {
    // 示例词汇表
    List<String[]> vocabularyList = List.of(
      new String[]{"反常的，不正常的，不规则的", "abnormal"},
      new String[]{"在船(飞机、车)上", "aboard"},
      new String[]{"废除(法律、习惯等);取消", "abolish"}
    );
    new Function1Frame(vocabularyList, "反常的，不正常的，不规则的", "abnormal", 10).setVisible(true);
  }
}