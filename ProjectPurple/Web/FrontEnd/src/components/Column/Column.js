import React from 'react';
import PropTypes from 'prop-types';

const Column = ({ children, option }) => (
    <div className={"col "+option}>
        {children}
    </div>
);

Column.propTypes = {
    option: PropTypes.string,
}

export default Column;