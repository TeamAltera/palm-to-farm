import React from 'react';
import PropTypes from 'prop-types';
import './SideBar.css'
import TextCenter from '../TextCenter/TextCenter';
import { MdDeviceHub, MdTimeline, MdPermIdentity } from 'react-icons/lib/md';
import { Link } from 'react-router-dom';

const SideBar = ({selected, changeFunc}) => {
    let sidebar=['','',''];
    sidebar[selected]=' selected';
    return (
        <div id="sidebar-wrapper">
            <div>
                <Link to="/main" onClick={()=>changeFunc(0)}>
                    <div className={"sidebar-item"+sidebar[0]}>
                        <MdDeviceHub size="20" />
                    </div>
                </Link>
                <Link to="/main" onClick={()=>changeFunc(1)}>
                    <div className={"sidebar-item"+sidebar[1]}>
                        <MdTimeline size="20" />
                    </div>
                </Link>
                <Link to="/main" onClick={()=>changeFunc(2)}>
                    <div className={"sidebar-item"+sidebar[2]}>
                        <MdPermIdentity size="20" />
                    </div>
                </Link>
            </div>
        </div>
    )
};

SideBar.propTypes = {

}

export default SideBar;