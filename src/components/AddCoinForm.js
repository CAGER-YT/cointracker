import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  Container,
  Typography,
  Paper,
  Snackbar,
  Alert,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
} from "@mui/material";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import { saveAs } from "file-saver";
import * as XLSX from "xlsx";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";
import { Height } from "@mui/icons-material";
import api from "../api"; 

function AddCoinForm() {
  const [date, setDate] = useState(new Date());
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [checkIns, setCheckIns] = useState([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedDate, setSelectedDate] = useState(null);
  const [coins, setCoins] = useState("");
  const [updateDialogOpen, setUpdateDialogOpen] = useState(false);

  useEffect(() => {
    fetchCheckIns();
  }, []);

  const fetchCheckIns = () => {
    api
      .get(`/api/coins`)
      .then((response) => setCheckIns(response.data))
      .catch((error) => console.error("Error fetching check-ins:", error));
  };

  const handleOpenDialog = (date) => {
    setSelectedDate(date);
    const formattedDate = date.toISOString().split("T")[0];
    const existingCheckIn = checkIns.find((item) => item.date === formattedDate);

    if (existingCheckIn) {
      setCoins(existingCheckIn.coins);
      setUpdateDialogOpen(true);
    } else {
      setCoins("");
      setOpenDialog(true);
    }
  };

  const handleSubmit = () => {
    if (!coins) return;
    const formattedDate = selectedDate.toISOString().split("T")[0];
    const existingCheckIn = checkIns.find((item) => item.date === formattedDate);

    if (existingCheckIn) {
      api
        .put(`/api/coins/${existingCheckIn.id}`, { date: formattedDate, coins })
        .then(() => {
          setSnackbarOpen(true);
          fetchCheckIns();
        })
        .catch((error) => console.error("Error updating check-in:", error));
      setUpdateDialogOpen(false);
    } else {
      api
        .post("/api/coins", { date: formattedDate, coins })
        .then(() => {
          setSnackbarOpen(true);
          fetchCheckIns();
        })
        .catch((error) => console.error("Error adding check-in:", error));
      setOpenDialog(false);
    }
    setCoins("");
  };

  const exportToExcel = () => {
    const ws = XLSX.utils.json_to_sheet(checkIns);
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Coins Data");
    const excelBuffer = XLSX.write(wb, { bookType: "xlsx", type: "array" });
    const data = new Blob([excelBuffer], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" });
    saveAs(data, "CoinsData.xlsx");
  };

  const exportToPDF = () => {
    const doc = new jsPDF();
    doc.setFontSize(18);
    doc.text("Coin Tracker Dashboard", 70, 15);
    
    doc.setFontSize(12);
    doc.text(`Total Check-ins: ${checkIns.length}`, 20, 30);
    const totalCoins = checkIns.reduce((acc, item) => acc + Number(item.coins), 0);
    doc.text(`Total Coins Collected: ${totalCoins}`, 20, 40);
    
    const tableColumn = ["Date", "Coins"];
    const tableRows = checkIns.map(item => [item.date, item.coins]);

    autoTable(doc, { startY: 50, head: [tableColumn], body: tableRows });

    doc.save("CoinsData.pdf");
  };

  const tileContent = ({ date, view }) => {
    if (view === "month") {
      const formattedDate = date.toISOString().split("T")[0];
      const checkIn = checkIns.find((item) => item.date === formattedDate);
      return checkIn ? <div className="checked-in">💰 {checkIn.coins}</div> : null;
    }
  };

  return (
    <Container maxWidth="sm">
      <Paper elevation={3} sx={{ padding: 3, mt: 5, borderRadius: 2 }}>
        <Typography variant="h5" gutterBottom>
          Daily Check-in Calendar
        </Typography>
        <Calendar onClickDay={handleOpenDialog} value={date} tileContent={tileContent} />
      </Paper>

      {/* Dialog for adding new check-in */}
      <Dialog open={openDialog} onClose={() => setOpenDialog(false)}>
        <DialogTitle>Enter Coins</DialogTitle>
        <DialogContent>
          <TextField
            label="Coins"
            type="number"
            value={coins}
            onChange={(e) => setCoins(e.target.value)}
            required
            fullWidth
            margin="dense"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenDialog(false)} color="secondary">Cancel</Button>
          <Button onClick={handleSubmit} color="primary" variant="contained">Submit</Button>
        </DialogActions>
      </Dialog>

      {/* Modern Confirmation Dialog for Updates */}
      <Dialog open={updateDialogOpen} onClose={() => setUpdateDialogOpen(false)}>
        <DialogTitle>Update Coins</DialogTitle>
        <DialogContent>
          <Typography variant="body1" gutterBottom>
            Coins already added for this date. Do you want to update it?
          </Typography>
          <TextField
            label="Coins"
            type="number"
            value={coins}
            onChange={(e) => setCoins(e.target.value)}
            required
            fullWidth
            margin="dense"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setUpdateDialogOpen(false)} color="secondary">Cancel</Button>
          <Button onClick={handleSubmit} color="primary" variant="contained">Update</Button>
        </DialogActions>
      </Dialog>

      <Button variant="contained" color="primary" sx={{ mt: 2, mr: 1 }} onClick={exportToExcel}>
        Export to Excel
      </Button>
      <Button variant="contained" color="secondary" sx={{ mt: 2 }} onClick={exportToPDF}>
        Export to PDF
      </Button>

      <Snackbar
        open={snackbarOpen}
        autoHideDuration={3000}
        onClose={() => setSnackbarOpen(false)}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert severity="success" onClose={() => setSnackbarOpen(false)}>
          Check-in {checkIns.find((item) => item.date === selectedDate?.toISOString().split("T")[0]) ? "updated" : "added"} successfully!
        </Alert>
      </Snackbar>
    </Container>
  );
}

export default AddCoinForm;
