//action, action creator, reducer가 모두 포함

import { createAction, handleActions } from 'redux-actions';
import { pender } from 'redux-pender';
import * as PlantApi from '../../lib/spring_api/plant';
import * as DeviceApi from '../../lib/spring_api/device';
import { Map } from 'immutable';

//action 경로
const GET_PLANT = 'plant/GET_PLANT'; // input 값 변경
const CHANGE_FARMING='plant/CHANGE_FARMING';

//action 생성
export const getDataset = createAction(GET_PLANT, PlantApi.getDataset);
export const changeFarming = createAction(CHANGE_FARMING, PlantApi.sendCommand);

//state 정의
const initialState = Map({
  result: Map({}),
});

//action에 따른 리듀서 수행동작
export default handleActions(
{
    ...pender({
      type: GET_PLANT,
      onSuccess: (state, action) =>
        state.set('result', Map(action.payload.data.data)),
    }),

    ...pender({
      type: CHANGE_FARMING,
      onSuccess: (state, action) =>
        state.set('result', Map(action.payload.data.data)),
    }),

  },
  initialState
);
