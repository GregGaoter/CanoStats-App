import dayjs from 'dayjs';

export interface IMouvementsStock {
  id?: string;
  epicerioId?: number | null;
  createdDate?: dayjs.Dayjs;
  lastUpdatedDate?: dayjs.Dayjs;
  importedDate?: dayjs.Dayjs;
  date?: dayjs.Dayjs | null;
  utilisateur?: string | null;
  type?: string | null;
  epicerioMouvement?: number | null;
  mouvement?: number | null;
  solde?: number | null;
  vente?: string | null;
  codeProduit?: string | null;
  produit?: string | null;
  responsableProduit?: string | null;
  fournisseurProduit?: string | null;
  codeFournisseur?: string | null;
  reduction?: number | null;
  ponderation?: number | null;
  venteChf?: number | null;
  valeurChf?: number | null;
  remarques?: string | null;
}

export const defaultValue: Readonly<IMouvementsStock> = {};

export type MouvementsStockField = keyof IMouvementsStock;
