import React from 'react';
import './HeaderBlank.css'
import { MdMenu } from 'react-icons/lib/md';

const HeaderBlank = ({ children }) => {
    return (
        <div className="HeaderBlank">
            {children}
            <div className="HeaderBlank-btn mt-2 ml-3">
                <MdMenu size={25}/>
            </div>
        </div>
    )
};

export default HeaderBlank;