import React from 'react';

const PageBody = ({ children }) => {
    return (
        <div className="container mt-3">
            <div className="row">
                {children}
            </div>
        </div>
    )
};

export default PageBody;