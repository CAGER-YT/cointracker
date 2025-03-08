import React from "react";
import { AppBar, Toolbar, Typography, Button, Box } from "@mui/material";
import { Link } from "react-router-dom";
import { Home, AddCircle, History } from "@mui/icons-material"; // Icons

const Header = () => {
  return (
    <AppBar 
      position="static" 
      sx={{ 
        background: "rgba(0, 0, 0, 0.7)", // Glassmorphism effect
        backdropFilter: "blur(10px)", 
        boxShadow: "0px 4px 10px rgba(255, 255, 255, 0.1)"
      }}
    >
      <Toolbar>
        {/* Logo / Title */}
        <Typography 
          variant="h5" 
          sx={{ flexGrow: 1, fontWeight: "bold", letterSpacing: 1 }}
        >
          🪙 Coin Tracker
        </Typography>

        {/* Navigation Buttons */}
        <Box>
          <Button 
            color="inherit" 
            component={Link} 
            to="/"
            startIcon={<Home />} 
            sx={{ mx: 1, fontSize: "16px", fontWeight: "bold" }}
          >
            Dashboard
          </Button>

          <Button 
            color="inherit" 
            component={Link} 
            to="/add"
            startIcon={<AddCircle />} 
            sx={{ mx: 1, fontSize: "16px", fontWeight: "bold" }}
          >
            Add Coins
          </Button>

          <Button 
            color="inherit" 
            component={Link} 
            to="/history"
            startIcon={<History />} 
            sx={{ mx: 1, fontSize: "16px", fontWeight: "bold" }}
          >
            History
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
