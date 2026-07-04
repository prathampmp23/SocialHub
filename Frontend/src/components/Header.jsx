import React, { useState } from "react";
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Box,
  Avatar,
  Menu,
  MenuItem,
  Divider,
} from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import HomeIcon from "@mui/icons-material/Home";
import ArticleIcon from "@mui/icons-material/Article";
import LogoutIcon from "@mui/icons-material/Logout";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

const Header = () => {
  const { user, token, logout } = useAuth();
  const navigate = useNavigate();
  const [anchorEl, setAnchorEl] = useState(null);

  const handleMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
    console.log(user);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    logout();
    handleMenuClose();
    navigate("/");
  };

  return (
    <AppBar
      position="sticky"
      elevation={0}
      sx={{
        backdropFilter: "blur(5px)",
        background:
          "linear-gradient(180deg, rgba(255, 255, 255, 0) 0%, rgba(0, 0, 0, 0) 100%)",
        border: "0px solid rgba(255, 255, 255, 0.18)",
        boxShadow: "0 8px 32px rgba(0, 0, 0, 0.08)",
      }}
    >
      <Toolbar sx={{ justifyContent: "space-between" }}>
        <Typography
          variant="h5"
          component={Link}
          to="/"
          sx={{
            fontWeight: 800,
            background:
              "linear-gradient(135deg, #111827 10%, #00ffee 40%, #00053b 100%)",
            backgroundClip: "text",
            WebkitBackgroundClip: "text",
            WebkitTextFillColor: "transparent",
            textDecoration: "none",
            display: "flex",
            alignItems: "center",
            gap: 1,
          }}
        >
          SocialHub
        </Typography>

        <Box sx={{ display: "flex", alignItems: "center", gap: 3 }}>
          {token && (
            <Box sx={{ display: "flex", gap: 1 }}>
              <Button
                color="inherit"
                component={Link}
                to="/"
                startIcon={<HomeIcon />}
                sx={{
                  fontWeight: 600,
                  color: "#000000",
                  "&:hover": {
                    background: "rgba(0, 0, 0, 0.05)",
                  },
                }}
              >
                Home
              </Button>
              <Button
                color="inherit"
                component={Link}
                to="/posts"
                startIcon={<ArticleIcon />}
                sx={{
                  fontWeight: 600,
                  color: "#000000",
                  "&:hover": {
                    background: "rgba(0, 0, 0, 0.05)",
                  },
                }}
              >
                Posts
              </Button>
            </Box>
          )}

          {token ? (
            <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
              <Typography
                variant="body2"
                sx={{
                  color: "#000000",
                  fontWeight: 600,
                }}
              >
                {/* {user} */}
              </Typography>
              <Avatar
                sx={{
                  cursor: "pointer",
                  background: "#fcfcfc",
                  fontWeight: 700,
                  boxShadow: "0 4px 16px rgba(0, 0, 0, 0.2)",
                  transition: "all 0.3s ease",
                  "&:hover": {
                    boxShadow: "0 6px 24px rgba(0, 0, 0, 0.3)",
                    transform: "scale(1.05)",
                  },
                }}
                onClick={handleMenuOpen}
              >
                <AccountCircleIcon sx={{ color: "black", fontSize: 32 }} />
              </Avatar>
              <Menu
                anchorEl={anchorEl}
                open={Boolean(anchorEl)}
                onClose={handleMenuClose}
                PaperProps={{
                  sx: {
                    backdropFilter: "blur(20px)",
                    background:
                      "linear-gradient(135deg, rgba(255, 255, 255, 0.25) 0%, rgba(255, 255, 255, 0.15) 100%)",
                    border: "1px solid rgba(255, 255, 255, 0.2)",
                    boxShadow: "0 8px 32px rgba(0, 0, 0, 0.1)",
                  },
                }}
              >
                <MenuItem disabled>
                  <b>{user}</b>
                </MenuItem>
                <Divider sx={{ opacity: 0.2 }} />
                <MenuItem
                  onClick={handleLogout}
                  sx={{ color: "error.main", fontWeight: 600 }}
                >
                  <LogoutIcon sx={{ mr: 1 }} /> Logout
                </MenuItem>
              </Menu>
            </Box>
          ) : (
            <Box sx={{ display: "flex", gap: 1 }}>
              <Button
                color="inherit"
                component={Link}
                to="/login"
                variant="outlined"
                sx={{
                  borderColor: "#000000",
                  color: "#000000",
                  fontWeight: 600,
                }}
              >
                Login
              </Button>
              <Button
                color="inherit"
                component={Link}
                to="/register"
                variant="contained"
                sx={{
                  background: "#000000",
                  color: "white",
                  fontWeight: 600,
                  "&:hover": {
                    background: "#101010",
                  },
                }}
              >
                Register
              </Button>
            </Box>
          )}
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
