import './update-coupon.css'
import { Field, Formik, Form } from 'formik'
import history from "../../../../history";
import CompanyService from "../../../../../../service/CompanyService";
import AutenticationService from "../../../../../../service/AutenticationService";
import { values } from 'lodash';

export default function UpdateCoupon(props) {

    const handleSubmit = (values) => {
        console.log(values)
        CompanyService.updateCompanyCoupon(
            props.updateCoupon.id,
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
                alert("The coupon " + response.data.title + " was updated successfully")
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
    console.log(props.updateCoupon.title + "????")
    console.log(props.updateCoupon)
    return (
        <div className="UpdateCoupon">
            <button type="submit" class="btn btn-primary my-1" title="Return" id="UpdateCoupon-return" onClick={back}>
                ←
            </button>

            <Formik

                initialValues={{
                    category: props.updateCoupon.category,
                    title: props.updateCoupon.title,
                    description: props.updateCoupon.description,
                    startDate: props.updateCoupon.startDate,
                    endDate: props.updateCoupon.endDate,
                    amount: props.updateCoupon.amount,
                    price: props.updateCoupon.price,
                    image: props.updateCoupon.image

                }}
                onSubmit={(values, { setFieldValue }) => handleSubmit(values, { setFieldValue })}

            >
                {({ isValid, dirty, values }) => (
                    <div className="UpdateCoupon-form">
                        <h2 className="UpdateCoupon-title">Update Coupon</h2><br />
                        <Form >
                            <div class="form-group">
                                <label class="control-label col-sm-2" id="UpdateCoupon-label">Title</label>
                                <Field class="col-sm-2" id="UpdateCoupon-filed"
                                    type="name"
                                    name="title"
                                    placeholder="Enter Title"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group">

                                <label class="control-label col-sm-2" id="UpdateCoupon-label">Category</label>
                                <Field class="col-sm-2" id="UpdateCoupon-filed"
                                    component="select"
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
                                <div className="UpdateCoupon-category-seperator"></div>

                                <div class="form-group">
                                    <label class="control-label col-sm-2" id="UpdateCoupon-label">Description</label>
                                    <Field class="col-sm-2" id="UpdateCoupon-filed"
                                        name="description"
                                        placeholder="Enter Description"
                                        required={true}
                                        autocomplete={true}
                                    />
                                </div>

                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" id="UpdateCoupon-label">Start Date</label>
                                <Field class="col-sm-2" id="UpdateCoupon-filed"
                                    type="date"
                                    min={getToday()}
                                    name="startDate"
                                    placeholder="Enter your Password"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2" id="UpdateCoupon-label">End Date</label>
                                <Field class="col-sm-2" id="UpdateCoupon-filed"
                                    type="date"
                                    min={values.startDate}
                                    name="endDate"
                                    placeholder="Enter End Date"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2" id="UpdateCoupon-label">Amount</label>
                                <Field class="col-sm-2" id="UpdateCoupon-filed"
                                    type="number"
                                    name="amount"
                                    min="0"
                                    placeholder="Enter Amount"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2" id="UpdateCoupon-label">Price</label>
                                <Field class="col-sm-2" id="UpdateCoupon-filed"
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
                                <label class="control-label col-sm-2" id="UpdateCoupon-label">Image</label>
                                <Field class="col-sm-2" id="UpdateCoupon-filed"
                                    type="url"
                                    name="image"
                                    placeholder="Enter Image"
                                    required={true}
                                    autocomplete={true}
                                />
                            </div>
                            <div className="UpdateCoupon-seperator"></div>

                            <button type="submit" class="btn btn-primary my-1" title="Add">
                                Update
                        </button>

                            <div className="UpdateCoupon-seperator"></div>
                        </Form>
                    </div>
                )}
            </Formik >
        </div>

    )
}