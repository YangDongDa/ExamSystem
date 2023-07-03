package GetWork;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class LoadWork {

    HashSet<Work> set = new HashSet<>();//单选题库
    HashSet<Work> set1 = new HashSet<>();//判断题库

    HashSet<Work> examWork0 = new HashSet<>();//考试单选题库
    HashSet<Work> examWork1 = new HashSet<>();//考试判断题库
    String[] choose = {"A","B","C","D"};
    String path = "";//题库路径或者试卷路径
    String line;//读取文件的每一行
    String newline;//替换后的每一行
    String answer = "";//答案
    BufferedReader br = null;//读取文件
    BufferedWriter bw = null;//写入文件



    public void  RandomWork(){//随机抽取试题
        examWork0.clear();
        examWork1.clear();
        Random r = new Random();
        ArrayList<Work> list = new ArrayList<>(set);
        ArrayList<Work> list2 = new ArrayList<>(set1);
        while (examWork0.size() < 10){
            examWork0.add(list.get(r.nextInt(list.size())));
        }

        while (examWork1.size() < 5){
            examWork1.add(list2.get(r.nextInt(list2.size())));
        }

    }

    public void save(String path) throws IOException {//保存试卷
        bw = new BufferedWriter(new FileWriter(path));
        int a = 0;
        for (Work work : examWork0){
            String[] split = work.getTitle().split("\r\n");
            String line = (a+1) + "、" + split[0] + "\r\n";
            bw.write(line);
            bw.newLine();
            for (int i = 1; i < split.length; i++) {
                line =  "    " + split[i];
                bw.write(line);
                bw.newLine();
            }

                for (int i = 0; i < work.getOptions().length; i++) {
                    line = "    " + choose[i] + "、" + work.getOptions()[i];
                    bw.write(line);
                    bw.newLine();
                }


            line = "参考答案：" + work.getAnswer();
            bw.write(line);
            bw.newLine();
            a++;

        }
        for (Work work : examWork1){
            String[] split = work.getTitle().split("\r\n");
            String line = (a+1) + "、" + split[0] + "\r\n";
            bw.write(line);
            bw.newLine();
            for (int i = 1; i < split.length; i++) {
                line =  "    " + split[i];
                bw.write(line);
                bw.newLine();
            }


                line = "    " + work.getOptions()[0];
                bw.write(line);
                bw.newLine();
                line = "    " + work.getOptions()[1];
                bw.write(line);
                bw.newLine();

            line = "参考答案：" + work.getAnswer();
            bw.write(line);
            bw.newLine();
            a++;

        }

        bw.close();
    }


    //加载题库
    public void load(String path) throws IOException {
        set.clear();
        set1.clear();
        this.path = path;
        br = new BufferedReader(new FileReader(this.path));
        while( (line = br.readLine()) != null){
            Work work = new Work();
            StringBuilder sb = new StringBuilder();
            String[] options = new String[4];
            String[] options2 = new String[2];
            if (line.matches("\\d{1,}、.{1,}")){


                //存入题目
                newline = line.replaceAll("\\d{1,}、\\s*","");
                sb.append(newline);
                sb.append("\r\n");
                line = br.readLine();
                while(line != null && !line.matches("    [A-Z]{1}、.{1,}") && !line.matches("    [√×]{1}")){
                    if (line.matches("\\([0-9]{1,}分\\)") == false) {
                        sb.append(line);
                        sb.append("\r\n");
                    }
                    line = br.readLine();
                }
                work.setTitle(sb.toString());


                //存入选项

                if (line.matches("    [A-Z]{1}、.{1,}")) {
                    for (int i = 0; i < 4; i++) {
                        newline = line.replaceAll("    [A-D]、", "");
                        options[i] = newline;
                        line = br.readLine();
                    }
                    work.setOptions(options);
                    work.setWorkType(0);
                }else if (line.matches("    [√×]{1}")){
                    for (int i = 0; i < 2; i++) {
                        newline = line.replaceAll("    ", "");
                        options2[i] = newline;
                        line = br.readLine();
                    }
                    work.setOptions(options2);
                    work.setWorkType(2);
                }


                //存入答案

                if (line.matches("参考答案：.*")){
                    newline = line.replaceAll("参考答案：","");
                    answer = newline;
                }
                work.setAnswer(answer);
                if (work.getWorkType() == 0) {
                    set.add(work);
                }else if (work.getWorkType() == 2){
                    set1.add(work);
                }

            }else if (line.matches("\\d{1,}、.{0}")){


                line = br.readLine();
                sb.append(line);
                sb.append("\r\n");

                line = br.readLine();
                while(line != null && !line.matches("    [A-Z]{1}、.{1,}") && !line.matches("    [√×]{1}")){
                    if (line.matches("\\([0-9]{1,}分\\)") == false) {
                        sb.append(line);
                        sb.append("\r\n");
                    }
                    line = br.readLine();
                }

                work.setTitle(sb.toString());

                //存入选项
                if (line.matches("    [A-Z]{1}、.{1,}")) {
                    for (int i = 0; i < 4; i++) {
                        newline = line.replaceAll("    [A-D]、", "");
                        options[i] = newline;
                        line = br.readLine();
                    }
                    work.setOptions(options);
                    work.setWorkType(0);
                }else if (line.matches("    [√×]{1}")){
                    for (int i = 0; i < 2; i++) {
                        newline = line.replaceAll("    ", "");
                        options2[i] = newline;
                        line = br.readLine();
                    }
                    work.setOptions(options2);
                    work.setWorkType(2);
                }


                //存入答案
                if (line.matches("参考答案：.*")){
                    newline = line.replaceAll("参考答案：","");
                    answer = newline;
                }

                work.setAnswer(answer);

                if (work.getWorkType() == 0) {
                    set.add(work);
                }else if (work.getWorkType() == 2){
                    set1.add(work);
                }

            }
        }
        br.close();
    }

    public HashSet<Work> getSet() {
        return set;
    }

    public void setSet(HashSet<Work> set) {
        this.set = set;
    }

    public HashSet<Work> getSet1() {
        return set1;
    }

    public void setSet1(HashSet<Work> set1) {
        this.set1 = set1;
    }

    public HashSet<Work> getExamWork0() {
        return examWork0;
    }

    public void setExamWork0(HashSet<Work> examWork0) {
        this.examWork0 = examWork0;
    }

    public HashSet<Work> getExamWork1() {
        return examWork1;
    }

    public void setExamWork1(HashSet<Work> examWork1) {
        this.examWork1 = examWork1;
    }
}
