// src/services/api.js
import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const todoAPI = {
  getAllTodos: () => api.get('/todos'),
  getTodoById: (id) => api.get(`/todos/${id}`),
  createTodo: (todo) => api.post('/todos', todo),
  updateTodo: (id, todo) => api.put(`/todos/${id}`, todo),
  deleteTodo: (id) => api.delete(`/todos/${id}`),
};

export default api;