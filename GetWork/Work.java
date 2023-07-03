package GetWork;

public class Work {
    private Integer id;//考试题目编号
    private String title;//题干
    private String[] options;//选项
    private String answer;//答案

    private int workType;
    //0:单选题 1:判断题

    public Work() {
    }
    public Work(String title, String[] options, String answer) {
        this.title = title;
        this.options = options;
        this.answer = answer;
    }

    @Override
    public boolean equals(Object work) {
        if (this == work) return true;
        if (work instanceof Work) {
            Work work1 = (Work) work;
            if (this.title.equals(work1.title)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.title.hashCode();
    }//题干相同则认为是同一题


    @Override
    public String toString() {
        return super.toString();
    }

    public void print(){//打印题目
        System.out.println("题目：" + this.title);

        System.out.println("选项：");
        if (this.workType == 0) {
            for (int i = 0; i < 4; i++) {
                System.out.println(this.options[i]);
            }
            System.out.println("题型：选择题");
        }else if (this.workType == 2){
            for (int i = 0; i < 2; i++) {
                System.out.println(this.options[i]);
            }
            System.out.println("题型：判断题");
        }
//        for (int i = 0; i < 4; i++) {
//            System.out.println(this.options[i]);
//        }
        System.out.println("答案：" + this.answer);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }


}
