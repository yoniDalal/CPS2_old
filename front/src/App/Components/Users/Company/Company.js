import { Route, Redirect, Switch } from "react-router";
import CompanyProfile from "./CompanyProfile/CompanyProfile";
import CompanyCoupons from "./CompanyCoupons/CompanyCoupons";
import AllCoupons from "./AllCoupons/AllCoupons";
import CompanyHome from "./CompanyHome/CompanyHome";
import "./company.css";

export default function Company() {
  const getUser = () => {
    return (
      <Switch>
        <Route path="/company/profile">
          <CompanyProfile />
        </Route>
        <Route path="/company/my-coupons">
          <CompanyCoupons />
        </Route>
        <Route path="/company/all-coupons">
          <AllCoupons />
        </Route>
        <Route path="/company">
          <Redirect to="/company/home" />
          <CompanyHome />
        </Route>
      </Switch>
    );
  };
  return <div className="Company">{getUser()}</div>;
}
