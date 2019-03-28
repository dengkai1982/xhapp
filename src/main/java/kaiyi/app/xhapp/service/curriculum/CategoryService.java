package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface CategoryService extends DatabaseQuery<Category>,DatabaseFastOper<Category> {
    /**
     * 获取所有类别,按照树形结构排列
     * @return
     */
    StreamCollection<Category> getRootCategory();
    /**
     * 获取所有类别,按照树形结构排列
     * @return
     */
    StreamCollection<Category> getEnableRootCategory();
    /**
     * 获取类别树,用json表示
     * @param categories
     * @param templateName
     * @return
     */
    String toJsonTree(StreamCollection<Category> categories,String templateName);
}
