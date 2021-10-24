import './App.css';
import Login from './App/Components/Login/Login';
import history from './App/Components/history';
import { Router, Switch, Route, Redirect } from 'react-router-dom'
import Footer from './App/Components/Footer/Footer';
import LoginNavbar from './App/Components/NavBar/LoginNavbar';
import HomePage from './App/Components/HomePage/HomePage';
import CompanyNavbar from './App/Components/NavBar/users/CompanyNavbar'
import AdminNavbar from './App/Components/NavBar/users/AdminNavbar'
import CustomerNavbar from './App/Components/NavBar/users/CustomerNavbar'
import Customer from './App/Components/Users/Customer/customer';
import Admin from './App/Components/Users/Admin/admin';
import Company from './App/Components/Users/Company/Company';
import AutenticationService from './service/AutenticationService';

function App() {

  // console.log(AutenticationService.getCurrentUser())
  if (AutenticationService.getCurrentUser() != null) {
    switch (AutenticationService.getCurrentUser().authorities[0].authority) {
      case "CUSTOMER":
        if (!history.location.pathname.includes("/customer")) {
          history.push("/customer")
        }
        break;
      case "COMPANY":
        if (!history.location.pathname.includes("/company")) {
          history.push("/company")
        }
        break;
      case "ADMIN":
        if (!history.location.pathname.includes("/admin")) {
          history.push("/admin")
        }
        break;

      default:
        break;
    }
  } else {
    if (!history.location.pathname.includes("/home-page") && !history.location.pathname.includes("/login")) {
      history.push("/login")

    }
  }

  function loginuser() {
    return (
      <Switch>
        <Route path="/login">
          <Login />
        </Route>
        <Route path="/customer">
          <Customer />
        </Route>
        <Route path="/admin">
          <Admin />
        </Route>
        <Route path="/company">
          <Company />
        </Route>
        <Route>
          <Redirect to="/home-page" />
          <HomePage />
        </Route>

      </Switch >)
  }
  function getNavbar() {
    return (
      <Switch>
        <Route path="/company">
          <CompanyNavbar />
        </Route>
        <Route path="/admin">
          <AdminNavbar />
        </Route>
        <Route path="/customer">
          <CustomerNavbar />
        </Route>
        <Route >
          <LoginNavbar />
        </Route>
      </Switch >)
  }
  return (
    <Router history={history}>


      <div className="App">
        {getNavbar()}
        {loginuser()}
        <Footer />

      </div>

    </Router>
  );

}

export default App;
