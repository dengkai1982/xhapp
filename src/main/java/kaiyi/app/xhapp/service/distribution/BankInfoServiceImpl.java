package kaiyi.app.xhapp.service.distribution;

import kaiyi.app.xhapp.entity.distribution.BankInfo;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("bankInfoService")
public class BankInfoServiceImpl extends InjectDao<BankInfo> implements BankInfoService {
}
