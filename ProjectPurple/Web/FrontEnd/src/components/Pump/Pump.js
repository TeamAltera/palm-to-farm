import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import { MdFormatColorFill } from 'react-icons/lib/md';
import TextCenter from '../TextCenter/TextCenter';
import '../RouterItem/Router.css';
import '../LED/LED.css';

const Pump = ({ children, obj, sendFunc, isDisabled, text }) => {
    let onBtn = obj.state === true ? 'led-btn-enabled' : 'led-btn-disabled';
    let onBtnDisabled = isDisabled  ? 'btn-disabled' : ' ';
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
                            {obj.state && <MdFormatColorFill size={40} color="#4f8ffa" />}
                            {!obj.state && <MdFormatColorFill size={40} color="#999999" />}
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
                    <div className="col-12">
                        <button className={onBtn} onClick={() => sendFunc(obj.code)}
                            disabled={isDisabled || obj.state === true} id={onBtnDisabled}>
                            <TextCenter>
                                <span style={{
                                    fontSize: '12px'
                                }}>
                                    양액 공급
                                    </span>
                            </TextCenter>
                        </button>
                    </div>
                </div>
            </div>
        </Fragment>
    )
}

export default Pump;