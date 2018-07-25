import React from 'react';
import PropTypes from 'prop-types';

const Button = ({ children, onClick, option, disabled }) => {
    let res;
    if(disabled)
        res="";
    else
        res="diabled";
    return (
        <input 
        type="button" 
        className={"btn btn-"+option+" btn-block"} 
        onClick={onClick} 
        value={children}
        disabled={res}zz
        />)
    };

Button.propTypes = {
    children: PropTypes.string,
    onClick: PropTypes.func.isRequired,
    disabled: PropTypes.bool.isRequired,
    option: PropTypes.string.isRequired,
}

export default Button;