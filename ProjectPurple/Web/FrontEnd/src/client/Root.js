import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import App from '../shared/App';
import { Provider } from 'react-redux';

/**
 * BrowserRouter를 통한 라우터 구성
 */

const Root = ({ store }) => (
  <Provider store={store}>
    <BrowserRouter>
      <Route path="/" component={App} />
    </BrowserRouter>
  </Provider>
);

export default Root;
