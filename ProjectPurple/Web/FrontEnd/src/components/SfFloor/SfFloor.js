import React, { Fragment } from 'react';
import './SfFloor.css';

const SfFloor = ({ floorIndex, children, resetPipeFunc, resetFloorFunc, option }) => {
    let pipeUp = '1~8';
    let pipeDn = '9~16';
    let up = 0;
    let dn = 1;
    if (floorIndex === 2) {
        pipeUp = '17~24';
        pipeDn = '25~32';
        up = 2;
        dn = 3;
    }
    return (
        <Fragment>
            <div>
                <div className="row StartFarmingModal-justify-left mb-0 mr-1 ml-1">
                    <span className="StartFarmingModal-floor-title smaller size-8" id="nanum-gothic">
                        <strong>{floorIndex}F</strong>
                    </span>
                    {
                        option &&
                        <Fragment>
                            <span className="StartFarmingModal-floor-blank smaller size-8" id="nanum-gothic"
                                onClick={() => resetFloorFunc(up, dn)}>
                                전체
                        </span>
                            <span className="StartFarmingModal-floor-blank smaller size-8" id="nanum-gothic"
                                onClick={() => resetPipeFunc(up, false)}
                            >
                                {pipeUp}
                            </span>
                            <span className="StartFarmingModal-floor-blank smaller size-8" id="nanum-gothic"
                                onClick={() => resetPipeFunc(dn, false)}
                            >
                                {pipeDn}
                            </span>
                        </Fragment>
                    }
                </div>
                <div className="row StartFarmingModal-justify mb-3 mr-1 ml-1">
                    <div className="StartFarmingModal-floor">
                        {children}
                    </div>
                </div>
            </div>

        </Fragment>
    )
};

export default SfFloor;