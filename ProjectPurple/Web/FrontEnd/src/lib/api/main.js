import axios from "axios";

const DEFAULT_DOMAIN = "203.250.32.180/smart_plant/";
const DOMAIN_CONFIRM = "device/confirm";

//공유기 추가
export const confirm_ip = (a, b, c, d) => {
  axios
    .post(DEFAULT_DOMAIN + DOMAIN_CONFIRM, {
      ip: a + "." + b + "." + c + "." + d
    })
    .then(function(response) {
      console.log(response);
    })
    .catch(function(error) {
      console.log(error);
    });
};
