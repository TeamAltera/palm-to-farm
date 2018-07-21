
export default function SigninValidator(email, password) {
    const emailPattern =
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    let error = null;

    if (email.trim() === "") {
        error = {
            msg: '이메일을 입력하세요.',
            location: 1
        }
    }
    else if (!emailPattern.test(email)) {
        error = {
            msg: '이메일 주소가 형식에 맞지 않습니다.',
            location: 1
        };
    }
    else if (password.trim() === "")
        error = {
            msg: '비밀번호를 입력하세요.',
            location: 2
        }
    return error;
}
