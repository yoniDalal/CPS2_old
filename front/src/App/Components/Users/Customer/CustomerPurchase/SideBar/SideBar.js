import '../../../../../../styles/sidebar.css';
import CustomerService from '../../../../../../service/CustomerService';
import AutenticationService from '../../../../../../service/AutenticationService';

export default function CustomerPurchaseSidebar(props) {

    const couponPurchase = () => {
        if (props.values.checked.length === 0) {
            alert("Please choose at least one coupon")
        } else
            for (let couponId of Object.values(props.values.checked)) {
                CustomerService.purchaseCoupon(couponId).then(
                    (response) => {
                        alert(response.data.title);
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



    return (
        <>
            <div className="sidebar-top"></div>
            <div className="side-bar">

                <div className="side-bar-div"><button type="button" className="side-bar-button" title="All Coupons" onClick={couponPurchase}>Purchase </button></div>

            </div>
        </>
    )

}

