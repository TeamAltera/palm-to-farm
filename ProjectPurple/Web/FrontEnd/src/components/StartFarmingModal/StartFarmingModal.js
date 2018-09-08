import React from 'react';
import PropTypes from 'prop-types';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import { MdRouter, MdClear } from 'react-icons/lib/md';
import './StartFarmingModal.css';
import SfPipe from '../SfPipe/SfPipe'
import SfFloor from '../SfFloor/SfFloor';

const StartFarmingModal = ({ sfName, isOpen, modalFunc, onChange, sendFunc, reset, errorRender
    , isConfirm, farm, resetFunc, resetPipeFunc, resetFloorFunc, farmingCount, plantChange,farmRes }) => {
    let isDisabled = '';
    if (!isConfirm) isDisabled = 'disabled';
    return (
        <Modal isOpen={isOpen} className="RouterAddModal-content">
            <div id="RouterAddModal-header">
                <div className="StartFarmingModal-title ml-2 mt-2 mb-2 col-10" id="nanum-gothic">
                    {sfName}의 작물재배 설정
                </div>
                <div className="col-2" id="padding-right-8">
                    <button type="button" className="RouterAddModal-close"
                        aria-label="Close" onClick={modalFunc}>
                        <MdClear size={20} />
                    </button>
                </div>
            </div>
            <ModalBody>
                <div className="container-fluid" id="nopadding">
                    <div className="row StartFarmingModal-right mb-1">
                        <select id="nanum-gothic" className="StartFarmingModal-box mr-1"
                        onChange={plantChange}>
                            <option value="1">
                                상추
                            </option>
                        </select>
                        <span id="nanum-gothic" className="StartFarmingModal-box mr-4">
                            <span className="text-muted">포트 수: </span>
                            <strong>{farmingCount}</strong>
                        </span>
                    </div>
                    <SfFloor floorIndex={3} resetPipeFunc={resetPipeFunc}
                        resetFloorFunc={resetFloorFunc} option={true}>
                        <SfPipe portIndex={1} onChange={onChange} pipe={farm.get(0)} pipeIndex={0} option={true}/>
                        <SfPipe portIndex={9} onChange={onChange} pipe={farm.get(1)} pipeIndex={1} option={true}/>
                    </SfFloor>
                    <SfFloor floorIndex={2} resetPipeFunc={resetPipeFunc}
                        resetFloorFunc={resetFloorFunc} option={true}>
                        <SfPipe portIndex={17} onChange={onChange} pipe={farm.get(2)} pipeIndex={2} option={true}/>
                        <SfPipe portIndex={25} onChange={onChange} pipe={farm.get(3)} pipeIndex={3} option={true}/>
                    </SfFloor>
                    {errorRender && errorRender()}
                    <div className="row StartFarmingModal-justify">
                        <button type="button" className="StartFarmingModal-btn ml-2 mr-2"
                            onClick={() => sendFunc(10)} disabled={isDisabled}>
                            <span className="smaller size-9" id="nanum-gothic">완료</span>
                        </button>
                        <button type="button" className="StartFarmingModal-btn" onClick={resetFunc}>
                            <span className="smaller size-9" id="nanum-gothic">리셋</span>
                        </button>
                    </div>
                </div>
            </ModalBody>
        </Modal>
    )
};

StartFarmingModal.propTypes = {
    isConfirm: PropTypes.bool.isRequired,
    isOpen: PropTypes.bool.isRequired,
    modalFunc: PropTypes.func.isRequired,
    onChange: PropTypes.func.isRequired,
    sendFunc: PropTypes.func.isRequired,
    resetFunc: PropTypes.func.isRequired,
    errorRender: PropTypes.func,
}

export default StartFarmingModal;