import axios from 'axios';

const DEFAULT_DOMAIN = 'http://203.250.32.180:9001/smart_plant/';
const DOMAIN_CONFIRM = 'device/confirm';

//공유기 추가
export const confirm_ip = (a, b, c, d) => {
  axios
    .post(DEFAULT_DOMAIN + DOMAIN_CONFIRM, {
      ip: a + '.' + b + '.' + c + '.' + d,
    })
    .then(function(response) {
      console.log(response.data);
    })
    .catch(function(error) {
      console.log(error);
    });
};
