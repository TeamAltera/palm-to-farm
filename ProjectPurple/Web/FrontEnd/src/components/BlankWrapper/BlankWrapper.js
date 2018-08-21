import React from 'react';
import './BlankWrapper.css';

const BlankWrapper = ({children, blocking}) => {
    return (
        <div id="BlankWrapper" className={!blocking?"BlankWrapper-hide":""}>
            {children}
        </div>
    )
};

export default BlankWrapper;
