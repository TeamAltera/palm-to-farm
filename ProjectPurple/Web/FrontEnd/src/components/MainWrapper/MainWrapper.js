import React from 'react';
import './MainWrapper.css'

const MainWrapper = ({ children }) => {
    return (
        <div id="MainWrapper">
            {children}
        </div>
    )
};

export default MainWrapper;