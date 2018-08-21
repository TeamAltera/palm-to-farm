import React, { Component } from 'react';
import { Route, Switch } from 'react-router';
import { Signin, Signup, Main, NotFound, SfDevice } from '../pages';

class App extends Component {

  render() {
    //URL에 따른 분기
    return (
      <div>
        <Switch>
          <Route exact path="/" component={Signin} />
          <Route path="/signup" component={Signup} />
          <Route path="/main" component={Main} />
          <Route path="/sf_device" component={SfDevice} />
          <Route component={NotFound} />
        </Switch>
      </div>
    );
  }
}

export default App;
