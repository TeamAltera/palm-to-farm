import React, { Component, Fragment } from 'react';
import { Button } from 'semantic-ui-react';

class BottonGroup extends Component {
  state = {
    cSelected: [],
  };

  onRadioBtnClick(rSelected) {
    this.setState({ rSelected });
  }

  onCheckboxBtnClick(selected) {
    const index = this.state.cSelected.indexOf(selected);
    if (index < 0) {
      this.state.cSelected.push(selected);
    } else {
      this.state.cSelected.splice(index, 1);
    }
    this.setState({ cSelected: [...this.state.cSelected] });
  }

  render() {
    return (
      <Fragment>
        <Button.Group>
          <Button
            color="facebook"
            onClick={() => this.onRadioBtnClick(1)}
            size="massive"
            active={this.state.rSelected === 1}>
            Pump
          </Button>
          <Button
            color="facebook"
            onClick={() => this.onRadioBtnClick(2)}
            size="massive"
            active={this.state.rSelected === 2}>
            Light
          </Button>
          <Button
            color="facebook"
            onClick={() => this.onRadioBtnClick(3)}
            size="massive"
            active={this.state.rSelected === 3}>
            Cooler
          </Button>
        </Button.Group>
        <p>{this.state.rSelected}</p>
      </Fragment>
    );
  }
}

export default BottonGroup;
