import React from 'react';
import ReactDOM from 'react-dom';
import Root from './client/Root';
import configStore from './redux/configStore';
import registerServiceWorker from './registerServiceWorker';
import './include/bootstrap';
import Promise from 'promise-polyfill';
import setAuthorizationToken from './utils/setAuthorizationToken';
import { setCurrentUser } from './redux/modules/auth';
import jwt from 'jsonwebtoken';
import './index.css'

/*
    리덕스 스토어 구성
    렌더링
    src/client/Root.js로
*/

//구형 브라우저에서 Promise기능 호환
if(!window.Promise){
    window.Promise=Promise;
}

//리덕스 스토어 구성
const store = configStore();

localStorage.removeItem('jwtToken');

//인증 토큰 구성
if(localStorage.jwtToken){
    console.log('init token');
    setAuthorizationToken(localStorage.jwtToken);
    store.dispatch(setCurrentUser(jwt.decode(localStorage.jwtToken)));
}

ReactDOM.render(<Root store={store}/>, document.getElementById('root'));
registerServiceWorker();

