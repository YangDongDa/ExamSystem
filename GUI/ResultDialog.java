package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;



public class ResultDialog extends JDialog implements ActionListener {//结果窗口

    JLabel jLabel = new JLabel("",JLabel.CENTER);
    JButton jb1 = new JButton("重新考试");
    JButton jb2 = new JButton("查看结果");
    JButton jb3 = new JButton("退出考试");

    JPanel jPanel1 = new JPanel( );
    ExamJFrame examJFram;//父窗口
    int score = 0;//分数
    CloseDialog closeDialog;//关闭窗口
    ResultDialog resultDialog;



    public ResultDialog(ExamJFrame jFrame, String title, boolean modal){
        super(jFrame,title,modal);
        this.examJFram = jFrame;
        closeDialog = new CloseDialog(examJFram,"提示",true);
        resultDialog = this;
        init();



    }

    private void init() {
        this.setLayout(null);
        this.setSize(400,300);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(2,1,0,0));
        this.add(jLabel);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog.setVisible(true);;//关闭窗口
            }
        });
        jPanel1.add(jb1);
        jPanel1.add(jb2);
        jPanel1.add(jb3);
        this.add(jPanel1);
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        jLabel.setFont(new Font("宋体",Font.BOLD,16));
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb1){
            this.setVisible(false);
            try {
                examJFram.redo();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else if (e.getSource() == jb2){
            this.dispose();
            try {
                new CheckWork(examJFram);
                examJFram.dispose();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else if (e.getSource() == jb3){
            closeDialog.setVisible(true);
        }
    }


    public JLabel getjLabel() {
        return jLabel;
    }

    public void setjLabel(JLabel jLabel) {
        this.jLabel = jLabel;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        jLabel.setText("考试结束，你答对了"+(score/10)+"题，你的分数为：" + score);
    }

}
