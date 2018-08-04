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
const RES_INIT = 'main/RES_INIT';
const INITIALIZE_FORM = 'main/INITIALIZE_FORM'; // form 초기화
const CHANGE_CONFIRM = 'main/CHANGE_CONFIRM'; // form 초기화
const CHANGE_SF_TOGGLE_STATE = 'main/CHANGE_SF_TOGGLE_STATE';
const CHANGE_SELECTED_AP='main/CHANGE_SELECTED_AP';

//action 생성
export const changeToggleState = createAction(CHANGE_TOGGLE_STATE);
export const changeSfToggleState = createAction(CHANGE_SF_TOGGLE_STATE);
export const changePopoverState = createAction(CHANGE_POPOVER_STATE);
export const getDeviceAllInfo = createAction(GET_DEVICE_ALL_INFO, DeviceApi.getDeviceAllInfo);
export const changeModalsState = createAction(CHANGE_MODALS_STATE);
export const changeDropdownState = createAction(CHANGE_DROPDOWN_STATE);
export const deleteAllRouter = createAction(DELETE_ALL_ROUTER);
export const deleteSingleRouter = createAction(DELETE_SINGLE_ROUTER, DeviceApi.deleteSingleRouter);
export const changeInput = createAction(CHANGE_INPUT);
export const searchRouter = createAction(SEARCH_ROUTER, DeviceApi.searchRouter);
export const addRouter = createAction(ADD_ROUTER, DeviceApi.addRouter);
export const resInit = createAction(RES_INIT);
export const initializeForm = createAction(INITIALIZE_FORM); // form
export const changeConfirm = createAction(CHANGE_CONFIRM);
export const changeSelectedAp = createAction(CHANGE_SELECTED_AP);

//state 정의
const initialState = Map({
    sfToggleState: true,
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
    deviceInfo: Map({}),
    result: Map({}),
    isConfirm: false,
    selectedAp: Map({
        apName: '',
        apCode: '',
    }),
});

//action에 따른 리듀서 수행동작
export default handleActions(
    {
        [CHANGE_SELECTED_AP]: (state, action) => {
            return state.set('selectedAp', action.payload);
        },

        [CHANGE_TOGGLE_STATE]: (state, action) => {
            return state.set('toggleState', action.payload);
        },

        [CHANGE_SF_TOGGLE_STATE]: (state, action) => {
            return state.set('sfToggleState', action.payload);
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

        [CHANGE_CONFIRM]: (state, action) => {
            return state.set('isConfirm', action.payload);
        },

        [RES_INIT]: (state, action) => {
            return state.set('result', null);
        },

        [INITIALIZE_FORM]: (state, action) => {
            const initialForm = initialState.get('ip');
            return state.set('ip', initialForm);
        },

        ...pender({
            type: GET_DEVICE_ALL_INFO,
            onSuccess: (state, action) =>{
                if (action.payload.data.status === 'OK')
                    return state.set('deviceInfo', Map(action.payload.data))
                else
                    return state.set('result', Map(action.payload.data))
            }
        }),

        ...pender({
            type: DELETE_SINGLE_ROUTER,
            onSuccess: (state, action) =>{
                if (action.payload.data.status === 'OK')
                    return state.set('deviceInfo', Map(action.payload.data))
                else
                    return state.set('result', Map(action.payload.data))
            }
        }),

        ...pender({
            type: SEARCH_ROUTER,
            onSuccess: (state, action) =>
                state.set('result', Map(action.payload.data)),
        }),

        ...pender({
            type: ADD_ROUTER,
            onSuccess: (state, action) => {
                if (action.payload.data.status === 'OK')
                    return state.set('deviceInfo', Map(action.payload.data))
                else
                    return state.set('result', Map(action.payload.data))
            }
        }),

    },
    initialState
);

