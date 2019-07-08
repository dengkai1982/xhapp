package kaiyi.app.xhapp.service.examination;
import kaiyi.app.xhapp.entity.examination.ExamChoiceAnswer;
import kaiyi.app.xhapp.entity.examination.ExamQuestion;
import kaiyi.app.xhapp.entity.examination.ExamQuestionItem;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.bean.BeanSyntacticSugar;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.Pagination;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.json.JsonCreator;
import kaiyi.puer.json.JsonValuePolicy;
import kaiyi.puer.json.creator.CollectionJsonCreator;
import kaiyi.puer.json.creator.JsonBuilder;
import kaiyi.puer.json.creator.ObjectJsonCreator;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service("examQuestionItemService")
public class ExamQuestionItemServiceImpl extends InjectDao<ExamQuestionItem> implements ExamQuestionItemService {
    @Override
    public ExamQuestionItem finishAnswer(String entityId, String answer) {
        ExamQuestionItem questionItem=findForPrimary(entityId);
        if(Objects.nonNull(questionItem)){
            questionItem.setResultAnswer(answer);
            questionItem.setFinished(true);
            questionItem.setResult(questionItem.getStandardAnswer().equalsIgnoreCase(answer));
            updateObject(questionItem);
        }
        return questionItem;
    }

    @Override
    public int successScore(String examQuestionId) {
        ExamQuestion examQuestion=new ExamQuestion();
        examQuestion.setEntityId(examQuestionId);
        Object result=em.createQuery("select coalesce(sum(o.score),0) from "+getEntityName(entityClass)+" o " +
                "where o.examQuestion=:examQuestion and o.finished=:finished and o.result=:result")
                .setParameter("examQuestion",examQuestion).setParameter("finished",Boolean.TRUE)
                .setParameter("result",Boolean.TRUE).getSingleResult();
        if(Objects.nonNull(result)){
            return Integer.parseInt(result.toString());
        }
        return 0;
    }

    @Override
    public boolean isFinished(int totalNumber,String examQuestionId) {
        ExamQuestion examQuestion=new ExamQuestion();
        examQuestion.setEntityId(examQuestionId);
        QueryExpress query=new CompareQueryExpress("examQuestion", CompareQueryExpress.Compare.EQUAL,
                examQuestion);
        query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,new CompareQueryExpress("finished", CompareQueryExpress.Compare.EQUAL,
                Boolean.TRUE));
        return count(query)==totalNumber;
    }

    @Override
    public ObjectJsonCreator<Pagination> getCustomerCreator(Pagination<? extends JsonBuilder> pagination) {
        ObjectJsonCreator<Pagination> creator=new ObjectJsonCreator<Pagination>(pagination, new String[]{
                "totalPage", "currentPage", "dataList","first", "maxResult", "total",
        }, new JsonValuePolicy<Pagination>() {
            @Override
            public JsonCreator getCreator(Pagination entity, String field, Object fieldValue) {
                if(field.equals("dataList")&&StreamCollection.assertNotEmpty(entity.getDataList())){
                    StreamCollection<ExamQuestionItem> stream=entity.getDataList();
                    ExamQuestionItem examQuestionItem=stream.get(0);
                    String[] showFieldArray=BeanSyntacticSugar.getFieldStringNotStatic(examQuestionItem.getClass(),
                            examQuestionItem.filterField().getArray());
                    CollectionJsonCreator<ExamQuestionItem> streamCreate=new CollectionJsonCreator<>(stream,
                            showFieldArray, examQuestionItem.jsonFieldReplacePolicy(), new JsonValuePolicy<ExamQuestionItem>() {
                        @Override
                        public JsonCreator getCreator(ExamQuestionItem entity, String field, Object fieldValue) {
                            if(field.equals("choiceAnswers")){
                                StreamCollection<ExamChoiceAnswer> examChoiceAnswers = entity.getChoiceAnswerStream();
                                return new CollectionJsonCreator<>(examChoiceAnswers,new String[]{
                                        "optionName","detailValue"
                                });
                            }
                            return null;
                        }
                    });
                    return streamCreate;
                }
                return null;
            }
        });
        return creator;
    }
}
