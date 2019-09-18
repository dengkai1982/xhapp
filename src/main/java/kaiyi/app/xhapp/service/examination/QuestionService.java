package kaiyi.app.xhapp.service.examination;
import kaiyi.app.xhapp.entity.examination.Question;
import kaiyi.app.xhapp.entity.examination.QuestionCategory;
import kaiyi.puer.commons.collection.StreamArray;
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

    void deleteOneQuestion();
    /**
     * 根据ID进行删除
     * @param entityId
     */
    void deleteById(String entityId);

    /**
     * 启用或停用试题
     * @param entityId
     */
    void changeEnable(String entityId);

    boolean isQuestion(List<ExcelData> line);


    Question parseQuestion(List<ExcelData> line,StreamCollection<QuestionCategory> categories)throws ServiceException;

    void parseChoiceAnswer(Question question,List<ExcelData> line)throws ServiceException;

    /**
     * 批量启用或停用
     * @param entityIdArray
     */
    void batchEnable(StreamArray<String> entityIdArray, boolean enable);
}
