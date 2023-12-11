$errors = {
    valid: function (errors) {
        const globalErrorsTarget = $("[globalErrors]");
        $(".modal_error").each(function () {
            if ($(this).attr("override")) {
                $(this).hide();
                $(this).text("");
            } else {
                $(this).remove();
            }
        });

        $("[errorclass]").each(function () {
            $(this).removeClass($(this).attr("errorclass"));
        });

        const fieldErrors = errors["fieldErrors"];
        if (fieldErrors) {
            fieldErrors.forEach(error => {
                const errorField = $("#" + error.field);
                errorField.addClass(errorField.attr("errorclass"));

                let errorMsgField
                if (errorField.attr("editor")) {
                    errorMsgField = $('[placeholder="' + error.field + '"]')
                    errorMsgField.text(error.message);
                } else {
                    if ($(`span[errors='${error.field}']`).length) {
                        $(`span[errors='${error.field}']`).text(error.message);
                        $(`span[errors='${error.field}']`).css("display", "inline-block");
                    } else {
                        errorMsgField = `<span errors="${error.field}" class="modal_error">${error.message}</span>`
                        errorField.after(errorMsgField);
                    }
                }
            });
        }

        const globalErrors = errors["globalErrors"];
        if (globalErrors) {
            if (globalErrorsTarget.length) {
                globalErrors.forEach(error => {
                    globalErrorsTarget.append(`<span class="modal_error">${error.message}</span>`)
                });
            } else {
                globalErrors.forEach(error => {
                    alert(error.message);
                });
            }
        }
    }
}

$api = {
    addressApi: function (zipCodeId = 'zip', addressId = 'adres',
                          detailId = 'adresDetail', sido = 'ctprvn',
                          sigungu = 'signgu') {
        new daum.Postcode({
            oncomplete: function (data) { //선택시 입력값 세팅
                document.getElementById(zipCodeId).value = data.zonecode; // 주소 넣기
                document.getElementById(addressId).value = data.address; // 주소 넣기
                document.getElementById(sido).value = data.sido;
                document.getElementById(sigungu).value = data.sigungu;
                document.getElementById(detailId).focus();
            }
        }).open();
    }
}

$form = {
    getData: function (target = $("form")) {
        if ($valid.dataValidation()) {
            const formData = new FormData(target[0]);

            if ($("input[type='file']").length) {
                formData.delete("files");

                for (const file of $files.getFiles()) {
                    formData.append("files", file);
                }

                return formData;
            } else {
                return Object.fromEntries(formData);
            }
        }
    }
}

/* 경북 참고 */
$url = {
    getPath: function (extPath) {
        if (extPath && extPath[0] != '/') extPath = '/' + extPath;
        return location.pathname + (extPath ? extPath : '');
    },
    getHostUrl: function () {
        return location.protocol + "//" + this.getHost();
    },
    getHost: function () {
        return location.host;
    },
    redirect: function (path) {
        location.href = path ? path : this.getPath();
    },
    gotoUrl: function (id, url) {
        localStorage["clickMenus"] = id;
        location.href = url;
    }
}


$ajax = {
    defaultOption: {
        url: $url.getPath(),
        contentType: 'application/json',
        async: true
    },

    post: function (options) {
        options = $.extend({}, this.defaultOption, options);
        $.ajax({
            url: options.url,
            type: 'POST',
            data: JSON.stringify(options.data),
            contentType: options.contentType,
            async: options.async
        }).done(function (data) {
            if (options.success) {
                options.success(data);
            } else {
                $url.redirect();
            }
        }).fail(function (error) {
            if (options.error) {
                options.error(error.responseJSON);
            } else {
                $errors.valid(error.responseJSON);
            }
        });
    },
    postMultiPart: function (options) {
        options = $.extend({}, this.defaultOption, options);

        $.ajax({
            url: options.url,
            type: 'POST',
            data: options.data,
            processData: false,
            contentType: false,
            enctype: 'multipart/form-data',
            async: options.async
        }).done(function (data) {
            if (options.success) {
                options.success(data);
            } else {
                $url.redirect();
            }
        }).fail(function (error) {
            if (options.error) {
                options.error(error.responseJSON);
            } else {
                $errors.valid(error.responseJSON);
            }
        });
    },

    put: function (options) {
        options = $.extend({}, this.defaultOption, options);

        $.ajax({
            url: options.url,
            type: 'PUT',
            data: JSON.stringify(options.data),
            contentType: options.contentType,
            async: options.async
        }).done(function (data) {
            if (options.success) {
                options.success(data);
            } else {
                $url.redirect();
            }
        }).fail(function (error) {
            if (options.error) {
                options.error(error.responseJSON);
            } else {
                $errors.valid(error.responseJSON);
            }
        });
    },
    putMultiPart: function (options) {
        options = $.extend({}, this.defaultOption, options);

        $.ajax({
            url: options.url,
            type: 'PUT',
            data: options.data,
            processData: false,
            contentType: false,
            async: options.async
        }).done(function (data) {
            if (options.success) {
                options.success(data);
            } else {
                $url.redirect();
            }
        }).fail(function (error) {
            if (options.error) {
                options.error(error.responseJSON);
            } else {
                $errors.valid(error.responseJSON);
            }
        });
    },

    get: function (options) {
        options = $.extend({}, this.defaultOption, options);

        $.ajax({
            url: options.url,
            type: 'GET',
            contentType: options.contentType,
            async: options.async
        }).done(function (data) {
            if (options.success) {
                options.success(data);
            } else {
                $url.redirect();
            }
        }).fail(function (error) {
            if (options.error) {
                options.error(error.responseJSON);
            } else {
                $errors.valid(error.responseJSON);
            }
        });
    },

    delete: function (options) {
        options = $.extend({}, this.defaultOption, options);

        $.ajax({
            url: options.url,
            type: 'DELETE',
            data: JSON.stringify(options.data),
            contentType: options.contentType,
            async: options.async
        }).done(function (data) {
            alert("삭제 되었습니다.");

            if (options.success) {
                options.success(data);
            } else {
                $url.redirect();
            }
        }).fail(function (error) {
            if (options.error) {
                options.error(error.responseJSON);
            } else {
                console.log(error);
                $errors.valid(error.responseJSON);
            }
        });
    }
}


$valid = {
    delete: function () {
        return confirm("삭제 하시겠습니까?")
    },
    deletes: function (selectCondition) {
        if (!selectCondition) {
            alert("삭제할 항목을 선택해 주세요.");
        } else {
            return confirm("선택된 항목을 삭제 하시겠습니까?")
        }
    },
    duplicate: function (option) {
        var result = true;
        $.ajax({
            url: option.url,
            type: 'POST',
            data: JSON.stringify(option.data),
            contentType: 'application/json',
            async: false,
        }).done(function (data) {
            result = data;
        });

        return result
    },
    modalInputValid: function () {
        let result = true;
        $.each($("input[valid='modal']"), function () {
            let value = this.value;
            let title = this.title;
            if (value == null || value == '') {
                alert(`${title}을(를) 입력해주세요.`);
                $(this).focus();
                return result = false;
            }
        })

        return result;
    },
    periodValid: function (start = moment().format('YYYY-MM-DD'), end = moment().format('YYYY-MM-DD')) {
        let result = true;
        if (new Date(start) >= new Date(end)) {
            alert("기간을 다시 입력해주세요.");
            result = false;
        }

        return result;
    },
    dataValidation: function () {
        let result = true;

        $.each($("[valid*='id']"), function () {
            var regExp = /^[a-zA-Z0-9]{4,19}$/g;
            let r = regExp.test(this.value);

            if (!r) {
                alert(`${this.title}을(를) 확인해 주세요.`);
                $(this).focus();
            }

            return result = r;
        })
        if (!result) return false;

        $.each($("[valid*='pwd']"), function () {
            var regExp = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$]).{8,}$/
            let r = regExp.test(this.value);

            if (!r) {
                alert(`${this.title}을(를) 확인해 주세요.`);
                $(this).focus();
            }

            return result = r;
        })
        if (!result) return false;

        $.each($("[valid*='email']"), function () {
            var regExp = /(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))/
            let r = regExp.test(this.value);

            if (!r) {
                alert(`${this.title}을(를) 확인해 주세요.`);
                $(this).focus();
            }

            return result = r;
        })
        if (!result) return false;

        $.each($("[valid*='bizrno']"), function () {
            var r = true;
            var bizrno = this.value;
            var numberMap = bizrno.replace(/-/gi, '').split('').map(function (d){
                return parseInt(d, 10);
            });

            if(numberMap.length == 10){
                var keyArr = [1, 3, 7, 1, 3, 7, 1, 3, 5];
                var chk = 0;

                keyArr.forEach(function(d, i){
                    chk += d * numberMap[i];
                });

                chk += parseInt((keyArr[8] * numberMap[8])/ 10, 10);

                r = Math.floor(numberMap[9]) === ( (10 - (chk % 10) ) % 10);

                if (!r) {
                    alert(`${this.title}가 유효한 패턴이 아닙니다.`);
                    $(this).focus();
                }
            } else {
                alert(`${this.title}의 자릿수를 확인해주십시오.`);
                $(this).focus();
                r = false;
            }
            return r;
        })
        if (!result) return false;

        $.each($("[valid*='num']"), function () {
            var regExp = /^[0-9]$/g;
            let r = regExp.test(this.value);

            if (!r) {
                alert(`${this.title}는 숫자만 입력 가능합니다.`);
                $(this).focus();
            }

            return result = r;
        })
        if (!result) return false;

        $.each($("[valid*='length']"), function () {
            let length = 0;
            if ($(this).attr('length')) {
                length = $(this).attr('length');
            }

            if (this.value.length < length) {
                alert(`${this.title}는 ${length}자리 까지 입력 가능합니다.`);
                $(this).focus();
                return result = false
            }

            return result = true;
        })
        if (!result) return false;

        $.each($("[valid*='notNull']"), function () {
            if (this.value == null || this.value == '' || this.value.length == 0) {
                alert(`${this.title}는 필수 항목입니다.`);
                $(this).focus();
                return result =  false;
            }

            return result = true;
        })
        if (!result) return false;

        return true;
    }
}