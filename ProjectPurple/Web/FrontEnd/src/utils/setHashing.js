import sha256 from 'crypto-js/sha256';

export default function setHashing(password){
    return sha256(password);
}