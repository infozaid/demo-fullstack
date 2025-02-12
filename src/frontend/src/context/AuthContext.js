import React, { createContext, useState, useEffect, useContext } from 'react';
import { login as performLogin } from '../client';
import jwtDecode from 'jwt-decode';

const AuthContext = createContext();

function AuthProvider({ children }) {

    const [user, setUser] = useState(null);

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem('user'));
        if (storedUser) {
            setUser(storedUser);
        }

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
                const token = res.token;

                console.log("Extracted Token: ", token)

                if (!token || token.split(".").length !== 3) {
                    console.error("Invalid JWT Token:", token);
                    throw new Error("Invalid JWT Token");
                }
                localStorage.setItem("access_token", token);

                const decodeToken = jwtDecode(token);
                const userData = {
                    roles: decodeToken.scopes,
                    username: decodeToken.sub
                };
                localStorage.setItem('user', JSON.stringify(userData));
                setUser(userData);
                resolve(res);
            }).catch(err => {
                reject(err);
            })
        })
    }

    const logout = () => {
        localStorage.removeItem("access_token");
        localStorage.removeItem("user");
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