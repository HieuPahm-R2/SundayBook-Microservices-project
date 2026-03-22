import { useState } from 'react'
import './App.css'
import { ThemeProvider } from "@mui/material";
import { Route, Routes } from 'react-router-dom';
function App() {

  return (
    <ThemeProvider theme={greenTheme}>
      <div className="relative">


        <Routes>
          {<Route path="/salon-dashboard/*" element={<SalonDashboard />} />}
          <Route path="/login" element={<Auth />} />
          <Route path="/register" element={<Auth />} />
          <Route path="/become-partner" element={<BecomePartner />} />
          <Route path="/admin/*" element={<AdminDashboard />} />
          <Route path="*" element={<CustomerRoutes />} />
        </Routes>
      </div>
    </ThemeProvider>
  )
}

export default App
