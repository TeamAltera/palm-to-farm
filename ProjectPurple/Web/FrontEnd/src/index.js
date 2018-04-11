import React from "react";
import ReactDOM from "react-dom";
import Root from "./client/Root";
import registerServiceWorker from "./registerServiceWorker";
import "./index.css";
import "semantic-ui-css/semantic.min.css";
import Promise from "promise-polyfill";

if (!window.Promise) {
  window.Promise = Promise;
}

ReactDOM.render(<Root />, document.getElementById("root"));
registerServiceWorker();
