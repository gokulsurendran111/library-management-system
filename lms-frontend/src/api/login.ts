const apiUrl = import.meta.env.VITE_API_URL;

export async function login(userName: string, password: string) {
  const response = await fetch(`${apiUrl}/api/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ userName, password }),
  });

  if (!response.ok) {
    throw new Error("Login failed");
  }

  const data = await response.json();
  localStorage.setItem("jwtToken", data.token);
  localStorage.setItem("userName", userName);
  return data;
}

export function getToken() {
  return localStorage.getItem("jwtToken");
}
