import React, { Fragment } from 'react';
import { TextCenter } from '../../components';
import './FormHeader.css'
import logo from '../../resources/img/ptf.png'

const FormHeader = () => (
    <Fragment>
        <div className="style__login__logo--wrapper">
            <a href="/">
                <img className="style__login__logo" src={logo} />
            </a>
        </div>
        <TextCenter>
            <span className="style-title">당신의 식물공장을 지금 시작해보세요 :)</span>
        </TextCenter>
    </Fragment>
);

export default FormHeader;
