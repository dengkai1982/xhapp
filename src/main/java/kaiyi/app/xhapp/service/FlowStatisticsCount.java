package kaiyi.app.xhapp.service;

import kaiyi.app.xhapp.entity.pojo.FlowStatisticsPojo;
import kaiyi.puer.db.query.QueryExpress;

public interface FlowStatisticsCount {
    FlowStatisticsPojo flowStatisticsPojo(QueryExpress queryExpress);
}
