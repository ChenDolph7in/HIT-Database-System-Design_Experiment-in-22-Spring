package SocialNet;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author: ChenD7x
 * @description: 用户登陆后各界面
 */
public class User {

    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/SOCIAL?serverTimezone=GMT%2B8";

    // 数据库的用户名与密码
    public static String username = "root";
    public static String password = "7757123";

    public static String email;
    public static String passwd;

    public User() {
    }

    /**
     * @description: 登录验证
     * @param: []
     * @return: boolean 是否登陆成功
     */
    public static boolean login() {
        Connection conn = null;
        Statement stmt = null;
        String db_passwd;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 连接数据库
            System.out.println("登陆中...");
            conn = DriverManager.getConnection(DB_URL, username, password);

            // 执行查询
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT PASSWD FROM REGISTER_USERS WHERE EMAIL = '" + email + "'";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            if (rs.next()) {
                // 通过字段检索
                db_passwd = rs.getString("PASSWD");
                if (db_passwd.equals(passwd)) {
                    menu();
                    return true;
                }
            }
            // 完成后关闭
            rs.close();
            conn.close();
            stmt.close();
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            alarm("错误", 60, "连接出错");
            return false;
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
    }

    /**
     * @description: 主菜单, 选择各种功能
     * @param: []
     * @return: void
     */
    private static void menu() {

        JFrame jf = new JFrame("菜单");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JButton signatureButton = new JButton("修改个人签名");
        JButton myPersonalButton = new JButton("查看个人信息");
        JButton changePersonalButton = new JButton("修改个人信息");
        JButton careerButton = new JButton("修改个人经历");
        JButton friendsButton = new JButton("好友");
        JButton logsButton = new JButton("日志");
        JButton replyButton = new JButton("回复");

        // 设置标签 & 输入框 & 按钮的大小和位置
        signatureButton.setBounds(220, 20, 160, 30);
        myPersonalButton.setBounds(220, 60, 160, 30);
        changePersonalButton.setBounds(220, 100, 160, 30);
        careerButton.setBounds(220, 140, 160, 30);
        friendsButton.setBounds(220, 180, 160, 30);
        logsButton.setBounds(220, 220, 160, 30);
        replyButton.setBounds(220, 260, 160, 30);

        // 加入面板中
        panel.add(myPersonalButton);
        panel.add(changePersonalButton);
        panel.add(careerButton);
        panel.add(friendsButton);
        panel.add(logsButton);
        panel.add(replyButton);
        panel.add(signatureButton);

        // 设置按钮监听事件
        signatureButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                changeSignature();
            }
        });

        // 设置按钮监听事件
        myPersonalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                myPersonal();
            }
        });

        // 设置按钮监听事件
        changePersonalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                changePersonal();
            }
        });

        // 设置按钮监听事件
        friendsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                friends();
            }
        });

        // 设置按钮监听事件
        careerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addCareerInfo();
            }
        });

        // 设置按钮监听事件
        logsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                logs();
            }
        });

        // 设置按钮监听事件
        replyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                reply();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 查看个人签名
     * @param: []
     * @return: void
     */
    private static void changeSignature() {
        JFrame jf = new JFrame("修改个人签名");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel signatureLabel = new JLabel("个性签名:");
        JTextArea signatureText = new JTextArea(8, 30);
        signatureText.setLineWrap(true);

        JButton changeButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        signatureLabel.setBounds(150, 40, 80, 25);
        signatureText.setBounds(150, 70, 300, 200);
        changeButton.setBounds(200, 280, 80, 30);
        returnButton.setBounds(320, 280, 80, 30);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(150, 70, 300, 200);
        scrollPane.setViewportView(signatureText);

        // 加入面板中
        panel.add(signatureLabel);
        panel.add(scrollPane);
        panel.add(changeButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        changeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String text = signatureText.getText();
                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行查询
                    stmt = conn.createStatement();
                    String sql;
                    sql = "UPDATE Signature SET DATA = '" + text + "' WHERE EMAIL = '" + email + "'";

                    // 展开结果集数据库
                    if (stmt.executeUpdate(sql) > 0) {
                        alarm("错误", 60, "修改成功");
                    }
                    // 完成后关闭
                    conn.close();
                    stmt.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                    alarm("错误", 60, "连接错误");
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
                jf.dispose();
            }
        });

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 查看个人信息
     * @param: []
     * @return: void
     */
    private static void myPersonal() {
        // String allEmails = "";
        ArrayList<String[]> allEmails = new ArrayList<>();
        String name = null;
        String sex = null;
        String birthday = null;
        String phone = null;
        String signature = null;
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 连接数据库
            conn = DriverManager.getConnection(DB_URL, username, password);

            // 执行查询
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM user_info WHERE SIGNUP_EMAIL = '" + email + "'";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            if (rs.next()) {
                // 通过字段检索
                name = rs.getString("USERNAME");
                sex = rs.getString("SEX");
                if (sex.equals("m")) {
                    sex = "男";
                } else {
                    sex = "女";
                }
                //allEmails = rs.getString("SIGNUP_EMAIL");
                String[] newEmailRow = new String[]{rs.getString("SIGNUP_EMAIL")};
                allEmails.add(newEmailRow);
                birthday = rs.getString("BIRTHDAY");
                phone = rs.getString("PHONE");
                signature = rs.getString("DATA");
            }
            sql = "SELECT * FROM other_email WHERE EMAIL = '" + email + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String[] newEmailRow = new String[]{rs.getString("OTHER_EMAIL")};
                allEmails.add(newEmailRow);
            }
            // 完成后关闭
            rs.close();
            menu();
            conn.close();
            stmt.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            alarm("错误", 60, "连接错误");
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

        JFrame jf = new JFrame("个人信息");
        jf.setSize(600, 500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel nameLabel = new JLabel("姓名:" + "\t" + name);
        JLabel sexLabel = new JLabel("性别:" + "\t" + sex);
        JLabel birthdayLabel = new JLabel("出生日期:" + "\t" + birthday);
        // JLabel emailLabel = new JLabel("用户名(邮箱):");
        // TextArea emailText = new JTextArea(allEmails);
        // 创建一个表格，指定 所有行数据 和 表头
        Object[] allEmailTableColumnNames = {"用户名(邮箱):"};
        Object[][] allEmailRowData = new Object[allEmails.size()][1];
        for (int i = 0; i < allEmails.size(); i++) {
            allEmailRowData[i] = allEmails.get(i);
        }
        JTable allEmailTable = new JTable(allEmailRowData, allEmailTableColumnNames);
        // 设置表格内容颜色
        allEmailTable.setForeground(Color.BLACK);                   // 字体颜色
        allEmailTable.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
        allEmailTable.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        allEmailTable.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        allEmailTable.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        allEmailTable.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        allEmailTable.getTableHeader().setForeground(Color.BLACK);                // 设置表头名称字体颜色
        allEmailTable.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        allEmailTable.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        allEmailTable.setRowHeight(30);

        // 创建单元格渲染器
        MyTableCellRenderer renderer = new MyTableCellRenderer();
        // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < allEmailTableColumnNames.length; i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = allEmailTable.getColumn(allEmailTableColumnNames[i]);
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }

        JLabel phoneLabel = new JLabel("通讯地址:" + "\t" + phone);
        JLabel signatureLabel = new JLabel("个人签名:");
        JTextArea signatureText = new JTextArea(signature);
        signatureText.setLineWrap(true);
        JButton returnButton = new JButton("返回");
        JButton addEmailsButton = new JButton("注册新邮箱");


        // 设置标签 & 输入框 & 按钮的大小和位置
        nameLabel.setBounds(170, 40, 300, 25);
        sexLabel.setBounds(170, 70, 300, 25);
        birthdayLabel.setBounds(170, 100, 300, 25);
        // emailLabel.setBounds(170, 140, 300, 25);
        // emailText.setBounds(150, 170, 300, 80);
        //allEmailTable.setBounds(170, 140, 300, 100);
        phoneLabel.setBounds(170, 250, 300, 25);
        signatureLabel.setBounds(170, 280, 300, 25);
        signatureText.setBounds(150, 310, 300, 90);
        returnButton.setBounds(160, 400, 80, 30);
        addEmailsButton.setBounds(340, 400, 120, 30);

        allEmailTable.setPreferredScrollableViewportSize(new Dimension(300, 110));
        JScrollPane scrollPane1 = new JScrollPane(allEmailTable);
        scrollPane1.setBounds(150, 130, 300, 120);
        // scrollPane1.setViewportView(emailText);

        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(150, 310, 300, 90);
        scrollPane2.setViewportView(signatureText);

        // 加入面板中
        panel.add(nameLabel);
        panel.add(sexLabel);
        panel.add(birthdayLabel);
        panel.add(scrollPane1);
        // panel.add(emailLabel);
        panel.add(phoneLabel);
        panel.add(returnButton);
        panel.add(signatureLabel);
        panel.add(scrollPane2);
        panel.add(addEmailsButton);

        addEmailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmails();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });

        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 修改个人信息
     * @param: []
     * @return: void
     */
    private static void changePersonal() {
        JFrame jf = new JFrame("修改个人信息");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel nameLabel = new JLabel("姓名:");
        JLabel sexLabel = new JLabel("性别:");
        JLabel birthdayLabel = new JLabel("出生日期:");
        JLabel emailLabel = new JLabel("用户名(邮箱):");
        JLabel phoneLabel = new JLabel("通讯地址:");

        JTextField nameText = new JTextField(20);
        JTextField sexText = new JTextField(20);
        JTextField birthdayText = new JTextField(20);
        JTextField emailText = new JTextField(20);
        JTextField phoneText = new JTextField(20);
        JButton addEmailButton = new JButton("添加邮箱");
        JButton changeButton = new JButton("修改");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        nameLabel.setBounds(150, 40, 80, 25);
        nameText.setBounds(240, 40, 165, 25);
        sexLabel.setBounds(150, 70, 80, 25);
        sexText.setBounds(240, 70, 165, 25);
        birthdayLabel.setBounds(150, 110, 80, 25);
        birthdayText.setBounds(240, 110, 165, 25);
        emailLabel.setBounds(150, 140, 80, 25);
        emailText.setBounds(240, 140, 165, 25);
        phoneLabel.setBounds(150, 170, 80, 25);
        phoneText.setBounds(240, 170, 165, 25);
        addEmailButton.setBounds(260, 200, 120, 30);
        changeButton.setBounds(200, 250, 80, 30);
        returnButton.setBounds(320, 250, 80, 30);

        // 加入面板中
        panel.add(nameLabel);
        panel.add(sexLabel);
        panel.add(birthdayLabel);
        panel.add(emailLabel);
        panel.add(phoneLabel);
        panel.add(nameText);
        panel.add(sexText);
        panel.add(birthdayText);
        panel.add(emailText);
        panel.add(phoneText);
        panel.add(addEmailButton);
        panel.add(changeButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        changeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String sex = "m";
                if (sexText.getText().equals("女")) {
                    sex = "f";
                }
                String birthday = birthdayText.getText();
                String myEmail = emailText.getText();
                if (myEmail == null) {
                    myEmail = email;
                }
                String phone = phoneText.getText();
                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    System.out.println("连接数据库...");
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行更新
                    stmt = conn.createStatement();
                    String sql;
                    sql = "UPDATE REGISTER_USERS SET USERNAME = '" + name + "', SEX ='"
                            + sex + "', BIRTHDAY = '" + birthday + "', EMAIL = '" + myEmail + "', PHONE = '" +
                            phone + "' WHERE USERNAME ='" + email + "'";
                    // System.out.println(sql);
                    if (stmt.executeUpdate(sql) > 0) {
                        System.out.println("register success");
                        stmt.close();
                        conn.close();
                        alarm("成功", 60, "修改成功");
                        jf.dispose();
                    } else {
                        System.out.println("register failed");
                        alarm("错误", 60, "修改失败");
                    }
                    // 完成后关闭
                    stmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                    alarm("错误", 60, "修改失败");
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

            }
        });

        // 设置按钮监听事件
        addEmailButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addEmails();
                jf.dispose();
            }
        });

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 注册其他邮箱, 但后加邮箱不能作为用户名
     * @param: []
     * @return: void
     */
    private static void addEmails() {
        JFrame jf = new JFrame("添加注册邮箱");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel emailLabel = new JLabel("新加邮箱:");
        JTextField emailText = new JTextField(20);
        JButton addButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        emailLabel.setBounds(150, 140, 80, 25);
        emailText.setBounds(240, 140, 165, 25);
        addButton.setBounds(200, 250, 80, 30);
        returnButton.setBounds(320, 250, 80, 30);

        // 加入面板中
        panel.add(emailLabel);
        panel.add(emailText);
        panel.add(addButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String newEmail = emailText.getText();
                if (newEmail.equals("")) {
                    alarm("错误", 180, "注册失败，输入不能为空");
                    jf.dispose();
                    return;
                } else if (newEmail.equals(email)) {
                    alarm("错误", 180, "注册失败，邮箱已注册");
                    jf.dispose();
                    return;
                }

                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    System.out.println("连接数据库...");
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行插入
                    stmt = conn.createStatement();
                    String sql;
                    sql = "INSERT INTO OTHER_EMAIL VALUES(" +
                            "'" + email + "', '" + newEmail + "')";

                    if (stmt.executeUpdate(sql) > 0) {
                        stmt.close();
                        conn.close();
                        alarm("成功", 60, "注册成功");
                        jf.dispose();
                    } else {
                        alarm("错误", 180, "注册失败，邮箱已被注册");
                    }
                    // 完成后关闭
                    stmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    alarm("错误", 180, "注册失败，邮箱已被注册");
                    e1.printStackTrace();
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
            }
        });

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);

    }

    /**
     * @description: 添加个人经历信息, 包括添加学习经历和工作经历功能, 以及其他经历相关操作
     * @param: []
     * @return: void
     */
    private static void addCareerInfo() {
        JFrame jf = new JFrame("添加人格经历");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JButton addLearningCareerButton = new JButton("添加求学经历");
        JButton addWorkingCareerButton = new JButton("添加工作经历");
        JButton lookupCareerButton = new JButton("查看个人经历");
        JButton replyButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        addLearningCareerButton.setBounds(220, 100, 160, 30);
        addWorkingCareerButton.setBounds(220, 140, 160, 30);
        lookupCareerButton.setBounds(220, 180, 160, 30);
        replyButton.setBounds(220, 220, 160, 30);
        // 加入面板中
        panel.add(addLearningCareerButton);
        panel.add(addWorkingCareerButton);
        panel.add(lookupCareerButton);
        panel.add(replyButton);

        addLearningCareerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLearningCareer();
            }
        });

        addWorkingCareerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWorkingCareer();
            }
        });

        lookupCareerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lookupCareer();
            }
        });

        replyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 添加学习经历
     * @param: []
     * @return: void
     */
    private static void addLearningCareer() {
        JFrame jf = new JFrame("添加求学经历");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel schoolNameLabel = new JLabel("学校名称:");
        JLabel educateLevelLabel = new JLabel("教育级别:");
        JLabel startDateLabel = new JLabel("开始日期:");
        JLabel endDateLabel = new JLabel("结束日期:");
        JLabel degreeLabel = new JLabel("学位:");

        JTextField schoolNameText = new JTextField(20);
        JTextField educateLevelText = new JTextField(20);
        JTextField startDateText = new JTextField(20);
        JTextField endDateText = new JTextField(20);
        JTextField degreeText = new JTextField(20);
        JButton addButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        schoolNameLabel.setBounds(150, 40, 80, 25);
        schoolNameText.setBounds(240, 40, 165, 25);
        educateLevelLabel.setBounds(150, 70, 80, 25);
        educateLevelText.setBounds(240, 70, 165, 25);
        startDateLabel.setBounds(150, 100, 80, 25);
        startDateText.setBounds(240, 100, 165, 25);
        endDateLabel.setBounds(150, 130, 80, 25);
        endDateText.setBounds(240, 130, 165, 25);
        degreeLabel.setBounds(150, 160, 80, 25);
        degreeText.setBounds(240, 160, 165, 25);
        addButton.setBounds(200, 250, 80, 30);
        returnButton.setBounds(320, 250, 80, 30);

        // 加入面板中
        panel.add(schoolNameLabel);
        panel.add(schoolNameText);
        panel.add(educateLevelLabel);
        panel.add(educateLevelText);
        panel.add(startDateLabel);
        panel.add(startDateText);
        panel.add(endDateLabel);
        panel.add(endDateText);
        panel.add(degreeLabel);
        panel.add(degreeText);
        panel.add(addButton);
        panel.add(returnButton);

        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String schoolName = schoolNameText.getText();
                String educateLevel = educateLevelText.getText();
                String startDate = startDateText.getText();
                String endDate = endDateText.getText();
                String degree = degreeText.getText();
                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    System.out.println("连接数据库...");
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行更新
                    stmt = conn.createStatement();
                    String sql;
                    sql = "INSERT INTO LEARNS_IN VALUES('" + email + "', '"
                            + schoolName + "',  '" + educateLevel + "',  '" + degree + "', '" + startDate + "', '" +
                            endDate + "') ";
                    // System.out.println(sql);
                    if (stmt.executeUpdate(sql) > 0) {
                        stmt.close();
                        conn.close();
                        alarm("成功", 60, "添加成功");
                        jf.dispose();
                    } else {
                        alarm("错误", 60, "添加失败");
                    }
                    // 完成后关闭
                    stmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                    alarm("错误", 60, "添加失败");
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

            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 添加工作经历
     * @param: []
     * @return: void
     */
    private static void addWorkingCareer() {
        JFrame jf = new JFrame("添加工作经历");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel workplaceLabel = new JLabel("工作单位:");
        JLabel jobLabel = new JLabel("职业:");
        JLabel startDateLabel = new JLabel("开始日期:");
        JLabel endDateLabel = new JLabel("结束日期:");

        JTextField workplaceText = new JTextField(20);
        JTextField jobText = new JTextField(20);
        JTextField startDateText = new JTextField(20);
        JTextField endDateText = new JTextField(20);
        JButton addButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        workplaceLabel.setBounds(150, 40, 80, 25);
        workplaceText.setBounds(240, 40, 165, 25);
        jobLabel.setBounds(150, 70, 80, 25);
        jobText.setBounds(240, 70, 165, 25);
        startDateLabel.setBounds(150, 100, 80, 25);
        startDateText.setBounds(240, 100, 165, 25);
        endDateLabel.setBounds(150, 130, 80, 25);
        endDateText.setBounds(240, 130, 165, 25);
        addButton.setBounds(200, 250, 80, 30);
        returnButton.setBounds(320, 250, 80, 30);

        // 加入面板中
        panel.add(workplaceLabel);
        panel.add(workplaceText);
        panel.add(jobLabel);
        panel.add(jobText);
        panel.add(startDateLabel);
        panel.add(startDateText);
        panel.add(endDateLabel);
        panel.add(endDateText);
        panel.add(addButton);
        panel.add(returnButton);

        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String workplace = workplaceText.getText();
                String job = jobText.getText();
                String startDate = startDateText.getText();
                String endDate = endDateText.getText();
                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    System.out.println("连接数据库...");
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行更新
                    stmt = conn.createStatement();
                    String sql;
                    sql = "INSERT INTO WORKS_ON VALUES('" + email + "', '"
                            + workplace + "',  '" + job + "', '" + startDate + "', '" +
                            endDate + "') ";
                    // System.out.println(sql);
                    if (stmt.executeUpdate(sql) > 0) {
                        stmt.close();
                        conn.close();
                        alarm("成功", 60, "添加成功");
                        jf.dispose();
                    } else {
                        alarm("错误", 60, "添加失败");
                    }
                    // 完成后关闭
                    stmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                    alarm("错误", 60, "添加失败");
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

            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 查看个人经历
     * @param: []
     * @return: void
     */
    private static void lookupCareer() {
        JFrame jf = new JFrame("查看个人经历");
        jf.setSize(600, 700);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        ArrayList<String[]> learnRows = new ArrayList<>();
        ArrayList<String[]> workRows = new ArrayList<>();

        // String learnStr = "";
        // String workStr = "";
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 连接数据库
            conn = DriverManager.getConnection(DB_URL, username, password);

            // 执行查询
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM WORKS_ON WHERE EMAIL = '" + email + "'";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                String[] newWorkTableRow = new String[]{rs.getString("WORKPLACE_NAME"), rs.getString("JOB"), rs.getString("START_DATE"), rs.getString("END_DATE")};
                workRows.add(newWorkTableRow);
                // workStr = workStr + "工作单位：" + rs.getString("WORKPLACE_NAME") + "\n";
                // workStr = workStr + "职业：" + rs.getString("JOB") + "\n";
                // workStr = workStr + "开始日期：" + rs.getString("START_DATE") + "\n";
                // workStr = workStr + "结束日期:" + rs.getString("END_DATE") + "\n\n";
            }
            sql = "SELECT * FROM LEARNS_IN WHERE EMAIL = '" + email + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // 通过字段检索
                String[] newLearnTableRow = new String[]{rs.getString("SCHOOL_NAME"), rs.getString("EDUCATE_LEVEL"), rs.getString("DEGREE"), rs.getString("START_DATE"), rs.getString("END_DATE")};
                learnRows.add(newLearnTableRow);
                // learnStr = learnStr + "学校名称：" + rs.getString("SCHOOL_NAME") + "\n";
                // learnStr = learnStr + "教育级别：" + rs.getString("EDUCATE_LEVEL") + "\n";
                // learnStr = learnStr + "学位：" + rs.getString("DEGREE") + "\n";
                // learnStr = learnStr + "开始日期：" + rs.getString("START_DATE") + "\n";
                // learnStr = learnStr + "结束日期:" + rs.getString("END_DATE") + "\n\n";
            }
            // 完成后关闭
            rs.close();
            menu();
            conn.close();
            stmt.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            alarm("错误", 60, "连接错误");
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

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel workLabel = new JLabel("工作经历:");
        JLabel learnLabel = new JLabel("学习经历:");
        // JTextArea workText = new JTextArea(workStr);
        // JTextArea learnText = new JTextArea(learnStr);
        // workText.setLineWrap(true);
        // learnText.setLineWrap(true);
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        workLabel.setBounds(150, 40, 80, 25);
        // workText.setBounds(150, 70, 300, 200);
        learnLabel.setBounds(150, 280, 80, 25);
        // learnText.setBounds(150, 310, 300, 200);
        returnButton.setBounds(260, 600, 80, 30);

        Object[] allWorkTableColumnNames = {"工作单位", "职业", "开始日期", "结束日期"};
        Object[][] allWorkRowData = new Object[workRows.size()][1];
        for (int i = 0; i < workRows.size(); i++) {
            allWorkRowData[i] = workRows.get(i);
        }
        JTable workTable = new JTable(allWorkRowData, allWorkTableColumnNames);
        // 设置表格内容颜色
        workTable.setForeground(Color.BLACK);                   // 字体颜色
        workTable.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
        workTable.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        workTable.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        workTable.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        workTable.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        workTable.getTableHeader().setForeground(Color.BLACK);                // 设置表头名称字体颜色
        workTable.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        workTable.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        workTable.setRowHeight(30);

        // 创建单元格渲染器
        MyTableCellRenderer renderer = new MyTableCellRenderer();
        // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < allWorkTableColumnNames.length; i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = workTable.getColumn(allWorkTableColumnNames[i]);
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }

        JScrollPane scrollPane1 = new JScrollPane(workTable);
        scrollPane1.setBounds(50, 70, 500, 210);

        Object[] allLearnTableColumnNames = {"学校名称", "教育级别", "学位", "开始日期", "结束日期"};
        Object[][] allLearnRowData = new Object[learnRows.size()][1];
        for (int i = 0; i < learnRows.size(); i++) {
            allLearnRowData[i] = learnRows.get(i);
        }
        JTable learnTable = new JTable(allLearnRowData, allLearnTableColumnNames);
        // 设置表格内容颜色
        learnTable.setForeground(Color.BLACK);                   // 字体颜色
        learnTable.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
        learnTable.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        learnTable.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        learnTable.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        learnTable.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        learnTable.getTableHeader().setForeground(Color.BLACK);                // 设置表头名称字体颜色
        learnTable.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        learnTable.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        learnTable.setRowHeight(30);

        // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < allLearnTableColumnNames.length; i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = learnTable.getColumn(allLearnTableColumnNames[i]);
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }
        JScrollPane scrollPane2 = new JScrollPane(learnTable);
        scrollPane2.setBounds(50, 310, 500, 210);

        // 加入面板中
        panel.add(learnLabel);
        panel.add(scrollPane1);
        panel.add(learnLabel);
        panel.add(scrollPane2);
        panel.add(returnButton);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 好友相关操作界面, 可选择好友相关功能
     * @param: []
     * @return: void
     */
    private static void friends() {
        JFrame jf = new JFrame("好友");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        JButton addFriendsButton = new JButton("添加好友");
        JButton allFriendsButton = new JButton("所有好友");
        JButton manageFriendGroupsButton = new JButton("管理好友分组");
        JButton searchFriendsButton = new JButton("搜索好友");
        JButton deleteFriendsButton = new JButton("删除好友");
        JButton returnButton = new JButton("返回");

        addFriendsButton.setBounds(220, 20, 160, 30);
        allFriendsButton.setBounds(220, 60, 160, 30);
        manageFriendGroupsButton.setBounds(220, 100, 160, 30);
        searchFriendsButton.setBounds(220, 140, 160, 30);
        deleteFriendsButton.setBounds(220, 180, 160, 30);
        returnButton.setBounds(220, 220, 160, 30);

        panel.add(addFriendsButton);
        panel.add(allFriendsButton);
        panel.add(manageFriendGroupsButton);
        panel.add(searchFriendsButton);
        panel.add(deleteFriendsButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        addFriendsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addFriends();
            }
        });

        allFriendsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                allFriends();
            }
        });

        manageFriendGroupsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                manageFriendGroups();
            }
        });

        searchFriendsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                searchFriends();
            }
        });

        deleteFriendsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                deleteFriends();
            }
        });

        returnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 添加好友, 按照邮箱
     * @param: []
     * @return: void
     */
    private static void addFriends() {
        JFrame jf = new JFrame("添加好友");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel emailLabel = new JLabel("好友邮箱:");
        JTextField emailText = new JTextField(180);
        JButton addButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        emailLabel.setBounds(150, 140, 80, 25);
        emailText.setBounds(240, 140, 165, 25);
        addButton.setBounds(200, 250, 80, 30);
        returnButton.setBounds(320, 250, 80, 30);

        // 加入面板中
        panel.add(emailLabel);
        panel.add(emailText);
        panel.add(addButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String addEmail = emailText.getText();

                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    System.out.println("连接数据库...");
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行查找
                    stmt = conn.createStatement();
                    String sql;
                    sql = "INSERT INTO friends(EMAIL,FRIEND) VALUES( '" + email + "','" + addEmail + "')";

                    if (stmt.executeUpdate(sql) > 0) {
                        alarm("成功", 60, "添加成功");
                        jf.dispose();
                    } else {
                        alarm("错误", 60, "添加失败");
                    }
                    // 完成后关闭
                    stmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    alarm("错误", 60, "添加失败");
                    e1.printStackTrace();
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
            }
        });

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 查看所有好友, 按照树状给出, 树节点为好友分组名
     * @param: []
     * @return: void
     */
    private static void allFriends() {
        JFrame jf = new JFrame("所有好友");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        HashMap<String, DefaultMutableTreeNode> twoLevelNodes = new HashMap<>();

        // 创建根节点
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("好友");

        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 连接数据库
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, username, password);

            // 执行插入
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT GROUP_NAME FROM FRIEND_GROUP WHERE EMAIL = '" + email + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String newGroup = rs.getString("GROUP_NAME");
                // 创建二级节点
                DefaultMutableTreeNode twoLevelNode = new DefaultMutableTreeNode(newGroup);
                twoLevelNodes.put(newGroup, twoLevelNode);
                // 把二级节点作为子节点添加到根节点
                rootNode.add(twoLevelNode);
            }
            for (String groupName : twoLevelNodes.keySet()) {
                sql = "SELECT * FROM FRIENDS WHERE EMAIL = '" + email + "' AND GROUP_NAME = '" + groupName + "'";
                rs = stmt.executeQuery(sql);
                DefaultMutableTreeNode fatherNode = twoLevelNodes.get(groupName);
                while (rs.next()) {
                    // 创建三级节点
                    DefaultMutableTreeNode threeLevelNode = new DefaultMutableTreeNode(rs.getString("FRIEND"));
                    fatherNode.add(threeLevelNode);
                }
            }
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e1) {
            e1.printStackTrace();
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

        // 使用根节点创建树组件
        JTree tree = new JTree(rootNode);

        // 设置树显示根节点句柄
        tree.setShowsRootHandles(true);

        // 设置树节点可编辑
        tree.setEditable(true);

        // 创建滚动面板，包裹树（因为树节点展开后可能需要很大的空间来显示，所以需要用一个滚动面板来包裹）
        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setBounds(50, 40, 480, 250);
        // 添加滚动面板到那内容面板
        panel.add(scrollPane);


        //JTextArea allFriendsText = new JTextArea(text);
        JButton returnButton = new JButton("返回");

        //allFriendsText.setBounds(50, 40, 480, 250);
        returnButton.setBounds(260, 300, 80, 30);

        panel.add(returnButton);

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 管理好友分组
     * @param: []
     * @return: void
     */
    private static void manageFriendGroups() {
        JFrame jf = new JFrame("管理分组");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        JButton addGroupsButton = new JButton("添加分组");
        JButton moveFriendGroupsButton = new JButton("移动好友");
        JButton allGroupsButton = new JButton("所有分组");
        JButton returnButton = new JButton("返回");


        addGroupsButton.setBounds(220, 60, 160, 30);
        moveFriendGroupsButton.setBounds(220, 100, 160, 30);
        allGroupsButton.setBounds(220, 140, 160, 30);
        returnButton.setBounds(220, 220, 160, 30);

        panel.add(addGroupsButton);
        panel.add(moveFriendGroupsButton);
        panel.add(allGroupsButton);
        panel.add(returnButton);

        addGroupsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addGroups();
            }
        });

        moveFriendGroupsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                moveFriendGroups();
            }
        });

        allGroupsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn = null;
                Statement stmt = null;
                ArrayList<String[]> rows = new ArrayList<>();
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    System.out.println("连接数据库...");
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行查找
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT * FROM friend_group WHERE EMAIL = '" + email + "'";

                    ResultSet rs = stmt.executeQuery(sql);
                    //String str = "";
                    while (rs.next()) {
                        String[] newRow = new String[]{rs.getString("GROUP_NAME")};
                        // str = str + rs.getString("GROUP_NAME") + "\n";
                        rows.add(newRow);
                    }
                    if(rows.size()>0){
                        String[] rowNames = new String[]{"分组名"};
                        Object[][] rowData = new Object[rows.size()][1];
                        for (int i = 0; i < rows.size(); i++) {
                            rowData[i] = rows.get(i);
                        }
                        result(rowNames, rowData);
                    }
                    else{
                        alarm("错误",80,"没有分组");
                    }
                    jf.dispose();
                    // 完成后关闭
                    stmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
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

            }
        });

        returnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 添加好友分组
     * @param: []
     * @return: void
     */
    private static void addGroups() {
        JFrame jf = new JFrame("添加分组");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel groupLabel = new JLabel("分组名:");
        JTextField groupText = new JTextField(180);
        JButton addButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        groupLabel.setBounds(150, 140, 80, 25);
        groupText.setBounds(240, 140, 165, 25);
        addButton.setBounds(200, 250, 80, 30);
        returnButton.setBounds(320, 250, 80, 30);

        // 加入面板中
        panel.add(groupLabel);
        panel.add(groupText);
        panel.add(addButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String groupName = groupText.getText();

                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    System.out.println("连接数据库...");
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行查找
                    stmt = conn.createStatement();
                    String sql;
                    sql = "INSERT INTO friend_group VALUES( '" + email + "','" + groupName + "')";

                    if (stmt.executeUpdate(sql) > 0) {
                        alarm("成功", 60, "添加成功");
                        jf.dispose();
                    } else {
                        alarm("错误", 60, "添加失败");
                    }
                    // 完成后关闭
                    stmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    alarm("错误", 60, "添加失败");
                    e1.printStackTrace();
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
            }
        });

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 将好友移动到指定分组
     * @param: []
     * @return: void
     */
    private static void moveFriendGroups() {
        JFrame jf = new JFrame("移动好友分组");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel friendLabel = new JLabel("将好友(邮箱):");
        JTextField friendText = new JTextField(180);
        JLabel groupLabel = new JLabel("移动到:");
        JTextField groupText = new JTextField(180);
        JButton addButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        friendLabel.setBounds(150, 140, 80, 25);
        friendText.setBounds(240, 140, 165, 25);
        groupLabel.setBounds(150, 170, 80, 25);
        groupText.setBounds(240, 170, 165, 25);
        addButton.setBounds(200, 250, 80, 30);
        returnButton.setBounds(320, 250, 80, 30);

        // 加入面板中
        panel.add(friendLabel);
        panel.add(friendText);
        panel.add(groupLabel);
        panel.add(groupText);
        panel.add(addButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String groupName = groupText.getText();
                String friendEmail = friendText.getText();
                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    System.out.println("连接数据库...");
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行查找
                    stmt = conn.createStatement();
                    String sql;
                    sql = "UPDATE friends SET GROUP_NAME ='" + groupName + "' WHERE FRIEND ='" + friendEmail + "' AND EMAIL ='" + email + "'";

                    if (stmt.executeUpdate(sql) > 0) {
                        alarm("成功", 60, "添加成功");
                        jf.dispose();
                    } else {
                        alarm("错误", 60, "添加失败");
                    }
                    // 完成后关闭
                    stmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    alarm("错误", 60, "添加失败");
                    e1.printStackTrace();
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
            }
        });

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 查询好友
     * @param: []
     * @return: void
     */
    private static void searchFriends() {
        JFrame jf = new JFrame("查找好友");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JButton searchFriendsFromNameButton = new JButton("按照姓名查找");
        JButton searchFriendsFromEmailButton = new JButton("按照邮箱查找");
        JButton replyButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        searchFriendsFromNameButton.setBounds(220, 100, 160, 30);
        searchFriendsFromEmailButton.setBounds(220, 150, 160, 30);
        replyButton.setBounds(260, 260, 80, 30);

        // 加入面板中
        panel.add(searchFriendsFromNameButton);
        panel.add(searchFriendsFromEmailButton);
        panel.add(replyButton);

        searchFriendsFromNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFriendsFromName();
            }
        });

        searchFriendsFromEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFriendsFromEmail();
            }
        });


        replyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 根据姓名查找好友
     * @param: []
     * @return: void
     */
    private static void searchFriendsFromName() {
        JFrame jf = new JFrame("按姓名查找好友");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel nameLabel = new JLabel("好友姓名:");
        JTextField nameText = new JTextField(180);
        JButton searchButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        nameLabel.setBounds(150, 140, 80, 25);
        nameText.setBounds(240, 140, 165, 25);
        searchButton.setBounds(200, 250, 80, 30);
        returnButton.setBounds(320, 250, 80, 30);

        // 加入面板中
        panel.add(nameLabel);
        panel.add(nameText);
        panel.add(searchButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String searchName = nameText.getText();
                ArrayList<String[]> rows = new ArrayList<>();

                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    System.out.println("连接数据库...");
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行查找
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT * FROM friendsInfo WHERE friend_name = '" + searchName + "' AND EMAIL ='"
                            + email + "'";

                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        String[] newRow = new String[]{rs.getString("friend_name"), rs.getString("friend_email"), rs.getString("group_name")};
                        // String str = "姓名：" + rs.getString("friend_name") + "\n";
                        // str = str + "邮箱：" + rs.getString("friend_email") + "\n";
                        // tr = str + "分组：" + rs.getString("group_name");
                        rows.add(newRow);
                    }
                    if (rows.size() > 0) {
                        Object[] rowNames = {"姓名", "邮箱", "分组"};
                        Object[][] rowData = new Object[rows.size()][3];
                        for (int i = 0; i < rows.size(); i++) {
                            rowData[i] = rows.get(i);
                        }
                        int[] rowWidth = {100, 200, 100};
                        result(rowNames, rowData, rowWidth);
                    } else {
                        alarm("错误", 80, "没有这个人");
                    }
                    // 完成后关闭
                    stmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
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
            }
        });

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 根据邮箱查找好友
     * @param: []
     * @return: void
     */
    private static void searchFriendsFromEmail() {
        JFrame jf = new JFrame("按邮箱查找好友");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel emailLabel = new JLabel("好友邮箱:");
        JTextField emailText = new JTextField(180);
        JButton searchButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        emailLabel.setBounds(150, 140, 80, 25);
        emailText.setBounds(240, 140, 165, 25);
        searchButton.setBounds(200, 250, 80, 30);
        returnButton.setBounds(320, 250, 80, 30);

        // 加入面板中
        panel.add(emailLabel);
        panel.add(emailText);
        panel.add(searchButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String searchEmail = emailText.getText();

                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    System.out.println("连接数据库...");
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行查找
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT * FROM friendsInfo WHERE friend_email = '" + searchEmail + "' AND EMAIL ='"
                            + email + "'";

                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        String[] newRow = new String[]{rs.getString("friend_name"), rs.getString("friend_email"), rs.getString("group_name")};
                        // String str = "姓名：" + rs.getString("friend_name") + "\n";
                        // str = str + "邮箱：" + rs.getString("friend_email") + "\n";
                        // tr = str + "分组：" + rs.getString("group_name");
                        Object[] rowNames = {"姓名", "邮箱", "分组"};
                        Object[][] rowData = new Object[1][3];
                        rowData[0] = newRow;
                        int[] rowWidth = {100, 200, 100};
                        result(rowNames, rowData, rowWidth);
                    } else {
                        alarm("错误", 80, "没有这个人");
                    }
                    // 完成后关闭
                    stmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
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
            }
        });

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 删除好友
     * @param: []
     * @return: void
     */
    private static void deleteFriends() {
        JFrame jf = new JFrame("删除好友");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel emailLabel = new JLabel("好友邮箱:");
        JTextField emailText = new JTextField(180);
        JButton searchButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        emailLabel.setBounds(150, 140, 80, 25);
        emailText.setBounds(240, 140, 165, 25);
        searchButton.setBounds(200, 250, 80, 30);
        returnButton.setBounds(320, 250, 80, 30);

        // 加入面板中
        panel.add(emailLabel);
        panel.add(emailText);
        panel.add(searchButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String searchEmail = emailText.getText();

                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    System.out.println("连接数据库...");
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行查找
                    stmt = conn.createStatement();
                    String sql;
                    sql = "DELETE FROM FRIENDS WHERE FRIEND = '" + searchEmail + "' AND EMAIL ='"
                            + email + "'";
                    if (stmt.executeUpdate(sql) > 0) {
                        alarm("成功", 60, "删除成功");
                        jf.dispose();
                    }
                    // 完成后关闭
                    stmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    alarm("错误", 60, "删除失败");
                    jf.dispose();
                    e1.printStackTrace();
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
            }
        });

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 日志相关操作
     * @param: []
     * @return: void
     */
    private static void logs() {
        JFrame jf = new JFrame("日志");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        JButton allLogsButton = new JButton("所有日志");
        JButton searchLogsButton = new JButton("查看日志");
        JButton writeLogsButton = new JButton("写日志");
        JButton replyLogsButton = new JButton("评论日志");
        JButton returnButton = new JButton("返回");

        allLogsButton.setBounds(220, 60, 160, 30);
        searchLogsButton.setBounds(220, 100, 160, 30);
        writeLogsButton.setBounds(220, 140, 160, 30);
        replyLogsButton.setBounds(220, 180, 160, 30);
        returnButton.setBounds(220, 220, 160, 30);

        panel.add(allLogsButton);
        panel.add(searchLogsButton);
        panel.add(writeLogsButton);
        panel.add(replyLogsButton);
        panel.add(returnButton);

        allLogsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                allLogs();
            }
        });

        searchLogsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                searchLogs();
            }
        });

        writeLogsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                writeLogs();
            }
        });

        replyLogsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                replyLogs();
            }
        });

        returnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);

    }

    /**
     * @description: 查看所有日志简略信息, 可查看对应日志号后使用“查看日志”详细查询日志信息
     * @param: []
     * @return: void
     */
    private static void allLogs() {
        JFrame jf = new JFrame("所有日志");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局
        Object[] rowNames = null;
        Object[][] rowData = null;
        int[] rowWidth = null;
        //String text = "";
        ArrayList<String[]> rows = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 连接数据库
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, username, password);

            // 执行插入
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM user_update_logs WHERE EMAIL = '" + email + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String[] newRow = new String[]{rs.getString("LNO"), rs.getString("LOG_NAME"), rs.getString("USERNAME"), rs.getString("EMAIL"), rs.getString("LAST_UPDATE")};
//                String authorName = rs.getString("USERNAME");
//                String authorEmail = rs.getString("EMAIL");
//                String LNO = rs.getString("LNO");
//                String logName = rs.getString("LOG_NAME");
//                String lastUpdateDate = rs.getString("LAST_UPDATE");
                rows.add(newRow);
            }

            if (rows.size() > 0) {
                rowNames = new Object[]{"日志编号", "日志标题", "作者", "邮箱", "最后更新时间"};
                rowData = new Object[rows.size()][5];
                for (int i = 0; i < rows.size(); i++) {
                    rowData[i] = rows.get(i);
                }
                rowWidth = new int[]{50, 120, 80, 150, 100};
            } else {
                alarm("错误", 60, "没有日志");
            }
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e1) {
            e1.printStackTrace();
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

        JTable table = new JTable(rowData, rowNames);
        // 设置表格内容颜色
        table.setForeground(Color.BLACK);                   // 字体颜色
        table.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
        table.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        table.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        table.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        table.getTableHeader().setForeground(Color.BLACK);                // 设置表头名称字体颜色
        table.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        table.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        table.setRowHeight(30);

        // 设置列宽
        for (int i = 0; i < rowWidth.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(rowWidth[i]);
        }

        // 创建单元格渲染器
        MyTableCellRenderer renderer = new MyTableCellRenderer();
        // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < rowNames.length; i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = table.getColumn(rowNames[i]);
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 50, 500, 270);

        // JTextArea allLogsText = new JTextArea(text);
        JButton returnButton = new JButton("返回");

        // llLogsText.setBounds(50, 40, 480, 250);
        returnButton.setBounds(260, 320, 80, 30);

        panel.add(scrollPane);
        panel.add(returnButton);

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 根据日志号查找日志详细信息, 以及相关评论
     * @param: []
     * @return: void
     */
    private static void searchLogs() {
        JFrame jf = new JFrame("查看日志");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel LNOLabel = new JLabel("日志编号:");
        JTextField LNOText = new JTextField(180);
        JButton addButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        LNOLabel.setBounds(150, 140, 80, 25);
        LNOText.setBounds(240, 140, 165, 25);
        addButton.setBounds(200, 250, 80, 30);
        returnButton.setBounds(320, 250, 80, 30);

        // 加入面板中
        panel.add(LNOLabel);
        panel.add(LNOText);
        panel.add(addButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String LNO = LNOText.getText();
                // String text = "";

                String[] logRows = new String[3];
                ArrayList<String[]> replyRows = new ArrayList<>();

                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    System.out.println("连接数据库...");
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行插入
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT * FROM log_info WHERE LNO = '" + LNO + "'";
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        String authorName = rs.getString("USERNAME");
                        String authorEmail = rs.getString("EMAIL");
                        String logName = rs.getString("LOG_NAME");
                        String lastUpdateDate = rs.getString("LAST_UPDATE");
                        String logData = rs.getString("LOG_DATA");
//                        text = text + "日志编号：" + LNO + "\t" + "日志标题：" + logName + "\n";
//                        text = text + "作者：" + authorName + "(" + authorEmail + ")" + "\n" + "最后更新时间" + lastUpdateDate +
//                                "\n日志内容：\t" + logData + "\n\n" + "评论：" + "\n";
                        logRows[0] = "[" + LNO + "]" + logName;
                        logRows[1] = "作者：" + authorName + "(" + authorEmail + ")         最后更新时间：" + lastUpdateDate;

                        logRows[2] = logData;
                        sql = "SELECT * FROM user_review_logs WHERE LNO = '" + LNO + "'";
                        rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            String[] newRow = new String[]{rs.getString("REVIEW_USER"), rs.getString("REVIEW_EMAIL"), rs.getString("REVIEW_DATE"), rs.getString("REVIEW_DATA")};
                            replyRows.add(newRow);
//                        String reviewer = rs.getString("REVIEW_USER");
//                        String reviewerEmail = rs.getString("REVIEW_EMAIL");
//                        String reviewDate = rs.getString("REVIEW_DATE");
//                        String reviewData = rs.getString("REVIEW_DATA");
//                        text = text + "评论者：" + reviewer + "(" + reviewerEmail + ")" + "\n" + "评论时间：" + reviewDate + "\n评论内容\t" + reviewData + "\n";
                        }

                        Object[] replyRowNames = {"评论者", "邮箱", "评论时间", "评论内容"};
                        Object[][] replyRowData = new Object[replyRows.size()][4];
                        for (int i = 0; i < replyRows.size(); i++) {
                            replyRowData[i] = replyRows.get(i);
                        }
                        int[] rowsWidth = {60, 100, 80, 160};
                        resultLog(logRows[0], logRows[1], logRows[2], replyRowNames, replyRowData, rowsWidth);
                    } else {
                        alarm("错误", 80, "没有这个日志");
                    }

                    // 完成后关闭
                    stmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
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
            }
        });

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 写日志
     * @param: []
     * @return: void
     */
    private static void writeLogs() {
        JFrame jf = new JFrame("写日志");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel themeLabel = new JLabel("日志标题:");
        JTextField themeText = new JTextField();
        JTextArea logText = new JTextArea(8, 30);
        logText.setLineWrap(true);

        JButton changeButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        themeLabel.setBounds(150, 40, 80, 25);
        themeText.setBounds(230, 40, 165, 25);
        logText.setBounds(150, 70, 300, 200);
        changeButton.setBounds(200, 280, 80, 30);
        returnButton.setBounds(320, 280, 80, 30);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(150, 70, 300, 200);
        scrollPane.setViewportView(logText);

        // 加入面板中
        panel.add(themeLabel);
        panel.add(themeText);
        panel.add(scrollPane);
        panel.add(changeButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        changeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String themeStr = themeText.getText();
                String textStr = logText.getText();

                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行查询
                    conn.setAutoCommit(false);
                    stmt = conn.createStatement();

                    // 向日志表中插入新的日志
                    PreparedStatement ps1 = conn.prepareStatement("INSERT INTO LOGS(LOG_NAME,LOG_DATA) VALUES(?,?)");
                    ps1.setString(1, themeStr);
                    ps1.setString(2, textStr);
                    ps1.executeUpdate();
                    // 查找刚刚插入的新的日志获得的自动增加的日志号
                    String sql = "SELECT LNO FROM LOGS WHERE LOG_NAME = '" + themeStr + "' AND LOG_DATA = '" + textStr + "'";
                    ResultSet rs = stmt.executeQuery(sql);
                    int TNO = -1;
                    if (rs.next()) {
                        TNO = rs.getInt("LNO");
                    }
                    // 更新日志更新表
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    PreparedStatement ps2 = conn.prepareStatement("INSERT INTO UPDATE_LOGS(EMAIL,LNO,LAST_UPDATE) VALUES(?,?,?)");
                    ps2.setString(1, email);
                    ps2.setInt(2, TNO);
                    ps2.setString(3, formatter.format(date));
                    ps2.executeUpdate();
//                    String sql;
//                    sql = "INSERT INTO LOGS(LOG_NAME,LOG_DATA) VALUES('" + themeStr + "','" + textStr + "')";
//                    // 展开结果集数据库
//                    if (stmt.executeUpdate(sql) > 0) {
//                        sql = "SELECT LNO FROM LOGS WHERE LOG_NAME = '" + themeStr + "' AND LOG_DATA = '" + textStr + "'";
//
//                        ResultSet rs = stmt.executeQuery(sql);
//                        int TNO = -1;
//                        if (rs.next()) {
//                            TNO = rs.getInt("LNO");
//                            Date date = new Date();
//                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                            //System.out.println(formatter.format(date));
//                            sql = "INSERT INTO UPDATE_LOGS VALUES('" + email + "','" + TNO + "','" + formatter.format(date) + "')";
//                            System.out.println(sql);
//                            if (stmt.executeUpdate(sql) > 0) {
//                                alarm("写日志成功");
//                            } else {
//                                alarm("写日志失败");
//                            }
//                        } else {
//                            alarm("写日志失败");
//                        }
//                    }
                    conn.commit();
                    // 完成后关闭
                    conn.close();
                    stmt.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    alarm("错误", 60, "连接错误");
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
                jf.dispose();
            }
        });

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 评论日志
     * @param: []
     * @return: void
     */
    private static void replyLogs() {
        JFrame jf = new JFrame("评论日志");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel LNOLabel = new JLabel("日志编号:");
        JTextField LNOText = new JTextField();
        JTextArea logText = new JTextArea(8, 30);
        logText.setLineWrap(true);

        JButton changeButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        LNOLabel.setBounds(150, 40, 80, 25);
        LNOText.setBounds(230, 40, 165, 25);
        logText.setBounds(150, 70, 300, 200);
        changeButton.setBounds(200, 280, 80, 30);
        returnButton.setBounds(320, 280, 80, 30);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(150, 70, 300, 200);
        scrollPane.setViewportView(logText);

        // 加入面板中
        panel.add(LNOLabel);
        panel.add(LNOText);
        panel.add(scrollPane);
        panel.add(changeButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        changeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String LNO = LNOText.getText();
                String textStr = logText.getText();

                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行查询
                    stmt = conn.createStatement();
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                    String sql;
                    sql = "INSERT INTO REVIEW_LOGS(EMAIL,LNO,REVIEW_DATE,REVIEW_DATA) VALUES('" + email + "','" + LNO + "','" + formatter.format(date) + "','" + textStr + "')";

                    // 展开结果集数据库
                    if (stmt.executeUpdate(sql) > 0) {
                        alarm("成功", 60, "评论成功");
                    } else {
                        alarm("错误", 60, "评论失败");
                    }
                    // 完成后关闭
                    conn.close();
                    stmt.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                    alarm("错误", 60, "连接错误");
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
                jf.dispose();
            }
        });

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 好友回复相关操作
     * @param: []
     * @return: void
     */
    private static void reply() {
        JFrame jf = new JFrame("回复");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        JButton allReplyButton = new JButton("查看好友回复");
        JButton replyButton = new JButton("回复好友");
        JButton returnButton = new JButton("返回");

        allReplyButton.setBounds(220, 60, 160, 30);
        replyButton.setBounds(220, 100, 160, 30);
        returnButton.setBounds(220, 220, 160, 30);

        panel.add(replyButton);
        panel.add(allReplyButton);
        panel.add(returnButton);

        replyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replyFriends();
            }
        });

        allReplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allReply();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 所有好友回复信息
     * @param: []
     * @return: void
     */
    private static void allReply() {
        JFrame jf = new JFrame("所有好友回复");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        //String text = "";
        Object[] rowNames = {"回复者", "邮箱", "回复日期", "回复内容"};
        Object[][] rowData = null;
        ArrayList<String[]> rows = new ArrayList<String[]>();
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 连接数据库
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, username, password);

            // 执行插入
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM user_reply_friends WHERE RECIEVER_EMAIL = '" + email + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String[] newRow = new String[]{rs.getString("RESPONDER_NAME"), rs.getString("RESPONDER_EMAIL"), rs.getString("REPLY_DATE"), rs.getString("REPLY_DATA")};
                rows.add(newRow);
//                String RESPONDER_NAME = rs.getString("RESPONDER_NAME");
//                String RESPONDER_EMAIL = rs.getString("RESPONDER_EMAIL");
//                String REPLY_DATE = rs.getString("REPLY_DATE");
//                String REPLY_DATA = rs.getString("REPLY_DATA");
//                text = text + "回复者：" + RESPONDER_NAME + "(" + RESPONDER_EMAIL + ")" + "\n" + "回复日期" + REPLY_DATE + "\n";
//                text = text + "回复内容：" + REPLY_DATA + "\n";
            }
            if (rows.size() > 0) {
                rowData = new Object[rows.size()][4];
                for (int i = 0; i < rows.size(); i++) {
                    rowData[i] = rows.get(i);
                }
            } else {
                alarm("错误", 60, "没有回复");
            }
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e1) {
            e1.printStackTrace();
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

        JTable table = new JTable(rowData, rowNames);
        // 设置表格内容颜色
        table.setForeground(Color.BLACK);                   // 字体颜色
        table.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
        table.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        table.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        table.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        table.getTableHeader().setForeground(Color.BLACK);                // 设置表头名称字体颜色
        table.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        table.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        table.setRowHeight(60);

        int[] rowWidth = {80, 140, 80, 200};
        // 设置列宽
        for (int i = 0; i < rowWidth.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(rowWidth[i]);
        }

        // 创建单元格渲染器
        MyTableCellRenderer renderer = new MyTableCellRenderer();
        // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < rowNames.length; i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = table.getColumn(rowNames[i]);
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 50, 500, 240);

        // JTextArea allLogsText = new JTextArea(text);
        JButton returnButton = new JButton("返回");

        // allLogsText.setBounds(50, 40, 480, 250);
        returnButton.setBounds(260, 320, 80, 30);


        panel.add(scrollPane);
        panel.add(returnButton);

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 回复好友
     * @param: []
     * @return: void
     */
    private static void replyFriends() {
        JFrame jf = new JFrame("回复好友");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);    // 面板布局

        // 创建标签 & 输入框 & 按钮
        JLabel friendEmailLabel = new JLabel("好友邮箱:");
        JTextField friendEmailText = new JTextField();
        JTextArea replyText = new JTextArea(8, 30);
        replyText.setLineWrap(true);

        JButton changeButton = new JButton("确定");
        JButton returnButton = new JButton("返回");

        // 设置标签 & 输入框 & 按钮的大小和位置
        friendEmailLabel.setBounds(150, 40, 80, 25);
        friendEmailText.setBounds(230, 40, 165, 25);
        replyText.setBounds(150, 70, 300, 200);
        changeButton.setBounds(200, 280, 80, 30);
        returnButton.setBounds(320, 280, 80, 30);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(150, 70, 300, 200);
        scrollPane.setViewportView(replyText);

        // 加入面板中
        panel.add(friendEmailLabel);
        panel.add(friendEmailText);
        panel.add(scrollPane);
        panel.add(changeButton);
        panel.add(returnButton);

        // 设置按钮监听事件
        changeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String friendEmail = friendEmailText.getText();
                String replyTextStr = replyText.getText();

                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");

                    // 连接数据库
                    conn = DriverManager.getConnection(DB_URL, username, password);

                    // 执行查询
                    stmt = conn.createStatement();
                    String sql;
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                    sql = "INSERT INTO REPLY_FRIENDS(EMAIL,FRIEND,REPLY_DATE,REPLY_DATA) VALUES('" + email + "','" + friendEmail + "','" + formatter.format(date) + "','" + replyTextStr + "')";
                    // 展开结果集数据库
                    if (stmt.executeUpdate(sql) > 0) {
                        alarm("成功", 60, "回复成功");
                    } else {
                        alarm("错误", 60, "回复失败");
                    }
                    // 完成后关闭
                    conn.close();
                    stmt.close();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                    alarm("错误", 60, "连接错误");
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
                jf.dispose();
            }
        });

        // 设置按钮监听事件
        returnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        // 将面板加入窗口中
        jf.add(panel);
        // 设置窗口可见
        jf.setVisible(true);
    }

    /**
     * @description: 显示简单结果
     * @param: [str]显示的字符串
     * @return: void
     */
    private static void result(String str) {
        JFrame jf = new JFrame("结果");
        jf.setSize(400, 200);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        JTextArea jt = new JTextArea(str);
        JButton jb = new JButton("确定");

        jt.setBounds(70, 30, 260, 70);
        jb.setBounds(160, 100, 80, 30);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(70, 30, 260, 70);
        scrollPane.setViewportView(jt);

        panel.add(scrollPane);
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

    /**
     * @description: 以表格形式显示结果
     * @param: [rowNames, rowData]
     * @return: void
     */
    private static void result(Object[] rowNames, Object[][] rowData) {
        JFrame jf = new JFrame("结果");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        //JTextArea jt = new JTextArea(str);
        JButton jb = new JButton("确定");

        JTable table = new JTable(rowData, rowNames);
        // 设置表格内容颜色
        table.setForeground(Color.BLACK);                   // 字体颜色
        table.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
        table.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        table.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        table.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        table.getTableHeader().setForeground(Color.BLACK);                // 设置表头名称字体颜色
        table.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        table.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        table.setRowHeight(30);

        // 创建单元格渲染器
        MyTableCellRenderer renderer = new MyTableCellRenderer();
        // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < rowNames.length; i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = table.getColumn(rowNames[i]);
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 50, 400, 270);

        // jt.setBounds(70, 30, 260, 70);
        jb.setBounds(260, 320, 80, 30);

        panel.add(scrollPane);
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

    /**
     * @description: 以表格形式显示结果
     * @param: [rowNames, rowData, rowWidth]
     * @return: void
     */
    private static void result(Object[] rowNames, Object[][] rowData, int rowWidth[]) {
        JFrame jf = new JFrame("结果");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        //JTextArea jt = new JTextArea(str);
        JButton jb = new JButton("确定");

        JTable table = new JTable(rowData, rowNames);
        // 设置表格内容颜色
        table.setForeground(Color.BLACK);                   // 字体颜色
        table.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
        table.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        table.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        table.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        table.getTableHeader().setForeground(Color.BLACK);                // 设置表头名称字体颜色
        table.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        table.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        table.setRowHeight(30);

        // 设置列宽
        for (int i = 0; i < rowWidth.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(rowWidth[i]);
        }

        // 创建单元格渲染器
        MyTableCellRenderer renderer = new MyTableCellRenderer();
        // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < rowNames.length; i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = table.getColumn(rowNames[i]);
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 50, 400, 270);

        // jt.setBounds(70, 30, 260, 70);
        jb.setBounds(260, 320, 80, 30);

        panel.add(scrollPane);
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

    /**
     * @description: 以域和表格的形式分两段显示结果
     * @param: [logTheme, subLogTheme, logData, rowNames2, rowData2, rowWidth]
     * @return: void
     */
    private static void resultLog(String logTheme, String subLogTheme, String logData, Object[] rowNames2, Object[][] rowData2, int rowWidth[]) {
        JFrame jf = new JFrame("结果");
        jf.setSize(600, 800);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel jl = new JLabel("回复：");
        JButton jb = new JButton("确定");

        JLabel theme = new JLabel(logTheme);
        theme.setFont(new Font("宋体", Font.PLAIN, 24));
        JLabel subTheme = new JLabel(subLogTheme);
        JTextArea data = new JTextArea(logData);

        theme.setBounds(100, 50, 400, 40);
        subTheme.setBounds(100, 90, 400, 40);
        JScrollPane scrollPane1 = new JScrollPane(data);
        scrollPane1.setBounds(50, 130, 500, 270);

        JTable table2 = new JTable(rowData2, rowNames2);
        // 设置表格内容颜色
        table2.setForeground(Color.BLACK);                   // 字体颜色
        table2.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
        table2.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        table2.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        table2.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        table2.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        table2.getTableHeader().setForeground(Color.BLACK);                // 设置表头名称字体颜色
        table2.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        table2.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        table2.setRowHeight(60);
        table2.getTableHeader().setSize(500, 30);
        // 设置列宽
        for (int i = 0; i < rowWidth.length; i++) {
            table2.getColumnModel().getColumn(i).setPreferredWidth(rowWidth[i]);
        }

        // 创建单元格渲染器
        MyTableCellRenderer renderer = new MyTableCellRenderer();
        // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < rowNames2.length; i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = table2.getColumn(rowNames2[i]);
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }

        JScrollPane scrollPane2 = new JScrollPane(table2);
        scrollPane2.setBounds(50, 450, 500, 270);

        jl.setBounds(100, 400, 260, 50);
        jb.setBounds(260, 720, 80, 30);

        panel.add(jb);
        panel.add(jl);
        panel.add(theme);
        panel.add(subTheme);
        panel.add(scrollPane1);
        panel.add(scrollPane2);

        // 监听确认按钮
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });

        jf.add(panel);
        jf.setVisible(true);
    }

    /**
     * @description: 提出相应的提示
     * @param: [type, len, str]
     * @return: void
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

    /**
     * @Author: ChenD7x
     * @description: 单元格渲染器，继承已实现渲染器接口的默认渲染器 DefaultTableCellRenderer
     */
    public static class MyTableCellRenderer extends DefaultTableCellRenderer {

        /**
         * @description: 返回默认的表单元格渲染器，此方法在父类中已实现，直接调用父类方法返回，在返回前做相关参数的设置即可
         * @param: [table, value, isSelected, hasFocus, row, column]
         * @return: java.awt.Component
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // 偶数行背景设置为白色，奇数行背景设置为灰色
            if (row % 2 == 0) {
                setBackground(Color.WHITE);
            } else {
                setBackground(Color.LIGHT_GRAY);
            }

            // 第一列的内容水平居中对齐，最后一列的内容水平右对齐，其他列的内容水平左对齐
            if (column == 0) {
                setHorizontalAlignment(SwingConstants.CENTER);
            } else {
                setHorizontalAlignment(SwingConstants.LEFT);
            }

            // 设置提示文本，当鼠标移动到当前(row, column)所在单元格时显示的提示文本
            setToolTipText("提示的内容: " + row + ", " + column);

            // 调用父类的该方法完成渲染器的其他设置
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

}
