import React from 'react';
import TodoItem from './TodoItem';

const TodoList = ({ todos = [], onToggleComplete, onDelete, onEdit, processingId = null }) => {
  if (!todos || todos.length === 0) return <div className="todo-list empty"><p>No todos yet. Add your first todo!</p></div>;

  return (
    <div className="todo-list">
      <h2>Your Todos ({todos.length})</h2>
      <div className="todos-container">
        {todos.map((todo) => (
          <TodoItem
            key={todo.id}
            todo={todo}
            onToggleComplete={onToggleComplete}
            onDelete={onDelete}
            onEdit={onEdit}
            processing={processingId === todo.id}
          />
        ))}
      </div>
    </div>
  );
};

export default TodoList;
