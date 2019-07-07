package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.examination.Question;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface QuestionService extends DatabaseQuery<Question>, DatabaseFastOper<Question>, DatabaseOperator<Question> {
    /**
     * 删除参考答案
     */
    void deleteChoiceAnswer(String choiceAnswerId);
    /**
     * 移除所有参考答案
     */
    void removeChoiceAnswer(String questionId);
}
