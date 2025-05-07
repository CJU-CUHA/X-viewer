// renderer.js
import React from "react";
import { createRoot } from "react-dom/client";
import App from "./app.jsx";
import "./index.css";
// index.html 에 반드시 <div id="root"></div> 가 있어야 합니다.
const container = document.getElementById("root");
const root = createRoot(container);
root.render(<App />);
