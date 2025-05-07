import React from "react";
import Sidebar from "../components/sections/Sidebar";
import Content from "../components/sections/Content";

const Main = () => {
  return (
    <div className="wrapper-component">
      <div className="header-section">{/* 여기에 앱 헤더 삽입 */}</div>

      <div className="middle-section" style={{ display: "flex" }}>
        <Sidebar />
        {/* Content 내부에서 탭 바와 Outlet을 관리 */}
        <Content />
      </div>

      <div className="footer-section">{/* 여기에 앱 푸터 삽입 */}</div>
    </div>
  );
};

export default Main;
