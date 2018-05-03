import React, { Component } from "react";
import { Button, Form, Modal, Header, Image } from "semantic-ui-react";
import { MainWrapper } from "../../components";
import raz_router from "../../assets/images/raz_router.png";

class MainContainer extends Component {
  render() {
    return (
      <MainWrapper>
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
                  <Form.Input placeholder="A" type="text" />
                  <Form.Input placeholder="B" type="text" />
                  <Form.Input placeholder="C" type="text" />
                  <Form.Input placeholder="D" type="text" />
                  <Form.Button>조회</Form.Button>
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
