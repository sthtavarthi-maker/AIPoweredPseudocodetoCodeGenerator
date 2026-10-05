import axios from "axios";

const API = axios.create({
    baseURL: "http://localhost:8080/api",
    headers: {
        "Content-Type": "application/json",
    },
});


// =========================================================
// LOGIN
// =========================================================

export const loginUser = async (email, password) => {

    const response = await API.post(
        "/auth/login",
        {
            email,
            password,
        }
    );

    return response.data;
};


// =========================================================
// REGISTER
// =========================================================

export const registerUser = async (data) => {

    const response = await API.post(
        "/auth/register",
        data
    );

    return response.data;
};


// =========================================================
// ADD JWT TOKEN TO EVERY PROTECTED REQUEST
// =========================================================

API.interceptors.request.use(
    (config) => {

        const token = localStorage.getItem("token");

        if (token) {

            config.headers.Authorization =
                `Bearer ${token}`;
        }

        return config;
    },

    (error) => {
        return Promise.reject(error);
    }
);


// =========================================================
// GENERATE CODE
// Backend performs Ollama CALL #1
// =========================================================

export const generateCode = async (data) => {

    const response = await API.post(
        "/generator/code",
        data
    );

    return response.data;
};


// =========================================================
// GENERATE ANALYSIS
// Backend performs Ollama CALL #2
// =========================================================

export const generateAnalysis = async (data) => {

    const response = await API.post(
        "/generator/analysis",
        data
    );

    return response.data;
};


// =========================================================
// GET HISTORY
// =========================================================

export const getHistory = async () => {

    const response = await API.get(
        "/history"
    );

    return response.data;
};


// =========================================================
// DELETE HISTORY
// =========================================================

export const deleteHistory = async (id) => {

    await API.delete(
        `/history/${id}`
    );
};


export default API;