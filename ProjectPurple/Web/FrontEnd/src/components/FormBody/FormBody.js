import React from 'react';
import './FormBody.css'

const FormBody = ({ children }) => (
    <div id="nopadding-top" className="FormBody">
        {children}
    </div>
);

export default FormBody;