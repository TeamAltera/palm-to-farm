import axios from 'axios';
import setAuthorizationToken from '../../utils/setAuthorizationToken';

const DOMAIN = 'http://203.250.32.91:9001/smart_plant/';
const USER_NAMESPACE = 'user/';
const MAIL_NAMESPACE = 'mail/';


//signin function
export const signin = (email, password) => {

    return axios
        .post(DOMAIN + USER_NAMESPACE + 'signin', {
            email: email,
            pwd: password
        })
        .then(response => {
            //status는 INTERNAL_SEVER_ERROR 또는 OK
            if (response.data.status === "OK") {
                const token = response.data.data.token;
                if (token) {
                    // sessionStorage.setItem('jwtToken', token);
                    localStorage.setItem('jwtToken', token);
                    setAuthorizationToken(token);
                }
            }
            return response;
        }).catch();
};

//signup function
export const signup = (email, password, firstName, secondName) => {
    return axios
        .post(DOMAIN + USER_NAMESPACE + 'signup', {
            email: email,
            pwd: password,
            firstName: firstName,
            secondName: secondName,
        });
};

//email is exists?
export const checkEmailExists = (email) => {
    return axios
        .get(DOMAIN + USER_NAMESPACE + 'find?email=' + email);
};

//인증 이메일 발송
export const sendAuthCode = (email) => {
    return axios
        .post(DOMAIN + MAIL_NAMESPACE + 'send', {
            email: email,
        }).then(
            res => {
                console.log(res);
                return res;
            }
        );
}

//인증 코드 확인
export const checkAuthCode = (code) => {
    return axios
        .post(DOMAIN + MAIL_NAMESPACE + 'check', {
            code: code,
        });
}

//사용자 정보 조회
export const getUserInfo = () => {
    return axios
        .get(DOMAIN + USER_NAMESPACE+'info')
        .then(res => {
            console.log(res);
            return res;
        });
};


