package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.log.TeamJoinNote;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.db.query.OrderBy;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("teamJoinNoteService")
public class TeamJoinNoteServiceImpl extends InjectDao<TeamJoinNote> implements TeamJoinNoteService {
    @Override
    public void saveNote(Account parent, Account joiner) {
        TeamJoinNote teamJoinNote=new TeamJoinNote();
        teamJoinNote.setParentAccount(parent);
        teamJoinNote.setJoinAccount(joiner);
        teamJoinNote.setJoinTime(new Date());
        saveObject(teamJoinNote);
    }
    @Override
    public OrderBy getDefaultOrderBy(String prefix) {
        return new OrderBy(prefix,"joinTime",OrderBy.TYPE.DESC);
    }
}
