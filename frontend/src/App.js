import logo from './logo.svg';
import './App.css';
import { useEffect, useState } from 'react';
import { getHello } from './api';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

function HelloView() {
  const [helloMessage, setHelloMessage] = useState('Loading...');

  useEffect(() => {
    getHello()
      .then(message => setHelloMessage(message))
      .catch(error => {
        console.error('Error fetching hello:', error);
        setHelloMessage('Error loading message');
      });
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>Simple Hello Message</p>
        <p>Backend message: {helloMessage}</p>
      </header>
    </div>
  );
}

function DefaultView() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
        <Link to="/hello">
          <button className="App-button">Go to Hello Page</button>
        </Link>
      </header>
    </div>
  );
}

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<DefaultView />} />
        <Route path="/hello" element={<HelloView />} />
      </Routes>
    </Router>
  );
}

export default App;
