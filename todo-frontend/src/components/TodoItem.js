// todo-frontend\src\components\TodoItem.js
import React from 'react';

const TodoItem = ({ todo, onToggleComplete, onDelete, onEdit, processing = false }) => {
  const safeDate = (d) => {
    if (!d) return null;
    try { return new Date(d).toLocaleString(); } catch { return String(d); }
  };

  const handleToggle = () => {
    if (!todo.title?.trim()) {
      alert("Cannot toggle: title is empty");
      return;
    }
    onToggleComplete?.(todo.id, {
      title: todo.title.trim(),
      description: todo.description?.trim() || '',
      completed: !todo.completed,
    });
  };

  const handleDelete = () => {
    if (window.confirm('Are you sure you want to delete this todo?')) {
      onDelete?.(todo.id);
    }
  };

  const handleEdit = () => onEdit?.(todo);

  return (
    <div className={`todo-item ${todo.completed ? 'completed' : ''}`}>
      <div className="todo-content">
        <h3>{todo.title}</h3>
        <p>{todo.description}</p>
        <small className="meta">
          {safeDate(todo.createdAt) ? `Created: ${safeDate(todo.createdAt)}` : ''}
          {todo.updatedAt && todo.updatedAt !== todo.createdAt ? ` | Updated: ${safeDate(todo.updatedAt)}` : ''}
        </small>
      </div>
      <div className="todo-actions">
        <button className={`toggle-btn ${todo.completed ? 'completed' : ''}`} onClick={handleToggle} disabled={processing}>
          {todo.completed ? 'Mark Incomplete' : 'Mark Complete'}
        </button>
        <button className="edit-btn" onClick={handleEdit} disabled={processing}>Edit</button>
        <button className="delete-btn" onClick={handleDelete} disabled={processing}>Delete</button>
      </div>
    </div>
  );
};

export default TodoItem;
