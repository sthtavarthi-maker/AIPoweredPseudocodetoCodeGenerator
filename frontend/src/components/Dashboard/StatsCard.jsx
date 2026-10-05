function StatsCard({ title, value, icon }) {
  return (
    <div className="stats-card">

      <div className="stats-icon">
        {icon}
      </div>

      <h3>{title}</h3>

      <h2>{value}</h2>

    </div>
  );
}

export default StatsCard;