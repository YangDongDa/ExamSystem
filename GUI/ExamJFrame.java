package GUI;

import GetWork.LoadWork;
import GetWork.Work;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class ExamJFrame extends JFrame implements ActionListener {
    JMenuBar jMenuBar = new JMenuBar();
    JMenu functionJMenu = new JMenu("功能");
    JMenuItem redo = new JMenuItem("重新考试");
    JMenuItem back = new JMenuItem("返回");
    JMenuItem close = new JMenuItem("关闭");
    JButton jb1 = new JButton("提交试卷");
    JButton jb2 = new JButton("放弃考试");
    JPanel jButtonPanel = new JPanel();//底部按钮面板
    JPanel panel = new JPanel(new GridLayout(17, 1, 0, 0));//存放试题面板的面板，最多15道题
    JPanel jp = new JPanel();
    JPanel jp1 = new JPanel();
    WorkJPanel[] jpWork = new WorkJPanel[15];//试题面板，一张试卷最多15道题
    JScrollPane scrollPane = new JScrollPane();
    HashSet<Work> examWork0 = new HashSet<>();//考试单选题库
    HashSet<Work> examwork1 = new HashSet<>();//考试判断题库
    ResultDialog resultDialog = new ResultDialog(this, "考试结果", true);//考试结果对话框
    CloseDialog closeDialog0 = new CloseDialog(this, "提示", true,0);//关闭对话框
    CloseDialog closeDialog1 = new CloseDialog(this, "提示", true);//退出考试对话框
    CloseDialog closeDialog2 = new CloseDialog(this, "提示", true,2);//重新考试对话框
    Timer timer;//计时器，用于倒计时
    LoadWork loadWork = new LoadWork();//用于装载试题，或者保存试题

    WelcomeJFrame welcomeJFram;//欢迎窗口

    String path = "";//试卷题库路径
    int score = 0;//考试成绩
    int DwonTime = 600;//考试倒计时，15分钟
    int CurrentTime = 0;//当前时间
    JLabel DownTimeCount = new JLabel();//倒计时标签
    ArrayList<int[]> myAnswer = new ArrayList<>();//存放我的答案

    int a = 0;






    public ExamJFrame(String path) throws IOException, InterruptedException {
        this.path = path;

        //初始化
        init();

        //初始化菜单栏
        initJMenuBar();

        //读取试题
        readWork();

        //显示试题
        showWork();

        //开启倒计时
        CountDown();
        this.setVisible(true);

    }

    public ExamJFrame(WelcomeJFrame welcomeJFram, String path) throws IOException, InterruptedException {
        this.welcomeJFram = welcomeJFram;
        this.path = path;

        //初始化
        init();

        //初始化菜单栏
        initJMenuBar();

        //读取试题
        readWork();

        //显示试题
        showWork();

        //开启倒计时
        CountDown();
        this.setVisible(true);

    }

    private void init() {//初始化
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("考试系统");
        jp.setBounds(0, 0, 1200, 800);
        jp.setLayout(null);
        this.getContentPane().add(jp);
        jp1.setLayout(new BorderLayout());
        jp1.setBounds(100, 50, 1000, 600);
        jp.add(jp1);
        jp1.add(DownTimeCount, BorderLayout.NORTH);
        DownTimeCount.setFont(new Font("宋体", Font.BOLD, 16));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeDialog0.setVisible(true);
            }
        });
    }

    private void initJMenuBar() {//初始化菜单栏
        redo.setActionCommand("redo");
        redo.addActionListener(this);
        back.setActionCommand("back");
        back.addActionListener(this);
        close.setActionCommand("close");
        close.addActionListener(this);
        functionJMenu.add(redo);
        functionJMenu.add(back);
        functionJMenu.add(close);
        jMenuBar.add(functionJMenu);
        this.setJMenuBar(jMenuBar);
    }



    private void readWork() throws IOException {//读取试题
        loadWork.load(path);
        loadWork.RandomWork();
        examWork0 = loadWork.getExamWork0();
        examwork1 = loadWork.getExamWork1();


    }

    private void showWork() throws IOException, InterruptedException {//在面板上显示试题
        a = 0;
        for (Work work : examWork0) {
            work.setId(a + 1);
            jpWork[a] = new WorkJPanel(work, 1);
            panel.add(jpWork[a]);
            a++;
        }

        for (Work work : examwork1) {
            work.setId(a + 1);
            jpWork[a] = new WorkJPanel(work, 1);
            panel.add(jpWork[a]);
            a++;
        }



        jb1.setActionCommand("提交试卷");
        jb2.setActionCommand("放弃考试");
        jButtonPanel.setLayout(new FlowLayout());
        jButtonPanel.add(jb1);
        jButtonPanel.add(jb2);
        panel.add(jButtonPanel);
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb1.setFont(new Font("宋体", Font.BOLD, 20));
        jb2.setFont(new Font("宋体", Font.BOLD, 20));
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);
        jp1.add(scrollPane, BorderLayout.CENTER);
//        this.getPanel().add(scrollPane);

    }



    void redo() throws InterruptedException, IOException {//重新考试，更新面版
        timer.cancel();
        panel.removeAll();
        panel.setLayout(null);
        panel.setLayout(new GridLayout(17, 1, 0, 0));
        readWork();
        a = 0;
        for (Work work : examWork0) {
            work.setId(a + 1);
            jpWork[a].update(work);
            panel.add(jpWork[a]);
            a++;
        }

        for (Work work : examwork1) {
                work.setId(a + 1);
                jpWork[a].update(work);
                panel.add(jpWork[a]);
                a++;
        }
        panel.add(jButtonPanel);
        jp1.updateUI();
        score = 0;

        CountDown();
        CurrentTime = DwonTime;
    }

    void CountDown() throws InterruptedException {//倒计时
        CurrentTime = DwonTime;

        System.out.println("count down from "+DwonTime+" s ");
        this.timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                System.out.println("Time remians "+ --CurrentTime +" s");

                int minute = CurrentTime/60;
                int second = CurrentTime%60;
                DownTimeCount.setText("剩余时间："+minute+"分"+second+"秒");
                if (CurrentTime == 0){
                    timer.cancel();
                    for (int i = 0; i < jpWork.length; i++) {
                        score = score + jpWork[i].getScore();
                        myAnswer.add(jpWork[i].getAnswer());
                    }
                    resultDialog.setScore(score);
                    resultDialog.setVisible(true);
                    System.out.println(score);
                }
            }
        },0,1000);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s == "提交试卷") {

            timer.cancel();
            for (int i = 0; i < jpWork.length; i++) {
                score = score + jpWork[i].getScore();
                myAnswer.add(jpWork[i].getAnswer());
            }
            resultDialog.setScore(score);
            resultDialog.setVisible(true);


            System.out.println(score);
        } else if (s == "redo") {
            closeDialog2.setVisible(true);


        } else if (s == "back") {
//            welcomeJFram.setVisible(true);
            closeDialog1.setVisible(true);
//            this.dispose();

        } else if (s == "放弃考试") {

//            welcomeJFram.setVisible(true);
//            this.dispose();
            closeDialog1.setVisible(true);
        } else if (s == "close") {
            closeDialog0.setVisible(true);
        }
    }
    public ArrayList<int[]> getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(ArrayList<int[]> myAnswer) {
        this.myAnswer = myAnswer;
    }
    public WelcomeJFrame getWelcomeJFram() {
        return welcomeJFram;
    }

    public void setWelcomeJFram(WelcomeJFrame welcomeJFram) {
        this.welcomeJFram = welcomeJFram;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public LoadWork getLoadWork() {
        return loadWork;
    }

    public void setLoadWork(LoadWork loadWork) {
        this.loadWork = loadWork;
    }

    public JMenuBar getjMenuBar() {
        return jMenuBar;
    }

    public void setjMenuBar(JMenuBar jMenuBar) {
        this.jMenuBar = jMenuBar;
    }

    public JMenu getFunctionJMenu() {
        return functionJMenu;
    }

    public void setFunctionJMenu(JMenu functionJMenu) {
        this.functionJMenu = functionJMenu;
    }

    public JMenuItem getRedo() {
        return redo;
    }

    public void setRedo(JMenuItem redo) {
        this.redo = redo;
    }

    public JMenuItem getBack() {
        return back;
    }

    public void setBack(JMenuItem back) {
        this.back = back;
    }

    public JMenuItem getClose() {
        return close;
    }

    public void setClose(JMenuItem close) {
        this.close = close;
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

    public JPanel getjButtonPanel() {
        return jButtonPanel;
    }

    public void setjButtonPanel(JPanel jButtonPanel) {
        this.jButtonPanel = jButtonPanel;
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

    public WorkJPanel[] getJpWork() {
        return jpWork;
    }

    public void setJpWork(WorkJPanel[] jpWork) {
        this.jpWork = jpWork;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public HashSet<Work> getExamWork0() {
        return examWork0;
    }

    public void setExamWork0(HashSet<Work> examWork0) {
        this.examWork0 = examWork0;
    }

    public HashSet<Work> getExamwork1() {
        return examwork1;
    }

    public void setExamwork1(HashSet<Work> examwork1) {
        this.examwork1 = examwork1;
    }

    public ResultDialog getResultDialog() {
        return resultDialog;
    }

    public void setResultDialog(ResultDialog resultDialog) {
        this.resultDialog = resultDialog;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
