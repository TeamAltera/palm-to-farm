//action, action creator, reducer가 모두 포함

import { createAction, handleActions } from 'redux-actions';
import { pender } from 'redux-pender';
import * as AuthApi from '../../lib/spring_api/auth';
import { Map } from 'immutable';

//action 경로
const CHANGE_INPUT = 'auth/CHANGE_INPUT'; // input 값 변경
const INITIALIZE_FORM = 'auth/INITIALIZE_FORM'; // form 초기화
const SIGNIN = 'auth/SIGNIN'; // 이메일 로그인
const SIGNUP = 'auth/SIGNUP'; // 이메일 가입
const SET_CURRENT_USER = 'auth/SET_CURRENT_USER'; //토큰으로 부터 사용자 정보 설정
const SET_ERROR = 'auth/SET_ERROR'; // 오류 설정
const SET_AUTENTICATED = 'auth/AUTENTICATED';//인증 여부 설정
const CHANGE_PASSWORD_SCORE = 'auth/CHANGE_PASSWORD_SCORE';//인증 여부 설정
const CHECK_EMAIL_EXISTS = 'auth/CHECK_EMAIL_EXISTS'; // 이메일 중복 확인
const CHANGE_EMAIL_EXISTS = 'auth/CHANGE_EMAIL_EXISTS';// emailExists 초기화
const CHANGE_PASSWORD_CONFIRM='auth/CHANGE_PASSWORD_CONFIRM';
const SEND_AUTHCODE='auth/SEND_AUTHCODE'; // 인증코드 이메일 전송
const CHECK_AUTHCODE='auth/CHECK_AUTHCODE';// 인증코드 확인
const SET_SPINNER_LOADING='auth/SET_SPINNER_LOADING'
const SET_AUTHCODE_CONFIRM='auth/SET_AUTHCODE_CONFIRM'

//action 생성
export const changeInput = createAction(CHANGE_INPUT); //  { form, name, value }
export const initializeForm = createAction(INITIALIZE_FORM); // form
export const doSignin = createAction(SIGNIN, AuthApi.signin); // { email, password }
export const doSignup = createAction(SIGNUP, AuthApi.signup); // { email, username, password }
export const setError = createAction(SET_ERROR); // { form, message }
export const setCurrentUser = createAction(SET_CURRENT_USER);
export const setAutenticated = createAction(SET_AUTENTICATED);
export const changePasswordScore = createAction(CHANGE_PASSWORD_SCORE);
export const checkEmailExists = createAction(
  CHECK_EMAIL_EXISTS,AuthApi.checkEmailExists
); // email
export const changeEmailExists = createAction(CHANGE_EMAIL_EXISTS); // email
export const changePasswordConfirm = createAction(CHANGE_PASSWORD_CONFIRM); // password confirm
export const sendAuthCode = createAction(SEND_AUTHCODE,AuthApi.sendAuthCode); // email
export const checkAuthCode = createAction(CHECK_AUTHCODE,AuthApi.checkAuthCode); // email
export const setSpinnerLoading= createAction(SET_SPINNER_LOADING);
export const setAuthCodeConfirm= createAction(SET_AUTHCODE_CONFIRM);

//state 정의
const initialState = Map({
  signin: Map({
    form: Map({
      email: '',
      password: '',
    }),
    error: null,
    isAutenticated: false,
    spinnerLoading:false,
  }),
  signup: Map({
    form: Map({
      email: '',
      firstName: '',
      secondName: '',
      password: '',
      passwordConfirm: '',
      authCode: '',
    }),
    passwordScore:0,
    isAutenticated: false,
    spinnerLoading:false,
    isPasswordConfirm: 0,
    isAuthCodeConfirm: 6,
    isExists: 0,
    error: Map({
      msg:null,
      locaction:0,
    }),
  }),
  result: Map({}),
  user: null,
});

//action에 따른 리듀서 수행동작
export default handleActions(
  {
    //changeInput action 수행 시
    [CHANGE_INPUT]: (state, action) => {
      const { form, name, value } = action.payload;
      //signin -> form -> name(email 또는 password 둘 중 일치하는 값, 문자열)에 입력받은 value값 설정
      return state.setIn([form, 'form', name], value);
    },

    //initializeForm action 수행 시
    [INITIALIZE_FORM]: (state, action) => {
      const initialForm = initialState.get(action.payload);
      return state.set(action.payload, initialForm);
    },


    [SET_ERROR]: (state, action) => {
      const { form, error } = action.payload;
      return state.setIn([form, 'error'], Map(error));
    },

    [SET_CURRENT_USER]: (state, action) => {
      const { user } = action.payload;
      return state.set('user', Map(user));
    },

    [SET_AUTENTICATED]: (state, action) => {
      const { form, autenticated } = action.payload;
      return state.setIn([form, 'isAutenticated'], autenticated);
    },

    [CHANGE_PASSWORD_SCORE]: (state, action) => {
      return state.setIn(['signup', 'passwordScore'], action.payload);
    },

    [CHANGE_EMAIL_EXISTS]: (state, action) => {
      return state.setIn(['signup', 'isExists'], action.payload);
    },

    [CHANGE_PASSWORD_CONFIRM]: (state, action) => {
      return state.setIn(['signup', 'isPasswordConfirm'], action.payload);
    },

    [SET_SPINNER_LOADING]: (state, action) => {
      return state.setIn(['signup', 'spinnerLoading'], action.payload);
    },

    [SET_AUTHCODE_CONFIRM]: (state, action) => {
      return state.setIn(['signup', 'isAuthCodeConfirm'], action.payload);
    },

    ...pender({
      type: SIGNIN,
      //signin 요청이 성공했을 때 수행하는 작업
      onSuccess: (state, action) =>
        state.set('result', Map(action.payload.data)),
    }),

    ...pender({
      type: SIGNUP,
      onSuccess: (state, action) =>
        state.set('result', Map(action.payload.data)),
    }),

    ...pender({
      type: CHECK_EMAIL_EXISTS,
      onSuccess: (state, action) =>{
        return state.setIn(
          ['signup', 'isExists'], action.payload.data.data);
        }
    }),

    ...pender({
      type: SEND_AUTHCODE,
      onFairue: (state, action)  =>
        state.setIn(['signup', 'error'], {
          msg: '인증 이메일 전송이 실패하였습니다.',
          location: 1
        }),
      onSuccess: (state) =>
        state.setIn(['signup', 'isAutenticated'], true),
    }),

    ...pender({
      type: CHECK_AUTHCODE,
      onSuccess: (state, action) =>
        state.set('result', Map(action.payload.data)),
    }),
  },
  initialState
);
