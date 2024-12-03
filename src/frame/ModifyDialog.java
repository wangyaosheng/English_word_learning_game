package frame;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import util.Conf;
import util.FileOpe;
import util.GUIUtil;

// 定义一个继承自 JDialog 的类 ModifyDialog，并实现 ActionListener 接口
public class ModifyDialog extends JDialog implements ActionListener {
  /*********************** 定义各控件 *****************************/
  private JLabel lbMsg = new JLabel("您的账号为：");
  private JLabel lbAccount = new JLabel(Conf.account);
  private JLabel lbPassword1 = new JLabel("请您输入密码");
  private JPasswordField pfPassword1 = new JPasswordField(Conf.password, 10);
  private JLabel lbPassword2 = new JLabel("输入确认密码");
  private JPasswordField pfPassword2 = new JPasswordField(Conf.password, 10);
  private JLabel lbName = new JLabel("请您修改姓名");
  private JTextField tfName = new JTextField(Conf.name, 10);
  private JButton btModify = new JButton("修改");
  private JButton btExit = new JButton("关闭");

  // 构造方法，初始化对话框
  public ModifyDialog(JFrame frm) {
    /*********************** 界面初始化 *************************/
    super(frm, true); // 调用父类构造方法，设置对话框为模态
    this.setLayout(new GridLayout(5, 2)); // 设置布局为 GridLayout，5 行 2 列
    this.add(lbMsg); // 添加控件到对话框
    this.add(lbAccount);
    this.add(lbPassword1);
    this.add(pfPassword1);
    this.add(lbPassword2);
    this.add(pfPassword2);
    this.add(lbName);
    this.add(tfName);
    this.add(btModify);
    this.add(btExit);
    this.setSize(240, 200); // 设置对话框大小
    GUIUtil.toCenter(this); // 将对话框居中
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 设置关闭操作
    /*********************** 增加监听 *************************/
    btModify.addActionListener(this); // 为按钮添加事件监听
    btExit.addActionListener(this);
    this.setResizable(false); // 禁止调整大小
    this.setVisible(true); // 显示对话框
  }

  // 事件处理方法
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btModify) { // 如果点击的是“修改”按钮
      String password1 = new String(pfPassword1.getPassword()); // 获取输入的密码
      String password2 = new String(pfPassword2.getPassword()); // 获取确认密码
      if (!password1.equals(password2)) { // 如果两个密码不一致
        JOptionPane.showMessageDialog(this, "两个密码不相同"); // 显示错误信息
        return;
      }
      String name = tfName.getText(); // 获取输入的姓名
      // 将新的值存入静态变量
      Conf.password = password1;
      Conf.name = name;
      FileOpe.updateCustomer(Conf.account, password1, name); // 更新客户信息
      JOptionPane.showMessageDialog(this, "修改成功"); // 显示成功信息
    } else { // 如果点击的是“关闭”按钮
      this.dispose(); // 关闭对话框
    }
  }
}
