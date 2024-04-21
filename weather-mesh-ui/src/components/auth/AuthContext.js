import React, {createContext, useState} from 'react';

const AuthContext = createContext(null);

export const useCreateAppContext = () => {
    const [isUserAuthenticated, setIsUserAuthenticated] = useState(
        localStorage.getItem("token") !== null &&
        localStorage.getItem("role") !== 'UNAUTHORIZED'
    );

    const userLogin = (token, login, role) => {
        localStorage.setItem("token", token)
        localStorage.setItem("login", login)
        localStorage.setItem("role", role)
        setIsUserAuthenticated(true)
    };

    const userLogout = () => {
        localStorage.removeItem("token")
        localStorage.setItem("login", 'unknown')
        localStorage.setItem("role", 'UNAUTHORIZED')
        setIsUserAuthenticated(false)
    };

    const getToken = () => localStorage.getItem("token");
    const getLogin = () => localStorage.getItem("login");
    const getRole = () => localStorage.getItem("role");

    return {
        userLogin, userLogout, getToken, getLogin, getRole, isUserAuthenticated
    };
};

export const AuthProvider = ({children, ...props}) => {
    const context = useCreateAppContext(props);

    return (<AuthContext.Provider value={context}>
        {children}
    </AuthContext.Provider>);
};

export default AuthContext