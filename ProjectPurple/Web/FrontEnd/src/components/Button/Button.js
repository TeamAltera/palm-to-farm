import React from 'react';
import PropTypes from 'prop-types';
import './Button.css'

const Button = ({ children, onClick, disabled }) => {
    let res;
    if(disabled)
        res="";
    else
        res="diabled";
    return (
        <input 
        type="button" 
        className="Form-btn"
        onClick={onClick} 
        value={children}
        disabled={res}
        id="nanum-gothic"
        >
        </input>
    )
    };

Button.propTypes = {
    children: PropTypes.string,
    onClick: PropTypes.func.isRequired,
    disabled: PropTypes.bool.isRequired,
}

export default Button;