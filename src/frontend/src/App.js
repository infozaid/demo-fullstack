import './App.css';
import { AuthProvider } from './context/AuthContext';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import Navbar from './misc/NavBar';
import PrivateRoute from './misc/PrivateRoute';
import UserPage from './user/UserPage';
import './App.css';
import Home from './home/home';
import Login from './home/login';
import AccessDenied from './error-display/AccessDenied';





function App() {
  <Route path="/userpage" element={<PrivateRoute><UserPage /></PrivateRoute>} />
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Navbar />}>
            <Route index element={<Home />} /> {/* Default route */}
            <Route path="userpage" element={<UserPage />} />
            <Route path="/userpage" element={<PrivateRoute><UserPage /></PrivateRoute>} />
            <Route path="/access-denied" element={<AccessDenied/>}/>
            <Route path="login" element={<Login />} />
            {/* <Route path="signup" element={<Signup />} />  */}
          </Route>
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
