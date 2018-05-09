import axios from 'axios';

const DEFAULT_DOMAIN = 'http://203.250.32.180:9001/smart_plant/';
const DOMAIN_USER = 'user/';

//로그인
export const signin = (email, password) => {
  axios
    .post(DEFAULT_DOMAIN + DOMAIN_USER + 'signin', {
      email: email,
      pwd: password,
    })
    .then(function(response) {
      console.log(response);
    })
    .catch(function(error) {
      console.log(error);
    });
};

//회원가입
export const signup = (username, email, password) => {
  axios
    .post(DEFAULT_DOMAIN + DOMAIN_USER + 'signup', {
      username: username,
      email: email,
      pwd: password,
    })
    .then(function(response) {
      console.log(response);
    })
    .catch(function(error) {
      console.log(error);
    });
};

//로그아웃
export const logout = () => {
  axios
    .post(DEFAULT_DOMAIN + DOMAIN_USER + 'logout')
    .then(function(response) {
      console.log(response);
    })
    .catch(function(error) {
      console.log(error);
    });
};

//회원가입 페이지에서의 이메일 조회
export const findUser = () => {
  axios
    .post(DEFAULT_DOMAIN + DOMAIN_USER + 'finduser')
    .then(function(response) {
      console.log(response);
    })
    .catch(function(error) {
      console.log(error);
    });
};

//로그인 상태확인? 만들어야함
export const checkStatus = () => {
  axios
    .post(DEFAULT_DOMAIN + 'checkStatus')
    .then(function(response) {
      console.log(response);
    })
    .catch(function(error) {
      console.log(error);
    });
};
