export default function scorePassword(password) {
 
    //TextBox left blank.
    if (password.trim() === "") {
        return  0;
    }

    //Regular Expressions.
    let regex = [];
    regex.push("[A-Z]"); //Uppercase Alphabet.
    regex.push("[a-z]"); //Lowercase Alphabet.
    regex.push("[0-9]"); //Digit.
    regex.push("[$@$!%*#?&]"); //Special Character.

    var passed = -1;

    //Validate for each Regular Expression.
    for (var i = 0; i < regex.length; i++) {
        if (new RegExp(regex[i]).test(password)) {
            passed++;
        }
    }

    //Validate for length of Password.
    if (password.length > 2) {
        passed++;
    }
    else if (passed > 2 && password.length > 8) {
        passed++;
    }

    return passed;
}