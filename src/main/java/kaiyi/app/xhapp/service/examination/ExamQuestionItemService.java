package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.examination.ExamQuestionItem;
import kaiyi.app.xhapp.service.CustomerPaginationJson;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface ExamQuestionItemService extends DatabaseQuery<ExamQuestionItem>, CustomerPaginationJson {
    /**
     * 完成答题
     * @param entityId
     * @param answer
     */
    ExamQuestionItem finishAnswer(String entityId,String answer);

    /**
     * 获取指定考试题中的正确得分
     * @param examQuestionId
     */
    int successScore(String examQuestionId);

    /**
     * 是否已全部完成答题
     * @param totalNumber 总数量
     * @param examQuestionId
     */
    boolean isFinished(int totalNumber,String examQuestionId);
}
