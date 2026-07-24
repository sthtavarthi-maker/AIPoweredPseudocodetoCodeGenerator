import "./Footer.css";
import {
  FaGithub,
  FaLinkedin,
  FaEnvelope,
} from "react-icons/fa";

function Footer() {
  return (
    <footer className="footer" id="footer">

      <div className="footer-container">

        <div className="footer-about">

          <h2>Pseudocode to Code Generator</h2>

          <p>
            Convert your pseudocode into optimized,
            production-ready code using Artificial Intelligence.
          </p>

        </div>

        <div className="footer-links">

          <h3>Quick Links</h3>

          <a href="#">Home</a>
          <a href="#">Features</a>
          <a href="#">About</a>
          <a href="#">Generate</a>

        </div>

        <div className="footer-contact">

          <h3>Connect</h3>

          <a href="#">
            <FaGithub /> GitHub
          </a>

          <a href="#">
            <FaLinkedin /> LinkedIn
          </a>

          <a href="#">
            <FaEnvelope /> Contact
          </a>

        </div>

      </div>

      <hr />

      <p className="copyright">
        © 2026 Pseudocode to Code Generator. All Rights Reserved.
      </p>

    </footer>
  );
}

export default Footer;