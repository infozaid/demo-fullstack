import { useState } from 'react';
import { Layout, Menu, theme, Divider } from 'antd';
import {
    PieChartOutlined,
    UserOutlined
} from '@ant-design/icons';
import '../App.css';
import { useAuth } from '../context/AuthContext';
import { Outlet, useNavigate } from 'react-router-dom';
const { Header, Content, Footer, Sider } = Layout;

function getItem(label, key, icon, children, onClick, style) {
    return {
        key,
        icon,
        children,
        label,
        onClick,
        style,
    };
}

function Navbar() {
    const navigate = useNavigate();
    const [collapsed, setCollapsed] = useState(false);
    const { user, isUserAuthenticated, logout } = useAuth();
    const { token: { colorBgContainer } } = theme.useToken();

    const enterMenuStyle = () => isUserAuthenticated() ? { display: "none" } : { display: "block" };
    const logoutMenuStyle = () => isUserAuthenticated() ? { display: "block" } : { display: "none" };

    const adminPageStyle = () => {
        return user && user.roles.includes('ROLE_ADMIN') ? { display: "block" } : { display: "none" };
    };

    const userPageStyle = () => {
        return user && user.roles.includes('ROLE_USER') ? { display: "block" } : { display: "none" };
    };

    const items = [
        getItem('Home', '1', <PieChartOutlined />, null, () => navigate('/')),
        getItem('User Page', '2', <UserOutlined />, null, () => navigate('/UserPage'), userPageStyle()),
        getItem('Admin Page', '3', <UserOutlined />, null, () => navigate('/adminpage'), adminPageStyle()),
        getItem('Login', '4', <UserOutlined />, null, () => navigate('/login'), enterMenuStyle()),
        getItem('Sign Up', '5', <UserOutlined />, null, () => navigate('/signup'), enterMenuStyle()),
        getItem('Logout', '6', <UserOutlined />, null, () => {
            logout();
            navigate('/login'); // Redirect to login page after logout
        }, logoutMenuStyle()),
    ];

    return (
        <Layout style={{ minHeight: '100vh' }}>
            <Sider collapsible collapsed={collapsed} onCollapse={setCollapsed}>
                <div className="demo-logo-vertical" />
                <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline" items={items} />
            </Sider>
            <Layout>
                <Header style={{ padding: 0, background: colorBgContainer }} />
                <Content style={{ margin: '0px', background: colorBgContainer }}>
                    <Outlet />
                </Content>
                <Footer style={{ textAlign: 'center' }}>
                    Created By ZAID 2024.
                </Footer>
            </Layout>
        </Layout>
    );
}

export default Navbar;