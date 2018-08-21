import React from 'react';
import PropTypes from 'prop-types';
import { Collapse } from 'reactstrap';
import { MdRouter, MdClear, MdHistory } from 'react-icons/lib/md';
import TextCenter from '../TextCenter/TextCenter';
import './RouterItem.css';
import { Progress } from 'react-sweet-progress';
import "react-sweet-progress/lib/style.css";
import Moment from 'react-moment';

const RouterItem = ({ apName, userCode, ip, apCode, deleteFunc, count, regDate, open, close }) => {
    count *= 4;
    return (

        <div className="col-lg-3 col-md-4 col-sm-6 portfolio-item mb-3">
            <div className="list-group-item RouterItem" onClick={open}
                id="RouterItem-border"
            >
            <div className="container-fluid" id="nopadding">
                <div className="row md-5 padding-left">
                    <div className="col-2 mt-2">
                        <MdRouter size={35} color="#89bf04" />
                    </div>
                    <div className="col-7 mt-2">
                        <span className="title ml-1 mt-2 md-3"><strong>{apName}#{apCode}</strong></span>
                        <div className="text-muted smaller size-8 ml-1">{ip}</div>
                    </div>
                    <div className="col-3" id="">
                        <button className="RouterItem-clear-btn" id="nopadding"
                            onClick={deleteFunc}>
                            <MdClear size={20}/>
                        </button>
                    </div>
                </div>
                <div className="row" id="padding-horizontal">
                    <div className="col-5 col-xs-10 mt-5" id="nopadding-right">
                        <span className="smaller RouterItem-action">
                            동작중
                        </span>
                    </div>
                    <div className="col-7 col-xs-2 mt-5 text-right">
                        <MdHistory size={17} />
                        <span className="smaller size-9 ml-1 bold">
                            <Moment format="YYYY-MM-DD">
                                {new Date(regDate)}
                            </Moment>
                        </span>
                    </div>
                </div>
                <div className="row padding-bottom" id="padding-horizontal">
                    <div className="col-12 mt-1">
                        <Progress
                            percent={count}
                            status="default"
                            theme={
                                {
                                    default: {
                                        symbol:
                                            <strong className="text-muted smaller size-8">
                                                {count / 4 + '/25'}
                                            </strong>,
                                        trailColor: 'lightblue',
                                        color: '#24c9eb'
                                    }
                                }
                            }
                        />
                    </div>
                </div>
                </div>
            </div>
        </div>
    )
}

RouterItem.propTypes = {
    apName: PropTypes.string.isRequired,
    ip: PropTypes.string.isRequired,
    userCode: PropTypes.number.isRequired,
    apCode: PropTypes.number.isRequired,
    deleteFunc: PropTypes.func.isRequired,
    count: PropTypes.number.isRequired,
    regDate: PropTypes.number.isRequired,
    open:PropTypes.func.isRequired,
    close:PropTypes.func.isRequired,
}

export default RouterItem;