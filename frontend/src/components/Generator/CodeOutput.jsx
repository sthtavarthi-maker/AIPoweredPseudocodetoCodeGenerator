function CodeOutput({ generatedCode }) {

  const copyCode = () => {
    navigator.clipboard.writeText(generatedCode);
    alert("Copied!");
  };

  return (
    <div className="code-output">

      <div className="code-header">
        <h3>Generated Code</h3>

        <button className="copy-btn" onClick={copyCode}>
          Copy
        </button>
      </div>

      <pre className="code-box">
        <code>{generatedCode}</code>
      </pre>

    </div>
  );
}

export default CodeOutput;