import "./Hero.css";
import heroImage from "../../assets/images/hero-image.svg.svg";

function Hero() {
  return (
    <section className="hero" id="home">

      <div className="hero-content">

        <p className="hero-tag">
          🚀 AI Powered Code Generation
        </p>

        <h1>
          Transform Your
          <span> Pseudocode </span>
          Into
          <br />
          Production-Ready Code
        </h1>

        <p className="hero-description">
          Generate optimized Java, Python, C and C++ code using AI.
          Compare brute-force and optimal solutions, analyze time
          complexity, and accelerate your coding workflow.
        </p>

        <div className="hero-buttons">

          <button className="primary-btn">
            Generate Code
          </button>

          <button className="secondary-btn">
            Learn More
          </button>

        </div>

      </div>

      <div className="hero-image">

        <img src={heroImage} alt="Hero" />

      </div>

    </section>
  );
}

export default Hero;