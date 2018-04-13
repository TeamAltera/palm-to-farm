import { Map } from "immutable";
import { handleActions, createAction } from "redux-actions";

/**
 * 프로젝트의 전체적인 상태 관리
 */

const SET_HEADER_VISIBILITY = "base/SET_HEADER_VISIBILITY"; // 헤더 렌더링 여부 설정
const SET_ISLOGIN = "base/SET_ISLOGIN"; //로그인 여부 설정

export const setHeaderVisibility = createAction(SET_HEADER_VISIBILITY); // visible

const initialState = Map({
  header: Map({
    visible: true
  })
});

export default handleActions(
  {
    [SET_HEADER_VISIBILITY]: (state, action) =>
      state.setIn(["header", "visible"], action.payload)
  },
  initialState
);
