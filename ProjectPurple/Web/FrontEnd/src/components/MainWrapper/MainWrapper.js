import React from 'react';
import './MainWrapper.css'
import PropTypes from 'prop-types';

const MainWrapper = ({ children, option }) => {
    let toggled = "";
    if (option)
        toggled = "toggled";
    return (
        <div id="MainWrapper" className={toggled}>
            {children}
        </div>
    )
};

MainWrapper.propTypes = {
    option: PropTypes.bool.isRequired,
}

export default MainWrapper;