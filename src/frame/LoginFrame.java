package frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import util.Conf;
import util.FileOpe;
import util.GUIUtil;

public class LoginFrame extends JFrame implements ActionListener {
  /*********************** 定义各控件 *****************************/
  private JLabel lbWelcome;
  private JLabel lbAccount = new JLabel("请您输入账号");
  private JTextField tfAccount = new JTextField(10);
  private JLabel lbPassword = new JLabel("请您输入密码");
  private JPasswordField pfPassword = new JPasswordField(10);
  private JButton btLogin = new JButton("登录");
  private JButton btRegister = new JButton("注册");
  private JButton btExit = new JButton("退出");

  public LoginFrame() {
    /*********************** 界面初始化 *************************/
    super("登录");
    this.setLayout(new FlowLayout());

    // 加载图片
    String imagePath = "/Users/wangyaosheng/Desktop/JAVA实验/code/dict_game/System_library/image.png"; // 图片路径
    System.out.println("Loading image from: " + imagePath); // 调试信息
    Icon welcomeIcon = new ImageIcon(imagePath);
    if (welcomeIcon.getIconWidth() == -1) {
      System.out.println("Failed to load image."); // 调试信息
    } else {
      System.out.println("Image loaded successfully."); // 调试信息
    }
    lbWelcome = new JLabel(welcomeIcon);

    this.add(lbWelcome);
    this.add(lbAccount);
    this.add(tfAccount);
    this.add(lbPassword);
    this.add(pfPassword);
    this.add(btLogin);
    this.add(btRegister);
    this.add(btExit);
    this.setSize(500, 400);
    GUIUtil.toCenter(this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setVisible(true);

    /*********************** 增加监听 *************************/
    btLogin.addActionListener(this);
    btRegister.addActionListener(this);
    btExit.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btLogin) {
      String account = tfAccount.getText();
      String password = new String(pfPassword.getPassword());
      FileOpe.getInfoByAccount(account);
      if (Conf.account == null || !Conf.password.equals(password)) {
        JOptionPane.showMessageDialog(this, "登录失败");
        return;
      }
      JOptionPane.showMessageDialog(this, "登录成功");
      this.dispose();
      new OperationFrame().setVisible(true);
    } else if (e.getSource() == btRegister) {
      this.dispose();
      new RegisterFrame();
    } else {
      JOptionPane.showMessageDialog(this, "谢谢光临");
      System.exit(0);
    }
  }

  public static void main(String[] args) {
    new LoginFrame().setVisible(true);
  }
}