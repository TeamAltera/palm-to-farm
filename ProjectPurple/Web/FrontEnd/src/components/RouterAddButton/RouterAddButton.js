import React from 'react';
import { MdAdd } from 'react-icons/lib/md';
import './RouterAddButton.css';
import PropTypes from 'prop-types';

const RouterAddButton = ({onClick}) => {
    return (
        <div className="col-lg-3 col-md-4 col-sm-6 portfolio-item mb-3">
            <a className="list-group-item list-group-item-action RouterAddButton-container" 
            id="RouterAddButton-border" onClick={onClick}>
                <div className="RouterAddButton-wrapper">
                    <MdAdd size={50} />
                    <span className="text-muted smaller size-9">
                        <strong>새 라우터</strong>
                    </span>
                </div>
                {/* <div className="row md-5 RouterAddButton-item">
                        
                    </div> */}
            </a>
        </div>
    )
};

RouterAddButton.propTypes = {
    onClick: PropTypes.func.isRequired,
}

export default RouterAddButton;