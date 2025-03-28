import React, { useEffect, useState } from "react";
import { Container, Grid, Card, CardContent, Typography, LinearProgress, Box } from "@mui/material";
import axios from "axios";
import api from "../api"; 

const subGoals = [
  { coins: 500000, reward: "Apple Watch 9", image: "/images/apple-watch-9.jpg" },
  { coins: 600000, reward: "Sony PS5", image: "/images/sony-ps5.jpg" },
  { coins: 1000000, reward: "iPhone 15 Pro", image: "/images/iphone-15-pro.jpg" },
];

const Dashboard = () => {
  const [coins, setCoins] = useState(0);
  const [averageDailyEarnings, setAverageDailyEarnings] = useState(1000); // Example default daily rate
  const totalCoinsGoal = 1000000; // Example goal

  useEffect(() => {
    api.get(`/api/coins`)
      .then(response => {
        const total = response.data.reduce((acc, coin) => acc + coin.coins, 0);
        setCoins(total);
      })
      .catch(error => console.error("Error fetching coin data:", error));
  }, []);

  const remainingCoins = totalCoinsGoal - coins;
  const progress = (coins / totalCoinsGoal) * 100;
  const estimatedDays = averageDailyEarnings > 0 ? Math.ceil(remainingCoins / averageDailyEarnings) : "N/A";

  return (
    <Container maxWidth="md" sx={{ mt: 5 }}>
      <Typography variant="h4" textAlign="center" gutterBottom>
        Coin Tracker Dashboard
      </Typography>
      <Grid container spacing={3}>
        <Grid item xs={12} sm={4}>
          <Card sx={{ bgcolor: "#1e1e1e", color: "#fff" }}>
            <CardContent>
              <Typography variant="h6">Total Coins Earned</Typography>
              <Typography variant="h4">{coins.toLocaleString()}</Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} sm={4}>
          <Card sx={{ bgcolor: "#2e7d32", color: "#fff" }}>
            <CardContent>
              <Typography variant="h6">Goal</Typography>
              <Typography variant="h4">{totalCoinsGoal.toLocaleString()}</Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} sm={4}>
          <Card sx={{ bgcolor: "#d32f2f", color: "#fff" }}>
            <CardContent>
              <Typography variant="h6">Remaining to Earn</Typography>
              <Typography variant="h4">{remainingCoins.toLocaleString()}</Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
      
      <Box sx={{ mt: 3 }}>
        <Typography variant="h6" gutterBottom>Progress</Typography>
        <LinearProgress variant="determinate" value={progress} sx={{ height: 10, borderRadius: 5 }} />
        <Typography textAlign="center" mt={1}>{progress.toFixed(2)}% Completed</Typography>
        <Typography textAlign="center" mt={1} variant="h6" color="textSecondary">
          Estimated Days to Complete: {estimatedDays}
        </Typography>
      </Box>

      <Box sx={{ mt: 5 }}>

        <Grid container spacing={3}>
          {subGoals.map((goal, index) => (
            <Grid item xs={12} sm={4} key={index}>
              <Card sx={{ bgcolor: "#1976d2", color: "#fff" }}>
                <CardContent>
                  <img
                    src={goal.image}
                    alt={goal.reward}
                    style={{ width: "100%", height: "150px", objectFit: "cover", borderRadius: "8px" }}
                  />
                  <Typography variant="h6" sx={{ mt: 2 }}>{goal.reward}</Typography>
                  <Typography variant="body1">Coins Required: {goal.coins.toLocaleString()}</Typography>
                  <Typography variant="body2" color={coins >= goal.coins ? "success.main" : "error.main"}>
                    {coins >= goal.coins ? "Achieved" : "Not Achieved"}
                  </Typography>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Box>
    </Container>
  );
};

export default Dashboard;
