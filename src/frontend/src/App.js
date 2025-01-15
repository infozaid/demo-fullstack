import './App.css';
import { AuthProvider } from './context/AuthContext';
import { Navigate, Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import Sider from 'antd/es/layout/Sider';
import { Content } from 'antd/es/layout/layout';
import Navbar from './misc/NavBar';
import PrivateRoute from './misc/PrivateRoute';
import UserPage from './user/UserPage';
import './App.css';
import Home from './home/home';
import { Layout } from 'antd';
import Login from './home/login';





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
             <Route path="login" element={<Login />} />
            {/* <Route path="signup" element={<Signup />} />  */}
          </Route>
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
