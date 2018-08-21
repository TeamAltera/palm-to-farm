import React, { Fragment } from 'react';
import './FormHeader.css'

const FormHeader = ({ children }) => (
    <Fragment>
        <div className="FormHeader-back"></div>
        <div className="Form-title mt-2">
            <h3>
                <span className="blue">S</span>
                mart
            <span className="blue"> P</span>
                lant
            </h3>
        </div>
    </Fragment>
);

export default FormHeader;
