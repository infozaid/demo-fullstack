import React, { useState } from "react";
import { Button, Checkbox, Form, Input, Spin } from "antd";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { errorNotification } from "../Notification";
import { LoadingOutlined } from "@ant-design/icons";

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;
const Login = () => {
  const { login } = useAuth();
  const navigate = useNavigate();

  const [submitting, setSubmitting] = useState(false);

  const onFinish = (values) => {
    setSubmitting(true);

    login(values)
      .then((res) => {
        debugger;
        navigate("/");
        console.log("Successfully logged in");
      })
      .catch((err) => {
        debugger;
        const errorResponse = err.response.data;
        const status = err.response.status;
        const message =
          errorResponse.debugMessage || errorResponse.message || err.message;
        if (err.message === "Internal Server Error") {
          errorNotification(
            "Our Server is under construction Please try later",
            `${message} [${status}]`,
            "bottomLeft"
          );
        } else {
          errorNotification(
            "There was an issue",
            `${message} [${status}]`,
            "bottomLeft"
          );
        }
      })
      .finally(() => {
        setSubmitting(false);
      });
  };

  const onFinishFailed = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <div className="login-form-style">
      <h1 className="sign-in-heading-style">Sign In</h1>
      <Form
        name="basic"
        labelCol={{
          span: 8,
        }}
        wrapperCol={{
          span: 16,
        }}
        style={{
          maxWidth: 600,
        }}
        initialValues={{
          remember: true,
        }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
        <Form.Item
          label="Username"
          name="username"
          rules={[
            {
              required: true,
              message: "Please input your username!",
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Password"
          name="password"
          rules={[
            {
              required: true,
              message: "Please input your password!",
            },
          ]}
        >
          <Input.Password />
        </Form.Item>

        <Form.Item
          name="remember"
          valuePropName="checked"
          className="checkbox-position"
        >
          <Checkbox>Remember me</Checkbox>
        </Form.Item>

        <Form.Item label={null}>
          <Button
            type="primary"
            htmlType="submit"
            className="login-button-position"
            disabled={submitting}
          >
            {submitting ? (
              <>
                <Spin indicator={antIcon} style={{ marginRight: 8 }} />{" "}
                Submitting...
              </>
            ) : (
              "Submit"
            )}
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};
export default Login;
