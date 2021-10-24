import "./add-company.css";
import { Field, Formik, Form } from 'formik'
import history from "../../../../history";
import AdminService from "../../../../../../service/AdminService";
import AutenticationService from "../../../../../../service/AutenticationService";

export default function AddCompany() {

    const handleSubmit = (values) => {
        AdminService.addCompany(
            values.name,
            values.email,
            values.password
        ).then(
            (response) => {
                alert("The company " + response.data.name + " with the email " + response.data.email + " was created successfully")
                history.push("/admin/companies")
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
            history.push("/admin/companies")
        }
    }

    return (
        <div className="AddCompany">
            <button type="submit" class="btn btn-primary my-1" title="Return" id="AddCompany-return" onClick={back}>
                ←
            </button>

            <Formik
                initialValues={{ email: "", password: "", name: "" }}
                onSubmit={(values, { setFieldValue }) => handleSubmit(values, { setFieldValue })}
            >
                {({ isValid, dirty, values }) => (
                    <div className="AddCompany-form">
                        <h2 className="AddCompany-title">Add Company</h2><br />
                        <Form >
                            <div class="form-group">
                                <label class="control-label col-sm-2" id="AddCompany-label"> Name  </label>
                                <Field class="col-sm-2" id="AddCompany-filed"
                                    type="name"
                                    name="name"
                                    placeholder="Enter Company Name"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group" >

                                <label class="control-label col-sm-2" id="AddCompany-label">Email </label>
                                <Field class="col-sm-2" id="AddCompany-filed"
                                    type="email"
                                    name="email"
                                    placeholder="Enter your Email"
                                    title={values.email}
                                    required={true}
                                    autocomplete={true}
                                />

                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" id="AddCompany-label"> Password </label>
                                <Field class="col-sm-2" id="AddCompany-filed"
                                    type="password"
                                    name="password"
                                    placeholder="Enter your Password"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>
                            <div className="AddCompany-seperator"></div>

                            <button type="submit" class="btn btn-primary my-1" title="Add" id="AddCompany-add">
                                Add
                        </button>
                            <div className="AddCompany-seperator"></div>

                        </Form>
                    </div>
                )}
            </Formik >
        </div>
    )
}