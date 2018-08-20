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
                {/* <span className="smaller size-9 ml-1">정렬</span>
                <Dropdown isOpen={isOpen} toggle={toggle} >
                    <DropdownToggle className="MainToolBar-btn mr-2">
                    <span className="smaller size-9">업데이트순</span>
                    </DropdownToggle>
                    <DropdownMenu>
                        <DropdownItem>
                            <span>업데이트순</span>
                        </DropdownItem>
                        <DropdownItem>
                            <span>이름순</span>
                        </DropdownItem>
                    </DropdownMenu>
                </Dropdown> */}
                {/* <div id="nanum-gothic"> */}
                    <a href="#" className="MainToolBar-btn mr-2" onClick={deleteFunc}>
                        <MdCancel size={20} />
                        <span className="smaller size-9 ml-1">전체 삭제</span>
                    </a>
                    <a href="#" className="MainToolBar-btn mr-3" onClick={addFunc}>
                        <MdAddCircle size={20} />
                        <span className="smaller size-9 ml-1">새 라우터</span>
                    </a>
                {/* </div> */}
            </div>
        </div>
    )
};

MainToolBar.propTypes = {
    addFunc: PropTypes.func.isRequired,
    deleteFunc: PropTypes.func.isRequired,
}

export default MainToolBar;