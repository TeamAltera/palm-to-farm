import React from 'react';
import './PageBody.css'

const PageBody = ({ children }) => {
    return (
        <div id="PageBody">
            <div className={"container-fluid mt-3"}>
                <div className="row" id="nopadding">
                    {children}
                </div>
            </div>
        </div>
    )
};

export default PageBody;