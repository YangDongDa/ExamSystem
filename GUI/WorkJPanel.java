package GUI;

import GetWork.Work;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkJPanel extends JPanel implements ActionListener {//单个题目面板
    JLabel[] jLabels = new JLabel[100];
    JRadioButton[] jRadioButtons = new JRadioButton[4];
    JLabel[] jOption = new JLabel[4];
    String[] choose = {"A","B","C","D"};
    String[] choose1 = {"对","错"};

    ButtonGroup group=new ButtonGroup();

    Work work;//题目

    int score = 0;//分数

    int[] answer = new int[4];//用户答案
    int[] trueAnswer = new int[4];//正确答案

    int result = 0;//答对题目数

    int mode = 1;//0为查看答案，1为考试，2为查看试卷



    public WorkJPanel(Work work,int mode){
        this.mode = mode;
        this.work = work;
        analysisAnswer();
        if (mode == 1) {
            init1();
        }else if (mode == 0){
            init0();
        }else if (mode == 2){
            init2();
        }

    }
    public WorkJPanel(Work work,int mode,int[] answer){
        this.mode = mode;
        this.work = work;
        this.answer = answer;
        analysisAnswer();
        if (mode == 1) {
            init1();
        }else if (mode == 0){
            init0();
        }else if (mode == 2){
            init2();
        }
    }
    private void init0() {//查看答案，将试题和选择装载入面板
        LoadTitle();
        for (int i = 0; i < work.getOptions().length; i++){
            jOption[i] = new JLabel(choose[i]+ " 、 " + work.getOptions()[i]);
            jOption[i].setFont(new Font("宋体",Font.BOLD,16));
            this.add(jOption[i]);
        }
        StringBuilder charAnswer = new StringBuilder();
        if (work.getWorkType() == 2){
            if (answer[0] == 1){
                charAnswer.append(choose1[0]);
            }else if (answer[1] == 1){
                charAnswer.append(choose1[1]);
            }
        }

        if (work.getWorkType() == 0){
            for (int i = 0; i < answer.length; i++){
                if (answer[i] == 1 ){
                    charAnswer.append(choose[i]).append(" ");

                }
            }
        }
        this.add(new JLabel("你选择的答案：" + charAnswer));

        this.add(new JLabel("参考答案：" + work.getAnswer()));

    }

    private void init1() {//考试初始化，将试题和选择装载入面板
        LoadTitle();

        for (int i = 0; i < work.getOptions().length; i++) {
            jRadioButtons[i] = new JRadioButton(choose[i] + " 、 " + work.getOptions()[i]);
            jRadioButtons[i].setActionCommand(choose[i]);
            jRadioButtons[i].setFont(new Font("宋体", Font.BOLD, 16));
            this.add(jRadioButtons[i]);
            jRadioButtons[i].addActionListener(this);

        }

        groupOption();
    }

    private void init2() {//查看试卷，将试题和选择装载入面板
        LoadTitle();
        for (int i = 0; i < work.getOptions().length; i++){
            jOption[i] = new JLabel(choose[i]+ " 、 " + work.getOptions()[i]);
            jOption[i].setFont(new Font("宋体",Font.BOLD,16));
            this.add(jOption[i]);
        }


        this.add(new JLabel("参考答案：" + work.getAnswer()));
    }


    private void groupOption() {
        if (work.getWorkType() == 2) {

            group.add(jRadioButtons[0]);
            group.add(jRadioButtons[1]);
        }
    }

    private void LoadTitle() {//装载题目
        jLabels = new JLabel[100];
        String[] s = work.getTitle().split("\n");
        s[0] = work.getId() + "、" + s[0];
        this.setLayout(new GridLayout(s.length+2+ work.getOptions().length,1,0,0));
        for(int i = 0; i < s.length; i++){
            jLabels[i] = new JLabel(s[i]);
            jLabels[i].setFont(new Font("宋体",Font.BOLD,16));
            this.add(jLabels[i]);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        for (int i = 0; i < 4; i++){
            answer[i] = 0;
        }

        if(work.getWorkType() == 2) {
            if (s.equals("A")) {
                answer[0] = 1;
            } else if (s.equals("B")) {
                answer[1] = 1;
            }
        }


        if (work.getWorkType() == 0) {
            for (int i = 0; i < 4; i++){
                if (jRadioButtons[i].isSelected()){
                    answer[i] = 1;
                }
            }

        }

        judge();


    }

    public void analysisAnswer(){//解析正确答案
        String s = work.getAnswer();
        if (s.matches("A")||s.matches("对")||s.matches("A.*")){
            trueAnswer[0] = 1;
        }
        if (s.matches("B")||s.matches("错")||s.matches(".*B.*")){
            trueAnswer[1] = 1;
        }
        if (s.matches("C")||s.matches(".*C.*")){
            trueAnswer[2] = 1;
        }
        if (s.matches("D") || s.matches(".*D.*")){
            trueAnswer[3] = 1;
        }

    }

    public void judge(){//判断答案是否正确
        for (int i = 0; i < 4; i++){
            if (answer[i] != trueAnswer[i]){
                this.result = 0;
                score = 0;
                return;
            }
        }
        this.result = 1;
        score = result * 10;
    }

    public void update(Work work){//更新试题

        for (int i = 0; i < 4; i++){
            trueAnswer[i] = 0;
        }
        for (int i = 0; i < 4; i++){
            answer[i] = 0;
        }
        this.work = work;
        analysisAnswer();
        this.removeAll();
        this.setLayout(null);
        init1();
        this.updateUI();
        score = 0;


    }

    public JLabel[] getjLabels() {
        return jLabels;
    }

    public void setjLabels(JLabel[] jLabels) {
        this.jLabels = jLabels;
    }

    public JRadioButton[] getjRadioButtons() {
        return jRadioButtons;
    }

    public void setjRadioButtons(JRadioButton[] jRadioButtons) {
        this.jRadioButtons = jRadioButtons;
    }

    public String[] getChoose() {
        return choose;
    }

    public void setChoose(String[] choose) {
        this.choose = choose;
    }

    public String[] getChoose1() {
        return choose1;
    }

    public void setChoose1(String[] choose1) {
        this.choose1 = choose1;
    }

    public ButtonGroup getGroup() {
        return group;
    }

    public void setGroup(ButtonGroup group) {
        this.group = group;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public int[] getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(int[] trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
