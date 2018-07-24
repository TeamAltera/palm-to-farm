import React from 'react';
import './PageContent.css'

const PageContent = ({ children }) => {
    return (
        <div id="page-content-wrapper">
            <div className="container-fluid">
                {children}
            </div>
        </div>
    )
};

export default PageContent;