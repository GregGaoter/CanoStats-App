package ch.epicerielacanopee.statistics.service.util;

import java.util.List;

public class TopLossesAnalysis {

  private TopLossesKpis kpis;
  private List<TopLossesResult> topLossesResults;

  public TopLossesAnalysis(TopLossesKpis kpis, List<TopLossesResult> topLossesResults) {
    this.kpis = kpis;
    this.topLossesResults = topLossesResults;
  }

  public TopLossesKpis getKpis() {
    return kpis;
  }

  public void setKpis(TopLossesKpis kpis) {
    this.kpis = kpis;
  }

  public List<TopLossesResult> getTopLossesResults() {
    return topLossesResults;
  }

  public void setTopLossesResults(List<TopLossesResult> topLossesResults) {
    this.topLossesResults = topLossesResults;
  }
}
