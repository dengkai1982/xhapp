package kaiyi.app.xhapp.controller.app;

import kaiyi.app.xhapp.entity.examination.ExamQuestion;
import kaiyi.app.xhapp.service.examination.ExamQuestionService;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.json.creator.MutilJsonCreator;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 考试试卷相关
 */
@Controller
@RequestMapping(ExaminationAction.rootPath)
public class ExaminationAction extends SuperAction {
    public static final String rootPath=PREFIX+"/examination";
    @Resource
    private ExamQuestionService examQuestionService;
    /**
     * 根据试卷来构建考试题
     * testPager:试卷ID
     * accountId:账户ID
     * @param interactive
     * @param response
     */
    @PostMapping("/generatorExamQuestionByTestPager")
    public void generatorExamQuestionByTestPager(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String testPagerId=interactive.getStringParameter("testPagerId","");
        String accountId=interactive.getStringParameter("accountId","");
        MutilJsonCreator mjc=new MutilJsonCreator();
        JsonMessageCreator jmc=getSuccessMessage();
        mjc.addJsonCreator(jmc);
        try {
            ExamQuestion examQuestion=examQuestionService.generatorByTestPager(accountId,testPagerId);
            mjc.addJsonCreator(defaultWriteObject(examQuestion));
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(mjc.build());
    }
    /**
     * 根据QuestionCategory类别ID来构建考试试题
     */
    @PostMapping("/generatorExamQuestionByCategory")
    public void generatorExamQuestionByCategory(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String categoryId=interactive.getStringParameter("categoryId","");
        String accountId=interactive.getStringParameter("accountId","");
        MutilJsonCreator mjc=new MutilJsonCreator();
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            ExamQuestion examQuestion=examQuestionService.generatorByCategory(accountId,categoryId);
            mjc.addJsonCreator(defaultWriteObject(examQuestion));
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(mjc.build());
    }
    /**
     * 根据SimulationCategory来构建考试试题
     */
    @PostMapping("/generatorExamQuestionBySimulationCategory")
    public void generatorExamQuestionBySimulationCategory(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String categoryId=interactive.getStringParameter("categoryId","");
        String accountId=interactive.getStringParameter("accountId","");
        MutilJsonCreator mjc=new MutilJsonCreator();
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            ExamQuestion examQuestion=examQuestionService.generatorBySimulationCategory(accountId,categoryId);
            mjc.addJsonCreator(defaultWriteObject(examQuestion));
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(mjc.build());
    }

    /**
     * 考试答题
     * examQuestionId:问题ID
     * resultAnswer:回答问题的答案，如果是多选题，需要对答案进行排序并用,号分割，例如 A,B,D
     * @param interactive
     * @param response
     */
    @PostMapping("/answerQuestion")
    public void answerQuestion(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String examQuestionId=interactive.getStringParameter("examQuestionId","");
        String resultAnswer=interactive.getStringParameter("resultAnswer","");
        examQuestionService.answerQuestion(examQuestionId,resultAnswer);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }
}
