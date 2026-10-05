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
       </ul>

    </nav>
  );
}

export default Navbar;