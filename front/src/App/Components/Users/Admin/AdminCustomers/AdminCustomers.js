
import './admin-customer.css';
import AdminCutomersSidebar from "./AdminCustomersSidebar/AdminCustomersSidebar"
import AutenticationService from "../../../../../service/AutenticationService";
import { useState, useEffect } from 'react';
import AdminService from "../../../../../service/AdminService"
import { Formik, Form } from 'formik'
import { Route, Switch } from 'react-router';
import AddCustomer from "./AddCustomer/AddCustomer";
import UpdateCustomer from "./UpdateCustomer/UpdateCustomer";

export default function AdminCutomers() {

  const [updateCustomer, setUpdateCustomer] = useState({
    id: '',
    firstName: '',
    lastName: '',
    email: '',
    password: '',

  })

  const [customer, setCustomers] = useState([{
    id: '',
    firstName: '',
    lastName: '',
    email: '',
  }])

  useEffect(() => {
    AdminService.getAllCustomers().then(
      response => {
        setCustomers(response.data);
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

  const getCustomer = (handleChange) => {
    if (customer[0].id === '')
      return
    return (
      customer.map((interval, index) => {
        return (
          <tr key={index}>
            <td className="AdminCustomers-td" title="checked"><input onChange={handleChange} type="checkbox" name="checked" value={interval.id} /></td>
            <td className="AdminCustomers-td" title={interval.id}>{interval.id}</td>
            <td className="AdminCustomers-td" title={interval.firstName}>{interval.firstName}</td>
            <td className="AdminCustomers-td" title={interval.lastName}>{interval.lastName}</td>
            <td className="AdminCustomers-td" title={interval.email}>{interval.email}</td>
          </tr>
        )
      })
    )
  }

  function addCustomer() {

    return (
      <Switch>
        <Route path="/admin/customers/add-customer">
          <AddCustomer />
        </Route>
        <Route path="/admin/customers/update-customer">
          <UpdateCustomer updateCustomer={updateCustomer} />
        </Route>
        <Route path="/admin/customers">
          <div className="AdminCustomers">
            <Formik
              initialValues={{ checked: [] }}
            >
              {({ handleChange, values, handleReset }) => (
                <Form >

                  <AdminCutomersSidebar setCustomers={setCustomers} values={values} handleReset={handleReset} setUpdateCustomer={setUpdateCustomer} />
                  <table className="table" id="AdminCustomers-table">
                    <tbody>
                      <tr>
                        <th width="15%" className="AdminCustomers-table-th" title="Select">Select</th>
                        <th width="15%" className="AdminCustomers-table-th" title="ID">ID</th>
                        <th width="20%" className="AdminCustomers-table-th" title="First Name">First Name</th>
                        <th width="20%" className="AdminCustomers-table-th" title="Last Name">Last Name</th>
                        <th width="30%" className="AdminCustomers-table-th" title="Email">Email</th>
                      </tr>
                      {getCustomer(handleChange)}
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
      {addCustomer()}
    </>

  )

}





