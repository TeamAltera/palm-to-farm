import React, { Component } from "react";
import "./App.css";
import { Route } from "react-router-dom";
import { Home, Login, Signup, Find } from "../pages";

class App extends Component {
  render() {
    return (
      <div className="App">
        <Route exact path="/" component={Login} />

        {/* <Route path="/login" component={Login} /> */}
        <Route path="/signup" component={Signup} />
        <Route path="/find" component={Find} />
      </div>
    );
  }
}

export default App;
