import { Route, Redirect, Switch } from "react-router";
import AdminCoupons from "./AdminCoupons/AdminCoupons";
import AdminCompanies from "./AdminCompanies/AdminCompanies";
import AdminCustomers from "./AdminCustomers/AdminCustomers";
import AdminLogs from "./AdminLogs/AdminLogs";
import AdminHome from "./AdminHome/AdminHome";

export default function Admin() {
  const getUser = () => {
    return (
      <Switch>
        <Route path="/admin/coupons">
          <AdminCoupons />
        </Route>
        <Route path="/admin/companies">
          <AdminCompanies />
        </Route>
        <Route path="/admin/customers">
          <AdminCustomers />
        </Route>
        <Route path="/admin/logs">
          <AdminLogs />
        </Route>
        <Route path="/admin">
          <Redirect to="/admin/home" />
          <AdminHome />
        </Route>
      </Switch>
    );
  };
  return <div className="Admin">{getUser()}</div>;
}
