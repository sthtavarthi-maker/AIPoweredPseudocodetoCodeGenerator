import { BrowserRouter, Routes, Route } from "react-router-dom";

import Landing from "./pages/Landing";
import Login from "./pages/Login";
import Register from "./pages/Register";

import Dashboard from "./pages/Dashboard";
import Home from "./pages/Home";
import Generator from "./pages/Generator";
import History from "./pages/History";
import Profile from "./pages/Profile";

function App() {

    return (

        <BrowserRouter>

            <Routes>

                <Route
                    path="/"
                    element={<Landing />}
                />

                <Route
                    path="/login"
                    element={<Login />}
                />

                <Route
                    path="/register"
                    element={<Register />}
                />

                <Route
                    path="/dashboard"
                    element={<Dashboard />}
                >

                    <Route
                        index
                        element={<Home />}
                    />

                    <Route
                        path="generator"
                        element={<Generator />}
                    />

                    <Route
                        path="history"
                        element={<History />}
                    />

                    <Route
                        path="profile"
                        element={<Profile />}
                    />

                </Route>

            </Routes>

        </BrowserRouter>

    );

}

export default App;