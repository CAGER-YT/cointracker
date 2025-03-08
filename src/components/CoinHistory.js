import React, { useEffect, useState } from "react";
import api from "../api"; 
import { Container, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from "@mui/material";

function CoinHistory() {
  const [coinHistory, setCoinHistory] = useState([]);

  useEffect(() => {
    api.get('/api/coins')
      .then(response => setCoinHistory(response.data))
      .catch(error => console.error("Error fetching history:", error));
  }, []);

  return (
    <Container maxWidth="md" sx={{ mt: 5 }}>
      <Paper elevation={3} sx={{ padding: 3, borderRadius: 2 }}>
        <Typography variant="h5" gutterBottom>
          Coin History
        </Typography>
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell><b>Date</b></TableCell>
                <TableCell><b>Coins Earned</b></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {coinHistory.map((coin) => (
                <TableRow key={coin.id}>
                  <TableCell>{coin.date}</TableCell>
                  <TableCell>{coin.coins}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Paper>
    </Container>
  );
}

export default CoinHistory;