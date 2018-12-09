import React, { Fragment } from 'react';
import './Container.css'

const Container = ({ children }) => (
    <Fragment>
        <div id="SigninWrapper">
            <section className="style__basicLayout__page ">
                {children}
            </section>
        </div>
    </Fragment>
);

export default Container;
