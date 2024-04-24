import React from 'react'
import {HashRouter, Navigate, Route, Routes} from 'react-router-dom'
import {AuthProvider} from './components/auth/AuthContext'
import Layout from './components/layout/Layout'
import Login from './components/page/Login'
import Weather from "./components/page/Weather";
import Home from "./components/page/Home";
import Permissions from "./components/page/circuit-breaker/Permissions";
import PermissionsEdit from "./components/page/circuit-breaker/PermissionsEdit";
import History from "./components/page/circuit-breaker/History";
import Audit from "./components/page/circuit-breaker/Audit";
import Systems from "./components/page/circuit-breaker/Systems";
import About from "./components/page/About";
import Author from "./components/page/Author";
import Links from "./components/page/Links";

function App() {
    document.body.style.backgroundColor = "black"

    return (
        <AuthProvider>
            <HashRouter>
                <Routes>
                    <Route path='/' element={<Layout page={<Home/>}/>}/>
                    <Route path='/login' element={<Layout page={<Login/>}/>}/>
                    <Route path='/weather' element={<Layout page={<Weather/>}/>}/>
                    <Route path='/permissions' element={<Layout page={<Permissions/>}/>}/>
                    <Route path='/permissions/edit' element={<Layout page={<PermissionsEdit/>}/>}/>
                    <Route path='/history' element={<Layout page={<History/>}/>}/>
                    <Route path='/systems' element={<Layout page={<Systems/>}/>}/>
                    <Route path='/audit' element={<Layout page={<Audit/>}/>}/>
                    <Route path='/about' element={<Layout page={<About/>}/>}/>
                    <Route path='/author' element={<Layout page={<Author/>}/>}/>
                    <Route path='/links' element={<Layout page={<Links/>}/>}/>
                    <Route path="*" element={<Navigate to="/"/>}/>
                </Routes>
            </HashRouter>
        </AuthProvider>
    )
}

export default App
