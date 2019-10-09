package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.examination.ExamQuestionItem;
import kaiyi.app.xhapp.entity.examination.Question;
import kaiyi.app.xhapp.entity.examination.QuestionFavorites;
import kaiyi.app.xhapp.entity.examination.enums.QuestionType;
import kaiyi.app.xhapp.service.CustomerPaginationJson;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.bean.BeanSyntacticSugar;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.Pagination;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.json.DefaultJsonValuePolicy;
import kaiyi.puer.json.JsonCreator;
import kaiyi.puer.json.JsonValuePolicy;
import kaiyi.puer.json.creator.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("questionFavoritesService")
public class QuestionFavoritesServiceImpl extends InjectDao<QuestionFavorites> implements QuestionFavoritesService,
        CustomerPaginationJson {

    @Override
    public void favorites(String accountId, String questionId) {
        Account account=new Account();
        account.setEntityId(accountId);
        ExamQuestionItem question=new ExamQuestionItem();
        question.setEntityId(questionId);
        QueryExpress query=new CompareQueryExpress("account",CompareQueryExpress.Compare.EQUAL,account);
        query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,
                new CompareQueryExpress("question",CompareQueryExpress.Compare.EQUAL,question));
        if(exist(query)){
            em.createQuery("delete from "+getEntityName(entityClass)+" o where o.account=:account " +
                    "and o.question=:question").setParameter("account",account).setParameter("question",question)
                    .executeUpdate();
        }else{
            QuestionFavorites favorites=new QuestionFavorites();
            favorites.setAccount(account);
            favorites.setQuestion(question);
            favorites.setCreateTime(new Date());
            saveObject(favorites);
        }
    }

    @Override
    public ObjectJsonCreator<Pagination> getCustomerCreator(Pagination<? extends JsonBuilder> pagination) {
        ObjectJsonCreator<Pagination> creator=new ObjectJsonCreator<Pagination>(pagination, new String[]{
                "totalPage", "currentPage", "dataList","first", "maxResult", "total",
        }, new JsonValuePolicy<Pagination>() {
            @Override
            public JsonCreator getCreator(Pagination entity, String field, Object fieldValue) {
                if(field.equals("dataList")&&StreamCollection.assertNotEmpty(entity.getDataList())){
                    StreamCollection<JsonBuilder> stream=entity.getDataList();
                    JsonBuilder jsonBuilder=stream.get(0);
                    String[] showFieldArray=BeanSyntacticSugar.getFieldStringNotStatic(jsonBuilder.getClass(),
                            jsonBuilder.filterField().getArray());
                    CollectionJsonCreator<JsonBuilder> streamCreate=new CollectionJsonCreator<>(stream,
                            showFieldArray, jsonBuilder.jsonFieldReplacePolicy(), new JsonValuePolicy<JsonBuilder>() {
                        @Override
                        public JsonCreator getCreator(JsonBuilder entity, String field, Object fieldValue) {
                            if(field.equals("question")){
                                ExamQuestionItem questionItem=(ExamQuestionItem)fieldValue;
                                return new ObjectJsonCreator<>(questionItem, new String[]{
                                        "entityId", "questionType", "detail", "weight", "score", "analysis", "standardAnswer", "resultAnswer",
                                        "result", "finished"
                                }, new JsonValuePolicy<ExamQuestionItem>() {
                                    @Override
                                    public JsonCreator getCreator(ExamQuestionItem entity, String field, Object fieldValue) {
                                        if(field.equals("questionType")){
                                            MapJsonCreator mapJsonCreator=new MapJsonCreator();
                                            QuestionType type=entity.getQuestionType();
                                            mapJsonCreator.put("ordinal",new StringJsonCreator(String.valueOf(type.getItemNumber())));
                                            mapJsonCreator.put("value",new StringJsonCreator(type.getValue()));
                                            mapJsonCreator.put("name",new StringJsonCreator(type.getShowName()));
                                            return mapJsonCreator;
                                        }
                                        return null;
                                    }
                                });
                            }
                            return new DefaultJsonValuePolicy<JsonBuilder>(null).getCreator(entity,field,fieldValue);
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
