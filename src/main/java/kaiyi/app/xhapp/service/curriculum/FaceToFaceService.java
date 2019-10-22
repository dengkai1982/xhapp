package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.FaceToFace;
import kaiyi.puer.db.orm.DatabaseQuery;

import java.util.Date;

public interface FaceToFaceService extends DatabaseQuery<FaceToFace>,ReplyService {
    /**
     * 课程预约
     * @param name 预约人
     * @param phone 预约人联系电话
     * @param course 预约课程名称
     * @param faceTime 预约面授时间
     */
    FaceToFace make(String accountId,String name, String phone, String course, Date faceTime);
}
