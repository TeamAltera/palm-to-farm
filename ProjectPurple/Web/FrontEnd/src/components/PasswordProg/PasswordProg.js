import React  from 'react';
import { Progress } from 'reactstrap';
import PropTypes from 'prop-types';

const PasswordProg = ({ value, option }) => {
    return (
        <div>
            <Progress color={status[value].color} value={status[value].value}
                className={option}
            >
                {status[value].text}
            </Progress>
        </div>
    )
};

const status=[
    {
        value: 20,
        color: 'danger',
        text: '매우약함'
    },
    {
        value: 40,
        color: 'danger',
        text: '약함'
    },
    {
        value: 60,
        color: 'warning',
        text: '보통'
    },
    {
        value: 80,
        color: 'info',
        text: '강함'
    },
    {
        value: 100,
        color: 'success',
        text: '매우강함'
    },
];

PasswordProg.propTypes={
    value: PropTypes.number.isRequired,
    option: PropTypes.string,
}

export default PasswordProg;