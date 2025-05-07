import React, { useState, useEffect, useCallback } from "react";
import { useLocation, useNavigate, Outlet } from "react-router-dom";
import { X } from "lucide-react";

// @dnd-kit imports
import {
  DndContext,
  closestCenter,
  PointerSensor,
  useSensor,
  useSensors,
} from "@dnd-kit/core";
import {
  SortableContext,
  arrayMove,
  rectSortingStrategy,
  useSortable,
} from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";

/** 개별 탭 컴포넌트 (드래그 가능) */
function SortableTab({ tab, isActive, onSelect, onClose }) {
  const {
    attributes,
    listeners,
    setNodeRef,
    transform,
    transition,
    isDragging, // <- 추가
  } = useSortable({ id: tab.path });

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
    cursor: isDragging ? "grabbing" : "grab",
  };

  return (
    <div
      ref={setNodeRef}
      style={style}
      className={`tab ${isActive ? "active" : ""}`}
      onClick={() => onSelect(tab.path)}
      {...attributes}
      {...listeners}
    >
      {tab.title}
      <button
        type="button"
        className="close-btn"
        onClick={(e) => {
          e.stopPropagation();
          onClose(tab.path);
        }}
      >
        <X size={12} />
      </button>
    </div>
  );
}

const getTitleFromPath = (path) => {
  if (path === "/") return "Case";
  const name = path.slice(1);
  return name.charAt(0).toUpperCase() + name.slice(1);
};

const Content = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [tabs, setTabs] = useState([]);

  // 새 URL 방문 시 탭 추가
  useEffect(() => {
    const path = location.pathname;
    setTabs((prev) =>
      prev.some((t) => t.path === path)
        ? prev
        : [...prev, { path, title: getTitleFromPath(path) }]
    );
  }, [location.pathname]);

  const selectTab = (path) => navigate(path);

  const closeTab = (pathToClose) => {
    setTabs((prev) => prev.filter((t) => t.path !== pathToClose));
    if (location.pathname === pathToClose) {
      const remaining = tabs.filter((t) => t.path !== pathToClose);
      navigate(remaining.length ? remaining[remaining.length - 1].path : "/");
    }
  };

  // DnD 센서 설정
  const sensors = useSensors(
    useSensor(PointerSensor, { activationConstraint: { distance: 5 } })
  );

  // 드래그 완료 시 순서 교체
  const handleDragEnd = useCallback(
    (event) => {
      const { active, over } = event;
      if (over && active.id !== over.id) {
        const oldIndex = tabs.findIndex((t) => t.path === active.id);
        const newIndex = tabs.findIndex((t) => t.path === over.id);
        setTabs((prev) => arrayMove(prev, oldIndex, newIndex));
      }
    },
    [tabs]
  );

  return (
    <div className="content-section">
      <DndContext
        sensors={sensors}
        collisionDetection={closestCenter}
        onDragEnd={handleDragEnd}
      >
        <SortableContext
          items={tabs.map((t) => t.path)}
          strategy={rectSortingStrategy}
        >
          <div className="tabs">
            {tabs.map((tab) => (
              <SortableTab
                key={tab.path}
                tab={tab}
                isActive={location.pathname === tab.path}
                onSelect={selectTab}
                onClose={closeTab}
              />
            ))}
          </div>
        </SortableContext>
      </DndContext>

      <div className="tab-content">
        <Outlet />
      </div>
    </div>
  );
};

export default Content;
