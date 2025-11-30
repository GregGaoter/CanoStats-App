import dayjs from 'dayjs';

export interface IProduit {
  id?: string;
  epicerioId?: number | null;
  createdDate?: dayjs.Dayjs;
  lastUpdatedDate?: dayjs.Dayjs;
  importedDate?: dayjs.Dayjs;
  nom?: string | null;
  code?: string | null;
  disponible?: string | null;
  prixFournisseur?: number | null;
  htTtc?: string | null;
  tauxTva?: number | null;
  margeProfit?: number | null;
  prixVente?: number | null;
  vendu?: string | null;
  quantiteParPiece?: number | null;
  unite?: string | null;
  prixParUnite?: string | null;
  description?: string | null;
  remarquesInternes?: string | null;
  fournisseur?: string | null;
  refFournisseur?: string | null;
  stock?: number | null;
  commandesClients?: number | null;
  derniereVerificationDate?: dayjs.Dayjs | null;
  derniereLivraisonDate?: dayjs.Dayjs | null;
  achatFournisseur?: string | null;
  dernierAchatDate?: dayjs.Dayjs | null;
  dernierAchatQuantite?: number | null;
  statsLivraison?: number | null;
  statsPerte?: number | null;
  statsVente?: number | null;
  statsVenteSpeciale?: number | null;
  tags?: string | null;
}

export const defaultValue: Readonly<IProduit> = {};
