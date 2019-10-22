package kaiyi.app.xhapp.controller.mgr;

import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.json.creator.JsonBuilder;
import kaiyi.puer.web.servlet.WebInteractive;
@FunctionalInterface
public interface ExcuterQuery {
    void excute(WebInteractive interactive,
                DatabaseQuery<? extends JsonBuilder> databaseQuery, QueryExpress query);
}
