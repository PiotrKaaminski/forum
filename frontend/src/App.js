import logo from './logo.svg';
import './App.css';
import { useEffect, useState } from 'react';
import { getHello } from './api';

function App() {
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
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <p>Backend message: {helloMessage}</p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
