import React from 'react';
import PropTypes from 'prop-types';
import './SideBar.css'
import TextCenter from '../TextCenter/TextCenter';
import { MdDeviceHub, MdTimeline, MdPermIdentity } from 'react-icons/lib/md';
import { Link } from 'react-router-dom';

const SideBar = () => {
    return (
        <div id="sidebar-wrapper">
            <nav className="absolute-top navbar navbar-absolute navbar-expand-lg"
                id="sidebar-header"
            >
                <TextCenter>
                    <span className="sidebar-header-text">
                        <span className="sidebar-header-text bold">S
                        </span>mart <span className="sidebar-header-text bold">P</span>lant
                    </span>
                </TextCenter>
            </nav>
            <ul className="sidebar-nav" id="nanum-gothic">
                <li className="mt-3">
                    <Link to="/main">
                        <MdDeviceHub size="30" />
                        <span className="ml-3">디바이스 정보</span>
                    </Link>
                </li>
                <li className="mt-3">
                    <Link to="/main">
                        <MdTimeline size="30" />
                        <span className="ml-3">데이터 로거</span>
                    </Link>
                </li>
                <li className="mt-3">
                    <Link to="/main">
                        <MdPermIdentity size="30" />
                        <span className="ml-3">사용자 정보</span>
                    </Link>
                </li>
            </ul>
        </div>
    )
};

SideBar.propTypes = {

}

export default SideBar;