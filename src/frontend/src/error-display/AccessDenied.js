import { Link } from "react-router-dom";

const AccessDenied = () => {
  return (
    <div style={{ textAlign: "center", padding: "50px", color: "red" }}>
      <h1>403 - Access Denied</h1>
      <p>You do not have permission to view this page.</p>
      <Link to="/">Go Home</Link>
    </div>
  );
};

export default AccessDenied;