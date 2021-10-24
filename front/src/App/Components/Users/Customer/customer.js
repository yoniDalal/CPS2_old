import { Route, Redirect, Switch } from "react-router";
import CustomerCoupons from "./CustomerCoupons/CustomerCoupons";
import CustomerHome from "./CustomerHome/CustomerHome";
import CustomerProfile from "./CustomerProfile/CustomerProfile";
import CustomerPurchase from "./CustomerPurchase/CustomerPurchase";

export default function Customer() {
  const getUser = () => {
    return (
      <Switch>
        <Route path="/customer/profile">
          <CustomerProfile />
        </Route>
        <Route path="/customer/my-coupons">
          <CustomerCoupons />
        </Route>
        <Route path="/customer/purchase">
          <CustomerPurchase />
        </Route>
        <Route path="/customer">
          <Redirect to="/customer/home" />
          <CustomerHome />
        </Route>
      </Switch>
    );
  };
  return <div className="Customer">{getUser()}</div>;
}

