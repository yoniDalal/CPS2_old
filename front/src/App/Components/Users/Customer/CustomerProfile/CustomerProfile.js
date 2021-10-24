import "./customer-profile.css";
import CustomerService from "../../../../../service/CustomerService";
import AutenticationService from "../../../../../service/AutenticationService";
import { useEffect } from "react";
import { useState } from "react";
import './customer-profile.css'

export default function CustomerProfile() {

  console.log(AutenticationService.getCurrentUser())
  const [customer, setCustomer] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

  useEffect(() => {
    CustomerService.getCustomerDetails().then(
      (response) => {
        setCustomer(response.data);
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
    );
  }, []);

  return (

    <div className="profile-page">
      <div className="CustomerProfile-seperator"></div>
      <div className="customer-profile">
        <h1 className="customer-profile-h1">Customer Details</h1>

        <label>First Name: </label>  {customer.firstName}<br />
        <label>Last Name: </label> {customer.lastName}<br />
        <label>Email: </label> {customer.email}


      </div>

    </div>




  )
}


