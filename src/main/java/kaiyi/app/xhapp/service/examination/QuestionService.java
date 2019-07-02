package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.examination.Question;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface QuestionService extends DatabaseQuery<Question>, DatabaseFastOper<Question>, DatabaseOperator<Question> {
}
