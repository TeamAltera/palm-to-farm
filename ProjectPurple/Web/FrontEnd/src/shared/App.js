import React, { Component } from "react";
import "./App.css";
import { Route } from "react-router-dom";
import { Home, Login, Signup, Find } from "../pages";

/**
 * 라우트 정의
 */

class App extends Component {
  render() {
    return (
      <div className="App">
        <Route exact path="/" component={Login} />
        <Route path="/signup" component={Signup} />
        <Route path="/find" component={Find} />
        <Route path="/home" component={Home} />
      </div>
    );
  }
}

export default App;
