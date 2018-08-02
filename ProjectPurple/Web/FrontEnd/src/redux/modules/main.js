//action, action creator, reducer가 모두 포함

import { createAction, handleActions } from 'redux-actions';
import { pender } from 'redux-pender';
import * as DeviceApi from '../../lib/spring_api/device';
import { Map } from 'immutable';

//action 경로
const CHANGE_TOGGLE_STATE = 'main/CHANGE_TOGGLE_STATE'; // input 값 변경
const CHANGE_POPOVER_STATE = 'main/CHANGE_POPOVER_STATE'; // input 값 변경
const GET_DEVICE_ALL_INFO = 'main/GET_DEVICE_ALL_INFO';
const CHANGE_MODALS_STATE = 'main/CHANGE_MODALS_STATE';
const CHANGE_DROPDOWN_STATE = 'main/CHANGE_DROPDOWN_STATE';
const DELETE_ALL_ROUTER = 'main/DELETE_ALL_ROUTER';
const DELETE_SINGLE_ROUTER = 'main/DELETE_SINGLE_ROUTER';
const CHANGE_INPUT = 'main/CHANGE_INPUT';
const SEARCH_ROUTER = 'main/SEARCH_ROUTER';
const ADD_ROUTER = 'main/ADD_ROUTER';

//action 생성
export const changeToggleState = createAction(CHANGE_TOGGLE_STATE);
export const changePopoverState = createAction(CHANGE_POPOVER_STATE);
export const getDeviceAllInfo = createAction(GET_DEVICE_ALL_INFO, DeviceApi.getDeviceAllInfo);
export const changeModalsState = createAction(CHANGE_MODALS_STATE);
export const changeDropdownState = createAction(CHANGE_DROPDOWN_STATE);
export const deleteAllRouter = createAction(DELETE_ALL_ROUTER);
export const deleteSingleRouter = createAction(DELETE_SINGLE_ROUTER, DeviceApi.deleteSingleRouter);
export const changeInput = createAction(CHANGE_INPUT);
export const searchRouter = createAction(SEARCH_ROUTER, DeviceApi.searchRouter);
export const addRouter = createAction(ADD_ROUTER, DeviceApi.addRouter);

//state 정의
const initialState = Map({
    toggleState: false,
    modalsState: false,
    popoverState: false,
    dropdownState: false,
    //deviceInfo: Map({}),
    ip: Map({
        a: '',
        b: '',
        c: '',
        d: ''
    }),
    result: Map({}),
});

//action에 따른 리듀서 수행동작
export default handleActions(
    {
        [CHANGE_TOGGLE_STATE]: (state, action) => {
            return state.set('toggleState', action.payload);
        },

        [CHANGE_MODALS_STATE]: (state, action) => {
            return state.set('modalsState', action.payload);
        },

        [CHANGE_DROPDOWN_STATE]: (state, action) => {
            return state.set('dropdownState', action.payload);
        },

        [CHANGE_POPOVER_STATE]: (state, action) => {
            return state.set('popoverState', action.payload);
        },

        [CHANGE_INPUT]: (state, action) => {
            const { name, value } = action.payload;
            return state.setIn(['ip', name], value);
        },

        ...pender({
            type: GET_DEVICE_ALL_INFO,
            onSuccess: (state, action) =>
                state.set('result', Map(action.payload)),
        }),

        ...pender({
            type: DELETE_SINGLE_ROUTER,
            onSuccess: (state, action) =>
                state.set('result', Map(action.payload)),
        }),

        ...pender({
            type: SEARCH_ROUTER,
            onSuccess: (state, action) =>
                state.set('result', Map(action.payload)),
        }),

        ...pender({
            type: ADD_ROUTER,
            onSuccess: (state, action) =>
                state.set('result', Map(action.payload)),
        }),

    },
    initialState
);

