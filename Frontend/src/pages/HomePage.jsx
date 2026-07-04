import React from "react";
import {
  Typography,
  Container,
  Box,
  Button,
  Card,
  CardContent,
  useTheme,
} from "@mui/material";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import ForumIcon from "@mui/icons-material/Forum";
import ThumbsUpDownIcon from "@mui/icons-material/ThumbsUpDown";
import SecurityIcon from "@mui/icons-material/Security";
import ArrowForwardIcon from "@mui/icons-material/ArrowForward";

const HomePage = () => {
  const { token } = useAuth();
  const theme = useTheme();

  const features = [
    {
      icon: <ForumIcon sx={{ fontSize: 28, color: "#EC48990" }} />,
      bg: "linear-gradient(135deg, rgba(99, 102, 241, 0.15) 0%, rgba(99, 102, 241, 0.02) 100%)",
      borderColor: "rgba(99, 102, 241, 0.2)",
      title: "Create & Share Posts",
      description:
        "Express your thoughts and share them with an engaging, rapid-growing global community.",
    },
    {
      icon: <ThumbsUpDownIcon sx={{ fontSize: 28, color: "#EC48990" }} />,
      bg: "linear-gradient(135deg, rgba(236, 72, 153, 0.15) 0%, rgba(236, 72, 153, 0.02) 100%)",
      borderColor: "rgba(236, 72, 153, 0.2)",
      title: "Interactive Comments",
      description:
        "Engage deeply through seamless, real-time threaded discussions.",
    },
    {
      icon: <SecurityIcon sx={{ fontSize: 28, color: "#EC48990" }} />,
      bg: "linear-gradient(135deg, rgba(16, 185, 129, 0.12) 0%, rgba(16, 185, 129, 0.02) 100%)",
      borderColor: "rgba(16, 185, 129, 0.2)",
      title: "Secure Platform",
      description:
        "Your identity and interaction data are fully protected with robust, enterprise-grade distributed security structures.",
    },
  ];

  return (
    <Box sx={{ minHeight: "100vh", overflowX: "hidden", position: "relative" }}>
      {/* Dynamic Background Glows */}
      <Box
        sx={{
          position: "absolute",
          top: "-10%",
          left: "10%",
          width: "40vw",
          height: "40vw",
        }}
      />
      <Box
        sx={{
          position: "absolute",
          top: "20%",
          right: "-5%",
          width: "50vw",
          height: "50vw",
        }}
      />

      {/* Hero Section */}
      <Container maxWidth="lg">
        <Box
          sx={{
            pt: { xs: 10, md: 16 },
            pb: { xs: 8, md: 10 },
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            textAlign: "center",
            
          }}
        >
          <Typography
            variant="h1"
            sx={{
              fontWeight: 800,
              fontSize: { xs: "2.5rem", sm: "3.5rem", md: "4.5rem" },
              lineHeight: 1.15,
              mb: 3,
              letterSpacing: "-0.02em",
              background: "linear-gradient(135deg, #111827 10%, #00ffee 40%, #000864 100%)",
              backgroundClip: "text",
              WebkitBackgroundClip: "text",
              WebkitTextFillColor: "transparent",
              ...theme.applyStyles?.("dark", {
                background:
                  "linear-gradient(135deg, #FFFFFF 40%, #A5B4FC 100%)",
                backgroundClip: "text",
                WebkitBackgroundClip: "text",
              }),
            }}
          >
            Welcome to SocialHub
          </Typography>

          <Typography
            variant="h5"
            sx={{
              maxWidth: "750px",
              mb: 5,
              fontSize: { xs: "1.1rem", md: "1.25rem" },
              color: "text.secondary",
              lineHeight: 1.6,
              fontWeight: 400,
            }}
          >
            A highly scalable social platform enabling secure
            micro-interactions. Create posts, leave feedback, and engage safely
            across our distributed architecture.
          </Typography>

          <Box
            sx={{
              display: "flex",
              gap: 2,
              justifyContent: "center",
              flexWrap: "wrap",
            }}
          >
            <Button
              variant="contained"
              size="large"
              component={Link}
              to="/posts"
              endIcon={<ArrowForwardIcon />}
              sx={{
                background: "linear-gradient(135deg, #6366F1 0%, #4F46E5 100%)",
                boxShadow: "0 4px 14px 0 rgba(99, 102, 241, 0.4)",
                color: "white",
                fontWeight: 600,
                px: 4,
                py: 1.8,
                borderRadius: 1,
                textTransform: "none",
                fontSize: "1rem",
                transition: "all 0.2s ease-in-out",
                "&:hover": {
                  background:
                    "linear-gradient(135deg, #4F46E5 0%, #4338CA 100%)",
                  boxShadow: "0 6px 20px 0 rgba(99, 102, 241, 0.6)",
                  transform: "translateY(-1px)",
                },
              }}
            >
              Explore Posts
            </Button>

            {!token && (
              <Button
                variant="outlined"
                size="large"
                component={Link}
                to="/register"
                sx={{
                  color: "text.primary",
                  borderColor: "divider",
                  fontWeight: 600,
                  px: 4,
                  py: 1.8,
                  borderRadius: 1,
                  textTransform: "none",
                  fontSize: "1rem",
                  backgroundColor: "rgba(255, 255, 255, 0.4)",
                  backdropFilter: "blur(8px)",
                  transition: "all 0.2s ease-in-out",
                  "&:hover": {
                    backgroundColor: "rgba(0, 0, 0, 0.04)",
                    borderColor: "text.primary",
                  },
                }}
              >
                Join Now
              </Button>
            )}
          </Box>
        </Box>
      </Container>

      {/* Features Section */}
      <Container maxWidth="lg" sx={{ pb: 12 }}>
        <Typography
          variant="h3"
          sx={{
            fontWeight: 700,
            fontSize: { xs: "1.75rem", md: "2rem" },
            mb: 5,
            textAlign: "center",
            letterSpacing: "-0.01em",
          }}
        >
          Why Choose SocialHub?
        </Typography>

        {/* FIXED: The flexbox parent container is now OUTSIDE the loop */}
        <Box
          sx={{
            display: "flex",
            flexDirection: { xs: "column", md: "row" },
            gap: 3,
            justifyContent: "center",
            alignItems: "stretch",
            width: "100%",
          }}
        >
          {features.map((feature, index) => (
            <Card
              key={index}
              elevation={0}
              sx={{
                flex: { xs: "1 1 100%", md: "1 1 300px" },
                borderRadius: 2,
                border: "2px solid",
                borderColor: "white",
                background: feature.bg,
                backdropFilter: "blur(8px)",
                position: "relative",
                overflow: "hidden",
                "&:hover": {
                  transform: "translateY(-6px)",
                  "& .feature-icon-box": {
                    transform: "scale(1.05)",
                  },
                },
              }}
            >
              <CardContent
                sx={{
                  p: { xs: 3, sm: 4 },
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "flex-start",
                  height: "100%",
                }}
              >
                <Box
                  className="feature-icon-box"
                  sx={{
                    mb: 2.5,
                    p: 1.25,
                    borderRadius: 3,
                    background: "rgba(255, 255, 255, 0.6)",
                    border: "1.5px solid rgba(255, 255, 255, 0.8)",
                    boxShadow: "0 4px 12px rgba(0,0,0,0.02)",
                    display: "inline-flex",
                    width: "fit-content",
                    transition: "all 0.3s ease",
                    ...theme.applyStyles?.("dark", {
                      background: "rgba(30, 30, 30, 0.6)",
                      borderColor: "rgba(255, 255, 255, 0.05)",
                    }),
                  }}
                >
                  {feature.icon}
                </Box>

                <Typography
                  variant="h5"
                  component="h3"
                  sx={{
                    fontWeight: 700,
                    fontSize: "1.25rem",
                    mb: 1,
                    letterSpacing: "-0.01em",
                  }}
                >
                  {feature.title}
                </Typography>

                <Typography
                  variant="body2"
                  color="text.secondary"
                  sx={{
                    lineHeight: 1.6,
                    fontSize: "0.95rem",
                  }}
                >
                  {feature.description}
                </Typography>
              </CardContent>
            </Card>
          ))}
        </Box>
      </Container>
    </Box>
  );
};

export default HomePage;
