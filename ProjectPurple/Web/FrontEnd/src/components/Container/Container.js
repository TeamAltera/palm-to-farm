import React, { Fragment } from 'react';

const Container = ({ children }) => (
    <Fragment>
        <div className="container">
            {children}
        </div>
    </Fragment>
);

export default Container;
