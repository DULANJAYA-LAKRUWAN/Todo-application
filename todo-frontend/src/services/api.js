import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: { 'Content-Type': 'application/json' },
});

export function getApiErrorMessage(err, fallback = 'Something went wrong') {
  if (!err) return fallback;
  const resp = err.response?.data;
  if (resp) {
    if (typeof resp === 'string') return resp;
    if (resp.message) return resp.message;
    if (resp.error) return resp.error;
    if (resp.details) {
      if (typeof resp.details === 'string') return resp.details;
      if (typeof resp.details === 'object') return Object.values(resp.details).join('; ');
    }
    if (typeof resp === 'object') return Object.values(resp).join('; ');
  }
  return err.message || fallback;
}

export const todoAPI = {
  getAllTodos: () => api.get('/todos'),
  getTodoById: (id) => api.get(`/todos/${id}`),
  createTodo: (todo) => api.post('/todos', todo),
  updateTodo: (id, todo) => api.put(`/todos/${id}`, todo),
  deleteTodo: (id) => api.delete(`/todos/${id}`),
};

export default api;
