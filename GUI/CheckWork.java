package GUI;

import GetWork.Work;

import javax.swing.*;
import java.awt.*;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;

public class CheckWork extends JFrame implements ActionListener {
    JMenuBar jMenuBar = new JMenuBar();
    JMenu functionJMenu = new JMenu("功能");
    JMenuItem back = new JMenuItem("返回");
    JMenuItem close = new JMenuItem("关闭");
    JButton jb1 = new JButton("保存试题");
    JButton jb2 = new JButton("开始考试");

    JButton jb3 = new JButton("返回");


    ExamJFrame examJFram;//父考试窗口
    String path = "";//试卷路径
    WelcomeJFrame welcomeJFram;//欢迎窗口
    JPanel jp = new JPanel();
    JPanel jp1 = new JPanel();
    JPanel panel = new JPanel(new GridLayout(16,1,0,0));//存放试题面板的面板
    JScrollPane scrollPane = new JScrollPane();//滚动面板
    JPanel jButtonPanel = new JPanel();//底部按钮面板

    WorkJPanel[] jpWork = new WorkJPanel[15];//试题面板，一张试卷最多15道题

    HashSet<Work> examWork0;//考试单选题库
    HashSet<Work> examwork1; //考试判断题库
    FileDialog saveDialog = new FileDialog(this,"保存试题",FileDialog.SAVE);//保存试题对话框
    CloseDialog closeDialog1;//关闭对话框



    //带自己答案的试卷查看窗口
    public CheckWork(ExamJFrame examJFram) throws IOException {
        this.examJFram = examJFram;
        examWork0 = examJFram.getExamWork0();
        examwork1 = examJFram.getExamwork1();
        path = examJFram.getPath();
        welcomeJFram = examJFram.getWelcomeJFram();
        closeDialog1 = new CloseDialog(examJFram, "提示", true,0);

        //初始化
        init();

        //初始化菜单栏
        initJMenuBar();


        //展示考试结果
        showWork();


        this.setVisible(true);



    }

    //不带自己答案的试卷查看窗口
    public CheckWork(WelcomeJFrame welcomeJFram, String path) throws IOException, InterruptedException {
        this.welcomeJFram = welcomeJFram;
        examJFram = new ExamJFrame(this.welcomeJFram,path);
        examJFram.timer.cancel();
        this.welcomeJFram.setExamJFram(examJFram);
        examJFram.setWelcomeJFram(this.welcomeJFram);
        examWork0 = examJFram.getExamWork0();
        examwork1 = examJFram.getExamwork1();
        examJFram.dispose();
        this.path = path;
        closeDialog1 = new CloseDialog(examJFram, "提示", true,0);

        //初始化
        init();

        //初始化菜单栏
        initJMenuBar();

        //展示试题
        showWork0();
        this.setVisible(true);

    }

    private void showWork0() {//展示试题
        int a = 0;
        for (Work work : examWork0){
            work.setId(a+1);
            jpWork[a] =  new WorkJPanel(work,2);
            System.out.println();
            panel.add(jpWork[a]);
            a++;
        }

        for (Work work : examwork1){
            work.setId(a+1);
            jpWork[a] =  new WorkJPanel(work,2);
            System.out.println();
            panel.add(jpWork[a]);
            a++;
        }

        //面板设置
        PanelSet();


    }

    private void PanelSet() {//面板设置
        jb1.setActionCommand("保存试题");
        jb2.setActionCommand("开始考试");
        jb3.setActionCommand("返回");
        jButtonPanel .add(jb1);
        jButtonPanel .add(jb2);
        jButtonPanel .add(jb3);
        panel.add(jButtonPanel);
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        jb1.setFont(new Font("宋体",Font.BOLD,18));
        jb2.setFont(new Font("宋体",Font.BOLD,18));
        jb3.setFont(new Font("宋体",Font.BOLD,18));
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        jp1.add(scrollPane);
    }


    private void showWork() throws IOException {//展示考试结果
        int a = 0;
        for (Work work : examWork0){
            work.setId(a+1);
            jpWork[a] = new WorkJPanel(work,0,examJFram.getMyAnswer().get(a));
            System.out.println();
            panel.add(jpWork[a]);
            a++;
        }

        for (Work work : examwork1){
            work.setId(a+1);
            jpWork[a] = new WorkJPanel(work,0,examJFram.getMyAnswer().get(a));
            System.out.println();
            panel.add(jpWork[a]);
            a++;
        }

        PanelSet();




    }

    private void init() {//初始化
        this.setSize(1200,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("查看");
        jp.setBounds(0,0,1200, 800);
        this.getContentPane().add(jp);
        jp.setLayout(null);
        jp1.setLayout(new GridLayout(1,1,0,0));
        jp1.setBounds(40,20,1100, 700);
        jp.add(jp1);



    }

    private void initJMenuBar() {//初始化菜单栏
        back.setActionCommand("back");
        back.addActionListener(this);
        close.setActionCommand("close");
        close.addActionListener(this);
        functionJMenu.add(back);
        functionJMenu.add(close);
        jMenuBar.add(functionJMenu);
        this.setJMenuBar(jMenuBar);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("开始考试")){
            try {
                this.examJFram = new ExamJFrame(welcomeJFram, path);
                examJFram.redo();
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
        }
        if (s.equals("保存试题")){
            saveDialog.setVisible(true);
            String path = saveDialog.getDirectory();
            String fileName = saveDialog.getFile();
            if (path == null || fileName == null){
                return;
            }
            String savePath = path + fileName;
            try {
                examJFram.getLoadWork().save(savePath);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (s.equals("返回")){
            this.dispose();
            examJFram.welcomeJFram.setVisible(true);
        }
        if (s.equals("back")){
            this.dispose();
            examJFram.welcomeJFram.setVisible(true);
        }
        if (s.equals("close")){
            closeDialog1.setVisible(true);
        }


    }
}
