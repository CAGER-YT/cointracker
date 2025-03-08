const getEnvironment = () => {
    const hostname = window.location.hostname;
  
    if (hostname === 'localhost' || hostname === 'dev.example.com') {
      return 'development';
    } else {
      return 'production';
    }
  };
  
  const environment = getEnvironment();
  
  const config = {
    development: {
      API_BASE_URL: 'http://localhost:8080/',
    },
    production: {
      API_BASE_URL: 'https://q52dj2f4-8080.inc1.devtunnels.ms/',
    },
  };
  
  export const API_BASE_URL = config[environment].API_BASE_URL;
  
  export default environment;