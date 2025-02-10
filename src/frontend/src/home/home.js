import React from 'react';
import { Button, Checkbox, Form, Input } from 'antd';
import '../App.css';
const onFinish = (values) => {
  console.log('Success:', values);
};
const onFinishFailed = (errorInfo) => {
  console.log('Failed:', errorInfo);
};
const Home = () => {
  console.log("Rendering Home Page");
  return (

    <h1 className='sign-in-heading-style'>Top Deals</h1>
  );
};
export default Home;