import React, { Component, Fragment } from 'react';
import Header from '../Header/Header';
import SideBar from '../SideBar/SideBar';
import Footer from '../Footer/Footer';
import BottonGroup from '../BottonGroup/BottonGroup';
import LogTable from '../LogTable/LogTable';
import TreeMap from '../TreeMap/TreeMap';
import './DashBoard.css';

class DashBoard extends Component {
  render() {
    return (
      <Fragment>
        <Header />
        <div className="Header__main">
          <SideBar />
          <div className="Header__main__article">
            <TreeMap />
            <BottonGroup />
            <LogTable />
            <Footer />
          </div>
        </div>
      </Fragment>
    );
  }
}

export default DashBoard;
