
import DrawerList from "../seller/drawer_list/SellerDrawerList";
import DashboardIcon from '@mui/icons-material/Dashboard';
import LogoutIcon from '@mui/icons-material/Logout';
const menu = [
    {
        name: "Dashboard",
        path: "/admin",
        icon: <DashboardIcon className="text-primary-color" />,
        activeIcon: <DashboardIcon className="text-white" />,
    }

];

const menu2 = [

    // {
    //     name: "Account",
    //     path: "/admin",
    //     icon: <AccountBoxIcon className="text-primary-color" />,
    //     activeIcon: <AccountBoxIcon className="text-white" />,
    // },
    {
        name: "Logout",
        path: "/",
        icon: <LogoutIcon className="text-primary-color" />,
        activeIcon: <LogoutIcon className="text-white" />,
    },

]



const AdminDrawerList = ({ toggleDrawer }: { toggleDrawer?: (open: boolean) => () => void }) => {

    return (
        <>
            <DrawerList toggleDrawer={toggleDrawer} menu={menu} menu2={menu2} />
        </>
    );
};

export default AdminDrawerList;
