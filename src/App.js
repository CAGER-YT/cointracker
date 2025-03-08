// src/App.js
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Dashboard from "./components/Dashboard";
import AddCoinForm from "./components/AddCoinForm";
import CoinHistory from "./components/CoinHistory";
import Header from "./components/Header";

const App = () => {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/add" element={<AddCoinForm />} />
        <Route path="/history" element={<CoinHistory />} />
      </Routes>
    </Router>
  );
};

export default App;