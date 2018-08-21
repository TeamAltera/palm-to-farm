import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import { TextCenter } from '../../components';
import './Copyright.css'

const Copyright = ({ children, option }) => {
    return (
        <Fragment>
            <div className="Copyright-back" />
            <div className="Copyright">
                <TextCenter option="mt-4">
                    2018 &copy;
                <a
                        href="https://github.com/AlteraLab/ProjectPonics"
                        target="_blank"
                        rel="noopener noreferrer">
                        <span className="Copyright-a">DCU Altera LAB</span>
                    </a>
                </TextCenter>
            </div>
        </Fragment>
    )
};

Copyright.propTypes = {
    option: PropTypes.string,
}

export default Copyright;