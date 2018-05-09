import { createAction, handleActions } from 'redux-actions';
import { pender } from 'redux-pender';
import * as AuthAPI from '../../lib/api/auth';
import { Map } from 'immutable';

const CHANGE_INPUT = 'auth/CHANGE_INPUT'; // input 값 변경
const INITIALIZE_FORM = 'auth/INITIALIZE_FORM'; // form 초기화
const CHECK_EMAIL_EXISTS = 'auth/CHECK_EMAIL_EXISTS'; // 이메일 중복 확인
const SIGNUP = 'auth/SIGNUP'; // 이메일 가입
const LOGIN = 'auth/LOGIN'; // 이메일 로그인
const LOGOUT = 'auth/LOGOUT'; // 로그아웃
const SET_ERROR = 'auth/SET_ERROR'; // 오류 설정

export const changeInput = createAction(CHANGE_INPUT); //  { form, name, value }
export const initializeForm = createAction(INITIALIZE_FORM); // form
export const checkEmailExists = createAction(
  CHECK_EMAIL_EXISTS
  //AuthAPI.checkEmailExists
); // email
export const doSignup = createAction(SIGNUP, AuthAPI.signup); // { email, username, password }
export const doSignin = createAction(LOGIN, AuthAPI.signin); // { email, password }
export const doLogout = createAction(LOGOUT, AuthAPI.logout);
export const setError = createAction(SET_ERROR); // { form, message }

const initialState = Map({
  signup: Map({
    form: Map({
      email: '',
      username: '',
      password: '',
      passwordConfirm: '',
    }),
    exists: Map({
      email: false,
      password: false,
    }),
    error: null,
  }),
  login: Map({
    form: Map({
      email: '',
      password: '',
    }),
    error: null,
  }),
  result: Map({}),
});

export default handleActions(
  {
    [CHANGE_INPUT]: (state, action) => {
      const { form, name, value } = action.payload;
      return state.setIn([form, 'form', name], value);
    },
    [INITIALIZE_FORM]: (state, action) => {
      const initialForm = initialState.get(action.payload);
      return state.set(action.payload, initialForm);
    },
    [SET_ERROR]: (state, action) => {
      const { form, message } = action.payload;
      return state.setIn([form, 'error'], message);
    },
    ...pender({
      type: CHECK_EMAIL_EXISTS,
      onSuccess: (state, action) =>
        state.setIn(
          ['register', 'exists', 'email'],
          action.payload.data.exists
        ),
    }),
    ...pender({
      type: LOGIN,
      onSuccess: (state, action) =>
        state.set('result', Map(action.payload.data)),
    }),
    ...pender({
      type: SIGNUP,
      onSuccess: (state, action) =>
        state.set('result', Map(action.payload.data)),
    }),
  },
  initialState
);
