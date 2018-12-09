import axios from 'axios';
import {DOMAIN} from './urlSetting';
const SENSOR_NAMESPACE = 'sensor/';

export const getDataset = (apCode, stamp, date) => {
    //url, data, header순서
    return axios
        .post(DOMAIN + SENSOR_NAMESPACE + 'day', {
            apCode: apCode,
            stamp: stamp,
            date: date
        }).then(res => {
            console.log(res);
            return res;
        });
}

export const getLastDataset = (apCode, stamp, date) => {
    //url, data, header순서
    return axios
        .post(DOMAIN + SENSOR_NAMESPACE + 'day/last', {
            apCode: apCode,
            stamp: stamp,
            date: date
        }).then(res => {
            console.log(res);
            return res;
        });
}

