package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class WelcomeJFrame extends JFrame  implements ActionListener {//欢迎界面
    JPanel root = new JPanel();//根面板
    JPanel panel = new JPanel(new GridLayout(2,1,0,50));
     JPanel  jp = new JPanel();
     JPanel jp1 = new JPanel();
     JPanel jp2 = new JPanel(new GridLayout(3,1,100,10));
     JButton jb1 = new JButton("开始考试");
     JButton jb2 = new JButton("查看试题");
     JButton jb3 = new JButton("退出考试");
     FileDialog loadDialog = new FileDialog(this,"选择题库试卷",FileDialog.LOAD);//文件选择题库试对话框
    String path = null;//题库试题路径
    ExamJFrame examJFram;//考试界面
    CheckWork checkWork;//查看试题界面
    public WelcomeJFrame(){
        init();
    }
    private void init() {//欢迎界面布局配置，Label，按钮，字体
        this.setTitle("欢迎来到考试系统");
        this.setSize(1200,800);
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(null);
        this.getContentPane().add(root);
        root.setBounds(100,100,900,700);
        root.add(panel);
        panel.setSize(1100,700);
        panel.add(jp);
        panel.add(jp1);
        jp1.add(jp2);
        jp.setBounds(0,0,1200,800);
        JLabel jLabel = new JLabel("考试系统",JLabel.CENTER);
        JLabel jLabel1 = new JLabel("1.0",JLabel.LEFT);
        jLabel1.setFont(new Font("宋体",Font.BOLD,20));
        jLabel1.setForeground(Color.red);
        jLabel.setFont(new Font("宋体",Font.BOLD,150));
        jp.add(jLabel);
        jp.add(jLabel1);
        jb1.setFont(new Font("宋体",Font.BOLD,20));
        jb2.setFont(new Font("宋体",Font.BOLD,20));
        jb3.setFont(new Font("宋体",Font.BOLD,20));
        jp2.add(jb1);
        jp2.add(jb2);
        jp2.add(jb3);
        jb1.setActionCommand("开始考试");
        jb2.setActionCommand("查看试题");
        jb3.setActionCommand("退出考试");
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {//监听事件
        if (e.getActionCommand().equals("开始考试")) {
            loadDialog.setVisible(true);
            String dir = loadDialog.getDirectory();
            String file = loadDialog.getFile();
            if (dir == null || file == null) {
                return;
            }
            path = dir + file;
            try {
                examJFram = new ExamJFrame(this  ,path);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            examJFram.setVisible(true);
            this.dispose();
        }else if(e.getActionCommand().equals("退出考试")){
            System.exit(0);
        }else if (e.getActionCommand().equals("查看试题")){
            loadDialog.setVisible(true);
            String dir = loadDialog.getDirectory();
            String file = loadDialog.getFile();
            if (dir == null || file == null) {
                return;
            }
            path = dir + file;
            try {
                checkWork= new CheckWork(this,path);
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
        }

    }

    public JPanel getRoot() {
        return root;
    }

    public void setRoot(JPanel root) {
        this.root = root;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JPanel getJp() {
        return jp;
    }

    public void setJp(JPanel jp) {
        this.jp = jp;
    }

    public JPanel getJp1() {
        return jp1;
    }

    public void setJp1(JPanel jp1) {
        this.jp1 = jp1;
    }

    public JButton getJb1() {
        return jb1;
    }

    public void setJb1(JButton jb1) {
        this.jb1 = jb1;
    }

    public JButton getJb2() {
        return jb2;
    }

    public void setJb2(JButton jb2) {
        this.jb2 = jb2;
    }

    public JButton getJb3() {
        return jb3;
    }

    public void setJb3(JButton jb3) {
        this.jb3 = jb3;
    }

    public FileDialog getLoadDialog() {
        return loadDialog;
    }

    public void setLoadDialog(FileDialog loadDialog) {
        this.loadDialog = loadDialog;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ExamJFrame getExamJFram() {
        return examJFram;
    }

    public void setExamJFram(ExamJFrame examJFram) {
        this.examJFram = examJFram;
    }


}
