import axios from 'axios';
const DOMAIN = 'http://203.250.32.173:9001/smart_plant/';
const DEVICE_NAMESPACE = 'device/';

//장치 정보 받아오기
export const getDeviceAllInfo = () => {
    // {
    //     headers: {
    //         'Authorization': token
    //     }
    // }
    return axios
        .get(DOMAIN + DEVICE_NAMESPACE + 'info')
        .then(res => {
            console.log(res);
            return res;
        });
};

export const deleteAllRouter = () => {
    return axios
        .get(DOMAIN + DEVICE_NAMESPACE + 'info')
        .then(res => {
            console.log(res);
            return res;
        });
}

export const deleteSingleRouter = (apCode) => {
    //url, data, header순서
    return axios
        .post(DOMAIN + DEVICE_NAMESPACE + 'delete/ap/manual/' + apCode, null)
        .then(res => {
            console.log(res);
            return res;
        });
}

export const searchRouter = (ip) => {
    return axios
        .post(DOMAIN + DEVICE_NAMESPACE + 'confirm', {
            ip: ip
        }).then(res => {
            console.log(res);
            return res;
        });
}

export const addRouter = (ip) => {
    return axios
        .post(DOMAIN + DEVICE_NAMESPACE + 'add/ap/manual', {
            ip: ip
        }).then(res => {
            console.log(res);
            return res;
        });
}

export const sendCommand = (dataset) => {
    return axios
        .post(DOMAIN + DEVICE_NAMESPACE + 'control', dataset)
        .then(res => {
            console.log(res);
            return res;
        });
}

