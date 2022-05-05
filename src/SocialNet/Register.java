package SocialNet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * @author: ChenD7x
 * @description: 用户注册界面
 */
public class Register {

    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/SOCIAL?serverTimezone=GMT%2B8";

    // 数据库的用户名与密码
    private static String username = "root";
    private static String password = "7757123";

    /**
    *@description: 注册界面
    *@param: []
    *@return: void
    */
    public static void register() {
        JFrame jf = new JFrame("Register");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel nameLabel = new JLabel("姓名:");
        JLabel birthdayLabel = new JLabel("出生日期:");
        JLabel emailLabel = new JLabel("用户名(邮箱):");
        JLabel phoneLabel = new JLabel("通讯地址:");
        JLabel passwordLabel = new JLabel("用户密码:");

        // 创建两个单选按钮
        JRadioButton radioBtn01 = new JRadioButton("男");
        JRadioButton radioBtn02 = new JRadioButton("女");
        // 创建按钮组，把两个单选按钮添加到该组
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radioBtn01);
        btnGroup.add(radioBtn02);
        // 设置第一个单选按钮选中
        radioBtn01.setSelected(true);

        JTextField nameText = new JTextField(20);
        JTextField birthdayText = new JTextField(20);
        JTextField emailText = new JTextField(20);
        JTextField phoneText = new JTextField(20);
        JPasswordField passwordText = new JPasswordField(20);
        JButton registerButton = new JButton("注册");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        nameLabel.setBounds(150, 40, 80, 25);
        nameText.setBounds(240, 40, 165, 25);
        radioBtn01.setBounds(150, 70, 80, 25);
        radioBtn02.setBounds(240, 70, 165, 25);
        birthdayLabel.setBounds(150, 110, 80, 25);
        birthdayText.setBounds(240, 110, 165, 25);
        emailLabel.setBounds(150, 140, 80, 25);
        emailText.setBounds(240, 140, 165, 25);
        phoneLabel.setBounds(150, 170, 80, 25);
        phoneText.setBounds(240, 170, 165, 25);
        passwordLabel.setBounds(150, 210, 80, 25);
        passwordText.setBounds(240, 210, 165, 25);
        registerButton.setBounds(200, 250, 80, 30);
        returnButton.setBounds(320, 250, 80, 30);

        // 加入面板中
        panel.add(nameLabel);
        panel.add(birthdayLabel);
        panel.add(emailLabel);
        panel.add(phoneLabel);
        panel.add(passwordLabel);
        panel.add(nameText);
        panel.add(birthdayText);
        panel.add(emailText);
        panel.add(phoneText);
        panel.add(passwordText);
        panel.add(registerButton);
        panel.add(returnButton);

        panel.add(radioBtn01);
        panel.add(radioBtn02);

        // 设置按钮监听事件
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String sex = "m";
                boolean sexSelect = false;
                // 处理选择按钮
                if (radioBtn01.isSelected()) {
                    sexSelect = true;
                    sex = "m";
                } else if (radioBtn02.isSelected()) {
                    sexSelect = true;
                    sex = "f";
                } else {
                    alarm("错误", 80, "没有选择性别");
                }

                if (sexSelect) {
                    String name = nameText.getText();
                    String birthday = birthdayText.getText();
                    String email = emailText.getText();
                    String phone = phoneText.getText();
                    String passwd = passwordText.getText();
                    Connection conn = null;
                    Statement stmt = null;
                    try {
                        // 注册 JDBC 驱动
                        Class.forName("com.mysql.jdbc.Driver");

                        // 连接数据库
                        conn = DriverManager.getConnection(DB_URL, username, password);

                        // 改为手动提交
                        conn.setAutoCommit(false);
                        stmt = conn.createStatement();

                        // 查看注册的邮箱是否已经在“其他邮箱”表中注册过
                        String sql = "SELECT OTHER_EMAIL FROM OTHER_EMAIL WHERE EMAIL = '" + email + "'";
                        ResultSet rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            alarm("错误", 200,"注册失败，邮箱已被其他邮箱注册");
                        } else {
                            sql = "SELECT USERNAME FROM REGISTER_USERS WHERE EMAIL = '" + email + "'";
                            rs = stmt.executeQuery(sql);
                            if (rs.next()) {
                                alarm("错误", 200,"注册失败，邮箱已被作为用户名注册");
                            } else {
                                // 没有注册过，执行注册并在各个表中初始化
                                // 新用户插入用户表
                                PreparedStatement ps1 = conn.prepareStatement("INSERT INTO REGISTER_USERS(USERNAME,SEX,BIRTHDAY,EMAIL,PHONE,PASSWD) values (?,?,?,?,?,?)");
                                ps1.setString(1, name);
                                ps1.setString(2, sex);
                                ps1.setString(3, birthday);
                                ps1.setString(4, email);
                                ps1.setString(5, phone);
                                ps1.setString(6, passwd);
                                ps1.executeUpdate();
                                // 签名表初始化为NULL
                                PreparedStatement ps2 = conn.prepareStatement("INSERT INTO Signature(EMAIL) values(?)");
                                ps2.setString(1, email);
                                ps2.executeUpdate();
                                // 好友分组初始化为ALL
                                PreparedStatement ps3 = conn.prepareStatement("INSERT INTO FRIEND_GROUP(EMAIL) values(?)");
                                ps3.setString(1, email);
                                ps3.executeUpdate();
                                conn.commit();
                                alarm("成功", 60,"注册成功");
                                jf.dispose();
                            }
                        }
                        // 完成后关闭
                        stmt.close();
                        conn.close();
                    } catch (SQLException | ClassNotFoundException e1) {
                        alarm("错误", 80,"数据库运行出错");
                        e1.printStackTrace();
                        try {
                            conn.rollback();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    } finally {
                        // 关闭资源
                        try {
                            if (stmt != null)
                                stmt.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            if (conn != null)
                                conn.close();
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                    }

                    // 重置按钮的值
                    btnGroup.clearSelection();
                }
            }
        });

        returnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // 关掉当前界面
                jf.dispose();
            }
        });

        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);

    }

    /**
    *@description: 提示注册相关结果信息
    *@param: [type, len, str]
    *@return: void
    */
    private static void alarm(String type, int len, String str) {
        JFrame jf = new JFrame(type);
        jf.setSize(400, 200);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel jl = new JLabel(str);
        JButton jb = new JButton("确定");

        jl.setBounds(200 - len / 2, 30, len, 30);
        jb.setBounds(160, 100, 80, 30);

        panel.add(jl);
        panel.add(jb);

        // 监听确认按钮
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });

        jf.add(panel);
        jf.setVisible(true);
    }

}
