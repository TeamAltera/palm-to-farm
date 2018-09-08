//action, action creator, reducer가 모두 포함

import { createAction, handleActions } from 'redux-actions';
import { pender } from 'redux-pender';
import * as DeviceApi from '../../lib/spring_api/device';
import * as AuthApi from '../../lib/spring_api/auth';
import { Map,List } from 'immutable';

//action 경로
const CHANGE_POPOVER_STATE = 'main/CHANGE_POPOVER_STATE';
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
const GET_USER_INFO = 'main/GET_USER_INFO';
const CHANGE_SELECTED_SF = 'main/CHANGE_SELECTED_SF';
const CHANGE_SELECTED_SB = 'main/CHANGE_SELECTED_SB';
const ADD_ITEM = 'main/ADD_ITEM';
const INCREMENT_SF_CNT = 'main/INCREMENT_SF_CNT'

//action 생성
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
export const getUserInfo = createAction(GET_USER_INFO, AuthApi.getUserInfo);
export const changeSelectedSf = createAction(CHANGE_SELECTED_SF);
export const changeSelectedSb = createAction(CHANGE_SELECTED_SB);
export const addItem = createAction(ADD_ITEM);
export const incrementSfCnt = createAction(INCREMENT_SF_CNT);

//state 정의
const initialState = Map({
    sidebarSelected:0,
    user: null,
    sfToggle: Map({
        sfToggleState: false,
        selectedAp: Map({
            apName: '',
            apCode: '',
            count: 0,
            apIp: '',
            regDate:'',
            plantDevices: List([]),
        }),
    }),
    selectedSf: Map({
        // sfCode: '',
        // count: 0,
        // sfIp: '',
        // pumpSt: '',
        // ledSt: '',
        // coolerSt
    }),
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
    deviceInfo: List([]),
    result: Map({}),
    isConfirm: false,
});

//action에 따른 리듀서 수행동작
export default handleActions(
    {
        [ADD_ITEM]: (state, action) => {
            return state.updateIn(['deviceInfo',action.payload.index,'plantDevices'],
            plantDevices=>plantDevices.concat(action.payload.data));
        },

        [INCREMENT_SF_CNT]: (state, action) => {
            // if(state.get('deviceInfo')[action.payload.index])
                return state.updateIn(['deviceInfo',action.payload.index,'apSfCnt'],action.payload.count);
        },

        [CHANGE_SELECTED_SB]: (state, action) => {
            return state.set('sidebarSelected', action.payload);
        },

        [CHANGE_SF_TOGGLE_STATE]: (state, action) => {
            return state.set('sfToggle', Map(action.payload));
        },

        [CHANGE_SELECTED_SF]: (state, action) => {
            return state.set('selectedSf', Map(action.payload));
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
            onSuccess: (state, action) => {
                if (action.payload.data.status === 'OK'){
                    return state.set('deviceInfo', List(action.payload.data.data))
                }
                else
                    return state.set('result', Map(action.payload.data))
            }
        }),

        ...pender({
            type: DELETE_SINGLE_ROUTER,
            onSuccess: (state, action) => {
                if (action.payload.data.status === 'OK')
                    return state.set('deviceInfo', List(action.payload.data.data.deviceInfo))
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
                    return state.set('deviceInfo', List(action.payload.data.data))
                else
                    return state.set('result', Map(action.payload.data))
            }
        }),

        ...pender({
            type: GET_USER_INFO,
            onSuccess: (state, action) => {
                return state.set('user', Map(action.payload.data.data))
            }
        }),

    },
    initialState
);

