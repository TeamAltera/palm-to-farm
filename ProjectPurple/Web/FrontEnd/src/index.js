import React from 'react';
import ReactDOM from 'react-dom';
import Root from './client/Root';
import registerServiceWorker from './registerServiceWorker';
import './index.css';
import configureStore from './redux/configureStore';
import 'semantic-ui-css/semantic.min.css';
import Promise from 'promise-polyfill';

//promise-polyfill - axios IE8부터 적용
if (!window.Promise) {
  window.Promise = Promise;
}

const store = configureStore();

ReactDOM.render(<Root store={store} />, document.getElementById('root'));
registerServiceWorker();
