import React from 'react';
import PropTypes from 'prop-types';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import { MdRouter, MdClear, MdInfo } from 'react-icons/lib/md';
import './StartFarmingModal.css';
import SfPipe from '../SfPipe/SfPipe'
import SfFloor from '../SfFloor/SfFloor';
import TextCenter from '../TextCenter/TextCenter';

const StartFarmingModal = ({ sfName, isOpen, modalFunc, onChange, sendFunc, reset, errorRender
    , isConfirm, farm, resetFunc, resetPipeFunc, resetFloorFunc, farmingCount, plantChange, farmRes }) => {
    let isDisabled = '';
    if (!isConfirm) isDisabled = 'disabled';
    return (
        <Modal isOpen={isOpen} className="RouterAddModal-content">
            <div className="router-header">
                <div className="router-name">
                    <MdInfo size={22} color="#407de0" />
                    <div className="router-name-box">
                        <span className="router-name-text">{sfName}의 생육식물 설정</span>
                    </div>
                </div>
                <button className="router-delete" aria-label="Close" onClick={modalFunc}>
                    <MdClear size={20} />
                </button>
            </div>
            <ModalBody id="RouterAddModal-body">
                <div className="container-fluid" id="nopadding">
                    <div className="row StartFarmingModal-right mb-1">
                        <select className="StartFarmingModal-box mr-1"
                            onChange={plantChange}>
                            <option value="1">
                                상추
                            </option>
                        </select>
                        <span className="StartFarmingModal-box mr-4">
                            <span style={{backgroundColor:'white'}}>포트 수: </span>
                            <strong>{farmingCount}</strong>
                        </span>
                    </div>
                    <SfFloor floorIndex={3} resetPipeFunc={resetPipeFunc}
                        resetFloorFunc={resetFloorFunc} option={true}>
                        <SfPipe portIndex={1} onChange={onChange} pipe={farm.get(0)} pipeIndex={0} option={true} />
                        <SfPipe portIndex={9} onChange={onChange} pipe={farm.get(1)} pipeIndex={1} option={true} />
                    </SfFloor>
                    <SfFloor floorIndex={2} resetPipeFunc={resetPipeFunc}
                        resetFloorFunc={resetFloorFunc} option={true}>
                        <SfPipe portIndex={17} onChange={onChange} pipe={farm.get(2)} pipeIndex={2} option={true} />
                        <SfPipe portIndex={25} onChange={onChange} pipe={farm.get(3)} pipeIndex={3} option={true} />
                    </SfFloor>
                    <TextCenter>
                    <div style={{fontFamily:'sansr', fontSize:'13px'}}>
                    "식물 공장장님 생육 식물을 배치할 재배구들을 선택하세요~!"<br/>
                    (최소 1개의 재배구는 선택되어야 합니다.)
                    </div>
                    </TextCenter>
                    {errorRender && errorRender()}
                    <div className="row StartFarmingModal-justify mt-3 mb-3">
                        <button type="button" className="StartFarmingModal-btn ml-2 mr-2"
                            onClick={() => sendFunc(10)} disabled={isDisabled}>
                            <span className="smaller size-7" id="nanum-gothic">완료</span>
                        </button>
                        <button type="button" className="StartFarmingModal-btn" onClick={resetFunc}>
                            <span className="smaller size-7" id="nanum-gothic">리셋</span>
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