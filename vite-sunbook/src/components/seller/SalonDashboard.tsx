import { useEffect } from "react";

import SalonRoutes from "../../routes/SalonRoutes";
import { useDispatch } from "react-redux";
import SalonDrawerList from "../admin/SalonDrawerList";
import Navbar from "./navbar/Navbar";
import { fetchSalonByOwner } from "../../Redux/Salon/action";
import { getSalonReport } from "../../Redux/Booking/action";

const SalonDashboard = () => {
  const dispatch = useDispatch<any>();
  // const {}
  useEffect(() => {
    dispatch(fetchSalonByOwner(localStorage.getItem("jwt")));
    dispatch(getSalonReport(localStorage.getItem("jwt")))
  }, []);


  return (
    <div className="min-h-screen">
      <Navbar DrawerList={SalonDrawerList} />
      <section className="lg:flex lg:h-[90vh]">
        <div className="hidden lg:block h-full">
          <SalonDrawerList />
        </div>
        <div className="p-10 w-full lg:w-[80%]  overflow-y-auto">
          <SalonRoutes />
        </div>
      </section>
    </div>
  );
};

export default SalonDashboard;
