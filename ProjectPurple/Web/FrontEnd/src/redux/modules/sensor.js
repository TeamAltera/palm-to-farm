//action, action creator, reducer가 모두 포함

import { createAction, handleActions } from 'redux-actions';
import { pender } from 'redux-pender';
import * as SensorApi from '../../lib/spring_api/sensor';
import * as PlantApi from '../../lib/spring_api/plant';
import { Map, List } from 'immutable';
import moment from 'moment';

//action 경로
const GET_DATASET = 'sensor/GET_DATASET'; 
const GET_LAST_DATASET = 'sensor/GET_LAST_DATASET'; 
const CHANGE_START_DATE = 'sensor/CHANGE_START_DATE';
const CHANGE_DATE_PICKER = 'sensor/CHANGE_DATE_PICKER';
const BLOCK = 'sensor/BLOCK';
const CHANGE_MODALS_STATE = 'sensor/CHANGE_MODALS_STATE';
const CHANGE_INPUT = 'sensor/CHANGE_INPUT';
const RESET_FARM = 'sensor/RESET_FARM';
const CHANGE_FARM_COUNT = 'sensor/CHANGE_FARM_COUNT';
const RESET_PIPE = 'senspr/RESET_PIPE';
const CHANGE_PLANT = 'senspr/CHANGE_PLANT';
const RESET_DATA = 'sensor/RESET_DATA';
const CHANGE_TAB='sensor/CHANGE_TAB';
const GET_PORT='sensor/GET_PORT';

//action 생성
export const getDataset = createAction(GET_DATASET, SensorApi.getDataset);
export const getLastDataset =createAction(GET_LAST_DATASET, SensorApi.getLastDataset);
export const changeStartDate = createAction(CHANGE_START_DATE);
export const changeDatePickerToggle = createAction(CHANGE_START_DATE);

export const block = createAction(BLOCK);
export const changeModalsState = createAction(CHANGE_MODALS_STATE);
export const changeInput = createAction(CHANGE_INPUT);
export const resetFarm = createAction(RESET_FARM);
export const resetPipe = createAction(RESET_PIPE);
export const changeFarmingCount = createAction(CHANGE_FARM_COUNT);
export const changePlant = createAction(CHANGE_PLANT);
export const resetData = createAction(RESET_DATA);
export const changeTab = createAction(CHANGE_TAB);
export const getPortInfo = createAction(GET_PORT,PlantApi.getPortInfo);

//state 정의
const initialState = Map({
  tabSelected:0,
  startDate: moment(),
  datePickerToggle: false,
  result: Map({}),
  blocking: false,
  modalsState: false,
  farming: List([
    List(
      [Map({ status: true }), Map({ status: true }), Map({ status: true }), Map({ status: true }),
      Map({ status: true }), Map({ status: true }), Map({ status: true }), Map({ status: true })]),
    List(
      [Map({ status: true }), Map({ status: true }), Map({ status: true }), Map({ status: true }),
      Map({ status: true }), Map({ status: true }), Map({ status: true }), Map({ status: true })]),
    List(
      [Map({ status: true }), Map({ status: true }), Map({ status: true }), Map({ status: true }),
      Map({ status: true }), Map({ status: true }), Map({ status: true }), Map({ status: true })]),
    List(
      [Map({ status: true }), Map({ status: true }), Map({ status: true }), Map({ status: true }),
      Map({ status: true }), Map({ status: true }), Map({ status: true }), Map({ status: true })]),
  ]),
  farmingInfo: Map({
    farmingCount: 32,
    isConfirm: true,
  }),
  selectedPlant: 1,
  farmingResult:null,

  dataset: List(),
  lastDataset:Map()
});

//action에 따른 리듀서 수행동작
export default handleActions(
  {
    [CHANGE_TAB]: (state, action) => {
      return state.set('tabSelected', action.payload);
    },

    [CHANGE_PLANT]: (state, action) => {
      return state.set('selectedPlant', action.payload);
    },

    [CHANGE_FARM_COUNT]: (state, action) => {
      return state.set('farmingInfo', Map(action.payload));
    },

    [RESET_FARM]: (state, action) => {
      return state.set('farming', action.payload);
    },

    [RESET_PIPE]: (state, action) => {
      const { value, pipe } = action.payload;
      return state.setIn(['farming', value], pipe);
    },

    [CHANGE_INPUT]: (state, action) => {
      const { value, check } = action.payload;
      return state.setIn(['farming', value.row, value.col, 'status'], check);
    },

    [CHANGE_MODALS_STATE]: (state, action) => {
      return state.set('modalsState', action.payload);
    },

    [BLOCK]: (state, action) => {
      return state.set('blocking', action.payload);
    },

    [CHANGE_START_DATE]: (state, action) => {
      return state.set('startDate', action.payload);
    },

    [CHANGE_DATE_PICKER]: (state, action) => {
      return state.set('datePickerToggle', action.payload);
    },

    //데이터 말소
    [RESET_DATA]: (state, action) => {
      return state.set('dataset', 
        // ec: List(), //ec
        // ph: List(), //ph
        // t: List(), //온도
        // h: List(), //습도
        // wt: List(), //수온
        // wl: List(), //수위
        // e: List(), //조도
        // d: List(),//날짜
        List()
      );
    },

    ...pender({
      type: GET_DATASET,
      onSuccess: (state, action) =>
        state.set('dataset', List(action.payload.data.data)),
    }),

    ...pender({
      type: GET_LAST_DATASET,
      onSuccess: (state, action) =>
        state.set('lastDataset', action.payload.data.data),
    }),

    ...pender({
      type: GET_PORT,
      onSuccess: (state, action) =>
        state.set('farmingResult', List(action.payload.data.data)),
    }),
  },
  initialState
);
