import React from 'react';
import PropTypes from 'prop-types';
import './SideBar.css'
import TextCenter from '../TextCenter/TextCenter';
import { MdDeviceHub, MdTimeline, MdPermIdentity } from 'react-icons/lib/md';
import { Link } from 'react-router-dom';

const SideBar = () => {
    return (
        <div id="sidebar-wrapper">
            <div>
                <Link to="/main">
                    <div className="sidebar-item selected">
                        <MdDeviceHub size="20" />
                    </div>
                </Link>
                <Link to="/main">
                    <div className="sidebar-item">
                        <MdTimeline size="20" />
                    </div>
                </Link>
                <Link to="/main">
                    <div className="sidebar-item">
                        <MdPermIdentity size="20" />
                    </div>
                </Link>
            </div>
            {/* <ul className="sidebar-nav">
                <li className="mt-3">
                    
                </li>
                <li className="mt-3">
                    <Link to="/main">
                        <MdTimeline size="20"/>
                    </Link>
                </li>
                <li className="mt-3">
                    <Link to="/main">
                        <MdPermIdentity size="20" />
                    </Link>
                </li>
            </ul> */}
        </div>
    )
};

SideBar.propTypes = {

}

export default SideBar;