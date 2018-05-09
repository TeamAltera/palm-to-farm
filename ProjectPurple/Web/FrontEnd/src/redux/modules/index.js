import { combineReducers } from 'redux';
import base from './base';
import auth from './auth';
import user from './user';
import { penderReducer } from 'redux-pender';

/**
 * 리듀서를 이 곳에서 하나로 합쳐 내보내기
 */

export default combineReducers({
  base,
  auth,
  user,
  pender: penderReducer,
});
