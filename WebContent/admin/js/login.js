function login() {
	var $custid = $("input[name='domain']");
	var $user = $("input[name='uid']");
	var $pass = $("input[name='pwd']");
	var message = "";
	if ($custid.val() == "") {
		message = message + "请输入登录客户域,";
		$custid.focus();
		return;
	}
	if ($user.val() == "") {
		message = message + "请输入登录用户名,";
		$user.focus();
		return;
	}
	if ($pass.val() == "") {
		message = message + "请输入登录密码";
		$pass.focus();
		return;
	}

	if (message != "") {
		alert(message);
		return;
	}

	$.post('admin-login!userValidate', {
				"shortName" : $custid.val(),
				"user.userName" : $user.val(),
				"user.password" : $pass.val(),
				"loginVerifyCode" : $("input[name=code]").val(),
				"autoSave" : $("input[name=autoSave]").attr("checked")
						? true
						: false
			}, function(result) {
				if (result.success) {
					window.location.href = "customer.action";
					return;
				} else {
					refresh();
					if (result.field != undefined) {
						if (result.field.loginVerifyCode != undefined) {
							alert(result.field.loginVerifyCode);
						}
					} else if (result.message != undefined) {
						alert(result.message);
					}
				}
			});
}

function refresh() {
	var timenow = new Date().getTime();
	$(".code").attr("src", "admin-login!verifyCode.action?time=" + timenow);
}