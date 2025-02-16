import React, { useState } from 'react';
import { LoadingOutlined } from '@ant-design/icons';
import { Button, Col, Drawer, Form, Input, Row, Select, Spin } from 'antd';
import { addNewStudent } from './client';
import { successNotification, errorNotification } from './Notification';

const { Option } = Select;

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;


function StudentDrawerForm({ showDrawer, setShowDrawer, fetchStudents }) {

  const onClose = () => setShowDrawer(false);
  const [submitting, setSubmitting] = useState(false);

  const onFinish = student => {
    setSubmitting(true);
    console.log(JSON.stringify(student, null, 2))
    addNewStudent(student).then(() => {
      console.log("student added");
      onClose();
      successNotification(
        "Student Successfully added",
        `${student.name} was added to the system`);
      fetchStudents();
    }).catch(err => {
      console.log(err.response);
      err.response.json().then(res => {
        console.log(res);
        errorNotification(
          "There was an issue",
          `${res.message} [${res.status}] [${res.error}]`,
          "bottomLeft");
      });
    }).finally(() => {
      setSubmitting(false);
    })

  };


  const onFinishFailed = errorInfo => {
    alert(JSON.stringify(errorInfo, null, 2));
  };


  return (
    <>
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
          </Row>
          <Row gutter={16}>
            <Col span={24}>
              <Form.Item>
                <Button type="primary" htmlType="submit">
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