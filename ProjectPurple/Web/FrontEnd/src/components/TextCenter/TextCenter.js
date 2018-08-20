import React from 'react';
import PropTypes from 'prop-types';

const TextCenter = ({ children, option }) => (
    <div className={"text-center "+option}>
        {children}
    </div>
);

TextCenter.propTypes = {
    option: PropTypes.string,
}

export default TextCenter;