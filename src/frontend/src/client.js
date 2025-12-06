import axios from "axios";

const checkStatus = (response) => {
  if (response.ok) {
    return response;
  }
  // convert non-2xx HTTP responses into errors:
  const error = new Error(response.statusText);
  error.response = response;
  return Promise.reject(error);
};

const getToken = () => localStorage.getItem("access_token");

const getAuthConfig = () => ({
  headers: {
    Authorization: `Bearer ${getToken()}`,
  },
});

export const getAllStudents = async () => {
  return await axios
    .get(`${process.env.REACT_APP_API_BASE_URL}/api/v1/student`, getAuthConfig())
    .then(checkStatus);
};
//   fetch("api/v1/students", {
//     headers: {
//       Authorization: `Bearer ${getToken()}`,
//     },
//   }).then(checkStatus);

export const addNewStudent = async (student) => {
  return await axios
    .post(`${process.env.REACT_APP_API_BASE_URL}/api/v1/students`, student)
    .then(checkStatus);
};

// export const addNewStudent = (student) =>
//   fetch("api/v1/students", {
//     headers: {
//       "Content-Type": "application/json",
//       Authorization: `Bearer ${getToken()}`,
//     },
//     method: "POST",
//     body: JSON.stringify(student),
//   }).then(checkStatus);

export const deleteStudent = async (studentId) => {
  return await axios.delete(
    `${process.env.REACT_APP_API_BASE_URL}/api/v1/${studentId}`,
    getAuthConfig()
  );
};

// export const deleteStudent = (studentId) =>
//   fetch(`api/v1/students/${studentId}`, {
//     method: "DELETE",
//     headers: {
//       Authorization: `Bearer ${getToken()}`,
//     },
//   }).then(checkStatus);

export const login = async (usernameAndPassword) => {
  return await axios.post(
    `${process.env.REACT_APP_API_BASE_URL}/api/v1/auth`,
    usernameAndPassword
  );
};
