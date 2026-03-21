import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchBookings } from "../../../../Redux/Chart/action";
import { Line, LineChart, XAxis, YAxis, Tooltip, ResponsiveContainer } from "recharts";
import { Backdrop, CircularProgress } from "@mui/material";


const BookingCharts = () => {
  const dispatch = useDispatch();
  const { chart } = useSelector((store) => store);
  useEffect(() => {
    dispatch(fetchBookings(localStorage.getItem("jwt")));
  }, []);

  if (chart.earnings.loading) {
    return (
      <Backdrop
        sx={(theme) => ({ color: "#fff", zIndex: theme.zIndex.drawer + 1 })}
        open={true}
      // onClick={handleClose}
      >
        <CircularProgress color="inherit" />
      </Backdrop>
    );
  }
  console.log("-------- bookings", chart.bookings?.data?.daily);
  return (
    <div className=" h-[40vh] w-full">
      <ResponsiveContainer width="100%" height="100%">
        <LineChart data={chart.bookings?.data}>
          <Line type="monotone" dataKey="count" stroke="#067c06" />
          <XAxis dataKey="daily" />
          <YAxis />
          <Tooltip />

        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default BookingCharts;
