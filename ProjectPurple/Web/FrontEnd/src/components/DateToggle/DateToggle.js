import React, { Component } from 'react';
import './DateToggle.css';
import PropTypes from 'prop-types'
import Moment from 'react-moment';

class DateToggle extends Component {
    static propTypes = {
        onClick: PropTypes.func,
    }

    render() {
        const disabled = this.props.disabled;
        return (
            <div>
                <span className="text-muted smaller mr-1 size-8">날짜</span>
                <button className="DateToggle-btn" onClick={this.props.onClick} 
                disabled={disabled ? "disabled":''}>
                    <span id="nanum-gothic">
                        {!disabled && <Moment format="YYYY-MM-DD">
                            {this.props.startDate}
                        </Moment>}
                        { disabled && <span className="text-muted smaller size-8">데이터 없음</span> }
                    </span>
                </button>
            </div>
        )
    }
};

export default DateToggle;
