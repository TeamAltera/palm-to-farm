import axios from 'axios';
const DOMAIN = 'http://203.250.32.173:9001/smart_plant/';
const PLANT_NAMESPACE = 'plant/';
const DEVICE_NAMESPACE='device/';

export const getDataset = (apCode,sfCode,selectedPlant) => {
    //url, data, header순서
    return axios
        .post(DOMAIN + PLANT_NAMESPACE + 'info',{
            apCode: apCode,
            sfCode: sfCode,
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
