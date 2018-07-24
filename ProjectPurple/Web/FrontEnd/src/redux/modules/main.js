//action, action creator, reducer가 모두 포함

import { createAction, handleActions } from 'redux-actions';
import { pender } from 'redux-pender';
import * as AuthApi from '../../lib/spring_api/auth';
import { Map } from 'immutable';

//action 경로
const CHANGE_TOGGLE_STATE = 'auth/CHANGE_TOGGLE_STATE'; // input 값 변경

//action 생성
export const changeToggleState = createAction(CHANGE_TOGGLE_STATE);

//state 정의
const initialState = Map({
    toggleState: false,
});

//action에 따른 리듀서 수행동작
export default handleActions(
    {
        //changeInput action 수행 시
        [CHANGE_TOGGLE_STATE]: (state, action) => {
            return state.set('toggleState', action.payload);
        },

    },
    initialState
);

