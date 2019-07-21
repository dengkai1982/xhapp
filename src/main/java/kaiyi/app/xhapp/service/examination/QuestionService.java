package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.examination.ChoiceAnswer;
import kaiyi.app.xhapp.entity.examination.Question;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.poi.ExcelData;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

import java.util.List;

public interface QuestionService extends DatabaseQuery<Question>, DatabaseFastOper<Question>, DatabaseOperator<Question> {
    /**
     * 删除参考答案
     */
    void deleteChoiceAnswer(String choiceAnswerId);
    /**
     * 移除所有参考答案
     */
    void removeChoiceAnswer(String questionId);

    /**
     * 启用或停用试题
     * @param entityId
     */
    void changeEnable(String entityId);

    boolean isQuestion(List<ExcelData> line);


    Question parseQuestion(List<ExcelData> line,StreamCollection<Category> categories)throws ServiceException;

    void parseChoiceAnswer(Question question,List<ExcelData> line)throws ServiceException;
}
