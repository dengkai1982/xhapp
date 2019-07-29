package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.OrderItem;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("orderItemService")
public class OrderItemServiceImpl extends InjectDao<OrderItem> implements OrderItemService {

}
