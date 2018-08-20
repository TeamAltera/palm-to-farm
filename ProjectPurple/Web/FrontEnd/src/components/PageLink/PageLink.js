import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

const PageLink = ({ preChildren, middleChildren, postChildren, link, option } ) => (
    <span className={"d-block small "+option}>
        {preChildren}
        <Link to={link}>
        {middleChildren}
        </Link>
        {postChildren}
    </span>
);

PageLink.propTypes = {
    preChildren: PropTypes.string,
    middleChildren: PropTypes.string.isRequired,
    postChildren: PropTypes.string,
    link: PropTypes.string.isRequired,
    option: PropTypes.string,
}

export default PageLink;