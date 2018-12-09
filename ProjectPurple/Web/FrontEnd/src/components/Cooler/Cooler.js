import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import { MdToys } from 'react-icons/lib/md';
import TextCenter from '../TextCenter/TextCenter';
import '../RouterItem/Router.css';
import '../LED/LED.css';

const Cooler = ({ children, obj, sendFunc, isDisabled, text }) => {
    let onBtn = obj.state === false ? 'led-btn-enabled' : 'led-btn-disabled';
    let offBtn = obj.state === false ? 'led-btn-disabled' : 'led-btn-enabled';
    let offBtnDisabled = isDisabled && obj.state === true ? 'btn-disabled' : ' ';
    let onBtnDisabled = isDisabled && obj.state === false ? 'btn-disabled' : ' ';
    return (
        <Fragment>
            <div style={{ border: '1px solid #e7e7e7' }}>
                <div className="row">
                    <div className="col-12">
                        <TextCenter>
                            <div style={{
                                fontSize: '11px',
                                backgroundColor: '#b8d7e5',
                                fontWeight: 'bold',
                                paddingTop: '4px',
                                paddingBottom: '3px'
                            }}>
                                {children}
                            </div>
                        </TextCenter>
                    </div>
                </div>
                <div className="row">
                    <div className="col-12 mt-1">
                        <TextCenter>
                            {obj.state && <MdToys size={40} color="#e9b93e" />}
                            {!obj.state && <MdToys size={40} color="#999999" />}
                        </TextCenter>
                    </div>
                </div>
                <div className="row mb-1">
                    <div className="col-12">
                        <TextCenter>
                            <span style={{fontSize: '12px'}}>
                                <strong>
                                    {text}
                                </strong>
                            </span>
                        </TextCenter>
                    </div>
                </div>
                <div className="row">
                    <div className="col-6" style={{ paddingRight: '0' }}>
                        <button className={onBtn} onClick={() => sendFunc(obj.code)}
                            disabled={isDisabled || obj.state === true} id={onBtnDisabled}>
                            <TextCenter>
                                <span style={{
                                    fontSize: '12px'
                                }}>
                                    ON
                                    </span>
                            </TextCenter>
                        </button>
                    </div>
                    <div className="col-6" style={{ paddingLeft: '0' }}>
                        <button className={offBtn} onClick={() => sendFunc(obj.code)}
                            disabled={isDisabled || obj.state === false} id={offBtnDisabled}>
                            <TextCenter>
                                <span style={{
                                    fontSize: '12px'
                                }}>
                                    OFF
                                    </span>
                            </TextCenter>
                        </button>
                    </div>
                </div>
            </div>
        </Fragment>
    )
}

export default Cooler;