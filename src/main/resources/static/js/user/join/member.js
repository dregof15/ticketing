let pageObj = {
    save: function () {
        let form = $form.getData();
        // form1.email = form1.idEmail + "@" + form1.domainEmail;

        console.log(form)
        console.log("form.id:" + form.id);

        if (idCheckFlag == "N") {
            alert("아이디 중복을 확인해 주세요.");
            $("#mberId").focus()
            return false;
        }

        if (idCheckFlag == "Y" && idRegExp() && pwdCheck() && mberValidation()) {
            let smsFlag = true;
            if (`smsFlag`) {
                $ajax.post({
                    url: '/join/member',
                    data: form,
                    success: function (res) {
                        if (res == 'N') {
                            idCheckFlag = 'Y';

                            $ajax.get({
                                data: form,
                                success: function (user) {
                                    location.href = '/join/complete'
                                }
                            })
                        } else {
                            alert("회원가입에 실패 하였습니다.");
                            idCheckFlag = 'N';
                        }
                    }
                })
            }
        }
    }
}

function idRegExp() {
    // var regExp = /^[a-z]+[a-z0-9]{4,19}$/g;
    var regExp = /^[a-z]+[a-z0-9]{1,19}$/g;
    var id = $("#mberId").val();

    if (id === null) {
        alert("아이디를 입력해 주세요.");
        $("#mberId").focus();
        return false;
    }

    if (!regExp.test(id)) {
        alert("아이디를 확인해 주세요.");
        $("#mberId").focus();
        return false;
    } else {
        return true;
    }
}

function pwdCheck() {
    let reg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/

    let pwd = $("#pw").val();
    let pwdCheck = $("#pwCheck").val();


    if (pwd === null) {
        alert("비밀번호를 입력해 주세요.");
        $("#pw").focus();
        return false;
    }
    if (pwdCheck === null) {
        alert("비밀번호를 입력해 주세요.");
        $("#pwCheck").focus();
        return false;
    }
    if (!reg.test(pwd)) {
        alert("8~20자 영문 대소문자, 숫자, 특수문자를 사용하세요.");
        $("#pw").focus();
        return false;
    }
    if (pwd != pwdCheck) {
        alert("비밀번호가 일치하지 않습니다 비밀번호를 확인해 주세요.");
        $("#pwCheck").focus();
        return false;
    }

    if (pwd == pwdCheck) {
        return true;
    } else {
        alert("비밀번호가 일치하지 않습니다.");
        $("#pw").focus();
        return false;
    }
}

function mberValidation() {
    if ($("#name").val() === null || $("#name").val() == "") {
        alert("가입자명을 입력해 주세요.");
        $("#name").focus();
        return false;
    }

    if (!($("#idEmail").val() === null) && $("#domainEmail").val() == ""){ // 이메일 체크
        if($("#domainEmail").val()  === null){
            alert("이메일 @ 뒷 도메인을 입력해 주세요.");
            $("#domainSelect").focus()
            return false;
        }
    }

    if (($("#zip").val() === null) || ($("#adres").val() === null) || ($("#ctprvn").val() === null)) {
        alert("주소를 선택해 주세요.");
        return false;
    }

    if(($("#adresDetail").val() === null) || $("#adresDetail").val() == ""){
        alert("상세주소를 입력해 주세요.");
        return false;
    }

    return true;
}

function memberreplaceCheck() { // 아이디,가입자명,이메일

    // 특수문자 정규식 변수(공백 미포함)
    let replaceChar = /[~!@\#$%^&*\()\-=+_'\;<>0-9\/.\`:\"\\,\[\]?|{}]/gi;

    // 완성형 아닌 한글 정규식
    let replaceNotFullKorean = /[ㄱ-ㅎㅏ-ㅣ]/gi;

    // 영어 정규식 변수
    let replaceId = /[a-z A-Z]/gi;

    // 특수문자 변수(공백 미포함)
    let replacenSchar = /[~!@\#$%^&*\()\-=+_'\;<>\/.\`:\"\\,\[\]?|{}]/gi;

    $("input[formatter='userId']").on("focusout", function() { //아이디
        var x = $(this).val();
        if (x.length > 0) {
            if (x.match(replacenSchar) || x.match(replaceNotFullKorean)) {
                x = x.replace(replacenSchar, "").replace(replaceNotFullKorean, "");
            }
            $(this).val(x);
        }
    }).on("keyup", function() {
        $(this).val($(this).val().replace(replacenSchar, "").replace(replaceNotFullKorean, ""));
    });

    $("input[formatter='replaceKo']").on("focusout", function () { //가입자명
        var x = $(this).val();
        if (x.length > 0) {
            if (x.match(replaceChar) || x.match(replaceNotFullKorean) || x.match(replaceId)) {
                x = x.replace(replaceChar, "").replace(replaceNotFullKorean, "").replace(replaceId, "");
            }
            $(this).val(x);
        }
    }).on("keyup", function () {
        $(this).val($(this).val().replace(replaceChar, "").replace(replaceId, ""));
    });

    $("input[formatter='email']").on("focusout", function () { //이메일
        var x = $(this).val();
        if (x.length > 0) {
            if (x.match(replaceNotFullKorean)) {
                x = x.replace(replaceNotFullKorean, "");
            }
            $(this).val(x);
        }
    }).on("keyup", function () {
        $(this).val($(this).val().replace(replaceNotFullKorean, ""));
    });
}

var idCheckFlag = "N";

$(document).ready(function () {
    eventInit();
    memberreplaceCheck();
})

function eventInit() {
    $("#idCheck").on("click", idCheck);
    $("#mberId").keypress(function () { idCheckFlag = 'N'; })
    $("#domainSelect").on('change', domainSelectOnchange);
    $("#addPhone").on('click', addPhone);

    domainSelect();
}

function idCheck() {
    let id = $("#id").val();
    let regExp = /^[a-z]+[a-z0-9]{1,19}$/g;

    console.log("아이디 체크 중")

    if (id === null) {
        alert("아이디를 입력해주세요.");
        $("#id").focus();
        idCheckFlag = 'N';
        return false;
    } else {
        $ajax.post({
            url: '/join/idCheck',
            data: {mberId : id},
            success: function (res) {
                if (!regExp.test(id)){
                    alert("아이디는 영문 또는 영문 + 숫자를 포함하여 생성해 주세요.");
                    $("#id").focus();
                    idCheckFlag = 'N';
                }
                else if (res == 'N') {
                    alert("사용 가능한 아이디입니다.");
                    idCheckFlag = 'Y';
                } else {
                    alert("이미 사용 중인 아이디입니다.");
                    $("#id").focus();
                    idCheckFlag = 'N';
                }
            }
        })
    }
}

function domainSelect() {
    let list = ['naver.com', 'google.com', 'hanmail.net', 'nate.com', ''];

    list.forEach(domain => {
        let code;
        if (domain == '') {
            code = `<option value="none">직접입력</option>`;
        } else {
            code = `<option value="${domain}">${domain}</option>`;
        }

        $("#domainSelect").append(code);
    })
}

function domainSelectOnchange() {
    let domainSelect = $("#domainSelect").val();
    if (domainSelect != null && domainSelect != '' && domainSelect != 'none') {
        $("#domainEmail").prop('readonly', true);
        $("#domainEmail").val(domainSelect);
    } else if (domainSelect == 'none') {
        $("#domainEmail").prop('readonly', false);
        $("#domainEmail").val('');
    } else {
        $("#domainEmail").val('');
        $("#domainEmail").prop('readonly', true);
    }
}

function phoneNum() {
    $("input[formatter='phoneNum']").keyup(function () {
        $(this).attr("maxlength", 13);
        this.value = parse(this.value);
    });

    const parse = function (value) {
        if (!value) {
            return "";
        }

        value = value.replace(/[^0-9]/g, "");

        let result = [];
        let restNumber = "";

        if (value.startsWith("02")) {
            result.push(value.substring(0, 2));
            restNumber = value.substring(2);
        } else if (value.startsWith("1")) {
            restNumber = value;
        } else {
            result.push(value.substring(0, 3));
            restNumber = value.substring(3);
        }

        if (restNumber.length === 7) {
            result.push(restNumber.substring(0, 3));
            result.push(restNumber.substring(3));
        } else {
            result.push(restNumber.substring(0, 4));
            result.push(restNumber.substring(4));
        }

        return result.filter((val) => val).join("-");
    };
}

function addPhone() {
    let addPhoneSize = $("[name=addPhone]").length+1;
    if (addPhoneSize  <= 10) {
        let code = `<div class="form-item">
                        <p class="label"><span>추가 번호</span></p>
                        <div class="number">
                            <input type="text" name="addPhone" placeholder="전화번호" formatter="phoneNum">
                            <a class="btn red" href="javascript:void(0);" onclick="deleteAddPhone(this)">삭제</a>
                        </div>
                    </div>`;
        $("#smsRecPhone").append(code);
        phoneNum();
    } else {
        alert("추가번호는 10개까지 추가할 수 있습니다.");
        return false;
    }
}

function deleteAddPhone(target) {
    $(target).parent().parent().remove();
}
