package kaiyi.app.xhapp;

import kaiyi.puer.db.orm.ServiceException;

/**
 * 服务异常定义
 */
public class ServiceExceptionDefine {

    public static final ServiceException roleNotExist=new ServiceException(
            ServiceException.CODE_FAIL,"角色不存在");
    public static final ServiceException userNotExist=new ServiceException(
            ServiceException.CODE_FAIL,"账户不存在");

    public static final ServiceException phoneNumberExist=new ServiceException(
            ServiceException.CODE_FAIL,"手机号码已注册");
    public static final ServiceException phoneFormatError=new ServiceException(
            ServiceException.CODE_FAIL,"手机号码格式错误");

    public static final ServiceException passwordNotEmpty=new ServiceException(
            ServiceException.CODE_FAIL,"密码不能为空");
    public static final ServiceException passwordError=new ServiceException(
            ServiceException.CODE_FAIL,"密码错误");

    public static final ServiceException oldPasswordError=new ServiceException(
            ServiceException.CODE_FAIL,"旧密码错误");

    public static final ServiceException password2Error=new ServiceException(
            ServiceException.CODE_FAIL,"两次密码错误");

    public static final ServiceException entityNotExist=new ServiceException(
            ServiceException.CODE_FAIL,"实体不存在");

    public static final ServiceException loginNameExist=new ServiceException(
            ServiceException.CODE_FAIL,"登录名称重复");


}
