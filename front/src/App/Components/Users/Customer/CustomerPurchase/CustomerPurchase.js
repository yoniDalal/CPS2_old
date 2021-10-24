import AdminService from '../../../../../service/AdminService';
import AutenticationService from "../../../../../service/AutenticationService";
import { useState, useEffect } from 'react';
import { Formik, Form } from 'formik'
import CustomerPurchaseSidebar from './SideBar/SideBar';
import './customer-purchase.css'

export default function CustomerPurchase() {

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
            <td className="CustomerPurchase-td" title="checked"><input onChange={handleChange} type="checkbox" name="checked" value={interval.id} /></td>
            <td className="CustomerPurchase-td" title={interval.category}>{interval.category}</td>
            <td className="CustomerPurchase-td" title={interval.title}>{interval.title}</td>
            <td className="CustomerPurchase-td" title={interval.description}>{interval.description}</td>
            <td className="CustomerPurchase-td" title={interval.startDate}>{interval.startDate}</td>
            <td className="CustomerPurchase-td" title={interval.endDate}>{interval.endDate}</td>
            <td className="CustomerPurchase-td" title={interval.amount}>{interval.amount}</td>
            <td className="CustomerPurchase-td" title={interval.price}>{interval.price}</td>
            <td className="CustomerPurchase-td" title={interval.image}>
              <div id="CustomerPurchase-image-td">
                <div className="CustomerPurchase-image-div" style={{ backgroundImage: "url(" + interval.image + ")" }}>
                </div>
              </div>
            </td>
          </tr>
        )
      })
    )
  }


  return (
    <div className="CustomerPurchase">
      <Formik

        initialValues={{ checked: [] }}
      >
        {({ handleChange, values }) => (
          <Form >
            <CustomerPurchaseSidebar values={values} />
            <table className="table" id="CustomerPurchase-table">
              <tbody>
                <tr>
                  <th width="10%" className="CustomerPurchase-table-th" title="Select">Select</th>
                  <th width="10%" className="CustomerPurchase-table-th" title="Category">Category</th>
                  <th width="11%" className="CustomerPurchase-table-th" title="Title">Title</th>
                  <th width="11%" className="CustomerPurchase-table-th" title="Description">Description</th>
                  <th width="13%" className="CustomerPurchase-table-th" title="Release Date">Release Date</th>
                  <th width="13%" className="CustomerPurchase-table-th" title="Expiration Date">Expiration Date</th>
                  <th width="10%" className="CustomerPurchase-table-th" title="Stock">Stock</th>
                  <th width="10%" className="CustomerPurchase-table-th" title="Price">Price</th>
                  <th width="12%" className="CustomerPurchase-table-th" title="Image">Image</th>
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




