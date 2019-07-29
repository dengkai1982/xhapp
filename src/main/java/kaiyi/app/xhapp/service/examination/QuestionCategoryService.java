package kaiyi.app.xhapp.service.examination;
import kaiyi.app.xhapp.entity.examination.QuestionCategory;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface QuestionCategoryService extends DatabaseQuery<QuestionCategory>, DatabaseFastOper<QuestionCategory>, DatabaseOperator<QuestionCategory> {
    /**
     *
     * @return
     */
    StreamCollection<QuestionCategory> getChild(String districtId);
    /**
     *
     * @param districtId
     * @return
     */
    StreamCollection<QuestionCategory> getSameLevel(String districtId);

    void newQuestionCategory(String name,String parentName);

    /**
     * 获取所有类别,按照树形结构排列
     * @return
     */
    StreamCollection<QuestionCategory> getEnableRootCategory();
    /**
     * 获取类别树,用json表示
     * @param categories
     * @param templateName
     * @return
     */
    String toJsonTree(StreamCollection<QuestionCategory> categories,String templateName);
}
