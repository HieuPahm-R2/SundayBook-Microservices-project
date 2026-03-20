import { Route, Routes } from "react-router-dom";


const SalonRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/services" element={<Services />} />
      <Route path="/services/:id" element={<Services />} />
      <Route path="/add-services" element={<ServiceForm />} />

      <Route path="/bookings" element={<BookingTable />} />

      <Route path="/account" element={<Profile />} />

      <Route path="/category" element={<Category />} />
      <Route path="/category/:id" element={<Category />} />
      <Route path="/payment" element={<Payment />} />
      <Route path="/transaction" element={<TransactionTable />} />
      <Route path="/notifications" element={<Notification />} />
    </Routes>
  );
};

export default SalonRoutes;
