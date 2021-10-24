import "../../../../../../styles/sidebar.css";
import AdminService from "../../../../../../service/AdminService";
import AutenticationService from "../../../../../../service/AutenticationService";
import history from "../../../../history";

export default function AdminCustomersSidebar(props) {

  const handleAllCustomer = () => {
    AdminService.getAllCustomers().then(
      response => {
        props.setCustomers(response.data);
      },
      (error) => {
        try {
          if (error.response.data.string) {
            alert("There are no customers")
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
    AdminService.getOneCustomerByCustomerId(input).then(
      response => {
        props.setCustomers([response.data]);
        props.handleReset();
      },
      (error) => {
        try {
          if (error.response.data.string) {
            alert("The customer with the id " + input + " does not exist")
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
    AdminService.getCustomerByEmail(lowlInput).then(
      response => {
        props.setCustomers([response.data]);
      },
      (error) => {
        try {
          if (error.response.data.string) {
            alert("There is no customer with the email " + input);
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

  const deleteCustomer = () => {
    if (props.values.checked.length === 0) {
      alert("Please choose at least one customer")
    } else
      for (let customerId of Object.values(props.values.checked)) {
        AdminService.deleteCustomer(customerId).then(
          (response) => {
            alert("Customer with the id " + customerId + " deleted successfully")
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

  const handleUpdate = () => {
    if (props.values.checked.length === 1) {
      AdminService.getOneCustomerByCustomerId(props.values.checked[0]).then(
        response => {
          props.setUpdateCustomer(response.data);
          history.push("/admin/customers/update-customer")
        },
        (error) => {
          try {
            if (error.response.data.string) {
              handleAllCustomer()
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
          <button className="side-bar-button" title="All Coupons" onClick={handleAllCustomer}>
            All Customers
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="Coupons By Category" onClick={handleSearchById}>
            Search By Id
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="All Coupons" onClick={handleSearchByEmail}>
            Serch By Email
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="All Coupons" onClick={() => history.push("/admin/customers/add-customer")}>
            Add Customer
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="All Coupons" onClick={handleUpdate}>
            Update Customer
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="All Coupons" onClick={deleteCustomer}>
            Delete Customer
        </button>
        </div>
      </div>

    </>
  );
}



