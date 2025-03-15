import React, { useState, useEffect } from "react";
import api from "../api";
import { Container, TextField, Button, Typography, Paper, Snackbar, Alert } from "@mui/material";

const NotificationPreferences = () => {
  const [preferences, setPreferences] = useState({});
  const [snackbarOpen, setSnackbarOpen] = useState(false);

  useEffect(() => {
    fetchPreferences();
  }, []);

  const fetchPreferences = async () => {
    try {
      const response = await api.get("/api/notifications");
      setPreferences(response.data);
    } catch (error) {
      console.error("Error fetching preferences:", error);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPreferences((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async () => {
    try {
      await api.post("/api/notifications", preferences);
      setSnackbarOpen(true);
    } catch (error) {
      console.error("Error updating preferences:", error);
    }
  };

  return (
    <Container maxWidth="sm">
      <Paper elevation={3} sx={{ padding: 3, mt: 5, borderRadius: 2 }}>
        <Typography variant="h5" gutterBottom>
          Notification Preferences
        </Typography>
        <TextField
          label="Email Notifications"
          name="emailNotifications"
          value={preferences.emailNotifications || ""}
          onChange={handleChange}
          fullWidth
          margin="normal"
        />
        <TextField
          label="SMS Notifications"
          name="smsNotifications"
          value={preferences.smsNotifications || ""}
          onChange={handleChange}
          fullWidth
          margin="normal"
        />
        <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ mt: 2 }}>
          Save Preferences
        </Button>
      </Paper>
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={3000}
        onClose={() => setSnackbarOpen(false)}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert severity="success" onClose={() => setSnackbarOpen(false)}>
          Preferences updated successfully!
        </Alert>
      </Snackbar>
    </Container>
  );
};

export default NotificationPreferences;