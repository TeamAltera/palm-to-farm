import React, { Fragment } from 'react';
import './Container.css'

const Container = ({ children }) => (
    <Fragment>
        <div id="SigninWrapper">
            {children}
        </div>
    </Fragment>
);

export default Container;
