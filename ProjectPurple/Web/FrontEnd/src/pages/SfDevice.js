import React, { Component, Fragment } from 'react';
import { SfDeviceContainer } from '../containers';
import {
  Redirect
} from 'react-router-dom'

class SfDevice extends Component {
  render() {
    const logged=localStorage.getItem('jwtToken');
    return (
      <Fragment>
        {/* {!logged && <Redirect to='/'/>} */}
        <SfDeviceContainer/>
      </Fragment>
    );
  }
}

export default SfDevice;
