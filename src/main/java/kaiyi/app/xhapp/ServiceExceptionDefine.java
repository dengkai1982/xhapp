package kaiyi.app.xhapp;

import kaiyi.app.xhapp.entity.curriculum.AlreadyCourse;
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
    public static final ServiceException validateCodeError=new ServiceException(
            ServiceException.CODE_FAIL,"短信验证码错误");
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
    public static final ServiceException messageSenderError=new ServiceException(
            ServiceException.CODE_FAIL,"短信发送错误");

    public static final ServiceException addMediaError=new ServiceException(
            ServiceException.CODE_FAIL,"添加媒体库错误,请稍后重试");

    public static final ServiceException goldInsufficient=new ServiceException(
            ServiceException.CODE_FAIL,"账户金币余额不足");
    public static final ServiceException mediaReference=new ServiceException(
            ServiceException.CODE_FAIL,"视频文件已被引用,不能删除");

    public static final ServiceException certConcerned=new ServiceException(
            ServiceException.CODE_FAIL,"证书已经被关注过");

    public static final ServiceException enterpriseConcerned=new ServiceException(
            ServiceException.CODE_FAIL,"企业已经被关注过");

    public static final ServiceException resumeConcerned=new ServiceException(
            ServiceException.CODE_FAIL,"简历已经被关注过");

    public static final ServiceException examQuestionExist=new ServiceException(
            ServiceException.CODE_FAIL,"尚有同类型考试未完成");

    public static final ServiceException categoryError=new ServiceException(
            ServiceException.CODE_FAIL,"所属类别错误");

    public static final ServiceException questionType=new ServiceException(
            ServiceException.CODE_FAIL,"所属类型错误");

    public static final ServiceException courseNotFree=new ServiceException(
            ServiceException.CODE_FAIL,"所选课程非免费课程");

    public static final ServiceException alreadyCourseExist=new ServiceException(
            ServiceException.CODE_FAIL,"所选课程已加入到已购清单");

    public static final ServiceException dayWithdrawOne=new ServiceException(
            ServiceException.CODE_FAIL,"每日仅能申请一次提现");

    public static final ServiceException limitWithdraw=new ServiceException(
            ServiceException.CODE_FAIL,"当日申请的提现金额超出限制");

    public static final ServiceException withdrawableAmountError=new ServiceException(
            ServiceException.CODE_FAIL,"申请提现金额错误");
    public static final ServiceException withdrawableError=new ServiceException(
            ServiceException.CODE_FAIL,"可提现金额不足");
    public static final ServiceException rechargeZreo=new ServiceException(
            ServiceException.CODE_FAIL,"充值金额必须大于0");


}
