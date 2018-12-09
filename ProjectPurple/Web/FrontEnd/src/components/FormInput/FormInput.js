import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import './FormInput.css';
import { InputGroup, InputGroupAddon} from 'reactstrap';

const FormInput = ({ addon, addonText, type, id, placeholder, onChange, value, name, disabled }) => {
    return (
        <Fragment>
            <label htmlFor={id} className="inp">
                <input
                    type={type}
                    id={id}
                    placeholder="&nbsp;"
                    onChange={onChange}
                    value={value}
                    name={name}
                    autoComplete="off"
                    disabled={disabled}
                    spellCheck="false"
                />
                <span className="label" id="nanum-gothic">{placeholder}</span>
                <span className="border"></span>
            </label>
            <InputGroup>
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