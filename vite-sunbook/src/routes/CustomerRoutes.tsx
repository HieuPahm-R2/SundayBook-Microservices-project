import { Route, Routes } from 'react-router-dom'


const CustomerRoutes = () => {
  return (
    <>
      <Navbar />

      <div className='pb-20 min-h-[90vh] mt-[5rem]'>
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/salon/:id' element={<SalonDetails />} />
          <Route path='/bookings' element={<Bookings />} />
          <Route path='/search' element={<SearchSalon />} />
          <Route path='/notifications' element={<Notification type={"USER"} />} />
          <Route path='/payment-success/:id' element={<PaymentSuccessHandler />} />
          {/* payment-success */}
          <Route path='*' element={<NotFound />} />
        </Routes>

      </div>

      <Footer />
    </>
  )
}

export default CustomerRoutes