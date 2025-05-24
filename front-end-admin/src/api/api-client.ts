import axios, { AxiosError } from "axios";

export const apiClient = axios.create({
    baseURL: "https://doty-movie.onrender.com/api/v1/",
    timeout: 10000,
    // withCredentials: true
});

apiClient.interceptors.response.use(
    (response) => response,
    (error: AxiosError) => {
        if (error.response?.status === 401) {
            console.error('Unauthorized! Please log in again.');
        } else if (error.response?.status === 404) {
            console.error('API endpoint not found.');
        } else if (error.response?.status === 500) {
            console.error('Server error, please try again later.');
        }
        return Promise.reject(error);
    }
)