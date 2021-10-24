
import './company-coupons.css';
import CompanyCouponsSidebar from './Sidebar/CompanyCouponsSidebar';
import AutenticationService from "../../../../../service/AutenticationService";
import { useState, useEffect } from 'react';
import CompanyService from '../../../../../service/CompanyService';
import { Formik, Form } from 'formik'
import { Route, Switch } from 'react-router';
import AddCoupon from "./AddCoupon/AddCoupon";
import UpdateCoupon from './UpdateCoupon/UpdateCoupon';


export default function CompanyCoupons() {

  const [coupons, setCoupons] = useState([{
    id: '',
    category: '',
    title: '',
    description: '',
    startDate: '',
    endDate: '',
    amount: '',
    price: '',
    image: '',
  }])

  const [updateCoupon, setUpdateCoupon] = useState({
    id: '',
    category: '',
    title: '',
    description: '',
    startDate: '',
    endDate: '',
    amount: '',
    price: '',
    image: '',
  })

  useEffect(() => {

    CompanyService.getCompanyCoupons().then(
      response => {
        setCoupons(response.data);
      },
      (error) => {
        try {
          if (error.response.data.string) {
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
  }, [])

  const getCoupons = (handleChange) => {
    if (coupons[0].id === '')
      return
    return (
      coupons.map((interval, index) => {
        return (
          <tr key={index}>
            <td className="CompanyCoupons-td" title="checked"><input onChange={handleChange} type="checkbox" name="checked" value={interval.id} /></td>
            <td className="CompanyCoupons-td" title={interval.category}>{interval.category}</td>
            <td className="CompanyCoupons-td" title={interval.title}>{interval.title}</td>
            <td className="CompanyCoupons-td" title={interval.description}>{interval.description}</td>
            <td className="CompanyCoupons-td" title={interval.startDate}>{interval.startDate}</td>
            <td className="CompanyCoupons-td" title={interval.endDate}>{interval.endDate}</td>
            <td className="CompanyCoupons-td" title={interval.amount}>{interval.amount}</td>
            <td className="CompanyCoupons-td" title={interval.price}>{interval.price}</td>
            <td className="CompanyPurchase-td" title={interval.image}>
              <div id="CompanyCoupons-image-td">
                <div className="CompanyCoupons-image-div" style={{ backgroundImage: "url(" + interval.image + ")" }}>
                </div>
              </div>
            </td>
          </tr>
        )
      })
    )
  }

  function addCoupon() {

    return (
      <Switch>
        <Route path="/company/my-coupons/add-coupon">
          <AddCoupon />
        </Route><Route path="/company/my-coupons/update-coupon">
          <UpdateCoupon updateCoupon={updateCoupon} />
        </Route>
        <Route path="/company/my-coupons">
          <div className="CompanyCoupons">
            <Formik
              initialValues={{ checked: [] }}
            >
              {({ handleChange, values, handleReset }) => (
                <Form >

                  <CompanyCouponsSidebar setCoupons={setCoupons} values={values} handleReset={handleReset} setUpdateCoupon={setUpdateCoupon} />
                  <table className="table" id="CompanyCoupons-table">
                    <tbody>
                      <tr>
                        <th width="10%" className="CustomerPurchase-table-th" title="Select">Select</th>
                        <th width="10%" className="CompanyCoupons-table-th" title="Category">Category</th>
                        <th width="11%" className="CompanyCoupons-table-th" title="Title">Title</th>
                        <th width="11%" className="CompanyCoupons-table-th" title="Description">Description</th>
                        <th width="13%" className="CompanyCoupons-table-th" title="Release Date">Release Date</th>
                        <th width="13%" className="CompanyCoupons-table-th" title="Expiration Date">Expiration Date</th>
                        <th width="10%" className="CompanyCoupons-table-th" title="Stock">Stock</th>
                        <th width="10%" className="CompanyCoupons-table-th" title="Price">Price</th>
                        <th width="12%" className="CompanyCoupons-table-th" title="Image">Image</th>
                      </tr>
                      {getCoupons(handleChange)}
                    </tbody>
                  </table>
                </Form>
              )}
            </Formik >


          </div>
        </Route>
      </Switch>
    )
  }

  return (

    <>
      {addCoupon()}
    </>

  )
}