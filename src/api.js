import axios from 'axios';
import { API_BASE_URL } from './config/environment';

console.log("API Base URL:", API_BASE_URL);

const api = axios.create({
  baseURL: API_BASE_URL,
});

export default api;