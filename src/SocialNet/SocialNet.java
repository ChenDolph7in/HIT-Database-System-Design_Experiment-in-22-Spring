package SocialNet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: ChenD7x
 * @description: 主初始界面
 */
public class SocialNet {

    /**
    *@description: 程序入口,初始界面,进行登陆或注册
    *@param: [args]
    *@return: void
    */
    public static void main(String[] args) {

        JFrame jf = new JFrame("登录");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建 标签 & 输入框 & 按钮
        JLabel userLabel = new JLabel("用户名:");
        JLabel passwordLabel = new JLabel("密码:");
        JTextField userNameText = new JTextField(20);
        JPasswordField userPasswordText = new JPasswordField(20);
        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");

        // 设置标签的大小和位置
        userLabel.setBounds(200, 60, 80, 30);
        userNameText.setBounds(280, 60, 165, 30);
        passwordLabel.setBounds(200, 110, 80, 30);
        userPasswordText.setBounds(280, 110, 165, 30);

        loginButton.setBounds(200, 230, 80, 25);
        registerButton.setBounds(320, 230, 80, 25);

        // 设置面板内容
        panel.add(userLabel);
        panel.add(userNameText);
        panel.add(passwordLabel);
        panel.add(userPasswordText);
        panel.add(loginButton);
        panel.add(registerButton);

        // 将面板加入到窗口中
        jf.add(panel);

        // 监听确认按钮
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User.email = userNameText.getText();
                User.passwd = userPasswordText.getText();
                if (User.login()) {
                    jf.dispose();
                } else            //没有选按钮便提交
                {
                    error("密码错误");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Register.register();
            }
        });

        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
    *@description: 提示错误信息
    *@param: [str]
    *@return: void
    */
    private static void error(String str) {
        JFrame jf = new JFrame("错误");
        jf.setSize(400, 200);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel jl = new JLabel(str);
        JButton jb = new JButton("确定");

        jl.setBounds(180, 30, 300, 30);
        jb.setBounds(155, 100, 80, 30);

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
