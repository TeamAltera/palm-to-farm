import React,{Fragment} from 'react';
import './SingleChart.css';

const SingleChart = ({arg, arg_ref, dash_ref, unit, lim}) => {
    return(
        <Fragment>
        <div className="single-chart">
            <svg viewBox="0 0 36 36" className="circular-chart orange">
                <path className="circle-bg"
                    d="M18 2.0845
                    a 15.9155 15.9155 0 0 1 0 31.831
                    a 15.9155 15.9155 0 0 1 0 -31.831"
                />
                <path className="circle"
                    strokeDasharray={arg+","+lim}
                    ref={dash_ref}
                    d="M18 2.0845
                    a 15.9155 15.9155 0 0 1 0 31.831
                    a 15.9155 15.9155 0 0 1 0 -31.831"
                />
                <text x="18" y="20.35" className="percentage" ref={arg_ref}>
                    {arg}{unit}
                </text>
            </svg>
        </div>
        
        </Fragment>
)};

export default SingleChart;