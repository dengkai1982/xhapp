package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.VisitorRole;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ORMException;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.CompareQueryExpress.Compare;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
@Service("visitorUserService")
public class VisitorUserServiceImpl extends InjectDao<VisitorUser> implements VisitorUserService {
    private static final long serialVersionUID = 3356637804771575233L;
    @Resource
    private VisitorRoleService visitorRoleService;

    private String defaultPassword="123456";

    @PostConstruct
    public void postConstruct(){
        visitorRoleService.registDatasourceStatusNotify(this);
    }

    @Override
    public void beforeDelete(Object entity) throws ORMException {
        if(entity instanceof VisitorRole){
            if(exist(new CompareQueryExpress("visitorRole",Compare.EQUAL,entity))){
                throw new ORMException("角色已被用户引用,不能删除");
            }
        }

    }

    @Override
    protected void objectBeforePersistHandler(VisitorUser visitorUser,Map<String, JavaDataTyper> params)throws ServiceException {
        QueryExpress query=new CompareQueryExpress("loginName",Compare.EQUAL,visitorUser.getLoginName());
        if(exist(query)){
            throw ServiceExceptionDefine.loginNameExist;
        }
        //对密码进行加密操作,新增用户初始密码123456
        String password=defaultPassword;
        password=applicationService.cipherToString(password);
        visitorUser.setPassword(password);
    }
    @Override
    public VisitorUser doLogin(String loginName, String password) throws ServiceException {
        VisitorUser user=signleQuery("loginName",loginName);
        /*if(Objects.isNull(user)||!user.isEnable()){
            throw ServiceExceptionDefine.userNotExist;
        }*/
        if(!applicationService.checkChiper(password,user.getPassword())){
            throw ServiceExceptionDefine.passwordError;
        }
        user.setLastLoginTime(new Date());
        user.setAccessNumber(user.getAccessNumber()+1);
        updateObject(user);
        return user;
    }
    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) throws ServiceException {
        VisitorUser user=findForPrimary(userId);
        if(Objects.isNull(user)){
            throw ServiceExceptionDefine.userNotExist;
        }
        if(!applicationService.checkChiper(oldPassword,user.getPassword())){
            throw ServiceExceptionDefine.oldPasswordError;
        }
        user.setPassword(applicationService.cipherToString(newPassword));
    }
    @Override
    public void deleteById(String id)throws ServiceException {
        try{
            deleteForPrimary(id);
        }catch(ORMException e){
            throw new ServiceException(ServiceException.CODE_FAIL,e.getMessage());
        }
    }

    @Override
    public void resetPassword(String id) {
        VisitorUser user=findForPrimary(id);
        if(user!=null){
            user.setPassword(applicationService.cipherToString(defaultPassword));
            updateObject(user);
        }
    }
}
