import React from 'react';
import './PageContent.css'

const PageContent = ({ children }) => {
    return (
        <div id="page-content-wrapper">
            {children}
        </div>
    )
};

export default PageContent;