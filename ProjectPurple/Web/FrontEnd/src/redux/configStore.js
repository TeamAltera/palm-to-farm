import { createStore, applyMiddleware} from 'redux';
import modules from './modules';
import penderMiddleware from 'redux-pender';

/**
 * Store를 생성하는 곳
 */

const configStore = initialState => {
  const store = createStore(
    modules,
    initialState,
    applyMiddleware(penderMiddleware()),
  );
  return store;
};

export default configStore;