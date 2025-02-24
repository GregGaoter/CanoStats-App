export const getInventoryByWeightQueryParams = (mouvement: number, period: Date[]): string => {
  const searchParams: URLSearchParams = new URLSearchParams();
  searchParams.set('type.equals', 'Inventaire');
  searchParams.set('vente.equals', 'Au poids');
  searchParams.set('mouvement.lessThanOrEqual', `${-mouvement}`);
  searchParams.set('date.greaterThanOrEqual', period[0].toISOString());
  searchParams.set('date.lessThanOrEqual', period[1].toISOString());
  searchParams.set('cacheBuster', `${new Date().getTime()}`);
  return searchParams.toString();
};
