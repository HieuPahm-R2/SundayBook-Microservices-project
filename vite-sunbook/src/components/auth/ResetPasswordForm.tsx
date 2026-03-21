import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";
import { TextField, Button } from "@mui/material";
import { useLocation } from "react-router-dom";


const validationSchema = Yup.object().shape({
  password: Yup.string()
    .min(6, "Mật khẩu phải có ít nhất 6 ký tự")
    .required("Không được để trống mật khẩu"),
  confirmedPassword: Yup.string()
    .oneOf([Yup.ref("password"), null], "Mật khẩu không khớp")
    .required("Không được để trống mật khẩu"),
});

function ResetPasswordForm() {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const token = searchParams.get("token");

  const initialValues = {
    password: "",
    confirmedPassword: "",
  };

  const handleSubmit = (values, { setSubmitting }) => {
    // Handle form submission here
    console.log(values);
    if (values.password === values.confirmedPassword) {
      console.log("yes its working....");
    }
    const data = { password: values.password, token };
    // dispatch(resetPassword({ navigate, data }));
    setSubmitting(false);
  };

  return (
    <>
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
        <Form className="space-y-5">
          <div className="space-y-5">
            <div>
              <Field
                as={TextField}
                name="password"
                placeholder="Password"
                type="password"
                variant="outlined"
                fullWidth
              />
              <ErrorMessage
                name="password"
                component="div"
                className="text-red-500"
              />
            </div>
            <div>
              <Field
                as={TextField}
                name="confirmedPassword"
                placeholder="Confirmed Password"
                type="password"
                variant="outlined"
                fullWidth
              />
              <ErrorMessage
                name="confirmedPassword"
                component="div"
                className="text-red-500"
              />
            </div>
          </div>
          <Button
            sx={{ padding: ".8rem 0rem" }}
            fullWidth
            type="submit"
            variant="contained"
            color="primary"
          >
            Đặt lại mật khẩu
          </Button>
        </Form>
      </Formik>
    </>
  );
}

export default ResetPasswordForm;
