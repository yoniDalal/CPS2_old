import { Navbar, NavbarBrand, Nav } from "react-bootstrap";
import history from "../../history";
import AutenticationService from "../../../../service/AutenticationService";
import "../navbar.css"
import Idle from 'react-idle';

export default function AdminNavbar() {

    const handleTimeOut = () => {
        AutenticationService.logOut()
        alert("Login timed out.")
    }

    return (
        <>
            <Navbar bg="dark" variant="dark" className="nav">
                <NavbarBrand href="#">Coupon System</NavbarBrand>
                <Nav className="me-auto">
                    <Nav.Link onClick={() => history.push('/admin/coupons')} >Coupons</Nav.Link>
                    <Nav.Link onClick={() => history.push('/admin/companies')} >Companies</Nav.Link>
                    <Nav.Link onClick={() => history.push('/admin/customers')} >Customers</Nav.Link>
                    <Nav.Link onClick={() => history.push('/admin/logs')} >Logs</Nav.Link>
                    <Nav.Link onClick={() => history.push('/admin/home')} >Home</Nav.Link>
                    <Nav.Link onClick={() => AutenticationService.logOut()} >Logout</Nav.Link>
                </Nav>
            </Navbar>
            <Idle
                timeout={1000 * 60 * 30}
                render={({ idle }) => <>{idle ? handleTimeOut() : undefined}</>}

            />
        </>
    )
}