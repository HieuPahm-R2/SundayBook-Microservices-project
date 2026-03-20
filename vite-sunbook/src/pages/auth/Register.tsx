import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import {
  Button,
  TextField,
  Typography,
  CssBaseline,
  Container,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { registerUser } from "../Redux/Auth/action";

const initialValues = {
  fullName: "",
  email: "",
  password: "",
  role: "CUSTOMER",
};

const validationSchema = Yup.object({
  fullName: Yup.string().required("Full Name is required"),
  email: Yup.string()
    .email("Invalid email format")
    .required("Email is required"),
  password: Yup.string()
    .min(6, "Password must be at least 8 characters")
    .required("Password is required"),
  role: Yup.string().required("Type is required"),
});

const RegistrationForm = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleSubmit = (values) => {

    console.log("Form values: - ", values);
    values.username = values.email.split("@")[0]
    dispatch(registerUser({ userData: values, navigate }))
  };

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div>
        <Typography className="text-center" variant="h5">
          Đăng ký tài khoản
        </Typography>
        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={handleSubmit}
        >
          <Form>
            <Field
              as={TextField}
              variant="outlined"
              margin="normal"
              fullWidth
              label="Họ tên đầy đủ"
              name="fullName"
              id="fullName"
              autoComplete="fullName"
              helperText={<ErrorMessage name="fullName" />}
            />
            <Field
              as={TextField}
              variant="outlined"
              margin="normal"
              fullWidth
              label="Email"
              name="email"
              id="email"
              autoComplete="email"
              helperText={<ErrorMessage name="email" />}
            />
            <Field
              as={TextField}
              variant="outlined"
              margin="normal"
              fullWidth
              label="Mật khẩu"
              name="password"
              type="password"
              id="password"
              helperText={<ErrorMessage name="password" />}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              sx={{ mt: 3, py: ".8rem" }}
            >
              Đăng ký ngay
            </Button>
          </Form>
        </Formik>
        <Typography variant="body2" align="center" sx={{ mt: 3 }}>
          Đã có tài khoản ?{" "}
          <Button onClick={() => navigate("/login")}>Đăng nhập</Button>
        </Typography>
      </div>
    </Container>
  );
};

export default RegistrationForm;
