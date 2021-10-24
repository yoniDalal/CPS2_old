import AdminService from '../../../../../service/AdminService';
import AutenticationService from "../../../../../service/AutenticationService";
import { useState, useEffect } from 'react';
import { Formik, Form } from 'formik';
import "./admin-coupons.css";

export default function AllCoupons() {

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

  useEffect(() => {

    AdminService.getCoupons().then(
      response => {
        setCoupons(response.data);
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
  }, [])

  const getCoupons = (handleChange) => {
    if (coupons[0].id === '')
      return
    return (
      coupons.map((interval, index) => {
        return (
          <tr key={index}>
            <td className="AllCoupons-td" title={interval.category}>{interval.category}</td>
            <td className="AllCoupons-td" title={interval.title}>{interval.title}</td>
            <td className="AllCoupons-td" title={interval.description}>{interval.description}</td>
            <td className="AllCoupons-td" title={interval.startDate}>{interval.startDate}</td>
            <td className="AllCoupons-td" title={interval.endDate}>{interval.endDate}</td>
            <td className="AllCoupons-td" title={interval.amount}>{interval.amount}</td>
            <td className="AllCoupons-td" title={interval.price}>{interval.price}</td>
            <td className="AllCoupons-td" title={interval.image}>
              <div id="AllCoupons-image-td">
                <div className="AllCoupons-image-div" style={{ backgroundImage: "url(" + interval.image + ")" }}>
                </div>
              </div>
            </td>
          </tr>
        )
      })
    )
  }

  return (
    <div className="AllCoupons">
      <Formik

        initialValues={{ checked: [] }}
      >
        {({ handleChange, values }) => (
          <Form >

            <table className="table" id="AllCoupons-table">
              <tbody>
                <tr>
                  <th width="13%" className="AllCoupon-table-th" title="Category">Category</th>
                  <th width="13%" className="AllCoupon-table-th" title="Title">Title</th>
                  <th width="20%" className="AllCoupon-table-th" title="Description">Description</th>
                  <th width="12%" className="AllCoupon-table-th" title="Release Date">Release Date</th>
                  <th width="12%" className="AllCoupon-table-th" title="Expiration Date">Expiration Date</th>
                  <th width="10%" className="AllCoupon-table-th" title="Stock">Stock</th>
                  <th width="10%" className="AllCoupon-table-th" title="Price">Price</th>
                  <th width="10%" className="AllCoupon-table-th" title="Image">Image</th>
                </tr>
                {getCoupons(handleChange)}
              </tbody>
            </table>
          </Form>
        )}
      </Formik >
    </div>
  )
}



