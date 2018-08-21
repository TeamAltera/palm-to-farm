import React, { Component, Fragment } from 'react';
import { SigninContainer } from '../containers';
import {
  Redirect
} from 'react-router-dom'

class Signin extends Component {
  render() {
    // const logged=localStorage.getItem('jwtToken');
    return (    
      <Fragment>
        {/* {logged && <Redirect to='/main'/>} */}
        <SigninContainer/>
      </Fragment>
    );
  }
}

export default Signin;
