import { TopLossesKpis } from './TopLossesKpis';
import { TopLossesResult } from './TopLossesResult';

export interface TopLossesAnalysis {
  kpis?: TopLossesKpis;
  topLossesResults?: TopLossesResult[];
}
