import React from 'react'
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import { Toaster } from 'react-hot-toast';
import Home from './Pages/Home/Home.jsx';
import NotFound from './Pages/NotFound/NotFound.jsx';
import Success from './Pages/Success/Success.jsx';
import Login from './Pages/Login/Login.jsx';
import Register from './Pages/Register/Register.jsx';
import TableManagement from './Pages/Admin/TableManagement.jsx';
import RequireRole from './components/RequireRole.jsx';
import { AuthProvider } from './context/AuthContext.jsx';
import './App.css'

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path='/' element={<Home/>}/>
          <Route path='/login' element={<Login/>}/>
          <Route path='/register' element={<Register/>}/>
          <Route
            path='/admin/tables'
            element={
              <RequireRole roles={['OWNER', 'EMPLOYEE']}>
                <TableManagement/>
              </RequireRole>
            }
          />
          <Route path='/success' element={<Success/>}/>
          <Route path='*' element={<NotFound/>}/>
        </Routes>
        <Toaster/>
      </Router>
    </AuthProvider>
  )
}

export default App
