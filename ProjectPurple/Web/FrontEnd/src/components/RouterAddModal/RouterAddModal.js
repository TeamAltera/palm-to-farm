import React from 'react';
import PropTypes from 'prop-types';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import { MdSearch, MdClear, MdInfo, MdAddCircle } from 'react-icons/lib/md';
import './RouterAddModal.css';
import '../RouterItem/Router.css';

const RouterAddModal = ({ isOpen, modalFunc, onChange, searchFunc, addFunc, errorRender
    , isConfirm }) => {
    let isDisabled = '';
    if (!isConfirm) isDisabled = 'disabled';
    return (
        <Modal isOpen={isOpen} className="RouterAddModal-content">
            {/* <div id="RouterAddModal-header">
                <div className="RouterAddModal-title ml-2 mt-2 mb-2 col-10" id="nanum-gothic">
                    라우터 추가
                </div>
                <div className="col-2" id="padding-right-8">
                    <button type="button" className="RouterAddModal-close"
                        aria-label="Close" onClick={modalFunc}>
                        <MdClear size={20} />
                    </button>
                </div>
            </div> */}
            <div className="router-header">
                <div className="router-name">
                    <MdInfo size={22} color="#407de0" />
                    <div className="router-name-box">
                        <span className="router-name-text">새 라우터 생성</span>
                    </div>
                </div>
                <button className="router-delete" aria-label="Close" onClick={modalFunc}>
                    <MdClear size={20} />
                </button>
            </div>
            <ModalBody id="RouterAddModal-body">
                <div className="container-fluid">
                    <div className="row RouterAddModal-justify mb-2">
                        <img src={require('../../resources/img/ap.png')} height="150" />
                    </div>
                    <div className="row RouterAddModal-align">
                        <ol style={{fontFamily:'sansr', fontSize:'13px'}}>
                            <li>사용자 디바이스로 라즈베리파이 라우터에 연결합니다</li>
                            <li>브라우저에서 <span className="router-ip">192.168.4.1</span>로 접속합니다.</li>
                            <li>접속해서 나오는 라우터의 IP주소를 하단에 입력하고 등록합니다.</li>
                        </ol>
                    </div>
                </div>
            </ModalBody>
            <ModalFooter id="RouterAddModal-footer">
                <div className="container-fluid">
                    <div className="row">
                        {errorRender && errorRender()}
                    </div>
                    <div className="row mb-1">
                        <div className="col-2 router-label" style={{ padding: '5px 6px' }}>
                            공용 아이피
                        </div>
                        <div className="col-7 router-content">
                            <div className="ip-input-container">
                                <input type="text" className="ip-input-item"
                                    name="a" onChange={onChange} />
                                <i className="ip-input-dot"></i>
                                <input type="text" className="ip-input-item"
                                    name="b" onChange={onChange} />
                                <i className="ip-input-dot"></i>
                                <input type="text" className="ip-input-item"
                                    name="c" onChange={onChange} />
                                <i className="ip-input-dot"></i>
                                <input type="text" className="ip-input-item"
                                    name="d" onChange={onChange} />
                            </div>
                        </div>
                        <div className="col-3 router-label">
                            <button type="button" className="search-btn" onClick={searchFunc}>
                                <MdSearch size={16} />
                                <span className="ml-1">라우터 검색</span>
                            </button>
                        </div>
                    </div>
                    <div className="row mb-1">
                        <div className="col-2 router-label" style={{ padding: '5px 6px' }}>
                            라우터 이름
                        </div>
                        <div className="col-7 router-label">
                            <div className="ip-input-container" style={{width:'100%'}}>
                                <input type="text" name="router" onChange={onChange}
                                    className="router-name-input"
                                    placeholder="미기입 시 기본 이름" />
                            </div>
                        </div>
                        <div className="col-3 router-label">
                            <button type="button" className="RouterAddModal-btn" 
                            onClick={addFunc} disabled={isDisabled}>
                                <MdAddCircle size={16} />
                                <span className="ml-1">라우터 추가</span>
                            </button>
                        </div>
                    </div>
                </div>
            </ModalFooter>
        </Modal>
    )
};

RouterAddModal.propTypes = {
    isConfirm: PropTypes.bool.isRequired,
    isOpen: PropTypes.bool.isRequired,
    modalFunc: PropTypes.func.isRequired,
    onChange: PropTypes.func.isRequired,
    searchFunc: PropTypes.func.isRequired,
    addFunc: PropTypes.func.isRequired,
    errorRender: PropTypes.func,
}

export default RouterAddModal;