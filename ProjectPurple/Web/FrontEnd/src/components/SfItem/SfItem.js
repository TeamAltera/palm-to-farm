import React from 'react';
import PropTypes from 'prop-types';
import './SfItem.css'
import { MdSimCard, MdClear, MdDeleteForever, MdHistory } from 'react-icons/lib/md';
import Moment from 'react-moment';
import { Link } from 'react-router-dom';
import { Progress } from 'react-sweet-progress';

const SfItem = ({
    ip,
    coolerSt,
    ledSt,
    pumpSt,
    regDate,
    ctrlMode,
    sfCode,
    floor,
    port,
    onClick,
    stamp,
    plant,
    deleteFunc
}) => {
    let pump = '재배중'
    let pumpClass = ''
    let cooler = '동작중'
    let coolerClass = 'success'
    let led = '동작중'
    let ledClass = 'success'
    let ctrl = '수동'
    let ctrlClass = 'ready'
    if (pumpSt === 'F') {
        pump = '재배대기';
        pumpClass = 'ready'
    }
    if (coolerSt === 'F') {
        cooler = '동작중지';
        coolerClass = ''
    }
    if (ledSt === 'F') {
        led = '동작중지';
        ledClass = ''
    }
    if (ctrlMode === 'A') {
        ctrl = '자동';
        ctrlClass = 'success'
    }
    return (
        <div className="router-container mt-3" id="SfItem-box" style={{ height: '125px' }}>
            <div className="router-header">
                <div className="router-name">
                    <MdSimCard size={20} color="black" />
                    <div className="router-name-box">
                        <span className="router-name-text">sf-device#{sfCode}</span>
                    </div>
                </div>
                <button className="router-delete"
                    onClick={(e) => {
                        e.stopPropagation();//버블링 방지
                        deleteFunc();
                    }}>
                    <MdDeleteForever size={20} />
                    <span>삭제</span>
                </button>
            </div>
            <div className="router-body container-fluid">
                <div className="row">
                    <div className="col-8">
                        <div className="row">
                            <div className="col-4 router-label">내부 아이피</div>
                            <div className="col-8 router-content">{ip}</div>
                        </div>
                        <div className="row mt-1">
                            <div className="col-4 router-label">재배기 등록시간</div>
                            <div className="col-8 router-content">
                                <MdHistory size={16} className="mr-1" />
                                <Moment format="YYYY/MM/DD A HH:mm:ss">
                                    {new Date(regDate)}
                                </Moment>
                            </div>
                        </div>
                        <div className="row mt-1">
                            <div className="col-4 router-label">사용 재배구 수</div>
                            <div className="col-8 router-content">
                                <Progress
                                    percent={port / 32 * 100}
                                    status="default"
                                    theme={{
                                        default: {
                                            symbol:
                                                <strong className="text-muted smaller" style={{ fontSize: '12px' }}>
                                                    {port + '/32'}
                                                </strong>,
                                            trailColor: 'lightblue',
                                            color: '#24c9eb'
                                        }
                                    }} />
                            </div>
                        </div>
                    </div>
                    <div className="col-4" style={{ borderLeft: "1px solid #e7e7e7" }}>
                        <div className="row">
                            <div className="col-4 router-label">상태</div>
                            <div className="col-8 router-content">{pump}</div>
                        </div>
                        <div className="row mt-1">
                            <div className="col-4 router-label">생육식물</div>
                            <div className="col-8 router-content">
                                {!plant && '없음'}
                                {plant && plant}
                            </div>
                        </div>
                        <div className="row mt-1">
                            <div className="col-4 router-label">
                            </div>
                            <div className="col-8 router-content">
                                <Link to={'/sf_device?sf=sf-device#' + sfCode} onClick={onClick}>
                                    <div className="sf-execute pt-1">
                                        <span >장비 조회</span>
                                    </div>
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
};

SfItem.propTypes = {

}

export default SfItem;
