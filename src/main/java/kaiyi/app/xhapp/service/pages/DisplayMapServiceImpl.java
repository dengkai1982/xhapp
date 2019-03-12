package kaiyi.app.xhapp.service.pages;

import kaiyi.app.xhapp.entity.pages.DisplayMap;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("displayMapService")
public class DisplayMapServiceImpl extends InjectDao<DisplayMap> implements DisplayMapService{
    private static final long serialVersionUID = -276799615356668808L;
}
