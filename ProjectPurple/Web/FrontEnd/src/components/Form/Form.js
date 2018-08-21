import React from 'react';
import PropTypes from 'prop-types';
import './Form.css'

const Form = ({ children, isError }) => {
    // ={"card card-" + type + " mx-auto mt-5"} 
    // <div id="cont">
    //     <div id="Form">
    //         <div id="Form-item">
    //             {children}
    //         </div>
    //         <div className="Form-back-image"></div>
    //     </div>
    // </div>
    let wid='';
    if(isError){
        wid=' width';
    }
    return(
    <main>
        <div className={"Form-cont"+wid}>
            {/* <form action="">
                <img src="https://bit.ly/2tlJLoz" /><br />
                <input type="text" value="@AmJustSam" /><br />
                <input type="password" /><br />
                <input type="submit" value="SIGN IN" /><br />
                <span><a href="#">Forgot Password?</a></span>
            </form> */}
            <div id="Form">
                {children}
            </div>
        </div>
    </main>
    );
};

Form.propTypes = {
    onSubmit: PropTypes.func,
}

export default Form;