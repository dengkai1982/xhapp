package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.log.LoginLog;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("loginLogService")
public class LoginLogServiceImpl extends InjectDao<LoginLog> implements LoginLogService {
}
