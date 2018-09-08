//action, action creator, reducer가 모두 포함

import { createAction, handleActions } from 'redux-actions';
import { pender } from 'redux-pender';
import * as LogApi from '../../lib/spring_api/log';
import * as DeviceApi from '../../lib/spring_api/device';
import { Map, List } from 'immutable';

//action 경로
const GET_LOG = 'log/GET_LOG';
const ADD_LOG = 'log/ADD_LOG';
const SEND_COMMAND = 'log/SEND_COMMAND';
const CHANGE_FIRST_LOG='log/CHANGE_FIRST_LOG';
// const CLEAR_LOG = 'log/CLEAR_LOG';

//action 생성
export const getDataset = createAction(GET_LOG, LogApi.getDataset);
export const addLog = createAction(ADD_LOG);
// export const clearLog = createAction(CLEAR_LOG);
export const sendCommand = createAction(SEND_COMMAND, DeviceApi.sendCommand);
export const changeFirstLog =createAction(CHANGE_FIRST_LOG);

//state 정의
const initialState = Map({
  logset: List([]),
});

//action에 따른 리듀서 수행동작
export default handleActions(
  {

    [ADD_LOG]: (state, action) => {
      return state.update('logset', logset=>logset.unshift(Map(action.payload)))
    },

    [CHANGE_FIRST_LOG]: (state, action) => {
      return state.update('logset', logset=>logset.setIn([0,'result'],action.payload));
    },

    // [CLEAR_LOG]: (state, action) => {
    //   return state.set('logset', List([]))
    // },

    ...pender({
      type: GET_LOG,
      onSuccess: (state, action) => {
        if (action.payload.data.data.length !== 0) {
          return state.set('logset', List(action.payload.data.data));
        }
        else {
          return state.set('logset', List([]));
        }
      }
    }),

    ...pender({
      type: SEND_COMMAND,
      onSuccess: (state, action) =>{
        return state.update('logset', logset=>logset.setIn([0,'result'],action.payload.data.status==='OK'?'T':'F'))
      },
      onFailure: (state, action) => {
        return state.update('logset', logset=>logset.setIn([0,'result'],'F'))
      }
    }),
  },
  initialState
);
