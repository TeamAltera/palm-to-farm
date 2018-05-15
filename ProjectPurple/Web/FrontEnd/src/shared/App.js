import React, { Component } from 'react';
import './App.css';
import { Route } from 'react-router-dom';
import { Home, Login, Signup, Find, Graph } from '../pages';

import storage from 'lib/storage';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as userActions from 'redux/modules/user';

/**
 * 라우트 정의
 */

class App extends Component {
  initializeUserInfo = async () => {
    const loggedInfo = storage.get('loggedInfo'); // 로그인 정보를 로컬스토리지에서 가져옵니다.
    if (!loggedInfo) return; // 로그인 정보가 없다면 여기서 멈춥니다.

    const { UserActions } = this.props;
    UserActions.setLoggedInfo(loggedInfo);
    //TODO 로그인유지중인지
    // try {
    //   await UserActions.checkStatus();
    // } catch (e) {
    //   storage.remove('loggedInfo');
    //   window.location.href = '/auth/login?expired';
    // }
  };

  componentDidMount() {
    this.initializeUserInfo();
  }

  render() {
    return (
      <div className="App">
        <Route exact path="/" component={Login} />
        <Route path="/signup" component={Signup} />
        <Route path="/find" component={Find} />
        <Route path="/home" component={Home} />
        <Route path="/graph" component={Graph} />
      </div>
    );
  }
}
export default connect(null, dispatch => ({
  UserActions: bindActionCreators(userActions, dispatch),
}))(App);
