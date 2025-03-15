import React, { useState, useEffect } from "react";
import api from "../api";
import { Container, Typography, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Snackbar, Alert, TextField, Dialog, DialogActions, DialogContent, DialogTitle, MenuItem, Select, InputLabel, FormControl } from "@mui/material";
import { saveAs } from "file-saver";

const AdminDashboard = () => {
  const [users, setUsers] = useState([]);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [openDialog, setOpenDialog] = useState(false);
  const [dialogType, setDialogType] = useState("create");
  const [currentUser, setCurrentUser] = useState({ username: "", email: "", role: "user" });

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await api.get("/api/admin/users");
      setUsers(response.data);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };

  const deleteUser = async (id) => {
    try {
      await api.delete(`/api/admin/users/${id}`);
      setSnackbarMessage("User deleted successfully!");
      setSnackbarOpen(true);
      fetchUsers();
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };

  const exportData = async () => {
    try {
      const response = await api.get("/api/admin/export");
      // Handle the exported data (e.g., download as a file)
      console.log("Exported data:", response.data);
    } catch (error) {
      console.error("Error exporting data:", error);
    }
  };

  const monitorSystem = async () => {
    try {
      const response = await api.get("/api/admin/monitor");
      // Handle the system status (e.g., display in a modal)
      console.log("System status:", response.data);
    } catch (error) {
      console.error("Error monitoring system:", error);
    }
  };

  const handleDialogOpen = (type, user = { username: "", email: "", role: "user" }) => {
    setDialogType(type);
    setCurrentUser(user);
    setOpenDialog(true);
  };

  const handleDialogClose = () => {
    setOpenDialog(false);
    setCurrentUser({ username: "", email: "", role: "user" });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCurrentUser((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async () => {
    try {
      if (dialogType === "create") {
        await api.post("/api/admin/users", currentUser);
        setSnackbarMessage("User created successfully!");
      } else {
        await api.put(`/api/admin/users/${currentUser.id}`, currentUser);
        setSnackbarMessage("User updated successfully!");
      }
      setSnackbarOpen(true);
      fetchUsers();
      handleDialogClose();
    } catch (error) {
      console.error(`Error ${dialogType === "create" ? "creating" : "updating"} user:`, error);
    }
  };

  const backupDataAsCSV = async () => {
    try {
      const response = await api.get("/api/admin/backup/csv");
      const blob = new Blob([response.data], { type: "text/csv" });
      saveAs(blob, "backup.csv");
    } catch (error) {
      console.error("Error backing up data as CSV:", error);
    }
  };

  const backupDataAsJSON = async () => {
    try {
      const response = await api.get("/api/admin/backup/json");
      const blob = new Blob([response.data], { type: "application/json" });
      saveAs(blob, "backup.json");
    } catch (error) {
      console.error("Error backing up data as JSON:", error);
    }
  };

  const restoreDataFromCSV = async (csvData) => {
    try {
      await api.post("/api/admin/restore/csv", csvData);
      setSnackbarMessage("Data restored from CSV successfully!");
      setSnackbarOpen(true);
      fetchUsers();
    } catch (error) {
      console.error("Error restoring data from CSV:", error);
    }
  };

  const restoreDataFromJSON = async (jsonData) => {
    try {
      await api.post("/api/admin/restore/json", jsonData);
      setSnackbarMessage("Data restored from JSON successfully!");
      setSnackbarOpen(true);
      fetchUsers();
    } catch (error) {
      console.error("Error restoring data from JSON:", error);
    }
  };

  return (
    <Container maxWidth="md">
      <Typography variant="h4" gutterBottom>
        Admin Dashboard
      </Typography>
      <Button variant="contained" color="primary" onClick={exportData} sx={{ mb: 2 }}>
        Export Data
      </Button>
      <Button variant="contained" color="secondary" onClick={monitorSystem} sx={{ mb: 2, ml: 2 }}>
        Monitor System
      </Button>
      <Button variant="contained" color="primary" onClick={() => handleDialogOpen("create")} sx={{ mb: 2, ml: 2 }}>
        Create User
      </Button>
      {/* <Button variant="contained" color="primary" onClick={backupDataAsCSV} sx={{ mb: 2, ml: 2 }}>
        Backup Data as CSV
      </Button>
      <Button variant="contained" color="primary" onClick={backupDataAsJSON} sx={{ mb: 2, ml: 2 }}>
        Backup Data as JSON
      </Button> */}
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Username</TableCell>
              <TableCell>Email</TableCell>
              <TableCell>Role</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {users.map((user) => (
              <TableRow key={user.id}>
                <TableCell>{user.id}</TableCell>
                <TableCell>{user.username}</TableCell>
                <TableCell>{user.email}</TableCell>
                <TableCell>{user.role}</TableCell>
                <TableCell>
                  <Button variant="contained" color="primary" onClick={() => handleDialogOpen("edit", user)} sx={{ mr: 1 }}>
                    Edit
                  </Button>
                  <Button variant="contained" color="secondary" onClick={() => deleteUser(user.id)}>
                    Delete
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <Dialog open={openDialog} onClose={handleDialogClose}>
        <DialogTitle>{dialogType === "create" ? "Create User" : "Edit User"}</DialogTitle>
        <DialogContent>
          <TextField
            label="Username"
            name="username"
            value={currentUser.username}
            onChange={handleInputChange}
            fullWidth
            margin="normal"
          />
          <TextField
            label="Email"
            name="email"
            value={currentUser.email}
            onChange={handleInputChange}
            fullWidth
            margin="normal"
          />
          <FormControl fullWidth margin="normal">
            <InputLabel>Role</InputLabel>
            <Select
              name="role"
              value={currentUser.role}
              onChange={handleInputChange}
            >
              <MenuItem value="USER">User</MenuItem>
              <MenuItem value="ADMIN">Admin</MenuItem>
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDialogClose} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleSubmit} color="primary">
            {dialogType === "create" ? "Create" : "Update"}
          </Button>
        </DialogActions>
      </Dialog>
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={3000}
        onClose={() => setSnackbarOpen(false)}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert severity="success" onClose={() => setSnackbarOpen(false)}>
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </Container>
  );
};

export default AdminDashboard;