import { getToken } from "./login";

const apiUrl = import.meta.env.VITE_API_URL;

export async function getProfile() {
  const token = getToken();
    const storedUserName = localStorage.getItem("userName");
    if (!storedUserName) {
        throw new Error("User not logged in");
        return;
    }
    
    const response = await fetch(`${apiUrl}/api/users/username/${storedUserName}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
        },
    });

    if (!response.ok) throw new Error("Failed to fetch user details.");
    // console.log(response);

  return response.json();
}