package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.CourseFavorites;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface CourseFavoritesService extends DatabaseQuery<CourseFavorites> {

    void addFavorites(String accountId,String courseId)throws ServiceException;

}
