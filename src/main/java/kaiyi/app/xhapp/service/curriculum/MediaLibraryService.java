package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.MediaLibrary;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;
public interface MediaLibraryService extends DatabaseQuery<MediaLibrary>,DatabaseOperator<MediaLibrary> {
    //添加媒体库文件
    void newMediaLibrary(String title,String videoId)throws ServiceException;

}
