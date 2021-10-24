import { Field, Formik, Form, ErrorMessage } from 'formik'
import './login.css'
import history from '../history';
import * as Yup from "yup";
import AutenticationService from './../../../service/AutenticationService'

const validationSchema = Yup.object({
    email: Yup.string().email('Invalid email format').required('Required'),
    password: Yup.string().required('Required')
})

export default function Login() {
    const handleSubmit = (values, { setFieldValue }) => {

        AutenticationService
            .login(
                values.email,
                values.password,
                values.type
            )
            .then(() => {
                switch (values.type) {
                    case "ADMIN":
                        history.push('/admin')
                        break;
                    case "COMPANY":
                        history.push('/company')
                        break;
                    case "CUSTOMER":
                        history.push('/customer')
                        break;

                    default:
                        break;
                }
            },
                error => {
                    if (error.response) {
                        alert(error.response.data.string)
                        console.log(error.response)
                    }

                    else
                        alert("Server is down")
                    setFieldValue('password', "")
                }
            )
    }
    return (
        <div className="login-page">
            <div className="Login-seperator"></div>
            <div className="formik">

                <Formik

                    initialValues={{ email: "", password: "", type: "ADMIN" }}
                    onSubmit={(values, { setFieldValue }) => handleSubmit(values, { setFieldValue })}
                    validationSchema={validationSchema}
                >
                    {({ isValid, dirty, values }) => (
                        <div className="Login-form">
                            <h2 className="Login-title">Welcome to the coupon system</h2><br />
                            <Form >
                                <div class="form-group">

                                    <label class="control-label col-sm-2" id="Login-label">Email</label>
                                    <Field class="col-sm-2" id="Login-filed"
                                        type="email"
                                        name="email"
                                        placeholder="Enter Email"
                                        title={values.email}
                                        required={true}
                                        autocomplete={true}
                                    />

                                </div>
                                <ErrorMessage component='div' name='email' className='alert alert-danger' />


                                <div class="form-group">
                                    <label class="control-label col-sm-2" id="Login-label">Password</label>
                                    <Field class="col-sm-2" id="Login-filed"
                                        type="password"
                                        name="password"
                                        placeholder="Enter Password"
                                        required={true}
                                        autocomplete={true}
                                    />
                                </div>
                                <ErrorMessage component='div' name='password' className='alert alert-danger' />


                                <div class="form-group">
                                    <label class="control-label col-sm-2" id="Login-label">Type</label>
                                    <Field class="col-sm-2" id="Login-filed"
                                        component="select"
                                        name="type"
                                        title={values.type}
                                        required={true}
                                        autocomplete={true}>
                                        <option value="ADMIN">Admin</option>
                                        <option value="COMPANY">Company</option>
                                        <option value="CUSTOMER">Customer</option>
                                    </Field>
                                </div >
                                <div className="Login-seperator"></div>

                                <button type="submit" class="btn btn-primary my-1" disabled={!(isValid && dirty)} title="Login">
                                    Login
                                </button>

                                <div className="Login-seperator"></div>

                            </Form>
                        </div>
                    )}
                </Formik >

            </div>
        </div>
    )

}