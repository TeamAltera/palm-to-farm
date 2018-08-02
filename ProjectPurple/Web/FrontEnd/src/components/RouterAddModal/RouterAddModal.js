import React from 'react';
import PropTypes from 'prop-types';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import { MdRouter, MdClear } from 'react-icons/lib/md';
import './RouterAddModal.css';

const RouterAddModal = ({ isOpen, modalFunc, onChange, searchFunc, addFunc }) => {
    return (
        <Modal isOpen={isOpen} className="RouterAddModal-content">
            <div id="RouterAddModal-header">
                <div className="RouterAddModal-title ml-2 mt-2 mb-2 col-10" id="nanum-gothic">
                    라우터 추가
                </div>
                <div className="col-2" id="padding-right-8">
                    <button type="button" className="RouterAddModal-close"
                        aria-label="Close" onClick={modalFunc}>
                        <MdClear size={20} />
                    </button>
                </div>
            </div>
            <ModalBody className="container">
                <div>
                    <img src="../../resources/img/ap.png"/>
                </div>
                <ol className="smaller size-9" id="nanum-gothic">
                    <li>사용자 디바이스로 라즈베리파이 라우터에 연결합니다</li>
                    <li>브라우저에서 192.168.4.1로 접속합니다.</li>
                    <li>접속해서 나오는 라우터의 IP주소를 하단에 입력하고 등록합니다.</li>
                </ol>
                <div className="row">
                <div className="ip-input-container">
                    <input type="text" className="ip-input-item" 
                        name="a" onChange={onChange}/>
                    <i className="ip-input-dot"></i>
                    <input type="text" className="ip-input-item" 
                        name="b" onChange={onChange}/>
                    <i className="ip-input-dot"></i>
                    <input type="text" className="ip-input-item" 
                        name="c" onChange={onChange}/>
                    <i className="ip-input-dot"></i>
                    <input type="text" className="ip-input-item"
                        name="d" onChange={onChange}/>
                </div>
                <button type="button" className="RouterAddModal-btn mr-2" onClick={searchFunc}>
                        <span className="smaller size-9 ml-1">라우터 조회</span>
                </button>
                <button type="button" className="RouterAddModal-btn mr-2" onClick={addFunc}>
                        <span className="smaller size-9 ml-1">추가</span>
                </button>
                </div>
            </ModalBody>
            {/* <ModalFooter>
                <Button color="primary" onClick={modalFunc}>Do Something</Button>
                <Button color="secondary" onClick={modalFunc}>Cancel</Button>
            </ModalFooter> */}
        </Modal>
    )
};

RouterAddModal.propTypes = {
    isOpen: PropTypes.bool.isRequired,
    modalFunc: PropTypes.func.isRequired,
    onChange: PropTypes.func.isRequired,
    searchFunc: PropTypes.func.isRequired,
    addFunc: PropTypes.func.isRequired,
}

export default RouterAddModal;