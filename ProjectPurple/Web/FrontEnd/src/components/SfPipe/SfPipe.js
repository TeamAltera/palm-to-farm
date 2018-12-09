import React from 'react';
import './SfPipe.css';
import { MdLocalFlorist } from 'react-icons/lib/md';

const SfPipe = ({ portIndex, onChange, pipe, pipeIndex, option }) => {
    let cnt= 0;
    return (
        <div className="ml-3 mr-3 mt-3">
            <div className="row StartFarmingModal-justify mb-0">
                <div className="StartFarmingModal-tline"></div>
                {option &&
                <div className="StartFarmingModal-pipe">
                    {
                        pipe.map(
                            (port) => <SfPort index={cnt} key={cnt} 
                            onChange={onChange} checked={port.toJS().status} 
                            value={pipeIndex}
                            name={cnt++}
                            />
                        )
                    }
                </div>}
                {!option &&
                <div className="StartFarmingModal-pipe">
                    {
                        pipe.map(
                            (port) => <SfPortNoInput key={cnt++} checked={port.status}
                            />
                        )
                    }
                </div>}
                <div className="StartFarmingModal-bline"></div>
            </div>
            <div className="row StartFarmingModal-justify mb-0">
                <div className="row StartFarmingModal-index mb-1">
                    {
                        pipe.map(
                            (index) => <SfCount index={portIndex} key={portIndex++} />
                        )
                    }
                </div>
            </div>
        </div>
    )
};

const SfPort = ({ index, onChange, checked, value, name }) => {
    let isCheck=""
    if(checked) isCheck="checked"
    return (
        <label id="myCheckbox">
            <input type="checkbox" name={index} checked={isCheck} onChange={onChange} 
            value={value} name={name}/>
            <span style={{paddingLeft:'5px'}}>
                {checked && <MdLocalFlorist size={20} color='#3f831c'/>}
            </span>
        </label>
    )
}

const SfPortNoInput = ({ checked }) => {
    return (
        <div id="myCheckbox">
            {/* <div type="checkbox"/> */}
            <span style={{cursor: 'default'}}><span id="nopadding" 
            style={{cursor: 'default',  paddingLeft:'2px', paddingRight: '2px', paddingBottom:'2px', paddingTop:'0px'}}
            >
                {checked && <MdLocalFlorist size={20} color='#3f831c'/>}
            </span></span>
        </div>
    )
}

const SfCount = ({ index }) =>
    <div className="StartFarmingModal-count">
        <span className="text-muted smaller size-8" id="nanum-gothic"
        style={{fontSize:'11px'}}>{index}</span>
    </div>

export default SfPipe;