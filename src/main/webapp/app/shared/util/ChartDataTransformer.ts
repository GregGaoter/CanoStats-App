import { MonthlyAnalysisStats } from 'app/shared/model/MonthlyAnalysisStats';
import { ChartData, ChartDataset } from 'chart.js';
import dayjs from 'dayjs';
import { TopLossesResult } from '../model/TopLossesResult';

interface GraphicData {
  labels: string[];
  datasets: Array<{
    label: string;
    data: number[];
  }>;
}

/**
 * Transforms API response (Map of month -> MonthlyAnalysisResult[]) into Chart.js LineChart data format.
 *
 * @param apiMapResponse Map with month numbers (1-12) as keys and arrays of MonthlyAnalysisResult as values
 * @returns ChartData formatted for Chart.js with month labels and datasets per product code
 */
export const transformMonthlyAnalysisToChartData = (
  apiMapResponse: { [month: number]: MonthlyAnalysisStats[] },
  productTypesByCode: string[],
  monthToYears: Map<number, number[]>,
): GraphicData => {
  // Sort month keys to ensure proper order
  const sortedMonths = Object.keys(apiMapResponse)
    .map(Number)
    .sort((a, b) => a - b);

  // Map month numbers to month names
  const labels = sortedMonths.map(month => {
    const date = dayjs().month(month - 1);
    return `${date.format('MMMM')} ${monthToYears.get(month)?.join('-') ?? ''}`;
  });

  // Use provided product type prefixes (first three letters) to build consistent datasets
  const productTypePrefixes = Array.from(new Set(productTypesByCode));

  // Create a map of productTypePrefix -> array of percentageAverage values for each month
  const productDataMap = new Map<string, number[]>();

  // Initialize each product type with an array of the same length as months
  productTypePrefixes.forEach(prefix => {
    productDataMap.set(prefix, new Array(sortedMonths.length).fill(null));
  });

  // Fill in the data for each product type and month
  // If multiple results exist for the same product type in a month, average them
  sortedMonths.forEach((month, monthIndex) => {
    const resultsForMonth = apiMapResponse[month];
    const productGroupMap = new Map<string, MonthlyAnalysisStats[]>();

    // Group results by product type prefix (first three letters of productCode)
    resultsForMonth.forEach(result => {
      if (!result.productCode) {
        return;
      }
      const prefix = result.productCode.substring(0, 3);
      if (!productDataMap.has(prefix)) {
        return;
      }
      if (!productGroupMap.has(prefix)) {
        productGroupMap.set(prefix, []);
      }
      productGroupMap.get(prefix)?.push(result);
    });

    // Calculate average percentageAverage for each product type prefix
    productGroupMap.forEach((results, prefix) => {
      const validPercentages = results
        .filter(r => r.percentageStats.mean !== null && r.percentageStats.mean !== undefined)
        .map(r => r.percentageStats.mean);

      if (validPercentages.length > 0) {
        const average = validPercentages.reduce((sum, val) => sum + val, 0) / validPercentages.length;
        const dataArray = productDataMap.get(prefix);
        if (dataArray) {
          dataArray[monthIndex] = average;
        }
      }
    });
  });

  // Generate distinct colors for each product type
  const colors = generateColors(productTypePrefixes.length);

  // Create datasets for each product type prefix
  const datasets = productTypePrefixes.map((prefix, index) => ({
    label: prefix,
    data: productDataMap.get(prefix) || [],
    borderColor: colors[index],
    backgroundColor: `${colors[index]}15`, // Add transparency
    tension: 0.4,
    fill: false,
  }));

  return {
    labels,
    datasets,
  };
};

export const transformTopLossesToChartData = (results: TopLossesResult[]): ChartData<'bar'> => {
  const documentStyle = getComputedStyle(document.documentElement);
  const labels = results.map(r => r.product ?? 'N/A');
  const data = results.map(r => r.percentage ?? 0);
  const dataset: ChartDataset<'bar'> = {
    label: 'Percentage',
    data,
    backgroundColor: documentStyle.getPropertyValue('--blue-600'),
    borderColor: documentStyle.getPropertyValue('--surface-card'),
  };
  return { labels, datasets: [dataset] };
};

/**
 * Generates an array of distinct colors for chart datasets.
 * Uses a palette of vibrant colors that repeat if more colors are needed.
 */
const generateColors = (count: number): string[] => {
  const palette = [
    '#FF6B6B', // red
    '#4ECDC4', // teal
    '#45B7D1', // blue
    '#FFA07A', // salmon
    '#98D8C8', // mint
    '#F7DC6F', // yellow
    '#BB8FCE', // purple
    '#85C1E2', // light blue
    '#F8B88B', // peach
    '#A9DFBF', // light green
    '#F5B7B1', // light red
    '#D7BDE2', // light purple
  ];

  const colors: string[] = [];
  for (let i = 0; i < count; i++) {
    colors.push(palette[i % palette.length]);
  }

  return colors;
};
