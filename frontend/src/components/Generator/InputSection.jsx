import React from "react";
import "../../styles/generator/InputSection.css";

function InputSection({
  inputType,
  setInputType,
  language,
  setLanguage,
  optimization,
  setOptimization,
  pseudo,
  setPseudo,
  generateCode,
}) {
  const descriptionExamples = [
    "Reverse an Array",
    "Binary Search",
    "Palindrome Number",
    "Find Largest Element",
    "Prime Numbers",
  ];

  const pseudocodeExamples = [
`START

Read Array

Reverse Array

Print Array

END`,
`START

Read Number

Check Palindrome

Print Result

END`,
`START

Read Array

Find Maximum

Print Maximum

END`,
  ];

  const placeholder =
    inputType === "description"
      ? "Describe your programming problem here..."
      : `START

Read Input

Process

Display Output

END`;

  return (
    <div className="input-section">

      <h2>📝 Input</h2>

      <div className="input-tabs">

        <button
          className={inputType === "description" ? "active-tab" : ""}
          onClick={() => setInputType("description")}
        >
          Problem Description
        </button>

        <button
          className={inputType === "pseudocode" ? "active-tab" : ""}
          onClick={() => setInputType("pseudocode")}
        >
          Pseudocode
        </button>

      </div>

      <div className="example-container">

        {(inputType === "description"
          ? descriptionExamples
          : pseudocodeExamples
        ).map((item, index) => (
          <button
            key={index}
            className="example-chip"
            onClick={() => setPseudo(item)}
          >
            Example {index + 1}
          </button>
        ))}

      </div>

      <textarea
        placeholder={placeholder}
        value={pseudo}
        onChange={(e) => setPseudo(e.target.value)}
      />

      <div className="options">

    <div className="option-card">

        <label>💻 Programming Language</label>

            <select
        value={language}
        onChange={(e) => setLanguage(e.target.value)}
    >
        <option>Java</option>
        <option>Python</option>
        <option>C++</option>
        <option>C</option>
        <option>C#</option>
        <option>Go</option>
        <option>Rust</option>
      </select>

    </div>

    <div className="option-card">

        <label>⚡ Solution Approach</label>

        <select
            value={optimization}
            onChange={(e) => setOptimization(e.target.value)}
        >
            <option>Optimal</option>
            <option>Brute Force</option>
        </select>

    </div>

</div>

      <button
        className="generate-btn"
        onClick={generateCode}
      >
        🚀 Generate Code
      </button>

    </div>
  );
}

export default InputSection;