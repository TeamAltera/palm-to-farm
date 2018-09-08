import axios from 'axios';
const DOMAIN = 'http://203.250.32.91:9001/smart_plant/';
const LOG_NAMESPACE = 'log/';

export const getDataset = (data) => {
    //url, data, header순서
    return axios
        .post(DOMAIN + LOG_NAMESPACE + 'day',data).then(res => {
            console.log(res);
            return res;
        });
}