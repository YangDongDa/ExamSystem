package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CloseDialog extends JDialog implements ActionListener {
    ExamJFrame examJFram;//父窗口
    JLabel jLabel = new JLabel("",JLabel.CENTER);
    JPanel jPanel = new JPanel( );
    JButton jb1 = new JButton("确定");
    JButton jb2 = new JButton("取消");
    int mode = 0;//0为关闭系统，1为退出考试，2重新考试




    public CloseDialog(ExamJFrame jFrame, String title, boolean modal) {
        super(jFrame,title,modal);
        this.examJFram = jFrame;
        init1();


    }

    public CloseDialog(ExamJFrame jFrame, String title, boolean modal, int n) {
        super(jFrame, title, modal);
        this.examJFram = jFrame;
        if (n == 0) {
            init0();
        }else if (n == 1){
            init1();
        }else if (n == 2){
            init2();
        }


    }



    private void init0() {//关闭系统
        init();


        jLabel.setText("确定关闭系统吗？");
        jb1.setActionCommand("确定关闭");
        jb2.setActionCommand("取消");

    }


    private void init1() {//退出考试
        init();

        jLabel.setText("确定要退出考试吗？");
        jb1.setActionCommand("确定");
        jb2.setActionCommand("取消");

    }

    private void init2() {//重新开始考试
        init();

        jLabel.setText("确定重新开始考试吗？");
        jb1.setActionCommand("确定重新开始考试");
        jb2.setActionCommand("取消");


    }

    private void init() {//初始化配置
        this.setLayout(null);
        this.setSize(300,200);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(2,1,0,0));
        this.add(jLabel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jLabel.setFont(new Font("宋体",Font.BOLD,16));
        jPanel.add(jb1);
        jPanel.add(jb2);
        this.add(jPanel);
        jb1.addActionListener(this);
        jb2.addActionListener(this);
    }





    @Override
    public void actionPerformed(ActionEvent e) {//监听
        if (e.getActionCommand().equals("确定")){
               examJFram.welcomeJFram.setVisible(true);
               examJFram.timer.cancel();

            examJFram.dispose();
            this.dispose();

        }else if (e.getActionCommand().equals("取消")){
            this.dispose();
        }else if (e.getActionCommand().equals("确定关闭")){
            System.exit(0);
        }else if (e.getActionCommand().equals("确定重新开始考试")){
            try {
                examJFram.redo();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
        }
    }
}
