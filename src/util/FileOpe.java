package util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.Properties;
import javax.swing.JOptionPane;

public class FileOpe {
  // 定义文件名
  private static String fileName = "/Users/wangyaosheng/Desktop/JAVA实验/code/dict_game/System_library/cus.inc";
  // 定义 Properties 对象
  private static Properties pps;

  // 静态代码块，在类加载时执行
  static {
    pps = new Properties();
    FileReader reader = null;
    try {
      // 尝试读取文件
      reader = new FileReader(fileName);
      // 加载属性文件内容到 Properties 对象
      pps.load(reader);
    } catch (Exception ex) {
      // 异常处理，显示错误信息并退出程序
      JOptionPane.showMessageDialog(null, "文件操作异常: " + ex.getMessage());
      System.exit(0);
    } finally {
      try {
        if (reader != null) {
          // 关闭文件读取流
          reader.close();
        }
      } catch (Exception ex) {
        // 忽略关闭流时的异常
      }
    }
  }

  // 列出所有属性信息
  private static void listInfo() {
    PrintStream ps = null;
    try {
      // 创建 PrintStream 对象，用于输出属性信息到文件
      ps = new PrintStream(fileName);
      // 列出所有属性信息
      pps.list(ps);
    } catch (Exception ex) {
      // 异常处理，显示错误信息并退出程序
      JOptionPane.showMessageDialog(null, "文件操作异常: " + ex.getMessage());
      System.exit(0);
    } finally {
      try {
        if (ps != null) {
          // 关闭 PrintStream 流
          ps.close();
        }
      } catch (Exception ex) {
        // 忽略关闭流时的异常
      }
    }
  }

  // 根据账户获取信息
  public static void getInfoByAccount(String account) {
    // 获取指定账户的属性值
    String cusInfo = pps.getProperty(account);
    if (cusInfo != null) {
      // 分割属性值，获取账户信息
      String[] infos = cusInfo.split("#");
      // 设置配置信息
      Conf.account = account;
      Conf.password = infos[0];
      Conf.name = infos[1];
    }
  }

  // 更新客户信息
  public static void updateCustomer(String account, String password, String name) {
    // 设置新的属性值
    pps.setProperty(account, password + "#" + name);
    // 列出所有属性信息
    listInfo();
  }

  // 注册新用户
  public static void registerCustomer(String account, String password, String name) {
    // 检查账户是否已存在
    if (pps.getProperty(account) != null) {
      JOptionPane.showMessageDialog(null, "账户已存在");
      return;
    }
    // 设置新的属性值
    pps.setProperty(account, password + "#" + name);
    // 保存到文件
    try (FileWriter writer = new FileWriter(fileName)) {
      pps.store(writer, "Customer Information");
      JOptionPane.showMessageDialog(null, "注册成功");
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, "文件操作异常: " + ex.getMessage());
    }
  }
}