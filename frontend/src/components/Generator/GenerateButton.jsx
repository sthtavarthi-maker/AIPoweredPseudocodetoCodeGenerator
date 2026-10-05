function GenerateButton({ loading, onGenerate }) {
  return (
    <button
      type="button"
      className="generate-btn"
      onClick={onGenerate}
      disabled={loading}
    >
      {loading ? "Generating..." : "Generate Code"}
    </button>
  );
}

export default GenerateButton;