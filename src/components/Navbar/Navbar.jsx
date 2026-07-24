import "./Navbar.css";
import { FaCode } from "react-icons/fa";

function Navbar() {
  return (
    <nav className="navbar">
      <div className="logo">
        <FaCode className="logo-icon" />
        <h2>Pseudocode Generator</h2>
      </div>

      <ul className="nav-links">
        <li><a href="#home">Home</a></li>
        <li><a href="#features">Features</a></li>
        <li><a href="#about">About</a></li>
        <li><a href="#contact">Contact</a></li>
      </ul>

      <button className="generate-btn">
        Generate Code
      </button>
    </nav>
  );
}

export default Navbar;