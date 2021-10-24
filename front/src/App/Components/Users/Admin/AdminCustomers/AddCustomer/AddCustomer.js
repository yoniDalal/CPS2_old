import "./add-customer.css";
import { Field, Formik, Form } from 'formik'
import history from "../../../../history";
import AdminService from "../../../../../../service/AdminService";
import AutenticationService from "../../../../../../service/AutenticationService";

export default function AddCustomer() {

    const handleSubmit = (values) => {
        AdminService.addCustomer(
            values.firstName,
            values.lastName,
            values.email,
            values.password,

        ).then(
            (response) => {
                alert("The customer " + response.data.firstName + " " + response.data.lastName + " with the email " + response.data.email + " was created successfully")
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
        <div className="AddCustomer">
            <button type="submit" class="btn btn-primary my-1" title="Return" id="AddCustomer-return" onClick={back}>
                ←
            </button>

            <Formik

                initialValues={{ firstName: "", lastName: "", email: "", password: "" }}
                onSubmit={(values, { setFieldValue }) => handleSubmit(values, { setFieldValue })}
            >
                {({ isValid, dirty, values }) => (
                    <div className="AddCustomer-form">
                        <h2 className="AddCustomer-title">Add Customer</h2><br />
                        <Form >
                            <div class="form-group">
                                <label class="control-label col-sm-2" id="AddCustomer-label">First Name</label>
                                <Field class="col-sm-2" id="AddCustomer-filed"
                                    type="name"
                                    name="firstName"
                                    placeholder="Enter your first name"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2" id="AddCustomer-label">Last Name</label>
                                <Field class="col-sm-2" id="AddCustomer-filed"
                                    type="name"
                                    name="lastName"
                                    placeholder="Enter last name"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>


                            <div class="form-group">

                                <label class="control-label col-sm-2" id="AddCustomer-label">Email</label>
                                <Field class="col-sm-2" id="AddCustomer-filed"
                                    type="email"
                                    name="email"
                                    placeholder="Enter your Email"
                                    title={values.email}
                                    required={true}
                                    autocomplete={true}
                                />

                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" id="AddCustomer-label">Password</label>
                                <Field class="col-sm-2" id="AddCustomer-filed"
                                    type="password"
                                    name="password"
                                    placeholder="Enter your Password"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>
                            <div className="AddCustomer-seperator"></div>

                            <button type="submit" class="btn btn-primary my-1" title="Add">
                                Add
                        </button>
                            <div className="AddCustomer-seperator"></div>
                        </Form>
                    </div>
                )}
            </Formik >
        </div>
    )
}