import './admin-companies.css';
import AutenticationService from "../../../../../service/AutenticationService";
import { useState, useEffect } from 'react';
import AdminService from '../../../../../service/AdminService';
import AdminCompaniesSidebar from './AdminCompanyesSidebar/AdminCompaniesSidebar';
import { Formik, Form } from 'formik'
import { Route, Switch } from 'react-router';
import AddCompany from "./AddCompany/AddCompany";
import UpdateCompany from "./UpdateCompany/UpdateCompany";

export default function AdminCompanies() {

  const [updateCompany, setUpdateCompany] = useState({
    id: '',
    name: '',
    email: '',
    password: '',

  })

  const [comapny, setCompanies] = useState([{
    id: '',
    name: '',
    email: '',
  }])

  useEffect(() => {
    AdminService.getAllCompanies().then(
      response => {
        setCompanies(response.data);
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

  const getComapnies = (handleChange) => {
    if (comapny[0].id === '')
      return
    return (
      comapny.map((interval, index) => {
        return (
          <tr key={index}>
            <td className="AdminCompanies-td" title="checked"><input onChange={handleChange} type="checkbox" name="checked" value={interval.id} /></td>
            <td className="AdminCompanies-td" title={interval.id}>{interval.id}</td>
            <td className="AdminCompanies-td" title={interval.name}>{interval.name}</td>
            <td className="AdminCompanies-td" title={interval.email}>{interval.email}</td>
          </tr>
        )
      })
    )
  }

  function addCompany() {

    return (
      <Switch>
        <Route path="/admin/companies/add-company">
          <AddCompany />
        </Route>
        <Route path="/admin/companies/update-company">
          <UpdateCompany updateCompany={updateCompany} />
        </Route>
        <Route path="/admin/companies">
          <div className="AdminCompanies">
            <Formik
              initialValues={{ checked: [] }}
            >
              {({ handleChange, values, handleReset }) => (
                <Form >
                  <AdminCompaniesSidebar setCompanies={setCompanies} values={values} handleReset={handleReset} setUpdateCompany={setUpdateCompany} />
                  <table className="table" id="AdminCompanies-table">
                    <tbody>
                      <tr>
                        <th width="25%" className="AdminCompanies-table-th" title="Select">Select</th>
                        <th width="25%" className="AdminCompanies-table-th" title="ID">ID</th>
                        <th width="45%" className="AdminCompanies-table-th" title="Name">Name</th>
                        <th width="45%" className="AdminCompanies-table-th" title="Email">Email</th>
                      </tr>
                      {getComapnies(handleChange)}
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
      {addCompany()}
    </>

  )
}

