import React from 'react';
import PropTypes from 'prop-types';
import { Collapse } from 'reactstrap';
import { MdRouter, MdClear, MdDeleteForever } from 'react-icons/lib/md';
import TextCenter from '../TextCenter/TextCenter';
import { Progress } from 'react-sweet-progress';
import "react-sweet-progress/lib/style.css";
import Moment from 'react-moment';
//import './RouterItem.css';
import './Router.css';
import { Copyright } from '..';

const RouterItem = ({ apName, userCode, ip, apCode, deleteFunc, count, regDate, open, close }) => {
    count *= 4;
    return (

        <div className="col-lg-4 col-md-4 col-sm-6 col-xs-6 mb-3">
            <div className="router-container">
                <div className="router-header">
                    <div className="router-name">
                        <MdRouter size={25} color="#89bf04" />
                        <div className="router-name-box">
                            <span className="router-name-text">{apName}#{apCode}</span>
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
                    <div className="row mt-1 mb-1">
                        <div className="col-4 router-label">공용 아이피</div>
                        <div className="col-8 router-content">
                        {ip}
                        </div>
                    </div>
                    <div className="row mt-1 mb-1">
                        <div className="col-4 router-label">라우터 등록 시간</div>
                        <div className="col-8 router-content">
                            <Moment format="YYYY/MM/DD A HH:mm:ss">
                                {new Date(regDate)}
                            </Moment>
                        </div>
                    </div>
                    <div className="row mt-1 mb-1">
                        <div className="col-4 router-label">보유 재배기</div>
                        <div className="col-8 router-content">
                            <Progress
                                percent={count}
                                status="default"
                                theme={
                                    {
                                        default: {
                                            symbol:
                                                <strong className="size-8">
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
                    <div className="row mt-1 mb-1" style={{borderTop:'1px solid #eee', paddingTop:'10px'}}>
                        <div className="col-4 router-label">라우터 상태</div>
                        <div className="col-8 router-content">    
                            <span>동작중</span>
                        </div>
                    </div>
                </div>
                <div className="router-footer">
                    <button className="router-execute" onClick={open}>
                        <span>라우터 조회</span>
                    </button>
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
    open: PropTypes.func.isRequired,
    close: PropTypes.func.isRequired,
}

export default RouterItem;