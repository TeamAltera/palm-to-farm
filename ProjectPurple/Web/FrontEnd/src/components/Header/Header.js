import React from 'react';
import './Header.css';
import PropTypes from 'prop-types';
import { MdChevronRight, MdChevronLeft, MdAccountCircle } from 'react-icons/lib/md';
import { Popover, PopoverHeader, PopoverBody } from 'reactstrap';

const Header = ({ onClick, direction, popover }) => {
    return (
        <nav className="absolute-top navbar navbar-absolute navbar-expand-lg"
            id="Header">
            <div className="container-fluid" id="Header-wrapper">
                <div className="col-10">
                    <a className="Header-toggle"
                        onClick={onClick[0]}
                    >
                        {!direction && <MdChevronRight size={35} color="#2ca8ff" />}
                        {direction && <MdChevronLeft size={35} color="#2ca8ff" />}
                    </a>
                </div>
                <div className="col-2">
                    <a className="Header-toggle" onClick={onClick[1]} id="Popover1">
                        <MdAccountCircle size={35} color="#2ca8ff" />
                    </a>
                    <Popover placement="bottom" isOpen={popover} target="Popover1" toggle={onClick[1]}>
                        <PopoverHeader>
                            sencom1028@gmail.com
                        </PopoverHeader>
                        <PopoverBody>
                            Sed posuere consectetur est at lobortis. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum.
                        </PopoverBody>
                    </Popover>
                </div>
            </div>
        </nav>
    )
};

Header.propTypes = {
    direction: PropTypes.bool.isRequired,
    onClick: PropTypes.array.isRequired,
    popover: PropTypes.bool.isRequired,
}

export default Header;