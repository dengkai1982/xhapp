package kaiyi.app.xhapp.service.examination;
import kaiyi.app.xhapp.entity.examination.SimulationCategory;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface SimulationCategoryService extends DatabaseQuery<SimulationCategory>,
        DatabaseFastOper<SimulationCategory>, DatabaseOperator<SimulationCategory> {
    /**
     *
     * @return
     */
    StreamCollection<SimulationCategory> getChild(String districtId);
    /**
     *
     * @param districtId
     * @return
     */
    StreamCollection<SimulationCategory> getSameLevel(String districtId);

    void newQuestionCategory(String name,String parentName);

    /**
     * 获取所有类别,按照树形结构排列
     * @return
     */
    StreamCollection<SimulationCategory> getEnableRootCategory();
    /**
     * 获取类别树,用json表示
     * @param categories
     * @param templateName
     * @return
     */
    String toJsonTree(StreamCollection<SimulationCategory> categories,String templateName);

    StreamCollection<SimulationCategory> getCategoryAndChildren(String categoryId);
}
