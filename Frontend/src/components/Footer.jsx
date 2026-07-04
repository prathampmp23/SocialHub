import React from "react";
import { Typography, Container, Box, Grid, Link } from "@mui/material";
import FacebookIcon from "@mui/icons-material/Facebook";
import TwitterIcon from "@mui/icons-material/Twitter";
import LinkedInIcon from "@mui/icons-material/LinkedIn";

const Footer = () => {
  return (
    <Box
      component="footer"
      sx={{
        py: 4,
        px: 2,
        mt: "auto",
        backdropFilter: "blur(20px)",
        border: "1px solid rgba(255, 255, 255, 0.18)",
        boxShadow: "0 -8px 32px rgba(0, 0, 0, 0.05)",
      }}
    >
      <Container maxWidth="lg">
        <Grid container spacing={4} sx={{ mb: 4 }}>
          <Grid item xs={12} sm={6} md={3}>
            <Typography
              variant="h6"
              sx={{
                fontWeight: 700,
                mb: 2,
                background: "linear-gradient(135deg, #000000 0%, #000000 100%)",
                backgroundClip: "text",
                WebkitBackgroundClip: "text",
                WebkitTextFillColor: "transparent",
              }}
            >
              SocialHub
            </Typography>
            <Typography
              variant="body2"
              sx={{
                opacity: 0.7,
                lineHeight: 1.7,
              }}
            >
              Connect, share, and engage with our secure social platform built
              for meaningful interactions.
            </Typography>
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Typography
              variant="h6"
              sx={{ fontWeight: 700, mb: 2, color: "#000000" }}
            >
              Product
            </Typography>
            <Typography variant="body2" sx={{ opacity: 0.7, mb: 1 }}>
              <Link
                href="#"
                sx={{
                  color: "inherit",
                  textDecoration: "none",
                  "&:hover": { opacity: 1 },
                }}
              >
                Features
              </Link>
            </Typography>
            <Typography variant="body2" sx={{ opacity: 0.7 }}>
              <Link
                href="#"
                sx={{
                  color: "inherit",
                  textDecoration: "none",
                  "&:hover": { opacity: 1 },
                }}
              >
                Pricing
              </Link>
            </Typography>
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Typography
              variant="h6"
              sx={{ fontWeight: 700, mb: 2, color: "#000000" }}
            >
              Company
            </Typography>
            <Typography variant="body2" sx={{ opacity: 0.7, mb: 1 }}>
              <Link
                href="#"
                sx={{
                  color: "inherit",
                  textDecoration: "none",
                  "&:hover": { opacity: 1 },
                }}
              >
                About
              </Link>
            </Typography>
            <Typography variant="body2" sx={{ opacity: 0.7 }}>
              <Link
                href="#"
                sx={{
                  color: "inherit",
                  textDecoration: "none",
                  "&:hover": { opacity: 1 },
                }}
              >
                Contact
              </Link>
            </Typography>
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Typography
              variant="h6"
              sx={{ fontWeight: 700, mb: 2, color: "#000000" }}
            >
              Follow Us
            </Typography>
            <Box sx={{ display: "flex", gap: 1.5 }}>
              <Box
                sx={{
                  p: 1,
                  borderRadius: "50%",
                  backdropFilter: "blur(10px)",
                  cursor: "pointer",
                  transition: "all 0.3s ease",
                }}
              >
                <FacebookIcon sx={{ fontSize: "1.2rem", color: "#000000" }} />
              </Box>
              <Box
                sx={{
                  p: 1,
                  borderRadius: "50%",
                  backdropFilter: "blur(10px)",
                  cursor: "pointer",
                  transition: "all 0.3s ease",
                  "&:hover": {
                    boxShadow: "0 4px 12px rgba(236, 72, 153, 0.2)",
                  },
                }}
              >
                <TwitterIcon sx={{ fontSize: "1.2rem", color: "#000000" }} />
              </Box>
              <Box
                sx={{
                  p: 1,
                  borderRadius: "50%",
                  backdropFilter: "blur(10px)",
                  cursor: "pointer",
                  transition: "all 0.3s ease",
                }}
              >
                <LinkedInIcon sx={{ fontSize: "1.2rem", color: "#000000" }} />
              </Box>
            </Box>
          </Grid>
        </Grid>
        <Box
          sx={{
            borderTop: "1px solid rgba(255, 255, 255, 0.1)",
            pt: 3,
            textAlign: "center",
          }}
        >
          <Typography variant="body2" sx={{ opacity: 0.7 }}>
            © {new Date().getFullYear()} SocialHub. All rights reserved. |
            Privacy Policy | Terms of Service
          </Typography>
        </Box>
      </Container>
    </Box>
  );
};

export default Footer;
