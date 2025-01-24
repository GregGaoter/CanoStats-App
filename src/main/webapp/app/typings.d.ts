declare const VERSION: string;
declare const SERVER_API_URL: string;
declare const DEVELOPMENT: string;

declare module '*.json' {
  const value: any;
  export default value;
}

interface Window {
  showToast: (
    severity: 'success' | 'info' | 'warn' | 'error' | 'secondary' | 'contrast' | undefined,
    summary: React.ReactNode | undefined,
    detail: React.ReactNode | undefined,
  ) => void;
}
