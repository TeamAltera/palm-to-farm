import React from 'react';
import './Header.css'
import { PageLink } from '../index';

const Header = ({ value, option, onClick }) => {
    //navbar-absolute 
    return (
        <nav className="absolute-top navbar navbar-expand-lg bg-transparent">
            <div className="container-fluid">
                <div className="navbar-wrapper">
                    <button type="button" className="btn btn-default" aria-label="Left Align"
                        onClick={onClick}
                    >
                        <span className="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
                    </button>
                    <a href="/main" className="navbar-brand">Dashboard</a>
                </div>
            </div>
        </nav>
    )
};

export default Header;