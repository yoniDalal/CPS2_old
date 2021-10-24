import './update-customer.css'
import { Field, Formik, Form } from 'formik'
import history from "../../../../history";
import AdminService from "../../../../../../service/AdminService";
import AutenticationService from "../../../../../../service/AutenticationService";
import { values } from 'lodash';

export default function UpdateCustomer(props) {

    const handleSubmit = (values) => {
        let password = values.password;
        if (values.password === "") {
            password = props.updateCustomer.password
        }
        AdminService.updateCustomer(
            props.updateCustomer.id,
            values.firstName,
            values.lastName,
            values.email,
            password
        ).then(
            (response) => {
                alert("The customer " + response.data.firstName + " " + response.data.lastName + " with the email " + response.data.email + " was updated successfully")
                history.push("/admin/customers")
                window.location.reload()
            },
            (error) => {
                try {
                    if (error.response.data.string) {
                        alert(error.response.data.string);
                        return;
                    }
                    if (error.response) {
                        alert("Login expired, please login again.");
                        AutenticationService.logOut();
                    }
                } catch {
                    alert("Servers are down, please try again later.");
                    AutenticationService.logOut();
                }
            }
        )
    }

    const back = () => {
        if (window.confirm("Are you sure you want to leave this page?")) {
            history.push("/admin/customers")
        }
    }

    return (
        <div className="UpdateCustomer">
            <button type="submit" class="btn btn-primary my-1" title="Return" id="UpdateCustomer-return" onClick={back}>
                ←
            </button>

            <Formik

                initialValues={{ firstName: props.updateCustomer.firstName, lastName: props.updateCustomer.lastName, email: props.updateCustomer.email, password: "" }}
                onSubmit={(values, { setFieldValue }) => handleSubmit(values, { setFieldValue })}
            >
                {({ isValid, dirty, values }) => (
                    <div className="UpdateCustomer-form">
                        <h2 className="UpdateCustomer-title">Update Customer</h2><br />
                        <Form >
                            <div class="form-group">
                                <label class="control-label col-sm-2" id="UpdateCustomer-label">First Name</label>
                                <Field class="col-sm-2" id="UpdateCustomer-filed"
                                    type="name"
                                    name="firstName"
                                    placeholder="Enter your first name"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2" id="UpdateCustomer-label">Last Name</label>
                                <Field class="col-sm-2" id="UpdateCustomer-filed"
                                    type="name"
                                    name="lastName"
                                    placeholder="Enter your last name"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group">

                                <label class="control-label col-sm-2" id="UpdateCustomer-label">Email</label>
                                <Field class="col-sm-2" id="UpdateCustomer-filed"
                                    type="email"
                                    name="email"
                                    placeholder="Enter your Email"
                                    title={values.email}
                                    required={true}
                                    autocomplete={true}
                                />

                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" id="UpdateCustomer-label">Password</label>
                                <Field class="col-sm-2" id="UpdateCustomer-filed"
                                    type="password"
                                    name="password"
                                    placeholder="Enter your Password"
                                    autocomplete={true}
                                />
                            </div>
                            <div className="UpdateCustomer-seperator"></div>

                            <button type="submit" class="btn btn-primary my-1" title="Update" disabled={!dirty}>
                                Update
                        </button>

                            <div className="UpdateCustomer-seperator"></div>
                        </Form>
                    </div>
                )}
            </Formik >
        </div>
    )
}