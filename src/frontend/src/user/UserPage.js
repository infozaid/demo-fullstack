import { useState, useEffect } from 'react';
import { deleteStudent, getAllStudents } from '../client';
import { Spin, Button, Badge, Tag, Avatar, Popconfirm, Radio } from 'antd';
import {
  UserOutlined,
  LoadingOutlined,
  PlusOutlined
} from '@ant-design/icons';
import '../App.css';
import { Table } from 'antd';
import StudentDrawerForm from '../StudentDrawerForm';
import { errorNotification, successNotification } from '../Notification';
import { useAuth } from '../context/AuthContext';
import { PropTypes } from 'prop-types';
import { useNavigate } from 'react-router-dom';




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

TheAvatar.propTypes = {
  name: PropTypes.string.isRequired, // Ensures 'name' is a required string
};

const removeStudent = (studentId, callback) => {
  deleteStudent(studentId).then(() => {
    successNotification("Student deleted", `student with ${studentId} was deleted`)
    callback();
  }).catch(err => {
    console.log(err.response);
    err.response.json().then(res => {
      debugger;
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

const UserPage = () => {

  debugger;

  const [students, setStudents] = useState([]);
  const [fetching, setFetching] = useState(true);
  const [showDrawer, setShowDrawer] = useState(false);

  
  const auth = useAuth();
  const user = auth.getUser();
  const isUser = user && user.roles ? true : false; // works when user want to access the page after logout
  const naviagte = useNavigate();

 
  useEffect(() => {
    if (isUser) {
      console.log("Component is mounted.");
      fetchStudents();
    }
  }, [isUser]);

  if (!user || !isUser) {
    return (
      <div style={{ textAlign: "center", padding: "50px", fontSize: "20px", color: "red" }}>
        <h1>🚫 Access Denied</h1>
        <p>You do not have permission to view this page.</p>
      </div>
    );
  }

  const fetchStudents = () =>
   
    getAllStudents()
      .then(res => res.json())
      .then(data => {
        console.log(data)
        setStudents(data)
        setFetching(false);

      }).catch(err => {
        debugger;
        console.log(err.response);
        err.response.json().then(res => {
          console.log(res);
          if (res.status === 403) { 
            naviagte("/access-denied")
          } else {
            errorNotification("There was an issue", `${res.message} [${res.status}] [${res.error}]`);
          }
            
        });
      }).finally(() => setFetching(false));
  




  const renderStudents = () => {

    if (fetching) {
      return <Spin indicator={antIcon} />;
    }

    if (students.length <= 0) {
      return (
        <>
          <Button
            onClick={() => setShowDrawer(!showDrawer)}
            type="primary" size="small"
            icon={<PlusOutlined />} >Add Student</Button>

          <StudentDrawerForm
            showDrawer={showDrawer}
            setShowDrawer={setShowDrawer}
            fetchStudents={fetchStudents}
          />
        </>
      );
      

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
            <Button onClick={() => setShowDrawer(!showDrawer)} type="primary" size="small" icon={<PlusOutlined />} >Add Student</Button>
          </>
        }
        pagination={{ pageSize: 50 }}
        scroll={{ y: 240 }}
        rowKey={(student) => student.id}
      />
    </>
  }

  return (
    <div className='site-layout-background' style={{ padding: 24, minHeight: 360 }}>
      {renderStudents()}
    </div>
  );

  // return <p>{students.length}</p>;
}

export default UserPage;