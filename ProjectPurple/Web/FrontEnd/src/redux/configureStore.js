import { createStore, applyMiddleware, compose } from 'redux';
import modules from './modules';
import penderMiddleware from 'redux-pender';

/**
 * Store를 생성하는 곳
 * Todo: 미들웨어
 */
const isDevelopment = process.env.NODE_ENV === 'development'; // 환경이 개발모드인지 확인합니다
const composeEnhancers = isDevelopment
  ? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose
  : compose;

const configureStore = initialState => {
  const store = createStore(
    modules,
    initialState,
    composeEnhancers(applyMiddleware(penderMiddleware()))
  );
  return store;
};

export default configureStore;
