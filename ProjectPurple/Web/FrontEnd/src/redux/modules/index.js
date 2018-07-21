import { combineReducers } from 'redux';
import auth from './auth';
import { penderReducer } from 'redux-pender';

/**
 * 여러개로 분리된
 * 서브 리듀서를 이 곳에서 하나로 합쳐 내보내기
 */

export default combineReducers({
  auth,
  pender: penderReducer,
});
