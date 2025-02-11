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
    const { getUser, isUserAuthenticated } = useAuth();
    const {
        token: { colorBgContainer },
    } = theme.useToken();

    const enterMenuStyle = () => {
        return isUserAuthenticated() ? { "display": "none" } : { "display": "block" };
    }

    const adminPageStyle = () => { 
        const user = getUser();
        return user && user.roles.includes('ROLE_ADMIN') ? { "display": "block" } : { "display": "none" };
    }

    const userPageStyle = () => {
        const user = getUser();
        return user && user.roles.includes('ROLE_USER') ? { "display": "block" } : { "display": "none" };
    }


    const items = [
        getItem('Home', '1', <PieChartOutlined />, null, () => navigate('/')),
        //   getItem('Admin Page', '2', <DesktopOutlined />, null, () => navigate('/adminpage'), adminPageStyle()),
        getItem('User Page', '2', <UserOutlined />, null, () => navigate('/UserPage'), userPageStyle()),
        getItem('Login', '3', <UserOutlined />, null, () => navigate('/login'), enterMenuStyle()),
        getItem('Sign Up', '4', <UserOutlined />, null, () => navigate('/signup'), enterMenuStyle()),
        //  getItem(`Hi ${getUserName()}`, '6', null, null, null, logoutMenuStyle()),
        //  getItem('Logout', '7', <UserOutlined />, null, logout, logoutMenuStyle()),
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
                    <Outlet /> {/* This will render the content of the active route */}
                </Content>
                <Footer style={{ textAlign: 'center' }}>
                    Created By ZAID 2024.
                    <Divider>
                        <a
                            rel="noreferrer"
                            target="_blank"
                            href="https://www.bestbuy.com/site/computers-pcs/laptop-computers/abcat0502000.c?id=abcat0502000"
                        >
                            Click here to buy latest tech.
                        </a>
                    </Divider>
                </Footer>
            </Layout>
        </Layout>
    );
}
export default Navbar;