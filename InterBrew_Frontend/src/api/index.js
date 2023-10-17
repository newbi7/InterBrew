import axios from "axios";

function createAxiosInstance() {
  const BASE_URL = process.env.VUE_APP_BASE_URL_IP_ADDRESS;
  const API_VERSION = process.env.VUE_APP_API_VERSION;
  const PORT_NUMBER = process.env.VUE_APP_PORT_NUMBER;
  return axios.create({
    baseURL: `http://${BASE_URL}:${PORT_NUMBER}/${API_VERSION}`,
    headers: {
        "Content-Type": "application/json",
      },
  });
}

function createImageInstance() {
  const BASE_URL = process.env.VUE_APP_BASE_URL_IP_ADDRESS;
  const API_VERSION = process.env.VUE_APP_API_VERSION;
  const PORT_NUMBER = process.env.VUE_APP_PORT_NUMBER;
  return axios.create({
    baseURL: `http://${BASE_URL}:${PORT_NUMBER}/${API_VERSION}`,
    headers: {
      'Content-Type': 'multipart/form-data',
      },
  });
}

export { createAxiosInstance, createImageInstance };
