import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import { Provider  } from 'react-redux';
import App from '../shared/App';

/*
    라우터 구성
    src/shared/App.js로
*/
const Root = ({store}) => (
    <Provider store={store}>
        <BrowserRouter>
            <Route path="/" component={App} />
        </BrowserRouter>
    </Provider>
);

export default Root;
