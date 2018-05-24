import React, { Component, Fragment } from 'react';
import { LoginContainer } from '../containers';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as baseActions from 'redux/modules/base';

class Login extends Component {
  // 페이지에 진입 할 때 헤더를 비활성화
  componentWillMount() {}

  // 페이지에서 벗어 날 때 다시 활성화
  componentWillUnmount() {}

  render() {
    return (
      <Fragment>
        <LoginContainer />
      </Fragment>
    );
  }
}

export default connect(
  state => ({}),
  dispatch => ({
    BaseActions: bindActionCreators(baseActions, dispatch),
  })
)(Login);
