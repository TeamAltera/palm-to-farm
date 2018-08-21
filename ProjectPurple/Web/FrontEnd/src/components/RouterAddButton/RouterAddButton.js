import React from 'react';
import { MdAdd } from 'react-icons/lib/md';
import './RouterAddButton.css';
import PropTypes from 'prop-types';

const RouterAddButton = ({onClick}) => {
    return (
        <div className="col-lg-3 col-md-4 col-sm-6 portfolio-item mb-5">
            <div className="list-group-item RouterAddButton-container" 
            id="RouterAddButton-border" onClick={onClick}>
                <div className="RouterAddButton-wrapper">
                    <MdAdd size={50} />
                    <span className="smaller size-9" id="nanum-gothic">
                        <strong>새 라우터</strong>
                    </span>
                </div>
            </div>
            <div className="mb-5"/>
            <div className="mb-5"/>
        </div>
    )
};

RouterAddButton.propTypes = {
    onClick: PropTypes.func.isRequired,
}

export default RouterAddButton;