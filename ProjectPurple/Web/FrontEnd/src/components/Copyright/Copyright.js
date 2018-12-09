import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import { TextCenter } from '../../components';
import './Copyright.css'

const Copyright = ({ children, option }) => {
    return (
        <footer className="copyright-footer pt-2 pb-2">
            <TextCenter>
                2018 &copy;
                    <a
                        href="https://github.com/AlteraLab/ProjectPonics"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="copyright-footer-text"
                        >
                        <span>DCU Altera LAB</span>
                    </a>
            </TextCenter>
        </footer>
    )
};

Copyright.propTypes = {
    option: PropTypes.string,
}

export default Copyright;