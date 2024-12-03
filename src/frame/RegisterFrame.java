package frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import util.Conf;
import util.FileOpe;
import util.GUIUtil;

public class RegisterFrame extends JFrame implements ActionListener {
  /*********************** 定义各控件 *****************************/
  private JLabel lbAccount = new JLabel("请您输入账号");
  private JTextField tfAccount = new JTextField(10);
  private JLabel lbPassword1 = new JLabel("请您输入密码");
  private JPasswordField pfPassword1 = new JPasswordField(10);
  private JLabel lbPassword2 = new JLabel("输入确认密码");
  private JPasswordField pfPassword2 = new JPasswordField(10);
  private JLabel lbName = new JLabel("请您输入姓名");
  private JTextField tfName = new JTextField(10);
  private JButton btRegister = new JButton("注册");
  private JButton btLogin = new JButton("登录");
  private JButton btExit = new JButton("退出");

  public RegisterFrame() {
    /*********************** 界面初始化 *************************/
    super("注册");
    this.setLayout(new FlowLayout());
    this.add(lbAccount);
    this.add(tfAccount);
    this.add(lbPassword1);
    this.add(pfPassword1);
    this.add(lbPassword2);
    this.add(pfPassword2);
    this.add(lbName);
    this.add(tfName);
    this.add(btRegister);
    this.add(btLogin);
    this.add(btExit);
    this.setSize(240, 220);
    GUIUtil.toCenter(this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setVisible(true);
    /*********************** 增加监听 *************************/
    btRegister.addActionListener(this);
    btLogin.addActionListener(this);
    btExit.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btRegister) {
      String account = tfAccount.getText();
      String password1 = new String(pfPassword1.getPassword());
      String password2 = new String(pfPassword2.getPassword());
      String name = tfName.getText();

      if (!password1.equals(password2)) {
        JOptionPane.showMessageDialog(this, "两次输入的密码不一致");
        return;
      }

      try {
        FileOpe.registerCustomer(account, password1, name);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "注册失败：" + ex.getMessage());
      }
    } else if (e.getSource() == btLogin) {
      this.dispose();
      new LoginFrame();
    } else {
      JOptionPane.showMessageDialog(this, "谢谢光临");
      System.exit(0);
    }
  }

  public static void main(String[] args) {
    new RegisterFrame().setVisible(true);
  }
}