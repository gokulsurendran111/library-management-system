import axios from 'axios';

const apiUrl = import.meta.env.VITE_API_URL;

interface RegisterParams {
    userName: string;
    email: string;
    password: string;
    role: string;
}

export async function registerUser(params: RegisterParams) {
    const response = await axios.post(`${apiUrl}/api/auth/register`, {
        userName: params.userName,
        email: params.email,
        password: params.password,
        role: params.role,
    });
    return response.data;
}