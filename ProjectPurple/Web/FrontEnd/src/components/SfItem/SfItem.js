import React from 'react';
import PropTypes from 'prop-types';
import './SfItem.css'
import { MdSimCard, MdClear } from 'react-icons/lib/md';
import Moment from 'react-moment';
import TextCenter from '../TextCenter/TextCenter';

const SfItem = ({ }) => {
    return (
        <div className="SfItem-box mt-3">
            <a className="list-group-item list-group-item-action RouterItem" id="nopadding">
                <div className="container-fluid">
                    <div className="row SFItem-title">
                        <div className="col-11">
                            <div className="ml-1 mt-1 mb-1">
                                <MdSimCard size={20} />
                                <span className="SfItem-title ml-1 mt-2 md-3" id="nanum-gothic">
                                    sf-device#1
                                </span>
                            </div>
                        </div>
                        <div className="col-1" id="nopadding">
                            <button type="button"
                                className="SfItem-btn-clear">
                                <MdClear />
                            </button>
                        </div>
                    </div>
                    {/* <div className="row">
                        <div className="col-11">
                            <div className="ml-1 mb-1">
                                <span className="text-muted smaller size-8 ml-4">192.168.4.2</span>
                            </div>
                        </div>
                    </div> */}
                    <div className="row SfItem-line" id="nopadding">
                        <div className="col-3 SfItem-line-right">
                            <div className="row">
                                <div className="col-12 SFItem-small-title">
                                    <TextCenter>
                                        <span className="text-muted smaller size-9" id="nanum-gothic">
                                            내부 아이피
                                        </span>
                                    </TextCenter>
                                </div>
                            </div>
                            <div className="row SfItem-line">Lorem ipsum dolor </div>
                        </div>
                        <div className="col-3 SfItem-line-right">
                            <div className="row">
                                <div className="col-12">
                                    <TextCenter>
                                        <span className="text-muted" id="nanum-gothic">
                                            내부 아이피
                                        </span>
                                    </TextCenter>
                                </div>
                            </div>
                            <div className="row SfItem-line">Lorem ipsum dolor </div>
                        </div>
                        <div className="col-3 SfItem-line-right">
                            <div className="row">
                                <div className="col-12">
                                    <TextCenter>
                                        <span className="text-muted" id="nanum-gothic">내부 아이피</span>
                                    </TextCenter>
                                </div>
                            </div>
                            <div className="row SfItem-line">Lorem ipsum dolor </div>
                        </div>
                        <div className="col-3">
                            <div className="row">
                                <div className="col-12">
                                    <TextCenter>
                                        <span className="text-muted" id="nanum-gothic">내부 아이피</span>
                                    </TextCenter>
                                </div>
                            </div>
                            <div className="row SfItem-line">Lorem ipsum dolor </div>
                        </div>
                    </div>
                </div>
            </a>
        </div>
    )
};

SfItem.propTypes = {

}

export default SfItem;