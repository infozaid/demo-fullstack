import { useState, useEffect } from 'react';
import { deleteStudent, getAllStudents } from './client';
import { Breadcrumb, Layout, Menu, Spin, theme, Button, Badge, Tag, Avatar, Popconfirm, Radio, Divider } from 'antd';
import {
  DesktopOutlined,
  FileOutlined,
  PieChartOutlined,
  TeamOutlined,
  UserOutlined,
  LoadingOutlined,
  PlusOutlined
} from '@ant-design/icons';
import './App.css';
import { Table } from 'antd';
import StudentDrawerForm from './StudentDrawerForm';
import { errorNotification, successNotification } from './Notification';



function getItem(label, key, icon, children) {
  return {
    key,
    icon,
    children,
    label,
  };
}
const { Header, Content, Footer, Sider } = Layout;

const items = [
  getItem('Option 1', '1', <PieChartOutlined />),
  getItem('Option 2', '2', <DesktopOutlined />),
  getItem('User', 'sub1', <UserOutlined />, [
    getItem('Tom', '3'),
    getItem('Bill', '4'),
    getItem('Alex', '5'),
  ]),
  getItem('Team', 'sub2', <TeamOutlined />, [getItem('Team 1', '6'), getItem('Team 2', '8')]),
  getItem('Files', '9', <FileOutlined />),
];

const TheAvatar = ({ name }) => {
  let trim = name.trim();
  if (trim.length === 0) {
    return <Avatar icon={<UserOutlined />}></Avatar>
  }
  let split = trim.split(" ");
  if (split.length === 1) {
    return <Avatar>{name.charAt(0)}</Avatar>
  }

  return <Avatar>{`${name.charAt(0)}${name.length - 1}`}</Avatar>
}

const removeStudent = (studentId, callback) => {
  deleteStudent(studentId).then(() => {
    successNotification("Student deleted", `student with ${studentId} was deleted`)
    callback();
  }).catch(err => {
    console.log(err.response);
    err.response.json().then(res => {
      console.log(res);
      errorNotification("There was an issue" `${res.message} [${res.status}] [${res.error}]`)
    })
  })
};

const columns = fetchStudents => [

  {
    title: '',
    dataIndex: 'avatar',
    key: 'avatar',
    render: (text, student) =>
      <TheAvatar name={student.name} ></TheAvatar>
  },

  {
    title: 'Id',
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: 'Name',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: 'Email',
    dataIndex: 'email',
    key: 'email',
  },
  {
    title: 'Gender',
    dataIndex: 'gender',
    key: 'gender',
  },

  {
    title: 'Action',
    key: 'action',
    render: (text, student) => (
      <Radio.Group>
        <Popconfirm
          placement='topRight'
          title={`Are you sure to delete? ${student.name}`}
          onConfirm={() => removeStudent(student.id, fetchStudents)}
          okText='Yes'
          cancelText='No'>
          <Radio.Button value="small">Delete</Radio.Button>
        </Popconfirm>
        <Radio.Button value="small">Edit</Radio.Button>
      </Radio.Group>
    ),
  },

];



const antIcon = <LoadingOutlined style={{ fontSize: 24 }} Spin />;

function App() {

  const [students, setStudents] = useState([]);
  const [collapsed, setCollapsed] = useState(false);
  const [fetching, setFetching] = useState(true);
  const [showDrawer, setShowDrawer] = useState(false);
  const {
    token: { colorBgContainer },
  } = theme.useToken();


  const fetchStudents = () =>
    getAllStudents()
      .then(res => res.json())
      .then(data => {
        console.log(data)
        setStudents(data)
        setFetching(false);

      }).catch(err => {
        console.log(err.response);
        err.response.json().then(res => {
          console.log(res);
          errorNotification("There was an issue", `${res.message} [${res.status}] [${res.error}]`);
        });
      }).finally(() => setFetching(false));


  useEffect(() => {
    console.log("Component is mounted.");
    fetchStudents();
  }, []);

  const renderStudents = () => {

    if (fetching) {
      return <Spin indicator={antIcon} />;
    }

    if (students.length <= 0) {

      <>
        <Button
          onClick={() => setShowDrawer(!showDrawer)}
          type="primary" size="small"
          icon=<PlusOutlined /> >Add Student</Button>

        <StudentDrawerForm
          showDrawer={showDrawer}
          setShowDrawer={setShowDrawer}
          fetchStudents={fetchStudents}
        />
      </>


    }

    return <>
      <StudentDrawerForm
        showDrawer={showDrawer}
        setShowDrawer={setShowDrawer}
        fetchStudents={fetchStudents}
      />

      <Table
        columns={columns(fetchStudents)}
        dataSource={students}
        bordered
        style={{ minHeight: '300px' }}
        title={() =>
          <>
            <Tag>Number of students</Tag>
            <Badge count={students.length} className='site-badge-count-4' color='#f0f5ff' />
            <br /><br />
            <Button onClick={() => setShowDrawer(!showDrawer)} type="primary" size="small" icon=<PlusOutlined /> >Add Student</Button>
          </>
        }
        pagination={{ pageSize: 50 }}
        scroll={{ y: 240 }}
        rowKey={(student) => student.id}
      />
    </>
  }





  return (
    <Layout
      style={{
        minHeight: '100vh',
      }}
    >
      <Sider collapsible collapsed={collapsed} onCollapse={setCollapsed}>
        <div className="demo-logo-vertical" />
        <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline" items={items} />
      </Sider>
      <Layout>
        <Header
          style={{
            padding: 0,
            background: colorBgContainer,
          }}
        />
        <Content
          style={{
            margin: '0 16px',
          }}
        >
          <Breadcrumb
            style={{
              margin: '16px 0',
            }}
          >
            <Breadcrumb.Item>User</Breadcrumb.Item>
            <Breadcrumb.Item>Bill</Breadcrumb.Item>
          </Breadcrumb>

          <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
            {renderStudents()}
          </div>
        </Content>
        <Footer style={{ textAlign: 'center', }}>
          Created By ZAID 2024.
          <Divider>
            <a
              rel="noreferrer"
              target="_blank"
              href="https://www.bestbuy.com/site/computers-pcs/laptop-computers/abcat0502000.c?id=abcat0502000">
              Click here to buy latest tech.
            </a>
          </Divider>
        </Footer>
      </Layout>
    </Layout>
  );

  // return <p>{students.length}</p>;
}

export default App;
