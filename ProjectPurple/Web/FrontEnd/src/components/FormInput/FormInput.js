import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import './FormInput.css';
import { InputGroup, InputGroupAddon, Input } from 'reactstrap';

const FormInput = ({ addon, addonText, type, id, placeholder, aria, onChange, value, name, disabled }) => {
    return(<Fragment>
        <InputGroup>
            <Input
                className="form-control"
                id={id}
                type={type}
                aria-describedby={aria}
                placeholder={placeholder}
                onChange={onChange}
                value={value}
                name={name}
                autoComplete="off"
                disabled={disabled}
            />
            {addon &&
                <InputGroupAddon addonType="append">
                    {addonText}
                </InputGroupAddon>
            }
        </InputGroup>
    </Fragment>)
};

FormInput.propTypes = {
    disabled: PropTypes.bool.isRequired,
    addon: PropTypes.bool,
    addonText: PropTypes.string,
    type: PropTypes.string.isRequired,
    id: PropTypes.string.isRequired,
    placeholder: PropTypes.string.isRequired,
    aria: PropTypes.string,
    onChange: PropTypes.func.isRequired,
    value: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
}

export default FormInput;