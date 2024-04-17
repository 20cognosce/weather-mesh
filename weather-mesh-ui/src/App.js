import React from 'react'
import {HashRouter, Navigate, Route, Routes} from 'react-router-dom'
import {AuthProvider, useAuth} from './components/auth/AuthContext'
import Home from './components/layout/Home'
import Login from './components/auth/Login'
import Signup from './components/auth/Signup'
import AdminPage from './components/admin/AdminPage'
import AdvertsPage from './components/adverts/AdvertsPage'
import AdvertsCreatePage from "./components/adverts/AdvertsCreatePage";

function App() {
    document.body.style.backgroundColor = "black"

    return (
        <AuthProvider>
            <HashRouter>
                <Routes>
                    <Route path='/' element={
                        <Home/>
                    }/>

                    <Route path='/login' element={
                        <Home page={<Login/>}/>
                    }/>

                    <Route path='/signup' element={<Signup/>}/>
                    <Route path="/admin" element={<PrivateRoute><AdminPage/></PrivateRoute>}/>
                    <Route path="/adverts" element={<PrivateRoute><AdvertsPage/></PrivateRoute>}/>
                    <Route path="/adverts/new" element={<PrivateRoute><AdvertsCreatePage/></PrivateRoute>}/>
                    <Route path="*" element={<Navigate to="/"/>}/>
                </Routes>
            </HashRouter>
        </AuthProvider>
    )
}

function PrivateRoute({children}) {
    const {userIsAuthenticated} = useAuth()
    return userIsAuthenticated() ? children : <Navigate to="/login"/>
}

export default App
