import axios from 'axios';
import {DOMAIN} from './urlSetting';
const LOG_NAMESPACE = 'log/';

export const getDataset = (data) => {
    //url, data, header순서
    return axios
        .post(DOMAIN + LOG_NAMESPACE + 'day',data).then(res => {
            console.log(res);
            return res;
        });
}