import React, { Component, Fragment } from 'react';
import { Table } from 'semantic-ui-react';

class LogTable extends Component {
  render() {
    return (
      <Fragment>
        <Table>
          <thead>
            <tr>
              <th />
              <th>동작 결과</th>
              <th>동작 내용</th>
              <th>시간</th>
              <th>유저IP</th>
              <th>재배기코드</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <th scope="row"> 1</th>
              <td>성공</td>
              <td>수동모드</td>
              <td>Tue May 29 2018 03:01:53 GMT+0900 (KST)</td>
              <td>203.2</td>
              <td>79</td>
            </tr>
            <tr>
              <th scope="row"> 2</th>
              <td>성공</td>
              <td>LED켜기</td>
              <td>Tue May 29 2018 03:02:12 GMT+0900 (KST)</td>
              <td>203.2</td>
              <td>79</td>
            </tr>
            <tr>
              <th scope="row"> 3</th>
              <td>실패</td>
              <td>LED켜기</td>
              <td>Tue May 29 2018 03:02:40 GMT+0900 (KST)</td>
              <td>203.3</td>
              <td>79</td>
            </tr>
          </tbody>
        </Table>
      </Fragment>
    );
  }
}

export default LogTable;
