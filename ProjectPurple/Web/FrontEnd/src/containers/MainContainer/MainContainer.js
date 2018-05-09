import React, { Component } from 'react';
import { Button, Form, Modal, Image } from 'semantic-ui-react';
import { MainWrapper } from '../../components';
import raz_router from '../../assets/images/raz_router.png';
import * as MainApi from '../../lib/api/main';
import { Link } from 'react-router-dom';

class MainContainer extends Component {
  state = {
    first: '',
    second: '',
    third: '',
    fourth: '',
  };

  doConfirmIp = async () => {
    const { first, second, third, fourth } = this.state;
    try {
      await MainApi.confirm_ip(first, second, third, fourth);
    } catch (e) {
      console.log(e);
    }
  };

  moveGraph = () => {};

  handleChange = (e, { name, value }) => this.setState({ [name]: value });

  render() {
    const { first, second, third, fourth } = this.state;
    return (
      <MainWrapper>
        <Link to="graph">그래프</Link>
        <Modal trigger={<Button content="수경재배기 ap 추가" />}>
          <Modal.Header>공유기 추가</Modal.Header>
          <Modal.Content>
            <Modal.Description>
              <Image size="medium" src={raz_router} />
              <p>1.사용자의 디바이스로 라즈베리파이 공유기에 연결합니다.</p>
              <p>2.브라우저에서 192.168.4.1로 접속합니다.</p>
              <p>
                3.접속해서 나오는 공유기의 IP정보를 하단에 입력하고 등록합니다.
              </p>
              <Form>
                라즈베리공유기 API :
                <Form.Group widths="equal">
                  <Form.Input
                    placeholder="A"
                    type="text"
                    value={first}
                    onChange={this.handleChange}
                    name="first"
                  />
                  <Form.Input
                    placeholder="B"
                    type="text"
                    value={second}
                    onChange={this.handleChange}
                    name="second"
                  />
                  <Form.Input
                    placeholder="C"
                    type="text"
                    value={third}
                    onChange={this.handleChange}
                    name="third"
                  />
                  <Form.Input
                    placeholder="D"
                    type="text"
                    value={fourth}
                    onChange={this.handleChange}
                    name="fourth"
                  />
                  <Form.Button onClick={this.doConfirmIp}>조회</Form.Button>
                </Form.Group>
              </Form>
              <Button>등록</Button>
            </Modal.Description>
          </Modal.Content>
        </Modal>
      </MainWrapper>
    );
  }
}

export default MainContainer;
