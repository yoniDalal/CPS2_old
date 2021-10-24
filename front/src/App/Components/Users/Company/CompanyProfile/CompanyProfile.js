import "./company-profile.css";
import CompanyService from "../../../../../service/CompanyService";
import AutenticationService from "../../../../../service/AutenticationService";
import { useEffect } from "react";
import { useState } from "react";


export default function ComapnyProfile() {

  console.log(AutenticationService.getCurrentUser())
  const [company, setCompany] = useState({
    name: "",
    email: "",
  });

  useEffect(() => {
    CompanyService.getCompanyDetails().then(
      (response) => {
        setCompany(response.data);
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

    <div className="profil-page">
      <div className="CompanyProfile-seperator"></div>
      <div className="company-profile">
        <h1 className="company-profile-h1">Company Details</h1>

        <label>Name:   </label>  {company.name}<br />
        <label>Email:  </label> {company.email}
      </div>

    </div>

  )
}
