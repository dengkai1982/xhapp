package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.entity.curriculum.CourseComment;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.access.VisitorUserService;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.db.orm.ORMException;
import org.springframework.stereotype.Service;
import kaiyi.puer.db.orm.ServiceException;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Service("courseCommentService")
public class CourseCommentServiceImpl extends InjectDao<CourseComment> implements CourseCommentService {
    private static final long serialVersionUID = 8841216915639902920L;
    @Resource
    private CourseService courseService;
    @Resource
    private AccountService accountService;
    @Resource
    private VisitorUserService visitorUserSerivce;

    private static final int MIN_SCORE=1;

    private static final int MAX_SCORE=5;

    @Override
    public void comment(String courseId, String commentatorId, String content,int score)throws ServiceException {
        Course course=courseService.findForPrimary(courseId);
        Account commentator=accountService.findForPrimary(commentatorId);
        if(Objects.isNull(course)||Objects.isNull(commentator)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        CourseComment comment=new CourseComment();
        comment.setCommentator(commentator);
        comment.setCommentTime(new Date());
        comment.setContent(content);
        comment.setCourse(course);
        if(score>MAX_SCORE){
            score=MAX_SCORE;
        }else if(score<MIN_SCORE){
            score=MIN_SCORE;
        }
        comment.setScore(score);
        double avgCommentScore=avgCommentScore(course);
        course.setCommentAvgScore(avgCommentScore);
        courseService.updateObject(course);
        saveObject(comment);
    }

    @Override
    public void reply(String entityId, String replierId, String replyContent)throws ServiceException {
        CourseComment comment=findForPrimary(entityId);
        VisitorUser replier=visitorUserSerivce.findForPrimary(replierId);
        if(Objects.isNull(comment)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        comment.setReplier(replier);
        comment.setReplyTime(new Date());
        comment.setReply(replyContent);
        comment.setAnswer(true);
    }


    @Override
    public void deleteForPrimary(Serializable entityid) throws ORMException {
        CourseComment courseComment=findForPrimary(entityid);
        if(Objects.nonNull(courseComment)){
            Course course=courseComment.getCourse();
            super.deleteForPrimary(entityid);
            avgCommentScore(course);
        }
    }

    private double avgCommentScore(Course course){
        Double result=(Double)em.createQuery("select avg(o.score) from "+getEntityName(entityClass)+" o " +
                "where o.course=:course ").setParameter("course",course).getSingleResult();
        if(Objects.isNull(result)){
            result=new Double(0);
        }
        return Currency.build(result,1).doubleValue();
    }

}
