import { Outlet, NavLink } from "react-router-dom";

const Layout = () => {
  
  const navLinkStyle = ({isActive}) => {
    return{
    fontWeight: isActive ? "bold" : "normal",
    textDecoration: isActive ? "underline" : "none",
  }
}
  
  return (
      <>
        <nav>
          <ul>
            <li>
              <NavLink style={(navLinkStyle)} to="/">Home</NavLink>
            </li>
            <li>
              <NavLink style={(navLinkStyle)} to="/contact">Contact</NavLink>
            </li>
            <li>
              <NavLink style={(navLinkStyle)} to="/zoos">List of Zoos</NavLink>
            </li>
          </ul>
        </nav>
        <Outlet />
      </>
    )
  };
  
  export default Layout;