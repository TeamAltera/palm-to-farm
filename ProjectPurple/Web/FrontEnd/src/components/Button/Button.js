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
        <button
        className="Form-btn"
        onClick={onClick} 
        disabled={res}
        id="nanum-gothic"
        >
        {children}
        </button>
    )
    };

Button.propTypes = {
    children: PropTypes.string,
    onClick: PropTypes.func.isRequired,
    disabled: PropTypes.bool.isRequired,
}

export default Button;