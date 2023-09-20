//import logo from './logo.svg';
import {useState,useEffect} from 'react'
import './App.css';
import { getAllsStudents } from './client';

function App() {

  const [students,setStudents] = useState([]);
  
  const fetchStudents = () =>
  getAllsStudents().then(res=>res.json())
  .then(data => {
       console.log(data);
       setStudents(data);
  })

  useEffect(()=>{
        console.log("Component is mounted");
        fetchStudents();
  },[]);

  if(students.length<=0){
    return <p>No Data found</p>
  }

  return students.map((student,index)=>{

    return <h1 key={index}>{student.id} {student.name} {student.gender}</h1>

  });
  
 // return <h1>{students.length}</h1>;
}

export default App;
