import React from 'react';
import './PageBody.css'

const PageBody = ({ children }) => {
    return (
        <div id="PageBody">
            <div className={"container mt-3"}>
                <div className="row">
                    {children}
                </div>
            </div>
        </div>
    )
};

export default PageBody;