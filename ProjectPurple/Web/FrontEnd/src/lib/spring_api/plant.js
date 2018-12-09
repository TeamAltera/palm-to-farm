import axios from 'axios';
import {DOMAIN} from './urlSetting';
const PLANT_NAMESPACE = 'plant/';

export const getDataset = (apCode,stamp,selectedPlant) => {
    //url, data, header순서
    return axios
        .post(DOMAIN + PLANT_NAMESPACE + 'info',{
            apCode: apCode,
            stamp: stamp,
            plantCode: selectedPlant,
        }).then(res => {
            console.log(res);
            return res;
        });
}

export const sendCommand = (dataset) => {
    return axios
        .post(DOMAIN + PLANT_NAMESPACE + 'farming', dataset)
        .then(res => {
            console.log(res);
            return res;
        });
}

export const getPortInfo = (dataset) => {
    return axios
        .post(DOMAIN + PLANT_NAMESPACE + 'port', dataset)
        .then(res => {
            console.log(res);
            return res;
        });
}
