import { Navbar, NavbarBrand, Nav } from "react-bootstrap";
import "./navbar.css"

export default function Sidebar() {
  return (

    <Navbar bg="dark" variant="dark" className="nav" >
      <NavbarBrand href="#">Coupon System</NavbarBrand>
      <Nav className="me-auto">
        <Nav.Link href="/home-page">Home</Nav.Link>
        <Nav.Link href="/login">Login</Nav.Link>

      </Nav>
    </Navbar>





  )
}