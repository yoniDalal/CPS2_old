import "../../../../../../styles/sidebar.css"
import history from "../../../../history";
import CompanyServics from "../../../../../../service/CompanyService";
import AutenticationService from "../../../../../../service/AutenticationService";
import AdminService from "../../../../../../service/AdminService";

export default function CompanyCouponsSidebar(props) {

  const handleSearchCategory = () => {

    const input = prompt("Choose a category: FOOD, ELECTRICITY, RESTAURANT, VACATION, SPORT, AUTOMOBILE, GAMING, ATTRACTION")
    if (input === null) {
      return
    }
    const capitalInput = input.toUpperCase();
    switch (capitalInput) {
      case "FOOD":
        break;
      case "ELECTRICITY":
        break;
      case "RESTAURANT":
        break;
      case "VACATION":
        break;
      case "SPORT":
        break;
      case "AUTOMOBILE":
        break;
      case "GAMING":
        break;
      case "ATTRACTION":
        break;
      default:
        alert("Category does not exists")
        return;
    }
    CompanyServics.getCompanyCouponsByCategory(capitalInput).then(
      response => {
        props.setCoupons(response.data);
      },
      (error) => {
        try {
          if (error.response.data.string) {
            alert("You dont own any coupons with \"" + input + "\" category");
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

  const handleAllCoupons = () => {
    CompanyServics.getCompanyCoupons().then(
      response => {
        props.setCoupons(response.data);
      },
      (error) => {
        try {
          if (error.response.data.string) {
            alert("You do not own any coupons")
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

  const handleSearchPrice = () => {
    const input = prompt("Enter price: ")
    if (input === null) {
      return
    }
    if (isNaN(input) || input.includes(" ") || input === "") {
      alert("Please insert a numeric value")
      return
    }
    if (input < 0) {
      alert("The minimum value needs to be at least 0")
      return
    }

    CompanyServics.getCompanyCouponsByMaxPrice(input).then(
      response => {
        props.setCoupons(response.data);
      },
      (error) => {
        try {
          if (error.response.data.string) {
            alert("You do not own any coupons below the price: " + input)
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

  const deleteCoupons = () => {
    if (props.values.checked.length === 0) {
      alert("Please choose atleast one coupon")
    } else
      for (let couponId of Object.values(props.values.checked)) {
        CompanyServics.deleteCompanyCoupon(couponId).then(
          (response) => {
            alert("Coupon with the id " + couponId + " deleted successfully")
            handleAllCoupons();

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
      AdminService.getCoupons().then(
        response => {
          for (let coupon of Object.values(response.data)) {
            console.log(coupon.id + " " + props.values.checked[0])
            if (Number(coupon.id) === Number(props.values.checked[0])) {
              props.setUpdateCoupon(coupon);
              history.push("/company/my-coupons/update-coupon")
            }
          }
        },
        (error) => {
          try {
            if (error.response.data.string) {
              handleAllCoupons()
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
          <button className="side-bar-button" title="All Coupons" onClick={handleAllCoupons}>
            All Coupons
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="Coupons By Category" onClick={handleSearchCategory}>
            Search Category
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="All Coupons" onClick={handleSearchPrice}>
            Search Price
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="Coupons By Category" onClick={() => history.push("/company/my-coupons/add-coupon")}>
            Add Coupon
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="Coupons By Category" onClick={handleUpdate}>
            Update Coupon
        </button>
        </div>
        <div className="side-bar-div">
          <button className="side-bar-button" title="Coupons By Category" onClick={deleteCoupons}>
            Delete Coupon
        </button>
        </div>
      </div>

    </>

  );
}


