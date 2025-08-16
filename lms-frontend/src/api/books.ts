import { getToken } from "./login";
import { useNavigate } from 'react-router-dom';

const apiUrl = import.meta.env.VITE_API_URL;

export async function findBooksByPattern(query: string) {
  const token = getToken();
  const response = await fetch(`${apiUrl}/api/books/search?pattern=${encodeURIComponent(query)}`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      },
    }
  );
  if (!response.ok) {
    throw new Error("Failed to search books by name");
  }
  return response.json();
}

export async function getBooksPage(page: number, size: number = 20) {
  const token = getToken();
  // console.log(token);

  const response = await fetch(`${apiUrl}/api/books?page=${page}&size=${size}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error("Failed to fetch books");
  }

  return response.json();
}
