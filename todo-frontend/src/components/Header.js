// src/components/Header.js
import React from 'react';

const Header = () => {
  return (
    <header className="app-header" role="banner">
      <div className="header-inner">
        <h1>Todo Application</h1>
        <p className="tagline">Manage your tasks efficiently</p>
      </div>
    </header>
  );
};

export default Header;
