const documentStyle = getComputedStyle(document.documentElement);
const textColor = documentStyle.getPropertyValue('--text-color');
const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

export const lineOptions = (chartLabel: string, xLabel: string, yLabel: string) => ({
  plugins: {
    legend: {
      labels: {
        color: textColorSecondary,
      },
    },
    title: {
      display: true,
      text: chartLabel,
      color: textColorSecondary,
      font: {
        size: 16,
        weight: 'bold',
      },
    },
  },
  scales: {
    x: {
      ticks: {
        color: textColorSecondary,
      },
      title: {
        display: true,
        text: xLabel,
        color: textColorSecondary,
        font: {
          weight: 'bold',
        },
      },
      grid: {
        color: surfaceBorder,
      },
    },
    y: {
      ticks: {
        color: textColorSecondary,
      },
      title: {
        display: true,
        text: yLabel,
        color: textColorSecondary,
        font: {
          weight: 'bold',
        },
      },
      grid: {
        color: surfaceBorder,
      },
    },
  },
});

export const topLossesOptions = (chartLabel: string, xLabel: string, yLabel: string) => ({
  indexAxis: 'y',
  plugins: {
    legend: {
      display: false,
    },
    title: {
      display: true,
      text: chartLabel,
      color: textColorSecondary,
      font: {
        size: 16,
        weight: 'bold',
      },
    },
  },
  scales: {
    x: {
      ticks: {
        color: textColorSecondary,
      },
      title: {
        display: true,
        text: xLabel,
        color: textColorSecondary,
        font: {
          weight: 'bold',
        },
      },
      grid: {
        color: surfaceBorder,
      },
    },
    y: {
      ticks: {
        color: textColorSecondary,
        autoSkip: false,
      },
      title: {
        display: true,
        text: yLabel,
        color: textColorSecondary,
        font: {
          weight: 'bold',
        },
      },
      grid: {
        color: surfaceBorder,
      },
    },
  },
});
