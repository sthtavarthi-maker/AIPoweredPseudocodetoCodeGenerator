import "./Footer.css";
import {
  FaGithub,
  FaLinkedin,
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

        <div className="footer-connect">

          <h3>Connect</h3>

          <a href="#">
            <FaGithub /> GitHub
          </a>

          <a href="#">
            <FaLinkedin /> LinkedIn
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