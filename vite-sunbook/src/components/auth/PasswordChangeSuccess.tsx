import { Alert, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

const PasswordChangeSuccess = () => {
  const navigate = useNavigate();
  return (
    <div className="flex flex-col items-center justify-center  ">
      <div className="lg:w-[50vw] mt-20">
        <Alert severity="success">Mat khau da thay doi thanh cong!</Alert>
        <div className="flex justify-center mt-5">
          <Button
            onClick={() => navigate("/account/login")}
            fullWidth
            variant="outlined"
            sx={{ padding: ".8rem 0rem" }}
          >
            Đăng nhập lại
          </Button>
        </div>
      </div>
    </div>
  );
};

export default PasswordChangeSuccess;
