package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.MediaLibrary;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("mediaLibraryService")
public class MediaLibraryServiceImpl extends InjectDao<MediaLibrary> implements MediaLibraryService {
}
