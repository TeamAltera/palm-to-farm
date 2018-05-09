import axios from 'axios';

const DEFAULT_DOMAIN = 'http://203.250.32.180:9001/smart_plant/';
const SENS_DATA = 'sensing_data/';

//센싱데이터
export const sens_data = () => {
  axios
    .post(DEFAULT_DOMAIN + SENS_DATA)
    .then(function(response) {
      console.log(response);
    })
    .catch(function(error) {
      console.log(error);
    });
};
