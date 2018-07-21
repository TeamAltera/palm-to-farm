import React, { Component } from 'react';
import { Route } from 'react-router';
import { Signin , Signup, Main} from '../pages';

class App extends Component {

  render() {
    //URL에 따른 분기
    return (
      <div>
        <Route exact path="/" component={Signin} />
        <Route path="/signup" component={Signup} />
        <Route path="/main" component={Main} />
      </div>
    );
  }
}

export default App;
