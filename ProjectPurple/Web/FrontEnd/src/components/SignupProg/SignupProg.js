import React from 'react';
import './SignupProg.scss'

const SignupProg = ({ value }) => {
    return (
        <div className="SignupProg-progress-bar">
            <div className="dot blue"></div>
            <div className="bar blue"></div>

            <div className="dot blue"></div>
            <div className="bar blue"></div>

            <div className="dot blue"></div>
            <div className="bar"></div>

            <div className="dot"></div>
            <div className="bar"></div>

            <div className="dot"></div>
        </div>
    )
};

export default SignupProg;