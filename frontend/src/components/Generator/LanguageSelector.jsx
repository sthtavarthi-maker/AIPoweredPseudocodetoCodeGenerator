function LanguageSelector({ language, setLanguage }) {
  return (
    <div className="form-group">
      <label htmlFor="language">Programming Language</label>

      <select
        id="language"
        value={language}
        onChange={(e) => setLanguage(e.target.value)}
      >
        <option value="Java">Java</option>
        <option value="Python">Python</option>
        <option value="C">C</option>
        <option value="C++">C++</option>
      </select>
    </div>
  );
}

export default LanguageSelector;