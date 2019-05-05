package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.log.CourseBrowse;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface CourseBrowseService extends DatabaseQuery<CourseBrowse> {

    void addCourseBrowse(String accountId,String courseId)throws ServiceException;
}
