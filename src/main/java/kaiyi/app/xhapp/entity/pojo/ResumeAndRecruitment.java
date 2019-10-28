package kaiyi.app.xhapp.entity.pojo;

import java.util.Map;

public class ResumeAndRecruitment {

    Map<String,Integer> resume;

    Map<String,Integer> recruitment;

    public Map<String, Integer> getResume() {
        return resume;
    }

    public void setResume(Map<String, Integer> resume) {
        this.resume = resume;
    }

    public Map<String, Integer> getRecruitment() {
        return recruitment;
    }

    public void setRecruitment(Map<String, Integer> recruitment) {
        this.recruitment = recruitment;
    }
}
