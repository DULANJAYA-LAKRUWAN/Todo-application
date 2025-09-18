import React, { useState, useEffect } from 'react';

const TodoForm = ({ onSubmit, editingTodo, onCancelEdit, submitting = false }) => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (editingTodo) {
      setTitle(editingTodo.title || '');
      setDescription(editingTodo.description || '');
      setErrors({});
    } else {
      setTitle('');
      setDescription('');
      setErrors({});
    }
  }, [editingTodo]);

  const validateForm = () => {
    const newErrors = {};
    const t = title?.trim() || '';
    if (!t) newErrors.title = 'Title is required';
    if (t.length > 100) newErrors.title = 'Title must be less than 100 characters';
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    onSubmit?.({
      title: title.trim(),
      description: description.trim(),
      completed: editingTodo ? !!editingTodo.completed : false,
    });

    if (!editingTodo) {
      setTitle('');
      setDescription('');
    }
  };

  const handleCancel = () => {
    setTitle('');
    setDescription('');
    setErrors({});
    onCancelEdit?.();
  };

  return (
    <form className="todo-form" onSubmit={handleSubmit} noValidate>
      <h2>{editingTodo ? 'Edit Todo' : 'Add New Todo'}</h2>
      <div className="form-group">
        <label htmlFor="title">Title *</label>
        <input
          id="title"
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          className={errors.title ? 'error' : ''}
          maxLength={100}
          disabled={submitting}
        />
        {errors.title && <div className="error-text" role="alert">{errors.title}</div>}
      </div>
      <div className="form-group">
        <label htmlFor="description">Description</label>
        <textarea
          id="description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          rows="3"
          disabled={submitting}
        />
      </div>
      <div className="form-actions">
        <button type="submit" className="submit-btn" disabled={submitting}>
          {submitting ? (editingTodo ? 'Updating…' : 'Adding…') : (editingTodo ? 'Update Todo' : 'Add Todo')}
        </button>
        {editingTodo && (
          <button type="button" className="cancel-btn" onClick={handleCancel} disabled={submitting}>
            Cancel
          </button>
        )}
      </div>
    </form>
  );
};

export default TodoForm;
