import React, { useEffect, useState } from "react";
import "../styles/BooksPage.css";

import {findBooksByPattern, getBooksPage } from "../api/books";
import SortButton from "../components/SortButton";
import type { ChangeEvent } from "react";
import { useNavigate } from 'react-router-dom';

interface Book {
  id: number;
  title: string;
  author: string;
  category: string;
  available: boolean;
}

export default function BooksPage() {
  const [books, setBooks] = useState<any[]>([]);
  const [allBooks, setAllBooks] = useState<any[]>([]);
  const [totalPages, setTotalPages] = useState(0);
  const [page, setPage] = useState(0);
  const [nbPages, setNbPages] = useState(0);
  const [searchTerm, setSearchTerm] = useState("");
  const tableHeadings = [
    { label: "Book ID", field: "id" },
    { label: "Title", field: "title" },
    { label: "Author", field: "author" },
    { label: "Category", field: "category" },
    { label: "Availability", field: "available" },
    { label: "Actions", field: "actions" }
  ];
  const [sortOrder, setSortOrder] = useState<"asc" | "desc">("asc");
  const [sortField, setSortField] = useState<keyof Book>("id");
  const [cart, setCart] = useState<Book[]>([]);

useEffect(() => {
  getBooksPage(page)
    .then((data) => {
      setBooks(data.content);
      setAllBooks(data.content);
      setNbPages(data.totalPages);
      setTotalPages(data.totalPages);
    })
    .catch((err) => {
      console.error(err);
      navigate('/login'); // redirect if unauthorized
    });
}, [page]);


  const addToCart = (book: Book) => {
    if (!cart.some((b) => b.id === book.id)) {
      setCart([...cart, book]);
    }
  };

  const removeFromCart = (id: number) => {
    setCart(cart.filter((b) => b.id !== id));
  };

  const navigate = useNavigate();

  function handleSearchChange(event: ChangeEvent<HTMLInputElement>): void {
    setSearchTerm(event.target.value);
  }

  function handleClick() {
    if (searchTerm.trim() === "") {
      setBooks(allBooks);
      setNbPages(totalPages);
      return;
    }
    findBooksByPattern(searchTerm).then(
      data => {
        setBooks(data.content)
        setNbPages(data.totalPages);
      }
    )
  }

  function handleSort() {
    setSortOrder((prev) => (prev === "asc" ? "desc" : "asc"));
    setBooks((prevBooks) =>
      [...prevBooks].sort((a, b) => {
        const aValue = a[sortField];
        const bValue = b[sortField];
        if (aValue < bValue) return sortOrder === "asc" ? -1 : 1;
        if (aValue > bValue) return sortOrder === "asc" ? 1 : -1;
        return 0;
      })
    );
  }

  return (
    <div className="books-layout">
      
      {/* LEFT SIDEBAR */}
      <aside className="sidebar">
        <h2 className="sidebar-title">Menu</h2>
        <ul>
          <li onClick={() => navigate('/profile')} style={{ cursor: "pointer" }}>Profile</li>
          <li>My Books</li>
          <li>Past Books I Held</li>
          <li>Past Due Payments</li>
          <li>Transaction Log</li>
          <li>Logout</li>
        </ul>
      </aside>

      {/* CENTER CONTENT */}
      <main className="main-content">
        <h1 className="books-title">📚 Library Books</h1>
        
        <div className="books-actions">
          <input onChange={handleSearchChange} type="text" placeholder="Search books..." className="books-search" />
          <button className="books-search-btn" onClick={handleClick}>Search</button>
          <p className="adv-search">Advanced Search</p>
        </div>

        <table className="books-table">
          <thead>
            <tr>
              {tableHeadings.map((heading) => (
                <th key={heading.field}>
                  {heading.label}
                  {heading.field !== "actions" && (
                  <SortButton onClick={() => {
                    setSortField(heading.field as keyof Book);
                    handleSort();
                  }} />
                  )}
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {books.map((book) => (
              <tr key={book.id}>
                <td>{book.id}</td>
                <td>{book.title}</td>
                <td>{book.author}</td>
                <td>{book.category}</td>
                <td>{book.available ? "Available" : "Borrowed"}</td>
                <td>
                  <button className="add-to-cart-btn" onClick={() => addToCart(book)}>Add to Cart</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {/* Pagination */}
        <div className="books-pagination">
          {Array.from({ length: nbPages }, (_, i) => (
            <button
              key={i}
              className={i === page ? "active" : ""}
              onClick={() => setPage(i)}
            >
              {i + 1}
            </button>
          ))}
        </div>
      </main>

      {/* RIGHT SIDEBAR */}
      <aside className="cart-sidebar">
        <h2>🛒 My Cart</h2>
        {cart.length === 0 ? (
          <p>No books in cart.</p>
        ) : (
          <ul>
            {cart.map((book) => (
              <li key={book.id}>
                {book.title}
                <button onClick={() => removeFromCart(book.id)}>❌</button>
              </li>
            ))}
          </ul>
        )}
        <button className="borrow-all-btn" disabled={cart.length === 0}>
          Borrow All
        </button>
      </aside>
    </div>
  );
}
