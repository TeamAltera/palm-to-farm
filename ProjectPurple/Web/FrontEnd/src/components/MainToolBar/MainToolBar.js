import React from 'react';
import { MdAdd, MdDeleteForever, MdCancel, MdAddCircle } from 'react-icons/lib/md';
import PropTypes from 'prop-types';
import './MainToolBar.css';
import { Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';

const MainToolBar = ({ isOpen, toggle, addFunc, deleteFunc }) => {
    return (
        <div className="mt-0 MainToolBar-container">
            <div className="MainToolBar-box-left mt-2 mb-2">
            </div>
            <div className="MainToolBar-box-right mt-2 mb-2">
                <div href="#" className="MainToolBar-btn mr-2" onClick={deleteFunc}>
                    <MdCancel size={18} />
                    <span className="ml-1">전체 삭제</span>
                </div>
                <div className="MainToolBar-btn mr-3" onClick={addFunc}>
                    <MdAddCircle size={18} />
                    <span className="ml-1">새 라우터</span>
                </div>
            </div>
        </div>
    )
};

MainToolBar.propTypes = {
    addFunc: PropTypes.func.isRequired,
    deleteFunc: PropTypes.func.isRequired,
}

export default MainToolBar;