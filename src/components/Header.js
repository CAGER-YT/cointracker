import React, { useState, useEffect } from "react";
import { AppBar, Toolbar, Typography, Button, Box } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import { Home, AddCircle, History, ExitToApp, Notifications, AdminPanelSettings } from "@mui/icons-material";
import api from "../api";

const Header = () => {
  const navigate = useNavigate();
  const [userRole, setUserRole] = useState(null);

  useEffect(() => {
    const fetchUserRole = async () => {
      try {
        const response = await api.get("/api/admin/user/me");
        setUserRole(response.data.role);
      } catch (error) {
        console.error("Error fetching user role:", error);
      }
    };

    fetchUserRole();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    navigate("/login");
  };

  return (
    <AppBar
      position="static"
      sx={{
        background: "rgba(0, 0, 0, 0.7)",
        backdropFilter: "blur(10px)",
        boxShadow: "0px 4px 10px rgba(255, 255, 255, 0.1)",
      }}
    >
      <Toolbar>
        <Typography variant="h5" sx={{ flexGrow: 1, fontWeight: "bold", letterSpacing: 1 }}>
          🪙 Coin Tracker
        </Typography>
        <Box>
          <Button color="inherit" component={Link} to="/" startIcon={<Home />} sx={{ mx: 1, fontSize: "16px", fontWeight: "bold" }}>
            Dashboard
          </Button>
          <Button color="inherit" component={Link} to="/add" startIcon={<AddCircle />} sx={{ mx: 1, fontSize: "16px", fontWeight: "bold" }}>
            Add Coins
          </Button>
          <Button color="inherit" component={Link} to="/history" startIcon={<History />} sx={{ mx: 1, fontSize: "16px", fontWeight: "bold" }}>
            History
          </Button>
          <Button color="inherit" component={Link} to="/notifications" startIcon={<Notifications />} sx={{ mx: 1, fontSize: "16px", fontWeight: "bold" }}>
            Notifications
          </Button>
          {userRole === "ADMIN" && (
            <Button color="inherit" component={Link} to="/admin" startIcon={<AdminPanelSettings />} sx={{ mx: 1, fontSize: "16px", fontWeight: "bold" }}>
              Admin
            </Button>
          )}
          <Button color="inherit" onClick={handleLogout} startIcon={<ExitToApp />} sx={{ mx: 1, fontSize: "16px", fontWeight: "bold" }}>
            Logout
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
