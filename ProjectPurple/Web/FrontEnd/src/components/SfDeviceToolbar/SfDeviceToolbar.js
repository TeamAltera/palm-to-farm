import React from 'react';
import PropTypes from 'prop-types';
import './SfDeviceToolbar.css';
import { MdSimCard, MdNavigateBefore } from 'react-icons/lib/md';

const SfDeviceToolbar = ({ 
    children, backFunc, sfCode, sendFunc, startFunc, farmingState}) => {
        let btnText='재배중지';
        let func=()=>sendFunc(11);
        if(!farmingState){
            btnText='재배시작';
            func=startFunc;
        }
        console.log(farmingState);
    return (
        <div className="mt-0 SfDeviceToolbar-container container-fluid" id="nopadding">
            <div className="col-lg-6 col-md-4 col-sm-4 SfDeviceToolbar-container-left" id="nopadding">
                <div className="SfDeviceToolbar-back" onClick={backFunc}>
                    <MdNavigateBefore size={35} />
                </div>
                <div className="mt-2">
                    <span className="SfDeviceToolbar-item size-10">
                        sf-device#{sfCode}
                    </span>
                </div>
            </div>
            <div className="col-lg-6 col-md-8 col-sm-8 SfDeviceToolbar-container-right" id="nopadding">
                <div className="mr-2">
                    {children}
                </div>
                <div className="MainToolBar-btn mr-2 SfDeviceToolbar-btn"
                onClick={func}
                >
                    <span className="smaller size-9 ml-1" id="nanum-gothic">
                    {btnText}
                    </span>
                </div>
                <div className="MainToolBar-btn mr-3 SfDeviceToolbar-btn">
                    <span className="smaller size-9 ml-1" id="nanum-gothic">수동전환</span>
                </div>
            </div>
        </div>
    )
};

SfDeviceToolbar.propTypes = {
}

export default SfDeviceToolbar;