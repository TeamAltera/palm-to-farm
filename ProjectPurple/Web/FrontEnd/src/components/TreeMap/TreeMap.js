import React, { Component } from 'react';
import './TreeMap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import raz_router from '../../assets/images/raz_router.png';
import {
  Alert,
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Form,
  Input,
  FormGroup,
} from 'reactstrap';
import * as MainApi from '../../lib/api/main';

class TreeMap extends Component {
  state = {
    first: '',
    second: '',
    third: '',
    fourth: '',
    modal: false,
  };

  doConfirmIp = async () => {
    const { first, second, third, fourth } = this.state;
    try {
      await MainApi.confirm_ip(first, second, third, fourth);
    } catch (e) {
      console.log(e);
    }
  };

  handleChange = (e, { name, value }) => this.setState({ [name]: value });

  toggle = () =>
    this.setState({
      modal: !this.state.modal,
    });

  render() {
    const { first, second, third, fourth } = this.state;
    return (
      <div className="TreeMap">
        <Button
          color="primary"
          className="TreeMap__apBtn"
          onClick={this.toggle}>
          AP추가
        </Button>
        <Button color="primary" className="TreeMap__moduleBtn">
          공유기추가
        </Button>

        <Modal
          isOpen={this.state.modal}
          toggle={this.toggle}
          className={this.props.className}
          size="lg">
          <ModalHeader toggle={this.toggle}>공유기 추가</ModalHeader>
          <ModalBody>
            <img src={raz_router} />
            <p />
            <p>1.사용자의 디바이스로 라즈베리파이 공유기에 연결합니다.</p>
            <p>2.브라우저에서 192.168.4.1로 접속합니다.</p>
            <p>
              3.접속해서 나오는 공유기의 IP정보를 하단에 입력하고 등록합니다.
            </p>
            <p>라즈베리공유기 API :</p>
            <Form inline>
              <FormGroup>
                <Input
                  placeholder="A"
                  type="text"
                  value={first}
                  onChange={this.handleChange}
                  name="first"
                />
                <Input
                  placeholder="B"
                  type="text"
                  value={second}
                  onChange={this.handleChange}
                  name="second"
                />
                <Input
                  placeholder="C"
                  type="text"
                  value={third}
                  onChange={this.handleChange}
                  name="third"
                />
                <Input
                  placeholder="D"
                  type="text"
                  value={fourth}
                  onChange={this.handleChange}
                  name="fourth"
                />
                <Button onClick={this.doConfirmIp}>조회</Button>
              </FormGroup>
            </Form>
          </ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={this.toggle}>
              등록
            </Button>{' '}
            <Button color="secondary" onClick={this.toggle}>
              취소
            </Button>
          </ModalFooter>
        </Modal>
      </div>
    );
  }
}

export default TreeMap;
