function AlgorithmSelector({ algorithmType, setAlgorithmType }) {
  return (
    <div className="form-group">
      <label htmlFor="algorithm">Algorithm Type</label>

      <select
        id="algorithm"
        value={algorithmType}
        onChange={(e) => setAlgorithmType(e.target.value)}
      >
        <option value="Optimal">Optimal</option>
        <option value="Brute Force">Brute Force</option>
      </select>
    </div>
  );
}

export default AlgorithmSelector;