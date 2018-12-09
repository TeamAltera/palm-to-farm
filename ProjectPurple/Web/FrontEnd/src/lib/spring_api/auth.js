import axios from 'axios';
import setAuthorizationToken from '../../utils/setAuthorizationToken';
import {DOMAIN} from './urlSetting';

const USER_NAMESPACE = 'user';
const MAIL_NAMESPACE = 'mail/';


//
export const signin = (email, password) => {
    var clientId='foo';
    var clientSecret='bar';
    return axios
        .post('http://192.168.0.6:8081/oauth/token',
        'grant_type=password&username=' + email+'&password='+  password,
        // {
        //     grant_type: 'password',
        //     username: email,
        //     password: password
        // }, 
        {
            headers:{
                'Content-Type':'application/x-www-form-urlencoded;charset=utf-8',
                'Authorization':'Basic '+btoa(clientId+':'+clientSecret)
            }
        })
        .then(response => {
            //로그인 성공시 토큰 저장
            if (response.status===200) {
                const token = response.data.access_token;
                if (token) {
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
        .post(DOMAIN + USER_NAMESPACE, {
            email: email,
            pwd: password,
            firstName: firstName,
            secondName: secondName,
        });
};

//email is exists?
export const checkEmailExists = (email) => {
    return axios
        .get(DOMAIN + USER_NAMESPACE + '/find?email=' + email);
};

//인증 이메일 발송
export const sendAuthCode = (email) => {
    return axios
        .post(DOMAIN + MAIL_NAMESPACE + '/send', {
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
        .post(DOMAIN + MAIL_NAMESPACE + '/check', {
            code: code,
        });
}

//사용자 정보 조회
export const getUserInfo = () => {
    return axios
        .get(DOMAIN + USER_NAMESPACE)
        .then(res => {
            console.log(res);
            return res;
        });
};


