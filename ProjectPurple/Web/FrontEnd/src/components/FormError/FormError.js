import React from 'react';
import './FormError.css';

const FormError = ({ children, isSuccess }) => {
    let className='FormError';
    if(isSuccess){
        className+=' success'
    }
    return (
        <div className={className}>
            {children}
        </div>
    )
};

export default FormError;
