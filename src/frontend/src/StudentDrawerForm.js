import React, { useState } from 'react';
import { PlusOutlined,LoadingOutlined } from '@ant-design/icons';
import { Button, Col, DatePicker, Drawer, Form, Input, Row, Select, Space, Spin } from 'antd';
import {addNewStudent} from './client';
import {successNotification,errorNotification} from './Notification';

const { Option } = Select;
const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;


function StudentDrawerForm({showDrawer,setShowDrawer,fetchStudents}) {
  //const [open, setOpen] = useState(false);
  const onClose = () => setShowDrawer(false);
  const [submitting,setSubmitting]= useState(false);
//   const showDrawer = () => {
//     setOpen(true);
//   };
//   const onClose = () => {
//     setOpen(false);
//   };

  const onFinish = student =>{
       setSubmitting(true);
       console.log(JSON.stringify(student,null,2))
       addNewStudent(student).then(()=>{
                            console.log("student added");
                            onClose();
                            successNotification(
                                "Student Successfully added",
                                 `${student.name} was added to the system`)
                            fetchStudents();
                        }).catch(err =>{
                            console.log(err);
                        }).finally(()=>{
                            setSubmitting(false);
                        })
  };

  
  const onFinishFailed = errorInfo =>{
    alert(JSON.stringify(errorInfo,null,2));
};


  return (
    <>
      {/* <Button type="primary" onClick={showDrawer} icon={<PlusOutlined />}>
        New account
      </Button> */}
      <Drawer
        title="Create new student"
        width={720}
        onClose={onClose}
        visible={showDrawer}
        styles={{
          body: {
            paddingBottom: 80,
          },
        }}
        // extra={
        //   <Space>
        //     <Button onClick={onClose}>Cancel</Button>
        //     <Button onClick={onClose} type="primary">
        //       Submit
        //     </Button>
        //   </Space>
        // }
      >
        <Form layout="vertical" 
              onFinishFailed={onFinishFailed}
              onFinish={onFinish}
              hideRequiredMark>
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="name"
                label="Name"
                rules={[
                  {
                    required: true,
                    message: 'Please enter Student name',
                  },
                ]}
              >
                <Input placeholder="Please enter Student name" />
              </Form.Item>
            </Col>
            <Col span={12}>
            <Form.Item
                name="email"
                label="Email"
                rules={[
                  {
                    required: true,
                    message: 'Please enter Student email',
                  },
                ]}
              >
                <Input placeholder="Please enter Student email" />
              </Form.Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="gender"
                label="Gender"
                rules={[
                  {
                    required: true,
                    message: 'Please select a gender',
                  },
                ]}
              >
                <Select placeholder="Please select an gender">
                  <Option value="MALE">MALE</Option>
                  <Option value="FEMALE">FEMALE</Option>
                  <Option value="OTHER">OTHER</Option>
                </Select>
              </Form.Item>
            </Col>
            {/* <Col span={12}>
              <Form.Item
                name="type"
                label="Type"
                rules={[
                  {
                    required: true,
                    message: 'Please choose the type',
                  },
                ]}
              >
                <Select placeholder="Please choose the type">
                  <Option value="private">Private</Option>
                  <Option value="public">Public</Option>
                </Select>
              </Form.Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="approver"
                label="Approver"
                rules={[
                  {
                    required: true,
                    message: 'Please choose the approver',
                  },
                ]}
              >
                <Select placeholder="Please choose the approver">
                  <Option value="jack">Jack Ma</Option>
                  <Option value="tom">Tom Liu</Option>
                </Select>
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="dateTime"
                label="DateTime"
                rules={[
                  {
                    required: true,
                    message: 'Please choose the dateTime',
                  },
                ]}
              >
                <DatePicker.RangePicker
                  style={{
                    width: '100%',
                  }}
                  getPopupContainer={(trigger) => trigger.parentElement}
                />
              </Form.Item>
            </Col> */}
          </Row>
          <Row gutter={16}>
            <Col span={24}>
              <Form.Item>
                    <Button  type="primary" htmlType="submit">
                       Submit
                   </Button>
              </Form.Item>
            </Col>
          </Row>
          <Row>
               {submitting && <Spin indicator={antIcon} />}
          </Row>
        </Form>
      </Drawer>
    </>
  );
};
export default StudentDrawerForm;