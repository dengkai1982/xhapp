package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.examination.QuestionFavorites;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface QuestionFavoritesService extends DatabaseQuery<QuestionFavorites> {
    /**
     * 收藏或取消收藏
     * @param accountId
     * @param questionId
     */
    void favorites(String accountId,String questionId);
}
