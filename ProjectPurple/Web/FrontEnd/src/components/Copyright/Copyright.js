import React from 'react';
import PropTypes from 'prop-types';
import {TextCenter} from '../../components';

const Copyright = ({ children, option }) => {
    return (
    <TextCenter option="mt-2">
        2018 &copy; 
        <a 
            href="https://github.com/AlteraLab/ProjectPonics" 
            target="_blank"
            rel="noopener noreferrer">
            DCU Altera LAB
        </a>
    </TextCenter>
    )
};

Copyright.propTypes = {
    option: PropTypes.string,
}

export default Copyright;