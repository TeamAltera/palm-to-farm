import React, { Component, Fragment } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as baseActions from 'redux/modules/base';
import { Line } from 'react-chartjs-2';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

class Graph extends Component {
  state = {
    data: {
      labels: ['13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00'],
      datasets: [
        {
          label: 'Sample dataset',
          fill: false,
          lineTension: 0.1,
          backgroundColor: 'rgba(75,192,192,0.4)',
          borderColor: 'rgba(75,192,192,1)',
          borderCapStyle: 'butt',
          borderDash: [],
          borderDashOffset: 0.0,
          borderJoinStyle: 'miter',
          pointBorderColor: 'rgba(75,192,192,1)',
          pointBackgroundColor: '#fff',
          pointBorderWidth: 1,
          pointHoverRadius: 5,
          pointHoverBackgroundColor: 'rgba(75,192,192,1)',
          pointHoverBorderColor: 'rgba(220,220,220,1)',
          pointHoverBorderWidth: 2,
          pointRadius: 1,
          pointHitRadius: 10,
          data: [65, 59, 80, 81, 56, 55, 40],
        },
      ],
    },
  };

  connect() {
    var subscribeCode = 0;
    var stompClient = null;
    var socket = new SockJS(
      'http://203.250.32.180:9001/smart_plant/sensing_data'
    );
    stompClient = Stomp.over(socket);
    stompClient.connect('manager', 'manager', function(frame) {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/messages' + subscribeCode, function(
        message
      ) {
        var msg = JSON.stringify(message.body);
        console.log(msg['t']);
        //dataAdd(parseInt(msg['t']));
      });
    });
  }

  componentDidMount() {
    this.connect();
  }

  render() {
    return (
      <Fragment>
        <Line data={this.state.data} height="720" width="800" />
      </Fragment>
    );
  }
}

export default connect(
  state => ({}),
  dispatch => ({
    BaseActions: bindActionCreators(baseActions, dispatch),
  })
)(Graph);
