import './update-company.css'
import { Field, Formik, Form } from 'formik'
import history from "../../../../history";
import AdminService from "../../../../../../service/AdminService";
import AutenticationService from "../../../../../../service/AutenticationService";
import { values } from 'lodash';

export default function UpdateCompany(props) {

    const handleSubmit = (values) => {
        let password = values.password;
        if (values.password === "") {
            password = props.updateCompany.password
        }
        AdminService.updateCompany(
            props.updateCompany.id,
            values.name,
            values.email,
            password
        ).then(
            (response) => {
                alert("The company " + response.data.name + " with the email " + response.data.email + " was updated successfully")
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
        <div className="UpdateCompany">
            <button type="submit" class="btn btn-primary my-1" title="Return" id="UpdateCompany-return" onClick={back}>
                ←
            </button>

            <Formik

                initialValues={{ email: props.updateCompany.email, password: "", name: props.updateCompany.name }}
                onSubmit={(values, { setFieldValue }) => handleSubmit(values, { setFieldValue })}
            >
                {({ isValid, dirty, values }) => (
                    <div className="UpdateCompany-form">
                        <h2 className="UpdateCompany-title">Update Company</h2><br />
                        <Form >
                            <div class="form-group">
                                <label class="control-label col-sm-2" id="UpdateCompany-label">Name</label>
                                <Field class="col-sm-2" id="UpdateCompany-filed"
                                    type="name"
                                    name="name"
                                    placeholder="Enter Company Name"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group">

                                <label class="control-label col-sm-2" id="UpdateCompany-label">Email</label>
                                <Field class="col-sm-2" id="UpdateCompany-filed"
                                    type="email"
                                    name="email"
                                    placeholder="Enter your Email"
                                    title={values.email}
                                    required={true}
                                    autocomplete={true}
                                />

                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" id="UpdateCompany-label"> Password </label>
                                <Field class="col-sm-2" id="UpdateCompany-filed"
                                    type="password"
                                    name="password"
                                    placeholder="Enter your Password"
                                    autocomplete={true}
                                />
                            </div>
                            <div className="UpdateCompany-seperator"></div>

                            <button type="submit" class="btn btn-primary my-1" title="Update" disabled={!dirty}>
                                Update
                        </button>

                            <div className="UpdateCompany-seperator"></div>
                        </Form>
                    </div>
                )}
            </Formik >
        </div>
    )
}