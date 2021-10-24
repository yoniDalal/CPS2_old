import "../../../../../../styles/sidebar.css";
import AdminService from "../../../../../../service/AdminService";
import AutenticationService from "../../../../../../service/AutenticationService";
import history from "../../../../history";
import { values } from "lodash";

export default function AdminCompaniesSidebar(props) {

  const handleSearchById = () => {
    const input = prompt("Enter ID: ")
    if (input === null) {
      return
    }
    if (isNaN(input) || input.includes(" ") || input === "") {
      alert("Please insert a numeric value")
      return
    }
    if (input < 1) {
      alert("The minimum value needs to be at least 1")
      return
    }
    console.log(input)
    AdminService.getOneCompanyByCompanyId(input).then(
      response => {
        props.setCompanies([response.data]);
        props.handleReset();
      },
      (error) => {
        try {
          if (error.response.data.string) {
            alert("The company with the id " + input + " does not exist")
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

  const handleSearchByEmail = () => {
    const input = prompt("Enter Email: ")
    if (input === null) {
      return
    }
    const lowlInput = input.toLowerCase();
    AdminService.getCompanyByEmail(lowlInput).then(
      response => {
        props.setCompanies([response.data]);
      },
      (error) => {
        try {
          if (error.response.data.string) {
            alert("There is no company with the email " + input);
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

  const deleteCompanies = () => {
    if (props.values.checked.length === 0) {
      alert("Please choose at least one company")
    } else
      for (let companyId of Object.values(props.values.checked)) {
        AdminService.deleteCompany(companyId).then(
          (response) => {
            alert("Company with the id " + companyId + " deleted successfully")
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
        );
      }
  }

  const handleAllCompany = () => {
    AdminService.getAllCompanies().then(
      response => {
        props.setCompanies(response.data);
      },
      (error) => {
        try {
          if (error.response.data.string) {
            alert("There are no companies")
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

  const handleUpdate = () => {
    if (props.values.checked.length === 1) {
      AdminService.getOneCompanyByCompanyId(props.values.checked[0]).then(
        response => {
          props.setUpdateCompany(response.data);
          history.push("/admin/companies/update-company")
        },
        (error) => {
          try {
            if (error.response.data.string) {
              handleAllCompany()
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
    else if (props.values.checked.length === 0) {
      alert("Please choose one coupon")
    } else {
      alert("Please choose only one coupon")
    }
  }

  return (
    <>
      <div className="sidebar-top"></div>
      <div className="side-bar">
        <div className="side-bar-div">
          <button className="side-bar-button" title="All Companies" onClick={handleAllCompany}>
            All Companies
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="Search By Id" onClick={handleSearchById}>
            Search By Id
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="Search By Email" onClick={handleSearchByEmail}>
            Search By Email
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="Add Company" onClick={() => history.push("/admin/companies/add-company")}>
            Add Company
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="Update Company" onClick={handleUpdate}>
            Update Company
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="Delete Company" onClick={deleteCompanies}>
            Delete Company
        </button>
        </div>

      </div>
    </>
  );
}

