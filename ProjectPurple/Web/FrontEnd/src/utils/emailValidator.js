
export default function emailValidator(email) {
    const emailPattern =
        /^(([^<>()/[\]\\.,;:\s@"]+(\.[^<>()/[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (email.trim() !== "") {
        if (!emailPattern.test(email)) {
            return 3;
        }
        else {
            return 2;
        }
    }
    else
        return 0;
}
