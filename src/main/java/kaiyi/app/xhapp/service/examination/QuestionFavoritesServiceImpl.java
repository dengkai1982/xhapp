package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.examination.Question;
import kaiyi.app.xhapp.entity.examination.QuestionFavorites;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("questionFavoritesService")
public class QuestionFavoritesServiceImpl extends InjectDao<QuestionFavorites> implements QuestionFavoritesService {

    @Override
    public void favorites(String accountId, String questionId) {
        Account account=new Account();
        account.setEntityId(accountId);
        Question question=new Question();
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
}
