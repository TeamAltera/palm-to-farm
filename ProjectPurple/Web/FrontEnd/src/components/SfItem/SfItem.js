import React from 'react';
import PropTypes from 'prop-types';
import './SfItem.css'
import { MdSimCard, MdClear, MdHistory } from 'react-icons/lib/md';
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
        <div className="mt-3" id="SfItem-box">
            <Link to={'/sf_device?sf=sf-device#'+sfCode} onClick={onClick}>
                <div className="list-group-item RouterItem" id="nopadding">
                    <div className="container-fluid">
                        <div className="row SFItem-title">
                            <div className="col-7">
                                <div className="ml-1 mt-2">
                                    <MdSimCard size={20} color="black" />
                                    <span className="SfItem-title ml-1 mt-2 md-3" id="nanum-gothic">
                                        <span id="SfItem-color">sf-device#{sfCode}</span>
                                    </span>
                                </div>
                            </div>
                            <div className="col-4 mt-2" id="nopadding">
                                <div className="SFItem-right">
                                    <span className={"smaller size-8 SFItem-action " + pumpClass}
                                        id="nanum-gothic">
                                        {pump}
                                    </span>
                                </div>
                            </div>
                            <div className="col-1 mb-0" id="nopadding">
                                <button type="button"
                                    className="SfItem-btn-clear">
                                    <MdClear />
                                </button>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-3">
                                <div className="ml-1">
                                    <span className="text-muted smaller size-8 ml-4">
                                        {ip}
                                    </span>
                                </div>
                            </div>
                            <div className="col-8" id="nopadding">
                                    <div className="text-muted smaller size-8 
                                    SFItem-right mt-1">
                                        <MdHistory size={17} />
                                        <span className="smaller size-8 ml-1 bold">
                                            <Moment format="HH:mm:SS">
                                                {new Date(regDate)}
                                            </Moment>
                                        </span>
                                    </div>
                            </div>
                            <div className="col-1" id="nopadding">
                                {/* <button type="button"
                                    className="SfItem-btn-clear">
                                    <MdClear />
                                </button> */}
                            </div>
                        </div>
                        <div className="row mb-1">
                            <div className="col-7 ml-1">
                                <Progress
                                    percent={port/32*100}
                                    status="default"
                                    theme={
                                        {
                                            default: {
                                                symbol:
                                                    <strong className="text-muted smaller size-8">
                                                        {port + '/32'}
                                                    </strong>,
                                                trailColor: 'lightblue',
                                                color: '#24c9eb'
                                            }
                                        }
                                    }
                                />
                            </div>
                            <div className="col-4 mr-1" id="nopadding">
                                <div className="ml-4 SFItem-right">
                                    <span className="text-muted smaller size-8">
                                        <span className="smaller size-8 bold">
                                            {floor}층, {port}개 재배포트
                                    </span>
                                    </span>
                                </div>
                            </div>
                            <div className="col-1">
                            </div>
                        </div>


                        {/* <div className="row mt-1 mb-1 SfItem-line" id="nopadding">
                            <div className="col-3">
                                <div className="row">
                                    <div className="col-12">
                                        <TextCenter>
                                            <span className="text-muted smaller size-9 bold"
                                                id="nanum-gothic">
                                                작물재배
                                        </span>
                                        </TextCenter>
                                    </div>
                                </div>
                                <div className="row">

                                </div>
                            </div> */}
                        {/* <div className="col-3">
                                <div className="row">
                                    <div className="col-12">
                                        <TextCenter>
                                            <span className="text-muted smaller size-9 bold"
                                                id="nanum-gothic">
                                                쿨러 상태
                                        </span>
                                        </TextCenter>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-12">
                                        <TextCenter>
                                            <span className={"smaller size-8 SFItem-action " + coolerClass}
                                                id="nanum-gothic">
                                                {cooler}
                                            </span>
                                        </TextCenter>
                                    </div>
                                </div>
                            </div>
                            <div className="col-3">
                                <div className="row">
                                    <div className="col-12">
                                        <TextCenter>
                                            <span className="text-muted smaller size-9 bold"
                                                id="nanum-gothic">
                                                LED 상태
                                        </span>
                                        </TextCenter>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-12">
                                        <TextCenter>
                                            <div>
                                                <span className={"smaller size-8 SFItem-action " + ledClass}
                                                    id="nanum-gothic">
                                                    {led}
                                                </span>
                                            </div>
                                        </TextCenter>
                                    </div>
                                </div>
                            </div>
                            <div className="col-3">
                                <div className="row">
                                    <div className="col-12">
                                        <TextCenter>
                                            <span className="text-muted smaller size-9 bold" id="nanum-gothic">
                                                제어모드
                                        </span>
                                        </TextCenter>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-12">
                                        <TextCenter>
                                            <span className={"smaller size-8 SFItem-action " + ctrlClass}
                                                id="nanum-gothic">
                                                {ctrl}
                                            </span>
                                        </TextCenter>
                                    </div>
                                </div>
                            </div>
                        </div> */}
                    </div>
                </div>
            </Link>
        </div>
    )
};

SfItem.propTypes = {

}

export default SfItem;