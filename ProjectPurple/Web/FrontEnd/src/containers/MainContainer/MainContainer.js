import React, { Component } from 'react';
import { Button, Form, Modal, Image } from 'semantic-ui-react';
import { MainWrapper, DashBoard } from '../../components';
import raz_router from '../../assets/images/raz_router.png';
import * as MainApi from '../../lib/api/main';
import { Link } from 'react-router-dom';

class MainContainer extends Component {
  render() {
    return (
      <MainWrapper>
        <DashBoard />
      </MainWrapper>
    );
  }
}

export default MainContainer;
