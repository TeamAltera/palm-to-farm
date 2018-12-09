import React from 'react';
import './PageBody.css'
import { MdDashboard } from 'react-icons/lib/md';

const PageBody = ({ children, routerCnt, disabled }) => {
    return (

        <div id="PageBody">
            <div id="PageBody-addiction">
                <div className={"container-fluid mt-3"}>
                    {!disabled && <div className="mb-3">
                        <MdDashboard size={26} className="mb-1" />
                        <span className="router-list ml-1">
                            {"보유 라우터 목록(" + routerCnt + "개)"}
                        </span>
                    </div>}
                    <div className="row" id="nopadding">
                        {children}
                    </div>
                </div>
            </div>
        </div>
    )
};

export default PageBody;