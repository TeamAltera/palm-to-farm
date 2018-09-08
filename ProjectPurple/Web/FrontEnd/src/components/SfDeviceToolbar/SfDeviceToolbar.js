import React from 'react';
import PropTypes from 'prop-types';
import './SfDeviceToolbar.css';
import { MdSimCard, MdNavigateBefore } from 'react-icons/lib/md';

const SfDeviceToolbar = ({
    children, backFunc, sfCode, sendFunc, startFunc, farmingState, tabSelected, tabChangeFunc }) => {
    let btnText = '재배중지';
    let func = () => sendFunc(11);
    let tabs=['',''];
    if (!farmingState) {
        btnText = '재배시작';
        func = startFunc;
    }
    tabs[tabSelected]=' selected disabled';

    return (
        <div className="mt-0 SfDeviceToolbar-container container-fluid" id="nopadding">

            {/* left */}
            <div className="col-lg-4 col-md-4 col-sm-3" id="nopadding">
                <div className="SfDeviceToolbar-left">
                    <div className="SfDeviceToolbar-back" onClick={backFunc}>
                        <MdNavigateBefore size={35} />
                    </div>
                    <div className="mt-2">
                        <span className="SfDeviceToolbar-item size-10">
                            sf-device#{sfCode}
                        </span>
                    </div>
                </div>
            </div>

            {/* center */}
            <div className="col-lg-4 col-md-3 col-sm-3" id="nopadding">
                <div className="SfDeviceToolbar-center">
                    <button className={"mr-2 SfDeviceToolbar-tab-btn"+tabs[0]}
                        onClick={()=>tabChangeFunc(0)}
                    >
                        <span className="smaller size-8">형상정보</span>
                    </button>
                    <button className={"SfDeviceToolbar-tab-btn"+tabs[1]}
                        onClick={()=>tabChangeFunc(1)}
                    >
                        <span className="smaller size-8">데이터 로거</span>
                    </button>
                </div>
            </div>

            {/* right */}
            <div className="col-lg-4 col-md-5 col-sm-6" id="nopadding">
                <div className="SfDeviceToolbar-right">
                    <div className="mr-2">
                        {children}
                    </div>
                    <div className="MainToolBar-btn mr-2 SfDeviceToolbar-btn"
                        onClick={func}>
                        <span className="smaller size-9 ml-1" id="nanum-gothic">
                            {btnText}
                        </span>
                    </div>
                    {/* <div className="MainToolBar-btn mr-3 SfDeviceToolbar-btn">
                        <span className="smaller size-9 ml-1" id="nanum-gothic">수동전환</span>
                    </div> */}
                </div>
            </div>
        </div>
    )
};

SfDeviceToolbar.propTypes = {
}

export default SfDeviceToolbar;