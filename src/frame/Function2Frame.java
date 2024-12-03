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

public class Function2Frame extends JFrame implements ActionListener {
  private JLabel lbEnglish;
  private JLabel lbTimer;
  private JLabel lbScore;
  private JButton btExit;
  private JButton btExitFrame;
  private JButton[] optionButtons;
  private int score;
  private String englishWord;
  private String correctChinese;
  private Timer timer;
  private int timeLeft;
  private List<String[]> vocabularyList;

  public Function2Frame(List<String[]> vocabularyList, String english, String correctChinese, int score) {
    super("根据英文选择中文");
    this.vocabularyList = vocabularyList;
    this.englishWord = english;
    this.correctChinese = correctChinese;
    this.score = score;
    this.setLayout(new BorderLayout());

    JPanel panelCenter = new JPanel(new GridLayout(5, 1));
    lbEnglish = new JLabel("英文单词: " + english);
    lbTimer = new JLabel("剩余时间: 10秒");

    // 创建选项按钮
    optionButtons = new JButton[4];
    JPanel panelOptions = new JPanel(new GridLayout(2, 2));
    String[] labels = {"A", "B", "C", "D"};
    for (int i = 0; i < 4; i++) {
      optionButtons[i] = new JButton(labels[i] + ". ");
      optionButtons[i].setHorizontalAlignment(SwingConstants.LEFT); // 设置按钮内容靠左对齐
      optionButtons[i].addActionListener(this);
      panelOptions.add(optionButtons[i]);
    }
    setOptions();

    panelCenter.add(lbEnglish);
    panelCenter.add(lbTimer);
    panelCenter.add(panelOptions);

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

  private void setOptions() {
    Random random = new Random();
    int correctIndex = random.nextInt(4);
    optionButtons[correctIndex].setText(optionButtons[correctIndex].getText() + correctChinese);

    for (int i = 0; i < 4; i++) {
      if (i != correctIndex) {
        String[] randomVocabulary;
        do {
          randomVocabulary = vocabularyList.get(random.nextInt(vocabularyList.size()));
        } while (randomVocabulary[1].equals(englishWord) || randomVocabulary[0].equals(correctChinese));
        optionButtons[i].setText(optionButtons[i].getText() + randomVocabulary[0]);
      }
    }
  }

  private void checkAnswer(String answer) {
    if (answer.endsWith(correctChinese)) {
      score++;
      JOptionPane.showMessageDialog(this, "恭喜回答正确！");
      FileUtil.saveWord("已掌握单词.txt", englishWord, "正确");
    } else if (answer.isEmpty()) {
      score--;
      JOptionPane.showMessageDialog(this, "您没有回答，正确答案是: " + correctChinese);
      FileUtil.saveWord("未掌握单词.txt", englishWord, "未答");
    } else {
      score -= 2;
      JOptionPane.showMessageDialog(this, "回答错误，正确答案是: " + correctChinese);
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
      new Function2Frame(vocabularyList, vocabulary[1], vocabulary[0], score).setVisible(true);
      this.dispose();
    }
  }

  public void actionPerformed(ActionEvent e) {
    timer.cancel();
    if (e.getSource() == btExit) {
      this.dispose();
      new OperationFrame().setVisible(true);
    } else if (e.getSource() == btExitFrame) {
      JOptionPane.showMessageDialog(this, "谢谢光临");
      System.exit(0);
    } else {
      for (JButton button : optionButtons) {
        if (e.getSource() == button) {
          checkAnswer(button.getText());
          return;
        }
      }
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
    new Function2Frame(vocabularyList, "abnormal", "反常的，不正常的，不规则的", 10).setVisible(true);
  }
}