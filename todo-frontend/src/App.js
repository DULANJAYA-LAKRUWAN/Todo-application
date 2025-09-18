import React, { useState, useEffect, useCallback } from 'react';
import Header from './components/Header';
import TodoForm from './components/TodoForm';
import TodoList from './components/TodoList';
import { todoAPI, getApiErrorMessage } from './services/api';
import './App.css';

function App() {
  const [todos, setTodos] = useState([]);
  const [editingTodo, setEditingTodo] = useState(null);
  const [loading, setLoading] = useState(true);
  const [actionLoading, setActionLoading] = useState(false);
  const [processingId, setProcessingId] = useState(null);
  const [error, setError] = useState('');

  const fetchTodos = useCallback(async () => {
    setLoading(true);
    try {
      const res = await todoAPI.getAllTodos();
      setTodos(res.data || []);
      setError('');
    } catch (err) {
      setError(getApiErrorMessage(err, 'Failed to fetch todos.'));
      console.error(err);
    } finally { setLoading(false); }
  }, []);

  useEffect(() => { fetchTodos(); }, [fetchTodos]);

  const handleCreateTodo = async (todoData) => {
    setActionLoading(true);
    try {
      const res = await todoAPI.createTodo(todoData);
      setTodos((prev) => [...prev, res.data]);
      setError('');
    } catch (err) {
      setError(getApiErrorMessage(err, 'Failed to create todo.'));
      console.error(err);
    } finally { setActionLoading(false); }
  };

  const handleUpdateTodo = async (id, todoData) => {
    setActionLoading(true);
    setProcessingId(id);
    try {
      const res = await todoAPI.updateTodo(id, todoData);
      setTodos((prev) => prev.map((t) => (t.id === id ? res.data : t)));
      setEditingTodo(null);
      setError('');
    } catch (err) {
      setError(getApiErrorMessage(err, 'Failed to update todo.'));
      console.error(err);
    } finally {
      setActionLoading(false);
      setProcessingId(null);
    }
  };

  const handleDeleteTodo = async (id) => {
    setActionLoading(true);
    setProcessingId(id);
    try {
      await todoAPI.deleteTodo(id);
      setTodos((prev) => prev.filter((t) => t.id !== id));
      setError('');
    } catch (err) {
      setError(getApiErrorMessage(err, 'Failed to delete todo.'));
      console.error(err);
    } finally {
      setActionLoading(false);
      setProcessingId(null);
    }
  };

  const handleSubmit = (todoData) => {
    if (editingTodo) {
      handleUpdateTodo(editingTodo.id, { ...todoData });
    } else {
      handleCreateTodo(todoData);
    }
  };

  const handleCancelEdit = () => setEditingTodo(null);
  const handleEditTodo = (todo) => setEditingTodo(todo);
  const handleToggleComplete = (id, todoData) => handleUpdateTodo(id, todoData);

  if (loading) return <div className="app-loading">Loading...</div>;

  return (
    <div className="app">
      <Header />
      {error && (
        <div className="error-message">
          {error}
          <button className="dismiss-btn" onClick={() => setError('')}>×</button>
        </div>
      )}
      <div className="app-content">
        <TodoForm onSubmit={handleSubmit} editingTodo={editingTodo} onCancelEdit={handleCancelEdit} submitting={actionLoading} />
        <TodoList todos={todos} onToggleComplete={handleToggleComplete} onDelete={handleDeleteTodo} onEdit={handleEditTodo} processingId={processingId} />
      </div>
    </div>
  );
}

export default App;
