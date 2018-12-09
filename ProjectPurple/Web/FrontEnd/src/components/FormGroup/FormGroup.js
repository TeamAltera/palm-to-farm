import React from 'react';
import PropTypes from 'prop-types';

const FormGroup = ({ children, option }) => (

    <div className={"form-group form-group-width" + option}>
        {children}
    </div>

);

FormGroup.propTypes = {
    option: PropTypes.string,
}

export default FormGroup;