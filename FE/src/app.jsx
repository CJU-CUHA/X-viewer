// app.jsx
import React from "react";
import { HashRouter, Routes, Route, Navigate } from "react-router-dom";
import Main from "./pages/Main";

import Case from "./pages/Case";
import Views from "./pages/Views";
import Process from "./pages/Process";
import Search from "./pages/Search";
import Mappings from "./pages/Mappings";

export default function App() {
  return (
    <HashRouter>
      <Routes>
        <Route path="/" element={<Main />}>
          <Route index element={<Case />} />
          <Route path="view" element={<Views />} />
          <Route path="proc" element={<Process />} />
          <Route path="search" element={<Search />} />
          <Route path="mapping" element={<Mappings />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Route>
      </Routes>
    </HashRouter>
  );
}
