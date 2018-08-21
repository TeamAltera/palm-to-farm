import axios from 'axios';
const DOMAIN = 'http://203.250.32.173:9001/smart_plant/';
const SENSOR_NAMESPACE = 'sensor/';

export const getDataset = (apCode, sfCode, date) => {
    //url, data, header순서
    return axios
        .post(DOMAIN + SENSOR_NAMESPACE + 'day', {
            apCode: apCode,
            sfCode: sfCode,
            date: date
        }).then(res => {
            console.log(res);
            return res;
        });
}

