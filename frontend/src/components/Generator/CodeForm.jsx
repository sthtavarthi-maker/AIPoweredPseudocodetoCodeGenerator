import { useState } from "react";
import "../../styles/CodeForm.css";

import LanguageSelector from "./LanguageSelector";
import AlgorithmSelector from "./AlgorithmSelector";
import GenerateButton from "./GenerateButton";
import LoadingSpinner from "./LoadingSpinner";
import CodeOutput from "./CodeOutput";

function CodeForm() {
  const [pseudocode, setPseudocode] = useState("");
  const [language, setLanguage] = useState("Java");
  const [algorithmType, setAlgorithmType] = useState("Optimal");
  const [generatedCode, setGeneratedCode] = useState("");
  const [loading, setLoading] = useState(false);

  const handleGenerate = () => {
    setLoading(true);

    setTimeout(() => {
      setGeneratedCode(`public class Main {

    public static void main(String[] args) {

        System.out.println("AI Code Generated Successfully!");

    }

}`);
      setLoading(false);
    }, 2000);
  };

  return (
    <section className="generator-section">

      <div className="generator-header">

        <h2>AI Code Generator</h2>

        <p>
          Convert pseudocode into clean, optimized code using AI.
        </p>

      </div>

      <div className="generator-container">

        {/* LEFT PANEL */}

        <div className="editor-panel">

          <h3>Pseudocode</h3>

          <textarea
            placeholder="Write your pseudocode here..."
            value={pseudocode}
            onChange={(e) => setPseudocode(e.target.value)}
          />

          <LanguageSelector
            language={language}
            setLanguage={setLanguage}
          />

          <AlgorithmSelector
            algorithmType={algorithmType}
            setAlgorithmType={setAlgorithmType}
          />

          <GenerateButton
            loading={loading}
            onGenerate={handleGenerate}
          />

        </div>

        {/* RIGHT PANEL */}

        <div className="output-panel">

          <h3>Generated Code</h3>

          {loading && <LoadingSpinner />}

          {!loading && (
            <CodeOutput generatedCode={generatedCode} />
          )}

        </div>

      </div>

      <div className="complexity-container">

        <div className="complexity-card">
          <h4>Time Complexity</h4>
          <p>O(n)</p>
        </div>

        <div className="complexity-card">
          <h4>Space Complexity</h4>
          <p>O(1)</p>
        </div>

      </div>

    </section>
  );
}

export default CodeForm;