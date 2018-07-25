//action, action creator, reducer가 모두 포함

import { createAction, handleActions } from 'redux-actions';
import { pender } from 'redux-pender';
import * as AuthApi from '../../lib/spring_api/auth';
import { Map } from 'immutable';

//action 경로
const CHANGE_TOGGLE_STATE = 'main/CHANGE_TOGGLE_STATE'; // input 값 변경
const CHANGE_POPOVER_STATE = 'main/CHANGE_POPOVER_STATE'; // input 값 변경

//action 생성
export const changeToggleState = createAction(CHANGE_TOGGLE_STATE);
export const changePopoverState = createAction(CHANGE_POPOVER_STATE);

//state 정의
const initialState = Map({
    toggleState: false,
    popoverState: false,
});

//action에 따른 리듀서 수행동작
export default handleActions(
    {
        [CHANGE_TOGGLE_STATE]: (state, action) => {
            return state.set('toggleState', action.payload);
        },

        [CHANGE_POPOVER_STATE]: (state, action) => {
            return state.set('popoverState', action.payload);
        },

    },
    initialState
);

