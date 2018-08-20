import React from 'react';
import PropTypes from 'prop-types';

const Form = ({ children, type, onSubmit }) => (
    <div className={"card card-" + type + " mx-auto mt-5"}>
        {children}
    </div>
);

Form.propTypes = {
    type: PropTypes.string.isRequired,
    onSubmit: PropTypes.func,
}

export default Form;