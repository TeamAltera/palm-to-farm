import { combineReducers } from 'redux';
import auth from './auth';
import main from './main';
import sensor from './sensor';
import plant from './plant';
import base from './base';
import log from './log';
import { penderReducer } from 'redux-pender';

/**
 * 여러개로 분리된
 * 서브 리듀서를 이 곳에서 하나로 합쳐 내보내기
 */

export default combineReducers({
  auth,
  main,
  sensor,
  plant,
  base,
  log,
  pender: penderReducer,
});
