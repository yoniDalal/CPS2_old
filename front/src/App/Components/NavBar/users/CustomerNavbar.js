import { Navbar, NavbarBrand, Nav } from "react-bootstrap";
import history from "../../history";
import AutenticationService from "../../../../service/AutenticationService";
import "../navbar.css"
import Idle from 'react-idle';

export default function CustomerNavbar() {

    const handleTimeOut = () => {
        AutenticationService.logOut()
        alert("Login timed out.")
    }

    return (
        <>
            <Navbar bg="dark" variant="dark" className="nav">
                <NavbarBrand >Coupon System</NavbarBrand>
                <Nav className="me-auto">
                    <Nav.Link onClick={() => history.push('/customer/profile')} >Profile</Nav.Link>
                    <Nav.Link onClick={() => history.push('/customer/my-coupons')} >My Coupons</Nav.Link>
                    <Nav.Link onClick={() => history.push('/customer/purchase')} >Purchase Coupons</Nav.Link>
                    <Nav.Link onClick={() => history.push('/customer/home')} >Home</Nav.Link>
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