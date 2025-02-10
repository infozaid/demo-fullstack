import React, { createContext, useState, useEffect, useContext } from 'react';
import { login as performLogin } from '../client';
import jwtDecode from 'jwt-decode';

const AuthContext = createContext();

function AuthProvider({ children }) {

    const [user, setUser] = useState(null);

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem('user'));
        setUser(storedUser);
    }, [])

    const getUser = () => {
        return JSON.parse(localStorage.getItem('user'));
    }


    const isUserAuthenticated = () => {
        const token = localStorage.getItem("access_token");
        if (!token) {
            return false;
        }
        const { exp: expiration } = jwtDecode(token);
        if (Date.now > expiration * 100) {
            logout()
            return false;
        }
        return true;
    }



    const login = async (userNameAndPassword) => {
        return new Promise((resolve, reject) => {
            performLogin(userNameAndPassword).then(res => {
                const jwtToken = res.headers["authorization"];
                localStorage.setItem("access_token", jwtToken);

                const decodeToken = jwtDecode(jwtToken);
                setUser({
                    username: decodeToken.sub,
                    roles: decodeToken.scopes
                })
                resolve(res);
            }).catch(err => {
                reject(err);
            })
        })
    }

    const logout = () => {
        localStorage.removeItem("access_token");
        setUser(null);
    }

    const contextValue = {
        user,
        getUser,
        isUserAuthenticated,
        login,
        logout,
    }

    return (
        <AuthContext.Provider value={contextValue}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext;

export function useAuth() {
    return useContext(AuthContext);
}

export { AuthProvider }