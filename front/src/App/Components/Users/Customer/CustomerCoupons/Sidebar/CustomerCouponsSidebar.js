import '../../../../../../styles/sidebar.css';
import CustomerService from "../../../../../../service/CustomerService";
import AutenticationService from "../../../../../../service/AutenticationService";



export default function CustomerCouponsSidebar(props) {

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

        CustomerService.getCustomerCouponsByCategory(capitalInput).then(
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
        CustomerService.getCustomerCoupons().then(
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

        CustomerService.getCustomerCouponsByMaxPrice(input).then(
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

    return (
        <>
            <div className="sidebar-top"></div>
            <div className="side-bar">
                <div className="side-bar-div"><button className="side-bar-button" title="All Coupons" onClick={handleAllCoupons}>All Coupons</button></div>
                <div className="side-bar-div"><button className="side-bar-button" title="Search Category" onClick={handleSearchCategory}>Search Category</button></div>
                <div className="side-bar-div"><button className="side-bar-button" title="Search Price" onClick={handleSearchPrice}>Search Price</button></div>
            </div>
        </>
    )
}


