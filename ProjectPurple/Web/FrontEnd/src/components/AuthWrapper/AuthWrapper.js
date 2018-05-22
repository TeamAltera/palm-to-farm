import React, { Fragment } from 'react';
import './AuthWrapper.css';

const AuthWrapper = ({ children }) => (
  <Fragment className="AuthWrapper">{children}</Fragment>
);

export default AuthWrapper;
