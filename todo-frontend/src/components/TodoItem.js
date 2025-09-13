// src/components/TodoItem.js
import React from 'react';

const TodoItem = ({ todo, onToggleComplete, onDelete, onEdit }) => {
  const handleToggle = () => {
    onToggleComplete(todo.id, { ...todo, completed: !todo.completed });
  };

  const handleDelete = () => {
    onDelete(todo.id);
  };

  const handleEdit = () => {
    onEdit(todo);
  };

  return (
    <div className={`todo-item ${todo.completed ? 'completed' : ''}`}>
      <div className="todo-content">
        <h3>{todo.title}</h3>
        <p>{todo.description}</p>
        <small>
          Created: {new Date(todo.createdAt).toLocaleString()}
          {todo.updatedAt !== todo.createdAt && 
            ` | Updated: ${new Date(todo.updatedAt).toLocaleString()}`
          }
        </small>
      </div>
      <div className="todo-actions">
        <button 
          className={`toggle-btn ${todo.completed ? 'completed' : ''}`}
          onClick={handleToggle}
        >
          {todo.completed ? 'Mark Incomplete' : 'Mark Complete'}
        </button>
        <button className="edit-btn" onClick={handleEdit}>
          Edit
        </button>
        <button className="delete-btn" onClick={handleDelete}>
          Delete
        </button>
      </div>
    </div>
  );
};

export default TodoItem;