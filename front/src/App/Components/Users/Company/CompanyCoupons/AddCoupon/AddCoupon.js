import CompanyService from "../../../../../../service/CompanyService"
import AutenticationService from "../../../../../../service/AutenticationService"
import history from "../../../../history"
import { Formik, Form, Field } from 'formik'
import "./add-coupon.css"

export default function AddCoupon() {

    const handleSubmit = (values) => {

        CompanyService.addCompanyCoupon(
            values.category,
            values.title,
            values.description,
            values.startDate,
            values.endDate,
            values.amount,
            values.price,
            values.image

        ).then(
            (response) => {
                alert("The Coupon " + response.data.title + " was created successfully")
                history.push("/company/my-coupons")
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
            history.push("/company/my-coupons")
        }
    }

    const getToday = () => {

        let today = new Date();
        let dd = today.getDate();
        let mm = today.getMonth() + 1;
        let yyyy = today.getFullYear();

        if (dd < 10) {
            dd = '0' + dd
        }

        if (mm < 10) {
            mm = '0' + mm
        }

        return yyyy + '-' + mm + '-' + dd;
    }

    return (
        <div className="AddCoupon">
            <button type="submit" class="btn btn-primary my-1" title="Return" id="AddCoupon-return" onClick={back}>
                ←
            </button>

            <Formik

                initialValues={{ category: "FOOD", title: "", description: "", startDate: "", endDate: "", amount: "", price: "", image: "" }}
                onSubmit={(values, { setFieldValue }) => handleSubmit(values, { setFieldValue })}
            >
                {({ isValid, dirty, values }) => (
                    <div className="AddCoupon-form">
                        <h2 className="AddCoupon-title">Add Coupon</h2><br />
                        <Form >
                            <div class="form-group">
                                <label class="control-label col-sm-2" id="AddCoupon-label">Title</label>
                                <Field class="col-sm-2" id="AddCoupon-filed"
                                    type="name"
                                    name="title"
                                    placeholder="Enter Title"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group">

                                <label class="control-label col-sm-2" id="AddCoupon-label">Category</label>
                                <Field class="col-sm-2" id="AddCoupon-filed"
                                    component="select"
                                    type="email"
                                    name="category"
                                    title={values.type}
                                    placeholder="Enter Category"
                                    required={true}
                                    autocomplete={true}
                                >
                                    <option value="FOOD">FOOD</option>
                                    <option value="ELECTRICITY">ELECTRICITY</option>
                                    <option value="RESTAURANT">RESTAURANT</option>
                                    <option value="VACATION">VACATION</option>
                                    <option value="SPORT">SPORT</option>
                                    <option value="AUTOMOBILE">AUTOMOBILE</option>
                                    <option value="GAMING">GAMING</option>
                                    <option value="ATTRACTION">ATTRACTION</option>

                                </Field>
                                <div className="AddCoupon-category-seperator"></div>

                                <div class="form-group">
                                    <label class="control-label col-sm-2" id="AddCoupon-label">Description</label>
                                    <Field class="col-sm-2" id="AddCoupon-filed"
                                        name="description"
                                        placeholder="Enter Description"
                                        required={true}
                                        autocomplete={true}
                                    />
                                </div>

                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" id="AddCoupon-label">Start Date</label>
                                <Field class="col-sm-2" id="AddCoupon-filed"
                                    type="date"
                                    min={getToday()}
                                    name="startDate"
                                    placeholder="Enter your Password"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2" id="AddCoupon-label">End Date</label>
                                <Field class="col-sm-2" id="AddCoupon-filed"
                                    type="date"
                                    min={values.startDate}
                                    name="endDate"
                                    placeholder="Enter End Date"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2" id="AddCoupon-label">Amount</label>
                                <Field class="col-sm-2" id="AddCoupon-filed"
                                    type="number"
                                    name="amount"
                                    min="0"
                                    placeholder="Enter Amount"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2" id="AddCoupon-label">Price</label>
                                <Field class="col-sm-2" id="AddCoupon-filed"
                                    type="number"
                                    step=".01"
                                    min="0"
                                    name="price"
                                    placeholder="Enter Price"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2" id="AddCoupon-label">Image</label>
                                <Field class="col-sm-2" id="AddCoupon-filed"
                                    type="url"
                                    name="image"
                                    placeholder="Enter Image"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>
                            <div className="AddCoupon-seperator"></div>

                            <button type="submit" class="btn btn-primary my-1" title="Add">
                                Add
                        </button>

                            <div className="AddCoupon-seperator"></div>
                        </Form>
                    </div>
                )}
            </Formik >
        </div>
    )
}