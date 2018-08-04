import axios from 'axios';
const DOMAIN = 'http://203.250.32.173:9001/smart_plant/';
const DEVICE_NAMESPACE = 'device/';

//장치 정보 받아오기
export const getDeviceAllInfo = () => {
    console.log(localStorage.getItem('jwtToken'));
    let token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwibWVtYmVyIjp7InVzZXJDb2RlIjo0MiwicHdkIjpudWxsLCJlbWFpbCI6InNlQG5hdmVyLmNvbSIsImZpcnN0TmFtZSI6Iu2ZjSIsInNlY29uZE5hbWUiOiLquLjrj5kiLCJzZkNudCI6MCwiYmxvY2siOjB9LCJpYXQiOjE1MzMyMDk1MjgxNzF9.Urb7kRqQLEuaWxg9GZz8z_sAesPixZrLffhgndyUU6g'
    return axios
        .get(DOMAIN + DEVICE_NAMESPACE + 'info', {
            headers: {
                'Authorization': token
            }
        }).then(res => {
            console.log(res);
            return res;
        });
};

export const deleteAllRouter = () => {
    let token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwibWVtYmVyIjp7InVzZXJDb2RlIjo0MiwicHdkIjpudWxsLCJlbWFpbCI6InNlQG5hdmVyLmNvbSIsImZpcnN0TmFtZSI6Iu2ZjSIsInNlY29uZE5hbWUiOiLquLjrj5kiLCJzZkNudCI6MCwiYmxvY2siOjB9LCJpYXQiOjE1MzMyMDk1MjgxNzF9.Urb7kRqQLEuaWxg9GZz8z_sAesPixZrLffhgndyUU6g'
    return axios
        .get(DOMAIN + DEVICE_NAMESPACE + 'info', {
            headers: {
                'Authorization': token,
            }
        }).then(res => {
            console.log(res);
            return res;
        });
}

export const deleteSingleRouter = (apCode) => {
    let token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwibWVtYmVyIjp7InVzZXJDb2RlIjo0MiwicHdkIjpudWxsLCJlbWFpbCI6InNlQG5hdmVyLmNvbSIsImZpcnN0TmFtZSI6Iu2ZjSIsInNlY29uZE5hbWUiOiLquLjrj5kiLCJzZkNudCI6MCwiYmxvY2siOjB9LCJpYXQiOjE1MzMyMDk1MjgxNzF9.Urb7kRqQLEuaWxg9GZz8z_sAesPixZrLffhgndyUU6g'
    //url, data, header순서
    return axios
        .post(DOMAIN + DEVICE_NAMESPACE + 'delete/ap/manual/' + apCode, null, {
            headers: {
                'Authorization': token,
            }
        }).then(res => {
            console.log(res);
            return res;
        });
}

export const searchRouter = (ip) => {
    let token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwibWVtYmVyIjp7InVzZXJDb2RlIjo0MiwicHdkIjpudWxsLCJlbWFpbCI6InNlQG5hdmVyLmNvbSIsImZpcnN0TmFtZSI6Iu2ZjSIsInNlY29uZE5hbWUiOiLquLjrj5kiLCJzZkNudCI6MCwiYmxvY2siOjB9LCJpYXQiOjE1MzMyMDk1MjgxNzF9.Urb7kRqQLEuaWxg9GZz8z_sAesPixZrLffhgndyUU6g'
    return axios
        .post(DOMAIN + DEVICE_NAMESPACE + 'confirm', {
            ip: ip
        },{
            headers: {
                'Authorization': token,
            }
        }).then(res => {
            console.log(res);
            return res;
        });
}

export const addRouter = (ip) => {
    let token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwibWVtYmVyIjp7InVzZXJDb2RlIjo0MiwicHdkIjpudWxsLCJlbWFpbCI6InNlQG5hdmVyLmNvbSIsImZpcnN0TmFtZSI6Iu2ZjSIsInNlY29uZE5hbWUiOiLquLjrj5kiLCJzZkNudCI6MCwiYmxvY2siOjB9LCJpYXQiOjE1MzMyMDk1MjgxNzF9.Urb7kRqQLEuaWxg9GZz8z_sAesPixZrLffhgndyUU6g'
    return axios
        .post(DOMAIN + DEVICE_NAMESPACE + 'add/ap/manual', {
            ip: ip
        },{
            headers: {
                'Authorization': token,
            }
        }).then(res => {
            console.log(res);
            return res;
        });
}


