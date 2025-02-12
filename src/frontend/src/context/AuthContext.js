import React, { createContext, useState, useEffect, useContext } from 'react';
import { getAllStudents, login as performLogin } from '../client';
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

    const isUserHasAccessOnPage = () => {
        if (isUserAuthenticated()) {
            return getAllStudents()
                .then(res => {
                    if (res.status === 403) {
                        console.log("Access Denied");
                        return false;
                    }
                    return res.json().then(data => {
                        console.log("Access Granted:", data);
                        return true; // ✅ Return true if API call succeeds
                    });
                })
                .catch(err => {
                    console.log(err);
                    return false;
                });
        }
        return Promise.resolve(false);
    };



    const login = async (userNameAndPassword) => {
        return new Promise((resolve, reject) => {
            performLogin(userNameAndPassword).then(res => {
                const jwtToken = res.token;

                if (!jwtToken || jwtToken.split(".").length !== 3) {
                    console.error("Invalid JWT Token:", jwtToken);
                    throw new Error("Invalid JWT Token");
                }
                localStorage.setItem("access_token", jwtToken);

                const decodeToken = jwtDecode(jwtToken);
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
        isUserHasAccessOnPage,
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