import fetch from "unfetch";

const checkStatus = response => {
    if (response.ok) {
        return response;
    }
    // convert non-2xx HTTP responses into errors:
    const error = new Error(response.statusText);
    error.response = response;
    return Promise.reject(error);
}

const getToken = () => localStorage.getItem("access_token");

export const getAllStudents = () =>
    fetch("api/v1/students",{
        headers: {
            'Authorization': `Bearer ${getToken()}`,
        }
    }).then(checkStatus);

export const addNewStudent = student =>
    fetch("api/v1/students", {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${getToken()}`,
        },
        method: "POST",
        body: JSON.stringify(student)
    }
    ).then(checkStatus);

export const deleteStudent = (studentId) =>
    fetch(`api/v1/students/${studentId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${getToken()}`,
        }
    }).then(checkStatus);

export const login = async (userNameAndPassword) =>
    fetch("api/v1/auth", {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(userNameAndPassword)
    }
    ).then(checkStatus)
        .then(res => res.json());

