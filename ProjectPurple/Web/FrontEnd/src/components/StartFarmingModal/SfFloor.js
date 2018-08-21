import React from 'react';
import './SfFloor.css';

const SfFloor = ({ floorIndex, children, resetPipeFunc, resetFloorFunc }) => {
    let pipeUp='1~8';
    let pipeDn='9~16';
    let up=0;
    let dn=1;
    if(floorIndex===2){
        pipeUp='17~24';
        pipeDn='25~32';
        up=2;
        dn=3;
    }
    return (
        <div>
            <div className="row StartFarmingModal-justify-left mb-0 mr-1 ml-1">
            <span className="StartFarmingModal-floor-title smaller size-8" id="nanum-gothic">
                <strong>{floorIndex}F</strong>
            </span>
            <span className="StartFarmingModal-floor-blank smaller size-8" id="nanum-gothic"
            onClick={()=>resetFloorFunc(up,dn)}>
                <strong>전체</strong>
            </span>
            <span className="StartFarmingModal-floor-blank smaller size-8" id="nanum-gothic"
            onClick={()=>resetPipeFunc(up,false)}
            >
                <strong>{pipeUp}</strong>
            </span>
            <span className="StartFarmingModal-floor-blank smaller size-8" id="nanum-gothic"
            onClick={()=>resetPipeFunc(dn,false)}
            >
                <strong>{pipeDn}</strong>
            </span>
            </div>
            <div className="row StartFarmingModal-justify mb-3 mr-1 ml-1">
                <div className="StartFarmingModal-floor">
                    {children}
                </div>
            </div>
        </div>
    )
};

export default SfFloor;