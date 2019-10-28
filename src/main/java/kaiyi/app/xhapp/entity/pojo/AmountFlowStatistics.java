package kaiyi.app.xhapp.entity.pojo;

/**
 * 资金流水统计
 */
public class AmountFlowStatistics {
    private String name;
    //消费提成
    private String settlementRoyalty;
    //人工结算
    private String manualCaculation;
    //消费提成(内部)
    private String insideSettlementRoyalty;
    //人工结算(内部)
    private String insideManualCaculation;

    public AmountFlowStatistics(){
        this.settlementRoyalty="0.00";
        this.manualCaculation="0.00";
        this.insideSettlementRoyalty="0.00";
        this.insideManualCaculation="0.00";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSettlementRoyalty() {
        return settlementRoyalty;
    }

    public void setSettlementRoyalty(String settlementRoyalty) {
        this.settlementRoyalty = settlementRoyalty;
    }

    public String getManualCaculation() {
        return manualCaculation;
    }

    public void setManualCaculation(String manualCaculation) {
        this.manualCaculation = manualCaculation;
    }

    public String getInsideSettlementRoyalty() {
        return insideSettlementRoyalty;
    }

    public void setInsideSettlementRoyalty(String insideSettlementRoyalty) {
        this.insideSettlementRoyalty = insideSettlementRoyalty;
    }

    public String getInsideManualCaculation() {
        return insideManualCaculation;
    }

    public void setInsideManualCaculation(String insideManualCaculation) {
        this.insideManualCaculation = insideManualCaculation;
    }
}
