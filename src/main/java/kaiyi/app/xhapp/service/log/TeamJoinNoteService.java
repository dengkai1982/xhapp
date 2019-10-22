package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.log.TeamJoinNote;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface TeamJoinNoteService extends DatabaseQuery<TeamJoinNote> ,DatabaseOperator<TeamJoinNote> {

    void saveNote(Account parent,Account joiner);
}
