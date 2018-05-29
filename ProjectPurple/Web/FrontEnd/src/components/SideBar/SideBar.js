import React, { Component, Fragment } from 'react';
import { Navbar, Nav, NavItem } from 'reactstrap';
import './SideBar.css';
import { Link } from 'react-router-dom';
import { Icon } from 'semantic-ui-react';

class SideBar extends Component {
  render() {
    return (
      <div className="SideBar">
        <Link to="Home" className="SideBar__link">
          <Icon name="home" size="large" />DashBoard
        </Link>
        <Link to="Home" className="SideBar__link">
          <Icon name="podcast" size="large" />AP
        </Link>
        <Link to="graph" className="SideBar__link">
          <Icon name="cube" size="large" />Component
        </Link>
      </div>
    );
  }
}

export default SideBar;
