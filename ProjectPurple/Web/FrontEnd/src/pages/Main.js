import React, { Component, Fragment } from 'react';
import { MainContainer } from '../containers';
import {
  Redirect
} from 'react-router-dom'

class Main extends Component {
  render() {
    const logged=localStorage.getItem('jwtToken');
    return (
      <Fragment>
        {/* {!logged && <Redirect to='/'/>} */}
        <MainContainer/>
      </Fragment>
    );
  }
}

export default Main;
