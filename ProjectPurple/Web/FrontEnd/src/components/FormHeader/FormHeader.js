import React from 'react';
import './FormHeader.css';

const AuthHeader = ({ children }) => (
    <div className="card-header">
        <div className="FormHeader-text">
            <h4>{children}</h4>
        </div>
    </div>
);

export default AuthHeader;